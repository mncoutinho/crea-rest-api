package br.org.crea.commons.service.protocolo;

import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.protocolo.AssuntoConverter;
import br.org.crea.commons.converter.protocolo.BlocosAssuntosConverter;
import br.org.crea.commons.dao.protocolo.AssuntoDao;
import br.org.crea.commons.dao.protocolo.BlocosAssuntosDao;
import br.org.crea.commons.dao.protocolo.RlAssuntosDao;
import br.org.crea.commons.models.corporativo.Assunto;
import br.org.crea.commons.models.corporativo.dtos.AssuntoDto;
import br.org.crea.commons.models.corporativo.dtos.BlocosAssuntosDto;
import br.org.crea.commons.models.corporativo.dtos.FuncionarioDto;

public class AssuntoService {

	@Inject
	AssuntoConverter converter;
	
	@Inject
	BlocosAssuntosConverter blocosAssuntosConverter;
	
	@Inject
	BlocosAssuntosDao blocosAssuntosDao;
	
	@Inject
	AssuntoDao dao;
	
	@Inject
	RlAssuntosDao daoRlAssuntos;

	public AssuntoDto getAssuntoBy(Long codigo) {
		return converter.toDto(dao.getAssuntoBy(codigo));		
	}

	public List<AssuntoDto> getAssuntosDisponiveis(Long idBloco) {
		return converter.toListDto(dao.getAssuntosDisponiveis(idBloco));
	}
	
	public List<BlocosAssuntosDto> getblocos() {
		return blocosAssuntosConverter.toListDto(blocosAssuntosDao.getblocos());
	}
	
	public List<AssuntoDto> getAssuntosComDocumentacao() {
		return converter.toListDto(dao.getAssuntosComDocumentacao());
	}
	
	public List<AssuntoDto> getAssuntoSiacol() {
		return converter.toListDto(dao.getAssuntoSiacol());
	}

	public List<AssuntoDto> getAssuntoSiacolHabilitadoFuncionario(FuncionarioDto dto) {
		
		if (dto.getSiacol()){
			return converter.toListDto(dao.getAssuntoSiacolHabilitadoFuncionario(dto));
		}else{
			return converter.toListDtoSiacol(dao.getAssuntoSiacolHabilitadoConselheiro(dto));
		}
	}

	public List<AssuntoDto> getAssuntoSiacolDesabilitadoFuncionario(FuncionarioDto dto) {
		
		if (dto.getSiacol()){
			return converter.toListDto(dao.getAssuntoSiacolDesabilitadoFuncionario(dto));
		}else{
			return converter.toListDtoSiacol(dao.getAssuntoSiacolDesabilitadoConselheiro(dto));
		}
		
	}
	
	public AssuntoDto habilitaSiacol(AssuntoDto dto) {
		Assunto assunto = new Assunto();
		
		assunto = dao.getAssuntoBy(dto.getCodigo());
		
		assunto.setSiacol(dto.getSiacol());
		return converter.toDto(dao.update(assunto));
	}

	public List<AssuntoDto> getAssuntos() {
		return converter.toListDto(dao.getAssuntos());
	}

	public AssuntoDto atualizaAssunto(AssuntoDto dto) {
		dao.atualizaAssunto(dto);
		if (!dto.ehSiacol()){
			daoRlAssuntos.deleteByAssuntoProtocolo(dto.getId());
		}
		return dto;
	}

	public List<AssuntoDto> getAssuntoByTipoAssunto(String tipoAssunto) {
		return converter.toListDto(dao.getAssuntoByTipoAssunto(tipoAssunto));
	}
	
}
