package br.org.crea.commons.models.siacol.dtos;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonPropertyOrder;

import br.org.crea.commons.models.corporativo.dtos.PessoaDto;
import br.org.crea.commons.models.siacol.enuns.VotoReuniaoEnum;

@JsonPropertyOrder({ "id", "dataVoto", "dataVotoFormatado", "voto", "justificativa", "destaque", "declaracao",  "reuniao", "protocolo", "pessoa", "item", "descricaoItem", "itens" })
public class VotoReuniaoSiacolDto {
	
	private Long id;
	
	private Date dataVoto;

	private String dataVotoFormatado;

	private VotoReuniaoEnum voto;

	private boolean destaque;

	private boolean declaracao;
	
	private boolean vista;

	private String justificativa;

	private ReuniaoSiacolDto reuniao;
	
	private ProtocoloSiacolDto protocolo;
	
	private PessoaDto pessoa;
	
	private String item;

	private String descricaoItem;
	
	private Long idRlItemPauta;
	
	private List<ItemPautaDto> itens;
	
	private Boolean minerva;
	
	private Long idRespostaEnquete;
	
	public Long getIdRlItemPauta() {
		return idRlItemPauta;
	}

	public void setIdRlItemPauta(Long idRlItemPauta) {
		this.idRlItemPauta = idRlItemPauta;
	}
	
	public List<ItemPautaDto> getItens() {
		return itens;
	}

	public void setItens(List<ItemPautaDto> itens) {
		this.itens = itens;
	}

	public boolean isDestaque() {
		return destaque;
	}

	public void setDestaque(boolean destaque) {
		this.destaque = destaque;
	}
	
	public boolean temId() {
		return this.id != null ? true : false;
	}

	public boolean temPessoa() {
		return this.pessoa != null ? true : false;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataVoto() {
		return dataVoto;
	}

	public void setDataVoto(Date dataVoto) {
		this.dataVoto = dataVoto;
	}

	public String getDataVotoFormatado() {
		return dataVotoFormatado;
	}

	public void setDataVotoFormatado(String dataVotoFormatado) {
		this.dataVotoFormatado = dataVotoFormatado;
	}

	public VotoReuniaoEnum getVoto() {
		return voto;
	}

	public void setVoto(VotoReuniaoEnum voto) {
		this.voto = voto;
	}

	public boolean isVista() {
		return vista;
	}

	public void setVista(boolean vista) {
		this.vista = vista;
	}

	public String getJustificativa() {
		return justificativa;
	}

	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

	public ReuniaoSiacolDto getReuniao() {
		return reuniao;
	}

	public void setReuniao(ReuniaoSiacolDto reuniao) {
		this.reuniao = reuniao;
	}

	public ProtocoloSiacolDto getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(ProtocoloSiacolDto protocolo) {
		this.protocolo = protocolo;
	}

	public PessoaDto getPessoa() {
		return pessoa;
	}

	public void setPessoa(PessoaDto pessoa) {
		this.pessoa = pessoa;
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

	public Long primeiroIdItemPautaDaLista() {
		return this.itens.get(0).getId();
	}

	public boolean isDeclaracao() {
		return declaracao;
	}

	public void setDeclaracao(boolean declaracao) {
		this.declaracao = declaracao;
	}

	public Boolean getMinerva() {
		return minerva;
	}

	public void setMinerva(Boolean minerva) {
		this.minerva = minerva;
	}

	public Long getIdRespostaEnquete() {
		return idRespostaEnquete;
	}

	public void setIdRespostaEnquete(Long idRespostaEnquete) {
		this.idRespostaEnquete = idRespostaEnquete;
	}

	

	
}
