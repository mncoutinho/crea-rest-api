package br.org.crea.commons.models.corporativo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.org.crea.commons.models.commons.Endereco;
import br.org.crea.commons.models.corporativo.enuns.TipoPessoa;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;


@Entity
@Table(name = "FIS_DENUNCIA")
@SequenceGenerator(name = "sqDenuncia", sequenceName = "FIS_DENUNCIA_SEQ", initialValue = 1, allocationSize = 1)
public class Denuncia {
	
	@Id
	@Column(name="NUMERO")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sqDenuncia")
	private Long numero;
	
	@Column(name="MOTIVO")
	private String motivo;
	
	@Column(name="DENUNCIADO")
	private String denunciado;
	
	@OneToOne
	@Column(name="FK_CODIGO_ENDERECO")
	private Endereco endereco;
	
	@Column(name="DATA_DENUNCIA")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataDenuncia;
	
	@Column(name="DATA_CADASTRO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCadastro;
	
	@OneToOne
	@Column(name="FK_ID_TIPO_DENUNCIA")
	private Long tipoDenuncia;
	
	@OneToOne
	@Column(name="FK_ID_PROCEDENCIA_DENUNCIA")
	private Long procedenciaDenuncia;
	
/*	@OneToOne
	@Column(name="FK_ID_PROTOCOLO")
	private Protocolo protocolo;

	@OneToOne
	@Column(name="FK_ID_DEPARTAMENTO")
	private Departamento departamento;
	
	@OneToOne
	@Column(name="FK_ID_FUNCIONARIO")
	private Funcionario funcionario;*/
	
	@OneToOne
	@Column(name="FK_ID_PESSOA")
	private Pessoa pessoa;
	
	@Column(name="TIPO_PESSOA")
	private TipoPessoa tipoPessoa;
	
/*	@Column(name="DDD")
	private String ddd;
	
	@Column(name="TELEFONE")
	private String telefone;
	
	@Column(name="EMAIL")
	private String email;*/
	
	@OneToOne
	@Column(name="FK_FATO_GERADOR")
	private Long fatoGerador;
	
/*	@Column(name="DESCRITIVO_FATO_GERADOR")
	private String descritivoFatoGerador;
	
	@Column(name="DATA_ULTIMA_ALTERACAO")
	private Date dataUltimaAlteracao;
	
	@OneToOne
	@Column(name="FK_FUNCIONARIO_ALTERACAO")
	private Funcionario funcionarioAlteracao;
	
	@OneToOne
	@Column(name="FK_FUNCIONARIO_CADASTRO")
	private Funcionario funcionarioCadastro;*/

	
	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getDenunciado() {
		return denunciado;
	}

	public void setDenunciado(String denunciado) {
		this.denunciado = denunciado;
	}

	public Endereco getEndereco() {
		return endereco;
	}
	
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
	public Date getDataDenuncia() {
		return dataDenuncia;
	}

	public void setDataDenuncia(Date dataDenuncia) {
		this.dataDenuncia = dataDenuncia;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Long getTipoDenuncia() {
		return tipoDenuncia;
	}

	public void setTipoDenuncia(Long tipoDenuncia) {
		this.tipoDenuncia = tipoDenuncia;
	}

	public Long getProcedenciaDenuncia() {
		return procedenciaDenuncia;
	}

	public void setProcedenciaDenuncia(Long procedenciaDenuncia) {
		this.procedenciaDenuncia = procedenciaDenuncia;
	}

/*	public Protocolo getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(Protocolo protocolo) {
		this.protocolo = protocolo;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}*/

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public TipoPessoa getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(TipoPessoa tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

/*	public String getDdd() {
		return ddd;
	}

	public void setDdd(String ddd) {
		this.ddd = ddd;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}*/

	public Long getFatoGerador() {
		return fatoGerador;
	}

	public void setFatoGerador(Long fatoGerador) {
		this.fatoGerador = fatoGerador;
	}

/*	public String getDescritivoFatoGerador() {
		return descritivoFatoGerador;
	}

	public void setDescritivoFatoGerador(String descritivoFatoGerador) {
		this.descritivoFatoGerador = descritivoFatoGerador;
	}

	public Date getDataUltimaAlteracao() {
		return dataUltimaAlteracao;
	}

	public void setDataUltimaAlteracao(Date dataUltimaAlteracao) {
		this.dataUltimaAlteracao = dataUltimaAlteracao;
	}

	public Funcionario getFuncionarioAlteracao() {
		return funcionarioAlteracao;
	}

	public void setFuncionarioAlteracao(Funcionario funcionarioAlteracao) {
		this.funcionarioAlteracao = funcionarioAlteracao;
	}

	public Funcionario getFuncionarioCadastro() {
		return funcionarioCadastro;
	}

	public void setFuncionarioCadastro(Funcionario funcionarioCadastro) {
		this.funcionarioCadastro = funcionarioCadastro;
	}*/

}
