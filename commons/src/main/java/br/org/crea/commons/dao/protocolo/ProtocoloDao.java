package br.org.crea.commons.dao.protocolo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.dao.cadastro.empresa.RequerimentoPJDao;
import br.org.crea.commons.dao.cadastro.pessoa.InteressadoDao;
import br.org.crea.commons.dao.corporativo.Page;
import br.org.crea.commons.models.commons.Movimento;
import br.org.crea.commons.models.commons.ObservacoesMovimento;
import br.org.crea.commons.models.commons.Protocolo;
import br.org.crea.commons.models.commons.dtos.PesquisaGenericDto;
import br.org.crea.commons.models.commons.dtos.ProtocoloDto;
import br.org.crea.commons.models.commons.enuns.ModuloSistema;
import br.org.crea.commons.models.corporativo.enuns.TipoPessoa;
import br.org.crea.commons.models.corporativo.pessoa.IInteressado;
import br.org.crea.commons.models.protocolo.dtos.SubstituicaoProtocoloDto;
import br.org.crea.commons.models.protocolo.dtos.TramiteDto;
import br.org.crea.commons.models.protocolo.enuns.TipoJuntadaProtocoloEnum;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class ProtocoloDao extends GenericDao<Protocolo, Serializable> {

	@Inject HttpClientGoApi httpGoApi;
	
	@Inject RequerimentoPJDao requerimentoDao;
	
	@Inject InteressadoDao interessadoDao;
	
	public ProtocoloDao() {
		super(Protocolo.class);
	}

	public Protocolo getProtocoloBy(Long numero) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT P FROM Protocolo P ");
		sql.append("	WHERE P.numeroProtocolo = :numero ");

		try {
			TypedQuery<Protocolo> query = em.createQuery(sql.toString(), Protocolo.class);
			query.setParameter("numero", numero);
			
			return query.getSingleResult();
			
		}catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("ProtocoloDao || Get Protocolo By", StringUtil.convertObjectToJson(numero), e);
		}
		return null;
	}

	public List<Protocolo> getProtocoloByProcesso(Long numeroProcesso) {

		List<Protocolo> listProtocolo = new ArrayList<Protocolo>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT P FROM Protocolo P ");
		sql.append("	WHERE P.numeroProcesso = :processo");

		try {
			TypedQuery<Protocolo> query = em.createQuery(sql.toString(), Protocolo.class);
			query.setParameter("processo", numeroProcesso);
			
			listProtocolo = query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("ProtocoloDao || Get Protocolo By Processo", StringUtil.convertObjectToJson(numeroProcesso), e);
		}
		
		return listProtocolo;
	}

	public Integer buscaQuantidadeProtocolosBy(Long idPessoa) {

		Integer quantidadeProtocolo = null;

		StringBuilder sql = new StringBuilder();

		sql.append("SELECT COUNT (DISTINCT A.ID) FROM PRT_PROTOCOLOS A");
		sql.append("	WHERE A.ID IN (SELECT B.ID FROM PRT_PROTOCOLOS B WHERE B.FK_ID_PESSOAS = :idPessoa OR EXISTS ");
		sql.append("	(SELECT C.FK_ID_PROTOCOLOS FROM PRT_COINTERESSADOS C WHERE C.FK_ID_PESSOAS = :idPessoa )) ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idPessoa", idPessoa);

			BigDecimal resp = (BigDecimal) query.getSingleResult();
			quantidadeProtocolo = resp.intValueExact();

		} catch (Throwable e) {
			httpGoApi.geraLog("ProtocoloDao || Busca Quantidade Protocolos By", StringUtil.convertObjectToJson(idPessoa), e);
		}

		return quantidadeProtocolo;

	}

	public List<Movimento> getMovimentosBy(Long idProtocolo) {

		List<Movimento> movimentos = new ArrayList<Movimento>();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT M FROM Movimento M ");
		sql.append("	WHERE M.protocolo.idProtocolo = :idProtocolo ");
		sql.append("	ORDER BY M.dataEnvio DESC ");

		try {

			TypedQuery<Movimento> query = em.createQuery(sql.toString(), Movimento.class);
			query.setParameter("idProtocolo", idProtocolo);

			movimentos = query.getResultList();

		} catch (Throwable e) {
			httpGoApi.geraLog("ProtocoloDao || Get Movimentos By", StringUtil.convertObjectToJson(idProtocolo), e);
		}

		return movimentos;
	}

	public List<ObservacoesMovimento> getObservacoesMovimento(Long idMovimento) {

		List<ObservacoesMovimento> movimentos = new ArrayList<ObservacoesMovimento>();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT O FROM ObservacoesMovimento O ");
		sql.append("	WHERE O.movimento.id = :idMovimento ");

		try {

			TypedQuery<ObservacoesMovimento> query = em.createQuery(sql.toString(), ObservacoesMovimento.class);
			query.setParameter("idMovimento", idMovimento);

			movimentos = query.getResultList();

		}catch (NoResultException e) {
			return movimentos;
		}  catch (Throwable e) {
			httpGoApi.geraLog("ProtocoloDao || Get Observacoe Movimento", StringUtil.convertObjectToJson(idMovimento), e);
		}

		return movimentos;
	}

	public ObservacoesMovimento getAnexoObservacaoBy(Long idObservacao) {

		ObservacoesMovimento observacoesMovimento = new ObservacoesMovimento();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT O FROM ObservacoesMovimento O ");
		sql.append("	WHERE O.id = :idObservacao ");
		
		try {
			Query query = em.createQuery(sql.toString(), ObservacoesMovimento.class);
			query.setParameter("idObservacao", idObservacao);
			
			observacoesMovimento = (ObservacoesMovimento) query.getSingleResult();
		}catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("ProtocoloDao || Get Anexo Observacao By", StringUtil.convertObjectToJson(idObservacao), e);
		}
		
		return observacoesMovimento;

	}

	public List<Protocolo> getProtocolosByPessoa(Long idPessoa) {

		List<Protocolo> listProtocolo = new ArrayList<Protocolo>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT P FROM Protocolo P ");
		sql.append("	WHERE P.pessoa.id = :idPessoa ");

		try {
			TypedQuery<Protocolo> query = em.createQuery(sql.toString(), Protocolo.class);
			query.setParameter("idPessoa", idPessoa);
			listProtocolo = query.getResultList();
		}catch (NoResultException e) {
			return listProtocolo;
		}  catch (Throwable e) {
			httpGoApi.geraLog("ProtocoloDao || Get Protocolos By Pessoa", StringUtil.convertObjectToJson(idPessoa), e);
		}
		
		return listProtocolo;

	}
	
	public List<Protocolo> getProtocolosPaginadosByPessoa(PesquisaGenericDto pesquisa) {
		
		List<Protocolo> listProtocolo = new ArrayList<Protocolo>();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT P FROM Protocolo P ");
		sql.append("  WHERE P.pessoa.id = :idPessoa ");

		try {
			
			TypedQuery<Protocolo> query = em.createQuery(sql.toString(), Protocolo.class);
			query.setParameter("idPessoa", pesquisa.getIdPessoa());
			
			Page page = new Page(pesquisa.getPage(), pesquisa.getRows());
			page.paginate(query);
			
			listProtocolo = query.getResultList();
		} catch (NoResultException e) {
			return listProtocolo;
		} catch (Throwable e) {
			httpGoApi.geraLog("ProtocoloDao || getProtocolosPaginadosByPessoa", StringUtil.convertObjectToJson(pesquisa), e);
		}
		
		return listProtocolo;
	}
	public List<Protocolo> getListaFiltroProtocolosPaginado(PesquisaGenericDto pesquisa) {
		
		List<Protocolo> listProtocolo = new ArrayList<Protocolo>();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT P FROM Protocolo P ");
		sql.append("  WHERE P.pessoa.id = :idPessoa ");
		
		if(pesquisa.temProcesso()) {
			sql.append("	AND P.numeroProcesso = :processo");
		}
		if(pesquisa.temNumeroProtocolo()) {
			sql.append("	AND P.numeroProtocolo = :numeroProtocolo");
		}
		sql.append("  ORDER BY P.dataEmissao DESC ");

		try {
			TypedQuery<Protocolo> query = em.createQuery(sql.toString(), Protocolo.class);
			query.setParameter("idPessoa", pesquisa.getIdPessoa());
			
			if(pesquisa.temNumeroProtocolo()) {
				query.setParameter("numeroProtocolo",Long.parseLong(pesquisa.getNumeroProtocolo()));
			}
			if(pesquisa.temProcesso()) {
				query.setParameter("processo",Long.parseLong(pesquisa.getProcesso()));
			}
			
			Page page = new Page(pesquisa.getPage(), pesquisa.getRows());
			page.paginate(query);
			
			listProtocolo = query.getResultList();
		} catch (NoResultException e) {
			return listProtocolo;
		} catch (Throwable e) {
			httpGoApi.geraLog("ProtocoloDao || getListaFiltroProtocolosPaginado", StringUtil.convertObjectToJson(pesquisa), e);
		}
		
		return listProtocolo;
	}
	
	public int getTotalDeRegistrosDaPesquisaProtocolosByPessoa(PesquisaGenericDto pesquisa) {

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT P FROM Protocolo P      ");
		sql.append("  WHERE P.pessoa.id = :idPessoa ");

		try {
			TypedQuery<Protocolo> query = em.createQuery(sql.toString(), Protocolo.class);
			query.setParameter("idPessoa", pesquisa.getIdPessoa());
			return query.getResultList().size();
		} catch (NoResultException e) {
			return 0;
		}  catch (Throwable e) {
			httpGoApi.geraLog("ProtocoloDao || getTotalDeRegistrosDaPesquisaProtocolosByPessoa", StringUtil.convertObjectToJson(pesquisa), e);
		}
		
		return 0;
	}
	
	public List<Protocolo> getApensosDoProtocoloPor(Long numeroProtocolo) {
		List<Protocolo> listApensos = new ArrayList<Protocolo>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT P FROM Protocolo P ");
		sql.append("	WHERE P.idProtocoloPaiApenso = :numeroProtocolo ");

		try {
			TypedQuery<Protocolo> query = em.createQuery(sql.toString(), Protocolo.class);
			query.setParameter("numeroProtocolo", numeroProtocolo);

			listApensos = query.getResultList();
			
		} catch (NoResultException e) {
			return listApensos;
		} catch (Throwable e) {
			httpGoApi.geraLog("ProtocoloDao || getApensosDoProtocoloPor", StringUtil.convertObjectToJson(numeroProtocolo), e);
		}
		return listApensos;
	}
	
	public List<Protocolo> getAnexosDoProtocoloPor(Long numeroProtocolo) {
		List<Protocolo> listAnexos = new ArrayList<Protocolo>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT P FROM Protocolo P ");
		sql.append("	WHERE P.idProtocoloPaiAnexo = :numeroProtocolo ");

		try {
			TypedQuery<Protocolo> query = em.createQuery(sql.toString(), Protocolo.class);
			query.setParameter("numeroProtocolo", numeroProtocolo);

			listAnexos = query.getResultList();
			
		} catch (NoResultException e) {
			return listAnexos;
		} catch (Throwable e) {
			httpGoApi.geraLog("ProtocoloDao || getAnexosDoProtocoloPor", StringUtil.convertObjectToJson(numeroProtocolo), e);
		}
		return listAnexos;
	}
	
	public List<Protocolo> getListProtocolosEmTramite(List<TramiteDto> listDto) {
		List<Protocolo> listProtocolosEmTramite = new ArrayList<Protocolo>();

		try {
			
			for (TramiteDto dto : listDto) {
				
				Protocolo protocolo = getProtocoloBy(dto.getNumeroProtocolo());
				protocolo.setIdSituacaoTramite(dto.getIdSituacaoTramite());
				protocolo.setIdObservacaoTramite(dto.getIdObservacaoTramite());
				listProtocolosEmTramite.add(protocolo);
			}
			
		} catch (NoResultException e) {
			return listProtocolosEmTramite;
		} catch (Throwable e) {
			httpGoApi.geraLog("ProtocoloDao || getListProtocolosEmTramite", StringUtil.convertObjectToJson(listDto), e);
		}
		
		return listProtocolosEmTramite;
	}
	
	@SuppressWarnings("unchecked")
	public String getStatusTransacaoProtocolo(Long idStatusTransacao) {
		
		StringBuilder statusTransacao = new StringBuilder();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT S.descricao FROM prt_status_transacao S ");
		sql.append("	WHERE S.id = :idStatusTransacao ");

		try {
			
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idStatusTransacao", idStatusTransacao);
			query.getResultList().forEach(r -> statusTransacao.append("Código Status Transação: " + idStatusTransacao + " / " + r.toString()));
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ProtocoloDao || getStatusTransacaoProtocolo", StringUtil.convertObjectToJson(idStatusTransacao), e);
		}
		return statusTransacao.toString();
	}
	
	@SuppressWarnings("unchecked")
	public List<IInteressado> getListCoInteressadoPor(Long numeroProtocolo) {

		List<IInteressado> listCointeressados = new ArrayList<IInteressado>();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT P.FK_ID_PESSOAS ");
		sql.append("	FROM PRT_COINTERESSADOS P ");
		sql.append("	WHERE P.FK_ID_PROTOCOLOS = :numeroProtocolo ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("numeroProtocolo", numeroProtocolo);

			Iterator<Object> it = query.getResultList().iterator();
			
			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {
					
					Object[] result = (Object[]) it.next();
					Long idPessoa = ((BigDecimal)result[0]).longValue();

					listCointeressados.add( interessadoDao.buscaInteressadoBy(idPessoa) );
				}
			}

		}catch(NoResultException e){
			return listCointeressados;
		}catch (Throwable e) {
			httpGoApi.geraLog("ProtocoloDao || getListCoInteressadoPor", StringUtil.convertObjectToJson(numeroProtocolo), e);
		}

		return listCointeressados;

	}
	
	/**
	 * @author Monique Santos
	 * @since 04/2018
	 * 
	 * O método abaixo, bem como as rotinas auxiliares dele se propõe a determinar quais protocolos vinculados ao processo
	 * que devem ser anexados. Abaixo, está senda aplicada lógica para encontrar o protocolo principal para os casos
	 * onde numero de protocolo era diferente de numero de processo (protocolos antigos).
	 *  
	 * */
	public List<Protocolo> buscaProtocolosNaoJuntadosAoProcessoPrincipal(ProtocoloDto protocolo) {
		List<Protocolo> listProtocolosNaoJuntados = new ArrayList<Protocolo>();
		
		List<Protocolo> listProtocolosDoProcesso = getProtocoloByProcesso(protocolo.getNumeroProcesso());
		
		Long numeroProcesso = !processoPossuiMesmoNumeroProtocolo(protocolo) ? 
				getProtocoloInicialQuandoNumeroEProcessoSaoDiferentes(listProtocolosDoProcesso) : protocolo.getNumeroProtocolo();
		
		listProtocolosDoProcesso.forEach(p -> {
			
			if ( protocoloDeveSerJuntadoAoPrincipal(numeroProcesso, p) ) {
				listProtocolosNaoJuntados.add(p);
			}
		});		
				
		return listProtocolosNaoJuntados;
	}
	
	/**
	 * @author Monique Santos
	 * @since 04/2018
	 * 
	 * O método abaixo identifica dentre os protocolos do processo digital, quais protocolos foram originados 
	 * pelo usuário do portal e ainda permanecem físicos (não digitais).  
	 * Por hora a rotina está considerando somente protocolos dos assuntos baixa quadro técnico (2012) e segunda via carteira (1006), pois
	 * somente estes assuntos possuem texto para cadastrar documento no Docflow.
	 * 
	 * */
	public List<Protocolo> getListProtocolosVirtuaisProcessoDigital(ProtocoloDto processo) {
		List<Protocolo> listProtocoloPortal = new ArrayList<Protocolo>();
		List<Protocolo> listProtocolosVinculadosProcesso = buscaProtocolosNaoJuntadosAoProcessoPrincipal(processo);

		
		for (Protocolo protocoloVinculado : listProtocolosVinculadosProcesso) {
			
			if ( (!protocoloVinculado.isDigital() && protocoloVinculado.getIdFuncionario().equals(new Long(99990))) &&
				 (protocoloVinculado.assuntoEhBaixaQuadroTecnico() || protocoloVinculado.assuntoEhEntregaCarteira()) ) {
				 
				 listProtocoloPortal.add(protocoloVinculado);
			}
		}
		return listProtocoloPortal;
	}
	
	/**
	 * @author Monique Santos
	 * @since 04/2018
	 * 
	 * Para protocolos antigos quando não temos condições de identificar quem é o protocolo inicial (numero e processo iguais),
	 * estamos extraindo o mais antigo dentro da lista de protocolos do processo.
	 * */
	public Long getProtocoloInicialQuandoNumeroEProcessoSaoDiferentes(List<Protocolo> listProtocoloDoProcesso) {
		
		listProtocoloDoProcesso.sort((p1,p2) -> p1.getDataEmissao().compareTo(p2.getDataEmissao()) );
		return listProtocoloDoProcesso.get(0).getNumeroProtocolo();
	}
	
	public boolean protocoloEstaMarcadoParaInventario(Long numeroProtocolo) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT COUNT(*) FROM prt_sit_proto_inventarios P ");
		sql.append("	WHERE P.fk_id_protocolos = :numeroProtocolo ");
		sql.append("	AND   P.datainventariado is null ");

		Long resultado = new Long(0);
		
		try {
			
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("numeroProtocolo", numeroProtocolo);
			
			resultado = ((BigDecimal) query.getSingleResult()).longValue();
			
		}catch (NoResultException e) {
			return false;
		} catch (Throwable e) {
			httpGoApi.geraLog("ProtocoloDao || protocoloEstaMarcadoParaInventario", StringUtil.convertObjectToJson(numeroProtocolo), e);
		}
		
		return resultado > 0 ? true : false;
	}
	
	public boolean protocoloEmAnalisePossuiRequerimento(Long numeroProtocolo) {
		
		return requerimentoDao.buscaRequerimentosPor(numeroProtocolo).isEmpty() ? false : true;
		
	}
	
	/**
	 * @author Monique Santos 
	 * @since 04/2018
	 * 
	 * O método procura o número do protocolo inicial, sendo útil quando não é possível
	 * encontrar o processo comparando se o número de protocolo é igual a coluna processo (esta é a regra atual)
	 * Para protocolos antigos nem sempre número e processo eram iguais quando o processo era autuado.
	 * 
	 * Aqui já estão contempladas ambas situações (protocolos novos e antigos) e o número retornado corresponde de 
	 * fato ao protocolo inicial.
	 * 
	 * */
	public Protocolo getNumeroProtocoloInicial(ProtocoloDto protocolo) {
		List<Protocolo> listProtocolosDoProcesso = getProtocoloByProcesso(protocolo.getNumeroProcesso());
		
		Long protocoloInicial = !processoPossuiMesmoNumeroProtocolo(protocolo) ? 
				getProtocoloInicialQuandoNumeroEProcessoSaoDiferentes(listProtocolosDoProcesso) : protocolo.getNumeroProtocolo();
		
		return getProtocoloBy(protocoloInicial);		
	}

	public boolean verificaDigitalizacao(String protocolo) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT P FROM Protocolo P ");
		sql.append("	WHERE P.numeroProtocolo = :protocolo ");

		try {
			TypedQuery<Protocolo> query = em.createQuery(sql.toString(), Protocolo.class);
			query.setParameter("protocolo", new Long(protocolo));

			return query.getSingleResult().isDigital() ? true : false;
			
		}  catch (Throwable e) {
			httpGoApi.geraLog("ProtocoloDao || verificaDigitalizacao", StringUtil.convertObjectToJson(protocolo), e);
		}
		
		return false;
	}
	
	public boolean estaEmFinDivida(Long numeroProtocolo, Long natureza) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT COUNT(D) FROM FinDivida D ");
		sql.append("	WHERE D.identificadorDivida = :numeroProtocolo ");
		sql.append("	AND   D.natureza.id = :naturezaDivida ");
		sql.append("	AND   D.parcela = 0 ");
		
		try {
			
			Query query = em.createQuery(sql.toString());
			query.setParameter("numeroProtocolo", numeroProtocolo);
			query.setParameter("natureza", natureza);

			Long quantidadeDivida = (Long) query.getSingleResult();
			return quantidadeDivida > new Long(0) ? true : false;
			
		}  catch (Throwable e) {
			httpGoApi.geraLog("ProtocoloDao || protocoloEstaEmFinDivida", StringUtil.convertObjectToJson(numeroProtocolo), e);
		}
		
		return false;
	}
	
	/**
	 * @author Monique Santos
	 * Estão previstos dois tipos de juntada para vincular protocolos entre si: ANEXACAO ou APENSACAO.
	 * Método útil para gravar esta referência no protocolo que estiver sendo juntado.
	 * */
	public void juntarProtocolo(Long numeroProtocoloPrincipal, Long numeroProtocoloJuntada, TipoJuntadaProtocoloEnum tipoJuntada) {
		
		try {
			
			Protocolo protocoloJuntada = getProtocoloBy(numeroProtocoloJuntada);
			
			if( tipoJuntada.equals(TipoJuntadaProtocoloEnum.ANEXACAO) ) {
				protocoloJuntada.setIdProtocoloPaiAnexo(numeroProtocoloPrincipal);
			} else {
				protocoloJuntada.setIdProtocoloPaiApenso(numeroProtocoloPrincipal);  
			}
			update(protocoloJuntada);
			
		}  catch (Throwable e) {
			httpGoApi.geraLog("ProtocoloDao || juntarProtocolo", StringUtil.convertObjectToJson(numeroProtocoloJuntada), e);
		}
	}
	
	public void desanexarProtocolo(Long protocoloAnexo) {
		
		try {
			
			Protocolo protocoloDesanexado = getProtocoloBy(protocoloAnexo);
			protocoloDesanexado.setIdProtocoloPaiAnexo(null);
			update(protocoloDesanexado);
			
		}  catch (Throwable e) {
			httpGoApi.geraLog("ProtocoloDao || desanexarProtocolo", StringUtil.convertObjectToJson(protocoloAnexo), e);
		}
	}
	
	public void desapensarProtocolo(Long protocoloApenso) {
		
		try {
			
			Protocolo protocoloDesapensado = getProtocoloBy(protocoloApenso);
			protocoloDesapensado.setIdProtocoloPaiApenso(null);
			update(protocoloDesapensado);
			
		}  catch (Throwable e) {
			httpGoApi.geraLog("ProtocoloDao || desapensarProtocolo", StringUtil.convertObjectToJson(protocoloApenso), e);
		}
	}
	
	/**
	 * Grava o status da transação de acordo com a ação que está sendo executada (Ex anexação, apensação etc.)
	 * para que seja possível uma retomada de ação caso haja algum erro durante o processamento da funcionalidade.
	 * @param numeroProtocolo
	 */
	public void setStatusTransacaoProtocolos(Long numeroProtocolo, Long status) {

		try {

			Protocolo protocolo = getProtocoloBy(numeroProtocolo);
			protocolo.setIdStatusTransacao(status);
			update(protocolo);

		} catch (Throwable e) {
			httpGoApi.geraLog("ProtocoloDao || setStatusTransacaoProtocolos", StringUtil.convertObjectToJson(numeroProtocolo), e);
		}
	}
	
	public boolean protocoloDeveSerJuntadoAoPrincipal(Long numeroProcesso, Protocolo protocolo) {
		
		if ( !estaAnexado(protocolo) && !estaApensado(protocolo) && !protocolo.getNumeroProtocolo().equals(numeroProcesso) 
				 && !assuntoEhAberturaTomo(protocolo) && estaEmAndamento(protocolo) ) {
			
			return true;
			
		} else {
			
			return false;
		}
	}
	
	public boolean estaNoSiacol(Long numeroProtocolo) {
		
		return getProtocoloBy(numeroProtocolo).getUltimoMovimento().
			   getDepartamentoDestino().getModuloDepartamento().equals(ModuloSistema.SIACOL) ? true : false;
		
	}
	
	public boolean protocoloEstaJuntadoAoProtocoloPrincipal(Long numeroProtocoloPrincipal, Long numeroProtocoloJuntado) {
		
		Protocolo protocoloJuntado = getProtocoloBy(numeroProtocoloJuntado);
		Long protocoloPaiAnexo = protocoloJuntado.getIdProtocoloPaiAnexo();
		Long protocoloPaiApenso = protocoloJuntado.getIdProtocoloPaiApenso();
		
		return  (protocoloPaiAnexo != null && protocoloPaiAnexo.equals(numeroProtocoloPrincipal)) || 
				(protocoloPaiApenso != null && protocoloPaiApenso.equals(numeroProtocoloPrincipal)) ? true : false; 
	}
	
	public boolean estaApensado(Protocolo protocolo) {
		return protocolo.getIdProtocoloPaiApenso() == null ? false : true; 
	}
	
	public boolean estaAnexado(Protocolo protocolo) {
		return protocolo.getIdProtocoloPaiAnexo() == null ? false : true; 
	}
	
	public boolean processoPossuiMesmoNumeroProtocolo(ProtocoloDto processo) {
		return processo.getNumeroProtocolo().equals(processo.getNumeroProcesso()) ? true : false;
	}
	
	public boolean estaEmAndamento(Protocolo protocolo) {
		return !protocolo.isExcluido() && !protocolo.isFinalizado() ? true : false;
	}
	
	public boolean assuntoEhAberturaTomo(Protocolo protocolo) {
		
		Long idAssunto = protocolo.getAssunto().getId();
		
		if ( protocolo.getAssunto().getDescricao().contains("ABERTURA DE TOMO") ||
				idAssunto.equals(new Long(1239)  ) || 
				idAssunto.equals(new Long(10205) ) ||
				idAssunto.equals(new Long(10205) )) {
			 return true;
		}
		return false; 
	}
	
	public boolean hasProcessoInteressado(Long numeroProcesso, Long idInteressado){
				
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT P FROM Protocolo P ");
		sql.append("	WHERE P.numeroProcesso = :numeroProcesso ");
		sql.append("    AND P.interessado.id = :idInteressado ");
		
		try{
			TypedQuery<Protocolo> query = em.createQuery(sql.toString(), Protocolo.class);
			query.setParameter("numeroProcesso", numeroProcesso);
			query.setParameter("idInteressado", idInteressado);
			
			return query.getResultList().isEmpty() ? false : true;
			
		}catch(NoResultException e){
			return false;
		}catch (Exception e) {
			httpGoApi.geraLog("ProtocoloDao || hasProcessoInteressado", StringUtil.convertObjectToJson(numeroProcesso), e);
		}
		return false;
	}
	
	public boolean existeProcesso(Long numeroProcesso){
		List<Protocolo> lista = new ArrayList<Protocolo>();
				
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT P FROM Protocolo P ");
		sql.append("	WHERE P.numeroProcesso = :numeroProcesso ");
				
		try{
			TypedQuery<Protocolo> query = em.createQuery(sql.toString(), Protocolo.class);
			query.setParameter("numeroProcesso", numeroProcesso);
					
			lista = query.getResultList();
			
		}catch(NoResultException e){
			return false;
		}catch (Exception e) {
			httpGoApi.geraLog("ProtocoloDao || existeProcesso", StringUtil.convertObjectToJson(numeroProcesso), e);
		}
		
		return lista.isEmpty() ? false : true;
	}
	
	public List<Protocolo> getProtocoloAntecedente(Long idPessoa, Long idAssunto, TipoPessoa tipoPessoa) {

		List<Protocolo> protocolos = new ArrayList<Protocolo>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT p ");
		sql.append("	FROM Protocolo p ");
		sql.append("	WHERE p.pessoa.id = :idPessoa ");
		sql.append("	AND p.assunto.id = :idAssunto ");
		sql.append("	AND p.tipoPessoa = :tipoPessoa ");
		sql.append("	ORDER BY p.dataEmissao DESC ");

		TypedQuery<Protocolo> query = em.createQuery(sql.toString(), Protocolo.class);
		query.setParameter("idPessoa", idPessoa);
		query.setParameter("idAssunto", idAssunto);
		query.setParameter("tipoPessoa", tipoPessoa);

		try {
			protocolos = query.getResultList();
		} catch (NoResultException e) {
			return protocolos; 
		}  catch (Throwable e) {
			httpGoApi.geraLog("ProtocoloDao || getProtocoloAntecedente", StringUtil.convertObjectToJson(idPessoa), e);
		}

		return protocolos;

	}
	
	public void substituirProtocolo(SubstituicaoProtocoloDto dto) {
		
		try {
			
			Protocolo protocoloSubstituido = getProtocoloBy(dto.getProtocoloSubstituido().getNumeroProtocolo());
			protocoloSubstituido.setIdProtocoloSubstituto(dto.getProtocoloSubstituto().getNumeroProtocolo());
			protocoloSubstituido.setFinalizado(true);
			update(protocoloSubstituido);
			
		} catch (Exception e) {
			httpGoApi.geraLog("ProtocoloDao || substituirProtocolo", StringUtil.convertObjectToJson(dto), e);
		}
	}

	public Long getNumeroProtocoloOuProtocoloAnexado(Long numeroProtocolo) {
		
		Protocolo protocolo = new Protocolo();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT p ");
		sql.append("	FROM Protocolo p ");
		sql.append("	WHERE p.numeroProtocolo = :numeroProtocolo ");

		TypedQuery<Protocolo> query = em.createQuery(sql.toString(), Protocolo.class);
		query.setParameter("numeroProtocolo", numeroProtocolo);

		try {
			protocolo = query.getSingleResult();
		} catch (NoResultException e) {
			return null; 
		}  catch (Throwable e) {
			httpGoApi.geraLog("ProtocoloDao || getProtocoloAntecedente", StringUtil.convertObjectToJson(numeroProtocolo), e);
		}

		return protocolo.getIdProtocoloPaiAnexo() != null ? getBy(protocolo.getIdProtocoloPaiAnexo()).getNumeroProtocolo() : protocolo.getNumeroProtocolo();
	}
	public int getTotalDeProtocolos(PesquisaGenericDto pesquisa) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(P.ID) FROM PRT_PROTOCOLOS P");
		sql.append("  WHERE P.FK_ID_PESSOAS = :idPessoa ");
		
		if(pesquisa.temProcesso()) {
			sql.append("	AND P.PROCESSO = :processo");
		}
		if(pesquisa.temNumeroProtocolo()) {
			sql.append("	AND P.NUMERO = :numeroProtocolo");
		}

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idPessoa", pesquisa.getIdPessoa());
			
			if(pesquisa.temNumeroProtocolo()) {
				query.setParameter("numeroProtocolo",Long.parseLong(pesquisa.getNumeroProtocolo()));
			}
			if(pesquisa.temProcesso()) {
				query.setParameter("processo",Long.parseLong(pesquisa.getProcesso()));
			}
			BigDecimal resultado = (BigDecimal) query.getSingleResult();
			return resultado.intValue();
		

		} catch (Throwable e) {
			httpGoApi.geraLog("ProtocoloDao || getTotalDeProtocolos", StringUtil.convertObjectToJson(pesquisa), e);
		}

		return 0;
	}
	
}