package br.org.crea.commons.models.portal;

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

import br.org.crea.commons.models.corporativo.pessoa.Funcionario;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;

@Entity
@Table(name="ATE_ATENDIMENTO")
@SequenceGenerator(name="ATENDIMENTO_SEQUENCE",sequenceName="ATE_ATENDIMENTO_SEQ",allocationSize = 1)
public class Atendimento {
	
	
	@Id
	@Column(name="CODIGO")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ATENDIMENTO_SEQUENCE")
	private Long codigo;
	
	@OneToOne
	@JoinColumn(name="FK_CODIGO_FUNCIONARIO")
	private Funcionario funcionario;
	
	@OneToOne
	@JoinColumn(name="FK_CODIGO_PESSOA")
	private Pessoa pessoaAtendida;
	
	@Column(name="NUMERO_CHAMADO")
	private Long numeroAtendimento;
	
	@Column(name="TEMPO_ESPERA")
	private int tempoEspera;
	
	@Column(name="CORDIALIDADE")
	private int cordialidade;
	
	@Column(name="CLAREZA")
	private int clareza;

	@Column(name="ORIENTACOES")
	private int orientacoes;
	
	@Column(name="FK_ID_DEPARTAMENTOS")
	private Long idDepartamento;
	
	@Column(name="SUGESTAO")
	private String sugestao;
	
	@Column(name="IP_MAQUINA")
	private String ipMaquina;
	
	@Column(name="INICIO_ATENDIMENTO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataInicioAtendimento;
	
	@Column(name="DATA_RESPOSTA")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataResposta;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Pessoa getPessoaAtendida() {
		return pessoaAtendida;
	}

	public void setPessoaAtendida(Pessoa pessoaAtendida) {
		this.pessoaAtendida = pessoaAtendida;
	}

	public Long getNumeroAtendimento() {
		return numeroAtendimento;
	}

	public void setNumeroAtendimento(Long numeroAtendimento) {
		this.numeroAtendimento = numeroAtendimento;
	}

	public int getTempoEspera() {
		return tempoEspera;
	}

	public void setTempoEspera(int tempoEspera) {
		this.tempoEspera = tempoEspera;
	}

	public int getCordialidade() {
		return cordialidade;
	}

	public void setCordialidade(int cordialidade) {
		this.cordialidade = cordialidade;
	}

	public int getClareza() {
		return clareza;
	}

	public void setClareza(int clareza) {
		this.clareza = clareza;
	}

	public int getOrientacoes() {
		return orientacoes;
	}

	public void setOrientacoes(int orientacoes) {
		this.orientacoes = orientacoes;
	}

	public String getSugestao() {
		return sugestao;
	}

	public void setSugestao(String sugestao) {
		this.sugestao = sugestao;
	}
	
	public Date getDataResposta() {
		return dataResposta;
	}

	public void setDataResposta(Date dataResposta) {
		this.dataResposta = dataResposta;
	}
	public Date getDataInicioAtendimento() {
		return dataInicioAtendimento;
	}

	public void setDataInicioAtendimento(Date dataInicioAtendimento) {
		this.dataInicioAtendimento = dataInicioAtendimento;
	}

	public String getIpMaquina() {
		return ipMaquina;
	}

	public void setIpMaquina(String ipMaquina) {
		this.ipMaquina = ipMaquina;
	}

	public Long getIdDepartamento() {
		return idDepartamento;
	}

	public void setIdDepartamento(Long idDepartamento) {
		this.idDepartamento = idDepartamento;
	}
	

}
