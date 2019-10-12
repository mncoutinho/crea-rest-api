package br.org.crea.commons.models.commons;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.org.crea.commons.models.commons.enuns.TipoRlArquivo;

@Entity
@Table(name="CAD_RL_ARQUIVOS")
@SequenceGenerator(name = "sqRlArquivo", sequenceName = "CAD_RL_ARQUIVOS_SEQ", initialValue = 1, allocationSize = 1)
public class RlArquivo {
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sqRlArquivo")
	private Long id;
	
	@Enumerated(EnumType.STRING)
	@Column(name="TIPO_RL_ARQUIVO")
	private TipoRlArquivo tipoRlArquivo;
	
	@Column(name = "FK_ID_TIPO")
	private Long idTipoRlArquivo;

	@OneToOne
	@JoinColumn(name = "FK_ID_ARQUIVOS")
	private Arquivo arquivo;
	
	@Column(name = "POSICAO")
	private String posicao;
	
	@Column(name = "DESCRICAO")
	private String descricao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoRlArquivo getTipoRlArquivo() {
		return tipoRlArquivo;
	}

	public void setTipoRlArquivo(TipoRlArquivo tipoRlArquivo) {
		this.tipoRlArquivo = tipoRlArquivo;
	}

	public Long getIdTipoRlArquivo() {
		return idTipoRlArquivo;
	}

	public void setIdTipoRlArquivo(Long idTipoRlArquivo) {
		this.idTipoRlArquivo = idTipoRlArquivo;
	}

	public Arquivo getArquivo() {
		return arquivo;
	}

	public void setArquivo(Arquivo arquivo) {
		this.arquivo = arquivo;
	}

	public boolean temTipoRlArquivo() {
		return this.tipoRlArquivo != null;
	}

	public String getPosicao() {
		return posicao;
	}

	public void setPosicao(String posicao) {
		this.posicao = posicao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	
	
}
