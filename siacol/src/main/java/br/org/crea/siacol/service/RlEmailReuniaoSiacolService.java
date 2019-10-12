package br.org.crea.siacol.service;

import javax.inject.Inject;

import br.org.crea.commons.converter.cadastro.domains.EmailConverter;
import br.org.crea.commons.dao.siacol.RlEmailReuniaoSiacolDao;
import br.org.crea.commons.models.cadastro.dtos.RlEmailReuniaoSiacolDto;
import br.org.crea.commons.models.commons.EmailEnvio;
import br.org.crea.commons.models.commons.dtos.GenericDto;
import br.org.crea.commons.models.siacol.ReuniaoSiacol;
import br.org.crea.commons.models.siacol.RlEmailReuniaoSiacol;

public class RlEmailReuniaoSiacolService {
	
	@Inject	RlEmailReuniaoSiacolDao rlEmailReuniaoSiacolDao;
	
	@Inject	EmailConverter converter;
	
	public RlEmailReuniaoSiacolDto getTemplateReuniaoBy(GenericDto dto) {
		return converter.toRlEmailReuniaoDto(rlEmailReuniaoSiacolDao.getEmailsPor(dto.getIdReuniao(), dto.getIdTipo()));
	}
	
	public RlEmailReuniaoSiacol salvaRlTemplateReuniao(GenericDto dto) {
		RlEmailReuniaoSiacol rlEmailReuniaoSiacol = new RlEmailReuniaoSiacol();
		ReuniaoSiacol reuniaoSiacol = new ReuniaoSiacol();
		reuniaoSiacol.setId(dto.getIdReuniao());
		EmailEnvio emailEnvio = new EmailEnvio();
		emailEnvio.setId(dto.getIdConfiguracao());

		rlEmailReuniaoSiacol.setReuniao(reuniaoSiacol);
		rlEmailReuniaoSiacol.setEmailEnvio(emailEnvio);

		return rlEmailReuniaoSiacolDao.create(rlEmailReuniaoSiacol);
	}

}
