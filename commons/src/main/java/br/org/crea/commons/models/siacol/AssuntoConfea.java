package br.org.crea.commons.models.siacol;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="SIACOL_ASSUNTO_CONFEA")
@SequenceGenerator(name="sqSiacolAssuntoConfea",sequenceName="SQ_SIACOL_ASSUNTO_CONFEA",allocationSize = 1)
public class AssuntoConfea {
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sqSiacolAssuntoConfea")
	private Long id;
	
	@Column(name="CODIGO")
	private Long codigo;
	
	@Column(name="NOME")
	private String nome;
	
	@Column(name="ATIVO")
	private Boolean ativo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	
	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

}
