package br.org.crea.commons.models.art;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ART_TIPO_ACAO")
public class ArtTipoAcao implements Serializable {
		

	/**
	 * 
	 */
	private static final long serialVersionUID = -6263395096817961799L;

	@Id
	@Column(name="CODIGO")
	private Long id;
	
	@Column(name="DESCRICAO")
	private String descricao;
	
	@Column(name="FLAG")
	private Boolean flag;
	
	@Column(name="OBRIGATORIO")
	private Boolean obrigatorio;

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

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public Boolean getObrigatorio() {
		return obrigatorio;
	}

	public void setObrigatorio(Boolean obrigatorio) {
		this.obrigatorio = obrigatorio;
	}
	
}
