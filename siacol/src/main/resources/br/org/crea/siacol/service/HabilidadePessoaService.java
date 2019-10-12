package br.org.crea.siacol.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.cadastro.pessoa.PessoaConverter;
import br.org.crea.commons.dao.cadastro.pessoa.InteressadoDao;
import br.org.crea.commons.dao.cadastro.pessoa.PessoaDao;
import br.org.crea.commons.dao.siacol.HabilidadePessoaDao;
import br.org.crea.commons.models.commons.dtos.GenericDto;
import br.org.crea.commons.models.corporativo.dtos.PessoaDto;
import br.org.crea.commons.models.corporativo.pessoa.IInteressado;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;
import br.org.crea.commons.models.corporativo.pessoa.PessoaFisica;
import br.org.crea.commons.models.siacol.ConfigPessoaSiacol;
import br.org.crea.commons.models.siacol.HabilidadePessoaSiacol;
import br.org.crea.commons.models.siacol.dtos.GenericSiacolDto;
import br.org.crea.commons.models.siacol.dtos.HabilidadePessoaDto;
import br.org.crea.commons.util.DateUtils;
import br.org.crea.siacol.converter.HabilidadePessoaConverter;
import br.org.crea.siacol.dao.ConfigPessoaSiacolDao;

public class HabilidadePessoaService {

	@Inject HabilidadePessoaConverter converter;

	@Inject HabilidadePessoaDao dao;

	@Inject ConfigPessoaSiacolDao daoConfig;

	@Inject PessoaConverter converterPessoa;
	
	@Inject PessoaDao pessoaDao;
	
	@Inject InteressadoDao interessadoDao;

	public Boolean verificaAtivo(Long idPessoa) {
		return dao.verificaAtivo(idPessoa);
	}

	public GenericSiacolDto programaHabilidadeCria(GenericSiacolDto dto) {

		if (dto.getListaId().size() > 1) {
			dto.setListaId(buscaDepartamentosByPessoa(dto.getIdFuncionario(), dto.getIdDepartamento()));
		}
		for (Long id : dto.getListaId()) {
			ConfigPessoaSiacol pessoaConfig = new ConfigPessoaSiacol();

			PessoaFisica pessoa = new PessoaFisica();
			pessoa.setId(dto.getIdPessoa());
			pessoaConfig.setPessoa(pessoa);
			pessoaConfig.setDataInicio(dto.getDatainicio());
			pessoaConfig.setDataFim(dto.getDatafim());
			pessoaConfig.setAtivo(true);
			pessoaConfig.setIdDepartamento(id);

			daoConfig.create(pessoaConfig);

			if (DateUtils.eNoDiaAtual(dto.getDatainicio())) {
				daoConfig.desativaHabilidades();
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
				dto.setDatainicio(pessoaConfig.getDataInicio());
				dto.setDatafim(pessoaConfig.getDataFim());
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

	public List<PessoaDto> getPessoasByHabilidade(GenericSiacolDto dto) {

		return converterPessoa.toListDtoPessoaFisica(dao.getPessoasByHabilidade(dto));

	}

	public PessoaDto buscaCoordenador(GenericSiacolDto dto) {
		Pessoa pessoa = new Pessoa();
		pessoa = dao.getCoordedandorComHabilidade(dto.getIdDepartamento());
		PessoaDto pessoaDto = new PessoaDto();
		pessoaDto.setId(pessoa.getId());
		pessoaDto.setNome(pessoa.getNome());
		return pessoaDto;
	}

	public PessoaDto buscaAnalistaDisponivel(GenericSiacolDto dto) {
		
		PessoaDto pessoa = new PessoaDto();
		
		GenericSiacolDto genericDto = new GenericSiacolDto();
		genericDto.setIdDepartamento(dto.getIdDepartamento());
		genericDto.setIdAssunto(dto.getIdAssunto());
		genericDto.setDistribuicaoParaConselheiro(false);
		
		Long idInteressado = dao.getResponsavelDistribuicao(genericDto).getId();
		
		if(idInteressado != null){
			String nomeInteressado = pessoaDao.buscaNomePessoaFisica(idInteressado);
			pessoa.setId(idInteressado);
			pessoa.setNome(nomeInteressado);
		}
	
		return pessoa.getId() == 0 ? null : pessoa;
	}
	
	public PessoaDto getResponsavelDistribuicao(GenericSiacolDto dto) {
		
		IInteressado responsavel = interessadoDao.buscaInteressadoBy(dao.getResponsavelDistribuicao(dto));
		return converterPessoa.toPessoaInteressadoDto(responsavel);
	}

}
