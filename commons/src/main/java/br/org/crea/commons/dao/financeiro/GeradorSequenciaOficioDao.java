package br.org.crea.commons.dao.financeiro;

import java.io.Serializable;
import java.text.NumberFormat;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.financeiro.GeradorSequenciaOficio;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.DateUtils;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class GeradorSequenciaOficioDao extends GenericDao<GeradorSequenciaOficio, Serializable>{
	
	@Inject HttpClientGoApi httpGoApi;
	
	public GeradorSequenciaOficioDao(){
		super(GeradorSequenciaOficio.class);
	}

	public Long getSequenciaNumeroTermoInscricao(String sigla) {
		GeradorSequenciaOficio gerador = new GeradorSequenciaOficio();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT G FROM GeradorSequenciaOficio G ");
		sql.append("	WHERE TRIM(G.sigla) = :sigla ");
		sql.append("	AND G.ano = :ano ");
		
		try {

			TypedQuery<GeradorSequenciaOficio> query = em.createQuery(sql.toString(), GeradorSequenciaOficio.class);
			query.setParameter("sigla", sigla);
			query.setParameter("ano", (long) DateUtils.getAnoCorrente());

			gerador = query.getSingleResult();
			gerador.setSequencial(gerador.getSequencial() + 1);

		} catch (NoResultException e) {
			
			gerador = buscaNumeroEAtualizaSequencial(sigla);
			
		} catch (Throwable e) {
			httpGoApi.geraLog("GeradorSequenciaOficioDao || getSequenciaNumeroTermoInscricao", StringUtil.convertObjectToJson(sigla), e);
		}
		return formataSequenciaTermoInscricao(gerador.getAno(), gerador.getSequencial());
		
	}
	
	public GeradorSequenciaOficio buscaNumeroEAtualizaSequencial(String sigla) {
		GeradorSequenciaOficio gerador = new GeradorSequenciaOficio();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT G FROM GeradorSequenciaOficio G ");
		sql.append("	WHERE TRIM(G.sigla) = :sigla ");
		sql.append("	AND G.ano = (SELECT MAX (GS.ano) ");
		sql.append("				 	FROM  GeradorSequenciaOficio GS ");
		sql.append("				 	WHERE TRIM(GS.sigla) GS ");
		
		try {

			TypedQuery<GeradorSequenciaOficio> query = em.createQuery(sql.toString(), GeradorSequenciaOficio.class);
			query.setParameter("sigla", sigla);

			gerador = query.getSingleResult();
			
			StringBuilder sqlInsert = new StringBuilder();
			sqlInsert.append("INSERT INTO cad_gerador_sequencia_oficio	");
			sqlInsert.append("	(codigo, sigla, ano, sequencial, version) ");
			sqlInsert.append("   VALUES (cad_gerador_seq_oficio_SEQ.nextval, :sigla, :ano, :sequencial, :version) ");
			
			Query queryInsert = em.createNativeQuery(sqlInsert.toString());
			queryInsert.setParameter("sigla", sigla);
			queryInsert.setParameter("ano", (long) DateUtils.getAnoCorrente());
			queryInsert.setParameter("sequencial", gerador.getSequencial()+1 );
			queryInsert.setParameter("version", gerador.getVersion()+1 );
			queryInsert.executeUpdate();

		} catch (Throwable e) {
			httpGoApi.geraLog("GeradorSequenciaOficioDao || buscaNumeroEAtualizaSequencial", StringUtil.convertObjectToJson(sigla), e);
		}
		
		return gerador;
	}
	
	private Long formataSequenciaTermoInscricao(long ano, long sequencial){
		
		StringBuilder montador = new StringBuilder();
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumIntegerDigits(5);
		nf.setGroupingUsed(false);
		nf.setParseIntegerOnly(true);
		
		montador.append(nf.format(sequencial));
		return Long.parseLong(montador.toString());
	}
}
