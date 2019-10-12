package br.org.crea.commons.models.art;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.org.crea.commons.models.cadastro.UnidadeMedida;

@Entity
@Table(name="ART_RECEITA")
@SequenceGenerator(name = "sqReceita", sequenceName = "ART_RECEITA_SEQUENCE", initialValue = 1, allocationSize = 1)
public class ArtReceita {

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sqReceita")
	private Long id;
	
	@OneToOne
	@JoinColumn(name="FK_ART")
	private Art art;
	
	@Column(name="INFORMACOES_COMPL")
	private String informacoesComplementares;
	
	@OneToOne
	@JoinColumn(name="FK_CULTURA")
	private ArtCultura cultura;
	
	@OneToOne
	@JoinColumn(name="FK_DIAGNOSTICO")
	private ArtDiagnostico diagnostico;
	
	@Column(name="AREA_VOLUME_PESO")
	private Long areaVolumePeso;
	
	@OneToOne
	@JoinColumn(name="FK_UNIDADE_MEDIDA")
	private UnidadeMedida unidadeMedida;
	
	@OneToMany(mappedBy="id.receita", cascade= CascadeType.ALL)
	private List<ArtReceitaProduto> receitaProdutos;
	
	@OneToOne(mappedBy="receita")
	private ContratoArt contrato;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Art getArt() {
		return art;
	}

	public void setArt(Art art) {
		this.art = art;
	}

	public String getInformacoesComplementares() {
		return informacoesComplementares;
	}

	public void setInformacoesComplementares(String informacoesComplementares) {
		this.informacoesComplementares = informacoesComplementares;
	}

	public ArtCultura getCultura() {
		return cultura;
	}

	public void setCultura(ArtCultura cultura) {
		this.cultura = cultura;
	}

	public ArtDiagnostico getDiagnostico() {
		return diagnostico;
	}

	public void setDiagnostico(ArtDiagnostico diagnostico) {
		this.diagnostico = diagnostico;
	}

	public Long getAreaVolumePeso() {
		return areaVolumePeso;
	}

	public void setAreaVolumePeso(Long areaVolumePeso) {
		this.areaVolumePeso = areaVolumePeso;
	}

	public UnidadeMedida getUnidadeMedida() {
		return unidadeMedida;
	}

	public void setUnidadeMedida(UnidadeMedida unidadeMedida) {
		this.unidadeMedida = unidadeMedida;
	}

	public List<ArtReceitaProduto> getReceitaProdutos() {
		return receitaProdutos;
	}

	public void setReceitaProdutos(List<ArtReceitaProduto> receitaProdutos) {
		this.receitaProdutos = receitaProdutos;
	}

	public ContratoArt getContrato() {
		return contrato;
	}

	public void setContrato(ContratoArt contrato) {
		this.contrato = contrato;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((areaVolumePeso == null) ? 0 : areaVolumePeso.hashCode());
		result = prime * result + ((art == null) ? 0 : art.hashCode());
		result = prime * result + ((contrato == null) ? 0 : contrato.hashCode());
		result = prime * result + ((cultura == null) ? 0 : cultura.hashCode());
		result = prime * result + ((diagnostico == null) ? 0 : diagnostico.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((informacoesComplementares == null) ? 0 : informacoesComplementares.hashCode());
		result = prime * result + ((receitaProdutos == null) ? 0 : receitaProdutos.hashCode());
		result = prime * result + ((unidadeMedida == null) ? 0 : unidadeMedida.hashCode());
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
		ArtReceita other = (ArtReceita) obj;
		if (areaVolumePeso == null) {
			if (other.areaVolumePeso != null)
				return false;
		} else if (!areaVolumePeso.equals(other.areaVolumePeso))
			return false;
		if (art == null) {
			if (other.art != null)
				return false;
		} else if (!art.equals(other.art))
			return false;
		if (contrato == null) {
			if (other.contrato != null)
				return false;
		} else if (!contrato.equals(other.contrato))
			return false;
		if (cultura == null) {
			if (other.cultura != null)
				return false;
		} else if (!cultura.equals(other.cultura))
			return false;
		if (diagnostico == null) {
			if (other.diagnostico != null)
				return false;
		} else if (!diagnostico.equals(other.diagnostico))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (informacoesComplementares == null) {
			if (other.informacoesComplementares != null)
				return false;
		} else if (!informacoesComplementares.equals(other.informacoesComplementares))
			return false;
		if (receitaProdutos == null) {
			if (other.receitaProdutos != null)
				return false;
		} else if (!receitaProdutos.equals(other.receitaProdutos))
			return false;
		if (unidadeMedida == null) {
			if (other.unidadeMedida != null)
				return false;
		} else if (!unidadeMedida.equals(other.unidadeMedida))
			return false;
		return true;
	}

	
}
