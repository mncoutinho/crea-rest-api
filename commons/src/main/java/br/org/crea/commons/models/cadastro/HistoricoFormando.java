package br.org.crea.commons.models.cadastro;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CAD_HIST_FORMANDO")
public class HistoricoFormando {
	
	@Column(name="PROTOCOLO")
	private String protocolo;
	
	@Column(name="PRECISAO_DT")
	private String  precisao;
	
	@Column(name="DT_FORMATURA")
	private Date  dataFormatura;
	
	@OneToOne
	@JoinColumn(name="FK_CODIGO_INST_ENSINO")
	private CampusCadastro campusCadastro;
	
	@OneToOne
	@JoinColumn(name="FK_CODIGO_CAMPI")
	private CursoCadastro cursoCadastro;
	
	@OneToOne
	@JoinColumn(name="FK_CURSOS")
	private InstituicaoEnsino instituicaoEnsino;

}