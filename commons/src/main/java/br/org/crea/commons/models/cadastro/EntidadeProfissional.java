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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="CAD_ENTIDADES_PROFISSIONAIS")
@SequenceGenerator(name = "CAD_ENTIDADES_PROF_SEQUENCE", sequenceName = "CAD_ENTIDADES_PROF_SEQ")
public class EntidadeProfissional implements Serializable{

	private static final long serialVersionUID = 4266543941548766834L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CAD_ENTIDADES_PROF_SEQUENCE")
	@Column(name="CODIGO")
	private Long id;
	
	@Column(name="DATAFILIACAO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataFiliacao;
	
	@Column(name="DATAOPCAO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataOpcao;
	
	@OneToOne
	@JoinColumn(name="FK_COD_ENT_CLASSE")
	private EntidadeClasse entidade;
	
	@Column(name="OPCAOVOTO")
	private boolean opcaoVoto;
	
	@Column(name="FK_COD_PROF")
	private Long idProfissional;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataFiliacao() {
		return dataFiliacao;
	}

	public void setDataFiliacao(Date dataFiliacao) {
		this.dataFiliacao = dataFiliacao;
	}

	public Date getDataOpcao() {
		return dataOpcao;
	}

	public void setDataOpcao(Date dataOpcao) {
		this.dataOpcao = dataOpcao;
	}

	public EntidadeClasse getEntidade() {
		return entidade;
	}

	public void setEntidade(EntidadeClasse entidade) {
		this.entidade = entidade;
	}

	public boolean isOpcaoVoto() {
		return opcaoVoto;
	}

	public void setOpcaoVoto(boolean opcaoVoto) {
		this.opcaoVoto = opcaoVoto;
	}

	public Long getIdProfissional() {
		return idProfissional;
	}

	public void setIdProfissional(Long idProfissional) {
		this.idProfissional = idProfissional;
	}

}
