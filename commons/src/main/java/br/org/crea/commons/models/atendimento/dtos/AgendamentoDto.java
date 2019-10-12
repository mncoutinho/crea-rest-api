package br.org.crea.commons.models.atendimento.dtos;

import java.util.Date;
import java.util.UUID;

import br.org.crea.commons.models.commons.dtos.StatusDto;
import br.org.crea.commons.models.corporativo.dtos.AssuntoDto;
import br.org.crea.commons.models.corporativo.dtos.PessoaDto;

public class AgendamentoDto {
	
	private Long id;
	
	private Date dataAgendamento;
	
	private AssuntoDto assunto;
	
	private Long idAssunto;
	
	private StatusDto status;
	
	private PessoaDto pessoa;

	private PessoaDto funcionario;
	
	private String dataFormatada;
	
	private Long idFuncionario;
	
	private String horaFormatada;
	
	private String diaFormatado;
	
	private String turno;
	
	private boolean semRegistro;
	
	private Long idUnidadeAtendimento;
	
	private String localAtendimento;
	
	private String siglaLocalAtendimento;
	
	private String cpfOuCnpj;
	
	private String nome;
	
	private String email;
	
	private String meioAgendamento;
	
	private String novaData;
	
	private Long guiche;
	
	private String horaChegada;
	
	private String horaInicio;
	
	private boolean checked;
	
	private String senha;
	
	private String telefone;
	 
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataAgendamento() {
		return dataAgendamento;
	}

	public void setDataAgendamento(Date dataAgendamento) {
		this.dataAgendamento = dataAgendamento;
	}

	public AssuntoDto getAssunto() {
		return assunto;
	}

	public void setAssunto(AssuntoDto assunto) {
		this.assunto = assunto;
	}

	public Long getIdAssunto() {
		return idAssunto;
	}

	public void setIdAssunto(Long idAssunto) {
		this.idAssunto = idAssunto;
	}

	public StatusDto getStatus() {
		return status;
	}

	public void setStatus(StatusDto status) {
		this.status = status;
	}

	public PessoaDto getPessoa() {
		return pessoa;
	}

	public void setPessoa(PessoaDto pessoa) {
		this.pessoa = pessoa;
	}

	public String getDataFormatada() {
		return dataFormatada;
	}

	public void setDataFormatada(String dataFormatada) {
		this.dataFormatada = dataFormatada;
	}

	public Long getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(Long idFuncionario) {
		this.idFuncionario = idFuncionario;
	}

	public String getHoraFormatada() {
		return horaFormatada;
	}

	public void setHoraFormatada(String horaFormatada) {
		this.horaFormatada = horaFormatada;
	}

	public String getDiaFormatado() {
		return diaFormatado;
	}

	public void setDiaFormatado(String diaFormatado) {
		this.diaFormatado = diaFormatado;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}

	public boolean isSemRegistro() {
		return semRegistro;
	}

	public void setSemRegistro(boolean semRegistro) {
		this.semRegistro = semRegistro;
	}

	public Long getIdUnidadeAtendimento() {
		return idUnidadeAtendimento;
	}

	public void setIdUnidadeAtendimento(Long idUnidadeAtendimento) {
		this.idUnidadeAtendimento = idUnidadeAtendimento;
	}

	public String getCpfOuCnpj() {
		
		if(temPessoa()){
		    if(this.pessoa.isPessoaFisica()){
		    	return this.pessoa.getCpf();
		    }else{
		    	return this.pessoa.getCnpj();
		    }	
		}else{
			return cpfOuCnpj;
		}
	}

	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}
	
	

	public String getLocalAtendimento() {
		return localAtendimento;
	}

	public void setLocalAtendimento(String localAtendimento) {
		this.localAtendimento = localAtendimento;
	}
	
	public String getSiglaLocalAtendimento() {
		return siglaLocalAtendimento;
	}

	public void setSiglaLocalAtendimento(String siglaLocalAtendimento) {
		this.siglaLocalAtendimento = siglaLocalAtendimento;
	}

	public String getNome() {
		
		return this.pessoa != null ? this.pessoa.getNome() : nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		if(temPessoa()){
			return this.pessoa.getEmail();
		}else{
			return email;
		}
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMeioAgendamento() {
		return meioAgendamento;
	}

	public void setMeioAgendamento(String meioAgendamento) {
		this.meioAgendamento = meioAgendamento;
	}

	public String getNovaData() {
		return novaData;
	}

	public void setNovaData(String novaData) {
		this.novaData = novaData;
	}

	public Long getGuiche() {
		return guiche;
	}

	public void setGuiche(Long guiche) {
		this.guiche = guiche;
	}

	public String getHoraChegada() {
		return horaChegada;
	}

	public void setHoraChegada(String horaChegada) {
		this.horaChegada = horaChegada;
	}

	public String getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public boolean temAssunto(){
		return this.assunto != null ? true : false;
	}

	public boolean temPessoa(){
		return this.pessoa != null ? true : false;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public PessoaDto getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(PessoaDto funcionario) {
		this.funcionario = funcionario;
	}
	
	public void geraSenha() {
		 this.senha =  UUID.randomUUID().toString().substring(0, 5).replace("-", "").toUpperCase();
	}

	public boolean temEmailAgendado() {
		return this.email != null ? !this.email.equals("") : false;
	}
	
	
}
