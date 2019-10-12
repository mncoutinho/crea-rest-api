package br.org.crea.commons.models.cadastro.dtos.profissional;

import java.util.List;

import br.org.crea.commons.models.cadastro.dtos.HistoricoDto;
import br.org.crea.commons.models.corporativo.dtos.SituacaoDto;

public class ProfissionalDto {
	
	
	private Long id;
		
	private String numeroRNP;
	
	private String nome;
	
	private String nomeSocial;
	
	private String email;
	
	private String cpf;
	
	private String dataRegistro;
	
	private String dataVisto;
	
	private String carteira;
	
	private String tipoCarteira;
	
	private String expedicaoCarteira;
	
	private String ufCarteira;
	
	private Integer quantidadeProtocolo;
	
	private SituacaoDto situacao;
	
	private String titulo;
	
	private String nivel;
	
	private char tipo;
	
	private List<TituloProfissionalDto> titulos;

	private int quantidadeArts;
	
	private int quantidadeQuadroTecnico;
	
	private List<HistoricoDto> historicos;
	
	private Boolean ativo;
	
	private Boolean regular;
	
	private String anotacoesEspeciais;
	
	private String observacoesProfissional;
	
	private List<QuadroTecnicoProfissionalDto> quadrosTecnicos;
	
	public String getAnotacoesEspeciais() {
		return anotacoesEspeciais;
	}

	public void setAnotacoesEspeciais(String anotacoesEspeciais) {
		this.anotacoesEspeciais = anotacoesEspeciais;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumeroRNP() {
		return numeroRNP;
	}

	public void setNumeroRNP(String numeroRNP) {
		this.numeroRNP = numeroRNP;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNomeSocial() {
		return nomeSocial;
	}

	public void setNomeSocial(String nomeSocial) {
		this.nomeSocial = nomeSocial;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getDataRegistro() {
		return dataRegistro;
	}

	public void setDataRegistro(String dataRegistro) {
		this.dataRegistro = dataRegistro;
	}

	public String getDataVisto() {
		return dataVisto;
	}

	public void setDataVisto(String dataVisto) {
		this.dataVisto = dataVisto;
	}

	public String getCarteira() {
		return carteira;
	}

	public void setCarteira(String carteira) {
		this.carteira = carteira;
	}
	
	public String getTipoCarteira() {
		return tipoCarteira;
	}

	public void setTipoCarteira(String tipoCarteira) {
		this.tipoCarteira = tipoCarteira;
	}

	public String getExpedicaoCarteira() {
		return expedicaoCarteira;
	}

	public void setExpedicaoCarteira(String expedicaoCarteira) {
		this.expedicaoCarteira = expedicaoCarteira;
	}

	public String getUfCarteira() {
		return ufCarteira;
	}

	public void setUfCarteira(String ufCarteira) {
		this.ufCarteira = ufCarteira;
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

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

	public char getTipo() {
		return tipo;
	}

	public void setTipo(char tipo) {
		this.tipo = tipo;
	}

	public List<TituloProfissionalDto> getTitulos() {
		return titulos;
	}

	public void setTitulos(List<TituloProfissionalDto> titulos) {
		this.titulos = titulos;
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

	public List<HistoricoDto> getHistoricos() {
		return historicos;
	}

	public void setHistoricos(List<HistoricoDto> historicos) {
		this.historicos = historicos;
	}

	public String getObservacoesProfissional() {
		return observacoesProfissional;
	}

	public void setObservacoesProfissional(String observacoesProfissional) {
		this.observacoesProfissional = observacoesProfissional;
	}

	public List<QuadroTecnicoProfissionalDto> getQuadrosTecnicos() {
		return quadrosTecnicos;
	}

	public void setQuadrosTecnicos(List<QuadroTecnicoProfissionalDto> quadrosTecnicos) {
		this.quadrosTecnicos = quadrosTecnicos;
	}

	public Boolean getRegular() {
		return regular;
	}

	public void setRegular(Boolean regular) {
		this.regular = regular;
	}
	
}
