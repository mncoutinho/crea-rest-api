package br.org.crea.commons.models.cadastro.dtos;

import br.org.crea.commons.models.corporativo.dtos.PessoaDto;

public class ParticipanteEventoDto {
	
	private Long id;
	
	private String numeroRnp;
	
	private String localEvento;
	
	private String endereco;
	
	private String dataFormatada;
	
	private String horaFormatada;
	
	private PessoaDto participante;
	
	private Long idEvento;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getNumeroRnp() {
		return numeroRnp;
	}

	public void setNumeroRnp(String numeroRnp) {
		this.numeroRnp = numeroRnp;
	}

	public PessoaDto getParticipante() {
		return participante;
	}

	public void setParticipante(PessoaDto participante) {
		this.participante = participante;
	}

	public Long getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(Long idEvento) {
		this.idEvento = idEvento;
	}

	public String getLocalEvento() {
		return localEvento;
	}

	public void setLocalEvento(String localEvento) {
		this.localEvento = localEvento;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getDataFormatada() {
		return dataFormatada;
	}

	public void setDataFormatada(String dataFormatada) {
		this.dataFormatada = dataFormatada;
	}

	public String getHoraFormatada() {
		return horaFormatada;
	}

	public void setHoraFormatada(String horaFormatada) {
		this.horaFormatada = horaFormatada;
	}
	
	
	

}
