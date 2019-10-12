package br.org.crea.commons.models.siacol;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.org.crea.commons.models.cadastro.Documento;
import br.org.crea.commons.models.siacol.enuns.ResultadoVotacaoSiacolEnum;
import br.org.crea.commons.models.siacol.enuns.VotoReuniaoEnum;

@Entity
@Table(name = "SIACOL_DOCUMENTO_PROTOCOLO")
@SequenceGenerator(name = "sqSiacolRlDocProtocolo", sequenceName = "SQ_RL_DOCUMENTO_PROTOCOLO", initialValue = 1, allocationSize = 1)
public class RlDocumentoProtocoloSiacol implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sqSiacolRlDocProtocolo")
	private Long id;

	@OneToOne
	@JoinColumn(name = "FK_DOCUMENTO")
	private Documento documento;

	@OneToOne
	@JoinColumn(name = "FK_PROTOCOLO")
	private ProtocoloSiacol protocolo;

	@Column(name = "FK_PESSOA_DESTAQUE")
	private Long idPessoaDestaque;

	@Column(name = "FK_PESSOA_VISTA")
	private Long idPessoaVista;
	
	@Column(name = "FK_PESSOA_MINERVA")
	private Long idPessoaMinerva;

	@Column(name = "ITEM")
	private String item;

	@Column(name = "DS_ITEM")
	private String descricaoItem;

	@OneToOne
	@JoinColumn(name = "FK_STATUS")
	private StatusItemPauta status;

	@Column(name = "TEM_EXTRA_PAUTA")
	private boolean temExtraPauta;
	
	@Column(name = "TEM_DECLARACAO_VOTO")
	private boolean temDeclaracaoVoto;

	@Column(name = "EM_VOTACAO")
	private boolean emVotacao;
	
	@Column(name = "OBS_REUNIAO")
	private String obsReuniao;
	
	@Column(name = "OBS_COORDENADOR")
	private String obsCoordenador;
	
	@Column(name = "PERGUNTA")
	private String pergunta;
	
	@Column(name = "SOLICITACAO_VISTA")
	private boolean solicitacaoVista;	
	
	@Lob
	@Column(name="ENQUETE")
	private String enquete;
	
	@Column(name="RESULTADO_ENQUETE")
	private String resultadoEnquete;

	@Column(name = "TOTAL_VOTOS_SIM")
	private Long totalVotosSim;
	
	@Column(name = "TOTAL_VOTOS_NAO")
	private Long totalVotosNao;
	
	@Column(name = "TOTAL_VOTOS_ABSTENCAO")
	private Long totalVotosAbstencao;
	
	@Column(name = "TEM_ENQUETE")
	private boolean temEnquete;
	
	@Column(name = "NUMERO_DOCUMENTO")
	private String numeroDocumento;	
	
	@Enumerated(EnumType.STRING)
	@Column(name = "RESULTADO")
	private ResultadoVotacaoSiacolEnum resultado;	
	
	@Lob
	@Column(name = "OBS_SUMULA")
	private String obsSumula;
	
	@Column(name = "URGENCIA")
	private Boolean urgencia;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	public ProtocoloSiacol getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(ProtocoloSiacol protocolo) {
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

	public StatusItemPauta getStatus() {
		return status;
	}

	public void setStatus(StatusItemPauta status) {
		this.status = status;
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


	public boolean temDocumento() {
		return this.documento != null;
	}

	public boolean temProtocolo() {
		return this.protocolo != null;
	}

	public boolean temId() {
		return this.id != null;
	}

	public boolean temStatus() {
		return this.status != null;
	}

	public boolean temDestaque() {
		return this.status.getId() == 3;
	}

	public boolean temVista() {
		return this.status.getId() == 4;
	}

	public boolean getTemExtraPauta() {
		return temExtraPauta;
	}

	public void setTemExtraPauta(boolean extraPauta) {
		this.temExtraPauta = extraPauta;
	}

	public boolean isTemDeclaracaoVoto() {
		return temDeclaracaoVoto;
	}

	public void setTemDeclaracaoVoto(boolean temDeclaracaoVoto) {
		this.temDeclaracaoVoto = temDeclaracaoVoto;
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

	public boolean isSolicitacaoVista() {
		return solicitacaoVista;
	}

	public void setSolicitacaoVista(boolean solicitacaoVista) {
		this.solicitacaoVista = solicitacaoVista;
	}

	public boolean temPessoaDestaque() {
		return this.idPessoaDestaque != null;
	}

	public boolean temPessoaVista() {
		return this.idPessoaVista != null;
	}

	public boolean foiVotado() {
		return this.getStatus().getId().equals(new Long(1));
	}

	public Long getIdPessoaMinerva() {
		return idPessoaMinerva;
	}

	public void setIdPessoaMinerva(Long idPessoaMinerva) {
		this.idPessoaMinerva = idPessoaMinerva;
	}

	public String getEnquete() {
		return enquete;
	}

	public void setEnquete(String enquete) {
		this.enquete = enquete;
	}

	public String getResultadoEnquete() {
		return resultadoEnquete;
	}

	public void setResultadoEnquete(String resultadoEnquete) {
		this.resultadoEnquete = resultadoEnquete;
	}
	

	public void setTemEnquete(boolean temEnquete) {
		this.temEnquete = temEnquete;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public boolean isTemEnquete() {
		return temEnquete;
	}

	public boolean houveEmpate() {
		return this.totalVotosSim == this.totalVotosNao;
	}

	public boolean maioriaDosVotosEhSim() {
		return this.totalVotosSim > this.totalVotosNao;
	}

	public void desempate(VotoReuniaoEnum votoDeMinerva) {
		if (votoDeMinerva == VotoReuniaoEnum.S) {
			this.totalVotosSim++;
		} else if (votoDeMinerva == VotoReuniaoEnum.N) {
			this.totalVotosNao++;
		}
	}

	public boolean temPessoaMinerva() {
		return this.idPessoaMinerva != null;
	}

	public ResultadoVotacaoSiacolEnum getResultado() {
		return resultado;
	}

	public void setResultado(ResultadoVotacaoSiacolEnum resultado) {
		this.resultado = resultado;
	}

	public String getObsSumula() {
		return obsSumula;
	}

	public void setObsSumula(String obsSumula) {
		this.obsSumula = obsSumula;
	}

	public Boolean getUrgencia() {
		return urgencia;
	}

	public void setUrgencia(Boolean urgencia) {
		this.urgencia = urgencia;
	}
}
