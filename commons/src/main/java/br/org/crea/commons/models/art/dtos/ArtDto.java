package br.org.crea.commons.models.art.dtos;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonPropertyOrder;

import br.org.crea.commons.models.commons.dtos.DomainGenericDto;
import br.org.crea.commons.models.corporativo.dtos.PessoaDto;

@JsonPropertyOrder({ "numero", "descricaoFatoGerador", "numeroArtPrincipal","numeroArtParticipacaoTecnica", "cancelada", "baixada", "dataBaixa", "motivoBaixaOutros", 
	"haEmpresaVinculada", "haProfissionalCoResponsavel", "dataCadastro", "dataCadastroFormatada", "dataPagamentoFormatada",
	"dataReprocessamentoExigencia", "forma", "funcionarioCadastro", "funcionarioAlteracao", "protocolo", "protocoloDevolucao", 
	"codigoLiberacao", "tipoTaxa", "formaRegistro", "pessoaOutEstPais", "isOnline", "pagouPrazo",
	"dataPagamento", "dataUltimaAlteracao",	"dataEmailCobranca", "isOEPaisLiberacao", "valorPago", "valorReceber", "isAcaoOrdinaria",
	"atual", "isEmpresa", "isEmpresaLiberacao", "isEmpresaStatusLiberacao", "isTermoAditivo", "finalizada",	"liberada",	"exigencia",
	"acessibilidade", "assinaturaContratado", "usuarioCadastro", "isCertificada", "isResgate", "is1025", "isOutEstPais", "pagouNoPrazo", 
	"isOutEstPaisStatusLiberacao", "estaQuitada", "multiplaMensal", "vinculoContratual", "webService", "quantidadeContratos", 
	"natureza", "tipo","participacaoTecnica", "fatoGerador", "entidadeClasse", "baixaArt", "profissional", "empresa", "contrato", "dadosContratoAnalise", 
	"contratos" })
public class ArtDto {

	private String numero;
	
	private String descricaoFatoGerador;

	private String numeroArtPrincipal;
	
	private String numeroArtParticipacaoTecnica;
	
	private Boolean cancelada;
	
	private Boolean baixada;
	
	private Date dataBaixa;
	
	private String motivoBaixaOutros;
	
	private Boolean haEmpresaVinculada;
	
	private Boolean haProfissionalCoResponsavel;
	
	private Date dataCadastro;
	
	private String dataCadastroFormatada;
	
	private String dataPagamentoFormatada;
	
	private String dataReprocessamentoExigencia;
	
	private String forma;
	
	private Long funcionarioCadastro;
	
	private Long funcionarioAlteracao;
	
	private String protocolo;
	
	private String protocoloDevolucao;
	
	private String codigoLiberacao;
	
	private Long tipoTaxa;
	
	private String formaRegistro;
		
	private String pessoaOutEstPais;
		
	private Date dataPagamento;
	
	private Date dataUltimaAlteracao;
	
	private Date dataEmailCobranca;
	
	private Date isOEPaisLiberacao;
	
	private BigDecimal valorPago;
	
	private BigDecimal valorReceber;
	
	private Boolean isAcaoOrdinaria;
	
	private Boolean isOnline;
	
	private Boolean pagouPrazo;
	
	private Boolean atual;
	
	private Boolean isEmpresa;
	
	private Boolean isEmpresaLiberacao;
	
	private Boolean isEmpresaStatusLiberacao;
	
	private Boolean isTermoAditivo;
	
	private Boolean finalizada;
	
	private Boolean liberada;
	
	private Boolean exigencia;
	
	private Boolean acessibilidade;
	
	private Boolean assinaturaContratado;
	
	private Boolean usuarioCadastro;
	
	private Boolean isCertificada;
	
	private Boolean isResgate;
	
	private Boolean is1025;
	
	private Boolean isOutEstPais;
	
	private Boolean isOutEstPaisStatusLiberacao;
	
	private Boolean estaQuitada;
	
	private Boolean multiplaMensal;
	
	private Boolean vinculoContratual;
	
	private Boolean webService;
	
	private Boolean pagouNoPrazo;
	
	private int quantidadeContratos;
	
	private DomainGenericDto natureza;
	
	private DomainGenericDto tipo;
	
	private DomainGenericDto participacaoTecnica;
	
	private DomainGenericDto fatoGerador;
	
	private DomainGenericDto entidadeClasse;
	
	private DomainGenericDto baixaArt;
	
	private Boolean modelo;
	
	private String descricaoModelo;
	
	private PessoaDto profissional;
	
	private PessoaDto empresa;	
	
	private ContratoServicoDto contrato;
	
	private ContratoArtDto contratoArt;
	
	private ContratoServicoDto dadosContratoAnalise;
	
	private List<ContratoServicoDto> contratos;
	
	private Boolean possuiContratoCadastrado;
	
	private Boolean primeiraParticipacaoTecnica;
	

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	public String getDescricaoFatoGerador() {
		return descricaoFatoGerador;
	}

	public void setDescricaoFatoGerador(String descricaoFatoGerador) {
		this.descricaoFatoGerador = descricaoFatoGerador;
	}

	public DomainGenericDto getNatureza() {
		return natureza;
	}

	public void setNatureza(DomainGenericDto natureza) {
		this.natureza = natureza;
	}

	public DomainGenericDto getTipo() {
		return tipo;
	}

	public void setTipo(DomainGenericDto tipo) {
		this.tipo = tipo;
	}

	public String getNumeroArtPrincipal() {
		return numeroArtPrincipal;
	}

	public void setNumeroArtPrincipal(String numeroArtPrincipal) {
		this.numeroArtPrincipal = numeroArtPrincipal;
	}

	public DomainGenericDto getFatoGerador() {
		return fatoGerador;
	}

	public void setFatoGerador(DomainGenericDto fatoGerador) {
		this.fatoGerador = fatoGerador;
	}

	public DomainGenericDto getEntidadeClasse() {
		return entidadeClasse;
	}

	public void setEntidadeClasse(DomainGenericDto entidadeClasse) {
		this.entidadeClasse = entidadeClasse;
	}

	public Boolean getCancelada() {
		return cancelada;
	}

	public void setCancelada(Boolean cancelada) {
		this.cancelada = cancelada;
	}

	public Boolean getBaixada() {
		return baixada;
	}

	public void setBaixada(Boolean baixada) {
		this.baixada = baixada;
	}

	public DomainGenericDto getBaixaArt() {
		return baixaArt;
	}

	public void setBaixaArt(DomainGenericDto baixaArt) {
		this.baixaArt = baixaArt;
	}

	public Date getDataBaixa() {
		return dataBaixa;
	}

	public void setDataBaixa(Date dataBaixa) {
		this.dataBaixa = dataBaixa;
	}

	public String getMotivoBaixaOutros() {
		return motivoBaixaOutros;
	}

	public void setMotivoBaixaOutros(String motivoBaixaOutros) {
		this.motivoBaixaOutros = motivoBaixaOutros;
	}

	public Boolean getHaEmpresaVinculada() {
		return haEmpresaVinculada;
	}

	public void setHaEmpresaVinculada(Boolean haEmpresaVinculada) {
		this.haEmpresaVinculada = haEmpresaVinculada;
	}

	public Boolean getHaProfissionalCoResponsavel() {
		return haProfissionalCoResponsavel;
	}

	public void setHaProfissionalCoResponsavel(Boolean haProfissionalCoResponsavel) {
		this.haProfissionalCoResponsavel = haProfissionalCoResponsavel;
	}

	public PessoaDto getProfissional() {
		return profissional;
	}

	public void setProfissional(PessoaDto profissional) {
		this.profissional = profissional;
	}

	public PessoaDto getEmpresa() {
		return empresa;
	}

	public void setEmpresa(PessoaDto empresa) {
		this.empresa = empresa;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getDataCadastroFormatada() {
		return dataCadastroFormatada;
	}

	public void setDataCadastroFormatada(String dataCadastroFormatada) {
		this.dataCadastroFormatada = dataCadastroFormatada;
	}

	public String getDataPagamentoFormatada() {
		return dataPagamentoFormatada;
	}

	public void setDataPagamentoFormatada(String dataPagamentoFormatada) {
		this.dataPagamentoFormatada = dataPagamentoFormatada;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public Date getDataUltimaAlteracao() {
		return dataUltimaAlteracao;
	}

	public void setDataUltimaAlteracao(Date dataUltimaAlteracao) {
		this.dataUltimaAlteracao = dataUltimaAlteracao;
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

	public Boolean getIsAcaoOrdinaria() {
		return isAcaoOrdinaria;
	}

	public void setIsAcaoOrdinaria(Boolean isAcaoOrdinaria) {
		this.isAcaoOrdinaria = isAcaoOrdinaria;
	}

	public Boolean getIsTermoAditivo() {
		return isTermoAditivo;
	}

	public void setIsTermoAditivo(Boolean isTermoAditivo) {
		this.isTermoAditivo = isTermoAditivo;
	}

	public Boolean getFinalizada() {
		return finalizada;
	}

	public void setFinalizada(Boolean finalizada) {
		this.finalizada = finalizada;
	}

	public Boolean getAcessibilidade() {
		return acessibilidade;
	}

	public void setAcessibilidade(Boolean acessibilidade) {
		this.acessibilidade = acessibilidade;
	}

	public Boolean getAssinaturaContratado() {
		return assinaturaContratado;
	}

	public void setAssinaturaContratado(Boolean assinaturaContratado) {
		this.assinaturaContratado = assinaturaContratado;
	}

	public Boolean getUsuarioCadastro() {
		return usuarioCadastro;
	}

	public void setUsuarioCadastro(Boolean usuarioCadastro) {
		this.usuarioCadastro = usuarioCadastro;
	}

	public Boolean getIsCertificada() {
		return isCertificada;
	}

	public void setIsCertificada(Boolean isCertificada) {
		this.isCertificada = isCertificada;
	}

	public Boolean getEstaQuitada() {
		return estaQuitada;
	}

	public void setEstaQuitada(Boolean estaQuitada) {
		this.estaQuitada = estaQuitada;
	}

	public ContratoServicoDto getContrato() {
		return contrato;
	}

	public void setContrato(ContratoServicoDto contrato) {
		this.contrato = contrato;
	}

	public int getQuantidadeContratos() {
		return quantidadeContratos;
	}

	public void setQuantidadeContratos(int quantidadeContratos) {
		this.quantidadeContratos = quantidadeContratos;
	}

	public List<ContratoServicoDto> getContratos() {
		return contratos;
	}

	public ContratoArtDto getContratoArt() {
		return contratoArt;
	}

	public void setContratoArt(ContratoArtDto contratoArt) {
		this.contratoArt = contratoArt;
	}

	public void setContratos(List<ContratoServicoDto> contratos) {
		this.contratos = contratos;
	}

	public ContratoServicoDto getDadosContratoAnalise() {
		return dadosContratoAnalise;
	}

	public void setDadosContratoAnalise(ContratoServicoDto dadosContratoAnalise) {
		this.dadosContratoAnalise = dadosContratoAnalise;
	}
	
	public String getDataReprocessamentoExigencia() {
		return dataReprocessamentoExigencia;
	}

	public void setDataReprocessamentoExigencia(String dataReprocessamentoExigencia) {
		this.dataReprocessamentoExigencia = dataReprocessamentoExigencia;
	}

	public String getForma() {
		return forma;
	}

	public void setForma(String forma) {
		this.forma = forma;
	}

	public Long getFuncionarioCadastro() {
		return funcionarioCadastro;
	}

	public void setFuncionarioCadastro(Long funcionarioCadastro) {
		this.funcionarioCadastro = funcionarioCadastro;
	}

	public Long getFuncionarioAlteracao() {
		return funcionarioAlteracao;
	}

	public void setFuncionarioAlteracao(Long funcionarioAlteracao) {
		this.funcionarioAlteracao = funcionarioAlteracao;
	}

	public String getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}

	public String getProtocoloDevolucao() {
		return protocoloDevolucao;
	}

	public void setProtocoloDevolucao(String protocoloDevolucao) {
		this.protocoloDevolucao = protocoloDevolucao;
	}

	public String getCodigoLiberacao() {
		return codigoLiberacao;
	}

	public void setCodigoLiberacao(String codigoLiberacao) {
		this.codigoLiberacao = codigoLiberacao;
	}

	public Long getTipoTaxa() {
		return tipoTaxa;
	}

	public void setTipoTaxa(Long tipoTaxa) {
		this.tipoTaxa = tipoTaxa;
	}

	public String getFormaRegistro() {
		return formaRegistro;
	}

	public void setFormaRegistro(String formaRegistro) {
		this.formaRegistro = formaRegistro;
	}

	public String getPessoaOutEstPais() {
		return pessoaOutEstPais;
	}

	public void setPessoaOutEstPais(String pessoaOutEstPais) {
		this.pessoaOutEstPais = pessoaOutEstPais;
	}

	public Date getDataEmailCobranca() {
		return dataEmailCobranca;
	}

	public void setDataEmailCobranca(Date dataEmailCobranca) {
		this.dataEmailCobranca = dataEmailCobranca;
	}

	public Date getIsOEPaisLiberacao() {
		return isOEPaisLiberacao;
	}

	public void setIsOEPaisLiberacao(Date isOEPaisLiberacao) {
		this.isOEPaisLiberacao = isOEPaisLiberacao;
	}

	public Boolean getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(Boolean isOnline) {
		this.isOnline = isOnline;
	}

	public Boolean getPagouPrazo() {
		return pagouPrazo;
	}

	public void setPagouPrazo(Boolean pagouPrazo) {
		this.pagouPrazo = pagouPrazo;
	}

	public Boolean getAtual() {
		return atual;
	}

	public void setAtual(Boolean atual) {
		this.atual = atual;
	}

	public Boolean getIsEmpresa() {
		return isEmpresa;
	}

	public void setIsEmpresa(Boolean isEmpresa) {
		this.isEmpresa = isEmpresa;
	}

	public Boolean getIsEmpresaLiberacao() {
		return isEmpresaLiberacao;
	}

	public void setIsEmpresaLiberacao(Boolean isEmpresaLiberacao) {
		this.isEmpresaLiberacao = isEmpresaLiberacao;
	}

	public Boolean getIsEmpresaStatusLiberacao() {
		return isEmpresaStatusLiberacao;
	}

	public void setIsEmpresaStatusLiberacao(Boolean isEmpresaStatusLiberacao) {
		this.isEmpresaStatusLiberacao = isEmpresaStatusLiberacao;
	}

	public Boolean getLiberada() {
		return liberada;
	}

	public void setLiberada(Boolean liberada) {
		this.liberada = liberada;
	}

	public Boolean getExigencia() {
		return exigencia;
	}

	public void setExigencia(Boolean exigencia) {
		this.exigencia = exigencia;
	}

	public Boolean getIsResgate() {
		return isResgate;
	}

	public void setIsResgate(Boolean isResgate) {
		this.isResgate = isResgate;
	}

	public Boolean getIs1025() {
		return is1025;
	}

	public void setIs1025(Boolean is1025) {
		this.is1025 = is1025;
	}

	public Boolean getIsOutEstPais() {
		return isOutEstPais;
	}

	public void setIsOutEstPais(Boolean isOutEstPais) {
		this.isOutEstPais = isOutEstPais;
	}

	public Boolean getIsOutEstPaisStatusLiberacao() {
		return isOutEstPaisStatusLiberacao;
	}

	public void setIsOutEstPaisStatusLiberacao(Boolean isOutEstPaisStatusLiberacao) {
		this.isOutEstPaisStatusLiberacao = isOutEstPaisStatusLiberacao;
	}

	public Boolean getMultiplaMensal() {
		return multiplaMensal;
	}

	public void setMultiplaMensal(Boolean multiplaMensal) {
		this.multiplaMensal = multiplaMensal;
	}

	public Boolean getVinculoContratual() {
		return vinculoContratual;
	}

	public void setVinculoContratual(Boolean vinculoContratual) {
		this.vinculoContratual = vinculoContratual;
	}

	public Boolean getWebService() {
		return webService;
	}

	public void setWebService(Boolean webService) {
		this.webService = webService;
	}
	
	public boolean ehObraEServico() {
		return this.getNatureza().getId().equals(new Long(1));
	}

	public boolean ehDesempenhoDeCargoEFuncao() {
		return this.getNatureza().getId().equals(new Long(2));
	}

	public boolean temEmpresa() {
		return this.empresa != null;
	}

	public Boolean getPagouNoPrazo() {
		return pagouNoPrazo;
	}

	public void setPagouNoPrazo(Boolean pagouNoPrazo) {
		this.pagouNoPrazo = pagouNoPrazo;
	}

	public Boolean getPossuiContratoCadastrado() {
		return possuiContratoCadastrado;
	}

	public void setPossuiContratoCadastrado(Boolean possuiContratoCadastrado) {
		this.possuiContratoCadastrado = possuiContratoCadastrado;
	}
	
	public boolean naoEstaFinalizada () {
		return this.finalizada != null && !this.finalizada;
	}
	
	public boolean heOMesmoProfissional (Long idPessoa) {
		return this.profissional.getId().equals(idPessoa);
	}

	public boolean heAMesmaEmpresa(Long idPessoa) {
		return this.empresa.getId().equals(idPessoa);
	}

	public String getNumeroArtParticipacaoTecnica() {
		return numeroArtParticipacaoTecnica;
	}

	public void setNumeroArtParticipacaoTecnica(String numeroArtParticipacaoTecnica) {
		this.numeroArtParticipacaoTecnica = numeroArtParticipacaoTecnica;
	}

	public DomainGenericDto getParticipacaoTecnica() {
		return participacaoTecnica;
	}

	public void setParticipacaoTecnica(DomainGenericDto participacaoTecnica) {
		this.participacaoTecnica = participacaoTecnica;
	}

	public Boolean getModelo() {
		return modelo;
	}

	public void setModelo(Boolean modelo) {
		this.modelo = modelo;
	}

	public String getDescricaoModelo() {
		return descricaoModelo;
	}

	public void setDescricaoModelo(String descricaoModelo) {
		this.descricaoModelo = descricaoModelo;
	}

	public Boolean getPrimeiraParticipacaoTecnica() {
		return primeiraParticipacaoTecnica;
	}

	public void setPrimeiraParticipacaoTecnica(Boolean primeiraParticipacaoTecnica) {
		this.primeiraParticipacaoTecnica = primeiraParticipacaoTecnica;
	} 
	
}
