package br.org.crea.commons.models.cadastro;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.org.crea.commons.models.commons.Endereco;

@Entity
@Table(name="ADM_UNIDADE_ATENDIMENTO")
public class UnidadeAtendimento {
	
	@Id
	@Column(name="CODIGO")
	private Long id;
	
	@Column(name="NOME")
	private String nome;
	
	@Column(name="FK_CODIGO_UA_PRINCIPAL")
	private Long regional;
	
	@OneToOne
	@JoinColumn(name="FK_CODIGO_ENDERECO")
	private Endereco endereco;
	
	@Column(name="TELEFONE1")
	private String telefone;
	
	@OneToOne
	@JoinColumn(name="FK_CODIGO_DEPARTAMENTO")
	private Departamento departamento;
	
	@Column(name="EMAIL")
	private String email;
	

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getRegional() {
		return regional;
	}

	public void setRegional(Long regional) {
		this.regional = regional;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
