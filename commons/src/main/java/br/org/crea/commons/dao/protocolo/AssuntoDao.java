package br.org.crea.commons.dao.protocolo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.corporativo.Assunto;
import br.org.crea.commons.models.corporativo.dtos.AssuntoDto;
import br.org.crea.commons.models.corporativo.dtos.FuncionarioDto;
import br.org.crea.commons.models.siacol.AssuntoSiacol;
import br.org.crea.commons.models.siacol.dtos.PesquisaRelatorioSiacolDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;


public class AssuntoDao extends GenericDao<Assunto, Serializable> {
	
	@Inject HttpClientGoApi httpGoApi;

	public AssuntoDao() {
		super(Assunto.class);
	}
	
	public Assunto getAssuntoBy(Long id){
		
		Assunto assunto = new Assunto();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM PRT_ASSUNTOS A");
		sql.append("	WHERE A.id = :id ");

		try {
			Query query = em.createNativeQuery(sql.toString(), Assunto.class);
			query.setParameter("id", id);
			
			assunto = (Assunto) query.getSingleResult();
		} catch (Throwable e) {
			httpGoApi.geraLog("AssuntoDao || Get Assunto By", StringUtil.convertObjectToJson(id), e);

		}
		
		return assunto;
	}
	

	@SuppressWarnings("unchecked")
	public List<Assunto> getAssuntosDisponiveis(Long idBloco){
		
		List<Assunto> assunto = new ArrayList<Assunto>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM PRT_ASSUNTOS A ");  
		sql.append("  WHERE A.removido = 0 AND "); 
		sql.append("  A.FK_ID_BLOCOSASSUNTOS = :idBloco ");

		try {
			Query query = em.createNativeQuery(sql.toString(), Assunto.class);
			query.setParameter("idBloco", idBloco);
			
			assunto = query.getResultList();

		} catch (Throwable e) {
			httpGoApi.geraLog("AssuntoDao || getAssuntosDisponiveis", "sem parametro",  e);
		}
		
		return assunto;
	}
	
	public Assunto getAssuntoPor(Long codigo) {
		Assunto assunto = new Assunto();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT A FROM Assunto A ");
		sql.append("	WHERE A.codigo = :codigo ");

		try {
			TypedQuery<Assunto> query = em.createQuery(sql.toString(), Assunto.class);
			query.setParameter("codigo", codigo);
			
			assunto = query.getSingleResult();
			
		}catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("AssuntoDao || getAssuntoBy", StringUtil.convertObjectToJson(codigo), e);
		}
		return assunto;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Assunto> getAssuntosComDocumentacao(){
		
		List<Assunto> assunto = new ArrayList<Assunto>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM PRT_ASSUNTOS A");
		sql.append("	WHERE A.removido = 0 AND ");
		sql.append("	A.removido = 0 AND ");
		sql.append("	Exists ( SELECT FK_ID_PRT_ASSUNTOS ");
		sql.append("		FROM CAD_RL_ASSUNTOS_DOCUMENTACAO CRA where CRA.FK_ID_PRT_ASSUNTOS = A.CODIGO ) ");
		sql.append("	ORDER BY A.descricao ");

		try {
			Query query = em.createNativeQuery(sql.toString(), Assunto.class);
			
			assunto = query.getResultList();

		} catch (Throwable e) {
			httpGoApi.geraLog("AssuntoDao || getAssuntosDisponiveis", "sem parametro",  e);

		}
		
		return assunto;
	}

	public List<Assunto> getAssuntoSiacol() {
		List<Assunto> listAssunto = new ArrayList<Assunto>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT A FROM Assunto A     ");
		sql.append("  WHERE A.removido = 0       ");
		sql.append("	AND A.siacol = 1         ");

		try {
			TypedQuery<Assunto> query = em.createQuery(sql.toString(), Assunto.class);
			
			listAssunto = query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("AssuntoDao || getAssuntoSiacol", "SEM PARAMETRO", e);

		}
		
		return listAssunto;
	}

	public List<Assunto> getAssuntoSiacolHabilitadoFuncionario(FuncionarioDto dto) {
		List<Assunto> listAssunto = new ArrayList<Assunto>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT A FROM Assunto A   ");  
		sql.append("  WHERE A.id IN (SELECT H.assunto.id FROM HabilidadePessoaSiacol H "); 
		sql.append("  					WHERE H.pessoa.id = :idFuncionario AND H.departamento.id = :idDepartamento ");
		sql.append("  						AND H.assunto.id is not null ) ");
		sql.append("	AND A.removido = 0 ");
		sql.append("	AND A.siacol = 1 ");

		try {
			TypedQuery<Assunto> query = em.createQuery(sql.toString(), Assunto.class);
			query.setParameter("idFuncionario", dto.getId());
			query.setParameter("idDepartamento", dto.getIdDepartamento());

			listAssunto = query.getResultList();
		} catch (NoResultException e){
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("AssuntoDao || getAssuntoSiacolHabilitadoIdFuncionario", "SEM PARAMETRO", e);

		}
		
		return listAssunto;
	}

	public List<Assunto> getAssuntoSiacolDesabilitadoFuncionario(FuncionarioDto dto) {
		List<Assunto> listAssunto = new ArrayList<Assunto>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT A FROM Assunto A   ");  
		sql.append("  WHERE A.id NOT IN (SELECT H.assunto.id FROM HabilidadePessoaSiacol H "); 
		sql.append("  					WHERE H.pessoa.id = :idFuncionario AND H.departamento.id = :idDepartamento ");
		sql.append("  						AND H.assunto.id is not null ) ");
		sql.append("	AND A.removido = 0 ");
		sql.append("	AND A.siacol = 1 ");

		try {
			TypedQuery<Assunto> query = em.createQuery(sql.toString(), Assunto.class);
			query.setParameter("idFuncionario", dto.getId());
			query.setParameter("idDepartamento", dto.getIdDepartamento());

			listAssunto = query.getResultList();
		} catch (NoResultException e){
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("AssuntoDao || getAssuntoSiacolHabilitadoIdFuncionario", "SEM PARAMETRO", e);

		}
		
		return listAssunto;
	}
	
	public List<AssuntoSiacol> getAssuntoSiacolHabilitadoConselheiro(FuncionarioDto dto) {
		List<AssuntoSiacol> listAssunto = new ArrayList<AssuntoSiacol>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT A FROM AssuntoSiacol A   ");  
		sql.append("  WHERE A.id IN (SELECT H.assuntoSiacol.id FROM HabilidadePessoaSiacol H "); 
		sql.append("  					WHERE H.pessoa.id = :idFuncionario AND H.departamento.id = :idDepartamento ");
		sql.append("  						AND H.assuntoSiacol.id is not null ) ");
		sql.append("	AND A.ativo = 1 ");
		
		try {
			TypedQuery<AssuntoSiacol> query = em.createQuery(sql.toString(), AssuntoSiacol.class);
			query.setParameter("idFuncionario", dto.getId());
			query.setParameter("idDepartamento", dto.getIdDepartamento());

			listAssunto = query.getResultList();
		} catch (NoResultException e){
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("AssuntoDao || getAssuntoSiacolHabilitadoIdConselheiro", "SEM PARAMETRO", e);

		}
		
		return listAssunto;
	}
	
	public List<AssuntoSiacol> getAssuntoSiacolDesabilitadoConselheiro(FuncionarioDto dto) {
		List<AssuntoSiacol> listAssunto = new ArrayList<AssuntoSiacol>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT A FROM AssuntoSiacol A   ");  
		sql.append("  WHERE A.id NOT IN (SELECT H.assuntoSiacol.id FROM HabilidadePessoaSiacol H "); 
		sql.append("  					WHERE H.pessoa.id = :idFuncionario AND H.departamento.id = :idDepartamento ");
		sql.append("  						AND H.assuntoSiacol.id is not null ) ");
		sql.append("	AND A.ativo = 1 ");	

		try {
			TypedQuery<AssuntoSiacol> query = em.createQuery(sql.toString(), AssuntoSiacol.class);
			query.setParameter("idFuncionario", dto.getId());
			query.setParameter("idDepartamento", dto.getIdDepartamento());

			listAssunto = query.getResultList();
		} catch (NoResultException e){
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("AssuntoDao || getAssuntoSiacolHabilitadoIdConselheiro", "SEM PARAMETRO", e);

		}
		
		return listAssunto;
	}


	public List<Assunto> getAssuntos() {
		List<Assunto> listAssunto = new ArrayList<Assunto>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT A FROM Assunto A ");
		sql.append("	WHERE A.removido = 0 ");

		try {
			TypedQuery<Assunto> query = em.createQuery(sql.toString(), Assunto.class);
			
			listAssunto = query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("AssuntoDao || getAssuntos", "SEM PARAMETRO", e);

		}
		
		return listAssunto;
	}

	@Transactional()
	public void atualizaAssunto(AssuntoDto dto) {

		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE PRT_ASSUNTOS PA "); 
		sql.append("	SET PA.SIACOL = :siacol ");
		sql.append("		WHERE PA.ID = :idAssunto "); 

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("siacol", dto.getSiacol());
			query.setParameter("idAssunto", dto.getId());

			query.executeUpdate();
		} catch (Throwable e) {
			httpGoApi.geraLog("AssuntoDao || atualizaAssunto", StringUtil.convertObjectToJson(dto), e);

		}

	}

	public List<Assunto> getAssuntoSiacolByIdPessoaRelatorioNove(PesquisaRelatorioSiacolDto pesquisa) {
		
		List<Assunto> listaDeAssuntos = new ArrayList<Assunto>();
		List<Long> listaIdsDeAssuntosCorporativos = new ArrayList<Long>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT P.idAssuntoCorportativo FROM ProtocoloSiacol P 	");
		sql.append("	WHERE P.idResponsavel = :idResponsavel 				");
		if (!pesquisa.isTodosDepartamentos()) {
			sql.append("  	AND P.departamento.id in (:idDepartamento)			");
		}
		sql.append("    AND P.assuntoSiacol IS NULL           				");
		sql.append("	AND P.status in ('AGUARDANDO_RECEBIMENTO', 'ANALISE', 'DISTRIBUICAO_ANALISTA') ");
		sql.append("	GROUP BY P.idAssuntoCorportativo 					");

		try {
			TypedQuery<Long> query = em.createQuery(sql.toString(), Long.class);
			query.setParameter("idResponsavel", pesquisa.getResponsaveis().get(0).getId());
			if (!pesquisa.isTodosDepartamentos()) {
				query.setParameter("idDepartamento", pesquisa.getListaIdsDepartamentos());
			}
			
			
			listaIdsDeAssuntosCorporativos = query.getResultList();
			
			for (Long idAssuntoCorporativo : listaIdsDeAssuntosCorporativos) {
				listaDeAssuntos.add(getBy(idAssuntoCorporativo));
			}
			
		}catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("AssuntoDao || getAssuntoSiacolByIdPessoaRelatorioNove", StringUtil.convertObjectToJson(pesquisa), e);
		}
		return listaDeAssuntos;
		
	}

	public List<Assunto> getAssuntoByTipoAssunto(String tipoAssunto) {
		List<Assunto> listAssunto = new ArrayList<Assunto>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT A FROM Assunto A ");
		sql.append("	WHERE A.removido = 0 ");
		sql.append("	AND A.tipoAssunto = :tipoAssunto ");
		
		try {
			TypedQuery<Assunto> query = em.createQuery(sql.toString(), Assunto.class);
			query.setParameter("tipoAssunto", tipoAssunto);
			
			listAssunto = query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("AssuntoDao || getAssuntoByTipoAssunto", StringUtil.convertObjectToJson(tipoAssunto), e);

		}
		
		return listAssunto;
	}
	
	
}