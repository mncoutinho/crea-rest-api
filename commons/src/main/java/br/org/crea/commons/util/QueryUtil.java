package br.org.crea.commons.util;

import java.util.List;

import javax.persistence.Query;


public class QueryUtil {	

	@SuppressWarnings("unchecked")
	public static <T> T getSingleResultOrNull(Query query) {
	    List<T> list = query.getResultList();
	    if (list == null || list.isEmpty() || list.size() > 1) {
	        return null;
	    }

	    return list.get(0);
	}	

}
