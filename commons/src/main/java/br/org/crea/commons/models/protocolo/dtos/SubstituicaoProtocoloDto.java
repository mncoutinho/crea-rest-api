package br.org.crea.commons.models.protocolo.dtos;

import java.util.List;
import java.util.Optional;

import br.org.crea.commons.models.commons.dtos.ProtocoloDto;
import br.org.crea.commons.models.commons.enuns.ModuloSistema;
import br.org.crea.commons.models.corporativo.dtos.FuncionarioDto;

public class SubstituicaoProtocoloDto {

	private ProtocoloDto protocoloSubstituto;
	
	private ProtocoloDto protocoloSubstituido;
	
	private FuncionarioDto funcionarioDaSubstituicao;
	
	private ModuloSistema moduloSistema;
	
	private List<String> mensagensSubstituicao;
	
	private boolean possuiErrosSubstituicao;

	public ProtocoloDto getProtocoloSubstituto() {
		return protocoloSubstituto;
	}

	public void setProtocoloSubstituto(ProtocoloDto protocoloSubstituto) {
		this.protocoloSubstituto = protocoloSubstituto;
	}

	public ProtocoloDto getProtocoloSubstituido() {
		return protocoloSubstituido;
	}

	public void setProtocoloSubstituido(ProtocoloDto protocoloSubstituido) {
		this.protocoloSubstituido = protocoloSubstituido;
	}

	public FuncionarioDto getFuncionarioDaSubstituicao() {
		return funcionarioDaSubstituicao;
	}

	public void setFuncionarioDaSubstituicao(FuncionarioDto funcionarioDaSubstituicao) {
		this.funcionarioDaSubstituicao = funcionarioDaSubstituicao;
	}

	public ModuloSistema getModuloSistema() {
		return moduloSistema;
	}

	public void setModuloSistema(ModuloSistema moduloSistema) {
		this.moduloSistema = moduloSistema;
	}

	public List<String> getMensagensSubstituicao() {
		return mensagensSubstituicao;
	}

	public void setMensagensSubstituicao(List<String> mensagensSubstituicao) {
		this.mensagensSubstituicao = mensagensSubstituicao;
	}

	public boolean possuiErrosSubstituicao() {
		return possuiErrosSubstituicao;
	}

	public void setPossuiErrosSubstituicao(boolean possuiErrosSubstituicao) {
		this.possuiErrosSubstituicao = possuiErrosSubstituicao;
	}

	public Optional<ProtocoloDto> optionalProtocoloSubstituto() {
		return Optional.ofNullable(this.protocoloSubstituto);
	}
	
	public Optional<ProtocoloDto> optionalProtocoloSubstituido() {
		return Optional.ofNullable(this.protocoloSubstituido);
	}
}
