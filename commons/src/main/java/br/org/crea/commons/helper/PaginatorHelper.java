package br.org.crea.commons.helper;

import java.util.List;

import br.org.crea.commons.util.StringUtil;

public class PaginatorHelper {
	
	public PaginatorHelper(int page, int limitTo,  List<String> orderBy, String order) {
		this.page = page;
		this.limitTo = limitTo;
		this.orderBy = orderBy;
		this.order = order;
	}
	
	private int page;
	private int limitTo;
	private List<String> orderBy;
	private String order;
	
	public int getPage() {
		return page;
	}
	public int getLimitTo() {
		return limitTo;
	}
	public String getOrderBy() {		
		StringBuilder sbCampos = new StringBuilder();
		for (String campo : orderBy) {
			if (StringUtil.isValidAndNotEmpty(sbCampos.toString())) {
				sbCampos.append(",").append(campo);				
			}else {
				sbCampos.append(campo);
			}			
		}
		return sbCampos.toString();
	}
	public String getOrder() {
		return order;
	}	

}
