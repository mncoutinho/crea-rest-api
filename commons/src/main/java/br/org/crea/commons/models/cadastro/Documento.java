package br.org.crea.commons.models.cadastro;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.org.crea.commons.models.commons.Arquivo;
import br.org.crea.commons.models.commons.enuns.ModuloSistema;


@Entity
@Table(name = "CAD_DOCUMENTO")
@SequenceGenerator(name = "sqCadDocumento", sequenceName = "SQ_CAD_DOCUMENTO", initialValue = 1, allocationSize = 1)
public class Documento {
	
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sqCadDocumento")
	private Long id;
	
	@Column(name="CODIGO_EXTERNO")
	private String codigoExterno;
	
	@Enumerated(EnumType.STRING)
	@Column(name="MODULO")
	private ModuloSistema modulo;
	
	@OneToOne
	@JoinColumn(name="FK_TIPO_DOCUMENTO")
	private TipoDocumento tipo;

	@Column(name="FK_PESSOA_RESPONSAVEL")
	private Long responsavel;
	
	@Column(name="NUMERO_PROTOCOLO")
	private Long protocolo;

	@Column(name="NUMERO_PROCESSO")
	private Long processo;
	
	@OneToOne
	@JoinColumn(name="FK_DEPARTAMENTO")
	private Departamento departamento;
	
	@Column(name="DT_CREATE", updatable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCriacao;
	
	@Column(name="DT_UPDATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataAtualizacao;
	
	@Lob
	@Column(name="RASCUNHO")
	private String rascunho;
	
	@Lob
	@Column(name="DOCUMENTO")
	private String documento;
	
	@Column(name="OBSERVACAO")
	private String observacao;
	
	@OneToOne
	@JoinColumn(name="FK_STATUS_DOCUMENTO")
	private StatusDocumento statusDocumento;
	
	@Column(name="ASSINADO")
	private boolean assinado;
	
	@Column(name="NUMERO_DOCUMENTO")
	private String numeroDocumento;
	
	@Lob
	@Column(name="HASH")
	private String hash;
	
	@OneToOne
	@JoinColumn(name="FK_ARQUIVO")
	private Arquivo arquivo;
	
	/*Chave para recuperar selo de assinatura digital do documento*/
	@Column(name="KEY_ASS_REDIS")
	private String chaveAssinaturaRedis;
	
	/*Numero do protocolo do Documento no Docflow. Ex. ATEC/0001/2018*/
	@Column(name="PROTOCOLO_DOCFLOW")
	private String protocoloDocflow;
	
	public boolean temDataAtualizacao() {
		return this.dataAtualizacao != null;
	}

	public boolean temDataCriacao() {
		return this.dataCriacao != null;
	}
	
	public boolean temTipo() {
		return this.tipo != null;
	}
	
	public boolean temDocumento() {
		return this.documento != null;
	}
	
	public boolean temRascunho() {
		return this.rascunho != null;
	}

	public Long getId() {
		return id;
	}
	

	public String getCodigoExterno() {
		return codigoExterno;
	}


	public void setCodigoExterno(String codigoExterno) {
		this.codigoExterno = codigoExterno;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public ModuloSistema getModulo() {
		return modulo;
	}


	public void setModulo(ModuloSistema modulo) {
		this.modulo = modulo;
	}


	public TipoDocumento getTipo() {
		return tipo;
	}


	public void setTipo(TipoDocumento tipo) {
		this.tipo = tipo;
	}


	public Long getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Long responsavel) {
		this.responsavel = responsavel;
	}

	public Long getProtocolo() {
		return protocolo;
	}


	public void setProtocolo(Long protocolo) {
		this.protocolo = protocolo;
	}


	public Long getProcesso() {
		return processo;
	}


	public void setProcesso(Long processo) {
		this.processo = processo;
	}


	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}


	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}


	public Date getDataAtualizacao() {
		return dataAtualizacao;
	}


	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}


	public String getDocumento() {
		return documento;
	}


	public void setDocumento(String documento) {
		this.documento = documento;
	}


	public String getObservacao() {
		return observacao;
	}


	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}


	public boolean isAssinado() {
		return assinado;
	}


	public void setAssinado(boolean assinado) {
		this.assinado = assinado;
	}


	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getHash() {
		return hash;
	}


	public void setHash(String hash) {
		this.hash = hash;
	}

	public boolean temStatus() {
		return this.statusDocumento != null;
	}

	public StatusDocumento getStatusDocumento() {
		return statusDocumento;
	}

	public void setStatusDocumento(StatusDocumento statusDocumento) {
		this.statusDocumento = statusDocumento;
	}

	public String getRascunho() {
		return rascunho;
	}

	public void setRascunho(String rascunho) {
		this.rascunho = rascunho;
	}

	public Arquivo getArquivo() {
		return arquivo;
	}

	public void setArquivo(Arquivo arquivo) {
		this.arquivo = arquivo;
	}
	
	public String getChaveAssinaturaRedis() {
		return chaveAssinaturaRedis;
	}

	public void setChaveAssinaturaRedis(String chaveAssinaturaRedis) {
		this.chaveAssinaturaRedis = chaveAssinaturaRedis;
	}

	public String getProtocoloDocflow() {
		return protocoloDocflow;
	}

	public void setProtocoloDocflow(String protocoloDocflow) {
		this.protocoloDocflow = protocoloDocflow;
	}

	public boolean estaEmEdicao() {
		return this.statusDocumento.getId() == new Long(9);
	}
	
	public boolean temArquivo() {
		return this.arquivo != null;
	}

	public boolean temDepartamento() {
		return this.departamento != null;
	}
	
	public boolean temChaveAssinaturaRedis() {
		return this.chaveAssinaturaRedis != null;
	}
	
}
