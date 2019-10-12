package br.org.crea.commons.models.cadastro.dtos.pessoa;

import java.util.List;

import br.org.crea.commons.models.commons.dtos.EnderecoDto;
import br.org.crea.commons.models.commons.dtos.TelefoneDto;

public class DadosContatoPessoaDto {
	
	private String email;
	
	private EnderecoDto enderecoPostal;
	
	private List<TelefoneDto> listTelefones;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public EnderecoDto getEnderecoPostal() {
		return enderecoPostal;
	}

	public void setEnderecoPostal(EnderecoDto enderecoPostal) {
		this.enderecoPostal = enderecoPostal;
	}

	public List<TelefoneDto> getListTelefones() {
		return listTelefones;
	}

	public void setListTelefones(List<TelefoneDto> listTelefones) {
		this.listTelefones = listTelefones;
	}

}
