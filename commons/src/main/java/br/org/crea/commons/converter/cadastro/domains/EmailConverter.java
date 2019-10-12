package br.org.crea.commons.converter.cadastro.domains;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.commons.EmailEnvioConverter;
import br.org.crea.commons.dao.DocumentoDao;
import br.org.crea.commons.models.cadastro.EmailPessoa;
import br.org.crea.commons.models.cadastro.dtos.EmailDto;
import br.org.crea.commons.models.cadastro.dtos.RlEmailReuniaoSiacolDto;
import br.org.crea.commons.models.siacol.ReuniaoSiacol;
import br.org.crea.commons.models.siacol.RlEmailReuniaoSiacol;
import br.org.crea.commons.models.siacol.dtos.ReuniaoSiacolDto;
import br.org.crea.commons.models.siacol.enuns.TipoReuniaoEnum;
import br.org.crea.commons.util.DateUtils;

public class EmailConverter {
	
	@Inject EmailEnvioConverter emailEnvioConverter;
	@Inject DepartamentoConverter departamentoConverter;
	@Inject DocumentoConverter documentoConverter;
	@Inject DocumentoDao documentoDao;
	
	public List<EmailDto> toListEmailDto(List<EmailPessoa> listaModel){
		
		List<EmailDto> listaDto = new ArrayList<EmailDto>();
		
		listaModel.forEach(email -> {
			listaDto.add(toEmailDto(email));
		});
		
		return listaDto;
	}
	
	public EmailDto toEmailDto(EmailPessoa model){
		
		EmailDto dto = new EmailDto();
		dto.setIdPessoa(model.getPessoa().getId());
		dto.setDescricao(model.getDescricao());
		
		return dto;
	}

	public RlEmailReuniaoSiacolDto toRlEmailReuniaoDto(RlEmailReuniaoSiacol rlEmailReuniaoSiacol) {
		
		if (rlEmailReuniaoSiacol != null) {
			RlEmailReuniaoSiacolDto dto = new RlEmailReuniaoSiacolDto();
			dto.setId(rlEmailReuniaoSiacol.getId());
			dto.setReuniao(populaReuniao(rlEmailReuniaoSiacol.getReuniao()));
			dto.setEmailEnvio(emailEnvioConverter.toDto(rlEmailReuniaoSiacol.getEmailEnvio()));
			return dto;
		}else{
			return null;
		}
		

	}

	private ReuniaoSiacolDto populaReuniao(ReuniaoSiacol model) {

			ReuniaoSiacolDto dto = new ReuniaoSiacolDto();

			dto.setId(model.getId());
			dto.setDataReuniao(model.getDataReuniao());
			dto.setDataReuniaoFormatado(DateUtils.format(model.getDataReuniao(), DateUtils.DD_MM_YYYY));
			
			dto.setDepartamento(departamentoConverter.toDto(model.getDepartamento()));
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
			if (model.temExtraPauta()) {
				dto.setExtraPauta(documentoConverter.toDto(model.getExtraPauta()));
			}
			
			
			dto.setIdsPautas(idsPautas);
			dto.setPrazo(model.getPrazo());
			dto.setQuorum(model.getQuorum());
			dto.setTipo(model.getTipo());
			dto.setDescricaoTipo(TipoReuniaoEnum.getNomeBy(new Long(model.getTipo())));
			dto.setStatus(model.getStatus());
			dto.setVirtual(model.getVirtual());

			return dto;
	}
}
