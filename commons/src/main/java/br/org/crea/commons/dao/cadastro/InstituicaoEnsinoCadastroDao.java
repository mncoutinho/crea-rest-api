package br.org.crea.commons.dao.cadastro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.transform.Transformers;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.cadastro.CampusCadastro;
import br.org.crea.commons.models.cadastro.CursoCadastro;
import br.org.crea.commons.models.cadastro.InstituicaoEnsino;
import br.org.crea.commons.models.commons.dtos.InstituicaoEnsinoCadastroDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;
@Stateless
public class InstituicaoEnsinoCadastroDao extends GenericDao<InstituicaoEnsino, Serializable> {
	
	@Inject HttpClientGoApi httpGoApi;
	
	public InstituicaoEnsinoCadastroDao() {
		super(InstituicaoEnsino.class);
	}
	
	public List<InstituicaoEnsino> getAll() {
		
		List<InstituicaoEnsino> listInstituicaoEnsino = new ArrayList<InstituicaoEnsino>();
		StringBuilder sql = new StringBuilder();
		
		sql.append("FROM InstituicaoEnsino I ");
		
		try {
			TypedQuery<InstituicaoEnsino> query = em.createQuery(sql.toString(), InstituicaoEnsino.class);
			listInstituicaoEnsino = query.getResultList();
			if(listInstituicaoEnsino.size() > 0) {
				listInstituicaoEnsino.forEach(i -> {
					i.setNomeRazaoSocial(buscaDescricaoRazaoSocial(i.getIdPessoa()));
				});
			}
			listInstituicaoEnsino.sort((InstituicaoEnsino i1, InstituicaoEnsino i2)->i1.getNomeRazaoSocial().compareTo(i2.getNomeRazaoSocial()));
			
		} catch (Throwable e) {
			httpGoApi.geraLog("InstituicaoEnsinoCadastroDao || Get All", StringUtil.convertObjectToJson("SEM PARAMETRO"), e);
		}
		
		return listInstituicaoEnsino;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<InstituicaoEnsinoCadastroDto> getInstituicaoEnsinoDTOListByUF() {
		
		List<InstituicaoEnsinoCadastroDto> listInstituicaoEnsino = new ArrayList<InstituicaoEnsinoCadastroDto>();
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT DISTINCT IE.CODIGO as id, RS.DESCRICAO as descricao ");
		sql.append("	FROM CAD_INSTITUICOES_ENSINO IE "); 
		sql.append(" 	INNER JOIN CAD_PESSOAS_JURIDICAS PJ ON IE.FK_ID_PESSOAS_JURIDICAS = PJ.CODIGO "); 
		sql.append("	LEFT JOIN CAD_RAZOES_SOCIAIS RS ON PJ.CODIGO = RS.FK_CODIGO_PJS AND RS.ATIVO = 1"); 
		sql.append("	INNER JOIN CAD_ENDERECOS EN ON EN.FK_CODIGO_PESSOAS = PJ.CODIGO "); 
		sql.append("	WHERE EN.FK_CODIGO_UFS = 1 AND IE.CODIGO <> 1 ORDER BY RS.DESCRICAO");
		
		try {
			
			listInstituicaoEnsino = em.createNativeQuery(sql.toString())
			        .unwrap(org.hibernate.Query.class).setResultTransformer(Transformers.aliasToBean(InstituicaoEnsinoCadastroDto.class)).list();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("InstituicaoEnsinoCadastroDao || get Instituicao EnsinoDTO List By UF", StringUtil.convertObjectToJson("SEM PARAMETRO"), e);
		}
		
		return listInstituicaoEnsino;
		
	}
	
	public String buscaDescricaoRazaoSocial(Long idPessoa) {

		String razaoSocial = "";

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT RS.Descricao FROM cad_razoes_sociais RS ");
		sql.append("	WHERE RS.fk_codigo_pjs = :idPessoa ");
		sql.append("	AND RS.ativo = 1 ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idPessoa", idPessoa);

			razaoSocial = (String) query.getSingleResult();

		}catch (NoResultException e) {
			return "";
		} catch (Throwable e) {
			httpGoApi.geraLog("InstituicaoEnsinoCadastroDao || buscaDescricaoRazaoSocial", StringUtil.convertObjectToJson(idPessoa), e);
		}

		return razaoSocial;

	}
	
	public List<CampusCadastro> getCampusListByInstituicaoEnsinoCadastroId(Long idInstituicaoEnsinoCadastro) {
		
		List<CampusCadastro> listCampus = new ArrayList<CampusCadastro>();
		StringBuilder sql = new StringBuilder();
		
		sql.append("FROM CampusCadastro C ");
		sql.append("	WHERE C.instituicaoEnsino.id = :idInstituicaoEnsinoCadastro ");
		sql.append("	ORDER BY C.nome ");
		
		try {
			TypedQuery<CampusCadastro> query =  em.createQuery(sql.toString(), CampusCadastro.class);
			query.setParameter("idInstituicaoEnsinoCadastro", idInstituicaoEnsinoCadastro);
			
			listCampus =  query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("InstituicaoEnsinoCadastroDao || Get Campus List By InstituicaoEnsinoCadastro Id", StringUtil.convertObjectToJson(idInstituicaoEnsinoCadastro), e);
		}
		
		return listCampus;
		
	}
	
	public List<CursoCadastro> getCursoListByCampusCadastroId(Long idCampusCadastro) {
		
		List<CursoCadastro> listCampus = new ArrayList<CursoCadastro>();
		StringBuilder sql = new StringBuilder();
		
		sql.append("FROM CursoCadastro C ");
		sql.append("	WHERE C.campi.id = :idCampusCadastro ");
		sql.append("	ORDER BY C.titulo ");
		
		try {
			TypedQuery<CursoCadastro> query =  em.createQuery(sql.toString(), CursoCadastro.class);
			query.setParameter("idCampusCadastro", idCampusCadastro);
			
			listCampus =  query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("InstituicaoEnsinoCadastroDao || Get Curso List By Campus Id", StringUtil.convertObjectToJson(idCampusCadastro), e);
		}
		
		return listCampus;
		
	}

}
