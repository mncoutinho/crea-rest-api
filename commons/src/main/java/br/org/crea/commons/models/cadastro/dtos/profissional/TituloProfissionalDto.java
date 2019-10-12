package br.org.crea.commons.models.cadastro.dtos.profissional;

import java.io.Serializable;
import java.util.List;

import br.org.crea.commons.models.commons.dtos.DomainGenericDto;

public class TituloProfissionalDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long codigoTituloCrea;
	
	private String tituloCrea;
	
	private Long codigoTituloConfea;
	
	private String tituloConfea;
	
	private List<String> atribuicoesTitulo;
	
	private List<String> dispositivosAtribuicaoTitulo;
	
	private String escolaDeObtencaoTitulo;
	
	private String estadoEscolaObtencaoTitulo;
	
	private String escolaridade;
	
	private String tipoTitulo;
	
	private String dataExpedicao;
	
	private String dataValidade;
	
	private String diploma;
	
	private String dataExpedicaoDiploma;
	
	private String dataFormatura;
	
	private String dataColacaoGrau;
	
	private String dataCancelamento;
	
	private String motivo;
	
	private String apostila;
	
	private String observacao;
	
	private String nivelFormacao;
	
	private Long idModalidade;
	
	private Long idTitulo;
	
	private boolean opcaoVoto;

	private DomainGenericDto modalidade;

	public Long getCodigoTituloCrea() {
		return codigoTituloCrea;
	}

	public void setCodigoTituloCrea(Long codigoTituloCrea) {
		this.codigoTituloCrea = codigoTituloCrea;
	}

	public String getTituloConfea() {
		return tituloConfea;
	}

	public void setTituloConfea(String tituloConfea) {
		this.tituloConfea = tituloConfea;
	}

	public Long getCodigoTituloConfea() {
		return codigoTituloConfea;
	}

	public void setCodigoTituloConfea(Long codigoTituloConfea) {
		this.codigoTituloConfea = codigoTituloConfea;
	}

	public String getTituloCrea() {
		return tituloCrea;
	}

	public void setTituloCrea(String tituloCrea) {
		this.tituloCrea = tituloCrea;
	}

	public List<String> getAtribuicoesTitulo() {
		return atribuicoesTitulo;
	}

	public void setAtribuicoesTitulo(List<String> atribuicoesTitulo) {
		this.atribuicoesTitulo = atribuicoesTitulo;
	}

	public List<String> getDispositivosAtribuicaoTitulo() {
		return dispositivosAtribuicaoTitulo;
	}

	public void setDispositivosAtribuicaoTitulo(
			List<String> dispositivosAtribuicaoTitulo) {
		this.dispositivosAtribuicaoTitulo = dispositivosAtribuicaoTitulo;
	}

	public String getEscolaDeObtencaoTitulo() {
		return escolaDeObtencaoTitulo;
	}

	public void setEscolaDeObtencaoTitulo(String escolaDeObtencaoTitulo) {
		this.escolaDeObtencaoTitulo = escolaDeObtencaoTitulo;
	}

	public String getEstadoEscolaObtencaoTitulo() {
		return estadoEscolaObtencaoTitulo;
	}

	public void setEstadoEscolaObtencaoTitulo(String estadoEscolaObtencaoTitulo) {
		this.estadoEscolaObtencaoTitulo = estadoEscolaObtencaoTitulo;
	}

	public String getEscolaridade() {
		return escolaridade;
	}

	public void setEscolaridade(String escolaridade) {
		this.escolaridade = escolaridade;
	}

	public String getTipoTitulo() {
		return tipoTitulo;
	}

	public void setTipoTitulo(String tipoTitulo) {
		this.tipoTitulo = tipoTitulo;
	}

	public String getDataExpedicao() {
		return dataExpedicao;
	}

	public void setDataExpedicao(String dataExpedicao) {
		this.dataExpedicao = dataExpedicao;
	}

	public String getDataValidade() {
		return dataValidade;
	}

	public void setDataValidade(String dataValidade) {
		this.dataValidade = dataValidade;
	}

	public String getDiploma() {
		return diploma;
	}

	public void setDiploma(String diploma) {
		this.diploma = diploma;
	}
	
	public String getDataExpedicaoDiploma() {
		return dataExpedicaoDiploma;
	}

	public void setDataExpedicaoDiploma(String dataExpedicaoDiploma) {
		this.dataExpedicaoDiploma = dataExpedicaoDiploma;
	}

	public String getDataFormatura() {
		return dataFormatura;
	}

	public void setDataFormatura(String dataFormatura) {
		this.dataFormatura = dataFormatura;
	}

	public String getDataColacaoGrau() {
		return dataColacaoGrau;
	}

	public void setDataColacaoGrau(String dataColacaoGrau) {
		this.dataColacaoGrau = dataColacaoGrau;
	}

	public String getDataCancelamento() {
		return dataCancelamento;
	}

	public void setDataCancelamento(String dataCancelamento) {
		this.dataCancelamento = dataCancelamento;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getApostila() {
		return apostila;
	}

	public void setApostila(String apostila) {
		this.apostila = apostila;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getNivelFormacao() {
		return nivelFormacao;
	}

	public void setNivelFormacao(String nivelFormacao) {
		this.nivelFormacao = nivelFormacao;
	}

	public DomainGenericDto getModalidade() {
		return modalidade;
	}

	public void setModalidade(DomainGenericDto modalidade) {
		this.modalidade = modalidade;
	}

	public Long getIdModalidade() {
		return idModalidade;
	}

	public void setIdModalidade(Long idModalidade) {
		this.idModalidade = idModalidade;
	}
	
	public Long getIdTitulo() {
		return idTitulo;
	}

	public void setIdTitulo(Long idTitulo) {
		this.idTitulo = idTitulo;
	}

	public boolean isOpcaoVoto() {
		return opcaoVoto;
	}

	public void setOpcaoVoto(boolean opcaoVoto) {
		this.opcaoVoto = opcaoVoto;
	}

}

