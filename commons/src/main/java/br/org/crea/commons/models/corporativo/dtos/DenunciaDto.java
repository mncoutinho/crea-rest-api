package br.org.crea.commons.models.corporativo.dtos;

import java.util.Date;

import br.org.crea.commons.models.commons.dtos.EnderecoDto;
import br.org.crea.commons.models.corporativo.enuns.TipoPessoa;

public class DenunciaDto {
	
	private Long numero;
	
	private String motivo;
	
	private String denunciado;
	
	private EnderecoDto endereco;
	
	private Date dataDenuncia;
	
	private Date dataCadastro;	
	
	private Long tipoDenuncia;
	
	private Long procedenciaDenuncia;
	
/*	private ProtocoloDto protocolo;
	
	private Departamento departamento;
	
	private FuncionarioDto funcionario;*/
	
	private PessoaDto pessoa;
	
	private TipoPessoa tipoPessoa;
	
/*	private String ddd;
	
	private String telefone;
	
	private String email;*/
	
	private Long fatoGerador;
	
/*	private String descritivoFatoGerador;
	
	private Date dataUltimaAlteracao;
	
	private FuncionarioDto funcionarioAlteracao;
	
	private FuncionarioDto funcionarioCadastro;*/

	
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

	public EnderecoDto getEndereco() {
		return endereco;
	}
	
	public void setEndereco(EnderecoDto endereco) {
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

/*	public ProtocoloDto getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(ProtocoloDto protocolo) {
		this.protocolo = protocolo;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public FuncionarioDto getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(FuncionarioDto funcionario) {
		this.funcionario = funcionario;
	}*/

	public PessoaDto getPessoa() {
		return pessoa;
	}

	public void setPessoa(PessoaDto pessoa) {
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

	public FuncionarioDto getFuncionarioAlteracao() {
		return funcionarioAlteracao;
	}

	public void setFuncionarioAlteracao(FuncionarioDto funcionarioAlteracao) {
		this.funcionarioAlteracao = funcionarioAlteracao;
	}

	public FuncionarioDto getFuncionarioCadastro() {
		return funcionarioCadastro;
	}

	public void setFuncionarioCadastro(FuncionarioDto funcionarioCadastro) {
		this.funcionarioCadastro = funcionarioCadastro;
	}*/

}
