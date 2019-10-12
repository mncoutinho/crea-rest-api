package br.org.crea.commons.models.cadastro;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.org.crea.commons.models.commons.enuns.ModuloSistema;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;

@Entity
@Table(name="PRT_DEPARTAMENTOS")
public class Departamento {
	
	@Id
	@Column(name="ID")
	private Long id;
	
	@Column(name="CODIGO")
	private Long codigo;
	
	@Column(name="NOME")
	private String nome;

	@Column(name="NOME_EXIBICAO")
	private String nomeExibicao;
	
	@Column(name="SIGLA")
	private String sigla;
	
	@ManyToOne
	@JoinColumn(name="FK_ID_DEPARTAMENTOS")
	private Departamento departamentoPai;
	
	@Column(name="EMAILCOORDENACAO")
	private String emailCoordenacao;
	
	@Column(name="REMOVIDO")
	private Boolean removido;
	
	@Column(name="ATENDIMENTO")
	private Boolean atendimento;
	
	@Column(name="IMPORTACAO_SIACOL")
	private Boolean importacaoSiacol;
	
	@Column(name="JUL_REVELIA")
	private Boolean executaJulgamentoRevelia;
	
	@Column(name="ENV_REVELIA")
	private Boolean enviaParaJulgamentoRevelia;
	
	@Column(name="DIVIDAATIVA")
	private boolean dividaAtiva;
	
	@Column(name="MODULO")
	private String modulo;
	
	@OneToOne
	@JoinColumn(name="FK_COORDENADOR")
	private Pessoa coordenador;
	
	@OneToOne
	@JoinColumn(name="FK_ADJUNTO")
	private Pessoa adjunto;

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getNomeExibicao() {
		return nomeExibicao;
	}

	public String getSigla() {
		return sigla;
	}

	public Departamento getDepartamentoPai() {
		return departamentoPai;
	}

	public String getEmailCoordenacao() {
		return emailCoordenacao;
	}

	public Boolean getRemovido() {
		return removido;
	}

	public Boolean getAtendimento() {
		return atendimento;
	}

	public Boolean getImportacaoSiacol() {
		return importacaoSiacol;
	}

	public Boolean getExecutaJulgamentoRevelia() {
		return executaJulgamentoRevelia;
	}

	public Boolean getEnviaParaJulgamentoRevelia() {
		return enviaParaJulgamentoRevelia;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setNomeExibicao(String nomeExibicao) {
		this.nomeExibicao = nomeExibicao;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public void setDepartamentoPai(Departamento departamentoPai) {
		this.departamentoPai = departamentoPai;
	}

	public void setEmailCoordenacao(String emailCoordenacao) {
		this.emailCoordenacao = emailCoordenacao;
	}

	public void setRemovido(Boolean removido) {
		this.removido = removido;
	}

	public void setAtendimento(Boolean atendimento) {
		this.atendimento = atendimento;
	}

	public void setImportacaoSiacol(Boolean importacaoSiacol) {
		this.importacaoSiacol = importacaoSiacol;
	}

	public void setExecutaJulgamentoRevelia(Boolean executaJulgamentoRevelia) {
		this.executaJulgamentoRevelia = executaJulgamentoRevelia;
	}

	public void setEnviaParaJulgamentoRevelia(Boolean enviaParaJulgamentoRevelia) {
		this.enviaParaJulgamentoRevelia = enviaParaJulgamentoRevelia;
	}

	public boolean isDividaAtiva() {
		return dividaAtiva;
	}

	public void setDividaAtiva(boolean dividaAtiva) {
		this.dividaAtiva = dividaAtiva;
	}

	public String getModulo() {
		return modulo;
	}

	public void setModulo(String modulo) {
		this.modulo = modulo;
	}

	public Pessoa getCoordenador() {
		return coordenador;
	}

	public void setCoordenador(Pessoa coordenador) {
		this.coordenador = coordenador;
	}

	public Pessoa getAdjunto() {
		return adjunto;
	}

	public void setAdjunto(Pessoa adjunto) {
		this.adjunto = adjunto;
	}
	
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public boolean temCoordenador() {
		return (this.coordenador != null && this.coordenador.getId() != new Long(0)) ? true : false;
	}
	
	public boolean temAdjunto() {
		return (this.adjunto != null && this.adjunto.getId() != new Long(0)) ? true : false;
	}
	
	public boolean temDepartamentoPai() {
		return this.departamentoPai != null ? true : false;
	}
	
	public boolean ehComissao() {
		
		if ( this.codigo.equals(new Long(2301)) ) {
			
			return true;
			
		} else if ( temDepartamentoPai() && this.departamentoPai.getCodigo().equals(new Long(13)) ) {
			
			return true;
		}
		
		return false;
	}
	
	public boolean ehPlenario() {
		return this.codigo.equals(new Long(11));
	}
	
	public ModuloSistema getModuloDepartamento() {
		
		if(this.getModulo() == null) {
			
			return null;
			
		} else {
			
			switch (this.getModulo()) {
			case "SIACOL":
				return ModuloSistema.SIACOL;
			case "CADASTRO":
				return ModuloSistema.CADASTRO;
			case "CORPORATIVO":
				return ModuloSistema.CORPORATIVO;
			case "FINANCEIRO":
				return ModuloSistema.FINANCEIRO;
			case "ATENDIMENTO":
				return ModuloSistema.ATENDIMENTO;
			default:
				return null;
			}
		}
	}
}
