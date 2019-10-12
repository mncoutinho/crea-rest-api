package br.org.crea.commons.models.siacol;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;


@Entity
@Table(name="SIACOL_REUNIAO_PRESENCA")
@SequenceGenerator(name = "sqSiacolReuniaoPresenca", sequenceName = "SQ_SIACOL_REUNIAO_PRESENCA", initialValue = 1, allocationSize = 1)
public class PresencaReuniaoSiacol implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sqSiacolReuniaoPresenca")
	private Long id;
	
	@OneToOne
	@JoinColumn(name="FK_REUNIAO")
	private ReuniaoSiacol reuniao;
	
	@OneToOne
	@JoinColumn(name="FK_PESSOA")
	private Pessoa pessoa;
	
	@Column(name="HORA_ENTREGA_CRACHA")
	private Date horaEntregaCracha;
	
	@Column(name="HORA_DEVOLUCAO_CRACHA")
	private Date horaDevolucaoCracha;
	
	@Column(name="PAPEL")
	private String papel;
	
	@Column(name="TIPO")
	private String tipo;
	
	@Column(name="VOTO_MINERVA")
	private Boolean votoMinerva;
	
	@Column(name="ATINGIU_80")
	private Boolean atingiu80;
	
	@Column(name="HORA_80")
	private Date hora80;
	
	@Column(name="PARTE")
	private Long parte;
	
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

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Date getHoraEntregaCracha() {
		return horaEntregaCracha;
	}

	public void setHoraEntregaCracha(Date horaEntregaCracha) {
		this.horaEntregaCracha = horaEntregaCracha;
	}

	public Date getHoraDevolucaoCracha() {
		return horaDevolucaoCracha;
	}

	public void setHoraDevolucaoCracha(Date horaDevolucaoCracha) {
		this.horaDevolucaoCracha = horaDevolucaoCracha;
	}

	public String getPapel() {
		return papel;
	}

	public void setPapel(String papel) {
		this.papel = papel;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Boolean isVotoMinerva() {
		return votoMinerva;
	}

	public void setVotoMinerva(Boolean votoMinerva) {
		this.votoMinerva = votoMinerva;
	}

	public Boolean getAtingiu80() {
		return atingiu80;
	}

	public void setAtingiu80(Boolean atingiu80) {
		this.atingiu80 = atingiu80;
	}

	public Date getHora80() {
		return hora80;
	}

	public void setHora80(Date hora80) {
		this.hora80 = hora80;
	}

	public Boolean getVotoMinerva() {
		return votoMinerva;
	}

	public boolean temReuniao() {
		return this.reuniao != null;
	}
	
	public Long getParte() {
		return parte;
	}

	public void setParte(Long parte) {
		this.parte = parte;
	}
	
	public boolean temPessoa() {
		return this.pessoa != null;
	}

	public boolean naoAtingiu80() {
		if (this.atingiu80 != null) {
			return !this.atingiu80;
		}
		return true;
	}

}
