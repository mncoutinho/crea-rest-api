package br.org.crea.commons.dao.cadastro.pessoa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.dao.corporativo.Page;
import br.org.crea.commons.models.cadastro.Historico;
import br.org.crea.commons.models.commons.dtos.PesquisaGenericDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.DateUtils;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class HistoricoDao extends GenericDao<Historico, Serializable> {

	@Inject
	HttpClientGoApi httpGoApi;
	
	public HistoricoDao() {
		super(Historico.class);
	}
	
	public List<Historico> getHistoricosByPessoa(Long idPessoa){
		List<Historico> historicos = new ArrayList<Historico>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("  SELECT h FROM Historico h ");
		sql.append("   WHERE h.pessoa.id = :idPessoa ");
		sql.append("ORDER BY h.dataInicio DESC ");
		
		try{
			TypedQuery<Historico> query = em.createQuery(sql.toString(), Historico.class);
			query.setParameter("idPessoa", idPessoa);
			
			historicos = query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("HistoricoDao || Get Historico by Pessoa", StringUtil.convertObjectToJson(idPessoa), e);
		}	
		
		return historicos;
	}
	
	public List<Historico> getHistoricosByPessoaEByEvento(Long idPessoa, Long idEvento){
		List<Historico> historicos = new ArrayList<Historico>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("  SELECT H FROM Historico H      ");
		sql.append("   WHERE H.pessoa.id = :idPessoa ");
		sql.append("     AND H.evento.id = :idEvento ");
		sql.append("  ORDER BY H.dataInicio          ");
		
		try{
			TypedQuery<Historico> query = em.createQuery(sql.toString(), Historico.class);
			query.setParameter("idPessoa", idPessoa);
			query.setParameter("idEvento", idEvento);
			
			historicos = query.getResultList();
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("HistoricoDao || getHistoricosByPessoaEByHistorico", StringUtil.convertObjectToJson(idPessoa + " - " + idEvento), e);
		}	
		
		return historicos;
	}
	
	public boolean possuiRegistroDeInatividadeNoInicioDaObraServicoDeAcordoCom(Long idPessoas, Date dataInicio) {

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(H.CODIGO)                                                       ");
		sql.append(" FROM CAD_HISTORICOS H                                                        ");
		sql.append(" WHERE H.FK_CODIGO_PESSOAS = :idPessoas                                       ");
		sql.append(" AND TO_CHAR(H.DATAINICIO, 'YYYYMMDD') <=  :dataInicio                        ");
		sql.append(" AND H.FK_CODIGO_EVENTOS < 20                                                 ");
		sql.append(" AND H.FK_CODIGO_EVENTOS <> 0                                                 ");
		sql.append(" AND (TO_CHAR(H.DATAFINAL, 'YYYYMMDD') > :dataInicio OR H.DATAFINAL IS NULL)  ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idPessoas", idPessoas);
			query.setParameter("dataInicio", DateUtils.format(dataInicio, DateUtils.YYYY_MM_DD));

			BigDecimal qt = (BigDecimal) query.getSingleResult();
		
			return qt.compareTo(new BigDecimal(0)) > 0;
		
		} catch (NoResultException e) {
			return false;
		} catch (Throwable e) {
			httpGoApi.geraLog("HistoricoDao || possuiRegistroDeInatividadeNoInicioDaObraServicoDeAcordoCom", StringUtil.convertObjectToJson(idPessoas+""+dataInicio), e);
		}
		return false;
	}
	
	public boolean possuiRegistroDeInatividadeDuranteAObraServicoDeAcordoCom(Long idPessoas, Date dataInicio, Date dataFim) {

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(H.CODIGO)                                ");
		sql.append(" FROM CAD_HISTORICOS H                                 ");
		sql.append(" WHERE H.FK_CODIGO_PESSOAS = :idPessoas                ");
		sql.append(" AND TO_CHAR(H.DATAINICIO, 'YYYYMMDD') >=  :dataInicio ");
		sql.append(" AND H.FK_CODIGO_EVENTOS < 20                          ");
		sql.append(" AND H.FK_CODIGO_EVENTOS <> 0                          ");
		sql.append(" AND TO_CHAR(H.DATAINICIO, 'YYYYMMDD') <= :dataFim     ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idPessoas", idPessoas);
			query.setParameter("dataInicio", DateUtils.format(dataInicio, DateUtils.YYYY_MM_DD));
			query.setParameter("dataFim", DateUtils.format(dataFim, DateUtils.YYYY_MM_DD));

			BigDecimal qt = (BigDecimal) query.getSingleResult();
		
			return qt.compareTo(new BigDecimal(0)) > 0;
		
		} catch (NoResultException e) {
			return false;
		} catch (Throwable e) {
			httpGoApi.geraLog("HistoricoDao || possuiRegistroDeInatividadeDuranteAObraServicoDeAcordoCom", StringUtil.convertObjectToJson(idPessoas+""+dataInicio+""+dataFim), e);
		}
		return false;
	}

	public List<Historico> getHistoricosPaginadoByPessoa(PesquisaGenericDto pesquisa) {
		List<Historico> historicos = new ArrayList<Historico>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("   SELECT h FROM Historico h      ");
		sql.append("    WHERE h.pessoa.id = :idPessoa ");
		sql.append(" ORDER BY h.dataInicio DESC       ");
		
		try{
			TypedQuery<Historico> query = em.createQuery(sql.toString(), Historico.class);
			query.setParameter("idPessoa", pesquisa.getId());
			
			Page page = new Page(pesquisa.getPage(), pesquisa.getRows());
			page.paginate(query);
			
			historicos = query.getResultList();
		} catch (NoResultException e) {
			return historicos;
		} catch (Throwable e) {
			httpGoApi.geraLog("HistoricoDao || getHistoricosPaginadoByPessoa", StringUtil.convertObjectToJson(pesquisa), e);
		}	
		
		return historicos;
	}

	public int getTotalDeHistoricos(PesquisaGenericDto pesquisa) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(H.CODIGO)                  ");
		sql.append("   FROM CAD_HISTORICOS H                 ");
		sql.append("  WHERE H.FK_CODIGO_PESSOAS = :idPessoa  ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idPessoa", pesquisa.getId());

			BigDecimal resultado = (BigDecimal) query.getSingleResult();
			return resultado.intValue();

		} catch (Throwable e) {
			httpGoApi.geraLog("HistoricoDao || getTotalDeHistoricos", StringUtil.convertObjectToJson(pesquisa), e);
		}

		return 0;
	}
	

}
