package br.org.crea.commons.models.commons.dtos;

import java.util.Date;

import br.org.crea.commons.models.cadastro.enuns.TipoEventoAuditoria;
import br.org.crea.commons.models.commons.enuns.ModuloSistema;

public class PesquisaAuditoriaDto {
	
	private ModuloSistema modulo;
	
	private TipoEventoAuditoria evento;
	
	private Date dataInicio;
	
	private Date dataFim;
	
	private Long idUsuario;
	
	private String numero;
	
	private Long idDepartamentoDestino;

	public ModuloSistema getModulo() {
		return modulo;
	}

	public void setModulo(ModuloSistema modulo) {
		this.modulo = modulo;
	}

	public TipoEventoAuditoria getEvento() {
		return evento;
	}

	public void setEvento(TipoEventoAuditoria evento) {
		this.evento = evento;
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

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Long getIdDepartamentoDestino() {
		return idDepartamentoDestino;
	}

	public void setIdDepartamentoDestino(Long idDepartamentoDestino) {
		this.idDepartamentoDestino = idDepartamentoDestino;
	}
	
	
	

}
