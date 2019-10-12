package br.org.crea.restapi.art.contrato;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.dao.art.ArtCUBDao;
import br.org.crea.commons.dao.art.ArtDao;
import br.org.crea.commons.dao.art.ArtQuantificacaoDao;
import br.org.crea.commons.dao.art.ContratoArtDao;
import br.org.crea.commons.dao.financeiro.FinMoedaDao;
import br.org.crea.commons.models.art.Art;
import br.org.crea.commons.models.art.ArtCUB;
import br.org.crea.commons.models.art.ArtTipoTaxa;
import br.org.crea.commons.models.art.ContratoArt;
import br.org.crea.commons.models.art.enuns.TipoAtividadeContratoArt;
import br.org.crea.commons.models.art.enuns.TipoComplementoContratoArt;
import br.org.crea.commons.models.art.enuns.TipoEspecificacaoContratoArt;
import br.org.crea.commons.models.art.enuns.TipoTaxaArtEnum;
import br.org.crea.commons.models.commons.Endereco;
import br.org.crea.commons.models.commons.dtos.DomainGenericDto;
import br.org.crea.commons.models.financeiro.FinMoeda;
import br.org.crea.commons.util.DateUtils;
import br.org.crea.commons.util.EnderecoUtil;
import br.org.crea.commons.util.ListUtils;

public class CalculaValorArtMultiplaTest {

	ContratoArtDao dao;
	ArtQuantificacaoDao artQuantificacaoDao;
	ArtCUBDao artCUBDao;
	FinMoedaDao finMoedaDao;
	ArtDao artDao;
	private static EntityManager em = null;
	
	ContratoArt contratoArtPrincipal;
	
	ContratoArt contrato;
	
	@Before
	public void inicio() {
		dao = new ContratoArtDao();
		artQuantificacaoDao = new ArtQuantificacaoDao();
		artDao = new ArtDao();
		artCUBDao = new ArtCUBDao();
		finMoedaDao = new FinMoedaDao();
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dsCreaTest");
		em = factory.createEntityManager();
		dao.setEntityManager(em);
		artDao.setEntityManager(em);
		artCUBDao.setEntityManager(em);
		finMoedaDao.setEntityManager(em);
		artQuantificacaoDao.setEntityManager(em);
		contrato = new ContratoArt();
		contratoArtPrincipal = new ContratoArt();
	}
	
	@After
	public void fim() {
		em.close();
	}
	
	@Test
	public void deveAtualizarValorDaArtEDoContrato() {
		dao.createTransaction();
		DomainGenericDto dto = new DomainGenericDto();
		dto.setNumero("2020180000517-1");
		assertTrue(this.multipla(dto));
		dao.commitTransaction();
	}
	
	public boolean multipla(DomainGenericDto dto) {

		contrato = dao.getBy(dto.getNumero());
		Date database = new Date();

		try {

			if (contrato.hePrimeiroContrato()) {
				contrato.getArt().setTaxaMinima(defineSeVaiUtilizarTaxaMinima());
				contrato.getArt().setForneceConcreto(verificaSeForneceConcreto()); 				//verifica se fornece concreto - linha 1191
			}
			Art art = artDao.getArtPorId(contrato.getArt().getNumero());

			contrato.getArt().setMultiplaMensal(verificaSeEhMultiplaMensal(art)); 			//verifica se é multiplaMensal - linha 1190
			
			if (contrato.artPossuiArtPrincipal()) {
				// ATENDE a este caso: ART MULTIPLA VINCULADA A ART GLOBAL
				contratoArtPrincipal = dao.getPrimeiroContratoPor(contrato.getArt().getNumeroARTPrincipal());
				if (heTaxaMinimaEvinculadaHeDeObraEServico()) {
					if (todosOsValoresDeContratosSaoInferioresAMil()) {
						// cobrar pela tabela B linha 451
											
						BigDecimal valorTotal = artDao.getValorTotalDosContratosPelaTaxaMultipla(contrato.getArt().getNumero(), TipoTaxaArtEnum.MULTIPLA, database);
						BigDecimal valorMinimo = artDao.getTaxaPorDataBaseETipoTaxa(database, TipoTaxaArtEnum.TAXA_ESPECIAL_1);
						
						if(valorTotal.compareTo(valorMinimo) < 0) {
							valorTotal = valorMinimo;
						}
						//Verificar se é necessário preencher os valores a receber de cada contrato
						atualizaValorETipoTaxaSomenteArt(valorTotal, TipoTaxaArtEnum.MULTIPLA.getId());
						
					} else {
						defineValorAReceberPelaTaxaMinima(TipoTaxaArtEnum.TAXA_ESPECIAL_1); // cobrar pela tabela A
					}
					dao.atualizaFinalizado(dto.getNumero(), true);
					return true;
				}
				// Atende a este caso: ART MULTIPLA VINCULADA A CARGO FUNCAO
				if (heTaxaMinimaEvinculadaHeDeDesempenhoDeCargoEfuncao()) {
					if (verificaAtividadeMultiplaVinculada()) {
						defineValorAReceberPelaTaxaMinima(TipoTaxaArtEnum.TAXA_ESPECIAL_1); // cobrar pela tabela A
					}
					
					else { // cobrar pelo valor faixa 7 para cada contrato - linha 475

						BigDecimal valorTotal = artDao.getValorTotalDosContratosPelaFaixaETaxaMultipla(contrato.getArt().getNumero(), TipoTaxaArtEnum.MULTIPLA, database, 7L);
						BigDecimal valorMinimo = artDao.getTaxaPorDataBaseETipoTaxa(database, TipoTaxaArtEnum.TAXA_ESPECIAL_1);
						
						if(valorTotal.compareTo(valorMinimo) < 0) {
							valorTotal = valorMinimo;
						}
						//Verificar se é necessário preencher os valores a receber de cada contrato
						atualizaValorETipoTaxaSomenteArt(valorTotal, TipoTaxaArtEnum.MULTIPLA.getId());

					}
					dao.atualizaFinalizado(dto.getNumero(), true);
					return true;
				}

			}

			if (heMultiplaMensal() && heDeFornecimentoDeConcreto()) {
					// cobrar o valor da ART de fornecimento de concreto
					BigDecimal valorCalculadoConcreteira = artDao.getTaxaPorDataBaseETipoTaxa(database, TipoTaxaArtEnum.ART_CONCRETEIRA);
					
					//this.obterTaxa(art, valorART, query, dataBase, new Long(900)); - linha 495
					if (temMaisDeDezContratos()) {
						// do 11º em diante incrementar o valor seguindo outro valor
						//TaxaART taxaART = obterTaxaMultipla(contratoART.getValorContrato(), dataBase, new Long(5)); - linha 511
						//art.setValorReceber(art.getValorReceber().add(taxaART.getValor() )); 
						// CONFIRMAR ESTE CALCULO  - linha 512
						BigDecimal valorSoma = artDao.getValorTotalDosContratosExcetoDezPrimeiros(contrato.getArt().getNumero(), database);
						
						BigDecimal valorArt = valorCalculadoConcreteira.add(valorSoma);
						
						// confirmar qual tipo taxa será gravado
						atualizaValorETipoTaxaSomenteArt(valorArt, TipoTaxaArtEnum.VALOR_CONTRATO_SERVICO.getId());
						dao.atualizaFinalizado(dto.getNumero(), true);
						return true;
					} else {
						atualizaValorETipoTaxaSomenteArt(valorCalculadoConcreteira, TipoTaxaArtEnum.ART_CONCRETEIRA.getId());
						dao.atualizaFinalizado(dto.getNumero(), true);
						return true;
					}
			} else {
				
				List<ContratoArt> listaContratos = dao.getAllContratos(contrato.getArt().getNumero());
				Long tipoTaxa = 0L;
				
				for (ContratoArt contrato : listaContratos) {
					tipoTaxa = calcularContratosMultipla(contrato, database); //this.calcularObraServicoMultipla2(art, valorART, query, dataBase, dataPgto); linha 521
				}
				
				BigDecimal valorArt = new BigDecimal("0");
				BigDecimal somaDoValorCalculadoDosContratos = dao.getSomaDoValorCalculadoDosContratos(contrato.getArt().getNumero());

				if (contrato.getArt().getMultiplaMensal()) {
					BigDecimal valorMinimo = artDao.getTaxaPorDataBaseETipoTaxa(database, TipoTaxaArtEnum.TAXA_ESPECIAL_1);
					
					if(somaDoValorCalculadoDosContratos.compareTo(valorMinimo) < 0) {
						valorArt = valorMinimo;						
					}
				} else {
					valorArt = somaDoValorCalculadoDosContratos;
				}
				
				atualizaValorETipoTaxaSomenteArt(valorArt, tipoTaxa);
			}

			dao.atualizaFinalizado(dto.getNumero(), true);

		} catch (Exception e) {
			return false;
		}

		return true;
	}
	
	/**
	 * 
	 * @param dto
	 * @return
	 */
	private void defineValorAReceberPelaTaxaMinima(TipoTaxaArtEnum tipoTaxaArtEnum) {
		BigDecimal valorArt = artDao.getTaxaPorDataBaseETipoTaxa(new Date(), tipoTaxaArtEnum);
		atualizaValorETipoTaxaArt(valorArt, tipoTaxaArtEnum.getId());
	}
	
	private void atualizaValorETipoTaxaArt(BigDecimal valorArt, Long tipoTaxa) {
		contrato.setValorReceber(valorArt);
		contrato.setValorCalculado(valorArt);
		
		ArtTipoTaxa tipoTaxaContrato = new ArtTipoTaxa();
		tipoTaxaContrato.setId(tipoTaxa);
		contrato.setTipoTaxa(tipoTaxaContrato);
		
		contrato.getArt().setValorReceber(valorArt);
		contrato.getArt().setTipoTaxa(tipoTaxaContrato);
		dao.atualizaValorReceberCalculadoETipoTaxa(contrato);
		artDao.atualizaValorReceberETipoTaxa(contrato.getArt());
	}
	
	private void atualizaValorETipoTaxaSomenteArt(BigDecimal valorArt, Long tipoTaxa) {
		
		ArtTipoTaxa tipoTaxaContrato = new ArtTipoTaxa();
		tipoTaxaContrato.setId(tipoTaxa);
		contrato.getArt().setValorReceber(valorArt);
		contrato.getArt().setTipoTaxa(tipoTaxaContrato);
		artDao.atualizaValorReceberETipoTaxa(contrato.getArt());
	}
	
	private void atualizaValorETipoTaxaContrato(BigDecimal valor, Long tipoTaxa) {
		contrato.setValorReceber(valor);
		contrato.setValorCalculado(valor);
		ArtTipoTaxa tipoTaxaContrato = new ArtTipoTaxa();
		tipoTaxaContrato.setId(tipoTaxa);
		contrato.setTipoTaxa(tipoTaxaContrato);
		dao.atualizaValorReceberCalculadoETipoTaxa(contrato);
	}

	/**
	 * 
	 * @param dto
	 * @return
	 */
	private boolean verificaSeForneceConcreto() {
		if (temApenasUmaAtividadeEEhExecucaoDeServicoTecnico() && 
			temDuasEspecificacoesEUmaEhFabricacaoEOutraEhFornecimento() && 
			temApenasUmComplementoEEhConcreto()
		) {
			return artDao.atualizaForneceConcreto(contrato.getArt().getNumero(), true);
		}
		return artDao.atualizaForneceConcreto(contrato.getArt().getNumero(), false);
	}

	/**
	 * 
	 * @param dto
	 * @return
	 */
	private boolean temApenasUmComplementoEEhConcreto() {
		List<Long> complementos = dao.getListaDeCodigosDosComplementosDoContratoPor(contrato.getId());
		if (complementos.size() == 1) {
			return ListUtils.verificaSeTodosOsElementosSaoComuns(complementos, Arrays.asList(TipoComplementoContratoArt.CONCRETO.getId()));
		}
		return false;
	}

	/**
	 * 
	 * @param dto
	 * @return
	 */
	private boolean temDuasEspecificacoesEUmaEhFabricacaoEOutraEhFornecimento() {
		List<Long> especificacoes = dao.getListaDeCodigosDasEspecificacoesDoContratoPor(contrato.getId());
		if (especificacoes.size() == 2) {
			return ListUtils.verificaSeTodosOsElementosSaoComuns(especificacoes, Arrays.asList(TipoEspecificacaoContratoArt.FABRICACAO.getId(),TipoEspecificacaoContratoArt.FORNECIMENTO.getId()));
		}
		return false;
	}

	/**
	 * 
	 * @param dto
	 * @return
	 */
	private boolean temApenasUmaAtividadeEEhExecucaoDeServicoTecnico() {
		List<Long> atividades = dao.getListaDeCodigosDasAtividadesDoContratoPor(contrato.getId());
		if (atividades.size() == 1) {
			return ListUtils.verificaSeTodosOsElementosSaoComuns(atividades, Arrays.asList(TipoAtividadeContratoArt.EXECUCAO_SERVICO_TECNICO.getId()));
		}
		return false;
	}

	/**
	 * 
	 * @param dto
	 * @return
	 */
	private boolean verificaSeEhMultiplaMensal(Art art) {
		
		if (art.heTaxaMinima() && artPrincipalEhDeObraServico(art)) {
			return artDao.atualizaMultiplaMensal(art.getNumero(), false);
		}
		
		if (todosOsContratosTemDataInicioNoMesmoMesEAno(art) && todosOsContratosTemPrazoAteTrintaEDoisDias(art)) {
			return artDao.atualizaMultiplaMensal(art.getNumero(), true);
		}		
		return artDao.atualizaMultiplaMensal(art.getNumero(), false);
	}

	/**
	 * 
	 * @param dto
	 * @return
	 */
	private boolean todosOsContratosTemPrazoAteTrintaEDoisDias(Art art) {
		return dao.todosOsContratosTemPrazoAteTrintaEDoisDias(art.getNumero());
	}

	/**
	 * 
	 * @param dto
	 * @return
	 */
	private boolean todosOsContratosTemDataInicioNoMesmoMesEAno(Art art) {		
		return dao.todosOsContratosTemDataInicioNoMesmoMesEAno(art.getNumero());
	}

	/**
	 * 
	 * @param dto
	 * @return
	 */
	private boolean artPrincipalEhDeObraServico(Art art) {
		if (art.temArtPrincipal()) {
			Art artPrincipal = artDao.getArtPorId(art.getNumeroARTPrincipal());
			if(artPrincipal != null) {
				return artPrincipal.ehObraServico();
			}
		}
		return false;
	}

	private void calcularObraServico() {
		
		Long tipoTaxa = TipoTaxaArtEnum.METRAGEM_QUADRADA.getId();
		
		contrato.setQuantificacao(artQuantificacaoDao.getByIdContrato(contrato.getId()));
		contrato.setListCodigoAtividades(dao.getListaDeCodigosDasAtividadesDoContratoPor(contrato.getId()));
		contrato.setListCodigoEspecificacoes(dao.getListaDeCodigosDasEspecificacoesDoContratoPor(contrato.getId()));
		contrato.setListCodigoComplementos(dao.getListaDeCodigosDosComplementosDoContratoPor(contrato.getId()));
		contrato.setIdStringModalidade(getIdStringModalidadePeloRamoArt());
		
		
		
		BigDecimal valorRecolher 			= new BigDecimal(0);
		BigDecimal valorObraEServico	 	= defineValorDoContratoCorrigido(contrato);
		BigDecimal valorCalculo				= contrato.temQuantificacao() ? contrato.getQuantificacao().getValor() : new BigDecimal(0);
		BigDecimal valorEspecial 			= new BigDecimal(0);
		Date dataBase = new Date();
		
				
		List<Long> listTipoTaxa = artDao.getTaxaCobrancaEspecial(contrato, valorCalculo, dataBase);
		
		if(listTipoTaxa == null){
			listTipoTaxa = new ArrayList<Long>();
			tipoTaxa = TipoTaxaArtEnum.VALOR_CONTRATO_SERVICO.getId();	
			valorCalculo = valorObraEServico;
		}else{
			tipoTaxa = listTipoTaxa.get(0);
		}
		
		if(tipoTaxa.longValue() != TipoTaxaArtEnum.METRAGEM_QUADRADA.getId().longValue() && 
			tipoTaxa.longValue() != TipoTaxaArtEnum.LAUDO_VISTORIA_PROPRIEDADE_RURAL.getId().longValue() &&
			tipoTaxa.longValue() != TipoTaxaArtEnum.BOLETIM_PRODUCAO_AGRICOLA.getId().longValue() ) {
			
			listTipoTaxa.clear();
			tipoTaxa = TipoTaxaArtEnum.VALOR_CONTRATO_SERVICO.getId();
			valorCalculo = valorObraEServico;
		}
		
		if(tipoTaxa.longValue() == TipoTaxaArtEnum.METRAGEM_QUADRADA.getId().longValue()) {
			ArtCUB artCUB = artCUBDao.buscarPorMesEAno(DateUtils.getMesCorrente(), DateUtils.getAnoCorrente());
			valorCalculo = contrato.temQuantificacao() ? contrato.getQuantificacao().getValor().multiply(artCUB.getValor()) : new BigDecimal(0);
			tipoTaxa = TipoTaxaArtEnum.VALOR_CONTRATO_SERVICO.getId();
		}
		
		if (listTipoTaxa.contains(TipoTaxaArtEnum.MULTIPLA.getId()) || listTipoTaxa.contains(TipoTaxaArtEnum.MULTIPLA_MENSAL_FAIXA_7.getId())) {
			valorEspecial = artDao.getTaxaPorValorCalculoDataBaseETipoTaxaSemFaixaInicial(valorCalculo, dataBase, tipoTaxa);
			if(valorEspecial == null) {
				valorEspecial = new BigDecimal(0);
				tipoTaxa = TipoTaxaArtEnum.VALOR_CONTRATO_SERVICO.getId();
			}
		}
		
		valorEspecial = artDao.getTaxaPorValorCalculoDataBaseETipoTaxaComFaixaInicial(valorCalculo, dataBase, tipoTaxa);
		if(valorEspecial == null) {
			valorEspecial = new BigDecimal(0);
		}
		
		valorRecolher = valorEspecial;
		
		atualizaValorETipoTaxaArt(valorRecolher, tipoTaxa);
		
	}
	

	private String getIdStringModalidadePeloRamoArt() {
		return contrato.temRamoArt() ? String.valueOf(contrato.getRamoArt().getId()).substring(0, 1) : "0";
	}

	private BigDecimal defineValorDoContratoCorrigido(ContratoArt contrato) {
		
		FinMoeda moeda = finMoedaDao.getMoedaBy(contrato.getDataInicio());
		
		if (moeda != null) {
			BigDecimal valorCorrigido = contrato.getValorContrato().divide(moeda.getFatorConversao(), MathContext.DECIMAL128);
			return valorCorrigido.intValue() == 0 ? new BigDecimal(0) : valorCorrigido;
		}
		
		return new BigDecimal(0);
	}

	private Long calcularContratosMultipla(ContratoArt contrato, Date database) {
		
		Long tipoTaxa = TipoTaxaArtEnum.VALOR_CONTRATO_SERVICO.getId();
		
		contrato.setQuantificacao(artQuantificacaoDao.getByIdContrato(contrato.getId()));
		contrato.setListCodigoAtividades(dao.getListaDeCodigosDasAtividadesDoContratoPor(contrato.getId()));
		contrato.setListCodigoEspecificacoes(dao.getListaDeCodigosDasEspecificacoesDoContratoPor(contrato.getId()));
		contrato.setListCodigoComplementos(dao.getListaDeCodigosDosComplementosDoContratoPor(contrato.getId()));
		contrato.setIdStringModalidade(getIdStringModalidadePeloRamoArt());
		
		BigDecimal valorRecolher 			= new BigDecimal(0);
		BigDecimal valorObraEServico	 	= defineValorDoContratoCorrigido(contrato);
		BigDecimal valorCalculo				= valorObraEServico;
		BigDecimal valorEspecial 			= new BigDecimal(0);
		Date dataBase = new Date();
		
				
		List<Long> listTipoTaxa = artDao.getTaxaCobrancaEspecial(contrato, valorCalculo, dataBase);
		
		
		if (listTipoTaxa == null) {
			listTipoTaxa = new ArrayList<Long>();
			tipoTaxa = TipoTaxaArtEnum.VALOR_CONTRATO_SERVICO.getId();	
		} else if (contrato.getArt().naoHeMultiplaMensal() && 
			( listTipoTaxa.contains(TipoTaxaArtEnum.MULTIPLA.getId()) || 
			  listTipoTaxa.contains(TipoTaxaArtEnum.MULTIPLA_MENSAL_FAIXA_7.getId()) || 
			  listTipoTaxa.contains(TipoTaxaArtEnum.ART_CONCRETEIRA.getId()) )
			) {
			if (contrato.getArt().heTaxaMinima()) {
				tipoTaxa = TipoTaxaArtEnum.TAXA_ESPECIAL_1.getId();
			} else {
				 tipoTaxa = TipoTaxaArtEnum.VALOR_CONTRATO_SERVICO.getId();
			}
			
		} else if (listTipoTaxa != null && listTipoTaxa.size() > 0) {
			tipoTaxa = listTipoTaxa.get(0);
		}
		
		
		if(tipoTaxa.longValue() == TipoTaxaArtEnum.METRAGEM_QUADRADA.getId().longValue()) {
			ArtCUB artCUB = artCUBDao.buscarPorMesEAno(DateUtils.getMesCorrente(), DateUtils.getAnoCorrente());
			valorCalculo = contrato.temQuantificacao() ? contrato.getQuantificacao().getValor().multiply(artCUB.getValor()) : new BigDecimal(0);
			tipoTaxa = TipoTaxaArtEnum.VALOR_CONTRATO_SERVICO.getId();
		}
		
		if (listTipoTaxa.contains(TipoTaxaArtEnum.MULTIPLA.getId()) || listTipoTaxa.contains(TipoTaxaArtEnum.MULTIPLA_MENSAL_FAIXA_7.getId())) {
			valorEspecial = artDao.getTaxaPorValorCalculoDataBaseETipoTaxaSemFaixaInicial(valorCalculo, dataBase, tipoTaxa);
			if(valorEspecial == null) {
				valorEspecial = new BigDecimal(0);
				tipoTaxa = TipoTaxaArtEnum.VALOR_CONTRATO_SERVICO.getId();
			}
		}
		
		valorEspecial = artDao.getTaxaPorValorCalculoDataBaseETipoTaxaComFaixaInicial(valorCalculo, dataBase, tipoTaxa);
		if(valorEspecial == null) {
			valorEspecial = new BigDecimal(0);
		}
		
		valorRecolher = valorEspecial;
		
		if (contrato.artPossuiArtPrincipal()) {
			contratoArtPrincipal = dao.getPrimeiroContratoPor(contrato.getArt().getNumeroARTPrincipal());
			if (contratoArtPrincipal.artEhObraServico()) {
				if (numeroDeContratoEhOMesmo() && periodoEhOMesmo()) {
					tipoTaxa = TipoTaxaArtEnum.TAXA_ESPECIAL_1.getId();
					valorEspecial = artDao.getTaxaPorDataBaseETipoTaxa(database, TipoTaxaArtEnum.TAXA_ESPECIAL_1);
					valorRecolher = valorEspecial; 
				}
			}
		}
		
		contrato.setValorReceber(valorRecolher);
		contrato.setValorCalculado(valorRecolher);
		
		atualizaValorETipoTaxaContrato(contrato.getValorReceber(),tipoTaxa);
		
		return tipoTaxa;	
		
	}
	
	
	private boolean periodoEhOMesmo() {
		if(contrato.temDataInicio() && contrato.temDataFim()
			&& contratoArtPrincipal.temDataInicio() && contratoArtPrincipal.temDataFim()) {
			return DateUtils.primeiraDataeMaiorOuIgualQueSegunda(contrato.getDataInicio(), contratoArtPrincipal.getDataInicio()) 
					&& DateUtils.primeiraDataeMenorIgualQueSegunda(contrato.getDataFim(), contratoArtPrincipal.getDataFim());
		}
		return false;
	}

	private boolean temMaisDeDezContratos() {
		return dao.getTotalDeContratosDaArt(contrato.getArt().getNumero()) > 10;
	}

	private boolean heDeFornecimentoDeConcreto() {
		return contrato.getArt().getForneceConcreto();
	}

	private boolean heMultiplaMensal() {
		return contrato.getArt().heMultiplaMensal();
	}

	private boolean verificaAtividadeMultiplaVinculada() {
		return ListUtils.verificaSeHaElementoComum(dao.getListaDeCodigosDasAtividadesDoContratoPor(contrato.getId()), Arrays.asList(TipoAtividadeContratoArt.MANUTENCAO_DE_EQUIPAMENTO.getId(), TipoAtividadeContratoArt.MANUTENCAO_DE_INSTALACAO.getId(), TipoAtividadeContratoArt.OPERACAO_DE_EQUIPAMENTO.getId(), TipoAtividadeContratoArt.OPERACAO_DE_INSTALACAO.getId()));
	}

	private boolean heTaxaMinimaEvinculadaHeDeDesempenhoDeCargoEfuncao() {
		return contrato.getArt().heTaxaMinima() && contratoArtPrincipal.getArt().ehDesempenhoDeCargoFuncao();
	}

	private boolean todosOsValoresDeContratosSaoInferioresAMil() {
		return dao.todosOsValoresDeContratosSaoInferioresAMil(contrato.getArt().getNumero());
	}

	private boolean heTaxaMinimaEvinculadaHeDeObraEServico() {
		return contrato.getArt().heTaxaMinima() && contratoArtPrincipal.getArt().ehObraServico();
	}

	public boolean defineSeVaiUtilizarTaxaMinima() {
		
		//Se for receituario Agronomico sempre a taxaMinima é true e retrun
		
		if(contrato.artNaoPossuiArtPrincipal()) { // Linha 1616 - legado
			return artDao.atualizaTaxaMinima(contrato.getArt().getNumero(), false);
		}
		
		if(contrato.artEhDesempenhoDeCargoEFuncao() && contrato.temPrazoIndeterminado()) {
			return artDao.atualizaTaxaMinima(contrato.getArt().getNumero(), true);
		}
		
		if(contrato.artPossuiArtPrincipal()) {
			contratoArtPrincipal = dao.getPrimeiroContratoPor(contrato.getArt().getNumeroARTPrincipal());
			
			if (contrato.artEhMultipla() && contratoArtPrincipal.artEhObraServico()) {
				if(contratanteEhOMesmo(contrato,contratoArtPrincipal) && 
				   numeroDeContratoEhOMesmo() &&
				   enderecoDoContratanteEhOmesmo(contrato,contratoArtPrincipal)
				) {
					return artDao.atualizaTaxaMinima(contrato.getArt().getNumero(), true);
				}
			}
			
			if (contrato.artEhMultipla() && contratoArtPrincipal.artEhDesempenhoDeCargoEFuncao()) {
				if(contratanteEhOMesmo(contrato,contratoArtPrincipal)) {
					return artDao.atualizaTaxaMinima(contrato.getArt().getNumero(), true);
				}
			}
			
			if(enderecoDoContratoEhOmesmo(contrato,contratoArtPrincipal) &&
			   contratanteEhOMesmo(contrato,contratoArtPrincipal)  &&
			   empresaEhAMesma(contrato,contratoArtPrincipal)
			  ) {
				return artDao.atualizaTaxaMinima(contrato.getArt().getNumero(), true);
			}
			
			if(enderecoDoContratoEhOmesmo(contrato,contratoArtPrincipal) &&
			   contratanteEhOMesmo(contrato,contratoArtPrincipal)  &&
			   peloMenosUmaAtividadeEhIgual(contrato,contratoArtPrincipal) &&
			   naoHaEmpresaEmAmbasArts(contrato,contratoArtPrincipal)
			  ) {
				return artDao.atualizaTaxaMinima(contrato.getArt().getNumero(), true);
			}
			
			if(contrato.artEhObraServico() && contratoArtPrincipal.artEhDesempenhoDeCargoEFuncao()) {
				if(profissionalEhOMesmo(contrato,contratoArtPrincipal) &&
				   empresaEhAMesma(contrato,contratoArtPrincipal) &&
				   contratanteEhOMesmo(contrato,contratoArtPrincipal)
				) {
					return artDao.atualizaTaxaMinima(contrato.getArt().getNumero(), true);
				}
			}
			
		}
		
		return artDao.atualizaTaxaMinima(contrato.getArt().getNumero(), false);
	}

	
	private Boolean numeroDeContratoEhOMesmo() {
		if (contrato.temNumeroContrato() && contratoArtPrincipal.temNumeroContrato()) {
			return contrato.getNumeroContrato().equals(contratoArtPrincipal.getNumeroContrato());
		}
		return false;
	}

	private Boolean naoHaEmpresaEmAmbasArts(ContratoArt contrato, ContratoArt contratoArtPrincipal) {
		return !contrato.getArt().temEmpresa() && !contratoArtPrincipal.getArt().temEmpresa();
	}

	private Boolean peloMenosUmaAtividadeEhIgual(ContratoArt contrato, ContratoArt contratoArtPrincipal) {
		return ListUtils.verificaSeHaElementoComum(dao.getListaDeCodigosDasAtividadesDoContratoPor(contrato.getId()), dao.getListaDeCodigosDasAtividadesDoContratoPor(contratoArtPrincipal.getId()));
	}

	private Boolean profissionalEhOMesmo(ContratoArt contrato, ContratoArt contratoArtPrincipal) {
		if(contrato.getArt().temProfissional() && contratoArtPrincipal.getArt().temProfissional()) {
			return contrato.getArt().getProfissional().getPessoaFisica().getId().equals(contrato.getArt().getProfissional().getPessoaFisica().getId());
		}
		return false;
	}

	private Boolean empresaEhAMesma(ContratoArt contrato, ContratoArt contratoArtPrincipal) {
		if(contrato.getArt().temEmpresa() && contratoArtPrincipal.getArt().temEmpresa()) {
			return contrato.getArt().getEmpresa().getPessoaJuridica().getId().equals(contratoArtPrincipal.getArt().getEmpresa().getPessoaJuridica().getId());
		}
		return false;
	}

	private Boolean enderecoDoContratanteEhOmesmo(ContratoArt contratoArt, ContratoArt contratoArtPrincipal) {
		if(contratoArt.temEnderecoContratante() && contratoArtPrincipal.temEnderecoContratante()) {
			Endereco enderecoArt = contratoArt.getEnderecoContratante().getClone();
			Endereco enderecoArtPrincipal = contratoArtPrincipal.getEnderecoContratante().getClone();
			
			return EnderecoUtil.enderecosSaoIguais(EnderecoUtil.removerAcentosEespacoDoEndereco(enderecoArt), EnderecoUtil.removerAcentosEespacoDoEndereco(enderecoArtPrincipal));
		} else if (!contratoArt.temEnderecoContratante() && !contratoArtPrincipal.temEnderecoContratante()) {
			return true;
		}
		return false;
	}
	
	private Boolean enderecoDoContratoEhOmesmo(ContratoArt contratoArt, ContratoArt contratoArtPrincipal) {
		if(contratoArt.temEndereco() && contratoArtPrincipal.temEndereco()) {
			Endereco enderecoArt = contratoArt.getEndereco().getClone();
			Endereco enderecoArtPrincipal = contratoArtPrincipal.getEndereco().getClone();
			
			return EnderecoUtil.enderecosSaoIguais(EnderecoUtil.removerAcentosEespacoDoEndereco(enderecoArt), EnderecoUtil.removerAcentosEespacoDoEndereco(enderecoArtPrincipal));
		} else if (!contratoArt.temEndereco() && !contratoArtPrincipal.temEndereco()) {
			return true;
		}
		return false;
	}
	
	private Boolean contratanteEhOMesmo(ContratoArt contrato,ContratoArt contratoArtPrincipal) {
		if(contrato.temPessoa() && contratoArtPrincipal.temPessoa()) {
			return contrato.getPessoa().getId().equals(contratoArtPrincipal.getPessoa().getId());
		}
		return false;
	}

}
