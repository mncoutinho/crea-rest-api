package br.org.crea.commons.service.cadastro;

import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.cadastro.domains.EventoConverter;
import br.org.crea.commons.dao.cadastro.EventoDao;
import br.org.crea.commons.models.siacol.dtos.EventoDto;

public class EventoService {

	@Inject
	EventoConverter converter;

	@Inject
	EventoDao dao;

	public List<EventoDto> getAll() {
		return converter.toListDto(dao.getAll());
	}

	public EventoDto salvaEvento(EventoDto dto) {
		return converter.toDto(dao.create(converter.toModel(dto)));
	}

	public EventoDto atualizaEvento(EventoDto dto) {
		return converter.toDto(dao.update(converter.toModel(dto)));
	}

	public void deletar(Long id) {
		dao.deleta(id);
	}

	public boolean podeDeletar(Long id) {
		return dao.podeDeletar(id);
	}

	public List<EventoDto> getEventoByAssuntoProtocolo(Long idAssuntoProtocolo) {
		return converter.toListDto(dao.getEventoByAssuntoProtocolo(idAssuntoProtocolo));
	}

}
