package br.org.crea.commons.models.corporativo.pessoa;

import java.beans.Transient;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "CAD_PESSOAS_JURIDICAS")
@PrimaryKeyJoinColumn(name = "CODIGO")
public class PessoaJuridica extends Pessoa {


	private static final long serialVersionUID = 1L;

	@Column(name="CNPJ")
	private String cnpj;

	@Column(name="NOMEFANTASIA")
	private String nomeFantasia;

	@Column(name = "ABREVIATURA")
	private String abreviatura;

	@Column(name="INSTITUICAO_FINANCEIRA")
	private Boolean instituicaoFinanceira;
	
	@Column(name="TIPO_PJ")
	private String tipoPJ;

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getAbreviatura() {
		return abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	public Boolean getInstituicaoFinanceira() {
		return instituicaoFinanceira;
	}

	public void setInstituicaoFinanceira(Boolean instituicaoFinanceira) {
		this.instituicaoFinanceira = instituicaoFinanceira;
	}
	
	public String getTipoPJ() {
		return tipoPJ;
	}

	public void setTipoPJ(String tipoPJ) {
		this.tipoPJ = tipoPJ;
	}

	@Transient
	public String getCnpjFormatado(){
		StringBuilder cnpjFormatado = new StringBuilder(cnpj);
		cnpjFormatado.insert(cnpj.length() - 2, "-");
		cnpjFormatado.insert(cnpj.length() - 6, "/");
		cnpjFormatado.insert(cnpj.length() - 10, ".");
		cnpjFormatado.insert(cnpj.length() - 13, ".");
		
		return cnpjFormatado.toString();
	}
	

}
