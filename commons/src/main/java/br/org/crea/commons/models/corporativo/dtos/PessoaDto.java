package br.org.crea.commons.models.corporativo.dtos;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import br.org.crea.commons.models.cadastro.dtos.pessoa.HomePageDto;
import br.org.crea.commons.models.commons.dtos.EnderecoDto;
import br.org.crea.commons.models.commons.dtos.TelefoneDto;
import br.org.crea.commons.models.corporativo.enuns.TipoPessoa;

public class PessoaDto implements Serializable {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = -3017107917982715736L;

	private Long id;
	
	private String nome;
	
	private String razaoSocial;
	
	private String email;
	
	private TipoPessoa tipo;
	
	private String cpf;
	
	private String cnpj;
	
	private Integer quantidadeProtocolo;
	
	private SituacaoDto situacao;
	
	private String numeroRNP;
	
	private String titulo;

	private int quantidadeArts;
	
	private int quantidadeQuadroTecnico;
	
	private Long matricula;
	
	private String registro;
	
	private String perfil;

	private String cpfOuCnpj;
	
	private List<TelefoneDto> telefones;
	
	private List<HomePageDto> homePages;
	
	private Long idFuncionarioAtualizacao;
	
	private String base64;
	
	private Long idInstituicao;

	private EnderecoDto enderecoPostal;
	
	private List<EnderecoDto> enderecos;
	
	private Long idPessoaResponsavel;
	
	private String tipoPj;

	// Corresponde ao campo "Assinou Termo de Registro e Responsabilidade em:"
	private String dataCriacaoLogin;
	
	public PessoaDto () {}
	
	public PessoaDto (Long id) {
		this.id = id;
	}
	
	public Long getIdInstituicao() {
		return idInstituicao;
	}

	public void setIdInstituicao(Long idInstitucao) {
		this.idInstituicao = idInstitucao;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public TipoPessoa getTipo() {
		return tipo;
	}

	public void setTipo(TipoPessoa tipo) {
		this.tipo = tipo;
	}


	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
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
	
	@JsonIgnore
	public boolean isPessoaJuridica(){
		return this.getTipo().equals(TipoPessoa.PESSOAJURIDICA) ? true : false;
	}
	
	@JsonIgnore
	public boolean isPessoaFisica(){
		if(this.tipo != null){
			return this.getTipo().equals(TipoPessoa.PESSOAFISICA) ? true : false;
		}
		return false;
	   
	}

	public SituacaoDto getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoDto situacao) {
		this.situacao = situacao;
	}

	public String getNumeroRNP() {
		return numeroRNP;
	}

	public void setNumeroRNP(String numeroRNP) {
		this.numeroRNP = numeroRNP;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
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

	public Long getMatricula() {
		return matricula;
	}

	public void setMatricula(Long matricula) {
		this.matricula = matricula;
	}

	public String getRegistro() {
		return registro;
	}

	public void setRegistro(String registro) {
		this.registro = registro;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}

	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}

	public Long getIdFuncionarioAtualizacao() {
		return idFuncionarioAtualizacao;
	}

	public void setIdFuncionarioAtualizacao(Long idFuncionarioAtualizacao) {
		this.idFuncionarioAtualizacao = idFuncionarioAtualizacao;
	}

	public EnderecoDto getEnderecoPostal() {
		return enderecoPostal;
	}

	public void setEnderecoPostal(EnderecoDto enderecoPostal) {
		this.enderecoPostal = enderecoPostal;
	}
	
	public List<EnderecoDto> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<EnderecoDto> enderecos) {
		this.enderecos = enderecos;
	}

	public Long getIdPessoaResponsavel() {
		return idPessoaResponsavel;
	}

	public void setIdPessoaResponsavel(Long idPessoaResponsavel) {
		this.idPessoaResponsavel = idPessoaResponsavel;
	}
	public String getBase64() {
		return base64;
	}

	public void setBase64(String base64) {
		this.base64 = base64;
	}

	public List<TelefoneDto> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<TelefoneDto> telefones) {
		this.telefones = telefones;
	}

	public List<HomePageDto> getHomePages() {
		return homePages;
	}

	public void setHomePages(List<HomePageDto> homePages) {
		this.homePages = homePages;
	}

	public String getDataCriacaoLogin() {
		return dataCriacaoLogin;
	}

	public void setDataCriacaoLogin(String dataCriacaoLogin) {
		this.dataCriacaoLogin = dataCriacaoLogin;
	}

	public String getTipoPj() {
		return tipoPj;
	}

	public void setTipoPj(String tipoPj) {
		this.tipoPj = tipoPj;
	}

	public boolean temTelefone() {
		if (this.telefones != null) {
			return this.telefones.size() > 0;
		}
		return false;
	}

	public boolean temEnderecoPostal() {
		return this.enderecoPostal != null;
	}
	
}
