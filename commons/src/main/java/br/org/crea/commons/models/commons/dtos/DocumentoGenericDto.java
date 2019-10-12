package br.org.crea.commons.models.commons.dtos;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.org.crea.commons.models.cadastro.dtos.DepartamentoDto;
import br.org.crea.commons.models.commons.enuns.ModuloSistema;

public class DocumentoGenericDto {
	
	private Long id;
	
	private String codigoExterno;
	
	private String protocoloDocflow;
	
	private ModuloSistema modulo;
	
	private TipoDocumentoDto tipo;
	
	private DepartamentoDto departamento;
	
	private Long responsavel;
	
	private String nomeResponsavel;

	private Long protocolo;

	private Long processo;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCriacao;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataAtualizacao;

	private String dataCriacaoFormatada;
	
	private String dataAtualizacaoFormatada;

	private Object documento;

	private Object rascunho;
	
	private String observacao;
	
	private DomainGenericDto status;
	
	private boolean privado;
	
	private boolean assinado;
	
	private String numeroDocumento;
	
	private String hash;
	
	private ArquivoDto arquivo;
	
	private String chaveAssinaturaRedis;

	private boolean setarArquivoNull;
	
	public Long getId() {
		return id;
	}

	public String getCodigoExterno() {
		return codigoExterno;
	}

	public void setCodigoExterno(String codigoExterno) {
		this.codigoExterno = codigoExterno;
	}

	public String getProtocoloDocflow() {
		return protocoloDocflow;
	}

	public void setProtocoloDocflow(String protocoloDocflow) {
		this.protocoloDocflow = protocoloDocflow;
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

	public TipoDocumentoDto getTipo() {
		return tipo;
	}

	public void setTipo(TipoDocumentoDto tipo) {
		this.tipo = tipo;
	}

	public DepartamentoDto getDepartamento() {
		return departamento;
	}

	public void setDepartamento(DepartamentoDto departamento) {
		this.departamento = departamento;
	}

	public Long getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Long responsavel) {
		this.responsavel = responsavel;
	}

	public String getNomeResponsavel() {
		return nomeResponsavel;
	}

	public void setNomeResponsavel(String nomeResponsavel) {
		this.nomeResponsavel = nomeResponsavel;
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

	public String getDataCriacaoFormatada() {
		return dataCriacaoFormatada;
	}

	public void setDataCriacaoFormatada(String dataCriacaoFormatada) {
		this.dataCriacaoFormatada = dataCriacaoFormatada;
	}

	public String getDataAtualizacaoFormatada() {
		return dataAtualizacaoFormatada;
	}

	public void setDataAtualizacaoFormatada(String dataAtualizacaoFormatada) {
		this.dataAtualizacaoFormatada = dataAtualizacaoFormatada;
	}

	public Object getDocumento() {
		return documento;
	}

	public void setDocumento(Object documento) {
		this.documento = documento;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public DomainGenericDto getStatus() {
		return status;
	}

	public void setStatus(DomainGenericDto status) {
		this.status = status;
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
	
	public boolean temId() {
		return this.id != null ? true : false;
	}

	public boolean temStatus() {
		return this.status != null ? true : false;
	}

	public Object getRascunho() {
		return rascunho;
	}

	public void setRascunho(Object rascunho) {
		this.rascunho = rascunho;
	}

	public ArquivoDto getArquivo() {
		return arquivo;
	}

	public void setArquivo(ArquivoDto arquivo) {
		this.arquivo = arquivo;
	}

	public boolean isPrivado() {
		return privado;
	}

	public void setPrivado(boolean privado) {
		this.privado = privado;
	}

	public String getChaveAssinaturaRedis() {
		return chaveAssinaturaRedis;
	}
	
	public void setChaveAssinaturaRedis(String chaveAssinaturaRedis) {
		this.chaveAssinaturaRedis = chaveAssinaturaRedis;
	}
	
	public boolean temArquivo() {
		return this.arquivo != null;
	}

	public boolean temDepartamento() {
		return this.departamento != null;
	}

	public boolean temNumeroDocumento() {
		return this.numeroDocumento != null || this.numeroDocumento != "";
	}
	
	public boolean temDocumento() {
		return this.documento != null;
	}
	
	public boolean temRascunho() {
		return this.rascunho != null;
	}
	
	public boolean temTipo() {
		return this.tipo != null;
	}

	public boolean temResponsavel() {
		return this.responsavel != null;
	}

	public boolean isSetarArquivoNull() {
		return setarArquivoNull;
	}

	public void setSetarArquivoNull(boolean setarArquivoNull) {
		this.setarArquivoNull = setarArquivoNull;
	}


}
