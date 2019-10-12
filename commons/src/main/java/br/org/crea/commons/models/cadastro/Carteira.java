package br.org.crea.commons.models.cadastro;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.org.crea.commons.models.commons.UF;
import br.org.crea.commons.models.corporativo.pessoa.Profissional;

@Entity
@Table(name="CAD_CARTEIRAS")
public class Carteira implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="CODIGO")
	private Long codigo;
	
	@Column(name="NUMEROVIA")
	private Long numeroVia;
	
	@Column(name="NUMEROCARTEIRA")
	private String numeroCarteira;
	
	@ManyToOne
	@JoinColumn(name="FK_CODIGO_TIPOS_CARTEIRAS")
	private TipoCarteira tipoCarteira;
	
	@Column(name="DATACADASTRO")
	@Temporal(TemporalType.DATE)
	private Date dataCadastro;
	
	@Column(name="DATAEMISSAO")
	@Temporal(TemporalType.DATE)
	private Date dataEmissao;
	
	@Column(name="DATAENTREGA")
	@Temporal(TemporalType.DATE)
	private Date dataEntrega;
	
	@Column(name="DATACANCELAMENTO")
	@Temporal(TemporalType.DATE)
	private Date dataCancelamento;

	@Column(name="CANCELADA")
	private Boolean cancelada;
	
	@Column(name="MOTIVOCANCELAMENTO")
	private String motivoCancelamento;
	
	@ManyToOne
	@JoinColumn(name="FK_CODIGO_UFS")
	private UF uf;
	
	@ManyToOne
	@JoinColumn(name="FK_CODIGO_PROFISSIONAIS")
	private Profissional profissional;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Long getNumeroVia() {
		return numeroVia;
	}

	public void setNumeroVia(Long numeroVia) {
		this.numeroVia = numeroVia;
	}

	public String getNumeroCarteira() {
		return numeroCarteira;
	}

	public void setNumeroCarteira(String numeroCarteira) {
		this.numeroCarteira = numeroCarteira;
	}

	public TipoCarteira getTipoCarteira() {
		return tipoCarteira;
	}

	public void setTipoCarteira(TipoCarteira tipoCarteira) {
		this.tipoCarteira = tipoCarteira;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Date getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(Date dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	public Date getDataEntrega() {
		return dataEntrega;
	}

	public void setDataEntrega(Date dataEntrega) {
		this.dataEntrega = dataEntrega;
	}

	public Date getDataCancelamento() {
		return dataCancelamento;
	}

	public void setDataCancelamento(Date dataCancelamento) {
		this.dataCancelamento = dataCancelamento;
	}

	public Boolean getCancelada() {
		return cancelada;
	}

	public void setCancelada(Boolean cancelada) {
		this.cancelada = cancelada;
	}

	public String getMotivoCancelamento() {
		return motivoCancelamento;
	}

	public void setMotivoCancelamento(String motivoCancelamento) {
		this.motivoCancelamento = motivoCancelamento;
	}

	public UF getUf() {
		return uf;
	}

	public void setUf(UF uf) {
		this.uf = uf;
	}

	public Profissional getProfissional() {
		return profissional;
	}

	public void setProfissional(Profissional profissional) {
		this.profissional = profissional;
	}
	
}
