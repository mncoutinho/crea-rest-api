package br.org.crea.commons.models.siacol.dtos;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

import br.org.crea.commons.helper.CustomJsonDateDeserializer;
import br.org.crea.commons.models.cadastro.enuns.TipoEventoAuditoria;
import br.org.crea.commons.models.siacol.enuns.ClassificacaoProtocoloPautaEnum;
import br.org.crea.commons.models.siacol.enuns.OrderFilterSiacolProtocolo;
import br.org.crea.commons.models.siacol.enuns.StatusProtocoloSiacol;

public class GenericSiacolDto {
	
    private String id;
    
    private String tipoTramitacao;
    
    private Long idProtocolo;
	
	private Long idFuncionario;
	
	private Long idPessoa;
	
	private String idPerfil;
	
	private Long idConselheiroDevolucao;
	
	private Long idResponsavelAtual;
	
	private String nomeResponsavelAtual;
	
	private Long idResponsavelNovo;
	
	private String nomeResponsavelNovo;
	
	private Long idDepartamento;
	
	private String codigo;
	
	private String nome;
	
	private String descricao;
	
	private String descricaoPerfil;
	
	private String sigla;
	
	private Boolean siacol;
	
	private Long idSituacao;
	
	private Long idAssuntoSiacolProtocolo;
	
	private List<Long> listaId;
	
	private List<Long> listaIdAssunto;
	
	private List<Long> listaIdDepartamento;
	
	private List<Long> listaNumeroProtocolo;
	
	private String numeroDocumento;
	
	private Long idCadDOcumento;
		
	@JsonDeserialize(using=CustomJsonDateDeserializer.class)
	private Date datainicio;
	
	@JsonDeserialize(using=CustomJsonDateDeserializer.class)
	private Date datafim;
	
	private Date dataInicio;
	
	private Date dataFim;
	
	private String dataInicioFormatada;
	
	private String dataFimFormatada;
	
	private Boolean ativo;
	
	private String motivoDevolucao;
	
	private String justificativa;
	
	private Long ordemFiltro;
	
	private StatusProtocoloSiacol status;
	
	private ClassificacaoProtocoloPautaEnum classificacao;
	
	private ClassificacaoProtocoloPautaEnum classificacaoFinal;
	
	private OrderFilterSiacolProtocolo filtroProtocolo;
	
	private String modulo;
	
	private String template;
	
	private Long idAssunto;
	
	private boolean distribuicaoParaConselheiro;
	
	private Long numeroProtocolo;
	
	private boolean adReferendum;
	
	private TipoEventoAuditoria evento;
	
	private EventoDto eventoProtocolo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getIdProtocolo() {
		return idProtocolo;
	}

	public void setIdProtocolo(Long idProtocolo) {
		this.idProtocolo = idProtocolo;
	}

	public Long getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(Long idFuncionario) {
		this.idFuncionario = idFuncionario;
	}

	public Long getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(Long idPessoa) {
		this.idPessoa = idPessoa;
	}

	public String getIdPerfil() {
		return idPerfil;
	}

	public void setIdPerfil(String idPerfil) {
		this.idPerfil = idPerfil;
	}

	public Long getIdConselheiroDevolucao() {
		return idConselheiroDevolucao;
	}

	public void setIdConselheiroDevolucao(Long idConselheiroDevolucao) {
		this.idConselheiroDevolucao = idConselheiroDevolucao;
	}

	public Long getIdDepartamento() {
		return idDepartamento;
	}

	public void setIdDepartamento(Long idDepartamento) {
		this.idDepartamento = idDepartamento;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public Boolean getSiacol() {
		return siacol;
	}

	public void setSiacol(Boolean siacol) {
		this.siacol = siacol;
	}

	public List<Long> getListaId() {
		return listaId;
	}

	public void setListaId(List<Long> listaId) {
		this.listaId = listaId;
	}

	public List<Long> getListaIdAssunto() {
		return listaIdAssunto;
	}

	public void setListaIdAssunto(List<Long> listaIdAssunto) {
		this.listaIdAssunto = listaIdAssunto;
	}

	public List<Long> getListaIdDepartamento() {
		return listaIdDepartamento;
	}

	public void setListaIdDepartamento(List<Long> listaIdDepartamento) {
		this.listaIdDepartamento = listaIdDepartamento;
	}

	public List<Long> getListaNumeroProtocolo() {
		return listaNumeroProtocolo;
	}

	public void setListaNumeroProtocolo(List<Long> listaNumeroProtocolo) {
		this.listaNumeroProtocolo = listaNumeroProtocolo;
	}

	public Date getDatainicio() {
		return datainicio;
	}

	public void setDatainicio(Date datainicio) {
		this.datainicio = datainicio;
	}

	public Date getDatafim() {
		return datafim;
	}

	public void setDatafim(Date datafim) {
		this.datafim = datafim;
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

	public String getDataInicioFormatada() {
		return dataInicioFormatada;
	}

	public void setDataInicioFormatada(String dataInicioFormatada) {
		this.dataInicioFormatada = dataInicioFormatada;
	}

	public String getDataFimFormatada() {
		return dataFimFormatada;
	}

	public void setDataFimFormatada(String dataFimFormatada) {
		this.dataFimFormatada = dataFimFormatada;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Long getIdSituacao() {
		return idSituacao;
	}

	public void setIdSituacao(Long idSituacao) {
		this.idSituacao = idSituacao;
	}

	public Long getIdResponsavelAtual() {
		return idResponsavelAtual;
	}

	public void setIdResponsavelAtual(Long idResponsavelAtual) {
		this.idResponsavelAtual = idResponsavelAtual;
	}

	public String getNomeResponsavelAtual() {
		return nomeResponsavelAtual;
	}

	public void setNomeResponsavelAtual(String nomeResponsavelAtual) {
		this.nomeResponsavelAtual = nomeResponsavelAtual;
	}

	public Long getIdResponsavelNovo() {
		return idResponsavelNovo;
	}

	public void setIdResponsavelNovo(Long idResponsavelNovo) {
		this.idResponsavelNovo = idResponsavelNovo;
	}

	public String getNomeResponsavelNovo() {
		return nomeResponsavelNovo;
	}

	public void setNomeResponsavelNovo(String nomeResponsavelNovo) {
		this.nomeResponsavelNovo = nomeResponsavelNovo;
	}

	public Long getIdAssuntoSiacolProtocolo() {
		return idAssuntoSiacolProtocolo;
	}

	public void setIdAssuntoSiacolProtocolo(Long idAssuntoSiacolProtocolo) {
		this.idAssuntoSiacolProtocolo = idAssuntoSiacolProtocolo;
	}

	public String getTipoTramitacao() {
		return tipoTramitacao;
	}

	public void setTipoTramitacao(String tipoTramitacao) {
		this.tipoTramitacao = tipoTramitacao;
	}
	

	public String getMotivoDevolucao() {
		return motivoDevolucao;
	}

	public void setMotivoDevolucao(String motivoDevolucao) {
		this.motivoDevolucao = motivoDevolucao;
	}

	public String getJustificativa() {
		return justificativa;
	}

	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}
	
	public String getDescricaoPerfil() {
		return descricaoPerfil;
	}

	public void setDescricaoPerfil(String descricaoPerfil) {
		this.descricaoPerfil = descricaoPerfil;
	}
	
	public Long getOrdemFiltro() {
		return ordemFiltro;
	}

	public void setOrdemFiltro(Long ordemFiltro) {
		this.ordemFiltro = ordemFiltro;
	}

	public OrderFilterSiacolProtocolo getFiltroProtocolo() {
		return filtroProtocolo;
	}

	public void setFiltroProtocolo(OrderFilterSiacolProtocolo filtroProtocolo) {
		this.filtroProtocolo = filtroProtocolo;
	}

	public StatusProtocoloSiacol getStatus() {
		return status;
	}

	public void setStatus(StatusProtocoloSiacol status) {
		this.status = status;
	}

	public ClassificacaoProtocoloPautaEnum getClassificacao() {
		return classificacao;
	}

	public void setClassificacao(ClassificacaoProtocoloPautaEnum classificacao) {
		this.classificacao = classificacao;
	}

	public ClassificacaoProtocoloPautaEnum getClassificacaoFinal() {
		return classificacaoFinal;
	}

	public void setClassificacaoFinal(
			ClassificacaoProtocoloPautaEnum classificacaoFinal) {
		this.classificacaoFinal = classificacaoFinal;
	}

	public String getModulo() {
		return modulo;
	}

	public void setModulo(String modulo) {
		this.modulo = modulo;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public Long getIdAssunto() {
		return idAssunto;
	}

	public void setIdAssunto(Long idAssunto) {
		this.idAssunto = idAssunto;
	}

	public boolean distribuicaoParaConselheiro() {
		return distribuicaoParaConselheiro;
	}

	public void setDistribuicaoParaConselheiro(boolean pessoaEhConselheiro) {
		this.distribuicaoParaConselheiro = pessoaEhConselheiro;
	}

	public Long getNumeroProtocolo() {
		return numeroProtocolo;
	}

	public void setNumeroProtocolo(Long numeroProtocolo) {
		this.numeroProtocolo = numeroProtocolo;
	}

	public boolean isDistribuicaoParaConselheiro() {
		return distribuicaoParaConselheiro;
	}

	public boolean isAdReferendum() {
		return adReferendum;
	}

	public void setAdReferendum(boolean adReferendum) {
		this.adReferendum = adReferendum;
	}

	public boolean temSituacao() {
		return this.getIdSituacao() != null;
	}

	public boolean temAssuntoSiacolProtocolo() {
		return this.getIdAssuntoSiacolProtocolo() != null;
	}

	public boolean temResponsavelNovo() {
		return this.getIdResponsavelNovo() != null;
	}

	public boolean temDepartamento() {
		return this.getIdDepartamento() != null;
	}
	
	public boolean temJustificativa() {
		return this.getJustificativa() != null;
	}
	
	public boolean temMotivoDevolucao() {
		return this.getMotivoDevolucao() != null;
	}

	public boolean temProtocolo() {
		return this.getIdProtocolo() != null;
	}

	public boolean temStatus() {
		return this.getStatus() != null;
	}

	public boolean temClassificacao() {
		return this.getClassificacao() != null;
	}
	
	public boolean temClassificacaoFinal() {
		return this.getClassificacaoFinal() != null;
	}
	
	public boolean temAdReferendum() {
		return this.isAdReferendum() != true ? false : true;
	}

	public boolean temConselheiroDevolucao() {
		return this.getIdConselheiroDevolucao() != null;
	}

	public TipoEventoAuditoria getEvento() {
		return evento;
	}

	public void setEvento(TipoEventoAuditoria evento) {
		this.evento = evento;
	}

	public boolean temEvento() {
		return this.evento != null;
	}

	public boolean temIdResponsavelAtual() {
		return this.idResponsavelAtual != null;
	}

	public EventoDto getEventoProtocolo() {
		return eventoProtocolo;
	}

	public void setEventoProtocolo(EventoDto eventoProtocolo) {
		this.eventoProtocolo = eventoProtocolo;
	}

	public boolean temEventoProtocolo() {
		return this.eventoProtocolo != null ;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public Long getIdCadDOcumento() {
		return idCadDOcumento;
	}

	public void setIdCadDOcumento(Long idCadDOcumento) {
		this.idCadDOcumento = idCadDOcumento;
	}

	public boolean temNumeroDocumento() {
		return this.numeroDocumento != null ;
	}

}
