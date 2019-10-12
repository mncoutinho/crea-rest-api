package br.org.crea.commons.dao.atendimento;

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
import br.org.crea.commons.models.atendimento.Ouvidoria;
import br.org.crea.commons.models.atendimento.OuvidoriaAssuntosEspecificos;
import br.org.crea.commons.models.atendimento.OuvidoriaAssuntosGerais;
import br.org.crea.commons.models.atendimento.OuvidoriaSituacoes;
import br.org.crea.commons.models.atendimento.OuvidoriaTipoDemanda;
import br.org.crea.commons.models.atendimento.dtos.OuvidoriaDto;
import br.org.crea.commons.models.commons.dtos.PesquisaGenericDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class OuvidoriaDao extends GenericDao<Ouvidoria, Serializable> {

	@Inject
	HttpClientGoApi httpGoApi;

	public OuvidoriaDao() {
		super(Ouvidoria.class);
	}

	public List<Ouvidoria> pesquisaAtendimentosOuvidoriaPaginado(PesquisaGenericDto pesquisa) {
		List<Ouvidoria> list = new ArrayList<Ouvidoria>();

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT O ");
		sql.append(" FROM Ouvidoria O ");
		sql.append(" WHERE O.pessoa.id = :idPessoa ");
		if(pesquisa.getAssuntoGeral() != null) {
			sql.append("AND O.assuntoGeral.id = :assuntoGeral ");
			if(pesquisa.getAssuntoEspecifico() != null) {
				sql.append("AND O.assuntoEspecifico.id = :assuntoEspecifico ");
			}
		}
		if(pesquisa.getTipoDemanda() != null) {
			sql.append(" AND O.tipoDemanda.id = :tipoDemanda ");
		}
		if(pesquisa.getSituacao() != null) {
			sql.append(" AND O.situacao = :situacao ");
		} 
		if(pesquisa.temDataInicioEFim()) {
			sql.append(" AND O.dataAtendimento >= :dataInicio ");
			sql.append(" AND O.dataAtendimento <= :dataFim ");
			sql.append(" ORDER BY O.dataAtendimento DESC ");
		} else {
			sql.append(" ORDER BY O.id DESC");
		}
		

		try {
			TypedQuery<Ouvidoria> query = em.createQuery(sql.toString(), Ouvidoria.class);
			query.setParameter("idPessoa", pesquisa.getIdPessoa());
			if(pesquisa.getAssuntoGeral() != null) {
				query.setParameter("assuntoGeral", pesquisa.getAssuntoGeral());
				if(pesquisa.getAssuntoEspecifico() != null) {
					query.setParameter("assuntoEspecifico", pesquisa.getAssuntoEspecifico());
				}
			}
			if(pesquisa.getTipoDemanda() != null) {
				query.setParameter("tipoDemanda", pesquisa.getTipoDemanda().getId());
			}
			if(pesquisa.temDataInicioEFim()) {
				query.setParameter("dataInicio", pesquisa.getDataInicio());
				query.setParameter("dataFim", pesquisa.getDataFim());
			}
			if(pesquisa.getSituacao() != null) {
				query.setParameter("situacao", pesquisa.getSituacao());
			}
			Page page = new Page(pesquisa.getPage(), pesquisa.getRows());
			page.paginate(query);

			list = query.getResultList();

		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("OuvidoriaDao || pesquisaAtendimentosOuvidoriaPaginado",
					StringUtil.convertObjectToJson(pesquisa), e);
		}

		return list;
	}

	public int getTotalDeRegistrosdaPesquisa(PesquisaGenericDto pesquisa) {

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT O ");
		sql.append(" FROM Ouvidoria O ");
		sql.append(" WHERE O.pessoa.id = :idPessoa ");

		try {
			TypedQuery<Ouvidoria> query = em.createQuery(sql.toString(), Ouvidoria.class);
			query.setParameter("idPessoa", pesquisa.getIdPessoa());

			return query.getResultList().size();

		} catch (Throwable e) {
			httpGoApi.geraLog("OuvidoriaDao || getTotalDeRegistrosdaPesquisa", StringUtil.convertObjectToJson(pesquisa),
					e);
		}

		return 0;
	}

	public List<OuvidoriaTipoDemanda> getAllTipoDemanda() {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT TD ");
		sql.append(" FROM OuvidoriaTipoDemanda TD ");
		sql.append(" WHERE REMOVIDO = 0 ");

		try {

			TypedQuery<OuvidoriaTipoDemanda> query = em.createQuery(sql.toString(), OuvidoriaTipoDemanda.class);
			return query.getResultList();


		} catch (Throwable e) {
			httpGoApi.geraLog("OuvidoriaDao || getAllTipoDemanda", StringUtil.convertObjectToJson(""),
					e);
		}

		return null;
	}

	public List<OuvidoriaAssuntosGerais> getAllAssuntosGerais() {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT AG ");
		sql.append(" FROM OuvidoriaAssuntosGerais AG ");
		sql.append(" WHERE REMOVIDO = 0 ");

		try {

			TypedQuery<OuvidoriaAssuntosGerais> query = em.createQuery(sql.toString(), OuvidoriaAssuntosGerais.class);
			return query.getResultList();


		} catch (Throwable e) {
			httpGoApi.geraLog("OuvidoriaDao || getAllAssuntosGerais", StringUtil.convertObjectToJson(""),
					e);
		}

		return null;
	}

	public List<OuvidoriaAssuntosEspecificos> getAllAssuntosEspecificos(Long assuntosGerais) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT AE ");
		sql.append(" FROM OuvidoriaAssuntosEspecificos AE ");
		sql.append(" WHERE AE.idAssuntosGerais = :id ");
		

		try {
			TypedQuery<OuvidoriaAssuntosEspecificos> query = em.createQuery(sql.toString(), OuvidoriaAssuntosEspecificos.class);
			query.setParameter("id", assuntosGerais);
			return query.getResultList();
		} 
		catch (Throwable e) {
			httpGoApi.geraLog("OuvidoriaDao || getAllAssuntosGerais", StringUtil.convertObjectToJson(""), e);
		}

		return null;
	}

	public List<OuvidoriaSituacoes> getSituacao() {
		StringBuilder sql = new StringBuilder();
		sql.append(" Select ST ");
		sql.append("FROM OuvidoriaSituacoes ST ");
		sql.append(" WHERE REMOVIDO = 0");
		try {

			TypedQuery<OuvidoriaSituacoes> query = em.createQuery(sql.toString(), OuvidoriaSituacoes.class);
			return query.getResultList();


		} catch (Throwable e) {
			httpGoApi.geraLog("OuvidoriaDao || getSituacao", StringUtil.convertObjectToJson(""),
					e);
		}

		return null;
	}

	public List<Ouvidoria> getOuvidoriaPublica(PesquisaGenericDto pesquisa) {
		List<Ouvidoria> list = new ArrayList<Ouvidoria>();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT O ");
		sql.append("   FROM Ouvidoria O ");
		if (pesquisa.temNumeroProtocolo()) {
			sql.append(" WHERE O.protocolo.id = :numeroProtocolo ");
		} else if (pesquisa.getCodigoAtendimento() != null) {
			sql.append(" WHERE O.id = :codigoAtendimento ");
		} else if (pesquisa.getEmail() != null) {
			sql.append(" WHERE O.pessoa.id IN (SELECT EP.pessoa.id FROM EmailPessoa EP WHERE EP.descricao = :email) ");
		}
		
		
		try {
			TypedQuery<Ouvidoria> query = em.createQuery(sql.toString(), Ouvidoria.class);
			if (pesquisa.temNumeroProtocolo()) {
				query.setParameter("numeroProtocolo", Long.parseLong(pesquisa.getNumeroProtocolo()));
			} else if (pesquisa.getCodigoAtendimento() != null) {
				query.setParameter("codigoAtendimento", pesquisa.getCodigoAtendimento());
			} else if (pesquisa.getEmail() != null) {
				query.setParameter("email", pesquisa.getEmail());
			}
			Page page = new Page(pesquisa.getPage(), pesquisa.getRows());
			page.paginate(query);

			list = query.getResultList();

		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("OuvidoriaDao || getOuvidoriaPublica", StringUtil.convertObjectToJson(pesquisa), e);
		}
		
		return list;
	}

	public int getTotalRegistrosPesquisaPublica(PesquisaGenericDto pesquisa) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT O ");
		sql.append("   FROM Ouvidoria O ");
		if (pesquisa.temNumeroProtocolo()) {
			sql.append(" WHERE O.protocolo.id = :numeroProtocolo ");
		} else if (pesquisa.getCodigoAtendimento() != null) {
			sql.append(" WHERE O.id = :codigoAtendimento ");
		} else if (pesquisa.getEmail() != null) {
			sql.append(" WHERE O.pessoa.id IN (SELECT EP.pessoa.id FROM EmailPessoa EP WHERE EP.descricao = :email) ");
		}
		
		try {
			TypedQuery<Ouvidoria> query = em.createQuery(sql.toString(), Ouvidoria.class);
			if (pesquisa.temNumeroProtocolo()) {
				query.setParameter("numeroProtocolo", Long.parseLong(pesquisa.getNumeroProtocolo()));
			} else if (pesquisa.getCodigoAtendimento() != null) {
				query.setParameter("codigoAtendimento", pesquisa.getCodigoAtendimento());
			} else if (pesquisa.getEmail() != null) {
				query.setParameter("email", pesquisa.getEmail());
			}

			return query.getResultList().size();

		} catch (Throwable e) {
			httpGoApi.geraLog("OuvidoriaDao || getTotalRegistrosPesquisaPublica", StringUtil.convertObjectToJson(pesquisa),
					e);
		}

		return 0;
	}

	public void anexaArquivoOcorrencia(OuvidoriaDto dto) {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE OUVI_ATENDIMENTO O ");
		sql.append("   SET O.FK_ARQUIVO = :idArquivo ");
		sql.append("  WHERE O.CODIGO = :id ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("id", dto.getId());
			query.setParameter("idArquivo", dto.getArquivo().getId());
			
			query.executeUpdate();
		} catch (Throwable e) {
			httpGoApi.geraLog("anexaArquivoOcorrencia || anexaArquivoOcorrencia", StringUtil.convertObjectToJson(dto), e);
		}
	}
	
}
