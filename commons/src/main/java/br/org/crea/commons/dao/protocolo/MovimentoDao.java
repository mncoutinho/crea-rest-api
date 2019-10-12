package br.org.crea.commons.dao.protocolo;

import java.io.Serializable;
import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.dao.cadastro.domains.DepartamentoDao;
import br.org.crea.commons.dao.siacol.ProtocoloSiacolDao;
import br.org.crea.commons.factory.AuditaProtocoloFactory;
import br.org.crea.commons.models.commons.Movimento;
import br.org.crea.commons.models.commons.Protocolo;
import br.org.crea.commons.models.commons.dtos.ProtocoloDto;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.models.commons.enuns.ModuloSistema;
import br.org.crea.commons.models.protocolo.dtos.TramiteDto;
import br.org.crea.commons.models.siacol.ProtocoloSiacol;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.DateUtils;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class MovimentoDao extends GenericDao<Movimento, Serializable> {

	@Inject HttpClientGoApi httpGoApi;
	
	@Inject DepartamentoDao departamentoDao;
	
	@Inject ProtocoloDao protocoloDao;
	
	@Inject SituacaoProtocoloDao situacaoDao;
	
	@Inject ObservacaoMovimentoDao observacaoMovimentoDao;
	
	@Inject ProtocoloSiacolDao protocoloSiacolDao;
	
	@Inject AuditaProtocoloFactory auditFactory;
	
	public MovimentoDao() {
		super(Movimento.class);
	}

	public Movimento gerarMovimentoProtocolo(TramiteDto dto, Long numeroProtocolo) {
		Movimento movimento = new Movimento();
		
		try {
			
			movimento.setDataEnvio(new Date());
			movimento.setIdFuncionarioRemetente(dto.getFuncionarioTramite().getId());
			movimento.setDepartamentoOrigem(departamentoDao.getBy(dto.getUltimoMovimento().getIdDepartamentoDestino()));
			movimento.setDepartamentoDestino(departamentoDao.getBy(dto.getIdDepartamentoDestino()));
			movimento.setProtocolo(protocoloDao.getProtocoloBy(numeroProtocolo));
			movimento.setSituacao(situacaoDao.getSituacaoByCodigo(dto.getIdSituacaoTramite()));
			movimento.setDespachado(false);
			movimento.setTempoPermanencia(0L);
			
			if(destinoMovimentoEhArquivoVirtual(dto.getIdDepartamentoDestino())) {
				
				movimento.setDataRecebimento(new Date());
				movimento.setIdFuncionarioReceptor(new Long(9000L));
				movimento.getProtocolo().setFinalizado(true);
			}
			
			this.calculaTempoProtocoloNoDepartamentoAnterior(movimento, new Date());
			create(movimento);
			
			Protocolo protocolo = protocoloDao.getProtocoloBy(numeroProtocolo);
			protocolo.setUltimoMovimento(movimento);
			protocoloDao.update(protocolo);
			
		} catch (Throwable e) {
			httpGoApi.geraLog("MovimentoDao || gerarMovimentoProtocolo", StringUtil.convertObjectToJson(dto), e);
		}
		
		return movimento;
	}
	
	public void movimentarAnexosDoProtocoloEmTramite(TramiteDto dto, ModuloSistema modulo, UserFrontDto usuario) {
		
		for(ProtocoloDto anexo : dto.getListAnexos()) {
			gerarMovimentoProtocolo(dto, anexo.getNumeroProtocolo());
			
			if (modulo.equals(ModuloSistema.SIACOL)) {
				ProtocoloSiacol protocoloSiacol = protocoloSiacolDao.getProtocoloBy(dto.getNumeroProtocolo());
				if (protocoloSiacol != null) {
					
					//FIXME auditoria anexo e apenso
					if (!dto.isPossuiErros()) auditFactory.auditaEnvioRecebimentoAnexo(dto, protocoloSiacol, usuario, modulo);
				}
			}
		}
	}
	
	public void movimentarApensosDoProtocoloEmTramite(TramiteDto dto, ModuloSistema modulo, UserFrontDto usuario) {
		
		for(ProtocoloDto apenso : dto.getListApensos()) {
			gerarMovimentoProtocolo(dto, apenso.getNumeroProtocolo());
			
			if (modulo.equals(ModuloSistema.SIACOL)) {
				ProtocoloSiacol protocoloSiacol = protocoloSiacolDao.getProtocoloBy(dto.getNumeroProtocolo());
				if (protocoloSiacol != null) {
					
					if (!dto.isPossuiErros()) auditFactory.auditaEnvioRecebimentoApenso(dto, protocoloSiacol, usuario, modulo);
				}
			}
		}
	}
	
	public Movimento buscaPenultimoMovimentoProtocolo(Long numeroProtocolo, Date dataUltimoMovimento) {
		Movimento movimento = new Movimento();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT M FROM Movimento M ");
		sql.append("	WHERE M.protocolo.id = :numeroProtocolo ");
		sql.append("	AND   M.dataEnvio < :dataUltimoMovimento ");
		sql.append("	ORDER BY M.dataEnvio DESC ");
		
		try {

			TypedQuery<Movimento> query = em.createQuery(sql.toString(), Movimento.class);
			query.setParameter("numeroProtocolo", numeroProtocolo);
			query.setParameter("dataUltimoMovimento", dataUltimoMovimento);
			query.setMaxResults(1);
			
			movimento = query.getSingleResult();
			
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("MovimentoDao || buscaPenultimoMovimentoProtocolo", StringUtil.convertObjectToJson(numeroProtocolo), e);
		}
		
		return movimento;
		
	}
	
	public Movimento buscaUltimoMovimentoPor(Long numeroProtocolo) {
		Movimento movimento = new Movimento();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT M FROM Movimento M ");
		sql.append("	WHERE M.protocolo.id = :numeroProtocolo ");
		sql.append("	ORDER BY M.dataEnvio DESC, m.id DESC ");
		
		try {

			TypedQuery<Movimento> query = em.createQuery(sql.toString(), Movimento.class);
			query.setParameter("numeroProtocolo", numeroProtocolo);
			query.setMaxResults(1);
			
			movimento = query.getSingleResult();
			
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("MovimentoDao || buscaUltimoMovimentoPor", StringUtil.convertObjectToJson(numeroProtocolo), e);
		}
		return movimento;
	}
	
	public Movimento recebeUltimoMovimento(Long idMovimento, Long idFuncionarioReceptor) {
		
		try {
			
			Movimento movimento = getBy(idMovimento);
			movimento.setDataRecebimento(new Date());
			movimento.setIdFuncionarioReceptor(idFuncionarioReceptor);
			update(movimento);
			return movimento;
			
		} catch (Throwable e) {
			httpGoApi.geraLog("MovimentoDao || recebeUltimoMovimento", StringUtil.convertObjectToJson(idMovimento), e);
		}
		return null;		
	}

	@SuppressWarnings("null")
	public void calculaTempoProtocoloNoDepartamentoAnterior(Movimento movimento, Date dataMovimentacao){

		try {
			
			Movimento penultimoMovimento = buscaPenultimoMovimentoProtocolo(movimento.getProtocolo().getIdProtocolo(), dataMovimentacao);
			
			if (penultimoMovimento != null){
				
				Date dataInicio = new Date();
				dataInicio.setTime(penultimoMovimento.getDataEnvio().getTime());
				
				Date dataFinal = new Date();
				dataFinal.setTime(dataMovimentacao.getTime());
				
				penultimoMovimento.setTempoPermanencia(DateUtils.getDiferencaDiasEntreDatas(dataFinal, dataInicio));
				update(penultimoMovimento);
				
			}else{			
				penultimoMovimento.setTempoPermanencia(new Long(0));
				update(penultimoMovimento);
			}		
			
		} catch (Throwable e) {
			httpGoApi.geraLog("MovimentoDao || calculaTempoProtocoloNoDepartamentoAnterior", StringUtil.convertObjectToJson(movimento), e);
		}
	}
	
	public boolean destinoMovimentoEhArquivoVirtual(Long idDepartamentoDestino) {
		return idDepartamentoDestino.equals(new Long(23040502)) ? true : false;
	}
	
	/**
	 * Método para criar um protocolo e gerar o seu primeiro movimento.
	 * Criado no mesmo escopo para utilizar a mesma instancia do Entity Manager, para não manter gravado no banco o protocolo, caso haja alguma exceção.
	 * Como não é um mapeamento bi-direcional, os objetos foram persistidos separadamente.
	 * Cria o Protocolo e depois seta o mesmo em uma nova instância de movimento, que será persistido posteriormente.
	 * Após o movimento ser criado, atualiza os campos primeiro e ultimo movimento do protocolo criado anteriormente.
	 * @param movimento
	 * @param protocolo
	 */
	public Protocolo cadastrarPrimeiroMovimento(Movimento movimento, Protocolo protocolo) {
		try{		
			movimento.setProtocolo(protocolo);
			protocolo.setPrimeiroMovimento(movimento);
			protocolo.setUltimoMovimento(movimento);
			
			em.persist(protocolo);
			em.persist(movimento);
			
			em.merge(protocolo);
		}catch(Exception e){
			httpGoApi.geraLog("MovimentoDao || cadastrarPrimeiroMovimento", StringUtil.convertObjectToJson(movimento), e);
		}
		
		return protocolo;
	}
}