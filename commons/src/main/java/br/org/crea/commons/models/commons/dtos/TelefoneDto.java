package br.org.crea.commons.models.commons.dtos;

public class TelefoneDto {
	
	private Long codigo;
	
	private Long codPessoa;
	
	private DomainGenericDto tipoTelefone;
	
	private String ddd;
	
	private String numero;
	
	private String telefoneFormatado;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Long getCodPessoa() {
		return codPessoa;
	}

	public void setCodPessoa(Long codPessoa) {
		this.codPessoa = codPessoa;
	}

	public DomainGenericDto getTipoTelefone() {
		return tipoTelefone;
	}

	public void setTipoTelefone(DomainGenericDto tipoTelefone) {
		this.tipoTelefone = tipoTelefone;
	}

	public String getDdd() {
		return ddd;
	}

	public void setDdd(String ddd) {
		this.ddd = ddd;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getTelefoneFormatado() {
		return telefoneFormatado;
	}

	public void setTelefoneFormatado(String telefoneFormatado) {
		this.telefoneFormatado = telefoneFormatado;
	}	
	
	public boolean temId(){
		return this.getCodigo() != null;
	}
}
