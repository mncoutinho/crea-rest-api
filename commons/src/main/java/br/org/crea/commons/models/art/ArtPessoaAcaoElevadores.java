package br.org.crea.commons.models.art;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="ART_PESSOA_ACAO_ELEVADORES")
public class ArtPessoaAcaoElevadores implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6215174315028446728L;
	
	@Column(name="FK_PESSOA")
	private Long idPessoa;

	@Column(name="FK_FUNCIONARIO_INCLUSAO")
	private Long funcionarioInclusao;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_INCLUSAO")
	private Date dataInclusao;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_INICIO")
	private Date dataInicio;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_FIM")
	private Date dataFim;
	
	@Column(name="TIPO")
	private Long tipo;

	public Long getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(Long idPessoa) {
		this.idPessoa = idPessoa;
	}

	public Long getFuncionarioInclusao() {
		return funcionarioInclusao;
	}

	public void setFuncionarioInclusao(Long funcionarioInclusao) {
		this.funcionarioInclusao = funcionarioInclusao;
	}

	public Date getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public Long getTipo() {
		return tipo;
	}

	public void setTipo(Long tipo) {
		this.tipo = tipo;
	}

	public boolean estaVigente() {
		return this.dataFim.after(new Date());
	}
}
