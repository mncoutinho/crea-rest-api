package br.org.crea.atendimento.converter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.commons.ArquivoConverter;
import br.org.crea.commons.models.atendimento.Ouvidoria;
import br.org.crea.commons.models.atendimento.OuvidoriaAssuntosEspecificos;
import br.org.crea.commons.models.atendimento.OuvidoriaAssuntosGerais;
import br.org.crea.commons.models.atendimento.OuvidoriaOrigem;
import br.org.crea.commons.models.atendimento.OuvidoriaSituacoes;
import br.org.crea.commons.models.atendimento.OuvidoriaTipoDemanda;
import br.org.crea.commons.models.atendimento.dtos.OuvidoriaAssuntoEspecificoDto;
import br.org.crea.commons.models.atendimento.dtos.OuvidoriaAssuntoGeraldto;
import br.org.crea.commons.models.atendimento.dtos.OuvidoriaDto;
import br.org.crea.commons.models.commons.Protocolo;
import br.org.crea.commons.models.commons.dtos.DomainGenericDto;
import br.org.crea.commons.models.commons.dtos.ProtocoloDto;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.DateUtils;

public class OuvidoriaConverter {
	
	@Inject
	ArquivoConverter arquivoConverter;

	@Inject
	HttpClientGoApi HttpGoApi;

	public List<OuvidoriaDto> toListOuvidoriaDto(List<Ouvidoria> listModel) {

		List<OuvidoriaDto> lista = new ArrayList<OuvidoriaDto>();

		listModel.forEach(model -> {
			lista.add(toDto(model));
		});

		return lista;
	}

	public OuvidoriaDto toDto(Ouvidoria model) {

		OuvidoriaDto dto = new OuvidoriaDto();

		if (model.existeId()) {
			dto.setId(model.getId());
		}
		if (model.existeDataAtendimento()) {
			dto.setDataAtendimento(model.getDataAtendimento());
		}
		dto.setDataAtendimentoFormatada(DateUtils.format(model.getDataAtendimento(), DateUtils.DD_MM_YYYY));

		if (model.existeDescricao()) {
			dto.setDescricao(model.getDescricao());
		}
		if (model.existeProvidencia()) {
			dto.setProvidencia(model.getProvidencia());
		}
		if (model.existeAssuntoEspecifico()) {
			dto.setAssuntoEspecifico(convertToAssuntoEspecificoDto(model.getAssuntoEspecifico()));
		}
		if (model.existeAssuntoGeral()) {
			dto.setAssuntoGeral(convertToAssuntoGeralDto(model.getAssuntoGeral()));
		}
		if (model.existeProtocolo()) {
			dto.setProtocolo(convertToProtocoloDto(model.getProtocolo()));
		}
		if (model.existeProvidencia()) {
			dto.setProvidencia(model.getProvidencia());
		}
		if (model.existeSolucao()) {
			dto.setSolucao(model.getSolucao());
		}
		if (model.existeTipoDemanda()) {
			dto.setTipoDemanda(convertToDemandaDto(model.getTipoDemanda()));
		}
		if (model.existeOrigem()) {
			dto.setOrigem(convertToOrigemDto(model.getOrigem()));
		}
		if (model.existeSituacao()) {
			dto.setSituacao(convertToSituacaoDto(model.getSituacao()));
		}
		if (model.temArquivo()) {
			dto.setArquivo(arquivoConverter.toDto(model.getArquivo()));
		}
		
		return dto;
	}

	private ProtocoloDto convertToProtocoloDto(Protocolo model) {
		ProtocoloDto dto = new ProtocoloDto();
		dto.setId(model.getIdProtocolo());
		return dto;
	}



	public Ouvidoria toModel(OuvidoriaDto dto) {

		Ouvidoria ouvidoria = new Ouvidoria();

		if (dto.getId() != null) {
			ouvidoria.setId(dto.getId());
		}
		ouvidoria.setDataAtendimento(dto.getDataAtendimento());
		ouvidoria.setDescricao(dto.getDescricao() != null ? dto.getDescricao() : "");
		ouvidoria.setProvidencia(dto.getProvidencia() != null ? dto.getProvidencia() : "");
		ouvidoria.setTipoDemanda(convertToDemandaModel(dto));
		ouvidoria.setAssuntoGeral(convertToAssuntoGeral(dto));
		ouvidoria.setAssuntoEspecifico(convertToAssuntoespecificoModel(dto));
		if (dto.getPessoa().getId() != null) {
			ouvidoria.setPessoa(convertToPessoaModel(dto));
		}
		if (dto.getProtocolo().getId() != null) {
			ouvidoria.setProtocolo(convertToProtocoloModel(dto.getProtocolo()));	
		}
		if (dto.getArquivo().getId() != null) {
			ouvidoria.setArquivo(arquivoConverter.toModel(dto.getArquivo()));
		}
		return ouvidoria;
	}

	private Protocolo convertToProtocoloModel(ProtocoloDto protocoloDto) {
		if (protocoloDto.getId() != null ) {
			Protocolo model = new Protocolo();
			model.setIdProtocolo(protocoloDto.getId());
			return model;
		} else {
			return null;
		}
		
	}

	private Pessoa convertToPessoaModel(OuvidoriaDto dto) {
		Pessoa model = new Pessoa();
		model.setId(dto.getPessoa().getId());
		return model;
	}

	private OuvidoriaTipoDemanda convertToDemandaModel(OuvidoriaDto dto) {
		OuvidoriaTipoDemanda model = new OuvidoriaTipoDemanda();
		model.setId(dto.getTipoDemanda().getId());
		model.setDescricao(dto.getTipoDemanda().getDescricao());
		return model;
	}
	
	private DomainGenericDto toTipoDemandaDto(OuvidoriaTipoDemanda model) {
		DomainGenericDto dto = new DomainGenericDto();
		dto.setDescricao(model.getDescricao());
		dto.setId(model.getId());
		return dto;
	}
	
	public List<DomainGenericDto> toListTipoDemandaDto(List<OuvidoriaTipoDemanda> listModel) {
		List<DomainGenericDto> list = new ArrayList<DomainGenericDto>();
		listModel.forEach(model -> {
			list.add(toTipoDemandaDto(model));
		});
		return list;
	}


	private OuvidoriaAssuntosGerais convertToAssuntoGeral(OuvidoriaDto dto) {
		OuvidoriaAssuntosGerais model = new OuvidoriaAssuntosGerais();
		model.setId(dto.getAssuntoGeral().getId());
		model.setDescricao(dto.getAssuntoGeral().getDescricao());
		model.setDataAlteracao(dto.getAssuntoGeral().getDataAlteracao());

		return model;
	}
	
	public List<OuvidoriaAssuntoGeraldto> toListAssuntosGeraisDto(List<OuvidoriaAssuntosGerais> listModel) {
		List<OuvidoriaAssuntoGeraldto> list = new ArrayList<OuvidoriaAssuntoGeraldto>();
		listModel.forEach(model -> {
			list.add(convertToAssuntoGeralDto(model));
		});
		return list;
	}
		
	private OuvidoriaAssuntoGeraldto convertToAssuntoGeralDto(OuvidoriaAssuntosGerais assuntoGeral) {
		OuvidoriaAssuntoGeraldto dto = new OuvidoriaAssuntoGeraldto();
		dto.setId(assuntoGeral.getId());
		dto.setDescricao(assuntoGeral.getDescricao());
		dto.setDataAlteracao(assuntoGeral.getDataAlteracao());
		return dto;
	}

	public List<DomainGenericDto> toListSituacaoDto(List<OuvidoriaSituacoes> listModel) {
		List<DomainGenericDto> list = new ArrayList<DomainGenericDto>();
		listModel.forEach(model -> {
			list.add(convertToSituacaoDto(model));
		});
		return list;
	}

	private DomainGenericDto convertToSituacaoDto(OuvidoriaSituacoes situacao) {
		DomainGenericDto dto = new DomainGenericDto();
		dto.setId(situacao.getId());
		dto.setDescricao(situacao.getDescricao());
		return dto;
	}

	private DomainGenericDto convertToDemandaDto(OuvidoriaTipoDemanda tipoDemanda) {
		DomainGenericDto dto = new DomainGenericDto();
		dto.setId(tipoDemanda.getId());
		dto.setDescricao(tipoDemanda.getDescricao());
		return dto;
	}

	private DomainGenericDto convertToOrigemDto(OuvidoriaOrigem origem) {
		DomainGenericDto dto = new DomainGenericDto();
		dto.setId(origem.getId());
		dto.setDescricao(origem.getDescricao());
		return dto;
	}


	private OuvidoriaAssuntoEspecificoDto convertToAssuntoEspecificoDto(
			OuvidoriaAssuntosEspecificos assuntoEspecifico) {
		OuvidoriaAssuntoEspecificoDto dto = new OuvidoriaAssuntoEspecificoDto();
		dto.setId(assuntoEspecifico.getId());
		dto.setDescricao(assuntoEspecifico.getDescricao());
		dto.setDataAlteracao(assuntoEspecifico.getDataAlteracao());
		dto.setAssuntosGerais(assuntoEspecifico.getIdAssuntosGerais());
		

		return dto;
	}

	public List<OuvidoriaAssuntoEspecificoDto> toListAssuntosEspecificosDto(List<OuvidoriaAssuntosEspecificos> listModel) {
			List<OuvidoriaAssuntoEspecificoDto> list = new ArrayList<OuvidoriaAssuntoEspecificoDto>();
			listModel.forEach(model -> {
				list.add(convertToAssuntoEspecificoDto(model));
			});
			return list;
		}
	private OuvidoriaAssuntosEspecificos convertToAssuntoespecificoModel(OuvidoriaDto dto) {
		OuvidoriaAssuntosEspecificos model = new OuvidoriaAssuntosEspecificos();
		model.setId(dto.getAssuntoEspecifico().getId());
		model.setDescricao(dto.getAssuntoEspecifico().getDescricao());
		model.setIdAssuntosGerais(dto.getAssuntoEspecifico().getAssuntosGerais());
		return model;
	}


}


