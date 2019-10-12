package br.org.crea.commons.models.protocolo.dtos;

import java.util.List;

import br.org.crea.commons.models.commons.dtos.ProtocoloDto;
import br.org.crea.commons.models.commons.enuns.ModuloSistema;
import br.org.crea.commons.models.corporativo.dtos.FuncionarioDto;
import br.org.crea.commons.models.protocolo.enuns.TipoJuntadaProtocoloEnum;

public class JuntadaProtocoloDto {

	private ProtocoloDto protocoloPrincipal;
	
	private ProtocoloDto protocoloDaJuntada;
	
	private TipoJuntadaProtocoloEnum tipoJuntadaProtocolo;
	
	private boolean possuiVinculoComProtocoloFisicoPortal;
	
	private boolean possuiErrosNaJuntada;
	
	private FuncionarioDto funcionarioDaJuntada;
	
	private List<String> mensagensJuntada;
	
	private ModuloSistema modulo;
	
	public ProtocoloDto getProtocoloPrincipal() {
		return protocoloPrincipal;
	}

	public void setProtocoloPrincipal(ProtocoloDto protocoloPrincipal) {
		this.protocoloPrincipal = protocoloPrincipal;
	}

	public ProtocoloDto getProtocoloDaJuntada() {
		return protocoloDaJuntada;
	}

	public void setProtocoloDaJuntada(ProtocoloDto protocoloDaJuntada) {
		this.protocoloDaJuntada = protocoloDaJuntada;
	}

	public TipoJuntadaProtocoloEnum getTipoJuntadaProtocolo() {
		return tipoJuntadaProtocolo;
	}

	public void setTipoJuntadaProtocolo(TipoJuntadaProtocoloEnum tipoJuntadaProtocolo) {
		this.tipoJuntadaProtocolo = tipoJuntadaProtocolo;
	}

	public FuncionarioDto getFuncionarioDaJuntada() {
		return funcionarioDaJuntada;
	}

	public void setFuncionarioDaJuntada(FuncionarioDto funcionarioDaJuntada) {
		this.funcionarioDaJuntada = funcionarioDaJuntada;
	}

	public boolean isPossuiErrosNaJuntada() {
		return possuiErrosNaJuntada;
	}

	public boolean isPossuiVinculoComProtocoloFisicoPortal() {
		return possuiVinculoComProtocoloFisicoPortal;
	}

	public void setPossuiVinculoComProtocoloFisicoPortal(boolean possuiVinculoComProtocoloFisicoPortal) {
		this.possuiVinculoComProtocoloFisicoPortal = possuiVinculoComProtocoloFisicoPortal;
	}

	public boolean possuiErrosNaJuntada() {
		return possuiErrosNaJuntada;
	}

	public void setPossuiErrosNaJuntada(boolean possuiErrosNaJuntada) {
		this.possuiErrosNaJuntada = possuiErrosNaJuntada;
	}
	
	public List<String> getMensagensJuntada() {
		return mensagensJuntada;
	}

	public void setMensagensJuntada(List<String> mensagensJuntada) {
		this.mensagensJuntada = mensagensJuntada;
	}

	public boolean assuntoProtocoloDoPortalASerAnexadoAptoASerDigitalizado() {
		
		switch (this.protocoloDaJuntada.getAssunto().getId().intValue()) {
		case 2012:
			return true;
		case 1006:
			return true;
		default:
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
