package br.org.crea.siacol.converter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.codehaus.jackson.map.ObjectMapper;

import br.org.crea.commons.converter.cadastro.domains.DocumentoConverter;
import br.org.crea.commons.dao.cadastro.pessoa.InteressadoDao;
import br.org.crea.commons.dao.siacol.ReuniaoSiacolDao;
import br.org.crea.commons.models.cadastro.Documento;
import br.org.crea.commons.models.commons.dtos.DomainGenericDto;
import br.org.crea.commons.models.corporativo.pessoa.IInteressado;
import br.org.crea.commons.models.siacol.ProtocoloSiacol;
import br.org.crea.commons.models.siacol.ReuniaoSiacol;
import br.org.crea.commons.models.siacol.RlDocumentoProtocoloSiacol;
import br.org.crea.commons.models.siacol.StatusItemPauta;
import br.org.crea.commons.models.siacol.dtos.EnqueteDto;
import br.org.crea.commons.models.siacol.dtos.ItemPautaDto;
import br.org.crea.commons.models.siacol.dtos.ParticipanteReuniaoSiacolDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.DateUtils;
import br.org.crea.commons.util.StringUtil;

public class PautaReuniaoSiacolConverter {
	
	@Inject
	ProtocoloSiacolConverter protocoloSiacolConverter;
	
	@Inject
	InteressadoDao interessadoDao;
	
	@Inject
	DocumentoConverter documentoConverter;
	
	@Inject
	ReuniaoSiacolDao reuniaoSiacolDao;
	
	@Inject
	HttpClientGoApi httpGoApi;
	
	public RlDocumentoProtocoloSiacol toItemPautalModel(ItemPautaDto dto) {
		
		RlDocumentoProtocoloSiacol model = new RlDocumentoProtocoloSiacol();
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			if(dto.temId()){
				model.setId(dto.getId());
			}
			model.setItem(dto.getItem());
			model.setDescricaoItem(dto.getDescricaoItem());
			model.setObsReuniao(dto.getObsReuniao());
			model.setObsCoordenador(dto.getObsCoordenador());
			model.setObsSumula(dto.getObsSumula());
			model.setPergunta(dto.getPergunta());
			model.setSolicitacaoVista(dto.isSolicitacaoVista());
			model.setIdPessoaDestaque(dto.getIdPessoaDestaque());
			model.setIdPessoaVista(dto.getIdPessoaVista());
			model.setIdPessoaMinerva(dto.getIdPessoaMinerva());
			model.setNumeroDocumento(dto.getNumeroDocumento());
			model.setTotalVotosSim(dto.getTotalVotosSim());
			model.setTotalVotosNao(dto.getTotalVotosNao());
			model.setTotalVotosAbstencao(dto.getTotalVotosAbstencao());
			model.setResultado(dto.getResultado());
			model.setUrgencia(dto.getUrgencia());
			model.setNumeroDocumento(dto.getNumeroDocumento());
//			model.setTemDeclaracaoVoto(dto.isTemDeclaracaoVoto());
			
			if(dto.temProtocolo()){
				ProtocoloSiacol protocolo = new ProtocoloSiacol();
				protocolo.setId(dto.getIdProtocolo());
				model.setProtocolo(protocolo);
			}
			
			if(dto.temDocumento()){
				Documento documento = new Documento();
				documento.setId(dto.getIdDocumento());
				model.setDocumento(documento);
			}
			
			if(dto.temStatus()) {
				StatusItemPauta status = new StatusItemPauta();
				status.setId(dto.getStatus().getId());
				model.setStatus(status);
			}
			
			if(dto.isDestaque()) {
				StatusItemPauta status = new StatusItemPauta();
				status.setId(new Long(3));
				model.setStatus(status);
			}
			
			if(dto.isVista()) {
				StatusItemPauta status = new StatusItemPauta();
				status.setId(new Long(4));
				model.setStatus(status);
			}
			
			if(dto.isTemEnquete()) {
				model.setTemEnquete(dto.isTemEnquete());
				model.setEnquete(mapper.writeValueAsString(dto.getEnquete()));
			} 
			
			
		} catch (Exception e) {
			httpGoApi.geraLog("PautaReuniaoSiacolConverter || toItemPautalModel", StringUtil.convertObjectToJson(dto), e);

		}
		
		return model;
		
		
	}

	public ItemPautaDto toItemPautaDto(RlDocumentoProtocoloSiacol model) {
		
		ItemPautaDto dto = new ItemPautaDto();
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			
			if(model.temId()){
				dto.setId(model.getId());
			}
			
			dto.setItem(model.getItem());
			dto.setDescricaoItem(model.getDescricaoItem());
			dto.setObsReuniao(model.getObsReuniao());
			dto.setObsCoordenador(model.getObsCoordenador());
			dto.setObsSumula(model.getObsSumula());
			dto.setPergunta(model.getPergunta());
			dto.setNumeroDocumento(model.getNumeroDocumento());
			dto.setResultado(model.getResultado());
			dto.setTemDeclaracaoVoto(model.isTemDeclaracaoVoto());
			dto.setUrgencia(model.getUrgencia());
			
			if(model.temProtocolo()){
				dto.setIdProtocolo(model.getProtocolo().getId());
			}
			
			if(model.temDocumento()){
				dto.setIdDocumento(model.getDocumento().getId());
			}
			
			if(model.temStatus()) {
				DomainGenericDto status = new DomainGenericDto();
				status.setId(model.getStatus().getId());
				status.setNome(model.getStatus().getNome());
				dto.setStatus(status);
			}
			
			
			if(model.temPessoaDestaque()) {
				ParticipanteReuniaoSiacolDto pessoaDestaque = new ParticipanteReuniaoSiacolDto();
				IInteressado interessado = interessadoDao.buscaInteressadoBy(model.getIdPessoaDestaque());
				pessoaDestaque.setId(interessado.getId());
				pessoaDestaque.setNome(interessado.getNome());
				pessoaDestaque.setBase64(interessado.getFotoBase64());
				dto.setIdPessoaDestaque(model.getIdPessoaDestaque());
				dto.setPessoaDestaque(pessoaDestaque);
				dto.setDestaque(true);
				dto.setNomePessoa(interessado.getNome());
			}
			
			if(model.temPessoaVista() || model.isSolicitacaoVista()) {
				ParticipanteReuniaoSiacolDto pessoaVista = new ParticipanteReuniaoSiacolDto();
				IInteressado interessado = interessadoDao.buscaInteressadoBy(model.getIdPessoaVista());
				pessoaVista.setId(interessado.getId());
				pessoaVista.setNome(interessado.getNome());
				pessoaVista.setBase64(interessado.getFotoBase64());
				dto.setIdPessoaVista(model.getIdPessoaVista());
				dto.setPessoaVista(pessoaVista);
				dto.setVista(true);
				dto.setNomePessoa(interessado.getNome());
			}
			
			if(model.temPessoaMinerva()) {
				ParticipanteReuniaoSiacolDto pessoaMinerva = new ParticipanteReuniaoSiacolDto();
				IInteressado interessado = interessadoDao.buscaInteressadoBy(model.getIdPessoaMinerva());
				pessoaMinerva.setId(interessado.getId());
				pessoaMinerva.setNome(interessado.getNome());
				pessoaMinerva.setBase64(interessado.getFotoBase64());
				dto.setIdPessoaMinerva(model.getIdPessoaDestaque());
				dto.setPessoaMinerva(pessoaMinerva);
//				dto.setNomePessoa(interessado.getNome());
			}
			
			dto.setTemEnquete(model.isTemEnquete());
			if(model.isTemEnquete()) {
				dto.setEnquete(mapper.readValue(model.getEnquete(), Object.class));
				dto.setEnqueteDto(mapper.readValue(model.getEnquete(), EnqueteDto.class));
			}
			
			dto.setSolicitacaoVista(model.isSolicitacaoVista());
			dto.setEmVotacao(model.isEmVotacao());
			dto.setExtraPauta(model.getTemExtraPauta());
		} catch (Throwable e) {
			httpGoApi.geraLog("PautaReuniaoSiacolConverter || toItemPautaDto", StringUtil.convertObjectToJson(model), e);
		}
		
		return dto;
	}
	
	public ItemPautaDto toItemPautaDtoComProtocolo(RlDocumentoProtocoloSiacol model) {
		
		ItemPautaDto dto = new ItemPautaDto();
		
		if(model.temId()){
			dto.setId(model.getId());
		}
		
		dto.setItem(model.getItem());
		dto.setDescricaoItem(model.getDescricaoItem());
		dto.setObsReuniao(model.getObsReuniao());
		dto.setObsCoordenador(model.getObsCoordenador());
		dto.setObsSumula(model.getObsSumula());
		dto.setRespostaEnquete(model.getResultadoEnquete());
		dto.setTotalVotosAbstencao(model.getTotalVotosAbstencao());
		dto.setTotalVotosNao(model.getTotalVotosNao());
		dto.setTotalVotosSim(model.getTotalVotosSim());
		dto.setResultado(model.getResultado());
		dto.setTemDeclaracaoVoto(model.isTemDeclaracaoVoto());
		dto.setUrgencia(model.getUrgencia());
		dto.setNumeroDocumento(model.getNumeroDocumento());
		
		if(model.temProtocolo()){
			dto.setProtocolo(protocoloSiacolConverter.toDto(model.getProtocolo()));
		}
		
		if(model.temDocumento()){
			ReuniaoSiacol reuniao = new ReuniaoSiacol();
			reuniao = reuniaoSiacolDao.getReuniaoPor(model.getDocumento().getId());
			dto.setPauta(documentoConverter.toDto(model.getDocumento()));
			dto.setDataReuniao(DateUtils.format(reuniao.getDataReuniao(),DateUtils.DD_MM_YYYY));
			dto.setIdDocumento(model.getDocumento().getId());
		}
		
		if(model.temStatus()) {
			DomainGenericDto status = new DomainGenericDto();
			status.setId(model.getStatus().getId());
			status.setNome(model.getStatus().getNome());
			dto.setStatus(status);
		}
		
		dto.setIdPessoaVista(model.getIdPessoaVista());
		dto.setIdPessoaDestaque(model.getIdPessoaDestaque());
		
		if(model.temStatus()) {
			if(model.temDestaque()) {
				dto.setDestaque(true);
			}
			
			if(model.temVista()) {
				dto.setVista(true);
			}
		}
		
		dto.setEmVotacao(model.isEmVotacao());
		
		return dto;
	}

	public List<ItemPautaDto> toListItemDto(List<RlDocumentoProtocoloSiacol> listModel) {
		
		List<ItemPautaDto> listDto = new ArrayList<ItemPautaDto>();
		
		for(RlDocumentoProtocoloSiacol m : listModel) {
			listDto.add(toItemPautaDto(m));
		}
		
		return listDto;
	}

	public List<ItemPautaDto> toListItemDtoComProtocolo(List<RlDocumentoProtocoloSiacol> listModel) {
		
		List<ItemPautaDto> listDto = new ArrayList<ItemPautaDto>();
		
		for(RlDocumentoProtocoloSiacol m : listModel) {
			listDto.add(toItemPautaDtoComProtocolo(m));
		}
		
		return listDto;
	}



	

}
