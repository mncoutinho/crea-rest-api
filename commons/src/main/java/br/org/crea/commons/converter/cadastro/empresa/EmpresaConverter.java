package br.org.crea.commons.converter.cadastro.empresa;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.cadastro.domains.EmailConverter;
import br.org.crea.commons.converter.cadastro.pessoa.EnderecoConverter;
import br.org.crea.commons.converter.cadastro.pessoa.HistoricoConverter;
import br.org.crea.commons.converter.cadastro.pessoa.SituacaoPessoaConverter;
import br.org.crea.commons.converter.cadastro.pessoa.TelefoneConverter;
import br.org.crea.commons.converter.financeiro.DividaConverter;
import br.org.crea.commons.dao.art.ArtDao;
import br.org.crea.commons.dao.cadastro.EmailDao;
import br.org.crea.commons.dao.cadastro.empresa.CapitalSocialDao;
import br.org.crea.commons.dao.cadastro.empresa.EmpresaDao;
import br.org.crea.commons.dao.cadastro.empresa.EmpresaEspecialidadeDao;
import br.org.crea.commons.dao.cadastro.empresa.ObjetoSocialDao;
import br.org.crea.commons.dao.cadastro.empresa.RamoAtividadeDao;
import br.org.crea.commons.dao.cadastro.empresa.ResponsavelTecnicoDao;
import br.org.crea.commons.dao.cadastro.empresa.TipoClasseEmpresaDao;
import br.org.crea.commons.dao.cadastro.pessoa.EnderecoDao;
import br.org.crea.commons.dao.cadastro.pessoa.HistoricoDao;
import br.org.crea.commons.dao.cadastro.pessoa.InteressadoDao;
import br.org.crea.commons.dao.cadastro.pessoa.TelefoneDao;
import br.org.crea.commons.dao.cadastro.profissional.ProfissionalDao;
import br.org.crea.commons.dao.financeiro.FinDividaDao;
import br.org.crea.commons.models.cadastro.ObjetoSocial;
import br.org.crea.commons.models.cadastro.dtos.empresa.CapitalSocialDto;
import br.org.crea.commons.models.cadastro.dtos.empresa.EmpresaDto;
import br.org.crea.commons.models.corporativo.pessoa.Empresa;
import br.org.crea.commons.models.corporativo.pessoa.TipoClasseEmpresa;
import br.org.crea.commons.models.financeiro.FinDivida;
import br.org.crea.commons.util.DateUtils;

public class EmpresaConverter {
	
	@Inject SituacaoPessoaConverter situacaoConverter;
	
	@Inject EnderecoConverter enderecoConverter;
	
	@Inject DividaConverter dividaConverter;
	
	@Inject InteressadoDao interessadoDao;
	
	@Inject EnderecoDao enderecoDao;
	
	@Inject 
	private ObjetoSocialDao objetoSocialDao;
	
	@Inject
	private ObjetoSocialConverter objetoSocialConverter;
	
	@Inject ProfissionalDao profissionalDao;
	
	@Inject FinDividaDao dividaDao;
	
	@Inject ArtDao artDao;
	
	@Inject EmpresaEspecialidadeDao empresaEspecialidadeDao;
	
	@Inject
	private TipoClasseEmpresaDao tipoClasseEmpresaDao;
	
	@Inject
	private TelefoneDao telefoneDao;
	
	@Inject
	private TelefoneConverter telefoneConverter;
	
	@Inject
	private EmailDao emailDao;
	
	@Inject
	private EmailConverter emailConverter;
	
	@Inject
	private CapitalSocialDao capitalSocialDao;
	
	@Inject
	private CapitalSocialConverter capitalSocialConverter;
	
	@Inject
	private RamoAtividadeDao ramoAtividadeDao;
	
	@Inject
	private RamoAtividadeConverter ramoAtividadeConverter;
	
	@Inject
	private HistoricoDao historicoDao;
	
	@Inject
	private HistoricoConverter historicoConverter;
	
	@Inject
	private ResponsavelTecnicoDao responsavelTecnicoDao;
	
	@Inject
	private ResponsavelTecnicoConverter responsavelTecnicoConverter;

	@Inject
	private QuadroTecnicoConverter quadroTecnicoConverter;
	
	@Inject
	private EmpresaDao empresaDao;

	public EmpresaDto toEmpresaDto(Empresa model) {
		EmpresaDto dto = new EmpresaDto();
		
		dto.setId(model.getId());
		dto.setNome(interessadoDao.buscaDescricaoRazaoSocial(model.getId()));
		dto.setCnpj(model.getCpfCnpj());
		dto.setSituacao(situacaoConverter.toDto(model.getSituacao()));
		dto.setRamo(empresaEspecialidadeDao.getTituloEmpresa(model.getId()));
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
	
	public List<EmpresaDto> toListDtoEmpresa(List<Empresa> listModel) {
		List<EmpresaDto> listDto = new ArrayList<EmpresaDto>();
		
		for(Empresa p  : listModel){
			listDto.add(toEmpresaDto(p));
		}
		
		return listDto;
	}
	
	public EmpresaDto toDto(Empresa model) {
		EmpresaDto dto = new EmpresaDto();
		
		dto.setId(model.getId());
		dto.setNome(interessadoDao.buscaDescricaoRazaoSocial(model.getId()));
		dto.setNomeFantasia(model.getPessoaJuridica().getNomeFantasia());
		dto.setCnpj(model.getCpfCnpj());
		dto.setSituacao(situacaoConverter.toDto(model.getSituacao()));
		dto.setEnderecoPostal(enderecoConverter.toDto(enderecoDao.getEnderecoValidoEPostalPor(model.getId())));
		dto.setEnderecosValidos(enderecoConverter.toListDto(enderecoDao.getListEnderecosValidosPor(model.getId())));
		dto.setObjetoSocial(getObjetoSocial(model) != null ? getObjetoSocial(model).getDescricao() : null );
		dto.setListRamosComRt(empresaEspecialidadeDao.getRamosComResponsavelTecnicoPor(model.getId()));
		dto.setListRamosSemRt(empresaEspecialidadeDao.getRamosSemResponsavelTecnicoPor(model.getId()));
		dto.setAnuidade(dividaConverter.toDividaAnuidadeDto(model.getId()));
		dto.setDataVisto(model.getDataVisto() != null ? model.getDataVisto() : null);
		dto.setDataVistoFormatada(dto.getDataVisto() != null ? DateUtils.format(model.getDataVisto(), DateUtils.DD_MM_YYYY) : "-");
		dto.setDataRegistro(DateUtils.format(model.getDataCadastro(), DateUtils.DD_MM_YYYY));
		dto.setDataValidade(DateUtils.format(model.getDataValidade(), DateUtils.DD_MM_YYYY));
		dto.setTipoEmpresa(model.getTipoEmpresa().getDescricao());
		dto.setTipoCategoriaRegistro(model.getTipoCategoriaRegistro().getDescricao());
		dto.setTipoGrupo(model.getTipoGrupo().getDescricao());
		List<TipoClasseEmpresa> lista = this.tipoClasseEmpresaDao.buscaTipoClasseEmpresaByRegistro(model.getId());
		if (lista != null && lista.size() > 0) {
			TipoClasseEmpresa tipoClasseEmpresa = lista.get(0);
			dto.setCodigoClasseEmpresa(tipoClasseEmpresa.getId());
			dto.setDescricaoClasseEmpresa(tipoClasseEmpresa.getDescricao());
		}
		
		FinDivida taxaRegistro = dividaDao.getTaxaQuitadaRegistroEmpresa(model.getId());
		dto.setDataQuitacaoTaxaRegistro(taxaRegistro != null ? DateUtils.format(taxaRegistro.getDataQuitacao(), DateUtils.DD_MM_YYYY) : "-");
		
		return dto;
	}
	
	public ObjetoSocial getObjetoSocial(Empresa model) {
		
		return model.getSituacao().getId() != 5 ? objetoSocialDao.getUltimoObjetoSocialEmpresaCadastradaPor(model.getId())
				: objetoSocialDao.getUltimoObjetoSocialEmpresaNovaInscritaPor(model.getId());
	}
	
	public List<EmpresaDto> toListDtoEmpresaDetalhada(List<Empresa> listModel) {
		List<EmpresaDto> listDto = new ArrayList<EmpresaDto>();
		
		for(Empresa p  : listModel){
			listDto.add(toEmpresaDetalhadaDto(p));
		}
		
		return listDto;
	}
	
	public EmpresaDto toEmpresaDetalhadaDto(Empresa model) {
		EmpresaDto dto = this.toDto(model);
		
		dto.setEnderecoPostal(null);
		dto.setAnuidade(null);
		dto.setObservacoes(model.getObservacoes());
		dto.setDataExpRegistroFormatada(model.getDataExpedicaoRegistro() != null ? DateUtils.format(model.getDataExpedicaoRegistro(), DateUtils.DD_MM_YYYY) : "-");
		dto.setDataRegistro(DateUtils.format(model.getDataCadastro(), DateUtils.DD_MM_YYYY));
		dto.setDataValidade(DateUtils.format(model.getDataValidade(), DateUtils.DD_MM_YYYY));
		dto.setTipoEmpresa(model.getTipoEmpresa().getDescricao());
		dto.setTipoCategoriaRegistro(model.getTipoCategoriaRegistro().getDescricao());
		dto.setTipoGrupo(model.getTipoGrupo().getDescricao());
		List<TipoClasseEmpresa> lista = this.tipoClasseEmpresaDao.buscaTipoClasseEmpresaByRegistro(model.getId());
		if (lista != null && lista.size() > 0) {
			TipoClasseEmpresa tipoClasseEmpresa = lista.get(0);
			dto.setCodigoClasseEmpresa(tipoClasseEmpresa.getId());
			dto.setDescricaoClasseEmpresa(tipoClasseEmpresa.getDescricao());
		}
		dto.setDataCriacaoLogin(DateUtils.format(model.getPessoaJuridica().getDataCriacaoLogin(), DateUtils.DD_MM_YYYY_HH_MM_SS));
		dto.setTelefones(this.telefoneConverter.toListDto(this.telefoneDao.getListTelefoneByPessoa(model.getId())));
		dto.setEmails(this.emailConverter.toListEmailDto(this.emailDao.getListEnderecoDeEmailPor(model.getId())));
		dto.setObjetosSociais(this.objetoSocialConverter.toListDto(this.objetoSocialDao.getObjetosSociaisByEmpresa(model.getId())));
		List<CapitalSocialDto> listaCapitaisSociais = this.capitalSocialConverter.toListDto(this.capitalSocialDao.getCapitaisSociaisByEmpresa(model.getId()));
		if (listaCapitaisSociais != null && listaCapitaisSociais.size() > 0) {
			dto.setCapitalSocial(listaCapitaisSociais.get(0));
		}
		dto.setListRamosComRt(null);
		dto.setListRamosSemRt(null);
		dto.setRamosAtividades(this.ramoAtividadeConverter.toListDto(this.ramoAtividadeDao.getRamosAtividadesByEmpresa(model.getId())));
		dto.setHistoricos(this.historicoConverter.toListHistoricoDto(this.historicoDao.getHistoricosByPessoa(model.getId())));
		dto.setResponsaveisTecnicos(this.responsavelTecnicoConverter.toListDto(this.responsavelTecnicoDao.getResponsaveisTecnicosByEmpresa(model.getId())));
		dto.setQuadrosTecnicos(this.quadroTecnicoConverter.toListQuadroTecnicoEmpresaDto(this.empresaDao.getQuadroTecnicoPorEmpresa(model.getId())));
		
		return dto;
	}

	public List<EmpresaDto> toListDtoEmpresaDetalharDadosGerais(List<Empresa> listEmpresa) {
		List<EmpresaDto> listDto = new ArrayList<EmpresaDto>();
		
		for(Empresa p : listEmpresa){
			listDto.add(toEmpresaDetalharDadosGeraisDto(p));
		}
		
		return listDto;
	}

	private EmpresaDto toEmpresaDetalharDadosGeraisDto(Empresa model) {
		EmpresaDto dto = this.toDto(model);
		
		dto.setEnderecoPostal(null);
		dto.setAnuidade(null);
		dto.setObservacoes(model.getObservacoes());
		dto.setDataExpRegistroFormatada(model.getDataExpedicaoRegistro() != null ? DateUtils.format(model.getDataExpedicaoRegistro(), DateUtils.DD_MM_YYYY) : "-");
		dto.setDataRegistro(DateUtils.format(model.getDataCadastro(), DateUtils.DD_MM_YYYY));
		dto.setDataValidade(DateUtils.format(model.getDataValidade(), DateUtils.DD_MM_YYYY));
		dto.setTipoEmpresa(model.getTipoEmpresa().getDescricao());
		dto.setTipoCategoriaRegistro(model.getTipoCategoriaRegistro().getDescricao());
		dto.setTipoGrupo(model.getTipoGrupo().getDescricao());
		List<TipoClasseEmpresa> lista = this.tipoClasseEmpresaDao.buscaTipoClasseEmpresaByRegistro(model.getId());
		if (lista != null && lista.size() > 0) {
			TipoClasseEmpresa tipoClasseEmpresa = lista.get(0);
			dto.setCodigoClasseEmpresa(tipoClasseEmpresa.getId());
			dto.setDescricaoClasseEmpresa(tipoClasseEmpresa.getDescricao());
		}
		dto.setDataCriacaoLogin(DateUtils.format(model.getPessoaJuridica().getDataCriacaoLogin(), DateUtils.DD_MM_YYYY_HH_MM_SS));
		dto.setTelefones(this.telefoneConverter.toListDto(this.telefoneDao.getListTelefoneByPessoa(model.getId())));
		dto.setEmails(this.emailConverter.toListEmailDto(this.emailDao.getListEnderecoDeEmailPor(model.getId())));
		dto.setObjetosSociais(this.objetoSocialConverter.toListDto(this.objetoSocialDao.getObjetosSociaisByEmpresa(model.getId())));
		List<CapitalSocialDto> listaCapitaisSociais = this.capitalSocialConverter.toListDto(this.capitalSocialDao.getCapitaisSociaisByEmpresa(model.getId()));
		if (listaCapitaisSociais != null && listaCapitaisSociais.size() > 0) {
			dto.setCapitalSocial(listaCapitaisSociais.get(0));
		}
		
		return dto;
	}
	
	
}
