package br.org.crea.commons.dao.art;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.dao.corporativo.Page;
import br.org.crea.commons.models.art.ArtLivroOrdem;
import br.org.crea.commons.models.art.dtos.PesquisaArtDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class ArtLivroOrdemDao extends GenericDao<ArtLivroOrdem, Serializable> {

	@Inject
	HttpClientGoApi httpGoApi;

	public ArtLivroOrdemDao() {
		super(ArtLivroOrdem.class);
	}

	public List<ArtLivroOrdem> getByART(String numeroArt) {
		
		List<ArtLivroOrdem> list = new ArrayList<ArtLivroOrdem>();

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT L FROM ArtLivroOrdem L  ");
		sql.append(" WHERE L.art.numero = :numeroArt ");
		sql.append(" ORDER BY L.dataInicioDaEtapa ASC ");

		try {
			TypedQuery<ArtLivroOrdem> query = em.createQuery(sql.toString(), ArtLivroOrdem.class);
			query.setParameter("numeroArt", numeroArt);

			list = query.getResultList();

		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtLivroOrdemDao || getByART", StringUtil.convertObjectToJson(numeroArt), e);
		}

		return list;
	}

	public List<ArtLivroOrdem> getByArtPaginado(PesquisaArtDto pesquisa) {
		
		List<ArtLivroOrdem> list = new ArrayList<ArtLivroOrdem>();

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT L FROM ArtLivroOrdem L  ");
		sql.append("  WHERE L.art.numero = :numeroArt ");

		try {
			TypedQuery<ArtLivroOrdem> query = em.createQuery(sql.toString(), ArtLivroOrdem.class);
			query.setParameter("numeroArt", pesquisa.getNumeroArt());
			
			Page page = new Page(pesquisa.getPage(), pesquisa.getRows());
			page.paginate(query);

			list = query.getResultList();

		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtLivroOrdemDao || getByArtPaginado", StringUtil.convertObjectToJson(pesquisa), e);
		}

		return list;
	}

	public int getTotalDeRegistrosDaPesquisa(PesquisaArtDto pesquisa) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT L.CODIGO              ");
		sql.append("   FROM ART_LIVRO_ORDEM L     ");
		sql.append("  WHERE L.FK_ART = :numeroArt ");
		
		try {
			
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("numeroArt", pesquisa.getNumeroArt());

			return query.getResultList().size();

		} catch (Throwable e) {
			httpGoApi.geraLog("ArtLivroOrdemDao || getTotalDeRegistrosDaPesquisa", StringUtil.convertObjectToJson(pesquisa), e);
		}

		return 0;
	}

	public boolean deletaArquivo(Long idArquivo) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ART_LIVRO_ORDEM L         ");
		sql.append("    SET L.FK_ARQUIVO = NULL       ");
		sql.append("  WHERE L.FK_ARQUIVO = :idArquivo ");
		
		try {
			
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idArquivo", idArquivo);

			query.executeUpdate();

		} catch (Throwable e) {
			httpGoApi.geraLog("ArtLivroOrdemDao || deletaArquivo", StringUtil.convertObjectToJson(idArquivo), e);
			return false;
		}
		return true;
	}
	
}
