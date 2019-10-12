package br.org.crea.siacol.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import br.org.crea.commons.dao.cadastro.pessoa.InteressadoDao;
import br.org.crea.commons.dao.siacol.PersonalidadeSiacolDao;
import br.org.crea.commons.dao.siacol.ReuniaoSiacolDao;
import br.org.crea.commons.dao.siacol.RlDocumentoProtocoloSiacolDao;
import br.org.crea.commons.factory.siacol.AuditaSiacolReuniaoFactory;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.models.corporativo.dtos.PessoaDto;
import br.org.crea.commons.models.corporativo.pessoa.IInteressado;
import br.org.crea.commons.models.siacol.ReuniaoSiacol;
import br.org.crea.commons.models.siacol.RlDocumentoProtocoloSiacol;
import br.org.crea.commons.models.siacol.VotoReuniaoSiacol;
import br.org.crea.commons.models.siacol.dtos.ApuracaoReuniaoSiacolDto;
import br.org.crea.commons.models.siacol.dtos.AtualizaPainelPlenariaSiacolDto;
import br.org.crea.commons.models.siacol.dtos.EnqueteDto;
import br.org.crea.commons.models.siacol.dtos.ItemPautaDto;
import br.org.crea.commons.models.siacol.dtos.ParticipanteReuniaoSiacolDto;
import br.org.crea.commons.models.siacol.dtos.PesquisaItemPautaSiacolDto;
import br.org.crea.commons.models.siacol.dtos.PesquisaVotoReuniaoDto;
import br.org.crea.commons.models.siacol.dtos.ProtocoloSiacolDto;
import br.org.crea.commons.models.siacol.dtos.RespostaEnqueteDto;
import br.org.crea.commons.models.siacol.dtos.ReuniaoSiacolDto;
import br.org.crea.commons.models.siacol.dtos.VotoOfflineSiacolDto;
import br.org.crea.commons.models.siacol.dtos.VotoReuniaoSiacolDto;
import br.org.crea.commons.models.siacol.enuns.ResultadoVotacaoSiacolEnum;
import br.org.crea.commons.models.siacol.enuns.TipoRelatorioSiacolEnum;
import br.org.crea.commons.models.siacol.enuns.VotoReuniaoEnum;
import br.org.crea.commons.service.HttpFirebaseApi;
import br.org.crea.siacol.converter.PautaReuniaoSiacolConverter;
import br.org.crea.siacol.converter.ProtocoloSiacolConverter;
import br.org.crea.siacol.converter.VotoReuniaoSiacolConverter;
import br.org.crea.siacol.dao.PresencaReuniaoSiacolDao;
import br.org.crea.siacol.dao.VotoReuniaoSiacolDao;
import br.org.crea.siacol.relatorio.GeraRelatorioSiacol;

public class VotoReuniaoSiacolService {

	@Inject
	private VotoReuniaoSiacolConverter converter;

	@Inject
	private VotoReuniaoSiacolDao dao;
	
	@Inject 
	private PersonalidadeSiacolDao personalidadeDao;
	
	@Inject 
	private InteressadoDao interessadoDao;

	@Inject
	private RlDocumentoProtocoloSiacolDao rlDocumentoProtocoloDao;
	
	@Inject
	private PautaReuniaoSiacolService pautaReuniaoSiacolService;
	
	@Inject
	private PautaReuniaoSiacolConverter pautaConverter;

	@Inject
	private ProtocoloSiacolConverter protocoloConverter;

	@Inject
	private ReuniaoSiacolDao reuniaoDao;
	
	@Inject
	private PresencaReuniaoSiacolDao presencaDao;

	@Inject
	private HttpFirebaseApi firebaseApi;
	
	@Inject
	private GeraRelatorioSiacol geraRelatorio;
	
	@Inject
	private AuditaSiacolReuniaoFactory auditaReuniaoFactory;
	
	public VotoReuniaoSiacolDto salvar(VotoReuniaoSiacolDto dto) {

		ReuniaoSiacol reuniao = new ReuniaoSiacol();
		reuniao = reuniaoDao.getBy(dto.getReuniao().getId());

		if (reuniao.estaAbertaParaVotacao()) {
			VotoReuniaoSiacol novoVoto = new VotoReuniaoSiacol();

			VotoReuniaoSiacol voto = new VotoReuniaoSiacol();
			voto = dao.getVotoByIdPessoaByIdProtocolo(dto.getPessoa().getId(), dto.getProtocolo().getId());

			if (voto != null) {
				dto.setId(voto.getId());
				novoVoto = dao.update(converter.toModel(dto));
			} else {
				novoVoto = dao.create(converter.toModel(dto));
			}
			return converter.toDto(dao.getBy(novoVoto.getId()));

		} else {
			return null;
		}

	}

	public VotoReuniaoSiacolDto getByIdVoto(Long id) {
		return converter.toDto(dao.getBy(id));
	}

	public List<VotoReuniaoSiacolDto> getVotosByIdProtocolo(Long id) {
		return converter.toListDto(dao.getByIdProtocolo(id));
	}

	public List<ProtocoloSiacolDto> contagem(PesquisaVotoReuniaoDto pesquisa) {

		List<ProtocoloSiacolDto> resultContagem = new ArrayList<ProtocoloSiacolDto>();
		resultContagem = protocoloConverter.toListDto(rlDocumentoProtocoloDao.getListProtocolos(pesquisa.getIdPauta()));
		ReuniaoSiacol reuniao = reuniaoDao.getBy(pesquisa.getIdReuniao());
		int quorum = Integer.parseInt(reuniao.getQuorum().toString());
		

		for (ProtocoloSiacolDto p : resultContagem) {
			
			RlDocumentoProtocoloSiacol itemModel = pautaReuniaoSiacolService.getItemByPautaProtocolo(p.getId() ,reuniao.getPauta().getId());

			p.setQuantidadeVotosSim(dao.getQuantidadesVotosPor(p.getId(), pesquisa.getIdReuniao(), VotoReuniaoEnum.S));
			p.setQuantidadeVotosNao(dao.getQuantidadesVotosPor(p.getId(), pesquisa.getIdReuniao(), VotoReuniaoEnum.N));
			p.setQuantidadeVotosAbstencao(dao.getQuantidadesVotosPor(p.getId(), pesquisa.getIdReuniao(), VotoReuniaoEnum.A));
			p.setQuantidadeVotosDestaque(dao.getQuantidadesVotosPor(p.getId(), pesquisa.getIdReuniao(), VotoReuniaoEnum.D));
			p.setQuorumReuniao(quorum);
			
			itemModel.setTotalVotosAbstencao(new Long(p.getQuantidadeVotosAbstencao()));
			itemModel.setTotalVotosNao(new Long(p.getQuantidadeVotosNao()));
			itemModel.setTotalVotosSim(new Long(p.getQuantidadeVotosSim()));

			if (p.deuEmpate()) {
				if (p.temCoordanadorOuAdjunto()) {
					VotoReuniaoSiacol voto = null;
					voto = dao.getVotoCoordenadorOuAdjunto(p.getId(), pesquisa.getIdReuniao(), p.obtemIdCoordenadorOuAdjunto());
					if (voto != null) {
						p.setVotoCoordenadorOuAdjunto(voto.getVoto());
						itemModel.setIdPessoaMinerva(p.obtemIdCoordenadorOuAdjunto());
					}
				}
			}

			if (p.obteveQuorumMinimo()) {
				if (p.maioriaDosVotosHeSim() || (p.deuEmpate() && p.getVotoCoordenadorOuAdjunto() == VotoReuniaoEnum.S)) {
					itemModel.setResultado(ResultadoVotacaoSiacolEnum.A_FAVOR_DO_RELATOR);
				}else {
					itemModel.setResultado(ResultadoVotacaoSiacolEnum.CONTRA_RELATOR);
				}
				rlDocumentoProtocoloDao.update(itemModel);
			}
			p.calculaGeraDecisao();

		}

		return resultContagem;
	}

	public List<VotoReuniaoSiacolDto> pesquisa(PesquisaVotoReuniaoDto pesquisa) {
		return converter.toListDto(dao.pesquisa(pesquisa));
	}

	public boolean protocoloFoiDestacado(PesquisaVotoReuniaoDto pesquisa) {

		for (VotoReuniaoSiacolDto voto : pesquisa(pesquisa)) {

			if (voto.isDestaque()) {
				return true;
			}
		}

		return false;
	}

	public VotoReuniaoSiacolDto salvarVotoPresencial(VotoReuniaoSiacolDto dto) {

		List<RlDocumentoProtocoloSiacol> itens = dao.getItensEmVotacao(dto.getReuniao().getIdsPautas());
		if (itens != null) {
			for (RlDocumentoProtocoloSiacol item : itens) {

				if (item.temProtocolo()) {
					ProtocoloSiacolDto protocolo = new ProtocoloSiacolDto();
					protocolo.setId(item.getProtocolo().getId());
					dto.setProtocolo(protocolo);
				}

				registraVoto(dto, dto.getPessoa().getId(), item.getId());
				
			}
			apuracao(dto, itens.get(0).isTemEnquete());
			return dto;
		} else {
			return null;
		}

	}

	public VotoOfflineSiacolDto salvarVotoOfflineOuAclamacao(VotoOfflineSiacolDto dto, UserFrontDto usuario) {

		// REGISTRA VOTOS, tentar reaproveitar metodo de registrar voto já existente
		for (ParticipanteReuniaoSiacolDto participanteDto : dto.getListaVotos()) {
			
			if (participanteDto.jaVotou()) {
			
				for (ItemPautaDto item : dto.getListaItens()) {
					VotoReuniaoSiacolDto votoDto = populaVotoReuniaoDto(participanteDto.getIdReuniao(), participanteDto.getId());
					
					votoDto.setVoto(participanteDto.getVoto());
	
					if (item.temProtocolo()) {
						ProtocoloSiacolDto protocolo = new ProtocoloSiacolDto();
						protocolo.setId(item.getIdProtocolo());
						votoDto.setProtocolo(protocolo);
					}
	
					registraVoto(votoDto, participanteDto.getId(), item.getId());
				}
			}
		}
		
		AtualizaPainelPlenariaSiacolDto atualizaDto = new AtualizaPainelPlenariaSiacolDto();
		atualizaDto.setIdsPautas(dto.getIdsPautas());
		atualizaDto.setListItens(dto.getListaItens());
		atualizaDto.setIdReuniao(dto.getReuniao().getId());
		atualizaDto.setIdRlItemPauta(dto.getIdRlItemPauta());
		atualizaVotacao("EMVOTACAO", atualizaDto);
		atualizaVotacao("ENCERRAR", atualizaDto);
			
		for (ItemPautaDto item : dto.getListaItens()) {			
			if (item.temProtocolo()) {
				item.setEventoAuditoria(dto.getEventoAuditoria());
				auditaReuniaoFactory.auditaItemReuniao(item, usuario);
			}
		}
		
		
		VotoReuniaoSiacolDto votoDto = new VotoReuniaoSiacolDto();
		votoDto.setReuniao(dto.getReuniao());
		votoDto.setIdRlItemPauta(dto.getIdRlItemPauta());
		apuracaoSemEnquete(votoDto);
		
		return dto;		
	}

	private void registraVoto(VotoReuniaoSiacolDto dto, Long idPessoa, Long idItem) {
		dto.setIdRlItemPauta(idItem);
		
		VotoReuniaoSiacol voto = new VotoReuniaoSiacol();
		voto = dao.getVotoByIdPessoaByIdItemPauta(idPessoa, idItem);
		
		if (voto != null) {
			dto.setId(voto.getId());
			dao.update(converter.toModel(dto));
		} else {
			dto.setId(null);
			dao.create(converter.toModel(dto));
		}		
	}
	
	private VotoReuniaoSiacolDto populaVotoReuniaoDto(Long idReuniao, Long idParticipante) {
		VotoReuniaoSiacolDto dto = new VotoReuniaoSiacolDto();
		
		ReuniaoSiacolDto reuniao = new ReuniaoSiacolDto();
		reuniao.setId(idReuniao);
		dto.setReuniao(reuniao);
		
		PessoaDto pessoa = new PessoaDto();
		pessoa.setId(idParticipante);
		dto.setPessoa(pessoa);

		return dto;		
	}

	public void apuracao(VotoReuniaoSiacolDto dto, boolean temEnquete) {

		ApuracaoReuniaoSiacolDto apuracao = new ApuracaoReuniaoSiacolDto();

		apuracao.setStatus("");
		apuracao.setQuorum(dto.getReuniao().getQuorum());

		apuracao.setQtdSim(dao.getQuantidadeVotosPresenciaisPor(dto.getReuniao().getId(), dto.getIdRlItemPauta(), VotoReuniaoEnum.S));
		apuracao.setQtdNao(dao.getQuantidadeVotosPresenciaisPor(dto.getReuniao().getId(), dto.getIdRlItemPauta(), VotoReuniaoEnum.N));
		apuracao.setQtdAbstencao(dao.getQuantidadeVotosPresenciaisPor(dto.getReuniao().getId(), dto.getIdRlItemPauta(), VotoReuniaoEnum.A));
		apuracao.setQtdDestaque(dao.getQuantidadeVotosPresenciaisPor(dto.getReuniao().getId(), dto.getIdRlItemPauta(), VotoReuniaoEnum.D));

		calcularPercentualItensVotados(apuracao, dto.getReuniao().getIdsPautas());
		
		// se tem enquete
		if (temEnquete) {
			
			// get item da enquete
			RlDocumentoProtocoloSiacol item = rlDocumentoProtocoloDao.getBy(dto.getIdRlItemPauta());
			
			ItemPautaDto itemDto = new ItemPautaDto();
			
			ObjectMapper mapper = new ObjectMapper();
			
			try {
				
				itemDto.setEnqueteDto(mapper.readValue(item.getEnquete(), EnqueteDto.class));
				
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// percorre respostas e obtem total de votos de cada uma
			itemDto.getEnqueteDto().getRespostas().forEach(resposta -> {
				resposta.setTotalVotos(dao.getTotalDeVotosEnquetePorId(dto.getIdRlItemPauta(), resposta.getId()));
			});
			
			// obter mais votado
			RespostaEnqueteDto maisVotado = itemDto.getEnqueteDto().getRespostas().stream()
                    .max((fc1, fc2) -> fc1.getTotalVotos() - fc2.getTotalVotos())
                    .get();
			
			// marcar na lista o mais votado
			itemDto.getEnqueteDto().getRespostas().forEach(resposta -> {
				if (resposta.getTotalVotos() == maisVotado.getTotalVotos()) {
					resposta.setMaisVotado(true);
				}
			});
			
			apuracao.setEnquete(itemDto.getEnqueteDto().getRespostas());
		}
		

		firebaseApi.salvaApuracao(apuracao, dto.getReuniao().getId());
	}
	
	private int calculaRazaoItensVotadosPorItensDaReuniao(List<Long> idsPautas) {
		ApuracaoReuniaoSiacolDto apuracao = new ApuracaoReuniaoSiacolDto();
		
		apuracao.setQtdItensReuniao(dao.getQuantidadeItensVotaveisReuniao(idsPautas));
		apuracao.setQtdItensVotados(dao.getQuantidadeItensVotados(idsPautas));
		return apuracao.calculaRazaolItensVotadosPorItensDaReuniao();
	}

	private ApuracaoReuniaoSiacolDto calcularPercentualItensVotados(ApuracaoReuniaoSiacolDto apuracao, List<Long> idsPautas) {
		apuracao.setQtdItensReuniao(dao.getQuantidadeItensVotaveisReuniao(idsPautas));
		apuracao.setQtdItensVotados(dao.getQuantidadeItensVotados(idsPautas));
		apuracao.setPercentualItensVotados(apuracao.calculaPercentualItensVotados());
		
		return apuracao;		
	}

	public List<ItemPautaDto> atualizaVotacao(String tipo, AtualizaPainelPlenariaSiacolDto itens) {

		List<ItemPautaDto> itensPainel = new ArrayList<ItemPautaDto>();

		if (tipo.equals("EMVOTACAO")) {
			rlDocumentoProtocoloDao.retiraDeVotacao(itens.getIdsPautas());
			for (ItemPautaDto dto : itens.getListItens()) {
				rlDocumentoProtocoloDao.colocaEmVotacao(dto.getId());
			}
		}

		itens.setIdRlItemPauta(dao.getIdPrimeiroItemEmVotacao(itens.getIdsPautas()));

		if (tipo.equals("CANCELAR")) {
			dao.apagarVotos(itens.getIdsPautas());
			rlDocumentoProtocoloDao.retiraDeVotacao(itens.getIdsPautas());
		}

		if (tipo.equals("ENCERRAR")) {
			ApuracaoReuniaoSiacolDto apuracao = apuraQuantidadeDeVotos(itens);
			
			apuracao.setVotadoAntesDaApuracao(calculaRazaoItensVotadosPorItensDaReuniao(itens.getIdsPautas()));
			
			atualizarResultado(itens.getIdsPautas(), apuracao);
			
			rlDocumentoProtocoloDao.retiraDeVotacaoEAtualizaParaVotado(itens.getIdsPautas(), apuracao);
			rlDocumentoProtocoloDao.retiraDeVotacaoEAtualizaParaVotadoHomologacaoProfissional(itens.getIdsPautas(), apuracao);
			
			apuracao.setVotadoDepoisDaApuracao(calculaRazaoItensVotadosPorItensDaReuniao(itens.getIdsPautas()));
			
			if (apuracao.atingiuOitentaPorcento()) {
				presencaDao.registraPresentesOitentaPorcentoVotado(itens.getIdReuniao());
				ReuniaoSiacolDto reuniaoDto = new ReuniaoSiacolDto();
				reuniaoDto.setId(itens.getIdReuniao());
				geraRelatorio.gera(reuniaoDto, TipoRelatorioSiacolEnum.RELATORIO_80_PORCENTO);
			}
			
			apuracaoEncerramento(populaVotoReuniaoSiacolDto(itens), itens.isTemEnquete(), apuracao);
		} else {
			apuracaoSemEnquete(populaVotoReuniaoSiacolDto(itens));
		}
		

		return itensPainel;
	}

	private void apuracaoEncerramento(VotoReuniaoSiacolDto dto, boolean temEnquete, ApuracaoReuniaoSiacolDto apuracao) {
		
		apuracao.setStatus("");
		apuracao.setQuorum(dto.getReuniao().getQuorum());

		apuracao.setQtdDestaque(dao.getQuantidadeVotosPresenciaisPor(dto.getReuniao().getId(), dto.getIdRlItemPauta(), VotoReuniaoEnum.D));

		calcularPercentualItensVotados(apuracao, dto.getReuniao().getIdsPautas());
		
		if (temEnquete) {
			RlDocumentoProtocoloSiacol item = rlDocumentoProtocoloDao.getBy(dto.getIdRlItemPauta());
			
			ItemPautaDto itemDto = new ItemPautaDto();
			
			ObjectMapper mapper = new ObjectMapper();
			
			try {
				itemDto.setId(item.getId());
				itemDto.setEnqueteDto(mapper.readValue(item.getEnquete(), EnqueteDto.class));
				
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			itemDto = verificaESalvaResultadoEnquete(itemDto);
			
			apuracao.setEnquete(itemDto.getEnqueteDto().getRespostas());
		}
		

		firebaseApi.salvaApuracao(apuracao, dto.getReuniao().getId());
	}

	private ItemPautaDto verificaESalvaResultadoEnquete(ItemPautaDto itemDto) {
		itemDto = defineItensMaisVotados(itemDto);
		for (RespostaEnqueteDto resposta : itemDto.getEnqueteDto().getRespostas()) {
			if (resposta.getMaisVotado()) {
				itemDto.setResultadoEnquete(resposta.getResposta());
				dao.atualizaResultadoEnquete(itemDto);
			}
		}
		return itemDto;
	}

	private ApuracaoReuniaoSiacolDto apuraQuantidadeDeVotos(AtualizaPainelPlenariaSiacolDto itens) {
		ApuracaoReuniaoSiacolDto apuracao = new ApuracaoReuniaoSiacolDto();
		
		apuracao.setQtdSim(dao.getQuantidadeVotosPresenciaisPor(itens.getIdReuniao(), itens.getIdRlItemPauta(), VotoReuniaoEnum.S));
		apuracao.setQtdNao(dao.getQuantidadeVotosPresenciaisPor(itens.getIdReuniao(), itens.getIdRlItemPauta(), VotoReuniaoEnum.N));
		apuracao.setQtdAbstencao(dao.getQuantidadeVotosPresenciaisPor(itens.getIdReuniao(), itens.getIdRlItemPauta(), VotoReuniaoEnum.A));
		
		return apuracao;
	}

	private void apuracaoSemEnquete(VotoReuniaoSiacolDto dto) {
		ApuracaoReuniaoSiacolDto apuracao = new ApuracaoReuniaoSiacolDto();

		apuracao.setStatus("");
		apuracao.setQuorum(reuniaoDao.getQuorumPor(dto.getReuniao().getId()));

		apuracao.setQtdSim(dao.getQuantidadeVotosPresenciaisPor(dto.getReuniao().getId(), dto.getIdRlItemPauta(), VotoReuniaoEnum.S));
		apuracao.setQtdNao(dao.getQuantidadeVotosPresenciaisPor(dto.getReuniao().getId(), dto.getIdRlItemPauta(), VotoReuniaoEnum.N));
		apuracao.setQtdAbstencao(dao.getQuantidadeVotosPresenciaisPor(dto.getReuniao().getId(), dto.getIdRlItemPauta(), VotoReuniaoEnum.A));
		apuracao.setQtdDestaque(dao.getQuantidadeVotosPresenciaisPor(dto.getReuniao().getId(), dto.getIdRlItemPauta(), VotoReuniaoEnum.D));

		calcularPercentualItensVotados(apuracao, dto.getReuniao().getIdsPautas());
		

		firebaseApi.salvaApuracao(apuracao, dto.getReuniao().getId());
	}

	private void atualizarResultado(List<Long> idsPautas, ApuracaoReuniaoSiacolDto apuracao) {
		
		PesquisaItemPautaSiacolDto pesquisa = new PesquisaItemPautaSiacolDto();
		pesquisa.setSomenteProtocolos(true);
		pesquisa.setIdsPautas(idsPautas);
		atualizaResultadoDeItensComProtocoloSemVistas(pesquisa, apuracao.getQtdSim(), apuracao.getQtdNao());
		atualizaResultadoDeItensComProtocoloComVistas(pesquisa, apuracao.getQtdSim(), apuracao.getQtdNao());
		rlDocumentoProtocoloDao.atualizaResultadoDeItensComProtocoloHomologacao();
		rlDocumentoProtocoloDao.atualizaResultadoDeItensSemProtocolo();
	}

	private void atualizaResultadoDeItensComProtocoloSemVistas(PesquisaItemPautaSiacolDto pesquisa, int qtdSim, int qtdNao) {
		pesquisa.setTemVistasConcedida(false);
		pesquisa.setEmVotacao(true);
		pesquisa.setStatus(new Long(0)); // indiferente
		
		List<RlDocumentoProtocoloSiacol> listaItens = rlDocumentoProtocoloDao.pesquisaEncerramento(pesquisa);
		
		for (RlDocumentoProtocoloSiacol item : listaItens) {
			if (qtdSim > qtdNao) {
				item.setResultado(ResultadoVotacaoSiacolEnum.A_FAVOR_DO_RELATOR);
			} else {
				item.setResultado(ResultadoVotacaoSiacolEnum.CONTRA_RELATOR);
			}
			rlDocumentoProtocoloDao.update(item);
		}		
	}

	private void atualizaResultadoDeItensComProtocoloComVistas(PesquisaItemPautaSiacolDto pesquisa, int qtdSim, int qtdNao) {
		pesquisa.setTemVistasConcedida(true);
		pesquisa.setEmVotacao(true);
		pesquisa.setStatus(new Long(0)); // indiferente
		
		List<RlDocumentoProtocoloSiacol> listaItens = rlDocumentoProtocoloDao.pesquisaEncerramento(pesquisa);
		
		for (RlDocumentoProtocoloSiacol item : listaItens) {
			if (qtdSim > qtdNao) {
				item.setResultado(ResultadoVotacaoSiacolEnum.A_FAVOR_DE_VISTAS);
			} else {
				item.setResultado(ResultadoVotacaoSiacolEnum.A_FAVOR_DO_RELATOR);
			}
			rlDocumentoProtocoloDao.update(item);
		}	
	}
	
	private VotoReuniaoSiacolDto populaVotoReuniaoSiacolDto(AtualizaPainelPlenariaSiacolDto itens) {
		VotoReuniaoSiacolDto dto = new VotoReuniaoSiacolDto();

		ReuniaoSiacolDto reuniao = new ReuniaoSiacolDto();
		reuniao.setId(itens.getIdReuniao());
		reuniao.setQuorum(itens.getQuorum());
		reuniao.setIdsPautas(itens.getIdsPautas());
		dto.setReuniao(reuniao);

		dto.setIdRlItemPauta(itens.getIdRlItemPauta());

		return dto;
	}

	public boolean houveEmpate(ReuniaoSiacolDto dto) {
		
		boolean houveEmpate = true;
		
		List<RlDocumentoProtocoloSiacol> listaItens = dao.getItensEmVotacao(dto.getIdsPautas());
		
		if (!listaItens.get(0).isTemEnquete()) {
		
			int qtdVotosSim = dao.getQuantidadeVotosPresenciaisPor(dto.getId(), listaItens.get(0).getId(), VotoReuniaoEnum.S);
			int qtdVotosNao = dao.getQuantidadeVotosPresenciaisPor(dto.getId(), listaItens.get(0).getId(), VotoReuniaoEnum.N);
			
			if (qtdVotosSim != qtdVotosNao) {
				return !houveEmpate;
			} else if (dto.ehPlenaria()) {
				return houveEmpate;
			} else {
				return verificaERegistraVotoDeMinerva(dto.getId(), listaItens);
			}
		}
		
		if (listaItens.get(0).isTemEnquete()) {
			
			ItemPautaDto itemDto = pautaConverter.toItemPautaDto(listaItens.get(0));
			
			itemDto = defineItensMaisVotados(itemDto);
			
			if (itemDto.getEnqueteDto().somenteUmaRespostaEhAMaisVotada()) {
				return !houveEmpate;
			} else if (dto.ehPlenaria()) {
				return houveEmpate;
			} else {
				return verificaERegistraVotoDeMinervaEnquete(dto.getId(), itemDto, listaItens);
			}
		}
		return false;
	}

	private boolean verificaERegistraVotoDeMinervaEnquete(Long idReuniao, ItemPautaDto itemDto, List<RlDocumentoProtocoloSiacol> listaItens) {
		boolean houveEmpate = true;
		
		Long idParticipanteComVotoDeMinerva = presencaDao.getParticipanteComVotoDeMinerva(idReuniao);
		if (idParticipanteComVotoDeMinerva != null) {
			
			VotoReuniaoSiacol voto = dao.getVotoByIdPessoaByIdItemPauta(idParticipanteComVotoDeMinerva, itemDto.getId());
			if (voto != null) {
				
				for (RespostaEnqueteDto resposta : itemDto.getEnqueteDto().getRespostas()) {
					if (resposta.getMaisVotado()) {
						// voto de minerva está entre opções
						if (resposta.getId().equals(voto.getIdRespostaEnquete().toString())) {
							dao.registraVotoMinervaNoItem(listaItens.get(0), idParticipanteComVotoDeMinerva);
							return !houveEmpate;
						}
					}
				}
				
			}
		}
		
		return houveEmpate;	
	}

	private boolean verificaERegistraVotoDeMinerva(Long idReuniao, List<RlDocumentoProtocoloSiacol> listaItens) {
		boolean houveEmpate = true;
		
		Long idParticipanteComVotoDeMinerva = presencaDao.getParticipanteComVotoDeMinerva(idReuniao);
		if (idParticipanteComVotoDeMinerva != null) {
			
			VotoReuniaoSiacol voto = dao.getVotoByIdPessoaByIdItemPauta(idParticipanteComVotoDeMinerva, listaItens.get(0).getId());
			if (voto != null) {
				
				if (!voto.ehAbstencao()) {
					listaItens.forEach(item -> {
						dao.registraVotoMinervaNoItem(item, idParticipanteComVotoDeMinerva);
					});
					return !houveEmpate;
				}
			}
		}
		
		return houveEmpate;		
	}

	public boolean jaVotou(Long idReuniao, Long idParticipante) {
		return dao.jaVotou(idReuniao, idParticipante);
	}

	public int getTotalDeVotos(Long idReuniao, List<Long> idsPautas) {
		return dao.getQuantidadesTotalDeVotosPor(idReuniao, dao.getIdPrimeiroItemEmVotacao(idsPautas));
	}

	public boolean houveVotosSuficientes(ReuniaoSiacolDto dto) {
		int maioria = (dto.getQuorum() / 2) + 1;
		int totalVotos = getTotalDeVotos(dto.getId(), dto.getIdsPautas());
		
		return totalVotos >= maioria;		
	}

	public List<ParticipanteReuniaoSiacolDto> getConselheirosPorIdItemPautaVotado(Long idItemPauta) {
		List<ParticipanteReuniaoSiacolDto> listParticipanteDto = new ArrayList<ParticipanteReuniaoSiacolDto>();
		List<VotoReuniaoSiacol> listVotoReuniaoSiacol = dao.getVotosPorIdItemPauta(idItemPauta);
		
		listVotoReuniaoSiacol.forEach(voto -> {
			listParticipanteDto.add(populaDtoParticipante(voto.getPessoa().getId()));
		});
		
		return listParticipanteDto;
	}
	
	private ParticipanteReuniaoSiacolDto populaDtoParticipante(Long idConselheiro) {
		ParticipanteReuniaoSiacolDto participante = new ParticipanteReuniaoSiacolDto();
		IInteressado interessado = interessadoDao.buscaInteressadoBy(idConselheiro);

		if (interessado != null) {
			participante.setId(interessado.getId());
			participante.setNome(interessado.getNome());
			participante.setBase64(interessado.getFotoBase64());
		}
	
		participante.setNomeGuerra(personalidadeDao.getNomeDeGuerraPorId(idConselheiro));
		return participante;
	}

	public ItemPautaDto getRespostasEmpatadas(ItemPautaDto itemDto) {
		return defineItensMaisVotados(itemDto);
	}

	private ItemPautaDto contabilizaVotosDeCadaRespostaDaEnquete(ItemPautaDto itemDto) {
		for (RespostaEnqueteDto resposta : itemDto.getEnqueteDto().getRespostas()) {
			resposta.setTotalVotos(dao.getTotalDeVotosEnquetePorId(itemDto.getId(), resposta.getId()));
		}
		return itemDto;
	}

	private int getQuantidadeDeVotosDaRespostaMaisVotada(ItemPautaDto itemDto) {
		RespostaEnqueteDto maisVotado = itemDto.getEnqueteDto().getRespostas().stream()
                .max((fc1, fc2) -> fc1.getTotalVotos() - fc2.getTotalVotos())
                .get();
		return maisVotado.getTotalVotos();
	}

	private ItemPautaDto defineItensMaisVotados(ItemPautaDto itemDto) {
		itemDto = contabilizaVotosDeCadaRespostaDaEnquete(itemDto);
		int qtdVotosMaisVotado = getQuantidadeDeVotosDaRespostaMaisVotada(itemDto);
		itemDto.getEnqueteDto().getRespostas().forEach(resposta -> {
			if (resposta.getTotalVotos() == qtdVotosMaisVotado) {
				resposta.setMaisVotado(true);
			}
		});
		return itemDto;
	}

}

