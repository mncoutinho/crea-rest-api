package br.org.crea.siacol.converter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.dao.cadastro.pessoa.PessoaDao;
import br.org.crea.commons.models.corporativo.dtos.PessoaDto;
import br.org.crea.commons.models.siacol.RlDeclaracaoVoto;
import br.org.crea.commons.models.siacol.RlDocumentoProtocoloSiacol;
import br.org.crea.commons.models.siacol.dtos.RlDeclaracaoVotoDto;

public class RlDeclaracaoVotoConverter {
	
	@Inject PautaReuniaoSiacolConverter pautaReuniaoSiacolConverter;
	@Inject PessoaDao pessoaDao;

	public List<RlDeclaracaoVotoDto> toListDto(List<RlDeclaracaoVoto> listModel) {
		
		List<RlDeclaracaoVotoDto> listDto = new ArrayList<RlDeclaracaoVotoDto>();
		
		for(RlDeclaracaoVoto s : listModel){
			listDto.add(toDto(s));
		}
		
		return listDto;
		
	}

	public RlDeclaracaoVotoDto toDto(RlDeclaracaoVoto model) {
		
		if (model != null){
			RlDeclaracaoVotoDto dto = new RlDeclaracaoVotoDto();
			
			dto.setId(model.getId());
			dto.setIdAnalistaUpload(model.getAnalistaUpload());
			dto.setIdConselheiro(model.getConselheiro());
			dto.setItem(pautaReuniaoSiacolConverter.toItemPautaDto(model.getItem()));
			dto.setIdArquivoDocflow(model.getIdArquivoDocflow());
			
			return dto;			
		}else{
			return null;
		}

	}
	
	public RlDeclaracaoVoto toModel(RlDeclaracaoVotoDto dto) {
		
		if (dto != null){
			RlDeclaracaoVoto model = new RlDeclaracaoVoto();
			
			if (dto.getId() != null && dto.getId() != 0) {
				model.setId(dto.getId());
			}
			model.setId(model.getId());
			model.setAnalistaUpload(dto.getIdAnalistaUpload());
			model.setConselheiro(dto.getIdConselheiro());
			RlDocumentoProtocoloSiacol item = new RlDocumentoProtocoloSiacol();
			item.setId(dto.getItem().getId());
			model.setItem(item);
			
			model.setIdArquivoDocflow(dto.getIdArquivoDocflow());
											
			return model;			
		}else{
			return null;
		}

	}

	public List<RlDeclaracaoVotoDto> toListDtoComPessoa(List<RlDeclaracaoVoto> listModel) {
		
		List<RlDeclaracaoVotoDto> listDto = new ArrayList<RlDeclaracaoVotoDto>();
		
		for(RlDeclaracaoVoto s : listModel){
			listDto.add(toDtoComPessoa(s));
		}
		
		return listDto;
		
	}

	public RlDeclaracaoVotoDto toDtoComPessoa(RlDeclaracaoVoto model) {
		
		if (model != null){
			RlDeclaracaoVotoDto dto = new RlDeclaracaoVotoDto();
			PessoaDto conselheiro = new PessoaDto();
			PessoaDto analistaUpload = new PessoaDto();
			
			conselheiro.setId(model.getConselheiro());
			conselheiro.setNome(pessoaDao.getBy(model.getConselheiro()).getNome());
			
			if (model.getAnalistaUpload() != null) {
				analistaUpload.setId(model.getAnalistaUpload());
				analistaUpload.setNome(pessoaDao.getBy(model.getAnalistaUpload()).getNome());
			}
			
			dto.setId(model.getId());
			dto.setIdAnalistaUpload(model.getAnalistaUpload());
			dto.setAnalistaUpload(analistaUpload);
			dto.setIdConselheiro(model.getConselheiro());
			dto.setConselheiro(conselheiro);
			dto.setItem(pautaReuniaoSiacolConverter.toItemPautaDto(model.getItem()));
			dto.setIdArquivoDocflow(model.getIdArquivoDocflow());
			
			return dto;			
		}else{
			return null;
		}

	}
	
}
