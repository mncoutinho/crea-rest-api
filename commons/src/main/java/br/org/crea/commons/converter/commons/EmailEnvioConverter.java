package br.org.crea.commons.converter.commons;

import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;

import org.codehaus.jackson.map.ObjectMapper;

import br.org.crea.commons.dao.cadastro.EmailDao;
import br.org.crea.commons.models.commons.DestinatarioEmailDto;
import br.org.crea.commons.models.commons.EmailEnvio;
import br.org.crea.commons.models.commons.EventoEmail;
import br.org.crea.commons.models.commons.dtos.DomainGenericDto;
import br.org.crea.commons.models.commons.dtos.EmailEnvioDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

public class EmailEnvioConverter {
	
	@Inject	private HttpClientGoApi httpGoApi;
	
	@Inject private EmailDao emailDao;

	public EmailEnvio toModel(EmailEnvioDto dto) {

		EmailEnvio model = new EmailEnvio();
		ObjectMapper mapper = new ObjectMapper();

		try {
			
			if (dto.getId() != null) {
				model.setId(dto.getId());
			}
			
			model.setEmissor(dto.getEmissor());

			model.setDestinatarios(mapper.writeValueAsString(dto.getDestinatarios()));
			model.setDestinatariosCC(mapper.writeValueAsString(dto.getDestinatariosCC()));
			model.setDestinatariosCCO(mapper.writeValueAsString(dto.getDestinatariosCCO()));

			model.setAssunto(dto.getAssunto());
			model.setMensagem(dto.getMensagem());
			model.setDataUltimoEnvio(dto.getDataUltimoEnvio());
			model.setStatus(dto.getStatus());

			if (dto.getEvento() != null) {
				EventoEmail eventoEmail = new EventoEmail();
				eventoEmail = emailDao.getEventoById(dto.getEvento().getId());
				model.setEventoEmail(eventoEmail); 
			}
		} catch (Exception e) {
			httpGoApi.geraLog("EmailEnvio || toModel", StringUtil.convertObjectToJson(dto), e);
		}

		return model;
	}

	public EmailEnvioDto toDto(EmailEnvio model) {

		EmailEnvioDto dto = new EmailEnvioDto();
		ObjectMapper mapper = new ObjectMapper();

		try {
			dto.setId(model.getId());
			dto.setEmissor(model.getEmissor());

			if (model.getDestinatarios() != null) {
				dto.setDestinatarios(new ArrayList<DestinatarioEmailDto>(Arrays.asList(mapper.readValue(model.getDestinatarios(), DestinatarioEmailDto[].class))));
				dto.setDestinatariosCC(new ArrayList<DestinatarioEmailDto>(Arrays.asList(mapper.readValue(model.getDestinatariosCC(), DestinatarioEmailDto[].class))));
				dto.setDestinatariosCCO(new ArrayList<DestinatarioEmailDto>(Arrays.asList(mapper.readValue(model.getDestinatariosCCO(), DestinatarioEmailDto[].class))));
			}

			dto.setAssunto(model.getAssunto());
			dto.setMensagem(model.getMensagem());
			dto.setDataUltimoEnvio(model.getDataUltimoEnvio());
			dto.setStatus(model.getStatus());

			if (model.getEventoEmail() != null) {
				DomainGenericDto eventoEmail = new DomainGenericDto();
				eventoEmail.setId(model.getEventoEmail().getId());
				dto.setEvento(eventoEmail);
			}

		} catch (Exception e) {
			httpGoApi.geraLog("EmailEnvioDto || toDto", StringUtil.convertObjectToJson(model), e);
		}

		return dto;
	}

}
