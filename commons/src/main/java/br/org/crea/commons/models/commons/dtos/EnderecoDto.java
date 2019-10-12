package br.org.crea.commons.models.commons.dtos;

import org.codehaus.jackson.annotate.JsonPropertyOrder;

import br.org.crea.commons.models.commons.Endereco;


@JsonPropertyOrder({ "id", "numero", "postal", "logradouro", "complemento", "bairro", "cep", 
	"dataInclusao", "enderecoCompleto", "latitude", "longitude", "aproximado", "uf", 
	"tipoLogradouro", "tipoEndereco", "localidade"})

public class EnderecoDto {

	private Long id;
	
	private String idString;
	
	private Long codPessoa;

	private String numero;

	private String postal;

	private String logradouro;

	private String complemento;

	private String bairro;

	private String cep;
	
	private String dataInclusao;
	
	private String enderecoCompleto;
	
	private String enderecoCompletoAnalise;

	private String latitude;

	private String longitude;

	private boolean aproximado;

	private DomainGenericDto uf;

	private DomainGenericDto tipoLogradouro;
	
	private DomainGenericDto tipoEndereco;
	
	private LocalidadeDto localidade;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public DomainGenericDto getUf() {
		return uf;
	}

	public void setUf(DomainGenericDto uf) {
		this.uf = uf;
	}

	public LocalidadeDto getLocalidade() {
		return localidade;
	}

	public void setLocalidade(LocalidadeDto localidade) {
		this.localidade = localidade;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getEnderecoCompleto() {
		return enderecoCompleto;
	}

	public void setEnderecoCompleto(String enderecoCompleto) {
		this.enderecoCompleto = enderecoCompleto;
	}

	public String getEnderecoCompletoAnalise() {
		return enderecoCompletoAnalise;
	}

	public void setEnderecoCompletoAnalise(String enderecoCompletoAnalise) {
		this.enderecoCompletoAnalise = enderecoCompletoAnalise;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public boolean isAproximado() {
		return aproximado;
	}

	public void setAproximado(boolean aproximado) {
		this.aproximado = aproximado;
	}

	public String getPostal() {
		return postal;
	}

	public void setPostal(String postal) {
		this.postal = postal;
	}

	public String getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(String dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	public DomainGenericDto getTipoLogradouro() {
		return tipoLogradouro;
	}

	public void setTipoLogradouro(DomainGenericDto tipoLogradouro) {
		this.tipoLogradouro = tipoLogradouro;
	}
	
	public boolean temTipoLogradouro() {
		return this.tipoLogradouro != null;
	}

	public DomainGenericDto getTipoEndereco() {
		return tipoEndereco;
	}

	public void setTipoEndereco(DomainGenericDto tipoEndereco) {
		this.tipoEndereco = tipoEndereco;
	}

	public boolean temLocalidade() {
		return this.localidade != null;
	}

	public boolean temUf() {
		return this.uf != null;
	}
	
	public boolean ehPostal() {
		if (this.postal != null) {
			return this.postal.equals("SIM");
		}
		return false;		
	}

	public Long getCodPessoa() {
		return codPessoa;
	}

	public void setCodPessoa(Long codPessoa) {
		this.codPessoa = codPessoa;
	}

	public String getIdString() {
		return idString;
	}

	public void setIdString(String idString) {
		this.idString = idString;
	}

	public boolean temBairro() {
		return this.bairro != null;
	}

	public boolean temLogradouro() {
		return this.logradouro != null;
	}

	public boolean temComplemento() {
		return this.complemento != null;
	}

	public boolean temNumero() {
		return this.numero != null;
	}

	public boolean temTipoEndereco() {
		return this.tipoEndereco != null;
	}
	
	public String transformaEnderecoSemCepMunicipioEUf(){

		StringBuilder enderecoConcat = new StringBuilder();
		
		if(getTipoLogradouro() != null){
			enderecoConcat.append(getTipoLogradouro().getDescricao() + " ");
		}
		
		enderecoConcat.append(getLogradouro());
		
		if(getNumero() != null && getNumero().trim().length() > 0){
			enderecoConcat.append(", " + getNumero());
		}
		
		if(getBairro() != null){
			enderecoConcat.append(" - " + getBairro());
		}
		
		
		
		
		return enderecoConcat.toString();
	}
}
