package br.org.crea.commons.models.art.dtos;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import br.org.crea.commons.models.commons.dtos.EnderecoDto;
import br.org.crea.commons.models.commons.dtos.GenericDto;
import br.org.crea.commons.models.commons.dtos.TelefoneDto;
import br.org.crea.commons.models.corporativo.dtos.PessoaDto;

public class ContratoServicoDto {

	private String id;

	private String numero;
	
	private Long idPessoaContratada;
	
	private Long idPessoaContratante;

	private String nomeContratante;
	
	private String nomeContratada;
	
	private PessoaDto empresaContratada;

	private String cpfOuCnpj;
	
	private EnderecoDto enderecoContrato;

	private EnderecoDto enderecoContratante;

	private GenericDto baixaContrato;

	private GenericDto ramo;

	private String motivoBaixaOutros;

	private Date dataBaixa;

	private Date dataInicio;
	
	private Date dataFim;
	
	private Date dataCadastro;

	private BigDecimal prazoDeterminado;

	private Boolean prolabore;

	private Boolean assinaturaContratante;

	private BigDecimal valorContrato;

	private BigDecimal valorPago;

	private BigDecimal valorReceber;

	private BigDecimal salario;

	private String jornadaDeTrabalho;

	private String descricaoComplementares;

	private BigDecimal prazoMes;

	private BigDecimal prazoDia;

	private BigDecimal numeroDePavimentos;

	private Long sequencial;
	
	private String atividades;
	
	private String especificacoes;
	
	private String complementos;
	
	private Long idReceita;
	
	private boolean ativo;
	
	private GenericDto servico;
	
	private Long codigo;
	
	private String nomeContratado;
	
	private List<TelefoneDto> telefonesContratado;
	
	private List<TelefoneDto> telefonesContratante;
	
	private String cpfOuCnpjContratado;
	
	private GenericDto atividade;
	
	private String notaFiscal;
	
	private String numeroArt;
	
	private String nomeProfissional;
	
	private String enderecoProfissional;
	
	private String numeroRegistroCreaProfissional;
	
	private String tipoVinculoProfissional;
	
	private String cpfOuCnpjContratante;
	
	private String ramoArt;
	
	private String DataInicioFormatada;
	
	private String dataFimFormatada;
	
	private String cpfOuCnpjContratadoFormatado;
	
	private String possuiContratoFormal;
	
	private String tipoAtividade;
	
	private String tipoContratacao;
	
	private Date dataContratoServico;
	
	private String dataContratoServicoFormatada;
	
	private String ativoSimNao;
	
	private String registroContratado;
	
	private String contrato;
	
	private String processo;
	
	private String pedidoCompra;
	
	private String ordemServico;
	
	private String cartaConvite;
	
	private BigDecimal valorEmReais;
	
	private String atividadeDesenvolvida;
	
	private String objetoContrato;
	
	private String numeroDocumentoCondominio;
	
	public String getNomeProfissional() {
		return nomeProfissional;
	}

	public void setNomeProfissional(String nomeProfissional) {
		this.nomeProfissional = nomeProfissional;
	}

	public String getEnderecoProfissional() {
		return enderecoProfissional;
	}

	public void setEnderecoProfissional(String enderecoProfissional) {
		this.enderecoProfissional = enderecoProfissional;
	}

	public String getNumeroRegistroCreaProfissional() {
		return numeroRegistroCreaProfissional;
	}

	public void setNumeroRegistroCreaProfissional(String numeroRegistroCreaProfissional) {
		this.numeroRegistroCreaProfissional = numeroRegistroCreaProfissional;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public GenericDto getServico() {
		return servico;
	}

	public void setServico(GenericDto servico) {
		this.servico = servico;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getNomeContratante() {
		return nomeContratante;
	}

	public void setNomeContratante(String nomeContratante) {
		this.nomeContratante = nomeContratante;
	}

	public EnderecoDto getEnderecoContrato() {
		return enderecoContrato;
	}

	public void setEnderecoContrato(EnderecoDto enderecoContrato) {
		this.enderecoContrato = enderecoContrato;
	}

	public EnderecoDto getEnderecoContratante() {
		return enderecoContratante;
	}

	public void setEnderecoContratante(EnderecoDto enderecoContratante) {
		this.enderecoContratante = enderecoContratante;
	}

	public GenericDto getBaixaContrato() {
		return baixaContrato;
	}

	public void setBaixaContrato(GenericDto baixaContrato) {
		this.baixaContrato = baixaContrato;
	}

	public GenericDto getRamo() {
		return ramo;
	}

	public void setRamo(GenericDto ramo) {
		this.ramo = ramo;
	}

	public String getMotivoBaixaOutros() {
		return motivoBaixaOutros;
	}

	public void setMotivoBaixaOutros(String motivoBaixaOutros) {
		this.motivoBaixaOutros = motivoBaixaOutros;
	}

	public Date getDataBaixa() {
		return dataBaixa;
	}

	public void setDataBaixa(Date dataBaixa) {
		this.dataBaixa = dataBaixa;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public BigDecimal getPrazoDeterminado() {
		return prazoDeterminado;
	}

	public void setPrazoDeterminado(BigDecimal prazoDeterminado) {
		this.prazoDeterminado = prazoDeterminado;
	}

	public Boolean getProlabore() {
		return prolabore;
	}

	public void setProlabore(Boolean prolabore) {
		this.prolabore = prolabore;
	}

	public Boolean getAssinaturaContratante() {
		return assinaturaContratante;
	}

	public void setAssinaturaContratante(Boolean assinaturaContratante) {
		this.assinaturaContratante = assinaturaContratante;
	}

	public BigDecimal getValorContrato() {
		return valorContrato;
	}

	public void setValorContrato(BigDecimal valorContrato) {
		this.valorContrato = valorContrato;
	}

	public BigDecimal getValorPago() {
		return valorPago;
	}

	public void setValorPago(BigDecimal valorPago) {
		this.valorPago = valorPago;
	}

	public BigDecimal getValorReceber() {
		return valorReceber;
	}

	public void setValorReceber(BigDecimal valorReceber) {
		this.valorReceber = valorReceber;
	}

	public BigDecimal getSalario() {
		return salario;
	}

	public void setSalario(BigDecimal salario) {
		this.salario = salario;
	}

	public String getJornadaDeTrabalho() {
		return jornadaDeTrabalho;
	}

	public void setJornadaDeTrabalho(String jornadaDeTrabalho) {
		this.jornadaDeTrabalho = jornadaDeTrabalho;
	}

	public String getDescricaoComplementares() {
		return descricaoComplementares;
	}

	public void setDescricaoComplementares(String descricaoComplementares) {
		this.descricaoComplementares = descricaoComplementares;
	}

	public BigDecimal getPrazoMes() {
		return prazoMes;
	}

	public void setPrazoMes(BigDecimal prazoMes) {
		this.prazoMes = prazoMes;
	}

	public BigDecimal getPrazoDia() {
		return prazoDia;
	}

	public void setPrazoDia(BigDecimal prazoDia) {
		this.prazoDia = prazoDia;
	}

	public BigDecimal getNumeroDePavimentos() {
		return numeroDePavimentos;
	}

	public void setNumeroDePavimentos(BigDecimal numeroDePavimentos) {
		this.numeroDePavimentos = numeroDePavimentos;
	}

	public Long getSequencial() {
		return sequencial;
	}

	public void setSequencial(Long sequencial) {
		this.sequencial = sequencial;
	}

	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}

	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}

	public Long getIdPessoaContratante() {
		return idPessoaContratante;
	}

	public void setIdPessoaContratante(Long idPessoaContratante) {
		this.idPessoaContratante = idPessoaContratante;
	}

	public PessoaDto getEmpresaContratada() {
		return empresaContratada;
	}

	public void setEmpresaContratada(PessoaDto empresaContratada) {
		this.empresaContratada = empresaContratada;
	}

	public String getAtividades() {
		return atividades;
	}

	public void setAtividades(String atividades) {
		this.atividades = atividades;
	}

	public String getEspecificacoes() {
		return especificacoes;
	}

	public void setEspecificacoes(String especificacoes) {
		this.especificacoes = especificacoes;
	}

	public String getComplementos() {
		return complementos;
	}

	public void setComplementos(String complementos) {
		this.complementos = complementos;
	}

	public Long getIdReceita() {
		return idReceita;
	}

	public void setIdReceita(Long idReceita) {
		this.idReceita = idReceita;
	}

	public String getNumeroArt() {
		return numeroArt;
	}

	public void setNumeroArt(String numeroArt) {
		this.numeroArt = numeroArt;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNomeContratado() {
		return nomeContratado;
	}

	public void setNomeContratado(String nomeContratado) {
		this.nomeContratado = nomeContratado;
	}

	public List<TelefoneDto> getTelefonesContratado() {
		return telefonesContratado;
	}

	public void setTelefonesContratado(List<TelefoneDto> telefonesContratado) {
		this.telefonesContratado = telefonesContratado;
	}

	public List<TelefoneDto> getTelefonesContratante() {
		return telefonesContratante;
	}

	public void setTelefonesContratante(List<TelefoneDto> telefonesContratante) {
		this.telefonesContratante = telefonesContratante;
	}

	public String getCpfOuCnpjContratado() {
		return cpfOuCnpjContratado;
	}

	public void setCpfOuCnpjContratado(String cpfOuCnpjContratado) {
		this.cpfOuCnpjContratado = cpfOuCnpjContratado;
	}

	public GenericDto getAtividade() {
		return atividade;
	}

	public void setAtividade(GenericDto atividade) {
		this.atividade = atividade;
	}

	public Long getIdPessoaContratada() {
		return idPessoaContratada;
	}

	public void setIdPessoaContratada(Long idPessoaContratada) {
		this.idPessoaContratada = idPessoaContratada;
	}

	public String getNotaFiscal() {
		return notaFiscal;
	}

	public void setNotaFiscal(String notaFiscal) {
		this.notaFiscal = notaFiscal;
	}

	public String getNomeContratada() {
		return nomeContratada;
	}

	public void setNomeContratada(String nomeContratada) {
		this.nomeContratada = nomeContratada;
	}

	public String getDataInicioFormatada() {
		return DataInicioFormatada;
	}

	public void setDataInicioFormatada(String dataInicioFormatada) {
		DataInicioFormatada = dataInicioFormatada;
	}

	public String getDataFimFormatada() {
		return dataFimFormatada;
	}

	public void setDataFimFormatada(String dataFimFormatada) {
		this.dataFimFormatada = dataFimFormatada;
	}

	public String getCpfOuCnpjContratadoFormatado() {
		return cpfOuCnpjContratadoFormatado;
	}

	public void setCpfOuCnpjContratadoFormatado(String cpfOuCnpjContratadoFormatado) {
		this.cpfOuCnpjContratadoFormatado = cpfOuCnpjContratadoFormatado;
	}

	public String possuiContratoFormal() {
		return possuiContratoFormal;
	}

	public void setPossuiContratoFormal(String possuiContratoFormal) {
		this.possuiContratoFormal = possuiContratoFormal;
	}

	public String getTipoVinculoProfissional() {
		return tipoVinculoProfissional;
	}

	public void setTipoVinculoProfissional(String tipoVinculoProfissional) {
		this.tipoVinculoProfissional = tipoVinculoProfissional;
	}

	public String getCpfOuCnpjContratante() {
		return cpfOuCnpjContratante;
	}

	public void setCpfOuCnpjContratante(String cpfOuCnpjContratante) {
		this.cpfOuCnpjContratante = cpfOuCnpjContratante;
	}

	public String getRamoArt() {
		return ramoArt;
	}

	public void setRamoArt(String ramoArt) {
		this.ramoArt = ramoArt;
	}

	public String getTipoAtividade() {
		return tipoAtividade;
	}

	public void setTipoAtividade(String tipoAtividade) {
		this.tipoAtividade = tipoAtividade;
	}

	public String getTipoContratacao() {
		return tipoContratacao;
	}

	public void setTipoContratacao(String tipoContratacao) {
		this.tipoContratacao = tipoContratacao;
	}

	public Date getDataContratoServico() {
		return dataContratoServico;
	}

	public void setDataContratoServico(Date dataContratoServico) {
		this.dataContratoServico = dataContratoServico;
	}

	public String getPossuiContratoFormal() {
		return possuiContratoFormal;
	}

	public String getDataContratoServicoFormatada() {
		return dataContratoServicoFormatada;
	}

	public void setDataContratoServicoFormatada(String dataContratoServicoFormatada) {
		this.dataContratoServicoFormatada = dataContratoServicoFormatada;
	}

	public String getAtivoSimNao() {
		return ativoSimNao;
	}

	public void setAtivoSimNao(String ativoSimNao) {
		this.ativoSimNao = ativoSimNao;
	}

	public String getRegistroContratado() {
		return registroContratado;
	}

	public void setRegistroContratado(String registroContratado) {
		this.registroContratado = registroContratado;
	}

	public String getContrato() {
		return contrato;
	}

	public void setContrato(String contrato) {
		this.contrato = contrato;
	}

	public String getProcesso() {
		return processo;
	}

	public void setProcesso(String processo) {
		this.processo = processo;
	}

	public String getPedidoCompra() {
		return pedidoCompra;
	}

	public void setPedidoCompra(String pedidoCompra) {
		this.pedidoCompra = pedidoCompra;
	}

	public String getOrdemServico() {
		return ordemServico;
	}

	public void setOrdemServico(String ordemServico) {
		this.ordemServico = ordemServico;
	}

	public String getCartaConvite() {
		return cartaConvite;
	}

	public void setCartaConvite(String cartaConvite) {
		this.cartaConvite = cartaConvite;
	}

	public BigDecimal getValorEmReais() {
		return valorEmReais;
	}

	public void setValorEmReais(BigDecimal valorEmReais) {
		this.valorEmReais = valorEmReais;
	}

	public String getAtividadeDesenvolvida() {
		return atividadeDesenvolvida;
	}

	public void setAtividadeDesenvolvida(String atividadeDesenvolvida) {
		this.atividadeDesenvolvida = atividadeDesenvolvida;
	}

	public String getObjetoContrato() {
		return objetoContrato;
	}

	public void setObjetoContrato(String objetoContrato) {
		this.objetoContrato = objetoContrato;
	}

	public String getNumeroDocumentoCondominio() {
		return numeroDocumentoCondominio;
	}

	public void setNumeroDocumentoCondominio(String numeroDocumentoCondominio) {
		this.numeroDocumentoCondominio = numeroDocumentoCondominio;
	}

	
		
}
