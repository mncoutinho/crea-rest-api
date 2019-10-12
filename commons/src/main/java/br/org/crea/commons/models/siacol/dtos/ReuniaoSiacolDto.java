package br.org.crea.commons.models.siacol.dtos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonPropertyOrder;

import br.org.crea.commons.models.cadastro.dtos.DepartamentoDto;
import br.org.crea.commons.models.commons.dtos.ArquivoDto;
import br.org.crea.commons.models.commons.dtos.DocumentoGenericDto;
import br.org.crea.commons.models.siacol.enuns.StatusReuniaoSiacol;

@JsonPropertyOrder({ "id", "local", "tipo", "descricaoTipo", "virtual", "dataReuniao", 
	                 "dataReuniaoFormatado", "horaInicio", "horaInicioFormatado", "horaFim", 
	                 "horaFimFormatado", "prazo", "quorum", "status", "departamento", "pauta", "extraPauta", 
	                 "listDigitoExclusaoProtocolo", "ordenacaoPauta", "incluiProtocoloDesfavoravel", "acao", "idsPautas"})
public class ReuniaoSiacolDto {

	private Long id;

	private String local;

	private Long tipo;

	private String descricaoTipo;

	private Boolean virtual;
	
//	@Temporal(TemporalType.TIMESTAMP)
	private Date dataReuniao;

	private String dataReuniaoFormatado;

//	@Temporal(TemporalType.TIMESTAMP)
	private Date horaInicio;

	private String horaInicioFormatado;

//	@Temporal(TemporalType.TIMESTAMP)
	private Date horaFim;

	private String horaFimFormatado;

	private Integer prazo;

	private Integer quorum;

	private StatusReuniaoSiacol status;

	private DepartamentoDto departamento;

	private DocumentoGenericDto pauta;

	private DocumentoGenericDto extraPauta;
	
	private DocumentoGenericDto sumula;
	
	private String motivoCancelamento;

	private List<String> listDigitoExclusaoProtocolo = new ArrayList<String>();

	private List<GenericSiacolDto> ordenacaoPauta;

	private boolean incluiProtocoloDesfavoravel;
	
	private boolean manterPauta;

	private String acao;
	
	private List<Long> idsPautas;
	
	private boolean statusDestaqueParticipante = true;
	
	private boolean statusVistasParticipante = true;
	/*
	 * Status somente painel
	 */
	private boolean statusReuniaoPainel = true;

	private String statusPainelPrincipal = "principal";

	private String statusPainelConselheiro;
	
	private boolean houvePausa;
	
	private Long parte;

	private ArquivoDto arquivoNaoAssinado;
	
	private ArquivoDto arquivoAssinado;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DocumentoGenericDto getPauta() {
		return pauta;
	}

	public void setPauta(DocumentoGenericDto pauta) {
		this.pauta = pauta;
	}

	public DocumentoGenericDto getExtraPauta() {
		return extraPauta;
	}

	public void setExtraPauta(DocumentoGenericDto extraPauta) {
		this.extraPauta = extraPauta;
	}

	public DocumentoGenericDto getSumula() {
		return sumula;
	}

	public void setSumula(DocumentoGenericDto sumula) {
		this.sumula = sumula;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public Long getTipo() {
		return tipo;
	}

	public void setTipo(Long tipo) {
		this.tipo = tipo;
	}

	public Boolean getVirtual() {
		return virtual;
	}

	public void setVirtual(Boolean virtual) {
		this.virtual = virtual;
	}

	public Date getDataReuniao() {
		return dataReuniao;
	}

	public void setDataReuniao(Date dataReuniao) {
		this.dataReuniao = dataReuniao;
	}

	public Date getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(Date horaInicio) {
		this.horaInicio = horaInicio;
	}

	public Date getHoraFim() {
		return horaFim;
	}

	public void setHoraFim(Date horaFim) {
		this.horaFim = horaFim;
	}

	public Integer getPrazo() {
		return prazo;
	}

	public void setPrazo(Integer prazo) {
		this.prazo = prazo;
	}

	public StatusReuniaoSiacol getStatus() {
		return status;
	}

	public void setStatus(StatusReuniaoSiacol status) {
		this.status = status;
	}

	public String getDataReuniaoFormatado() {
		return dataReuniaoFormatado;
	}

	public void setDataReuniaoFormatado(String dataReuniaoFormatado) {
		this.dataReuniaoFormatado = dataReuniaoFormatado;
	}

	public String getHoraInicioFormatado() {
		return horaInicioFormatado;
	}

	public void setHoraInicioFormatado(String horaInicioFormatado) {
		this.horaInicioFormatado = horaInicioFormatado;
	}

	public String getHoraFimFormatado() {
		return horaFimFormatado;
	}

	public void setHoraFimFormatado(String horaFimFormatado) {
		this.horaFimFormatado = horaFimFormatado;
	}

	public String getDescricaoTipo() {
		return descricaoTipo;
	}

	public void setDescricaoTipo(String descricaoTipo) {
		this.descricaoTipo = descricaoTipo;
	}

	public List<String> getListDigitoExclusaoProtocolo() {
		return listDigitoExclusaoProtocolo;
	}

	public void setListDigitoExclusaoProtocolo(List<String> listDigitoExclusaoProtocolo) {
		this.listDigitoExclusaoProtocolo = listDigitoExclusaoProtocolo;
	}

	public List<GenericSiacolDto> getOrdenacaoPauta() {
		return ordenacaoPauta;
	}

	public void setOrdenacaoPauta(List<GenericSiacolDto> ordenacaoPauta) {
		this.ordenacaoPauta = ordenacaoPauta;
	}

	public DepartamentoDto getDepartamento() {
		return departamento;
	}

	public void setDepartamento(DepartamentoDto departamento) {
		this.departamento = departamento;
	}

	public boolean isIncluiProtocoloDesfavoravel() {
		return incluiProtocoloDesfavoravel;
	}

	public Integer getQuorum() {
		return quorum;
	}

	public void setQuorum(Integer quorum) {
		this.quorum = quorum;
	}

	public boolean incluiProtocoloDesfavoravel() {
		return incluiProtocoloDesfavoravel;
	}

	public void setIncluiProtocoloDesfavoravel(boolean incluiProtocoloDesfavoravel) {
		this.incluiProtocoloDesfavoravel = incluiProtocoloDesfavoravel;
	}

	public boolean temId() {
		return this.id != null;
	}

	public boolean temPauta() {
		return this.pauta != null;
	}
	
	public boolean temExtraPauta() {
		return this.extraPauta != null;
	}
	
	public boolean temSumula() {
		return this.sumula != null;
	}
	
	public boolean temArquivoNaoAssinado() {
		return this.arquivoNaoAssinado != null;
	}

	public boolean temArquivoAssinado() {
		return this.arquivoAssinado != null;
	}
	
	public boolean estaAberta() {
		return this.getStatus() == StatusReuniaoSiacol.ABERTA;
	}

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

	public boolean heIniciarSessao() {
		return this.acao.equals("INICIAR");
	}

	public boolean heEncerrarSessao() {
		return this.acao.equals("ENCERRAR");
	}

	public List<Long> getIdsPautas() {
		return idsPautas;
	}

	public void setIdsPautas(List<Long> idsPautas) {
		this.idsPautas = idsPautas;
	}

	public boolean isStatusReuniaoPainel() {
		return statusReuniaoPainel;
	}

	public void setStatusReuniaoPainel(boolean statusReuniaoPainel) {
		this.statusReuniaoPainel = statusReuniaoPainel;
	}

	public boolean isStatusDestaqueParticipante() {
		return statusDestaqueParticipante;
	}

	public void setStatusDestaqueParticipante(boolean statusDestaqueParticipante) {
		this.statusDestaqueParticipante = statusDestaqueParticipante;
	}

	public boolean isStatusVistasParticipante() {
		return statusVistasParticipante;
	}

	public void setStatusVistasParticipante(boolean statusVistasParticipante) {
		this.statusVistasParticipante = statusVistasParticipante;
	}

	public String getStatusPainelPrincipal() {
		return statusPainelPrincipal;
	}

	public void setStatusPainelPrincipal(String statusPainelPrincipal) {
		this.statusPainelPrincipal = statusPainelPrincipal;
	}

	public String getStatusPainelConselheiro() {
		return statusPainelConselheiro;
	}

	public void setStatusPainelConselheiro(String statusPainelConselheiro) {
		this.statusPainelConselheiro = statusPainelConselheiro;
	}

	public boolean ehPlenaria() {
		return this.getDepartamento().getId().equals(new Long(11));
	}

	public boolean isManterPauta() {
		return manterPauta;
	}

	public void setManterPauta(boolean manterPauta) {
		this.manterPauta = manterPauta;
	}

	public boolean heCancelarSessao() {
		return this.acao.equals("CANCELAR");
	}

	public boolean hePausarSessao() {
		return this.acao.equals("PAUSAR");
	}

	public String getMotivoCancelamento() {
		return motivoCancelamento;
	}

	public void setMotivoCancelamento(String motivoCancelamento) {
		this.motivoCancelamento = motivoCancelamento;
	}

	public boolean isHouvePausa() {
		return houvePausa;
	}

	public void setHouvePausa(boolean houvePausa) {
		this.houvePausa = houvePausa;
	}

	public Long getParte() {
		return parte;
	}

	public void setParte(Long parte) {
		this.parte = parte;
	}

	public boolean temParte() {
		return this.parte != null;
	}

	public ArquivoDto getArquivoNaoAssinado() {
		return arquivoNaoAssinado;
	}

	public void setArquivoNaoAssinado(ArquivoDto arquivoNaoAssinado) {
		this.arquivoNaoAssinado = arquivoNaoAssinado;
	}

	public ArquivoDto getArquivoAssinado() {
		return arquivoAssinado;
	}

	public void setArquivoAssinado(ArquivoDto arquivoAssinado) {
		this.arquivoAssinado = arquivoAssinado;
	}

}
