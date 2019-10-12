package br.org.crea.commons.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import br.org.crea.commons.dao.CommonsGenericDao;
import br.org.crea.commons.dao.cadastro.pessoa.InteressadoDao;
import br.org.crea.commons.models.cadastro.EntidadeClasse;
import br.org.crea.commons.models.commons.DestinatarioEmailDto;
import br.org.crea.commons.models.commons.TipoAtendimento;
import br.org.crea.commons.models.commons.dtos.AuthenticationDto;
import br.org.crea.commons.models.commons.dtos.AuthenticationSemLoginDto;
import br.org.crea.commons.models.commons.dtos.EmailEnvioDto;
import br.org.crea.commons.models.commons.dtos.GenericDto;
import br.org.crea.commons.models.commons.dtos.TelefoneDto;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.models.corporativo.dtos.LeigoDto;
import br.org.crea.commons.models.corporativo.enuns.TipoPessoa;
import br.org.crea.commons.models.redmine.RedMineIssueDto;
import br.org.crea.commons.util.EmailEvent;

public class CommonsService {

	@Inject
	CommonsGenericDao dao;

	@Inject
	InteressadoDao interessadoDao;

	@Inject
	EmailService emailService;

	@Inject
	EmailEvent evento;

	@Inject
	PessoaService pessoaService;
	
	@Inject 
	HttpRedMineApi redmineHttp;

	public List<TipoAtendimento> getTiposAtendimento() {
		return dao.getListTipoAtendimento();
	}

	public boolean cpfHeValido(String cpf) {

		CPFValidator validator = new CPFValidator();

		try {
			validator.assertValid(cpf);
			return true;
		} catch (InvalidStateException e) {
			return false;
		}

	}

	public boolean cnpjHeValido(String cnpj) {

		CNPJValidator validator = new CNPJValidator();

		try {
			validator.assertValid(cnpj);
			return true;
		} catch (InvalidStateException e) {
			return false;
		}
	}

	public boolean existeCpjOuCnpjNaBaseDoCrea(String cpf) {
		return dao.validaCpfNoCrea(cpf);
	}

	public boolean existeCpfOuCnpjNoAgendamento(AuthenticationSemLoginDto auth) {

		if (auth.hePessoaFisica()) {
			return dao.validaCpfOuCnpjNoAgendamento(auth.getCpfOuCnpj());
		} else {
			return dao.validaCpfOuCnpjNoAgendamento(auth.getCpfOuCnpj());
		}

	}

	public boolean validaCnpjNoCrea(String cnpj) {
		return dao.validaCnpjNoCrea(cnpj);
	}

	public boolean validaRnp(String rnp) {
		return dao.validaRnpNoCrea(rnp);
	}

	public boolean validaRegistro(String registro) {
		return dao.validaRegistroNoCrea(registro);
	}

	public List<GenericDto> getEntidadesClasse() {

		List<GenericDto> listaEntidadeClasseDto = new ArrayList<GenericDto>();

		for (EntidadeClasse e : dao.getEntidadesClasse()) {

			GenericDto dto = new GenericDto();

			dto.setIdRegistro(e.getId());
			dto.setSigla(e.getPessoaJuridica().getAbreviatura());
			dto.setDescricao(interessadoDao.buscaDescricaoRazaoSocial(e.getPessoaJuridica().getId()));

			listaEntidadeClasseDto.add(dto);

		}

		return listaEntidadeClasseDto;
	}


	public boolean cpfOuCnpjJaExisteNaBaseDoCrea(String cpfOuCnpj) {

		return cpfOuCnpj.length() == 11  ? dao.validaCpfNoCrea(cpfOuCnpj) : dao.validaCnpjNoCrea(cpfOuCnpj);

	}

	public LeigoDto populaLeigoDto(AuthenticationSemLoginDto auth) {

		LeigoDto leigoDto = new LeigoDto();

		leigoDto.setCpfOuCnpj(auth.getCpfOuCnpj());
		leigoDto.setNome(auth.getNome());
		leigoDto.setEmail(auth.getEmail());

		if (auth.hePessoaFisica()) {
			leigoDto.setTipoPessoa(TipoPessoa.LEIGOPF);
		} else {
			leigoDto.setTipoPessoa(TipoPessoa.LEIGOPJ);
		}

		List<TelefoneDto> listaTelefone = new ArrayList<TelefoneDto>();
		listaTelefone.add(auth.getTelefone());
		leigoDto.setTelefones(listaTelefone);

		return leigoDto;
	}

	public LeigoDto cadastrarLeigo(AuthenticationSemLoginDto auth) {

		LeigoDto leigoDto = populaLeigoDto(auth);

		return pessoaService.cadastrarLeigo(leigoDto);

	}

	public boolean validaFormatoCpfOuCnpj(String cpfOuCnpj) {
		return cpfOuCnpj.length() == 11 ? cpfHeValido(cpfOuCnpj) : cnpjHeValido(cpfOuCnpj);
	}
	
	public boolean validaFormatoCpfOuRegistro(AuthenticationDto auth) {
		
		if(auth.ehPessoaFisica()) {
			return cpfHeValido(auth.getLogin());
		} else {
			return auth.getLogin().length() == 10 | auth.getLogin().length() == 12;
		}
		
	}
	
	public boolean cpfOuRegistroExisteNaBase(AuthenticationDto auth) {
		
		if(auth.ehPessoaFisica()) {
			return dao.validaCpfNoCrea(auth.getLogin());
		} else {
			return dao.validaRegistroNoCrea(auth.getLogin());
		}
	}

	public RedMineIssueDto issueRedmine(RedMineIssueDto dto, UserFrontDto userDto) {
		
		EmailEnvioDto email = new EmailEnvioDto();
		DestinatarioEmailDto destino = new DestinatarioEmailDto();
		List<DestinatarioEmailDto> listDestino = new ArrayList<DestinatarioEmailDto>();
		StringBuilder sb = new StringBuilder();
		
		email.setEmissor(emailService.getUltimoEmailCadastrado(userDto.getIdPessoa()));
		email.setAssunto(dto.getIssue().getSubject());
		
		sb.append("Registro: " + userDto.getRegistro() + "Nome: " + userDto.getNome() + "\n");
		sb.append( "\n");
		sb.append(dto.getIssue().getDescription());
		email.setMensagem(sb.toString());
		
		if(dto.enviaAtendimento()) {
			destino.setEmail("ricanalista@gmail.com");
			destino.setNome("ricardo");
			
		} else {
			destino.setEmail("ricanalista@gmail.com");
			destino.setNome("ricardo");
		}
		
		listDestino.add(destino);
		email.setDestinatarios(listDestino);
		emailService.envia(email);
		
		dto.getIssue().setDescription(sb.toString());
		redmineHttp.salvaIssue(dto);
		return dto;
	}

	public boolean bancoOn() {
		return dao.bancoOn();
	}

}