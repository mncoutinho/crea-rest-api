package br.org.crea.commons.dao.siacol;

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
import br.org.crea.commons.dao.cadastro.domains.DepartamentoDao;
import br.org.crea.commons.models.cadastro.Departamento;
import br.org.crea.commons.models.commons.dtos.PesquisaGenericDto;
import br.org.crea.commons.models.siacol.EmailConselheiro;
import br.org.crea.commons.models.siacol.PersonalidadeSiacol;
import br.org.crea.commons.models.siacol.dtos.ParticipanteReuniaoSiacolDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.HashUtil;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class PersonalidadeSiacolDao extends GenericDao<PersonalidadeSiacol, Serializable> {

	@Inject HttpClientGoApi httpGoApi;

	@Inject DepartamentoDao departamentoDao;
	
	public PersonalidadeSiacolDao() {
		super(PersonalidadeSiacol.class);
	}
	
	public PersonalidadeSiacol autentica(Long senha) {

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT C FROM PersonalidadeSiacol C  ");
		sql.append("  WHERE C.senhaPersonalidade = :senha ");
		
		try {
			TypedQuery<PersonalidadeSiacol> query = em.createQuery(sql.toString(), PersonalidadeSiacol.class);
			query.setParameter("senha", HashUtil.criptografa(senha.toString()));

			return query.getSingleResult();
			
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("PersonalidadeSiacolDao || autentica", StringUtil.convertObjectToJson(senha), e);
		}
		return null;
		
	}

	public Long getConselheiroPor(PesquisaGenericDto pesquisa) {
		Long idConselheiro = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT DISTINCT P.CODIGO                                                          ");
		sql.append("   FROM PER_PERSONALIDADES P                                                       ");
		sql.append("	WHERE (P.CODIGO IN                                                             ");
		sql.append("	   (SELECT FK_CODIGO_PERSONALIDADE FROM PER_CONSELHEIRO_REGIONAL               ");
		sql.append("	     WHERE REMOVIDO = 0                                                        ");
		sql.append("	       AND FINALIZADO = 0                                                      ");
		sql.append("	       AND LICENCIADO = 0)                                                     ");
		sql.append("       OR P.CODIGO IN                                                              ");
		sql.append("	   (SELECT FK_CODIGO_SUPLENTE FROM PER_CONSELHEIRO_REGIONAL                    ");
		sql.append("	     WHERE FK_CODIGO_SUPLENTE IS NOT NULL                                      ");
		sql.append("	       AND REMOVIDO = 0                                                        ");
		sql.append("	       AND FINALIZADO = 0))                                                    ");
		
		queryGetConselheiro(pesquisa, sql);

		try {

			Query query = em.createNativeQuery(sql.toString());
			parametrosGetConselheiro(pesquisa, query);

			BigDecimal id = (BigDecimal) query.getSingleResult();
			idConselheiro = new Long(id.setScale(0, BigDecimal.ROUND_UP).longValueExact());
		
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("PersonalidadeSiacolDao || getConselheiroPor", StringUtil.convertObjectToJson(pesquisa), e);
		}
		
		return idConselheiro;
	}
	
	private void queryGetConselheiro(PesquisaGenericDto pesquisa, StringBuilder sql) {
		if (pesquisa.getTipo().equals("REGISTRO")) {
			sql.append("	  AND P.CODIGO = :registro                   ");
		}
		
		if (pesquisa.getTipo().equals("CRACHA")) {
			sql.append("	  AND P.CRACHA = :cracha                     ");
		}
		
		if (pesquisa.getTipo().equals("CODIGOBARRAS")) {
			sql.append("	  AND P.CODIGO_BARRAS = :codigoBarras        ");
		}
		
		if (pesquisa.getTipo().equals("NOME")) {
			sql.append("	  AND P.CODIGO IN                            ");
			sql.append("	   (SELECT CODIGO FROM CAD_PESSOAS_FISICAS   ");
			sql.append("         WHERE NOME LIKE :nome)                  ");
		}
		
	}
	
	private void parametrosGetConselheiro(PesquisaGenericDto pesquisa, Query query) {
		if (pesquisa.getTipo().equals("REGISTRO")) {
			query.setParameter("registro", pesquisa.getRegistro());
		}
		
		if (pesquisa.getTipo().equals("CRACHA")) {
			query.setParameter("cracha", pesquisa.getRegistro()); // utilizar outro campo
		}
		
		if (pesquisa.getTipo().equals("CODIGOBARRAS")) {
			query.setParameter("codigoBarras", pesquisa.getRegistro()); // utilizar outro campo
		}
		
		if (pesquisa.getTipo().equals("NOME")) {
			query.setParameter("nome", "%" + pesquisa.getNomePessoa().toUpperCase() + "%");
		}
		
	}

	public Long buscaIdSuplente(Long idConselheiro) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT FK_CODIGO_SUPLENTE FROM PER_CONSELHEIRO_REGIONAL C                    ");
		sql.append("  WHERE C.FK_CODIGO_SUPLENTE IS NOT NULL                                      ");
		sql.append("	AND C.REMOVIDO = 0                                                        ");
		sql.append("	AND C.FINALIZADO = 0                                                      ");
		sql.append("	AND C.FK_CODIGO_PERSONALIDADE = :idConselheiro                            ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idConselheiro", idConselheiro);

			BigDecimal id = (BigDecimal) query.getSingleResult();
			return new Long(id.setScale(0, BigDecimal.ROUND_UP).longValueExact());
		
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("PersonalidadeSiacolDao || ehConselheiroEfetivo", StringUtil.convertObjectToJson(idConselheiro), e);
		}
		
		return null;
	}

	public Long getConselheiroEfetivoDoSuplente(Long idSuplente) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT FK_CODIGO_PERSONALIDADE FROM PER_CONSELHEIRO_REGIONAL C               ");
		sql.append("  WHERE C.FK_CODIGO_SUPLENTE = :idSuplente                                    ");
		sql.append("	AND C.REMOVIDO   = 0                                                      ");
		sql.append("	AND C.FINALIZADO = 0                                                      ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idSuplente", idSuplente);

			BigDecimal id = (BigDecimal) query.getSingleResult();
			return new Long(id.setScale(0, BigDecimal.ROUND_UP).longValueExact());
		
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("PersonalidadeSiacolDao || getConselheiroEfetivoDoSuplente", StringUtil.convertObjectToJson(idSuplente), e);
		}
		
		return null;
	}

	public Long getCrachaPorId(Long idConselheiro) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT P.CRACHA FROM PER_PERSONALIDADES P    ");
		sql.append("  WHERE P.CODIGO = :idConselheiro             ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idConselheiro", idConselheiro);

			BigDecimal id = (BigDecimal) query.getSingleResult();
			
			return id != null ? new Long(id.setScale(0, BigDecimal.ROUND_UP).longValueExact()) : null;
		
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("PersonalidadeSiacolDao || getCrachaPorId", StringUtil.convertObjectToJson(idConselheiro), e);
		}
		
		return null;
	}
	
	public List<Long> buscaConselheirosEfetivosNaoLicenciadosPor(Departamento departamentoReuniao) {
		List<Long> listaIdConselheiro = new ArrayList<Long>();
		Long idConselheiro = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT fk_codigo_personalidade FROM per_cargo_conselheiro  	     		");
		sql.append(" 	WHERE removido = 0												 		");
		sql.append(" 	AND datadesligamentocargo IS NULL 								 		");
		
		if (departamentoReuniao.ehComissao()) {
			
			sql.append(" AND fk_cargo_raiz IN (2560, 2559, 2962, 2935, 2934) 	");
			sql.append(" AND fk_departamento = :codigoDepartamento 						        ");

		} else if (departamentoReuniao.ehPlenario()) {
			
			sql.append(" AND fk_cargo_raiz IN (2562, 2560, 2559, 49)							");					 
			sql.append(" AND fk_departamento IN (SELECT codigo FROM prt_departamentos WHERE fk_id_departamentos = 12) "); 

		} else {
			
			sql.append(" AND fk_cargo_raiz IN (2562, 2560, 2559, 49)							");
			sql.append(" AND fk_departamento = :codigoDepartamento 						        ");
		}
		
		sql.append(" 	AND fk_codigo_personalidade 									 		");
		sql.append(" 		IN ( SELECT fk_codigo_personalidade 						 		");
		sql.append(" 		 	 	FROM per_conselheiro_regional 						 		");
		sql.append(" 		 		WHERE removido = 0 									 		");
		sql.append(" 				AND finalizado = 0									 		");
		sql.append(" 				AND licenciado = 0 )								 		"); 			
		sql.append(" ORDER BY fk_codigo_personalidade 									 		");
		
		try {
			
			Query query = em.createNativeQuery(sql.toString());
			if (departamentoReuniao.ehComissao() || !departamentoReuniao.ehPlenario()) {
				
				query.setParameter("codigoDepartamento", departamentoReuniao.getCodigo());
			}
			
			Iterator<?> it = query.getResultList().iterator();
			
			if ( query.getResultList() != null && !query.getResultList().isEmpty() ) {
				while (it.hasNext()) {
					idConselheiro = ((BigDecimal) it.next()).setScale(0,BigDecimal.ROUND_UP).longValueExact();
					if( idConselheiro != null ) {
						listaIdConselheiro.add(idConselheiro);
					}
				}
			}
			
		} catch (NoResultException e) {
			return listaIdConselheiro;
		} catch (Throwable e) {
			httpGoApi.geraLog("PersonalidadeSiacolDao || buscaConselheirosEfetivosNaoLicenciadosPor ", StringUtil.convertObjectToJson(departamentoReuniao), e);
		}
		return listaIdConselheiro;
	}
	
	public List<Long> buscaConselheirosSuplentesEfetivosLicenciadosPor(Departamento departamentoReuniao) {
		List<Long> listaIdConselheiro = new ArrayList<Long>();
		Long idConselheiro = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT fk_codigo_suplente FROM per_conselheiro_regional  	    						          			 ");
		sql.append("	WHERE removido = 0  	    														          			 ");
		sql.append("	AND finalizado = 0  	    														          			 ");
		sql.append("  	AND fk_codigo_personalidade															          			 ");
		sql.append("  		IN ( SELECT fk_codigo_personalidade     										          			 ");
		sql.append("  	    		FROM per_cargo_conselheiro												          			 ");
		sql.append("  	    		WHERE removido = 0 																  			 ");
		sql.append("  	    		AND datadesligamentocargo IS NULL 												  			 ");
		
		if (departamentoReuniao.ehComissao()) {
			
			sql.append(" 			AND fk_cargo_raiz IN (2560, 2559, 2962 ,2935, 2934) 			  			 ");
			sql.append(" 			AND fk_departamento = :codigoDepartamento 						        		  			 ");

		} else if (departamentoReuniao.ehPlenario()) {
			
			sql.append(" 			AND fk_cargo_raiz IN (2562, 2560, 2559, 49)												     ");					 
			sql.append(" 			AND fk_departamento IN (SELECT codigo FROM prt_departamentos WHERE fk_id_departamentos = 12) "); 

		} else {
			
			sql.append(" 			AND fk_cargo_raiz IN (2562, 2560, 2559, 49)												     ");
			sql.append(" 			AND fk_departamento = :codigoDepartamento 						        					 ");
		}
		
		sql.append("  	    		AND TO_CHAR(datafinalcargo, 'YYYYMMDD') >= TO_CHAR(SYSDATE, 'YYYYMMDD') 					 ");
		sql.append("  	    		AND fk_codigo_personalidade																	 ");
		sql.append("  	    			IN ( SELECT fk_codigo_personalidade														 ");
		sql.append("  	    				 	FROM per_conselheiro_regional 													 ");
		sql.append("  	    					WHERE removido = 0 																 ");
		sql.append("  	    					AND finalizado = 0 																 ");
		sql.append("  	    					AND licenciado = 1 	))															 ");
		sql.append(" ORDER BY FK_CODIGO_PERSONALIDADE	  	    																 ");
		
		try {
			
			Query query = em.createNativeQuery(sql.toString());
			if (departamentoReuniao.ehComissao() || !departamentoReuniao.ehPlenario()) {
				
				query.setParameter("codigoDepartamento", departamentoReuniao.getCodigo());
			}
			
			Iterator<?> it = query.getResultList().iterator();
			
			if ( !query.getResultList().isEmpty() ) {
				while (it.hasNext()) {
					idConselheiro = ((BigDecimal) it.next()).setScale(0,BigDecimal.ROUND_UP).longValueExact();
					if( idConselheiro != null ) {
						listaIdConselheiro.add(idConselheiro);
					}
				}
			}
			
		} catch (NoResultException e) {
			return listaIdConselheiro;
		} catch (Throwable e) {
			httpGoApi.geraLog("PersonalidadeSiacolDao || buscaConselheirosEfetivosNaoLicenciadosPor ", StringUtil.convertObjectToJson(departamentoReuniao), e);
		}
		return listaIdConselheiro;
	}
	
	public EmailConselheiro buscaEmailConselheirosPor(Long codigoPessoa) {
		EmailConselheiro email = new EmailConselheiro();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT E ");
		sql.append("	FROM EmailConselheiro E ");
		sql.append("	WHERE E.codigo IN ");
		sql.append("	  (SELECT MAX(EM.codigo) ");
		sql.append("		FROM EmailConselheiro EM ");
		sql.append("		WHERE EM.pessoa.id = :codigoPessoa)");
		
		try {
			
			TypedQuery<EmailConselheiro> query = em.createQuery(sql.toString(), EmailConselheiro.class);
			query.setParameter("codigoPessoa", codigoPessoa);
			
			email = query.getSingleResult();
			
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("PersonalidadeSiacolDao || buscaEmailConselheirosPor ", StringUtil.convertObjectToJson(codigoPessoa), e);
		}
		
		return email;
	}
	
	public List<EmailConselheiro> buscaEmailConselheirosEnvioPautaPor(Long codigoDepartamento) {
		List<EmailConselheiro> listEmails = new ArrayList<EmailConselheiro>();
		Departamento departamentoReuniao = departamentoDao.buscaDepartamentoPor(codigoDepartamento);
		
		List<Long> efetivos = buscaConselheirosEfetivosNaoLicenciadosPor(departamentoReuniao);
		List<Long> suplentes = buscaConselheirosSuplentesEfetivosLicenciadosPor(departamentoReuniao);
		
		efetivos.forEach(e -> {
			listEmails.add(buscaEmailConselheirosPor(e));
		});
		suplentes.forEach(s -> {
			listEmails.add(buscaEmailConselheirosPor(s));
		});
		
		return listEmails;
	}	

	public List<Long> getConselheirosEfetivosPorIdDepartamento(Long idDepartamento, Long statusLicenca, boolean ehComissao) {
		
		List<Long> listaIdsEfetivos = new ArrayList<Long>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT DISTINCT P.FK_CODIGO_PERSONALIDADE FROM PER_CARGO_CONSELHEIRO P         "); 
		sql.append("	WHERE TO_CHAR(P.DATAFINALCARGO, 'YYYYMMDD') >= TO_CHAR(SYSDATE, 'YYYYMMDD') ");
		sql.append("	  AND P.REMOVIDO = 0                                                        ");
		
		if (idDepartamento.longValue() != new Long(11)) {
			sql.append("      AND P.FK_DEPARTAMENTO = :idDepartamento                               ");
		}
		
		sql.append("	  AND P.DATADESLIGAMENTOCARGO IS NULL                                       ");
		if (ehComissao) {
			sql.append("  AND P.FK_CARGO_RAIZ IN (2560, 2559, 2562,									  ");
			sql.append("              2934, 2929, 2930, 2931, 2932, 2933, 2935, 2936, 2937, 2938)   ");
		} else {
			sql.append("	  AND P.FK_CARGO_RAIZ IN (2562, 2560, 2559)                             ");	
		}
		
		sql.append("	  AND P.FK_CODIGO_PERSONALIDADE IN                                          ");
		sql.append("	     (SELECT DISTINCT C.FK_CODIGO_PERSONALIDADE                             ");
		sql.append("	                 FROM PER_CONSELHEIRO_REGIONAL C                            ");
		sql.append("	                WHERE C.REMOVIDO   = 0                                      ");
		
		if (!statusLicenca.equals(new Long(99))) {
			sql.append("	              AND C.LICENCIADO = :statusLicenca                         ");
		}
		
		sql.append("	                  AND C.FINALIZADO = 0)                                     ");
		
		try {
	
			Query query = em.createNativeQuery(sql.toString());
			
			if (idDepartamento.longValue() != new Long(11)) {
				query.setParameter("idDepartamento", idDepartamento);
			}
			
			if (!statusLicenca.equals(new Long(99))) {
				query.setParameter("statusLicenca", statusLicenca);
			}
	
			Iterator<?> it = query.getResultList().iterator();
			
			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {
					
					BigDecimal id = (BigDecimal) it.next();
					Long idEfetivo = new Long(id.setScale(0, BigDecimal.ROUND_UP).longValueExact());
	
					listaIdsEfetivos.add(idEfetivo);
				}
			}
		
		} catch (Throwable e) {
			httpGoApi.geraLog("PersonalidadeSiacolDao || getConselheirosEfetivosPorIdDepartamento", StringUtil.convertObjectToJson(idDepartamento + " -- " + statusLicenca), e);
		}
		
		return listaIdsEfetivos;
	}

	public String getNomeDeGuerraPorId(Long idConselheiro) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT P.NOME_GUERRA FROM PER_PERSONALIDADES P    ");
		sql.append("  WHERE P.CODIGO = :idConselheiro                  ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idConselheiro", idConselheiro);

			String nomeGuerra = (String) query.getSingleResult();
			return nomeGuerra == null ? "" : nomeGuerra;
		
		} catch (NoResultException e) {
			return "";
		} catch (Throwable e) {
			httpGoApi.geraLog("PersonalidadeSiacolDao || getNomeDeGuerraPorId", StringUtil.convertObjectToJson(idConselheiro), e);
		}
		
		return "";
	}

	public List<ParticipanteReuniaoSiacolDto> getDiretoria() {
		
		List<ParticipanteReuniaoSiacolDto> listaMembrosDiretoria = new ArrayList<ParticipanteReuniaoSiacolDto>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT DISTINCT C.FK_CODIGO_PERSONALIDADE, C.FK_CODIGO_CARGO, PC.DESCRICAO FROM PER_CARGO_CONSELHEIRO C ");
		sql.append(" JOIN PER_CARGOS PC ON (C.FK_CODIGO_CARGO = PC.CODIGO)                                          ");
		sql.append("  WHERE TO_CHAR(C.DATAFINALCARGO, 'YYYYMMDD') >= TO_CHAR(SYSDATE, 'YYYYMMDD')                   ");
		sql.append("    AND C.REMOVIDO = 0                                                                          ");
		sql.append("    AND C.FK_CODIGO_CARGO IN (2550, 2551, 2552, 2553)                                           ");
		sql.append("  ORDER BY C.FK_CODIGO_CARGO                                                                    ");

		try {

			Query query = em.createNativeQuery(sql.toString());

			Iterator<?> it = query.getResultList().iterator();
			
			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {
					ParticipanteReuniaoSiacolDto participante = new ParticipanteReuniaoSiacolDto();
					
					Object[] result = (Object[]) it.next();
					
					BigDecimal id = (BigDecimal) result[0];
					participante.setId(new Long(id.setScale(0, BigDecimal.ROUND_UP).longValueExact()));
					
					BigDecimal idCargo = (BigDecimal) result[1];
					participante.setIdCargo(new Long(idCargo.setScale(0, BigDecimal.ROUND_UP).longValueExact()));
					
					participante.setDescricaoCargo(result[2] == null ? "" : result[2].toString());

					listaMembrosDiretoria.add(participante);
				}
			}
		
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("PersonalidadeSiacolDao || getDiretoria", StringUtil.convertObjectToJson("sem parametros"), e);
		}

		return listaMembrosDiretoria;
		
	}

	public void redefineSenha(ParticipanteReuniaoSiacolDto participante) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE PER_PERSONALIDADES P          ");
		sql.append("    SET P.SENHA = :senha              ");
		sql.append("  WHERE P.CODIGO = :idPersonalidade   ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("senha", HashUtil.criptografa(participante.getSenha().toString()));
			query.setParameter("idPersonalidade", participante.getId());

			query.executeUpdate();
		
		} catch (Throwable e) {
			httpGoApi.geraLog("PersonalidadeSiacolDao || redefineSenha", StringUtil.convertObjectToJson(participante), e);
		}
		
	}

	public void atualizaNomeGuerra(ParticipanteReuniaoSiacolDto participante) {
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE PER_PERSONALIDADES P          ");
		sql.append("    SET P.NOME_GUERRA = :nomeGuerra   ");
		sql.append("  WHERE P.CODIGO = :idPersonalidade   ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("nomeGuerra", participante.getNomeGuerra());
			query.setParameter("idPersonalidade", participante.getId());

			query.executeUpdate();
		
		} catch (Throwable e) {
			httpGoApi.geraLog("PersonalidadeSiacolDao || redefineSenha", StringUtil.convertObjectToJson(participante), e);
		}		
	}

	public boolean senhaEstaEmUso(Long senha) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT P.SENHA FROM PER_PERSONALIDADES P    ");
		sql.append("  WHERE P.SENHA = :senha                     ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("senha", HashUtil.criptografa(senha.toString()));

			Object senhaEmUso = query.getSingleResult();
			return senhaEmUso != null;
		
		} catch (NoResultException e) {
			return false;
		} catch (Throwable e) {
			httpGoApi.geraLog("PersonalidadeSiacolDao || senhaEstaEmUso", StringUtil.convertObjectToJson(senha), e);
		}
		
		return false;
	}
	
	public String getDescricaoCargoPorIdDepartamento(Long idConselheiro, Long idDepartamento) {
		
		String descricaoCargo = new String();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT DISTINCT C.DESCRICAO                                                    ");
		sql.append("   FROM PER_CARGO_CONSELHEIRO P                                                 ");
		sql.append("   JOIN PER_CARGOS C ON (C.CODIGO = P.FK_CARGO_RAIZ)                            ");
		sql.append("	WHERE TO_CHAR(P.DATAFINALCARGO, 'YYYYMMDD') >= TO_CHAR(SYSDATE, 'YYYYMMDD') ");
		sql.append("	  AND P.REMOVIDO = 0                                                        ");
		sql.append("      AND P.FK_DEPARTAMENTO = :idDepartamento                                   ");
		sql.append("	  AND P.DATADESLIGAMENTOCARGO IS NULL                                       ");
		sql.append("	  AND P.FK_CODIGO_PERSONALIDADE = :idConselheiro                            ");
		sql.append("	  AND ROWNUM = 1                                                            ");
	
		try {
	
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idConselheiro", idConselheiro);
			query.setParameter("idDepartamento", idDepartamento);
			
			descricaoCargo = (String) query.getSingleResult();
		
		} catch (NoResultException e) {
			return "";
		} catch (Throwable e) {
			httpGoApi.geraLog("PersonalidadeSiacolDao || getDescricaoCargoPorIdDepartamento", StringUtil.convertObjectToJson(idConselheiro + " -- " + idDepartamento), e);
		}
		
		return descricaoCargo;
	}

	public List<Long> getDepartamentoByIdConselheiro(Long idConselheiro) {
		
		List<Long> listaIdDepartamentos = new ArrayList<Long>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT DISTINCT P.FK_DEPARTAMENTO                                              ");
		sql.append("   FROM PER_CARGO_CONSELHEIRO P                                                 ");
		sql.append("	WHERE TO_CHAR(P.DATAFINALCARGO, 'YYYYMMDD') >= TO_CHAR(SYSDATE, 'YYYYMMDD') ");
		sql.append("	  AND P.REMOVIDO = 0                                                        ");
		sql.append("	  AND P.DATADESLIGAMENTOCARGO IS NULL                                       ");
		sql.append("	  AND P.FK_CODIGO_PERSONALIDADE = :idConselheiro                            ");
	
		try {
	
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idConselheiro", idConselheiro);
			
			Iterator<?> it = query.getResultList().iterator();
			
			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {
					
					BigDecimal id = (BigDecimal) it.next();
					Long idDepartamento = new Long(id.setScale(0, BigDecimal.ROUND_UP).longValueExact());
	
					listaIdDepartamentos.add(idDepartamento);
				}
			}
		
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("PersonalidadeSiacolDao || getDescricaoCargoPorIdDepartamento", StringUtil.convertObjectToJson(idConselheiro), e);
		}
		
		return listaIdDepartamentos;
	}

	public Boolean ehSuplente(Long idConselheiro, Long statusLicenca) {
		Long idSuplente = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT C.FK_CODIGO_SUPLENTE FROM PER_CONSELHEIRO_REGIONAL C    ");
		sql.append("  WHERE C.FK_CODIGO_SUPLENTE = :idConselheiro                   ");
		sql.append("    AND C.REMOVIDO = 0                                          ");
		sql.append("    AND C.FINALIZADO = 0                                        ");
		if (!statusLicenca.equals(new Long(99))) {
			sql.append("    AND C.LICENCIADO = :statusLicenca                       ");
		}
		
		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idConselheiro", idConselheiro);
			if (!statusLicenca.equals(new Long(99))) {
				query.setParameter("statusLicencia", statusLicenca);				
			}

			BigDecimal id = (BigDecimal) query.getSingleResult();
			idSuplente =  new Long(id.setScale(0, BigDecimal.ROUND_UP).longValueExact());
		
		} catch (NoResultException e) {
			return false;
		} catch (Throwable e) {
			httpGoApi.geraLog("PersonalidadeSiacolDao || ehSuplenteDoLicenciado", StringUtil.convertObjectToJson(idConselheiro), e);
		}
		
		return idSuplente != null;
	}
	
	public List<Long> getSuplentesDaComissao(Long idComissao) {
		List<Long> listaIdsEfetivos = new ArrayList<Long>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT DISTINCT P.FK_CODIGO_PERSONALIDADE FROM PER_CARGO_CONSELHEIRO P         "); 
		sql.append("	WHERE TO_CHAR(P.DATAFINALCARGO, 'YYYYMMDD') >= TO_CHAR(SYSDATE, 'YYYYMMDD') ");
		sql.append("	  AND P.REMOVIDO = 0                                                        ");
		sql.append("      AND P.FK_DEPARTAMENTO = :idDepartamento                                   ");
		sql.append("	  AND P.DATADESLIGAMENTOCARGO IS NULL                                       ");
		sql.append("	  AND P.FK_CARGO_RAIZ IN (2929, 2930, 2931, 2932, 2933, 2937, 2938)         ");
		sql.append("	  AND P.FK_CODIGO_PERSONALIDADE IN (:idsPersonalidade)                      ");
	
		try {
	
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idDepartamento", idComissao);
	
			Iterator<?> it = query.getResultList().iterator();
			
			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {
					
					BigDecimal id = (BigDecimal) it.next();
					Long idEfetivo = new Long(id.setScale(0, BigDecimal.ROUND_UP).longValueExact());
	
					listaIdsEfetivos.add(idEfetivo);
				}
			}
		
		} catch (Throwable e) {
			httpGoApi.geraLog("PersonalidadeSiacolDao || getSuplentesDaComissao", StringUtil.convertObjectToJson(idComissao), e);
		}
		
		return listaIdsEfetivos;
	}
	
	public List<Long> getSuplentesDosLicenciadosPor(Long idDepartamento) {

		List<Long> listaIdSuplentesDosLicenciados = new ArrayList<Long>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT DISTINCT C.FK_CODIGO_SUPLENTE FROM PER_CONSELHEIRO_REGIONAL C                ");
		sql.append("  WHERE C.FK_CODIGO_PERSONALIDADE IN                                                 ");
		sql.append("     (SELECT C.FK_CODIGO_PERSONALIDADE FROM PER_CARGO_CONSELHEIRO P                  ");
		sql.append("        WHERE P.REMOVIDO = 0                                                         ");
		if (idDepartamento.longValue() != new Long (11)) {
			sql.append("        AND P.FK_DEPARTAMENTO = :idDepartamento                                  ");
		}
		sql.append("          AND P.DATADESLIGAMENTOCARGO IS NULL                                        ");
		sql.append("          AND TO_CHAR(P.DATAFINALCARGO, 'YYYYMMDD') >= TO_CHAR(SYSDATE, 'YYYYMMDD')  ");
		sql.append("          AND P.FK_CARGO_RAIZ IN (2562, 2560, 2559, 49) )                            ");
		sql.append("    AND C.FK_CODIGO_SUPLENTE IS NOT NULL                                             ");
		sql.append("    AND C.REMOVIDO = 0                                                               ");
		sql.append("    AND C.FINALIZADO = 0                                                             ");
		sql.append("    AND C.LICENCIADO = 1                                                             ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			if (idDepartamento.longValue() != new Long (11)) {
				query.setParameter("idDepartamento", idDepartamento);
			}
			
			Iterator<?> it = query.getResultList().iterator();
			
			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {
					
					BigDecimal id = (BigDecimal) it.next();

					Long idConselheiro = new Long(id.setScale(0, BigDecimal.ROUND_UP).longValueExact());

					listaIdSuplentesDosLicenciados.add(idConselheiro);
				}
			}
		
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("PersonalidadeSiacolDao || getSuplentesDosLicenciadosPor", StringUtil.convertObjectToJson(idDepartamento), e);
		}
		
		return listaIdSuplentesDosLicenciados;
	}
	
	public List<Long> getSuplentesPor(List<Long> listaIdConselheirosEfetivos) {

		List<Long> listaIdSuplentes = new ArrayList<Long>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT DISTINCT C.FK_CODIGO_SUPLENTE FROM PER_CONSELHEIRO_REGIONAL C ");
		sql.append("  WHERE C.FK_CODIGO_PERSONALIDADE IN (:listaIdConselheirosEfetivos)   ");
		sql.append("    AND C.FK_CODIGO_SUPLENTE IS NOT NULL                              ");
		sql.append("    AND C.REMOVIDO = 0                                                ");
		sql.append("    AND C.FINALIZADO = 0                                              ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("listaIdConselheirosEfetivos", listaIdConselheirosEfetivos);		
			
			Iterator<?> it = query.getResultList().iterator();
			
			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {
					
					BigDecimal id = (BigDecimal) it.next();

					Long idConselheiro = new Long(id.setScale(0, BigDecimal.ROUND_UP).longValueExact());

					listaIdSuplentes.add(idConselheiro);
				}
			}
		
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("PersonalidadeSiacolDao || getSuplentesPor", StringUtil.convertObjectToJson(listaIdConselheirosEfetivos), e);
		}
		
		return listaIdSuplentes;
	}

	public List<Long> getConselheirosEfetivosDoSuplentes(List<Long> idsSuplentes) {
		
		List<Long> listaIdEfetivos = new ArrayList<Long>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT FK_CODIGO_PERSONALIDADE FROM PER_CONSELHEIRO_REGIONAL C               ");
		sql.append("  WHERE C.FK_CODIGO_SUPLENTE IN (:idsSuplentes)                               ");
		sql.append("	AND C.REMOVIDO   = 0                                                      ");
		sql.append("	AND C.FINALIZADO = 0                                                      ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idsSuplentes", idsSuplentes);

			Iterator<?> it = query.getResultList().iterator();
			
			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {
					
					BigDecimal id = (BigDecimal) it.next();

					Long idConselheiro = new Long(id.setScale(0, BigDecimal.ROUND_UP).longValueExact());

					listaIdEfetivos.add(idConselheiro);
				}
			}
		
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("PersonalidadeSiacolDao || getConselheirosEfetivosDoSuplentes", StringUtil.convertObjectToJson(idsSuplentes), e);
		}
		
		return listaIdEfetivos;
	}
}
