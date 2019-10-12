package br.org.crea.commons.models.siacol.enuns;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum TipoDocumentoSiacolEnum {
	
	DECISAO_DELIBERACAO(new Long(1102), "Decisão Deliberação"),
	DESPACHO_PROVISORIO(new Long(1110), "Despacho Provisório"),
	DECISAO_AD_REFERENDUM(new Long(1112), "Decisão AD Referendum");
	
	private final Long id;
	
	private final String descricao;
	
	private TipoDocumentoSiacolEnum(Long id, String descricao){
		this.id = id;
		this.descricao = descricao;
	}

	public Long getId() {
		return id;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public static Long getIdBy(TipoDocumentoSiacolEnum tipo){
		
		for(TipoDocumentoSiacolEnum s : TipoDocumentoSiacolEnum.values()){
			if(s.equals(tipo)){
				return s.id;
			}
		}
		
		return null;
	}
	
	public static String getNomeBy(Long id){
		
		for(TipoDocumentoSiacolEnum s : TipoDocumentoSiacolEnum.values()){
			if(s.id.equals(id)){
				return s.descricao;
			}
		}
		
		return null;
	}

	public static List<String> getAll () {
		TipoDocumentoSiacolEnum[] vetor = TipoDocumentoSiacolEnum.class.getEnumConstants();
		List<String> listaStatus = new ArrayList<String>();

		for (TipoDocumentoSiacolEnum statusEnum : vetor) {
			listaStatus.add(statusEnum.toString());
		}
		Collections.sort(listaStatus);
		
		return listaStatus;
	}

}
