package br.org.crea.commons.models.corporativo.pessoa;

import java.io.Serializable;
import java.util.Base64;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.org.crea.commons.models.cadastro.Departamento;
import br.org.crea.commons.models.corporativo.SituacaoRegistro;
import br.org.crea.commons.models.corporativo.enuns.TipoPessoa;

@Entity 
@Table(name="CAD_PROFISSIONAIS")
public class Profissional implements IInteressado, Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="CODIGO")
	public  Long id;
	
	@Column(name="REGISTRO")
	private String registro;
	
	@Column(name="RNP")
	private String numeroRNP;
	
	@Column(name="DATAREGISTRO")
	@Temporal(TemporalType.DATE)
	private Date dataRegistro;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="FK_ID_PESSOAS_FISICAS")
	private PessoaFisica pessoaFisica;
	
	@Column(name="DATAVISTO")
	@Temporal(TemporalType.DATE)
	public Date dataVisto;
	
	@Column(name="OBSERVACOESESPECIAIS")
	private String observacoesEspeciais;

	@Column(name="ANOTACOESESPECIAIS")
	private String anotacoesEspeciais;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRegistro() {
		return registro;
	}

	public void setRegistro(String registro) {
		this.registro = registro;
	}

	public Date getDataRegistro() {
		return dataRegistro;
	}

	public void setDataRegistro(Date dataRegistro) {
		this.dataRegistro = dataRegistro;
	}

	public PessoaFisica getPessoaFisica() {
		return pessoaFisica;
	}

	public void setPessoaFisica(PessoaFisica pessoaFisica) {
		this.pessoaFisica = pessoaFisica;
	}

	public String getObservacoesEspeciais() {
		return observacoesEspeciais;
	}

	public void setObservacoesEspeciais(String observacoesEspeciais) {
		this.observacoesEspeciais = observacoesEspeciais;
	}

	public String getAnotacoesEspeciais() {
		return anotacoesEspeciais;
	}

	public void setAnotacoesEspeciais(String anotacoesEspeciais) {
		this.anotacoesEspeciais = anotacoesEspeciais;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public TipoPessoa getTipoPessoa() {
		return TipoPessoa.PROFISSIONAL;
	}

	@Override
	public String getCpfCnpj() {
		return pessoaFisica.getCpf();
	}

	@Override
	public String getNomeRazaoSocial() {
		return this.getPessoaFisica() != null ? this.getPessoaFisica().getNome() : "";
	}

	@Override
	public void setNomeRazaoSocial(String nomeRazaoSocial) {
		
	}

	@Override
	public String getNome() {
		return this.getPessoaFisica() != null ? this.getPessoaFisica().getNome() : "";
	}

	@Override
	public String getFotoBase64() {
		if(pessoaFisica.getFotografia() != null){
			StringBuilder sb = new StringBuilder();
			sb.append("data:image/png;base64,");
			sb.append(Base64.getEncoder().encodeToString(pessoaFisica.getFotografia()));
			return sb.toString();
		}else{
			return "";
		}
	}

	@Override
	public SituacaoRegistro getSituacao() {
		if(pessoaFisica != null){
			return pessoaFisica.getSituacao();
			
		}
		return null;
	}
	
	@Override
	public String getPerfil() {
		if(pessoaFisica != null){
			return pessoaFisica.getDescricaoPerfil();
			
		}
		return null;
	}

	public String getNumeroRNP() {
		return numeroRNP;
	}

	public void setNumeroRNP(String numeroRNP) {
		this.numeroRNP = numeroRNP;
	}

	public Date getDataVisto() {
		return dataVisto;
	}

	public void setDataVisto(Date dataVisto) {
		this.dataVisto = dataVisto;
	}

	@Override
	public Departamento getDepartamento() {
		return null;
	}

	@Override
	public Long getMatricula() {
		return null;
	}

	@Override
	public void setDepartamento(Departamento departamento) {
		
	}
	
	
	public Long getSexo() {
		if (pessoaFisica.getTipoSexo() == null) {
			return new Long(0);
			
		} else {
			return pessoaFisica.getTipoSexo().getId();
		}
	}

	@Override
	public Long getIdPessoa() {
		return this.pessoaFisica.getId();
	}
	
	public boolean situacaoEhNormal() {
		return this.getSituacao().getDescricao().equals("NORMAL");
	}

	public boolean situacaoEhInativa() {
		return this.getSituacao().getDescricao().equals("INTERROMPIDO") 
			|| this.getSituacao().getDescricao().equals("CANCELADO") 
			|| this.getSituacao().getDescricao().equals("SUSPENSO")
			|| this.getSituacao().getDescricao().equals("SEM VALIDADE");
	}

	public boolean temDataRegistro() {
		return this.dataRegistro != null;
	}
	
}
