package br.org.crea.commons.service.commons;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.google.gson.Gson;

import br.org.crea.commons.converter.cadastro.domains.DepartamentoConverter;
import br.org.crea.commons.dao.DocumentoDao;
import br.org.crea.commons.dao.siacol.ProtocoloSiacolDao;
import br.org.crea.commons.dao.siacol.ReuniaoSiacolDao;
import br.org.crea.commons.models.cadastro.Documento;
import br.org.crea.commons.models.cadastro.dtos.DepartamentoDto;
import br.org.crea.commons.models.cadastro.dtos.DocumentoDto;
import br.org.crea.commons.models.siacol.ProtocoloSiacol;
import br.org.crea.commons.models.siacol.ReuniaoSiacol;
import br.org.crea.commons.models.siacol.dtos.ProtocoloSiacolDto;
import br.org.crea.commons.models.siacol.dtos.ReuniaoSiacolDto;
import br.org.crea.commons.models.siacol.dtos.documento.PautaDto;
import br.org.crea.commons.models.siacol.dtos.documento.RelatorioVotoDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.service.NumeroDocumentoService;
import br.org.crea.commons.util.DateUtils;
import br.org.crea.commons.util.StringUtil;

public class FinalizarDocumentoPautaVirtual {

	@Inject ReuniaoSiacolDao reuniaoSiacolDao;
	@Inject DepartamentoConverter departamentoConverter;
	@Inject HttpClientGoApi httpGoApi;
	@Inject DocumentoDao documentoDao;
	@Inject ProtocoloSiacolDao protocoloSiacolDao;
	@Inject NumeroDocumentoService numeroDocumentoService;
	
	private Gson gson = null;
	
	public Documento atualizaDocumento(Documento pauta, ReuniaoSiacol reuniao) {
		
		try {
			Gson gson = new Gson(); 
			List<ProtocoloSiacolDto> listProtocoloSiacol =
					protocoloSiacolConverterToListDto(reuniaoSiacolDao.getProtocolosAPautar(pesquisaAtributosPauta(reuniao)));
			DocumentoDto documentoDto = gson.fromJson(pauta.getRascunho(), DocumentoDto.class);  
			String textoPrincipal = "";
	
			for (ProtocoloSiacolDto protocolo :  listProtocoloSiacol) {
				
				textoPrincipal+="<p>1."+(listProtocoloSiacol.indexOf(protocolo)+1)+" - <b>Processo nº "+protocolo.getNumeroProcesso()+"</b>";
				textoPrincipal+="<b> - Protocolo nº "+protocolo.getNumeroProtocolo()+"</b></p></br>";
				textoPrincipal+="<p><b>Interessado: </b>"+protocolo.getNomeInteressado()+"</p></br>";
				textoPrincipal+="<p><b>Assunto: </b>"+protocolo.getDescricaoAssuntoCorporativo()+"</p></br>";
				textoPrincipal+="<p><b>Relator: </b>"+protocolo.getNomeResponsavel()+"</p></br><p></p>";
	
	            Long indexDocumento = new Long(1);
	            if (protocolo.getListRelatorioVoto() != null && !protocolo.getListRelatorioVoto().isEmpty()) {
	            	for (RelatorioVotoDto relatorioVoto :  protocolo.getListRelatorioVoto()) {
	            		String relatorio = "";
	            		if(relatorioVoto.getTexto() != null){
	            			relatorio = relatorioVoto.getTexto().replaceAll("[\"]", "'");
	            			textoPrincipal+=indexDocumento+"º Relatório<p></br>"+relatorio+"</p><p></p>";
	            			indexDocumento++;
	            		}
	              };
	            }
	            textoPrincipal+="<p></p>";
	        };
	        
	        textoPrincipal.replaceAll("\"", "\'");
	        documentoDto.setTextoPrincipal(textoPrincipal);
	        documentoDto.setLocal(reuniao.getLocal());
	 		documentoDto.setDataDocumento(DateUtils.format(reuniao.getDataReuniao(), DateUtils.DD_MM_YYYY));
	 		

	 		pauta.setNumeroDocumento(numeroDocumentoService.getProximoNumeroDocumento(reuniao.getDepartamento().getId(), 1105L));
			pauta.setDocumento(populaDtoToString(documentoDto));
			return pauta;
		} catch (Exception e) {
			return null;
		}
	}

	private String populaDtoToString(DocumentoDto documentoDto) {
		
		String documento ="{";	
		documento += "\"assunto\":\"\", ";
		documento +="\"assinatura\":\"\",";
		documento +="\"cargo\":\""+documentoDto.getCargo()+"\",";
		documento +="\"dataAssinatura\":\"\",";
		documento +="\"departamentoOrigem\":\""+documentoDto.getDepartamentoOrigem()+"\",";
		documento +="\"departamentoDestino\":\"\",";
		documento +="\"enderecoDescritivo\":\"\",";
		documento +="\"ementa\":\"\",";
		documento +="\"funcao\":\"\",";
		documento +="\"interessado\":\"\",";
		documento +="\"itemDaPauta\":\"\",";
		documento +="\"local\":\""+documentoDto.getLocal()+"\",";
		documento +="\"matricula\":\"\",";
		documento +="\"nomeDestinatario\":\"\",";
		documento +="\"nomeOrigem\":\"\",";
		documento +="\"nomeResponsavel\":\""+documentoDto.getNomeResponsavel()+"\",";
		documento +="\"numeroDocumento\":\""+documentoDto.getNumeroDocumento()+"\",";
		documento +="\"numeroDocumentoExterno\":\"\",";
		documento +="\"numeroProcesso\":\"\",";
		documento +="\"numeroProtocolo\":\"\",";
		documento +="\"numeroMemorando\":\"\",";
		documento +="\"oficiado\":\"\",";
		documento +="\"origem\":\"\",";
		documento +="\"outros\":\""+documentoDto.getOutros()+"\",";
		documento +="\"referencia\":\"\",";
		documento +="\"template\":\"SIACOL_PAUTA\",";
		documento +="\"textoPrincipal\":\""+documentoDto.getTextoPrincipal()+"\",";
		documento +="\"textoVoto\":\"\",";
		documento +="\"textoAuxiliar\":\"\",";
		documento +="\"tipoDocumento\":\""+documentoDto.getTipoDocumento()+"\",";
		documento +="\"tratamento\":\"\",";
		documento +="\"quorum\":\"\",";
		documento +="\"dataDocumento\":\""+documentoDto.getDataDocumento()+"\",";
		documento +="\"dataDocumentoFormatada\":\""+documentoDto.getDataDocumentoFormatada()+"\",";
		documento +="\"comunicados\":\"\",";
		documento +="\"extratoDePauta\":\"\",";
		documento +="\"cabecalhoPauta\":\"\",";
		documento +="\"protocolosDeferido\":\"\",";
		documento +="\"correspondenciasRecebidas\":\"\",";
		documento +="\"assuntoEmPauta\":\"\",";
		documento +="\"assuntoRelatados\":\"\",";
		documento +="\"assuntoExtraPauxa\":\"\"";
		documento +="}";
		
		Gson g = new Gson(); 
		Object jsonString = g.fromJson(documento, Object.class);

		String Stringjson = gson.toJson(jsonString); 
		return Stringjson;
	}

	private ReuniaoSiacolDto pesquisaAtributosPauta(ReuniaoSiacol reuniao) {
		ReuniaoSiacolDto dto = new ReuniaoSiacolDto();
		Gson gson = new Gson(); 
		
		try {
			
			DepartamentoDto departamentoReuniao = new DepartamentoDto();
			departamentoReuniao.setId(reuniao.getDepartamento().getId());
			
			dto.setDepartamento(departamentoReuniao);
			dto.setOrdenacaoPauta(gson.fromJson(reuniao.getPauta().getRascunho(), PautaDto.class).getOrdenacaoPauta());
			dto.setListDigitoExclusaoProtocolo(gson.fromJson(reuniao.getPauta().getRascunho(), PautaDto.class).getListDigitoExclusaoProtocolo());
			dto.setIncluiProtocoloDesfavoravel(gson.fromJson(reuniao.getPauta().getRascunho(), PautaDto.class).incluiProtocoloDesfavoravel());
			dto.setVirtual(reuniao.getVirtual());
			
		} catch (Throwable e) {
			httpGoApi.geraLog("JobFinalizaPautaReuniao || pesquisaAtributosPauta", StringUtil.convertObjectToJson(reuniao), e);
		}
		
		return dto;
	}
	
	public List<ProtocoloSiacolDto> protocoloSiacolConverterToListDto(List<ProtocoloSiacol> ListaProtocoloSiacol) {

		List<ProtocoloSiacolDto> listDto = new ArrayList<ProtocoloSiacolDto>();

		for (ProtocoloSiacol p : ListaProtocoloSiacol) {
			if ( p != null ) {
				listDto.add(toDto(p));
			}
		}
		return listDto;
	}

	public ProtocoloSiacolDto toDto(ProtocoloSiacol model) {
		ProtocoloSiacolDto dto = null;

		if(model != null) {
			
			dto = new ProtocoloSiacolDto();
			
			dto.setId(model.getId());
			dto.setNumeroProcesso(model.getNumeroProcesso());
			dto.setNumeroProtocolo(model.getNumeroProtocolo());
			dto.setIdAssuntoCorportativo(model.getIdAssuntoCorportativo());
			dto.setDescricaoAssuntoCorporativo(model.getDescricaoAssuntoCorporativo());	
			dto.setNomeInteressado(model.getNomeInteressado());
			dto.setNomeResponsavel(model.getNomeResponsavel());
			
			if (protocoloSiacolDao.temRelatorioVoto(model.getNumeroProtocolo())) {
				dto.setListRelatorioVoto(toListRelatorioVotoDto(documentoDao.getDocumentosPorTipoENumeroProtocolo(new Long(1101), model.getNumeroProtocolo())));
			}
			
		}

		return dto;
	}
	
	public RelatorioVotoDto toRelatorioVotoDto(Documento model) {
		RelatorioVotoDto relatorioVotoDto = new RelatorioVotoDto();
		try {
			gson = new Gson();
			relatorioVotoDto = gson.fromJson(model.getRascunho(), RelatorioVotoDto.class);
			relatorioVotoDto.setDataCriacao(model.getDataCriacao().getTime());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return relatorioVotoDto;
		
	}
	
	public List<RelatorioVotoDto> toListRelatorioVotoDto(List<Documento> listModel) {
		List<RelatorioVotoDto> listRelatorioVoto = new ArrayList<RelatorioVotoDto>();
		
		for (Documento documento : listModel) {
			listRelatorioVoto.add(toRelatorioVotoDto(documento));
		}
		
		return listRelatorioVoto;
	}
		
}
