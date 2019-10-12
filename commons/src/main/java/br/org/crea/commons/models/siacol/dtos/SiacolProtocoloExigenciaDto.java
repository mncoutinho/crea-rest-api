package br.org.crea.commons.models.siacol.dtos;

import java.util.Date;

import br.org.crea.commons.models.commons.dtos.ArquivoDto;

public class SiacolProtocoloExigenciaDto {

	private Long id;
	
	private ProtocoloSiacolDto protocolo;
	
	private String motivo;
	
	private String tipoContato;
	
	private String pessoaContato;
	
	private Date dataContato;

	private Date dataInicio;

	private Date dataFim;

	private ArquivoDto arquivo;
	
	private String descricao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ProtocoloSiacolDto getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(ProtocoloSiacolDto protocolo) {
		this.protocolo = protocolo;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getTipoContato() {
		return tipoContato;
	}

	public void setTipoContato(String tipoContato) {
		this.tipoContato = tipoContato;
	}

	public String getPessoaContato() {
		return pessoaContato;
	}

	public void setPessoaContato(String pessoaContato) {
		this.pessoaContato = pessoaContato;
	}

	public Date getDataContato() {
		return dataContato;
	}

	public void setDataContato(Date dataContato) {
		this.dataContato = dataContato;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public boolean temId() {
		return this.id != null ? true : false;
	}

	public ArquivoDto getArquivo() {
		return arquivo;
	}

	public void setArquivo(ArquivoDto arquivo) {
		this.arquivo = arquivo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
}
