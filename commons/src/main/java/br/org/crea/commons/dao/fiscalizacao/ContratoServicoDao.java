package br.org.crea.commons.dao.fiscalizacao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.art.dtos.ContratoServicoDto;
import br.org.crea.commons.models.art.dtos.PesquisaContratoDto;
import br.org.crea.commons.models.fiscalizacao.ContratoServico;
import br.org.crea.commons.models.fiscalizacao.enuns.TipoAtividade;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class ContratoServicoDao extends GenericDao<ContratoServico, Serializable>{

	@Inject HttpClientGoApi httpGoApi;
	
	public ContratoServicoDao() {
		super(ContratoServico.class);
	}

	public List<ContratoServico> getServicosPorContratante(PesquisaContratoDto dto) {
		List<ContratoServico> resultado = new ArrayList<ContratoServico>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT C FROM ContratoServico C ");
		sql.append("            WHERE C.contratante.id = :idContratante");
		
		if(dto.getTipoPesquisa() == 2){
			sql.append("            and C.ativo = 1");
		}
		
		sql.append("            order by C.ativo, C.dataAtualizacao, C.atividade.descricao");
		try{
			TypedQuery<ContratoServico> query = em.createQuery(sql.toString(), ContratoServico.class);
			query.setParameter("idContratante", dto.getIdContratante());
		
			resultado = query.getResultList();
			return resultado;
			
		} catch (NoResultException e ){
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoServicoDao || Get Servicos Por Contratante", StringUtil.convertObjectToJson(dto), e);
		}	
		
		return resultado;
	}

	public ContratoServico salvar(ContratoServico model) {
		try{
			create(model);
		}catch(Throwable e){
			httpGoApi.geraLog("ContratoServicoDao || Salvar", StringUtil.convertObjectToJson(model), e);
		}
		return model;
	}

	public ContratoServico getServicoAtivoPorContratanteContratadoEAtividade(ContratoServicoDto dto) {
		ContratoServico resultado = new ContratoServico();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT C FROM ContratoServico C ");
		sql.append("            WHERE C.contratante.id = :idContratante ");
		sql.append("            AND   C.contratado.id = :codigoContratado ");
		sql.append("            AND   C.atividade.codigo = :codigoAtividade ");
		sql.append("            AND   C.tipoAtividade = :tipo ");
		sql.append("            AND   C.ativo = true ");

				
		try{
			
			TypedQuery<ContratoServico> query = em.createQuery(sql.toString(), ContratoServico.class);
			query.setParameter("idContratante", dto.getIdPessoaContratante());
			query.setParameter("codigoContratado", dto.getIdPessoaContratada());
			query.setParameter("codigoAtividade", Long.parseLong(dto.getAtividade().getCodigo()));
			query.setParameter("tipo", TipoAtividade.valueOf(dto.getTipoAtividade()));
		
			resultado = query.getSingleResult();
			
		} catch (NoResultException e ){
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoServicoDao || getServicoAtivoPorContratanteContratadoEAtividade", StringUtil.convertObjectToJson(dto), e);
		}	
		
		return resultado;
	}
	
	public List<ContratoServico> getListServicosPorContratanteContratadoEAtividade(ContratoServicoDto dto) {
		List<ContratoServico> resultado = new ArrayList<ContratoServico>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT C FROM ContratoServico C ");
		sql.append("            WHERE C.contratante.id = :idContratante ");
		sql.append("            AND   C.contratado.id = :codigoContratado ");
		sql.append("            AND   C.atividade.codigo = :codigoAtividade ");
		sql.append("            AND   C.tipoAtividade = :tipo ");
				
		try{
			
			TypedQuery<ContratoServico> query = em.createQuery(sql.toString(), ContratoServico.class);
			query.setParameter("idContratante", dto.getIdPessoaContratante());
			query.setParameter("codigoContratado", dto.getIdPessoaContratada());
			query.setParameter("codigoAtividade", Long.parseLong(dto.getAtividade().getCodigo()));
			query.setParameter("tipo", TipoAtividade.valueOf(dto.getTipoAtividade()));
		
			resultado = query.getResultList();
			
		} catch (NoResultException e ){
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoServicoDao || getListServicosPorContratanteContratadoEAtividade", StringUtil.convertObjectToJson(dto), e);
		}	
		
		return resultado;
	}
	
	public ContratoServico getContratoAtivoPor(Long codigoContrato) {
		ContratoServico contrato = new ContratoServico();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT C FROM ContratoServico C ");
		sql.append("    WHERE C.codigo = :codigoContrato ");
		
		
		try{
			TypedQuery<ContratoServico> query = em.createQuery(sql.toString(), ContratoServico.class);
			query.setParameter("codigoContrato", codigoContrato);
		
		
			contrato = query.getSingleResult();
			return contrato;
			
		} catch (NoResultException e ){
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoServicoDao || getContratoAtivoPor", StringUtil.convertObjectToJson(codigoContrato), e);
		}	
		
		return contrato ;
	}

}
