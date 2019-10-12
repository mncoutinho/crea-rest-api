package br.org.crea.corporativo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import br.org.crea.commons.converter.cadastro.pessoa.PessoaConverter;
import br.org.crea.commons.dao.AuthDao;
import br.org.crea.commons.dao.cadastro.administrativo.UnidadeAtendimentoDao;
import br.org.crea.commons.dao.cadastro.empresa.RazaoSocialDao;
import br.org.crea.commons.dao.cadastro.funcionario.FuncionarioDao;
import br.org.crea.commons.dao.cadastro.pessoa.InteressadoDao;
import br.org.crea.commons.dao.cadastro.pessoa.PessoaDao;
import br.org.crea.commons.factory.commons.EmailFactory;
import br.org.crea.commons.models.atendimento.AgendamentoMobile;
import br.org.crea.commons.models.cadastro.Departamento;
import br.org.crea.commons.models.commons.TokenUser;
import br.org.crea.commons.models.commons.dtos.AuthenticationDto;
import br.org.crea.commons.models.commons.dtos.AuthenticationSemLoginDto;
import br.org.crea.commons.models.commons.dtos.GenericDto;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.models.corporativo.dtos.LeigoDto;
import br.org.crea.commons.models.corporativo.dtos.PessoaDto;
import br.org.crea.commons.models.corporativo.dtos.SituacaoDto;
import br.org.crea.commons.models.corporativo.enuns.SituacaoRegistro;
import br.org.crea.commons.models.corporativo.enuns.TipoPessoa;
import br.org.crea.commons.models.corporativo.pessoa.Funcionario;
import br.org.crea.commons.models.corporativo.pessoa.IInteressado;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;
import br.org.crea.commons.models.corporativo.pessoa.PessoaJuridica;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.service.HttpCouchApi;
import br.org.crea.commons.service.PessoaService;
import br.org.crea.commons.service.commons.LoginLogService;
import br.org.crea.commons.service.funcionario.PerfilService;
import br.org.crea.commons.util.HashUtil;
import br.org.crea.commons.util.IpUtil;
import br.org.crea.commons.util.StringUtil;
import br.org.crea.commons.util.TokenUtil;
import eu.bitwalker.useragentutils.UserAgent;

public class AuthService {

	@Inject
	AuthDao dao;

	@Inject
	InteressadoDao interessadoDao;

	@Inject
	PessoaDao pessoaDao;

	@Inject
	UnidadeAtendimentoDao unidadeAtendimentoDao;

	@Inject
	PessoaService pessoaService;

	@Inject
	FuncionarioDao funcionarioDao;

	@Inject
	PerfilService perfilService;

	@Inject
	HttpClientGoApi httpClientGoApi;

	@Inject
	HttpCouchApi httpCouchApi;

	@Inject
	PessoaConverter pessoaConverter;

	@Inject
	RazaoSocialDao razaoSocialDao;
	
	@Inject
	EmailFactory emailFactory;
	
	@Inject
	LoginLogService loginLogService;
	
	public UserFrontDto loginPorRegistro(AuthenticationDto auth, HttpServletRequest request) {

		Pessoa pessoa = new Pessoa();
		UserFrontDto dto = null;
		pessoa = dao.autentica(Long.parseLong(auth.getLogin().trim()), auth.getSenha());

		if (pessoa != null) {

			IInteressado interessado = interessadoDao.buscaInteressadoBy(pessoa);
			
			dto = toDto(interessado);
			setPertilEGeraToken(request, pessoa, dto);

		}
		
		return dto;

	}

	private void setPertilEGeraToken(HttpServletRequest request, Pessoa pessoa, UserFrontDto dto) {
		dto.setPerfil(httpCouchApi.getPerfil(pessoa.getPerfil()));
		dto.setSituacao(populaSituacaoDto(pessoa));
		dto.setIdPessoa(pessoa.getId());
		if(pessoa.temIdInstituicao()) dto.setIdInstituicao(pessoa.getIdInstituicao());
		geraToken(request, dto);
	}

	public UserFrontDto loginPorMatricula(AuthenticationDto auth, HttpServletRequest request) {

		UserFrontDto dto = null;
		Funcionario funcionario = new Funcionario();
		funcionario = dao.autenticaPorLogin(auth);

		if (funcionario != null) {

			Pessoa pessoa = new Pessoa();
			pessoa = pessoaDao.getBy(funcionario.getPessoaFisica().getId());

			IInteressado interessado = interessadoDao.buscaInteressadoBy(funcionario.getPessoaFisica().getId());
			
			if(interessado != null) {
				dto = toDtoFuncionario(interessado, funcionario);
				setPertilEGeraToken(request, pessoa, dto);
			}

			return dto;

		} else {
			return null;
		}

	}

	private UserFrontDto toDto(IInteressado interessado) {

		UserFrontDto dto = new UserFrontDto();

		dto.setRegistro(interessado.getRegistro());
		dto.setCpfOuCnpj(interessado.getCpfCnpj());
		dto.setRazaoSocial(interessado.getNomeRazaoSocial());
		dto.setNome(interessado.getNome());
		dto.setTipoPessoa(interessado.getTipoPessoa());
		dto.setBase64(interessado.getFotoBase64());

		return dto;
	}

	private UserFrontDto toDtoFuncionario(IInteressado interessado, Funcionario funcionario) {

		UserFrontDto dto = new UserFrontDto();

		dto.setRegistro(interessado.getRegistro());
		dto.setCpfOuCnpj(interessado.getCpfCnpj());
		dto.setRazaoSocial(interessado.getNomeRazaoSocial());
		dto.setNome(interessado.getNome());
		dto.setTipoPessoa(interessado.getTipoPessoa());
		dto.setBase64(interessado.getFotoBase64());
//		dto.setBase64Assinatura(interessado.getAssinaturaBase64());
		dto.setMatricula(funcionario.getMatricula());
		
		if(funcionario.temCargo()){
			GenericDto cargo = new GenericDto();
			cargo.setId(String.valueOf(funcionario.getCargo().getId()));
			cargo.setDescricao(funcionario.getCargo().getDescricao());
			dto.setCargo(cargo);
		}

		if (funcionario.temDepartamento()) {

			GenericDto departamentoDto = new GenericDto();
			GenericDto unidadeAtendimentoDto = new GenericDto();

			departamentoDto.setId(String.valueOf(funcionario.getDepartamento().getId()));
			departamentoDto.setNome(funcionario.getDepartamento().getNome());
			departamentoDto.setSigla(funcionario.getDepartamento().getSigla());
			dto.setDepartamento(departamentoDto);

			Departamento unidadeAtendimento = unidadeAtendimentoDao.getUnidadeAtendimentoByFuncionario(funcionario);
			unidadeAtendimentoDto.setId(String.valueOf(unidadeAtendimento.getId()));

			dto.setUnidadeDeAtendimento(unidadeAtendimentoDto);
			dto.setIdFuncionario(funcionario.getId());
		}

		return dto;
	}

	public UserFrontDto autenticaNoAgendamento(AuthenticationSemLoginDto auth, HttpServletRequest request) {

		UserFrontDto dto = new UserFrontDto();

		AgendamentoMobile agendamento = dao.getNomeEmailUsuarioSemRegistro(auth.getCpfOuCnpj());

		if (agendamento != null) {
			dto.setNome(agendamento.getNome());
			dto.setEmail(agendamento.getEmail());
		}

		if (auth.hePessoaFisica()) {
			dto.setTipoPessoa(TipoPessoa.LEIGOPF);
		} else {
			dto.setTipoPessoa(TipoPessoa.LEIGOPJ);
		}

		dto.setSituacao(populaSituacaoLeigo());
		dto.setSemRegistro(true);
		dto.setCpfOuCnpj(auth.getCpfOuCnpj());

		geraToken(request, dto);

		return dto;

	}

	public UserFrontDto autenticaPorCpfOuCnpj(AuthenticationSemLoginDto auth, HttpServletRequest request) {

		UserFrontDto dto = new UserFrontDto();
		List<PessoaDto> pessoaDto = new ArrayList<PessoaDto>();

		if (auth.hePessoaFisica()) {
			pessoaDto = pessoaService.getPessoaByNumeroCPF(auth.getCpfOuCnpj());
			dto.setCpfOuCnpj(pessoaDto.get(0).getCpf());
			dto.setNome(pessoaDto.get(0).getNome());

		} else {
			pessoaDto = pessoaService.getPessoaByNumeroCNPJ(auth.getCpfOuCnpj());
			if (pessoaDto.size() > 0) {
				dto.setCpfOuCnpj(pessoaDto.get(0).getCnpj());
			} else {
				return null;
			}			
		}

		dto.setSituacao(pessoaDto.get(0).getSituacao());
		dto.setEmail(pessoaDto.get(0).getEmail());
		dto.setNome(pessoaDto.get(0).getNome().toUpperCase());
		dto.setTipoPessoa(pessoaDto.get(0).getTipo());
		dto.setIdPessoa(pessoaDto.get(0).getId());

		geraToken(request, dto);

		return dto;

	}
	
	public UserFrontDto loginPorCpfOuCnpj(AuthenticationDto auth, HttpServletRequest request) {
		
		Pessoa pessoa = new Pessoa();
		UserFrontDto dto = null;
		List<PessoaDto> pessoaDto = new ArrayList<PessoaDto>();

		if (auth.ehCpf()) { // conferir se comparação é válida
			pessoaDto = pessoaService.getPessoaByNumeroCPF(auth.getLogin());
		} else {
			pessoaDto = pessoaService.getPessoaByNumeroCNPJ(auth.getLogin());
		}
		
		pessoa = dao.autentica(pessoaDto.get(0).getId(), auth.getSenha());

		if (pessoa != null) {

			IInteressado interessado = interessadoDao.buscaInteressadoBy(pessoa);
			
			dto = toDto(interessado);
			setPertilEGeraToken(request, pessoa, dto);
		}

		return dto;
	}
	
	public UserFrontDto loginPorCpfOuRegistro(AuthenticationDto auth, HttpServletRequest request) {
		
		Pessoa pessoa = new Pessoa();
		UserFrontDto dto = null;
		List<PessoaDto> pessoaDto = new ArrayList<PessoaDto>();

		if (auth.ehPessoaFisica()) { 
			pessoaDto = pessoaService.getPessoaByNumeroCPF(auth.getLogin());
		} else {
			pessoaDto.add(pessoaService.getInteressadoBy(Long.parseLong(auth.getLogin())));
		}
		
		pessoa = dao.autentica(pessoaDto.get(0).getId(), auth.getSenha());

		if (pessoa != null) {

			IInteressado interessado = interessadoDao.buscaInteressadoBy(pessoa);
			
			dto = toDto(interessado);
			setPertilEGeraToken(request, pessoa, dto);

			loginLogService.registra(IpUtil.getClientIpAddr(request), pessoa.getId(), "CREAONLINE2 - Logou no sistema");
			
		} else {
			loginLogService.registra(IpUtil.getClientIpAddr(request), pessoaDto.get(0).getId(), "CREAONLINE2 - Tentativa de login - senha inválida");
		}

		return dto;
	}

	public UserFrontDto autenticaTempAgendamento(LeigoDto authLeigoDto, HttpServletRequest request) {

		UserFrontDto dto = new UserFrontDto();

		if (authLeigoDto.getTipoPessoa().equals(TipoPessoa.LEIGOPF)) {
			dto.setTipoPessoa(TipoPessoa.LEIGOPF);
		} else {
			dto.setTipoPessoa(TipoPessoa.LEIGOPJ);
		}

		dto.setIdPessoa(authLeigoDto.getId());
		dto.setCpfOuCnpj(authLeigoDto.getCpfOuCnpj());
		dto.setNome(authLeigoDto.getNome());
		dto.setEmail(authLeigoDto.getEmail());
		dto.setSemRegistro(true);
		dto.setSituacao(populaSituacaoLeigo());

		geraToken(request, dto);

		return dto;
	}

	private void geraToken(HttpServletRequest request, UserFrontDto dto) {

		UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
		dto.setIp(IpUtil.getClientIpAddr(request));
		dto.setBrowser(userAgent.getBrowser() + " Version: " + userAgent.getBrowserVersion());
		dto.setSistemaOperacional(userAgent.getOperatingSystem().toString());

		TokenUser token = TokenUtil.generateTokenWith(dto);
		dto.setToken(token.getId());
		token.setTimeLife(21600);// 6 horas

		httpClientGoApi.salvaToken(token);

	}

	public String geraTokenComTempoDeVidaCustomizado(int tempo) {
		
		UserFrontDto dto = new UserFrontDto();
		dto.setNome("Usuario Sistema");
		
		TokenUser token = TokenUtil.generateTokenWith(dto);
		dto.setToken(token.getId());
		token.setTimeLife(tempo);
		
		httpClientGoApi.salvaToken(token);
		
		return token.getId();
		
	}

	private SituacaoDto populaSituacaoLeigo() {
		SituacaoDto situacao = new SituacaoDto();
		situacao.setId(new Long(0));
		return situacao;
	}

	private SituacaoDto populaSituacaoDto(Pessoa pessoa) {
		SituacaoDto dto = new SituacaoDto();
		if (pessoa.temSituacao()) {
			dto.setId(pessoa.getSituacao().getId());
			dto.setDescricao(pessoa.getSituacao().getDescricao());
		}
		return dto;
	}

	public void logoff(String token) {
		httpClientGoApi.deleteToken(token);
	}

	public UserFrontDto verificaToken(String token) {
		if (!StringUtil.isValidAndNotEmpty(token))
			return null;
		return httpClientGoApi.validaToken(token);
	}

	public UserFrontDto autenticaPorCnpjRestrito(AuthenticationSemLoginDto auth, HttpServletRequest request) {
		
		UserFrontDto dto = null;
		PessoaJuridica pessoa = new PessoaJuridica();
		pessoa = dao.autenticaCnpj(auth);
		
		if(pessoa != null){
			
			dto = new UserFrontDto(); 

			String nome = razaoSocialDao.buscaDescricaoRazaoSocial(pessoa.getId());
			dto.setCpfOuCnpj(pessoa.getCnpj());
			dto.setEmail(pessoa.getEmail());
			dto.setNome(nome);
			dto.setTipoPessoa(pessoa.getTipoPessoa());
			dto.setIdPessoa(pessoa.getId());
			dto.setTrocarSenha(pessoa.getDataSenha() == null);

			geraToken(request, dto);
			
		}

		return dto;
	}

	public void gerarSenha(AuthenticationDto auth) {
		
		Pessoa pessoa = pessoaService.getPessoaByTipoPessoa(auth.getTipo(), auth.getLogin());
		
		emailFactory.enviarEmailSenha(pessoa, gerarSenhaEAtualizarPessoa(pessoa), auth.isEnviarResponsavel());
	}
	
	private String gerarSenhaEAtualizarPessoa(Pessoa pessoa){
		
		String senha = StringUtil.gerarSenha();
		
		String senhaCriptografada = HashUtil.criptografa(senha);
		
		pessoa.setSenha(senhaCriptografada);
		pessoa.setDataSenha(new Date());
		
		pessoaDao.update(pessoa);
		
		return senha;
	}

	public boolean alterarSenha(AuthenticationDto auth) {
		
		Pessoa pessoa = pessoaDao.getPessoa(Long.parseLong(auth.getLogin()));
				
		String senhaCriptografada = HashUtil.criptografa(auth.getNovaSenha());
		
		if (validaSeSenhaEstaCorreta(HashUtil.criptografa(auth.getSenha()), pessoa.getSenha())) {
			pessoa.setSenha(senhaCriptografada);
			pessoa.setDataSenha(new Date());
			pessoaDao.update(pessoa);
			return true;
		} else {
			return false;
		}
	}
	
	private boolean validaSeSenhaEstaCorreta(String senhaAntiga, String senha ) {
		if(senhaAntiga.equals(senha) ){
			return true;
		} else {
			return false;
		}
	}
	
	public UserFrontDto verificarAutorizacao(UserFrontDto dto) {
		if(dto.heProfissional()) {
			if(dao.profissionalPossuiEventoTransferidoCAUComDataFinalVazia(dto.getRegistro())){
				httpClientGoApi.deleteToken(dto.getToken());
				dto.setMensagem(
						"Conforme a Lei 12.378/2010, as atividades de Arquitetura e Urbanismo passam a ser fiscalizadas pelo\n" + 
						"Conselho de Arquitetura e Urbanismo. Acesse www.caubr.org.br ou ligue\n" + 
				        "para (21) 2103-1952, 2103-1954 ou 2103-1957.");
				return dto;
			}
			if(dao.profissionalPossuiEventoTransferidoParaCFTComDataFinalVazia(dto.getRegistro())) {
				httpClientGoApi.deleteToken(dto.getToken());
				dto.setMensagem(
						"Conforme Lei 13.639/2018, as atividades dos Técnicos Industriais passam a ser fiscalizadas pelo\n" + 
						"Conselho Federal dos Técnicos Industriais. Acesse www.cft.org.br ou\n" + 
						"envie email para contato@cft.org.br");
				return dto;
			}
			Funcionario funcionario = funcionarioDao.getFuncionarioByPessoa(dto.getIdPessoa());
			if(funcionario.getId() == null) {
				if(dao.profissionalPossuiSituacaoRegistroNovoInscritoOuDataRegistroVazia(dto.getRegistro())) {
					httpClientGoApi.deleteToken(dto.getToken());
					dto.setMensagem(
							"O seu registro encontra-se em tramitação e ainda não foi efetivado.");
					return dto;
				}
			}
//			if (dto.getSituacao().getId().longValue() == SituacaoRegistro.CANCELADO.getCodigo().longValue() || dto.getSituacao().getId().longValue() == SituacaoRegistro.SUSPENSO.getCodigo().longValue() ) {
//				httpClientGoApi.deleteToken(dto.getToken());
//				dto.setMensagem(
//						"Entre em contato com a Central de Relacionamento: (21) 2179-2007, de segunda a sexta-feira, no horário de 09:00 as 17:30.");
//				return dto;
//			}
		}
		if(dto.heEmpresa()) {
			if(dao.empresaPossuiEventoTransferidoCAU(dto.getRegistro())) {
				httpClientGoApi.deleteToken(dto.getToken());
				dto.setMensagem(
						"Conforme a Lei 12.378/2010, as atividades de Arquitetura e Urbanismo passam a ser fiscalizadas pelo\n" + 
						"Conselho de Arquitetura e Urbanismo. Acesse www.caubr.org.br ou ligue\n" + 
						"para (21) 2103-1952, 2103-1954 ou 2103-1957.");
				return dto;
			}
			if(dao.empresaPossuiEventoExcluidoOuSuspensoOuInterropidoOuSemValidade(dto.getRegistro())) {
				httpClientGoApi.deleteToken(dto.getToken());
				dto.setMensagem(
						"Entre em contato com a Central de Relacionamento: (21) 2179-2007 de segunda a sexta-feira, no horário de 09:00 as 17:30.");
				return dto;
			}
			//FIXME AVALIAR NO FUTURO SE EMPRESA EMM REGISTRO VAI ACESSAR
//			if(dao.empresaPossuiDataRegistroVaziaETipoCategoriaRegistroDiferenteDeVisto(dto.getRegistro())) {
//				httpClientGoApi.deleteToken(dto.getToken());
//				dto.setMensagem(
//						"A empresa ainda não possui o seu registro efetivado.");
//				return dto;
//			}
		}
		
		return dto;
	}


	
}
