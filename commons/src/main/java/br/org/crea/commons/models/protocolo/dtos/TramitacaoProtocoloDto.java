package br.org.crea.commons.models.protocolo.dtos;

import java.util.List;

import br.org.crea.commons.models.commons.enuns.ModuloSistema;
import br.org.crea.commons.models.corporativo.dtos.FuncionarioDto;

public class TramitacaoProtocoloDto {
	
	private List<TramiteDto> listProtocolos;
	
	private FuncionarioDto funcionarioTramitacao;
	
	private Long idDepartamentoDestino;
	
	private Long idDepartamentoPaiDestino;
	
	private ModuloSistema modulo;
	
	public List<TramiteDto> getListProtocolos() {
		return listProtocolos;
	}

	public FuncionarioDto getFuncionarioTramitacao() {
		return funcionarioTramitacao;
	}

	public Long getIdDepartamentoDestino() {
		return idDepartamentoDestino;
	}

	public Long getIdDepartamentoPaiDestino() {
		return idDepartamentoPaiDestino;
	}

	public void setListProtocolos(List<TramiteDto> listProtocolos) {
		this.listProtocolos = listProtocolos;
	}

	public void setFuncionarioTramitacao(FuncionarioDto funcionarioTramitacao) {
		this.funcionarioTramitacao = funcionarioTramitacao;
	}

	public void setIdDepartamentoDestino(Long idDepartamentoDestino) {
		this.idDepartamentoDestino = idDepartamentoDestino;
	}

	public void setIdDepartamentoPaiDestino(Long idDepartamentoPaiDestino) {
		this.idDepartamentoPaiDestino = idDepartamentoPaiDestino;
	}

	public boolean destinoTramiteEhArquivo() {
		return this.idDepartamentoDestino.equals(new Long(23040501L));
	}
	
	public boolean destinoTramiteEhArquivoVirtual() {
		return this.idDepartamentoDestino.equals(new Long(23040502L));
	}
	
	public boolean destinoTramiteExecutaJulgamentoRevelia() {
		if (this.idDepartamentoPaiDestino != null) {
			return this.idDepartamentoPaiDestino.equals(new Long(12L)) || this.idDepartamentoDestino.equals(new Long(230201L));
		} else {
			return false;
		}
	}

	public ModuloSistema getModulo() {
		return modulo;
	}

	public void setModulo(ModuloSistema modulo) {
		this.modulo = modulo;
	}
	
	
	
}
