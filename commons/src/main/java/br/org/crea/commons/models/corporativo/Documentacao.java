package br.org.crea.commons.models.corporativo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "CAD_DOCUMENTACAO")
@SequenceGenerator(name = "sqDocumentacao", sequenceName = "CAD_DOCUMENTACAO_SEQ", initialValue = 1, allocationSize = 1)
public class Documentacao {

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sqDocumentacao")
	private Long id;

	@Column(name="NOME")
	private String nome;
	
	@Column(name="DESCRICAO")
	private String descricao;

	@Column(name="STATUS")
	private Boolean status;

	@Column(name="LINK")
	private String link;

//	@Column(name="DATA_CRIACAO", insertable = false, updatable = false)
//	@Temporal(TemporalType.TIMESTAMP)
//	private Date dataCriacao;
//	
//	@Column(name="DATA_ALTERACAO",  updatable = false)
//	@Temporal(TemporalType.TIMESTAMP)
//	private Date dataAlteracao;
//
//	@ManyToMany
//	@JoinColumn(name="FK_ID_FUNCIONARIO")
//	private Funcionario funcionario;

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	

	
	
	

}
