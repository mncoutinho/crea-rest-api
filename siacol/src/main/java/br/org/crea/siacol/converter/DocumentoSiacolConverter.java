package br.org.crea.siacol.converter;

import java.util.ArrayList;
import java.util.List;

import br.org.crea.commons.models.cadastro.Documento;
import br.org.crea.commons.models.siacol.dtos.documento.DecisaoDto;
import br.org.crea.commons.models.siacol.dtos.documento.RelatorioVotoDto;
import br.org.crea.commons.models.siacol.dtos.documento.SugestaoRelatorioVotoDto;

import com.google.gson.Gson;

public class DocumentoSiacolConverter {
	
	private Gson gson = null;
	
	public RelatorioVotoDto toRelatorioVotoDto(Documento model) {
		RelatorioVotoDto relatorioVotoDto = new RelatorioVotoDto();
		try {
			gson = new Gson();
			relatorioVotoDto = gson.fromJson(model.getRascunho(), RelatorioVotoDto.class);
			relatorioVotoDto.setDataCriacao(model.getDataCriacao().getTime());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return relatorioVotoDto;
		
	}
	
	public List<RelatorioVotoDto> toListRelatorioVotoDto(List<Documento> listModel) {
		List<RelatorioVotoDto> listRelatorioVoto = new ArrayList<RelatorioVotoDto>();
		
		for (Documento documento : listModel) {
			listRelatorioVoto.add(toRelatorioVotoDto(documento));
		}
		
		return listRelatorioVoto;
	}
	
	public DecisaoDto toDecisaoDto(Documento model) {
		DecisaoDto decisaoDto = new DecisaoDto();
		gson = new Gson();
		decisaoDto = gson.fromJson(model.getRascunho(), DecisaoDto.class);
		decisaoDto.setDataCriacao(model.getDataCriacao().getTime());
		decisaoDto.setNumeroDocumento(model.getNumeroDocumento() != null ? model.getNumeroDocumento() : "");
		return decisaoDto;
	}
	
	public List<DecisaoDto> toListDecisaoDto(List<Documento> listModel) {
		List<DecisaoDto> listDecisao = new ArrayList<DecisaoDto>();
		
		for (Documento documento : listModel) {
			listDecisao.add(toDecisaoDto((documento)));
		}
		
		return listDecisao;
	}
	
	public SugestaoRelatorioVotoDto toSugestaoRelatorioVotoDto(Documento model) {
		
		try {
			SugestaoRelatorioVotoDto sugestaoRelatorioVotoDto = new SugestaoRelatorioVotoDto();
			gson = new Gson();
			sugestaoRelatorioVotoDto = gson.fromJson(model.getRascunho(), SugestaoRelatorioVotoDto.class);
			sugestaoRelatorioVotoDto.setDataCriacao(model.getDataCriacao().getTime());
			return sugestaoRelatorioVotoDto;
		} catch (Exception e) {
			System.out.println(e);
		}
		gson = new Gson();
		return gson.fromJson(model.getRascunho(), SugestaoRelatorioVotoDto.class);
	}
	
	public List<SugestaoRelatorioVotoDto> toListSugestaoRelatorioVotoDto(List<Documento> listModel) {
		List<SugestaoRelatorioVotoDto> listRelatorioVoto = new ArrayList<SugestaoRelatorioVotoDto>();
		
		for (Documento documento : listModel) {
			listRelatorioVoto.add(toSugestaoRelatorioVotoDto(documento));
		}
		
		return listRelatorioVoto;
	}


}
