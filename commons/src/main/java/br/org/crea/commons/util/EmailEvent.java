package br.org.crea.commons.util;

import java.io.Serializable;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.ejb.Asynchronous;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.enterprise.event.Observes;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.mail.MultiPartEmail;

import br.org.crea.commons.helper.InputStreamDataSource;
import br.org.crea.commons.models.commons.DestinatarioEmailDto;
import br.org.crea.commons.models.commons.dtos.ArquivoFormUploadDto;
import br.org.crea.commons.models.commons.dtos.EmailEnvioDto;


@Singleton
public class EmailEvent implements Serializable {
	
	private static final long serialVersionUID = 6094944990171278902L;

	@Asynchronous
	@Lock(LockType.READ)
	public void consumeEvent(@Observes EmailEnvioDto emailCrea) throws InterruptedException {
		try {
			MultiPartEmail email = configuraEmail(emailCrea);
			email.send();
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Erro disparo email:" + e.getMessage());
		}
	}	
	
	private static MultiPartEmail configuraEmail(EmailEnvioDto emailCrea) {

		try {
			if (emailCrea.getMensagem() == null) emailCrea.setMensagem("");
			String mensagem = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">"
			                + "<HTML>"
			                + "<HEAD>"
			                + "<META http-equiv=Content-Type content=\"text/html; charset=iso-8859-1\">"
			                + "<META content=\"MSHTML 6.00.2800.1491\" name=GENERATOR>"
			                + "<STYLE></STYLE>" + "</HEAD>" + "<BODY"
			                + "<P>&nbsp;</P>"
			                + "<TABLE WIDTH=\"70%\" border=0 BORDER=\"0\">"
	                        + "<TR><TD height=\"38\" align=left vAlign=top bordercolor=\"#256E77\" bgcolor=\"#FFFFFF\">"
			                + "<div align=\"left\">"
			                + "<img src=\"http://portalservicos.crea-rj.org.br/images/brasaoRepublicaSituacao.png\" width=\"723\" height=\"60\" border=\"0\" usemap=\"#Map\">"
			                + "<br><br>" + "</div>" + "</TD></TR>"
			                + "<TR><TD>" + emailCrea.getMensagem() + "</TD></TR>"
			                + "</p>"
			                + "<P>&nbsp;</P>" + "</BODY>" + "</HTML>";
			emailCrea.setMensagem(mensagem);
			MultiPartEmail email = new MultiPartEmail();
			
			email.setAuthentication("ingres", "huD3LywUSjaM");
			email.setHostName("192.168.10.66");
			email.setSmtpPort(25);
			email.setFrom(emailCrea.getEmissor().trim().toLowerCase());
			email.setSubject(emailCrea.getAssunto());
			
			MimeMultipart multipart = new MimeMultipart();

			final MimeBodyPart  messageBodyPart = new MimeBodyPart();
			// HTML Content
			messageBodyPart.setContent(emailCrea.getMensagem(), "text/html;charset=UTF-8");
			// add it
			multipart.addBodyPart(messageBodyPart);
			
			/* Anexos */
			if(emailCrea.temAnexos()) {
				for (ArquivoFormUploadDto f : emailCrea.getAnexos()) {
					if (f != null) {	
						MimeBodyPart bodyPart = new MimeBodyPart();
						if(f.possuiPathDoArquivo()) {
							DataSource source = new FileDataSource(f.getPath());
							bodyPart.setDataHandler(new DataHandler(source));
						} else {
							if(f.getFile() != null && f.getFileName() != null) {
								bodyPart.setDataHandler(new DataHandler(new InputStreamDataSource(f.getFile(),f.getFileName())));
							}
						}
						
						bodyPart.setFileName(f.getFileName());
						multipart.addBodyPart(bodyPart);
					} 
				}
			}
			
			email.setContent(multipart);
			
			
			/* Destinatarios */
			if(emailCrea.getDestinatarios() != null){
				for(DestinatarioEmailDto e : emailCrea.getDestinatarios()){
					email.addTo(e.getEmail(), e.getNome());
				}
			}
	
			/* Com Cópia */
			if(emailCrea.getDestinatariosCC() != null){
				for(DestinatarioEmailDto e : emailCrea.getDestinatariosCC()){
					email.addCc(e.getEmail(), e.getNome());
				}
			}
	
			/* Cópia Oculta	 */
			if(emailCrea.getDestinatariosCCO() != null){
				for(DestinatarioEmailDto e : emailCrea.getDestinatariosCCO()){
					email.addBcc(e.getEmail(), e.getNome());
				}
			}
			
			return email;
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Erro Configura Email:" + e.getMessage());
		}
	}	

}
