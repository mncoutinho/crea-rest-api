package br.org.crea.commons.dao.cadastro;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.org.crea.commons.models.cadastro.Departamento;
import br.org.crea.commons.models.cadastro.EmailPessoa;
import br.org.crea.commons.models.cadastro.QuadroTecnico;
import br.org.crea.commons.models.cadastro.UnidadeAtendimento;
import br.org.crea.commons.models.corporativo.pessoa.Funcionario;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class CadastroDao{
	


	@Inject HttpClientGoApi httpGoApi;

	
	@PersistenceContext(unitName = "dscrea")
	protected EntityManager em;
	
	
	public List<QuadroTecnico> getQuadroBy(Long idPessoa, String type){
		
		List<QuadroTecnico> listQuadroTecnico = new ArrayList<QuadroTecnico>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT Q FROM QuadroTecnico Q ");
		
		if(type.equals("EMPRESA")){
			sql.append("	WHERE Q.empresa.id = :idPessoa ");
		}
		
		if(type.equals("PROFISSIONAL")){
			sql.append("	WHERE Q.profissional.id = :idPessoa ");
		}

		try {
			TypedQuery<QuadroTecnico> query = em.createQuery(sql.toString(), QuadroTecnico.class);
			query.setParameter("idPessoa", idPessoa);
			
			listQuadroTecnico = query.getResultList();			
		} catch (Throwable e) {
			httpGoApi.geraLog("CadastroDao || Get Quadro By", StringUtil.convertObjectToJson(idPessoa), e);
		}
		return listQuadroTecnico;
	
	}
	
	public String getEmailsBy(Long idPessoa){
		
		List<EmailPessoa> emails = new ArrayList<EmailPessoa>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT E FROM EmailPessoa E ");
		sql.append("     WHERE E.pessoa.id = :idPessoa ");
		sql.append("    order by E.id desc ");
		
		try {
			TypedQuery<EmailPessoa> query = em.createQuery(sql.toString(), EmailPessoa.class);
			query.setParameter("idPessoa", idPessoa);
			
			emails = query.getResultList();	
			if(!emails.isEmpty()){
				return emails.get(0).getDescricao();
			}else{
				return "";
			}
		} catch (Throwable e) {
			httpGoApi.geraLog("CadastroDao || Get descricao email by", StringUtil.convertObjectToJson(idPessoa), e);
		}
		
		return "";
	}
	
	public List<UnidadeAtendimento> getUnidadesAtendimento() {
		
		List<UnidadeAtendimento> listUnidadeAtendimento = new ArrayList<UnidadeAtendimento>();
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT U FROM UnidadeAtendimento U ");
		sql.append("	ORDER BY U.nome ");
		
		try {
			TypedQuery<UnidadeAtendimento> query = em.createQuery(sql.toString(), UnidadeAtendimento.class);
			
			listUnidadeAtendimento = query.getResultList();		
		} catch (Throwable e) {
			httpGoApi.geraLog("CadastroDao || Get Unidades Atendimento", StringUtil.convertObjectToJson("SEM PARAMETRO"), e);
		}
		
		return listUnidadeAtendimento;
	}
	
	
	public List<Departamento> getUnidadesCoordenacaoPor(Long matricula) {
		
		List<Departamento> unidadesAtendimento = new ArrayList<Departamento>();
		
		StringBuilder sqlFuncionario = new StringBuilder();
		sqlFuncionario.append("SELECT F FROM Funcionario F ");
		sqlFuncionario.append("	 	     WHERE 	F.matricula = :matricula ");
	
		TypedQuery<Funcionario> query = em.createQuery(sqlFuncionario.toString(), Funcionario.class);
		query.setParameter("matricula", matricula);
		
		Funcionario func = query.getSingleResult();
		
		Departamento departamento = func.getDepartamento();			
				
		StringBuilder sqlDepartamento = new StringBuilder();
		sqlDepartamento.append("SELECT D FROM   Departamento D ");
		sqlDepartamento.append("	 	     WHERE D.departamentoPai.id = :departamentoPai ");
		sqlDepartamento.append("	 	     AND 	D.removido  = false ");
		sqlDepartamento.append("	 	     AND 	D.atendimento  = true ");
		
		
		try {
			TypedQuery<Departamento> queryDepartamento = em.createQuery(sqlDepartamento.toString(), Departamento.class);
			queryDepartamento.setParameter("departamentoPai", departamento.getDepartamentoPai().getId());
			
			unidadesAtendimento = queryDepartamento.getResultList();

		} catch (Throwable e) {
			httpGoApi.geraLog("CadastroDao || Get Unidades Coordenacao Por", StringUtil.convertObjectToJson(matricula), e);
		}
		return unidadesAtendimento;
		
	}
	
	public List<Departamento> getUnidadesAtendimentoRegionalPor(Long matricula){
		
		List<Departamento> unidadesAtendimentoFuncionario = new ArrayList<Departamento>();
		
		StringBuilder sqlFuncionario = new StringBuilder();
		sqlFuncionario.append("SELECT F FROM Funcionario F ");
		sqlFuncionario.append("	 	     WHERE 	F.matricula = :matricula ");
	
		TypedQuery<Funcionario> query = em.createQuery(sqlFuncionario.toString(), Funcionario.class);
		query.setParameter("matricula", matricula);
		
		Funcionario funcionario = query.getSingleResult();
		Departamento regionalFuncionario = funcionario.getDepartamento();	
		
		StringBuilder sqlDepartamentosPorRegional = new StringBuilder();
		sqlDepartamentosPorRegional.append("SELECT D FROM   Departamento D ");
		sqlDepartamentosPorRegional.append("	 	     WHERE 	(D.departamentoPai.id = :departamentoPai OR D.departamentoPai.id = :departamento) ");
		sqlDepartamentosPorRegional.append("	 	     AND 	D.removido  = false ");
		sqlDepartamentosPorRegional.append("	 	     AND 	D.atendimento  = true ");
		
		try {
			
			TypedQuery<Departamento> queryDepartamento = em.createQuery(sqlDepartamentosPorRegional.toString(), Departamento.class);
			queryDepartamento.setParameter("departamentoPai", regionalFuncionario.getDepartamentoPai().getId());
			queryDepartamento.setParameter("departamento", regionalFuncionario.getId());
			
			unidadesAtendimentoFuncionario = queryDepartamento.getResultList();

		} catch (Throwable e) {
			httpGoApi.geraLog("CadastroDao || Get Unidades Atendimento Regional Por", StringUtil.convertObjectToJson(matricula), e);
		}
		return unidadesAtendimentoFuncionario;
	}
	

	public List<Departamento> getUnidadesCoordenacaoSeat() {
		
		List<Departamento> unidadesAtendimento = new ArrayList<Departamento>();
		
		StringBuilder sqlFuncionario = new StringBuilder();
		sqlFuncionario.append("SELECT F FROM Funcionario F ");
		sqlFuncionario.append("	 	     WHERE 	F.matricula = :matricula ");
	
		TypedQuery<Funcionario> query = em.createQuery(sqlFuncionario.toString(), Funcionario.class);
		query.setParameter("matricula", new Long (438));
		
		Departamento departamento = query.getSingleResult().getDepartamento();
		
		
		StringBuilder sqlDepartamento = new StringBuilder();
		sqlDepartamento.append("SELECT D FROM   Departamento D ");
		sqlDepartamento.append("	 	     WHERE 	D.departamentoPai.id = :departamentoPai");
		sqlDepartamento.append("	 	     AND 	D.removido  = false ");
		sqlDepartamento.append("	 	     AND 	D.atendimento  = true ");

		try {
			TypedQuery<Departamento> queryDepartamento = em.createQuery(sqlDepartamento.toString(), Departamento.class);
			queryDepartamento.setParameter("departamentoPai", departamento.getId());
			
			unidadesAtendimento = queryDepartamento.getResultList();	
		} catch (Throwable e) {
			httpGoApi.geraLog("CadastroDao || Get Unidades Coordenacao Seat", StringUtil.convertObjectToJson("SEM PARAMETRO"), e);
		}
		
		return unidadesAtendimento;
		
	}

	public List<Departamento> getAllUnidades() {
        
		List<Departamento> unidadesAtendimento = new ArrayList<Departamento>();
		
		
		StringBuilder sqlDepartamento = new StringBuilder();
		sqlDepartamento.append("SELECT D FROM   Departamento D ");
		sqlDepartamento.append("	 	     WHERE 	D.id NOT IN (15, 99999, 2302040101) AND  D.removido  = false AND D.atendimento  = true ");
		sqlDepartamento.append("	 	     ORDER BY D.nomeExibicao ");
		

		try {
			
			TypedQuery<Departamento> queryDepartamento = em.createQuery(sqlDepartamento.toString(), Departamento.class);
			
			unidadesAtendimento = queryDepartamento.getResultList();	
		} catch (Throwable e) {
			httpGoApi.geraLog("CadastroDao || Get All Unidades", StringUtil.convertObjectToJson("SEM PARAMETRO"), e);
		}
		
		return unidadesAtendimento;
	}
}
