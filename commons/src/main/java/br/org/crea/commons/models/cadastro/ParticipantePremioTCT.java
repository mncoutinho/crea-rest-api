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

import br.org.crea.commons.models.corporativo.pessoa.PessoaFisica;


@Entity
@Table(name = "CAD_PARTICIPANTES_TCT")
@SequenceGenerator(name = "PARTICIPANTE_PREMIO_SEQUENCE", sequenceName = "SQ_CAD_PARTICIPANTE_PREMIO_TCT", initialValue = 1, allocationSize = 1)

public class ParticipantePremioTCT {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PARTICIPANTE_PREMIO_SEQUENCE")
	@Column(name = "ID")
	private Long id;
	
	@OneToOne
	@JoinColumn(name="FK_CAD_PREMIO")
	private PremioTCT premio;
	
	@OneToOne
	@JoinColumn(name="FK_PESSOA")
	private PessoaFisica pessoa;
	
	@Column(name="PAPEL")
	private String papel;
	
	@Column(name="EMAIL")
	private String email;
	
	@Column(name="TELEFONE")
	private String telefone;
	
	@Column(name="CELULAR")
	private String celular;
	
	@Column(name="CAD_ENDERECO_FK")
	private Long idEndereco;
	
	@Column(name="CAD_TITULO_FK")
	private Long idTitulo;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PremioTCT getPremio() {
		return premio;
	}

	public void setPremio(PremioTCT premio) {
		this.premio = premio;
	}

	public PessoaFisica getPessoa() {
		return pessoa;
	}

	public void setPessoa(PessoaFisica pessoa) {
		this.pessoa = pessoa;
	}

	public String getPapel() {
		return papel;
	}

	public void setPapel(String papel) {
		this.papel = papel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public Long getIdEndereco() {
		return idEndereco;
	}

	public void setIdEndereco(Long idEndereco) {
		this.idEndereco = idEndereco;
	}

	public Long getIdTitulo() {
		return idTitulo;
	}

	public void setIdTitulo(Long idTitulo) {
		this.idTitulo = idTitulo;
	}
	
}