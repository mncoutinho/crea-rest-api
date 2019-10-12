package br.org.crea.siacol.converter;

import javax.inject.Inject;

import br.org.crea.commons.converter.commons.ArquivoConverter;
import br.org.crea.commons.models.siacol.ProtocoloSiacol;
import br.org.crea.commons.models.siacol.SiacolProtocoloExigencia;
import br.org.crea.commons.models.siacol.dtos.SiacolProtocoloExigenciaDto;

public class SiacolProtocoloExigenciaConverter {
	
	@Inject ArquivoConverter arquivoConverter;

	public SiacolProtocoloExigencia toModel(SiacolProtocoloExigenciaDto dto) {

		SiacolProtocoloExigencia model = new SiacolProtocoloExigencia();

		if (dto.temId()) {
			model.setId(dto.getId());
		}
		ProtocoloSiacol protocolo = new ProtocoloSiacol();
		protocolo.setId(dto.getProtocolo().getId());
		model.setProtocolo(protocolo);
		model.setMotivo(dto.getMotivo());
		model.setTipoContato(dto.getTipoContato());
		model.setPessoaContato(dto.getPessoaContato());
		model.setDataContato(dto.getDataContato());
		model.setDataInicio(dto.getDataInicio());
		model.setDataFim(dto.getDataFim());	
		if (dto.getArquivo() != null) {
			model.setArquivo(arquivoConverter.toModel(dto.getArquivo()));
		}

		return model;
	}

	
}
