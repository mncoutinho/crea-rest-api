package br.org.crea.commons.models.cadastro.dtos;

import java.io.InputStream;

import br.org.crea.commons.models.art.dtos.ArtDto;
import br.org.crea.commons.models.corporativo.dtos.FuncionarioDto;
import br.org.crea.commons.report.TemplateReportEnum;
import br.org.crea.commons.validsigner.dto.ValidSignDto;


public class DocumentoDto {

	private String assinado;
	private String tipoDocumento;
	private String numeroDocumento;
	private String numeroDocumentoExterno;
	private String numeroProcesso;
	private String numeroProtocolo;
	private String numeroMemorando;
	private String quorum;
	private String texto;
	private String dataAssinatura;
	private String dataDocumento;
	private String dataDocumentoFormatada;
	private String enderecoDescritivo;
	private String assunto;
	private String referencia;
	private String textoPrincipal;
	private String textoVoto;
	private String textoAuxiliar;
	private String protocolosDeferido;
	private String assinatura;
	private InputStream inputAssinatura;
	private TemplateReportEnum template;
	private String departamentoOrigem;
	private String departamentoDestino;
	private String interessado;
	private String oficiado;
	private String local;
	private String origem;
	private String ementa;
	private String nomeResponsavel;
	private String nomeOrigem;
	private String nomeDestinatario;
	private String cargo;
	private String funcao;
	private String matricula;
	private String outros;
	private String plenario;
	private String diretoria;
	private String camaraEspecializada;
	private String comissaoPermanente;
	private String comissaoEspecial;
	private String outrosOrgaoDeOrigem;
	private String itemDaPauta;
	private String relator;
	private String tratamento;
	private String emailOficiado;
	private String textoEmailOficiado;
	private String comunicados;
	private String extratoDePauta;
	private String cabecalhoPauta;
	private String correspondenciasRecebidas;
	private String assuntoEmPauta;
	private String assuntoRelatados;
	private String assuntoExtraPauxa;
	private String setor;
	private String classificacao;
	private ArtDto art;
	private FuncionarioDto funcionarioDto;
	private ValidSignDto validSigner;

	
	public String getAssinado() {
		return assinado;
	}
	public void setAssinado(String assinado) {
		this.assinado = assinado;
	}
	public String getClassificacao() {
		return classificacao;
	}
	public void setClassificacao(String classificacao) {
		this.classificacao = classificacao;
	}
	public String getSetor() {
		return setor;
	}
	public void setSetor(String setor) {
		this.setor = setor;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public String getNumeroDocumento() {
		return numeroDocumento;
	}
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	public String getNumeroDocumentoExterno() {
		return numeroDocumentoExterno;
	}
	public void setNumeroDocumentoExterno(String numeroDocumentoExterno) {
		this.numeroDocumentoExterno = numeroDocumentoExterno;
	}
	public String getNumeroProcesso() {
		return numeroProcesso;
	}
	public void setNumeroProcesso(String numeroProcesso) {
		this.numeroProcesso = numeroProcesso;
	}
	public String getNumeroProtocolo() {
		return numeroProtocolo;
	}
	public void setNumeroProtocolo(String numeroProtocolo) {
		this.numeroProtocolo = numeroProtocolo;
	}
	public String getNumeroMemorando() {
		return numeroMemorando;
	}
	public void setNumeroMemorando(String numeroMemorando) {
		this.numeroMemorando = numeroMemorando;
	}
	public String getQuorum() {
		return quorum;
	}
	public void setQuorum(String quorum) {
		this.quorum = quorum;
	}
	public String getDataAssinatura() {
		return dataAssinatura;
	}
	public void setDataAssinatura(String dataAssinatura) {
		this.dataAssinatura = dataAssinatura;
	}
	public String getDataDocumento() {
		return dataDocumento;
	}
	public void setDataDocumento(String dataDocumento) {
		this.dataDocumento = dataDocumento;
	}
	public String getDataDocumentoFormatada() {
		return dataDocumentoFormatada;
	}
	public void setDataDocumentoFormatada(String dataDocumentoFormatada) {
		this.dataDocumentoFormatada = dataDocumentoFormatada;
	}
	public String getEnderecoDescritivo() {
		return enderecoDescritivo;
	}
	public void setEnderecoDescritivo(String enderecoDescritivo) {
		this.enderecoDescritivo = enderecoDescritivo;
	}
	public String getAssunto() {
		return assunto;
	}
	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}
	public String getReferencia() {
		return referencia;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	public String getTextoPrincipal() {
		return textoPrincipal;
	}
	public void setTextoPrincipal(String textoPrincipal) {
		this.textoPrincipal = textoPrincipal;
	}
	public String getTextoVoto() {
		return textoVoto;
	}
	public void setTextoVoto(String textoVoto) {
		this.textoVoto = textoVoto;
	}
	public String getTextoAuxiliar() {
		return textoAuxiliar;
	}
	public void setTextoAuxiliar(String textoAuxiliar) {
		this.textoAuxiliar = textoAuxiliar;
	}
	public String getProtocolosDeferido() {
		return protocolosDeferido;
	}
	public void setProtocolosDeferido(String protocolosDeferido) {
		this.protocolosDeferido = protocolosDeferido;
	}
	public String getAssinatura() {
		return assinatura;
	}
	public void setAssinatura(String assinatura) {
		this.assinatura = assinatura;
	}
	public InputStream getInputAssinatura() {
		return inputAssinatura;
	}
	public void setInputAssinatura(InputStream inputAssinatura) {
		this.inputAssinatura = inputAssinatura;
	}
	public TemplateReportEnum getTemplate() {
		return template;
	}
	public void setTemplate(TemplateReportEnum template) {
		this.template = template;
	}
	public String getDepartamentoOrigem() {
		return departamentoOrigem;
	}
	public void setDepartamentoOrigem(String departamentoOrigem) {
		this.departamentoOrigem = departamentoOrigem;
	}
	public String getDepartamentoDestino() {
		return departamentoDestino;
	}
	public void setDepartamentoDestino(String departamentoDestino) {
		this.departamentoDestino = departamentoDestino;
	}
	public String getInteressado() {
		return interessado;
	}
	public void setInteressado(String interessado) {
		this.interessado = interessado;
	}
	public String getOficiado() {
		return oficiado;
	}
	public void setOficiado(String oficiado) {
		this.oficiado = oficiado;
	}
	public String getLocal() {
		return local;
	}
	public void setLocal(String local) {
		this.local = local;
	}
	public String getOrigem() {
		return origem;
	}
	public void setOrigem(String origem) {
		this.origem = origem;
	}
	public String getEmenta() {
		return ementa;
	}
	public void setEmenta(String ementa) {
		this.ementa = ementa;
	}
	public String getNomeResponsavel() {
		return nomeResponsavel;
	}
	public void setNomeResponsavel(String nomeResponsavel) {
		this.nomeResponsavel = nomeResponsavel;
	}
	public String getNomeOrigem() {
		return nomeOrigem;
	}
	public void setNomeOrigem(String nomeOrigem) {
		this.nomeOrigem = nomeOrigem;
	}
	public String getNomeDestinatario() {
		return nomeDestinatario;
	}
	public void setNomeDestinatario(String nomeDestinatario) {
		this.nomeDestinatario = nomeDestinatario;
	}
	public String getCargo() {
		return cargo;
	}
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	public String getFuncao() {
		return funcao;
	}
	public void setFuncao(String funcao) {
		this.funcao = funcao;
	}
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public String getOutros() {
		return outros;
	}
	public void setOutros(String outros) {
		this.outros = outros;
	}
	public String getPlenario() {
		return plenario;
	}
	public void setPlenario(String plenario) {
		this.plenario = plenario;
	}
	public String getDiretoria() {
		return diretoria;
	}
	public void setDiretoria(String diretoria) {
		this.diretoria = diretoria;
	}
	public String getCamaraEspecializada() {
		return camaraEspecializada;
	}
	public void setCamaraEspecializada(String camaraEspecializada) {
		this.camaraEspecializada = camaraEspecializada;
	}
	public String getComissaoPermanente() {
		return comissaoPermanente;
	}
	public void setComissaoPermanente(String comissaoPermanente) {
		this.comissaoPermanente = comissaoPermanente;
	}
	public String getComissaoEspecial() {
		return comissaoEspecial;
	}
	public void setComissaoEspecial(String comissaoEspecial) {
		this.comissaoEspecial = comissaoEspecial;
	}
	public String getOutrosOrgaoDeOrigem() {
		return outrosOrgaoDeOrigem;
	}
	public void setOutrosOrgaoDeOrigem(String outrosOrgaoDeOrigem) {
		this.outrosOrgaoDeOrigem = outrosOrgaoDeOrigem;
	}
	public String getItemDaPauta() {
		return itemDaPauta;
	}
	public void setItemDaPauta(String itemDaPauta) {
		this.itemDaPauta = itemDaPauta;
	}
	public String getRelator() {
		return relator;
	}
	public void setRelator(String relator) {
		this.relator = relator;
	}
	public String getTratamento() {
		return tratamento;
	}
	public void setTratamento(String tratamento) {
		this.tratamento = tratamento;
	}
	public String getEmailOficiado() {
		return emailOficiado;
	}
	public void setEmailOficiado(String emailOficiado) {
		this.emailOficiado = emailOficiado;
	}
	public String getComunicados() {
		return comunicados;
	}
	public void setComunicados(String comunicados) {
		this.comunicados = comunicados;
	}	
	public String getExtratoDePauta() {
		return extratoDePauta;
	}
	public void setExtratoDePauta(String extratoDePauta) {
		this.extratoDePauta = extratoDePauta;
	}
	public String getCabecalhoPauta() {
		return cabecalhoPauta;
	}
	public void setCabecalhoPauta(String cabecalhoPauta) {
		this.cabecalhoPauta = cabecalhoPauta;
	}
	public String getCorrespondenciasRecebidas() {
		return correspondenciasRecebidas;
	}
	public void setCorrespondenciasRecebidas(String correspondenciasRecebidas) {
		this.correspondenciasRecebidas = correspondenciasRecebidas;
	}
	public String getAssuntoEmPauta() {
		return assuntoEmPauta;
	}
	public void setAssuntoEmPauta(String assuntoEmPauta) {
		this.assuntoEmPauta = assuntoEmPauta;
	}
	public String getAssuntoRelatados() {
		return assuntoRelatados;
	}
	public void setAssuntoRelatados(String assuntoRelatados) {
		this.assuntoRelatados = assuntoRelatados;
	}
	public String getAssuntoExtraPauxa() {
		return assuntoExtraPauxa;
	}
	public void setAssuntoExtraPauxa(String assuntoExtraPauxa) {
		this.assuntoExtraPauxa = assuntoExtraPauxa;
	}
	public String getTextoEmailOficiado() {
		return textoEmailOficiado;
	}
	public void setTextoEmailOficiado(String textoEmailOficiado) {
		this.textoEmailOficiado = textoEmailOficiado;
	}
	public FuncionarioDto getFuncionarioDto() {
		return funcionarioDto;
	}
	public void setFuncionarioDto(FuncionarioDto funcionarioDto) {
		this.funcionarioDto = funcionarioDto;
	}
	public ValidSignDto getValidSigner() {
		return validSigner;
	}
	public void setValidSigner(ValidSignDto validSigner) {
		this.validSigner = validSigner;
	}
	public ArtDto getArt() {
		return art;
	}
	public void setArt(ArtDto art) {
		this.art = art;
	}
	
}
