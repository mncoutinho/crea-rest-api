package br.org.crea.commons.models.art;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class IdReceitaProduto implements Serializable{

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name="FK_RECEITA")
	private ArtReceita receita;
	
	@ManyToOne
	@JoinColumn(name="FK_PRODUTO")
	private ArtProduto produto;

	public ArtReceita getReceita() {
		return receita;
	}

	public void setReceita(ArtReceita receita) {
		this.receita = receita;
	}

	public ArtProduto getProduto() {
		return produto;
	}

	public void setProduto(ArtProduto produto) {
		this.produto = produto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((produto == null) ? 0 : produto.hashCode());
		result = prime * result + ((receita == null) ? 0 : receita.hashCode());
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
		IdReceitaProduto other = (IdReceitaProduto) obj;
		if (produto == null) {
			if (other.produto != null)
				return false;
		} else if (!produto.equals(other.produto))
			return false;
		if (receita == null) {
			if (other.receita != null)
				return false;
		} else if (!receita.equals(other.receita))
			return false;
		return true;
	}

	
	
	
	
}
