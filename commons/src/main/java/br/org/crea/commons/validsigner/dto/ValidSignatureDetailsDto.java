package br.org.crea.commons.validsigner.dto;

public class ValidSignatureDetailsDto {

	private String uuid;

	private String baosPdf;

	private String hash;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getBaosPdf() {
		return baosPdf;
	}

	public void setBaosPdf(String osPDF) {
		this.baosPdf = osPDF;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

}
