package br.org.crea.commons.models.corporativo.dtos;

import java.util.List;

import br.org.crea.commons.models.commons.dtos.EnderecoDto;
import br.org.crea.commons.models.commons.dtos.TelefoneDto;
import br.org.crea.commons.models.corporativo.enuns.TipoPessoa;
import br.org.crea.commons.util.StringUtil;

public class LeigoDto {
	
	private Long id;
	
	private String nome;
	
	private TipoPessoa tipoPessoa;
	
	private String cpfOuCnpj;
	
	private EnderecoDto endereco;
	
	private String email;
	
	private List<TelefoneDto> telefones;
	
	private boolean geraSenha;
	
	private Long idPessoaResponsavel;
	
	private String tipoPj;

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

	public TipoPessoa getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(TipoPessoa tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}

	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}

	public EnderecoDto getEndereco() {
		return endereco;
	}

	public void setEndereco(EnderecoDto endereco) {
		this.endereco = endereco;
	}

	public String getCpfOuCnpjFormatado() {
		return StringUtil.getCnpjCpfFormatado(cpfOuCnpj);
	}

	public List<TelefoneDto> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<TelefoneDto> telefones) {
		this.telefones = telefones;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public boolean isGeraSenha() {
		return geraSenha;
	}

	public void setGeraSenha(boolean geraSenha) {
		this.geraSenha = geraSenha;
	}
	
	public boolean temTelefones() {
		return  this.telefones != null && !this.telefones.isEmpty() ? true : false;
	}

	public boolean heLeigoPF() {
		return this.getTipoPessoa().equals(TipoPessoa.LEIGOPF);
	}
	
	public Long getIdPessoaResponsavel() {
		return idPessoaResponsavel;
	}

	public void setIdPessoaResponsavel(Long idPessoaResponsavel) {
		this.idPessoaResponsavel = idPessoaResponsavel;
	}

	public String getTipoPj() {
		return tipoPj;
	}

	public void setTipoPj(String tipoPj) {
		this.tipoPj = tipoPj;
	}	
	
	public boolean temCpfCnpj() {
		return this.cpfOuCnpj != null;
	}
	
	
	public boolean ehIsentoCpf() {
		return this.cpfOuCnpj.equals("_ISENTO_CPF");
	}
	
	public boolean ehIsentoCnpj() {
		return this.cpfOuCnpj.equals("_ISENTO_CNPJ");
	}
	
}