package br.org.crea.commons.models.siacol;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name="PER_CONSELHEIRO_REGIONAL")
public class ConselheiroRegionalSiacol {
	
	@Id
	@Column(name="CODIGO") // relacionar com cad_pessoas
	private Long id;
	
	@Column(name="FK_CODIGO_EFETIVO")
	private Long idEfetivo;
	
	@Column(name="FK_CODIGO_ENTIDADE")
	private Long idEntidade;
	
	@Column(name="FK_CODIGO_INSTITUICAO")
	private Long idInstituicao;	
	
	@OneToOne
	@JoinColumn(name = "FK_CODIGO_PERSONALIDADE", nullable = false)
	private PersonalidadeSiacol personalidade;
	
	@Column(name="LICENCIADO")
	private Boolean licenciado;
	
	@Column(name="REMOVIDO")
	private Boolean removido;
	
	@Column(name="FINALIZADO")
	private Boolean finalizado;
	
	@Column(name="REPRESENTATIVIDADE")
	private String representatividade;
	
	@Column(name="MATRICULA_ALTERACAO")
	private Long matriculaAlteracao;
	
	@Column(name="DATACADASTRO")
	private Date dataCadastro;
	
	@Column(name="DATADESLIGAMENTO")
	private Date dataDesligamento;
	
	@Column(name="DATAPOSSE")
    private Date dataPosse;	
	
	@Column(name="DATAINICIOMANDATO")
	private Date dataInicioMandato;
	
	@Column(name="DATAFIMMANDATO")
	private Date dataFimMandato;
	
	@Column(name="ULTIMA_ATUALIZACAO")
	private Date ultimaAtualizacao;
	
	@Column(name="FK_CODIGO_SUPLENTE")
	private Long idSuplente;
	
	@Column(name="DATAPOSSE_SUPLENTE")
	private Date dataPosseSuplente;
	
	@Column(name="DATADESLIGAMENTO_SUPLENTE")
	private Date dataDesligamentoSuplente;
	
	@Column(name="FK_CODIGO_CONFEA_TIULO")
	private Long idTituloConfea;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdEfetivo() {
		return idEfetivo;
	}

	public void setIdEfetivo(Long idEfetivo) {
		this.idEfetivo = idEfetivo;
	}

	public Long getIdEntidade() {
		return idEntidade;
	}

	public void setIdEntidade(Long idEntidade) {
		this.idEntidade = idEntidade;
	}

	public Long getIdInstituicao() {
		return idInstituicao;
	}

	public void setIdInstituicao(Long idInstituicao) {
		this.idInstituicao = idInstituicao;
	}

	public PersonalidadeSiacol getPersonalidade() {
		return personalidade;
	}

	public void setPersonalidade(PersonalidadeSiacol personalidade) {
		this.personalidade = personalidade;
	}

	public Boolean getLicenciado() {
		return licenciado;
	}

	public void setLicenciado(Boolean licenciado) {
		this.licenciado = licenciado;
	}

	public Boolean getRemovido() {
		return removido;
	}

	public void setRemovido(Boolean removido) {
		this.removido = removido;
	}

	public Boolean getFinalizado() {
		return finalizado;
	}

	public void setFinalizado(Boolean finalizado) {
		this.finalizado = finalizado;
	}

	public String getRepresentatividade() {
		return representatividade;
	}

	public void setRepresentatividade(String representatividade) {
		this.representatividade = representatividade;
	}

	public Long getMatriculaAlteracao() {
		return matriculaAlteracao;
	}

	public void setMatriculaAlteracao(Long matriculaAlteracao) {
		this.matriculaAlteracao = matriculaAlteracao;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Date getDataDesligamento() {
		return dataDesligamento;
	}

	public void setDataDesligamento(Date dataDesligamento) {
		this.dataDesligamento = dataDesligamento;
	}

	public Date getDataPosse() {
		return dataPosse;
	}

	public void setDataPosse(Date dataPosse) {
		this.dataPosse = dataPosse;
	}

	public Date getDataInicioMandato() {
		return dataInicioMandato;
	}

	public void setDataInicioMandato(Date dataInicioMandato) {
		this.dataInicioMandato = dataInicioMandato;
	}

	public Date getDataFimMandato() {
		return dataFimMandato;
	}

	public void setDataFimMandato(Date dataFimMandato) {
		this.dataFimMandato = dataFimMandato;
	}

	public Date getUltimaAtualizacao() {
		return ultimaAtualizacao;
	}

	public void setUltimaAtualizacao(Date ultimaAtualizacao) {
		this.ultimaAtualizacao = ultimaAtualizacao;
	}

	public Long getIdSuplente() {
		return idSuplente;
	}

	public void setIdSuplente(Long idSuplente) {
		this.idSuplente = idSuplente;
	}

	public Date getDataPosseSuplente() {
		return dataPosseSuplente;
	}

	public void setDataPosseSuplente(Date dataPosseSuplente) {
		this.dataPosseSuplente = dataPosseSuplente;
	}

	public Date getDataDesligamentoSuplente() {
		return dataDesligamentoSuplente;
	}

	public void setDataDesligamentoSuplente(Date dataDesligamentoSuplente) {
		this.dataDesligamentoSuplente = dataDesligamentoSuplente;
	}

	public Long getIdTituloConfea() {
		return idTituloConfea;
	}

	public void setIdTituloConfea(Long idTituloConfea) {
		this.idTituloConfea = idTituloConfea;
	}
	
}


