package br.org.crea.commons.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.siacol.ReuniaoSiacolDao;
import br.org.crea.commons.models.cadastro.Documento;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;
import br.org.crea.commons.validsigner.enums.TipoDocumentoAssinaturaEnum;

@Stateless
public class DocumentoDao extends GenericDao<Documento, Serializable>{

	@Inject HttpClientGoApi httpGoApi;
	
	@Inject ReuniaoSiacolDao reuniaoSiacolDao;
	
	public DocumentoDao() {
		super(Documento.class);
	}

	public List<Documento> recuperaDocumentosByNumeroProtocolo(Long numeroProtocolo) {
		
		List<Documento> listProtocolo = new ArrayList<Documento>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT D FROM Documento D ");
		sql.append("	WHERE D.protocolo = :numeroProtocolo ");
		sql.append("	AND D.statusDocumento.id in (1,8) ");
		sql.append("   	ORDER BY D.dataCriacao");

		try {
			TypedQuery<Documento> query = em.createQuery(sql.toString(), Documento.class);
			query.setParameter("numeroProtocolo", numeroProtocolo);
			
			listProtocolo = query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("DocumentoDao || recuperaDocumentosByNumeroProtocolo", StringUtil.convertObjectToJson(numeroProtocolo), e);
		}
		
		return listProtocolo;
	
	}
	

	public List<Documento> getDocumentosByIdTipoDocumento(Long idTipoDocumento) {
		
		List<Documento> listProtocolo = new ArrayList<Documento>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT D FROM Documento D ");
		sql.append("	WHERE D.tipo.id = :idTipoDocumento ");
		sql.append("	AND D.statusDocumento.id in (1,8) ");
		sql.append("   	ORDER BY D.dataCriacao");

		try {
			TypedQuery<Documento> query = em.createQuery(sql.toString(), Documento.class);
			query.setParameter("idTipoDocumento", idTipoDocumento);
			
			listProtocolo = query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("DocumentoDao || getDocumentosByIdTipoDocumento", StringUtil.convertObjectToJson(idTipoDocumento), e);
		}
		
		return listProtocolo;
	
	}
	
	

	public void excluiDocumento(Long idDocumento) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE  CAD_DOCUMENTO S ");
		sql.append("     SET S.FK_STATUS_DOCUMENTO = 6 ");
		sql.append("	 WHERE S.ID = :idDocumento ");

		try {
			
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idDocumento", idDocumento);
			
			query.executeUpdate();
		} catch (Throwable e) {
			httpGoApi.geraLog("DocumentoDao || excluiDocumento", StringUtil.convertObjectToJson(idDocumento), e);
		}
		
	}
	
	
	
	public List<Documento> getDocumentosPorTipoENumeroProtocolo(Long tipo, Long numeroProtocolo) {
		
		List<Documento> listDocumentos = new ArrayList<Documento>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT D FROM Documento D ");
		sql.append("	WHERE D.tipo.id = :tipo ");
		sql.append("	AND D.statusDocumento.id in (1,8) ");
		sql.append("	AND D.protocolo = :numeroProtocolo) ");
		sql.append("   	ORDER BY D.dataCriacao ");

		try {
			TypedQuery<Documento> query = em.createQuery(sql.toString(), Documento.class);
			query.setParameter("numeroProtocolo", numeroProtocolo);
			query.setParameter("tipo", tipo);
			
			listDocumentos = query.getResultList();
		} catch (NoResultException e) {
			return listDocumentos;
		} catch (Throwable e) {
			httpGoApi.geraLog("DocumentoDao || getDocumentosPorTipoENumeroProtocolo", StringUtil.convertObjectToJson(numeroProtocolo), e);
		}
		
		return listDocumentos;
	}

	public Documento recuperaByProtocoloDocumento(Long numeroProtocolo, Long idTipoDocumento) {
		List<Documento> listProtocolo = new ArrayList<Documento>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT D FROM Documento D ");
		sql.append("	WHERE D.protocolo = :numeroProtocolo ");
		sql.append("	AND D.tipo.id = :idTipoDocumento ");
		sql.append("	AND D.statusDocumento.id in (1,8) ");
		sql.append("   	ORDER BY D.dataCriacao DESC");

		try {
			
			TypedQuery<Documento> query = em.createQuery(sql.toString(), Documento.class);
			query.setParameter("numeroProtocolo", numeroProtocolo);
			query.setParameter("idTipoDocumento", idTipoDocumento);
			
			listProtocolo = query.getResultList();
			
			return listProtocolo.get(0);
			
		} catch (Throwable e) {
			httpGoApi.geraLog("DocumentoDao || getByIdProtocoloByIdDocumento", StringUtil.convertObjectToJson(numeroProtocolo + " -- " + idTipoDocumento ), e);
		}
		
		return null;
	}

	public Documento recuperaDocumentosById(Long id) {				
		
		Documento documento = new Documento();		
		StringBuilder sql = new StringBuilder();		
		sql.append("SELECT D FROM Documento D ");		
		sql.append("	WHERE D.id = :id ");	
				
		try {		
			TypedQuery<Documento> query = em.createQuery(sql.toString(), Documento.class);	
			query.setParameter("id", id);	
				
			documento = query.getSingleResult();	
		} catch (Throwable e) {		
			httpGoApi.geraLog("DocumentoDao || recuperaDocumentosById", StringUtil.convertObjectToJson(id), e);	
		}		
				
		return documento;		
				
	}

	public void atualizaReferenciaDoArquivoNoDocumento(Long idArquivo, Long idDocumento) {
		
		StringBuilder sql = new StringBuilder();		
		sql.append(" UPDATE CAD_DOCUMENTO D           ");
		sql.append("    SET D.FK_ARQUIVO = :idArquivo ");
		sql.append("	WHERE D.ID = :idDocumento     ");	
				
		try {		
			Query query = em.createNativeQuery(sql.toString());	
			query.setParameter("idArquivo", idArquivo);
			query.setParameter("idDocumento", idDocumento);	
				
			query.executeUpdate();	
		} catch (Throwable e) {		
			httpGoApi.geraLog("DocumentoDao || atualizaArquivoNoDocumento", StringUtil.convertObjectToJson(idArquivo + " -- " + idDocumento), e);	
		}		
				
	}	
	
	public void atualizaReferenciaDoDocflowNoDocumento(Long idDocumento, String codigoDocflow, String protocoloDocflow) {
		
		StringBuilder sql = new StringBuilder();		
		sql.append(" UPDATE CAD_DOCUMENTO D  				  		");
		sql.append("    SET D.CODIGO_EXTERNO = :codigoDocflow, 		");
		sql.append("		D.PROTOCOLO_DOCFLOW = :protocoloDocflow ");
		sql.append("	WHERE D.ID = :idDocumento     		  		");	
				
		try {
			
			Query query = em.createNativeQuery(sql.toString());	
			query.setParameter("codigoDocflow", codigoDocflow);
			query.setParameter("idDocumento", idDocumento);	
			query.setParameter("protocoloDocflow", protocoloDocflow);	
			query.executeUpdate();
			
		} catch (Throwable e) {		
			httpGoApi.geraLog("DocumentoDao || atualizaReferenciaDoDocflowNoDocumento", StringUtil.convertObjectToJson(idDocumento + " -- " + codigoDocflow), e);	
		}		
				
	}	
	
	/**
	 * Método útil para verificar a existência de um documento a partir de um id.
	 * O id informado pode ser tanto da pk de documento ou da fk de arquivo relacionada na entidade 'documento',
	 * dependendo do tipo de documento que está sendo submetido para assinatura.
	 *
	 * Criado para apoiar o processo de assinatura.
	 * @author Monique Santos
	 * @return true/false
	 * */
	public boolean documentoParaAssinaturaExiste(Long id, TipoDocumentoAssinaturaEnum tipoDocumentoAssinatura) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT COUNT(D) FROM Documento D ");
		
		if(tipoDocumentoAssinatura.equals(TipoDocumentoAssinaturaEnum.DOCUMENTO_JSON)) {
			sql.append("  WHERE D.id = :id ");
		} else if(tipoDocumentoAssinatura.equals(TipoDocumentoAssinaturaEnum.ARQUIVO_FILE_SYSTEM)) {
			sql.append("  WHERE D.arquivo.id = :id ");
		}
		
		try {
			
			Query query = em.createQuery(sql.toString());
			query.setParameter("id", id);
			Long countDocumento = (Long) query.getSingleResult();
			return countDocumento > new Long(0);
			
		} catch (NoResultException e) {
			return false;
		} catch (Throwable e) {
			httpGoApi.geraLog("DocumentoDao || documentoParaAssinaturaExiste", StringUtil.convertObjectToJson(id), e);	
		}
		return false;
	}
	
	/**
	 * Salva a chave para recuperar selo de assinatura gerado na primeira etapa do processo de assinatura
	 * com certificado digital da Valid Certificadora
	 * */
	public void atualizaChaveAssinaturaRedisNoDocumento(Long idDocumento, String chaveRedis) {

		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE CAD_DOCUMENTO D ");
		sql.append("     SET D.KEY_ASS_REDIS = :chaveRedis ");
		sql.append("	 WHERE D.ID = :idDocumento ");
		
		try {
			
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idDocumento", idDocumento);
			query.setParameter("chaveRedis", chaveRedis);
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("DocumentoDao || atualizaChaveAssinaturaRedisNoDocumento", StringUtil.convertObjectToJson("idDocumento: " + idDocumento + " keyRedis: "+ chaveRedis ), e);
		}
		
	}

	public Documento getByNumeroDocumento(String numeroDecisao) {
			
		Documento documento = new Documento();		
		StringBuilder sql = new StringBuilder();		
		sql.append("SELECT D FROM Documento D ");		
		sql.append("	WHERE D.numeroDocumento like '" + numeroDecisao + "' " );	
				
		try {		
			TypedQuery<Documento> query = em.createQuery(sql.toString(), Documento.class);	
//			query.setParameter("numeroDocumento", numeroDecisao);	
				
			documento = query.getSingleResult();	
		} catch (Throwable e) {		
			httpGoApi.geraLog("DocumentoDao || getByNumeroDocumento", StringUtil.convertObjectToJson(numeroDecisao), e);	
		}		
				
		return documento;		
				
	}
}
