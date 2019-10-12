package br.org.crea.commons.models.corporativo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.org.crea.commons.models.protocolo.enuns.TipoAssuntoEnum;

@Entity
@Table(name = "PRT_ASSUNTOS")
public class Assunto {

	@Id
	@Column(name="ID")
	private Long id;
	
	@Column(name="CODIGO")
	private Long codigo;

	@Column(name="DESCRICAO")
	private String descricao;

	@Column(name="MOBI")
	private Integer mobile;

	@Column(name="VIAPORTAL")
	private Boolean viaPortal;
	
	@Column(name="REMOVIDO")
	private Boolean removido;
	
	@Column(name="SIACOL")
	private Boolean siacol;
	
	@Enumerated(EnumType.STRING)
	@Column(name="TIPOASSUNTO")
	private TipoAssuntoEnum tipoAssunto;
	
	@OneToOne
	@JoinColumn(name="FK_ID_BLOCOSASSUNTOS")
	private BlocosAssuntos bloco;
	
	@Column(name="DOCFLOW")
	private boolean docflow;
	
	@Column(name="DEVE_DIGITALIZAR")
	private boolean deveDigitalizar;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public boolean isDeveDigitalizar() {
		return deveDigitalizar;
	}

	public void setDeveDigitalizar(boolean deveDigitalizar) {
		this.deveDigitalizar = deveDigitalizar;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getMobile() {
		return mobile;
	}

	public void setMobile(Integer mobile) {
		this.mobile = mobile;
	}

	public Boolean getViaPortal() {
		return viaPortal;
	}

	public void setViaPortal(Boolean viaPortal) {
		this.viaPortal = viaPortal;
	}

	public Boolean getRemovido() {
		return removido;
	}

	public void setRemovido(Boolean removido) {
		this.removido = removido;
	}

	public Boolean getSiacol() {
		return siacol;
	}

	public void setSiacol(Boolean siacol) {
		this.siacol = siacol;
	}

	public TipoAssuntoEnum getTipoAssunto() {
		return tipoAssunto;
	}

	public void setTipoAssunto(TipoAssuntoEnum tipoAssunto) {
		this.tipoAssunto = tipoAssunto;
	}

	public boolean isDocflow() {
		return docflow;
	}

	public void setDocflow(boolean docflow) {
		this.docflow = docflow;
	}

	public boolean ehObrigatorioDocflow(){
		return docflow && deveDigitalizar ? true : false;
	}

	public BlocosAssuntos getBloco() {
		return bloco;
	}

	public void setBloco(BlocosAssuntos bloco) {
		this.bloco = bloco;
	}
	
	
	
}
