package br.org.crea.commons.docflow.converter;

import java.util.ArrayList;
import java.util.List;

import br.org.crea.commons.docflow.dto.ProtocoloDocflowDto;
import br.org.crea.commons.docflow.helper.ConstantDocflow;
import br.org.crea.commons.docflow.model.processo.MetadadoProcessoDocflow;

public class ProtocoloDocflowConverter {
	
	public ProtocoloDocflowDto toDto(List<MetadadoProcessoDocflow> listDataProcesso){
		
		ProtocoloDocflowDto dto = populaDadosProcesso(listDataProcesso);
		return dto;
	}
	
	public ProtocoloDocflowDto populaDadosProcesso(List<MetadadoProcessoDocflow> metadadosProcesso){
		ProtocoloDocflowDto dto = new ProtocoloDocflowDto();
		
		List<String> listCoInteressadosProcesso = new ArrayList<String>();
		
		for (MetadadoProcessoDocflow dataProcesso : metadadosProcesso) {
			
			switch (dataProcesso.getName()) {
			case ConstantDocflow.NUMERO:
				dto.setNumero(dataProcesso.getValue());
				break;
			case ConstantDocflow.PROTOCOLO:
				dto.setProtocolo(dataProcesso.getValue());
				break;
			case ConstantDocflow.DATA_CRIACAO:
				dto.setDataCriacao(dataProcesso.getValue());
				break;
			case ConstantDocflow.INTERESSADO:
				dto.setInteressado(dataProcesso.getValue());
				break;
			case ConstantDocflow.COINTERESSADOS:
				String[] arrayCointeressados = dataProcesso.getValue().split(",");
				for (String nomeCointeressado : arrayCointeressados) { listCoInteressadosProcesso.add(nomeCointeressado); }
				dto.setCointeressados(listCoInteressadosProcesso);
				break;
			case ConstantDocflow.ASSUNTO:
				dto.setAssunto(dataProcesso.getValue());
				break;
			case ConstantDocflow.OBSERVACAO:
				dto.setObservacao(dataProcesso.getValue());
				break;
			case ConstantDocflow.EXTERNO:
				dto.setExterno(dataProcesso.getValue());
				break;
			case ConstantDocflow.SITUACAO:
				dto.setSituacao(dataProcesso.getValue());
				break;
			case ConstantDocflow.CODIGO_SIGILO:
				dto.setCodigoSigilo(dataProcesso.getValue());
				break;
			case ConstantDocflow.SIGILO:
				dto.setSigilo(dataProcesso.getValue());
				break;
			case ConstantDocflow.TIPO_PROCESSO:
				dto.setTipoProcesso(dataProcesso.getValue());
				break;
			case ConstantDocflow.PAGINAS:
				dto.setPaginas(dataProcesso.getValue());
				break;
			case ConstantDocflow.VOLUMES:
				dto.setVolumes(dataProcesso.getValue());
				break;
			case ConstantDocflow.ORGAO_PRIMEIRO_DESTINO:
				dto.setOrgaoPrimeiroDestino(dataProcesso.getValue());
				break;
			case ConstantDocflow.CODIGO_UND_PRIMEIRO_DESTINO:
				dto.setCodigoUnidadePrimeiroDestino(dataProcesso.getValue());
				break;
			case ConstantDocflow.UNIDADE_PRIMEIRO_DESTINO:
				dto.setUnidadePrimeiroDestino(dataProcesso.getValue());
				break;
			case ConstantDocflow.CIDADE_PRIMEIRO_DESTINO:
				dto.setCidadePrimeiroDestino(dataProcesso.getValue());
				break;
			case ConstantDocflow.ORGAO_AUTOR:
				dto.setOrgaoAutor(dataProcesso.getValue());
				break;
			case ConstantDocflow.CODIGO_UND_AUTOR:
				dto.setCodigoUnidadeAutor(dataProcesso.getValue());
				break;
			case ConstantDocflow.UNIDADE_AUTOR:
				dto.setUnidadeAutor(dataProcesso.getValue());
				break;
			case ConstantDocflow.CIDADE_AUTOR:
				dto.setCidadeAutor(dataProcesso.getValue());
				break;
			case ConstantDocflow.AUTOR:
				dto.setAutor(dataProcesso.getValue());
				break;
			case ConstantDocflow.ORGAO_ATUAL:
				dto.setOrgaoAtual(dataProcesso.getValue());
				break;
			case ConstantDocflow.CODIGO_UND_ATUAL:
				dto.setCodigoUnidadeAtual(dataProcesso.getValue());
				break;
			case ConstantDocflow.UNIDADE_ATUAL:
				dto.setUnidadeAtual(dataProcesso.getValue());
				break;
			case ConstantDocflow.CIDADE_ATUAL: 
				dto.setCidadeAtual(dataProcesso.getValue());
				break;
			case ConstantDocflow.DETENTOR: 
				dto.setDetentor(dataProcesso.getValue());
				break;
			case ConstantDocflow.DATA_ENVIO: 
				dto.setDataEnvio(dataProcesso.getValue());
				break;
			case ConstantDocflow.DATA_RECEBIMENTO: 
				dto.setDataRecebido(dataProcesso.getValue());
				break;
			}
		}
		
		return dto;
	}
}
