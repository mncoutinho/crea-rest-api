package br.org.crea.commons.models.cadastro.dtos;

import java.io.Serializable;
import java.util.List;

public class CertidaoPesquisaDto implements Serializable {
	
	/**
	 * SERIAL UID
	 */
	private static final long serialVersionUID = 247380146844264337L;
	
	private String registro;
	private String tipoCertidao;
	private String finalidade;
	private Boolean todasEmpresas;
	private List<Long> empresas;
	private Boolean todasArts;
	private List<String> arts;
	private Long localidade;
	
	public String getRegistro() {
		return registro;
	}
	public void setRegistro(String registro) {
		this.registro = registro;
	}
	public String getTipoCertidao() {
		return tipoCertidao;
	}
	public void setTipoCertidao(String tipoCertidao) {
		this.tipoCertidao = tipoCertidao;
	}
	public String getFinalidade() {
		return finalidade;
	}
	public void setFinalidade(String finalidade) {
		this.finalidade = finalidade;
	}
	public Boolean getTodasEmpresas() {
		return todasEmpresas;
	}
	public void setTodasEmpresas(Boolean todasEmpresas) {
		this.todasEmpresas = todasEmpresas;
	}
	public List<Long> getEmpresas() {
		return empresas;
	}
	public void setEmpresas(List<Long> empresas) {
		this.empresas = empresas;
	}
	public Boolean getTodasArts() {
		return todasArts;
	}
	public void setTodasArts(Boolean todasArts) {
		this.todasArts = todasArts;
	}
	public List<String> getArts() {
		return arts;
	}
	public void setArts(List<String> arts) {
		this.arts = arts;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((finalidade == null) ? 0 : finalidade.hashCode());
		result = prime * result + ((registro == null) ? 0 : registro.hashCode());
		result = prime * result + ((tipoCertidao == null) ? 0 : tipoCertidao.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CertidaoPesquisaDto other = (CertidaoPesquisaDto) obj;
		if (finalidade == null) {
			if (other.finalidade != null)
				return false;
		} else if (!finalidade.equals(other.finalidade))
			return false;
		if (registro == null) {
			if (other.registro != null)
				return false;
		} else if (!registro.equals(other.registro))
			return false;
		if (tipoCertidao == null) {
			if (other.tipoCertidao != null)
				return false;
		} else if (!tipoCertidao.equals(other.tipoCertidao))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "CertidaoPesquisaDto [registro=" + registro + ", tipoCertidao=" + tipoCertidao + ", finalidade="
				+ finalidade + ", todasEmpresas=" + todasEmpresas + ", empresas=" + empresas + ", todasArts="
				+ todasArts + ", arts=" + arts + "]";
	}
	public Long getLocalidade() {
		return localidade;
	}
	public void setLocalidade(Long localidade) {
		this.localidade = localidade;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
