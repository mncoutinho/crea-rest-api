package br.org.crea.commons.models.commons;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.org.crea.commons.models.commons.enuns.ModuloSistema;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;

@Entity
@Table(name="CAD_ARQUIVOS")
@SequenceGenerator(name = "sqArquivo", sequenceName = "SQ_CAD_ARQUIVOS", initialValue = 1, allocationSize = 1)
public class Arquivo {
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sqArquivo")
	private Long id;

	@Column(name = "CAMINHO_ORIGINAL")
	private String caminhoOriginal;
	
	@Column(name = "CAMINHO_STORAGE")
	private String caminhoStorage;

	@Column(name = "NOME_ORIGINAL")
	private String nomeOriginal;
	
	@Column(name = "NOME_STORAGE")
	private String nomeStorage;
	
	@Column(name = "DESCRICAO")
	private String descricao;
	
	@Column(name = "EXTENSAO")
	private String extensao;
	
	@Column(name = "TAMANHO")
	private Long tamanho;

	@Enumerated(EnumType.STRING)
	@Column(name="MODULO_SISTEMA")
	private ModuloSistema modulo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_INCLUSAO")
	private Date dataInclusao;
	
	@OneToOne
	@JoinColumn(name="FK_ID_PESSOA")
	private Pessoa pessoa;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="PRIVADO")
	private boolean privado;
	
	@Column(name="URI")
	private String uri;

	public boolean isPrivado() {
		return privado;
	}

	public void setPrivado(boolean privado) {
		this.privado = privado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCaminhoOriginal() {
		return caminhoOriginal;
	}

	public void setCaminhoOriginal(String caminhoOriginal) {
		this.caminhoOriginal = caminhoOriginal;
	}

	public String getCaminhoStorage() {
		return caminhoStorage;
	}

	public void setCaminhoStorage(String caminhoStorage) {
		this.caminhoStorage = caminhoStorage;
	}

	public String getNomeOriginal() {
		return nomeOriginal;
	}

	public void setNomeOriginal(String nomeOriginal) {
		this.nomeOriginal = nomeOriginal;
	}

	public String getNomeStorage() {
		return nomeStorage;
	}

	public void setNomeStorage(String nomeStorage) {
		this.nomeStorage = nomeStorage;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getExtensao() {
		return extensao;
	}

	public void setExtensao(String extensao) {
		this.extensao = extensao;
	}

	public Long getTamanho() {
		return tamanho;
	}

	public void setTamanho(Long tamanho) {
		this.tamanho = tamanho;
	}

	public Date getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ModuloSistema getModulo() {
		return modulo;
	}

	public void setModulo(ModuloSistema modulo) {
		this.modulo = modulo;
	}

	public boolean temPessoa() {
		return this.pessoa != null;
	}
	
	public boolean temModulo() {
		return this.modulo != null;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}	
	


}
