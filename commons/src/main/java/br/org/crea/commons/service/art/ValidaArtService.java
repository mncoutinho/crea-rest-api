package br.org.crea.commons.service.art;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.dao.art.ArtDao;
import br.org.crea.commons.dao.art.ArtQuantificacaoDao;
import br.org.crea.commons.dao.art.ContratoArtDao;
import br.org.crea.commons.dao.cadastro.empresa.QuadroTecnicoDao;
import br.org.crea.commons.dao.cadastro.pessoa.HistoricoDao;
import br.org.crea.commons.models.art.Art;
import br.org.crea.commons.models.art.ContratoArt;
import br.org.crea.commons.models.art.enuns.TipoAtividadeContratoArt;
import br.org.crea.commons.models.art.enuns.TipoComplementoContratoArt;
import br.org.crea.commons.models.commons.dtos.DomainGenericDto;
import br.org.crea.commons.util.DateUtils;
import br.org.crea.commons.util.ListUtils;

public class ValidaArtService {
	
	@Inject
	private ContratoArtDao dao;
	
	@Inject
	ArtQuantificacaoDao quantificacaoDao;
	
	@Inject
	HistoricoDao historicoDao;
	
	@Inject
	QuadroTecnicoDao quadroTecnicoDao;
	
	@Inject
	ArtDao artDao;
	
	public List<String> validaONaoPreenchimentoDosDadosIniciaisObrigatoriosDoContrato(String idContrato) {
		ContratoArt contrato = dao.getContratoPor(idContrato);
		List<String> mensagens = new ArrayList<>();
		
		if(contrato.naoTemDataInicio()) mensagens.add("Data Início");
		
		if(contrato.temDataInicio() && contrato.temDataFim()) {
			if (DateUtils.primeiraDataeMaiorQueSegunda(contrato.getDataInicio(), contrato.getDataFim())) mensagens.add("Data de Início é maior que Previsão de Término");
		}
		
		if (contrato.artEhObraServico() || contrato.artEhMultipla()) {
			if(contrato.artEhObraServico()) {
				if(contrato.artPossuiArtPrincipal()) {
					Art artPrincipal = artDao.getArtPorId(contrato.getArt().getNumeroARTPrincipal());
					if(!artPrincipal.ehDesempenhoDeCargoFuncao()) {
						if(contrato.naoTemValorDoContrato()) mensagens.add("Valor do contrato");
					}
				} else {
					if(contrato.naoTemValorDoContrato()) mensagens.add("Valor do contrato");
				}
				if(contrato.naoTemFinalidade()) mensagens.add("Finalidade");
			}
			if(contrato.artEhMultipla()) {
				if(contrato.naoTemValorDoContrato()) mensagens.add("Valor do contrato");
			}
			if(contrato.naoTemDataCelebracao()) mensagens.add("Data de Celebração");
			if(contrato.dataCelebracaoEhSuperiorA2Meses()) mensagens.add("A Data de Celebração não pode ser superior a 2 meses em relação a data corrente.");
			if(contrato.dataInicioEhSuperiorA2Meses()) mensagens.add("A Data de Início não pode ser superior a 2 meses em relação a data corrente.");
			
			if(contrato.naoTemDataFim() && contrato.naoTemPrazoMes() && contrato.naoTemPrazoDia()) mensagens.add("Preencha a data prevista de término ou prazo mês/dia");
			
			if(contrato.temDataInicio() && contrato.temDataCelebracao()) {
				if (DateUtils.primeiraDataeMaiorQueSegunda(contrato.getDataCelebracao(), contrato.getDataInicio())) mensagens.add("Data de Celebração posterior à Data de Início dos serviços. Caso a assinatura do contrato tenha ocorrido efetivamente após o início dos serviços, preencher o campo \"data de Celebração\" igual à data de início e, imediatamente após o cadastramento, apresentar cópia do contrato através do e-mail acervotecnico@crea-rj.org.br para que a informação seja retificada.");
			}
			
			return mensagens;
		}
		
		if (contrato.artEhDesempenhoDeCargoEFuncao()) {
			if(contrato.naoTemNHHJT()) mensagens.add("HH / JT");
			if(contrato.naoTemSalario() && contrato.selecionouQueNaoTemProLabore()) mensagens.add("Salário");
			if(contrato.naoTemProLabore()) mensagens.add("Pró-Labore");
			if(contrato.naoTemTipoUnidadeAdministrativa()) mensagens.add("Tipo Unidade Administrativa");
			if(contrato.naoTemPrazoDeterminado()) mensagens.add("Prazo Determinado");
			if(contrato.naoTemTipoVinculo()) mensagens.add("Tipo Vínculo");
			if(contrato.temPrazoDeterminadoVerificaDataPrevisaoTermino()) mensagens.add("Previsão de término");
			if(contrato.dataInicioEhSuperiorADataAtual()) mensagens.add("A Data de Início não pode ser superior a data atual.");
//			if(contrato.temContratantePessoaJuridicaDireitoPrivado()) {
//				if (contrato.temSalario()) {
//					if (validaSalarioMinimoProfissional(contrato)) mensagens.add("Salário informado está em desacordo com a legislação");
//				}
//			}
			return mensagens;
		}
		
		
		return mensagens;
	}
	
//	private boolean validaSalarioMinimoProfissional(ContratoArt contrato) {
////		return dao.validaSalarioMinimoProfissional(contrato);
//	}

	public boolean verificaSeTodosOsContratosEstaoFinalizados(String numeroArt) {
		int totalDeContratos = dao.getTotalDeContratosDaArt(numeroArt);
		if (totalDeContratos == 0) {
			return false;
		}
		
		return dao.verificaSeTodosOsContratosEstaoFinalizados(numeroArt);
	}

	public List<String> validaONaoPreenchimentoDosDadosTecnicosObrigatoriosDoContrato(String idContrato) {
		
		ContratoArt contrato = dao.getContratoPor(idContrato);
		List<String> mensagens = new ArrayList<String>();
		
		if(contrato.naoTemRamoArt()) mensagens.add("Ramos");
		
		
		
		contrato.setListCodigoAtividades(dao.getListaDeCodigosDasAtividadesDoContratoPor(idContrato));
		contrato.setListCodigoComplementos(dao.getListaDeCodigosDosComplementosDoContratoPor(idContrato));
		
		if(contrato.naoTemAtividades()) mensagens.add("Adicione pelo menos uma atividade");
		if(contrato.naoTemComplementos()) mensagens.add("Adicione pelo menos um complemento");

		if (contrato.artEhObraServico() || contrato.artEhMultipla()) {
			if(contrato.artEhMultipla()) {
				if(contrato.naoTemFinalidade()) mensagens.add("Finalidade");
			}
			contrato.setListCodigoEspecificacoes(dao.getListaDeCodigosDasEspecificacoesDoContratoPor(idContrato));
			if(contrato.naoTemEspecificacoes()) mensagens.add("Adicione pelo menos uma especificação");
			
			contrato.setQuantificacao(quantificacaoDao.getByIdContrato(idContrato));
			if(contrato.naoTemValorQuantificacao()) mensagens.add("Quantificação");
			if(contrato.naoTemUnidadeQuantificacao()) mensagens.add("Unidade");
			if(contrato.naoTemNumeroPavtos() && temRamoEExigePavimentos(contrato)) mensagens.add("Nº Pavimentos");
			
			if (validaRamoEletrotecnicaEComplemento(contrato)) mensagens.add("Complemento deve obrigatoriamente possuir o código 242.");
			
			if (verificaSeEhMetroQuadrado(contrato)) mensagens.add("Unidade de medida deve ser metro quadrado.");
			
			if (contrato.temEndereco()) {
				if (contrato.getEndereco().ufNaoEhRJ()) {
					if (contrato.temAtividades() && contrato.temComplementos()) {
						if(dao.complementoNaoEhPermitidoEnderecoDeOutroEstado(contrato) && atividadeNaoEhPermitidaEmOutroEstado(contrato)) {
							mensagens.add("Não é permitido realizar essa obra/serviço em outro estado.");
						}
					}
					
				}
			}
		}
		
		if(contrato.artEhDesempenhoDeCargoEFuncao()) {
			
			if(contrato.temAtividades() && contrato.temComplementos()) {
				
				if (selecionouAtividadeResponsavelTecnicoEQuadroTecnicoDaEmpresa(contrato)) {
					mensagens.add("As atividades 68 e 69 só poderão ser selecionadas separadamente.");
				} else if (selecionouAtividadeDesempenhoDeCargouEFuncaoTecnica(contrato)) {
					mensagens.add("As atividades 15 e 16 só poderão ser selecionadas separadamente.");
				} else if (selecionouComplementoRTeQT(contrato)) {
					mensagens.add("Os complementos 189 e 190 só poderão ser selecionados separadamente.");
				} else {
					if (possuiAtividadeResponsavelTecnicoEnaoPossuiComplementoResponsavelTecnico(contrato)) mensagens.add("Complemento deve obrigatoriamente possuir o código 189.");
					
					if (possuiAtividadeQuadroTecnicoEnaoPossuiComplementoQuadroTecnico(contrato)) mensagens.add("Complemento deve obrigatoriamente possuir o código 190.");
				}
				
			}
		}
		
		return mensagens;
	}
	private boolean atividadeNaoEhPermitidaEmOutroEstado(ContratoArt contrato) {
		
		if (ListUtils.verificaSeHaElementoComum(
				Arrays.asList(
						TipoAtividadeContratoArt.ANALISE.getId(),
						TipoAtividadeContratoArt.ASSESSORIA.getId(),
						TipoAtividadeContratoArt.AVALIACAO.getId(),
						TipoAtividadeContratoArt.CONSULTORIA.getId(),
						TipoAtividadeContratoArt.ELABORACAO_DE_ORCAMENTO.getId(),
						TipoAtividadeContratoArt.ESPECIFICACAO.getId(),
						TipoAtividadeContratoArt.ESTUDO.getId(),
						TipoAtividadeContratoArt.ESTUDO_DE_VIABILIDADE_TECNICO_ECONOMICA.getId(),
						TipoAtividadeContratoArt.EXECUCAO_DE_DESENHO_TECNICO.getId(),
						TipoAtividadeContratoArt.LAUDO_TECNICO.getId(),
						TipoAtividadeContratoArt.ORIENTACAO_TECNICA.getId(),
						TipoAtividadeContratoArt.PADRONIZACAO.getId(),
						TipoAtividadeContratoArt.PARECER_TECNICO.getId(),
						TipoAtividadeContratoArt.PESQUISA.getId(),
						TipoAtividadeContratoArt.PLANEJAMENTO.getId(),
						TipoAtividadeContratoArt.PROJETO.getId()
						),
				contrato.getListCodigoAtividades())) {
			return false;
		}
		return true;
	}

	private boolean selecionouAtividadeResponsavelTecnicoEQuadroTecnicoDaEmpresa(ContratoArt contrato) {
		if (ListUtils.verificaSeHaElementoComum(Arrays.asList(TipoAtividadeContratoArt.RESPONSAVEL_TECNICO_POR_EMPRESA.getId()), contrato.getListCodigoAtividades())) {
			if (ListUtils.verificaSeHaElementoComum(Arrays.asList(TipoAtividadeContratoArt.QUADRO_TECNICO_DA_EMPRESA.getId()), contrato.getListCodigoAtividades())) {
				return true;
			}
		}
		return false;
	}
	
	private boolean selecionouAtividadeDesempenhoDeCargouEFuncaoTecnica(ContratoArt contrato) {
		if (ListUtils.verificaSeHaElementoComum(Arrays.asList(TipoAtividadeContratoArt.DESEMPENHO_CARGO_TECNICO.getId()), contrato.getListCodigoAtividades())) {
			if (ListUtils.verificaSeHaElementoComum(Arrays.asList(TipoAtividadeContratoArt.DESEMPENHO_FUNCAO_TECNICA.getId()), contrato.getListCodigoAtividades())) {
				return true;
			}
		}
		return false;
	}
	
	private boolean selecionouComplementoRTeQT(ContratoArt contrato) {
		if (ListUtils.verificaSeHaElementoComum(Arrays.asList(TipoComplementoContratoArt.PROF_DO_QT_DA_EMPRESA.getId()), contrato.getListCodigoComplementos())) {
			if (ListUtils.verificaSeHaElementoComum(Arrays.asList(TipoComplementoContratoArt.RESP_POR_TODA_ATV_TEC_EXECUTADA_PELA_PJ.getId()), contrato.getListCodigoComplementos())) {
				return true;
			}
		}
		return false;
	}

	private boolean possuiAtividadeResponsavelTecnicoEnaoPossuiComplementoResponsavelTecnico(ContratoArt contrato) {
		if (ListUtils.verificaSeHaElementoComum(Arrays.asList(TipoAtividadeContratoArt.RESPONSAVEL_TECNICO_POR_EMPRESA.getId()), contrato.getListCodigoAtividades())) {
			if (!ListUtils.verificaSeHaElementoComum(Arrays.asList(TipoComplementoContratoArt.RESP_POR_TODA_ATV_TEC_EXECUTADA_PELA_PJ.getId()), contrato.getListCodigoComplementos())) {
				return true;
			}
		}
		return false;
	}

	private boolean possuiAtividadeQuadroTecnicoEnaoPossuiComplementoQuadroTecnico(ContratoArt contrato) {
		if (ListUtils.verificaSeHaElementoComum(Arrays.asList(TipoAtividadeContratoArt.QUADRO_TECNICO_DA_EMPRESA.getId()), contrato.getListCodigoAtividades())) {
			if(!ListUtils.verificaSeHaElementoComum(Arrays.asList(TipoComplementoContratoArt.PROF_DO_QT_DA_EMPRESA.getId()), contrato.getListCodigoComplementos())) {
				return true;
			}
		}
		return false;
	}
	
	private boolean validaRamoEletrotecnicaEComplemento(ContratoArt contrato) {
		if (contrato.temRamoArt()) {
			if (ListUtils.verificaSeHaElementoComum(Arrays.asList(2302L, 203L), Arrays.asList(contrato.getRamoArt().getId()))) {
				if (dao.profissionalPossuiAtribuicao90922(contrato.getArt().getProfissional().getId())) {
					return !ListUtils.verificaSeHaElementoComum(Arrays.asList(242L), contrato.getListCodigoComplementos());
				}
			}
		}
		return false;
	}

	private boolean verificaSeEhMetroQuadrado(ContratoArt contrato) {
		boolean temModalidadeCivil = dao.possuiRamoDaModalidadeCivil(contrato.getArt().getProfissional().getId());
		
		if (temModalidadeCivil && contrato.temAtividades() && contrato.temEspecificacoes() && contrato.temComplementos()) {
			contrato.setIdStringModalidade("1");
			boolean ehMetroQuadrado = dao.verificaSeEhMetroQuadrado(contrato);
			return ehMetroQuadrado && contrato.naoEhUnidadeDeMedidaMetroQuadrado();
		} else {
			return false;
		}
		
	}
	
	private boolean temRamoEExigePavimentos(ContratoArt contrato) {
		if (contrato.temRamoArt()) {
			if (ListUtils.verificaSeHaElementoComum(Arrays.asList(1101L, 1102L, 1203L, 1204L, 1301L), Arrays.asList(contrato.getRamoArt().getId()))) {
				return dao.ehNecessarioValidarPavimentos(contrato);
			}
		}
		return false;
	}

	public List<String> validaONaoPreenchimentoDoContratanteDoContrato(String idContrato) {
		ContratoArt contrato = dao.getContratoPor(idContrato);
		List<String> mensagens = new ArrayList<>();
		
		if(contrato.naoTemContratante()) mensagens.add("Favor efetuar busca do contratante");
		if(contrato.naoTemTipoContratante()) mensagens.add("Favor preencher o tipo do contratante");
		if(contrato.naoTemEnderecoContratante()) mensagens.add("Favor preencher o endereço completo do contratante, incluindo o campo número.");
		
		return mensagens;
	}
	
	
	public List<String> validaONaoPreenchimentoDoEnderecoDoProprietarioDoContrato(String idContrato) {
		ContratoArt contrato = dao.getContratoPor(idContrato);
		List<String> mensagens = new ArrayList<>();
		
		if(contrato.temProprietario()) {
			if(contrato.naoTemEnderecoProprietario()) mensagens.add("Favor preencher o endereço completo do proprietário, incluindo o campo número.");
		} else {
			mensagens.add("Favor preencher informar o proprietário");
		}
		
		return mensagens;
	}

	public List<String> validaONaoPreenchimentoDoEnderecoDoContrato(String idContrato) {
		ContratoArt contrato = dao.getContratoPor(idContrato);
		List<String> mensagens = new ArrayList<>();
		
		if(contrato.naoTemEndereco()) mensagens.add("Favor preencher o endereço completo do contrato, incluindo o campo número.");
		
		if (contrato.artEhDesempenhoDeCargoEFuncao()) {
			if (contrato.temEndereco()) {
				if (contrato.getEndereco().ufNaoEhRJ() && contrato.getEndereco().ufNaoEhExterior()) {
					mensagens.add("Não é permitido que endereço do contrato seja de outro estado. Caso tenha marcado unidade administrativa SEDE em 'Dados do Contrato', favor alterar para desbloquear a edição do endereço.");
				}
			}
		}
		
		return mensagens;
	}
	
	public boolean artDeParticipacaoTecnicaDeCoAutoriaOuCorresponsabilidadeNaoEhDaMesmaModalidade(DomainGenericDto dto) {
		if (dto.getId().equals(2L) || dto.getId().equals(3L)) {
			ContratoArt contratoArtParticipacaoTecnica = dao.getPrimeiroContratoPor(dto.getNome());
			if(contratoArtParticipacaoTecnica != null) {
				if (contratoArtParticipacaoTecnica.temRamoArt()) {
					if(!dto.getCodigo().equals(contratoArtParticipacaoTecnica.getRamoart().getId())) {
						return true;
					}
				}
			}
		}		
		
		return false;
	}

	public List<String> validaRegistrosDoProfissionalEEmpresaNoPeriodoDoContrato(String idContrato) {
		ContratoArt contratoArt = dao.getContratoPor(idContrato);
		List<String> mensagens = new ArrayList<>();
		
		// 1- estava registrado no inicio do contrato?
		if (contratoArt.getArt().getProfissional().temDataRegistro()) {
			if (DateUtils.primeiraDataeMaiorQueSegunda(contratoArt.getArt().getProfissional().getDataRegistro(), contratoArt.getDataInicio())) {
				mensagens.add("Profissional não estava registrado na data de início do contrato.");
			}
		}	
		
		// 2- tem historico de inatividade no inicio do contrato?
		if (historicoDao.possuiRegistroDeInatividadeNoInicioDaObraServicoDeAcordoCom(contratoArt.getArt().getProfissional().getIdPessoa(), contratoArt.getDataInicio())) {
			mensagens.add("Registro do Profissional não estava Ativo na data de início do contrato.");
		}
		
		if (contratoArt.artEhObraServico() || contratoArt.artEhMultipla()) {
			
			// 3- tem historico de inatividade durante a obra?
			if (historicoDao.possuiRegistroDeInatividadeDuranteAObraServicoDeAcordoCom(contratoArt.getArt().getProfissional().getIdPessoa(), contratoArt.getDataInicio(), contratoArt.getDataFim())) {
				mensagens.add("Registro do Profissional não estava Ativo durante a obra/serviço.");
			}
			
			// se houver empresa verificar registro da empresa
			if (contratoArt.getArt().temEmpresa()) {
				// 1- estava registrado no inicio da obra?
				if (contratoArt.getArt().getEmpresa().temDataExpedicaoRegistro()) {
					if (DateUtils.primeiraDataeMaiorQueSegunda(contratoArt.getArt().getEmpresa().getDataExpedicaoRegistro(), contratoArt.getDataInicio())) {
						mensagens.add("Empresa não estava registrada na data de início da obra.");
					}
				}
				// 2- tem historico de inatividade no inicio da obra?
				if (historicoDao.possuiRegistroDeInatividadeNoInicioDaObraServicoDeAcordoCom(contratoArt.getArt().getEmpresa().getIdPessoa(), contratoArt.getDataInicio())) {
					mensagens.add("Registro da Empresa não estava Ativo na data de início da obra/serviço.");
				}
				// 3- tem historico de inatividade durante a obra?
				if (historicoDao.possuiRegistroDeInatividadeDuranteAObraServicoDeAcordoCom(contratoArt.getArt().getEmpresa().getIdPessoa(), contratoArt.getDataInicio(), contratoArt.getDataFim())) {
					mensagens.add("Registro da Empresa não estava Ativo durante a obra/serviço.");
				}
				
				
				// 4- profissional faz parte do qt no inicio da obra?
				if (!quadroTecnicoDao.fazParteDoQuadroTecnicoNaData(contratoArt.getArt().getProfissional().getIdPessoa(), contratoArt.getArt().getEmpresa().getIdPessoa(), contratoArt.getDataInicio())) {
					mensagens.add("Profissional não vinculado à empresa no início da obra/serviço.");
				}
				// 5- profissional faz parte do qt durante a obra?
				if (!quadroTecnicoDao.fazParteDoQuadroTecnicoNoPeriodo(contratoArt.getArt().getProfissional().getIdPessoa(), contratoArt.getArt().getEmpresa().getIdPessoa(), contratoArt.getDataInicio(), contratoArt.getDataFim())) {
					mensagens.add("Profissional desvinculou-se da Empresa no período da execução da obra/serviço.");
				}
			}
			
			
		}
		
		return mensagens;
	}

	public boolean possuiSomenteOutrosNaListaDeEspecificacao(ContratoArt contrato) {
		List<Long> listaEspecificacoes = dao.getListaDeCodigosDasEspecificacoesDoContratoPor(contrato.getId());
		if(listaEspecificacoes != null) {
			return ListUtils.verificaSeTodosOsElementosSaoComuns(Arrays.asList(73L), listaEspecificacoes);
		}
		return false;
	}

	public boolean possuiSomenteOutrosNaListaDeComplementos(ContratoArt contrato) {
		List<Long> listaComplementos = dao.getListaDeCodigosDosComplementosDoContratoPor(contrato.getId());
		if(listaComplementos != null) {
			return ListUtils.verificaSeTodosOsElementosSaoComuns(Arrays.asList(175L), listaComplementos);
		}
		return false;
	}

	public List<String> validaFinalizarArt(String numeroArt) {
		Art art = artDao.getArtPorId(numeroArt);
		List<String> mensagens = new ArrayList<>();
		
		if(art.naoTemTipo()) mensagens.add("Forma de Registro");
		if(art.temTipoArt()) {
			if (art.tipoNaoEhInicial() && art.naoTemNumeroArtPrincipal()) mensagens.add("Número ART Principal");
		}
		
		if(art.naoTemProfissional()) mensagens.add("Responsável Técnico");
		
		if(!art.ehDesempenhoDeCargoFuncao()) {
			if(art.naoTemParticipacaoTecnica()) mensagens.add("Participação Técnica");
		}
		
		if(art.temParticipacaoTecnica()) {
			if (art.participacaoTecnicaNaoEhIndividual() && art.naoEscolheuPrimeiraParticipacaoTecnica()) mensagens.add("Responda a pergunta Primeira ART por participação técnica? No item PARTICIPAÇÃO TÉCNICA.");
			else if (art.participacaoTecnicaNaoEhIndividual() && art.naoTemNumeroArtParticipacaoTecnica()) mensagens.add("Número ART Participação Técnica");
		}
		
		if(art.naoTemFatoGerador()) mensagens.add("Motivo de Registro");
		if(art.temFatoGerador()) {
			if (art.fatoGeradorNecessitaDeDescricao() && art.naoTemDescricaoFatoGerador()) mensagens.add("Descrição Motivo de Registro");
		}
		
		if(art.naoTemEntidadeDeClasse()) mensagens.add("Entidade de Classe");
		
		
		if (art.temParticipacaoTecnica() && art.temNumeroARTParticipacaoTecnica()) {
			DomainGenericDto dto = new DomainGenericDto();
			dto.setId(art.getParticipacaoTecnica().getId());
			dto.setNome(art.getNumeroARTParticipacaoTecnica());
			if(artDeParticipacaoTecnicaDeCoAutoriaOuCorresponsabilidadeNaoEhDaMesmaModalidade(dto)) mensagens.add("O ramo selecionado não é igual ao da ART de participação técnica de Coautoria ou Corresponsabilidade indicada. Favor verificar a ART informada na participação técnica.");
		}
		
		return mensagens;
	}

	public List<String> validaPreenchimentoAcessibilidadeArbitragem(String idContrato) {
		ContratoArt contratoArt = dao.getContratoPor(idContrato);
		List<String> mensagens = new ArrayList<>();
		
		if (!contratoArt.artEhReceituarioAgronomico()) {
			if (!contratoArt.temAcessibilidade()) {
				mensagens.add("Você precisa preencher Acessibilidade!");
			}
		}
		
		if (!contratoArt.artEhReceituarioAgronomico() && !contratoArt.artEhDesempenhoDeCargoEFuncao()) {
			if (!contratoArt.temArbitragem()) {
				mensagens.add("Você precisa preencher Arbitragem!");
			}
		}
		
		return mensagens;
	}

}
