package br.org.crea.restapi.util;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.junit.Test;

public class TestEmail
{
	  @Test
      public void main() {
		  
		  	final String username = "renan.bastos@crea-rj.org.br";
		  	final String password = "qazsw@3ed";
            Properties props = new Properties();
        /** Parâmetros de conexão com servidor Gmail */
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp-relay.gmail.com");
            props.put("mail.smtp.port", "587");
            

            Session session = Session.getInstance(props,
                        new javax.mail.Authenticator() {
            	
                             protected PasswordAuthentication getPasswordAuthentication()
                
                             {
                                   return new PasswordAuthentication(username, password);
                             }
                        });

          /** Ativa Debug para sessão */
            session.setDebug(true);

            try {

                  Message message = new MimeMessage(session);
                  message.setFrom(new InternetAddress("renan.bastos@crea-rj.org.br")); //Remetente

                  Address[] toUser = InternetAddress //Destinatário(s)
                             .parse("crea.ctec@crea-rj.org.br");  

                  message.setRecipients(Message.RecipientType.TO, toUser);
                  message.setSubject("Enviando email com JavaMail");//Assunto
                  message.setText("Enviei este email utilizando JavaMail com minha conta GMail!");
                 /**Método para enviar a mensagem criada*/
                  Transport.send(message);

                  System.out.println("Feito!!!");

          } catch (MessagingException e) {
               throw new RuntimeException(e);
          }
      }
}