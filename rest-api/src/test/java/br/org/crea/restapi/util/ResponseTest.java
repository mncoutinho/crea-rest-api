package br.org.crea.restapi.util;

import java.util.ArrayList;
import java.util.List;

import br.org.crea.commons.enuns.ResponseTypes;

public class ResponseTest {
	
	private ResponseTypes types;
	
	private Object data;
	
	private int rowCount;
	
	private int totalCount;
	
	private List<String> messages = new ArrayList<String>();

	public ResponseTypes getTypes() {
		return types;
	}

	public void setTypes(ResponseTypes types) {
		this.types = types;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

}
