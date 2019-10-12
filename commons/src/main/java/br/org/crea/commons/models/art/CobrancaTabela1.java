package br.org.crea.commons.models.art;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ART_COBRANCA_TABELA1")
public class CobrancaTabela1 implements Serializable  {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CODIGO")
	private Long id;

	@Column(name="TIPO_TAXA_ESPECIAL")
	private String tipoTaxaEspecial;
	
	@Column(name="INICIO_VIGENCIA")
	private Long inicioVigencia;
	
	@Column(name="FIM_VIGENCIA")
	private Date fimVigencia;
	
	@Column(name="FK_ATIVIDADE_ART")
	private Long idAtividade;
	
	@Column(name="FK_ESPECIFICACAO_ART")
	private Long idEspecificacao;
	
	@Column(name="FK_COMPLEMENTO_ART")
	private Long idComplemento;
	
	@Column(name="FK_RAMO_ART")
	private RamoArt ramo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipoTaxaEspecial() {
		return tipoTaxaEspecial;
	}

	public void setTipoTaxaEspecial(String tipoTaxaEspecial) {
		this.tipoTaxaEspecial = tipoTaxaEspecial;
	}

	public Long getInicioVigencia() {
		return inicioVigencia;
	}

	public void setInicioVigencia(Long inicioVigencia) {
		this.inicioVigencia = inicioVigencia;
	}

	public Date getFimVigencia() {
		return fimVigencia;
	}

	public void setFimVigencia(Date fimVigencia) {
		this.fimVigencia = fimVigencia;
	}

	public Long getIdAtividade() {
		return idAtividade;
	}

	public void setIdAtividade(Long idAtividade) {
		this.idAtividade = idAtividade;
	}

	public Long getIdEspecificacao() {
		return idEspecificacao;
	}

	public void setIdEspecificacao(Long idEspecificacao) {
		this.idEspecificacao = idEspecificacao;
	}

	public Long getIdComplemento() {
		return idComplemento;
	}

	public void setIdComplemento(Long idComplemento) {
		this.idComplemento = idComplemento;
	}

	public RamoArt getRamo() {
		return ramo;
	}

	public void setRamo(RamoArt ramo) {
		this.ramo = ramo;
	}

	public boolean ehArq() {
		return this.tipoTaxaEspecial.equals("arq");
	}

	public boolean ehEst() {
		return this.tipoTaxaEspecial.equals("est");
	}

	public boolean ehEle() {
		return this.tipoTaxaEspecial.equals("ele");
	}

	public boolean ehHid() {
		return this.tipoTaxaEspecial.equals("hid");
	}

	public boolean ehOu() {
		return this.tipoTaxaEspecial.equals("ou");
	}
}
