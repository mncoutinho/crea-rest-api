package br.org.crea.commons.models.cadastro.dtos.empresa;

import java.io.Serializable;
import java.util.List;

import br.org.crea.commons.models.cadastro.dtos.profissional.TituloProfissionalDto;

public class QuadroTecnicoEmpresaDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String registro;
	
	private String nome;
	
	private String anuidadePaga;
	
	private String dataInicioQuadroTecnico;
	
	private List<TituloProfissionalDto> titulos;
	
	private String responsavelTecnico;

	public String getRegistro() {
		return registro;
	}

	public void setRegistro(String registro) {
		this.registro = registro;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getAnuidadePaga() {
		return anuidadePaga;
	}

	public void setAnuidadePaga(String anuidadePaga) {
		this.anuidadePaga = anuidadePaga;
	}

	public String getDataInicioQuadroTecnico() {
		return dataInicioQuadroTecnico;
	}

	public void setDataInicioQuadroTecnico(String dataInicioQuadroTecnico) {
		this.dataInicioQuadroTecnico = dataInicioQuadroTecnico;
	}
	
	public List<TituloProfissionalDto> getTitulos() {
		return titulos;
	}

	public void setTitulos(List<TituloProfissionalDto> titulos) {
		this.titulos = titulos;
	}

	public String getResponsavelTecnico() {
		return responsavelTecnico;
	}

	public void setResponsavelTecnico(String responsavelTecnico) {
		this.responsavelTecnico = responsavelTecnico;
	}
	
}
