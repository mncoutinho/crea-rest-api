package br.org.crea.commons.dao.art;

import java.io.Serializable;
import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.art.ArtPessoaAcaoElevadores;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class ArtPessoaAcaoElevadoresDao  extends GenericDao<ArtPessoaAcaoElevadores, Serializable> {
	
		@Inject
		HttpClientGoApi httpGoApi;

		public ArtPessoaAcaoElevadoresDao() {
			super(ArtPessoaAcaoElevadores.class);
		}

		public ArtPessoaAcaoElevadores getByIdPessoa(Long idPessoa) {
			
			if(idPessoa == null) idPessoa = 0l;
			
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM ART_PESSOA_ACAO_ELEVADORES P  ");
			sql.append("  WHERE P.FK_PESSOA = :idPessoa            ");
			
			try {
				Query query = em.createNativeQuery(sql.toString());
				query.setParameter("idPessoa", idPessoa);
				query.setMaxResults(1);
				
				Object[] result = (Object[]) query.getSingleResult();
				if(result[0] != null) {
					ArtPessoaAcaoElevadores artPessoaAcaoElevadores = new ArtPessoaAcaoElevadores();
					artPessoaAcaoElevadores.setDataFim((Date) result[4]);
					return artPessoaAcaoElevadores;
				}
				
			} catch (NoResultException e) {
				return null;
			} catch (Throwable e) {
				httpGoApi.geraLog("ArtPessoaAcaoElevadoresDao || getByIdPessoa", StringUtil.convertObjectToJson(idPessoa), e);
			}
			return null;
		}

}
