package br.org.crea.commons.converter.cadastro.profissional;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.cadastro.empresa.QuadroTecnicoConverter;
import br.org.crea.commons.converter.cadastro.pessoa.HistoricoConverter;
import br.org.crea.commons.converter.cadastro.pessoa.SituacaoPessoaConverter;
import br.org.crea.commons.dao.art.ArtDao;
import br.org.crea.commons.dao.cadastro.empresa.RazaoSocialDao;
import br.org.crea.commons.dao.cadastro.pessoa.EnderecoDao;
import br.org.crea.commons.dao.cadastro.pessoa.HistoricoDao;
import br.org.crea.commons.dao.cadastro.pessoa.InteressadoDao;
import br.org.crea.commons.dao.cadastro.profissional.CarteiraDao;
import br.org.crea.commons.dao.cadastro.profissional.ProfissionalDao;
import br.org.crea.commons.dao.cadastro.profissional.ProfissionalEspecialidadeDao;
import br.org.crea.commons.models.cadastro.Carteira;
import br.org.crea.commons.models.cadastro.QuadroTecnico;
import br.org.crea.commons.models.cadastro.dtos.empresa.QuadroTecnicoDto;
import br.org.crea.commons.models.cadastro.dtos.profissional.ProfissionalDto;
import br.org.crea.commons.models.commons.dtos.PesquisaGenericDto;
import br.org.crea.commons.models.corporativo.dtos.PessoaDto;
import br.org.crea.commons.models.corporativo.pessoa.Profissional;
import br.org.crea.commons.util.DateUtils;

public class ProfissionalConverter {

	@Inject
	SituacaoPessoaConverter situacaoConverter;

	@Inject
	InteressadoDao interessadoDao;

	@Inject
	ProfissionalDao profissionalDao;

	@Inject
	EnderecoDao enderecoDao;

	@Inject
	RazaoSocialDao razaoSocialDao;

	@Inject
	ArtDao artDao;

	@Inject
	ProfissionalEspecialidadeDao profissionalEspecialidadeDao;

	@Inject
	private CarteiraDao carteiraDao;
	
	@Inject
	private TituloProfissionalConverter tituloProfissionalConverter;
	
	@Inject
	private HistoricoDao historicoDao;
	
	@Inject
	private HistoricoConverter historicoConverter;
	
	@Inject
	private QuadroTecnicoConverter quadroTecnicoConverter;
	
	public ProfissionalDto toProfissionalDto(Profissional model) {
		ProfissionalDto dto = new ProfissionalDto();

		dto.setId(model.getId());
		dto.setNumeroRNP(model.getNumeroRNP());
		dto.setNome(model.getNome());
		dto.setCpf(model.getCpfCnpj());
		dto.setSituacao(situacaoConverter.toDto(model.getSituacao()));
		dto.setTitulo(profissionalEspecialidadeDao.getTituloProfissional(model));
		dto.setQuantidadeQuadroTecnico(profissionalDao.getQuantidadeQuadroTecnico(model.getId()));
		dto.setQuantidadeArts(artDao.getQuantidadeArt(model.getId()));
		

		if (model.situacaoEhInativa()) {
			dto.setAtivo(false);
		} else {
			dto.setAtivo(true);
			dto.setRegular(interessadoDao.verificaSeEmpresaOuProfissionalEstaRegular(model.getId()));
		}

		return dto;
	}

	public List<ProfissionalDto> toListDtoProfissional(List<Profissional> listModel) {
		List<ProfissionalDto> listDto = new ArrayList<ProfissionalDto>();

		for (Profissional p : listModel) {
			listDto.add(toProfissionalDto(p));
		}

		return listDto;
	}

	public List<QuadroTecnicoDto> toListDtoQuadroTecnico(List<QuadroTecnico> listModel) {
		
		List<QuadroTecnicoDto> listDto = new ArrayList<QuadroTecnicoDto>();

		if (listModel != null) {
			for (QuadroTecnico q : listModel) {
				listDto.add(toQuadroTecnicoDto(q));
			}
		}
		return listDto;
	}

	public QuadroTecnicoDto toQuadroTecnicoDto(QuadroTecnico model) {

		QuadroTecnicoDto dto = new QuadroTecnicoDto();

		dto.setId(model.getId());

		PessoaDto profissionalDto = new PessoaDto();
		profissionalDto.setId(model.getProfissional().getId());
		profissionalDto.setNome(interessadoDao.buscaProfissionalBy(model.getProfissional().getId()).getNome());
		dto.setProfissional(profissionalDto);

		PessoaDto empresaDto = new PessoaDto();
		empresaDto.setId(model.getEmpresa().getId());
		empresaDto.setNome(razaoSocialDao.buscaDescricaoRazaoSocial(model.getEmpresa().getId()));
		dto.setEmpresa(empresaDto);

		return dto;
	}

	public ProfissionalDto toProfissionalDtoDetalhado(Profissional model) {
		ProfissionalDto dto = toProfissionalDto(model);

		dto.setNomeSocial(model.getPessoaFisica().getNomeSocial());
		dto.setDataRegistro(DateUtils.format(model.getDataRegistro(), DateUtils.DD_MM_YYYY));
		dto.setDataVisto(DateUtils.format(model.getDataVisto(), DateUtils.DD_MM_YYYY));

		Carteira carteira = carteiraDao.buscaCarteiraAtivaPorProfissional(model.getRegistro());
		if (carteira != null) {
			dto.setCarteira(carteira.getNumeroCarteira());
			dto.setTipoCarteira(carteira.getTipoCarteira().getDescricao());
			dto.setExpedicaoCarteira(DateUtils.format(carteira.getDataEmissao(), DateUtils.DD_MM_YYYY));
			dto.setUfCarteira(carteira.getUf().getSigla());
		}

		dto.setTitulos(tituloProfissionalConverter.toListTituloProfissionalDto(model));
		dto.setHistoricos(historicoConverter.toListHistoricoDto(historicoDao.getHistoricosByPessoa(model.getPessoaFisica().getId())));
		dto.setAnotacoesEspeciais(model.getAnotacoesEspeciais());
		dto.setObservacoesProfissional(model.getObservacoesEspeciais());
		
		PesquisaGenericDto pesquisaGenericDto = new PesquisaGenericDto();
		pesquisaGenericDto.setIdPessoa(model.getPessoaFisica().getId());
		pesquisaGenericDto.setTipoPessoa(model.getTipoPessoa().name());
		
		dto.setQuadrosTecnicos(this.quadroTecnicoConverter.toListQuadroTecnicoProfissionalDto(profissionalDao.getQuadroTecnicoDetalhado(pesquisaGenericDto)));

		return dto;
	}

	public List<ProfissionalDto> toListDtoProfissionalDetalhado(List<Profissional> listProfissional) {
		List<ProfissionalDto> listDto = new ArrayList<ProfissionalDto>();

		for (Profissional p : listProfissional) {
			listDto.add(toProfissionalDtoDetalhado(p));
		}

		return listDto;
	}
}
