package br.org.crea.commons.models.protocolo.dtos;

import br.org.crea.commons.models.commons.TipoPessoa;
import br.org.crea.commons.models.commons.enuns.TipoCointeressado;
import br.org.crea.commons.models.corporativo.dtos.PessoaDto;

public class CadastroProtocoloDto {
	
	private Long idFuncionario;
	
	private Long idAssunto;
	
	private Long idSituacao;
	
	private Long numeroProcesso;
	
	private Long idDepartamentoDestino; 
	
	private String interessadoProtocolo;
	
	private String motivoCointeressado;
	
	private String observacaoDoProtocolo;
	
	private String observacaoAssunto;
	
	private Boolean requerenteProfOuEmp;
	
	private TipoPessoa tipoPessoa;
	
	private TipoCointeressado tipoCointeressado;
	
	private PessoaDto pessoaDoProtocolo;
	
	private PessoaDto pessoaCointeressada;

	public Long getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(Long idFuncionario) {
		this.idFuncionario = idFuncionario;
	}

	public Long getIdAssunto() {
		return idAssunto;
	}

	public void setIdAssunto(Long idAssunto) {
		this.idAssunto = idAssunto;
	}

	public Long getIdSituacao() {
		return idSituacao;
	}

	public void setIdSituacao(Long idSituacao) {
		this.idSituacao = idSituacao;
	}

	public Long getNumeroProcesso() {
		return numeroProcesso;
	}

	public void setNumeroProcesso(Long numeroProcesso) {
		this.numeroProcesso = numeroProcesso;
	}

	public Long getIdDepartamentoDestino() {
		return idDepartamentoDestino;
	}

	public void setIdDepartamentoDestino(Long idDepartamentoDestino) {
		this.idDepartamentoDestino = idDepartamentoDestino;
	}

	public String getInteressadoProtocolo() {
		return interessadoProtocolo;
	}

	public void setInteressadoProtocolo(String interessadoProtocolo) {
		this.interessadoProtocolo = interessadoProtocolo;
	}

	public String getMotivoCointeressado() {
		return motivoCointeressado;
	}

	public void setMotivoCointeressado(String motivoCointeressado) {
		this.motivoCointeressado = motivoCointeressado;
	}

	public String getObservacaoDoProtocolo() {
		return observacaoDoProtocolo;
	}

	public void setObservacaoDoProtocolo(String observacaoDoProtocolo) {
		this.observacaoDoProtocolo = observacaoDoProtocolo;
	}

	public String getObservacaoAssunto() {
		return observacaoAssunto;
	}

	public void setObservacaoAssunto(String observacaoAssunto) {
		this.observacaoAssunto = observacaoAssunto;
	}

	public Boolean getRequerenteProfOuEmp() {
		return requerenteProfOuEmp;
	}

	public void setRequerenteProfOuEmp(Boolean requerenteProfOuEmp) {
		this.requerenteProfOuEmp = requerenteProfOuEmp;
	}

	public TipoPessoa getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(TipoPessoa tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public TipoCointeressado getTipoCointeressado() {
		return tipoCointeressado;
	}

	public void setTipoCointeressado(TipoCointeressado tipoCointeressado) {
		this.tipoCointeressado = tipoCointeressado;
	}

	public PessoaDto getPessoaDoProtocolo() {
		return pessoaDoProtocolo;
	}

	public void setPessoaDoProtocolo(PessoaDto pessoaDoProtocolo) {
		this.pessoaDoProtocolo = pessoaDoProtocolo;
	}

	public PessoaDto getPessoaCointeressada() {
		return pessoaCointeressada;
	}

	public void setPessoaCointeressada(PessoaDto pessoaCointeressada) {
		this.pessoaCointeressada = pessoaCointeressada;
	}

}
