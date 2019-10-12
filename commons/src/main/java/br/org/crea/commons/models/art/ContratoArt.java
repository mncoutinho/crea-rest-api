package br.org.crea.commons.models.art;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.org.crea.commons.models.commons.Endereco;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;
import br.org.crea.commons.util.DateUtils;
import br.org.crea.commons.util.StringUtil;


@Entity
@Table(name="ART_CONTRATO")
public class ContratoArt implements Serializable  {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CODIGO")
	private String id;
	
	@Column(name="NUMERO_CONTRATO")
	private String numeroContrato;
	
	@Column(name="NOME_CONTRATANTE")
	private String nomeContratante;
	
	@OneToOne
	@JoinColumn(name="FK_PESSOA")
	private Pessoa pessoa; // CONTRATANTE DE UMA ART
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="FK_ENDERECO")
	private Endereco endereco;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="FK_ENDERECO_CONTRATANTE")
	private Endereco enderecoContratante;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="FK_ENDERECO_PROPRIETARIO")
	private Endereco enderecoProprietario;
	
	@OneToOne
	@JoinColumn(name="FK_CONVENIO_PUBLICO")
	private ConvenioPublico convenioPublico;
	
	@OneToOne
	@JoinColumn(name="FK_BAIXA")
	private BaixaArt baixaArt;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_BAIXA")
	private Date dataBaixa;
	
	@OneToOne
	@JoinColumn(name="FK_RAMO_ART")
	private RamoArt ramoArt;
	
	@OneToOne
	@JoinColumn(name="FK_ART")
	private Art art;
	
	@Column(name="SEQUENCIAL")
	private Long sequencial;
	
	@Column(name="NUMERO_PAVTOS")
	private Long numeroPavtos;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_INICIO")
	private Date dataInicio;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_FIM")
	private Date dataFim;
	
	@Column(name="PRAZO_DETERMINADO")
	private Boolean prazoDeterminado;
	
	@Column(name="PRAZO_MES")
	private Long prazoMes;
	
	@Column(name="PRAZO_DIA")
	private Long prazoDia;
	
	@Column(name="VALOR_CONTRATO")
	private BigDecimal valorContrato;
	
	@Column(name="VALOR_PAGO")
	private BigDecimal valorPago;
	
	@Column(name="SALARIO")
	private BigDecimal salario;
	
	@Column(name="DESCRICAO_COMPLEMENTARES")
	private String descricaoComplementares;
	
	@Column(name="MOTIVO_BAIXA_OUTROS")
	private String motivoBaixaOutros;
	
	@Column(name="ASS_CONTRATANTE")
	private Boolean assContratante;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_ASS")
	private Date dataAss;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CADASTRO")
	private Date dataCadastro;
	
	@Column(name="VALOR_RECEBER")
	private BigDecimal valorReceber;
	
	@Column(name="VALOR_CALCULADO")
	private BigDecimal valorCalculado;
	
	@Column(name="NHHJT")
	private String NHHJT;
	
	@Column(name="PROLABORE")
	private Boolean prolabore;
	
	@Column(name="CODIGO_OBRA_SERVICO")
	private String codigoObraServico;
	
	@Column(name="ARBITRAGEM")
	private Boolean arbitragem;
	
	@Column(name="FK_FUNCIONARIO_CADASTRO")
	private Long funcionarioCadastro;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="FK_RECEITA")
	private ArtReceita receita;
	
	@Column(name="ACESSIBILIDADE")
	private Boolean acessibilidade;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CELEBRACAO")
	private Date dataCelebracao;
	
	@OneToOne
	@JoinColumn(name="FK_ART_TIPO_TAXA")
	private ArtTipoTaxa tipoTaxa;
	
	@OneToOne
	@JoinColumn(name="FK_FINALIDADE")
	private ContratoArtFinalidade finalidade;
	
	@OneToOne
	@JoinColumn(name="FK_TIPO_ACAO_INSTITUCIONAL")
	private ContratoArtTipoAcaoInstitucional tipoAcaoInstitucional;
	
	@OneToOne
	@JoinColumn(name="FK_TIPO_CONTRATANTE")
	private ContratoArtTipoContratante tipoContratante;
	
	@OneToOne
	@JoinColumn(name="FK_PROPRIETARIO")
	private Pessoa proprietario;
	
	@OneToOne
	@JoinColumn(name="FK_ART_TIPO_UNIDADE_ADM")
	private ContratoArtTipoUnidadeAdministrativa tipoUnidadeAdministrativa;
	
	@OneToOne
	@JoinColumn(name="FK_ART_TIPO_VINCULO")
	private ContratoArtTipoVinculo tipoVinculo;
	
	@OneToOne
	@JoinColumn(name="FK_ART_TIPO_CARGO_FUNCAO")
	private ContratoArtTipoCargoFuncao tipoCargoFuncao;
	
	@OneToOne
	@JoinColumn(name="FK_ART_TIPO_FUNCAO")
	private ContratoArtTipoFuncao tipoFuncao;
	
	@Column(name="DESCRICAO_CARGO_FUNCAO")
	private String descricaoCargoFuncao;
	
	@Column(name="FINALIZADO")
	private Boolean finalizado;
	
	@Column(name="ART_VINCULADA_CONTRATO")
	private String numeroArtVinculadaAoContrato;
	
	@Transient
	private String idStringModalidade;
	
	@Transient
	private ArtQuantificacao quantificacao;
	
	@Transient
	private List<Long> listCodigoAtividades;
	
	@Transient
	private List<Long> listCodigoEspecificacoes;
	
	@Transient
	private List<Long> listCodigoComplementos;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNumeroContrato() {
		return numeroContrato;
	}

	public void setNumeroContrato(String numeroContrato) {
		this.numeroContrato = numeroContrato;
	}

	public String getNomeContratante() {
		return nomeContratante;
	}

	public void setNomeContratante(String nomeContratante) {
		this.nomeContratante = nomeContratante;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Endereco getEnderecoContratante() {
		return enderecoContratante;
	}

	public void setEnderecoContratante(Endereco enderecoContratante) {
		this.enderecoContratante = enderecoContratante;
	}
	
	

	public BaixaArt getBaixaArt() {
		return baixaArt;
	}

	public void setBaixaArt(BaixaArt baixaArt) {
		this.baixaArt = baixaArt;
	}

	public Date getDataBaixa() {
		return dataBaixa;
	}

	public void setDataBaixa(Date dataBaixa) {
		this.dataBaixa = dataBaixa;
	}

	public RamoArt getRamoart() {
		return ramoArt;
	}

	public void setRamoart(RamoArt ramoArt) {
		this.ramoArt = ramoArt;
	}

	public Art getArt() {
		return art;
	}

	public void setArt(Art art) {
		this.art = art;
	}

	public Long getSequencial() {
		return sequencial;
	}

	public void setSequencial(Long sequencial) {
		this.sequencial = sequencial;
	}

	public Long getNumeroPavtos() {
		return numeroPavtos;
	}

	public void setNumeroPavtos(Long numeroPavtos) {
		this.numeroPavtos = numeroPavtos;
	}
	
	public boolean naoTemNumeroPavtos() {
		return this.numeroPavtos == null;
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

	public Boolean getPrazoDeterminado() {
		return prazoDeterminado;
	}

	public void setPrazoDeterminado(Boolean prazoDeterminado) {
		this.prazoDeterminado = prazoDeterminado;
	}

	public Long getPrazoMes() {
		return prazoMes;
	}

	public void setPrazoMes(Long prazoMes) {
		this.prazoMes = prazoMes;
	}

	public Long getPrazoDia() {
		return prazoDia;
	}

	public void setPrazoDia(Long prazoDia) {
		this.prazoDia = prazoDia;
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

	public BigDecimal getSalario() {
		return salario;
	}

	public void setSalario(BigDecimal salario) {
		this.salario = salario;
	}

	public String getDescricaoComplementares() {
		return descricaoComplementares;
	}

	public void setDescricaoComplementares(String descricaoComplementares) {
		this.descricaoComplementares = descricaoComplementares;
	}

	public String getMotivoBaixaOutros() {
		return motivoBaixaOutros;
	}

	public void setMotivoBaixaOutros(String motivoBaixaOutros) {
		this.motivoBaixaOutros = motivoBaixaOutros;
	}

	public Boolean getAssContratante() {
		return assContratante;
	}

	public void setAssContratante(Boolean assContratante) {
		this.assContratante = assContratante;
	}

	public Date getDataAss() {
		return dataAss;
	}

	public void setDataAss(Date dataAss) {
		this.dataAss = dataAss;
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

	public String getNHHJT() {
		return NHHJT;
	}

	public void setNHHJT(String nHHJT) {
		NHHJT = nHHJT;
	}
	
	public boolean naoTemNHHJT () {
		return this.NHHJT == null;
	}

	public Boolean getProlabore() {
		return prolabore;
	}

	public void setProlabore(Boolean prolabore) {
		this.prolabore = prolabore;
	}
	
	

	public ArtReceita getReceita() {
		return receita;
	}

	public void setReceita(ArtReceita receita) {
		this.receita = receita;
	}
	
	public boolean temPessoa() {
		return this.pessoa != null;
	}
	
	public boolean temEndereco() {
		return this.endereco != null;
	}
	
	public boolean naoTemEndereco() {
		if(this.endereco != null) {
			return StringUtil.isBlank(this.endereco.getNumero()) || 
					StringUtil.isBlank(this.endereco.getLogradouro()) ||
					StringUtil.isBlank(this.endereco.getBairro());
		}
		return this.endereco == null;
	}
	
	public boolean temEnderecoContratante () {
		return this.enderecoContratante != null;
	}
	
	public boolean naoTemEnderecoContratante () {
		if(this.enderecoContratante != null) {
			return StringUtil.isBlank(this.enderecoContratante.getNumero()) || 
					StringUtil.isBlank(this.enderecoContratante.getLogradouro()) ||
					StringUtil.isBlank(this.enderecoContratante.getBairro());
		}
		return this.enderecoContratante == null;
	}
	
	public boolean temRamoArt () {
		return this.ramoArt != null;
	}
	
	public boolean naoTemRamoArt () {
		return this.ramoArt == null;
	}
	
	public boolean temBaixaArt () {
		return this.baixaArt != null;
	}

	public ConvenioPublico getConvenioPublico() {
		return convenioPublico;
	}

	public void setConvenioPublico(ConvenioPublico convenioPublico) {
		this.convenioPublico = convenioPublico;
	}

	public RamoArt getRamoArt() {
		return ramoArt;
	}

	public void setRamoArt(RamoArt ramoArt) {
		this.ramoArt = ramoArt;
	}
	
	public Boolean getAcessibilidade() {
		return acessibilidade;
	}

	public void setAcessibilidade(Boolean acessibilidade) {
		this.acessibilidade = acessibilidade;
	}

	public Date getDataCelebracao() {
		return dataCelebracao;
	}

	public void setDataCelebracao(Date dataCelebracao) {
		this.dataCelebracao = dataCelebracao;
	}

	public ContratoArtFinalidade getFinalidade() {
		return finalidade;
	}

	public void setFinalidade(ContratoArtFinalidade finalidade) {
		this.finalidade = finalidade;
	}

	public ContratoArtTipoAcaoInstitucional getTipoAcaoInstitucional() {
		return tipoAcaoInstitucional;
	}

	public void setTipoAcaoInstitucional(ContratoArtTipoAcaoInstitucional tipoAcaoInstitucional) {
		this.tipoAcaoInstitucional = tipoAcaoInstitucional;
	}

	public ContratoArtTipoContratante getTipoContratante() {
		return tipoContratante;
	}

	public void setTipoContratante(ContratoArtTipoContratante tipoContratante) {
		this.tipoContratante = tipoContratante;
	}

	public Pessoa getProprietario() {
		return proprietario;
	}

	public void setProprietario(Pessoa proprietario) {
		this.proprietario = proprietario;
	}

	public ContratoArtTipoUnidadeAdministrativa getTipoUnidadeAdministrativa() {
		return tipoUnidadeAdministrativa;
	}

	public void setTipoUnidadeAdministrativa(ContratoArtTipoUnidadeAdministrativa tipoUnidadeAdministrativa) {
		this.tipoUnidadeAdministrativa = tipoUnidadeAdministrativa;
	}

	public ContratoArtTipoVinculo getTipoVinculo() {
		return tipoVinculo;
	}

	public void setTipoVinculo(ContratoArtTipoVinculo tipoVinculo) {
		this.tipoVinculo = tipoVinculo;
	}

	public ContratoArtTipoCargoFuncao getTipoCargoFuncao() {
		return tipoCargoFuncao;
	}

	public void setTipoCargoFuncao(ContratoArtTipoCargoFuncao tipoCargoFuncao) {
		this.tipoCargoFuncao = tipoCargoFuncao;
	}

	public ContratoArtTipoFuncao getTipoFuncao() {
		return tipoFuncao;
	}

	public void setTipoFuncao(ContratoArtTipoFuncao tipoFuncao) {
		this.tipoFuncao = tipoFuncao;
	}

	public ArtTipoTaxa getTipoTaxa() {
		return tipoTaxa;
	}

	public void setTipoTaxa(ArtTipoTaxa tipoTaxa) {
		this.tipoTaxa = tipoTaxa;
	}

	public boolean temTipoUnidadeAdministrativa() {
		return this.tipoUnidadeAdministrativa != null;
	}

	public boolean temTipoVinculo() {
		return this.tipoVinculo != null;
	}

	public boolean temTipoContratante() {
		return this.tipoContratante != null;
	}

	public boolean temTipoAcaoInstitucional() {
		return this.tipoAcaoInstitucional != null;
	}

	public boolean temTipoCargoFuncao() {
		return this.tipoCargoFuncao != null;
	}

	public boolean temTipoFuncao() {
		return this.tipoFuncao != null;
	}

	public boolean temFinalidade() {
		return this.finalidade != null;
	}
	
	public boolean naoTemFinalidade() {
		return this.finalidade == null;
	}

	public boolean temProprietario() {
		return this.proprietario != null;
	}

	public boolean temValorContrato() {
		return this.valorContrato != null;
	}

	public boolean temConvenioPublico() {
		return this.convenioPublico != null;
	}

	public String getCodigoObraServico() {
		return codigoObraServico;
	}

	public void setCodigoObraServico(String codigoObraServico) {
		this.codigoObraServico = codigoObraServico;
	}

	public Boolean getArbitragem() {
		return arbitragem;
	}

	public void setArbitragem(Boolean arbitragem) {
		this.arbitragem = arbitragem;
	}

	public Boolean getFinalizado() {
		return finalizado;
	}

	public void setFinalizado(Boolean finalizado) {
		this.finalizado = finalizado;
	}

	public Long getFuncionarioCadastro() {
		return funcionarioCadastro;
	}

	public void setFuncionarioCadastro(Long funcionarioCadastro) {
		this.funcionarioCadastro = funcionarioCadastro;
	}
	
	public boolean hePrimeiroContrato() {
		return this.sequencial == 1L;
	}

	public boolean artEhDesempenhoDeCargoEFuncao() {
		return this.getArt().ehDesempenhoDeCargoFuncao();
	}
	
	public boolean artPossuiArtPrincipal() {
		return this.getArt().temArtPrincipal();
	}

	public boolean artNaoPossuiArtPrincipal() {
		return !this.getArt().temArtPrincipal();
	}

	public boolean temNumeroContrato() {
		return this.numeroContrato != null;
	}

	public boolean artEhMultipla() {
		return this.getArt().heMultipla();
	}

	public boolean artEhObraServico() {
		return this.getArt().ehObraServico();
	}
	
	
	public Date getDataFinalContrato() {
		return this.dataFim != null ? this.dataFim : new Date();
	}
	
	public Date getDataInicialContrato() {
		return this.dataInicio != null ? this.dataInicio : new Date();
	}

	public boolean comparaProfissionalComArtVinculada(Pessoa pessoa) {
		return this.getPessoa().getId().equals(pessoa.getId());
	}
	
	public boolean temPrazoIndeterminado() {
		if(this.prazoDeterminado != null) {
			return !this.prazoDeterminado;
		}
		return true;
	}

	public boolean temPrazoMes() {
		return this.prazoMes != null;
	}
	
	public boolean temPrazoDia() {
		return this.prazoDia != null;
	}

	public BigDecimal getValorCalculado() {
		return valorCalculado;
	}

	public void setValorCalculado(BigDecimal valorCalculado) {
		this.valorCalculado = valorCalculado;
	}

	public List<Long> getListCodigoAtividades() {
		return listCodigoAtividades;
	}

	public void setListCodigoAtividades(List<Long> listCodigoAtividades) {
		this.listCodigoAtividades = listCodigoAtividades;
	}

	public List<Long> getListCodigoEspecificacoes() {
		return listCodigoEspecificacoes;
	}

	public void setListCodigoEspecificacoes(List<Long> listCodigoEspecificacoes) {
		this.listCodigoEspecificacoes = listCodigoEspecificacoes;
	}

	public List<Long> getListCodigoComplementos() {
		return listCodigoComplementos;
	}

	public void setListCodigoComplementos(List<Long> listCodigoComplementos) {
		this.listCodigoComplementos = listCodigoComplementos;
	}

	public String getIdStringModalidade() {
		return idStringModalidade;
	}

	public void setIdStringModalidade(String idStringModalidade) {
		this.idStringModalidade = idStringModalidade;
	}

	public ArtQuantificacao getQuantificacao() {
		return quantificacao;
	}

	public void setQuantificacao(ArtQuantificacao quantificacao) {
		this.quantificacao = quantificacao;
	}
	
	public boolean temQuantificacao() {
		return this.quantificacao != null;
	}
	
	public boolean naoTemValorQuantificacao() {
		if(this.quantificacao != null) {
			return this.quantificacao.getValor() == null || this.quantificacao.getValor().equals(new BigDecimal("0"));
		}
		return true;
	}
	
	public boolean naoTemUnidadeQuantificacao() {
		if(this.quantificacao != null) {
			return this.quantificacao.getUnidadeMedida() == null;
		}
		return true;
	}

	public boolean temAtividades() {
		return this.listCodigoAtividades != null;
	}
	
	public boolean naoTemAtividades() {
		return this.listCodigoAtividades == null;
	}

	public boolean temEspecificacoes() {
		return this.listCodigoEspecificacoes != null;
	}
	
	public boolean naoTemEspecificacoes() {
		return this.listCodigoEspecificacoes == null;
	}

	public boolean temComplementos() {
		return this.listCodigoComplementos != null;
	}

	public boolean naoTemComplementos() {
		return this.listCodigoComplementos == null;
	}
	
	public boolean temDataInicio() {
		return this.dataInicio != null;
	}
	
	public boolean temDataFim() {
		return this.dataFim != null;
	}

	public boolean artNaoEhMultiplaMensal() {
		return !this.art.heMultiplaMensal();
	}

	public boolean artEhMultiplaMensal() {
		return this.art.heMultiplaMensal();
	}

	public boolean temDescricaoComplementares() {
		if (this.descricaoComplementares != null) {
			return !this.descricaoComplementares.equals("");
		}
		return false;
	}

	public boolean artEhReceituarioAgronomico() {
		return this.art.ehReceituarioAgronomico();
	}

	public boolean naoTemDataInicio() {
		return this.dataInicio == null;
	}
	
	public boolean dataInicioEhSuperiorA2Meses() {
		if(this.dataInicio != null) {
			return DateUtils.primeiraDataeMaiorQueSegunda(this.dataInicio, DateUtils.adicionaOrSubtraiMesesA(new Date(),2));
		}
		return false;
	}
	
	public boolean dataInicioEhSuperiorADataAtual() {
		if(this.dataInicio != null) {
			return DateUtils.primeiraDataeMaiorQueSegunda(this.dataInicio, new Date());
		}
		return false;
	}

	public boolean naoTemDataFim() {
		return this.dataFim == null;
	}

	public String getDescricaoCargoFuncao() {
		return descricaoCargoFuncao;
	}

	public void setDescricaoCargoFuncao(String descricaoCargoFuncao) {
		this.descricaoCargoFuncao = descricaoCargoFuncao;
	}

	public boolean naoTemNumeroDoContrato() {
		return this.numeroContrato == null;
	}

	public boolean naoTemValorDoContrato() {
		if (this.valorContrato != null) {
			return this.valorContrato.equals(new BigDecimal("0"));
		}
		return true;
	}

	public boolean naoTemDataCelebracao() {
		return this.dataCelebracao == null;
	}
	
	public boolean temDataCelebracao() {
		return this.dataCelebracao != null;
	}

	public boolean naoTemPrazoMes() {
		return this.prazoMes == null;
	}
	
	public boolean naoTemPrazoDia() {
		return this.prazoDia == null;
	}

	public boolean naoTemSalario() {
		if (this.salario != null) {
			return this.salario.equals(new BigDecimal(0));
		}
		return true;
	}

	public boolean naoTemProLabore() {
		return this.prolabore == null;
	}
	
	public boolean selecionouQueNaoTemProLabore() {
		if(this.prolabore != null) {
			return !this.prolabore;
		}
		return true;
	}

	public boolean naoTemTipoUnidadeAdministrativa() {
		if (this.tipoUnidadeAdministrativa != null) {
			return this.tipoUnidadeAdministrativa.getId().equals(0L);
		}
		return true;
	}

	public boolean naoTemPrazoDeterminado() {
		return this.prazoDeterminado == null;
	}

	public boolean naoTemTipoVinculo() {
		if (this.tipoVinculo != null) {
			return this.tipoVinculo.getId().equals(0L);
		}
		return true;
	}

	public boolean temPrazoDeterminadoVerificaPrazosMesesEDias() {
		if (this.prazoDeterminado != null) {
			if (this.prazoDeterminado) {
				return naoTemPrazoDia() && naoTemPrazoMes();
			}
		}
		return false;
	}
	
	public boolean temPrazoDeterminadoVerificaDataPrevisaoTermino() {
		if (this.prazoDeterminado != null) {
			if (this.prazoDeterminado) {
				return naoTemDataFim();
			}
		}
		return false;
	}

	public boolean naoTemContratante() {
		return this.pessoa == null;
	}

	public boolean naoTemTipoContratante() {
		return this.tipoContratante == null;
	}

	public boolean artNaoPossuiArtParticipacaoTecnica() {
		return this.art.getNumeroARTParticipacaoTecnica() == null;
	}

	public boolean artPossuiArtParticipacaoTecnica() {
		return this.art.getNumeroARTParticipacaoTecnica() != null;
	}

	public boolean dataCelebracaoEhSuperiorA2Meses() {
		if(this.dataCelebracao != null) {
			return DateUtils.primeiraDataeMaiorQueSegunda(this.dataCelebracao, DateUtils.adicionaOrSubtraiMesesA(new Date(),2));
		}
		return false;
	}

	public Endereco getEnderecoProprietario() {
		return enderecoProprietario;
	}

	public void setEnderecoProprietario(Endereco enderecoProprietario) {
		this.enderecoProprietario = enderecoProprietario;
	}

	public boolean temEnderecoProprietario() {
		return this.enderecoProprietario != null;
	}

	public boolean naoTemEnderecoProprietario() {
		if(this.enderecoProprietario != null) {
			return StringUtil.isBlank(this.enderecoProprietario.getNumero()) || 
					StringUtil.isBlank(this.enderecoProprietario.getLogradouro()) ||
					StringUtil.isBlank(this.enderecoProprietario.getBairro());
		}
		return this.enderecoProprietario == null;
	}

	public boolean temContratantePessoaJuridicaDireitoPrivado() {
		if (this.tipoContratante != null) {
			return this.tipoContratante.getId().equals(2L);
		}
		return false;
	}

	public boolean temSalario() {
		return this.salario != null;
	}

	public String getNumeroArtVinculadaAoContrato() {
		return numeroArtVinculadaAoContrato;
	}

	public void setNumeroArtVinculadaAoContrato(String numeroArtVinculadaAoContrato) {
		this.numeroArtVinculadaAoContrato = numeroArtVinculadaAoContrato;
	}

	public boolean naoEhUnidadeDeMedidaMetroQuadrado() {
		if (this.quantificacao != null) {
			if (this.quantificacao.getUnidadeMedida() != null) {
				return !this.quantificacao.getUnidadeMedida().getCodigo().equals(14L);
			}
		}
		return false;
	}

	public boolean temAcessibilidade() {
		return this.acessibilidade != null;
	}

	public boolean temArbitragem() {
		return this.arbitragem != null;
	}
	
}
