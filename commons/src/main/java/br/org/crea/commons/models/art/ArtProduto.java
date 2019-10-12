package br.org.crea.commons.models.art;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ART_PRODUTO")
public class ArtProduto {
	
	@Id
	@Column(name="CODIGO")
	private Long codigo;
	
	@Column(name="DESCRICAO")
	private String descricao;
	
	@Column(name="INGREDIENTE_ATIVO")
	private String ingredienteAtivo;
	
	@Column(name="CONCENTRACAO")
	private String concentracao;
	
	@Column(name="CLASSE_USO")
	private String classeUso;
	
	@Column(name="CLASSE_TOXICOLOGICA")
	private String classeToxicologica;
	
	@Column(name="GRAU_PERICULO_AMBIENTAL")
	private String grauPericuloAmbiental;
	
	@Column(name="TITULAR_CADASTRO")
	private String titularCadastro;
	
	@Column(name="CNPJ")
	private String cnpj;
	
	@Column(name="PROCESSO_ADM")
	private String processoAdm;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getIngredienteAtivo() {
		return ingredienteAtivo;
	}

	public void setIngredienteAtivo(String ingredienteAtivo) {
		this.ingredienteAtivo = ingredienteAtivo;
	}

	public String getConcentracao() {
		return concentracao;
	}

	public void setConcentracao(String concentracao) {
		this.concentracao = concentracao;
	}

	public String getClasseUso() {
		return classeUso;
	}

	public void setClasseUso(String classeUso) {
		this.classeUso = classeUso;
	}

	public String getClasseToxicologica() {
		return classeToxicologica;
	}

	public void setClasseToxicologica(String classeToxicologica) {
		this.classeToxicologica = classeToxicologica;
	}

	public String getGrauPericuloAmbiental() {
		return grauPericuloAmbiental;
	}

	public void setGrauPericuloAmbiental(String grauPericuloAmbiental) {
		this.grauPericuloAmbiental = grauPericuloAmbiental;
	}

	public String getTitularCadastro() {
		return titularCadastro;
	}

	public void setTitularCadastro(String titularCadastro) {
		this.titularCadastro = titularCadastro;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getProcessoAdm() {
		return processoAdm;
	}

	public void setProcessoAdm(String processoAdm) {
		this.processoAdm = processoAdm;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((classeToxicologica == null) ? 0 : classeToxicologica.hashCode());
		result = prime * result + ((classeUso == null) ? 0 : classeUso.hashCode());
		result = prime * result + ((cnpj == null) ? 0 : cnpj.hashCode());
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + ((concentracao == null) ? 0 : concentracao.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((grauPericuloAmbiental == null) ? 0 : grauPericuloAmbiental.hashCode());
		result = prime * result + ((ingredienteAtivo == null) ? 0 : ingredienteAtivo.hashCode());
		result = prime * result + ((processoAdm == null) ? 0 : processoAdm.hashCode());
		result = prime * result + ((titularCadastro == null) ? 0 : titularCadastro.hashCode());
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
		ArtProduto other = (ArtProduto) obj;
		if (classeToxicologica == null) {
			if (other.classeToxicologica != null)
				return false;
		} else if (!classeToxicologica.equals(other.classeToxicologica))
			return false;
		if (classeUso == null) {
			if (other.classeUso != null)
				return false;
		} else if (!classeUso.equals(other.classeUso))
			return false;
		if (cnpj == null) {
			if (other.cnpj != null)
				return false;
		} else if (!cnpj.equals(other.cnpj))
			return false;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (concentracao == null) {
			if (other.concentracao != null)
				return false;
		} else if (!concentracao.equals(other.concentracao))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (grauPericuloAmbiental == null) {
			if (other.grauPericuloAmbiental != null)
				return false;
		} else if (!grauPericuloAmbiental.equals(other.grauPericuloAmbiental))
			return false;
		if (ingredienteAtivo == null) {
			if (other.ingredienteAtivo != null)
				return false;
		} else if (!ingredienteAtivo.equals(other.ingredienteAtivo))
			return false;
		if (processoAdm == null) {
			if (other.processoAdm != null)
				return false;
		} else if (!processoAdm.equals(other.processoAdm))
			return false;
		if (titularCadastro == null) {
			if (other.titularCadastro != null)
				return false;
		} else if (!titularCadastro.equals(other.titularCadastro))
			return false;
		return true;
	}	
}
