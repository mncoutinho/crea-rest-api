package br.org.crea.commons.factory.commons;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.helper.HelperMessages;
import br.org.crea.commons.models.art.ContratoArt;
import br.org.crea.commons.models.commons.DestinatarioEmailDto;
import br.org.crea.commons.models.commons.dtos.EmailEnvioDto;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;
import br.org.crea.commons.service.EmailService;
import br.org.crea.commons.util.EmailUtil;

public class EmailFactory {

	@Inject private EmailService emailService;
	@Inject private EmailUtil emailUtil;
	@Inject private HelperMessages messages;

	public void enviarEmailPadrao(List<DestinatarioEmailDto> destinatarios, String assunto, String mensagem) {
		EmailEnvioDto email = new EmailEnvioDto();
		email.setDestinatarios(destinatarios);

		if (email.getDestinatarios().size() > 0) {
			email.setEmissor(emailUtil.EMAIL_ATENDIMENTO);
			email.setDataUltimoEnvio(new Date());
			email.setAssunto(assunto);
			email.setMensagem(mensagem);			
			
			emailService.envia(email);
		}

	}

	public void enviarEmailTaxaDeIncorporacao(Long idPessoa, String numeroArt) {
		enviarEmailPadrao(emailUtil.montarListaDestinatarios(idPessoa), "Taxa de Incorporação", messages.corpoEmailEnvioTaxaDeIncorporacao(emailUtil.populaNomePessoa(idPessoa), numeroArt));
	}

	public void enviarEmailCadastroDeArtPorEmpresa(ContratoArt contrato) {
		enviarEmailPadrao(emailUtil.montarListaDestinatarios(contrato.getArt().getProfissional().getId()), "CREA-RJ - Cadastro de ART", messages.corpoEmailEnvioCadastroDeArtPorEmpresa(contrato));
	}

	public void enviarEmailCadastroOuvidoriaAtendimento(Long idPessoa, Long idOuvidoria) {
		enviarEmailPadrao(emailUtil.montarListaDestinatarios(idPessoa), "CREA-RJ - Cadastro de Atendimento Ouvidoria", messages.corpoEmailEnvioCadastroAtendimentoOuvidoria(idOuvidoria));
	}

	public void enviarEmailSenha(Pessoa pessoa, String senha, boolean enviarResponsavel) {		
		enviarEmailPadrao(emailUtil.montarListaDestinatarios(pessoa.getId(), enviarResponsavel ? pessoa.getIdPessoaResponsavel() : null), "Envio de Nova Senha", messages.corpoEmailEnvioSenha(senha));
	}	

	public void enviarEmailSolicitacaoSegundaViaDeCarteira(Long idPessoa) { 
		// FIXME alterar msg incluindo brasao, eh necessario? enviar anexo tambem
		enviarEmailPadrao(emailUtil.montarListaDestinatarios(idPessoa), "Solicitação de Segunda Via de Carteira", messages.corpoEmailEnvioSolicitacaoSegundaViaDeCarteira());
	}
}
