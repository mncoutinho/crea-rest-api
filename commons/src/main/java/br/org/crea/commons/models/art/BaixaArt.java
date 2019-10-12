package br.org.crea.commons.models.art;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ART_BAIXA")
public class BaixaArt {
	
	@Id
	@Column(name="CODIGO")
	private Long id;
	
	@Column(name="DESCRICAO")
	private String descricao;
	
	@Column(name="SITUACAO")
	private String situacao;
	
	@Column(name="DESCRITIVO_SITUACAO")
	private String descritivoSituacao;
	
	@Column(name="ON_LINE")
	private Boolean onLine;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getDescritivoSituacao() {
		return descritivoSituacao;
	}

	public void setDescritivoSituacao(String descritivoSituacao) {
		this.descritivoSituacao = descritivoSituacao;
	}
	
	public Boolean heCancelada ()  {
		return this.id == 6L || this.id == 9L || this.id == 14L || this.id == 15L || this.id == 16L;
	}

	public Boolean getOnLine() {
		return onLine;
	}

	public void setOnLine(Boolean onLine) {
		this.onLine = onLine;
	}

}
