package br.org.crea.commons.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

import br.org.crea.commons.enuns.ResponseTypes;

@JsonPropertyOrder({ "type", "messages", "row_count", "total_count", "data", "total"  })
public class ResponseRestApi {

	@JsonProperty("type")
	private ResponseTypes types;
	
	@JsonProperty("data")
	private Object data;
	
	@JsonProperty("row_count")
	private int rowCount;
	
	@JsonProperty("total_count")
	private int totalCount;
	
	@JsonProperty("messages")
	private List<String> messages = new ArrayList<String>();
	
	@JsonIgnore
	private int statusCode;
	
	@JsonIgnore
	private String frase;
	
	@JsonIgnore
	private String header = "";
	
	private InputStream file;

	/* status */
	public ResponseRestApi status(int code) {
		statusCode = code;
		return this;
	}

	/* Tipos */
	public ResponseRestApi type(ResponseTypes type) {
		types = type;
		return this;
	}

	/* Tipos e status */
	public ResponseRestApi serverError() {
		types = ResponseTypes.SERVER_ERROR;
		statusCode = 500;
		return this;
	}

	public ResponseRestApi error() {
		clearMessages();
		types = ResponseTypes.ERROR;
		statusCode = 400;
		return this;
	}

	public ResponseRestApi unauthorized() {
		types = ResponseTypes.UNAUTHORIZED;
		statusCode = 401;
		return this;
	}

	public ResponseRestApi forbidden() {
		types = ResponseTypes.FORBIDDEN;
		statusCode = 403;
		return this;
	}

	public ResponseRestApi success() {
		types = ResponseTypes.SUCCESS;
		statusCode = 200;
		return this;
	}

	public ResponseRestApi information() {
		types = ResponseTypes.INFORMATION;
		statusCode = 200;
		return this;
	}

	public ResponseRestApi confirmation() {
		types = ResponseTypes.CONFIRMATION;
		statusCode = 200;
		return this;
	}
	
	public ResponseRestApi notFound() {
		types = ResponseTypes.INFORMATION;
		statusCode = 404;
		return this;
	}

	public ResponseRestApi message(String message) {
		getMessages().add(message);
		return this;
	}

	public ResponseRestApi messages(List<String> messages) {
		this.messages = messages;
		return this;
	}

	public boolean hasMensages() {
		return getMessages().isEmpty() ? false : true;
	}

	public void clearMessages() {
		this.getMessages().clear();
	}

	public ResponseRestApi frase(String frase) {
		this.frase = frase;
		return this;
	}

	public boolean hasError() {
		return getMessages().isEmpty() ? false : true;
	}

	public ResponseRestApi header(String header) {
		this.header = header;
		return this;
	}

	public ResponseRestApi data(Object object) {
		data = object;
		return this;
	}

	public <T> T getData(Class<T> clazz) {
		return clazz.cast(data);
	}

	public ResponseRestApi rowCount(int total) {
		this.rowCount = total;
		return this;
	}

	public ResponseRestApi totalCount(int total) {
		this.totalCount = total;
		return this;
	}

	public Response build() {
		
		if (file != null) {
			return Response.status(statusCode).entity(file).header("Content-Disposition", header).type(MediaType.APPLICATION_OCTET_STREAM).build();
		}

		if (!header.equals("")) {
			return Response.status(statusCode).entity(this).header("Content-Disposition", header)
					.type(MediaType.APPLICATION_JSON).build();
		} else {
			return Response.status(statusCode).entity(this).type(MediaType.APPLICATION_JSON).build();
		}
	}

	public Response string() {
		if (!header.equals("")) {
			return Response.status(statusCode).entity(this).header("Content-Disposition", header)
					.type(MediaType.TEXT_HTML).build();
		} else {
			return Response.status(statusCode).entity(this).type(MediaType.TEXT_HTML).build();
		}
	}
	
	public Object getData() {
		return data;
	}

	public int getRowCount() {
		return rowCount;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getFrase() {
		return frase;
	}

	public String getHeader() {
		return header;
	}
	
	public List<String> getMessages() {
		return messages;
	}
	
	public ResponseTypes getTypes() {
		return types;
	}



}