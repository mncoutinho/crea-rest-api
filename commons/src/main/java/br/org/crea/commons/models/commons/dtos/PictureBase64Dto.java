package br.org.crea.commons.models.commons.dtos;

public class PictureBase64Dto {
	
	private String type;
	
	private String data;
	
	private String uuid;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	

}
