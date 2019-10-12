package br.org.crea.commons.models.siacol.dtos;


public class SiacolRlProtocoloTagsDto {
	
	private Long id;
	
	private Long numeroProtocolo;
	
	private String descricao;

	private ProtocoloSiacolDto protocolo;

	private SiacolTagsDto tag;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNumeroProtocolo() {
		return numeroProtocolo;
	}

	public void setNumeroProtocolo(Long numeroProtocolo) {
		this.numeroProtocolo = numeroProtocolo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public ProtocoloSiacolDto getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(ProtocoloSiacolDto protocolo) {
		this.protocolo = protocolo;
	}

	public SiacolTagsDto getTag() {
		return tag;
	}

	public void setTag(SiacolTagsDto tag) {
		this.tag = tag;
	}
	
	
}
