package br.org.crea.commons.dao.cadastro.pessoa;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.commons.dtos.DomainGenericDto;
import br.org.crea.commons.models.corporativo.pessoa.PessoaFisica;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class PessoaFisicaDao extends GenericDao<PessoaFisica, Serializable> {
	
	@Inject HttpClientGoApi httpGoApi;

	public PessoaFisicaDao() {
		super(PessoaFisica.class);
	}
	
	public PessoaFisica getByCPF(String cpf) {
		//EJC 04-11-2010
		//O CPF nao � unico na base, logo para evitar (result returns x elements) e nao parar a aplicação do usuario optei por esta situação
		//Alertar ao usuario caso o retorno nao seja o esperado para tratar a informação na base antes de prosseguir com o cadastramento
		//da ART.
		PessoaFisica pessoaFisica = null;
		Query query = em.createQuery("from PessoaFisica p where p.cpf = ?1 ");
		query.setParameter(1, cpf); 
	    query.setMaxResults(1);	
		try{
			pessoaFisica = (PessoaFisica) query.getSingleResult();
		}catch (Throwable e) {
			httpGoApi.geraLog("PessoaDao || Busca Pessoa Jurídica por CPF ", cpf, e);
		}
		
		return pessoaFisica;
		
	}
	
	public PessoaFisica buscaPessoaFisicaById(long id) {
		PessoaFisica pessoaFisica = null;
		
		Query query = em.createQuery("from PessoaFisica p where p.id = ?1");
		query.setParameter(1, id); 
		
		try{
			pessoaFisica = (PessoaFisica) query.getSingleResult();
		}catch (NoResultException e) {
			return pessoaFisica;
		}catch (Throwable e) {
			httpGoApi.geraLog("PessoaDao || buscaPessoaFisicaById ", StringUtil.convertObjectToJson(id), e);
		}
		
		return pessoaFisica;
	}

	public DomainGenericDto getPisPasep(Long idPessoa) {
		
		DomainGenericDto dto = new DomainGenericDto();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT P.PISPASEP             ");
		sql.append("   FROM CAD_PESSOAS_FISICAS P  ");
		sql.append("  WHERE P.CODIGO = :idPessoa   ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idPessoa", idPessoa);
			
			Object pisPasep = (Object) query.getSingleResult();
			dto.setDescricao(pisPasep != null ? pisPasep.toString() : null);

		} catch (NoResultException e) {
			return dto;
		} catch (Throwable e) {
			httpGoApi.geraLog("PessoaFisicaDao || getPisPasep", StringUtil.convertObjectToJson(idPessoa), e);
		}
		
		return dto;
	}

	public void salvaPisPasep(DomainGenericDto dto) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE CAD_PESSOAS_FISICAS P   ");
		sql.append("    SET P.PISPASEP = :pisPasep  ");
		sql.append("  WHERE P.CODIGO = :idPessoa    ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idPessoa", dto.getId());
			query.setParameter("pisPasep", dto.getDescricao() != null ? StringUtil.limitaTamanhoString(dto.getDescricao(), 11) : "");
			
			query.executeUpdate();

		} catch (Throwable e) {
			httpGoApi.geraLog("PessoaFisicaDao || salvaPisPasep", StringUtil.convertObjectToJson(dto), e);
		}
	}
	
	public boolean possuiFoto(Long idPessoa) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(P.CODIGO)           ");
		sql.append("   FROM CAD_PESSOAS_FISICAS P     ");
		sql.append("  WHERE P.CODIGO = :idPessoa      ");
		sql.append("    AND P.FOTOGRAFIA IS NOT NULL  ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idPessoa", idPessoa);
			
			BigDecimal valor = (BigDecimal) query.getSingleResult(); 
			
			return valor.compareTo(new BigDecimal(0)) > 0;

		} catch (Throwable e) {
			httpGoApi.geraLog("PessoaFisicaDao || possuiFoto", StringUtil.convertObjectToJson(idPessoa), e);
		}
		return false;
	}
	
	public boolean possuiAssinatura(Long idPessoa) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(P.CODIGO)           ");
		sql.append("   FROM CAD_PESSOAS_FISICAS P     ");
		sql.append("  WHERE P.CODIGO = :idPessoa      ");
		sql.append("    AND P.ASSINATURA IS NOT NULL  ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idPessoa", idPessoa);
			
			BigDecimal valor = (BigDecimal) query.getSingleResult(); 
			
			return valor.compareTo(new BigDecimal(0)) > 0;

		} catch (Throwable e) {
			httpGoApi.geraLog("PessoaFisicaDao || possuiAssinatura", StringUtil.convertObjectToJson(idPessoa), e);
		}
		return false;
	}

}