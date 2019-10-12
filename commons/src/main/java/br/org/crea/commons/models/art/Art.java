package br.org.crea.commons.models.art;



import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.org.crea.commons.models.cadastro.EntidadeClasse;
import br.org.crea.commons.models.corporativo.pessoa.Empresa;
import br.org.crea.commons.models.corporativo.pessoa.Profissional;


@Entity
@Table(name="ART_ART")
public class Art implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="NUMERO")
	private String numero;

	@Column(name="CANCELADA")
	private Boolean cancelada;
	
	@OneToOne
	@JoinColumn(name="FK_ENTIDADE_CLASSE")
	private EntidadeClasse entidadeClasse;
	
	@Column(name="BAIXADA")
	private Boolean baixada;
	
	@Column(name="MOTIVO_BAIXA_OUTROS")
	private String motivoBaixaOutros;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_BAIXA")
	private Date dataBaixa;
	
	@OneToOne
	@JoinColumn(name="FK_BAIXA")	
	private BaixaArt baixaArt;
		
	@OneToOne
	@JoinColumn(name="FK_NATUREZA_ART")
	private NaturezaArt naturezaArt; 

	@OneToOne
	@JoinColumn(name="FK_TIPO_ART")
	private TipoArt tipoArt;
	
	@OneToOne
	@JoinColumn(name="FK_PARTICIPACAO_TECNICA")
	private ParticipacaoTecnicaArt participacaoTecnica;
	
	@OneToOne
	@JoinColumn(name="FK_FATO_GERADOR_ART")
	private FatoGeradorArt fatoGeradorArt;
	
	@Column(name="DESCRICAO_FATO_GERADOR") 
	private String descricaoFatoGerador;
	
	@OneToOne
	@JoinColumn(name="FK_PROFISSIONAL")
	private Profissional profissional; 
	
	@Column(name="HA_EMPRESA_VINCULADA")
	private Boolean haEmpresaVinculada;
	
	@Column(name="HA_PROFISSIONAL_CORESPONSAVEL")
	private Boolean haProfissionalCoResponsavel;
	
	@OneToOne
	@JoinColumn(name="FK_EMPRESA")
	private Empresa empresa; 
	
	
	//@ManyToOne
	//@JoinColumn(name="FK_ART_PRINCIPAL")
	//private Art artPrincipal;
	
	@Column(name="ART_PRINCIPAL_NUMERO")
	private String numeroARTPrincipal;
	
	@Column(name="FK_ART_PARTICIPACAO")
	private String numeroARTParticipacaoTecnica;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_PAGAMENTO")
	private Date dataPagamento;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CADASTRO")
	private Date dataCadastro;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_ULTIMA_ALTERACAO")
	private Date dataUltimaAlteracao;
	
	@Column(name="VALOR_RECEBER")
	private BigDecimal valorReceber;
	
	@Column(name="VALOR_PAGO")
	private BigDecimal valorPago;
	
	@Column(name="WS")
	private Boolean webService;
	
	@Column(name="FINALIZADA")
	private Boolean finalizada;
	
	@Column(name="LIBERADA")
	private Boolean liberada;
	
	@Column(name="ISACAO_ORDINARIA")
	private Boolean isAcaoOrdinaria ;

	@Column(name="POSSUI_EXIGENCIA")
	private Boolean exigencia ;
	
	@Column(name="TERMO_ADITIVO")
	private Boolean isTermoAditivo ;
	
	@Column(name="ACESSIBILIDADE")
	private Boolean acessibilidade;
	
	@Column(name="ASS_CONTRATADO")
	private Boolean assinaturaContratado;
	
	@Column(name="USUARIO_CADASTRO")
	private Boolean usuarioCadastro;
	
	@Column(name="ISONLINE")
	private Boolean isOnline;
	
	@Column(name="CERTIFICADA")
	private Boolean isCertificada;
	
	@Column(name="FK_FUNCIONARIO_CADASTRO")
	private Long funcionarioCadastro;
	
	@Column(name="FK_FUNCIONARIO_ALTERACAO")
	private Long funcionarioAlteracao;
	
	@Column(name="MULTIPLA_MENSAL")
	private Boolean multiplaMensal;
	
	@Column(name="PAGOU_NO_PRAZO")
	private Boolean pagouNoPrazo;
	
	@Column(name="TAXA_MINIMA")
	private Boolean taxaMinima;
	
	@Column(name="FORNECE_CONCRETO")
	private Boolean forneceConcreto;
	
	@OneToOne
	@JoinColumn(name="FK_ART_TIPO_TAXA")
	private ArtTipoTaxa tipoTaxa;
	
	@Column(name="MODELO")
	private Boolean modelo;
	
	@Column(name="DESCRICAO_MODELO")
	private String descricaoModelo;

	@Column(name="PRIMEIRA_PARTICIPACAO_TECNICA")
	private Boolean primeiraParticipacaoTecnica;

	
	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
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

	public BaixaArt getBaixaArt() {
		return baixaArt;
	}

	public void setBaixaArt(BaixaArt baixaArt) {
		this.baixaArt = baixaArt;
	}

	public NaturezaArt getNaturezaArt() {
		return naturezaArt;
	}

	public void setNaturezaArt(NaturezaArt naturezaArt) {
		this.naturezaArt = naturezaArt;
	}

	public TipoArt getTipoArt() {
		return tipoArt;
	}

	public void setTipoArt(TipoArt tipoArt) {
		this.tipoArt = tipoArt;
	}

	public FatoGeradorArt getFatoGeradorArt() {
		return fatoGeradorArt;
	}

	public void setFatoGeradorArt(FatoGeradorArt fatoGeradorArt) {
		this.fatoGeradorArt = fatoGeradorArt;
	}

	public String getDescricaoFatoGerador() {
		return descricaoFatoGerador;
	}

	public void setDescricaoFatoGerador(String descricaoFatoGerador) {
		this.descricaoFatoGerador = descricaoFatoGerador;
	}

	public Profissional getProfissional() {
		return profissional;
	}

	public void setProfissional(Profissional profissional) {
		this.profissional = profissional;
	}

	public Boolean getHaEmpresaVinculada() {
		return haEmpresaVinculada;
	}

	public void setHaEmpresaVinculada(Boolean haEmpresaVinculada) {
		this.haEmpresaVinculada = haEmpresaVinculada;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public String getNumeroARTPrincipal() {
		return numeroARTPrincipal;
	}

	public void setNumeroARTPrincipal(String numeroARTPrincipal) {
		this.numeroARTPrincipal = numeroARTPrincipal;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public BigDecimal getValorReceber() {
		return valorReceber;
	}

	public void setValorReceber(BigDecimal valorReceber) {
		this.valorReceber = valorReceber;
	}

	public BigDecimal getValorPago() {
		return valorPago;
	}

	public void setValorPago(BigDecimal valorPago) {
		this.valorPago = valorPago;
	}

	public Boolean getWebService() {
		return webService;
	}

	public void setWebService(Boolean webService) {
		this.webService = webService;
	}

	public Boolean getFinalizada() {
		return finalizada;
	}

	public void setFinalizada(Boolean finalizada) {
		this.finalizada = finalizada;
	}

	public Boolean getIsAcaoOrdinaria() {
		return isAcaoOrdinaria;
	}

	public void setIsAcaoOrdinaria(Boolean isAcaoOrdinaria) {
		this.isAcaoOrdinaria = isAcaoOrdinaria;
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

	public EntidadeClasse getEntidadeClasse() {
		return entidadeClasse;
	}

	public void setEntidadeClasse(EntidadeClasse entidadeClasse) {
		this.entidadeClasse = entidadeClasse;
	}
	
	public Boolean getHaProfissionalCoResponsavel() {
		return haProfissionalCoResponsavel;
	}

	public void setHaProfissionalCoResponsavel(Boolean haProfissionalCoResponsavel) {
		this.haProfissionalCoResponsavel = haProfissionalCoResponsavel;
	}
	
	public Boolean getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(Boolean isOnline) {
		this.isOnline = isOnline;
	}
	
	
	public Boolean getIsTermoAditivo() {
		return isTermoAditivo;
	}

	public void setIsTermoAditivo(Boolean isTermoAditivo) {
		this.isTermoAditivo = isTermoAditivo;
	}	
	
	public Boolean getIsCertificada() {
		return isCertificada;
	}

	public void setIsCertificada(Boolean isCertificada) {
		this.isCertificada = isCertificada;
	}	

	public Date getDataUltimaAlteracao() {
		return dataUltimaAlteracao;
	}

	public void setDataUltimaAlteracao(Date dataUltimaAlteracao) {
		this.dataUltimaAlteracao = dataUltimaAlteracao;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public boolean temBaixaArt(){
		return this.baixaArt != null;
	}

	public boolean temNaturezaArt(){
		return this.naturezaArt != null;
	}

	public boolean temEmpresa(){
		if(this.empresa != null) {
			return !this.empresa.getId().equals(0L);
		}
		return false;
	}
	
	public boolean temEmpresaInformada(){
		return this.empresa != null;
	}

	public boolean temProfissional(){
		return this.profissional != null;
	}
	
	public boolean temTipoArt(){
		return this.tipoArt != null;
	}
	
	public boolean temFatoGerador(){
		return this.fatoGeradorArt != null;
	}
	
	public boolean temEntidadeClasse(){
		return this.entidadeClasse != null;
	}

	public boolean heMultipla() {
		return this.getNaturezaArt().getId().equals(5L);
	}

	public Boolean getExigencia() {
		return exigencia;
	}

	public void setExigencia(Boolean exigencia) {
		this.exigencia = exigencia;
	}

	public Boolean getMultiplaMensal() {
		return multiplaMensal;
	}
	
	public boolean heMultiplaMensal() {
		return this.multiplaMensal == true;
	}

	public void setMultiplaMensal(Boolean multiplaMensal) {
		this.multiplaMensal = multiplaMensal;
	}

	public Boolean getPagouNoPrazo() {
		return pagouNoPrazo;
	}

	public void setPagouNoPrazo(Boolean pagouNoPrazo) {
		this.pagouNoPrazo = pagouNoPrazo;
	}
	public Boolean verificaSeHeUmaArtCancelada() {
		return this.getBaixaArt().heCancelada();
	}
	
	public boolean temArtPrincipalEtemTaxaMinima() {
    	return this.numeroARTPrincipal != null && this.taxaMinima == true;
	}
	
	public boolean heTaxaMinima() {
		return this.taxaMinima == true;
	}

	public boolean heCargoEfuncao() {
		return this.naturezaArt.getId() == 2L;
	}


	public boolean profissionalEoMesmoDaArtPrincipal(Profissional profissional) {
		return this.getProfissional().getId().equals(profissional.getId());
	}

	public boolean temArtPrincialEObraEServico() {
		return this.numeroARTPrincipal != null && this.naturezaArt.getId() == 1L;
	}


	public boolean temArtPrincipal() {
		return this.numeroARTPrincipal != null;
	}

	public boolean ehObraServico() {
		return this.naturezaArt.getId().equals(1L);
	}
	
	public boolean ehDesempenhoDeCargoFuncao() {
		return this.naturezaArt.getId().equals(2L);
	}

	public Boolean getTaxaMinima() {
		return taxaMinima;
	}

	public void setTaxaMinima(Boolean taxaMinima) {
		this.taxaMinima = taxaMinima;
	}

	public Boolean getForneceConcreto() {
		return forneceConcreto;
	}

	public void setForneceConcreto(Boolean forneceConcreto) {
		this.forneceConcreto = forneceConcreto;
	}

	public ArtTipoTaxa getTipoTaxa() {
		return tipoTaxa;
	}

	public void setTipoTaxa(ArtTipoTaxa tipoTaxa) {
		this.tipoTaxa = tipoTaxa;
	}

	public boolean naoHeMultiplaMensal() {
		return !this.multiplaMensal;
	}
	
	public boolean temValorReceber() {
		return this.valorReceber != null;
	}

	public Boolean getLiberada() {
		return liberada;
	}

	public void setLiberada(Boolean liberada) {
		this.liberada = liberada;
	}

	public boolean ehSubstituta() {
		return this.tipoArt.getId().equals(2L);
	}

	public boolean ehReceituarioAgronomico() {
		return this.naturezaArt.getId().equals(3L);
	}

	public boolean naoEhTaxaMinima() {
		return !this.taxaMinima;
	}

	public ParticipacaoTecnicaArt getParticipacaoTecnica() {
		return participacaoTecnica;
	}

	public void setParticipacaoTecnica(ParticipacaoTecnicaArt participacaoTecnica) {
		this.participacaoTecnica = participacaoTecnica;
	}

	public String getNumeroARTParticipacaoTecnica() {
		return numeroARTParticipacaoTecnica;
	}

	public void setNumeroARTParticipacaoTecnica(String numeroARTParticipacaoTecnica) {
		this.numeroARTParticipacaoTecnica = numeroARTParticipacaoTecnica;
	}
	
	public boolean temNumeroARTParticipacaoTecnica() {
		return numeroARTParticipacaoTecnica != null;
	}

	public boolean temParticipacaoTecnica() {
		return this.participacaoTecnica != null;
	}

	public boolean naoTemTipo() {
		return this.tipoArt == null;
	}
	
	public boolean naoTemParticipacaoTecnica() {
		return this.participacaoTecnica == null;
	}
	
	public boolean naoTemEntidadeDeClasse() {
		return this.entidadeClasse == null;
	}

	public boolean tipoNaoEhInicial() {
		return !this.tipoArt.getId().equals(0L);
	}

	public boolean naoTemNumeroArtPrincipal() {
		return this.numeroARTPrincipal == null;
	}

	public boolean participacaoTecnicaNaoEhIndividual() {
		return !this.participacaoTecnica.getId().equals(1L) && this.primeiraParticipacaoTecnica == false;
	}

	public boolean naoTemNumeroArtParticipacaoTecnica() {
		return this.numeroARTParticipacaoTecnica == null;
	}

	public boolean naoTemFatoGerador() {
		return this.fatoGeradorArt == null;
	}

	public boolean fatoGeradorNecessitaDeDescricao() {
		return !this.fatoGeradorArt.getId().equals(0L) && !this.fatoGeradorArt.getId().equals(4L) && !this.fatoGeradorArt.getId().equals(5L) && !this.fatoGeradorArt.getId().equals(6L) ;
	}
	
	public boolean naoTemDescricaoFatoGerador() {
		return this.descricaoFatoGerador == null;
	}

	public boolean naoTemProfissional() {
		return this.profissional == null;
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

	public boolean naoEscolheuPrimeiraParticipacaoTecnica() {
		return this.primeiraParticipacaoTecnica == null;
	}
	
}