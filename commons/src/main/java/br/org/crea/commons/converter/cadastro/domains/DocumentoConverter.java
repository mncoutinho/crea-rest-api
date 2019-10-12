package br.org.crea.commons.converter.cadastro.domains;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.codehaus.jackson.map.ObjectMapper;

import br.org.crea.commons.converter.commons.ArquivoConverter;
import br.org.crea.commons.dao.cadastro.pessoa.PessoaDao;
import br.org.crea.commons.models.cadastro.Departamento;
import br.org.crea.commons.models.cadastro.Documento;
import br.org.crea.commons.models.cadastro.StatusDocumento;
import br.org.crea.commons.models.cadastro.dtos.DocumentoDto;
import br.org.crea.commons.models.commons.dtos.DocumentoGenericDto;
import br.org.crea.commons.models.commons.dtos.DomainGenericDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.DateUtils;
import br.org.crea.commons.util.StringUtil;

public class DocumentoConverter {


	@Inject	private HttpClientGoApi httpGoApi;
	
	@Inject private TipoDocumentoConverter tipoDocumentoConverter;
	
	@Inject private ArquivoConverter arquivoConverter;
	
	@Inject private PessoaDao pessoaDao;
	
	@Inject DepartamentoConverter departamentoConverter;
	
	
	public List<DocumentoGenericDto> toListDto(List<Documento> listModel) {
		
		List<DocumentoGenericDto> listDto = new ArrayList<DocumentoGenericDto>();
		
		for(Documento documento : listModel){
			listDto.add(toDto(documento));
		}
		
		return listDto;
	}

	public DocumentoGenericDto toDto(Documento model) {

		DocumentoGenericDto dto = new DocumentoGenericDto();
		ObjectMapper mapper = new ObjectMapper();

		try {
			
			if(model != null) {
				
				dto.setId(model.getId());
				dto.setCodigoExterno(model.getCodigoExterno());
				dto.setProtocoloDocflow(model.getProtocoloDocflow());
				dto.setModulo(model.getModulo());
				dto.setResponsavel(model.getResponsavel());
				if (dto.temResponsavel()) {
					dto.setNomeResponsavel(pessoaDao.getBy(dto.getResponsavel()).getNome());
				}
				dto.setProtocolo(model.getProtocolo());
				
				if(model.temDataCriacao()) {
					dto.setDataCriacao(model.getDataCriacao());
					dto.setDataCriacaoFormatada(DateUtils.format(model.getDataCriacao(), DateUtils.DD_MM_YYYY_HH_MM));
				}
				
				if(model.temDataAtualizacao()) {
					dto.setDataAtualizacao(model.getDataAtualizacao());
					dto.setDataAtualizacaoFormatada(DateUtils.format(model.getDataAtualizacao(), DateUtils.DD_MM_YYYY_HH_MM));
				}
				
				if(model.temDocumento()) {
					dto.setDocumento(mapper.readValue(model.getDocumento(), Object.class));
				}
				
				if(model.temRascunho()) {
					dto.setRascunho(mapper.readValue(model.getRascunho(), Object.class));
				}
				
				dto.setObservacao(model.getObservacao());
				
				if(model.temStatus()){
					
					DomainGenericDto status = new DomainGenericDto();
					status.setId(model.getStatusDocumento().getId());
					status.setNome(model.getStatusDocumento().getDescricao());
					dto.setStatus(status);
				}
				
				if(model.temArquivo()) {
					dto.setArquivo(arquivoConverter.toDto(model.getArquivo()));
				}
				
				if(model.temTipo()) {
					dto.setTipo(tipoDocumentoConverter.toDto(model.getTipo()));
				}
				
				dto.setAssinado(model.isAssinado());
				dto.setHash(model.getHash());
				dto.setProcesso(model.getProcesso());
			}
			
			if(model.temDepartamento()){
				dto.setDepartamento(
						departamentoConverter.toDto(model.getDepartamento()));	
			}
			
			dto.setAssinado(model.isAssinado());
			dto.setNumeroDocumento(model.getNumeroDocumento());
			dto.setHash(model.getHash());

		} catch (Exception e) {
			httpGoApi.geraLog("DocumentoConverter || toDto", StringUtil.convertObjectToJson(model), e);
		}

		return dto;
	}


	public Documento toModel(DocumentoGenericDto dto) {

		Documento model = new Documento();
		ObjectMapper mapper = new ObjectMapper();

		try {

			if (dto.temId()) {
				model.setId(dto.getId());
			}
			
			model.setCodigoExterno(dto.getCodigoExterno());
			model.setProtocoloDocflow(dto.getProtocoloDocflow());
			model.setModulo(dto.getModulo());
			model.setResponsavel(dto.getResponsavel());
			model.setProtocolo(dto.getProtocolo());
			model.setProcesso(dto.getProcesso());
			model.setDataCriacao(new Date());
			model.setDataAtualizacao(dto.getDataAtualizacao());
			model.setObservacao(dto.getObservacao());
			model.setChaveAssinaturaRedis(dto.getChaveAssinaturaRedis());
			
			if(dto.temStatus()) {
				StatusDocumento status = new StatusDocumento();
				status.setId(dto.getStatus().getId());
				model.setStatusDocumento(status);
			}
			
			if (dto.temArquivo()) {
				model.setArquivo(arquivoConverter.toModel(dto.getArquivo()));
			}
			if (dto.isSetarArquivoNull()) {
				model.setArquivo(null);
			}
			
			if (dto.temDocumento()) {
				model.setDocumento(mapper.writeValueAsString(dto.getDocumento()));
			}
			
			if (dto.temRascunho()) {
				model.setRascunho(mapper.writeValueAsString(dto.getRascunho()));
			}
			
			if (dto.temTipo()) {
				model.setTipo(tipoDocumentoConverter.toModel(dto.getTipo()));
			}
			
			model.setChaveAssinaturaRedis(dto.getChaveAssinaturaRedis());
			if (dto.temDepartamento()) {
				Departamento departamento = new Departamento();
				departamento.setId(dto.getDepartamento().getId());
				model.setDepartamento(departamento);
			}
			
			model.setAssinado(dto.isAssinado());
			model.setNumeroDocumento(dto.getNumeroDocumento());
			model.setHash(dto.getHash());

		} catch (Exception e) {
			httpGoApi.geraLog("DocumentoConverter || toModel", StringUtil.convertObjectToJson(dto), e);
		}

		return model;
	}

	public DocumentoDto toDocumentoDto(DocumentoGenericDto documentoGenericDto) {
		
		ObjectMapper mapper = new ObjectMapper();
		DocumentoDto dto = new DocumentoDto();
		
		try {
			
			dto = mapper.convertValue(documentoGenericDto.getDocumento(), DocumentoDto.class);
			
		} catch (Exception e) {
			httpGoApi.geraLog("DocumentoConverter || toDocumentoDto", StringUtil.convertObjectToJson(dto), e);
		}
		
		return dto;
		
	}


	
}
