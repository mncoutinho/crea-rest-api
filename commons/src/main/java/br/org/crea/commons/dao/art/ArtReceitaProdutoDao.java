package br.org.crea.commons.dao.art;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.art.ArtReceitaProduto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class ArtReceitaProdutoDao extends GenericDao<ArtReceitaProduto, Serializable>{

	@Inject HttpClientGoApi  httpGoApi;
	
	public ArtReceitaProdutoDao() {
		super(ArtReceitaProduto.class);
	}

	public ArtReceitaProduto salvar(ArtReceitaProduto receitaProduto){
		try{
			receitaProduto = create(receitaProduto);
		}catch(Throwable e){
			httpGoApi.geraLog("ArtReceitaProdutoArtDao || salvar " , StringUtil.convertObjectToJson(receitaProduto), e);
		}
		return receitaProduto;
	}
}
