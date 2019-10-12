package br.org.crea.commons.models.protocolo.dtos;

import java.util.Date;

import br.org.crea.commons.models.commons.dtos.ProtocoloDto;
import br.org.crea.commons.models.commons.dtos.SituacaoProtocoloDto;
import br.org.crea.commons.models.commons.enuns.ModuloSistema;

public class MovimentoProtocoloDto {
	
	private Long id;
	
	private Long idDepartamentoOrigem;
	
	private Long idDepartamentoDestino;
	
	private Long idDepartamentoPaiDestino;
	
	private SituacaoProtocoloDto situacao;
	
	private Date dataEnvio;
	
	private Date dataRecebimento;
	
	private String dataEnvioFormatada;
	
	private String dataRecebimentoFormatada;
	
	private Long idFuncionarioRemetente;
	
	private Long idFuncionarioReceptor;
	
	private boolean destinoEhAqruivo;
	
	private boolean destinoEhArquivoVirtual;
	
	private ModuloSistema moduloDepartamentoDestino;
	
	private ProtocoloDto protocolo;

	public Long getId() {
		return id;
	}

	public Long getIdDepartamentoOrigem() {
		return idDepartamentoOrigem;
	}

	public Long getIdDepartamentoDestino() {
		return idDepartamentoDestino;
	}

	public Long getIdDepartamentoPaiDestino() {
		return idDepartamentoPaiDestino;
	}

	public SituacaoProtocoloDto getSituacao() {
		return situacao;
	}

	public Date getDataEnvio() {
		return dataEnvio;
	}

	public Date getDataRecebimento() {
		return dataRecebimento;
	}

	public String getDataEnvioFormatada() {
		return dataEnvioFormatada;
	}

	public String getDataRecebimentoFormatada() {
		return dataRecebimentoFormatada;
	}

	public Long getIdFuncionarioRemetente() {
		return idFuncionarioRemetente;
	}

	public Long getIdFuncionarioReceptor() {
		return idFuncionarioReceptor;
	}

	public boolean isDestinoEhAqruivo() {
		return destinoEhAqruivo;
	}

	public boolean isDestinoEhArquivoVirtual() {
		return destinoEhArquivoVirtual;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setIdDepartamentoOrigem(Long idDepartamentoOrigem) {
		this.idDepartamentoOrigem = idDepartamentoOrigem;
	}

	public void setIdDepartamentoDestino(Long idDepartamentoDestino) {
		this.idDepartamentoDestino = idDepartamentoDestino;
	}

	public void setIdDepartamentoPaiDestino(Long idDepartamentoPaiDestino) {
		this.idDepartamentoPaiDestino = idDepartamentoPaiDestino;
	}

	public void setSituacao(SituacaoProtocoloDto situacao) {
		this.situacao = situacao;
	}

	public void setDataEnvio(Date dataEnvio) {
		this.dataEnvio = dataEnvio;
	}

	public void setDataRecebimento(Date dataRecebimento) {
		this.dataRecebimento = dataRecebimento;
	}

	public void setDataEnvioFormatada(String dataEnvioFormatada) {
		this.dataEnvioFormatada = dataEnvioFormatada;
	}

	public void setDataRecebimentoFormatada(String dataRecebimentoFormatada) {
		this.dataRecebimentoFormatada = dataRecebimentoFormatada;
	}

	public void setIdFuncionarioRemetente(Long idFuncionarioRemetente) {
		this.idFuncionarioRemetente = idFuncionarioRemetente;
	}

	public void setIdFuncionarioReceptor(Long idFuncionarioReceptor) {
		this.idFuncionarioReceptor = idFuncionarioReceptor;
	}

	public void setDestinoEhAqruivo(boolean destinoEhAqruivo) {
		this.destinoEhAqruivo = destinoEhAqruivo;
	}

	public void setDestinoEhArquivoVirtual(boolean destinoEhArquivoVirtual) {
		this.destinoEhArquivoVirtual = destinoEhArquivoVirtual;
	}

	public ModuloSistema getModuloDepartamentoDestino() {
		return moduloDepartamentoDestino;
	}

	public void setModuloDepartamentoDestino(ModuloSistema moduloDepartamentoDestino) {
		this.moduloDepartamentoDestino = moduloDepartamentoDestino;
	}

	public ProtocoloDto getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(ProtocoloDto protocolo) {
		this.protocolo = protocolo;
	}
	
	public boolean estaRecebido() {
		return this.dataRecebimento != null;
	}

}
