package br.org.crea.commons.models.cadastro;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.org.crea.commons.models.corporativo.pessoa.PessoaJuridica;

@Entity
@Table(name="CAD_ENTIDADES_CLASSE")
public class EntidadeClasse implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CODIGO")
	private Long id;
	
	@OneToOne
	@JoinColumn(name="FK_ID_PESSOAS_JURIDICAS")
	private PessoaJuridica pessoaJuridica;
	
	@Column(name="CODIGO_ART")
	private String codigoReferenciaArt;
	
	@Column(name="FK_CODIGO_TIPOS_ENTIDADES")
	private Long tipoEntidade;
	
	public EntidadeClasse () {}
	
	public EntidadeClasse (Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public PessoaJuridica getPessoaJuridica() {
		return pessoaJuridica;
	}
	public void setPessoaJuridica(PessoaJuridica pessoaJuridica) {
		this.pessoaJuridica = pessoaJuridica;
	}

	public String getCodigoReferenciaArt() {
		return codigoReferenciaArt;
	}

	public void setCodigoReferenciaArt(String codigoReferenciaArt) {
		this.codigoReferenciaArt = codigoReferenciaArt;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getTipoEntidade() {
		return tipoEntidade;
	}

	public void setTipoEntidade(Long tipoEntidade) {
		this.tipoEntidade = tipoEntidade;
	}
	
	


}
