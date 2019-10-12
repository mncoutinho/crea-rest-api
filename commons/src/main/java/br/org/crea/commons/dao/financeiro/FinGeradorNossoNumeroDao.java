package br.org.crea.commons.dao.financeiro;

import java.io.Serializable;
import java.util.Calendar;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.financeiro.FinGeradorNossoNumero;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.DateUtils;

@Stateless
public class FinGeradorNossoNumeroDao extends GenericDao<FinGeradorNossoNumero, Serializable>{
	
	@Inject HttpClientGoApi httpGoApi;
	
	public FinGeradorNossoNumeroDao(){
		super(FinGeradorNossoNumero.class);
	}

	public synchronized String getSequenciaNossoNumero() {
		
		String nossoNumero = null;	
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT FIN_NOSSONUMERO_SEQ.nextval FROM DUAL ");
				
		try {
			Query query = em.createNativeQuery(sql.toString());
			
			String ns =  query.getSingleResult().toString().trim();
			nossoNumero = DateUtils.getAnoCorrente() + "2000000000";
			nossoNumero = nossoNumero.substring(0, 14 - ns.length()) + ns;
			nossoNumero = nossoNumero + this.getDVNossnoNumeroModulo11(nossoNumero);
		} catch (Exception e) {
			nossoNumero = this.gerarNossoNumeroPelaData();
		}
		return nossoNumero;

	}


	public static String getDVNossnoNumeroModulo11 (String nossoNumero){
		String nossoNumeroCovertidao = "";
		int ia = nossoNumero.length();
		
		for (char a : nossoNumero.toCharArray()) {
			nossoNumeroCovertidao += nossoNumero.charAt(ia-1);
			ia--;
		}
		int i = 9;
		double total = 0;
		for (char a : nossoNumeroCovertidao.toCharArray()) {
			total += Integer.parseInt(String.valueOf(a)) * i;
			i--;
			if(i == 1){
				i = 9;
			}
		}
		int finalizado = (int) (total%11);
		if(finalizado > 9){
			return "1";
		}
		return ""+finalizado;

	}



	public String gerarNossoNumeroPelaData() {
		Calendar dt = Calendar.getInstance();
		long t0=System.currentTimeMillis();
		String ano         = "" +  dt.get(Calendar.YEAR);
		String mes         = "" + (dt.get(Calendar.MONTH) + 101); 
	    String dia         = "" + (dt.get(Calendar.DAY_OF_MONTH) + 100); 
		String hor         = "" + (dt.get(Calendar.HOUR_OF_DAY) + 100);
		String min         = "" + (dt.get(Calendar.MINUTE) + 100);
		String seg         = "" + (dt.get(Calendar.SECOND) + 100);
		String mil         = "" + (dt.get(Calendar.MILLISECOND) + 1000);		
		
		String nossoNumero = ano + 
		                     mes.substring(1) +
		                     dia.substring(1) +
		                     hor.substring(1) +
		                     min.substring(1) +
		                     seg.substring(1) +
		                     mil.substring(1);
        long t1;
        do {
           t1=System.currentTimeMillis();
       } while (t1-t0 < 1);
        
       return nossoNumero;
	}

	
	public String gerarNossoNumeroComConvenio (Long idConvenio) {
		String convenio = String.valueOf(idConvenio);
		
		if (convenio.length() < 7) {
			return this.getSequenciaNossoNumero();
		}
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT FIN_CONV" + convenio.trim() + "_SEQ.NextVal FROM DUAL ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			
			String ns = query.getSingleResult().toString().trim();
			String nu = (String.format("%07d", Long.parseLong(convenio)) + (String.format("%010d", new Long(ns))));
			
			return nu;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}

}
