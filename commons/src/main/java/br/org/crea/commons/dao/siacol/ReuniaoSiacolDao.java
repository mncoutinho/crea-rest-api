package br.org.crea.commons.dao.siacol;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.org.crea.commons.converter.commons.EmailEnvioConverter;
import br.org.crea.commons.dao.DocumentoDao;
import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.dao.corporativo.Page;
import br.org.crea.commons.models.cadastro.StatusDocumento;
import br.org.crea.commons.models.cadastro.dtos.DepartamentoDto;
import br.org.crea.commons.models.commons.DestinatarioEmailDto;
import br.org.crea.commons.models.commons.dtos.EmailEnvioDto;
import br.org.crea.commons.models.commons.dtos.PesquisaGenericDto;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.models.siacol.EmailConselheiro;
import br.org.crea.commons.models.siacol.ProtocoloSiacol;
import br.org.crea.commons.models.siacol.ReuniaoSiacol;
import br.org.crea.commons.models.siacol.RlEmailReuniaoSiacol;
import br.org.crea.commons.models.siacol.dtos.GenericSiacolDto;
import br.org.crea.commons.models.siacol.dtos.ReuniaoSiacolDto;
import br.org.crea.commons.models.siacol.dtos.TesteSiacolGenericDto;
import br.org.crea.commons.models.siacol.dtos.documento.PautaDto;
import br.org.crea.commons.models.siacol.enuns.StatusReuniaoSiacol;
import br.org.crea.commons.service.DocumentoService;
import br.org.crea.commons.service.EmailService;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.service.commons.FinalizarDocumentoPautaVirtual;
import br.org.crea.commons.util.DateUtils;
import br.org.crea.commons.util.EmailEvent;
import br.org.crea.commons.util.StringUtil;

import com.google.gson.Gson;

@Stateless
public class ReuniaoSiacolDao extends GenericDao<ReuniaoSiacol, Serializable> {

	@Inject HttpClientGoApi httpGoApi;

	@Inject RlDocumentoProtocoloSiacolDao rlDocumentoProtocoloDao;
	
	@Inject PersonalidadeSiacolDao personalidadeDao;
	
	@Inject RlEmailReuniaoSiacolDao rlEmailReuniaoDao;
	
	@Inject EmailEvent mailEvent;
	
	@Inject DocumentoService documentoService;
	
	@Inject DocumentoDao documentoDao;
	
	@Inject FinalizarDocumentoPautaVirtual finalizarDocumentoPautaVirtual;
	
	@Inject EmailEnvioConverter mailConverter;
	
	@Inject EmailService mailService;
	
	@Inject Properties properties;
	
	private String url;

	@PostConstruct
	public void before () {
	 this.url = properties.getProperty("apache.url");
	}

	@PreDestroy
	public void reset () {
		properties.clear();
	}
		
	//private Gson gson = new Gson();


	public ReuniaoSiacolDao() {
		super(ReuniaoSiacol.class);
	}

	public List<ReuniaoSiacol> getReunioesSiacol(PesquisaGenericDto pesquisa) {

		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT R FROM ReuniaoSiacol R WHERE 1 = 1 ");

			queryPesquisaReunioes(pesquisa, sql);

			TypedQuery<ReuniaoSiacol> query = em.createQuery(sql.toString(), ReuniaoSiacol.class);
			parametrosPesquisaReuniao(pesquisa, query);
			Page page = new Page(pesquisa.getPage(), 50);
			page.paginate(query);

			return query.getResultList();

		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("ReuniaoSiacolDao || getReunioesSiacol", StringUtil.convertObjectToJson("sem paramentro"), e);
		}
		return null;
	}

	public List<ReuniaoSiacol> getReunioesSiacolLiberadas(PesquisaGenericDto pesquisa) {

		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT R FROM ReuniaoSiacol R WHERE 1 = 1 ");

			queryPesquisaReunioes(pesquisa, sql);

			TypedQuery<ReuniaoSiacol> query = em.createQuery(sql.toString(), ReuniaoSiacol.class);
			parametrosPesquisaReuniao(pesquisa, query);

			return query.getResultList();

		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("ReuniaoSiacolDao || getReunioesSiacol", StringUtil.convertObjectToJson("sem paramentro"), e);
		}
		return null;
	}

	public void queryPesquisaReunioes(PesquisaGenericDto pesquisa, StringBuilder sql) {

		if (pesquisa.getId() != null) {
			sql.append("AND R.id = :id ");
		}

		if (pesquisa.getIdDocumento() != null) {
			sql.append("AND R.pauta.id = :idDocumento ");
		}
		
		if (pesquisa.getTipo() != null) {
			sql.append("AND R.tipo= :tipo ");
		}
		
		if (pesquisa.getVirtual() != null) {
			sql.append("AND R.virtual = :virtual ");
		}
		
		if (pesquisa.getIdDocumento() != null) {
			sql.append("AND R.pauta.id = :idDocumento ");
		}

		if (pesquisa.getIdDepartamento() != null) {
			sql.append("AND R.departamento.id = :idDepartamento ");
		}

		if (pesquisa.getDataInicio() != null && pesquisa.getDataFim() != null) {
			sql.append("AND TO_CHAR(R.dataReuniao, 'mm/dd/yyyy') >= TO_CHAR(:dataInicio, 'mm/dd/yyyy') ");
			sql.append("AND TO_CHAR(R.dataReuniao, 'mm/dd/yyyy') <= TO_CHAR(:dataFim, 'mm/dd/yyyy') ");
		}

		if (pesquisa.isHoje()) {
			sql.append("AND TO_CHAR(R.dataReuniao, 'mm/dd/yyyy') = TO_CHAR(:hoje, 'mm/dd/yyyy') ");
		}
		
		if (pesquisa.temAno()) {
			sql.append("AND TO_CHAR(R.dataReuniao, 'YYYY') = :ano ");
		}

		if (pesquisa.getStatusDescritivo() != null) {
			switch (pesquisa.getStatusDescritivo()) {
			case "ABERTA":
				sql.append("AND R.status = 'ABERTA' ");
				break;
			case "FECHADA":
				sql.append("AND R.status = 'FECHADA' ");
				break;
			case "CADASTRADA":
				sql.append("AND R.status = 'CADASTRADA' ");
				break;
			case "TODAS":
				sql.append("AND R.status in ('CADASTRADA', 'ABERTA', 'FECHADA') ");
				break;
			default:
				sql.append("AND R.status in ('CADASTRADA', 'ABERTA') ");
			}
		}else {
			sql.append("AND R.status in ('CADASTRADA', 'ABERTA', 'FECHADA') ");
		}

		if (pesquisa.isLivre()) {
			sql.append("AND R.pauta = null ");
		}

		sql.append("ORDER BY R.dataReuniao ASC");

	}

	private void parametrosPesquisaReuniao(PesquisaGenericDto pesquisa, TypedQuery<ReuniaoSiacol> query) {

		if (pesquisa.getDataInicio() != null && pesquisa.getDataFim() != null) {
			query.setParameter("dataInicio", pesquisa.getDataInicio());
			query.setParameter("dataFim", pesquisa.getDataFim());
		}

		if (pesquisa.isHoje()) {
			query.setParameter("hoje", new Date());
		}
		
		if (pesquisa.temAno()) {
			query.setParameter("ano", pesquisa.getAno().toString());
		}

		if (pesquisa.getIdDepartamento() != null) {
			query.setParameter("idDepartamento", pesquisa.getIdDepartamento());
		}

		if (pesquisa.getId() != null) {
			query.setParameter("id", pesquisa.getId());
		}
		
		if (pesquisa.getTipo() != null) {
			query.setParameter("tipo", new Long(pesquisa.getTipo()));
		}
		
		if (pesquisa.getVirtual() != null) {
			query.setParameter("virtual", pesquisa.getVirtual() == 1 ? true : false);
		}
		
		if (pesquisa.getIdDocumento() != null) {
			query.setParameter("idDocumento", pesquisa.getIdDocumento());
		}
	}

	public void deletePauta(ReuniaoSiacolDto dto) {

		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE SIACOL_REUNIAO SET FK_DOCUMENTO = null WHERE FK_DOCUMENTO = :idDocumento");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idDocumento", dto.getPauta().getId());

			query.executeUpdate();
		} catch (Throwable e) {
			httpGoApi.geraLog("ReuniaoSiacolDao || deletePauta", StringUtil.convertObjectToJson(dto), e);
		}
	}

	public void atualizaStatusPainelReuniao(String status, Long idReuniao) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE SIACOL_REUNIAO SET STATUS_PAINEL = :status WHERE ID = :idReuniao");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("status", status);
			query.setParameter("idReuniao", idReuniao);
			
			query.executeUpdate();
		} catch (Throwable e) {
			httpGoApi.geraLog("ReuniaoSiacolDao || atualizaStatusPainelReuniao", StringUtil.convertObjectToJson(idReuniao), e);
		}
	}

	public List<ProtocoloSiacol> getProtocolosAPautar(ReuniaoSiacolDto reuniaoDto) {
		List<ProtocoloSiacol> listProtocoloSiacol = new ArrayList<ProtocoloSiacol>();

		String orderByClause = getParametrosClausulaOrderBy(reuniaoDto.getOrdenacaoPauta());

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT PS FROM ProtocoloSiacol PS ");
		sql.append("	WHERE PS.departamento.id  = :idDepartamento ");
		sql.append("	AND   PS.classificacao != 'NAO_CLASSIFICADO' ");
		
		if (!reuniaoDto.getListDigitoExclusaoProtocolo().isEmpty()) {
			sql.append("	AND   SUBSTR(PS.numeroProtocolo,5,1) NOT IN (:listDigitoExclusao) ");
		}
		
		if (reuniaoDto.getVirtual()) {
			sql.append("	AND  ( PS.status = 'A_PAUTAR' ");
			sql.append("		OR   PS.status = 'A_PAUTAR_SEM_QUORUM' ) ");
		} else {
			sql.append("	AND   (PS.status = 'A_PAUTAR' ");
			sql.append("		OR   PS.status = 'A_PAUTAR_SEM_QUORUM' ");
			sql.append("		OR   PS.status = 'DESTACADO' ");
			sql.append("		OR   PS.status = 'A_PAUTAR_PROVISORIO' ");
			sql.append("		OR   PS.status = 'A_PAUTAR_VISTAS' ");
			sql.append("		OR   PS.status = 'A_PAUTAR_DESTAQUE' ");
			sql.append("		OR   PS.status = 'A_PAUTAR_AD_REFERENDUM' ");
			sql.append("		OR   PS.status = 'A_PAUTAR_PRESENCIAL' ) ");
		}

		if (reuniaoDto.getVirtual() && !reuniaoDto.incluiProtocoloDesfavoravel()) {
			sql.append(" AND  PS.classificacao = 'FAVORAVEL' ");
		}
		sql.append("    ORDER BY ");
		sql.append(orderByClause);

		try {
			
			TypedQuery<ProtocoloSiacol> query = em.createQuery(sql.toString(), ProtocoloSiacol.class);
			query.setParameter("idDepartamento", new Long(reuniaoDto.getDepartamento().getId()));
			if (!reuniaoDto.getListDigitoExclusaoProtocolo().isEmpty()) {
				query.setParameter("listDigitoExclusao", reuniaoDto.getListDigitoExclusaoProtocolo());
			}

			listProtocoloSiacol = query.getResultList();

		} catch (NoResultException e) {
			return listProtocoloSiacol;
		} catch (Throwable e) {
			httpGoApi.geraLog("ReuniaoSiacolDao || getListProtocolosAPautarEmReuniao", StringUtil.convertObjectToJson(reuniaoDto), e);
		}

		return listProtocoloSiacol;
	}

	public List<ProtocoloSiacol> getProtocolosSemClassificacaoPautaPor(Long idDepartamento) {
		List<ProtocoloSiacol> listProtocoloSiacol = new ArrayList<ProtocoloSiacol>();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT PS FROM ProtocoloSiacol PS ");
		sql.append("	WHERE PS.departamento.id  = :idDepartamento ");
			sql.append("	AND   (PS.status = 'A_PAUTAR' ");
			sql.append("		OR   PS.status = 'A_PAUTAR_SEM_QUORUM' ");
			sql.append("		OR   PS.status = 'DESTACADO' ");
			sql.append("		OR   PS.status = 'A_PAUTAR_PROVISORIO' ");
			sql.append("		OR   PS.status = 'A_PAUTAR_VISTAS' ");
			sql.append("		OR   PS.status = 'A_PAUTAR_DESTAQUE' ");
			sql.append("		OR   PS.status = 'A_PAUTAR_AD_REFERENDUM' ");
			sql.append("		OR   PS.status = 'A_PAUTAR_PRESENCIAL' ) ");
			sql.append("	AND   PS.classificacao = 'NAO_CLASSIFICADO' ");
		try {
			TypedQuery<ProtocoloSiacol> query = em.createQuery(sql.toString(), ProtocoloSiacol.class);
			query.setParameter("idDepartamento", idDepartamento);
			listProtocoloSiacol = query.getResultList();

		} catch (NoResultException e) {
			return listProtocoloSiacol;
		} catch (Throwable e) {
			httpGoApi.geraLog("ReuniaoSiacolDao || getProtocolosSemClassificacaoPautaPor", StringUtil.convertObjectToJson(idDepartamento), e);
		}

		return listProtocoloSiacol;
	}

	public ProtocoloSiacol buscaProtocoloAReclassificarPor(Long numeroProtocolo, Long idDepartamento) {
		ProtocoloSiacol protocoloReclassificar = new ProtocoloSiacol();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT PS FROM ProtocoloSiacol PS ");
		sql.append("	WHERE PS.departamento.id  = :idDepartamento ");
		sql.append("	AND   PS.status = 'A_PAUTAR' ");
		sql.append("	AND   PS.numeroProtocolo = :numeroProtocolo ");

		try {
			TypedQuery<ProtocoloSiacol> query = em.createQuery(sql.toString(), ProtocoloSiacol.class);
			query.setParameter("idDepartamento", idDepartamento);
			query.setParameter("numeroProtocolo", numeroProtocolo);

			protocoloReclassificar = query.getSingleResult();

		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("ReuniaoSiacolDao || buscaProtocoloReclassificacaoPor", StringUtil.convertObjectToJson(numeroProtocolo), e);
		}

		return protocoloReclassificar;
	}

	public List<ProtocoloSiacol> getProtocolosAAssinar(Long idDepartamento) {
		List<ProtocoloSiacol> listProtocoloSiacol = new ArrayList<ProtocoloSiacol>();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT PS FROM ProtocoloSiacol PS ");
		sql.append("	WHERE PS.status IN ('PARA_ASSINAR_VIRTUAL', 'PARA_ASSINAR_PRESENCIAL', 'PARA_ASSINAR', 'ASSINATURA_AD_REFERENDUM') ");
		sql.append("	AND PS.departamento.id = :idDepartamento ");
		sql.append("	ORDER BY PS.id ");

		try {
			TypedQuery<ProtocoloSiacol> query = em.createQuery(sql.toString(), ProtocoloSiacol.class);
			query.setParameter("idDepartamento", idDepartamento);
			
			listProtocoloSiacol = query.getResultList();

		} catch (NoResultException e) {
			return listProtocoloSiacol;
		} catch (Throwable e) {
			httpGoApi.geraLog("ReuniaoSiacolDao || getProtocolosAAssinar", "SEM PARAMETRO", e);
		}

		return listProtocoloSiacol;
	}
	
	public List<ProtocoloSiacol> getProtocolosAAssinarAdReferendum(Long idDepartamento) {
		List<ProtocoloSiacol> listProtocoloSiacol = new ArrayList<ProtocoloSiacol>();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT PS FROM ProtocoloSiacol PS ");
		sql.append("	WHERE PS.status IN ('ASSINATURA_AD_REFERENDUM') ");
		sql.append("	AND PS.departamento.id = :idDepartamento ");
		sql.append("	ORDER BY PS.id ");

		try {
			TypedQuery<ProtocoloSiacol> query = em.createQuery(sql.toString(), ProtocoloSiacol.class);
			query.setParameter("idDepartamento", idDepartamento);
			
			listProtocoloSiacol = query.getResultList();

		} catch (NoResultException e) {
			return listProtocoloSiacol;
		} catch (Throwable e) {
			httpGoApi.geraLog("ReuniaoSiacolDao || getProtocolosAAssinar", "SEM PARAMETRO", e);
		}

		return listProtocoloSiacol;
	}

	//FIXME: PROVISORIO - Retirar após homologação (serviço isolado no job fim pauta)
	//Está aqui somente para testar manualmente a rotina do job
	public void finalizaPautaReuniao(Date dataVerificacaoPrazo) {
		List<ReuniaoSiacol> listReuniaoValidaComPautaAberta = new ArrayList<ReuniaoSiacol>();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT R FROM ReuniaoSiacol R ");
		sql.append("	WHERE R.pauta IS NOT NULL ");
		sql.append("	AND R.virtual = true ");
		sql.append("	AND R.pauta.statusDocumento.id NOT IN (5, 6, 8) ");

		try {

			TypedQuery<ReuniaoSiacol> query = em.createQuery(sql.toString(), ReuniaoSiacol.class);
			for (ReuniaoSiacol reuniao : query.getResultList()) {
				//if (prazoPautaReuniaoExpirou(dataVerificacaoPrazo, reuniao)) {
					
					listReuniaoValidaComPautaAberta.add(reuniao);
					UserFrontDto userFrontDto = new UserFrontDto();
					userFrontDto.setIdPessoa(new Long(0));
					reuniao.setPauta(finalizarDocumentoPautaVirtual.atualizaDocumento(reuniao.getPauta(),getReuniaoPor(reuniao.getPauta().getId())));
					reuniao.getPauta().setArquivo(documentoService.geraArquivoJob(reuniao.getPauta(),  userFrontDto));
					StatusDocumento statusDocumento = new StatusDocumento();
					statusDocumento.setId(8L);
					reuniao.getPauta().setStatusDocumento(statusDocumento);
					documentoDao.update(reuniao.getPauta());
					
				//}
			}

			insereProtocolosParaEncerrarPauta(listReuniaoValidaComPautaAberta);
			enviaPautaEmailInteressados(listReuniaoValidaComPautaAberta);

		} catch (Throwable e) {
			httpGoApi.geraLog("ReuniaoSiacolDao || finalizaPautaReuniao", StringUtil.convertObjectToJson(dataVerificacaoPrazo), e);
		}

	}
	
	//FIXME: PROVISORIO - Retirar após homologação (serviço isolado no job fim pauta)
	private void enviaPautaEmailInteressados(List<ReuniaoSiacol> listReuniao) throws InterruptedException {

		
		for (ReuniaoSiacol reuniao : listReuniao) {
			
			List<EmailConselheiro> mailConselheiros = new ArrayList<EmailConselheiro>();
			EmailEnvioDto mailEnvio = new EmailEnvioDto();
					
			RlEmailReuniaoSiacol emailReuniao = rlEmailReuniaoDao.getEmailsPor(reuniao.getId(), new Long(2));
			if( emailReuniao != null ) {
				
				mailEnvio = mailConverter.toDto(emailReuniao.getEmailEnvio());
				mailConselheiros.addAll(personalidadeDao.buscaEmailConselheirosEnvioPautaPor(reuniao.getDepartamento().getCodigo()));
				
				for (EmailConselheiro conselheiro : mailConselheiros) {
					
					if (conselheiro != null) {
						DestinatarioEmailDto dto = new DestinatarioEmailDto();
						dto.setNome(conselheiro.getPessoa() != null ? conselheiro.getPessoa().getNome() : "");
						dto.setEmail(conselheiro.getDescricao());
						mailEnvio.setDestinatarios(new ArrayList<DestinatarioEmailDto>(mailEnvio.getDestinatarios()));
						mailEnvio.getDestinatarios().add(dto);
					}
				}
				
				/**
				 * FIXME: Demandado por Juan -> Temporariamente a pauta será disponibilizada com um get em cad-documento.
				 *        Depois excluir a url temporaria e usar a que busca o documento no file system
				 *        descomentando bloco dentro do if abaixo
				 */
				mailEnvio.setDataUltimoEnvio(new Date()); 
				if (reuniao.getPauta().temArquivo()) {
					String mensagem = mailEnvio.getMensagem();
					String linkDownloadPauta =  url + "arquivos/" + reuniao.getPauta().getArquivo().getUri();
					mensagem += "<!DOCTYPE html>";
					mensagem += "<html>";
					mensagem += "<body>";
					mensagem += "</br><p>Segue o link da Pauta Virtual</p>";
					mensagem += "<p><a href='"+linkDownloadPauta+"'>Pauta da "+reuniao.getDepartamento().getNome()+"</a></p>";
					mensagem += "<p> URL: "+linkDownloadPauta+"</p>";
					mensagem += "</body>";
					mensagem += "</html>";
	                
					mailEnvio.setMensagem(mensagem);
				}else{
					mailEnvio.setMensagem("TESTE E-MAIL SEM ARQUIVO");
				}
				mailService.envia(mailEnvio);
			}
			
		}
	}
	
	public void iniciaReuniaoVirtual(Date dataVerificacaoAbertura) {

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT R FROM ReuniaoSiacol R ");
		sql.append("	WHERE R.pauta.statusDocumento.id = 8 ");
		sql.append("	AND TO_CHAR(R.dataReuniao, 'DD/MM/YYYY') = TO_CHAR(:dataVerificacaoAbertura, 'DD/MM/YYYY') ");
		sql.append("	AND R.virtual = true ");

		try {

			TypedQuery<ReuniaoSiacol> query = em.createQuery(sql.toString(), ReuniaoSiacol.class);
			query.setParameter("dataVerificacaoAbertura", dataVerificacaoAbertura);

			for (ReuniaoSiacol reuniao : query.getResultList()) {

				reuniao.setStatus(StatusReuniaoSiacol.ABERTA);
				reuniao.setHoraInicio(dataVerificacaoAbertura);
				update(reuniao);
			}

		} catch (Throwable e) {
			httpGoApi.geraLog("ReuniaoSiacolDao || iniciaReuniao", StringUtil.convertObjectToJson(dataVerificacaoAbertura), e);
		}
	}

	public List<ReuniaoSiacol> getReunioesAptasEncerrar(Date dataVerificacaoFechamento) {
		List<ReuniaoSiacol> listReuniao = new ArrayList<ReuniaoSiacol>();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT R FROM ReuniaoSiacol R ");
		sql.append("	WHERE R.status = 'ABERTA' ");
		sql.append("	AND TO_CHAR(R.dataReuniao, 'DD/MM/YYYY') = TO_CHAR(:dataVerificacaoFechamento, 'DD/MM/YYYY') ");
		sql.append("	AND R.virtual = true ");

		try {

			TypedQuery<ReuniaoSiacol> query = em.createQuery(sql.toString(), ReuniaoSiacol.class);
			query.setParameter("dataVerificacaoFechamento", dataVerificacaoFechamento);
			listReuniao = query.getResultList();

		} catch (NoResultException e) {
			return listReuniao;
		} catch (Throwable e) {
			httpGoApi.geraLog("ReuniaoSiacolDao || iniciaReuniao", StringUtil.convertObjectToJson(dataVerificacaoFechamento), e);
		}

		return listReuniao;
	}
	
	public List<ReuniaoSiacol> getReunioesSemPautaCadastradaNoPeriodoLimite(Date dataVerificacaoFechamento) {
		
		List<ReuniaoSiacol> listResult = new ArrayList<ReuniaoSiacol>();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT R FROM ReuniaoSiacol R ");
		sql.append("	WHERE R.pauta IS NULL ");
		sql.append("	AND R.status != 'CANCELADA' ");
		
		try {

			TypedQuery<ReuniaoSiacol> query = em.createQuery(sql.toString(), ReuniaoSiacol.class);
			query.getResultList().forEach(r -> {
				
				Date vesperaFechamentoPauta = DateUtils.adicionaOrSubtraiDiasA(dataPrazoCriacaoPauta(r), new Integer(-1));
				if( DateUtils.isMesmoDia(dataVerificacaoFechamento, vesperaFechamentoPauta) ){
					listResult.add(r);
				}
			});
			
			return listResult;

		} catch (NoResultException e) {
			return listResult;
		} catch (Throwable e) {
			httpGoApi.geraLog("ReuniaoSiacolDao || getReunioesSemPautaCadastradaNoPeriodoLimite", StringUtil.convertObjectToJson(dataVerificacaoFechamento), e);
		}

		return listResult;
	}
	
	//FIXME: PROVISORIO - Retirar após homologação (serviço isolado no job fim pauta)
	public void insereProtocolosParaEncerrarPauta(List<ReuniaoSiacol> listReuniao) throws IllegalArgumentException, IllegalAccessException {

		for (ReuniaoSiacol reuniao : listReuniao) {

			DepartamentoDto departamentoReuniao = new DepartamentoDto();
			departamentoReuniao.setId(reuniao.getDepartamento().getId());

			ReuniaoSiacolDto dtoPesquisa = new ReuniaoSiacolDto();
			dtoPesquisa.setDepartamento(departamentoReuniao);
			
			rlDocumentoProtocoloDao.cadastraProtocolosFechamentoPauta(getProtocolosAPautar(pesquisaAtributosPauta(reuniao)), reuniao.getPauta());
//			rlDocumentoProtocoloDao.cadastraProtocolosFechamentoPauta(getProtocolosAPautar(pesquisaAtributosPauta(dtoPesquisa, reuniao.getPauta().getRascunho())), reuniao.getPauta());
		}
	}

	//FIXME: PROVISORIO - Retirar após homologação (serviço isolado no job fim pauta)
	private ReuniaoSiacolDto pesquisaAtributosPauta(ReuniaoSiacol reuniao) {
		ReuniaoSiacolDto dto = new ReuniaoSiacolDto();
		Gson gson = new Gson(); 
		
		try {
			
			DepartamentoDto departamentoReuniao = new DepartamentoDto();
			departamentoReuniao.setId(reuniao.getDepartamento().getId());
			
			dto.setDepartamento(departamentoReuniao);
			dto.setOrdenacaoPauta(gson.fromJson(reuniao.getPauta().getRascunho(), PautaDto.class).getOrdenacaoPauta());
			dto.setListDigitoExclusaoProtocolo(gson.fromJson(reuniao.getPauta().getRascunho(), PautaDto.class).getListDigitoExclusaoProtocolo());
			dto.setIncluiProtocoloDesfavoravel(gson.fromJson(reuniao.getPauta().getRascunho(), PautaDto.class).incluiProtocoloDesfavoravel());
			dto.setVirtual(reuniao.getVirtual());
			
		} catch (Throwable e) {
			httpGoApi.geraLog("JobFinalizaPautaReuniao || pesquisaAtributosPauta", StringUtil.convertObjectToJson(reuniao), e);
		}
		
		return dto;
	}
	
	public String getParametrosClausulaOrderBy(List<GenericSiacolDto> listFiltroOrdenacao) {

		listFiltroOrdenacao.sort( (f1, f2) -> f1.getOrdemFiltro().compareTo(f2.getOrdemFiltro()) );
		
		String orderByClause = "";
		for (GenericSiacolDto filtro : listFiltroOrdenacao) {
			orderByClause += filtro.getFiltroProtocolo().getCampoConsulta() + ", ";
		}

		String ordem = orderByClause.trim();
		return String.valueOf(ordem.charAt(ordem.length() - 1)).equals(",") ? ordem.substring(0, ordem.length() - 1) : orderByClause;
	}

	public boolean prazoPautaReuniaoExpirou(Date dataVerificacao, ReuniaoSiacol reuniao) {

		Date dataFimPauta = DateUtils.adicionaOrSubtraiDiasA(reuniao.getDataReuniao(), -reuniao.getPrazo());
		return dataVerificacao.after(dataFimPauta) ? true : false;
	}

	public Date dataPrazoCriacaoPauta(ReuniaoSiacol reuniao) {
		return DateUtils.adicionaOrSubtraiDiasA(reuniao.getDataReuniao(), -reuniao.getPrazo());
		
	}

	public ReuniaoSiacol getReuniaoPor(Long idPauta) {
		ReuniaoSiacol reuniaoSiacol = new ReuniaoSiacol();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT R FROM ReuniaoSiacol R ");
		sql.append("	WHERE (R.pauta.id = :idPauta ");
		sql.append("	 OR R.extraPauta.id = :idPauta ) ");
		sql.append("	AND Rownum = 1 ");
		sql.append("	ORDER BY  R.dataReuniao DESC ");

		try {

			TypedQuery<ReuniaoSiacol> query = em.createQuery(sql.toString(), ReuniaoSiacol.class);
			query.setParameter("idPauta", idPauta);

			reuniaoSiacol = query.getSingleResult();
		} catch (NoResultException e){
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("ReuniaoSiacolDao || getReuniaoPor", StringUtil.convertObjectToJson(idPauta), e);
		}
		return reuniaoSiacol;
	}
	
	public ReuniaoSiacol getReuniaoAnteriorPor(ReuniaoSiacolDto reuniaoAtual) {
		ReuniaoSiacol reuniaoSiacol = new ReuniaoSiacol();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT R FROM ReuniaoSiacol R ");
		sql.append("	WHERE TO_CHAR(R.dataReuniao, 'YYYY-MON-DD') < TO_CHAR(:dataReuniaoAtual, 'YYYY-MON-DD') ");
		sql.append("	 AND R.departamento.id = :idDepartamentoReuniaoAtual ) ");
		sql.append("	AND Rownum = 1 ");
		sql.append("	ORDER BY  R.dataReuniao DESC ");

		try {

			TypedQuery<ReuniaoSiacol> query = em.createQuery(sql.toString(), ReuniaoSiacol.class);
			query.setParameter("dataReuniaoAtual", reuniaoAtual.getDataReuniao());
			query.setParameter("idDepartamentoReuniaoAtual", reuniaoAtual.getDepartamento().getId());

			reuniaoSiacol = query.getSingleResult();
		} catch (NoResultException e){
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("ReuniaoSiacolDao || getReuniaoPor", StringUtil.convertObjectToJson(reuniaoAtual), e);
		}
		return reuniaoSiacol;
	}

	// FIXME: Método provisório somente para possibilitar iniciar e finalizar
	// reunião e pauta
	// Após apresentação do Juan em 16/02 para a presidencia excluir este método;

	public void manipulaReuniao(TesteSiacolGenericDto dto) {

		/**
		 * 
		 * dto :
		 * 
		 * { "nome": "iniciaReuniao", "dataManipularReuniao": "31/08/2018") }
		 * 
		 * EX. Se quero mostrar uma reunião iniciando que está marcada para 01/02/2018:
		 * informe esta data como parametro.
		 * 
		 */

		try {

			if (dto.getNome().equals("iniciaReuniao")) {

				iniciaReuniaoVirtual(DateUtils.DD_MM_YYYY.parse(dto.getDataManipularReuniao()));

			} else if (dto.getNome().equals("finalizaPauta")) {

				finalizaPautaReuniao(DateUtils.DD_MM_YYYY.parse(dto.getDataManipularReuniao()));
			}

		} catch (Throwable e) {
			httpGoApi.geraLog("ReuniaoSiacolDao || manipulaReuniao", StringUtil.convertObjectToJson(dto), e);
		}

	}
	
	public List<ProtocoloSiacol> getProtocolosHomologacao(Long idDepartamento) {
		List<ProtocoloSiacol> listProtocoloSiacol = new ArrayList<ProtocoloSiacol>();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT PS FROM ProtocoloSiacol PS ");
		sql.append("	WHERE PS.status IN ('RELACAO_VIRTUAL') ");
		sql.append("	AND PS.departamento.id = :idDepartamento ");
		sql.append("	ORDER BY PS.id ");

		try {
			TypedQuery<ProtocoloSiacol> query = em.createQuery(sql.toString(), ProtocoloSiacol.class);
			query.setParameter("idDepartamento", idDepartamento);
			listProtocoloSiacol = query.getResultList();

		} catch (NoResultException e) {
			return listProtocoloSiacol;
		} catch (Throwable e) {
			httpGoApi.geraLog("ReuniaoSiacolDao || getProtocolosDeferido", "SEM PARAMETRO", e);
		}

		return listProtocoloSiacol;
	}

	public List<ProtocoloSiacol> getProtocolosDeferido(Long idDepartamento) {
		List<ProtocoloSiacol> listProtocoloSiacol = new ArrayList<ProtocoloSiacol>();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT PS FROM ProtocoloSiacol PS ");
		sql.append("	WHERE PS.status IN ('RELACAO_VIRTUAL','DEFERIDO', 'AD_REFERENDUM_ASSINADO', 'RETIRADO_DE_PAUTA') ");
		sql.append("	AND PS.departamento.id = :idDepartamento ");
		sql.append("	AND PS.ativo = true ");
		sql.append("	ORDER BY PS.id ");

		try {
			TypedQuery<ProtocoloSiacol> query = em.createQuery(sql.toString(), ProtocoloSiacol.class);
			query.setParameter("idDepartamento", idDepartamento);
			listProtocoloSiacol = query.getResultList();

		} catch (NoResultException e) {
			return listProtocoloSiacol;
		} catch (Throwable e) {
			httpGoApi.geraLog("ReuniaoSiacolDao || getProtocolosDeferido", "SEM PARAMETRO", e);
		}

		return listProtocoloSiacol;
	}
	
	public List<ProtocoloSiacol> getProtocolosProvisorio(Long idDepartamento) {
		List<ProtocoloSiacol> listProtocoloSiacol = new ArrayList<ProtocoloSiacol>();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT PS FROM ProtocoloSiacol PS ");
		sql.append("	WHERE PS.status = 'A_PAUTAR_PROVISORIO' ");
		sql.append("	AND PS.departamento.id = :idDepartamento ");
		sql.append("	ORDER BY PS.id ");

		try {
			TypedQuery<ProtocoloSiacol> query = em.createQuery(sql.toString(), ProtocoloSiacol.class);
			query.setParameter("idDepartamento", idDepartamento);
			listProtocoloSiacol = query.getResultList();

		} catch (NoResultException e) {
			return listProtocoloSiacol;
		} catch (Throwable e) {
			httpGoApi.geraLog("ReuniaoSiacolDao || getProtocolosDeferido", "SEM PARAMETRO", e);
		}

		return listProtocoloSiacol;
	}

	public int quantidadeConsultaReunioes(PesquisaGenericDto pesquisa) {

		List<ProtocoloSiacol> listProtocoloSiacol = new ArrayList<ProtocoloSiacol>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT R FROM ReuniaoSiacol R WHERE 1 = 1 ");

		try {
			queryPesquisaReunioes(pesquisa, sql);

			TypedQuery<ReuniaoSiacol> query = em.createQuery(sql.toString(), ReuniaoSiacol.class);
			parametrosPesquisaReuniao(pesquisa, query);

			query.getResultList();

		} catch (Throwable e) {
			httpGoApi.geraLog("ReuniaoSiacolDao || quantidadeConsultaReunioes", StringUtil.convertObjectToJson(pesquisa), e);
		}

		return listProtocoloSiacol.size();

	}

	public ReuniaoSiacol getReuniaoPorId(Long idReuniao) {
		ReuniaoSiacol reuniaoSiacol = new ReuniaoSiacol();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT R FROM ReuniaoSiacol R ");
		sql.append("	WHERE R.id = :idReuniao ");

		try {

			TypedQuery<ReuniaoSiacol> query = em.createQuery(sql.toString(), ReuniaoSiacol.class);
			query.setParameter("idReuniao", idReuniao);

			reuniaoSiacol = query.getSingleResult();

		} catch (Throwable e) {
			httpGoApi.geraLog("ReuniaoSiacolDao || getReuniaoPorId", StringUtil.convertObjectToJson(idReuniao), e);
		}
		return reuniaoSiacol;
	}

	public List<ReuniaoSiacol> getReunioesAbertas() {

		try {
			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT R FROM ReuniaoSiacol R WHERE 1 = 1 ");
			sql.append("AND R.status IN ('CADASTRADA','ABERTA','PAUSADA') ");
			sql.append("AND R.pauta.id is not null ");
			sql.append("   ORDER BY  R.departamento.nome ");
			//FIXME SETAR A DATA DO DIA

			TypedQuery<ReuniaoSiacol> query = em.createQuery(sql.toString(), ReuniaoSiacol.class);


			return query.getResultList();

		} catch (Throwable e) {
			httpGoApi.geraLog("ReuniaoSiacolDao || getReunioesAbertas", StringUtil.convertObjectToJson("sem paramentro"), e);
		}
		return null;
	}

	public boolean estaAbertaParaVotacao(Long idReuniao) {

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT R FROM ReuniaoSiacol R ");
		sql.append("	WHERE R.id = :idReuniao ");
		sql.append("	AND R.status = 'ABERTA'");

		try {

			TypedQuery<ReuniaoSiacol> query = em.createQuery(sql.toString(), ReuniaoSiacol.class);
			query.setParameter("idReuniao", idReuniao);

			return query.getSingleResult() != null ? true : false;
		
		} catch (Throwable e) {
			httpGoApi.geraLog("ReuniaoSiacolDao || reuniaoEstaAbertaParaVotacao", StringUtil.convertObjectToJson(idReuniao), e);
		}
		
		return false;
	}
	
	/**
	 * Rotina chamado pelo Job de fechamento de pauta virtual.
	 * Procura todas reuniões que estão com pauta em estado pendente de fechamento
	 * @param dataVerificacaoPrazo Data/hora que o job está vendo iniciado
	 * */
	public List<ReuniaoSiacol>  buscaPautaReuniaoVirtualFechamento(Date dataVerificacaoPrazo) {
		List<ReuniaoSiacol> listReuniaoValidaComPautaAberta = new ArrayList<ReuniaoSiacol>();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT R FROM ReuniaoSiacol R ");
		sql.append("	WHERE R.pauta IS NOT NULL ");
		sql.append("	AND R.virtual = true ");
		sql.append("	AND R.pauta.statusDocumento.id NOT IN (5, 6, 8) ");

		try {

			TypedQuery<ReuniaoSiacol> query = em.createQuery(sql.toString(), ReuniaoSiacol.class);
			for (ReuniaoSiacol reuniao : query.getResultList()) {
				if (prazoPautaReuniaoExpirou(dataVerificacaoPrazo, reuniao)) {
					listReuniaoValidaComPautaAberta.add(reuniao);
				}
			}

		} catch (NoResultException e) {
			return listReuniaoValidaComPautaAberta;
		} catch (Throwable e) {
			httpGoApi.geraLog("ReuniaoSiacolDao || buscaPautaReuniaoVirtualFechamento", StringUtil.convertObjectToJson(dataVerificacaoPrazo), e);
		}
		
		return listReuniaoValidaComPautaAberta;
	}

	public boolean existeReuniaoNestaData(ReuniaoSiacolDto dto) {
		
		boolean existeReuniao = false;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT R FROM ReuniaoSiacol R ");
		sql.append("	WHERE TO_CHAR(R.dataReuniao, 'DD-MON-YYYY') = TO_CHAR(:dataReuniao, 'DD-MON-YYYY') ");
		sql.append("	AND R.status IN ('CADASTRADA','ABERTA') ");
		sql.append("	AND  R.departamento.id = :idDepartamento ");
		if(dto.getId() != null){
			sql.append("	AND  R.id <> :idReuniao ");
		}
		if (!dto.getVirtual()) {
			sql.append("	AND  ( R.virtual = true OR ( ");
			sql.append("	(TO_CHAR(R.horaInicio, 'HH24:MI') <= TO_CHAR(:horaInicio, 'HH24:MI') ");
			sql.append("	AND TO_CHAR(R.horaFim, 'HH24:MI') >= TO_CHAR(:horaFim, 'HH24:MI') ) ");
			
			sql.append("	OR ( TO_CHAR(R.horaInicio, 'HH24:MI') >= TO_CHAR(:horaInicio, 'HH24:MI') ");
			sql.append("		AND TO_CHAR(R.horaInicio, 'HH24:MI') <= TO_CHAR(:horaFim, 'HH24:MI') )");
			
			sql.append("	OR ( TO_CHAR(R.horaFim, 'HH24:MI') >= TO_CHAR(:horaInicio, 'HH24:MI') ");
			sql.append("		AND TO_CHAR(R.horaFim, 'HH24:MI') <= TO_CHAR(:horaFim, 'HH24:MI') ) ) )");
		}

		try {

		TypedQuery<ReuniaoSiacol> query = em.createQuery(sql.toString(), ReuniaoSiacol.class);
		if(dto.getId() != null){
			query.setParameter("idReuniao", dto.getId());
		}
		if (!dto.getVirtual()) {
			query.setParameter("horaInicio", dto.getHoraInicio());
			query.setParameter("horaFim", dto.getHoraFim());
		}
		query.setParameter("dataReuniao", dto.getDataReuniao());
		query.setParameter("idDepartamento", dto.getDepartamento().getId());

		existeReuniao = query.getResultList().isEmpty() ? false : true;
		
		} catch (NoResultException e) {
			return false;
		} catch (Throwable e) {
			httpGoApi.geraLog("ReuniaoSiacolDao || existeReuniaoNestaData", StringUtil.convertObjectToJson(dto), e);
		}
		
		return existeReuniao;
	}

	public boolean podeSalvarPauta(ReuniaoSiacol reuniao) {
		
		List<ReuniaoSiacol> reuniaoSiacolAnterior = new ArrayList<ReuniaoSiacol>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT R FROM ReuniaoSiacol R ");
		//sql.append("	WHERE TO_CHAR(R.dataReuniao, 'DD-MON-YYYY') < TO_CHAR(:dataReuniaoAtual, 'DD-MON-YYYY') ");
		sql.append("	WHERE R.dataReuniao < :dataReuniaoAtual ");
		sql.append("	AND R.departamento.id = :idDepartamento ");
		sql.append("	AND R.status NOT IN ('CANCELADA') ");

		try {

			TypedQuery<ReuniaoSiacol> query = em.createQuery(sql.toString(), ReuniaoSiacol.class);
			query.setParameter("dataReuniaoAtual", reuniao.getDataReuniao());
			query.setParameter("idDepartamento", reuniao.getDepartamento().getId());

			reuniaoSiacolAnterior = query.getResultList();
			
			if (reuniaoSiacolAnterior.size() == 0 || reuniaoSiacolAnterior.get(0).getStatus().getId() == 3) {
				return true;
			}
			
		} catch (NoResultException e){
			return true;
		} catch (Throwable e) {
			httpGoApi.geraLog("ReuniaoSiacolDao || podeSalvarPauta", StringUtil.convertObjectToJson(reuniao.getId()), e);
		}
		
		return false;
	}

	public List<ReuniaoSiacol> reunioesParaCriacaoDeSumula(Long idDepartamento) {
		
		List<ReuniaoSiacol> listReuniaoSiacol = new ArrayList<ReuniaoSiacol>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT R FROM ReuniaoSiacol R ");
		sql.append("	WHERE R.departamento.id = :idDepartamento ");
		sql.append("	AND R.status IN ('FECHADA') ");
		sql.append("	AND (R.sumula is null ");
		sql.append("	OR EXISTS (SELECT CD FROM Documento CD WHERE CD.id = R.sumula.id)  ) ");
		
		
		try {

			TypedQuery<ReuniaoSiacol> query = em.createQuery(sql.toString(), ReuniaoSiacol.class);
			query.setParameter("idDepartamento", idDepartamento);

			listReuniaoSiacol = query.getResultList();

			
		} catch (NoResultException e){
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("ReuniaoSiacolDao || reunioesParaCriacaoDeSumula", StringUtil.convertObjectToJson(idDepartamento), e);
		}
		
		return listReuniaoSiacol;
		
	}
	
	public List<ReuniaoSiacol> reunioesParaCriacaoDeAta(Long idDepartamento, Long anoBusca) {
		
		List<ReuniaoSiacol> listReuniaoSiacol = new ArrayList<ReuniaoSiacol>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT R FROM ReuniaoSiacol R ");
		sql.append("	WHERE R.departamento.id = :idDepartamento ");
		sql.append("	AND TO_CHAR(R.dataReuniao, 'yyyy') = :anoBusca ");
		sql.append("	AND R.status IN ('FECHADA') ");
		sql.append("	AND (R.sumula is null ");
		sql.append("	OR EXISTS (SELECT CD FROM Documento CD WHERE CD.id = R.sumula.id)  ) ");
		
		
		try {

			TypedQuery<ReuniaoSiacol> query = em.createQuery(sql.toString(), ReuniaoSiacol.class);
			query.setParameter("idDepartamento", idDepartamento);
			query.setParameter("anoBusca", anoBusca.toString());

			listReuniaoSiacol = query.getResultList();

			
		} catch (NoResultException e){
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("ReuniaoSiacolDao || reunioesParaCriacaoDeSumula", StringUtil.convertObjectToJson(idDepartamento), e);
		}
		
		return listReuniaoSiacol;
		
	}

	public int getQuorumPor(Long idReuniao) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT R.QUORUM FROM SIACOL_REUNIAO R ");
		sql.append("	WHERE R.id = :idReuniao ");
		
		
		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idReuniao", idReuniao);

			Object quorum = (Object) query.getSingleResult();
			return quorum == null ? 0 : Integer.parseInt(quorum.toString());

			
		} catch (NoResultException e){
			return 0;
		} catch (Throwable e) {
			httpGoApi.geraLog("ReuniaoSiacolDao || reunioesParaCriacaoDeSumula", StringUtil.convertObjectToJson(idReuniao), e);
		}
		
		return 0;
	}

}
