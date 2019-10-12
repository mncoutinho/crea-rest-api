package br.org.crea.siacol.service;

import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.models.siacol.SiacolTags;
import br.org.crea.commons.models.siacol.dtos.SiacolRlProtocoloTagsDto;
import br.org.crea.commons.models.siacol.dtos.SiacolTagsDto;
import br.org.crea.siacol.converter.SiacolRlProtocoloTagsConverter;
import br.org.crea.siacol.converter.SiacolTagsConverter;
import br.org.crea.siacol.dao.SiacolRlProtocoloTagsDao;
import br.org.crea.siacol.dao.SiacolTagsDao;

public class SiacolTagsService {
	
	@Inject SiacolTagsDao dao;
	@Inject SiacolRlProtocoloTagsDao siacolRlProtocoloTagsDao;
	@Inject SiacolTagsConverter converter;
	@Inject SiacolRlProtocoloTagsConverter siacolRlProtocoloTagsConverter;

	public List<SiacolTagsDto> getAll() {
		return converter.toListDto(dao.getAll());
	}

	public List<SiacolRlProtocoloTagsDto> consultarTags(List<SiacolTagsDto> listTagsDto) {
		return siacolRlProtocoloTagsConverter.toListDtoSemProtocolo(siacolRlProtocoloTagsDao.consultarTags(listTagsDto));
	}

	public List<SiacolRlProtocoloTagsDto> getTagsByProtocolo(Long numeroProtocolo) {
		return siacolRlProtocoloTagsConverter.toListDtoSemProtocolo(siacolRlProtocoloTagsDao.getTagsByProtocolo(numeroProtocolo));

	}

	public SiacolRlProtocoloTagsDto salvarTagProtocolo(SiacolRlProtocoloTagsDto siacolRlProtocoloTags) {
		
		siacolRlProtocoloTags.getTag().setId(new Long(0));
		if (jaExiste(siacolRlProtocoloTags.getTag())) {
			siacolRlProtocoloTags.setTag(converter.toDto(dao.getTagByDescricao(siacolRlProtocoloTags.getTag())));
			siacolRlProtocoloTagsDao.create(siacolRlProtocoloTagsConverter.toModel(siacolRlProtocoloTags));
		}else{
			SiacolTags siacolTags = dao.create(converter.toModel(siacolRlProtocoloTags.getTag()));
			siacolRlProtocoloTags.getTag().setId(siacolTags.getId());
			siacolRlProtocoloTagsDao.create(siacolRlProtocoloTagsConverter.toModel(siacolRlProtocoloTags));
		}
		return null;
	}

	private boolean jaExiste(SiacolTagsDto tag) {
		return dao.jaExisteTag(tag);
	}

	public boolean tagJaFoiCadastrada(SiacolRlProtocoloTagsDto siacolRlProtocoloTagsDto) {
		return siacolRlProtocoloTagsDao.tagJaFoiCadastrada(siacolRlProtocoloTagsDto);
	}

	public SiacolRlProtocoloTagsDto removerRlProtocoloTags(Long idRlProtocoloTags) {
		SiacolRlProtocoloTagsDto siacolRlProtocoloTagsDto = siacolRlProtocoloTagsConverter.toDto(siacolRlProtocoloTagsDao.deleta(idRlProtocoloTags));
		if (naoTemUsoDaTag(siacolRlProtocoloTagsDto.getTag().getId())) {
			dao.deleta(siacolRlProtocoloTagsDto.getTag().getId());
		}
		return siacolRlProtocoloTagsDto;
	}

	private boolean naoTemUsoDaTag(Long id) {
		return siacolRlProtocoloTagsDao.naoTemUsoDaTag(id);
	}

	public SiacolTagsDto atualizarTag(SiacolTagsDto siacolTagDto) {
		if (!jaExiste(siacolTagDto)) {
			return converter.toDto(dao.update(converter.toModel(siacolTagDto)));
		}
		return null;
		
	}
	
}
