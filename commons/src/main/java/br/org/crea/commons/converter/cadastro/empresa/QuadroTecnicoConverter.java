package br.org.crea.commons.converter.cadastro.empresa;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.cadastro.pessoa.PessoaConverter;
import br.org.crea.commons.converter.cadastro.profissional.TituloProfissionalConverter;
import br.org.crea.commons.dao.cadastro.empresa.ResponsavelTecnicoDao;
import br.org.crea.commons.dao.cadastro.pessoa.InteressadoDao;
import br.org.crea.commons.dao.financeiro.FinDividaDao;
import br.org.crea.commons.models.cadastro.QuadroTecnico;
import br.org.crea.commons.models.cadastro.ResponsavelTecnico;
import br.org.crea.commons.models.cadastro.dtos.empresa.QuadroTecnicoDto;
import br.org.crea.commons.models.cadastro.dtos.empresa.QuadroTecnicoEmpresaDto;
import br.org.crea.commons.models.cadastro.dtos.empresa.ResponsavelTecnicoDto;
import br.org.crea.commons.models.cadastro.dtos.profissional.QuadroTecnicoProfissionalDto;
import br.org.crea.commons.models.commons.dtos.DomainGenericDto;
import br.org.crea.commons.models.corporativo.dtos.PessoaDto;
import br.org.crea.commons.models.financeiro.FinDivida;
import br.org.crea.commons.util.DateUtils;

public class QuadroTecnicoConverter {

	@Inject
	private InteressadoDao interessadoDao;

	@Inject
	private PessoaConverter pessoaConverter;

	@Inject
	private FinDividaDao finDividaDao;
	
	@Inject
	private ResponsavelTecnicoDao responsavelTecnicoDao;

	@Inject
	private TituloProfissionalConverter tituloConverter;

	public List<QuadroTecnicoDto> toListDto(List<QuadroTecnico> listModel) {

		List<QuadroTecnicoDto> listDto = new ArrayList<QuadroTecnicoDto>();

		for (QuadroTecnico q : listModel) {
			listDto.add(toDto(q));
		}

		return listDto;
	}

	public QuadroTecnicoDto toDto(QuadroTecnico m) {

		QuadroTecnicoDto dto = new QuadroTecnicoDto();

		if (m.getEmpresa() != null) {
			dto.setEmpresa(pessoaConverter.toPessoaJuridicaDto(m.getEmpresa().getPessoaJuridica()));
		}

		if (m.getProfissional() != null) {
			dto.setProfissional(pessoaConverter.toDtoProfissional(m.getProfissional()));
		}
		dto.setEhResponsavelTecnico(m.getEhResponsavelTecnico());
		dto.setDataInicioQuadro(DateUtils.format(m.getDataInicio(), DateUtils.DD_MM_YYYY));
		dto.setDataFimQuadro(DateUtils.format(m.getDataFim(), DateUtils.DD_MM_YYYY));

		List<ResponsavelTecnicoDto> listResponsavelDto = new ArrayList<ResponsavelTecnicoDto>();

		if (!m.getResponsaveis().isEmpty()) {

			for (ResponsavelTecnico r : m.getResponsaveis()) {
				listResponsavelDto.add(toResponsavelDto(r));
			}
			dto.setResponsaveis(listResponsavelDto);

		} else {

			dto.setResponsaveis(listResponsavelDto);

		}

		return dto;

	}

	private ResponsavelTecnicoDto toResponsavelDto(ResponsavelTecnico m) {

		ResponsavelTecnicoDto dto = new ResponsavelTecnicoDto();

		if (m.getRamoAtividade() != null) {
			dto.setAtividade(m.getRamoAtividade().getAtividade().getDescricao());
			dto.setRamo(m.getRamoAtividade().getRamo().getDescricao());
			dto.setDataInicioRT(DateUtils.format(m.getDataInicio(), DateUtils.DD_MM_YYYY));
			dto.setDataFimRT(DateUtils.format(m.getDataFim(), DateUtils.DD_MM_YYYY));
		}

		return dto;

	}

	public List<QuadroTecnicoProfissionalDto> toListQuadroTecnicoProfissionalDto(List<QuadroTecnico> listModel) {

		List<QuadroTecnicoProfissionalDto> listDto = new ArrayList<QuadroTecnicoProfissionalDto>();

		for (QuadroTecnico q : listModel) {
			listDto.add(toQuadroTecnicoProfissionalDto(q));
		}

		return listDto;

	}

	public QuadroTecnicoProfissionalDto toQuadroTecnicoProfissionalDto(QuadroTecnico quadroTecnico) {

		QuadroTecnicoProfissionalDto dto = new QuadroTecnicoProfissionalDto();
		dto.setIdQuadroTecnico(quadroTecnico.getId());
		dto.setIdProfissional(quadroTecnico.getProfissional().getId());
		if (quadroTecnico.getEmpresa() != null) {
			dto.setIdEmpresa(quadroTecnico.getEmpresa().getPessoaJuridica().getId());
			dto.setEmpresaNome(interessadoDao.buscaDescricaoRazaoSocial(quadroTecnico.getEmpresa().getPessoaJuridica().getId()));
		}
		
		if (quadroTecnico.getJornadaTrabalho() != null){
			dto.setJornadaTrabalho(quadroTecnico.getJornadaTrabalho().toString());
		}
		if (quadroTecnico.getVinculo() != null){
			DomainGenericDto vinculo = new DomainGenericDto();
			vinculo.setId(quadroTecnico.getVinculo().getId());
			vinculo.setDescricao(quadroTecnico.getVinculo().getDescricao());
			dto.setVinculo(vinculo);
		}
		dto.setDataInicio(DateUtils.format(quadroTecnico.getDataInicio(), DateUtils.DD_MM_YYYY));
		dto.setDataFim(DateUtils.format(quadroTecnico.getDataFim(), DateUtils.DD_MM_YYYY));
		dto.setResponsavelTecnico("NAO");
		for (ResponsavelTecnico rt : quadroTecnico.getResponsaveis()) {
			if (quadroTecnico.getDataInicio().compareTo(rt.getDataInicio()) <= 0 && rt.getDataFim() == null) {
				dto.setResponsavelTecnico("SIM");
				break;
			}
		}

		return dto;

	}

	public List<QuadroTecnicoEmpresaDto> toListQuadroTecnicoEmpresaDto(List<QuadroTecnico> listModel) {

		List<QuadroTecnicoEmpresaDto> listDto = new ArrayList<QuadroTecnicoEmpresaDto>();

		for (QuadroTecnico q : listModel) {
			listDto.add(toQuadroTecnicoEmpresaDto(q));
		}

		return listDto;

	}

	public QuadroTecnicoEmpresaDto toQuadroTecnicoEmpresaDto(QuadroTecnico model) {

		QuadroTecnicoEmpresaDto dto = new QuadroTecnicoEmpresaDto();

		dto.setRegistro(model.getProfissional().getRegistro());
		dto.setNome(model.getProfissional().getNome());
		dto.setDataInicioQuadroTecnico(DateUtils.format(model.getDataInicio(), DateUtils.DD_MM_YYYY));
		FinDivida divida = finDividaDao.getUltimaAnuidadePagaPor(model.getProfissional().getId());
		if (divida != null) {
			dto.setAnuidadePaga(divida.getIdentificadorDivida());
		}
		dto.setTitulos(tituloConverter.toListTituloProfissionalDto(model.getProfissional()));

		dto.setResponsavelTecnico("NAO");
		for (ResponsavelTecnico rt : model.getResponsaveis()) {
			if (model.getDataInicio().compareTo(rt.getDataInicio()) <= 0 && rt.getDataFim() == null) {
				dto.setResponsavelTecnico("SIM");
				break;
			}
		}

		return dto;

	}

	public List<QuadroTecnicoDto> toListDtoDadosGerais(List<QuadroTecnico> listModel) {
		List<QuadroTecnicoDto> listDto = new ArrayList<QuadroTecnicoDto>();

		for (QuadroTecnico q : listModel) {
			listDto.add(toDtoDadosGerais(q));
		}

		return listDto;
	}

	private QuadroTecnicoDto toDtoDadosGerais(QuadroTecnico m) {
		QuadroTecnicoDto dto = new QuadroTecnicoDto();
		
		dto.setId(m.getId());

		if (m.getProfissional() != null) {
			dto.setProfissional(pessoaConverter.toDtoProfissional(m.getProfissional()));
			dto.setPossuiDivida(finDividaDao.pessoaPossuiDivida(dto.getProfissional().getId()));
		}
		
		if (m.getEmpresa() != null) {
			PessoaDto empresa = new PessoaDto();
			empresa.setId(m.getEmpresa().getId());
			empresa.setNome(interessadoDao.buscaDescricaoRazaoSocial(m.getEmpresa().getId()));
			
			dto.setEmpresa(empresa);
		}
		
		dto.setEhResponsavelTecnico(responsavelTecnicoDao.ehResponsavelTecnico(m.getId()));
		dto.setDataInicioQuadro(DateUtils.format(m.getDataInicio(), DateUtils.DD_MM_YYYY));
		dto.setBaixado(m.getDataFim() != null);

		return dto;

	}

	public QuadroTecnicoDto toDtoDetalhado(QuadroTecnico m) {
		QuadroTecnicoDto dto = new QuadroTecnicoDto();
		
		if (m.getProfissional() != null) {
			dto.setProfissional(pessoaConverter.toDtoProfissional(m.getProfissional()));
		}
		if (m.getEmpresa() != null) {
			dto.setEmpresa(pessoaConverter.toPessoaJuridicaDto(m.getEmpresa().getPessoaJuridica()));
		}
		dto.setDataFimQuadro(DateUtils.format(m.getDataFim(), DateUtils.DD_MM_YYYY));
		dto.setDataInicioQuadro(DateUtils.format(m.getDataInicio(), DateUtils.DD_MM_YYYY));
		dto.setDataAdmissao(DateUtils.format(m.getDataAdmissao(), DateUtils.DD_MM_YYYY));
		dto.setDataContrato(DateUtils.format(m.getDataContrato(), DateUtils.DD_MM_YYYY));
		dto.setDataValidadeContrato(DateUtils.format(m.getDataValidadeContrato(), DateUtils.DD_MM_YYYY));
		dto.setDataDesligamento(DateUtils.format(m.getDataDesligamento(), DateUtils.DD_MM_YYYY));
		dto.setJornadaTrabalho(m.getJornadaTrabalho() != null ? String.valueOf(m.getJornadaTrabalho()) : null);
		dto.setRemuneracao(m.getRemuneracao() != null ? String.valueOf(m.getRemuneracao()) : null);
		
		if(m.getVinculo() != null) {
			DomainGenericDto vinculo = new DomainGenericDto();
			vinculo.setDescricao(m.getVinculo().getDescricao());
			dto.setVinculo(vinculo);
		}
		
		dto.setEhResponsavelTecnico(responsavelTecnicoDao.ehResponsavelTecnico(m.getId()));
		if (dto.getEhResponsavelTecnico()) {
			
			
			List<ResponsavelTecnicoDto> listaRT = new ArrayList<ResponsavelTecnicoDto>(); 
			if (!m.getResponsaveis().isEmpty()) {
				for (ResponsavelTecnico rt : m.getResponsaveis()) {
					if (rt.getDataFim() == null) {
						listaRT.add(toResponsavelTecnicoDto(rt));
					}
				}
				dto.setResponsaveis(listaRT);
			}
		}

		dto.setBaixado(m.getDataFim() != null);

		return dto;
	}

	private ResponsavelTecnicoDto toResponsavelTecnicoDto(ResponsavelTecnico rt) {
		ResponsavelTecnicoDto dto = new ResponsavelTecnicoDto();
		
		dto.setId(rt.getId());
		
		dto.setDataInicioRT(DateUtils.format(rt.getDataInicio(), DateUtils.DD_MM_YYYY));
		dto.setDataFimRT(DateUtils.format(rt.getDataFim(), DateUtils.DD_MM_YYYY));
		
		if (rt.temRamo()) {
			dto.setRamo(rt.getRamoAtividade().getRamo().getDescricao());
			dto.setAtividade(rt.getRamoAtividade().getAtividade().getDescricao());
		}
		if (rt.temAtividade()) {
			dto.setAtividade(rt.getRamoAtividade().getAtividade().getDescricao());
		}
		return dto;
	}

}
