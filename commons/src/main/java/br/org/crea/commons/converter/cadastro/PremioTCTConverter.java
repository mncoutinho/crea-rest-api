package br.org.crea.commons.converter.cadastro;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.commons.ArquivoConverter;
import br.org.crea.commons.dao.cadastro.ParticipantePremioTCTDao;
import br.org.crea.commons.dao.cadastro.empresa.RazaoSocialDao;
import br.org.crea.commons.models.cadastro.PremioTCT;
import br.org.crea.commons.models.cadastro.dtos.premio.PremioTCTDto;
import br.org.crea.commons.models.commons.dtos.ArquivoDto;
import br.org.crea.commons.models.commons.dtos.DomainGenericDto;
import br.org.crea.commons.models.commons.enuns.ModuloSistema;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;
import br.org.crea.commons.util.DateUtils;

public class PremioTCTConverter {

	@Inject
	ArquivoConverter arquivoConverter;

	@Inject
	ParticipantePremioTCTConverter participanteConverter;

	@Inject
	ParticipantePremioTCTDao participanteDao;

	@Inject
	RazaoSocialDao razaoSocialDao;

	public PremioTCT toModel(PremioTCTDto dto) {

		Pessoa pessoa = new Pessoa();
		pessoa.setId(dto.getIdPessoa());

		PremioTCT premioTCT = new PremioTCT();
		premioTCT.setId(dto.getId());

		if (dto.temCampus()) {
			premioTCT.setIdCampusCouchDb(dto.getCampus().getIdString());
			premioTCT.setNomeCampus(dto.getCampus().getNome());
		}

		if (dto.temInstituicao()) {
			premioTCT.setIdInstituicaoEnsinoCouchDb(dto.getInstituicao().getIdString());
			premioTCT.setNomeInstituicaoEnsino(dto.getInstituicao().getNome());
		}

		if (dto.temCurso()) {
			if (!"0".equals(dto.getCurso().getIdString())) {
				premioTCT.setIdCursoCouchDb(dto.getCurso().getIdString());
				premioTCT.setNomeCurso(dto.getCurso().getNome());
			}
		}

		premioTCT.setTitulo(dto.getTitulo());
		premioTCT.setAno(dto.getAno());
		premioTCT.setDataEnvio(new Date());
		premioTCT.setAceite(dto.getAceite());
		premioTCT.setStatus(dto.getStatus());
		premioTCT.setPessoa(pessoa);
		premioTCT.setProtocoloCurso(dto.getProtocoloCurso());
		premioTCT.setDescricaoCurso(dto.getDescricaoCurso());
		premioTCT.setNivel(dto.getNivel());

		return premioTCT;

	}

	public PremioTCTDto toDtoSemParticipantes(PremioTCT premioTCT) {
		PremioTCTDto dto = new PremioTCTDto();
		DomainGenericDto instituicao = new DomainGenericDto();
		DomainGenericDto curso = new DomainGenericDto();
		DomainGenericDto campus = new DomainGenericDto();

		if (premioTCT.temInstituicao()) {
			instituicao.setIdString(premioTCT.getIdInstituicaoEnsinoCouchDb());
			instituicao.setNome(premioTCT.getNomeInstituicaoEnsino());
			instituicao.setDescricao(premioTCT.getNomeInstituicaoEnsino());
		}
		if (premioTCT.temCurso()) {
			curso.setIdString(premioTCT.getIdCursoCouchDb());
			curso.setNome(premioTCT.getNomeCurso());
			curso.setDescricao(premioTCT.getNomeCurso());
		} else {
			curso.setId(0l);
		}
		if (premioTCT.temCampus()) {
			campus.setIdString(premioTCT.getIdCampusCouchDb());
			campus.setNome(premioTCT.getNomeCampus());
			campus.setDescricao(premioTCT.getNomeCampus());
		}
		dto.setCampus(campus);
		dto.setInstituicao(instituicao);
		dto.setCurso(curso);

		if (premioTCT.temPessoa()) {
			dto.setIdPessoa(premioTCT.getPessoa().getId());
			dto.setNomePessoa(premioTCT.getPessoa().getNome());
		}

		dto.setId(premioTCT.getId());
		dto.setAno(premioTCT.getAno());
		dto.setDataEnvio(premioTCT.getDataEnvio());
		dto.setDataEnvioFormatada(DateUtils.format(premioTCT.getDataEnvio(), DateUtils.DD_MM_YYYY));
		dto.setAceite(premioTCT.getAceite());
		dto.setStatus(premioTCT.getStatus());
		dto.setTitulo(premioTCT.getTitulo().toUpperCase());
		dto.setProtocoloCurso((premioTCT.getProtocoloCurso()));
		dto.setDescricaoCurso((premioTCT.getDescricaoCurso()));
		dto.setNivel(premioTCT.getNivel());
		adicionaArquivoTermo(premioTCT, dto);
		adicionaArquivoResumo(premioTCT, dto);
		adicionaArquivo(premioTCT, dto);

		return dto;
	}

	public PremioTCTDto toDtoComParticipantes(PremioTCT premioTCT) {
		PremioTCTDto dto = new PremioTCTDto();
		DomainGenericDto instituicao = new DomainGenericDto();
		DomainGenericDto curso = new DomainGenericDto();
		DomainGenericDto campus = new DomainGenericDto();

		if (premioTCT.temInstituicao()) {
			instituicao.setIdString(premioTCT.getIdInstituicaoEnsinoCouchDb());
			instituicao.setNome(premioTCT.getNomeInstituicaoEnsino());
			instituicao.setDescricao(premioTCT.getNomeInstituicaoEnsino());
		}
		if (premioTCT.temCurso()) {
			curso.setIdString(premioTCT.getIdCursoCouchDb());
			curso.setNome(premioTCT.getNomeCurso());
			curso.setDescricao(premioTCT.getNomeCurso());
		} else {
			curso.setId(0l);
		}
		if (premioTCT.temCampus()) {
			campus.setIdString(premioTCT.getIdCampusCouchDb());
			campus.setNome(premioTCT.getNomeCampus());
			campus.setDescricao(premioTCT.getNomeCampus());
		}
		dto.setCampus(campus);
		dto.setInstituicao(instituicao);
		dto.setCurso(curso);

		if (premioTCT.temPessoa()) {
			dto.setIdPessoa(premioTCT.getPessoa().getId());
			dto.setNomePessoa(premioTCT.getPessoa().getNome());
			dto.setCpfPessoa(premioTCT.getPessoa().getCpfOuCnpj());
		}

		dto.setId(premioTCT.getId());
		dto.setAno(premioTCT.getAno());
		dto.setDataEnvio(premioTCT.getDataEnvio());
		dto.setDataEnvioFormatada(DateUtils.format(premioTCT.getDataEnvio(), DateUtils.DD_MM_YYYY));
		dto.setAceite(premioTCT.getAceite());
		dto.setStatus(premioTCT.getStatus());
		dto.setTitulo(premioTCT.getTitulo().toUpperCase());
		dto.setProtocoloCurso((premioTCT.getProtocoloCurso()));
		dto.setDescricaoCurso((premioTCT.getDescricaoCurso()));
		dto.setAceite(premioTCT.getAceite());
		dto.setNivel(premioTCT.getNivel());
		adicionaArquivoTermo(premioTCT, dto);
		adicionaArquivoResumo(premioTCT, dto);
		adicionaArquivo(premioTCT, dto);

		dto.setListaParticipantes(
				participanteConverter.toListDto(participanteDao.getListParticipantes(premioTCT.getId())));

		return dto;
	}

	public List<PremioTCTDto> toListDtoComParticipantes(List<PremioTCT> listModel) {

		List<PremioTCTDto> listDto = new ArrayList<PremioTCTDto>();

		for (PremioTCT a : listModel) {
			listDto.add(toDtoComParticipantes(a));
		}

		return listDto;

	}

	public List<PremioTCTDto> toListDtoSemParticipantes(List<PremioTCT> listModel) {

		List<PremioTCTDto> listDto = new ArrayList<PremioTCTDto>();

		for (PremioTCT a : listModel) {
			listDto.add(toDtoSemParticipantes(a));
		}

		return listDto;

	}

	private void adicionaArquivoResumo(PremioTCT premioTCT, PremioTCTDto dto) {
		if (premioTCT.temArquivoResumo()) {
			ArquivoDto arquivoResumoDto = arquivoConverter.toDto(premioTCT.getArquivoResumo());
			dto.setArquivoResumo(arquivoResumoDto);
		} else {
			ArquivoDto arquivoResumoDto = new ArquivoDto();
			arquivoResumoDto.setModulo(ModuloSistema.INSTITUICAO);
			arquivoResumoDto.setPrivado(true);
			arquivoResumoDto.setNomeOriginal(" ");
			dto.setArquivoResumo(arquivoResumoDto);
		}
	}

	private void adicionaArquivoTermo(PremioTCT premioTCT, PremioTCTDto dto) {
		if (premioTCT.temArquivoTermo()) {
			ArquivoDto arquivoTermoDto = arquivoConverter.toDto(premioTCT.getArquivoTermo());
			dto.setArquivoTermo(arquivoTermoDto);
		} else {
			ArquivoDto arquivoTermoDto = new ArquivoDto();
			arquivoTermoDto.setModulo(ModuloSistema.INSTITUICAO);
			arquivoTermoDto.setPrivado(true);
			arquivoTermoDto.setNomeOriginal(" ");
			dto.setArquivoTermo(arquivoTermoDto);
		}
	}

	private void adicionaArquivo(PremioTCT premioTCT, PremioTCTDto dto) {
		if (premioTCT.temArquivo()) {
			ArquivoDto arquivoDto = arquivoConverter.toDto(premioTCT.getArquivo());
			dto.setArquivo(arquivoDto);
		} else {
			ArquivoDto arquivoDto = new ArquivoDto();
			arquivoDto.setModulo(ModuloSistema.INSTITUICAO);
			arquivoDto.setPrivado(true);
			arquivoDto.setNomeOriginal(" ");
			dto.setArquivo(arquivoDto);
		}
	}

	public String toDescricaoPapel(String papel) {
		String descricaoPapel = "";

		switch (papel) {
		case "1":
			descricaoPapel = "AUTOR";
			break;
		case "2":
			descricaoPapel = "AVALIADOR";
			break;
		case "3":
			descricaoPapel = "COORIENTADOR";
			break;
		case "4":
			descricaoPapel = "ORIENTADOR";
			break;
		case "5":
			descricaoPapel = "COMISSÃO";
			break;
		}

		return descricaoPapel;
	}

	public String toDescricaoNivel(String nivel) {
		switch (nivel) {
		case "TÉCNICO":
			nivel = "MÉDIO";
			break;
		case "GRADUAÇÃO":
			nivel = "SUPERIOR";
			break;
		}

		return nivel;
	}

}
