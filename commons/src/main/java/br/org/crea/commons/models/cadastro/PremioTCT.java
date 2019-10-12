package br.org.crea.commons.models.cadastro;

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

import br.org.crea.commons.models.commons.Arquivo;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;

@Entity
@Table(name = "CAD_PREMIO_TCT")
@SequenceGenerator(name = "PREMIO_SEQUENCE", sequenceName = "SQ_CAD_PREMIO_TCT", initialValue = 1, allocationSize = 1)

public class PremioTCT {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PREMIO_SEQUENCE")
	@Column(name = "ID")
	private Long id;

	@Column(name = "ID_INST_COUCHDB")
	private String idInstituicaoEnsinoCouchDb;

	@Column(name = "NOME_INSTITUICAO_ENSINO")
	private String nomeInstituicaoEnsino;

	@Column(name = "ID_CURSO_COUCHDB")
	private String idCursoCouchDb;

	@Column(name = "NOME_CURSO")
	private String nomeCurso;

	@Column(name = "ID_CAMPUS_COUCHDB")
	private String idCampusCouchDb;

	@Column(name = "NOME_CAMPUS")
	private String nomeCampus;

	@OneToOne
	@JoinColumn(name = "FK_ARQUIVO")
	private Arquivo arquivo;

	@OneToOne
	@JoinColumn(name = "FK_ARQUIVO_RESUMO")
	private Arquivo arquivoResumo;

	@OneToOne
	@JoinColumn(name = "FK_ARQUIVO_TERMO")
	private Arquivo arquivoTermo;

	@OneToOne
	@JoinColumn(name = "FK_PESSOA")
	private Pessoa pessoa;

	@Column(name = "ANO")
	private Long ano;

	@Column(name = "TITULO_TRABALHO")
	private String titulo;

	@Column(name = "STATUS")
	private Long status;

	@Column(name = "ACEITE_TERMO")
	private Boolean aceite;

	@Column(name = "DT_ENVIO")
	@Temporal(TemporalType.DATE)
	private Date dataEnvio;

	@Column(name = "PROTOCOLO_CURSO")
	private String protocoloCurso;

	@Column(name = "DESCRICAO_CURSO")
	private String descricaoCurso;

	@Column(name = "NIVEL")
	private String nivel;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Arquivo getArquivo() {
		return arquivo;
	}

	public void setArquivo(Arquivo arquivo) {
		this.arquivo = arquivo;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Long getAno() {
		return ano;
	}

	public void setAno(Long ano) {
		this.ano = ano;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Boolean getAceite() {
		return aceite;
	}

	public void setAceite(Boolean aceite) {
		this.aceite = aceite;
	}

	public Date getDataEnvio() {
		return dataEnvio;
	}

	public void setDataEnvio(Date dataEnvio) {
		this.dataEnvio = dataEnvio;
	}

	public String getProtocoloCurso() {
		return protocoloCurso;
	}

	public void setProtocoloCurso(String protocoloCurso) {
		this.protocoloCurso = protocoloCurso;
	}

	public String getDescricaoCurso() {
		return descricaoCurso;
	}

	public void setDescricaoCurso(String descricaoCurso) {
		this.descricaoCurso = descricaoCurso;
	}

	public boolean temArquivo() {
		return this.arquivo != null;
	}

	public boolean temArquivoResumo() {
		return this.arquivoResumo != null;
	}

	public boolean temArquivoTermo() {
		return this.arquivoTermo != null;
	}

	public boolean temInstituicao() {
		return this.idInstituicaoEnsinoCouchDb != null;
	}

	public boolean temCurso() {
		return this.idCursoCouchDb != null;
	}

	public boolean temCampus() {
		return this.idCampusCouchDb != null;
	}

	public boolean temPessoa() {
		return this.pessoa != null;
	}

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

	public String getIdInstituicaoEnsinoCouchDb() {
		return idInstituicaoEnsinoCouchDb;
	}

	public void setIdInstituicaoEnsinoCouchDb(String idInstituicaoEnsinoCouchDb) {
		this.idInstituicaoEnsinoCouchDb = idInstituicaoEnsinoCouchDb;
	}

	public String getNomeInstituicaoEnsino() {
		return nomeInstituicaoEnsino;
	}

	public void setNomeInstituicaoEnsino(String nomeInstituicaoEnsino) {
		this.nomeInstituicaoEnsino = nomeInstituicaoEnsino;
	}

	public String getIdCursoCouchDb() {
		return idCursoCouchDb;
	}

	public void setIdCursoCouchDb(String idCursoCouchDb) {
		this.idCursoCouchDb = idCursoCouchDb;
	}

	public String getNomeCurso() {
		return nomeCurso;
	}

	public void setNomeCurso(String nomeCurso) {
		this.nomeCurso = nomeCurso;
	}

	public String getIdCampusCouchDb() {
		return idCampusCouchDb;
	}

	public void setIdCampusCouchDb(String idCampusCouchDb) {
		this.idCampusCouchDb = idCampusCouchDb;
	}

	public String getNomeCampus() {
		return nomeCampus;
	}

	public void setNomeCampus(String nomeCampus) {
		this.nomeCampus = nomeCampus;
	}

	public Arquivo getArquivoResumo() {
		return arquivoResumo;
	}

	public void setArquivoResumo(Arquivo arquivoResumo) {
		this.arquivoResumo = arquivoResumo;
	}

	public Arquivo getArquivoTermo() {
		return arquivoTermo;
	}

	public void setArquivoTermo(Arquivo arquivoTermo) {
		this.arquivoTermo = arquivoTermo;
	}

}
