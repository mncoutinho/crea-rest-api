package br.org.crea.commons.models.siacol.dtos;

import br.org.crea.commons.models.cadastro.enuns.TipoEventoAuditoria;
import br.org.crea.commons.models.commons.dtos.DocumentoGenericDto;
import br.org.crea.commons.models.commons.dtos.DomainGenericDto;
import br.org.crea.commons.models.siacol.enuns.ResultadoVotacaoSiacolEnum;

public class ItemPautaDto {
	
	private int __index;
	
	private Long id;
	
	private Long idDocumento;
	
	private DocumentoGenericDto pauta;

	private Long idProtocolo;
	
	private ProtocoloSiacolDto protocolo;
	
	private String item;
	
	private String descricaoItem;
	
	private String obsReuniao;
	
	private String obsCoordenador;
	
	private String obsSumula;
	
	private String pergunta;
	
	private Long idPessoaDestaque;
	
	private ParticipanteReuniaoSiacolDto pessoaDestaque;

	private Long idPessoaVista;
	
	private ParticipanteReuniaoSiacolDto pessoaVista;

	private DomainGenericDto status;
	
	private boolean destaque;
	
	private boolean vista;
	
	private boolean solicitacaoVista;
	
	private boolean emVotacao;
	
	private boolean extraPauta;
	
	private boolean temEnquete;
	
	private boolean temDeclaracaoVoto;
	
	private String dataReuniao;
	
	private Long idPessoaMinerva;
	
	private Long totalVotosSim;
	
	private Long totalVotosNao;
	
	private Long totalVotosAbstencao;
	
	private ParticipanteReuniaoSiacolDto pessoaMinerva;
	
	private Object enquete;
	
	private EnqueteDto enqueteDto;
	
	private String respostaEnquete;
	
	private String resultadoEnquete;
	
	private String numeroDocumento;
	
	private String nomePessoa;
	
	private ResultadoVotacaoSiacolEnum resultado;
	
	private Boolean urgencia;
	
	private TipoEventoAuditoria eventoAuditoria;
		
	public int get__index() {
		return __index;
	}

	public void set__index(int __index) {
		this.__index = __index;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(Long idDocumento) {
		this.idDocumento = idDocumento;
	}

	public DocumentoGenericDto getPauta() {
		return pauta;
	}

	public void setPauta(DocumentoGenericDto pauta) {
		this.pauta = pauta;
	}

	public Long getIdProtocolo() {
		return idProtocolo;
	}

	public void setIdProtocolo(Long idProtocolo) {
		this.idProtocolo = idProtocolo;
	}

	public ProtocoloSiacolDto getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(ProtocoloSiacolDto protocolo) {
		this.protocolo = protocolo;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getDescricaoItem() {
		return descricaoItem;
	}

	public void setDescricaoItem(String descricaoItem) {
		this.descricaoItem = descricaoItem;
	}
	
	public Long getIdPessoaDestaque() {
		return idPessoaDestaque;
	}

	public void setIdPessoaDestaque(Long idPessoaDestaque) {
		this.idPessoaDestaque = idPessoaDestaque;
	}

	public Long getIdPessoaVista() {
		return idPessoaVista;
	}

	public void setIdPessoaVista(Long idPessoaVista) {
		this.idPessoaVista = idPessoaVista;
	}

	public DomainGenericDto getStatus() {
		return status;
	}

	public void setStatus(DomainGenericDto status) {
		this.status = status;
	}
	
	public String getObsReuniao() {
		return obsReuniao;
	}

	public void setObsReuniao(String obsReuniao) {
		this.obsReuniao = obsReuniao;
	}

	public String getObsCoordenador() {
		return obsCoordenador;
	}

	public void setObsCoordenador(String obsCoordenador) {
		this.obsCoordenador = obsCoordenador;
	}

	public String getObsSumula() {
		return obsSumula;
	}

	public void setObsSumula(String obsSumula) {
		this.obsSumula = obsSumula;
	}

	public boolean temId() {
		return this.id != null;
	}
	
	public boolean temDocumento() {
		return this.idDocumento != null;
	}
	
	public boolean temProtocolo() {
		return this.idProtocolo != null;
	}
	
	public boolean temStatus() {
		return this.status != null;
	}

	public boolean isDestaque() {
		return destaque;
	}

	public void setDestaque(boolean destaque) {
		this.destaque = destaque;
	}

	public boolean isVista() {
		return vista;
	}

	public void setVista(boolean vista) {
		this.vista = vista;
	}

	public boolean isSolicitacaoVista() {
		return solicitacaoVista;
	}
	
	public void setSolicitacaoVista(boolean solicitacaoVista) {
		this.solicitacaoVista = solicitacaoVista;
	}

	public boolean isEmVotacao() {
		return emVotacao;
	}

	public void setEmVotacao(boolean emVotacao) {
		this.emVotacao = emVotacao;
	}

	public String getPergunta() {
		return pergunta;
	}

	public void setPergunta(String pergunta) {
		this.pergunta = pergunta;
	}

	public boolean isExtraPauta() {
		return extraPauta;
	}

	public void setExtraPauta(boolean extraPauta) {
		this.extraPauta = extraPauta;
	}

	public ParticipanteReuniaoSiacolDto getPessoaDestaque() {
		return pessoaDestaque;
	}

	public void setPessoaDestaque(ParticipanteReuniaoSiacolDto pessoaDestaque) {
		this.pessoaDestaque = pessoaDestaque;
	}

	public ParticipanteReuniaoSiacolDto getPessoaVista() {
		return pessoaVista;
	}

	public void setPessoaVista(ParticipanteReuniaoSiacolDto pessoaVista) {
		this.pessoaVista = pessoaVista;
	}

	public String getDataReuniao() {
		return dataReuniao;
	}

	public void setDataReuniao(String dataReuniao) {
		this.dataReuniao = dataReuniao;
	}

	public Long getIdPessoaMinerva() {
		return idPessoaMinerva;
	}

	public void setIdPessoaMinerva(Long idPessoaMinerva) {
		this.idPessoaMinerva = idPessoaMinerva;
	}

	public Long getTotalVotosSim() {
		return totalVotosSim;
	}

	public void setTotalVotosSim(Long totalVotosSim) {
		this.totalVotosSim = totalVotosSim;
	}

	public Long getTotalVotosNao() {
		return totalVotosNao;
	}

	public void setTotalVotosNao(Long totalVotosNao) {
		this.totalVotosNao = totalVotosNao;
	}

	public Long getTotalVotosAbstencao() {
		return totalVotosAbstencao;
	}

	public void setTotalVotosAbstencao(Long totalVotosAbstencao) {
		this.totalVotosAbstencao = totalVotosAbstencao;
	}

	public Object getEnquete() {
		return enquete;
	}

	public void setEnquete(Object enquete) {
		this.enquete = enquete;
	}

	public String getRespostaEnquete() {
		return respostaEnquete;
	}

	public void setRespostaEnquete(String respostaEnquete) {
		this.respostaEnquete = respostaEnquete;
	}

	public String getResultadoEnquete() {
		return resultadoEnquete;
	}

	public void setResultadoEnquete(String resultadoEnquete) {
		this.resultadoEnquete = resultadoEnquete;
	}
	
	public boolean isTemEnquete() {
		return temEnquete;
	}

	public void setTemEnquete(boolean temEnquete) {
		this.temEnquete = temEnquete;
	}
	
	public boolean isTemDeclaracaoVoto() {
		return temDeclaracaoVoto;
	}

	public void setTemDeclaracaoVoto(boolean temDeclaracaoVoto) {
		this.temDeclaracaoVoto = temDeclaracaoVoto;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public ParticipanteReuniaoSiacolDto getPessoaMinerva() {
		return pessoaMinerva;
	}

	public void setPessoaMinerva(ParticipanteReuniaoSiacolDto pessoaMinerva) {
		this.pessoaMinerva = pessoaMinerva;
	}

	public String getNomePessoa() {
		return nomePessoa;
	}

	public void setNomePessoa(String nomePessoa) {
		this.nomePessoa = nomePessoa;
	}

	public ResultadoVotacaoSiacolEnum getResultado() {
		return resultado;
	}

	public void setResultado(ResultadoVotacaoSiacolEnum resultado) {
		this.resultado = resultado;
	}

	public EnqueteDto getEnqueteDto() {
		return enqueteDto;
	}

	public void setEnqueteDto(EnqueteDto enqueteDto) {
		this.enqueteDto = enqueteDto;
	}

	public Boolean getUrgencia() {
		return urgencia;
	}

	public void setUrgencia(Boolean urgencia) {
		this.urgencia = urgencia;
	}

	public TipoEventoAuditoria getEventoAuditoria() {
		return eventoAuditoria;
	}

	public void setEventoAuditoria(TipoEventoAuditoria eventoAuditoria) {
		this.eventoAuditoria = eventoAuditoria;
	}
	
	public boolean temEventoAuditoria() {
		return this.eventoAuditoria != null;
	}
}
