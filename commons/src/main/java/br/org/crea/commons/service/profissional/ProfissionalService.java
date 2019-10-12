package br.org.crea.commons.service.profissional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.cadastro.profissional.EntidadeProfissionalConverter;
import br.org.crea.commons.converter.cadastro.profissional.ProfissionalConverter;
import br.org.crea.commons.converter.cadastro.profissional.TituloProfissionalConverter;
import br.org.crea.commons.dao.ModalidadeDao;
import br.org.crea.commons.dao.cadastro.AcoesDao;
import br.org.crea.commons.dao.cadastro.pessoa.HistoricoDao;
import br.org.crea.commons.dao.cadastro.pessoa.InteressadoDao;
import br.org.crea.commons.dao.cadastro.profissional.ProfissionalDao;
import br.org.crea.commons.models.cadastro.Acoes;
import br.org.crea.commons.models.cadastro.Evento;
import br.org.crea.commons.models.cadastro.Historico;
import br.org.crea.commons.models.cadastro.dtos.empresa.QuadroTecnicoDto;
import br.org.crea.commons.models.cadastro.dtos.profissional.EntidadeClasseDto;
import br.org.crea.commons.models.cadastro.dtos.profissional.EntidadeProfissionalDto;
import br.org.crea.commons.models.cadastro.dtos.profissional.ProfissionalDto;
import br.org.crea.commons.models.cadastro.dtos.profissional.ProfissionalHomologacaoDto;
import br.org.crea.commons.models.cadastro.dtos.profissional.TituloProfissionalDto;
import br.org.crea.commons.models.cadastro.dtos.profissional.ValidaEntidadeClasseDto;
import br.org.crea.commons.models.cadastro.enuns.TipoEvento;
import br.org.crea.commons.models.commons.dtos.DomainGenericDto;
import br.org.crea.commons.models.commons.dtos.GenericDto;
import br.org.crea.commons.models.commons.dtos.PesquisaGenericDto;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.models.commons.enuns.TipoAcaoEnum;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;
import br.org.crea.commons.models.corporativo.pessoa.Profissional;

public class ProfissionalService {
	
	@Inject	ProfissionalConverter profissionalConverter;
	
	@Inject EntidadeProfissionalConverter entidadeProfissionalConverter;
	
	@Inject TituloProfissionalConverter tituloConverter;
		
	@Inject ProfissionalDao profissionalDao;
	
	@Inject InteressadoDao interessadoDao;
	
	@Inject ModalidadeDao modalidadeDao;
	
	@Inject HistoricoDao historicoDao;
	
	@Inject AcoesDao acoesDao;
	
	public List<ProfissionalDto> buscaListProfissionalByNome(PesquisaGenericDto dto) {
		
		List<Profissional> listProfissional = new ArrayList<Profissional>();
		listProfissional = profissionalDao.buscaListProfissionalByNome(dto);
		
		return !listProfissional.isEmpty() ? profissionalConverter.toListDtoProfissional(listProfissional) : new ArrayList<ProfissionalDto>();
	}
	public int getTotalbuscaProfissionalByNome(PesquisaGenericDto pesquisa) {
		return profissionalDao.totalBuscaListProfissionalByNome(pesquisa);
	}
	
	public List<ProfissionalDto> buscaProfissionalByCPF(String numeroCPF) {
		
		List<Profissional> listProfissional = new ArrayList<Profissional>();
		listProfissional = profissionalDao.buscaProfissionalByCPF(numeroCPF);
		
		return !listProfissional.isEmpty() ? profissionalConverter.toListDtoProfissional(listProfissional) : new ArrayList<ProfissionalDto>();
	}
	
	
	public List<ProfissionalDto> buscaProfissionalByRegistro(String numeroRegistro) {
		
		List<Profissional> listProfissional = new ArrayList<Profissional>();
		listProfissional = profissionalDao.buscaProfissionalByRegistro(numeroRegistro);
		
		return !listProfissional.isEmpty() ? profissionalConverter.toListDtoProfissional(listProfissional) : new ArrayList<ProfissionalDto>();

	}
	
	
	public List<ProfissionalDto> buscaProfissionalByRNP(String numeroRNP) {
		
		List<Profissional> listProfissional = new ArrayList<Profissional>();
		listProfissional = profissionalDao.buscaProfissionalByRNP(numeroRNP);
		
		return !listProfissional.isEmpty() ? profissionalConverter.toListDtoProfissional(listProfissional) : new ArrayList<ProfissionalDto>();

	}
	
	public List<TituloProfissionalDto> buscaTitulosProfissionalPor(Long numeroRegistro) {
		return tituloConverter.toListTituloProfissionalDto(profissionalDao.buscaProfissionalPor(numeroRegistro.toString()));
	}

	public List<QuadroTecnicoDto> getQuadroTecnicoByIdProfissional(PesquisaGenericDto pesquisa) {
		return profissionalConverter.toListDtoQuadroTecnico(profissionalDao.getQuadroTecnicoByIdProfissional(pesquisa));
	}

	public List<ProfissionalDto> buscaDetalhadaProfissionalByRegistro(String numeroRegistro) {
		List<Profissional> listProfissional = new ArrayList<Profissional>();
		listProfissional.add(profissionalDao.buscaProfissionalPor(numeroRegistro));
		
		return !listProfissional.isEmpty() ? profissionalConverter.toListDtoProfissionalDetalhado(listProfissional) : new ArrayList<ProfissionalDto>();
	}


	

	/**
	 * Exporta uma relação de profissionais que terão titulos homologados em determinado período de uma determinada modalidade.
	 * O Sistema Siacol é dono deste processo uma vez que a relação será demandada pela data de reunião de cada Câmara que homologa os novos títulos
	 *
	 * @param codigoModalidade - Deve estar de acordo com a reunião da câmara que está homologando 
	 * 							 (Ex. Modalidade Civil -> Reunião Câmara Especializada de Engenharia Civil)
	 * 
	 * @param dataReuniao  - Data de realização da reunião (YYYY/MM/DD) 
	 * @return listResult
	 */
	public List<ProfissionalHomologacaoDto> buscaProfissionaisParaHomologacaoNoPeriodo(Long codigoModalidade, String dataReuniaoCamara) {
		return profissionalDao.getProfissionaisParaHomologacaoNoPeriodo(codigoModalidade, dataReuniaoCamara);
	}
	
	/** Responsável por atualizar a coluna rel_para_homologacao do titulo do profissional em cad_profxespec.
	 *  Uma vez que o profissional já foi listado para homologação e foi para pauta de reunião, não deve estar mais disponível 
	 *  para ser listado na próxima pauta da modalidade (modalidade -> Pautas de Câmaras Civil, Agronomia, Mecânica etc.)
	 *  
	 *  @author Monique Santos
	 *  @since 05/2018
	 *  @param codigoProfissional
	 *  @param codigoEspecialidade
	 * */
	public void atualizaProfissionaisParaHomologacao(List<ProfissionalHomologacaoDto>  listDto) {
		listDto.forEach(p -> profissionalDao.atualizaTitutoProfissionalHomologacao(p.getRegistro(), p.getEspecialidade().getId(), p.getDataUpdate()));
	}
	
	public void excluirHomologacaoProfissional(GenericDto dto) {
		profissionalDao.excluirHomologacaoProfissional(dto);
	}
	
	public boolean atualizarNumeroDocumentoHomologacao(GenericDto dto) {
		try {
			dto.setIdCodigo(modalidadeDao.getByIdDepartamento(dto.getIdDepartamento()).getCodigo());
			profissionalDao.atualizarNumeroDocumentoHomologacao(dto);
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}
	
	public boolean existeOpcaoDeVotoNoAnoAtual(UserFrontDto usuario) {		
		return profissionalDao.existeOpcaoDeVotoNoAnoAtual(usuario.getIdPessoa());
	}
	
	public Integer verificarSeHaModalidadesDoMesmoTipo(UserFrontDto usuario, TituloProfissionalDto dto) {
		return profissionalDao.verificaSeModalidadeEscolhidaExiste(dto.getIdModalidade(), usuario.getRegistro());
	}

	public Integer verificaSeTituloPodeSerHabilitado(UserFrontDto usuario, TituloProfissionalDto dto) {
		Integer entidadeClasse = profissionalDao.verificaSeTituloPodeSerHabilitado(dto.getIdModalidade(), usuario.getRegistro());
		return entidadeClasse;
	}

	public List<ProfissionalHomologacaoDto> recuperaProfissionaisDataModalidade(GenericDto dto) {
		return profissionalDao.recuperaProfissionaisDataModalidade(dto);
	}
	
	public DomainGenericDto participaCatalogoProfissional(DomainGenericDto dto) {
		profissionalDao.participaCatalogoProfissional(dto);
		historicoDao.create(populaHistoricoParticipaCatalogoProfissional(dto));
		acoesDao.create(populaAcaoParticipaCatalogoProfissional(dto));
		
		return dto;
	}
		
	private Historico populaHistoricoParticipaCatalogoProfissional(DomainGenericDto dto) {
		Historico historico = new Historico();
		
		Pessoa pessoa = new Pessoa();
		pessoa.setId(dto.getId());
		historico.setPessoa(pessoa);
		
		Evento evento = new Evento();
		evento.setId(TipoEvento.OPCAO_PELO_CATALOGO_DO_CREA_RJ.getId());
		historico.setEvento(evento);
		
		historico.setDataInicio(new Date());
		historico.setDataInclusao(historico.getDataInicio());
		historico.setMatriculaInclusao(99990L);
		historico.setOptLock(0L);
		historico.setObservacoes(dto.isChecked() ? "Incluído no Catálogo" : "Removido do Catálogo");
		
		return historico;
	}
	
	private Acoes populaAcaoParticipaCatalogoProfissional(DomainGenericDto dto) {
		Acoes acao = new Acoes();
		
		acao.setIdPessoa(dto.getId());
		acao.setTipoAcao(dto.isChecked() ? TipoAcaoEnum.INCLUIDO_NO_CATALOGO_PROFISSIONAL.getId() : TipoAcaoEnum.EXCLUIDO_DO_CATALOGO_PROFISSIONAL.getId());
		acao.setDataAcao(new Date());
		acao.setDescricao(dto.isChecked() ? "Incluído no Catálogo" : "Removido do Catálogo");
		acao.setFuncionario(99990L);
		
		return acao;
	}
	
	public boolean getParticipaCatalogoProfissional(Long idProfissional) {
		return profissionalDao.getParticipaCatalogoProfissional(idProfissional);
	}
	
	public void habilitartituloProfissional(UserFrontDto usuario, TituloProfissionalDto dto) {
		if (profissionalDao.habilitartituloProfissional(dto.getCodigoTituloCrea(), usuario.getRegistro())) {
			profissionalDao.desabilitaTitulosNaoSelecionados(dto.getCodigoTituloCrea(), usuario.getRegistro());
		};
	}
	
	public ValidaEntidadeClasseDto validaEntidadesFiliadas(Long idProfissional) {
		return entidadeProfissionalConverter.toListValidadaDto(profissionalDao.getListaEntidadesFiliadas(idProfissional));
	}

	public String podeHabilitarOpcaoVoto(EntidadeProfissionalDto entidadeProfissionalSelecionada) {
		String mensagemErro = null;
		
		EntidadeProfissionalDto tituloAtivoOpcaoVoto = profissionalDao.possuiTituloProfissionalAtivo(entidadeProfissionalSelecionada);
		if (tituloAtivoOpcaoVoto != null) {
			
			if (profissionalDao.entidadeNaoPodeSerHabilitada(populaEntidadeProfissionalDto(entidadeProfissionalSelecionada, tituloAtivoOpcaoVoto))) {
				mensagemErro = " A entidade escolhida não possui a mesma modalidade do Título habilitado.";
			}
		} else {
			if (profissionalDao.entidadeNaoPodeSerHabilitada(populaEntidadeProfissionalDto(entidadeProfissionalSelecionada, tituloAtivoOpcaoVoto))) {
				mensagemErro = " Nenhum dos Títulos possui a mesma modalidade da entidade escolhida. ";
			}
		}
		habilitarEntidadeEscolhida(entidadeProfissionalSelecionada);
		
		
		return mensagemErro;
	}
	public EntidadeProfissionalDto populaEntidadeProfissionalDto(EntidadeProfissionalDto entidadeProfissionalSelecionada,EntidadeProfissionalDto tituloAtivoOpcaoVoto) {
			EntidadeProfissionalDto combinacao = new EntidadeProfissionalDto();
			
			EntidadeClasseDto entidade = new EntidadeClasseDto();
			entidade.setId(entidadeProfissionalSelecionada.getEntidadeClasse().getId());
			
			if (tituloAtivoOpcaoVoto != null) {
				if(tituloAtivoOpcaoVoto.temCodigoEspecialidade()) {
					combinacao.setCodigoEspecialidade(tituloAtivoOpcaoVoto.getCodigoEspecialidade());
				}
				if(tituloAtivoOpcaoVoto.temCodigoModalidade()) {
					combinacao.setCodigoModalidade(tituloAtivoOpcaoVoto.getCodigoModalidade());	
				}
			}
			combinacao.setIdProfissional(entidadeProfissionalSelecionada.getIdProfissional());
			combinacao.setEntidadeClasse(entidade);
			return combinacao;
		}
	
	public void habilitarEntidadeEscolhida(EntidadeProfissionalDto dto) {
		if (profissionalDao.habilitarEntidadeEscolhida(dto)) {
			profissionalDao.desabilitarEntidadeEscolhida(dto);
		};
	}
}
