package br.org.crea.commons.dao.siacol;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.DocumentoDao;
import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.dao.cadastro.domains.DepartamentoDao;
import br.org.crea.commons.dao.cadastro.pessoa.PessoaDao;
import br.org.crea.commons.models.cadastro.Departamento;
import br.org.crea.commons.models.cadastro.Documento;
import br.org.crea.commons.models.cadastro.enuns.TipoEventoAuditoria;
import br.org.crea.commons.models.commons.dtos.GenericDto;
import br.org.crea.commons.models.commons.dtos.PesquisaAuditoriaDto;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;
import br.org.crea.commons.models.corporativo.pessoa.PessoaFisica;
import br.org.crea.commons.models.siacol.HabilidadePessoaSiacol;
import br.org.crea.commons.models.siacol.ProtocoloSiacol;
import br.org.crea.commons.models.siacol.dtos.GenericSiacolDto;
import br.org.crea.commons.models.siacol.dtos.HabilidadePessoaDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.service.cadastro.AuditoriaService;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class HabilidadePessoaDao extends GenericDao<HabilidadePessoaSiacol, Serializable> {

	@Inject HttpClientGoApi httpGoApi;
	
	@Inject DepartamentoDao departamentoDao;
	
	@Inject PessoaDao pessoaDao;
	
	@Inject PersonalidadeSiacolDao personalidadeDao;
	
	@Inject  ProtocoloSiacolDao protocoloSiacolDao;
	
	@Inject AuditoriaService auditoriaService;
	
	@Inject DocumentoDao documentoDao;
	
	@Inject PersonalidadeSiacolDao personalidadeSiacolDao;

	public HabilidadePessoaDao() {
		super(HabilidadePessoaSiacol.class);
	}

	public HabilidadePessoaSiacol getHabilidadePessoaAnalista(HabilidadePessoaDto dto) {

		HabilidadePessoaSiacol habilidadePessoaSiacol = new HabilidadePessoaSiacol();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT A FROM HabilidadePessoaSiacol A");
		sql.append("	WHERE A.pessoa.id = :idPessoa ");
		sql.append("	AND A.assunto.id = :idAssunto ");
		sql.append("	AND A.departamento.id = :idDepartamento ");
		
		try {
			TypedQuery<HabilidadePessoaSiacol> query = em.createQuery(sql.toString(), HabilidadePessoaSiacol.class);
			query.setParameter("idPessoa", dto.getIdPessoa());
			query.setParameter("idAssunto", new Long(dto.getIdAssunto()));
			query.setParameter("idDepartamento", new Long(dto.getIdDepartamento()));

			habilidadePessoaSiacol = query.getSingleResult();
		} catch (Throwable e) {
			httpGoApi.geraLog("HabilidadePessoaDao || getHabilidadePessoaAnalista", StringUtil.convertObjectToJson(dto), e);

		}

		return habilidadePessoaSiacol;
	}
	
	public Pessoa getCoordedandorComHabilidade(Long idDepartamento) {

		Departamento departamento = new Departamento();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT D FROM Departamento D");
//		sql.append("	WHERE D.coordenador.descricaoPerfil =  'siacolcoordenadorcamara' ");
		sql.append("	WHERE D.id = :idDepartamento ");
		
		try {
			TypedQuery<Departamento> query = em.createQuery(sql.toString(), Departamento.class);
			query.setParameter("idDepartamento", idDepartamento);
			
			departamento = query.getSingleResult();

		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("HabilidadePessoaDao || getCoordedandorComHabilidade", StringUtil.convertObjectToJson(idDepartamento), e);

		}

		return  departamento.temCoordenador() ? departamento.getCoordenador() : 
				departamento.temAdjunto() ? departamento.getAdjunto() : null ;
	}
	
	//FIXME: esse método pode morrer e usar o que está genérico para analista e conselheiro
	//FIXME: getResponsavelDistribuicao
	public HabilidadePessoaSiacol getHabilidadePessoaConselheiro(HabilidadePessoaDto dto) {

		HabilidadePessoaSiacol habilidadePessoaSiacol = new HabilidadePessoaSiacol();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT A FROM HabilidadePessoaSiacol A");
		sql.append("	WHERE A.pessoa.id = :idPessoa ");
		sql.append("	AND A.assuntoSiacol.id = :idAssunto ");
		sql.append("	AND A.departamento.id = :idDepartamento ");
		
		try {
			TypedQuery<HabilidadePessoaSiacol> query = em.createQuery(sql.toString(), HabilidadePessoaSiacol.class);
			query.setParameter("idPessoa", dto.getIdPessoa());
			query.setParameter("idAssunto", new Long(dto.getIdAssunto()));
			query.setParameter("idDepartamento", new Long(dto.getIdDepartamento()));
			
			
			habilidadePessoaSiacol = query.getSingleResult();
		} catch (Throwable e) {
			httpGoApi.geraLog("HabilidadePessoaDao || getHabilidadePessoaConselheiro", StringUtil.convertObjectToJson(dto), e);

		}

		return habilidadePessoaSiacol;
	}

	public List<HabilidadePessoaSiacol> getAllByIdPessoa(GenericSiacolDto dto) {

		List<HabilidadePessoaSiacol> habilidadePessoaSiacol = new ArrayList<HabilidadePessoaSiacol>();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT A FROM HabilidadePessoaSiacol A");
		sql.append("	WHERE A.pessoa.id = :idPessoa ");
		sql.append("	AND  A.departamento.id = :idDepartamento ");

		try {
			TypedQuery<HabilidadePessoaSiacol> query = em.createQuery(sql.toString(), HabilidadePessoaSiacol.class);
			query.setParameter("idPessoa",new Long (dto.getId()));
			query.setParameter("idDepartamento", dto.getIdDepartamento());

			habilidadePessoaSiacol = query.getResultList();

		} catch (Throwable e) {
			httpGoApi.geraLog("HabilidadePessoaDao || getAllByIdPessoa", StringUtil.convertObjectToJson(dto), e);
		}

		return habilidadePessoaSiacol;
	}

	public Boolean verificaAtivo(Long idPessoa) {

		Boolean ativo = false;

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT A FROM HabilidadePessoaSiacol A");
		sql.append("	WHERE A.pessoa.id = :idPessoa ");
		sql.append("	AND A.ativo = 1 ");
		sql.append("	AND ROWNUM = 1 ");

		try {
			TypedQuery<HabilidadePessoaSiacol> query = em.createQuery(sql.toString(), HabilidadePessoaSiacol.class);
			query.setParameter("idPessoa", idPessoa);

			ativo = query.getResultList().isEmpty() ? false : true;
		} catch (NoResultException e) {
			return false;
		} catch (Throwable e) {
			httpGoApi.geraLog("HabilidadePessoaDao || verificaAtivo", StringUtil.convertObjectToJson(idPessoa), e);

		}

		return ativo;

	}

	public GenericDto ativacaoHabilidades(GenericDto dto) {

		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE HabilidadePessoaSiacol A");
		sql.append("	SET A.ativo = :ativo ");
		sql.append("	WHERE A.pessoa.id = :idPessoa ");

		try {
			TypedQuery<HabilidadePessoaSiacol> query = em.createQuery(sql.toString(), HabilidadePessoaSiacol.class);
			query.setParameter("ativo", dto.getSiacol());
			query.setParameter("idPessoa", dto.getIdFuncionario());

			query.executeUpdate();
		} catch (Throwable e) {
			httpGoApi.geraLog("HabilidadePessoaDao || ativacaoHabilidades", StringUtil.convertObjectToJson(dto), e);

		}

		return dto;

	}

	public List<HabilidadePessoaSiacol> getHabilidades(Long idFuncionario) {

		List<HabilidadePessoaSiacol> listaHabilidade = new ArrayList<HabilidadePessoaSiacol>();

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT A FROM HabilidadePessoaSiacol A ");
		sql.append("	WHERE A.pessoa.id = :idFuncionario ");

		try {
			TypedQuery<HabilidadePessoaSiacol> query = em.createQuery(sql.toString(), HabilidadePessoaSiacol.class);
			query.setParameter("idFuncionario", idFuncionario);

			listaHabilidade = query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("HabilidadePessoaDao || getHabilidades", StringUtil.convertObjectToJson(idFuncionario), e);

		}

		return listaHabilidade;

	}
	
	public List<Long> buscaDepartamentosByPessoa(Long idPessoa, Long idDepartamento) {
		
		List<Long> listaDepartamento = new ArrayList<Long>();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT A.departamento.id FROM HabilidadePessoaSiacol A ");
		sql.append("	WHERE A.pessoa.id = :idPessoa ");
		sql.append("	AND A.departamento.id NOT IN (:idDepartamento) ");
		
		try {
			TypedQuery<Object> query = em.createQuery(sql.toString(), Object.class);
			query.setParameter("idPessoa", idPessoa);
			query.setParameter("idDepartamento", idDepartamento);
			
			for ( Object o : query.getResultList() ){
				listaDepartamento.add((Long) o);
			}

			return listaDepartamento;
			
		} catch(NoResultException e){
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("HabilidadePessoaDao || buscaDepartamentosByPessoa", StringUtil.convertObjectToJson(idPessoa), e);

		}

		return null;
	}

	public List<PessoaFisica> getPessoasByHabilidade(GenericSiacolDto dto, UserFrontDto userFrontDto) {
		
		List<PessoaFisica> listaPessoaFisica = new ArrayList<PessoaFisica>();	
		List<Long> listIdsImpedidos = new ArrayList<Long>();
		List<Long> listaDepartamento = new ArrayList<Long>();
		List<Long> listaAssunto = new ArrayList<Long>();
		List<Long> idsConselheirosDepartamento = new ArrayList<Long>();
		listaDepartamento = dto.getListaIdDepartamento();
		listaAssunto = dto.getListaIdAssunto();
		
		
		if (dto.getNumeroProtocolo() != null) {
			PesquisaAuditoriaDto pesquisaAuditoriaDto = new PesquisaAuditoriaDto();
			pesquisaAuditoriaDto.setIdDepartamentoDestino(dto.getIdDepartamento());
			pesquisaAuditoriaDto.setEvento(TipoEventoAuditoria.CONSELHEIRO_IMPEDIDO);
			pesquisaAuditoriaDto.setNumero(protocoloSiacolDao.getBy(dto.getNumeroProtocolo()).getNumeroProtocolo().toString());
			
			listIdsImpedidos = auditoriaService.conselheirosImpedidos(pesquisaAuditoriaDto);
			listIdsImpedidos.add(new Long(userFrontDto.getIdPessoa()));
		}else {
			listIdsImpedidos.add(new Long(userFrontDto.getIdPessoa()));
		}
		if (dto.getListaIdDepartamento().contains(11L)) {
			if (dto.getNumeroProtocolo() != null) {
				idsConselheirosDepartamento = buscarConselheirosImpedidos(dto);
			} else if (dto.getListaNumeroProtocolo() != null){
				for (Long numeroProtocolo : dto.getListaNumeroProtocolo()) {
					dto.setNumeroProtocolo(numeroProtocolo);
					idsConselheirosDepartamento.addAll(buscarConselheirosImpedidos(dto));
				}
			}
			
			listIdsImpedidos.addAll(idsConselheirosDepartamento);
		}

		StringBuilder sql = new StringBuilder();

		sql.append("SELECT P FROM PessoaFisica P ");
		sql.append("	WHERE P.id IN ( ");
		sql.append("   SELECT HS.pessoa.id FROM HabilidadePessoaSiacol HS ");
		sql.append(" 	WHERE HS.ativo = 1  ");
		sql.append(" 	AND HS.departamento.id IN (:listaDepartamento) ");
		if (!listIdsImpedidos.isEmpty()) {
			sql.append(" AND HS.pessoa.id not in (:listIdsImpedidos) ");
		}
		if(dto.getIdPerfil().equals("ANALISTA")){
			sql.append(" 	AND HS.assunto.id IN (:listaAssuntos) )  ");
		} else if (dto.getIdPerfil().equals("ESTAGIARIOAI") ) {
			sql.append(" 	AND HS.assunto.id IN (:listaAssuntos)   ");
			sql.append(" 	AND HS.pessoa.perfil = '69ad894fe97b2f90b27cb642157e87cc' )  ");
			
		}else if(dto.getIdPerfil().equals("ANALISTAAI")){ 
			sql.append(" 	AND HS.assuntoSiacol.id IN (:listaAssuntos)   ");
			sql.append(" 	AND HS.pessoa.perfil = '69ad894fe97b2f90b27cb642157ebeb1' )  ");
		}else{
			sql.append(" 	AND HS.assuntoSiacol.id IN (:listaAssuntos) )  ");
		}
		

		try {
			TypedQuery<PessoaFisica> query = em.createQuery(sql.toString(), PessoaFisica.class);
			query.setParameter("listaDepartamento", listaDepartamento);
			query.setParameter("listaAssuntos", listaAssunto);
			if (!listIdsImpedidos.isEmpty()) {
				query.setParameter("listIdsImpedidos", listIdsImpedidos);
			}	

			listaPessoaFisica = query.getResultList();

		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("ProtocoloSiacolDao || getPessoasByHabilidade", StringUtil.convertObjectToJson(""), e);
		}
		return listaPessoaFisica;
	}
	
	
	private List<Long> buscarConselheirosImpedidos(GenericSiacolDto dto) {
		List<Long> idsConselheirosDepartamento = new ArrayList<Long>();
		ProtocoloSiacol protocoloSiacol = protocoloSiacolDao.getBy(dto.getNumeroProtocolo());
		if (protocoloSiacol.getProtocoloPrimeiraInstancia() != null) {
			idsConselheirosDepartamento = buscaConselheirosSuplentesEfetivosLicenciadosPor(protocoloSiacol.getProtocoloPrimeiraInstancia().getDepartamento());
		} else {
			List<Documento> ListaDecisao = documentoDao.getDocumentosPorTipoENumeroProtocolo(1115L, protocoloSiacolDao.getBy(dto.getNumeroProtocolo()).getNumeroProtocolo());
			if (!ListaDecisao.isEmpty()) {
				idsConselheirosDepartamento = buscaConselheirosSuplentesEfetivosLicenciadosPor(ListaDecisao.get(ListaDecisao.size()-1).getDepartamento());
			}
		}
		return idsConselheirosDepartamento;
	}
	
	private List<Long> buscarConselheirosImpedidosIndividual(GenericSiacolDto dto) {
		List<Long> idsConselheirosDepartamento = new ArrayList<Long>();
		ProtocoloSiacol protocoloSiacol = protocoloSiacolDao.getProtocoloBy(dto.getNumeroProtocolo());
		if (protocoloSiacol.getProtocoloPrimeiraInstancia() != null) {
			idsConselheirosDepartamento = buscaConselheirosSuplentesEfetivosLicenciadosPor(protocoloSiacol.getProtocoloPrimeiraInstancia().getDepartamento());
		} else {
			List<Documento> ListaDecisao = documentoDao.getDocumentosPorTipoENumeroProtocolo(1115L, protocoloSiacolDao.getProtocoloBy(dto.getNumeroProtocolo()).getNumeroProtocolo());
			if (!ListaDecisao.isEmpty()) {
				idsConselheirosDepartamento = buscaConselheirosSuplentesEfetivosLicenciadosPor(ListaDecisao.get(ListaDecisao.size()-1).getDepartamento());
			}
		}
		return idsConselheirosDepartamento;
	}

	private List<Long> buscaConselheirosSuplentesEfetivosLicenciadosPor(Departamento departamento) {
		List<Long> listaConselheirosEfetivosESuplentesDoDepartamento = new ArrayList<Long>();
		Long statusLicenca = new Long(0);
		boolean ehComissao = false;
		List<Long> listaIdsEfetivosAtivosDoDepartamento = personalidadeSiacolDao.getConselheirosEfetivosPorIdDepartamento(departamento.getId(), statusLicenca, ehComissao);
		List<Long> listaIdsSuplentesDosEfetivosAtivosDoDepartamento = personalidadeSiacolDao.getSuplentesPor(listaIdsEfetivosAtivosDoDepartamento);
		List<Long> listaIdsSuplentesDosLicenciadosDoDepartamento = personalidadeSiacolDao.getSuplentesDosLicenciadosPor(departamento.getId());
		
		listaConselheirosEfetivosESuplentesDoDepartamento.addAll(listaIdsEfetivosAtivosDoDepartamento);
		listaConselheirosEfetivosESuplentesDoDepartamento.addAll(listaIdsSuplentesDosEfetivosAtivosDoDepartamento);
		listaConselheirosEfetivosESuplentesDoDepartamento.addAll(listaIdsSuplentesDosLicenciadosDoDepartamento);
		return listaConselheirosEfetivosESuplentesDoDepartamento;
	}


	public Long recuperaAnalistaComHabilidade(Long idDepartamento, Long idAssuntoSiacol) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT GETCONSELHEIROSIACOL(:idDepartamento, :idAssunto) FROM DUAL ");

		try {
			
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idDepartamento", idDepartamento);
			query.setParameter("idAssunto", idAssuntoSiacol);
			
			BigDecimal result = (BigDecimal) query.getSingleResult();
			return result != null ? result.setScale(0,BigDecimal.ROUND_UP).longValueExact(): null;
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ProtocoloSiacolDao || distribuiAleatorio", StringUtil.convertObjectToJson(idDepartamento + " -- " + idAssuntoSiacol), e);
		}
		
		return null;
		
	}
	
	public Pessoa getResponsavelDistribuicao(GenericSiacolDto dto, UserFrontDto userFrontDto) {
		
		if( !grupoDaDistribuicaoPossuiResponsavel(dto) ) {
			
			Pessoa coordenadorCoac = departamentoDao.getBy(new Long(230201)).getCoordenador();
			Pessoa coordenadorCamara = departamentoDao.getBy(dto.getIdDepartamento()).getCoordenador();
			
			return dto.distribuicaoParaConselheiro() ? coordenadorCamara : coordenadorCoac;
		}
		
		if( filaResponsavelDistribuicaoEstaCheia(dto, userFrontDto) ) {
			
			liberaGrupoResponsaveisDistribuicao(dto);
		}
		
		List<HabilidadePessoaSiacol> listHabilidadePessoaSiacol = new ArrayList<HabilidadePessoaSiacol>(); 
		
		Random random = new Random();
		listHabilidadePessoaSiacol = getResponsaveisLiberadosParaDistribuicao(dto, userFrontDto);

		int numeroAleatorio = random.nextInt(listHabilidadePessoaSiacol.size());

		return getResponsaveisLiberadosParaDistribuicao(dto, userFrontDto).get(numeroAleatorio).getPessoa();
		
	}
	
	public List<HabilidadePessoaSiacol> getResponsaveisPorAssuntoEDepartamento(GenericSiacolDto dto) {

		List<HabilidadePessoaSiacol> listaHabilidade = new ArrayList<HabilidadePessoaSiacol>();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT H FROM HabilidadePessoaSiacol H ");
		sql.append("	WHERE H.departamento.id = :idDepartamento ");
		sql.append("	AND H.ativo = true ");
		
		if(dto.distribuicaoParaConselheiro()) {
			sql.append(" AND H.assuntoSiacol.id = :idAssunto ");
		} else {
			sql.append(" AND H.assunto.id = :idAssunto ");
		}

		try {
			
			TypedQuery<HabilidadePessoaSiacol> query = em.createQuery(sql.toString(), HabilidadePessoaSiacol.class);
			query.setParameter("idDepartamento", dto.getIdDepartamento());
			query.setParameter("idAssunto", dto.getIdAssunto());
			listaHabilidade = query.getResultList();
			
		} catch (NoResultException e) {
			return listaHabilidade;
		} catch (Throwable e) {
			httpGoApi.geraLog("HabilidadePessoaDao || getResponsaveisPorAssuntoEDepartamento", StringUtil.convertObjectToJson(dto), e);
		}

		return listaHabilidade;
	}

	public List<HabilidadePessoaSiacol> getResponsaveisLiberadosParaDistribuicao(GenericSiacolDto dto, UserFrontDto userFrontDto) {
		List<Long> listIdsImpedidos = new ArrayList<Long>();
		List<Long> idsConselheirosDepartamento = new ArrayList<Long>();
		List<HabilidadePessoaSiacol> listResponsaveis = new ArrayList<HabilidadePessoaSiacol>();
		if (dto.getIdDepartamento() == 11 && dto.distribuicaoParaConselheiro()) {
			Long statusLicenca = new Long(0);
			boolean ehComissao = false;
			dto.setListaId(personalidadeDao.getConselheirosEfetivosPorIdDepartamento(dto.getIdDepartamento(), statusLicenca, ehComissao));
		}
		
		if (dto.distribuicaoParaConselheiro()) {
			PesquisaAuditoriaDto pesquisaAuditoriaDto = new PesquisaAuditoriaDto();
//			pesquisaAuditoriaDto.setIdDepartamentoDestino(dto.getIdDepartamento());
			pesquisaAuditoriaDto.setEvento(TipoEventoAuditoria.CONSELHEIRO_IMPEDIDO);
			pesquisaAuditoriaDto.setNumero(dto.getNumeroProtocolo().toString());
			
			listIdsImpedidos = auditoriaService.conselheirosImpedidos(pesquisaAuditoriaDto);
			listIdsImpedidos.add(new Long(userFrontDto.getIdPessoa()));
		}
		if (dto.getIdDepartamento().equals(11L)) {
			if (dto.getNumeroProtocolo() != null) {
				idsConselheirosDepartamento = buscarConselheirosImpedidosIndividual(dto);
			} else if (dto.getListaNumeroProtocolo() != null){
				for (Long numeroProtocolo : dto.getListaNumeroProtocolo()) {
					dto.setNumeroProtocolo(numeroProtocolo);
					idsConselheirosDepartamento.addAll(buscarConselheirosImpedidosIndividual(dto));
				}
			}
			
			listIdsImpedidos.addAll(idsConselheirosDepartamento);
		}
		
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT H FROM HabilidadePessoaSiacol H ");
		sql.append("	WHERE H.departamento.id = :idDepartamento ");
		sql.append("	AND H.ativo = true ");
		sql.append("	AND ( H.pessoaLiberadaParaReceber = true ");
		sql.append("	OR  H.pessoaLiberadaParaReceber is null ) ");
		
//		if (dto.getIdDepartamento() == 11 && dto.distribuicaoParaConselheiro()) {
//			sql.append(" AND H.pessoa.id not in (:listaIdsEfetivos) ");
//		}
		
		if(dto.distribuicaoParaConselheiro()) {
			sql.append(" AND H.assuntoSiacol.id = :idAssunto ");
			if (!listIdsImpedidos.isEmpty()) {
				sql.append(" AND H.pessoa.id not in (:listIdsImpedidos) ");
			}	
		} else {
			sql.append(" AND H.assunto.id = :idAssunto ");
		}
		
		try {
			
			TypedQuery<HabilidadePessoaSiacol> query = em.createQuery(sql.toString(), HabilidadePessoaSiacol.class);
			query.setParameter("idAssunto", dto.getIdAssunto());
			query.setParameter("idDepartamento", dto.getIdDepartamento());	
//			if (dto.getIdDepartamento() == 11 && dto.distribuicaoParaConselheiro()) {
//				query.setParameter("listaIdsEfetivos", dto.getListaId());
//			}	
			if (dto.distribuicaoParaConselheiro() && !listIdsImpedidos.isEmpty()) {
				query.setParameter("listIdsImpedidos", listIdsImpedidos);
			}
			
			listResponsaveis = query.getResultList();
			
		} catch (NoResultException e) {
			return listResponsaveis;
		} catch (Throwable e) {
			httpGoApi.geraLog("HabilidadePessoaDao || getResponsaveisLiberadosParaDistribuicao", StringUtil.convertObjectToJson(dto), e);
		}
		
		return listResponsaveis;
	}
	
	public void bloquearResponsavelParaDistribuicao(GenericSiacolDto dto) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE HabilidadePessoaSiacol H ");
		sql.append("	SET H.pessoaLiberadaParaReceber = false ");
		sql.append("	WHERE H.pessoa.id = :idPessoa ");
		sql.append("	AND H.departamento.id = :idDepartamento ");
		
		if(dto.distribuicaoParaConselheiro()) {
			sql.append(" AND H.assuntoSiacol.id = :idAssunto ");
		} else {
			sql.append(" AND H.assunto.id = :idAssunto ");
		}
		
		try {
			
			Query query = em.createQuery(sql.toString());
			query.setParameter("idPessoa", dto.getIdResponsavelAtual());
			query.setParameter("idDepartamento", dto.getIdDepartamento());
			query.setParameter("idAssunto", dto.getIdAssunto());
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("HabilidadePessoaDao || bloquearResponsavelParaDistribuicao", StringUtil.convertObjectToJson(dto), e);
		}
	}
	
	public void liberaGrupoResponsaveisDistribuicao(GenericSiacolDto dto) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE HabilidadePessoaSiacol H ");
		sql.append("	SET H.pessoaLiberadaParaReceber = true ");
		sql.append("	WHERE H.departamento.id = :idDepartamento  ");
		
		if(dto.distribuicaoParaConselheiro()) {
			sql.append(" AND H.assuntoSiacol.id = :idAssunto ");
		} else {
			sql.append(" AND H.assunto.id = :idAssunto ");
		}

		try {
			
			Query query = em.createQuery(sql.toString());
			query.setParameter("idAssunto", dto.getIdAssunto());
			query.setParameter("idDepartamento", dto.getIdDepartamento());
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("HabilidadePessoaDao || liberarGrupoResponsaveisDistribuicao", StringUtil.convertObjectToJson(dto), e);
		}
	}

	public boolean grupoDaDistribuicaoPossuiResponsavel(GenericSiacolDto dto) {
		return getResponsaveisPorAssuntoEDepartamento(dto).isEmpty() ? false : true;
	}
	
	public boolean filaResponsavelDistribuicaoEstaCheia(GenericSiacolDto dto, UserFrontDto userFrontDto) {
		return !getResponsaveisLiberadosParaDistribuicao(dto, userFrontDto).isEmpty() ? false : true;
	}

	public List<HabilidadePessoaSiacol> getPessoasHabilidadePorPerfilEDepartamento(Long idDepartamento, List<String> perfis) {
		
	List<HabilidadePessoaSiacol> listHabilidade = new ArrayList<HabilidadePessoaSiacol>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT H FROM HabilidadePessoaSiacol H ");
		sql.append("	WHERE H.departamento.id = :idDepartamento ");
		sql.append("	AND H.pessoa.descricaoPerfil IN (:perfis) ");
		
		
		try {
			
			TypedQuery<HabilidadePessoaSiacol> query = em.createQuery(sql.toString(), HabilidadePessoaSiacol.class);
			query.setParameter("perfis", perfis);
			query.setParameter("idDepartamento", idDepartamento);
			
			listHabilidade = query.getResultList();
			
		} catch (NoResultException e) {
			return listHabilidade;
		} catch (Throwable e) {
			httpGoApi.geraLog("HabilidadePessoaDao || getPessoasHabilidadePorPerfilEDepartamento", StringUtil.convertObjectToJson("sem parametro"), e);
		}
		
		return listHabilidade;
	}

	public List<Departamento> listaDepartamentosHabilitado(Long idPessoa) {
		
		List<Departamento> listDepartamento = new ArrayList<Departamento>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT H.departamento FROM HabilidadePessoaSiacol H ");
		sql.append("	WHERE H.pessoa.id = :idPessoa ");
		
		
		try {
			
			TypedQuery<Departamento> query = em.createQuery(sql.toString(), Departamento.class);
			query.setParameter("idPessoa", idPessoa);
			
			listDepartamento = query.getResultList();
			
		} catch (NoResultException e) {
			return listDepartamento;
		} catch (Throwable e) {
			httpGoApi.geraLog("HabilidadePessoaDao || listaDepartamentosHabilitado", StringUtil.convertObjectToJson("sem parametro"), e);
		}
		
		return listDepartamento;
	}

	public List<HabilidadePessoaSiacol> listaPessoaHabilidade(GenericDto dto) {
		
		List<HabilidadePessoaSiacol> listHabilidadePessoaSiacol = new ArrayList<HabilidadePessoaSiacol>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT H FROM HabilidadePessoaSiacol H ");
		sql.append("	WHERE H.departamento.id = :idDepartamento ");
		if (dto.getSiacol()) {
			sql.append("	AND H.assuntoSiacol is not null ");
		}else{
			sql.append("	AND H.assunto is not null ");
		}
		sql.append("	ORDER BY H.pessoa.id ");
		
		
		try {
			
			TypedQuery<HabilidadePessoaSiacol> query = em.createQuery(sql.toString(), HabilidadePessoaSiacol.class);
			query.setParameter("idDepartamento", dto.getIdDepartamento());
			
			listHabilidadePessoaSiacol = query.getResultList();
			
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("HabilidadePessoaDao || listaPessoaHabilidade", StringUtil.convertObjectToJson("sem parametro"), e);
		}
		
		return listHabilidadePessoaSiacol;
	}

	public boolean verificaAtivoDepartamento(Long ultimoAnalista, Long idDepartamento) {

		Boolean ativo = false;

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT A FROM HabilidadePessoaSiacol A");
		sql.append("	WHERE A.pessoa.id = :idPessoa ");
		sql.append("	AND A.departamento.id = :idDepartamento ");
		sql.append("	AND A.ativo = 1 ");

		try {
			TypedQuery<HabilidadePessoaSiacol> query = em.createQuery(sql.toString(), HabilidadePessoaSiacol.class);
			query.setParameter("idPessoa", ultimoAnalista);
			query.setParameter("idDepartamento", idDepartamento);

			ativo = query.getResultList().isEmpty() ? false : true;
		} catch (NoResultException e) {
			return false;
		} catch (Throwable e) {
			httpGoApi.geraLog("HabilidadePessoaDao || verificaAtivo", StringUtil.convertObjectToJson(ultimoAnalista), e);

		}

		return ativo;

	}
	
	
}

