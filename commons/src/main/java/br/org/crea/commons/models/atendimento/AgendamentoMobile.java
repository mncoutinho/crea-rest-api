package br.org.crea.commons.models.atendimento;


import java.util.Date;
import java.util.UUID;

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
import javax.persistence.Transient;

import br.org.crea.commons.models.corporativo.Assunto;
import br.org.crea.commons.models.corporativo.pessoa.Funcionario;
import br.org.crea.commons.util.DateUtils;


@Entity
@Table(name = "MOB_AGENDAMENTO")
@SequenceGenerator(name = "sqAgendamento", sequenceName = "MOB_AGENDAMENTO_SEQ", initialValue = 1, allocationSize = 1)
public class AgendamentoMobile {
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sqAgendamento")
	private Long id;
	
	@Column(name="FK_PESSOA")
	private Long idPessoa;

	@OneToOne
	@JoinColumn(name="FK_ID_FUNCIONARIO")
	private Funcionario funcionario;
	
	@Column(name="DATA_ATUAL", insertable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataUpdate;
	
	@Column(name="DATA_AGENDAMENTO",  updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataAgendamento;
	
	@OneToOne
	@JoinColumn(name="FK_STATUS_AGENDAMENTO")
	private StatusAgendamentoMobile status;
	
	@OneToOne
	@JoinColumn(name="FK_ID_ASSUNTO_PROTOCOLO")
	private Assunto assunto;
	
	@Column(name="TURNO")
	private String turno;
	
	@Column(name="HORARIO_CHEGADA", insertable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date horarioChegada;
	
	@Column(name="HORARIO_INICIO", insertable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date horarioInicio;
	
	@Column(name="CPFCNPJ")
	private String cpfOuCnpj;
	
	@Column(name="EMAIL")
	private String email;

	@Column(name="NOME")
	private String nome;
	
	@Column(name="GUICHE")
	private Long guiche;
	
	@Column(name="TIPO_CADASTRO")
	private String tipoCadastro;
	
	@Column(name="FK_ID_UNIDADE_ATENDIMENTO")
	private Long idDepartamento;
	
	@Column(name="TELEFONE")
	private String telefone;
	
	@Column(name="SENHA")
	private String senha;
	
	@Column(name = "EXTRA")
	private boolean extra;
	
	@Transient
	private String dataFormatada;
	
	@Transient
	private String diaFormatado;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public Date getDataUpdate() {
		return dataUpdate;
	}

	public void setDataUpdate(Date dataUpdate) {
		this.dataUpdate = dataUpdate;
	}

	public Date getDataAgendamento() {
		return dataAgendamento;
	}

	public void setDataAgendamento(Date dataAgendamento) {
		this.dataAgendamento = dataAgendamento;
	}

	public Assunto getAssunto() {
		return assunto;
	}

	public void setAssunto(Assunto assunto) {
		this.assunto = assunto;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}

	public StatusAgendamentoMobile getStatus() {
		return status;
	}

	public void setStatus(StatusAgendamentoMobile status) {
		this.status = status;
	}

	public String getDataFormatada() {
		return DateUtils.format(this.getDataAgendamento(), DateUtils.DD_MM_YYYY_HH_MM);
	}

	public String getDiaFormatado() {
		return DateUtils.format(this.getDataAgendamento(), DateUtils.EEEE);
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	

	public Long getIdDepartamento() {
		return idDepartamento;
	}

	public void setIdDepartamento(Long idDepartamento) {
		this.idDepartamento = idDepartamento;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}

	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getGuiche() {
		return guiche;
	}

	public void setGuiche(Long guiche) {
		this.guiche = guiche;
	}

	public String getTipoCadastro() {
		return tipoCadastro;
	}

	public void setTipoCadastro(String tipoCadastro) {
		this.tipoCadastro = tipoCadastro;
	}
	
	public Date getHorarioChegada() {
		return horarioChegada;
	}

	public void setHorarioChegada(Date horarioChegada) {
		this.horarioChegada = horarioChegada;
	}

	public Date getHorarioInicio() {
		return horarioInicio;
	}

	public void setHorarioInicio(Date horarioInicio) {
		this.horarioInicio = horarioInicio;
	}
	
    public Long getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(Long idPessoa) {
		this.idPessoa = idPessoa;
	}

	public boolean temFuncionario(){
    	return this.funcionario != null ? true : false;
    }
    
    public boolean jaTemAlguemAgendado(){
    	return !this.status.getId().equals(new Long(0));
    }
    
    public boolean temPessoa(){
    	return this.idPessoa != null ? true : false;
    }
    
    public boolean temAssunto(){
    	return this.assunto != null ? true : false;
    }
    
    public boolean foiChamado(){
    	return !this.status.getId().equals(new Long(9)) ? false : true;
    }
    
	public void geraSenha() {
		 this.senha =  UUID.randomUUID().toString().substring(0, 5).replace("-", "").toUpperCase();
	}

	
	public boolean isExtra() {
		return extra;
	}

	public void setExtra(boolean extra) {
		this.extra = extra;
	}

	@Override
	public String toString() {
		return "AgendamentoMobile [idPessoa=" + idPessoa + ", dataAgendamento=" + dataAgendamento + ", status=" + status + ", assunto=" + assunto + ", turno=" + turno + ", cpfOuCnpj=" + cpfOuCnpj
				+ ", email=" + email + ", nome=" + nome + ", telefone=" + telefone + ", dataFormatada=" + dataFormatada + "]";
	}
	
	

	
}
