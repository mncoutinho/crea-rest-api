package br.org.crea.commons.models.siacol.dtos;

import br.org.crea.commons.models.siacol.enuns.VotoReuniaoEnum;

public class PesquisaVotoReuniaoDto {
	
	private Long idPessoa;
	
	private Long idProtocolo;
	
	private Long idReuniao;
	
	private Long idPauta;
	
	private Long idItemPauta;
	
	private VotoReuniaoEnum voto;

	public Long getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(Long idPessoa) {
		this.idPessoa = idPessoa;
	}

	public Long getIdProtocolo() {
		return idProtocolo;
	}

	public void setIdProtocolo(Long idProtocolo) {
		this.idProtocolo = idProtocolo;
	}

	public Long getIdReuniao() {
		return idReuniao;
	}

	public void setIdReuniao(Long idReuniao) {
		this.idReuniao = idReuniao;
	}

	public VotoReuniaoEnum getVoto() {
		return voto;
	}

	public void setVoto(VotoReuniaoEnum voto) {
		this.voto = voto;
	}

	public Long getIdPauta() {
		return idPauta;
	}

	public void setIdPauta(Long idPauta) {
		this.idPauta = idPauta;
	}
	
	
	public Long getIdItemPauta() {
		return idItemPauta;
	}

	public boolean temIdItemPauta() {
		return this.idItemPauta != null;
	}
	
	public void setIdItemPauta(Long idItemPauta) {
		this.idItemPauta = idItemPauta;
	}

	public boolean temProtocolo(){
		return this.idProtocolo != null;
	}

	public boolean temReuniao(){
		return this.idReuniao != null;
	}

	public boolean temPessoa(){
		return this.idPessoa != null;
	}
	
	public boolean temVoto() {
		return this.voto != null;
	}

}
