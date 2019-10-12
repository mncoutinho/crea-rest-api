package br.org.crea.siacol.converter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.dao.cadastro.pessoa.InteressadoDao;
import br.org.crea.commons.dao.cadastro.profissional.ProfissionalDao;
import br.org.crea.commons.dao.cadastro.profissional.ProfissionalEspecialidadeDao;
import br.org.crea.commons.models.corporativo.dtos.PessoaDto;
import br.org.crea.commons.models.corporativo.pessoa.IInteressado;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;
import br.org.crea.commons.models.corporativo.pessoa.Profissional;
import br.org.crea.commons.models.siacol.ProtocoloSiacol;
import br.org.crea.commons.models.siacol.ReuniaoSiacol;
import br.org.crea.commons.models.siacol.RlDocumentoProtocoloSiacol;
import br.org.crea.commons.models.siacol.VotoReuniaoSiacol;
import br.org.crea.commons.models.siacol.dtos.VotoReuniaoSiacolDto;
import br.org.crea.commons.util.DateUtils;

public class VotoReuniaoSiacolConverter {
	
	@Inject ProtocoloSiacolConverter protocoloConverter;
	@Inject ReuniaoSiacolConverter reuniaoConverter;
	@Inject InteressadoDao interessadoDao;
	@Inject ProfissionalEspecialidadeDao profissionalEspecialidadeDao;
	@Inject ProfissionalDao profissionalDao;

	public VotoReuniaoSiacol toModel(VotoReuniaoSiacolDto dto) {
		
		VotoReuniaoSiacol model = new VotoReuniaoSiacol();
		
		if(dto.temId()){
			model.setId(dto.getId());
		}
		
		model.setDataVoto(new Date());
		model.setDestaque(dto.isDestaque());
		model.setDeclaracao(dto.isDeclaracao());
		model.setJustificativa(dto.getJustificativa());
		model.setItem(dto.getItem());
		model.setDescricaoItem(dto.getDescricaoItem());
		model.setIdRespostaEnquete(dto.getIdRespostaEnquete());
		
		ReuniaoSiacol reuniao = new ReuniaoSiacol();
		reuniao.setId(dto.getReuniao().getId());
		model.setReuniao(reuniao);
		
		model.setVoto(dto.getVoto());
		
		if(dto.temPessoa()){
			Pessoa pessoa = new Pessoa();
			pessoa.setId(dto.getPessoa().getId());
			model.setPessoa(pessoa);
		}
		
		if (dto.getProtocolo() != null) {
			ProtocoloSiacol protocolo = new ProtocoloSiacol();
			protocolo.setId(dto.getProtocolo().getId());
			model.setProtocolo(protocolo);
		}
		
		if (dto.getIdRlItemPauta() != null) {
			RlDocumentoProtocoloSiacol itemPauta = new RlDocumentoProtocoloSiacol();
			itemPauta.setId(dto.getIdRlItemPauta());
			model.setItemPauta(itemPauta);
		}
		
		return model;
		
	}

	public VotoReuniaoSiacolDto toDto(VotoReuniaoSiacol model) {
		
		VotoReuniaoSiacolDto dto = new VotoReuniaoSiacolDto();
		
		dto.setId(model.getId());
		dto.setDataVoto(model.getDataVoto());
		dto.setDataVotoFormatado(DateUtils.format(model.getDataVoto(), DateUtils.DD_MM_YYYY_HH_MM));
		dto.setDestaque(model.isDestaque());
		dto.setDeclaracao(model.isDeclaracao());
		dto.setJustificativa(model.getJustificativa());
		dto.setVoto(model.getVoto());
		dto.setIdRespostaEnquete(model.getIdRespostaEnquete());
		
		dto.setProtocolo(protocoloConverter.toDto(model.getProtocolo()));
		dto.setReuniao(reuniaoConverter.toDto(model.getReuniao()));
		
		IInteressado interessado = interessadoDao.buscaInteressadoBy(model.getPessoa().getId());
		PessoaDto pessoa = new PessoaDto();
		Profissional profissional = null;
		profissional = profissionalDao.buscaProfissionalPor(interessado.getRegistro());
		
		pessoa.setId(interessado.getId());
		pessoa.setRegistro(interessado.getRegistro());
		pessoa.setNome(interessado.getNome());
		pessoa.setBase64(interessado.getFotoBase64());
		if(profissional != null){
			pessoa.setTitulo(profissionalEspecialidadeDao.getTituloProfissional(profissional));
		}
		pessoa.setMatricula(interessado.getMatricula());
		dto.setPessoa(pessoa);
		
		
		return dto;
	}

	public List<VotoReuniaoSiacolDto> toListDto(List<VotoReuniaoSiacol> listModel) {
	
		List<VotoReuniaoSiacolDto> listDto = new ArrayList<VotoReuniaoSiacolDto>();
		
		for(VotoReuniaoSiacol v : listModel){
			listDto.add(toDto(v));
		}
		
		return listDto;
	}

}
