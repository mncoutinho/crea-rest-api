package br.org.crea.commons.converter.cadastro.pessoa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.dao.art.ArtDao;
import br.org.crea.commons.dao.cadastro.CadastroDao;
import br.org.crea.commons.dao.cadastro.empresa.EmpresaDao;
import br.org.crea.commons.dao.cadastro.empresa.EmpresaEspecialidadeDao;
import br.org.crea.commons.dao.cadastro.pessoa.EnderecoDao;
import br.org.crea.commons.dao.cadastro.pessoa.HomePageDao;
import br.org.crea.commons.dao.cadastro.pessoa.InteressadoDao;
import br.org.crea.commons.dao.cadastro.pessoa.PessoaDao;
import br.org.crea.commons.dao.cadastro.pessoa.TelefoneDao;
import br.org.crea.commons.dao.cadastro.profissional.ProfissionalDao;
import br.org.crea.commons.dao.cadastro.profissional.ProfissionalEspecialidadeDao;
import br.org.crea.commons.models.corporativo.RazaoSocial;
import br.org.crea.commons.models.corporativo.SituacaoRegistro;
import br.org.crea.commons.models.corporativo.dtos.InteressadoWsDto;
import br.org.crea.commons.models.corporativo.dtos.LeigoDto;
import br.org.crea.commons.models.corporativo.dtos.PessoaDto;
import br.org.crea.commons.models.corporativo.dtos.PessoaFisicaDto;
import br.org.crea.commons.models.corporativo.dtos.SituacaoDto;
import br.org.crea.commons.models.corporativo.enuns.TipoPessoa;
import br.org.crea.commons.models.corporativo.pessoa.Empresa;
import br.org.crea.commons.models.corporativo.pessoa.IInteressado;
import br.org.crea.commons.models.corporativo.pessoa.LeigoPF;
import br.org.crea.commons.models.corporativo.pessoa.LeigoPJ;
import br.org.crea.commons.models.corporativo.pessoa.PessoaFisica;
import br.org.crea.commons.models.corporativo.pessoa.PessoaJuridica;
import br.org.crea.commons.models.corporativo.pessoa.Profissional;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.DateUtils;
import br.org.crea.commons.util.StringUtil;

public class PessoaConverter {

	@Inject
	SituacaoPessoaConverter situacaoConverter;

	@Inject
	InteressadoDao interessadoDao;

	@Inject
	CadastroDao cadastroDao;

	@Inject
	ProfissionalEspecialidadeDao profissionalEspecialidadeDao;

	@Inject
	ProfissionalDao profissionalDao;

	@Inject
	ArtDao artDao;

	@Inject
	EmpresaEspecialidadeDao empresaEspecialidadeDao;

	@Inject
	EmpresaDao empresaDao;

	@Inject
	private EnderecoDao enderecoDao;
	
	@Inject
	private EnderecoConverter enderecoConverter;

	@Inject
	PessoaDao pessoaDao;
	
	@Inject
	TelefoneDao telefoneDao;

	@Inject
	TelefoneConverter telefoneConverter;

	@Inject
	private HomePageDao homePageDao;
	
	@Inject
	private HomePageConverter homePageConverter;
	
	@Inject
	HttpClientGoApi httpGoApi;

	public List<PessoaDto> toListDto(List<IInteressado> listModel) {

		List<PessoaDto> listDto = new ArrayList<PessoaDto>();

		for (IInteressado p : listModel) {
			listDto.add(toPessoaInteressadoDto(p));
		}

		return listDto;
	}

	public List<PessoaDto> toListDtoPessoaFisica(List<PessoaFisica> listModel) {

		List<PessoaDto> listDto = new ArrayList<PessoaDto>();

		for (PessoaFisica p : listModel) {
			listDto.add(toPessoaFisicaDto(p));
		}

		return listDto;
	}
	
	public PessoaDto toPessoaFisicaDto(PessoaFisica model) {
		PessoaDto dto = new PessoaDto();

		try {

			dto.setId(model.getId());
			dto.setNome(model.getNome());
			dto.setCpf(model.getCpf());
			dto.setEmail(cadastroDao.getEmailsBy(model.getId()));
			if (model.temSituacao()) {
				dto.setSituacao(situacaoConverter.toDto(model.getSituacao()));
			}
			dto.setTipo(model.getTipoPessoa());
			dto.setCpfOuCnpj(StringUtil.getCnpjCpfFormatado(model.getCpf()));
			dto.setPerfil(model.getDescricaoPerfil());
			dto.setDataCriacaoLogin(populaDataParaString(model.getDataCriacaoLogin()));
			dto.setTelefones(telefoneConverter.toListDto(telefoneDao.getListTelefoneByPessoa(model.getId())));

			if (model.getTipoPessoa().equals(TipoPessoa.PROFISSIONAL)) {
				dto.setTitulo(profissionalEspecialidadeDao.getTituloProfissional(profissionalDao.getBy(model.getId())));
				dto.setQuantidadeQuadroTecnico(profissionalDao.getQuantidadeQuadroTecnico(model.getId()));
				dto.setQuantidadeArts(artDao.getQuantidadeArt(model.getId()));
				
				Profissional profissional = profissionalDao.getBy(model.getId());
				
				if(profissional != null){
					dto.setRegistro(profissional.getRegistro());
				}	
			}
			
			if (model.temIdInstituicao()) {
				dto.setIdInstituicao(model.getIdInstituicao());
			}

		} catch (Exception e) {
			httpGoApi.geraLog("PessoaConverter || toPessoaFisicaDto", StringUtil.convertObjectToJson(model), e);
		}

		return dto;
	}
	
	public InteressadoWsDto toInteressadoWSPessoaFisicaDto(PessoaFisica model) {
		InteressadoWsDto dto = new InteressadoWsDto();
		try {
			dto.setId(model.getId());
			dto.setTipo(model.getTipoPessoa());
		} catch (Exception e) {
			httpGoApi.geraLog("PessoaConverter || toInteressadoWSPessoaFisicaDto", StringUtil.convertObjectToJson(model), e);
		}
		return dto;
	}
	
	public InteressadoWsDto toInteressadoWSPessoaJuridicaDto(PessoaJuridica model) {
		InteressadoWsDto dto = new InteressadoWsDto();
		try {
			dto.setId(model.getId());
			dto.setTipo(model.getTipoPessoa());
		} catch (Exception e) {
			httpGoApi.geraLog("PessoaConverter || toInteressadoWSPessoaJuridicaDto", StringUtil.convertObjectToJson(model), e);
		}
		return dto;
	}

	private String populaDataParaString(Date data) {
		return data != null ? DateUtils.format(data, DateUtils.DD_MM_YYYY) : null;
	}

	public List<PessoaFisicaDto> toListDtoPessoaFisicaDetalhada(List<PessoaFisica> listModel) {

		List<PessoaFisicaDto> listDto = new ArrayList<PessoaFisicaDto>();

		for (PessoaFisica p : listModel) {
			listDto.add(toPessoaFisicaDtoDetalhada(p));
		}

		return listDto;
	}

	public PessoaFisicaDto toPessoaFisicaDtoDetalhada(PessoaFisica model) {
		PessoaFisicaDto dto = new PessoaFisicaDto();

		try {
			dto.setPessoa(toPessoaFisicaDto(model));
			dto.getPessoa().setTelefones(telefoneConverter.toListDto(telefoneDao.getListTelefoneByPessoa(model.getId())));
			dto.getPessoa().setHomePages(homePageConverter.toListDto(homePageDao.getHomePagesByPessoa(model.getId())));
			dto.getPessoa().setEnderecos(enderecoConverter.toListDto(enderecoDao.getListEnderecosValidosPor(model.getId())));
			dto.getPessoa().setEnderecoPostal(enderecoConverter.toDto(enderecoDao.getEnderecoValidoEPostalPor(model.getId())));
			dto.getPessoa().setDataCriacaoLogin(DateUtils.format(model.getDataCriacaoLogin(), DateUtils.DD_MM_YYYY_HH_MM_SS));
			dto.setNomePai(model.getNomePai());
			dto.setNomeMae(model.getNomeMae());
			dto.setNacionalidade(model.getNacionalidade().getDescricao());
			dto.setNaturalidade(model.getNaturalidade().getSigla());
			dto.setLocalidadeNaturalidade(model.getLocalidadeNaturalidade() != null ? model.getLocalidadeNaturalidade().getDescricao() : "");
			dto.setEstadoCivil(model.getEstadoCivil().getDescricao());
			dto.setSexo(model.getTipoSexo().getDescricao());
			dto.setDataNascimento(DateUtils.format(model.getDataNascimento(), DateUtils.DD_MM_YYYY));
			dto.setPortadorDeficiencia(model.getNecessidadeEspecial().getDescricao());
			dto.setTipoSanguineo(model.getFatorRh().getDescricao());
			dto.setDoador(model.getDoador() ? "SIM" : "NAO");
			dto.setPisPasep(model.getPisPasep());
			dto.setIdentidade(model.getIdentidade());
			dto.setEmissorIdentidade(model.getOrgaoEmissorIdentidade().getDescricao());
			dto.setDataEmissaoIdentidade(DateUtils.format(model.getDataExpedicaoIdentidade(), DateUtils.DD_MM_YYYY));
			dto.setTituloEleitoral(model.getTituloEleitoral());
			dto.setZonaEleitoral(model.getZonaEleitoral());
			dto.setSecaoEleitoral(model.getSecaoEleitoral());
			dto.setLocalidadeEleitoral(model.getLocalidadeEleitoral().getDescricao() + " / " + model.getUfEleitoral().getSigla());

		} catch (Exception e) {
			httpGoApi.geraLog("PessoaConverter || toPessoaFisicaDtoDetalhada", StringUtil.convertObjectToJson(model), e);
		}
		

		return dto;
	}
	
	public PessoaDto toPessoaInteressadoDto(IInteressado interessado) {
		PessoaDto dto = new PessoaDto();

		try {
			dto.setId(interessado.getId());
			dto.setTipo(interessado.getTipoPessoa());
			
			if (interessado.getTipoPessoa().equals(TipoPessoa.PROFISSIONAL)) {
				dto.setTitulo(profissionalEspecialidadeDao.getTituloProfissional(profissionalDao.getBy(interessado.getId())));
				dto.setQuantidadeQuadroTecnico(profissionalDao.getQuantidadeQuadroTecnico(interessado.getId()));
				dto.setQuantidadeArts(artDao.getQuantidadeArt(interessado.getId()));
				dto.setMatricula(interessado.getMatricula());
				dto.setRegistro(interessado.getRegistro());
				dto.setPerfil(pessoaDao.getBy(interessado.getId()).getPerfil());

			}
			if (interessado.getTipoPessoa().equals(TipoPessoa.FUNCIONARIO)) {
				dto.setMatricula(interessado.getMatricula());
				dto.setPerfil(pessoaDao.getBy(interessado.getId()).getPerfil());
			}
			if (interessado.getTipoPessoa().equals(TipoPessoa.LEIGOPF)) {
				dto.setRegistro(interessado.getRegistro());
				dto.setPerfil(pessoaDao.getBy(interessado.getId()).getPerfil());
			}
			if (interessado.getTipoPessoa().equals(TipoPessoa.FORMANDO)) {
				dto.setRegistro(interessado.getRegistro());
				dto.setPerfil(pessoaDao.getBy(interessado.getIdPessoa()).getPerfil());
			}
			if (interessado.getTipoPessoa().equals(TipoPessoa.EMPRESA)) {
				dto.setTitulo(empresaEspecialidadeDao.getTituloEmpresa(interessado.getId()));
				dto.setQuantidadeQuadroTecnico(profissionalDao.getQuantidadeQuadroTecnico(interessado.getId()));
				dto.setQuantidadeArts(artDao.getQuantidadeArt(interessado.getId()));
				dto.setRegistro(interessado.getRegistro());
			}
			dto.setEmail(cadastroDao.getEmailsBy(interessado.getId()));
			dto.setTipo(interessado.getTipoPessoa());

			if (interessado.getNomeRazaoSocial() != null) {
				if (!interessado.getNomeRazaoSocial().equals("")) {
					dto.setNome(interessado.getNomeRazaoSocial());
				}
			} else {
				dto.setNome(interessado.getNome());
			}

			if (interessado.getCpfCnpj() != null) {
				dto.setCpf(StringUtil.getCnpjCpfFormatado(interessado.getCpfCnpj()));
				dto.setCnpj(StringUtil.getCnpjCpfFormatado(interessado.getCpfCnpj()));
			}
			dto.setPerfil(interessado.getPerfil());

			dto.setSituacao(situacaoConverter.toDto(interessado.getSituacao()));

		} catch (Exception e) {
			httpGoApi.geraLog("PessoaConverter || toDto", StringUtil.convertObjectToJson(interessado), e);
		}

		return dto;
	}

	public List<PessoaDto> razaoSocialToListPessoaDto(List<RazaoSocial> listModel) {
		List<PessoaDto> listDto = new ArrayList<PessoaDto>();

		for (RazaoSocial r : listModel) {
			listDto.add(razaoSocialToPessoaDto(r));
		}

		return listDto;
	}

	private PessoaDto razaoSocialToPessoaDto(RazaoSocial model) {

		PessoaDto dto = new PessoaDto();

		dto.setId(model.getPessoaJuridica().getId());
		dto.setNome(model.getDescricao());
		dto.setTipo(model.getPessoaJuridica().getTipoPessoa());
		dto.setCnpj(model.getPessoaJuridica().getCnpj());
		dto.setSituacao(situacaoConverter.toDto(model.getPessoaJuridica().getSituacao()));

		if (model.getPessoaJuridica().getTipoPessoa().equals(TipoPessoa.EMPRESA)) {
			dto.setTitulo(empresaEspecialidadeDao.getTituloEmpresa(model.getPessoaJuridica().getId()));
			dto.setQuantidadeQuadroTecnico(profissionalDao.getQuantidadeQuadroTecnico(model.getPessoaJuridica().getId()));
			dto.setQuantidadeArts(artDao.getQuantidadeArt(model.getPessoaJuridica().getId()));
		}

		return dto;
	}

	public PessoaDto toPessoaJuridicaDto(PessoaJuridica model) {
		PessoaDto dto = new PessoaDto();

		dto.setId(model.getId());
		dto.setNome(interessadoDao.buscaDescricaoRazaoSocial(model.getId()));
		dto.setEmail(cadastroDao.getEmailsBy(model.getId()));
		dto.setCnpj(model.getCnpj());
		
		if(model.getSituacao() != null){
			dto.setSituacao(situacaoConverter.toDto(model.getSituacao()));
		}
		
		dto.setTipo(model.getTipoPessoa());
		dto.setCpfOuCnpj(StringUtil.getCnpjCpfFormatado(model.getCnpj()));
		dto.setIdPessoaResponsavel(model.getIdPessoaResponsavel());
		dto.setTipoPj(model.getTipoPJ());

		if (model.getTipoPessoa().equals(TipoPessoa.EMPRESA)) {
			dto.setTitulo(empresaEspecialidadeDao.getTituloEmpresa(model.getId()));
			dto.setQuantidadeQuadroTecnico(profissionalDao.getQuantidadeQuadroTecnico(model.getId()));
			dto.setQuantidadeArts(artDao.getQuantidadeArt(model.getId()));
			
			Empresa empresa = empresaDao.getBy(model.getId());
			
			if(empresa != null){
				dto.setRegistro(empresa.getRegistro());
			}	
		}		

		return dto;
	}

	public List<PessoaDto> toListDtoPessoaJuridica(List<PessoaJuridica> listModel) {
		List<PessoaDto> listDto = new ArrayList<PessoaDto>();

		for (PessoaJuridica p : listModel) {
			listDto.add(toPessoaJuridicaDto(p));
		}

		return listDto;
	}

	public PessoaDto toDtoProfissional(Profissional m) {

		PessoaDto dto = new PessoaDto();
		dto.setId(m.getPessoaFisica().getId());
		dto.setNome(m.getNome());
		dto.setNumeroRNP(m.getNumeroRNP());
		dto.setSituacao(situacaoConverter.toDto(m.getSituacao()));
		dto.setTipo(m.getTipoPessoa());
		dto.setPerfil(m.getPerfil());

		if (m.getTipoPessoa().equals(TipoPessoa.PROFISSIONAL)) {
			dto.setTitulo(profissionalEspecialidadeDao.getTituloProfissional(profissionalDao.getBy(m.getId())));
			dto.setQuantidadeQuadroTecnico(profissionalDao.getQuantidadeQuadroTecnico(m.getId()));
			dto.setQuantidadeArts(artDao.getQuantidadeArt(m.getId()));
		}

		return dto;

	}

	public LeigoPF toLeigoPF(LeigoDto dto) {
		SituacaoRegistro situacao = new SituacaoRegistro();
		situacao.setId(0L);

		PessoaFisica pessoaFisica = new PessoaFisica();
		pessoaFisica.setId(dto.getId());
		pessoaFisica.setCpf(dto.getCpfOuCnpj());
		pessoaFisica.setNome(dto.getNome().toUpperCase());
		pessoaFisica.setTipoPessoa(dto.getTipoPessoa());
		pessoaFisica.setSituacao(situacao);
		pessoaFisica.setIdPessoaResponsavel(dto.getIdPessoaResponsavel());

		LeigoPF leigoPF = new LeigoPF();
		leigoPF.setId(dto.getId());
		leigoPF.setPessoaFisica(pessoaFisica);
		leigoPF.setRegistro(String.valueOf(dto.getId()));
		leigoPF.setCadastroAtivado(true);

		return leigoPF;
	}

	public LeigoPJ toLeigoPJ(LeigoDto dto) {
		SituacaoRegistro situacao = new SituacaoRegistro();
		situacao.setId(0L);

		PessoaJuridica pessoaJuridica = new PessoaJuridica();
		pessoaJuridica.setId(dto.getId());
		pessoaJuridica.setCnpj(dto.getCpfOuCnpj());
		pessoaJuridica.setTipoPessoa(dto.getTipoPessoa());
		pessoaJuridica.setSituacao(situacao);
		pessoaJuridica.setIdPessoaResponsavel(dto.getIdPessoaResponsavel());
		pessoaJuridica.setTipoPJ(dto.getTipoPj());

		LeigoPJ leigoPJ = new LeigoPJ();
		leigoPJ.setId(dto.getId());
		leigoPJ.setPessoaJuridica(pessoaJuridica);
		leigoPJ.setRegistro(String.valueOf(dto.getId()));
		leigoPJ.setCadastroAtivado(true);

		return leigoPJ;
	}
	
	public PessoaDto toDto(PessoaFisica pessoa) {

		PessoaDto dto = new PessoaDto();
		dto.setId(pessoa.getId());
		dto.setNome(pessoa.getNome());
		dto.setCpf(pessoa.getCpf());
		
		if (pessoa.temSituacao()) {
			SituacaoDto situacao = new SituacaoDto();
			situacao.setId(pessoa.getSituacao().getId());
			situacao.setDescricao(pessoa.getSituacao().getDescricao());
			dto.setSituacao(situacao);
		}

		return dto;

	}

}
