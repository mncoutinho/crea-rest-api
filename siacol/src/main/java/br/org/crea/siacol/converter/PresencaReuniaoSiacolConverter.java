package br.org.crea.siacol.converter;

import br.org.crea.commons.models.corporativo.dtos.PessoaDto;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;
import br.org.crea.commons.models.siacol.PresencaReuniaoSiacol;
import br.org.crea.commons.models.siacol.ReuniaoSiacol;
import br.org.crea.commons.models.siacol.dtos.PresencaReuniaoSiacolDto;
import br.org.crea.commons.models.siacol.dtos.ReuniaoSiacolDto;

public class PresencaReuniaoSiacolConverter {

	public PresencaReuniaoSiacolDto toDto(PresencaReuniaoSiacol model) {
		PresencaReuniaoSiacolDto dto = null;

		if(model != null) {
			
			dto = new PresencaReuniaoSiacolDto();
			
			dto.setId(model.getId());
			dto.setHoraEntregaCracha(model.getHoraEntregaCracha());
			dto.setHoraDevolucaoCracha(model.getHoraDevolucaoCracha());
			dto.setHora80(model.getHora80());
			dto.setAtingiu80(model.getAtingiu80());
			dto.setPapel(model.getPapel());
			dto.setTipo(model.getTipo());
			dto.setVotoMinerva(model.getVotoMinerva());
			
			if (model.temReuniao()) {
				ReuniaoSiacolDto reuniao = new ReuniaoSiacolDto();
				reuniao.setId(model.getReuniao().getId());
				dto.setReuniao(reuniao);
			}
			
			if (model.temPessoa()) {
				PessoaDto pessoa = new PessoaDto();
				pessoa.setId(model.getPessoa().getId());
				pessoa.setNome(model.getPessoa().getNome());
				dto.setPessoa(pessoa);
			}

		}

		return dto;
	}
	
	public PresencaReuniaoSiacol toModel(PresencaReuniaoSiacolDto dto) {
		PresencaReuniaoSiacol model = null;

		if(dto != null) {
			
			model = new PresencaReuniaoSiacol();
			
			model.setId(dto.getId());
			model.setHoraEntregaCracha(dto.getHoraEntregaCracha());
			model.setHoraDevolucaoCracha(dto.getHoraDevolucaoCracha());
			model.setHora80(dto.getHora80());
			model.setAtingiu80(dto.getAtingiu80());
			model.setPapel(dto.getPapel());
			model.setTipo(dto.getTipo());
			model.setVotoMinerva(dto.getVotoMinerva());
			
			if (dto.temReuniao()) {
				ReuniaoSiacol reuniao = new ReuniaoSiacol();
				reuniao.setId(dto.getReuniao().getId());
				model.setReuniao(reuniao);
			}
			
			if (dto.temPessoa()) {
				Pessoa pessoa = new Pessoa();
				pessoa.setId(dto.getPessoa().getId());
				pessoa.setNome(dto.getPessoa().getNome());
				model.setPessoa(pessoa);
			}

		}

		return model;
	}
	
}
