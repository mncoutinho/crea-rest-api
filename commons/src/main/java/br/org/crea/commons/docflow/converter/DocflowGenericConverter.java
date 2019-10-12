package br.org.crea.commons.docflow.converter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import br.org.crea.commons.dao.siacol.ProtocoloSiacolDao;
import br.org.crea.commons.docflow.dto.DocflowGenericDto;
import br.org.crea.commons.docflow.dto.FileUploadDocflowDto;
import br.org.crea.commons.models.commons.dtos.DocumentoGenericDto;
import br.org.crea.commons.models.protocolo.dtos.JuntadaProtocoloDto;
import br.org.crea.commons.models.protocolo.dtos.MovimentoProtocoloDto;
import br.org.crea.commons.models.protocolo.dtos.SubstituicaoProtocoloDto;
import br.org.crea.commons.models.protocolo.dtos.TramiteDto;
import br.org.crea.commons.models.siacol.ProtocoloSiacol;
import br.org.crea.commons.util.FileUtils;
import br.org.crea.commons.validsigner.dto.FormUploadAssinaturaDto;

public class DocflowGenericConverter {

	@Inject ProtocoloSiacolDao protocoloSiacolDao;
	
	/**
	 * Pode ser usado para setar parâmetros necessários a qualquer chamada que faça uso do upload de binário na Api do Docflow,
	 * toda vez que um pdf eh enviado na requisição
	 * 
	 * @param uploadDocumentoDto - eh um formData que vem da request
	 * @return dto - Objeto com todos os parâmetros obrigatórios para o upload do binário no Docflow
	 *  
	 * */
	public DocflowGenericDto toDocFlowGenericDto(FileUploadDocflowDto uploadDocumentoDto) {
		DocflowGenericDto dto = new DocflowGenericDto();
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss,SSS");
		Date date = new Date();
		String dataArquivo = dateFormat.format(date);
		
		dto.setObservacaoDoDocumento(uploadDocumentoDto.getObservacao() != null ? uploadDocumentoDto.getObservacao() : "");
		dto.setAssunto(uploadDocumentoDto.getAssunto());
		dto.setDataArquivo(dataArquivo);
		dto.setInputStreamArquivoPdf(uploadDocumentoDto.getFile());
		dto.setUnidadeDestino(uploadDocumentoDto.getUnidadeDestino());
		dto.setUnidadeOrigem(uploadDocumentoDto.getUnidadeDestino());
		dto.setCodigoDepartamento(uploadDocumentoDto.getUnidadeDestino());
		dto.setInteressado(uploadDocumentoDto.getInteressado());
		dto.setMatricula(uploadDocumentoDto.getMatricula());
		dto.setProtocoloDoDocumento(uploadDocumentoDto.getProtocoloDoDocumento());
		dto.setNomeArquivoPdf(uploadDocumentoDto.getFileName());
		dto.setNumeroProcesso(uploadDocumentoDto.getNumeroProcesso());
		dto.setObservacao(uploadDocumentoDto.getObservacao());
		dto.setTipoDocumento(uploadDocumentoDto.getTipoDocumento());
		dto.setTipoProcesso(uploadDocumentoDto.getTipoProcesso());
		
		return dto;
	}
	
	/**
	 * Pode ser usado para setar parâmetros necessários a qualquer chamada que faça uso do upload de binário na Api do Docflow,
	 * toda vez que um pdf eh enviado na requisição
	 * 
	 * @param documentoDto - contém os dados com a representação do documento
	 * @return dto - Objeto com todos os parâmetros obrigatórios para o upload do binário no Docflow
	 *  
	 * */
	public DocflowGenericDto toDocFlowGenericDto(DocumentoGenericDto documentoDto) {
		DocflowGenericDto dto = new DocflowGenericDto();
		
//		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss,SSS");
//		Date date = new Date();
//		String dataArquivo = dateFormat.format(date);
//		
//		dto.setObservacaoDoDocumento(uploadDocumentoDto.getObservacao() != null ? uploadDocumentoDto.getObservacao() : "");
//		dto.setAssunto(uploadDocumentoDto.getAssunto());
//		dto.setDataArquivo(dataArquivo);
//		dto.setInputStreamArquivoPdf(uploadDocumentoDto.getFile());
//		dto.setUnidadeDestino(uploadDocumentoDto.getUnidadeDestino());
//		dto.setUnidadeOrigem(uploadDocumentoDto.getUnidadeDestino());
//		dto.setCodigoDepartamento(uploadDocumentoDto.getUnidadeDestino());
//		dto.setInteressado(uploadDocumentoDto.getInteressado());
//		dto.setMatricula(uploadDocumentoDto.getMatricula());
//		dto.setProtocoloDoDocumento(uploadDocumentoDto.getProtocoloDoDocumento());
//		dto.setNomeArquivoPdf(uploadDocumentoDto.getFileName());
//		dto.setNumeroProcesso(uploadDocumentoDto.getNumeroProcesso());
//		dto.setObservacao(uploadDocumentoDto.getObservacao());
//		dto.setTipoDocumento(uploadDocumentoDto.getTipoDocumento());
//		dto.setTipoProcesso(uploadDocumentoDto.getTipoProcesso());
		
		return dto;
	}
	
	/**
	 * Pode ser usado para setar parâmetros necessários a qualquer chamada que faça uso do seguintes serviços na Api do Docflow:
	 *    - envio de protocolo
	 *    - recebimento de protocolo
	 *    
	 * Obs. Se estivermos trabalhando com envio 'unidadeDestino' considera idDepartamentoDestino enviado na requisição, senão se
	 * a chamada for para recebimento 'unidadeDestino' considera destino do ultimo movimento, ou seja, onde o protocolo está.
	 * A comutação na propriedade está sendo executada no ternário abaixo.
	 *    
	 * @param tramiteDto
	 * @return dto - Objeto com todos os parâmetros obrigatórios para uso dos serviços descritos acima
	 *  
	 * */
	public DocflowGenericDto toDocFlowGenericDto(TramiteDto tramiteDto) {
		
		DocflowGenericDto dto = new DocflowGenericDto();
		
		 MovimentoProtocoloDto destinoRecebimento = tramiteDto.getUltimoMovimento();
		 Long destinoEnvio = tramiteDto.getIdDepartamentoDestino();
		
		dto.setMatricula(tramiteDto.getFuncionarioTramite().getMatricula().toString());
		dto.setNumeroProtocolo(tramiteDto.getNumeroProtocolo().toString());
		dto.setUnidadeDestino(destinoEnvio == null ? destinoRecebimento.getIdDepartamentoDestino().toString() : destinoEnvio.toString());
		dto.setUnidadeOrigem(tramiteDto.getUltimoMovimento().getIdDepartamentoDestino().toString());
		dto.setCodigoClassificacaoTramite(tramiteDto.getIdSituacaoTramite() != null ? tramiteDto.getIdSituacaoTramite().toString() : null);
		dto.setCodigoSituacao(tramiteDto.getIdSituacaoTramite() != null ? tramiteDto.getIdSituacaoTramite().toString() : null);

		return dto;
	}

	
	/**
	 * Usado para setar parâmetros necessários a qualquer chamada que faça uso dos serviços na Api do Docflow:
	 * - Anexar protocolo
	 * - Desanexar protocolo
	 * - Apensar protocolo
	 * - Desapensar protocolo   
	 *    
	 * @param juntadaDto
	 * @return dto - Objeto com todos os parâmetros obrigatórios para uso dos serviços descritos acima
	 *  
	 * */
	public DocflowGenericDto toDocFlowGenericDto(JuntadaProtocoloDto juntadaDto) {
		DocflowGenericDto dto = new DocflowGenericDto();
		
		dto.setMatricula(juntadaDto.getFuncionarioDaJuntada().getMatricula().toString());
		dto.setNumeroProtocoloPrincipal(juntadaDto.getProtocoloPrincipal().getNumeroProtocolo().toString());
		dto.setNumeroProtocoloJuntada(juntadaDto.getProtocoloDaJuntada().getNumeroProtocolo().toString());
		dto.setCodigoDepartamento(juntadaDto.getProtocoloPrincipal().getUltimoMovimento().getIdDepartamentoDestino().toString());

		return dto;
	}
	
	/**
	 * Usado para setar parâmetros necessários a qualquer chamada que faça uso 
	 * do serviço de substituição na Api do Docflow:
	 *    
	 * @param substituicaoDto
	 * @return dto - Objeto com todos os parâmetros obrigatórios para uso dos serviços descritos acima
	 *  
	 * */
	public DocflowGenericDto toDocFlowGenericDto(SubstituicaoProtocoloDto substituicaoDto) {
		DocflowGenericDto dto = new DocflowGenericDto();
		
		dto.setMatricula(substituicaoDto.getFuncionarioDaSubstituicao().getMatricula().toString());
		dto.setNumeroProtocoloSubstituido(substituicaoDto.getProtocoloSubstituido().getNumeroProtocolo().toString());
		dto.setNumeroProtocoloSubstituto(substituicaoDto.getProtocoloSubstituto().getNumeroProtocolo().toString());
		dto.setCodigoDepartamento(substituicaoDto.getProtocoloSubstituto().getUltimoMovimento().getIdDepartamentoDestino().toString());

		return dto;
	}

	public DocflowGenericDto toDocFlowGenericDtoAssinado(FormUploadAssinaturaDto uploadDocumentoDto, byte[] bs) {
		
		DocflowGenericDto dto = new DocflowGenericDto();
		
		String numeroProtocolo = uploadDocumentoDto.getObservacaoDocumento() != null ? uploadDocumentoDto.getObservacaoDocumento() : uploadDocumentoDto.getProtocolo().getNumeroProcesso().toString();
		ProtocoloSiacol protocoloSiacol = protocoloSiacolDao.getProtocoloBy(new Long(numeroProtocolo));
		
//		protocoloSiacol.getNumeroProtocolo();S
		
		Character tipoProcesso = protocoloSiacol.getNumeroProtocolo().SIZE <= 1 ? 
				'0' : String.valueOf(protocoloSiacol.getNumeroProtocolo()).charAt(4);
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss,SSS");
		Date date = new Date();
		String dataArquivo = dateFormat.format(date);
		String assunto = protocoloSiacol.getIdAssuntoCorportativo() + " - " + protocoloSiacol.getDescricaoAssuntoCorporativo();		
		dto.setObservacaoDoDocumento("Documento oriundo do Siacol");
		dto.setAssunto(assunto);
		dto.setDataArquivo(dataArquivo);
		dto.setInputStreamArquivoPdf(FileUtils.converteByteArrayParaInputStream(bs));
		dto.setUnidadeDestino(uploadDocumentoDto.getUnidadeDestino());
		dto.setUnidadeOrigem(uploadDocumentoDto.getUnidadeDestino());
		dto.setCodigoDepartamento(uploadDocumentoDto.getUnidadeDestino());
		dto.setInteressado(protocoloSiacol.getNomeInteressado());
		dto.setMatricula(uploadDocumentoDto.getMatricula().toString());
		dto.setProtocoloDoDocumento(protocoloSiacol.getNumeroProtocolo().toString());
		dto.setNomeArquivoPdf(uploadDocumentoDto.getFileName());
		dto.setNumeroProcesso(protocoloSiacol.getNumeroProcesso().toString());
		dto.setObservacao("Documento oriundo do Siacol");
		dto.setTipoDocumento(uploadDocumentoDto.getCodigoTipoDocumento().toString());
		dto.setTipoProcesso(tipoProcesso.toString());
		
		return dto;
	}

	
}