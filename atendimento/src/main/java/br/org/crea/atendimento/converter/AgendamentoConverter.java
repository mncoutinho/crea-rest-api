package br.org.crea.atendimento.converter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.dao.cadastro.domains.DepartamentoDao;
import br.org.crea.commons.dao.cadastro.pessoa.InteressadoDao;
import br.org.crea.commons.models.atendimento.AgendamentoMobile;
import br.org.crea.commons.models.atendimento.AssuntoMobile;
import br.org.crea.commons.models.atendimento.StatusAgendamentoMobile;
import br.org.crea.commons.models.atendimento.dtos.AgendamentoDto;
import br.org.crea.commons.models.atendimento.dtos.HorariosDisponiveisDto;
import br.org.crea.commons.models.cadastro.Departamento;
import br.org.crea.commons.models.commons.dtos.StatusDto;
import br.org.crea.commons.models.corporativo.Assunto;
import br.org.crea.commons.models.corporativo.dtos.AssuntoDto;
import br.org.crea.commons.models.corporativo.dtos.PessoaDto;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.DateUtils;
import br.org.crea.commons.util.StringUtil;

public class AgendamentoConverter {


	@Inject
	InteressadoDao interessadoDao;

	@Inject
	HttpClientGoApi httpGoApi;
	
	@Inject
	DepartamentoDao departamentoDao;

	public List<AgendamentoDto> toListDto(List<AgendamentoMobile> listModel) {

		List<AgendamentoDto> listDto = new ArrayList<AgendamentoDto>();

		for (AgendamentoMobile a : listModel) {
			listDto.add(toDto(a));
		}

		return listDto;

	}

	public AgendamentoMobile toModel(AgendamentoDto dto, Pessoa pessoa) {

		AgendamentoMobile model = new AgendamentoMobile();

		Assunto assunto = new Assunto();
		assunto.setId(dto.getAssunto().getId());
		model.setAssunto(assunto);
		model.setIdPessoa(pessoa.getId());

		if (dto.temAssunto()) {
			
			StatusAgendamentoMobile status = new StatusAgendamentoMobile();
			status.setId(dto.getAssunto().getId());
			model.setStatus(status);
		}

		model.setDataAgendamento(dto.getDataAgendamento());
		model.setTurno(dto.getTurno());
		model.setSenha(dto.getSenha());
		model.setTelefone(dto.getTelefone());

		return model;
	}

	public AgendamentoDto toDto(AgendamentoMobile model) {

		AgendamentoDto dto = new AgendamentoDto();

		try {
			dto.setId(model.getId());
			dto.setDataAgendamento(model.getDataAgendamento());
			dto.setTurno(model.getTurno());
			dto.setDataFormatada(DateUtils.format(model.getDataAgendamento(), DateUtils.DD_MM_YYYY));
			dto.setDiaFormatado(DateUtils.format(model.getDataAgendamento(), DateUtils.EEEE));
			dto.setHoraFormatada(DateUtils.format(model.getDataAgendamento(), DateUtils.HH_MM));
			dto.setHoraChegada(DateUtils.format(model.getHorarioChegada(), DateUtils.HH_MM));
			dto.setHoraInicio(DateUtils.format(model.getHorarioInicio(), DateUtils.HH_MM));
			dto.setNovaData(DateUtils.format(model.getDataUpdate(), DateUtils.HH_MM));
			
			if(model.getIdDepartamento() != null){
				
				dto.setIdUnidadeAtendimento(model.getIdDepartamento());
				Departamento departamento = new Departamento();
				departamento  = departamentoDao.getBy(model.getIdDepartamento());
				dto.setSiglaLocalAtendimento(departamento.getSigla());
				dto.setLocalAtendimento(departamento.getNomeExibicao());				
			}

			dto.setTelefone(model.getTelefone());
			dto.setSenha(model.getSenha());
			
			PessoaDto pessoaDto = new PessoaDto();

			pessoaDto.setNome(model.getNome());
			if(model.getCpfOuCnpj() != null){
				pessoaDto.setCnpj(StringUtil.getCnpjCpfFormatado(model.getCpfOuCnpj()));
				pessoaDto.setCpf(StringUtil.getCnpjCpfFormatado(model.getCpfOuCnpj()));
			}
			if(model.getEmail() != null){
				pessoaDto.setEmail(model.getEmail());
			}

			dto.setPessoa(pessoaDto);

			populaFuncionario(model, dto);
			populaAssunto(model, dto);
			dto.setStatus(populaStatus(model));

		} catch (Exception e) {
			httpGoApi.geraLog("AgendamentoConverter || toDto", StringUtil.convertObjectToJson(model), e);
		}

		return dto;
	}


	private void populaFuncionario(AgendamentoMobile model, AgendamentoDto dto) {

		try {
			if (model.temFuncionario()) {
				dto.setGuiche(model.getGuiche());
				PessoaDto pessoa = new PessoaDto();
				pessoa.setId(model.getFuncionario().getId());
				pessoa.setNome(model.getFuncionario().getPessoaFisica().getNome());
				
				dto.setFuncionario(pessoa);
				dto.setIdFuncionario(pessoa.getId());
			}
		} catch (Throwable e) {
			httpGoApi.geraLog("AgendamentoConverter || populaFuncionario", StringUtil.convertObjectToJson(model), e);
		}

	}

	private StatusDto populaStatus(AgendamentoMobile model) {

		StatusDto statusDto = new StatusDto();
		statusDto.setId(model.getStatus().getId());
		statusDto.setDescricao(model.getStatus().getDescricao());
		return statusDto;
	}

	private void populaAssunto(AgendamentoMobile model, AgendamentoDto dto) {

		try {
			if (model.temAssunto()) {
				AssuntoDto assuntoDto = new AssuntoDto();
				assuntoDto.setId(model.getAssunto().getId());
				assuntoDto.setDescricao(model.getAssunto().getDescricao());
//				assuntoDto.setLinkDocumentacao(model.getAssunto().getLinkDocumentacao());
				dto.setAssunto(assuntoDto);
			}
		} catch (Throwable e) {
			httpGoApi.geraLog("AgendamentoConverter || populaAssunto", StringUtil.convertObjectToJson(model), e);
		}

	}

	public List<AssuntoDto> toListAssuntoDto(List<AssuntoMobile> assuntos) {
		List<AssuntoDto> listDto = new ArrayList<AssuntoDto>();

		for (AssuntoMobile m : assuntos) {
			AssuntoDto dto = new AssuntoDto();

			dto.setId(m.getAssunto().getId());
			dto.setDescricao(m.getAssunto().getDescricao());
//			dto.setLinkDocumentacao(m.getAssunto().getLinkDocumentacao());
			listDto.add(dto);
		}

		return listDto;
	}

	public List<HorariosDisponiveisDto> toListDisponiveisDto(List<Date> listDatas) {

		List<HorariosDisponiveisDto> listDto = new ArrayList<HorariosDisponiveisDto>();

		for (Date d : listDatas) {

			HorariosDisponiveisDto dto = new HorariosDisponiveisDto();
			dto.setHorario(d);
			dto.setDataFormatada(DateUtils.format(d, DateUtils.DD_MM_YYYY));
			dto.setDiaFormatado(DateUtils.format(d, DateUtils.EEEE));
			dto.setHoraFormatada(DateUtils.format(d, DateUtils.HH_MM));

			listDto.add(dto);
		}
		return listDto;
	}

}
