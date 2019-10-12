package br.org.crea.commons.models.cadastro.dtos.premio;

import java.util.Date;
import java.util.List;

import br.org.crea.commons.models.cadastro.dtos.ParticipantePremioTCTDto;
import br.org.crea.commons.models.commons.dtos.ArquivoDto;
import br.org.crea.commons.models.commons.dtos.DomainGenericDto;

public class PremioTCTDto {

	private Long id;
	
	private DomainGenericDto instituicao;
	
	private DomainGenericDto curso;
	
	private DomainGenericDto campus;
	
	private Long ano;
	
	private String titulo;
	
	private Long status;
	
	private Long idPessoa;
	
	private String cpfPessoa;
	
	private String nomePessoa;
	
	private Boolean aceite;
	
	private Date dataEnvio;
	
	private String dataEnvioFormatada;
	
	private ArquivoDto arquivo;

	private ArquivoDto arquivoResumo;
	
	private ArquivoDto arquivoTermo;
	
	List<ParticipantePremioTCTDto> listaParticipantes;
	
	private String protocoloCurso;
	
	private String descricaoCurso;
	
	private String nivel;
	
	private String tipoRelatorio;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getAno() {
		return ano;
	}

	public void setAno(Long ano) {
		this.ano = ano;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Long getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(Long idPessoa) {
		this.idPessoa = idPessoa;
	}

	public String getNomePessoa() {
		return nomePessoa;
	}

	public void setNomePessoa(String nomePessoa) {
		this.nomePessoa = nomePessoa;
	}

	public Boolean getAceite() {
		return aceite;
	}

	public void setAceite(Boolean aceite) {
		this.aceite = aceite;
	}

	public Date getDataEnvio() {
		return dataEnvio;
	}

	public void setDataEnvio(Date dataEnvio) {
		this.dataEnvio = dataEnvio;
	}

	public ArquivoDto getArquivo() {
		return arquivo;
	}

	public void setArquivo(ArquivoDto arquivo) {
		this.arquivo = arquivo;
	}

	public List<ParticipantePremioTCTDto> getListaParticipantes() {
		return listaParticipantes;
	}

	public void setListaParticipantes(List<ParticipantePremioTCTDto> listaParticipantes) {
		this.listaParticipantes = listaParticipantes;
	}

	public DomainGenericDto getCampus() {
		return campus;
	}

	public void setCampus(DomainGenericDto campus) {
		this.campus = campus;
	}

	public String getProtocoloCurso() {
		return protocoloCurso;
	}

	public void setProtocoloCurso(String protocoloCurso) {
		this.protocoloCurso = protocoloCurso;
	}

	public String getDescricaoCurso() {
		return descricaoCurso;
	}

	public void setDescricaoCurso(String descricaoCurso) {
		this.descricaoCurso = descricaoCurso;
	}

	public boolean temPessoa() {
		return this.idPessoa != null;
	}
	
	public boolean temCurso() {
		return this.curso != null;
	}
	
	public boolean temCampus() {
		return this.campus != null;
	}
	
	public boolean temInstituicao() {
		return this.instituicao != null;
	}

	public boolean temAno() {
		return this.ano != null;
	}

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

	public String getDataEnvioFormatada() {
		return dataEnvioFormatada;
	}

	public void setDataEnvioFormatada(String dataEnvioFormatada) {
		this.dataEnvioFormatada = dataEnvioFormatada;
	}

	public String getTipoRelatorio() {
		return tipoRelatorio;
	}

	public void setTipoRelatorio(String tipoRelatorio) {
		this.tipoRelatorio = tipoRelatorio;
	}

	public boolean foiFinalizado() {
		if (this.status != null) {
			return this.status.equals(1L);
		}
		return false;
	}

	public String getCpfPessoa() {
		return cpfPessoa;
	}

	public void setCpfPessoa(String cpf) {
		this.cpfPessoa = cpf;
	}

	public ArquivoDto getArquivoResumo() {
		return arquivoResumo;
	}

	public void setArquivoResumo(ArquivoDto arquivoResumo) {
		this.arquivoResumo = arquivoResumo;
	}

	public ArquivoDto getArquivoTermo() {
		return arquivoTermo;
	}

	public void setArquivoTermo(ArquivoDto arquivoTermo) {
		this.arquivoTermo = arquivoTermo;
	}
}