package br.org.crea.commons.dao.cadastro.funcionario;

import java.io.Serializable;
import java.util.ArrayList;
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
import br.org.crea.commons.models.corporativo.pessoa.Funcionario;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;


@Stateless
public class FuncionarioDao extends GenericDao<Funcionario, Serializable>  {

	@Inject HttpClientGoApi httpGoApi;
	
	@Inject DepartamentoDao departamentoDao;
	
	public FuncionarioDao() {
		super(Funcionario.class);
	}
	
	public List<Funcionario> getAll(Long matricula){
		
		List<Funcionario> listFuncionario = new ArrayList<Funcionario>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT F FROM Funcionario F ");
		sql.append("LEFT JOIN FETCH F.pessoaFisica ");
		sql.append("WHERE F.matricula = :matricula");

		try {
			TypedQuery<Funcionario> query = em.createQuery(sql.toString(), Funcionario.class);
			query.setParameter("matricula", matricula);
			
			listFuncionario = query.getResultList();			
		} catch (Throwable e) {
			httpGoApi.geraLog("FuncionarioDao || Get All", StringUtil.convertObjectToJson(matricula), e);
		}
		
		return listFuncionario;
	}
	
	public Funcionario getFuncionarioBy(Long matricula){
		
		Funcionario funcionario = new Funcionario();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT F FROM Funcionario F ");
		sql.append("	LEFT JOIN FETCH F.pessoaFisica ");
		sql.append("WHERE F.matricula = :matricula");

		try {
			TypedQuery<Funcionario> query = em.createQuery(sql.toString(), Funcionario.class);
			query.setParameter("matricula", matricula);
			
			funcionario = query.getSingleResult();
		} catch (Throwable e) {
			httpGoApi.geraLog("FuncionarioDao || Get Funcionario By", StringUtil.convertObjectToJson(matricula), e);
		}
		
		return funcionario;
	}
	
	public Funcionario getFuncionarioPor(Long idFuncionario){
		
		Funcionario funcionario = new Funcionario();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT F FROM Funcionario F ");
		sql.append("	LEFT JOIN FETCH F.pessoaFisica ");
		sql.append("WHERE F.id = :idFuncionario");

		try {
			TypedQuery<Funcionario> query = em.createQuery(sql.toString(), Funcionario.class);
			query.setParameter("idFuncionario", idFuncionario);
			
			funcionario = query.getSingleResult();
		} catch (Throwable e) {
			httpGoApi.geraLog("FuncionarioDao || getFuncionarioPor", StringUtil.convertObjectToJson(idFuncionario), e);
		}
		
		return funcionario;
	}
	
	public Funcionario getFuncionarioByPessoa(Long idPessoa){
		
		Funcionario funcionario = new Funcionario();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT F FROM Funcionario F          ");
		sql.append("  WHERE F.pessoaFisica.id = :idPessoa ");
		sql.append("	AND F.cadastroAtivo = 1           ");
		
		try {
			TypedQuery<Funcionario> query = em.createQuery(sql.toString(), Funcionario.class);
			query.setParameter("idPessoa", idPessoa);
			
			funcionario = query.getSingleResult();
			
		} catch (NoResultException e) {
			return funcionario;
		} catch (Throwable e) {
			httpGoApi.geraLog("FuncionarioDao || Get Funcionario By Pessoa", StringUtil.convertObjectToJson(idPessoa), e);
		}

		return funcionario;
	}
	
	public List<Funcionario> getFuncionariosByDepartamento(PesquisaGenericDto pesquisa){
		
		List<Funcionario> listFuncionario = new ArrayList<Funcionario>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT F FROM Funcionario F");
		sql.append("	LEFT JOIN FETCH F.pessoaFisica");
		sql.append("	WHERE F.departamento.id = :idDepartamento");
		sql.append("	AND F.cadastroAtivo = true");
		sql.append("	ORDER BY F.pessoaFisica.nomePessoaFisica ");

		try {
			TypedQuery<Funcionario> query = em.createQuery(sql.toString(), Funcionario.class);
			query.setParameter("idDepartamento", pesquisa.getIdDepartamento());
			
			listFuncionario = query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("FuncionarioDao || Get Funcionario By Departamento", StringUtil.convertObjectToJson(pesquisa.getIdDepartamento()), e);
		}
		return listFuncionario;
	}


	public List<Funcionario> getFuncionariosPorNome(PesquisaGenericDto pesquisa){
		
		List<Funcionario> listFuncionario = new ArrayList<Funcionario>();
		
		try {
			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT F FROM Funcionario F");
			sql.append("	WHERE F.pessoaFisica.nomePessoaFisica LIKE :nomePessoa");
			sql.append("	AND F.cadastroAtivo = true");
			sql.append("	ORDER BY F.pessoaFisica.nomePessoaFisica ");
	
			TypedQuery<Funcionario> query = em.createQuery(sql.toString(), Funcionario.class);
			query.setParameter("nomePessoa", "%" + pesquisa.getNomePessoa().toUpperCase() + "%");
			
			listFuncionario = query.getResultList();
			
			
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("FuncionarioDao || getFuncionariosPorNome", StringUtil.convertObjectToJson(pesquisa.getNomePessoa()), e);
		}
		return listFuncionario;
	}
	
	public boolean funcionarioPossuiPermissaoTramiteNaUnidade(PesquisaGenericDto pesquisa) {
		
		List<Departamento> listUnidades = departamentoDao.getListUnidadesTramitacaoPor(pesquisa.getIdFuncionario());
		boolean possuiPermissao = false;
		
		for (Departamento unidade : listUnidades) {
			
			if(pesquisa.getIdDepartamento().equals(unidade.getId())) {
				possuiPermissao = true;
				break;
			}
		}
		
		return possuiPermissao;
	}
	
	public boolean temPermissaoProtocolo(Long idFuncionario, Long idPermissao) {
		
		try {
			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM prt_autorizacoes P ");
			sql.append("	WHERE P.fk_id_permissoes = :idPermissao ");
			sql.append("	AND   P.fk_id_funcionarios = :idFuncionario ");
	
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idPermissao", idPermissao);
			query.setParameter("idFuncionario", idFuncionario);
			
			return query.getResultList().isEmpty() ? false : true;
			
		} catch (NoResultException e) {
			return false;
		} catch (Throwable e) {
			httpGoApi.geraLog("FuncionarioDao || temPermissaoProtocolo", StringUtil.convertObjectToJson(idFuncionario), e);
		}
		return false;
	}

	public Funcionario getFuncionariosPorMatricula(Long matricula) {
		Funcionario funcionario = new Funcionario();
		
		try {
			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT F FROM Funcionario F");
			sql.append("	WHERE F.matricula = :matricula");
			sql.append("	AND F.cadastroAtivo = true");
	
			TypedQuery<Funcionario> query = em.createQuery(sql.toString(), Funcionario.class);
			query.setParameter("matricula", matricula);
			
			funcionario = query.getSingleResult();
			
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("FuncionarioDao || getFuncionariosPorMatricula", StringUtil.convertObjectToJson(matricula), e);
		}
		return funcionario;
	}

}
