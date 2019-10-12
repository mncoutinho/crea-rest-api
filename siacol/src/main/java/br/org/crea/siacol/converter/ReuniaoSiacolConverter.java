package br.org.crea.siacol.converter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.cadastro.domains.DepartamentoConverter;
import br.org.crea.commons.converter.cadastro.domains.DocumentoConverter;
import br.org.crea.commons.converter.commons.ArquivoConverter;
import br.org.crea.commons.dao.DocumentoDao;
import br.org.crea.commons.dao.cadastro.TipoDocumentoDao;
import br.org.crea.commons.models.cadastro.Departamento;
import br.org.crea.commons.models.cadastro.Documento;
import br.org.crea.commons.models.commons.Arquivo;
import br.org.crea.commons.models.siacol.ReuniaoSiacol;
import br.org.crea.commons.models.siacol.dtos.ReuniaoSiacolDto;
import br.org.crea.commons.models.siacol.enuns.TipoReuniaoEnum;
import br.org.crea.commons.util.DateUtils;

public class ReuniaoSiacolConverter {

	@Inject DocumentoConverter documentoConverter;
	
	@Inject ArquivoConverter arquivoConverter;
	
	@Inject TipoDocumentoDao tipoDocumentoDao; 
	
	@Inject DepartamentoConverter departametnoConverter;
	
	@Inject DocumentoDao documentoDao;
	

	public ReuniaoSiacol toModel(ReuniaoSiacolDto dto) {

		ReuniaoSiacol model = new ReuniaoSiacol();
		Departamento departamento = new Departamento();

		if (dto.temId()) {
			model.setId(dto.getId());
		}
		
		departamento.setId(new Long (dto.getDepartamento().getId()));
		model.setDepartamento(departamento);

		model.setDataReuniao(dto.getDataReuniao());
		model.setLocal(dto.getLocal());

		if (dto.temPauta()) {
			Documento pauta = new Documento();
			pauta.setId(dto.getPauta().getId());
			model.setPauta(pauta);
		}
		
		if (dto.temExtraPauta()) {
			Documento extraPauta = new Documento();
			extraPauta.setId(dto.getExtraPauta().getId());
			model.setExtraPauta(extraPauta);
		}
		
		if (dto.temSumula()) {
			Documento sumula = new Documento();
			sumula.setId(dto.getSumula().getId());
			model.setSumula(sumula);
		}
		
		if (dto.temArquivoNaoAssinado()) {
			Arquivo arquivoNaoAssinado = new Arquivo();
			arquivoNaoAssinado.setId(dto.getArquivoNaoAssinado().getId());
			model.setArquivoNaoAssinado(arquivoNaoAssinado);
		}
		
		if (dto.temArquivoAssinado()) {
			Arquivo arquivoAssinado = new Arquivo();
			arquivoAssinado.setId(dto.getArquivoAssinado().getId());
			model.setArquivoAssinado(arquivoAssinado);
		}

		model.setPrazo(dto.getPrazo());
		model.setQuorum(dto.getQuorum());
		model.setTipo(dto.getTipo());
		model.setDataReuniao(dto.getDataReuniao());
		model.setHoraInicio(dto.getHoraInicio());
		model.setHoraFim(dto.getHoraFim());
		model.setVirtual(dto.getVirtual());
		model.setStatus(dto.getStatus());
		model.setMotivoCancelamento(dto.getMotivoCancelamento());
		model.setHouvePausa(dto.isHouvePausa());
		model.setParte(dto.getParte());

		return model;
	}

	public ReuniaoSiacolDto toDto(ReuniaoSiacol model) {

		if (model != null) {
			ReuniaoSiacolDto dto = new ReuniaoSiacolDto();

			dto.setId(model.getId());
			dto.setDataReuniao(model.getDataReuniao());
			dto.setDataReuniaoFormatado(DateUtils.format(model.getDataReuniao(), DateUtils.DD_MM_YYYY));
			
			dto.setDepartamento(departametnoConverter.toDto(model.getDepartamento()));
			dto.setHoraInicio(model.getHoraInicio());
			dto.setHoraInicioFormatado(DateUtils.format(model.getHoraInicio(), DateUtils.HH_MM));
			dto.setHoraFim(model.getHoraFim());
			dto.setHoraFimFormatado(DateUtils.format(model.getHoraFim(), DateUtils.HH_MM));
			dto.setLocal(model.getLocal());
			
			List<Long> idsPautas = new ArrayList<Long>();

			if(model.temPauta()) {
				dto.setPauta(documentoConverter.toDto(documentoDao.getBy(model.getPauta().getId())));
				idsPautas.add(model.getPauta().getId());
			}
			
			if(model.temExtraPauta()){
				dto.setExtraPauta(documentoConverter.toDto(model.getExtraPauta()));
				idsPautas.add(model.getExtraPauta().getId());
			}
			if (model.temSumula()) {
				dto.setSumula(documentoConverter.toDto(model.getSumula()));
			}
			if (model.temArquivoNaoAssinado()) {
				dto.setArquivoNaoAssinado(arquivoConverter.toDto(model.getArquivoNaoAssinado()));
			}	
			if (model.temArquivoAssinado()) {
				dto.setArquivoAssinado(arquivoConverter.toDto(model.getArquivoAssinado()));
			}
			
			dto.setIdsPautas(idsPautas);
			dto.setPrazo(model.getPrazo());
			dto.setQuorum(model.getQuorum());
			dto.setTipo(model.getTipo());
			dto.setDescricaoTipo(TipoReuniaoEnum.getNomeBy(new Long(model.getTipo())));
			dto.setStatus(model.getStatus());
			dto.setVirtual(model.getVirtual());
			dto.setMotivoCancelamento(model.getMotivoCancelamento());
			dto.setHouvePausa(model.getHouvePausa());
			dto.setParte(model.getParte());
			
			if(!model.temStatusPainelConselheiro()) {
				dto.setStatusPainelConselheiro("principal");
			} else {
				dto.setStatusPainelConselheiro(model.getStatusPainel());
			}

			return dto;
		}else{
			return null;
		}
		
	}

	public List<ReuniaoSiacolDto> toListDto(List<ReuniaoSiacol> listModel) {
	
		List<ReuniaoSiacolDto> listDto = new ArrayList<ReuniaoSiacolDto>();
		
		for(ReuniaoSiacol r : listModel){
			listDto.add(toDto(r));
		}
		
		return listDto;
		
	}
	
	


}
