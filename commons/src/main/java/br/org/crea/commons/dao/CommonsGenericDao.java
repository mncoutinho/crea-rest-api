package br.org.crea.commons.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.org.crea.commons.models.atendimento.AgendamentoMobile;
import br.org.crea.commons.models.cadastro.EntidadeClasse;
import br.org.crea.commons.models.cadastro.UnidadeMedida;
import br.org.crea.commons.models.commons.TipoAtendimento;
import br.org.crea.commons.models.corporativo.Assunto;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;
import br.org.crea.commons.models.corporativo.pessoa.PessoaFisica;
import br.org.crea.commons.models.corporativo.pessoa.PessoaJuridica;
import br.org.crea.commons.models.corporativo.pessoa.Profissional;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;


@Stateless
public class CommonsGenericDao {
	
	@Inject HttpClientGoApi httpGoApi;
	
	@PersistenceContext(unitName = "dscrea")
	public EntityManager em;
	
	
    public List<TipoAtendimento> getListTipoAtendimento(){
    	
    	List<TipoAtendimento> listTipoAtendimento = new ArrayList<TipoAtendimento>();
    	
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT T FROM TipoAtendimento T ");
		sql.append("  ORDER BY T.descricao ");

		try {
			TypedQuery<TipoAtendimento> query = em.createQuery(sql.toString(), TipoAtendimento.class);
					
			listTipoAtendimento = query.getResultList();
		} catch (Exception e) {
			httpGoApi.geraLog("CommonsGenericDao || Get List Tipo Atendimento", StringUtil.convertObjectToJson("SEM PARAMETRO"), e);
		}
    	
		return listTipoAtendimento;
    }
    
    
	public Boolean verificaSeFeriado (String dt) {
		Query query = em.createNativeQuery("Select Count(*) From Fin_Feriado " +
				                           "Where (Data = '" + dt + "' Or Data = '" + dt.substring(0, 5) + "' ) " +
				                           "And Ativo = 1");
		BigDecimal count = (BigDecimal) query.getSingleResult();
		return count != null && count.compareTo(new BigDecimal(0)) > 0;
	}

    
    public List<Assunto> getListAssuntoMobileAgendamento(){
    	
    	List<Assunto> listAssunto = new ArrayList<Assunto>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT A FROM Assunto A ");
		sql.append("    WHERE A.mobile > 0 OR A.viaPortal = 1 ");
		sql.append("  ORDER BY A.descricao ");

		try {	
			TypedQuery<Assunto> query = em.createQuery(sql.toString(), Assunto.class);
			
			listAssunto =  query.getResultList();
		} catch (Exception e) {
			httpGoApi.geraLog("CommonsGenericDao || Get Assunto Mobile Agendamento", StringUtil.convertObjectToJson("SEM PARAMETRO"), e);
		}
    	
		return listAssunto;
    }
    
	public boolean validaCpfNoCrea(String cpf) {

		boolean resposta = false;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT P FROM PessoaFisica P ");
		sql.append("    WHERE P.cpf = :cpf ");

		try {
			TypedQuery<PessoaFisica> query = em.createQuery(sql.toString(), PessoaFisica.class);
			query.setParameter("cpf", cpf);
			
			List<PessoaFisica> listPessoa = new ArrayList<PessoaFisica>();
			
			listPessoa = query.getResultList();
			
			if(!listPessoa.isEmpty()){
				resposta = true;
			}else{
				resposta = false;
			}
			
		} catch (Throwable e) {
			httpGoApi.geraLog("CommonsGenericDao || Valida Cpf No Crea", StringUtil.convertObjectToJson(cpf), e);
		}
		
		return resposta;
			
	}

	public boolean validaCnpjNoCrea(String cnpj) {
		
		boolean resposta = false;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT P FROM PessoaJuridica P ");
		sql.append("    WHERE P.cnpj LIKE :cnpj ");

		try {
			TypedQuery<PessoaJuridica> query = em.createQuery(sql.toString(), PessoaJuridica.class);
			query.setParameter("cnpj", cnpj.substring(0, 8) + "%");
			
			List<PessoaJuridica> listPessoa = new ArrayList<PessoaJuridica>();
			
			listPessoa = query.getResultList();
			
			if(!listPessoa.isEmpty()){
				resposta = true;
			}else{
				resposta = false;
			}
		} catch (Throwable e) {
			httpGoApi.geraLog("CommonsGenericDao || Valida Cnpj No Crea", StringUtil.convertObjectToJson(cnpj), e);
		}
		
		return resposta;
	}
	
	public boolean validaRnpNoCrea(String rnp) {
		
		boolean resposta = false;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT P FROM Profissional P ");
		sql.append("    WHERE P.numeroRNP = :rnp ");

		try {
			TypedQuery<Profissional> query = em.createQuery(sql.toString(), Profissional.class);
			query.setParameter("rnp", rnp);
			
			List<Profissional> listPessoa = new ArrayList<Profissional>();
			
			listPessoa = query.getResultList();
			
			if(!listPessoa.isEmpty()){
				resposta = true;
			}else{
				resposta = false;
			}
		} catch (Throwable e) {
			httpGoApi.geraLog("CommonsGenericDao || Valida Rnp No Crea", StringUtil.convertObjectToJson(rnp), e);
		}
		
		return resposta;
	}

	public boolean validaRegistroNoCrea(String registro) {
		
		boolean resposta = false;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT P FROM Pessoa P ");
		sql.append("    WHERE P.id = :registro ");

		try {
			TypedQuery<Pessoa> query = em.createQuery(sql.toString(), Pessoa.class);
			query.setParameter("registro", Long.parseLong(registro) );
			
			List<Pessoa> listPessoa = new ArrayList<Pessoa>();
			
			listPessoa = query.getResultList();
			
			if(!listPessoa.isEmpty()){
				resposta = true;
			}else{
				resposta = false;
			}
		} catch (Throwable e) {
			httpGoApi.geraLog("CommonsGenericDao || Valida Registro No Crea", StringUtil.convertObjectToJson(registro), e);
		}
		
		return resposta;
	}

	public boolean validaCpfOuCnpjNoAgendamento(String cpfOuCnpj) {
		
		boolean resposta = false;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT A FROM AgendamentoMobile A ");
		sql.append("    WHERE A.cpfOuCnpj = :cpfOuCnpj ");

		try {
			TypedQuery<AgendamentoMobile> query = em.createQuery(sql.toString(), AgendamentoMobile.class);
			query.setParameter("cpfOuCnpj", cpfOuCnpj );
			
			List<AgendamentoMobile> listPessoa = new ArrayList<AgendamentoMobile>();
			
			listPessoa = query.getResultList();
			
			if(!listPessoa.isEmpty()){
				resposta = true;
			}else{
				resposta = false;
			}
		} catch (Throwable e) {
			httpGoApi.geraLog("CommonsGenericDao || Valida Cpf ou Cnpj no Agendamento", StringUtil.convertObjectToJson(cpfOuCnpj), e);
		}
		
		return resposta;
	}
	
	public List<UnidadeMedida> getAllUnidadesMedidaArtReceita(){
		List<UnidadeMedida> lista = new ArrayList<UnidadeMedida>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT U FROM UnidadeMedida U ");
		sql.append("             WHERE U.receita = 1 ");
		
		try{
			TypedQuery<UnidadeMedida> query = em.createQuery(sql.toString(), UnidadeMedida.class);
			
			lista = query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("CommonsGenericDao || Get All Unidades Medida", StringUtil.convertObjectToJson("SEM PARAMETRO"), e);
		}    
		
		return lista;
	}

	public List<EntidadeClasse> getEntidadesClasse() {
	
		List<EntidadeClasse> lista = new ArrayList<EntidadeClasse>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT E FROM EntidadeClasse E ");
		sql.append("             WHERE E.tipoEntidade IN (0, 1) ");
		
		try{
			TypedQuery<EntidadeClasse> query = em.createQuery(sql.toString(), EntidadeClasse.class);
			
			lista = query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("CommonsGenericDao || Get Entidades Classe", StringUtil.convertObjectToJson("SEM PARAMETRO"), e);
		}
		
		return lista;
	}


	public boolean bancoOn() {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT 1 FROM DUAL ");
		
		try{
			Query query = em.createNativeQuery(sql.toString());
			
			BigDecimal result = (BigDecimal) query.getSingleResult();
			return result.compareTo(new BigDecimal(1)) == 0;
			
		} catch (Throwable e) {
			httpGoApi.geraLog("CommonsGenericDao || bancoOn", StringUtil.convertObjectToJson("SEM PARAMETRO"), e);
		}
		
		return false;
	}
	
}
