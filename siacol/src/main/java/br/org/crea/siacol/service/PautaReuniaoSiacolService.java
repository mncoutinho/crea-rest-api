package br.org.crea.siacol.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import br.org.crea.commons.converter.commons.ArquivoConverter;
import br.org.crea.commons.dao.cadastro.pessoa.InteressadoDao;
import br.org.crea.commons.dao.commons.ArquivoDao;
import br.org.crea.commons.dao.siacol.AnexoItemPautaSiacolDao;
import br.org.crea.commons.dao.siacol.ProtocoloSiacolDao;
import br.org.crea.commons.dao.siacol.RlDocumentoProtocoloSiacolDao;
import br.org.crea.commons.factory.siacol.AuditaSiacolReuniaoFactory;
import br.org.crea.commons.models.commons.Arquivo;
import br.org.crea.commons.models.commons.dtos.ArquivoDto;
import br.org.crea.commons.models.commons.dtos.ArquivoFormUploadDto;
import br.org.crea.commons.models.commons.dtos.DocumentoGenericDto;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.models.corporativo.dtos.AssuntoDto;
import br.org.crea.commons.models.corporativo.pessoa.IInteressado;
import br.org.crea.commons.models.siacol.AnexosItemPautaReuniaoSiacol;
import br.org.crea.commons.models.siacol.RlDocumentoProtocoloSiacol;
import br.org.crea.commons.models.siacol.StatusItemPauta;
import br.org.crea.commons.models.siacol.dtos.AnexoItemPautaDto;
import br.org.crea.commons.models.siacol.dtos.ConsultaProtocoloDto;
import br.org.crea.commons.models.siacol.dtos.GenericSiacolDto;
import br.org.crea.commons.models.siacol.dtos.ItemPautaDto;
import br.org.crea.commons.models.siacol.dtos.PesquisaItemPautaSiacolDto;
import br.org.crea.commons.models.siacol.dtos.ProtocoloSiacolDto;
import br.org.crea.commons.models.siacol.dtos.ReuniaoSiacolDto;
import br.org.crea.commons.models.siacol.enuns.StatusProtocoloSiacol;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.service.commons.ArquivoService;
import br.org.crea.commons.util.PDFUtils;
import br.org.crea.commons.util.StorageUtil;
import br.org.crea.commons.util.StringUtil;
import br.org.crea.siacol.converter.AssuntoSiacolConverter;
import br.org.crea.siacol.converter.PautaReuniaoSiacolConverter;
import br.org.crea.siacol.converter.ProtocoloSiacolConverter;

public class PautaReuniaoSiacolService {

	@Inject private RlDocumentoProtocoloSiacolDao rlDocumentoProtocoloDao;

	@Inject private AnexoItemPautaSiacolDao anexoItemDao;

	@Inject private PautaReuniaoSiacolConverter pautaReuniaoSiacolConverter;

	@Inject private ProtocoloSiacolDao protocoloSiacolDao;
	
	@Inject private AssuntoSiacolConverter assuntoSiacolConverter;

	@Inject private ArquivoService arquivoService;

	@Inject private ArquivoDao arquivoDao;

	@Inject private HttpClientGoApi httpGoApi;

	@Inject private ArquivoConverter arquivoConverter;

	@Inject private ProtocoloSiacolConverter protocoloSiacolConverter;

	@Inject private InteressadoDao interessadoDao;
	
	@Inject private ProtocoloSiacolService protocoloSiacolService;
	
	@Inject private AuditaSiacolReuniaoFactory auditaReuniaoFactory;
	
	public ItemPautaDto salvaItem(ItemPautaDto dto, UserFrontDto usuario) {
		RlDocumentoProtocoloSiacol model = new RlDocumentoProtocoloSiacol();
		RlDocumentoProtocoloSiacol itemExistente = rlDocumentoProtocoloDao.getItemDocumentoNumeroItem(dto);
		if (itemExistente != null) {
			Long idItemExistente = itemExistente.getId();
			itemExistente = pautaReuniaoSiacolConverter.toItemPautalModel(dto);
			itemExistente.setId(idItemExistente);
			model = rlDocumentoProtocoloDao.update(itemExistente);
		} else {
			model = rlDocumentoProtocoloDao.create(pautaReuniaoSiacolConverter.toItemPautalModel(dto));
		}
		
		if (dto.temEventoAuditoria()) {
			auditaReuniaoFactory.auditaItemReuniao(dto, usuario);
		}

		return pautaReuniaoSiacolConverter.toItemPautaDto(model);

	}

	public ItemPautaDto atualizaItemDto(ItemPautaDto dto, UserFrontDto usuario) {
		
		RlDocumentoProtocoloSiacol model = new RlDocumentoProtocoloSiacol();
		RlDocumentoProtocoloSiacol itemExistente = rlDocumentoProtocoloDao.getItemDocumentoNumeroItem(dto);
		if (itemExistente != null || dto.getId() != null) {
			itemExistente = itemExistente != null ? itemExistente : new RlDocumentoProtocoloSiacol();
			Long idItemExistente = dto.getId() != null ? dto.getId() : itemExistente.getId();
			itemExistente = pautaReuniaoSiacolConverter.toItemPautalModel(dto);
			itemExistente.setId(idItemExistente);
			model = rlDocumentoProtocoloDao.update(itemExistente);
		} else {
			model = rlDocumentoProtocoloDao.create(pautaReuniaoSiacolConverter.toItemPautalModel(dto));
		}
		
		if (dto.temEventoAuditoria()) {
			auditaReuniaoFactory.auditaItemReuniao(dto, usuario);
		}

		return pautaReuniaoSiacolConverter.toItemPautaDto(model);

//		RlDocumentoProtocoloSiacol model = new RlDocumentoProtocoloSiacol();
//		model = rlDocumentoProtocoloDao.update(pautaReuniaoSiacolConverter.toItemPautalModel(dto));
//
//		return pautaReuniaoSiacolConverter.toItemPautaDto(model);


	}

	public ItemPautaDto atualizaItem(ItemPautaDto dto) {

		RlDocumentoProtocoloSiacol model = new RlDocumentoProtocoloSiacol();
		model = rlDocumentoProtocoloDao.update(pautaReuniaoSiacolConverter.toItemPautalModel(dto));
		

		return pautaReuniaoSiacolConverter.toItemPautaDto(model);
	}

	public void excluiItem(Long idItem, UserFrontDto userDto) {
		anexoItemDao.deletaAnexoItem(idItem);
		rlDocumentoProtocoloDao.deletaItemPauta(idItem);

	}

	public void excluiAnexo(Long idArquivo, UserFrontDto userDto) {
		anexoItemDao.deletaAnexo(idArquivo);
		arquivoService.delete(idArquivo);
	}

	public List<ItemPautaDto> pesquisa(PesquisaItemPautaSiacolDto dto) {

		List<ItemPautaDto> itensDto = new ArrayList<ItemPautaDto>();
		List<RlDocumentoProtocoloSiacol> listModel = new ArrayList<RlDocumentoProtocoloSiacol>();

		listModel = rlDocumentoProtocoloDao.pesquisa(dto);

		if (!listModel.isEmpty()) {
			itensDto = pautaReuniaoSiacolConverter.toListItemDto(listModel);
			return itensDto;
		} else {
			return itensDto;
		}

	}
	
	public List<ItemPautaDto> atualizaItens(List<ItemPautaDto> listItens, UserFrontDto usuario) {
		List<ItemPautaDto> itensPainel = new ArrayList<ItemPautaDto>();

		for (ItemPautaDto dto : listItens) {
			RlDocumentoProtocoloSiacol rlItem = new RlDocumentoProtocoloSiacol();
			rlItem = pautaReuniaoSiacolConverter.toItemPautalModel(dto);
			rlDocumentoProtocoloDao.update(rlItem);
			
			
			if (dto.temEventoAuditoria()) {
				auditaReuniaoFactory.auditaItemReuniao(dto, usuario);
			}
			
		}

		return itensPainel;
	}

	public List<ItemPautaDto> atualizaItensPor(List<ItemPautaDto> listItens, String tipo, Long idReuniao, UserFrontDto usuario) {

		List<ItemPautaDto> itensPainel = new ArrayList<ItemPautaDto>();

		for (ItemPautaDto dto : listItens) {
			RlDocumentoProtocoloSiacol rlItem = new RlDocumentoProtocoloSiacol();
			rlItem = pautaReuniaoSiacolConverter.toItemPautalModel(dto);
			StatusItemPauta status = new StatusItemPauta();
			status.setId(setStatus(tipo));
			rlItem.setStatus(status);
			rlDocumentoProtocoloDao.update(rlItem);
			
			if (dto.temEventoAuditoria()) {
				auditaReuniaoFactory.auditaItemReuniao(dto, usuario);
			}

			// rlDocumentoProtocoloDao.alteraStatusItemPauta(dto.getId(), setStatus(tipo));

		}

		return itensPainel;
	}

	public List<AssuntoDto> getAssuntosDeProtocoloDaPauta(Long idDepartamento) {
		return assuntoSiacolConverter.toListDto(protocoloSiacolDao.getAssuntosParaPauta(idDepartamento));
	}

	private Long setStatus(String tipo) {

		switch (tipo) {
		case "OUTROS":
			return new Long(0);
		case "VOTADO":
			return new Long(1);
		case "NAODESTACADO":
			return new Long(2);
		case "DESTAQUE":
			return new Long(3);
		case "VISTA":
			return new Long(4);
		case "RETIRADO":
			return new Long(5);
		case "EMVOTACAO":
			return new Long(6);
		case "RETIRAVOTACAO":
			return new Long(7);
		default:
			return new Long(0);
		}

	}

	public List<ItemPautaDto> getItensByIdDocumento(Long idDocumento) {
		List<RlDocumentoProtocoloSiacol> listaItens = rlDocumentoProtocoloDao.getItensByIdDocumento(idDocumento);
		Collections.sort(listaItens, new CustomComparator());
		return pautaReuniaoSiacolConverter.toListItemDtoComProtocolo(listaItens);
	}
	
	public class CustomComparator implements Comparator<RlDocumentoProtocoloSiacol> {
	    @Override
	    public int compare(RlDocumentoProtocoloSiacol o1, RlDocumentoProtocoloSiacol o2) {
	        return o1.getItem().compareTo(o2.getItem());
	    }
	}

	public AnexoItemPautaDto salvaAnexoItem(ArquivoFormUploadDto arquivoForm, Long idPessoa) {

		AnexoItemPautaDto itemAnexoDto = new AnexoItemPautaDto();
		ArquivoDto arquivoDto = new ArquivoDto();
		AnexosItemPautaReuniaoSiacol itemArquivo = new AnexosItemPautaReuniaoSiacol();

		Arquivo arquivo = new Arquivo();
		arquivoDto = arquivoService.upload(arquivoForm, idPessoa);
		arquivo.setId(arquivoDto.getId());

		RlDocumentoProtocoloSiacol rl = new RlDocumentoProtocoloSiacol();
		rl.setId(arquivoForm.getIdClient());
		itemArquivo.setItem(rl);
		itemArquivo.setArquivo(arquivo);

		anexoItemDao.create(itemArquivo);

		itemAnexoDto.setArquivo(arquivoDto);
		itemAnexoDto.setIdItemPauta(arquivoForm.getIdClient());
		itemAnexoDto.setId(itemArquivo.getId());
		return itemAnexoDto;
	}

	public ArquivoDto mergePautaEPublica(DocumentoGenericDto documento) {

		PDFUtils pdfUtil = new PDFUtils();
		byte[] arquivoMergido = null;

		Arquivo arquivoPrincipal = arquivoDao.getBy(documento.getArquivo().getId());
		StorageUtil.setPrivado(arquivoPrincipal.isPrivado());

		List<Arquivo> listaAnexos = anexoItemDao.getAnexosItensPauta(documento.getId());

		try {
			pdfUtil.adicionar(IOUtils.toByteArray(new FileInputStream(StorageUtil.getRaizStorage() + arquivoPrincipal.getCaminhoStorage())));

			for (Arquivo a : listaAnexos) {
				pdfUtil.adicionar(IOUtils.toByteArray(new FileInputStream(StorageUtil.getRaizStorage() + a.getCaminhoStorage())));
			}

			arquivoMergido = pdfUtil.concatenar().buildFile();

			FileUtils.writeByteArrayToFile(new File(StorageUtil.getRaizStorage() + arquivoPrincipal.getCaminhoStorage()), arquivoMergido);

		} catch (FileNotFoundException e) {
			httpGoApi.geraLog("PautaReuniaoSiacolService || mergePautaEPublica", StringUtil.convertObjectToJson(documento), e);
		} catch (IOException e) {
			httpGoApi.geraLog("PautaReuniaoSiacolService || mergePautaEPublica", StringUtil.convertObjectToJson(documento), e);
		}

		return arquivoConverter.toDto(arquivoPrincipal);
	}

	public List<ArquivoDto> getAnexosByItem(Long idItem) {
		return arquivoConverter.toListDto(anexoItemDao.getAnexosByItem(idItem));
	}

	public void concedeVista(List<ItemPautaDto> listItens, UserFrontDto usuario) {

		for (ItemPautaDto dto : listItens) {
			atualizaItem(dto);
	
			auditaReuniaoFactory.auditaItemReuniao(dto, usuario);
			
			GenericSiacolDto genericDto = new GenericSiacolDto();
			List<Long> lista = new ArrayList<Long>();
			lista.add(dto.getIdProtocolo());
			genericDto.setListaId(lista);
			
			genericDto.setStatus(StatusProtocoloSiacol.ANALISE_VISTAS);
			genericDto.setIdResponsavelNovo(dto.getIdPessoaVista());
			IInteressado interessado = interessadoDao.buscaInteressadoBy(dto.getIdPessoaVista());
			genericDto.setNomeResponsavelNovo(interessado.getNome());
			
			protocoloSiacolService.distribuiProtocolo(genericDto, usuario);
		}
	}

	public ItemPautaDto getItemBy(Long id) {

		return pautaReuniaoSiacolConverter.toItemPautaDto(rlDocumentoProtocoloDao.getBy(id));

	}

	public List<ItemPautaDto> acrescentaItensEmUmaPauta(List<ItemPautaDto> listItem, UserFrontDto usuario) {

		listItem.forEach(itemDto -> {
			itemDto.setItem(getUltimoItem(itemDto.getIdDocumento()));
			salvaItem(itemDto, usuario);
		});

		return listItem;
	}

	public String getUltimoItem(Long idDocumento) {
		return rlDocumentoProtocoloDao.getUltimoItem(idDocumento);
	}

	public List<ItemPautaDto> buscaRevisaoDecisao(ConsultaProtocoloDto dto) {
		
		List<RlDocumentoProtocoloSiacol> listModel = new ArrayList<RlDocumentoProtocoloSiacol>();
		
		if (dto.getStatus().get(0).getTipo().equals("REVISAO_ITENS_PROTOCOLADO")) {
			listModel.addAll(rlDocumentoProtocoloDao.buscaRevisaoDecisaoItens(dto));
		}
		List<Long> idsProtocoloRevisao = rlDocumentoProtocoloDao.buscaRevisaoDecisaoProtocolos(dto);
		for (Long idProtocolo : idsProtocoloRevisao) {
			listModel.add(rlDocumentoProtocoloDao.getUltimoItemByIdProtocolo(idProtocolo));
		}
		
//		listModel.addAll(rlDocumentoProtocoloDao.buscaRevisaoDecisaoProtocolos(dto));		
		
		return pautaReuniaoSiacolConverter.toListItemDtoComProtocolo(listModel);
	}

	public void excluirItensPauta(Long idPauta) {
		List<RlDocumentoProtocoloSiacol> listItens = new ArrayList<RlDocumentoProtocoloSiacol>();
			
		listItens = rlDocumentoProtocoloDao.getItensByIdDocumento(idPauta);
		listItens.forEach(item -> {
			anexoItemDao.deletaAnexoItem(item.getId());
		});
		rlDocumentoProtocoloDao.excluirItensPauta(idPauta);
		
		
	}

	public List<ItemPautaDto> buscaItensParaAssinar(Long idDepartamento) {
		
		//List<RlDocumentoProtocoloSiacol> listModel = new ArrayList<RlDocumentoProtocoloSiacol>();
		
		//listModel.addAll();
		//listModel.addAll(rlDocumentoProtocoloDao.buscaProtocolosParaAssinar(dto));
		
		return pautaReuniaoSiacolConverter.toListItemDtoComProtocolo(rlDocumentoProtocoloDao.buscaItensParaAssinar(idDepartamento));
	}

	public List<ItemPautaDto> buscaItensReuniao(ReuniaoSiacolDto dto) {
		return pautaReuniaoSiacolConverter.toListItemDtoComProtocolo(rlDocumentoProtocoloDao.buscaItensReuniao(dto));
	}

	public boolean observacaoCoordenador(ItemPautaDto dto) {
		return rlDocumentoProtocoloDao.observacaoCoordenador(rlDocumentoProtocoloDao.getUltimoItemByProtocolo(dto), dto.getObsCoordenador());
	}
	
	public RlDocumentoProtocoloSiacol getItemByPautaProtocolo(Long idProtocolo, Long idPauta) {

		return rlDocumentoProtocoloDao.getItemByPautaProtocolo(idProtocolo, idPauta);

	}

	public ProtocoloSiacolDto validaInclusaoProtocoloEmergencialNaExtraPauta(Long numeroProtocolo) {
		// que esse item é um protocolo de urgência, sugiro a criação da coluna	URGENCIA
		
		ProtocoloSiacolDto protocoloDto = protocoloSiacolConverter.toDto(protocoloSiacolDao.getProtocoloBy(numeroProtocolo));
		
		if (protocoloDto != null) {
			if(!br.org.crea.commons.util.ListUtils.verificaSeHaElementoComum(Arrays.asList(protocoloDto.getStatusDto().getId()), 
		    		Arrays.asList(StatusProtocoloSiacol.A_PAUTAR.getId(), StatusProtocoloSiacol.A_PAUTAR_AD_REFERENDUM.getId(), StatusProtocoloSiacol.A_PAUTAR_DESTAQUE.getId(), StatusProtocoloSiacol.A_PAUTAR_PRESENCIAL.getId(), StatusProtocoloSiacol.A_PAUTAR_PROVISORIO.getId(), StatusProtocoloSiacol.A_PAUTAR_SEM_QUORUM.getId(), StatusProtocoloSiacol.A_PAUTAR_VISTAS.getId(),
		    				StatusProtocoloSiacol.PAUTADO.getId(), StatusProtocoloSiacol.PAUTADO_AD_REFERENDUM.getId(), StatusProtocoloSiacol.PAUTADO_DESTAQUE.getId(), StatusProtocoloSiacol.PAUTADO_PROVISORIO.getId(), StatusProtocoloSiacol.PAUTADO_VISTAS.getId()))) {
				return protocoloDto;
			}
			
		}
		return null;
	}

	public RlDocumentoProtocoloSiacol retirarItem(Long numeroProtocolo) {
		
		StatusItemPauta statusItem = new StatusItemPauta();
		statusItem.setId(new Long(7));
		statusItem.setNome("PROIBIDO PARA VOTAÇÃO");
		RlDocumentoProtocoloSiacol item = rlDocumentoProtocoloDao.retirarItem(numeroProtocolo);
		
		if (item != null) {
			item.setStatus(statusItem);		
			return rlDocumentoProtocoloDao.update(item);
		}
		
		return null;
		
		
	}

	public boolean validaSolicitacaoDeVistas(List<ItemPautaDto> listItens) {
		
		boolean valida = false;
		
		for (ItemPautaDto r : listItens) {
			RlDocumentoProtocoloSiacol itemExistente = rlDocumentoProtocoloDao.getItemDocumentoNumeroItem(r);
			if(itemExistente.isSolicitacaoVista()) {
				valida = true;
				break;
			}
		}

		return valida;
	}

}
