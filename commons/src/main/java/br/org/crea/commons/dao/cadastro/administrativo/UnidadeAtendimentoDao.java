package br.org.crea.commons.dao.cadastro.administrativo;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.cadastro.Departamento;
import br.org.crea.commons.models.cadastro.UnidadeAtendimento;
import br.org.crea.commons.models.corporativo.pessoa.Funcionario;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class UnidadeAtendimentoDao extends GenericDao<UnidadeAtendimento, Serializable> {
	
	@Inject HttpClientGoApi httpGoApi;
	
	public UnidadeAtendimentoDao() {
		super(UnidadeAtendimento.class);
	}
	
	public Departamento getUnidadeAtendimentoByFuncionario(Funcionario funcionario){
		
		Departamento departamento = new Departamento();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT D FROM Departamento D ");
		sql.append("	WHERE D.id = :idDepartamento");

		try {

			TypedQuery<Departamento> query = em.createQuery(sql.toString(), Departamento.class);
			query.setParameter("idDepartamento", funcionario.getDepartamento().getId());

			departamento =  query.getSingleResult();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("UnidadeAtendimentoDao || Get Unidade Atendimento By Funcionario", StringUtil.convertObjectToJson(funcionario), e);
			return null;
		}
		return departamento;
	}
	public UnidadeAtendimento getUnidadeById(Long idUnidade){
		
		UnidadeAtendimento unidade = new UnidadeAtendimento();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT D FROM UnidadeAtendimento D ");
		sql.append("	WHERE D.id = :idUnidade");

		try {

			TypedQuery<UnidadeAtendimento> query = em.createQuery(sql.toString(), UnidadeAtendimento.class);
			query.setParameter("idUnidade",idUnidade );

			unidade =  query.getSingleResult();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("UnidadeAtendimentoDao || getUnidadeById", StringUtil.convertObjectToJson(idUnidade), e);
		}
		return unidade;
		
		
		
	}

}
