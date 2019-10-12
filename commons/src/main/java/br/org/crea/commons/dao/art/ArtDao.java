package br.org.crea.commons.dao.art;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.dao.corporativo.Page;
import br.org.crea.commons.models.art.Art;
import br.org.crea.commons.models.art.ContratoArt;
import br.org.crea.commons.models.art.dtos.ArtDto;
import br.org.crea.commons.models.art.dtos.ArtExigenciaDto;
import br.org.crea.commons.models.art.dtos.ArtMinDto;
import br.org.crea.commons.models.art.dtos.PesquisaArtDto;
import br.org.crea.commons.models.art.enuns.TipoTaxaArtEnum;
import br.org.crea.commons.models.commons.dtos.DomainGenericDto;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.DateUtils;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class ArtDao extends GenericDao<Art, Serializable> {
	
	@Inject
	HttpClientGoApi httpGoApi;

	public ArtDao() {
		super(Art.class);
	}
	
    public List<ArtMinDto> pesquisaARTs(PesquisaArtDto pesquisa) {
		
		List<ArtMinDto> listArt = new ArrayList<ArtMinDto>();

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT A.NUMERO, ");
		sql.append("   TO_CHAR(A.DATA_CADASTRO, 'DD/MM/YYYY'), ");
		sql.append("   N.DESCRICAO, ");
		sql.append("   B.DESCRICAO AS SITUACAO, ");
		sql.append("   TO_CHAR(A.FINALIZADA), ");
		sql.append("   (SELECT COUNT(*) FROM FIN_DIVIDA WHERE Identificador_Divida = A.NUMERO AND FK_CODIGO_STATUS_DIVIDA < 4) AS QT_DIVIDA, ");
		sql.append("   (SELECT COUNT(CODIGO) FROM ART_EXIGENCIA_ART WHERE FK_ART = A.NUMERO) AS QT_EXIGENCIA, ");
		sql.append("   TO_CHAR(A.DATA_PAGAMENTO, 'DD/MM/YYYY'), ");
		sql.append("   NVL(A.VALOR_PAGO, 0), ");
		sql.append("   NVL(A.BAIXADA, 0), ");
		sql.append("   NVL(A.ISACAO_ORDINARIA, 0), ");
		sql.append("   NVL(A.FK_BAIXA, 0) ");
		sql.append(" FROM ART_ART A ");
		
		joinPesquisaARTs(pesquisa, sql);
		
		queryPesquisaARTs(pesquisa, sql);

		try {
			
			Query query = em.createNativeQuery(sql.toString());
			parametrosPesquisaARTs(pesquisa, query);
			
			
			Page page = new Page(pesquisa.getPage(), pesquisa.getRows());
			page.paginate(query);

			Iterator<?> it = query.getResultList().iterator();
			
			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {
					
					Object[] result = (Object[]) it.next();
					ArtMinDto art = new ArtMinDto();

					art.setNumero(result[0] == null ? "" : result[0].toString());
					art.setDataCadastro(result[1] == null ? "" : result[1].toString());
					art.setNatureza(result[2] == null ? "" : result[2].toString());
					art.setSituacao(result[3] == null ? "" : result[3].toString());
					art.setFinalizada(result[4].toString().equals("1"));
					art.setTemDivida(!result[5].toString().equals("0"));
					art.setTemExigencia(!result[6].toString().equals("0"));
					art.setDataPagamento(result[7] == null ? "" : result[7].toString());
					art.setValorPago((BigDecimal) result[8]);
					art.setBaixada(result[9].toString().equals("1"));
					art.setAcaoOrdinaria(result[10].toString().equals("1"));
					BigDecimal idBaixa = (BigDecimal) result[11];
					art.setIdBaixa(idBaixa.setScale(0, BigDecimal.ROUND_UP).longValueExact());

					listArt.add(art);
				}
			}

		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDao || PesquisaARTs", StringUtil.convertObjectToJson(pesquisa), e);
		}

		return listArt;
	}
    
    private void joinPesquisaARTs(PesquisaArtDto pesquisa, StringBuilder sql) {
		
    	sql.append(" JOIN ART_NATUREZA N ON (A.FK_NATUREZA_ART = N.CODIGO) ");
		sql.append(" JOIN ART_BAIXA B ON (A.FK_BAIXA = B.CODIGO) ");
		
    	
		if(pesquisa.temDataInicioEfimContrato()) {
			sql.append(" JOIN ART_CONTRATO C ON (C.FK_ART = A.NUMERO) ");
		}
				
	}
    
	private void parametrosPesquisaARTs(PesquisaArtDto pesquisa, Query query) {
		
		query.setParameter("idPessoa", pesquisa.getIdPessoa());
		
		if(pesquisa.temNumeroART()) {
			query.setParameter("numeroArt", pesquisa.getNumero().toUpperCase() + "%");
		}
		
		if(pesquisa.temIdNatureza()) {
			query.setParameter("idNatureza", pesquisa.getIdNatureza());
		}
		
		if(pesquisa.temIdTipo()) {
			query.setParameter("idTipo", pesquisa.getIdTipo());
		}
		
		if(pesquisa.temNumeroARTPrincipal()) {
			query.setParameter("numeroArtPrincipal", pesquisa.getNumeroArtPrincipal().toUpperCase());
		}
				
		if(pesquisa.temDataInicioEfimContrato()) {
			query.setParameter("dataInicioContrato", DateUtils.generateDate(pesquisa.getDataInicioContrato()));
			query.setParameter("dataFimContrato", DateUtils.generateDate(pesquisa.getDataFimContrato()));
		}
		
		if(pesquisa.temDataInicioEfimCadastro()) {
			query.setParameter("dataInicioCadastro", DateUtils.generateDate(pesquisa.getDataInicioCadastro()));
			query.setParameter("dataFimCadastro", DateUtils.generateDate(pesquisa.getDataFimCadastro()));
		}
		
		if(pesquisa.temDataInicioEfimPagamento()) {
			query.setParameter("dataInicioPagamento", DateUtils.generateDate(pesquisa.getDataInicioPagamento()));
			query.setParameter("dataFimPagamento", DateUtils.generateDate(pesquisa.getDataFimPagamento()));
		}
		
		if(pesquisa.temNomeContratante()) {
			query.setParameter("nomeContratante", pesquisa.getNomeContratante().toUpperCase() + "%");
		}
				
	}

	private void queryPesquisaARTs(PesquisaArtDto pesquisa, StringBuilder sql) {
		
		sql.append(" WHERE 1 = 1 ");
		
		sql.append(" AND B.SITUACAO <> 'C' ");

		if(pesquisa.heProfissional()) {
			
			sql.append(" AND A.FK_PROFISSIONAL = :idPessoa ");
			
		} else {
			sql.append(" AND A.FK_EMPRESA = :idPessoa ");
			sql.append(" AND FK_PROFISSIONAL in ( select distinct FK_CODIGO_PROFISSIONAIS from CAD_QUADROS_TECNICOS where FK_CODIGO_EMPRESAS =  :idPessoa and DATAFIMQT is null ) ");
		}
				
		if(pesquisa.temNumeroART()) {
			sql.append(" AND A.NUMERO LIKE :numeroArt ");
		}
		
		if(pesquisa.temIdNatureza()) {
			sql.append(" AND A.FK_NATUREZA_ART = :idNatureza ");
		}
		
		if(pesquisa.temIdTipo()) {
			sql.append(" AND A.FK_TIPO_ART = :idTipo ");
		}
		
		if(pesquisa.temNumeroARTPrincipal()) {
			sql.append(" AND A.ART_PRINCIPAL_NUMERO = :numeroArtPrincipal ");
		}
		
		if(pesquisa.temDataInicioEfimContrato()) {
			sql.append(" AND TO_CHAR(C.DATA_INICIO, 'YYYYMMDD') >= TO_CHAR(:dataInicioContrato, 'YYYYMMDD') ");
			sql.append(" AND TO_CHAR(C.DATA_INICIO, 'YYYYMMDD') <= TO_CHAR(:dataFimContrato, 'YYYYMMDD') ");
		}
		
		if(pesquisa.temDataInicioEfimCadastro()) {
			sql.append(" AND TO_CHAR(A.DATA_CADASTRO, 'YYYYMMDD') >= TO_CHAR(:dataInicioCadastro, 'YYYYMMDD') ");
			sql.append(" AND TO_CHAR(A.DATA_CADASTRO, 'YYYYMMDD') <= TO_CHAR(:dataFimCadastro, 'YYYYMMDD') ");
		}
		
		if(pesquisa.temDataInicioEfimPagamento()) {
			sql.append(" AND TO_CHAR(A.DATA_PAGAMENTO, 'YYYYMMDD') >= TO_CHAR(:dataInicioPagamento, 'YYYYMMDD') ");
			sql.append(" AND TO_CHAR(A.DATA_PAGAMENTO, 'YYYYMMDD') <= TO_CHAR(:dataFimPagamento, 'YYYYMMDD') ");
		}
		
		if(pesquisa.isExigencia()) {
			sql.append(" AND A.NUMERO IN (SELECT AE.FK_ART FROM ART_EXIGENCIA_ART AE WHERE AE.FK_ART = A.NUMERO) ");
		}
		
		if(pesquisa.temNomeContratante()) {
			sql.append(" AND A.NUMERO IN (select AC.FK_ART from ART_CONTRATO AC where NOME_CONTRATANTE like :nomeContratante) ");
		}
		
		if (pesquisa.temRascunho()) {
			if (!pesquisa.getRascunho()) sql.append(" AND A.FINALIZADA = 1  ");
		}
		
		sql.append(" AND ( A.MODELO = 0 OR A.MODELO IS NULL) ");
		
		sql.append(" ORDER BY A.DATA_CADASTRO DESC ");
	}

	public int getTotalDeRegistrosDaPesquisa(PesquisaArtDto pesquisa) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT A.NUMERO ");
		sql.append(" FROM ART_ART A ");
		
		joinPesquisaARTs(pesquisa, sql);
					
		queryPesquisaARTs(pesquisa, sql);		

		try {
			
			Query query = em.createNativeQuery(sql.toString());
			parametrosPesquisaARTs(pesquisa, query);

			return query.getResultList().size();

		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDao || getTotalDeRegistrosDaPesquisa", StringUtil.convertObjectToJson(pesquisa), e);
		}

		return 0;
	}

	public List<String> getArtPorProfissional(Long idProfissional) {
		List<String> arts = new ArrayList<String>();
		StringBuilder sql = new StringBuilder();
		sql.append("select a.NUMERO from art_art a  ");
		sql.append("    where a.cancelada = 0 and FK_PROFISSIONAL = :idProfissional ");
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idProfissional", idProfissional);

			for (Object rl : query.getResultList()) {
				arts.add(rl.toString());
			}
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDao || getArtPorProfissional", StringUtil.convertObjectToJson(idProfissional), e);
		}
		return arts;
	}

	public List<String> getArtEmExigenciaPorProfissional(Long idProfissional) {
		List<String> arts = new ArrayList<String>();
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT DISTINCT e.fk_art ");
		sql.append("  FROM art_exigencia_art e ");
		sql.append(" WHERE e.fk_art IN (SELECT a.numero ");
		sql.append("                      FROM art_art a ");
		sql.append("                     WHERE a.fk_profissional = :idProfissional) ");
		sql.append("   AND e.fk_situacao_liberacao = 0 ");
		sql.append("   AND e.data IS NULL ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idProfissional", idProfissional);

			for (Object rl : query.getResultList()) {
				arts.add(rl.toString());
			}
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDao || getArtEmExigenciaPorProfissional", StringUtil.convertObjectToJson(idProfissional), e);
		}
		return arts;
	}
	
	public List<String> getArtPorEmpresa(Long idEmpresa) {

		List<String> arts = new ArrayList<String>();

		StringBuilder sql = new StringBuilder();
		sql.append(" select a.NUMERO from art_art a where a.FK_EMPRESA = :idEmpresa and a.cancelada = 0 and EXISTS ");
		sql.append("     (select * from CAD_QUADROS_TECNICOS cq ");
		sql.append("     where cq.DATAFIMQT is null and cq.FK_CODIGO_EMPRESAS = :idEmpresa and cq.FK_CODIGO_PROFISSIONAIS = a.FK_PROFISSIONAL ) ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idEmpresa", idEmpresa);

			for (Object rl : query.getResultList()) {
				arts.add(rl.toString());
			}

		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDao || getArtPorEmpresa", StringUtil.convertObjectToJson(idEmpresa), e);
		}

		return arts;
	}

	public int getQuantidadeArt(Long id) {
		int result = 0;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT COUNT(*) FROM art_art a  ");
		sql.append("    where a.cancelada = 0 and ");
		sql.append("    (FK_PROFISSIONAL = :id Or FK_EMPRESA = :id) AND a.data_pagamento is not null  ");
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("id", id);

			result = Integer.parseInt(query.getSingleResult().toString());
			return result;

		} catch (NoResultException e) {
			return 0;
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDao || Get Quantidade Art", StringUtil.convertObjectToJson(id), e);
		}
		return result;
	}

	public int getQuantidadeContratoBy(String numeroArt) {
		int result = 0;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT COUNT(*) FROM ART_CONTRATO C WHERE C.FK_ART = :numeroArt ");
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("numeroArt", numeroArt);

			result = Integer.parseInt(query.getSingleResult().toString());
			return result;

		} catch (NoResultException e) {
			return 0;
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDao || getQuantidadeContratoBy", StringUtil.convertObjectToJson(numeroArt), e);
		}

		return result;
	}
	

	public Art getArtPor(String numeroArt) {

		Art art = new Art();
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT A FROM Art A ");
		sql.append("   WHERE A.numero = :numeroArt ");
		sql.append("	AND A.dataPagamento is not null ");
		sql.append("	AND A.baixaArt.situacao != 'C' ");

		try {

			TypedQuery<Art> query = em.createQuery(sql.toString(), Art.class);
			query.setParameter("numeroArt", numeroArt);

			art = query.getSingleResult();

		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDao || getArtPor", StringUtil.convertObjectToJson(numeroArt), e);
		}
		return art;

	}
	
	public Art getArtPorId(String idArt) {
		Art art = new Art();
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT A FROM Art A ");
		sql.append("   WHERE A.numero = :numeroArt ");

		try {

			TypedQuery<Art> query = em.createQuery(sql.toString(), Art.class);
			query.setParameter("numeroArt", idArt);

			art = query.getSingleResult();

		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDao || getArtPorId", StringUtil.convertObjectToJson(idArt), e);
		}
		
		return art;
	}

	public List<Art> getListArtPor(PesquisaArtDto pesquisa) {

		List<Art> listArt = new ArrayList<Art>();
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT A FROM Art A ");
		if (!pesquisa.heProfissional()) {
			sql.append("	WHERE empresa.id = :idEmpresa AND A.dataPagamento is not null AND A.baixaArt.situacao != 'C' ");
		} else {
			sql.append("	WHERE profissional.id = :idProfissional AND A.dataPagamento is not null AND A.baixaArt.situacao != 'C' ");
		}
		
		try {

			TypedQuery<Art> query = em.createQuery(sql.toString(), Art.class);
			if (!pesquisa.heProfissional()) {
				query.setParameter("idEmpresa", pesquisa.getIdPessoa());
			} else {
				query.setParameter("idProfissional", pesquisa.getIdPessoa());
			}
			Page page = new Page(pesquisa.getPage(), pesquisa.getRows());
			page.paginate(query);

			listArt = query.getResultList();

		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDao || getListArtPor", StringUtil.convertObjectToJson(pesquisa), e);
		}

		return listArt;
	}
	
	public boolean atualizaDescricaoModelo(DomainGenericDto dto) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ART_ART A                       ");
		sql.append("    SET A.DESCRICAO_MODELO = :descricao ");
		sql.append("  WHERE A.NUMERO = :numeroArt           ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("descricao", dto.getDescricao() != null ? dto.getDescricao().toUpperCase() : "");
			query.setParameter("numeroArt", dto.getNumero());
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDao || atualizaDescricaoModelo", StringUtil.convertObjectToJson(dto), e);
			return false;
		}
		return true;
	}

	public DomainGenericDto atualizaNatureza(DomainGenericDto dto) {
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ART_ART A ");
		sql.append("     SET A.FK_NATUREZA_ART = :idNatureza ");
		sql.append("	 WHERE A.NUMERO = :numero ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idNatureza", dto.getId());
			query.setParameter("numero", dto.getNumero());
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDao || atualizaNatureza", StringUtil.convertObjectToJson(dto), e);
		}
		return dto;
	}
	
	
	public void limparCamposNaAtualizacaoDaNatureza(DomainGenericDto dto,UserFrontDto userFrontDto) {
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ART_ART A ");
		sql.append(userFrontDto.heProfissional() ? " SET A.FK_EMPRESA = '' " : " SET A.FK_PROFISSIONAL = '' ");
		sql.append("     , A.HA_PROFISSIONAL_CORESPONSAVEL = '' ");
		sql.append("     , A.HA_EMPRESA_VINCULADA = '' ");
		sql.append("     , A.FK_TIPO_ART = '' ");
		sql.append("     , A.ART_PRINCIPAL_NUMERO = '' ");
		sql.append("     , A.FK_ART_PRINCIPAL = '' ");
		sql.append("     , A.FK_PARTICIPACAO_TECNICA = '' ");
		sql.append("     , A.FK_ART_PARTICIPACAO = '' ");
		sql.append("     , A.DESCRICAO_FATO_GERADOR = '' ");
		sql.append("     , A.FK_FATO_GERADOR_ART = '' ");
		sql.append("	 WHERE A.NUMERO = :numero ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("numero", dto.getNumero());
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDao || limparCamposNaAtualizacaoDaNatureza", StringUtil.convertObjectToJson(dto), e);
		}
	}

	public DomainGenericDto atualizaFatoGerador(DomainGenericDto dto) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ART_ART A                                         ");
		sql.append("     SET A.FK_FATO_GERADOR_ART = :idFatoGerador           ");
		sql.append("	   WHERE A.NUMERO = :numero                           ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idFatoGerador", dto.getId());
			query.setParameter("numero", dto.getNumero());
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDao || atualizaFatoGerador", StringUtil.convertObjectToJson(dto), e);
		}
		return dto;
	}

	public boolean atualizaDescricaoFatoGerador(DomainGenericDto dto) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ART_ART A                                         ");
		sql.append("     SET A.DESCRICAO_FATO_GERADOR = :descricaoFatoGerador ");
		sql.append("	   WHERE A.NUMERO = :numero                           ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("descricaoFatoGerador", dto.getDescricao());
			query.setParameter("numero", dto.getNumero());
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDao || atualizaDescricaoFatoGerador", StringUtil.convertObjectToJson(dto), e);
			return false;
		}
		return true;
	}
	
	public DomainGenericDto atualizaTipo(DomainGenericDto dto) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ART_ART A                ");
		sql.append("    SET A.FK_TIPO_ART = :idTipo  ");	
		sql.append("  WHERE A.NUMERO = :numero       ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idTipo", dto.getId());
			query.setParameter("numero", dto.getNumero());
						
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDao || atualizaTipo", StringUtil.convertObjectToJson(dto), e);
		}
		return dto;
	}
	
	public void atualizaNumeroArtPrincipal(DomainGenericDto dto) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ART_ART A                                    ");
		sql.append("    SET A.ART_PRINCIPAL_NUMERO = :numeroArtPrincipal ");
		sql.append("    , A.FK_ART_PRINCIPAL = :numeroFKArtPrincipal ");
		sql.append("  WHERE A.NUMERO = :numero                           ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("numero", dto.getNumero());
			query.setParameter("numeroArtPrincipal", dto.getNome());
			query.setParameter("numeroFKArtPrincipal", dto.getNome());
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDao || atualizaNumeroArtPrincipal", StringUtil.convertObjectToJson(dto), e);
		}	
	}

	public void atualizaEmpresaContratado(DomainGenericDto dto) {
	
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ART_ART A                 ");
		sql.append("    SET A.FK_EMPRESA = :idEmpresa ");
		sql.append("  WHERE A.NUMERO = :numero        ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idEmpresa", dto.getId().equals(0L) ? "" : dto.getId());
			query.setParameter("numero", dto.getNumero());
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDao || atualizaEmpresaContratado", StringUtil.convertObjectToJson(dto), e);
		}
	}

	public void atualizaProfissionalContratado(DomainGenericDto dto) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ART_ART A                           ");
		sql.append("    SET A.FK_PROFISSIONAL = :idProfissional ");
		sql.append("  WHERE A.NUMERO = :numero                  ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idProfissional", dto.temId() ?  dto.getId() : "");
			query.setParameter("numero", dto.getNumero());
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDao || atualizaProfissionalContratado", StringUtil.convertObjectToJson(dto), e);
		}
		
	}

	public DomainGenericDto atualizaEntidadeClasse(DomainGenericDto dto) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ART_ART A ");
		sql.append("     SET A.FK_ENTIDADE_CLASSE = :idEntidadeClasse ");
		sql.append("	 WHERE A.NUMERO = :numero ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idEntidadeClasse", dto.getId());
			query.setParameter("numero", dto.getNumero());
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDao || atualizaEntidadeClasse", StringUtil.convertObjectToJson(dto), e);
		}
		return dto;
	}

	public void atualizaArtFinalizada(String numeroArt, boolean finalizada) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ART_ART A                    ");
		sql.append("    SET A.FINALIZADA = :finalizada   ");
		sql.append("  WHERE A.NUMERO = :numeroArt        ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("numeroArt", numeroArt);
			query.setParameter("finalizada", finalizada);
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDao || atualizaArtFinalizada", StringUtil.convertObjectToJson(numeroArt + " - " + finalizada), e);
		}
	}
	
	public BigDecimal getTaxaPorDataBaseETipoTaxa(Date dataBase, TipoTaxaArtEnum tipoTaxa) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT T.VALOR FROM ART_TAXA T                                                   ");
		sql.append("  WHERE TO_CHAR(T.INICIO_VIGENCIA, 'YYYYMMDD') <= TO_CHAR(:dataBase, 'YYYYMMDD')  ");
		sql.append("    AND (TO_CHAR(T.FIM_VIGENCIA, 'YYYYMMDD') >= TO_CHAR(:dataBase, 'YYYYMMDD')     ");
		sql.append("         OR T.FIM_VIGENCIA IS NULL)                                               ");
		sql.append("	AND T.FK_TIPO_TAXA = :tipoTaxa                                                ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("dataBase", dataBase);
			query.setParameter("tipoTaxa", tipoTaxa.getId());
			query.setMaxResults(1);
			BigDecimal valor = (BigDecimal) query.getSingleResult();
			
			return valor;
			
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDao || getTaxaPorDataBaseETipoTaxa", StringUtil.convertObjectToJson(dataBase + " - " + tipoTaxa), e);
		}
		
		return null;
	}
	
	public BigDecimal getTaxaPorValorCalculoDataBaseETipoTaxaSemFaixaInicial(BigDecimal valorCalculo, Date dataBase, Long tipoTaxa) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT T.VALOR FROM ART_TAXA T                                                   ");
		sql.append("  WHERE TO_CHAR(T.INICIO_VIGENCIA, 'YYYYMMDD') <= TO_CHAR(:dataBase, 'YYYYMMDD')  ");
		sql.append("    AND (TO_CHAR(T.FIM_VIGENCIA, 'YYYYMMDD') >= TO_CHAR(:dataBase, 'YYYYMMDD')    ");
		sql.append("         OR T.FIM_VIGENCIA IS NULL)                                               ");
		sql.append("	AND T.FK_TIPO_TAXA = :tipoTaxa                                                ");
		sql.append("	AND (T.FAIXA_FINAL >= :valorCalculo OR T.FAIXA_FINAL IS NULL)                 ");
		sql.append("	ORDER BY T.FAIXA_FINAL                                                        ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("dataBase", dataBase);
			query.setParameter("tipoTaxa", tipoTaxa);
			query.setParameter("valorCalculo", valorCalculo);
			query.setMaxResults(1);
			BigDecimal valor = (BigDecimal) query.getSingleResult();
			
			return valor;
			
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDao || getTaxaPorValorCalculoDataBaseETipoTaxa", StringUtil.convertObjectToJson(valorCalculo+" - "+dataBase + " - " + tipoTaxa), e);
		}
		
		return null;
	}
	
	public BigDecimal getTaxaPorValorCalculoDataBaseETipoTaxaComFaixaInicial(BigDecimal valorCalculo, Date dataBase, Long tipoTaxa) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT T.VALOR FROM ART_TAXA T                                                   ");
		sql.append("  WHERE TO_CHAR(T.INICIO_VIGENCIA, 'YYYYMMDD') <= TO_CHAR(:dataBase, 'YYYYMMDD')  ");
		sql.append("    AND (TO_CHAR(T.FIM_VIGENCIA, 'YYYYMMDD') >= TO_CHAR(:dataBase, 'YYYYMMDD')    ");
		sql.append("         OR T.FIM_VIGENCIA IS NULL)                                               ");
		sql.append("	AND T.FK_TIPO_TAXA = :tipoTaxa                                                ");
		sql.append("	AND T.FAIXA_INICIAL <= :valorCalculo                                          ");
		sql.append("	AND (T.FAIXA_FINAL >= :valorCalculo OR T.FAIXA_FINAL IS NULL)                 ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("dataBase", dataBase);
			query.setParameter("tipoTaxa", tipoTaxa);
			query.setParameter("valorCalculo", valorCalculo);
			query.setMaxResults(1);
			BigDecimal valor = (BigDecimal) query.getSingleResult();
			
			return valor;
			
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDao || getTaxaPorValorCalculoDataBaseETipoTaxaComFaixaInicial", StringUtil.convertObjectToJson(valorCalculo+" - "+dataBase + " - " + tipoTaxa), e);
		}
		
		return null;
	}
	
	public List<Long> getTaxaCobrancaEspecial(ContratoArt contrato, BigDecimal valorCalculo, Date dataBase) {
		
		List<Long> lista = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT T.FK_TIPO_TAXA_ART FROM ART_COBRANCA_ESPECIAL T                                        ");
		sql.append("  WHERE TO_CHAR(T.INICIO_VIGENCIA, 'YYYYMMDD') <= TO_CHAR(:dataBase, 'YYYYMMDD')               ");
		sql.append("    AND (TO_CHAR(T.FIM_VIGENCIA, 'YYYYMMDD') > TO_CHAR(:dataBase, 'YYYYMMDD')                  ");
		sql.append("         OR T.FIM_VIGENCIA IS NULL)                                                            ");
		if(contrato.temAtividades()) {
			sql.append("	AND (T.FK_ATIVIDADE_ART IN (:idsAtividade) OR T.FK_ATIVIDADE_ART IS NULL)              ");
		}else {
			sql.append("	AND T.FK_ATIVIDADE_ART IS NULL                                                         ");
		}
		if(contrato.temEspecificacoes()) {
			sql.append("	AND (T.FK_ESPECIFICACAO_ART IN (:idsEspecificacao) OR T.FK_ESPECIFICACAO_ART IS NULL)  ");
		}else {
			sql.append("	AND T.FK_ESPECIFICACAO_ART IS NULL                                                     ");
		}
		if(contrato.temComplementos()) {
			sql.append("	AND (T.FK_COMPLEMENTO_ART IN (:idsComplemento) OR T.FK_COMPLEMENTO_ART IS NULL)        ");
		}else {
			sql.append("	AND T.FK_COMPLEMENTO_ART IS NULL                                                       ");
		}
		if(contrato.temRamoArt()) {
			sql.append("	AND (T.FK_MODALIDADE = :idModalidade OR T.FK_MODALIDADE is null)                       ");
		}else {
			sql.append("	AND T.FK_MODALIDADE IS NULL                                                            ");
		}
		if(contrato.temQuantificacao()) {
			sql.append("	AND (T.FK_UNIDADE_MEDIDA = :idUnidadeMedida OR T.FK_UNIDADE_MEDIDA is null)            ");
			sql.append("	AND (T.QUANTIFICACAO >= :valorQuantificacao OR T.QUANTIFICACAO = 0)                    ");
		}else {
			sql.append("	AND T.FK_UNIDADE_MEDIDA is null                                                        ");
			sql.append("	AND T.QUANTIFICACAO =  0                                                               ");
		}
		sql.append("	AND ( T.VALOR >= :valorCalculo  OR T.VALOR = 0 )                                           ");
		sql.append("	ORDER BY T.CODIGO                                                                          ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("dataBase", dataBase);
			if(contrato.temAtividades()) query.setParameter("idsAtividade", contrato.getListCodigoAtividades());
			if(contrato.temEspecificacoes()) query.setParameter("idsEspecificacao", contrato.getListCodigoEspecificacoes());
			if(contrato.temComplementos()) query.setParameter("idsComplemento", contrato.getListCodigoComplementos());
			if(contrato.temRamoArt()) query.setParameter("idModalidade", contrato.getIdStringModalidade());
			if(contrato.temQuantificacao()) {
				query.setParameter("idUnidadeMedida", contrato.getQuantificacao().getUnidadeMedida());
				query.setParameter("valorQuantificacao", contrato.temQuantificacao() ? contrato.getQuantificacao().getValor() : 0);
			}
			query.setParameter("valorCalculo", valorCalculo);
			
			Iterator<?> it = query.getResultList().iterator();
			
			if (query.getResultList().size() > 0) {
				lista = new ArrayList<Long>();
				while (it.hasNext()) {
					
					BigDecimal valor = (BigDecimal) it.next();
					
					lista.add(valor.setScale(0, BigDecimal.ROUND_UP).longValueExact());
				}
			}
			
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDao || getTaxaCobrancaEspecial", StringUtil.convertObjectToJson(contrato + " - " + valorCalculo + " - " + dataBase), e);
		}
		
		return lista;
	}
	
	public List<ArtMinDto> getListRascunhos(PesquisaArtDto pesquisa) {
		List<ArtMinDto> lista = new ArrayList<ArtMinDto>();

		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT A.NUMERO, ");
		sql.append("   		TO_CHAR(A.DATA_CADASTRO, 'DD/MM/YYYY'), ");
		sql.append("   		N.DESCRICAO, ");
		sql.append("   		B.DESCRICAO AS SITUACAO ");
		sql.append("	FROM ART_ART A");
		
		
		joinPesquisaRascunhos(pesquisa, sql);
		queryPesquisaRascunhos(pesquisa, sql);
		
		sql.append("	ORDER BY A.DATA_CADASTRO DESC ");
		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idPessoa", pesquisa.getIdPessoa());
			
			Iterator<?> it = query.getResultList().iterator();
			
			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {
					
					Object[] result = (Object[]) it.next();
					ArtMinDto art = new ArtMinDto();

					art.setNumero(result[0] == null ? "" : result[0].toString());
					art.setDataCadastro(result[1] == null ? "" : result[1].toString());
					art.setNatureza(result[2] == null ? "" : result[2].toString());
					art.setSituacao(result[3] == null ? "" : result[3].toString());

					lista.add(art);
				}
			}

		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDao || getListRascunhos", StringUtil.convertObjectToJson(pesquisa), e);
		}

		return lista;
	}
	
	public List<ArtMinDto> getListModelos(PesquisaArtDto pesquisa) {
		List<ArtMinDto> lista = new ArrayList<ArtMinDto>();

		StringBuilder sql = new StringBuilder();		
		sql.append(" SELECT A.NUMERO, ");
		sql.append("   		TO_CHAR(A.DATA_CADASTRO, 'DD/MM/YYYY'), ");
		sql.append("   		N.DESCRICAO, ");
		sql.append("   		A.DESCRICAO_MODELO AS DESCRICAOMODELO ");
		sql.append(" FROM ART_ART A");
		sql.append(" JOIN ART_NATUREZA N ON (A.FK_NATUREZA_ART = N.CODIGO) ");
		sql.append(" WHERE 1 = 1 ");
		sql.append(" AND A.MODELO = 1 ");
		if(pesquisa.heProfissional()) {
			sql.append(" AND A.FK_PROFISSIONAL = :idPessoa ");
		} else {			
			sql.append(" AND A.FK_EMPRESA = :idPessoa ");
		}
		sql.append("	ORDER BY A.DATA_CADASTRO DESC ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idPessoa", pesquisa.getIdPessoa());
			Iterator<?> it = query.getResultList().iterator();
			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {
					Object[] result = (Object[]) it.next();
					ArtMinDto art = new ArtMinDto();
					art.setNumero(result[0] == null ? "" : result[0].toString());
					art.setDataCadastro(result[1] == null ? "" : result[1].toString());
					art.setNatureza(result[2] == null ? "" : result[2].toString());
					art.setDescricaoModelo(result[3] == null ? "" : result[3].toString());
					lista.add(art);
				}
			}
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDao || getListModelos", StringUtil.convertObjectToJson(pesquisa), e);
		}

		return lista;
	}

	public void atualizaAcaoOrdinaria(String numeroArt, boolean temAcaoJudicial) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ART_ART A                             ");
		sql.append("    SET A.ISACAO_ORDINARIA = :temAcaoJudicial ");
		sql.append("  WHERE A.NUMERO = :numeroArt                    ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("temAcaoJudicial", temAcaoJudicial);
			query.setParameter("numeroArt", numeroArt);
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDao || atualizaAcaoOrdinaria", StringUtil.convertObjectToJson(numeroArt + " - " + temAcaoJudicial), e);
		}
	}
	
	public boolean atualizaTaxaMinima(String numeroArt, boolean taxaMinima) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ART_ART A                      ");
		sql.append("     SET A.TAXA_MINIMA = :taxaMinima   ");
		sql.append("	   WHERE A.NUMERO = :numero        ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("taxaMinima", taxaMinima);
			query.setParameter("numero", numeroArt);
			
			query.executeUpdate();
			
			return taxaMinima;
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDao || atualizaTaxaMinima", StringUtil.convertObjectToJson(numeroArt + " - " + taxaMinima), e);
		}
		return false;
	}

	public boolean atualizaMultiplaMensal(String numeroArt, boolean ehMultiplaMensal) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ART_ART A                               ");
		sql.append("    SET A.MULTIPLA_MENSAL = :ehMultiplaMensal   ");
		sql.append("  WHERE A.NUMERO = :numero                      ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("ehMultiplaMensal", ehMultiplaMensal);
			query.setParameter("numero", numeroArt);
			
			query.executeUpdate();
			
			return ehMultiplaMensal;
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDao || atualizaMultiplaMensal", StringUtil.convertObjectToJson(numeroArt + " - " + ehMultiplaMensal), e);
		}
		
		return false;
	}
	
	public boolean atualizaForneceConcreto(String numeroArt, boolean forneceConcreto) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ART_ART A                               ");
		sql.append("    SET A.FORNECE_CONCRETO = :forneceConcreto   ");
		sql.append("  WHERE A.NUMERO = :numero                      ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("forneceConcreto", forneceConcreto);
			query.setParameter("numero", numeroArt);
			
			query.executeUpdate();
			
			return forneceConcreto;
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDao || atualizaForneceConcreto", StringUtil.convertObjectToJson(numeroArt + " - " + forneceConcreto), e);
		}
		
		return false;
	}

	public void atualizaValorReceberETipoTaxa(Art art) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ART_ART A                         ");
		sql.append("    SET A.VALOR_RECEBER  = :valorReceber  ");
		sql.append("    , A.FK_ART_TIPO_TAXA = :tipoTaxa      ");
		sql.append("  WHERE A.NUMERO = :numero                ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("valorReceber", art.getValorReceber());
			query.setParameter("tipoTaxa", art.getTipoTaxa());
			query.setParameter("numero", art.getNumero());
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDao || atualizaValorReceberETipoTaxa", StringUtil.convertObjectToJson(art), e);
		}
	}
	
    private void joinPesquisaRascunhos(PesquisaArtDto pesquisa, StringBuilder sql) {
		
    	sql.append(" JOIN ART_NATUREZA N ON (A.FK_NATUREZA_ART = N.CODIGO) ");
		sql.append(" JOIN ART_BAIXA B ON (A.FK_BAIXA = B.CODIGO) ");
		
	}
	private void queryPesquisaRascunhos(PesquisaArtDto pesquisa, StringBuilder sql) {
		
		sql.append(" WHERE 1 = 1 ");

		sql.append(" 	  AND (A.CANCELADA IS NULL OR A.CANCELADA = 0) ");
		sql.append(" 	  AND A.FINALIZADA  = 0                        ");
		sql.append(" 	  AND ( A.MODELO = 0 OR A.MODELO IS NULL )     ");
		
		if(pesquisa.heProfissional()) {
			
			sql.append(" AND A.FK_PROFISSIONAL = :idPessoa ");
			
		} else {			
			sql.append(" AND A.FK_EMPRESA = :idPessoa ");
		}
		
	}

	public BigDecimal getValorTotalDosContratosPelaTaxaMultipla(String numeroArt, TipoTaxaArtEnum taxaMultipla, Date database) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT SUM(VALOR) FROM                                                                                   ");
		sql.append("  (SELECT DISTINCT C.CODIGO AS CODIGO, T.VALOR AS VALOR from corporativo.ART_TAXA T                       ");
		sql.append("    JOIN CORPORATIVO.ART_CONTRATO C ON (C.FK_ART = :numeroArt)                                            ");
		sql.append("    WHERE T.FK_TIPO_TAXA = :tipoTaxa                                                                      ");
		sql.append("	AND TO_CHAR(T.INICIO_VIGENCIA, 'YYYYMMDD') <= TO_CHAR(:database, 'YYYYMMDD')                          ");
		sql.append("	AND (TO_CHAR(T.FIM_VIGENCIA, 'YYYYMMDD') >= TO_CHAR(:database, 'YYYYMMDD') OR T.FIM_VIGENCIA IS NULL) ");
		sql.append("	AND T.FAIXA_INICIAL <= C.VALOR_CONTRATO                                                               ");
		sql.append("	AND (T.FAIXA_FINAL >= C.VALOR_CONTRATO OR T.FAIXA_FINAL IS NULL))                                     ");
		

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("numeroArt", numeroArt);
			query.setParameter("database", database);
			query.setParameter("tipoTaxa", taxaMultipla.getId());
			query.setMaxResults(1);
			
			BigDecimal valor = (BigDecimal) query.getSingleResult();
			
			return valor;
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDao || getValorTotalDosContratosPelaTaxaMultipla", StringUtil.convertObjectToJson(numeroArt+" - "+taxaMultipla.getId()+" - "+database), e);
		}
		
		return null;
		
	}

	public BigDecimal getValorTotalDosContratosPelaFaixaETaxaMultipla(String numeroArt, TipoTaxaArtEnum taxaMultipla, Date database, long faixa) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT SUM(VALOR) FROM                                                                                   ");
		sql.append("  (SELECT DISTINCT C.CODIGO AS CODIGO, T.VALOR AS VALOR from corporativo.ART_TAXA T                       ");
		sql.append("    JOIN CORPORATIVO.ART_CONTRATO C ON (C.FK_ART = :numeroArt)                                            ");
		sql.append("    WHERE T.FK_TIPO_TAXA = :tipoTaxa                                                                      ");
		sql.append("	AND TO_CHAR(T.INICIO_VIGENCIA, 'YYYYMMDD') <= TO_CHAR(:database, 'YYYYMMDD')                          ");
		sql.append("	AND (TO_CHAR(T.FIM_VIGENCIA, 'YYYYMMDD') >= TO_CHAR(:database, 'YYYYMMDD') OR T.FIM_VIGENCIA IS NULL) ");
		sql.append("	AND T.FAIXA = :faixa)                                                                                 ");
		

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("numeroArt", numeroArt);
			query.setParameter("database", database);
			query.setParameter("tipoTaxa", taxaMultipla.getId());
			query.setParameter("faixa", faixa);
			query.setMaxResults(1);
			
			BigDecimal valor = (BigDecimal) query.getSingleResult();
			
			return valor;
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDao || getValorTotalDosContratosPelaFaixaETaxaMultipla", StringUtil.convertObjectToJson(numeroArt+" - "+taxaMultipla.getId()+" - "+database + " - " + faixa), e);
		}
		
		return null;
	}

	public BigDecimal getValorTotalDosContratosExcetoDezPrimeiros(String numeroArt, Date database) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT SUM(VALOR) FROM                                                                                    ");
		sql.append("  (SELECT DISTINCT C.CODIGO AS CODIGO, T.VALOR AS VALOR from corporativo.ART_TAXA T                        ");
		sql.append("    JOIN CORPORATIVO.ART_CONTRATO C ON (C.FK_ART = :numeroArt)                                             ");
		sql.append("    WHERE T.FK_TIPO_TAXA = :tipoTaxa                                                                       ");
		sql.append("	AND TO_CHAR(T.INICIO_VIGENCIA, 'YYYYMMDD') <= TO_CHAR(:database, 'YYYYMMDD')                           ");
		sql.append("	AND (TO_CHAR(T.FIM_VIGENCIA, 'YYYYMMDD') >= TO_CHAR(:database, 'YYYYMMDD') OR T.FIM_VIGENCIA IS NULL)  ");
		sql.append("	AND T.FAIXA_INICIAL <= C.VALOR_CONTRATO                                                                ");
		sql.append("	AND (T.FAIXA_FINAL >= C.VALOR_CONTRATO OR T.FAIXA_FINAL IS NULL)                                       ");
		sql.append("	AND C.SEQUENCIAL > 10)                                                                                 ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("numeroArt", numeroArt);
			query.setParameter("database", database);
			query.setParameter("tipoTaxa", TipoTaxaArtEnum.VALOR_CONTRATO_SERVICO.getId());
			query.setMaxResults(1);
			
			BigDecimal valor = (BigDecimal) query.getSingleResult();
			
			return valor;
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDao || getValorTotalDosContratosExcetoDezPrimeiros", StringUtil.convertObjectToJson(numeroArt), e);
		}
		
		return null;
	}

	public BigDecimal getValorArt(String numeroArt) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT A.VALOR_RECEBER FROM ART_ART A    ");
		sql.append("  WHERE A.NUMERO = :numeroArt             ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("numeroArt", numeroArt);
			
			return (BigDecimal) query.getSingleResult();
						
		} catch (NoResultException e) {
			return new BigDecimal("0");
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDao || getValorArt", StringUtil.convertObjectToJson(numeroArt), e);
		}
		
		return new BigDecimal("0");
	}

	public boolean validarSeNumeroArtPrincipalEhValido(DomainGenericDto dto) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT A.NUMERO FROM ART_ART A                ");
		sql.append("  WHERE A.NUMERO = :numeroArt                  ");
		sql.append("  AND (A.CANCELADA is null OR A.CANCELADA = 0) ");
		if (dto.getId().equals(2L)) {
			sql.append("  AND A.VALOR_PAGO IS NOT NULL                 ");
			sql.append("  AND A.NUMERO NOT IN (SELECT FK_ART           ");
			sql.append("                FROM ART_LIBERACAO             ");
			sql.append("   WHERE DATA_FINAL_LIBERACAO IS NOT NULL      ");
			sql.append("     AND FK_ART = :numeroArt)                  ");
		}
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("numeroArt", dto.getNome());
			if(query.getResultList().size() > 0) {
				return true;
			}
						
		} catch (NoResultException e) {
			return false;
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDao || validarSeNumeroArtPrincipalEhValido", StringUtil.convertObjectToJson(dto), e);
		}
		return false;
	}
	
	public boolean validarSeNumeroArtParticipacaoTecnicaEhValido(DomainGenericDto dto) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT A.NUMERO FROM ART_ART A                                                                            ");
		sql.append("  WHERE A.NUMERO = :numeroArtParticipacaoTecnica                                                           ");
		sql.append("  AND (A.CANCELADA is null OR A.CANCELADA = 0)                                                             ");
		sql.append("  AND A.FK_PROFISSIONAL NOT IN (SELECT ART.FK_PROFISSIONAL FROM ART_ART ART WHERE ART.NUMERO = :numeroArt) ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("numeroArtParticipacaoTecnica", dto.getNome());
			query.setParameter("numeroArt", dto.getNumero());
			if(query.getResultList().size() > 0) {
				return true;
			}
						
		} catch (NoResultException e) {
			return false;
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDao || validarSeNumeroArtParticipacaoTecnicaEhValido", StringUtil.convertObjectToJson(dto), e);
		}
		return false;
	}

	public boolean atualizaExigencia(String numeroArt, boolean possuiExigencia) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ART_ART A                               ");
		sql.append("    SET A.POSSUI_EXIGENCIA = :possuiExigencia   ");
		sql.append("  WHERE A.NUMERO = :numeroArt                   ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("possuiExigencia", possuiExigencia);
			query.setParameter("numeroArt", numeroArt);
			
			query.executeUpdate();
			
			return possuiExigencia;
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDao || atualizaExigencia", StringUtil.convertObjectToJson(numeroArt + " - " + possuiExigencia), e);
		}
		
		return false;
	}

	public void baixarArt(Long idBaixa, String numeroArt, String motivoBaixa) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ART_ART A                              ");
		sql.append("    SET A.BAIXADA = 1,                         ");
		sql.append("        A.FK_BAIXA = :idBaixa,                 ");
		sql.append("        A.DATA_BAIXA = :dataBaixa,             ");
		sql.append("        A.MOTIVO_BAIXA_OUTROS = :motivoBaixa   ");
		sql.append("  WHERE A.NUMERO = :numeroArt                  ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("dataBaixa", new Date());
			query.setParameter("motivoBaixa", motivoBaixa);
			query.setParameter("numeroArt", numeroArt);
			query.setParameter("idBaixa", idBaixa);
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDao || baixarArt", StringUtil.convertObjectToJson(idBaixa + "--" + numeroArt + "--" + motivoBaixa), e);
		}
	}

	/** Método para substituir a ART principal das ARTs vinculadas a ART que está sendo substituída.
	 *  Apenas a ART substituta deve manter a substituída como principal para manter registrado qual ART está sendo substituída
	 * @param art
	 */
	public void substituirNumeroArtPrincipalDasVinculadasAArtSubstituida(Art art) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ART_ART A                                     ");
		sql.append("    SET A.ART_PRINCIPAL_NUMERO = :numeroArt,          ");
		sql.append("        A.FK_ART_PRINCIPAL = :numeroArt               ");
		sql.append("  WHERE A.ART_PRINCIPAL_NUMERO = :numeroArtPrincipal  ");
		sql.append("    AND A.NUMERO <> :numeroArt                        ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("numeroArt", art.getNumero());
			query.setParameter("numeroArtPrincipal", art.getNumeroARTPrincipal());
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDao || substituirNumeroArtPrincipalDasVinculadasAArtSubstituida", StringUtil.convertObjectToJson(art), e);
		}
	}
	
	/** Método para desfazer a substituição da ART principal das ARTs vinculadas a ART que está sendo substituída.
	 *  Executado apenas em caso de erro na finalização da ART
	 * @param art
	 */
	public void desfazerSubstituicaoNumeroArtPrincipalDasVinculadasAArtSubstituida(Art art) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ART_ART A                                     ");
		sql.append("    SET A.ART_PRINCIPAL_NUMERO = :numeroArtPrincipal, ");
		sql.append("        A.FK_ART_PRINCIPAL = :numeroArtPrincipal      ");
		sql.append("  WHERE A.ART_PRINCIPAL_NUMERO = :numeroArt           ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("numeroArt", art.getNumero());
			query.setParameter("numeroArtPrincipal", art.getNumeroARTPrincipal());
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDao || desfazerSubstituicaoNumeroArtPrincipalDasVinculadasAArtSubstituida", StringUtil.convertObjectToJson(art), e);
		}
	}

	public List<String> getListNumeroArtsVinculadas(String numeroArtPrincipal) {
		
		List<String> listaVinculadas = new ArrayList<String>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT A.NUMERO FROM ART_ART A             ");
		sql.append("  WHERE A.ART_PRINCIPAL_NUMERO = :numeroArtPrincipal ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("numeroArtPrincipal", numeroArtPrincipal);
			
			Iterator<?> it = query.getResultList().iterator();
			
			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {
					
					String result = (String) it.next();

					listaVinculadas.add(result == null ? "" : result.toString());
				}
			}
						
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDao || getListNumeroArtsVinculadas", StringUtil.convertObjectToJson(numeroArtPrincipal), e);
		}
		
		return listaVinculadas;
	}


	public List<ArtExigenciaDto> getListExigencias(PesquisaArtDto pesquisa) {
		List<ArtExigenciaDto> lista = new ArrayList<ArtExigenciaDto>();

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT  AE.CODIGO, ");
		sql.append(" 		 AE.DESCRICAO, ");
		sql.append(" 		 AEA.MOTIVO ||' '||AEA.CONTEUDO , ");
		sql.append(" 		 ATA.DESCRICAO as descricao2 , ");
		sql.append(" 		 TO_CHAR(AEA.DATA, 'DD/MM/YYYY') , ");
		sql.append(" 		 AEA.DESCRICAO_ANALISE , ");
		sql.append(" 		 ASL.DESCRICAO as descricao3, ");
		sql.append(" 		 TO_CHAR(AEA.DATA_LIBERACAO, 'DD/MM/YYYY') , ");
		sql.append(" 		 AEA.DESCRICAO_LIBERACAO ");
		sql.append(" FROM ART_EXIGENCIA_ART AEA ");
		sql.append(" JOIN ART_EXIGENCIA AE ON AE.CODIGO = AEA.FK_EXIGENCIA ");
		sql.append(" JOIN ART_TIPO_ACAO ATA ON ATA.CODIGO = AEA.FK_TIPO_ACAO_ART ");
		sql.append(" JOIN ART_SITUACAO_LIBERACAO ASL ON ASL.CODIGO = AEA.FK_SITUACAO_LIBERACAO ");
		sql.append(" WHERE AEA.FK_ART = :numeroArt ");
		
		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("numeroArt", pesquisa.getNumeroArt());
			
			Iterator<?> it = query.getResultList().iterator();
			
			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {
					
					Object[] result = (Object[]) it.next();
					ArtExigenciaDto art = new ArtExigenciaDto();

					art.setCodigo(result[0] == null ? "" : result[0].toString());
					art.setDescricao(result[1] == null ? "" : result[1].toString());
					art.setConteudoGerador(result[2] == null ? "" : result[2].toString());
					art.setStatusTipoAcao(result[3] == null ? "" : result[3].toString());
					art.setData(result[4] == null ? "" : result[4].toString());
					art.setDescricaoAnalise(result[5] == null ? "" : result[5].toString());
					art.setSituacaoLiberacao(result[6] == null ? "" : result[6].toString());
					art.setDataDaLiberacao(result[7] == null ? "" : result[7].toString());
					art.setDescricaoLiberacao(result[8] == null ? "" : result[8].toString());

					lista.add(art);
				}
			}

		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDao || getListExigencias", StringUtil.convertObjectToJson(pesquisa), e);
		}

		return lista;
	}

	public void atualizaNumeroArtParticipacaoTecnica(DomainGenericDto dto) {
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ART_ART A                                    ");
		sql.append("    SET A.FK_ART_PARTICIPACAO = :numeroArtParticipacaoTecnica ");
		if("-".equals(dto.getNome())) sql.append(" ,A.PRIMEIRA_PARTICIPACAO_TECNICA = :primeiraParticipacaoTecnica ");
		sql.append("  WHERE A.NUMERO = :numero                           ");
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("numero", dto.getNumero());
			query.setParameter("numeroArtParticipacaoTecnica", "-".equals(dto.getNome()) ? "" : dto.getNome());
			if("-".equals(dto.getNome())) query.setParameter("primeiraParticipacaoTecnica", "");
			query.executeUpdate();
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDao || atualizaNumeroArtParticipacaoTecnica", StringUtil.convertObjectToJson(dto), e);
		}
	}

	public DomainGenericDto atualizaParticipacaoTecnica(DomainGenericDto dto) {
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ART_ART A                ");
		sql.append("    SET A.FK_PARTICIPACAO_TECNICA = :idTipo  ");	
		sql.append("  WHERE A.NUMERO = :numero       ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idTipo", dto.getId());
			query.setParameter("numero", dto.getNumero());
						
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDao || atualizaParticipacaoTecnica", StringUtil.convertObjectToJson(dto), e);
		}
		return dto;
	}

	public void excluiArt(String numeroArt) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" DELETE FROM ART_ART A        ");
		sql.append("  WHERE A.NUMERO = :numeroArt ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("numeroArt", numeroArt);
			// 21/09
			query.executeUpdate();
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDao || excluiArt", StringUtil.convertObjectToJson(numeroArt), e);
		}
	}

	public void aplicarModeloArt(String numeroArtModelo, String novoNumeroArt) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" INSERT INTO ART_ART (NUMERO,     ");
		
		
		sql.append("  ACESSIBILIDADE, ART_PRINCIPAL_NUMERO, ASS_CONTRATADO, ATUAL, BAIXADA,  ");
		sql.append("  CANCELADA, CERTIFICADA, DATA_BAIXA, DATA_CADASTRO, DATA_EMAIL_COBRANCA, DATA_PAGAMENTO,  ");
		sql.append("  DATA_REPROCESSAMENTO_EXIGENCIA, DATA_ULTIMA_ALTERACAO, DESCRICAO_FATO_GERADOR, FINALIZADA,  ");
		sql.append("  FK_ART_FORMA, FK_ART_PARTICIPACAO, FK_ART_PRINCIPAL, FK_ART_TIPO_TAXA, FK_ART_VINCULO_CONTRATUAL,  ");
		sql.append("  FK_BAIXA, FK_CODIGO_LIBERACAO, FK_EMPRESA, FK_ENTIDADE_CLASSE, FK_FATO_GERADOR_ART, FK_FORMA_REGISTRO,  ");
		sql.append("  FK_FUNCIONARIO_ALTERACAO, FK_FUNCIONARIO_CADASTRO, FK_NATUREZA_ART, FK_PARTICIPACAO_TECNICA,  ");
		sql.append("  FK_PESSOA_OUTESTPAIS, FK_PROFISSIONAL, FK_PROTOCOLO, FK_PROTOCOLO_DEVOLUCAO, FK_TIPO_ART,  ");
		sql.append("  FORNECE_CONCRETO, HA_EMPRESA_VINCULADA, HA_PROFISSIONAL_CORESPONSAVEL, ISACAO_ORDINARIA,  ");
		sql.append("  ISEMPRESA, ISEMPRESALIBERACAO, ISEMPRESASTATUSLIBERACAO, ISOEPAISLIBERACAO,	ISONLINE, ISOUTESTPAIS,  ");
		sql.append("  ISOUTESTPAISSTATUSLIBERACAO, ISRESGATE, IS1025, LIBERADA, MIGRADO, MOTIVO_BAIXA_OUTROS,	MULTIPLA_MENSAL,  ");
		sql.append("  PAGOU_NO_PRAZO, POSSUI_EXIGENCIA, QT_CONTRATOS, TAXA_MINIMA, TERMO_ADITIVO,	USUARIO_CADASTRO,  ");
		sql.append("  VALOR_PAGO, VALOR_RECEBER, WS)  ");
		
		sql.append(" (SELECT :novoNumeroArt,  ");
		
		sql.append("  A.ACESSIBILIDADE, A.ART_PRINCIPAL_NUMERO, A.ASS_CONTRATADO, A.ATUAL, A.BAIXADA,  ");
		sql.append("  CANCELADA, CERTIFICADA, DATA_BAIXA, SYSDATE, DATA_EMAIL_COBRANCA, DATA_PAGAMENTO,  ");
		sql.append("  DATA_REPROCESSAMENTO_EXIGENCIA, DATA_ULTIMA_ALTERACAO, DESCRICAO_FATO_GERADOR, FINALIZADA,  ");
		sql.append("  FK_ART_FORMA, FK_ART_PARTICIPACAO, FK_ART_PRINCIPAL, FK_ART_TIPO_TAXA, FK_ART_VINCULO_CONTRATUAL,  ");
		sql.append("  FK_BAIXA, FK_CODIGO_LIBERACAO, FK_EMPRESA, FK_ENTIDADE_CLASSE, FK_FATO_GERADOR_ART, FK_FORMA_REGISTRO,  ");
		sql.append("  FK_FUNCIONARIO_ALTERACAO, FK_FUNCIONARIO_CADASTRO, FK_NATUREZA_ART, FK_PARTICIPACAO_TECNICA,  ");
		sql.append("  FK_PESSOA_OUTESTPAIS, FK_PROFISSIONAL, FK_PROTOCOLO, FK_PROTOCOLO_DEVOLUCAO, FK_TIPO_ART,  ");
		sql.append("  FORNECE_CONCRETO, HA_EMPRESA_VINCULADA, HA_PROFISSIONAL_CORESPONSAVEL, ISACAO_ORDINARIA,  ");
		sql.append("  ISEMPRESA, ISEMPRESALIBERACAO, ISEMPRESASTATUSLIBERACAO, ISOEPAISLIBERACAO,	ISONLINE, ISOUTESTPAIS,  ");
		sql.append("  ISOUTESTPAISSTATUSLIBERACAO, ISRESGATE, IS1025, LIBERADA, MIGRADO, MOTIVO_BAIXA_OUTROS,	MULTIPLA_MENSAL,  ");
		sql.append("  PAGOU_NO_PRAZO, POSSUI_EXIGENCIA, QT_CONTRATOS, TAXA_MINIMA, TERMO_ADITIVO,	USUARIO_CADASTRO,  ");
		sql.append("  VALOR_PAGO, VALOR_RECEBER, WS  ");
		
		sql.append("    FROM ART_ART A        ");
		sql.append("   WHERE A.NUMERO = :numeroArtModelo)                      ");
		
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("numeroArtModelo", numeroArtModelo);
			query.setParameter("novoNumeroArt", novoNumeroArt);
			
			query.executeUpdate();
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDao || aplicarModeloArt", StringUtil.convertObjectToJson(numeroArtModelo), e);
		}
	}
	
	public void atualizaPrimeiraParticipacaoTecnica(DomainGenericDto dto) {
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ART_ART A                                    ");
		sql.append("    SET A.PRIMEIRA_PARTICIPACAO_TECNICA = :primeiraParticipacaoTecnica ");
		sql.append("  WHERE A.NUMERO = :numero                           ");
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("numero", dto.getNumero());
			query.setParameter("primeiraParticipacaoTecnica", dto.isChecked());
			query.executeUpdate();
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDao || atualizaPrimeiraParticipacaoTecnica", StringUtil.convertObjectToJson(dto), e);
		}
	}

	public void atualizaAcessibilidade(DomainGenericDto dto) {
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ART_ART A                           ");
		sql.append("    SET A.ACESSIBILIDADE = :acessibilidade  ");
		sql.append("  WHERE A.NUMERO = :numero                  ");
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("numero", dto.getNumero());
			query.setParameter("acessibilidade", dto.isChecked());
			query.executeUpdate();
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDao || atualizaAcessibilidade", StringUtil.convertObjectToJson(dto), e);
		}
	}

	public List<ArtDto> getArtsAcervoTecnico(UserFrontDto dto) {
			
			List<ArtDto> list = new ArrayList<ArtDto>();
			
			StringBuilder sql = new StringBuilder();
			
			sql.append("Select A.Numero, A.Data_Cadastro                                                       ");
			sql.append("From Corporativo.Art_Art A                                                             ");
			sql.append("	Where A.Fk_Profissional = :idPessoa                                                ");
			sql.append("    And A.Data_Pagamento Is Not Null                                                   ");
			sql.append("    And (Exists (Select * From Corporativo.Art_Liberacao L                             ");
			sql.append("    	Where L.Fk_Art = A.Numero                                                      ");
			sql.append("        And L.Fk_Situacao_Liberacao In (2, 3)                                          ");
			sql.append("        And L.Data_Liberacao >= To_Date('01/03/2003','dd/mm/yyyy')                     ");
			sql.append("        And L.Data_Final_Liberacao Is Null)                                            ");
			sql.append("		Or A.Isonline = 1)                                                             ");
			sql.append("	And A.Fk_Baixa In (1, 3, 8 )                                                       ");
			sql.append("    And A.Liberada = 1                                                                 ");
			sql.append("    And Not Exists (Select * From Art_Exigencia_Art E                                  ");
			sql.append("    	Where E.Fk_Art = A.Numero                                                      ");
			sql.append("    	And (E.Fk_Situacao_Liberacao != 1 Or E.Fk_Situacao_Liberacao Is Null))         ");
			sql.append("    	Order By A.Data_Cadastro                                                       ");
			
			try {
				Query query = em.createNativeQuery(sql.toString());
				query.setParameter("idPessoa", dto.getIdPessoa());
				
				Iterator<?> it = query.getResultList().iterator();
				
				if (query.getResultList().size() > 0) {
					while (it.hasNext()) {
						
						Object[] result = (Object[]) it.next();
						ArtDto art = new ArtDto();
						art.setNumero(result[0].toString());
						art.setDataCadastroFormatada(result[1].toString());
						list.add(art);
					}
				}
			} catch (Throwable e) {
				httpGoApi.geraLog("EmpresaDao || getEmpresasCurriculo",StringUtil.convertObjectToJson(dto), e);
			}
			return list;
		}
	
}