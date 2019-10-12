package br.org.crea.commons.models.commons.dtos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.org.crea.commons.models.corporativo.dtos.AssuntoDto;
import br.org.crea.commons.models.corporativo.dtos.PessoaDto;
import br.org.crea.commons.models.protocolo.dtos.MovimentoProtocoloDto;
import br.org.crea.commons.models.protocolo.enuns.TipoProtocoloEnum;

public class ProtocoloDto {
	
	private Long id;

	private Long numeroProtocolo;

	private Long numeroProcesso;

	private PessoaDto interessado;

	private AssuntoDto assunto;

	private Date dataEmissao;

	private String observacao;

	private String emissao;

	private String ultimaMovimentacao;
	
	private String unidadeAtendimento;
	
	private PessoaDto coInteressado;

	private String numeroArt;

	private TipoProtocoloEnum tipoProtocolo;

	private Long idDepartamentoDestino;

	private Long idDepartamentoOrigem;

	private List<GenericDto> listRegistroRtsComArt;
	
	private boolean digital;
	
	private List<ProtocoloDto> listApensos;
	
	private List<ProtocoloDto> listAnexos;
	
	private List<ProtocoloDto> listProtocolosVinculadosProcesso;
	
	private MovimentoProtocoloDto ultimoMovimento; 
	
	private MovimentoProtocoloDto primeiroMovimento;
	
	private boolean estaAnexado;
	
	private boolean estaApensado;
	
	private boolean estaSubstituido;
	
	private Long numeroProtocoloPaiAnexo;
	
	private Long numeroProtocoloPaiApenso;
	
	private boolean possuiErros;
	
	private List<String> mensagens = new ArrayList<String>();
	
	private PessoaDto pessoa;

	private Date dataDigitalizacao;
	
	private boolean statusTransacaoEstaDivergente;
	
	private String statusTransacao;
	
	private boolean excluido;
	
	private boolean finalizado;
	
	private Long idFuncionario;
	
	private String tipoPessoa;
	
	private String dataEmissaoFormatada;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNumeroProtocolo() {
		return numeroProtocolo;
	}

	public void setNumeroProtocolo(Long numeroProtocolo) {
		this.numeroProtocolo = numeroProtocolo;
	}

	public Long getNumeroProcesso() {
		return numeroProcesso;
	}

	public void setNumeroProcesso(Long numeroProcesso) {
		this.numeroProcesso = numeroProcesso;
	}

	public PessoaDto getInteressado() {
		return interessado;
	}

	public void setInteressado(PessoaDto interessado) {
		this.interessado = interessado;
	}

	public AssuntoDto getAssunto() {
		return assunto;
	}

	public void setAssunto(AssuntoDto assunto) {
		this.assunto = assunto;
	}

	public Date getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(Date dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getEmissao() {
		return emissao;
	}

	public void setEmissao(String emissao) {
		this.emissao = emissao;
	}

	public String getUltimaMovimentacao() {
		return ultimaMovimentacao;
	}

	public void setUltimaMovimentacao(String ultimaMovimentacao) {
		this.ultimaMovimentacao = ultimaMovimentacao;
	}

	public PessoaDto getCoInteressado() {
		return coInteressado;
	}

	public void setCoInteressado(PessoaDto coInteressado) {
		this.coInteressado = coInteressado;
	}

	public String getNumeroArt() {
		return numeroArt;
	}

	public void setNumeroArt(String numeroArt) {
		this.numeroArt = numeroArt;
	}

	public TipoProtocoloEnum getTipoProtocolo() {
		return tipoProtocolo;
	}

	public void setTipoProtocolo(TipoProtocoloEnum tipoProtocolo) {
		this.tipoProtocolo = tipoProtocolo;
	}

	public Long getIdDepartamentoDestino() {
		return idDepartamentoDestino;
	}

	public void setIdDepartamentoDestino(Long idDepartamentoDestino) {
		this.idDepartamentoDestino = idDepartamentoDestino;
	}

	public Long getIdDepartamentoOrigem() {
		return idDepartamentoOrigem;
	}

	public void setIdDepartamentoOrigem(Long idDepartamentoOrigem) {
		this.idDepartamentoOrigem = idDepartamentoOrigem;
	}

	public List<GenericDto> getListRegistroRtsComArt() {
		return listRegistroRtsComArt;
	}

	public void setListRegistroRtsComArt(List<GenericDto> listRegistroRtsComArt) {
		this.listRegistroRtsComArt = listRegistroRtsComArt;
	}

	public boolean isDigital() {
		return digital;
	}

	public void setDigital(boolean digital) {
		this.digital = digital;
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

	public List<ProtocoloDto> getListProtocolosVinculadosProcesso() {
		return listProtocolosVinculadosProcesso;
	}

	public void setListProtocolosVinculadosProcesso(List<ProtocoloDto> listProtocolosVinculadosProcesso) {
		this.listProtocolosVinculadosProcesso = listProtocolosVinculadosProcesso;
	}

	public MovimentoProtocoloDto getUltimoMovimento() {
		return ultimoMovimento;
	}

	public void setUltimoMovimento(MovimentoProtocoloDto ultimoMovimento) {
		this.ultimoMovimento = ultimoMovimento;
	}

	public MovimentoProtocoloDto getPrimeiroMovimento() {
		return primeiroMovimento;
	}

	public void setPrimeiroMovimento(MovimentoProtocoloDto primeiroMovimento) {
		this.primeiroMovimento = primeiroMovimento;
	}

	public boolean estaAnexado() {
		return estaAnexado;
	}

	public void setEstaAnexado(boolean estaAnexado) {
		this.estaAnexado = estaAnexado;
	}

	public boolean estaApensado() {
		return estaApensado;
	}

	public void setEstaApensado(boolean estaApensado) {
		this.estaApensado = estaApensado;
	}
	
	public boolean estaSubstituido() {
		return estaSubstituido;
	}

	public void setEstaSubstituido(boolean estaSubstituido) {
		this.estaSubstituido = estaSubstituido;
	}

	public Long getNumeroProtocoloPaiAnexo() {
		return numeroProtocoloPaiAnexo;
	}

	public void setNumeroProtocoloPaiAnexo(Long numeroProtocoloPaiAnexo) {
		this.numeroProtocoloPaiAnexo = numeroProtocoloPaiAnexo;
	}

	public Long getNumeroProtocoloPaiApenso() {
		return numeroProtocoloPaiApenso;
	}

	public void setNumeroProtocoloPaiApenso(Long numeroProtocoloPaiApenso) {
		this.numeroProtocoloPaiApenso = numeroProtocoloPaiApenso;
	}

	public boolean possuiErros() {
		return possuiErros;
	}

	public void setPossuiErros(boolean possuiErros) {
		this.possuiErros = possuiErros;
	}

	public List<String> getMensagens() {
		return mensagens;
	}

	public void setMensagens(List<String> mensagens) {
		this.mensagens = mensagens;
	}

	public PessoaDto getPessoa() {
		return pessoa;
	}

	public void setPessoa(PessoaDto pessoa) {
		this.pessoa = pessoa;
	}	

	public Date getDataDigitalizacao() {
		return dataDigitalizacao;
	}

	public void setDataDigitalizacao(Date dataDigitalizacao) {
		this.dataDigitalizacao = dataDigitalizacao;
	}

	public boolean statusTransacaoEstaDivergente() {
		return statusTransacaoEstaDivergente;
	}

	public void setStatusTransacaoEstaDivergente(boolean statusTransacaoEstaDivergente) {
		this.statusTransacaoEstaDivergente = statusTransacaoEstaDivergente;
	}

	public String getStatusTransacao() {
		return statusTransacao;
	}

	public void setStatusTransacao(String statusTransacao) {
		this.statusTransacao = statusTransacao;
	}

	public boolean estaExcluido() {
		return excluido;
	}

	public void setExcluido(boolean excluido) {
		this.excluido = excluido;
	}

	public boolean isFinalizado() {
		return finalizado;
	}

	public void setFinalizado(boolean finalizado) {
		this.finalizado = finalizado;
	}

	public Long getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(Long idFuncionario) {
		this.idFuncionario = idFuncionario;
	}

	public String getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(String tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public String getDataEmissaoFormatada() {
		return dataEmissaoFormatada;
	}

	public void setDataEmissaoFormatada(String dataEmissaoFormatada) {
		this.dataEmissaoFormatada = dataEmissaoFormatada;
	}
	
	public boolean temPrimeiroMovimento() {
		return this.primeiroMovimento != null;
	}
	
	public boolean temUltimoMovimento() {
		return this.ultimoMovimento != null;
	}

	public String getUnidadeAtendimento() {
		return unidadeAtendimento;
	}

	public void setUnidadeAtendimento(String unidadeAtendimento) {
		this.unidadeAtendimento = unidadeAtendimento;
	}
	
}
