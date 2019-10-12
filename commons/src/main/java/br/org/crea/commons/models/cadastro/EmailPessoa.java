package br.org.crea.commons.models.cadastro;

import java.io.Serializable;
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

import br.org.crea.commons.models.corporativo.pessoa.Pessoa;


@Entity
@Table(name = "CAD_EMAILS")
@SequenceGenerator(name = "EMAIL_SEQUENCE", sequenceName = "CAD_EMAILS_SEQ",allocationSize = 1)
public class EmailPessoa  implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EMAIL_SEQUENCE")
	@Column(name="CODIGO")
	private Long id;

	@Column(name="DESCRICAO")
	private String descricao;
	
	@OneToOne
	@JoinColumn(name="FK_CODIGO_PESSOAS")
	private Pessoa pessoa;
	
	@Column(name="FK_CODIGO_FUNCIONARIO")
	private Long idFuncionario;
	
	@Column(name="DATA_ATUALIZACAO")
	private Date dataAtualizacao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(Long idFuncionario) {
		this.idFuncionario = idFuncionario;
	}

	public Date getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}


}
