package br.org.crea.commons.models.cadastro.dtos.pessoa;

import java.util.Date;
import java.util.List;

import br.org.crea.commons.models.commons.dtos.DomainGenericDto;
import br.org.crea.commons.models.corporativo.enuns.TipoPessoa;

public class FormandoDto {
	
	private Long id;
	
	private Long __index;
	
	private String nome;
	
	private TipoPessoa tipo;
	
	private String cpf;
	
	private String registro;
	
	private DomainGenericDto instituicao;

	private DomainGenericDto curso;

	private DomainGenericDto campus;

	private DomainGenericDto uf;
	
	private String protocolo;

	private Date dataFormatura;
	
	private String dataFormaturaFormatada;
	
	private String precisaoDataFormatura;
	
	private DomainGenericDto status;
	
	private List<FormandoDto> formandos;
	
	private String dataFormaturaPlanilha;
	
	private List<FormandoDto> formandosProcessadoComSucesso;
	
	private List<FormandoDto> formandosProcessadoComErro;
	
	private Long idPessoa;
	
	private boolean protocoloGerado = false;
	
	private int page = 0;
	
	private int rows;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public TipoPessoa getTipo() {
		return tipo;
	}

	public void setTipo(TipoPessoa tipo) {
		this.tipo = tipo;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRegistro() {
		return registro;
	}

	public void setRegistro(String registro) {
		this.registro = registro;
	}

	public Long get__index() {
		return __index;
	}

	public void set__index(Long __index) {
		this.__index = __index;
	}

	public DomainGenericDto getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(DomainGenericDto instituicao) {
		this.instituicao = instituicao;
	}

	public DomainGenericDto getCurso() {
		return curso;
	}

	public void setCurso(DomainGenericDto curso) {
		this.curso = curso;
	}

	public DomainGenericDto getCampus() {
		return campus;
	}

	public void setCampus(DomainGenericDto campus) {
		this.campus = campus;
	}

	public DomainGenericDto getUf() {
		return uf;
	}

	public void setUf(DomainGenericDto uf) {
		this.uf = uf;
	}

	public String getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}

	public Date getDataFormatura() {
		return dataFormatura;
	}

	public void setDataFormatura(Date dataFormatura) {
		this.dataFormatura = dataFormatura;
	}

	public String getPrecisaoDataFormatura() {
		return precisaoDataFormatura;
	}

	public void setPrecisaoDataFormatura(String precisaoDataFormatura) {
		this.precisaoDataFormatura = precisaoDataFormatura;
	}

	public DomainGenericDto getStatus() {
		return status;
	}

	public void setStatus(DomainGenericDto status) {
		this.status = status;
	}

	public List<FormandoDto> getFormandos() {
		return formandos;
	}

	public String getDataFormaturaPlanilha() {
		return dataFormaturaPlanilha;
	}

	public void setDataFormaturaPlanilha(String dataFormaturaPlanilha) {
		this.dataFormaturaPlanilha = dataFormaturaPlanilha;
	}

	public void setFormandos(List<FormandoDto> formandos) {
		this.formandos = formandos;
	}
	
	public boolean temDataFormaturaPlanilha() {
		return this.dataFormaturaPlanilha != null;
	}

	public List<FormandoDto> getFormandosProcessadoComSucesso() {
		return formandosProcessadoComSucesso;
	}

	public void setFormandosProcessadoComSucesso(List<FormandoDto> formandosProcessadoComSucesso) {
		this.formandosProcessadoComSucesso = formandosProcessadoComSucesso;
	}

	public List<FormandoDto> getFormandosProcessadoComErro() {
		return formandosProcessadoComErro;
	}

	public void setFormandosProcessadoComErro(List<FormandoDto> formandosProcessadoComErro) {
		this.formandosProcessadoComErro = formandosProcessadoComErro;
	}
	
	public boolean temFormandosProcessadoComSucesso() {
		if(this.formandosProcessadoComSucesso != null) {
			return this.formandosProcessadoComSucesso.size() > 0;
		}
		return false;
	}

	public String getDataFormaturaFormatada() {
		return dataFormaturaFormatada;
	}

	public void setDataFormaturaFormatada(String dataFormaturaFormatada) {
		this.dataFormaturaFormatada = dataFormaturaFormatada;
	}
	
	public boolean temFormandosProcessadoComErro() {
		if(this.formandosProcessadoComErro != null) {
			return this.formandosProcessadoComErro.size() > 0;
		}
		return false;
	}

	public Long getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(Long idPessoa) {
		this.idPessoa = idPessoa;
	}

	public boolean isProtocoloGerado() {
		return protocoloGerado;
	}

	public void setProtocoloGerado(boolean protocoloGerado) {
		this.protocoloGerado = protocoloGerado;
	}
	
	public boolean temCursoSelecionado() {
		return this.curso != null;
	}
	
	public boolean temCampiSelecionado() {
		return this.campus != null;
	}
	
	public boolean temDataFormatura() {
		return this.dataFormatura != null;
	}
	
	public boolean temProtocolo() {
		if(this.protocolo != null) {
			if(this.protocolo.trim().length() > 0) {
				return true;
			}
		}
		return false;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}
	
	public boolean ehPrimeiraConsulta() {
		return this.page == 1;
	}
}
