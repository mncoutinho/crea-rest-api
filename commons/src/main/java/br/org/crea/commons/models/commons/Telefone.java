package br.org.crea.commons.models.commons;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.org.crea.commons.models.corporativo.pessoa.Pessoa;

@Entity
@Table(name="CAD_TELEFONES")
@SequenceGenerator(name = "TELEFONES_SEQUENCE", sequenceName = "CAD_TELEFONES_SEQ", initialValue = 1, allocationSize = 1)
public class Telefone{
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TELEFONES_SEQUENCE")
	@Column(name="CODIGO")
	private Long codigo;
	
	@OneToOne
	@JoinColumn(name="FK_CODIGO_PESSOAS")
	private Pessoa pessoa;
	
	@OneToOne
	@JoinColumn(name="FK_CODIGO_TIPO_TELEFONES")
	private TipoTelefone tipoTelefone;
	
	@Column(name="DDD")
	private String ddd;
	
	@Column(name="NUMERO")
	private String numero;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public TipoTelefone getTipoTelefone() {
		return tipoTelefone;
	}

	public void setTipoTelefone(TipoTelefone tipoTelefone) {
		this.tipoTelefone = tipoTelefone;
	}

	public String getDdd() {
		return ddd;
	}

	public void setDdd(String ddd) {
		this.ddd = ddd;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}
}
