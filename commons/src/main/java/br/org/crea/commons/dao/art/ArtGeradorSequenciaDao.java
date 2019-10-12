package br.org.crea.commons.dao.art;

import java.io.Serializable;
import java.text.NumberFormat;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.art.GeradorSequenciaArt;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class ArtGeradorSequenciaDao extends GenericDao<GeradorSequenciaArt, Serializable>{
	
	@Inject
	HttpClientGoApi httpGoApi;
	
	public ArtGeradorSequenciaDao() {
		super(GeradorSequenciaArt.class);
	}
	
	public String getSequenciaArt() {
	        
        GeradorSequenciaArt gerador = new GeradorSequenciaArt();   
        
        
        StringBuilder sql = new StringBuilder();
		sql.append(" SELECT G FROM GeradorSequenciaArt G ");
		sql.append("    WHERE G.alfa = 20 ");
		sql.append("    AND G.ano = :ano ");
       
        try{
        	TypedQuery<GeradorSequenciaArt> query = em.createQuery(sql.toString(), GeradorSequenciaArt.class);
	        query.setParameter("ano", gerador.getAnoAtual());
	                   
            gerador = query.getSingleResult();
            gerador.proximo();
 
        } catch (NoResultException e) {
        	
        	GeradorSequenciaArt geradorNovo = new GeradorSequenciaArt(); 
        	
        	Query queryNova = em.createQuery(" SELECT MAX(G.codigo) FROM GeradorSequenciaArt G ");
        	Long codigo =  (Long) queryNova.getSingleResult(); 

            geradorNovo.setCodigo(codigo+1);
            geradorNovo.setSequencial(1);
            geradorNovo.setAlfa("20");
            geradorNovo.setVersion(1);
            geradorNovo.setAno(String.valueOf(gerador.getAnoAtual()));
        	
        	StringBuilder sqlUpdate = new StringBuilder();
        	sqlUpdate.append(" INSERT INTO ART_GERADOR_SEQUENCIAS ");
        	sqlUpdate.append(" (CODIGO, ALFA, ANO, SEQUENCIAL, VERSION) ");
        	sqlUpdate.append(" values (:codigo, :alfa, :ano, :sequencial, :version ) ");
        	
        	Query queryUpdate = em.createNativeQuery(sqlUpdate.toString());
        	queryUpdate.setParameter("codigo", geradorNovo.getCodigo());
        	queryUpdate.setParameter("alfa", geradorNovo.getAlfa());
        	queryUpdate.setParameter("ano", geradorNovo.getAno());
        	queryUpdate.setParameter("sequencial", geradorNovo.getSequencial());
        	queryUpdate.setParameter("version", geradorNovo.getVersion());
        	
        	queryUpdate.executeUpdate();
        	
        	return formataSequencia(geradorNovo); 
		}catch (Throwable e) {
        	httpGoApi.geraLog("ArtGeradorSequenciaDao || Get Sequencia Art", StringUtil.convertObjectToJson("SEM PARAMETRO"), e);
		}

        return formataSequencia(gerador); 
	}
	     
    private String formataSequencia(GeradorSequenciaArt gerador) {
        
        StringBuilder montador = new StringBuilder();
        
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMinimumIntegerDigits(7);
        nf.setGroupingUsed(false);
        nf.setParseIntegerOnly(true);
        
        montador.append(gerador.getAlfa() + gerador.getAno() + nf.format(gerador.getSequencial()));
        	        
        return montador.toString();     
    }
	    
}
