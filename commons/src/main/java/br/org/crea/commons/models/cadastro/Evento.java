package br.org.crea.commons.models.cadastro;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.org.crea.commons.models.corporativo.Assunto;


@Entity
@Table(name="CAD_EVENTOS")
@SequenceGenerator(name="sqEventos",sequenceName="CAD_EVENTOS_SEQ",allocationSize = 1)
public class Evento {

	@Id
	@Column(name="CODIGO")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sqEventos")
	private Long id;
	
	@OneToOne
	@JoinColumn(name="FK_CODIGO_ASSUNTO")
	private Assunto assunto;	
	
	@Column(name="DESCRICAO")
	private String descricao;
	
	@Column(name="PROFISSIONAL")
	private Boolean profissional;
	
	@Column(name="EMPRESA")
	private Boolean empresa;
	
	@Column(name="LEIGO")
	private Boolean leigo;
	
	@Column(name="PERMANENTE")
	private Boolean permanente;
	
	@Column(name="MOSTRA_FIM")
	private Boolean fim;
	
	@Column(name="IS_ETICO")
	private Boolean etico;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	

	public Assunto getAssunto() {
		return assunto;
	}

	public void setAssunto(Assunto assunto) {
		this.assunto = assunto;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Boolean getProfissional() {
		return profissional;
	}

	public void setProfissional(Boolean profissional) {
		this.profissional = profissional;
	}

	public Boolean getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Boolean empresa) {
		this.empresa = empresa;
	}

	public Boolean getLeigo() {
		return leigo;
	}

	public void setLeigo(Boolean leigo) {
		this.leigo = leigo;
	}

	public Boolean getPermanente() {
		return permanente;
	}

	public void setPermanente(Boolean permanente) {
		this.permanente = permanente;
	}

	public Boolean getFim() {
		return fim;
	}

	public void setFim(Boolean fim) {
		this.fim = fim;
	}

	public Boolean getEtico() {
		return etico;
	}

	public void setEtico(Boolean etico) {
		this.etico = etico;
	}

	

	
	


}
