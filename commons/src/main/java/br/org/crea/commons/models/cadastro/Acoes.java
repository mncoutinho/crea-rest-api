package br.org.crea.commons.models.cadastro;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "CAD_ACOES")
@SequenceGenerator(name = "ACOES_SEQUENCE", sequenceName = "CAD_ACOES_SEQ", initialValue = 1, allocationSize = 1)
public class Acoes {
	
	@Id
	@Column(name="CODIGO")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACOES_SEQUENCE")
	private Long id;
	
	@Column(name="DATAACAO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataAcao;
	
	@Column(name="DESCRICAO")
	private String descricao;

	@Column(name="TIPOPESSOA")
	private String tipoPessoa;
	
	@Column(name="FK_CODIGO_FUNCIONARIOS")
	private Long funcionario;
	
	@Column(name="FK_CODIGO_PESSOAS")
	private Long idPessoa;

	@Column(name="FK_CODIGO_TIPOS_ACOES")
	private Long tipoAcao;
	
	@Column(name="FK_CODIGO_ENTIDADES_CLASSE")
	private Long idEntidadeClasse;
	
	@Column(name="FK_CODIGO_PESSOAS_OUTRA")
	private Long idOutraPessoa;
	
	@Column(name="TIPOPESSOA_OUTRA")
	private String tipoOutraPessoa;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataAcao() {
		return dataAcao;
	}

	public void setDataAcao(Date dataAcao) {
		this.dataAcao = dataAcao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(String tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public Long getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Long funcionario) {
		this.funcionario = funcionario;
	}

	public Long getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(Long idPessoa) {
		this.idPessoa = idPessoa;
	}

	public Long getTipoAcao() {
		return tipoAcao;
	}

	public void setTipoAcao(Long tipoAcao) {
		this.tipoAcao = tipoAcao;
	}

	public Long getIdEntidadeClasse() {
		return idEntidadeClasse;
	}

	public void setIdEntidadeClasse(Long idEntidadeClasse) {
		this.idEntidadeClasse = idEntidadeClasse;
	}

	public Long getIdOutraPessoa() {
		return idOutraPessoa;
	}

	public void setIdOutraPessoa(Long idOutraPessoa) {
		this.idOutraPessoa = idOutraPessoa;
	}

	public String getTipoOutraPessoa() {
		return tipoOutraPessoa;
	}

	public void setTipoOutraPessoa(String tipoOutraPessoa) {
		this.tipoOutraPessoa = tipoOutraPessoa;
	}


}