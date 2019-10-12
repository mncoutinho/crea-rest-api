package br.org.crea.commons.models.siacol;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.org.crea.commons.models.corporativo.pessoa.Pessoa;
import br.org.crea.commons.models.siacol.enuns.VotoReuniaoEnum;


@Entity
@Table(name="SIACOL_VOTO_REUNIAO")
@SequenceGenerator(name="sqSiacolVotoReuniao",sequenceName="SQ_SIACOL_VOTO_REUNIAO",allocationSize = 1)
public class VotoReuniaoSiacol {
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sqSiacolVotoReuniao")
	private Long id;
	
	@OneToOne
	@JoinColumn(name="FK_REUNIAO")
	private ReuniaoSiacol reuniao;
	
	@OneToOne
	@JoinColumn(name="FK_PROTOCOLO")
	private ProtocoloSiacol protocolo;
	
	@OneToOne
	@JoinColumn(name="FK_PESSOA")
	private Pessoa pessoa;
	
	@Enumerated(EnumType.STRING)
	@Column(name="VOTO")
	private VotoReuniaoEnum voto;
	
	@Column(name="DS_JUSTIFICATIVA")
	private String justificativa;
	 
	@Column(name="DATA_VOTO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataVoto;
	
	@Column(name="DESTAQUE")
	private boolean destaque;

	@Column(name="DECLARACAO")
	private boolean declaracao;
	
	@Column(name="ITEM")
	private String item;
	
	@Column(name="DS_ITEM")
	private String descricaoItem;
	
	@OneToOne
	@JoinColumn(name="FK_ITEM_PAUTA")
	private RlDocumentoProtocoloSiacol itemPauta;

	@Column(name="RESPOSTA_ENQUETE")
	private Long idRespostaEnquete;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ReuniaoSiacol getReuniao() {
		return reuniao;
	}

	public void setReuniao(ReuniaoSiacol reuniao) {
		this.reuniao = reuniao;
	}

	public ProtocoloSiacol getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(ProtocoloSiacol protocolo) {
		this.protocolo = protocolo;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}


	public VotoReuniaoEnum getVoto() {
		return voto;
	}

	public void setVoto(VotoReuniaoEnum voto) {
		this.voto = voto;
	}

	public String getJustificativa() {
		return justificativa;
	}

	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

	public Date getDataVoto() {
		return dataVoto;
	}

	public void setDataVoto(Date dataVoto) {
		this.dataVoto = dataVoto;
	}

	public boolean isDestaque() {
		return destaque;
	}

	public void setDestaque(boolean destaque) {
		this.destaque = destaque;
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

	public RlDocumentoProtocoloSiacol getItemPauta() {
		return itemPauta;
	}

	public void setItemPauta(RlDocumentoProtocoloSiacol itemPauta) {
		this.itemPauta = itemPauta;
	}

	public Boolean jaExiste() {
		return this != null ? true : false;
	}

	public boolean isDeclaracao() {
		return declaracao;
	}

	public void setDeclaracao(boolean declaracao) {
		this.declaracao = declaracao;
	}

	public Long getIdRespostaEnquete() {
		return idRespostaEnquete;
	}

	public void setIdRespostaEnquete(Long idRespostaEnquete) {
		this.idRespostaEnquete = idRespostaEnquete;
	}

	public boolean ehAbstencao() {
		return this.voto.equals(VotoReuniaoEnum.A);
	}

	


}


