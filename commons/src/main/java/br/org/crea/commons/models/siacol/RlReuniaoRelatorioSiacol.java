package br.org.crea.commons.models.siacol;

import java.io.Serializable;

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

import br.org.crea.commons.models.commons.Arquivo;
import br.org.crea.commons.models.siacol.enuns.TipoRelatorioSiacolEnum;

@Entity
@Table(name = "SIACOL_RL_REUNIAO_RELATORIO")
@SequenceGenerator(name = "sqSiacolRlReuniaoRelatorio", sequenceName = "SQ_RL_REUNIAO_RELATORIO", initialValue = 1, allocationSize = 1)
public class RlReuniaoRelatorioSiacol implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sqSiacolRlReuniaoRelatorio")
	private Long id;

	@OneToOne
	@JoinColumn(name = "FK_REUNIAO")
	private ReuniaoSiacol reuniao;

	@OneToOne
	@JoinColumn(name = "FK_ARQUIVO")
	private Arquivo relatorio;

	@Enumerated(EnumType.STRING)
	@Column(name = "TIPO")
	private TipoRelatorioSiacolEnum tipo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ReuniaoSiacol getReuniao() {
		return reuniao;
	}

	public void setReuniao(ReuniaoSiacol reuniao) {
		this.reuniao = reuniao;
	}

	public Arquivo getRelatorio() {
		return relatorio;
	}

	public void setRelatorio(Arquivo relatorio) {
		this.relatorio = relatorio;
	}

	public TipoRelatorioSiacolEnum getTipo() {
		return tipo;
	}

	public void setTipo(TipoRelatorioSiacolEnum tipo) {
		this.tipo = tipo;
	}
}
