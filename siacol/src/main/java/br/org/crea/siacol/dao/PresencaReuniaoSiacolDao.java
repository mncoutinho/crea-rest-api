package br.org.crea.siacol.dao;

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
import br.org.crea.commons.models.siacol.PresencaReuniaoSiacol;
import br.org.crea.commons.models.siacol.dtos.ParticipanteReuniaoSiacolDto;
import br.org.crea.commons.models.siacol.enuns.StatusPresencaReuniaoEnum;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class PresencaReuniaoSiacolDao extends GenericDao<PresencaReuniaoSiacol, Serializable> {

	@Inject
	HttpClientGoApi httpGoApi;

	public PresencaReuniaoSiacolDao() {
		super(PresencaReuniaoSiacol.class);
	}

	public PresencaReuniaoSiacol getParticipanteComPresencaNaReuniao(Long idParticipante, Long idReuniao) {
		
		PresencaReuniaoSiacol presenca = new PresencaReuniaoSiacol();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT P FROM PresencaReuniaoSiacol P         ");
		sql.append("  WHERE P.pessoa.id = :idParticipante          ");
		sql.append("	AND P.reuniao.id = :idReuniao              ");
		sql.append("    AND (P.parte = 0 OR P.parte IS NULL)       ");

		try {
			TypedQuery<PresencaReuniaoSiacol> query = em.createQuery(sql.toString(), PresencaReuniaoSiacol.class);
			query.setParameter("idReuniao", idReuniao);
			query.setParameter("idParticipante", idParticipante);

			presenca = query.getSingleResult();
		
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("PresencaReuniaoSiacolDao || getConselheirosPresenteNaReuniao", StringUtil.convertObjectToJson(idParticipante + " -- " + idReuniao), e);
		}
		
		return presenca;
	}
	public List<PresencaReuniaoSiacol> getPresentes(Long idReuniao) {
		
		List<PresencaReuniaoSiacol> presentes = new ArrayList<PresencaReuniaoSiacol>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT P FROM PresencaReuniaoSiacol P   ");
		sql.append("  WHERE P.reuniao.id = :idReuniao        ");
		sql.append("    AND P.horaDevolucaoCracha IS NULL    ");
		sql.append("    AND (P.parte = 0 OR P.parte IS NULL) ");
		
		try {
			TypedQuery<PresencaReuniaoSiacol> query = em.createQuery(sql.toString(), PresencaReuniaoSiacol.class);
			query.setParameter("idReuniao", idReuniao);
			
			presentes = query.getResultList();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("PresencaReuniaoSiacolDao || getConselheirosPresenteNaReuniao", StringUtil.convertObjectToJson( idReuniao), e);
		}
		
		return presentes;
	}
	
	public boolean estaPresente(Long idConselheiro, Long idReuniao) {
		
		Long idPersonalidade = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT P.FK_PESSOA FROM SIACOL_REUNIAO_PRESENCA P   ");
		sql.append("  WHERE P.FK_PESSOA = :idConselheiro                 ");
		sql.append("    AND P.FK_REUNIAO = :idReuniao                    ");
		sql.append("    AND P.HORA_DEVOLUCAO_CRACHA IS NULL              ");
		sql.append("    AND (P.PARTE = 0 OR P.PARTE IS NULL)             ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idConselheiro", idConselheiro);
			query.setParameter("idReuniao", idReuniao);

			BigDecimal id = (BigDecimal) query.getSingleResult();
			idPersonalidade =  new Long(id.setScale(0, BigDecimal.ROUND_UP).longValueExact());
		
		} catch (NoResultException e) {
			return false;
		} catch (Throwable e) {
			httpGoApi.geraLog("PresencaReuniaoSiacolDao || estaPresente", StringUtil.convertObjectToJson(idConselheiro + " -- " + idReuniao), e);
		}
		
		return idPersonalidade != null;
	}
	
	public StatusPresencaReuniaoEnum getStatusPresencaParticipante(Long idReuniao, Long idParticipante) {
		PresencaReuniaoSiacol presenca = new PresencaReuniaoSiacol();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT P FROM PresencaReuniaoSiacol P        ");
		sql.append("  WHERE P.reuniao.id = :idReuniao             ");
		sql.append("	AND P.pessoa.id = :idParticipante         ");
		sql.append("    AND (P.parte = 0 OR P.parte IS NULL)      ");

		try {

			TypedQuery<PresencaReuniaoSiacol> query = em.createQuery(sql.toString(), PresencaReuniaoSiacol.class);
			query.setParameter("idReuniao", idReuniao);
			query.setParameter("idParticipante", idParticipante);

			presenca = query.getSingleResult();
		
		} catch (NoResultException e) {
			return StatusPresencaReuniaoEnum.X;
		} catch (Throwable e) {
			httpGoApi.geraLog("PresencaReuniaoSiacolDao || getStatusPresencaParticipante", StringUtil.convertObjectToJson(idReuniao), e);
		}
		
		return presenca.getHoraDevolucaoCracha() == null ? StatusPresencaReuniaoEnum.E : StatusPresencaReuniaoEnum.D;
	}
	
	public int getQuantidadeConselheirosPresentes(Long idReuniao) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(*) FROM SIACOL_REUNIAO_PRESENCA P                            ");
		sql.append("	WHERE P.FK_REUNIAO = :idReuniao                                        ");
		sql.append("	  AND P.HORA_DEVOLUCAO_CRACHA IS NULL                                  ");
		sql.append("	  AND (P.TIPO NOT LIKE '%PRESIDENTE DE MESA%' OR P.TIPO IS NULL)       ");
		sql.append("	  AND (P.TIPO NOT LIKE '%VICE-PRESIDENTE DE MESA%' OR P.TIPO IS NULL)  ");
		sql.append("	  AND (P.TIPO NOT LIKE '%TERCEIRO CARGO%' OR P.TIPO IS NULL)           ");
		sql.append("      AND (P.PARTE = 0 OR P.PARTE IS NULL)                                 ");
		
		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idReuniao", idReuniao);

			return Integer.parseInt(query.getSingleResult().toString());
		
		} catch (Throwable e) {
			httpGoApi.geraLog("PresencaReuniaoSiacolDao || getQuantidadeConselheirosPresentes", StringUtil.convertObjectToJson(idReuniao), e);
		}
		return 0;
	}

	public PresencaReuniaoSiacol getRegistroPresencaMesaDiretora(Long idReuniao, String papel) {
		PresencaReuniaoSiacol presenca = new PresencaReuniaoSiacol();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT P FROM PresencaReuniaoSiacol P        ");
		sql.append("  WHERE P.reuniao.id = :idReuniao             ");
		sql.append("	AND P.papel = :papel                      ");
		sql.append("    AND (P.parte = 0 OR P.parte IS NULL)      ");

		try {

			TypedQuery<PresencaReuniaoSiacol> query = em.createQuery(sql.toString(), PresencaReuniaoSiacol.class);
			query.setParameter("idReuniao", idReuniao);
			query.setParameter("papel", papel);

			presenca = query.getSingleResult();
		
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("PresencaReuniaoSiacolDao || getRegistroPresencaMesaDiretora", StringUtil.convertObjectToJson(idReuniao), e);
		}
		
		return presenca;
	}

	public ParticipanteReuniaoSiacolDto getFuncionarioMesaDiretora(Long idReuniao) {
		PresencaReuniaoSiacol presenca = new PresencaReuniaoSiacol();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT P FROM PresencaReuniaoSiacol P        ");
		sql.append("  WHERE P.reuniao.id = :idReuniao             ");
		sql.append("	AND P.papel = 'TERCEIRO CARGO'            ");
		sql.append("    AND (P.parte = 0 OR P.parte IS NULL)      ");

		try {

			TypedQuery<PresencaReuniaoSiacol> query = em.createQuery(sql.toString(), PresencaReuniaoSiacol.class);
			query.setParameter("idReuniao", idReuniao);

			presenca = query.getSingleResult();
		
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("PresencaReuniaoSiacolDao || getRegistroPresencaMesaDiretora", StringUtil.convertObjectToJson(idReuniao), e);
		}
		
		ParticipanteReuniaoSiacolDto participante = new ParticipanteReuniaoSiacolDto();
		participante.setId(presenca.getPessoa().getId());
		return participante;
	}

	public Long getCoordenadorComPresencaNaReuniao(Long idReuniao) {
		
		PresencaReuniaoSiacol presenca = new PresencaReuniaoSiacol();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT P FROM PresencaReuniaoSiacol P     ");
		sql.append("  WHERE P.reuniao.id = :idReuniao          ");
		sql.append("	AND P.papel = 'COORDENADOR'            ");
		sql.append("    AND (P.parte = 0 OR P.parte IS NULL)   ");

		try {

			TypedQuery<PresencaReuniaoSiacol> query = em.createQuery(sql.toString(), PresencaReuniaoSiacol.class);
			query.setParameter("idReuniao", idReuniao);

			presenca = query.getSingleResult();
		
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("PresencaReuniaoSiacolDao || getCoordenadorComPresencaNaReuniao", StringUtil.convertObjectToJson(idReuniao), e);
		}
		
		return presenca.getPessoa().getId();
	}

	public Long getCoordenadorAdjuntoComPresencaNaReuniao(Long idReuniao) {
		
		PresencaReuniaoSiacol presenca = new PresencaReuniaoSiacol();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT P FROM PresencaReuniaoSiacol P     ");
		sql.append("  WHERE P.reuniao.id = :idReuniao          ");
		sql.append("	AND P.papel = 'COORDENADOR-ADJUNTO'    ");
		sql.append("    AND (P.parte = 0 OR P.parte IS NULL)   ");

		try {

			TypedQuery<PresencaReuniaoSiacol> query = em.createQuery(sql.toString(), PresencaReuniaoSiacol.class);
			query.setParameter("idReuniao", idReuniao);

			presenca = query.getSingleResult();
		
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("PresencaReuniaoSiacolDao || getCoordenadorComPresencaNaReuniao", StringUtil.convertObjectToJson(idReuniao), e);
		}
		
		return presenca.getPessoa().getId();
	}

	public boolean atingiuInicioDeQuorum(Long idReuniao) {
		
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT CASE WHEN R.QUORUM  =                       ");
		sql.append("  (SELECT COUNT(*) FROM SIACOL_REUNIAO_PRESENCA P   ");
		sql.append("	      WHERE P.FK_reuniao = :idReuniao           ");
		sql.append("	        AND P.papel NOT LIKE 'PRESIDENTE%'      ");
		sql.append("	        AND P.papel NOT LIKE 'TERCEIRO%'        ");
		sql.append("	        AND (P.PARTE = 0 OR P.PARTE IS NULL))   ");
		sql.append("	THEN 1 ELSE 0 END                               ");
		sql.append("  FROM SIACOL_REUNIAO R                             ");
		sql.append("	   WHERE R.ID = :idReuniao                      ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idReuniao", idReuniao);

			int resultado = Integer.parseInt(query.getSingleResult().toString()); 
			return resultado == 1;
		
		} catch (NoResultException e) {
			return false;
		} catch (Throwable e) {
			httpGoApi.geraLog("PresencaReuniaoSiacolDao || atingiuInicioDeQuorum", StringUtil.convertObjectToJson(idReuniao), e);
		}
		
		return false;

	}

	public boolean atingiuFimDeQuorum(Long idReuniao) {
		
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT CASE WHEN (R.QUORUM - 1) =                  ");
		sql.append("  (SELECT COUNT(*) FROM SIACOL_REUNIAO_PRESENCA P   ");
		sql.append("	      WHERE P.FK_REUNIAO = :idReuniao           ");
		sql.append("	        AND P.HORA_ENTREGA_CRACHA IS NOT NULL   ");
		sql.append("	        AND P.HORA_DEVOLUCAO_CRACHA IS NULL     ");
		sql.append("	        AND P.PAPEL NOT LIKE 'COORDENADOR%')    ");
		sql.append("	        AND (P.PARTE = 0 OR P.PARTE IS NULL))   ");
		sql.append("	THEN 1  ELSE 0 END                              ");
		sql.append("  FROM SIACOL_REUNIAO R                             ");
		sql.append("	   WHERE R.ID = :idReuniao                      ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idReuniao", idReuniao);

			return Integer.parseInt(query.getSingleResult().toString()) == 1;
		
		} catch (NoResultException e) {
			return false;
		} catch (Throwable e) {
			httpGoApi.geraLog("PresencaReuniaoSiacolDao || atingiuFimDeQuorum", StringUtil.convertObjectToJson(idReuniao), e);
		}
		
		return false;
	}

	public void atualizaMesaDiretoraCamara(Long idReuniao, Long idConselheiro) {
			
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE SIACOL_REUNIAO_PRESENCA P        ");
		sql.append("    SET P.VOTO_MINERVA = 1               ");
		sql.append("  WHERE P.FK_PESSOA = :idConselheiro     ");
		sql.append("    AND P.FK_REUNIAO = :idReuniao        ");
		sql.append("    AND (P.PARTE = 0 OR P.PARTE IS NULL) ");
			
		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idConselheiro", idConselheiro);
			query.setParameter("idReuniao", idReuniao);

			query.executeUpdate();
		
		} catch (Throwable e) {
			httpGoApi.geraLog("PresencaReuniaoSiacolDao || cadastraMesaDiretoraCamara", StringUtil.convertObjectToJson(idConselheiro + " -- " + idReuniao), e);
		}
		
	}
	
	public void zeraVotoMinerva(Long idReuniao) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE SIACOL_REUNIAO_PRESENCA P       ");
		sql.append("    SET P.VOTO_MINERVA = 0              ");
		sql.append("  WHERE P.FK_REUNIAO = :idReuniao       ");
		sql.append("   AND (P.PARTE = 0 OR P.PARTE IS NULL) ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idReuniao", idReuniao);

			query.executeUpdate();
		
		} catch (Throwable e) {
			httpGoApi.geraLog("PresencaReuniaoSiacolDao || zeraVotoMinerva", StringUtil.convertObjectToJson(idReuniao + " -- " + idReuniao), e);
		}
	}

	public Long getParticipanteComVotoDeMinerva(Long idReuniao) {
		
		PresencaReuniaoSiacol presenca = new PresencaReuniaoSiacol();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT P FROM PresencaReuniaoSiacol P     ");
		sql.append("  WHERE P.reuniao.id = :idReuniao          ");
		sql.append("	AND P.votoMinerva = 1                  ");
		sql.append("    AND (P.parte = 0 OR P.parte IS NULL)   ");

		try {

			TypedQuery<PresencaReuniaoSiacol> query = em.createQuery(sql.toString(), PresencaReuniaoSiacol.class);
			query.setParameter("idReuniao", idReuniao);

			presenca = query.getSingleResult();
		
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("PresencaReuniaoSiacolDao || getParticipanteComVotoDeMinerva", StringUtil.convertObjectToJson(idReuniao), e);
		}
		
		return presenca.getPessoa().getId();
	}
	
	public List<Long> getConselheirosEfetivosESuplentesPresentesNaReuniao(Long idReuniao) {
		List<Long> listaIdsPresentes = new ArrayList<Long>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT P.FK_PESSOA FROM SIACOL_REUNIAO_PRESENCA P       ");
		sql.append("  WHERE P.FK_REUNIAO = :idReuniao                        ");
		sql.append("    AND P.HORA_DEVOLUCAO_CRACHA IS NULL                  ");
		sql.append("    AND (P.PARTE = 0 OR P.PARTE IS NULL)                 ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idReuniao", idReuniao);

			Iterator<?> it = query.getResultList().iterator();
			
			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {
					
					BigDecimal id = (BigDecimal) it.next();
					Long idPresente = new Long(id.setScale(0, BigDecimal.ROUND_UP).longValueExact());

					listaIdsPresentes.add(idPresente);
				}
			}
		
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("PresencaReuniaoSiacolDao || getConselheirosPresentesNaReuniao", StringUtil.convertObjectToJson(idReuniao), e);
		}
		return listaIdsPresentes;
	}
	
	public List<Long> getConselheirosEfetivosESuplentesComPresencaNaReuniao(Long idReuniao) {
		List<Long> listaIdsPresentes = new ArrayList<Long>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT P.FK_PESSOA FROM SIACOL_REUNIAO_PRESENCA P                      ");
		sql.append("  WHERE P.FK_REUNIAO = :idReuniao                                       ");
		sql.append("   AND (P.TIPO NOT LIKE '%PRESIDENTE DE MESA%' OR P.TIPO IS NULL)       ");
		sql.append("   AND (P.TIPO NOT LIKE '%VICE-PRESIDENTE DE MESA%' OR P.TIPO IS NULL)  ");
		sql.append("   AND (P.TIPO NOT LIKE '%TERCEIRO CARGO%' OR P.TIPO IS NULL)           ");
		sql.append("   AND (P.PARTE = 0 OR P.PARTE IS NULL)                                 ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idReuniao", idReuniao);

			Iterator<?> it = query.getResultList().iterator();
			
			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {
					
					BigDecimal id = (BigDecimal) it.next();
					Long idPresente = new Long(id.setScale(0, BigDecimal.ROUND_UP).longValueExact());

					listaIdsPresentes.add(idPresente);
				}
			}
		
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("PresencaReuniaoSiacolDao || getConselheirosPresentesNaReuniao", StringUtil.convertObjectToJson(idReuniao), e);
		}
		return listaIdsPresentes;
	}

	public List<Long> getConselheirosPresentesSemMesaDiretora(Long idReuniao) {
		List<Long> listaIdsPresentes = new ArrayList<Long>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT P.FK_PESSOA FROM SIACOL_REUNIAO_PRESENCA P              ");
		sql.append("  WHERE P.FK_REUNIAO = :idReuniao                               ");
		sql.append("    AND P.HORA_DEVOLUCAO_CRACHA IS NULL                         ");
		sql.append("    AND P.PAPEL NOT LIKE 'COORDENADOR%'                         ");
		sql.append("    AND (P.PARTE = 0 OR P.PARTE IS NULL)                        ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idReuniao", idReuniao);

			Iterator<?> it = query.getResultList().iterator();
			
			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {
					
					BigDecimal id = (BigDecimal) it.next();
					Long idPresente = new Long(id.setScale(0, BigDecimal.ROUND_UP).longValueExact());

					listaIdsPresentes.add(idPresente);
				}
			}
		
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("PresencaReuniaoSiacolDao || getConselheirosPresentesSemMesaDiretora", StringUtil.convertObjectToJson(idReuniao), e);
		}
		return listaIdsPresentes;
	}
	
	public List<PresencaReuniaoSiacol> getMesaDiretora(Long idReuniao) {
		
		List<PresencaReuniaoSiacol> listaParticipantesMesaDiretora = new ArrayList<PresencaReuniaoSiacol>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT P FROM PresencaReuniaoSiacol P     ");
		sql.append("  WHERE P.reuniao.id = :idReuniao          ");
		sql.append("	AND P.tipo = 'MESA DIRETORA'           ");
		sql.append("    AND (P.parte = 0 OR P.parte IS NULL)   ");

		try {

			TypedQuery<PresencaReuniaoSiacol> query = em.createQuery(sql.toString(), PresencaReuniaoSiacol.class);
			query.setParameter("idReuniao", idReuniao);

			listaParticipantesMesaDiretora = query.getResultList();
		
		} catch (NoResultException e) {
			return listaParticipantesMesaDiretora;
		} catch (Throwable e) {
			httpGoApi.geraLog("PresencaReuniaoSiacolDao || getMesaDiretora", StringUtil.convertObjectToJson(idReuniao), e);
		}
		
		return listaParticipantesMesaDiretora;
	}

	public void registrarSaidaDeTodos(Long idReuniao) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE SIACOL_REUNIAO_PRESENCA P            ");
		sql.append("    SET P.HORA_DEVOLUCAO_CRACHA = :horaSaida ");
		sql.append("  WHERE P.FK_REUNIAO = :idReuniao            ");
		sql.append("    AND P.HORA_DEVOLUCAO_CRACHA IS NULL      ");
		sql.append("    AND (P.PARTE = 0 OR P.PARTE IS NULL)     ");
			
		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idReuniao", idReuniao);
			query.setParameter("horaSaida", new Date());

			query.executeUpdate();
		
		} catch (Throwable e) {
			httpGoApi.geraLog("PresencaReuniaoSiacolDao || registrarSaidaDeTodos", StringUtil.convertObjectToJson(idReuniao), e);
		}
	}

	public PresencaReuniaoSiacol getPresidenteDaMesaDiretora(Long idReuniao) {
	
		PresencaReuniaoSiacol participanteMesaDiretora = new PresencaReuniaoSiacol();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT P FROM PresencaReuniaoSiacol P     ");
		sql.append("  WHERE P.reuniao.id = :idReuniao          ");
		sql.append("	AND P.papel = 'PRESIDENTE DE MESA'     ");
		sql.append("	AND P.tipo = 'MESA DIRETORA'           ");
		sql.append("    AND (P.parte = 0 OR P.parte IS NULL)   ");

		try {

			TypedQuery<PresencaReuniaoSiacol> query = em.createQuery(sql.toString(), PresencaReuniaoSiacol.class);
			query.setParameter("idReuniao", idReuniao);

			participanteMesaDiretora = query.getSingleResult();
		
		} catch (NoResultException e) {
			return null;
			//httpGoApi.geraLog("PresencaReuniaoSiacolDao || getMesaDiretora", StringUtil.convertObjectToJson(idReuniao), e);
		}
		
		return participanteMesaDiretora;
	}

	public void registraPresentesOitentaPorcentoVotado(Long idReuniao) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE SIACOL_REUNIAO_PRESENCA P          ");
		sql.append("    SET P.ATINGIU_80 = 1,                  ");
		sql.append("        P.HORA_80 = :horaOitentaPorcento   ");
		sql.append("  WHERE P.FK_REUNIAO = :idReuniao          ");
		sql.append("    AND P.HORA_DEVOLUCAO_CRACHA IS NULL    ");
		sql.append("    AND P.HORA_80 IS NULL                  ");
		sql.append("    AND (P.PARTE = 0 OR P.PARTE IS NULL)   ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idReuniao", idReuniao);
			query.setParameter("horaOitentaPorcento", new Date());

			query.executeUpdate();
		
		} catch (Throwable e) {
			httpGoApi.geraLog("PresencaReuniaoSiacolDao || registraPresentesOitentaPorcentoVotado", StringUtil.convertObjectToJson(idReuniao), e);
		}
		
	}

	public void deletaRegistrosDePresenca(Long idReuniao) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" DELETE SIACOL_REUNIAO_PRESENCA P  ");
		sql.append("  WHERE P.FK_REUNIAO = :idReuniao  ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idReuniao", idReuniao);

			query.executeUpdate();
		
		} catch (Throwable e) {
			httpGoApi.geraLog("PresencaReuniaoSiacolDao || deletaRegistrosDePresenca", StringUtil.convertObjectToJson(idReuniao), e);
		}
	}
	
	public List<PresencaReuniaoSiacol> getListPresenca(Long idReuniao) {
		
		List<PresencaReuniaoSiacol> listaPresenca = new ArrayList<PresencaReuniaoSiacol>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT P FROM PresencaReuniaoSiacol P     ");
		sql.append("  WHERE P.reuniao.id = :idReuniao          ");
		sql.append("    AND (P.parte = 0 OR P.parte IS NULL)   ");

		try {

			TypedQuery<PresencaReuniaoSiacol> query = em.createQuery(sql.toString(), PresencaReuniaoSiacol.class);
			query.setParameter("idReuniao", idReuniao);

			listaPresenca = query.getResultList();
		
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("PresencaReuniaoSiacolDao || getListPresenca", StringUtil.convertObjectToJson(idReuniao), e);
		}
		
		return listaPresenca;
	}

	public void atualizarParteDaReuniaoNaListaDePresenca(Long idReuniao, Long parte) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE SIACOL_REUNIAO_PRESENCA P        ");
		sql.append("    SET P.PARTE = :parte                 ");
		sql.append("  WHERE P.FK_REUNIAO = :idReuniao        ");
		sql.append("    AND (P.PARTE = 0 OR P.PARTE IS NULL) ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idReuniao", idReuniao);
			query.setParameter("parte", parte);

			query.executeUpdate();
		
		} catch (Throwable e) {
			httpGoApi.geraLog("PresencaReuniaoSiacolDao || atualizarParteDaReuniaoNaListaDePresenca", StringUtil.convertObjectToJson(idReuniao + " - " + parte), e);
		}
	}
	
}
