package br.org.crea.art.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.art.ArtConverter;
import br.org.crea.commons.dao.art.ArtDao;
import br.org.crea.commons.dao.art.ContratoArtDao;
import br.org.crea.commons.dao.art.ExigenciaArtDao;
import br.org.crea.commons.dao.financeiro.FinDividaDao;
import br.org.crea.commons.factory.art.AuditaArtFactory;
import br.org.crea.commons.factory.art.LogArtFactory;
import br.org.crea.commons.factory.commons.EmailFactory;
import br.org.crea.commons.models.art.Art;
import br.org.crea.commons.models.art.ArtExigencia;
import br.org.crea.commons.models.art.ArtSituacaoLiberacao;
import br.org.crea.commons.models.art.ArtTipoAcao;
import br.org.crea.commons.models.art.ContratoArt;
import br.org.crea.commons.models.art.ExigenciaArt;
import br.org.crea.commons.models.art.dtos.ArtMinDto;
import br.org.crea.commons.models.art.enuns.TipoAtividadeContratoArt;
import br.org.crea.commons.models.art.enuns.TipoBaixaArtEnum;
import br.org.crea.commons.models.art.enuns.TipoComplementoContratoArt;
import br.org.crea.commons.models.art.enuns.TipoEspecificacaoContratoArt;
import br.org.crea.commons.models.art.enuns.TipoExigenciaArtEnum;
import br.org.crea.commons.models.art.enuns.TipoTaxaArtEnum;
import br.org.crea.commons.models.commons.TipoPessoa;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.models.commons.enuns.TipoAcaoEnum;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;
import br.org.crea.commons.models.financeiro.FinDivida;
import br.org.crea.commons.models.financeiro.NaturezaDivida;
import br.org.crea.commons.models.financeiro.StatusDivida;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.service.art.ValidaArtService;
import br.org.crea.commons.util.DateUtils;
import br.org.crea.commons.util.ListUtils;
import br.org.crea.commons.util.StringUtil;

public class FinalizaArtService {
	
	@Inject
	private ContratoArtDao dao;

	@Inject
	private ArtDao artDao;
	
	@Inject
	private FinDividaDao finDividaDao;
	
	@Inject
	private ExigenciaArtDao exigenciaArtDao;

	@Inject
	private HttpClientGoApi httpGoApi;
	
	@Inject
	private LogArtFactory artLog;
	
	@Inject
	private ContratoArt contrato;
	
	@Inject
	private ContratoArt contratoArtPrincipal;
	
	@Inject
	private ArtTipoAcao tipoAcaoArt;
	
	@Inject
	private ArtSituacaoLiberacao situacaoLiberacao;
	
	@Inject
	private ArtConverter artConverter;
	
	@Inject
	private AuditaArtFactory audita;
	
	@Inject
	private EmailFactory emailFactory;
	
	private Long idDivida;
	
	private Long idDividaTaxaIncorporacao;
	
	@Inject
	private ValidaArtService validaArtService;

	/** Método responsável por finalizar a ART
	 * Carrega os dados do contrato e executa as seguintes ações:
	 * 1. Verifica se é ação ordinária
	 * 2. Gera a dívida da ART e da taxa de incorporação se houver,
	 * 3. Gera exigências da ART
	 * 4. Em caso de ART substituta, efetua a substituição
	 * 5. Finaliza a ART
	 * 6. Loga a criação da ART
	 * 
	 * Em caso de erro, desfaz as alterações efetuadas pelo método
	 * 
	 * @param numeroArt
	 * @param usuario
	 * @return ArtMinDto
	 */
	public ArtMinDto finaliza(String numeroArt, UserFrontDto user) {
		ArtMinDto dto = new ArtMinDto();
		dto.setNumero(numeroArt);
		
		contrato = dao.getPrimeiroContratoPor(numeroArt);
		Boolean ehAcaoOrdinaria = contrato.getArt().getIsAcaoOrdinaria();
		
		try {
			contrato.getArt().setIsAcaoOrdinaria(verificaSeAcaoOrdinaria());
			
			idDivida = gerarDividaArt();
			dto.setCodigoDivida(idDivida.toString());
			dto.setTemDivida(idDivida != null);
			
			if (verificaTaxaDeIncorporacao()) {
				idDividaTaxaIncorporacao = gerarDividaTaxaDeIncorporacao(); // linha 2249 do legado
				dto.setTemTaxaDeIncorporacao(idDividaTaxaIncorporacao != null);
			}
				
			gerarExigenciasDeUmaNovaArt(); // linha 6459 do legado
						
			trataSubstituicaoArtPrincipal(user);
			
			artDao.atualizaArtFinalizada(numeroArt, true);
			
			artLog.logaAcaoArt(user, artConverter.toDto(contrato.getArt()), TipoAcaoEnum.INCLUSAO_ART);
			audita.finalizaArt(numeroArt, user);
			
			if (user.heEmpresa()) {
				emailFactory.enviarEmailCadastroDeArtPorEmpresa(contrato);
			}
			
		} catch (Exception e) {
			cancelaDividasDaArt(idDivida, idDividaTaxaIncorporacao);
			cancelaExigenciasArt();
			desfazerSubstituicaoArtPrincipal();
			retornaAcaoOrdinariaAValorAnterior(ehAcaoOrdinaria);
			cancelaFinalizacaoArt();
			
			httpGoApi.geraLog("FinalizaArtService || finaliza", StringUtil.convertObjectToJson(numeroArt), e);
			return null;
		}

		return dto;
	}

	private void desfazerSubstituicaoArtPrincipal() {
		artDao.desfazerSubstituicaoNumeroArtPrincipalDasVinculadasAArtSubstituida(contrato.getArt());
	}

	private void cancelaFinalizacaoArt() {
		artDao.atualizaArtFinalizada(contrato.getArt().getNumero(), false);
	}

	private void retornaAcaoOrdinariaAValorAnterior(boolean ehAcaoOrdinaria) {
		artDao.atualizaAcaoOrdinaria(contrato.getArt().getNumero(), ehAcaoOrdinaria);
	}

	private void cancelaExigenciasArt() {
		exigenciaArtDao.deletaExigenciasPor(contrato.getId());		
	}

	private void cancelaDividasDaArt(Long idDivida, Long idDividaTaxaIncorporacao) {
		if (idDivida != null) {
			finDividaDao.deleta(idDivida);
		}
		if (idDividaTaxaIncorporacao != null) {
			finDividaDao.deleta(idDividaTaxaIncorporacao);
		}
	}

	/** Método que gera as exigências de uma ART cadastrada
	 * Será gerada exigência nos seguintes casos:
	 *  ART que não seja isenta será gerada exigência de ART_SEM_PAGAMENTO
	 *  Se for gerada taxa de incorporação
	 *  Caso a lista de especificação ou complemento conste apenas o item outros
	 *  Para cada contrato com descrição complementar preenchida
	 * 
	 * @return boolean
	 */
	private boolean gerarExigenciasDeUmaNovaArt() {
		
		tipoAcaoArt = new ArtTipoAcao();
		tipoAcaoArt.setId(0L);
		situacaoLiberacao = new ArtSituacaoLiberacao();
		situacaoLiberacao.setId(0L);
		
		if (!contrato.getArt().getIsAcaoOrdinaria() && !contrato.getArt().getIsTermoAditivo()) { // && !pagaEmJuizo) 
			incluirExigencia(contrato, TipoExigenciaArtEnum.ART_SEM_PAGAMENTO);
		}
				
		if (idDividaTaxaIncorporacao != null) {
			incluirExigencia(contrato, TipoExigenciaArtEnum.PAGAMENTO_TAXA_INCORPORACAO_ACERVO);
		}
		
		if (validaArtService.possuiSomenteOutrosNaListaDeEspecificacao(contrato)) {
			incluirExigencia(contrato, TipoExigenciaArtEnum.CAMPO_18_SUJEITO_NOVA_ANALISE);
		}
		
		if (validaArtService.possuiSomenteOutrosNaListaDeComplementos(contrato)) {
			incluirExigencia(contrato, TipoExigenciaArtEnum.CAMPO_19_SUJEITO_NOVA_ANALISE);
		}
		
		if (contrato.artEhMultipla()) {
			List<ContratoArt> contratosComDescricaoComplementar = dao.getContratosComDescricaoComplementar(contrato.getArt().getNumero());
			for (ContratoArt contratoComDescricaoComplementar : contratosComDescricaoComplementar) {
				incluirExigencia(contratoComDescricaoComplementar, TipoExigenciaArtEnum.CAMPO_27_SUJEITO_NOVA_ANALISE);
			}			
			
		} else {		
			if (contrato.temDescricaoComplementares()) {
				incluirExigencia(contrato, TipoExigenciaArtEnum.CAMPO_27_SUJEITO_NOVA_ANALISE);
			}
		}
		
		return artDao.atualizaExigencia(contrato.getArt().getNumero(), true);
	}

	private void incluirExigencia(ContratoArt contrato, TipoExigenciaArtEnum tipoExigencia) {

		ExigenciaArt exigencia = new ExigenciaArt();
		
		exigencia.setData(new Date());
		exigencia.setArt(contrato.getArt());
		exigencia.setContrato(contrato);
		exigencia.setExigencia(populaExigencia(tipoExigencia));
		exigencia.setTipoAcaoArt(tipoAcaoArt);
		exigencia.setMotivo(tipoExigencia.getDescricao());
		exigencia.setSituacaoLiberacao(situacaoLiberacao);
		
		exigenciaArtDao.create(exigencia);
	}

	private ArtExigencia populaExigencia(TipoExigenciaArtEnum tipoExigencia) {
		ArtExigencia artExigencia = new ArtExigencia();
		
		artExigencia.setId(tipoExigencia.getId());
		return artExigencia;
	}

	private Long gerarDividaTaxaDeIncorporacao() {

		if (contrato.getArt().getIsAcaoOrdinaria()) {
			return null;
		}
		
		// se nao eh resgate ou eh de termo aditivo ...
		
		
		FinDivida divida = new FinDivida();
		
		Date hoje = new Date();
		
		BigDecimal valorTaxaDeIncorporacao = artDao.getTaxaPorDataBaseETipoTaxa(new Date(), TipoTaxaArtEnum.TAXA_RESGATE_ACERVO);
		
		divida.setData(hoje);
		divida.setDataVencimento(hoje); // vencimento é no próprio dia?????? // linha 2295 legado
		divida.setValorOriginal(valorTaxaDeIncorporacao);
		divida.setValorAtual(valorTaxaDeIncorporacao);
		divida.setDataValorAtualizado(DateUtils.getUltimoDiaDoAnoCorrente());
		divida.setIdentificadorDivida(contrato.getArt().getNumero());
		divida.setNatureza(populaNaturezaDividaTaxaDeIncorporacao());
		divida.setParcela(0);
		divida.setStatus(populaStatusDividaAVencer());
		divida.setTipoPessoa(populaTipoPessoaDivida());
		divida.setPessoa(populaPessoaDividaArt());
		divida.setObservacao("Dívida gerada automaticamente pelo sistema de ART.");
		divida.setDuodecimos("0");
		divida.setServicoExecutado(false);
		
		divida.setDiati(false);
		divida.setScpc(false);
		divida.setScpcBaixa(false);
		divida.setScpcRepasse(false);
		divida.setRepasseOk(false);
		
		divida = finDividaDao.create(divida);
		
		emailFactory.enviarEmailTaxaDeIncorporacao(divida.getPessoa().getId(), contrato.getArt().getNumero());
		
		
//		Exigencia nao analisada
		
//		ExigenciaART exigenciaART = exigenciaARTFacade.buscarByARTECodigo(art.getNumero(), new Long(50));
//		Exigencia exigencia = exigenciaFacade.buscarPorCodigo(new Long(50));
//		if ( exigenciaART == null && finDivida.getDataPgto() == null ) {
//			exigenciaART = new ExigenciaART();
//			exigenciaART.setData(Calendar.getInstance());
//			exigenciaART.setArt(art);
//			exigenciaART.setExigencia(exigencia);
//			exigenciaART.setTipoAcaoART(tipoAcaoARTFacade.buscarPorCodigo(new Long(0)));
//			exigenciaART.setMotivo(exigencia.getDescricao());
//
//			exigenciaARTFacade.cadastrar(exigenciaART);
//		}
		
		return divida.getId();
	}

	private NaturezaDivida populaNaturezaDividaTaxaDeIncorporacao() {
		NaturezaDivida naturezaDivida = new NaturezaDivida();
		naturezaDivida.setId(new Long(800));
		return naturezaDivida;
	}

	/** Método para Gerar a dívida da ART
	 * 
	 * @return boolean, retorna false caso não tenha gerado a dívida 
	 */
	private Long gerarDividaArt() { // linha 1265, 8164 processarRegrasFinanceirasDaArt

		if (contrato.getArt().getIsAcaoOrdinaria()) {
			return null;
		}
		
		FinDivida divida = new FinDivida();
	
		divida.setData(new Date());
		divida.setDataVencimento(defineDataDeVencimentoDividaArt());
		divida.setValorOriginal(contrato.getArt().getValorReceber());
		divida.setValorAtual(contrato.getArt().getValorReceber());
		divida.setDataValorAtualizado(DateUtils.getUltimoDiaDoAnoCorrente());
		divida.setIdentificadorDivida(contrato.getArt().getNumero());
		divida.setNatureza(populaNaturezaDividaArt());
		divida.setParcela(0);
		divida.setStatus(populaStatusDividaAVencer());
		divida.setTipoPessoa(populaTipoPessoaDivida());
		divida.setPessoa(populaPessoaDividaArt());
		divida.setObservacao("Dívida gerada automaticamente pelo sistema de ART.");
		divida.setServicoExecutado(false);
		divida.setDuodecimos("0");
		
		divida.setDiati(false);
		divida.setScpc(false);
		divida.setScpcBaixa(false);
		divida.setScpcRepasse(false);
		divida.setRepasseOk(false);
		
		divida = finDividaDao.create(divida);
		
		return divida.getId();
		
	}

	private TipoPessoa populaTipoPessoaDivida() {
		return contrato.getArt().temEmpresa() ? TipoPessoa.PESSOAJURIDICA : TipoPessoa.PESSOAFISICA;
	}

	private Pessoa populaPessoaDividaArt() {
		Pessoa pessoa = new Pessoa();
		if (contrato.getArt().temEmpresa()) {
			pessoa.setId(contrato.getArt().getEmpresa().getPessoaJuridica().getId());
		} else {
			pessoa.setId(contrato.getArt().getProfissional().getPessoaFisica().getId());
		}
		return pessoa;
	}

	private NaturezaDivida populaNaturezaDividaArt() {
		NaturezaDivida naturezaDivida = new NaturezaDivida();
		naturezaDivida.setId(new Long(1));
		return naturezaDivida;
	}
	
	private StatusDivida populaStatusDividaAVencer() {
		StatusDivida statusDivida = new StatusDivida();
		statusDivida.setId(new Long(1));
		return statusDivida;
	}
	
	/** Define data de vencimento da art após seu cadastramento.
	 *  Se a dataFim estiver entre amanhã e daqui dez dias, a data de vencimento será igual a dataFim,
	 *  caso contrário o prazo de vencimento será de dez dias.
	 *  No caso da múltipla mensal, o vencimento é até o décimo dia útil do mês seguinte.
	 * 
	 * @return Date
	 */
	private Date defineDataDeVencimentoDividaArt() {
		
		Date hoje = new Date();
		Date hojeMaisDezDias = DateUtils.adicionaOrSubtraiDiasA(hoje, 10);
		Date dataDeVencimento = hojeMaisDezDias;
		
		if (contrato.artNaoEhMultiplaMensal()) {
			Date amanha = DateUtils.adicionaOrSubtraiDiasA(hoje, 1);
			
			if (contrato.temDataFim()) {
				if (DateUtils.primeiraDataeMaiorOuIgualQueSegunda(contrato.getDataFim(), amanha) &&
				DateUtils.primeiraDataeMenorIgualQueSegunda(contrato.getDataFim(), hojeMaisDezDias)) {
					dataDeVencimento = contrato.getDataFim();
				}
			}
		}
		
		if (contrato.artEhMultiplaMensal()) { // confirmar com Jeferson
			Date dataMesSeguinte = DateUtils.adicionaOrSubtraiMesesA(contrato.getDataCadastro(), 1);
			dataDeVencimento = DateUtils.getEnesimoDiaUtilDoMesAno(10, DateUtils.format(dataMesSeguinte, DateUtils.MM) , DateUtils.format(dataMesSeguinte, DateUtils.YYYY));
		}
		
		return dataDeVencimento;
	}

	private boolean verificaSeAcaoOrdinaria() {
		// FIXME  podemos criar uma coluna VALIDAR_ATIVIDADES na tabela ART_PESSOA_ACAO_ELEVADORES para prever casos como esse em que é necessário validar atividades, especificações e complementos
		if (contrato.getArt().temEmpresa()) {
			if (contrato.getArt().getEmpresa().getPessoaJuridica().getId().equals(2011202764L)) { // linha 1008 do legado
				if (   atividadeEhManutencaoDeEquipamentoOuManutencaoDeEquipamento()
				    && possuiApenasUmaEspecificacaoEEhConservacao()
					&& possuiApenasUmComplementoEEhAparelhoEletricoEletronico()) {
					
					artDao.atualizaAcaoOrdinaria(contrato.getArt().getNumero(), true);
					return true;
				}

					ListUtils.verificaSeHaElementoComum(dao.getListaDeCodigosDasAtividadesDoContratoPor(contrato.getId()), Arrays.asList(TipoAtividadeContratoArt.MANUTENCAO_DE_EQUIPAMENTO.getId(), TipoAtividadeContratoArt.MANUTENCAO_DE_INSTALACAO.getId(), TipoAtividadeContratoArt.OPERACAO_DE_EQUIPAMENTO.getId(), TipoAtividadeContratoArt.OPERACAO_DE_INSTALACAO.getId()));
			}
		}
		return contrato.getArt().getIsAcaoOrdinaria();
	}

	private boolean atividadeEhManutencaoDeEquipamentoOuManutencaoDeEquipamento() {
		List<Long> atividades = dao.getListaDeCodigosDasAtividadesDoContratoPor(contrato.getId());
		if (atividades.size() > 0) {
			return ListUtils.verificaSeHaElementoComum(atividades, Arrays.asList(TipoAtividadeContratoArt.MANUTENCAO_DE_EQUIPAMENTO.getId(), TipoAtividadeContratoArt.MANUTENCAO_DE_INSTALACAO.getId()));
		}
		return false;
	}
	
	private boolean possuiApenasUmaEspecificacaoEEhConservacao() {
		List<Long> especificacoes = dao.getListaDeCodigosDasEspecificacoesDoContratoPor(contrato.getId());
		if (especificacoes.size() == 1) {
			return ListUtils.verificaSeTodosOsElementosSaoComuns(especificacoes, Arrays.asList(TipoEspecificacaoContratoArt.CONSERVACAO.getId()));
		}
		return false;
	}
	
	private boolean possuiApenasUmComplementoEEhAparelhoEletricoEletronico() {
		List<Long> complementos = dao.getListaDeCodigosDosComplementosDoContratoPor(contrato.getId());
		if (complementos.size() == 1) {
			return ListUtils.verificaSeTodosOsElementosSaoComuns(complementos, Arrays.asList(TipoComplementoContratoArt.APARELHO_ELETRICO_ELETRONICO.getId()));
		}
		return false;
	}

	/** Método que trata a substituição de uma ART
	 * 
	 * Substitui a ART principal e seus respectivos relacionamentos com outras ARTs
	 * E gera log da substituição
	 * 
	 * @param user
	 */
	private void trataSubstituicaoArtPrincipal(UserFrontDto user) {

		if (contrato.artPossuiArtPrincipal() && contrato.getArt().ehSubstituta())
		{
			contratoArtPrincipal = dao.getPrimeiroContratoPor(contrato.getArt().getNumeroARTPrincipal());
			
			String msgBaixa = "ART: " + contrato.getArt().getNumeroARTPrincipal() + " SUBSTITUÍDA POR: " + contrato.getArt().getNumero();
			artDao.baixarArt(TipoBaixaArtEnum.CANCELAMENTO_POR_SUBSTITUICAO_DE_ART.getId(), contrato.getArt().getNumeroARTPrincipal(), msgBaixa);
			
			if (contratoArtPrincipal.artEhMultipla()) {
				dao.darBaixaNosContratos(contratoArtPrincipal.getArt().getNumero(), TipoBaixaArtEnum.CANCELAMENTO_POR_SUBSTITUICAO_DE_ART);
			}
			
			cancelarDividasDaArtPrincipal();
			
			artLog.auditaAcaoSubstituicaoArt(user, contrato.getArt().getNumero(), contrato.getArt().getNumeroARTPrincipal(), TipoAcaoEnum.BAIXA_ART);
			
			artDao.substituirNumeroArtPrincipalDasVinculadasAArtSubstituida(contrato.getArt());
			
			List<String> listaVinculadas = artDao.getListNumeroArtsVinculadas(contrato.getArt().getNumeroARTPrincipal());
			criarLogDaSubstituicaoDasVinculadas(listaVinculadas, user);
		}
	}

	private void criarLogDaSubstituicaoDasVinculadas(List<String> listaVinculadas, UserFrontDto user) {
		listaVinculadas.remove(contrato.getArt().getNumero());
		
		for (String numeroArtVinculada : listaVinculadas) {
			artLog.auditaAcaoSubstituicaoArt(user, contrato.getArt().getNumero(), numeroArtVinculada, TipoAcaoEnum.BAIXA_ART);
		}
	}

	private void cancelarDividasDaArtPrincipal() {
		finDividaDao.cancelarDividaDeArtETaxaDeIncorporacao(contratoArtPrincipal.getArt().getNumero());
	}
	
	public boolean verificaTaxaDeIncorporacao() { // linha 1903 legado
		Date hoje = new Date();
		Date dataFinal = contrato.getDataFim() != null ? contrato.getDataFim() : hoje;
		
		Art art = artDao.getByIdString(contrato.getArt().getNumero());
		if (art.ehReceituarioAgronomico()) {
			return false;
		}
		
		contrato = dao.getPrimeiroContratoPor(contrato.getArt().getNumero());
		
		if (contrato.getArt().heTaxaMinima()) {
			return false;
		}		
		
		if (temArtPrincipalENaoEhTaxaMinima()) {
			contratoArtPrincipal = dao.getPrimeiroContratoPor(contrato.getArt().getNumeroARTPrincipal());

			if (verificaSeARTevinculadaCargoFuncao()) {
				return false;
			}

			if (contrato.artEhMultipla() && contratoArtPrincipal.artEhObraServico()) {
				if (temMesmoNumeroDeContrato()) {
					return false;
				}
			}
		}
		
		// simplificação da regra de prazo determinado, como só as antigas terão falha no preenchimento dos prazos, o único 
		// caso atual que é indeterminado é a de cargo e função
		if (contrato.artEhDesempenhoDeCargoEFuncao() && contrato.temPrazoIndeterminado()) {
			return false;
		}
		
		if (contrato.artEhMultipla()) {
			dataFinal = dao.pegaMaiorDataFinaldeContrato(contrato.getArt().getNumero());
		}
				
//		if (art.getArtNaoCobrarIncorporacao() != null && art.getArtNaoCobrarIncorporacao().getNumeroArt() != null ){
//				return false;
//			}

		if (DateUtils.primeiraDataeMaiorOuIgualQueSegunda(dataFinal, hoje)) {
			return false;
		}
		
				
		if (contrato.getArt().heMultiplaMensal()) {
			Date decimoDiaUtilDoMesSeguinte = DateUtils.getEnesimoDiaUtilDoMesAno(10, DateUtils.format(dataFinal, DateUtils.MM), DateUtils.format(dataFinal, DateUtils.YYYY));
			if (DateUtils.primeiraDataeMaiorOuIgualQueSegunda(decimoDiaUtilDoMesSeguinte, hoje)) {
				return false;
			}
		}			
		
		return true;
	}

	private boolean temArtPrincipalENaoEhTaxaMinima() {
		return contrato.getArt().temArtPrincipal() && contrato.getArt().naoEhTaxaMinima();
	}

	private boolean temMesmoNumeroDeContrato() {
		if (contrato.temNumeroContrato() && contratoArtPrincipal.temNumeroContrato()) {
			return contrato.getNumeroContrato().equals(contratoArtPrincipal.getNumeroContrato());
		}
		return false;
	}

	private boolean verificaSeARTevinculadaCargoFuncao() {

		if (contratoArtPrincipal.artEhDesempenhoDeCargoEFuncao()) {
			if (ehOMesmoProfissional() && ehAMesmaEmpresa() && ehOMesmoContratante()) {
				return true;
			}
		}

		return false;
	}

	private boolean ehOMesmoContratante() {
		if (contrato.temPessoa() && contratoArtPrincipal.temPessoa()) {
			return contrato.getPessoa().getId().equals(contratoArtPrincipal.getPessoa().getId());
		}
		return false;
	}

	private boolean ehOMesmoProfissional() {
		return contrato.getArt().getProfissional().getPessoaFisica().getId().equals(contratoArtPrincipal.getArt().getProfissional().getPessoaFisica().getId());
	}

	private boolean ehAMesmaEmpresa() {
		if (contrato.getArt().temEmpresa() && contratoArtPrincipal.getArt().temEmpresa()) {
			if (contrato.getArt().getEmpresa().getPessoaJuridica().getId().equals(contratoArtPrincipal.getArt().getEmpresa().getPessoaJuridica().getId())) {
				return true;
			}
		}
		return false;
	}


}
