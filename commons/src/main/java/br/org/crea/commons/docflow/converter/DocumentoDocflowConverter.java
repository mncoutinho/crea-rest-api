package br.org.crea.commons.docflow.converter;

import java.util.ArrayList;
import java.util.List;

import br.org.crea.commons.docflow.dto.DocumentoDocflowDto;
import br.org.crea.commons.docflow.helper.ConstantDocflow;
import br.org.crea.commons.docflow.model.processo.DocumentoProcessoDocflow;
import br.org.crea.commons.docflow.model.processo.MetadadoProcessoDocflow;

public class DocumentoDocflowConverter {
	
public DocumentoDocflowDto toDto(List<MetadadoProcessoDocflow> listDataDocumento){
		
		DocumentoDocflowDto dto = populaDadosDocumento(listDataDocumento);
		return dto;
	}
	
	public DocumentoDocflowDto populaDadosDocumento(List<MetadadoProcessoDocflow> metadadosDocumento){
		DocumentoDocflowDto dto = new DocumentoDocflowDto();
		
		for (MetadadoProcessoDocflow dataDocumento : metadadosDocumento) {
			
			switch (dataDocumento.getName()) {
			case ConstantDocflow.ID:
				dto.setId(dataDocumento.getValue());
			case ConstantDocflow.PROTOCOLADO:
				dto.setProtocolado(dataDocumento.getValue());
			case ConstantDocflow.DATA_CRIACAO:
				dto.setDataCriacao(dataDocumento.getValue());
			case ConstantDocflow.NUMERO_DOCUMENTO:
				dto.setNumeroDocumento(dataDocumento.getValue());
			case ConstantDocflow.PROTOCOLO:
				dto.setProtocolo(dataDocumento.getValue());
			case ConstantDocflow.ASSUNTO:
				dto.setAssunto(dataDocumento.getValue());
			case ConstantDocflow.INTERESSADO:
				dto.setInteressado(dataDocumento.getValue());
			case ConstantDocflow.ID_TIPO_CONTEUDO:
				dto.setCodigoTipoContent(dataDocumento.getValue());
			case ConstantDocflow.DATA_DOCUMENTO:
				dto.setDataDocumento(dataDocumento.getValue());
			case ConstantDocflow.PROTOCOLO_PROCESSO:
				dto.setProtocoloProcesso(dataDocumento.getValue());
			case ConstantDocflow.ID_VERSAO_MODELO:
				dto.setIdVersaoModeloPers(dataDocumento.getValue());
			case ConstantDocflow.PODE_RETIRAR:
				dto.setPodeRetirar(dataDocumento.getValue());
			}
		}
		
		return dto;
	}
	
	public List<DocumentoDocflowDto> toListDto(List<DocumentoProcessoDocflow> listMetadadosDocumentos) {
		List<DocumentoDocflowDto> listDocumentos = new ArrayList<DocumentoDocflowDto>();
		
		for (DocumentoProcessoDocflow doc : listMetadadosDocumentos) {

			List<MetadadoProcessoDocflow> listDadosDocumento = doc.getDadosDocumento();
			listDocumentos.add(toDto(listDadosDocumento));
		}
		return listDocumentos;
	}

}
