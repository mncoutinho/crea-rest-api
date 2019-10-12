package br.org.crea.commons.models.cadastro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name="CAD_PROFXESPEC")
public class RlProfissionalEspecialidade implements Serializable {
	
	private static final long serialVersionUID = 1L;


	@Id
	@OneToOne
	@JoinColumn(name="FK_CODIGO_ESPECIALIDADES")
	private Especialidade especialidade;


	@Id
	@Column(name="FK_CODIGO_PROFISSIONAIS")
	private Long codigoProfissional;
	
	
	@Id
	@Column(name="FK_CODIGO_INSTITUICOES_ENSINO")
	private Long codigoIntituicaoEnsino;
	
	
	@ManyToOne
	@JoinColumn(name="FK_CODIGO_TITULOS")
	private Titulo titulo;

	@ManyToOne
	@JoinColumn(name="FK_CODIGO_CONFEA_TITULOS")
	private ConfeaTitulo confeaTitulo;
	
	
	@ManyToOne
	@JoinColumn(name="FK_CODIGO_ESCOLARIDADES")
	private Escolaridade escolaridade;

	@Temporal(TemporalType.DATE)
	@Column(name="DATACANCELAMENTO")
	private Date dataCancelamento;
	
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATACOLACAOGRAU")
	private Date dataColacaoGrau;
	
	@ManyToOne
	@JoinColumn(name="FK_CODIGO_TIPOS_TITULOS")
	private TipoTitulo tipoTitulo;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATAINCLUSAO")
	private Date dataInclusao;

	@Temporal(TemporalType.DATE)
	@Column(name="DATAVALIDADE")
	private Date dataValidade;
	
	@Column(name="NUMERODIPLOMA")
	private String diploma;

	@Temporal(TemporalType.DATE)
	@Column(name="DATAEXPEDICAODIPLOMA")
	private Date dataExpedicaoDiploma;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATAFORMATURA")
	private Date dataFormatura;

	@Column(name="MOTIVO")
	private String motivo;

	@Column(name="APOSTILA")
	private String apostila;
	
	@Column(name="OBSERVACAO")
	private String observacao;
	
	@Column(name="FK_CODIGO_MODALIDADES")
	private Long idModalidade;
	
	@Column(name="OPCAO")
	private boolean opcao;
	
	@Transient
	private List<String> atribuicoesTitulo;
	
	@Transient
	private List<String> dispositivosAtribuicoesTitulo;

	public Especialidade getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(Especialidade especialidade) {
		this.especialidade = especialidade;
	}

	public Long getCodigoProfissional() {
		return codigoProfissional;
	}

	public void setCodigoProfissional(Long codigoProfissional) {
		this.codigoProfissional = codigoProfissional;
	}

	public Long getCodigoIntituicaoEnsino() {
		return codigoIntituicaoEnsino;
	}

	public void setCodigoIntituicaoEnsino(Long codigoIntituicaoEnsino) {
		this.codigoIntituicaoEnsino = codigoIntituicaoEnsino;
	}

	public Titulo getTitulo() {
		return titulo;
	}

	public void setTitulo(Titulo titulo) {
		this.titulo = titulo;
	}

	public ConfeaTitulo getConfeaTitulo() {
		return confeaTitulo;
	}

	public void setConfeaTitulo(ConfeaTitulo confeaTitulo) {
		this.confeaTitulo = confeaTitulo;
	}

	public Date getDataCancelamento() {
		return dataCancelamento;
	}

	public void setDataCancelamento(Date dataCancelamento) {
		this.dataCancelamento = dataCancelamento;
	}

	public Escolaridade getEscolaridade() {
		return escolaridade;
	}

	public void setEscolaridade(Escolaridade escolaridade) {
		this.escolaridade = escolaridade;
	}

	public Date getDataColacaoGrau() {
		return dataColacaoGrau;
	}

	public void setDataColacaoGrau(Date dataColacaoGrau) {
		this.dataColacaoGrau = dataColacaoGrau;
	}
	
	public boolean temTituloDoConfeaEstaCadastrado(){
		return this.getConfeaTitulo() != null &&  !this.getConfeaTitulo().getId().equals(0L) ? true : false;
	}
	

	public String getTituloDescricao(Long sexo) {
		
		if(sexo == new Long(1) && this.getTitulo().getDescricaoFeminino() != null){
			return !this.getTitulo().getDescricaoFeminino().equals(this.getEspecialidade().getDescricao()) ?  this.getTitulo().getDescricaoFeminino() + " / " + this.getEspecialidade().getDescricao(): this.getTitulo().getDescricaoFeminino();
		}else{
			return !this.getTitulo().getDescricao().equals(this.getEspecialidade().getDescricao()) ?  this.getTitulo().getDescricao() + " / " + this.getEspecialidade().getDescricao(): this.getTitulo().getDescricao();
		}
		
	}
	
	public String getConfeaDescricao(Long sexo) {
		
		if(sexo == new Long(1) && this.getConfeaTitulo().getDescricaoFeminino() != null){
			return  this.getConfeaTitulo().getDescricaoFeminino();
		}else{
			return this.getConfeaTitulo().getDescricao();
		
		
		}
	}
	
	public List<String> getAtribuicoesTitulo() {
		return atribuicoesTitulo == null ? this.atribuicoesTitulo = new ArrayList<String>() : atribuicoesTitulo; 
	}

	public void setAtribuicoesTitulo(List<String> atribuicoesTitulo) {
		this.atribuicoesTitulo = atribuicoesTitulo;
	}

	public List<String> getDispositivosAtribuicoesTitulo() {
		return dispositivosAtribuicoesTitulo == null ? this.dispositivosAtribuicoesTitulo = new ArrayList<String>() : dispositivosAtribuicoesTitulo;
	}

	public void setDispositivosAtribuicoesTitulo(
			List<String> dispositivosAtribuicoesTitulo) {
		this.dispositivosAtribuicoesTitulo = dispositivosAtribuicoesTitulo;
	}

	public TipoTitulo getTipoTitulo() {
		return tipoTitulo;
	}

	public void setTipoTitulo(TipoTitulo tipoTitulo) {
		this.tipoTitulo = tipoTitulo;
	}

	public Date getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}
	
	public Date getDataValidade() {
		return dataValidade;
	}

	public void setDataValidade(Date dataValidade) {
		this.dataValidade = dataValidade;
	}
	
	public String getDiploma() {
		return diploma;
	}

	public void setDiploma(String diploma) {
		this.diploma = diploma;
	}

	public Date getDataExpedicaoDiploma() {
		return dataExpedicaoDiploma;
	}

	public void setDataExpedicaoDiploma(Date dataExpedicaoDiploma) {
		this.dataExpedicaoDiploma = dataExpedicaoDiploma;
	}

	public Date getDataFormatura() {
		return dataFormatura;
	}

	public void setDataFormatura(Date dataFormatura) {
		this.dataFormatura = dataFormatura;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	
	public String getApostila() {
		return apostila;
	}

	public void setApostila(String apostila) {
		this.apostila = apostila;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigoIntituicaoEnsino == null) ? 0 : codigoIntituicaoEnsino.hashCode());
		result = prime * result + ((codigoProfissional == null) ? 0 : codigoProfissional.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RlProfissionalEspecialidade other = (RlProfissionalEspecialidade) obj;
		if (codigoIntituicaoEnsino == null) {
			if (other.codigoIntituicaoEnsino != null)
				return false;
		} else if (!codigoIntituicaoEnsino.equals(other.codigoIntituicaoEnsino))
			return false;
		if (codigoProfissional == null) {
			if (other.codigoProfissional != null)
				return false;
		} else if (!codigoProfissional.equals(other.codigoProfissional))
			return false;
		return true;
	}

	public Long getIdModalidade() {
		return idModalidade;
	}

	public void setIdModalidade(Long idModalidade) {
		this.idModalidade = idModalidade;
	}

	public boolean isOpcao() {
		return opcao;
	}

	public void setOpcao(boolean opcao) {
		this.opcao = opcao;
	}	

}
