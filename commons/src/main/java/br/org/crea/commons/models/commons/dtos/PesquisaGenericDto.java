package br.org.crea.commons.models.commons.dtos;

import java.util.Date;
import java.util.List;

import br.org.crea.commons.models.art.dtos.ArtDto;


public class PesquisaGenericDto {
	
	private Long id;
	
	private String mesAno;
	
	private String numeroProtocolo;
	
	private Long unidadeAtendimento;
	
	private Long ano;
	
	private Date data;
	
	private Date dataAtual;

	private Date dataInicio;

	private Date dataFim;
	
	private Long situacao;
	
	private boolean hoje;
	
	private boolean livre;
	
	private DomainGenericDto tipoDemanda;
	
	private Long assuntoGeral;
	
	private Long assuntoEspecifico;
	
	private Long status;
	
	private Long idFuncionario;
	
	private Long idPerfil;
	
	private String descricaoPerfil;
	
	private String statusAtendimento;
	
	private String registro;
	
	private String cnpj;
	
	private String cpf;
	
	private String protocolo;
	
	private String processo;
	
	private String tipoPessoa;

	private String nomePessoa;

	private String razaoSocial;
	
	private String tipo;
	
	private String numeroRNP;
	
	private String anuidade;
	
	private Long idPessoa;
	
	private Long idEmpresa;
	
	private Long idDocumento;
	
	private Long virtual;

	private String numeroArt;
	
	private ArtDto art;
	
	private int page = 1;
	
	private int rows;

	private Long idDepartamento;
	
	private List<String> idsPerfil;

	private Long registroProfissional;
	
	private String statusDescritivo;
	
	private Boolean checked;
	
	private Long codigoAtendimento;
	
	private String email;

	public Long getCodigoAtendimento() {
		return codigoAtendimento;
	}

	public void setCodigoAtendimento(Long codigoAtendimento) {
		this.codigoAtendimento = codigoAtendimento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setAssuntoEspecifico(Long assuntoEspecifico) {
		this.assuntoEspecifico = assuntoEspecifico;
	}

	private Boolean segundoChecked;


	public String getMesAno() {
		return mesAno;
	}

	public void setMesAno(String mesAno) {
		this.mesAno = mesAno;
	}

	public Long getUnidadeAtendimento() {
		return unidadeAtendimento;
	}

	public void setUnidadeAtendimento(Long unidadeAtendimento) {
		this.unidadeAtendimento = unidadeAtendimento;
	}
	
	public Long getAno() {
		return ano;
	}

	public void setAno(Long ano) {
		this.ano = ano;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Date getDataAtual() {
		return dataAtual;
	}

	public void setDataAtual(Date dataAtual) {
		this.dataAtual = dataAtual;
	}

	public Long getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(Long idFuncionario) {
		this.idFuncionario = idFuncionario;
	}

	public Long getIdPerfil() {
		return idPerfil;
	}

	public void setIdPerfil(Long idPerfil) {
		this.idPerfil = idPerfil;
	}

	public String getStatusAtendimento() {
		return statusAtendimento;
	}

	public void setStatusAtendimento(String statusAtendimento) {
		this.statusAtendimento = statusAtendimento;
	}

	public String getRegistro() {
		return registro;
	}

	public void setRegistro(String registro) {
		this.registro = registro;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}

	public String getProcesso() {
		return processo;
	}

	public void setProcesso(String processo) {
		this.processo = processo;
	}

	public String getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(String tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public String getNomePessoa() {
		return nomePessoa;
	}

	public void setNomePessoa(String nomePessoa) {
		this.nomePessoa = nomePessoa;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNumeroRNP() {
		return numeroRNP;
	}

	public void setNumeroRNP(String numeroRNP) {
		this.numeroRNP = numeroRNP;
	}

	public String getAnuidade() {
		return anuidade;
	}

	public void setAnuidade(String anuidade) {
		this.anuidade = anuidade;
	}

	public Long getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(Long idPessoa) {
		this.idPessoa = idPessoa;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public Long getIdDepartamento() {
		return idDepartamento;
	}

	public void setIdDepartamento(Long idDepartamento) {
		this.idDepartamento = idDepartamento;
	}

	public String getNumeroArt() {
		return numeroArt;
	}

	public void setNumeroArt(String numeroArt) {
		this.numeroArt = numeroArt;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public ArtDto getArt() {
		return art;
	}

	public void setArt(ArtDto art) {
		this.art = art;
	}

	public boolean ehPrimeiraConsulta() {
		return this.page == 1;
	}

	public List<String> getIdsPerfil() {
		return idsPerfil;
	}

	public void setIdsPerfil(List<String> idsPerfil) {
		this.idsPerfil = idsPerfil;
	}

	public Long getRegistroProfissional() {
		return registroProfissional;
	}

	public void setRegistroProfissional(Long registroProfissional) {
		this.registroProfissional = registroProfissional;
	}

	public String getDescricaoPerfil() {
		return descricaoPerfil;
	}

	public void setDescricaoPerfil(String descricaoPerfil) {
		this.descricaoPerfil = descricaoPerfil;
	}

	public boolean isHoje() {
		return hoje;
	}

	public void setHoje(boolean hoje) {
		this.hoje = hoje;
	}

	public boolean isLivre() {
		return livre;
	}

	public void setLivre(boolean livre) {
		this.livre = livre;
	}

	public String getStatusDescritivo() {
		return statusDescritivo;
	}

	public void setStatusDescritivo(String statusDescritivo) {
		this.statusDescritivo = statusDescritivo;
	}

	public Long getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(Long idDocumento) {
		this.idDocumento = idDocumento;
	}
	
	public Long getVirtual() {
		return virtual;
	}

	public void setVirtual(Long virtual) {
		this.virtual = virtual;
	}

	public Long getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public boolean temNomePessoa() {
		return this.getNomePessoa() != null;
	}

	public boolean temIdPessoa() {
		return getIdPessoa() != null;
	}
	public boolean temProcesso() {
		if (getProcesso() != null) {
			return !this.processo.equals("");
		}
		return false;
	}
	public boolean temNumeroProtocolo() {
		if (getNumeroProtocolo() != null) {
			return !this.numeroProtocolo.equals("");
		}
		return false;
	}

	public String getNumeroProtocolo() {
		return numeroProtocolo;
	}

	public void setNumeroProtocolo(String numeroProtocolo) {
		this.numeroProtocolo = numeroProtocolo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	
	public boolean temDataInicioEFim() {
		return this.dataInicio != null && this.dataFim != null;
	}

	public boolean temAno() {
		return this.ano != null ? true : false;
	}

	public Boolean isChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Long getSituacao() {
		return situacao;
	}

	public void setSituacao(Long situacao) {
		this.situacao = situacao;
	}

	public DomainGenericDto getTipoDemanda() {
		return tipoDemanda;
	}

	public void setTipoDemanda(DomainGenericDto tipoDemanda) {
		this.tipoDemanda = tipoDemanda;
	}

	public Long getAssuntoGeral() {
		return assuntoGeral;
	}

	public void setAssuntoGeral(Long assuntoGeral) {
		this.assuntoGeral = assuntoGeral;
	}

	public Long getAssuntoEspecifico() {
		return assuntoEspecifico;
	}

	public void setAsuntoEspecifico(Long asuntoEspecifico) {
		this.assuntoEspecifico = asuntoEspecifico;
	}

	public Boolean isSegundoChecked() {
		return segundoChecked;
	}

	public void setSegundoChecked(Boolean segundoChecked) {
		this.segundoChecked = segundoChecked;
	}	
	
	
}
