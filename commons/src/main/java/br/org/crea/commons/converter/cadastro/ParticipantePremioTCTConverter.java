package br.org.crea.commons.converter.cadastro;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.cadastro.pessoa.PessoaConverter;
import br.org.crea.commons.dao.cadastro.ConfeaTituloDao;
import br.org.crea.commons.models.cadastro.ParticipantePremioTCT;
import br.org.crea.commons.models.cadastro.PremioTCT;
import br.org.crea.commons.models.cadastro.dtos.ParticipantePremioTCTDto;
import br.org.crea.commons.models.corporativo.dtos.PessoaDto;
import br.org.crea.commons.models.corporativo.pessoa.PessoaFisica;
import br.org.crea.commons.service.cadastro.TituloService;
import br.org.crea.commons.util.StringUtil;

public class ParticipantePremioTCTConverter {
	
	@Inject PessoaConverter pessoaConverter;
	
	@Inject TituloService tituloService;
	
	@Inject ConfeaTituloDao confeaDao;
	
	public ParticipantePremioTCT toModel(ParticipantePremioTCTDto dto) {

		PremioTCT premio = new PremioTCT();
		premio.setId(dto.getIdPremio());

		PessoaFisica pessoa = new PessoaFisica();
		pessoa.setId(dto.getPessoa().getId());
		
		ParticipantePremioTCT participante = new ParticipantePremioTCT();
		participante.setPremio(premio);
		participante.setPessoa(pessoa);
		participante.setEmail(dto.getEmail());
		participante.setTelefone(dto.getTelefone());
		participante.setPapel(dto.getPapel());
		participante.setCelular(dto.getCelular());
		participante.setIdEndereco(Long.valueOf(dto.getIdEndereco()));
		participante.setIdTitulo(dto.getIdTitulo());
		return participante;

	}
	public List<ParticipantePremioTCTDto> toListDto(List<ParticipantePremioTCT> listModel) {

		List<ParticipantePremioTCTDto> listDto = new ArrayList<ParticipantePremioTCTDto>();

		for (ParticipantePremioTCT a : listModel) {
			listDto.add(toDto(a));
		}

		return listDto;

	}
	
	public ParticipantePremioTCTDto toDto(ParticipantePremioTCT model) {

		ParticipantePremioTCTDto dto = new ParticipantePremioTCTDto();
		dto.setEmail(model.getEmail());
		dto.setTelefone(model.getTelefone());
		dto.setId(model.getId());
		dto.setPapel(model.getPapel());
		dto.setCpf(StringUtil.formataCpf(model.getPessoa().getCpf()));
		dto.setCelular(model.getCelular());
		if(model.getIdTitulo() != null) {
			dto.setIdTitulo(model.getIdTitulo());
			dto.setTitulo(confeaDao.getTituloById(model.getIdTitulo()));
		}
		if(model.getIdEndereco() != null) {
			dto.setIdEndereco(Long.toString(model.getIdEndereco()));
		}
		if(model.getPessoa() != null){
			PessoaDto pessoa = pessoaConverter.toDto(model.getPessoa());
			dto.setPessoa(pessoa);
		}
		if (model.getPremio() != null){
			dto.setIdPremio(model.getPremio().getId());
		}
		
		
		return dto;
	}
}
