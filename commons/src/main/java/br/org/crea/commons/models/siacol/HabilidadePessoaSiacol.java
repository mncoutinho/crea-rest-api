package br.org.crea.commons.models.siacol;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.org.crea.commons.models.cadastro.Departamento;
import br.org.crea.commons.models.corporativo.Assunto;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;

@Entity
@Table(name="SIACOL_HABILIDADE_PESSOA")
@SequenceGenerator(name="sqHabilidadePessoa",sequenceName="SQ_HABILIDADE_PESSOA",allocationSize = 1)
public class HabilidadePessoaSiacol {
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sqHabilidadePessoa")
	private Long id;
	
	@OneToOne
	@JoinColumn(name = "FK_PESSOA")
	private Pessoa pessoa;
	
	@OneToOne
	@JoinColumn(name = "FK_ASSUNTO")
	private Assunto assunto;
	
	@OneToOne
	@JoinColumn(name = "FK_ASSUNTO_SIACOL")
	private AssuntoSiacol assuntoSiacol;
	
	@OneToOne
	@JoinColumn(name = "FK_DEPARTAMENTO")
	private Departamento departamento;
	
	@Column(name = "ATIVO")
	private Boolean ativo;
	
	@Column(name = "PESSOA_LIBERADA")
	private Boolean pessoaLiberadaParaReceber;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Assunto getAssunto() {
		return assunto;
	}

	public void setAssunto(Assunto assunto) {
		this.assunto = assunto;
	}
 
	public AssuntoSiacol getAssuntoSiacol() {
		return assuntoSiacol;
	}

	public void setAssuntoSiacol(AssuntoSiacol assuntoSiacol) {
		this.assuntoSiacol = assuntoSiacol;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Boolean pessoaLiberadaParaReceber() {
		return pessoaLiberadaParaReceber;
	}

	public void setPessoaLiberadaParaReceber(Boolean pessoaLiberadaParaReceber) {
		this.pessoaLiberadaParaReceber = pessoaLiberadaParaReceber;
	}
	
	
}
