package br.org.crea.commons.dao.cadastro.profissional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.cadastro.RlProfissionalEspecialidade;
import br.org.crea.commons.models.corporativo.pessoa.Profissional;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class ProfissionalEspecialidadeDao extends GenericDao<RlProfissionalEspecialidade, Serializable> {

	@Inject
	HttpClientGoApi httpGoApi;

	public ProfissionalEspecialidadeDao() {
		super(RlProfissionalEspecialidade.class);
	}

	public List<RlProfissionalEspecialidade> getProfissionalEspecialidade(Long idProfissional) {

		List<RlProfissionalEspecialidade> profissionalEspecialidade = new ArrayList<RlProfissionalEspecialidade>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT P FROM RlProfissionalEspecialidade P WHERE P.codigoProfissional = :idProfissional ");
		sql.append(" AND P.dataCancelamento is null");
//		sql.append(" AND P.titulo.id not between 41001 and 41031 ");
//		sql.append(" AND P.titulo.id not between 43003 and 43004 ");
		sql.append(" ORDER BY P.escolaridade.id, P.dataColacaoGrau ");

		try {
			TypedQuery<RlProfissionalEspecialidade> query = em.createQuery(sql.toString(), RlProfissionalEspecialidade.class);
			query.setParameter("idProfissional", idProfissional);

			profissionalEspecialidade = query.getResultList();

		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("ProfissionalEspecialidadeDao || Get Especialidade Profissional", StringUtil.convertObjectToJson(idProfissional), e);
		}

		return profissionalEspecialidade;
	}

	public String getTituloProfissional(Profissional profissional) {

		StringBuilder titulo = new StringBuilder();

		List<RlProfissionalEspecialidade> listProfissionalEspecialidade = new ArrayList<RlProfissionalEspecialidade>();
		listProfissionalEspecialidade = getProfissionalEspecialidade(profissional.getId());

		try {

			Long sexo = null;

			if (profissional.getPessoaFisica().getTipoSexo() == null) {
				sexo = new Long(0);
			} else {
				sexo = profissional.getPessoaFisica().getTipoSexo().getId();
			}

			if (!listProfissionalEspecialidade.isEmpty()) {

				for (RlProfissionalEspecialidade p : listProfissionalEspecialidade) {

					if (p.temTituloDoConfeaEstaCadastrado()) {
						titulo.append(p.getConfeaDescricao(sexo) + ", ");

					} else {
						titulo.append(p.getTituloDescricao(sexo) + ", ");
					}

				}

			}

		} catch (Exception e) {
			httpGoApi.geraLog("ProfissionalEspecialidadeDao || getTituloProfissional", StringUtil.convertObjectToJson(profissional), e);
		}

		if (titulo.length() > 0 ) {
			return titulo.substring (0, titulo.length() - 2);	
		}else{
			return titulo.toString();
		}
	}
	
	@SuppressWarnings("unchecked")
	public RlProfissionalEspecialidade getAtribuicoesDoTituloPorEspecialidade(RlProfissionalEspecialidade model) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT    A.descricao AS atribuicao, ");
		sql.append(" 		  D.descricao AS dispositivo ");
		sql.append(" 	FROM  cad_especxatrib EA, ");
		sql.append("   		  cad_atribuicoes A, ");
		sql.append(" 		  cad_especialidades ESP, ");
		sql.append(" 		  cad_dispositivos D ");
		sql.append("	WHERE EA.fk_codigo_atribuicoes 	  = A.codigo ");
		sql.append("	AND   EA.fk_codigo_especialidades = ESP.codigo ");
		sql.append("	AND   A.fk_codigo_dispositivos    = D.codigo ");
		sql.append("	AND   ESP.codigo				  = :codigoEspecialidade ");
		sql.append("	ORDER BY A.descricao ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("codigoEspecialidade", model.getEspecialidade().getId());
			
			List<Object[]> list = query.getResultList();
			
			for (Object[] object : list) {
				
				model.getAtribuicoesTitulo().add((String)object[0]);
				model.getDispositivosAtribuicoesTitulo().add((String)object[1]);
			}

		} catch (NoResultException e) {
			return model;
		} catch (Throwable e) {
			httpGoApi.geraLog("ProfissionalEspecialidadeDao || getAtribuicoesDoTituloPorEspecialidade", StringUtil.convertObjectToJson(model), e);
		}

		return model;
	}

}
