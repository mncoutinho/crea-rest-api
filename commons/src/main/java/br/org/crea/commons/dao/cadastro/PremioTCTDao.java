package br.org.crea.commons.dao.cadastro;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.cadastro.ParticipantePremioTCT;
import br.org.crea.commons.models.cadastro.PremioTCT;
import br.org.crea.commons.models.cadastro.dtos.ParticipantePremioTCTDto;
import br.org.crea.commons.models.cadastro.dtos.premio.PremioTCTDto;
import br.org.crea.commons.models.cadastro.dtos.premio.RelPremioQuantitativoDto;
import br.org.crea.commons.models.corporativo.pessoa.PessoaFisica;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless	
public class PremioTCTDao extends GenericDao<PremioTCT, Serializable>  {

	@Inject HttpClientGoApi httpGoApi;
		
		
	public PremioTCTDao() {
		super(PremioTCT.class);
	}

	public PremioTCT getPremio(PremioTCTDto dto) {
		
		PremioTCT premio = new PremioTCT();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT P                           				     ");
		sql.append(" FROM PremioTCT P										 ");
		sql.append("   LEFT JOIN P.arquivo                                   ");
		sql.append("   LEFT JOIN P.arquivoResumo      						 ");
		sql.append("   LEFT JOIN P.arquivoTermo								 ");
		sql.append("   LEFT JOIN P.pessoa                                    ");
		sql.append("  WHERE P.nivel = :nivel                                 ");
		sql.append("    AND P.ano = :ano                                     ");
		sql.append("    AND P.idInstituicaoEnsinoCouchDb = :idInstituicao    ");
		if (dto.getCurso().getIdString() != null) {
			sql.append("    AND P.nomeCurso = :nomeCurso                     ");
		} else {
			sql.append("    AND P.protocoloCurso = :protocoloCurso           ");
		}

		try {
			TypedQuery<PremioTCT> query = em.createQuery(sql.toString(), PremioTCT.class);
			query.setParameter("idInstituicao", dto.getInstituicao().getIdString());
			if (dto.getCurso().getIdString() != null) {
				query.setParameter("nomeCurso", dto.getCurso().getNome());
			} else {
				query.setParameter("protocoloCurso", dto.getProtocoloCurso());
			}
			query.setParameter("nivel", dto.getNivel());
			query.setParameter("ano", dto.getAno());
			
			premio = query.getSingleResult();
			
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("PremioTCTDao || getPremio", StringUtil.convertObjectToJson(dto), e);
		}
		
		return premio;
	}
	
	public PremioTCT getPremioNaoFinalizado(PremioTCTDto dto) {
		
		PremioTCT premio = new PremioTCT();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT P FROM PremioTCT P                               ");
		sql.append("   LEFT JOIN P.arquivo                                   ");
		sql.append("   LEFT JOIN P.pessoa                                    ");
		sql.append("  WHERE P.nivel = :nivel                                 ");
		sql.append("    AND P.ano = :ano                                     ");
		sql.append("    AND P.idInstituicaoEnsinoCouchDb = :idInstituicao    ");
		sql.append("    AND (P.status IS NULL OR P.status = 0)               ");
		if (dto.getCurso().getIdString() != null) {
			sql.append("    AND P.nomeCurso = :nomeCurso                     ");
		} else {
			sql.append("    AND P.protocoloCurso = :protocoloCurso           ");
		}

		try {
			TypedQuery<PremioTCT> query = em.createQuery(sql.toString(), PremioTCT.class);
			query.setParameter("idInstituicao", dto.getInstituicao().getIdString());
			if (dto.getCurso().getIdString() != null) {
				query.setParameter("nomeCurso", dto.getCurso().getNome());
			} else {
				query.setParameter("protocoloCurso", dto.getProtocoloCurso());
			}
			query.setParameter("nivel", dto.getNivel());
			query.setParameter("ano", dto.getAno());
			
			premio = query.getSingleResult();
			
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("PremioTCTDao || getPremio", StringUtil.convertObjectToJson(dto), e);
		}
		
		return premio;
	}
	
	public List<PremioTCT> getPremios(PremioTCTDto dto) {
		
		List<PremioTCT> listaPremios = new ArrayList<PremioTCT>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT P FROM PremioTCT P                               ");
		sql.append("   LEFT JOIN P.arquivo                                   ");
		sql.append("   LEFT JOIN P.pessoa                                    ");
		sql.append("  WHERE P.nivel = :nivel                                 ");
		sql.append("    AND P.ano = :ano                                     ");
		sql.append("    AND P.idInstituicaoEnsinoCouchDb = :idInstituicao    ");
		if (dto.getCurso().getIdString() != null) {
			sql.append("    AND P.nomeCurso = :nomeCurso                     ");
		} else {
			sql.append("    AND P.protocoloCurso = :protocoloCurso           ");
		}

		try {
			TypedQuery<PremioTCT> query = em.createQuery(sql.toString(), PremioTCT.class);
			query.setParameter("idInstituicao", dto.getInstituicao().getIdString());
			if (dto.getCurso().getIdString() != null) {
				query.setParameter("nomeCurso", dto.getCurso().getNome());
			} else {
				query.setParameter("protocoloCurso", dto.getProtocoloCurso());
			}
			query.setParameter("nivel", dto.getNivel());
			query.setParameter("ano", dto.getAno());
			
			listaPremios = query.getResultList();
			
		} catch (NoResultException e) {
			return listaPremios;
		} catch (Throwable e) {
			httpGoApi.geraLog("PremioTCTDao || getPremios", StringUtil.convertObjectToJson(dto), e);
		}
		
		return listaPremios;
	}
	
	public List<PremioTCT> getPremiosFinalizados(PremioTCTDto dto) {
		
		List<PremioTCT> listaPremios = new ArrayList<PremioTCT>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT P FROM PremioTCT P  ");
		sql.append("  WHERE P.ano = :ano        ");
		sql.append("    AND P.status = 1        ");

		try {
			TypedQuery<PremioTCT> query = em.createQuery(sql.toString(), PremioTCT.class);
			query.setParameter("ano", dto.getAno());
			
			listaPremios = query.getResultList();
			
		} catch (NoResultException e) {
			return listaPremios;
		} catch (Throwable e) {
			httpGoApi.geraLog("PremioTCTDao || getPremiosFinalizados", StringUtil.convertObjectToJson(dto), e);
		}
		
		return listaPremios;
	}
	
	public boolean possuiDezIndicacoesFinalizadas(PremioTCTDto dto) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(P.ID) FROM CAD_PREMIO_TCT P         ");
		sql.append("  WHERE P.NIVEL = :nivel                          ");
		sql.append("    AND P.ANO = :ano                              ");
		sql.append("    AND P.ID_INST_COUCHDB = :idInstituicao        ");
		sql.append("    AND P.STATUS = 1                              ");
		if (dto.getCurso().getIdString() != null) {
			sql.append("    AND P.NOME_CURSO = :nomeCurso             ");
		} else {
			sql.append("    AND P.PROTOCOLO_CURSO = :protocoloCurso   ");
		}

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idInstituicao", dto.getInstituicao().getIdString());
			if (dto.getCurso().getIdString() != null) {
				query.setParameter("nomeCurso", dto.getCurso().getNome());
			} else {
				query.setParameter("protocoloCurso", dto.getProtocoloCurso());
			}
			query.setParameter("nivel", dto.getNivel());
			query.setParameter("ano", dto.getAno());
			
			BigDecimal valor = (BigDecimal) query.getSingleResult();
			
			return valor.compareTo(new BigDecimal(10)) >= 0;
			
		} catch (Throwable e) {
			httpGoApi.geraLog("PremioTCTDao || getPremios", StringUtil.convertObjectToJson(dto), e);
		}
		return false;		
	}
	public void  atualizaParticipante(ParticipantePremioTCTDto dto) {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE CAD_PARTICIPANTES_TCT Q");
		sql.append("SET   Q.PAPEL = :papel,");
		sql.append(" 	  Q.EMAIL = :email ");
		sql.append(" 	  Q.TELEFONE = :telefone");
		sql.append(" 	  Q.CELULAR = :celular");
		sql.append(" 	  Q.CAD_ENDERECO_FK :endereco");
		sql.append(" 	  Q.CAD_TITULO_FK :titulo");
		sql.append("WHERE Q.ID : id 	");
		sql.append("AND Q.FK_CAD_PREMIO :idPremio  ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("papel", dto.getPapel());
			query.setParameter("email", dto.getEmail());
			query.setParameter("telefone", dto.getTelefone());
			query.setParameter("celular", dto.getCelular());
			query.setParameter("endereco", dto.getIdEndereco());
			query.setParameter("titulo", dto.getIdTitulo());
			query.setParameter("id", dto.getId());
			query.setParameter("idPremio", dto.getIdPremio());
		}catch (Throwable e) {
			httpGoApi.geraLog("PremioTCTDao || atualizaParticipante", StringUtil.convertObjectToJson(dto) , e);
		}
	}


	public void atualizaIndicacao(PremioTCTDto dto) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE CAD_PREMIO_TCT P              ");
		sql.append("    SET P.TITULO_TRABALHO = :titulo,  ");
		sql.append("        P.NIVEL = :nivel              ");
		sql.append("  WHERE P.ID = :id                    ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			
			query.setParameter("id", dto.getId());
			query.setParameter("titulo", dto.getTitulo().toUpperCase());
			query.setParameter("nivel", dto.getNivel());
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("PremioTCTDao || atualizaIndicacao", StringUtil.convertObjectToJson(dto), e);
		}
	}
public void atualizaArquivoTermo(PremioTCTDto dto) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE CAD_PREMIO_TCT P");
		sql.append("   SET P.FK_ARQUIVO_TERMO = :idArquivoTermo");
		sql.append("  WHERE P.ID = :id");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("id", dto.getId());
			query.setParameter("idArquivoTermo", dto.getArquivoTermo().getId());
			
			query.executeUpdate();
		} catch (Throwable e) {
			httpGoApi.geraLog("PremioTCTDao || atualizaArquivoTermo", StringUtil.convertObjectToJson(dto), e);
		}
	}
	
	public void atualizaArquivoResumo(PremioTCTDto dto) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE CAD_PREMIO_TCT P						 ");
		sql.append("   SET P.FK_ARQUIVO_RESUMO = :idArquivoResumo");
		sql.append("  WHERE P.ID = :id							 ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("id", dto.getId());
			query.setParameter("idArquivoResumo", dto.getArquivoResumo().getId());
			
			query.executeUpdate();
		} catch (Throwable e) {
			httpGoApi.geraLog("PremioTCTDao || atualizaArquivoResumo", StringUtil.convertObjectToJson(dto), e);
		}
	}
	
	public void atualizaArquivo(PremioTCTDto dto) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE CAD_PREMIO_TCT P            ");
		sql.append("    SET P.FK_ARQUIVO = :idArquivo   ");
		sql.append("  WHERE P.ID = :id                  ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			
			query.setParameter("id", dto.getId());
			query.setParameter("idArquivo", dto.getArquivo().getId());
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("PremioTCTDao || atualizaArquivo", StringUtil.convertObjectToJson(dto), e);
		}
	}
	
public void deletaArquivoResumo(Long id) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE CAD_PREMIO_TCT P            ");
		sql.append("    SET P.FK_ARQUIVO_RESUMO = null  ");
		sql.append("  WHERE P.ID = :id                  ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			
			query.setParameter("id", id);
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("PremioTCTDao || deletaArquivo", StringUtil.convertObjectToJson(id), e);
		}
	}
public void deletaArquivoTermo(Long id) {
	
	StringBuilder sql = new StringBuilder();
	sql.append(" UPDATE CAD_PREMIO_TCT P            ");
	sql.append("    SET P.FK_ARQUIVO_TERMO = null   ");
	sql.append("  WHERE P.ID = :id                  ");

	try {
		Query query = em.createNativeQuery(sql.toString());
		
		query.setParameter("id", id);
		
		query.executeUpdate();
		
	} catch (Throwable e) {
		httpGoApi.geraLog("PremioTCTDao || deletaArquivo", StringUtil.convertObjectToJson(id), e);
	}
}

	public void deletaArquivo(Long id) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE CAD_PREMIO_TCT P            ");
		sql.append("    SET P.FK_ARQUIVO = null 		");
		sql.append("  WHERE P.ID = :id                  ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			
			query.setParameter("id", id);
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("PremioTCTDao || deletaArquivo", StringUtil.convertObjectToJson(id), e);
		}
	}


	public PremioTCT getPremioById(Long id) {
		
		PremioTCT premio = new PremioTCT();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT P FROM PremioTCT P       ");
		sql.append(" LEFT JOIN P.arquivo  ");
		sql.append(" LEFT JOIN P.arquivoResumo ");
		sql.append(" LEFT JOIN P.arquivoTermo ");
		sql.append(" LEFT JOIN P.pessoa  ");
		sql.append(" WHERE P.id = :id    ");		

		try {
			TypedQuery<PremioTCT> query = em.createQuery(sql.toString(), PremioTCT.class);
			query.setParameter("id", id);
			
			premio = query.getSingleResult();
			
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("PremioTCTDao || getPremioById", StringUtil.convertObjectToJson(id), e);
		}
		
		return premio;
	}
	
	public void atualizaAceiteEStatus(PremioTCTDto dto) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE CAD_PREMIO_TCT P            ");
		sql.append("    SET P.ACEITE_TERMO = :aceite, P.STATUS = 1 ");
		sql.append("  WHERE P.ID = :id                  ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			
			query.setParameter("aceite", dto.getAceite());
			query.setParameter("id", dto.getId());
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("PremioTCTDao || atualizaAceiteEStatus", StringUtil.convertObjectToJson(dto), e);
		}
	}

	public List<PremioTCT> pesquisa(PremioTCTDto dto) {
	
		List<PremioTCT> listPremios = new ArrayList<PremioTCT>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT P FROM PremioTCT P            ");
		sql.append("  WHERE 1=1                           ");
		
		if(dto.temInstituicao()) {
			sql.append("  AND P.idInstituicaoEnsinoCouchDb = :idInstituicao  ");
		}
		if(dto.temCampus()) {
			sql.append("  AND  P.idCampusCouchDb  = :idCampus  ");
		}		
		if(dto.temCurso()) {
			sql.append("  AND  P.idCursoCouchDb  = :idCurso  ");
		}
		if(dto.temPessoa()) {
			sql.append("  AND  P.pessoa.id  = :idPessoa  ");
		}
		if(dto.temAno()) {
			sql.append("  AND  P.ano  = :ano             ");
		}
		
		sql.append("  ORDER BY P.dataEnvio DESC           ");

		try {
			TypedQuery<PremioTCT> query = em.createQuery(sql.toString(), PremioTCT.class);
			if(dto.temInstituicao()) {
				query.setParameter("idInstituicao", dto.getInstituicao().getIdString());
			}
			if(dto.temCampus()) {
				query.setParameter("idCampus", dto.getCampus().getIdString());
			}
			if(dto.temCurso()) {
				query.setParameter("idCurso", dto.getCurso().getIdString());
			}
			if(dto.temPessoa()) {
				query.setParameter("idPessoa", dto.getIdPessoa());
			}
			if(dto.temAno()) {
				query.setParameter("ano", dto.getAno());
			}
			
			listPremios = query.getResultList();
			
			
			
		} catch (Throwable e) {
			httpGoApi.geraLog("PremioTCTDao || pesquisa", StringUtil.convertObjectToJson(dto), e);
		}
		
		return listPremios;
		
	}
	
	
	// remover duplicidades
	public List<ParticipantePremioTCT> getParticipantesMalaDireta(PremioTCTDto dto) {
		
		List<ParticipantePremioTCT> listaParticipantes = new ArrayList<ParticipantePremioTCT>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT PART FROM ParticipantePremioTCT PART   ");
		sql.append("  WHERE PART.premio.ano = :ano                 ");
		sql.append("    AND PART.premio.status = 1                 ");
		sql.append("  ORDER BY PART.pessoa.id                      ");

		try {
			TypedQuery<ParticipantePremioTCT> query = em.createQuery(sql.toString(), ParticipantePremioTCT.class);
			query.setParameter("ano", dto.getAno());
						
			listaParticipantes = query.getResultList();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("PremioTCTDao || getParticipantesMalaDireta", StringUtil.convertObjectToJson(dto), e);
		}
		
		return listaParticipantes;
	}
	
	public List<ParticipantePremioTCT> relatorioParticipantesMalaDireta(PremioTCTDto dto) {
		
		List<ParticipantePremioTCT> listaParticipantes = new ArrayList<ParticipantePremioTCT>();
		//FIXME necessário saber se status = 1 e ano = 2018
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT DISTINCT PART.FK_PESSOA,                                 ");
		sql.append("        PF.NOME                                                 ");
//		sql.append("      PART.PAPEL,                                                ");
		sql.append("   FROM CAD_PARTICIPANTES_TCT PART                               ");
		sql.append("   JOIN CAD_PESSOAS_FISICAS PF ON (PART.FK_PESSOA = PF.CODIGO)   ");
//		sql.append("  ORDER BY PART.PAPEL                                            ");

		try {
			Query query = em.createNativeQuery(sql.toString());
//			query.setParameter("ano", dto.getAno());
						
			Iterator<?> it = query.getResultList().iterator();
			
			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {
					
					Object[] result = (Object[]) it.next();
					ParticipantePremioTCT participante = new ParticipantePremioTCT();
					
					PessoaFisica pessoa = new PessoaFisica();
					
					BigDecimal id = (BigDecimal) result[0];
					Long idPessoa = id.setScale(0, BigDecimal.ROUND_UP).longValueExact();
					pessoa.setId(idPessoa);
					pessoa.setNome(result[1] == null ? "" : result[1].toString());
//					participante.setPapel(result[2] == null ? "" : result[2].toString());
					participante.setPessoa(pessoa);

					listaParticipantes.add(participante);
				}
			}
			
		} catch (Throwable e) {
			httpGoApi.geraLog("PremioTCTDao || relatorioParticipantesMalaDireta", StringUtil.convertObjectToJson(dto), e);
		}
		
		return listaParticipantes;		
	}
	
	public List<ParticipantePremioTCT> relatorioCracha(PremioTCTDto dto) {
		
		List<ParticipantePremioTCT> listaParticipantes = new ArrayList<ParticipantePremioTCT>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT DISTINCT PART.FK_PESSOA,                                 ");
		sql.append("        PF.NOME,                                                 ");
		sql.append("      PART.PAPEL,                                                ");
		sql.append("         P.NOME_INSTITUICAO_ENSINO,                              ");
		sql.append("         P.NOME_CURSO,                                           ");
		sql.append("      PART.EMAIL                                                 ");
		sql.append("   FROM CAD_PARTICIPANTES_TCT PART                               ");
		sql.append("   JOIN CAD_PREMIO_TCT P ON (P.ID = PART.FK_CAD_PREMIO)          ");
		sql.append("   JOIN CAD_PESSOAS_FISICAS PF ON (PART.FK_PESSOA = PF.CODIGO)   ");
		sql.append("  WHERE P.ANO = :ano                                             ");
		sql.append("    AND P.STATUS = 1                                             ");
		sql.append("  ORDER BY PART.PAPEL, P.NOME_INSTITUICAO_ENSINO                 ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("ano", dto.getAno());
						
			Iterator<?> it = query.getResultList().iterator();
			
			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {
					
					Object[] result = (Object[]) it.next();
					ParticipantePremioTCT participante = new ParticipantePremioTCT();
					
					PessoaFisica pessoa = new PessoaFisica();
					
					BigDecimal id = (BigDecimal) result[0];
					Long idPessoa = id.setScale(0, BigDecimal.ROUND_UP).longValueExact();
					pessoa.setId(idPessoa);
					pessoa.setNome(result[1] == null ? "" : result[1].toString());
					participante.setPapel(result[2] == null ? "" : result[2].toString());
					participante.setPessoa(pessoa);
					PremioTCT premio = new PremioTCT();
					premio.setNomeInstituicaoEnsino(result[3] == null ? "" : result[3].toString());
					premio.setNomeCurso(result[4] == null ? "" : result[4].toString());
					participante.setPremio(premio);
					participante.setEmail(result[5] == null ? "" : result[5].toString());

					listaParticipantes.add(participante);
				}
			}
			
		} catch (Throwable e) {
			httpGoApi.geraLog("PremioTCTDao || relatorioCrachaOuCertificado", StringUtil.convertObjectToJson(dto), e);
		}
		
		return listaParticipantes;		
	}
	
	public List<ParticipantePremioTCT> relatorioCertificado(PremioTCTDto dto) {
		
		List<ParticipantePremioTCT> listaParticipantes = new ArrayList<ParticipantePremioTCT>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT DISTINCT PART.FK_PESSOA,                                 ");
		sql.append("        PF.NOME,                                                 ");
		sql.append("      PART.PAPEL,                                                ");
		sql.append("         P.NOME_INSTITUICAO_ENSINO,                              ");
		sql.append("         P.NOME_CURSO,                                           ");
		sql.append("         P.TITULO_TRABALHO                                       ");
		sql.append("   FROM CAD_PARTICIPANTES_TCT PART                               ");
		sql.append("   JOIN CAD_PREMIO_TCT P ON (P.ID = PART.FK_CAD_PREMIO)          ");
		sql.append("   JOIN CAD_PESSOAS_FISICAS PF ON (PART.FK_PESSOA = PF.CODIGO)   ");
		sql.append("  WHERE P.ANO = :ano                                             ");
		sql.append("    AND P.STATUS = 1                                             ");
		sql.append("  ORDER BY PART.PAPEL, P.NOME_INSTITUICAO_ENSINO                 ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("ano", dto.getAno());
						
			Iterator<?> it = query.getResultList().iterator();
			
			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {
					
					Object[] result = (Object[]) it.next();
					ParticipantePremioTCT participante = new ParticipantePremioTCT();
					
					PessoaFisica pessoa = new PessoaFisica();
					
					BigDecimal id = (BigDecimal) result[0];
					Long idPessoa = id.setScale(0, BigDecimal.ROUND_UP).longValueExact();
					pessoa.setId(idPessoa);
					pessoa.setNome(result[1] == null ? "" : result[1].toString());
					participante.setPapel(result[2] == null ? "" : result[2].toString());
					participante.setPessoa(pessoa);
					PremioTCT premio = new PremioTCT();
					premio.setNomeInstituicaoEnsino(result[3] == null ? "" : result[3].toString());
					premio.setNomeCurso(result[4] == null ? "" : result[4].toString());
					premio.setTitulo(result[5] == null ? "" : result[5].toString());
					participante.setPremio(premio);

					listaParticipantes.add(participante);
				}
			}
			
		} catch (Throwable e) {
			httpGoApi.geraLog("PremioTCTDao || relatorioCrachaOuCertificado", StringUtil.convertObjectToJson(dto), e);
		}
		
		return listaParticipantes;		
	}
		
	public List<ParticipantePremioTCT> relatorioConfirmacaoPresenca(PremioTCTDto dto) {
		
		List<ParticipantePremioTCT> listaParticipantes = new ArrayList<ParticipantePremioTCT>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT PART FROM ParticipantePremioTCT PART                ");
		sql.append("  WHERE PART.premio.ano = :ano                              ");
		sql.append("    AND PART.premio.status = 1                              ");
		sql.append("  ORDER BY PART.papel, PART.premio.nomeInstituicaoEnsino    ");

		try {
			TypedQuery<ParticipantePremioTCT> query = em.createQuery(sql.toString(), ParticipantePremioTCT.class);
			query.setParameter("ano", dto.getAno());
						
			listaParticipantes = query.getResultList();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("PremioTCTDao || relatorioConfirmacaoPresenca", StringUtil.convertObjectToJson(dto), e);
		}
		
		return listaParticipantes;
	}
	
		public int getTotalDeTrabalhosInscritos(PremioTCTDto dto) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(P.ID)       ");
		sql.append("   FROM CAD_PREMIO_TCT P  ");
		sql.append("  WHERE P.ANO = :ano      ");
		sql.append("    AND P.STATUS = 1      ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("ano", dto.getAno());
						
			return Integer.parseInt(String.valueOf(query.getSingleResult()));
			
		} catch (Throwable e) {
			httpGoApi.geraLog("PremioTCTDao || getTotalDeTrabalhosInscritos", StringUtil.convertObjectToJson(dto), e);
			return 0;
		}
	}

	public int getTotalDeCursosAbrangidos(PremioTCTDto dto) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(DISTINCT P.NOME_CURSO)  ");
		sql.append("   FROM CAD_PREMIO_TCT P              ");
		sql.append("  WHERE P.ANO = :ano                  ");
		sql.append("    AND P.STATUS = 1                  ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("ano", dto.getAno());
						
			return Integer.parseInt(String.valueOf(query.getSingleResult()));
			
		} catch (Throwable e) {
			httpGoApi.geraLog("PremioTCTDao || getTotalDeCursosAbrangidos", StringUtil.convertObjectToJson(dto), e);
			return 0;
		}
	}

	public int getTotalDeParticipantesPorPapel(Long ano, Long papel) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(DISTINCT PART.FK_PESSOA) FROM CAD_PREMIO_TCT P        ");
		sql.append("   JOIN CAD_PARTICIPANTES_TCT PART ON (PART.FK_CAD_PREMIO = P.ID)   ");
		sql.append("  WHERE PART.PAPEL = :papel                                         ");
		sql.append("    AND P.ANO = :ano                                                ");
		sql.append("    AND P.STATUS = 1                                                ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("ano", ano);
			query.setParameter("papel", papel);
						
			return Integer.parseInt(String.valueOf(query.getSingleResult()));
			
		} catch (Throwable e) {
			httpGoApi.geraLog("PremioTCTDao || getTotalDeParticipantesPorPapel", StringUtil.convertObjectToJson(ano + " - " + papel), e);
			return 0;
		}
	}

	public int getTotalDeTrabalhosPorNivel(Long ano, String nivel) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(DISTINCT P.ID)   ");
		sql.append("   FROM CAD_PREMIO_TCT P       ");
		sql.append("  WHERE P.NIVEL = :nivel       ");
		sql.append("    AND P.ANO = :ano           ");
		sql.append("    AND P.STATUS = 1           ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("ano", ano);
			query.setParameter("nivel", nivel);
						
			return Integer.parseInt(String.valueOf(query.getSingleResult()));
			
		} catch (Throwable e) {
			httpGoApi.geraLog("PremioTCTDao || getTotalDeTrabalhosPorNivel", StringUtil.convertObjectToJson(ano + " - " + nivel), e);
			return 0;
		}
	}

	public List<String> getInstituicoes(PremioTCTDto dto) {
		
		List<String> instituicoes = new ArrayList<String>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT DISTINCT P.NOME_INSTITUICAO_ENSINO  ");
		sql.append("   FROM CAD_PREMIO_TCT P                    ");
		sql.append("  WHERE P.ANO = :ano                        ");
		sql.append("    AND P.STATUS = 1                        ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("ano", dto.getAno());
						
			Iterator<?> it = query.getResultList().iterator();
			
			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {
					
					Object valor = (Object) it.next();
					
					instituicoes.add(valor != null ? valor.toString() : "");
				}
			}
			
		} catch (NoResultException e) {
			return instituicoes;
		} catch (Throwable e) {
			httpGoApi.geraLog("PremioTCTDao || getInstituicoes", StringUtil.convertObjectToJson(dto), e);
		}
		return instituicoes;
	}

	public List<PremioTCT> getRelatorioQuantitativoInstituicaoCursoENivel(PremioTCTDto dto) {
		
		List<PremioTCT> instituicoes = new ArrayList<PremioTCT>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT P FROM PremioTCT P          ");
		sql.append("  WHERE P.ano = :ano                ");
		sql.append("    AND P.status = 1                ");
		sql.append("  ORDER BY P.nomeInstituicaoEnsino, ");
		sql.append("           P.nomeCurso              ");

		try {
			TypedQuery<PremioTCT> query = em.createQuery(sql.toString(), PremioTCT.class);
			query.setParameter("ano", dto.getAno());
						
			instituicoes = query.getResultList();
			
		} catch (NoResultException e) {
			return instituicoes;
		} catch (Throwable e) {
			httpGoApi.geraLog("PremioTCTDao || getRelatorioQuantitativoInstituicaoCursoENivel", StringUtil.convertObjectToJson(dto), e);
		}
		return instituicoes;
	}

	public List<RelPremioQuantitativoDto> getRelatorioQuantitativoAutoresPorInstituicao(PremioTCTDto dto) {
		
		List<RelPremioQuantitativoDto> instituicoes = new ArrayList<RelPremioQuantitativoDto>();
		
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT P.NOME_INSTITUICAO_ENSINO,                       ");
		sql.append("  COUNT (DISTINCT PART.FK_PESSOA) AS QTD                 ");
		sql.append("   FROM CAD_PARTICIPANTES_TCT PART                       ");
		sql.append("   JOIN CAD_PREMIO_TCT P ON (PART.FK_CAD_PREMIO = P.ID)  ");
		sql.append("  WHERE PART.PAPEL = 1                                   ");
		sql.append("    AND P.STATUS = 1                                     ");
		sql.append("    AND P.ANO = :ano                                     ");
		sql.append("  GROUP BY P.NOME_INSTITUICAO_ENSINO                     ");
		sql.append("  ORDER BY QTD DESC                                      ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("ano", dto.getAno());
						
			Iterator<?> it = query.getResultList().iterator();
			
			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {
					
					RelPremioQuantitativoDto quantitativo = new RelPremioQuantitativoDto();
					
					Object[] result = (Object[]) it.next();				
					
					
					quantitativo.setInstituicao(result[0] == null ? "" : result[0].toString());
					quantitativo.setQuantidade(result[1] == null ? "" : result[1].toString());
					
					instituicoes.add(quantitativo);
				}
			}
			
		} catch (NoResultException e) {
			return instituicoes;
		} catch (Throwable e) {
			httpGoApi.geraLog("PremioTCTDao || getRelatorioQuantitativoAutoresPorInstituicao", StringUtil.convertObjectToJson(dto), e);
		}
		return instituicoes;
	}
	
	public List<RelPremioQuantitativoDto> getRelatorioQuantitativoAutoresPorNivel(PremioTCTDto dto) {
		
		List<RelPremioQuantitativoDto> instituicoes = new ArrayList<RelPremioQuantitativoDto>();
		
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT P.NIVEL,                                         ");
		sql.append("  COUNT (DISTINCT PART.FK_PESSOA) AS QTD                 ");
		sql.append("   FROM CAD_PARTICIPANTES_TCT PART                       ");
		sql.append("   JOIN CAD_PREMIO_TCT P ON (PART.FK_CAD_PREMIO = P.ID)  ");
		sql.append("  WHERE PART.PAPEL = 1                                   ");
		sql.append("    AND P.STATUS = 1                                     ");
		sql.append("    AND P.ANO = :ano                                     ");
		sql.append("  GROUP BY P.NIVEL                                       ");
		sql.append("  ORDER BY QTD DESC                                      ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("ano", dto.getAno());
						
			Iterator<?> it = query.getResultList().iterator();
			
			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {
					
					RelPremioQuantitativoDto quantitativo = new RelPremioQuantitativoDto();
					
					Object[] result = (Object[]) it.next();				
					
					
					quantitativo.setInstituicao(result[0] == null ? "" : result[0].toString());
					quantitativo.setQuantidade(result[1] == null ? "" : result[1].toString());
					
					instituicoes.add(quantitativo);
				}
			}
			
		} catch (NoResultException e) {
			return instituicoes;
		} catch (Throwable e) {
			httpGoApi.geraLog("PremioTCTDao || getRelatorioQuantitativoAutoresPorNivel", StringUtil.convertObjectToJson(dto), e);
		}
		return instituicoes;
	}
}