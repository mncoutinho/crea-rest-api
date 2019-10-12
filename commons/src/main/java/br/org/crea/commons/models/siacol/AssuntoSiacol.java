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

@Entity
@Table(name="SIACOL_ASSUNTO")
@SequenceGenerator(name="sqSiacolAssunto",sequenceName="SQ_SIACOL_ASSUNTO",allocationSize = 1)
public class AssuntoSiacol {
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sqSiacolAssunto")
	private Long id;
	
	@Column(name="CODIGO")
	private Long codigo;
	
	@Column(name="NOME")
	private String nome;
	
	@Column(name="ATIVO")
	private Boolean ativo;
	
	@OneToOne
	@JoinColumn(name="FK_ASSUNTO_CONFEA")
	private AssuntoConfea assuntoConfea;
	
	@Column(name="LEGISLACAO")
	private String legislacao;

	@Column(name="TEMPO_SERVICO")
	private Long tempoServico;
	
	@Column(name="INSTRUCAO")
	private Double pesoInstrucao;
	
	@Column(name="COMISSAO")
	private Double pesoComissao;
	
	@Column(name="CAMARA")
	private Double pesoCamara;
	
	@Column(name="PLENARIA")
	private Double pesoPlenaria;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	
	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public AssuntoConfea getAssuntoConfea() {
		return assuntoConfea;
	}

	public void setAssuntoConfea(AssuntoConfea assuntoConfea) {
		this.assuntoConfea = assuntoConfea;
	}

	public String getLegislacao() {
		return legislacao;
	}

	public void setLegislacao(String legislacao) {
		this.legislacao = legislacao;
	}

	public Long getTempoServico() {
		return tempoServico;
	}

	public void setTempoServico(Long tempoServico) {
		this.tempoServico = tempoServico;
	}

	public Double getPesoInstrucao() {
		return pesoInstrucao;
	}

	public void setPesoInstrucao(Double pesoInstrucao) {
		this.pesoInstrucao = pesoInstrucao;
	}

	public Double getPesoComissao() {
		return pesoComissao;
	}

	public void setPesoComissao(Double pesoComissao) {
		this.pesoComissao = pesoComissao;
	}

	public Double getPesoCamara() {
		return pesoCamara;
	}

	public void setPesoCamara(Double pesoCamara) {
		this.pesoCamara = pesoCamara;
	}

	public Double getPesoPlenaria() {
		return pesoPlenaria;
	}

	public void setPesoPlenaria(Double pesoPlenaria) {
		this.pesoPlenaria = pesoPlenaria;
	}

}
