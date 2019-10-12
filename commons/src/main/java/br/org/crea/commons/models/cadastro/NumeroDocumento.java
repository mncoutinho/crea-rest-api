package br.org.crea.commons.models.cadastro;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "CAD_DOCUMENTO_NUMERO")
@SequenceGenerator(name = "sqCadDocumentoNumero", sequenceName = "SQ_CAD_DOCUMENTO_NUMERO", initialValue = 1, allocationSize = 1)
public class NumeroDocumento {
	
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sqCadDocumentoNumero")
	private Long id;
	
	@OneToOne
	@JoinColumn(name="FK_TIPO_DOCUMENTO")
	private TipoDocumento tipo;
	
	@Column(name="NUMERO")
	private Long numero;
	
	@Column(name="ANO")
	private String ano;
	
	@Column(name="TEM_ANO")
	private boolean tem_ano;
	
	@OneToOne
	@JoinColumn(name="FK_DEPARTAMENTO")
	private Departamento departamento;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoDocumento getTipo() {
		return tipo;
	}

	public void setTipo(TipoDocumento tipo) {
		this.tipo = tipo;
	}

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}


	public boolean isTem_ano() {
		return tem_ano;
	}

	public void setTem_ano(boolean tem_ano) {
		this.tem_ano = tem_ano;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}
		

}
