package br.org.crea.commons.models.corporativo.pessoa;

import java.beans.Transient;
import java.io.Serializable;
import java.util.Base64;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.org.crea.commons.models.cadastro.EstadoCivil;
import br.org.crea.commons.models.cadastro.FatorRh;
import br.org.crea.commons.models.cadastro.Nacionalidade;
import br.org.crea.commons.models.cadastro.NecessidadeEspecial;
import br.org.crea.commons.models.cadastro.OrgaoEmissorIdentidade;
import br.org.crea.commons.models.cadastro.TipoSexo;
import br.org.crea.commons.models.commons.Localidade;
import br.org.crea.commons.models.commons.UF;

@Entity
@Table(name = "CAD_PESSOAS_FISICAS")
@PrimaryKeyJoinColumn(name = "CODIGO")
public class PessoaFisica extends Pessoa implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "NOME")
	private String nomePessoaFisica;

	@Column(name = "NOME_SOCIAL")
	private String nomeSocial;

	@Column(name = "CPF")
	private String cpf;

	@Column(name = "FOTOGRAFIA")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] fotografia;

	@OneToOne
	@JoinColumn(name = "SEXO")
	private TipoSexo tipoSexo;

	@Column(name = "NOMEPAI")
	private String nomePai;

	@Column(name = "NOMEMAE")
	private String nomeMae;

	@OneToOne
	@JoinColumn(name = "FK_CODIGO_NACIONALIDADES")
	private Nacionalidade nacionalidade;

	@OneToOne
	@JoinColumn(name = "FK_CODIGO_NATURALIDADES")
	private UF naturalidade;

	@OneToOne
	@JoinColumn(name = "FK_CODIGO_LOCALIDADE_NATURAL")
	private Localidade localidadeNaturalidade;

	@OneToOne
	@JoinColumn(name = "FK_CODIGO_ESTADOS_CIVIS")
	private EstadoCivil estadoCivil;
	
	@Column(name = "DATANASCIMENTO")
	@Temporal(TemporalType.DATE)
	private Date dataNascimento;
	
	@OneToOne
	@JoinColumn(name = "FK_CODIGO_NECESS_ESPECIAIS")
	private NecessidadeEspecial necessidadeEspecial;
	
	@OneToOne
	@JoinColumn(name = "FK_CODIGO_FATORES_RH")
	private FatorRh fatorRh;
	
	@Column(name = "DOADOR")
	private Boolean doador;
	
	@Column(name = "PISPASEP")
	private String pisPasep;

	@Column(name = "IDENTIDADE")
	private String identidade;
	
	@OneToOne
	@JoinColumn(name = "FK_CODIGO_ORGAO_EMISSOR_IDENT")
	private OrgaoEmissorIdentidade orgaoEmissorIdentidade;

	@Column(name = "DATAEXPEDICAOIDENTIDADE")
	@Temporal(TemporalType.DATE)
	private Date dataExpedicaoIdentidade;
	
	@Column(name = "TITULOELEITORAL")
	private String tituloEleitoral;
	
	@Column(name = "ZONAELEITORAL")
	private String zonaEleitoral;
	
	@Column(name = "SECAOELEITORAL")
	private String secaoEleitoral;
	
	@OneToOne
	@JoinColumn(name = "FK_CODIGO_LOCALIDADE_ELEITOR")
	private Localidade localidadeEleitoral;
	
	@OneToOne
	@JoinColumn(name = "FK_CODIGO_UF_ELEITORAL")
	private UF ufEleitoral;
	
	@Column(name = "ASSINATURA")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] assinatura;

	public String getNome() {
		return nomePessoaFisica;
	}

	public void setNome(String nome) {
		this.nomePessoaFisica = nome;
	}

	public String getNomeSocial() {
		return nomeSocial;
	}

	public void setNomeSocial(String nomeSocial) {
		this.nomeSocial = nomeSocial;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public byte[] getFotografia() {
		return fotografia;
	}

	public void setFotografia(byte[] fotografia) {
		this.fotografia = fotografia;
	}

	public TipoSexo getTipoSexo() {
		return tipoSexo;
	}

	public void setTipoSexo(TipoSexo tipoSexo) {
		this.tipoSexo = tipoSexo;
	}

	@Transient
	public String getCpfFormatado() {
		if (this.cpf != null) {
			StringBuilder cpfFormatado = new StringBuilder(cpf);
			cpfFormatado.insert(cpf.length() - 2, "-");
			cpfFormatado.insert(cpf.length() - 5, ".");
			cpfFormatado.insert(cpf.length() - 8, ".");

			return cpfFormatado.toString();
		}
		return null;		
	}

	public String getNomePai() {
		return nomePai;
	}

	public void setNomePai(String nomePai) {
		this.nomePai = nomePai;
	}

	public String getNomeMae() {
		return nomeMae;
	}

	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}

	public Nacionalidade getNacionalidade() {
		return nacionalidade;
	}

	public void setNacionalidade(Nacionalidade nacionalidade) {
		this.nacionalidade = nacionalidade;
	}

	public UF getNaturalidade() {
		return naturalidade;
	}

	public void setNaturalidade(UF naturalidade) {
		this.naturalidade = naturalidade;
	}

	public Localidade getLocalidadeNaturalidade() {
		return localidadeNaturalidade;
	}

	public void setLocalidadeNaturalidade(Localidade localidadeNaturalidade) {
		this.localidadeNaturalidade = localidadeNaturalidade;
	}

	public EstadoCivil getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(EstadoCivil estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public NecessidadeEspecial getNecessidadeEspecial() {
		return necessidadeEspecial;
	}

	public void setNecessidadeEspecial(NecessidadeEspecial necessidadeEspecial) {
		this.necessidadeEspecial = necessidadeEspecial;
	}

	public FatorRh getFatorRh() {
		return fatorRh;
	}

	public void setFatorRh(FatorRh fatorRh) {
		this.fatorRh = fatorRh;
	}

	public Boolean getDoador() {
		return doador;
	}

	public void setDoador(Boolean doador) {
		this.doador = doador;
	}

	public String getPisPasep() {
		return pisPasep;
	}

	public void setPisPasep(String pisPasep) {
		this.pisPasep = pisPasep;
	}

	public String getIdentidade() {
		return identidade;
	}

	public void setIdentidade(String identidade) {
		this.identidade = identidade;
	}

	public Date getDataExpedicaoIdentidade() {
		return dataExpedicaoIdentidade;
	}

	public void setDataExpedicaoIdentidade(Date dataExpedicaoIdentidade) {
		this.dataExpedicaoIdentidade = dataExpedicaoIdentidade;
	}

	public String getTituloEleitoral() {
		return tituloEleitoral;
	}

	public void setTituloEleitoral(String tituloEleitoral) {
		this.tituloEleitoral = tituloEleitoral;
	}

	public String getZonaEleitoral() {
		return zonaEleitoral;
	}

	public void setZonaEleitoral(String zonaEleitoral) {
		this.zonaEleitoral = zonaEleitoral;
	}

	public String getSecaoEleitoral() {
		return secaoEleitoral;
	}

	public void setSecaoEleitoral(String secaoEleitoral) {
		this.secaoEleitoral = secaoEleitoral;
	}

	public Localidade getLocalidadeEleitoral() {
		return localidadeEleitoral;
	}

	public void setLocalidadeEleitoral(Localidade localidadeEleitoral) {
		this.localidadeEleitoral = localidadeEleitoral;
	}

	public UF getUfEleitoral() {
		return ufEleitoral;
	}

	public void setUfEleitoral(UF ufEleitoral) {
		this.ufEleitoral = ufEleitoral;
	}

	public OrgaoEmissorIdentidade getOrgaoEmissorIdentidade() {
		return orgaoEmissorIdentidade;
	}

	public void setOrgaoEmissorIdentidade(OrgaoEmissorIdentidade orgaoEmissorIdentidade) {
		this.orgaoEmissorIdentidade = orgaoEmissorIdentidade;
	}

	public byte[] getAssinatura() {
		return assinatura;
	}

	public void setAssinatura(byte[] assinatura) {
		this.assinatura = assinatura;
	}
	
	public String getAssinaturaBase64() {
		if(this.assinatura != null){
			StringBuilder sb = new StringBuilder();
			sb.append("data:image/png;base64,");
			sb.append(Base64.getEncoder().encodeToString(this.assinatura));
			return sb.toString();
		}else{
			return "";
		}
	}

}
