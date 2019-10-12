package br.org.crea.atendimento.service;

import java.util.List;

import javax.inject.Inject;

import br.org.crea.atendimento.converter.OuvidoriaConverter;
import br.org.crea.commons.dao.atendimento.OuvidoriaDao;
import br.org.crea.commons.factory.commons.EmailFactory;
import br.org.crea.commons.models.atendimento.Ouvidoria;
import br.org.crea.commons.models.atendimento.dtos.OuvidoriaAssuntoEspecificoDto;
import br.org.crea.commons.models.atendimento.dtos.OuvidoriaAssuntoGeraldto;
import br.org.crea.commons.models.atendimento.dtos.OuvidoriaDto;
import br.org.crea.commons.models.commons.dtos.DomainGenericDto;
import br.org.crea.commons.models.commons.dtos.PesquisaGenericDto;
import br.org.crea.commons.service.protocolo.ProtocoloService;

public class OuvidoriaService {

	@Inject
	OuvidoriaDao dao;

	@Inject
	OuvidoriaConverter converter;

	@Inject
	EmailFactory emailFactory;

	@Inject
	ProtocoloService protocoloService;

	public OuvidoriaDto salvar(OuvidoriaDto dto) {
		try {
			Ouvidoria ouvidoria = new Ouvidoria();
			Ouvidoria OuvidoriaTmp = converter.toModel(dto);
			ouvidoria = dao.create(OuvidoriaTmp);
			OuvidoriaDto ouvidoriaDto = converter.toDto(ouvidoria);
			if (dto.getPessoa().getId() != null) {
				emailFactory.enviarEmailCadastroOuvidoriaAtendimento(dto.getPessoa().getId(), ouvidoria.getId());	
			}
			return ouvidoriaDto;
		} catch (Exception e) {
			return null;
		}
	}

	public int getTotalDeRegistrosDaPesquisa(PesquisaGenericDto pesquisa) {
		return dao.getTotalDeRegistrosdaPesquisa(pesquisa);
	}

	public List<OuvidoriaDto> getByOuvidoriaAtendimentoPaginado(PesquisaGenericDto pesquisa) {
		return converter.toListOuvidoriaDto(dao.pesquisaAtendimentosOuvidoriaPaginado(pesquisa));
	}

	public List<DomainGenericDto> getAllTipoDemanda() {
		return converter.toListTipoDemandaDto(dao.getAllTipoDemanda());
	}

	public List<OuvidoriaAssuntoGeraldto> getAllAssuntosGerais() {
		return converter.toListAssuntosGeraisDto(dao.getAllAssuntosGerais());
	}

	public List<OuvidoriaAssuntoEspecificoDto> getAllAssuntosEspecificos(Long assuntosGerais) {
		return converter.toListAssuntosEspecificosDto(dao.getAllAssuntosEspecificos(assuntosGerais));
	}

	public List<DomainGenericDto> getSituacao() {
		return converter.toListSituacaoDto(dao.getSituacao());
	}

	public List<OuvidoriaDto> getOuvidoriaPublica(PesquisaGenericDto pesquisa) {
		return converter.toListOuvidoriaDto(dao.getOuvidoriaPublica(pesquisa));
	}

	public int getTotalRegistrosPesquisaPublica(PesquisaGenericDto pesquisa) {
		return dao.getTotalRegistrosPesquisaPublica(pesquisa);
	}

	public void anexaArquivoOcorrencia(OuvidoriaDto dto) {
		dao.anexaArquivoOcorrencia(dto);
	}
	

}
