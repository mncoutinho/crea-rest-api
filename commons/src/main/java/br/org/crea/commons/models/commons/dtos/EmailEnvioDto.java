package br.org.crea.commons.models.commons.dtos;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import br.org.crea.commons.models.commons.DestinatarioEmailDto;

public class EmailEnvioDto implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2194701131579043704L;

	private Long id;
	
	private String assunto;
	
	private String emissor;
	
	private List<DestinatarioEmailDto> destinatarios;
	
	private List<DestinatarioEmailDto> destinatariosCC;
	
	private List<DestinatarioEmailDto> destinatariosCCO;
	
	private String mensagem;
	
	private Date dataUltimoEnvio;
	
	private String status;
	
	private DomainGenericDto evento;
	
	public List<ArquivoFormUploadDto> anexos;
	
	private List<Long> idsCadArquivo;
	
	private List<Long> idsDocFlow;
	
	private Long idPessoa;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getEmissor() {
		return emissor;
	}

	public void setEmissor(String emissor) {
		this.emissor = emissor;
	}

	public List<DestinatarioEmailDto> getDestinatarios() {
		return destinatarios;
	}

	public void setDestinatarios(List<DestinatarioEmailDto> destinatarios) {
		this.destinatarios = destinatarios;
	}

	public List<DestinatarioEmailDto> getDestinatariosCC() {
		return destinatariosCC;
	}

	public void setDestinatariosCC(List<DestinatarioEmailDto> destinatariosCC) {
		this.destinatariosCC = destinatariosCC;
	}

	public List<DestinatarioEmailDto> getDestinatariosCCO() {
		return destinatariosCCO;
	}

	public void setDestinatariosCCO(List<DestinatarioEmailDto> destinatariosCCO) {
		this.destinatariosCCO = destinatariosCCO;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public Date getDataUltimoEnvio() {
		return dataUltimoEnvio;
	}

	public void setDataUltimoEnvio(Date dataUltimoEnvio) {
		this.dataUltimoEnvio = dataUltimoEnvio;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public DomainGenericDto getEvento() {
		return evento;
	}

	public void setEvento(DomainGenericDto evento) {
		this.evento = evento;
	}

	public List<ArquivoFormUploadDto> getAnexos() {
		return anexos;
	}

	public void setAnexos(List<ArquivoFormUploadDto> anexos) {
		this.anexos = anexos;
	}
	
	public boolean temAnexos () {
		return this.anexos != null;
	}

	public List<Long> getIdsCadArquivo() {
		return idsCadArquivo;
	}

	public void setIdsCadArquivo(List<Long> idsCadArquivo) {
		this.idsCadArquivo = idsCadArquivo;
	}

	public List<Long> getIdsDocFlow() {
		return idsDocFlow;
	}

	public void setIdsDocFlow(List<Long> idsDocFlow) {
		this.idsDocFlow = idsDocFlow;
	}
	
	public boolean temIdsArquivos() {
		return this.idsCadArquivo != null;
	}
	
	public boolean temIdsDocFlow() {
		return this.idsDocFlow != null;
	}

	public Long getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(Long idPessoa) {
		this.idPessoa = idPessoa;
	}
}


