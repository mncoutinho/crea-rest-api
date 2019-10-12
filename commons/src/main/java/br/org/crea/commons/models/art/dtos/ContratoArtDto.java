package br.org.crea.commons.models.art.dtos;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonPropertyOrder;

import br.org.crea.commons.models.commons.dtos.DomainGenericDto;
import br.org.crea.commons.models.commons.dtos.EnderecoDto;
import br.org.crea.commons.models.corporativo.dtos.PessoaDto;

@JsonPropertyOrder({ "id", "numero", "numeroArt", "idPessoaContratante", "nomeContratante", 
	"idPessoaProprietario", "nomeProprietario", "atividades",
	"especificacoes", "cpfOuCnpjContratante", "tipoVinculoProfissional", "complementos", "dataCadastro",
	"dataCadastroFormatada", "prolabore", "assinaturaContratante", "valorContrato", "dataCelebracao", "dataCelebracaoFormatada", 
	"valorContratoFormatado", "valorContratoFormatadoPorExtenso", "valorPago", "valorReceber", "valorCalculado", 
    "valorInseridoManualmente", "salario", "jornadaDeTrabalho", "descricaoComplementares", "dataInicio", 
    "dataInicioFormatada", "dataFim", "dataFimFormatada", "dataBaixa", "dataBaixaFormatada",
    "dataContrato", "dataContratoFormatada", "motivoBaixaOutros", "sequencial",
    "prazoMes", "prazoDia", "prazoDeterminado", "numeroDePavimentos", "acessibilidade", "codigoObraServico", "arbitragem",
    "contratante", "enderecoContrato", "enderecoContratante", "baixa", "ramoART", "convenioPublico", "tipoTaxa", 
    "tipoContratante", "tipoUnidadeAdministrativa","tipoVinculo","tipoAcaoInstitucional","tipoCargoFuncao","tipoFuncao","finalidade","finalizado","numeroArtVinculadaAoContrato"})
public class ContratoArtDto {

	private String id;

	private String numero;
	
	private String numeroArt;
	
	private Long idPessoaContratante;
	
	private String nomeContratante;
	
	private String tipoPessoaContratante;
	
	private Long idPessoaProprietario;
	
	private String nomeProprietario;

	private Long idReceita;
	
	private String cpfOuCnpjContratante;
	
	private String cpfOuCnpjProprietario;
	
	private String registroContratante;
	
	private String tipoVinculoProfissional;
	
	private Date dataCadastro;
	
	private String dataCadastroFormatada;

	private Boolean prolabore;

	private Boolean assinaturaContratante;

	private BigDecimal valorContrato;
	
	private Date dataCelebracao;
	
	private String dataCelebracaoFormatada;

	private String valorContratoFormatado;

	private String valorContratoFormatadoPorExtenso;

	private BigDecimal valorPago;

	private BigDecimal valorReceber;

	private BigDecimal valorCalculado;

	private BigDecimal valorInseridoManualmente;

	private BigDecimal salario;

	private String jornadaDeTrabalho;

	private String descricaoComplementares;
	
	private Date dataInicio;
	
	private String dataInicioFormatada;
	
	private String dataInicioFormatadaYYYYMMDD;
	
	private Date dataFim;
	
	private String dataFimFormatada;
	
	private String dataFimFormatadaYYYYMMDD;

	private Date dataBaixa;
	
	private String dataBaixaFormatada;
	
	private Date dataContrato;
	
	private String dataContratoFormatada;
	
	private String motivoBaixaOutros;

	private Long sequencial;
	
	private Long prazoMes;

	private Long prazoDia;

	private Boolean prazoDeterminado;

	private Long numeroDePavimentos;

	private Boolean acessibilidade;
	
	private String codigoObraServico;
	
	private Boolean arbitragem;
	
	private String descricaoCargoFuncao;
	
	private List<DomainGenericDto> listAtividades;
	
	private String previewAtividades;
	
	private String previewEspecificacoes;
	
	private String previewComplementos;
	
	private List<DomainGenericDto> listEspecificacoes;
	
	private List<DomainGenericDto> listComplementos;
	
	private PessoaDto contratante;

	private EnderecoDto enderecoContrato;

	private EnderecoDto enderecoContratante;
	
	private EnderecoDto enderecoProprietario;
	
	private DomainGenericDto quantificacao;
	
	private DomainGenericDto tipoContratante;

	private DomainGenericDto baixa;

	private DomainGenericDto ramoART;
	
	private DomainGenericDto convenioPublico;
	
	private DomainGenericDto tipoTaxa;
	
	private DomainGenericDto tipoUnidadeAdministrativa;
	
	private DomainGenericDto tipoVinculo;
	
	private DomainGenericDto tipoAcaoInstitucional;
	
	private DomainGenericDto tipoCargoFuncao;
	
	private DomainGenericDto tipoFuncao;
	
	private DomainGenericDto finalidade;
	
	private Boolean finalizado;
	
	private String numeroArtVinculadaAoContrato;
	
	private String nome;
	
	public Long getIdReceita() {
		return idReceita;
	}

	public void setIdReceita(Long idReceita) {
		this.idReceita = idReceita;
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

	public String getNumeroArt() {
		return numeroArt;
	}

	public void setNumeroArt(String numeroArt) {
		this.numeroArt = numeroArt;
	}

	public String getNomeContratante() {
		return nomeContratante;
	}

	public void setNomeContratante(String nomeContratante) {
		this.nomeContratante = nomeContratante;
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

	public BigDecimal getValorCalculado() {
		return valorCalculado;
	}

	public void setValorCalculado(BigDecimal valorCalculado) {
		this.valorCalculado = valorCalculado;
	}

	public BigDecimal getValorInseridoManualmente() {
		return valorInseridoManualmente;
	}

	public void setValorInseridoManualmente(BigDecimal valorInseridoManualmente) {
		this.valorInseridoManualmente = valorInseridoManualmente;
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

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public String getDataInicioFormatada() {
		return dataInicioFormatada;
	}

	public void setDataInicioFormatada(String dataInicioFormatada) {
		this.dataInicioFormatada = dataInicioFormatada;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public String getDataFimFormatada() {
		return dataFimFormatada;
	}

	public void setDataFimFormatada(String dataFimFormatada) {
		this.dataFimFormatada = dataFimFormatada;
	}

	public Date getDataBaixa() {
		return dataBaixa;
	}

	public void setDataBaixa(Date dataBaixa) {
		this.dataBaixa = dataBaixa;
	}

	public String getDataBaixaFormatada() {
		return dataBaixaFormatada;
	}

	public void setDataBaixaFormatada(String dataBaixaFormatada) {
		this.dataBaixaFormatada = dataBaixaFormatada;
	}

	public Date getDataContrato() {
		return dataContrato;
	}

	public void setDataContrato(Date dataContrato) {
		this.dataContrato = dataContrato;
	}

	public String getDataContratoFormatada() {
		return dataContratoFormatada;
	}

	public void setDataContratoFormatada(String dataContratoFormatada) {
		this.dataContratoFormatada = dataContratoFormatada;
	}

	public String getMotivoBaixaOutros() {
		return motivoBaixaOutros;
	}

	public void setMotivoBaixaOutros(String motivoBaixaOutros) {
		this.motivoBaixaOutros = motivoBaixaOutros;
	}

	public Long getSequencial() {
		return sequencial;
	}

	public void setSequencial(Long sequencial) {
		this.sequencial = sequencial;
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

	public Boolean getPrazoDeterminado() {
		return prazoDeterminado;
	}

	public void setPrazoDeterminado(Boolean prazoDeterminado) {
		this.prazoDeterminado = prazoDeterminado;
	}

	public Long getNumeroDePavimentos() {
		return numeroDePavimentos;
	}

	public void setNumeroDePavimentos(Long numeroDePavimentos) {
		this.numeroDePavimentos = numeroDePavimentos;
	}

	public Boolean getAcessibilidade() {
		return acessibilidade;
	}

	public void setAcessibilidade(Boolean acessibilidade) {
		this.acessibilidade = acessibilidade;
	}

	public List<DomainGenericDto> getListAtividades() {
		return listAtividades;
	}

	public void setListAtividades(List<DomainGenericDto> listAtividades) {
		this.listAtividades = listAtividades;
	}

	public List<DomainGenericDto> getListEspecificacoes() {
		return listEspecificacoes;
	}

	public void setListEspecificacoes(List<DomainGenericDto> listEspecificacoes) {
		this.listEspecificacoes = listEspecificacoes;
	}

	public List<DomainGenericDto> getListComplementos() {
		return listComplementos;
	}

	public void setListComplementos(List<DomainGenericDto> listComplementos) {
		this.listComplementos = listComplementos;
	}

	public DomainGenericDto getQuantificacao() {
		return quantificacao;
	}

	public void setQuantificacao(DomainGenericDto quantificacao) {
		this.quantificacao = quantificacao;
	}

	public PessoaDto getContratante() {
		return contratante;
	}

	public void setContratante(PessoaDto contratante) {
		this.contratante = contratante;
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

	public DomainGenericDto getTipoContratante() {
		return tipoContratante;
	}

	public void setTipoContratante(DomainGenericDto tipoContratante) {
		this.tipoContratante = tipoContratante;
	}

	public DomainGenericDto getBaixa() {
		return baixa;
	}

	public void setBaixa(DomainGenericDto baixa) {
		this.baixa = baixa;
	}

	public DomainGenericDto getRamoART() {
		return ramoART;
	}

	public void setRamoART(DomainGenericDto ramoART) {
		this.ramoART = ramoART;
	}

	public DomainGenericDto getConvenioPublico() {
		return convenioPublico;
	}

	public void setConvenioPublico(DomainGenericDto convenioPublico) {
		this.convenioPublico = convenioPublico;
	}

	public DomainGenericDto getTipoTaxa() {
		return tipoTaxa;
	}

	public void setTipoTaxa(DomainGenericDto tipoTaxa) {
		this.tipoTaxa = tipoTaxa;
	}


	public String getCpfOuCnpjContratante() {
		return cpfOuCnpjContratante;
	}

	public void setCpfOuCnpjContratante(String cpfOuCnpjContratante) {
		this.cpfOuCnpjContratante = cpfOuCnpjContratante;
	}

	public String getTipoVinculoProfissional() {
		return tipoVinculoProfissional;
	}

	public void setTipoVinculoProfissional(String tipoVinculoProfissional) {
		this.tipoVinculoProfissional = tipoVinculoProfissional;
	}

	public Long getIdPessoaContratante() {
		return idPessoaContratante;
	}

	public void setIdPessoaContratante(Long idPessoaContratante) {
		this.idPessoaContratante = idPessoaContratante;
	}

	public String getValorContratoFormatado() {
		return valorContratoFormatado;
	}

	public void setValorContratoFormatado(String valorContratoFormatado) {
		this.valorContratoFormatado = valorContratoFormatado;
	}

	public String getValorContratoFormatadoPorExtenso() {
		return valorContratoFormatadoPorExtenso;
	}

	public void setValorContratoFormatadoPorExtenso(String valorContratoFormatadoPorExtenso) {
		this.valorContratoFormatadoPorExtenso = valorContratoFormatadoPorExtenso;
	}

	public DomainGenericDto getTipoUnidadeAdministrativa() {
		return tipoUnidadeAdministrativa;
	}

	public void setTipoUnidadeAdministrativa(DomainGenericDto tipoUnidadeAdministrativa) {
		this.tipoUnidadeAdministrativa = tipoUnidadeAdministrativa;
	}

	public DomainGenericDto getTipoVinculo() {
		return tipoVinculo;
	}

	public void setTipoVinculo(DomainGenericDto tipoVinculo) {
		this.tipoVinculo = tipoVinculo;
	}

	public DomainGenericDto getTipoAcaoInstitucional() {
		return tipoAcaoInstitucional;
	}

	public void setTipoAcaoInstitucional(DomainGenericDto tipoAcaoInstitucional) {
		this.tipoAcaoInstitucional = tipoAcaoInstitucional;
	}

	public DomainGenericDto getTipoCargoFuncao() {
		return tipoCargoFuncao;
	}

	public void setTipoCargoFuncao(DomainGenericDto tipoCargoFuncao) {
		this.tipoCargoFuncao = tipoCargoFuncao;
	}

	public DomainGenericDto getTipoFuncao() {
		return tipoFuncao;
	}

	public void setTipoFuncao(DomainGenericDto tipoFuncao) {
		this.tipoFuncao = tipoFuncao;
	}

	public DomainGenericDto getFinalidade() {
		return finalidade;
	}

	public void setFinalidade(DomainGenericDto finalidade) {
		this.finalidade = finalidade;
	}

	public Date getDataCelebracao() {
		return dataCelebracao;
	}

	public void setDataCelebracao(Date dataCelebracao) {
		this.dataCelebracao = dataCelebracao;
	}

	public Long getIdPessoaProprietario() {
		return idPessoaProprietario;
	}

	public void setIdPessoaProprietario(Long idPessoaProprietario) {
		this.idPessoaProprietario = idPessoaProprietario;
	}

	public String getNomeProprietario() {
		return nomeProprietario;
	}

	public void setNomeProprietario(String nomeProprietario) {
		this.nomeProprietario = nomeProprietario;
	}

	public String getDataCelebracaoFormatada() {
		return dataCelebracaoFormatada;
	}

	public void setDataCelebracaoFormatada(String dataCelebracaoFormatada) {
		this.dataCelebracaoFormatada = dataCelebracaoFormatada;
	}

	public String getCodigoObraServico() {
		return codigoObraServico;
	}

	public void setCodigoObraServico(String codigoObraServico) {
		this.codigoObraServico = codigoObraServico;
	}

	public String getCpfOuCnpjProprietario() {
		return cpfOuCnpjProprietario;
	}

	public void setCpfOuCnpjProprietario(String cpfOuCnpjProprietario) {
		this.cpfOuCnpjProprietario = cpfOuCnpjProprietario;
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

	public String getDescricaoCargoFuncao() {
		return descricaoCargoFuncao;
	}

	public void setDescricaoCargoFuncao(String descricaoCargoFuncao) {
		this.descricaoCargoFuncao = descricaoCargoFuncao;
	}

	public EnderecoDto getEnderecoProprietario() {
		return enderecoProprietario;
	}

	public void setEnderecoProprietario(EnderecoDto enderecoProprietario) {
		this.enderecoProprietario = enderecoProprietario;
	}

	public String getRegistroContratante() {
		return registroContratante;
	}

	public void setRegistroContratante(String registroContratante) {
		this.registroContratante = registroContratante;
	}

	public String getPreviewAtividades() {
		StringBuilder preview = new StringBuilder();
//		if (this.listAtividades != null) {
//			if(!this.listAtividades.isEmpty()) {
//				preview.append("<ul>");
//				this.listAtividades.forEach(atividade -> {
//					preview.append("<li>" + atividade.getId() + " " + atividade.getDescricao() + "</li>");
//				});
//			}
//		}
//		if (this.listEspecificacoes != null) {
//			if(!this.listEspecificacoes.isEmpty()) {
//				this.listEspecificacoes.forEach(especificacao -> {
//					preview.append("<li>" + especificacao.getId() + " " + especificacao.getDescricao() + "</li>");
//				});
//			}
//		}
//		if (this.listComplementos != null) {
//			if(!this.listComplementos.isEmpty()) {
//				this.listComplementos.forEach(complemento -> {
//					preview.append("<li>" + complemento.getId() + " " + complemento.getDescricao() + "</li>");
//				});
//			}
//		}
//		
//		this.previewAtividades = preview.append("</ul>").toString(); 
		return this.previewAtividades;
	}

	public void setPreviewAtividades(String previewAtividades) {
		this.previewAtividades = previewAtividades;
	}

	public String getNumeroArtVinculadaAoContrato() {
		return numeroArtVinculadaAoContrato;
	}
	
	

	public void setNumeroArtVinculadaAoContrato(String numeroArtVinculadaAoContrato) {
		this.numeroArtVinculadaAoContrato = numeroArtVinculadaAoContrato;
	}

	public String getDataInicioFormatadaYYYYMMDD() {
		return dataInicioFormatadaYYYYMMDD;
	}

	public void setDataInicioFormatadaYYYYMMDD(String dataInicioFormatadaYYYYMMDD) {
		this.dataInicioFormatadaYYYYMMDD = dataInicioFormatadaYYYYMMDD;
	}

	public String getDataFimFormatadaYYYYMMDD() {
		return dataFimFormatadaYYYYMMDD;
	}

	public void setDataFimFormatadaYYYYMMDD(String dataFimFormatadaYYYYMMDD) {
		this.dataFimFormatadaYYYYMMDD = dataFimFormatadaYYYYMMDD;
	}
	
	public String getPreviewEspecificacoes() {
//		StringBuilder preview = new StringBuilder();
//		
//		if (this.listEspecificacoes != null) {
//			if(!this.listEspecificacoes.isEmpty()) {
//				preview.append("<ul>");
//				this.listEspecificacoes.forEach(especificacao -> {
//					preview.append("<li>" + especificacao.getId() + " " + especificacao.getDescricao() + "</li>");
//				});
//			}
//		}
//		
//		this.previewEspecificacoes = preview.append("</ul>").toString(); 
		return this.previewEspecificacoes;
	}
	
	public String getPreviewComplementos() {
		StringBuilder preview = new StringBuilder();

		if (this.listComplementos != null) {
			if(!this.listComplementos.isEmpty()) {
				preview.append("<ul>");
				this.listComplementos.forEach(complemento -> {
					preview.append("<li>" + complemento.getId() + " " + complemento.getDescricao() + "</li>");
				});
			}
		}
		
		this.previewComplementos = preview.append("</ul>").toString();
		return this.previewComplementos;
	}
	
	public void setPreviewEspecificacoes(String previewEspecificacoes) {
		this.previewEspecificacoes = previewEspecificacoes;
	}
	
	public void setPreviewComplementos(String previewComplementos) {
		this.previewComplementos = previewComplementos;
	}

	public String getTipoPessoaContratante() {
		return tipoPessoaContratante;
	}

	public void setTipoPessoaContratante(String tipoPessoaContratante) {
		this.tipoPessoaContratante = tipoPessoaContratante;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
