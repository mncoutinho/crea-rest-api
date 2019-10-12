package br.org.crea.commons.models.commons.dtos;

import java.util.Date;
import java.util.List;

public class GenericDto {

	private String id;
	
	private List<Long> listIds;
	
	private Long idRegistro;

	private Long idFuncionario;

	private String idPerfil;

	private String descricaoPerfil;
	
	private Long idReuniao;

	private Long idDepartamento;

	private Long idDepartamentoPai;
	
	private Long idTipo;
	
	private Long idConfiguracao;
	
	private String codigo;
	
	private Long idCodigo;
	
	private Long idDocumento;
	
	private Long idPauta;
	
	private Long idModalidade;
	
	private Long codigoUnidade;

	private String nome;
	
	private String tipo;

	private String descricao;

	private String sigla;

	private Boolean siacol;
	
	private Date data;

	private Date datainicio;

	private Date datafim;
	
	private Date dataInicio;

	private Date dataFim;
	
	private String dataFormatada;

	private String dataInicioFormatada;

	private String dataFimFormatada;

	private Boolean ativo;

	private String registro;

	private String numeroArt;
	
	private Long numeroProtocolo;
	
	private Long numeroDocumento;
	
	private String numeroDocumentoDescritivo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Long> getListIds() {
		return listIds;
	}

	public void setListIds(List<Long> listIds) {
		this.listIds = listIds;
	}

	public Long getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(Long idFuncionario) {
		this.idFuncionario = idFuncionario;
	}

	public String getIdPerfil() {
		return idPerfil;
	}

	public void setIdPerfil(String idPerfil) {
		this.idPerfil = idPerfil;
	}

	public Long getIdReuniao() {
		return idReuniao;
	}

	public void setIdReuniao(Long idReuniao) {
		this.idReuniao = idReuniao;
	}

	public Long getIdDepartamento() {
		return idDepartamento;
	}

	public void setIdDepartamento(Long idDepartamento) {
		this.idDepartamento = idDepartamento;
	}

	public Long getIdDepartamentoPai() {
		return idDepartamentoPai;
	}

	public void setIdDepartamentoPai(Long idDepartamentoPai) {
		this.idDepartamentoPai = idDepartamentoPai;
	}

	public Long getIdTipo() {
		return idTipo;
	}

	public Long getIdConfiguracao() {
		return idConfiguracao;
	}

	public void setIdConfiguracao(Long idConfiguracao) {
		this.idConfiguracao = idConfiguracao;
	}

	public void setIdTipo(Long idTipo) {
		this.idTipo = idTipo;
	}

	public String getCodigo() {
		return codigo;
	}

	public Long getIdCodigo() {
		return idCodigo;
	}

	public void setIdCodigo(Long idCodigo) {
		this.idCodigo = idCodigo;
	}

	public Long getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(Long idDocumento) {
		this.idDocumento = idDocumento;
	}

	public Long getIdPauta() {
		return idPauta;
	}

	public void setIdPauta(Long idPauta) {
		this.idPauta = idPauta;
	}

	public Long getIdModalidade() {
		return idModalidade;
	}

	public void setIdModalidade(Long idModalidade) {
		this.idModalidade = idModalidade;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Long getCodigoUnidade() {
		return codigoUnidade;
	}

	public void setCodigoUnidade(Long codigoUnidade) {
		this.codigoUnidade = codigoUnidade;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Boolean getSiacol() {
		return siacol;
	}

	public void setSiacol(Boolean siacol) {
		this.siacol = siacol;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Date getDatainicio() {
		return datainicio;
	}

	public void setDatainicio(Date datainicio) {
		this.datainicio = datainicio;
	}

	public Date getDatafim() {
		return datafim;
	}

	public void setDatafim(Date datafim) {
		this.datafim = datafim;
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

	public String getDataFormatada() {
		return dataFormatada;
	}

	public void setDataFormatada(String dataFormatada) {
		this.dataFormatada = dataFormatada;
	}

	public String getDataInicioFormatada() {
		return dataInicioFormatada;
	}

	public void setDataInicioFormatada(String dataInicioFormatada) {
		this.dataInicioFormatada = dataInicioFormatada;
	}

	public String getDataFimFormatada() {
		return dataFimFormatada;
	}

	public void setDataFimFormatada(String dataFimFormatada) {
		this.dataFimFormatada = dataFimFormatada;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public String getRegistro() {
		return registro;
	}

	public void setRegistro(String registro) {
		this.registro = registro;
	}

	public String getNumeroArt() {
		return numeroArt;
	}

	public void setNumeroArt(String numeroArt) {
		this.numeroArt = numeroArt;
	}
	
	public String getDescricaoPerfil() {
		return descricaoPerfil;
	}

	public void setDescricaoPerfil(String descricaoPerfil) {
		this.descricaoPerfil = descricaoPerfil;
	}

	public Long getIdRegistro() {
		return idRegistro;
	}

	public void setIdRegistro(Long idRegistro) {
		this.idRegistro = idRegistro;
	}
	
	public boolean heSiacol() {
		return this.siacol == true ? true : false;
	}

	public Long getNumeroProtocolo() {
		return numeroProtocolo;
	}

	public void setNumeroProtocolo(Long numeroProtocolo) {
		this.numeroProtocolo = numeroProtocolo;
	}

	public Long getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(Long numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getNumeroDocumentoDescritivo() {
		return numeroDocumentoDescritivo;
	}

	public void setNumeroDocumentoDescritivo(String numeroDocumentoDescritivo) {
		this.numeroDocumentoDescritivo = numeroDocumentoDescritivo;
	}
	
	

}