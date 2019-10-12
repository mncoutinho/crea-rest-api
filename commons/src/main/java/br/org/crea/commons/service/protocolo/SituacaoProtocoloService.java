package br.org.crea.commons.service.protocolo;

import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.protocolo.SituacaoProtocoloConverter;
import br.org.crea.commons.dao.protocolo.SituacaoProtocoloDao;
import br.org.crea.commons.models.commons.dtos.SituacaoProtocoloDto;

public class SituacaoProtocoloService {
	
	@Inject SituacaoProtocoloConverter converter;
	
	@Inject SituacaoProtocoloDao dao;

	public List<SituacaoProtocoloDto> getSituacaoSiacol() {
		return converter.toListDto(dao.getSituacaoSiacol());
	}
	
	public List<SituacaoProtocoloDto> getAllSituacoes() {
		return converter.toListDto(dao.getAll());
	}

	public SituacaoProtocoloDto salvar(SituacaoProtocoloDto dto) {
		return converter.toDto(dao.create(converter.toModel(dto)));
	}

	public SituacaoProtocoloDto atualizar(SituacaoProtocoloDto dto) {
		return converter.toDto(dao.update(converter.toModel(dto)));
	}

	public void deletar(Long id) {
		dao.deleta(id);
	}
	

	public Object existeCodigoExistente(Long codigo) {
		return converter.toDto(dao.verificaCodigo(codigo));
	}
	

}
