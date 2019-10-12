package br.org.crea.commons.models.siacol;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.org.crea.commons.models.corporativo.pessoa.Pessoa;


@Entity
@Table(name = "CAD_EMAILS_CONSELHEIROS")
@SequenceGenerator(name = "sqSiacolEmailConselheiros", sequenceName = "SQ_SIACOL_EMAIL_CONSELHEIRO", initialValue = 1, allocationSize = 1)
public class EmailConselheiro {
	
	@Id
	@Column(name = "CODIGO")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sqSiacolEmailConselheiros")
	private Long codigo;
	
	@Column(name = "DESCRICAO")
	private String descricao;
	
	@OneToOne
	@JoinColumn(name = "FK_CODIGO_PESSOAS")
	private Pessoa pessoa;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_CRIACAO")
	private Date dataCriacao;
	
	@Column(name = "FK_CODIGO_FUNCIONARIO")
	private Long idFuncionario;

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

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Long getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(Long idFuncionario) {
		this.idFuncionario = idFuncionario;
	}
}
