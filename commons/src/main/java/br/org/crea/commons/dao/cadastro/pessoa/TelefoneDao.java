package br.org.crea.commons.dao.cadastro.pessoa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.commons.Telefone;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class TelefoneDao extends GenericDao<Telefone, Serializable>{
	
	@Inject HttpClientGoApi httpGoApi;
	
	public TelefoneDao(){
		super(Telefone.class);
	}
	
	public List<Telefone> getListTelefoneByPessoa(Long idPessoa){
		List<Telefone> telefones = new ArrayList<Telefone>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT T FROM Telefone T ");
		sql.append(" WHERE T.pessoa.id = :idPessoa ");
		
		try{
			TypedQuery<Telefone> query = em.createQuery(sql.toString(), Telefone.class);
			query.setParameter("idPessoa", idPessoa);
			
			telefones = query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("TelefoneDao || Get List Telefone by Pessoa", StringUtil.convertObjectToJson(idPessoa), e);
		}	
		
		return telefones;
	}

	public boolean existeTelefoneCadastrado(Telefone telefone, Pessoa contratado) {
		List<Telefone> telefones = new ArrayList<Telefone>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT T FROM Telefone T ");
		sql.append(" 	WHERE T.pessoa.id = :idPessoa ");
		sql.append(" 	AND T.ddd = :ddd AND T.numero = :numero AND T.tipoTelefone.codigo = :tipo ");
		
		try{
			TypedQuery<Telefone> query = em.createQuery(sql.toString(), Telefone.class);
			query.setParameter("idPessoa", contratado.getId());
			query.setParameter("ddd", telefone.getDdd());
			query.setParameter("numero", telefone.getNumero());
			query.setParameter("tipo", telefone.getTipoTelefone().getCodigo());
			
			telefones = query.getResultList();
		} catch (NoResultException e ){
			return false;
		} catch (Throwable e) {
			httpGoApi.geraLog("TelefoneDao || Get List Telefone by Pessoa", StringUtil.convertObjectToJson(telefone), e);
		}	
		
		return telefones.size() > 0 ? true : false;
	}
}
