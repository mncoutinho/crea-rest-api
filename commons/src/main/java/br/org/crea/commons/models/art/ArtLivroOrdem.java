package br.org.crea.commons.models.art;

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

import br.org.crea.commons.models.commons.Arquivo;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;

@Entity
@SequenceGenerator(name = "ART_LIVRO_ORDEM_SEQ", sequenceName = "ART_LIVRO_ORDEM_SEQ", initialValue = 1, allocationSize = 1)
@Table(name="ART_LIVRO_ORDEM")
public class ArtLivroOrdem implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2885807230026708749L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ART_LIVRO_ORDEM_SEQ")
	@Column(name="CODIGO")
	private Long id;
	
	@OneToOne
	@JoinColumn(name="FK_ART")
	private Art art;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_INICIO_ETAPA")
	private Date dataInicioDaEtapa;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_CONCLUSAO_ETAPA")
	private Date dataConclusao;
	
	@Column(name="RELATO_VISITA_TECNICA")
	private String relatoVisitaResponsavelTecnico;
	
	@Column(name="ORIENTACAO")
	private String orientacao;
	
	@Column(name="ACIDENTE_DANO_MATERIAL")
	private String acidentesDanosMateriais;
	
	@Column(name="EMPRESA_PRESTADOR_CONTRATADO")
	private String empresasePrestadoresContratadosSubContratados;
	
	@Column(name="PERIODO_INTERRUPCAO_MOTIVO")
	private String periodosInterrupcaoEMotivos;
	
	@Column(name="OUTRO_FATO_OBSERVACAO")
	private String outrosFatosEObservacoes;
	
	@OneToOne
	@JoinColumn(name="FK_PESSOA")
	private Pessoa pessoa;
	
	@OneToOne
	@JoinColumn(name="FK_ARQUIVO")
	private Arquivo arquivo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Art getArt() {
		return art;
	}

	public void setArt(Art art) {
		this.art = art;
	}

	public Date getDataInicioDaEtapa() {
		return dataInicioDaEtapa;
	}

	public void setDataInicioDaEtapa(Date dataInicioDaEtapa) {
		this.dataInicioDaEtapa = dataInicioDaEtapa;
	}

	public Date getDataConclusao() {
		return dataConclusao;
	}

	public void setDataConclusao(Date dataConclusao) {
		this.dataConclusao = dataConclusao;
	}

	public String getRelatoVisitaResponsavelTecnico() {
		return relatoVisitaResponsavelTecnico;
	}

	public void setRelatoVisitaResponsavelTecnico(String relatoVisitaResponsavelTecnico) {
		this.relatoVisitaResponsavelTecnico = relatoVisitaResponsavelTecnico;
	}

	public String getOrientacao() {
		return orientacao;
	}

	public void setOrientacao(String orientacao) {
		this.orientacao = orientacao;
	}

	public String getAcidentesDanosMateriais() {
		return acidentesDanosMateriais;
	}

	public void setAcidentesDanosMateriais(String acidentesDanosMateriais) {
		this.acidentesDanosMateriais = acidentesDanosMateriais;
	}

	public String getEmpresasePrestadoresContratadosSubContratados() {
		return empresasePrestadoresContratadosSubContratados;
	}

	public void setEmpresasePrestadoresContratadosSubContratados(String empresasePrestadoresContratadosSubContratados) {
		this.empresasePrestadoresContratadosSubContratados = empresasePrestadoresContratadosSubContratados;
	}

	public String getPeriodosInterrupcaoEMotivos() {
		return periodosInterrupcaoEMotivos;
	}

	public void setPeriodosInterrupcaoEMotivos(String periodosInterrupcaoEMotivos) {
		this.periodosInterrupcaoEMotivos = periodosInterrupcaoEMotivos;
	}

	public String getOutrosFatosEObservacoes() {
		return outrosFatosEObservacoes;
	}

	public void setOutrosFatosEObservacoes(String outrosFatosEObservacoes) {
		this.outrosFatosEObservacoes = outrosFatosEObservacoes;
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

	public boolean temArquivo() {
		return this.arquivo != null;
	}

	public boolean temPessoa() {
		return this.pessoa != null;
	}

	public boolean temDataConclusao() {
		return this.dataConclusao != null;
	}
	
}
