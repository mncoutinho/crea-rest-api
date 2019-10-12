package br.org.crea.commons.dao.art;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.art.ArtReceita;
import br.org.crea.commons.models.art.dtos.ArtQuantidadeReceitaDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class ArtReceitaDao extends GenericDao<ArtReceita, Serializable> {

	@Inject HttpClientGoApi  httpGoApi;

	public ArtReceitaDao() {
		super(ArtReceita.class);
	}

	public ArtReceita salvar(ArtReceita receita) {

		try {
			create(receita);
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtReceitaDao || Salvar", StringUtil.convertObjectToJson(receita), e);
		}

		return receita;
	}

	public ArtReceita recuperarReceitaEProdutos(Long idReceita) {
		ArtReceita artReceita = new ArtReceita();		
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT R FROM ArtReceita R ");
		sql.append(     "JOIN FETCH R.receitaProdutos rp ");
		sql.append("	where R.id = :idReceita ");
		
		try {
			
			TypedQuery<ArtReceita> queryArtReceita = em.createQuery(sql.toString(), ArtReceita.class);
			queryArtReceita.setParameter("idReceita", idReceita);
			artReceita = queryArtReceita.getSingleResult();	
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtReceitaDao || getAllByArt", StringUtil.convertObjectToJson(idReceita), e);

		}
		
		return artReceita;
	}

	@SuppressWarnings("unchecked")
	public List<ArtQuantidadeReceitaDto> getArtsDisponiveis(String registro)
		{
			List<ArtQuantidadeReceitaDto> resultado = new ArrayList<ArtQuantidadeReceitaDto>();	
			
			StringBuilder sql = new StringBuilder();

			sql.append("SELECT a.numero, count(r.id) quantidade " );
			sql.append("FROM art_art a, cad_profissionais p, art_receita r " );
			sql.append("WHERE a.fk_profissional = p.codigo " );
			sql.append("    AND r.fk_art(+) = a.numero " );
			sql.append("    AND p.registro = :registro " );
			sql.append("    AND a.fk_natureza_art = 3 " );
			sql.append("    AND a.data_pagamento IS NOT NULL " );    
			sql.append("GROUP BY a.numero, a.data_cadastro " );
			sql.append("ORDER BY a.data_cadastro DESC " );

			try {

				Query query = em.createNativeQuery(sql.toString());
				query.setParameter("registro", registro);

				List<Object[]> lista = query.getResultList();
				
				for(Object[] objeto: lista){
					ArtQuantidadeReceitaDto dto = new ArtQuantidadeReceitaDto();
					dto.setNumero((String) objeto[0]);
					dto.setQuantidade((BigDecimal) objeto[1]);
					resultado.add(dto);
				}
				

			} catch (Throwable e) {
				httpGoApi.geraLog("ProtocoloDao || getArtsDisponiveis", StringUtil.convertObjectToJson(registro), e);
			}

			return resultado.isEmpty() ? null : resultado;

		}
	
}
