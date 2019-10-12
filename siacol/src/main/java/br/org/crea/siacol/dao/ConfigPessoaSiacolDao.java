package br.org.crea.siacol.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.siacol.ConfigPessoaSiacol;
import br.org.crea.commons.models.siacol.dtos.GenericSiacolDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.DateUtils;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class ConfigPessoaSiacolDao extends GenericDao<ConfigPessoaSiacol, Serializable> {

	@Inject
	HttpClientGoApi httpGoApi;
	
	public ConfigPessoaSiacolDao() {
		super(ConfigPessoaSiacol.class);
	}

	public List<ConfigPessoaSiacol> getByIdPessoa(Long idPessoa) {

		try {
			StringBuilder sb = new StringBuilder();
			sb.append(" FROM ConfigPessoaSiacol C");
			sb.append(" 	WHERE C.pessoa.id = :idPessoa ");
			sb.append("    		AND TO_CHAR(C.dataFim, 'yyyy/mm/dd') >= TO_CHAR(sysdate, 'yyyy/mm/dd') ");
			sb.append(" 		AND C.ativo = 1 ");

			TypedQuery<ConfigPessoaSiacol> query = em.createQuery(sb.toString(), ConfigPessoaSiacol.class);
			query.setParameter("idPessoa", idPessoa);

			return query.getResultList();

		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("ConfigPessoaSiacolDao || getByIdFuncionario", StringUtil.convertObjectToJson(idPessoa), e);
		}
		return null;

	}

	public void desativaHabilidades(Long idDepartamento) {

		try {

			StringBuilder sb = new StringBuilder();
			sb.append(" SELECT C FROM ConfigPessoaSiacol C ");
			sb.append("    WHERE TO_CHAR(C.dataInicio, 'mm/dd/yyyy') = TO_CHAR(:hoje, 'mm/dd/yyyy') ");
			sb.append("    AND C.ativo = 1 ");
			
			TypedQuery<ConfigPessoaSiacol> query = em.createQuery(sb.toString(), ConfigPessoaSiacol.class);
			query.setParameter("hoje", new Date());

			for (ConfigPessoaSiacol c : query.getResultList()) {

				StringBuilder sb2 = new StringBuilder();
				sb2.append(" UPDATE  SIACOL_HABILIDADE_PESSOA C ");
				sb2.append(" 	SET C.ATIVO = 0 ");
				sb2.append(" 	WHERE C.FK_PESSOA = :id ");
				if (idDepartamento != null) {
					sb2.append(" 	AND  C.FK_DEPARTAMENTO = :idDepartamento ");	
				}

				Query query2 = em.createNativeQuery(sb2.toString());
				query2.setParameter("id", c.getPessoa().getId());
				if (idDepartamento != null) {
					query2.setParameter("idDepartamento", idDepartamento);				
				}
				
				query2.executeUpdate();
			}

		} catch (Throwable e) {
			httpGoApi.geraLog("ConfigPessoaSiacolDao || desativaHabilidades", StringUtil.convertObjectToJson("sem parametros"), e);
		}

	}
	
	public void ativaHabilidades() {

		try {

			StringBuilder sb = new StringBuilder();
			sb.append(" SELECT C FROM ConfigPessoaSiacol C ");
			sb.append("    WHERE TO_CHAR(C.dataFim, 'mm/dd/yyyy') < TO_CHAR(sysdate, 'mm/dd/yyyy') ");
			sb.append("    AND C.ativo = 1 ");
			
			TypedQuery<ConfigPessoaSiacol> query = em.createQuery(sb.toString(), ConfigPessoaSiacol.class);
			

			for (ConfigPessoaSiacol c : query.getResultList()) {
				
					c.setAtivo(false);
					update(c);
					
					StringBuilder sb2 = new StringBuilder();
					sb2.append(" UPDATE  SIACOL_HABILIDADE_PESSOA C ");
					sb2.append(" 	SET C.ATIVO = 1 ");				
					sb2.append(" 	WHERE C.FK_PESSOA = :id ");
					sb2.append(" 	 AND C.FK_DEPARTAMENTO = :idDepartamento ");

					Query query2 = em.createNativeQuery(sb2.toString());
					query2.setParameter("id", c.getPessoa().getId());
					query2.setParameter("idDepartamento", c.getIdDepartamento());
					query2.executeUpdate();

			}

		} catch (Throwable e) {
			httpGoApi.geraLog("ConfigPessoaSiacolDao || ativaHabilidades", StringUtil.convertObjectToJson("sem parametros"), e);
		}

	}

	public void removeProgramacao(GenericSiacolDto dto) {
				
		if (DateUtils.eMenorOuIgualAoDiaAtual(dto.getDataInicio()) 
				&& DateUtils.eMaiorOuIgualAoDiaAtual(dto.getDataFim())){
			
			StringBuilder sb2 = new StringBuilder();
			sb2.append(" UPDATE  SIACOL_HABILIDADE_PESSOA C ");
			sb2.append(" 	SET C.ATIVO = 1 ");				
			sb2.append(" 	WHERE C.FK_PESSOA = :id ");
			sb2.append(" 	AND  C.FK_DEPARTAMENTO IN (:idsDepartamento) ");

			Query query2 = em.createNativeQuery(sb2.toString());
			query2.setParameter("id", dto.getIdPessoa());
			query2.setParameter("idsDepartamento", dto.getListaId());
			query2.executeUpdate();		
		}

		try {
			StringBuilder sb = new StringBuilder();
			sb.append("UPDATE SIACOL_CONFIG_PESSOA C");
			sb.append(" 	SET C.ATIVO = 0 ");
			sb.append(" 	WHERE C.FK_FUNCIONARIO = :idPessoa ");
			sb.append(" 		AND TO_CHAR(C.DATA_INICIO,'DD/MM/yyyy') = :dataInicio ");
			sb.append(" 		AND TO_CHAR(C.DATA_FIM,'DD/MM/yyyy') = :dataFim ");
			sb.append(" 		AND C.FK_ID_DEPARTAMENTO IN (:idsDepartamento) ");
			sb.append(" 		AND C.ATIVO = 1 ");

			Query query = em.createNativeQuery(sb.toString());
			query.setParameter("idPessoa", dto.getIdPessoa());
			query.setParameter("idsDepartamento", dto.getListaId());
			query.setParameter("dataInicio", dto.getDataInicioFormatada());
			query.setParameter("dataFim", dto.getDataFimFormatada());

			query.executeUpdate();

		} catch (Throwable e) {
			httpGoApi.geraLog("ConfigPessoaSiacolDao || removeProgramacao", StringUtil.convertObjectToJson(dto), e);
		}

	}

}
