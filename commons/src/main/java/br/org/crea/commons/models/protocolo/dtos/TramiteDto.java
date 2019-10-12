package br.org.crea.commons.models.protocolo.dtos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonPropertyOrder;

import br.org.crea.commons.models.commons.dtos.ProtocoloDto;
import br.org.crea.commons.models.corporativo.dtos.AssuntoDto;
import br.org.crea.commons.models.corporativo.dtos.FuncionarioDto;
import br.org.crea.commons.models.corporativo.dtos.PessoaDto;
import br.org.crea.commons.models.protocolo.enuns.TipoProtocoloEnum;

@JsonPropertyOrder({"numeroProtocolo", "numeroProcesso", "assunto", "idPessoa", "idDepartamentoDestino", "funcionarioTramite", "ultimoMovimento", "possuiErros","estaComStatusDivergente", "protocoloEhDigital", "mensagensDoTramite", "listApensos", "listAnexos" })
public class TramiteDto {

	private Long numeroProtocolo;
	
	private Long numeroProcesso;
	
	private Long idPessoa;
	
	private boolean estaSubstituido;
	
	private boolean estaExcluido;
	
	private boolean estaAnexado;
	
	private boolean estaApensado;
	
	private boolean estaComStatusDivergente;
	
	private boolean possuiErros;
	
	private boolean destinoEhSiacol;
	
	private Long idDepartamentoDestino;
	
	private FuncionarioDto funcionarioTramite;
	
	private MovimentoProtocoloDto ultimoMovimento;
	
	private List<String> mensagensDoTramite;
	
	private TipoProtocoloEnum tipoProtocolo;
	
	private Long idSituacaoTramite;
	
	private boolean protocoloEhDigital;
	
	private Long idObservacaoTramite;
	
	private boolean protocoloEstaArquivoVirtual;
	
	private boolean assuntoAptoASerDigitalizado;
	
	private List<ProtocoloDto> listApensos;
	
	private List<ProtocoloDto> listAnexos;
	
	private AssuntoDto assunto;
	
	private PessoaDto interessado;
	
	private PessoaDto pessoa;
	
	private String dataProtocolo;

	public Long getNumeroProtocolo() {
		return numeroProtocolo;
	}

	public Long getNumeroProcesso() {
		return numeroProcesso;
	}

	public Long getIdPessoa() {
		return idPessoa;
	}

	public boolean isEstaSubstituido() {
		return estaSubstituido;
	}

	public boolean isEstaExcluido() {
		return estaExcluido;
	}

	public boolean isEstaAnexado() {
		return estaAnexado;
	}

	public boolean isEstaApensado() {
		return estaApensado;
	}

	public boolean isEstaComStatusDivergente() {
		return estaComStatusDivergente;
	}

	public boolean isPossuiErros() {
		return possuiErros;
	}

	public Long getIdDepartamentoDestino() {
		return idDepartamentoDestino;
	}

	public MovimentoProtocoloDto getUltimoMovimento() {
		return ultimoMovimento;
	}

	public List<String> getMensagensDoTramite() {
		return mensagensDoTramite;
	}

	public TipoProtocoloEnum getTipoProtocolo() {
		return tipoProtocolo;
	}

	public void setNumeroProtocolo(Long numeroProtocolo) {
		this.numeroProtocolo = numeroProtocolo;
	}

	public void setNumeroProcesso(Long numeroProcesso) {
		this.numeroProcesso = numeroProcesso;
	}

	public void setIdPessoa(Long idPessoa) {
		this.idPessoa = idPessoa;
	}

	public void setEstaSubstituido(boolean estaSubstituido) {
		this.estaSubstituido = estaSubstituido;
	}

	public void setEstaExcluido(boolean estaExcluido) {
		this.estaExcluido = estaExcluido;
	}

	public void setEstaAnexado(boolean estaAnexado) {
		this.estaAnexado = estaAnexado;
	}

	public void setEstaApensado(boolean estaApensado) {
		this.estaApensado = estaApensado;
	}

	public void setEstaComStatusDivergente(boolean estaComStatusDivergente) {
		this.estaComStatusDivergente = estaComStatusDivergente;
	}

	public void setPossuiErros(boolean possuiErros) {
		this.possuiErros = possuiErros;
	}

	public void setIdDepartamentoDestino(Long idDepartamentoDestino) {
		this.idDepartamentoDestino = idDepartamentoDestino;
	}

	public void setUltimoMovimento(MovimentoProtocoloDto ultimoMovimento) {
		this.ultimoMovimento = ultimoMovimento;
	}

	public void setMensagensDoTramite(List<String> mensagensDoTramite) {
		this.mensagensDoTramite = mensagensDoTramite;
	}

	public void setTipoProtocolo(TipoProtocoloEnum tipoProtocolo) {
		this.tipoProtocolo = tipoProtocolo;
	}

	public boolean ehDigital() {
		return protocoloEhDigital;
	}

	public void setProtocoloEhDigital(boolean protocoloEhDigital) {
		this.protocoloEhDigital = protocoloEhDigital;
	}

	public FuncionarioDto getFuncionarioTramite() {
		return funcionarioTramite;
	}

	public boolean isProtocoloEhDigital() {
		return protocoloEhDigital;
	}

	public void setFuncionarioTramite(FuncionarioDto funcionarioTramite) {
		this.funcionarioTramite = funcionarioTramite;
	}

	public Long getIdSituacaoTramite() {
		return idSituacaoTramite;
	}

	public void setIdSituacaoTramite(Long idSituacaoProtocolo) {
		this.idSituacaoTramite = idSituacaoProtocolo;
	}

	public Long getIdObservacaoTramite() {
		return idObservacaoTramite;
	}

	public void setIdObservacaoTramite(Long idObservacaoTramite) {
		this.idObservacaoTramite = idObservacaoTramite;
	}

	public List<ProtocoloDto> getListApensos() {
		return listApensos;
	}

	public void setListApensos(List<ProtocoloDto> listApensos) {
		this.listApensos = listApensos;
	}

	public List<ProtocoloDto> getListAnexos() {
		return listAnexos;
	}

	public void setListAnexos(List<ProtocoloDto> listAnexos) {
		this.listAnexos = listAnexos;
	}

	public boolean protocoloEstaArquivoVirtual() {
		return protocoloEstaArquivoVirtual;
	}

	public void setProtocoloEstaArquivoVirtual(boolean protocoloEstaArquivoVirtual) {
		this.protocoloEstaArquivoVirtual = protocoloEstaArquivoVirtual;
	}

	public boolean assuntoAptoASerDigitalizado() {
		return assuntoAptoASerDigitalizado;
	}

	public void setAssuntoAptoASerDigitalizado(boolean assuntoAptoASerDigitalizado) {
		this.assuntoAptoASerDigitalizado = assuntoAptoASerDigitalizado;
	}

	public AssuntoDto getAssunto() {
		return assunto;
	}

	public void setAssunto(AssuntoDto assunto) {
		this.assunto = assunto;
	}

	public PessoaDto getInteressado() {
		return interessado;
	}

	public void setInteressado(PessoaDto interessado) {
		this.interessado = interessado;
	}

	public String getDataProtocolo() {
		return dataProtocolo;
	}

	public void setDataProtocolo(String dataProtocolo) {
		this.dataProtocolo = dataProtocolo;
	}

	public PessoaDto getPessoa() {
		return pessoa;
	}

	public void setPessoa(PessoaDto pessoa) {
		this.pessoa = pessoa;
	}

	public boolean destinoEhSiacol() {
		return destinoEhSiacol;
	}

	public void setDestinoEhSiacol(boolean destinoEhSiacol) {
		this.destinoEhSiacol = destinoEhSiacol;
	}
	
}
