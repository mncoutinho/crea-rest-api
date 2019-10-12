package br.org.crea.commons.dao.cadastro;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.cadastro.EmailPessoa;
import br.org.crea.commons.models.cadastro.dtos.EmailDto;
import br.org.crea.commons.models.commons.EventoEmail;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.models.corporativo.enuns.TipoPessoa;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class EmailDao extends GenericDao<EmailPessoa, Serializable>{
	
	@Inject HttpClientGoApi httpGoApi;
	
	@Inject
	private Properties properties;
	
	public EmailDao(){
		super(EmailPessoa.class);
	}
	
	public List<EmailPessoa> getListEnderecoDeEmailPor(Long idPessoa){
		List<EmailPessoa> emails = new ArrayList<EmailPessoa>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT E FROM EmailPessoa E ");
		sql.append(" 	WHERE E.pessoa.id = :idPessoa ");
		
		try{
			TypedQuery<EmailPessoa> query = em.createQuery(sql.toString(), EmailPessoa.class);
			query.setParameter("idPessoa", idPessoa);
			
			emails = query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("EmailDao || getListEnderecoDeEmailPor", StringUtil.convertObjectToJson(idPessoa), e);
		}	
		
		return emails;
	}
	
	public String getUltimoEmailCadastradoPor(Long idPessoa){
		
		List<EmailPessoa> emails = new ArrayList<EmailPessoa>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT E FROM EmailPessoa E ");
		sql.append(" 	WHERE E.pessoa.id = :idPessoa ");
		sql.append(" 	ORDER BY E.dataAtualizacao desc NULLS last) ");
		
		try{
			TypedQuery<EmailPessoa> query = em.createQuery(sql.toString(), EmailPessoa.class);
			query.setParameter("idPessoa", idPessoa);
			
			 emails = query.getResultList();
			
		} catch (NoResultException e) {
			return null;
		} catch(Throwable e) {
			httpGoApi.geraLog("EmailDao || getUltimoEmailCadastradoPor", StringUtil.convertObjectToJson(idPessoa), e);
		}	
		return emails.isEmpty() ? null : emails.get(0).getDescricao();
	}
	
	public boolean existeEmailCadastradoPessoa(String descricaoEmail, Long idPessoa){

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT E FROM EmailPessoa E ");
		sql.append(" 	WHERE E.pessoa.id = :idPessoa ");
		sql.append("    AND E.descricao = :descricaoEmail ");
				
		try{
			TypedQuery<EmailPessoa> query = em.createQuery(sql.toString(), EmailPessoa.class);
			query.setParameter("idPessoa", idPessoa);
			query.setParameter("descricaoEmail", descricaoEmail);
			query.setMaxResults(1);
			
			query.getSingleResult();
			
		} catch(NoResultException e){
			return false;
		} catch(Throwable e) {
			httpGoApi.geraLog("EmailDao || existeEmailPessoa", StringUtil.convertObjectToJson(descricaoEmail), e);
		}	
		return true;
	}
	
	public boolean pessoaPossuiEmailCadastrado(Long idPessoa) {

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(E.CODIGO) FROM CAD_EMAILS E   ");
		sql.append("  WHERE E.FK_CODIGO_PESSOAS = :idPessoa     ");
				
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idPessoa", idPessoa);
			
			BigDecimal resultado = (BigDecimal) query.getSingleResult();
	 	    
		 	return resultado.compareTo(new BigDecimal(0)) > 0; 
			
		} catch(NoResultException e) {
			return false;
		} catch(Throwable e) {
			httpGoApi.geraLog("EmailDao || pessoaPossuiEmailCadastrado", StringUtil.convertObjectToJson(idPessoa), e);
		}	
		return true;
	}

	public boolean existeEmailCadastrado(EmailDto emailDto) {
		
		List<EmailPessoa> emails = new ArrayList<EmailPessoa>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT E FROM EmailPessoa E ");
		sql.append(" 	WHERE E.descricao = :descricao ");
		sql.append("    AND E.pessoa.id <> :id ");
		
		try{
			TypedQuery<EmailPessoa> query = em.createQuery(sql.toString(), EmailPessoa.class);
			query.setParameter("descricao", emailDto.getDescricao());
			query.setParameter("id", emailDto.getIdPessoa());
			query.setMaxResults(1);
			
			emails = query.getResultList();
			
		} catch (NoResultException e) {
			return false;
		} catch(Throwable e) {
			httpGoApi.geraLog("EmailDao || findByDescricao", StringUtil.convertObjectToJson(emailDto), e);
		}	
		return emails.size() > 0;
	}

	public EmailDto atualizaEmailPessoa(EmailDto dto, UserFrontDto userFrontDto ) {
		String usuarioPortal = properties.getProperty("usuario.portal");
		StringBuilder sql = new StringBuilder();
		
		sql.append(" UPDATE CAD_EMAILS E ");
		sql.append(" 	SET E.DESCRICAO = :descricao ,");
		sql.append(" 		E.DATA_ATUALIZACAO = SYSDATE ,");
		
		if (userFrontDto.getTipoPessoa().equals(TipoPessoa.FUNCIONARIO)){
			sql.append(" 		E.FK_CODIGO_FUNCIONARIO = :idFuncionario ");
		sql.append(" 	WHERE E.FK_CODIGO_PESSOAS = :idPessoa ");
		} else{
			sql.append(" 		E.FK_CODIGO_FUNCIONARIO = :usuarioPortal ");
			sql.append(" 	WHERE E.FK_CODIGO_PESSOAS = :idPessoa ");
		}
		
		try{
			Query query = em.createNativeQuery(sql.toString());
			
			if (userFrontDto.getTipoPessoa().equals(TipoPessoa.FUNCIONARIO)){
				query.setParameter("idFuncionario", userFrontDto.getIdFuncionario());
			} else {
				query.setParameter("usuarioPortal", Long.parseLong(usuarioPortal));
			}
			
			query.setParameter("descricao", dto.getDescricao());
			query.setParameter("idPessoa", dto.getIdPessoa());
			query.executeUpdate();
			
			
		} catch(Throwable e) {
			httpGoApi.geraLog("EmailDao || atualizaEmailPessoa", StringUtil.convertObjectToJson(dto), e);
		}
		return dto;
	}

	public EventoEmail getEventoById(Long id) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT E FROM EventoEmail E ");
		sql.append(" 	WHERE E.id = :id ");
		
		try{
			TypedQuery<EventoEmail> query = em.createQuery(sql.toString(), EventoEmail.class);
			query.setParameter("id", id);
			
			 return query.getSingleResult();
			
		} catch (NoResultException e) {
			return null;
		} catch(Throwable e) {
			httpGoApi.geraLog("EmailDao || getEventoById", StringUtil.convertObjectToJson(id), e);
		}
		return null;	
	}

}
