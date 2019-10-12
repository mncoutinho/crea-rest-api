package br.org.crea.siacol.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.cadastro.domains.DepartamentoConverter;
import br.org.crea.commons.converter.cadastro.pessoa.PessoaConverter;
import br.org.crea.commons.dao.cadastro.domains.DepartamentoDao;
import br.org.crea.commons.dao.cadastro.pessoa.InteressadoDao;
import br.org.crea.commons.dao.cadastro.pessoa.PessoaDao;
import br.org.crea.commons.dao.siacol.HabilidadePessoaDao;
import br.org.crea.commons.models.cadastro.Departamento;
import br.org.crea.commons.models.cadastro.dtos.DepartamentoDto;
import br.org.crea.commons.models.commons.dtos.DomainGenericDto;
import br.org.crea.commons.models.commons.dtos.GenericDto;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.models.corporativo.dtos.PessoaDto;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;
import br.org.crea.commons.models.siacol.ConfigPessoaSiacol;
import br.org.crea.commons.models.siacol.HabilidadePessoaSiacol;
import br.org.crea.commons.models.siacol.dtos.GenericSiacolDto;
import br.org.crea.commons.models.siacol.dtos.HabilidadePessoaDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.DateUtils;
import br.org.crea.commons.util.StringUtil;
import br.org.crea.siacol.converter.HabilidadePessoaConverter;
import br.org.crea.siacol.dao.ConfigPessoaSiacolDao;

public class HabilidadePessoaService {
	
	@Inject HttpClientGoApi httpGoApi;

	@Inject HabilidadePessoaConverter converter;

	@Inject HabilidadePessoaDao dao;

	@Inject ConfigPessoaSiacolDao daoConfig;

	@Inject PessoaConverter converterPessoa;
	
	@Inject PessoaDao pessoaDao;
	
	@Inject InteressadoDao interessadoDao;
	
	@Inject DepartamentoConverter departamentoConverter;
	
	@Inject DepartamentoDao departamentoDao;

	public Boolean verificaAtivo(Long idPessoa) {
		return dao.verificaAtivo(idPessoa);
	}

	public GenericSiacolDto programaHabilidadeCria(GenericSiacolDto dto) {

		if (dto.getListaId().size() > 1) {
			dto.setListaId(buscaDepartamentosByPessoa(dto.getIdPessoa(), dto.getIdDepartamento()));
		}
		for (Long id : dto.getListaId()) {
			ConfigPessoaSiacol pessoaConfig = new ConfigPessoaSiacol();

			try {
				Pessoa pessoa = new Pessoa();
				pessoa.setId(dto.getIdPessoa());
				pessoaConfig.setPessoa(pessoa);
				pessoaConfig.setDataInicio(dto.getDataInicio());
				pessoaConfig.setDataFim(dto.getDataFim());
				pessoaConfig.setAtivo(true);
				pessoaConfig.setIdDepartamento(id);

				daoConfig.create(pessoaConfig);
				
			} catch (Throwable e) {
				httpGoApi.geraLog("ConfigPessoaSiacolDao || ativaHabilidades", StringUtil.convertObjectToJson("sem parametros"), e);

			}
			

			if (DateUtils.eNoDiaAtual(dto.getDataInicio())) {
				daoConfig.desativaHabilidades(id);
			}

		}

		return dto;
	}

	public GenericSiacolDto programaHabilidadeRemove(GenericSiacolDto dto) {
		daoConfig.removeProgramacao(dto);
		return dto;
	}

	public void ativaHabilidade(Long id) {
		daoConfig.deleta(id);
	}

	public List<GenericDto> verificaProgramacao(Long idPessoa) {

		List<GenericDto> listDto = new ArrayList<GenericDto>();
		List<ConfigPessoaSiacol> listPessoaConfig = new ArrayList<ConfigPessoaSiacol>();

		listPessoaConfig = daoConfig.getByIdPessoa(idPessoa);

		for (ConfigPessoaSiacol pessoaConfig : listPessoaConfig) {
			GenericDto dto = new GenericDto();
			if (pessoaConfig != null) {
				dto.setId(String.valueOf(pessoaConfig.getId()));
				dto.setDataInicio(pessoaConfig.getDataInicio());
				dto.setDataFim(pessoaConfig.getDataFim());
				dto.setDataInicioFormatada(DateUtils.format(pessoaConfig.getDataInicio(), DateUtils.DD_MM_YYYY));
				dto.setDataFimFormatada(DateUtils.format(pessoaConfig.getDataFim(), DateUtils.DD_MM_YYYY));
				dto.setIdFuncionario(pessoaConfig.getPessoa().getId());
				dto.setSiacol(pessoaConfig.getAtivo());
				dto.setIdDepartamento(pessoaConfig.getIdDepartamento());
				listDto.add(dto);
			}
		}

		return listDto.isEmpty() ? null : listDto;

	}

	public HabilidadePessoaDto atualizaHabilidadesAnalista(HabilidadePessoaDto dto) {

		if (dto.heSiacol()) {
			dto.setLiberadoParaDistribuicao(true);
			dao.create(converter.toModelAnalista(dto));
			return dto;
		} else {
			HabilidadePessoaSiacol habilidadePessoa = new HabilidadePessoaSiacol();

			habilidadePessoa = dao.getHabilidadePessoaAnalista(dto);
			dao.deleta(habilidadePessoa.getId());
			return converter.toDto(habilidadePessoa);

		}
	}

	public Boolean ativaTodasHabilidadesAnalista(List<HabilidadePessoaDto> listDto) {
		for (HabilidadePessoaDto h : listDto) {

			if (listDto.indexOf(h) == 0) {
				GenericSiacolDto dto = new GenericSiacolDto();
				dto.setId(h.getIdPessoa().toString());
				dto.setIdDepartamento(h.getIdDepartamento());
				desativaTodasHabilidadesAnalista(dto);
			}
			h.setLiberadoParaDistribuicao(true);
			dao.create(converter.toModelAnalista(h));
		}

		return false;
	}

	public Boolean desativaTodasHabilidadesAnalista(GenericSiacolDto dto) {

		List<HabilidadePessoaSiacol> listHabilidadePessoa = new ArrayList<HabilidadePessoaSiacol>();
		listHabilidadePessoa = dao.getAllByIdPessoa(dto);

		if (listHabilidadePessoa.isEmpty()) {
			return false;
		} else {
			for (HabilidadePessoaSiacol h : listHabilidadePessoa) {
				dao.deleta(h.getId());
			}
			return true;
		}
	}

	public HabilidadePessoaDto atualizaHabilidadesConselheiro(HabilidadePessoaDto dto) {

		if (dto.heSiacol()) {
			dto.setLiberadoParaDistribuicao(true);
			dao.create(converter.toModelConselheiro(dto));
			return dto;
		} else {
			HabilidadePessoaSiacol habilidadePessoa = new HabilidadePessoaSiacol();

			habilidadePessoa = dao.getHabilidadePessoaConselheiro(dto);
			dao.deleta(habilidadePessoa.getId());
			return converter.toDto(habilidadePessoa);

		}
	}

	public Boolean ativaTodasHabilidadesConselheiro(List<HabilidadePessoaDto> listDto) {
		for (HabilidadePessoaDto h : listDto) {

			if (listDto.indexOf(h) == 0) {
				GenericSiacolDto dto = new GenericSiacolDto();
				dto.setId(h.getIdPessoa().toString());
				dto.setIdDepartamento(h.getIdDepartamento());
				desativaTodasHabilidadesAnalista(dto);
			}
			h.setLiberadoParaDistribuicao(true);
			dao.create(converter.toModelConselheiro(h));
		}

		return false;
	}

	public Boolean desativaTodasHabilidadesConselheiro(GenericSiacolDto dto) {

		List<HabilidadePessoaSiacol> listHabilidadePessoa = new ArrayList<HabilidadePessoaSiacol>();
		listHabilidadePessoa = dao.getAllByIdPessoa(dto);

		if (listHabilidadePessoa.isEmpty()) {
			return false;
		} else {
			for (HabilidadePessoaSiacol h : listHabilidadePessoa) {
				dao.deleta(h.getId());
			}
			return true;
		}
	}

	public List<Long> buscaDepartamentosByPessoa(Long idPessoa, Long idDepartamento) {
		return dao.buscaDepartamentosByPessoa(idPessoa, idDepartamento);
	}

	public List<PessoaDto> getPessoasByHabilidade(GenericSiacolDto dto, UserFrontDto userFrontDto) {

		return converterPessoa.toListDtoPessoaFisica(dao.getPessoasByHabilidade(dto, userFrontDto));

	}

	public PessoaDto buscaCoordenador(GenericSiacolDto dto) {
		Pessoa pessoa = new Pessoa();
		pessoa = dao.getCoordedandorComHabilidade(dto.getIdDepartamento());
		if (pessoa != null) {
			PessoaDto pessoaDto = new PessoaDto();
			pessoaDto.setId(pessoa.getId());
			pessoaDto.setNome(pessoa.getNome());
			return pessoaDto;
		} else {
			return null;
		}
	}

	public PessoaDto buscaResponsavelAleatoriaDistribuicao(GenericSiacolDto dto, UserFrontDto userFrontDto) {
		
		PessoaDto pessoa = new PessoaDto();
		
		DomainGenericDto responsavel = buscaResponsavelAleatorioParaDistribuicao(dto, userFrontDto);
		pessoa.setId(responsavel.getId());
		pessoa.setNome(responsavel.getNome());
		
		return pessoa;
	}
	
	public DomainGenericDto buscaResponsavelAleatorioParaDistribuicao(GenericSiacolDto dto, UserFrontDto userFrontDto) {
		
		GenericSiacolDto genericDto = new GenericSiacolDto();
		genericDto.setIdDepartamento(dto.getIdDepartamento());
		genericDto.setIdAssunto(dto.getIdAssunto());
		genericDto.setDistribuicaoParaConselheiro(true);
		genericDto.setNumeroProtocolo(dto.getNumeroProtocolo());
		
		DomainGenericDto responsavelDto = new DomainGenericDto();
		responsavelDto.setId(dao.getResponsavelDistribuicao(genericDto, userFrontDto).getId());
		
		String nomeResponsavel = interessadoDao.buscaInteressadoBy(responsavelDto.getId()).getNome();
		responsavelDto.setNome(nomeResponsavel);
		
		
		GenericSiacolDto bloqueioResponsavelDto = new GenericSiacolDto();
		bloqueioResponsavelDto.setIdResponsavelAtual(responsavelDto.getId());
		bloqueioResponsavelDto.setIdDepartamento(dto.getIdDepartamento());
		bloqueioResponsavelDto.setIdAssunto(dto.getIdAssunto());
		bloqueioResponsavelDto.setDistribuicaoParaConselheiro(true);
		dao.bloquearResponsavelParaDistribuicao(bloqueioResponsavelDto);
		
		return responsavelDto;
	}

	public List<DepartamentoDto> listaDepartamentosHabilitado(Long idPessoa) {
		return departamentoConverter.toListDto
				(dao.listaDepartamentosHabilitado(idPessoa));
	}
	
	public List<HabilidadePessoaSiacol> listaPessoaHabilidade(GenericDto dto) {
		return converter.toListModel(dao.listaPessoaHabilidade(dto));
	}

	public boolean ehCoordenadorCoac(Long idPessoa) {
		Departamento departamento = departamentoDao.getBy(new Long(230201));
			return departamento.temCoordenador() ? departamento.getCoordenador().getId().equals(idPessoa) ? true : false : false;
	}


}
