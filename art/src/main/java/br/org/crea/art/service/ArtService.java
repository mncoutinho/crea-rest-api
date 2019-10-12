package br.org.crea.art.service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.parsers.ParserConfigurationException;

import br.org.crea.commons.converter.ReportConverter;
import br.org.crea.commons.converter.art.ArtConverter;
import br.org.crea.commons.converter.art.ContratoArtConverter;
import br.org.crea.commons.dao.art.ArtDao;
import br.org.crea.commons.dao.art.ArtGeradorSequenciaDao;
import br.org.crea.commons.dao.art.ContratoArtDao;
import br.org.crea.commons.dao.cadastro.pessoa.EnderecoDao;
import br.org.crea.commons.factory.art.AuditaArtFactory;
import br.org.crea.commons.factory.art.LogArtFactory;
import br.org.crea.commons.models.art.Art;
import br.org.crea.commons.models.art.BaixaArt;
import br.org.crea.commons.models.art.ContratoArt;
import br.org.crea.commons.models.art.NaturezaArt;
import br.org.crea.commons.models.art.ParticipacaoTecnicaArt;
import br.org.crea.commons.models.art.dtos.ArtDto;
import br.org.crea.commons.models.art.dtos.ArtExigenciaDto;
import br.org.crea.commons.models.art.dtos.ArtMinDto;
import br.org.crea.commons.models.art.dtos.PesquisaArtDto;
import br.org.crea.commons.models.cadastro.EntidadeClasse;
import br.org.crea.commons.models.cadastro.dtos.DocumentoDto;
import br.org.crea.commons.models.commons.Endereco;
import br.org.crea.commons.models.commons.dtos.DomainGenericDto;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.models.commons.enuns.TipoAcaoEnum;
import br.org.crea.commons.models.corporativo.pessoa.Empresa;
import br.org.crea.commons.models.corporativo.pessoa.PessoaFisica;
import br.org.crea.commons.models.corporativo.pessoa.PessoaJuridica;
import br.org.crea.commons.models.corporativo.pessoa.Profissional;
import br.org.crea.commons.report.ReportManager;
import br.org.crea.commons.report.TemplateReportEnum;

public class ArtService {

	@Inject
	ReportConverter reportConverter;

	@Inject
	ReportManager reportManager;

	@Inject
	ArtConverter converter;

	@Inject
	ArtDao dao;

	@Inject
	ArtGeradorSequenciaDao geradorSequenciaDao;

	@Inject
	ContratoArtDao daoContrato;

	@Inject
	ContratoArtConverter contratoArtConverter;

	@Inject
	ArtAcaoOrdinariaService acaoOrdinariaService;

	@Inject
	LogArtFactory artLog;
	
	@Inject
	EnderecoDao enderecoDao;
	
	@Inject
	private AuditaArtFactory audita;

	public List<String> getArtPorProfissional(Long idProfissional) {
		return dao.getArtPorProfissional(idProfissional);
	}

	public List<String> getArtEmExigenciaPorProfissional(Long idProfissional) {
		return dao.getArtEmExigenciaPorProfissional(idProfissional);
	}

	public List<String> getArtPorEmpresa(Long idEmpresa) {
		List<String> arts = new ArrayList<String>();
		arts = dao.getArtPorEmpresa(idEmpresa);
		return arts;
	}

	public ArtDto getArtPor(String idArt) {
		return converter.toDto(dao.getArtPorId(idArt));
	}

	public ArtDto getArtFormularioPor(String idArt) {
		return converter.toDtoFormulario(dao.getArtPorId(idArt));
	}

	public ArtDto getDetalhado(String numeroArt) {
		return converter.toDto(dao.getArtPor(numeroArt));
	}

	public List<ArtDto> listaArts(PesquisaArtDto pesquisa) {
		return converter.toListDto(dao.getListArtPor(pesquisa));
	}

	public ArtDto novaArt(UserFrontDto usuario, Long idNatureza) {

		Art art = new Art();
		art.setNumero(geradorSequenciaDao.getSequenciaArt());
		art.setDataCadastro(new Date());
		art.setIsOnline(true);
		art.setFinalizada(false);
		art.setFuncionarioCadastro(new Long(99990));
		art.setFuncionarioAlteracao(new Long(99990));
		art.setIsTermoAditivo(false);
		art.setWebService(false);
		art.setIsAcaoOrdinaria(false);
		art.setMultiplaMensal(false);
		art.setIsCertificada(false);
		art.setLiberada(false);
		art.setExigencia(false);
		art.setCancelada(false);
		art.setBaixada(false);
		art.setPagouNoPrazo(false);
		art.setHaProfissionalCoResponsavel(false);
		art.setHaEmpresaVinculada(false);
		art.setEntidadeClasse(new EntidadeClasse(0L));

		setParticipacaoTecnicaDesempenhoCargoFuncaoOuReceituarioAgronomico(art, idNatureza);
		setNatureza(art, idNatureza);
		setBaixa(art);
		setPessoa(art, usuario);

		dao.create(art);
		
		audita.iniciaArt(art.getNumero(), usuario);
		return converter.toDto(dao.getByIdString(art.getNumero()));
	}

	public ArtDto novaArtModelo(UserFrontDto usuario, ArtDto artDto) {

		Art art = new Art();
		art.setNumero(geradorSequenciaDao.getSequenciaArt());
		art.setDataCadastro(new Date());
		art.setIsOnline(true);
		art.setFinalizada(false);
		art.setFuncionarioCadastro(new Long(99990));
		art.setFuncionarioAlteracao(new Long(99990));
		art.setIsTermoAditivo(false);
		art.setWebService(false);
		art.setIsAcaoOrdinaria(false);
		art.setMultiplaMensal(false);
		art.setIsCertificada(false);
		art.setLiberada(false);
		art.setExigencia(false);
		art.setCancelada(false);
		art.setBaixada(false);
		art.setPagouNoPrazo(false);
		art.setModelo(true);
		art.setDescricaoModelo(artDto.getDescricaoModelo());
		art.setHaProfissionalCoResponsavel(false);
		art.setHaEmpresaVinculada(false);
		art.setEntidadeClasse(new EntidadeClasse(0L));

		setParticipacaoTecnicaDesempenhoCargoFuncaoOuReceituarioAgronomico(art, artDto.getNatureza().getId());
		setNatureza(art, artDto.getNatureza().getId());
		setBaixa(art);
		setPessoa(art, usuario);

		dao.create(art);
		return converter.toDto(dao.getByIdString(art.getNumero()));
	}

	public String aplicarModeloArt(UserFrontDto userDto, String numeroModelo) {

		String novoNumero = geradorSequenciaDao.getSequenciaArt();
		dao.aplicarModeloArt(numeroModelo, novoNumero);
		
		daoContrato.aplicarModeloContrato(numeroModelo, novoNumero, duplicaEndereco(numeroModelo, "CONTRATO"), duplicaEndereco(numeroModelo, "CONTRATANTE"), duplicaEndereco(numeroModelo, "PROPRIETARIO"));
		daoContrato.aplicarModeloAtividades(numeroModelo, novoNumero);
		daoContrato.aplicarModeloEspecificacoes(numeroModelo, novoNumero);
		daoContrato.aplicarModeloComplementos(numeroModelo, novoNumero);
		daoContrato.aplicarModeloQuantificacao(numeroModelo, novoNumero);
		
		audita.artGeradaAPartirDoModelo(novoNumero, numeroModelo, userDto);

		return novoNumero;
	}

	private Long duplicaEndereco(String numeroModelo, String tipo) {
		ContratoArt contrato = daoContrato.getPrimeiroContratoPor(numeroModelo);
		Endereco endereco = null;
		if (contrato != null) {
			if (tipo.equals("CONTRATO")) {
				endereco = contrato.getEndereco();
			}
			if (tipo.equals("CONTRATANTE")) {
				endereco = contrato.getEnderecoContratante();
			}
			if (tipo.equals("PROPRIETARIO")) {
				endereco = contrato.getEnderecoProprietario();
			}
			if (endereco != null) {
				endereco.setId(null);
				endereco = enderecoDao.create(endereco);
				
				return endereco.getId();
			} else {
				return null;
			}			
		} else {
			return null;
		}
		
		
	}

	public boolean deletaArtModelo(UserFrontDto userDto, String numeroArt) {

		List<String> listaIdsContrato = daoContrato.getListaCodigosContratosByArt(numeroArt);

		if (listaIdsContrato != null) {
			daoContrato.excluiContratosByListId(listaIdsContrato);
		}

		dao.excluiArt(numeroArt);

		return true;
	}

	public boolean atualizaDescricaoModelo(DomainGenericDto dto) {
		return dao.atualizaDescricaoModelo(dto);
	}

	private void setParticipacaoTecnicaDesempenhoCargoFuncaoOuReceituarioAgronomico(Art art, Long idNatureza) {
		if (idNatureza.equals(2L) || idNatureza.equals(3L)) {
			ParticipacaoTecnicaArt participacaoTecnica = new ParticipacaoTecnicaArt();
			participacaoTecnica.setId(1L);
			art.setParticipacaoTecnica(participacaoTecnica);
		}
	}

	private void setNatureza(Art art, Long idNatureza) {
		NaturezaArt natureza = new NaturezaArt();
		natureza.setId(idNatureza);
		art.setNaturezaArt(natureza);
	}

	private void setBaixa(Art art) {
		BaixaArt baixaArt = new BaixaArt();
		baixaArt.setId(new Long(8));
		art.setBaixaArt(baixaArt);
	}

	private void setPessoa(Art art, UserFrontDto usuario) {
		if (usuario.heProfissional()) {
			Profissional profissional = new Profissional();
			profissional.setId(usuario.getIdPessoa());

			PessoaFisica pessoaFisica = new PessoaFisica();
			profissional.setPessoaFisica(pessoaFisica);

			art.setProfissional(profissional);
		} else {
			Empresa empresa = new Empresa();
			empresa.setId(usuario.getIdPessoa());

			PessoaJuridica pessoaJuridica = new PessoaJuridica();
			empresa.setPessoaJuridica(pessoaJuridica);

			art.setEmpresa(empresa);
		}
	}

	public DomainGenericDto atualizaNatureza(DomainGenericDto dto, UserFrontDto userFrontDto) {
		dao.limparCamposNaAtualizacaoDaNatureza(dto, userFrontDto);
		return dao.atualizaNatureza(dto);
	}

	public DomainGenericDto atualizaFatoGerador(DomainGenericDto dto) {
		return dao.atualizaFatoGerador(dto);
	}

	public boolean atualizaDescricaoFatoGerador(DomainGenericDto dto) {
		return dao.atualizaDescricaoFatoGerador(dto);
	}

	public DomainGenericDto atualizaTipo(DomainGenericDto dto) {
		return dao.atualizaTipo(dto);
	}

	public DomainGenericDto setEntidadesClasse(DomainGenericDto dto) {
		return dao.atualizaEntidadeClasse(dto);
	}

	public DomainGenericDto setEmpresaContratado(DomainGenericDto dto) {

		dao.atualizaAcaoOrdinaria(dto.getNumero(), acaoOrdinariaService.verificaSeTemAcaoJudicialParaIsencaoDePagamentoDeArt(dto.getId()));
		dao.atualizaEmpresaContratado(dto);
		return dto;
	}

	public DomainGenericDto setProfissionalContratado(DomainGenericDto dto) {

		dao.atualizaAcaoOrdinaria(dto.getNumero(), acaoOrdinariaService.verificaSeTemAcaoJudicialParaIsencaoDePagamentoDeArt(dto.getId()));
		dao.atualizaProfissionalContratado(dto);
		return dto;
	}

	public DomainGenericDto setNumeroArtPrincipal(DomainGenericDto dto) {
		dao.atualizaNumeroArtPrincipal(dto);
		return dto;
	}

	public List<ArtMinDto> pesquisaARTs(PesquisaArtDto pesquisa) {
		return dao.pesquisaARTs(pesquisa);
	}

	public int getTotalDeRegistrosDaPesquisa(PesquisaArtDto pesquisa) {
		return dao.getTotalDeRegistrosDaPesquisa(pesquisa);
	}

	public List<ArtMinDto> getListRascunhos(PesquisaArtDto pesquisa) {
		return dao.getListRascunhos(pesquisa);
	}

	public List<ArtMinDto> getListModelos(PesquisaArtDto pesquisa) {
		return dao.getListModelos(pesquisa);
	}

	public BigDecimal getValorArt(String numero) {
		return dao.getValorArt(numero);
	}

	public boolean validarSeNumeroArtPrincipalEhValido(DomainGenericDto dto) {
		return dao.validarSeNumeroArtPrincipalEhValido(dto);
	}

	public ArtDto baixaArt(ArtDto dto, UserFrontDto user) {
		dao.baixarArt(dto.getBaixaArt().getId(), dto.getNumero(), dto.getMotivoBaixaOutros());
		artLog.logaAcaoArt(user, dto, TipoAcaoEnum.BAIXA_ART);
		return dto;
	}

	public boolean validaEdicaoArt(ArtDto art, UserFrontDto user) {

		if (user.heProfissional()) {
			if (art.naoEstaFinalizada()) {
				return art.heOMesmoProfissional(user.getIdPessoa());
			}
		}

		if (user.heEmpresa()) {
			if (art.naoEstaFinalizada()) {
				return art.heAMesmaEmpresa(user.getIdPessoa());
			}
		}

		return false;
	}

	public List<ArtExigenciaDto> getListExigencias(PesquisaArtDto pesquisa) {
		return dao.getListExigencias(pesquisa);
	}

	public boolean verificaSeSubstitutaNaoEhDoProprio(DomainGenericDto dto, UserFrontDto userDto) {

		if (dto.getId().equals(2L)) {
			Art art = dao.getArtPorId(dto.getNome());

			if (userDto.heProfissional()) {
				if (art.temProfissional()) {
					return !art.getProfissional().getId().equals(userDto.getIdPessoa());
				}
				return true;
			} else {
				if (art.temEmpresa()) {
					return !art.getEmpresa().getId().equals(userDto.getIdPessoa());
				}
				return true;
			}
		}

		return false;
	}

	// FIXME: IMPLANTAR VALIDAÇÃO DO RAMO
	public boolean validarSeNumeroArtParticipacaoTecnicaEhValido(DomainGenericDto dto) {
		return dao.validarSeNumeroArtParticipacaoTecnicaEhValido(dto);
	}

	public DomainGenericDto setNumeroArtParticipacaoTecnica(DomainGenericDto dto) {
		dao.atualizaNumeroArtParticipacaoTecnica(dto);
		return dto;
	}

	public DomainGenericDto atualizaParticipacaoTecnica(DomainGenericDto dto) {
		return dao.atualizaParticipacaoTecnica(dto);
	}

	public void atualizaPrimeiraParticipacaoTecnica(DomainGenericDto dto) {
		dao.atualizaPrimeiraParticipacaoTecnica(dto);
	}

	public Response previewArt(HttpServletRequest request, String numero, String tipo) throws IllegalArgumentException, IllegalAccessException, IOException, ParserConfigurationException {

		ArtDto dto = new ArtDto();

		dto = getArtPor(numero);
		DocumentoDto documentoDto = new DocumentoDto();
		documentoDto.setArt(dto);
		documentoDto.setTemplate(TemplateReportEnum.getTemplatePorNome(tipo));
		

		List<Map<String, Object>> params = new ArrayList<>();
		params = reportConverter.toMapJrBeanCollection(documentoDto);

		return Response.ok(reportManager.httpPreviewArt(params, getModeloTemplate(documentoDto).getTemplate(request), request))
			       .header("Content-Type", "text/html").type(MediaType.TEXT_HTML).build();	
	}
	
	public TemplateReportEnum getModeloTemplate(Object object) throws IllegalArgumentException, IllegalAccessException {
		
		Field[] reportProperties = object.getClass().getDeclaredFields();
		for (Field propertie : reportProperties) {
			if (propertie.getName().equals("template")) {
				propertie.setAccessible(true);
				return (TemplateReportEnum) propertie.get(object);
			}
		}
		return null;
	}

	public List<ArtDto> getArtsAcervoTecnico(UserFrontDto dto) {
		return dao.getArtsAcervoTecnico(dto);
	}


}
