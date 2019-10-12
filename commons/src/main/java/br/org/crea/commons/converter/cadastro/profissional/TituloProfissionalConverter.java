package br.org.crea.commons.converter.cadastro.profissional;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.cadastro.pessoa.EnderecoConverter;
import br.org.crea.commons.dao.cadastro.pessoa.EnderecoDao;
import br.org.crea.commons.dao.cadastro.pessoa.InteressadoDao;
import br.org.crea.commons.dao.cadastro.profissional.ProfissionalEspecialidadeDao;
import br.org.crea.commons.models.cadastro.RlProfissionalEspecialidade;
import br.org.crea.commons.models.cadastro.dtos.profissional.TituloProfissionalDto;
import br.org.crea.commons.models.commons.Endereco;
import br.org.crea.commons.models.corporativo.pessoa.Profissional;
import br.org.crea.commons.util.DateUtils;

public class TituloProfissionalConverter {

	@Inject
	InteressadoDao interessadoDao;

	@Inject
	ProfissionalEspecialidadeDao especialidadeDao;

	@Inject
	EnderecoDao enderecoDao;

	@Inject
	EnderecoConverter enderecoConverter;

	public List<TituloProfissionalDto> toListTituloProfissionalDto(Profissional profissional) {
		List<TituloProfissionalDto> listDto = new ArrayList<TituloProfissionalDto>();

		List<RlProfissionalEspecialidade> listProfissionalEspecialidade = new ArrayList<RlProfissionalEspecialidade>();
		listProfissionalEspecialidade = especialidadeDao.getProfissionalEspecialidade(profissional.getId());

		for (RlProfissionalEspecialidade model : listProfissionalEspecialidade) {

			TituloProfissionalDto dto = toTituloEspecialidadeDto(model);

			if (model.temTituloDoConfeaEstaCadastrado()) {
				dto.setOpcaoVoto(model.isOpcao());
				dto.setIdTitulo(model.getTitulo().getId());
				dto.setCodigoTituloConfea(model.getConfeaTitulo().getId());
				dto.setTituloConfea(model.getConfeaDescricao(profissional.getSexo()));  
			}
			listDto.add(dto);     
		}
		return listDto;
	}

	public TituloProfissionalDto toTituloEspecialidadeDto(RlProfissionalEspecialidade model) {
		TituloProfissionalDto dto = new TituloProfissionalDto();

		dto.setCodigoTituloCrea(model.getEspecialidade().getId());
		if (model.getTitulo().getDescricao().equals(model.getEspecialidade().getDescricao())) {
			dto.setTituloCrea(model.getTitulo().getDescricao());
		} else {
			dto.setTituloCrea(model.getTitulo().getDescricao() + " / " + model.getEspecialidade().getDescricao());
		}
		dto.setIdModalidade(model.getIdModalidade());
		dto.setAtribuicoesTitulo(especialidadeDao.getAtribuicoesDoTituloPorEspecialidade(model).getAtribuicoesTitulo());
		dto.setDispositivosAtribuicaoTitulo(model.getDispositivosAtribuicoesTitulo());
		dto.setEscolaDeObtencaoTitulo(interessadoDao.buscaDescricaoRazaoSocial(model.getCodigoIntituicaoEnsino()));
		dto.setEscolaridade(model.getEscolaridade().getDescricao());
		dto.setTipoTitulo(model.getTipoTitulo().getDescricao());
		dto.setDataExpedicao(DateUtils.format(model.getDataInclusao(), DateUtils.DD_MM_YYYY));
		dto.setDataValidade(DateUtils.format(model.getDataValidade(), DateUtils.DD_MM_YYYY));
		dto.setDiploma(model.getDiploma());
		dto.setDataExpedicaoDiploma(DateUtils.format(model.getDataExpedicaoDiploma(), DateUtils.DD_MM_YYYY));
		dto.setDataFormatura(DateUtils.format(model.getDataFormatura(), DateUtils.DD_MM_YYYY));
		dto.setDataColacaoGrau(DateUtils.format(model.getDataColacaoGrau(), DateUtils.DD_MM_YYYY));
		dto.setDataCancelamento(DateUtils.format(model.getDataCancelamento(), DateUtils.DD_MM_YYYY));
		dto.setMotivo(model.getMotivo());
		dto.setApostila(model.getApostila());
		dto.setObservacao(model.getObservacao());

		Endereco enderecoEscola = enderecoDao.getEnderecoValidoEPostalPor(model.getCodigoIntituicaoEnsino());
		if (enderecoEscola != null) {
			dto.setEstadoEscolaObtencaoTitulo(enderecoEscola.getUf().getDescricao());
		}

		return dto;

	}


}
