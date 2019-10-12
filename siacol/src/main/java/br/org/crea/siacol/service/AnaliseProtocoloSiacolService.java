package br.org.crea.siacol.service;

import javax.inject.Inject;

import br.org.crea.commons.dao.protocolo.ProtocoloDao;
import br.org.crea.commons.models.commons.dtos.ProtocoloDto;
import br.org.crea.commons.models.siacol.dtos.AnaliseProtocoloSiacolDto;
import br.org.crea.commons.service.protocolo.ProtocoloService;
import br.org.crea.siacol.builder.AnaliseAutoInfracaoBuilder;
import br.org.crea.siacol.builder.AnaliseEmpresaBuilder;


public class AnaliseProtocoloSiacolService {
	
	@Inject AnaliseEmpresaBuilder analiseEmpresaBuilder;
	
	@Inject AnaliseAutoInfracaoBuilder analiseAutoInfracaoBuilder;
	
	@Inject ProtocoloDao protocoloDao;
	
	@Inject ProtocoloService protocoloService;


	public AnaliseProtocoloSiacolDto analisar(ProtocoloDto dto) {
		AnaliseProtocoloSiacolDto analiseDto = null;
			
		switch (dto.getTipoProtocolo()) {
		case EMPRESA:
			analiseDto = analiseEmpresaBuilder.constroiAnalise(dto).build();
			break;
		case AUTOINFRACAO:
			analiseDto = analiseAutoInfracaoBuilder.constroiAnalise(dto).build();
			break;
		default:
			break;
		}
		return analiseDto;
	}
	
	public boolean protocoloEmAnalisePossuiRequerimento(Long numeroProtocolo) {
		return protocoloDao.protocoloEmAnalisePossuiRequerimento(numeroProtocolo);
	}
	
}
