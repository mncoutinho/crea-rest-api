package br.org.crea.commons.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.dao.cadastro.EmailDao;
import br.org.crea.commons.models.cadastro.dtos.EmailDto;
import br.org.crea.commons.models.commons.DestinatarioEmailDto;
import br.org.crea.commons.models.commons.dtos.EmailEnvioDto;
import br.org.crea.commons.models.corporativo.dtos.PessoaDto;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;
import br.org.crea.commons.service.PessoaService;

public class EmailUtil {
	
	@Inject PessoaService pessoaService;
	
	@Inject EmailDao dao;
	
	public final String EMAIL_ATENDIMENTO = "atendimento@crea-rj.org.br";
	
	
	public List<DestinatarioEmailDto> montarListaDestinatarios(String email, String nome) {
		List<DestinatarioEmailDto> listaEmails = new ArrayList<DestinatarioEmailDto>();

		DestinatarioEmailDto destinatario = new DestinatarioEmailDto();
		destinatario.setEmail(email);
		destinatario.setNome(nome);

		listaEmails.add(destinatario);

		return listaEmails;
	}
	
	public List<DestinatarioEmailDto> montarListaDestinatarios(Pessoa pessoa) {
		List<DestinatarioEmailDto> listaEmails = new ArrayList<DestinatarioEmailDto>();

		String emailPessoa = dao.getUltimoEmailCadastradoPor(pessoa.getId());

		if (emailPessoa != null) {

			DestinatarioEmailDto destinatario = new DestinatarioEmailDto();
			destinatario.setEmail(emailPessoa);
			destinatario.setNome(populaNomePessoa(pessoa.getId()));

			listaEmails.add(destinatario);
		}

		return listaEmails;
	}
	
	public List<DestinatarioEmailDto> montarListaDestinatarios(Long idPessoa) {
		List<DestinatarioEmailDto> listaEmails = new ArrayList<DestinatarioEmailDto>();

		String emailPessoa = dao.getUltimoEmailCadastradoPor(idPessoa);

		if (emailPessoa != null) {

			DestinatarioEmailDto destinatario = new DestinatarioEmailDto();
			destinatario.setEmail(emailPessoa);
			destinatario.setNome(populaNomePessoa(idPessoa));

			listaEmails.add(destinatario);
		}

		return listaEmails;
	}
	
	public List<DestinatarioEmailDto> montarListaDestinatarios(Long ... idsPessoa) {
		
		List<DestinatarioEmailDto> listaEmails = new ArrayList<DestinatarioEmailDto>();
		
		for (Long idPessoa : idsPessoa) {
			if (idPessoa != null) {
				String emailPessoa = dao.getUltimoEmailCadastradoPor(idPessoa);

				if (emailPessoa != null) {

					DestinatarioEmailDto destinatario = new DestinatarioEmailDto();
					destinatario.setEmail(emailPessoa);
					destinatario.setNome(populaNomePessoa(idPessoa));

					listaEmails.add(destinatario);
				}
			}
		}		

		return listaEmails;
	}
	
	public String populaNomePessoa(Long idPessoa) {
		PessoaDto pessoa = pessoaService.getInteressadoBy(idPessoa);
		return pessoa.getNome();
	}
	
	public EmailEnvioDto populaEmail(List<DestinatarioEmailDto> listaDestinatario, String emissor, String assunto,
			String mensagem) {

		EmailEnvioDto email = new EmailEnvioDto();

		email.setEmissor(emissor);
		email.setDataUltimoEnvio(new Date());
		email.setDestinatarios(listaDestinatario);

		email.setAssunto(assunto);
		email.setMensagem(mensagem);

		return email;
	}
	
	public boolean validaEmail(EmailDto dto) {
		return !dao.existeEmailCadastrado(dto) && StringUtil.ehEmail(dto.getDescricao());
	}
}
