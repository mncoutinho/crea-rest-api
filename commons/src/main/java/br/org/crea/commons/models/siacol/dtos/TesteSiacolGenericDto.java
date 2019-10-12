package br.org.crea.commons.models.siacol.dtos;

import java.util.List;

public class TesteSiacolGenericDto {
	
	private Long id;
	
	private String nome;
	
	private String dataManipularReuniao;
	
	private List<VinculoProtocoloDto> listVinculoProtocolo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDataManipularReuniao() {
		return dataManipularReuniao;
	}

	public void setDataManipularReuniao(String dataManipularReuniao) {
		this.dataManipularReuniao = dataManipularReuniao;
	}

	public List<VinculoProtocoloDto> getListVinculos() {
		return listVinculoProtocolo;
	}

	public void setListVinculos(List<VinculoProtocoloDto> listVinculoProtocolo) {
		this.listVinculoProtocolo = listVinculoProtocolo;
	}

}
