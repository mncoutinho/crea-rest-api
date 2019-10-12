package br.org.crea.commons.models.cadastro.dtos.empresa;

import java.util.Date;
import java.util.List;

import br.org.crea.commons.models.cadastro.dtos.EmailDto;
import br.org.crea.commons.models.cadastro.dtos.HistoricoDto;
import br.org.crea.commons.models.commons.dtos.EnderecoDto;
import br.org.crea.commons.models.commons.dtos.TelefoneDto;
import br.org.crea.commons.models.corporativo.dtos.SituacaoDto;
import br.org.crea.commons.models.financeiro.dtos.DividaDto;


public class EmpresaDto {
	
	
	private Long id;
	
	private String nome;
	
	private String nomeFantasia;
	
	private String email;
	
	private String cnpj;
	
	private Integer quantidadeProtocolo;
	
	private SituacaoDto situacao;
	
	private String ramo;
	
	public boolean regular;

	private int quantidadeArts;
	
	private int quantidadeQuadroTecnico;
	
	private Boolean ativo;
	
	private EnderecoDto enderecoPostal;
	
	private List<EnderecoDto> enderecosValidos;
	
	private List<TelefoneDto> telefones;
	
	private List<EmailDto> emails;
	
	private List<ObjetoSocialDto> objetosSociais;
	
	private String objetoSocial;
	
	private CapitalSocialDto capitalSocial;
	
	private String dataQuitacaoTaxaRegistro;
	
	private List<RamoEmpresaDto> listRamosSemRt;
	
	private List<RamoEmpresaDto> listRamosComRt;
	
	private List<RamoAtividadeDto> ramosAtividades;
	
	private List<HistoricoDto> historicos;
	
	private List<ResponsavelTecnicoDto> responsaveisTecnicos;
	
	private List<QuadroTecnicoEmpresaDto> quadrosTecnicos;
	
	private DividaDto anuidade;
	
	private Date dataVisto;
	
	private Date dataExpRegistro;
	
	private String dataExpRegistroFormatada;

	private String dataVistoFormatada;
	
	private String dataRegistro;
	
	private String dataValidade;
	
	private String tipoEmpresa;
	
	private String tipoCategoriaRegistro;
	
	private String tipoGrupo;
	
	private String codigoClasseEmpresa;
	
	private String descricaoClasseEmpresa;
	
	private String dataCriacaoLogin;
	
	private String observacoes;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public Integer getQuantidadeProtocolo() {
		return quantidadeProtocolo;
	}

	public void setQuantidadeProtocolo(Integer quantidadeProtocolo) {
		this.quantidadeProtocolo = quantidadeProtocolo;
	}

	public SituacaoDto getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoDto situacao) {
		this.situacao = situacao;
	}

	public String getRamo() {
		return ramo;
	}

	public void setRamo(String ramo) {
		this.ramo = ramo;
	}

	public int getQuantidadeArts() {
		return quantidadeArts;
	}

	public void setQuantidadeArts(int quantidadeArts) {
		this.quantidadeArts = quantidadeArts;
	}

	public int getQuantidadeQuadroTecnico() {
		return quantidadeQuadroTecnico;
	}

	public void setQuantidadeQuadroTecnico(int quantidadeQuadroTecnico) {
		this.quantidadeQuadroTecnico = quantidadeQuadroTecnico;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public EnderecoDto getEnderecoPostal() {
		return enderecoPostal;
	}

	public void setEnderecoPostal(EnderecoDto enderecoPostal) {
		this.enderecoPostal = enderecoPostal;
	}

	public List<EnderecoDto> getEnderecosValidos() {
		return enderecosValidos;
	}

	public void setEnderecosValidos(List<EnderecoDto> enderecosValidos) {
		this.enderecosValidos = enderecosValidos;
	}
	
	public List<TelefoneDto> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<TelefoneDto> telefones) {
		this.telefones = telefones;
	}
	
	public List<EmailDto> getEmails() {
		return emails;
	}

	public void setEmails(List<EmailDto> emails) {
		this.emails = emails;
	}

	public String getObjetoSocial() {
		return objetoSocial;
	}

	public void setObjetoSocial(String objetoSocial) {
		this.objetoSocial = objetoSocial;
	}

	public List<ObjetoSocialDto> getObjetosSociais() {
		return objetosSociais;
	}

	public void setObjetosSociais(List<ObjetoSocialDto> objetosSociais) {
		this.objetosSociais = objetosSociais;
	}

	public CapitalSocialDto getCapitalSocial() {
		return capitalSocial;
	}

	public void setCapitalSocial(CapitalSocialDto capitalSocial) {
		this.capitalSocial = capitalSocial;
	}

	public String getDataQuitacaoTaxaRegistro() {
		return dataQuitacaoTaxaRegistro;
	}

	public void setDataQuitacaoTaxaRegistro(String dataQuitacaoTaxaRegistro) {
		this.dataQuitacaoTaxaRegistro = dataQuitacaoTaxaRegistro;
	}

	public List<RamoEmpresaDto> getListRamosSemRt() {
		return listRamosSemRt;
	}

	public void setListRamosSemRt(List<RamoEmpresaDto> listRamosSemRt) {
		this.listRamosSemRt = listRamosSemRt;
	}

	public List<RamoEmpresaDto> getListRamosComRt() {
		return listRamosComRt;
	}

	public void setListRamosComRt(List<RamoEmpresaDto> listRamosComRt) {
		this.listRamosComRt = listRamosComRt;
	}

	public List<RamoAtividadeDto> getRamosAtividades() {
		return ramosAtividades;
	}

	public void setRamosAtividades(List<RamoAtividadeDto> ramosAtividades) {
		this.ramosAtividades = ramosAtividades;
	}

	public List<HistoricoDto> getHistoricos() {
		return historicos;
	}

	public void setHistoricos(List<HistoricoDto> historicos) {
		this.historicos = historicos;
	}
	
	public List<ResponsavelTecnicoDto> getResponsaveisTecnicos() {
		return responsaveisTecnicos;
	}

	public void setResponsaveisTecnicos(List<ResponsavelTecnicoDto> responsaveisTecnicos) {
		this.responsaveisTecnicos = responsaveisTecnicos;
	}

	public List<QuadroTecnicoEmpresaDto> getQuadrosTecnicos() {
		return quadrosTecnicos;
	}

	public void setQuadrosTecnicos(List<QuadroTecnicoEmpresaDto> quadrosTecnicos) {
		this.quadrosTecnicos = quadrosTecnicos;
	}

	public DividaDto getAnuidade() {
		return anuidade;
	}

	public void setAnuidade(DividaDto anuidade) {
		this.anuidade = anuidade;
	}

	public Date getDataVisto() {
		return dataVisto;
	}

	public void setDataVisto(Date dataVisto) {
		this.dataVisto = dataVisto;
	}

	public String getDataVistoFormatada() {
		return dataVistoFormatada;
	}

	public void setDataVistoFormatada(String dataVistoFormatada) {
		this.dataVistoFormatada = dataVistoFormatada;
	}

	public String getDataRegistro() {
		return dataRegistro;
	}

	public void setDataRegistro(String dataRegistro) {
		this.dataRegistro = dataRegistro;
	}

	public String getDataValidade() {
		return dataValidade;
	}

	public void setDataValidade(String dataValidade) {
		this.dataValidade = dataValidade;
	}

	public String getTipoEmpresa() {
		return tipoEmpresa;
	}

	public void setTipoEmpresa(String tipoEmpresa) {
		this.tipoEmpresa = tipoEmpresa;
	}

	public String getTipoCategoriaRegistro() {
		return tipoCategoriaRegistro;
	}

	public void setTipoCategoriaRegistro(String tipoCategoriaRegistro) {
		this.tipoCategoriaRegistro = tipoCategoriaRegistro;
	}

	public String getTipoGrupo() {
		return tipoGrupo;
	}

	public void setTipoGrupo(String tipoGrupo) {
		this.tipoGrupo = tipoGrupo;
	}

	public String getCodigoClasseEmpresa() {
		return codigoClasseEmpresa;
	}

	public void setCodigoClasseEmpresa(String codigoClasseEmpresa) {
		this.codigoClasseEmpresa = codigoClasseEmpresa;
	}

	public String getDescricaoClasseEmpresa() {
		return descricaoClasseEmpresa;
	}

	public void setDescricaoClasseEmpresa(String descricaoClasseEmpresa) {
		this.descricaoClasseEmpresa = descricaoClasseEmpresa;
	}

	public String getDataCriacaoLogin() {
		return dataCriacaoLogin;
	}

	public void setDataCriacaoLogin(String dataCriacaoLogin) {
		this.dataCriacaoLogin = dataCriacaoLogin;
	}
	
	public Date getDataExpRegistro() {
		return dataExpRegistro;
	}

	public void setDataExpRegistro(Date dataExpRegistro) {
		this.dataExpRegistro = dataExpRegistro;
	}

	public String getDataExpRegistroFormatada() {
		return dataExpRegistroFormatada;
	}

	public void setDataExpRegistroFormatada(String dataExpRegistroFormatada) {
		this.dataExpRegistroFormatada = dataExpRegistroFormatada;
	}

	public boolean isRegular() {
		return regular;
	}

	public void setRegular(boolean regular) {
		this.regular = regular;
	}

}
