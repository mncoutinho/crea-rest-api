package br.org.crea.commons.models.fiscalizacao;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.org.crea.commons.models.corporativo.pessoa.Pessoa;

@Entity
@Table(name="FIS_CONTRATO_INATIVADO")
@SequenceGenerator(name = "sqContratoInativado", sequenceName = "FIS_CONTRATO_INATIVADO_SEQ", initialValue = 1, allocationSize = 1)
public class ContratoInativado {
	
	@Id
	@Column(name="CODIGO")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sqContratoInativado")
	private Long codigo;
	
	@OneToOne
	@JoinColumn(name="FK_PESSOA")
	private Pessoa pessoa;
	
	@OneToOne
	@JoinColumn(name="FK_ATIVIDADE")
	private ContratoAtividade atividade;
	
	@Column(name="DATA")
	@Temporal(TemporalType.DATE)
	private Date data;
	
	@Column(name="MOTIVO")
	private String motivo;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public ContratoAtividade getAtividade() {
		return atividade;
	}

	public void setAtividade(ContratoAtividade atividade) {
		this.atividade = atividade;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	
	
}
