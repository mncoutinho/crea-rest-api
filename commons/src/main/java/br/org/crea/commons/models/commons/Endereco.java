package br.org.crea.commons.models.commons;

import java.beans.Transient;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.javers.core.metamodel.annotation.DiffIgnore;
import org.javers.core.metamodel.annotation.TypeName;

import br.org.crea.commons.models.corporativo.pessoa.Pessoa;

@Entity
@Table(name = "CAD_ENDERECOS")
@SequenceGenerator(name = "ENDERECOS_SEQUENCE", sequenceName = "CAD_ENDERECOS_SEQ", initialValue = 1, allocationSize = 1)
@TypeName("Endereco")
public class Endereco implements Cloneable {

	@DiffIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ENDERECOS_SEQUENCE")
	@Column(name = "CODIGO")
	private Long id;

	@Column(name="NUMERO")
	private String numero;
	
	@OneToOne
	@JoinColumn(name="FK_CODIGO_TIPOS_LOGRADOUROS")
	private TipoLogradouro tipoLogradouro;

	@Column(name="LOGRADOURO")
	private String logradouro;

	@Column(name="COMPLEMENTO")
	private String complemento;

	@DiffIgnore
	@Column(name="BAIRRO")
	private String bairro;
	
	@DiffIgnore
	@Column(name="CEP")
	private String cep;
	
	@DiffIgnore
	@OneToOne 
	@JoinColumn(name="FK_CODIGO_PESSOAS")
	private Pessoa pessoa;
	
	@OneToOne
	@JoinColumn(name="FK_CODIGO_LOCALIDADES")
	private Localidade localidade;
	
	@OneToOne
	@JoinColumn(name="FK_CODIGO_UFS")
	private UF uf;
	
	@DiffIgnore
	@Column(name="POSTAL")
	private boolean postal;
	
	@DiffIgnore
	@Column(name="ENDERECOVALIDO")
	private boolean valido;
	
	@DiffIgnore
	@Column(name="EXCLUIDO")
	private boolean excluido;
	
	@DiffIgnore
	@OneToOne
	@JoinColumn(name="FK_CODIGO_TIPOS_ENDERECOS")
	private TipoEndereco tipoEndereco;
	
	@DiffIgnore
	@Column(name="LATITUDE")
	private String latitude;
	
	@DiffIgnore
	@Column(name="LONGITUDE")
	private String longitude;
	
	@DiffIgnore
	@Column(name="APROXIMADO")
	private boolean aproximado;
	
	@DiffIgnore
	@Column(name = "DATA_ENDERECO")
	@Temporal(TemporalType.DATE)
	private Date dataEndereco;
	
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

	public TipoLogradouro getTipoLogradouro() {
		return tipoLogradouro;
	}

	public void setTipoLogradouro(TipoLogradouro tipoLogradouro) {
		this.tipoLogradouro = tipoLogradouro;
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

	public String getCep() {
		return cep;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Localidade getLocalidade() {
		return localidade;
	}

	public void setLocalidade(Localidade localidade) {
		this.localidade = localidade;
	}

	public UF getUf() {
		return uf;
	}

	public void setUf(UF uf) {
		this.uf = uf;
	}

	public boolean isPostal() {
		return postal;
	}

	public void setPostal(boolean postal) {
		this.postal = postal;
	}

	public boolean isValido() {
		return valido;
	}

	public void setValido(boolean valido) {
		this.valido = valido;
	}

	public TipoEndereco getTipoEndereco() {
		return tipoEndereco;
	}

	public void setTipoEndereco(TipoEndereco tipoEndereco) {
		this.tipoEndereco = tipoEndereco;
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

	public Date getDataEndereco() {
		return dataEndereco;
	}

	public void setDataEndereco(Date dataEndereco) {
		this.dataEndereco = dataEndereco;
	}

	public boolean temTipoLogradouro(){
		return this.tipoLogradouro != null;
	}

	@Transient
	public String getCepFormatado(){
		StringBuilder cepFormatado = new StringBuilder(cep);
		cepFormatado.insert(cep.length() -3, "-");
		return cepFormatado.toString();
	}
	
    public Endereco getClone() {
        try {
            return (Endereco) super.clone();
        } catch (CloneNotSupportedException e) {
            return this;
        }
    }

    public boolean temLogradouro() {
		return this.logradouro != null;
	}
    
    public boolean temNumero() {
		return this.numero != null;
	}
    
	public boolean temComplemento() {
		return this.complemento != null;
	}

	public boolean ufNaoEhRJ() {
		if (this.uf != null) {
			return !this.uf.getId().equals(1L);
		}
		return true;
	}

	public boolean ufNaoEhExterior() {
		if (this.uf != null) {
			return this.uf.getBrasil() == 1;
		}
		return true;
	}
	
}
