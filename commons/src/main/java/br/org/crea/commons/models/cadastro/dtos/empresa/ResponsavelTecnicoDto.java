package br.org.crea.commons.models.cadastro.dtos.empresa;

import java.util.Date;
import java.util.List;

import br.org.crea.commons.models.art.dtos.ArtDto;
import br.org.crea.commons.models.cadastro.dtos.VistoDto;
import br.org.crea.commons.models.cadastro.dtos.profissional.TituloProfissionalDto;
import br.org.crea.commons.models.commons.dtos.EnderecoDto;
import br.org.crea.commons.models.commons.dtos.TelefoneDto;
import br.org.crea.commons.models.corporativo.dtos.SituacaoDto;
import br.org.crea.commons.models.financeiro.dtos.DividaDto;

public class ResponsavelTecnicoDto {
	
	private Long id;
	
	private String nomeProfissional; 
	
	private String nomeEmpresa;
	
	private String cpf;
	
	private SituacaoDto situacao;
	
	private String numeroRnp;
	
	private String registro;
		
	private String dataInicioRT;

	private String dataFimRT;
	
	private String dataInicioQuadroTecnico;
	
	private String ramo;
	
	private String atividade;
	
	private List<TituloProfissionalDto> titulos;
	
	private EnderecoDto enderecoResidencial;
	
	private List<EnderecoDto> enderecosValidos;
	
	private DividaDto anuidade;
	
	private List<TelefoneDto> listTelefones;
		
	private ArtDto artCargoFuncao;
	
	private List<VinculoEmpresaResponsavelTecnicoDto> vinculoEmpresas;
	
	private String dataVistoFormatada;
	
	private Date dataVisto;
	
	private String anotacoesEspeciais;
	
	private String ressalvas;
	
	private List<VistoDto> vistosRegionais;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeProfissional() {
		return nomeProfissional;
	}

	public void setNomeProfissional(String nomeProfissional) {
		this.nomeProfissional = nomeProfissional;
	}

	public String getNomeEmpresa() {
		return nomeEmpresa;
	}

	public void setNomeEmpresa(String nomeEmpresa) {
		this.nomeEmpresa = nomeEmpresa;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public SituacaoDto getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoDto situacao) {
		this.situacao = situacao;
	}

	public String getNumeroRnp() {
		return numeroRnp;
	}

	public void setNumeroRnp(String numeroRnp) {
		this.numeroRnp = numeroRnp;
	}	

	public String getRegistro() {
		return registro;
	}

	public void setRegistro(String registro) {
		this.registro = registro;
	}

	public String getDataInicioRT() {
		return dataInicioRT;
	}

	public void setDataInicioRT(String dataInicioRT) {
		this.dataInicioRT = dataInicioRT;
	}

	public String getDataFimRT() {
		return dataFimRT;
	}

	public void setDataFimRT(String dataFimRT) {
		this.dataFimRT = dataFimRT;
	}

	public String getDataInicioQuadroTecnico() {
		return dataInicioQuadroTecnico;
	}

	public void setDataInicioQuadroTecnico(String dataInicioQuadroTecnico) {
		this.dataInicioQuadroTecnico = dataInicioQuadroTecnico;
	}

	public String getRamo() {
		return ramo;
	}

	public void setRamo(String ramo) {
		this.ramo = ramo;
	}

	public String getAtividade() {
		return atividade;
	}

	public void setAtividade(String atividade) {
		this.atividade = atividade;
	}

	public List<TituloProfissionalDto> getTitulos() {
		return titulos;
	}

	public void setTitulos(List<TituloProfissionalDto> titulos) {
		this.titulos = titulos;
	}

	public EnderecoDto getEnderecoResidencial() {
		return enderecoResidencial;
	}

	public void setEnderecoResidencial(EnderecoDto enderecoResidencial) {
		this.enderecoResidencial = enderecoResidencial;
	}

	public List<EnderecoDto> getEnderecosValidos() {
		return enderecosValidos;
	}

	public void setEnderecosValidos(List<EnderecoDto> enderecosValidos) {
		this.enderecosValidos = enderecosValidos;
	}

	public DividaDto getAnuidade() {
		return anuidade;
	}

	public void setAnuidade(DividaDto anuidade) {
		this.anuidade = anuidade;
	}

	public List<TelefoneDto> getListTelefones() {
		return listTelefones;
	}

	public void setListTelefones(List<TelefoneDto> listTelefones) {
		this.listTelefones = listTelefones;
	}

	public ArtDto getArtCargoFuncao() {
		return artCargoFuncao;
	}

	public void setArtCargoFuncao(ArtDto artCargoFuncao) {
		this.artCargoFuncao = artCargoFuncao;
	}

	public List<VinculoEmpresaResponsavelTecnicoDto> getVinculoEmpresas() {
		return vinculoEmpresas;
	}

	public void setVinculoEmpresas(List<VinculoEmpresaResponsavelTecnicoDto> vinculoEmpresas) {
		this.vinculoEmpresas = vinculoEmpresas;
	}

	public String getDataVistoFormatada() {
		return dataVistoFormatada;
	}

	public void setDataVistoFormatada(String dataVistoFormatada) {
		this.dataVistoFormatada = dataVistoFormatada;
	}

	public Date getDataVisto() {
		return dataVisto;
	}

	public void setDataVisto(Date dataVisto) {
		this.dataVisto = dataVisto;
	}

	public String getAnotacoesEspeciais() {
		return anotacoesEspeciais;
	}

	public void setAnotacoesEspeciais(String anotacoesEspeciais) {
		this.anotacoesEspeciais = anotacoesEspeciais;
	}

	public String getRessalvas() {
		return ressalvas;
	}

	public void setRessalvas(String ressalvas) {
		this.ressalvas = ressalvas;
	}

	public List<VistoDto> getVistosRegionais() {
		return vistosRegionais;
	}

	public void setVistosRegionais(List<VistoDto> vistosRegionais) {
		this.vistosRegionais = vistosRegionais;
	}
}
