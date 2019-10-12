package br.org.crea.commons.dao.corporativo;

import javax.persistence.Query;

public class Page {

	private static final int ROWS_DEFAULT = 10;

	private int page;

	private int rows = ROWS_DEFAULT;

	public Page(int page, int rows) {
		this.page = page;
		this.rows = rows != 0 ? rows : ROWS_DEFAULT;
	}

	public int getPage() {
		return page;
	}

	public int getRows() {
		return rows;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}
	
	public int getMaxResult(){
		return page == 0 ? rows : page * rows;
	}
	
	public int getFirstResult(){
		return page == 0 || page == 1 ? 0 : (getMaxResult() - rows);
	}
	
	public <T> void paginate(Query query) {
		query.setFirstResult(getFirstResult());
		query.setMaxResults(rows);
	}
	
}
