package br.org.crea.commons.models.cadastro.dtos.profissional;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.org.crea.commons.models.commons.dtos.DomainGenericDto;

public class ProfissionalHomologacaoDto {
	
	private Long registro;
	
	private String nome;
	
	private String dataInclusaoRegistro;
	
	private String dataRegistro;
	
	private String tituloCadastrado;
	
	private DomainGenericDto especialidade;
	
	private String nivelTitulo;
	
	private String escolaObtencaoTitulo;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataUpdate;

	public Long getRegistro() {
		return registro;
	}

	public void setRegistro(Long registro) {
		this.registro = registro;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDataInclusaoRegistro() {
		return dataInclusaoRegistro;
	}

	public void setDataInclusaoRegistro(String dataInclusaoRegistro) {
		this.dataInclusaoRegistro = dataInclusaoRegistro;
	}

	public String getDataRegistro() {
		return dataRegistro;
	}

	public void setDataRegistro(String dataRegistro) {
		this.dataRegistro = dataRegistro;
	}

	public String getTituloCadastrado() {
		return tituloCadastrado;
	}

	public void setTituloCadastrado(String tituloCadastrado) {
		this.tituloCadastrado = tituloCadastrado;
	}

	public DomainGenericDto getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(DomainGenericDto especialidade) {
		this.especialidade = especialidade;
	}

	public String getNivelTitulo() {
		return nivelTitulo;
	}

	public void setNivelTitulo(String nivelTitulo) {
		this.nivelTitulo = nivelTitulo;
	}

	public String getEscolaObtencaoTitulo() {
		return escolaObtencaoTitulo;
	}

	public void setEscolaObtencaoTitulo(String escolaObtencaoTitulo) {
		this.escolaObtencaoTitulo = escolaObtencaoTitulo;
	}

	public Date getDataUpdate() {
		return dataUpdate;
	}

	public void setDataUpdate(Date dataUpdate) {
		this.dataUpdate = dataUpdate;
	}

}
