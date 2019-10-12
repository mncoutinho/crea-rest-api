package br.org.crea.commons.dao.art;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.dao.corporativo.Page;
import br.org.crea.commons.dao.corporativo.PageNative;
import br.org.crea.commons.models.art.Art;
import br.org.crea.commons.models.art.ArtReceita;
import br.org.crea.commons.models.art.ContratoArt;
import br.org.crea.commons.models.art.dtos.ContratoServicoDto;
import br.org.crea.commons.models.art.enuns.TipoBaixaArtEnum;
import br.org.crea.commons.models.commons.dtos.DomainGenericDto;
import br.org.crea.commons.models.commons.dtos.EnderecoDto;
import br.org.crea.commons.models.commons.dtos.PesquisaGenericDto;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.DateUtils;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class ContratoArtDao extends GenericDao<ContratoArt, Serializable> {

	@Inject
	HttpClientGoApi httpGoApi;

	public ContratoArtDao() {
		super(ContratoArt.class);
	}

	public List<ContratoArt> getContratos(PesquisaGenericDto pesquisa) {
		
		List<ContratoArt>  listContrato = new ArrayList<ContratoArt>();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT C FROM ContratoArt C WHERE C.art.numero = :numeroArt ");
		sql.append("    ORDER BY C.sequencial ");

		try {
			TypedQuery<ContratoArt> query = em.createQuery(sql.toString(), ContratoArt.class);
			query.setParameter("numeroArt", pesquisa.getNumeroArt());
			Page page = new Page(pesquisa.getPage(), pesquisa.getRows());
			page.paginate(query);

			listContrato = query.getResultList();

		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || getContratos", StringUtil.convertObjectToJson(pesquisa), e);
		}

		return listContrato;
	}
	
	public List<ContratoArt> getAllContratos(String numeroArt) {
		
		List<ContratoArt>  listContrato = new ArrayList<ContratoArt>();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT C FROM ContratoArt C WHERE C.art.numero = :numeroArt ");
		sql.append("    ORDER BY C.sequencial ");

		try {
			TypedQuery<ContratoArt> query = em.createQuery(sql.toString(), ContratoArt.class);
			query.setParameter("numeroArt", numeroArt);

			listContrato = query.getResultList();

		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || getAllContratos", StringUtil.convertObjectToJson(numeroArt), e);
		}

		return listContrato;
	}
	
	public int getTotalDeRegistrosDaPesquisaGetAll(PesquisaGenericDto pesquisa) {

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(C.CODIGO)       ");
		sql.append("   FROM ART_CONTRATO C        ");
		sql.append("  WHERE C.FK_ART = :numeroArt ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("numeroArt", pesquisa.getNumeroArt());

			return Integer.parseInt(query.getSingleResult().toString());

		}  catch (NoResultException e) {
			return 0;
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || getTotalDeRegistrosDaPesquisaGetAll", StringUtil.convertObjectToJson(pesquisa), e);
			return 0;
		}
	}

	public ContratoArt salvar(ContratoArt contrato) {

		contrato = create(contrato);
		return contrato;
	}

	public Long getUltimoSequencialContrato(String numeroArt) {
		Long total = 0L;

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT max(C.sequencial) FROM ContratoArt C WHERE C.art.numero = :numeroArt");

		try {
			Query query = em.createQuery(sql.toString());
			query.setParameter("numeroArt", numeroArt);

			total = (Long) query.getSingleResult();
		} catch (Throwable e) {
			httpGoApi.geraLog(" ContratoArtDao || listar por Art ", StringUtil.convertObjectToJson(numeroArt), e);
		}

		return total == null ? 0L : total;
	}

	@SuppressWarnings("unchecked")
	public List<ContratoArt> getContratoReceitaPor(String numeroArt) {

		List<ContratoArt> lista = new ArrayList<ContratoArt>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT C.id, C.nomeContratante, C.receita.id, C.art, c.dataCadastro, c.pessoa FROM ContratoArt C WHERE C.art.numero = :numeroArt and C.receita is not null");

		try {
			Query query = em.createQuery(sql.toString());
			query.setParameter("numeroArt", numeroArt);

			List<Object[]> resultado = query.getResultList();

			for (Object[] object : resultado) {
				ContratoArt contrato = new ContratoArt();
				contrato.setId((String) object[0]);
				contrato.setNomeContratante((String) object[1]);

				ArtReceita receita = new ArtReceita();
				receita.setId((Long) object[2]);
				contrato.setReceita(receita);

				contrato.setArt((Art) object[3]);
				contrato.setDataCadastro((Date) object[4]);
				contrato.setPessoa((Pessoa) object[5]);

				lista.add(contrato);
			}

		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog(" ContratoArtDao || Get Contrato Receita Por ", StringUtil.convertObjectToJson(numeroArt), e);
		}

		return lista;
	}
	
	@SuppressWarnings("unchecked")
	public List<ContratoArt> getContratosByArtAPartirDoSequencial(String numeroArt, Long sequencial) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT C FROM ContratoArt C WHERE C.art.numero = :numeroArt AND C.sequencial > :sequencial ORDER BY C.sequencial");

		try {
			Query query = em.createQuery(sql.toString());
			query.setParameter("numeroArt", numeroArt);
			query.setParameter("sequencial", sequencial);

			return (List<ContratoArt>) query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog(" ContratoArtDao || getContratosByArtAPartirDoSequencial ", StringUtil.convertObjectToJson(numeroArt), e);
			return null;
		}

	}

	public void excluiContrato(String idContrato) {
		
		excluiAtividadeDoContrato(idContrato);
		excluiComplementosDoContrato(idContrato);
		excluiEspecificacoesDoContrato(idContrato);
		excluiQuantificacaoDoContrato(idContrato);
		
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM  ART_CONTRATO A ");
		sql.append("	 WHERE A.CODIGO = :idContrato ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idContrato", idContrato);

			query.executeUpdate();
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || excluiContrato", StringUtil.convertObjectToJson(idContrato), e);
		}
		
	}

	public void excluiAtividadeDoContrato(String idContrato) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM  ART_CONTRATO_ATIVIDADE A ");
		sql.append("	 WHERE A.FK_CONTRATO = :idContrato ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idContrato", idContrato);
			
			query.executeUpdate();
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || excluiAtividadeDoContrato", StringUtil.convertObjectToJson(idContrato), e);
		}
		
	}
	
	public void excluiComplementosDoContrato(String idContrato) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM  ART_CONTRATO_COMPLEMENTO A ");
		sql.append("	 WHERE A.FK_CONTRATO = :idContrato ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idContrato", idContrato);
			
			query.executeUpdate();
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || excluiComplementosContrato", StringUtil.convertObjectToJson(idContrato), e);
		}
		
	}

	public void excluiEspecificacoesDoContrato(String idContrato) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM  ART_CONTRATO_ESPECIFICACAO A ");
		sql.append("	 WHERE A.FK_CONTRATO = :idContrato ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idContrato", idContrato);
			
			query.executeUpdate();
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || excluiEspecificacoesDoContrato", StringUtil.convertObjectToJson(idContrato), e);
		}
		
	}

	public void excluiQuantificacaoDoContrato(String idContrato) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM  ART_QUANTIFICACAO A ");
		sql.append("	 WHERE A.FK_CONTRATO_ART = :idContrato ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idContrato", idContrato);
			
			query.executeUpdate();
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || excluiQuantificacaoDoContrato", StringUtil.convertObjectToJson(idContrato), e);
		}
		
	}
	
	public void excluiContratosByListId(List<String> idsContrato) {
		
		excluiAtividadesDosContratosByListIdContrato(idsContrato);
		excluiEspecificacoesDosContratosByListIdContrato(idsContrato);
		excluiComplementosDosContratosByListIdContrato(idsContrato);		
		excluiQuantificacoesDosContratosByListIdContrato(idsContrato);
		
		StringBuilder sql = new StringBuilder();
		sql.append(" DELETE FROM ART_CONTRATO A        ");
		sql.append("  WHERE A.CODIGO IN (:idsContrato) ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idsContrato", idsContrato);

			query.executeUpdate();
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || excluiContratosByListId", StringUtil.convertObjectToJson(idsContrato), e);
		}
	}

	public void excluiAtividadesDosContratosByListIdContrato(List<String> idsContrato) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" DELETE FROM  ART_CONTRATO_ATIVIDADE A  ");
		sql.append("  WHERE A.FK_CONTRATO IN (:idsContrato) ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idsContrato", idsContrato);
			
			query.executeUpdate();
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || excluiAtividadesDosContratosByListIdContrato", StringUtil.convertObjectToJson(idsContrato), e);
		}
	}

	public void excluiEspecificacoesDosContratosByListIdContrato(List<String> idsContrato) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" DELETE FROM ART_CONTRATO_ESPECIFICACAO A ");
		sql.append("  WHERE A.FK_CONTRATO IN (:idsContrato)   ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idsContrato", idsContrato);
			
			query.executeUpdate();
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || excluiEspecificacoesDosContratosByListIdContrato", StringUtil.convertObjectToJson(idsContrato), e);
		}
		
	}

	public void excluiComplementosDosContratosByListIdContrato(List<String> idsContrato) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" DELETE FROM ART_CONTRATO_COMPLEMENTO A ");
		sql.append("  WHERE A.FK_CONTRATO IN (:idsContrato) ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idsContrato", idsContrato);
			
			query.executeUpdate();
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || excluiComplementosDosContratosByListIdContrato", StringUtil.convertObjectToJson(idsContrato), e);
		}
	}

	public void excluiQuantificacoesDosContratosByListIdContrato(List<String> idsContrato) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" DELETE FROM ART_QUANTIFICACAO A            ");
		sql.append("  WHERE A.FK_CONTRATO_ART IN (:idsContrato) ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idsContrato", idsContrato);
			
			query.executeUpdate();
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || excluiQuantificacoesDosContratosByListIdContrato", StringUtil.convertObjectToJson(idsContrato), e);
		}
	}

	public ContratoArt getContratoPor(String idContrato) {
		ContratoArt contrato = new ContratoArt();
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT A FROM ContratoArt A ");
		sql.append("   WHERE A.id = :idContrato ");

		try {

			TypedQuery<ContratoArt> query = em.createQuery(sql.toString(), ContratoArt.class);
			query.setParameter("idContrato", idContrato);

			contrato = query.getSingleResult();

		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || getContratoPor", StringUtil.convertObjectToJson(idContrato), e);
		}
		return contrato;
	}
	
	@SuppressWarnings("unused")
	public List<ContratoServicoDto> getContratosPor(PesquisaGenericDto pesquisa) {

		List<ContratoServicoDto> listContratos = new ArrayList<ContratoServicoDto>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT c.codigo id,   ");
		sql.append(" c.numero_contrato numero, ");
		sql.append(" c.nome_contratante contratante_nome, ");
		sql.append(" c.descricao_complementares descricao, ");
		sql.append(" endereco.codigo end_codigo, ");
		sql.append(" tipo_logr.descricao end_tipo, ");
		sql.append(" tipo_logr.abreviatura end_abrev, ");
		sql.append(" endereco.logradouro end_logradouro, ");
		sql.append(" endereco.numero end_numero, ");
		sql.append(" endereco.complemento end_complemento, ");
		sql.append(" endereco.bairro end_bairro, ");
		sql.append(" localidades.descricao end_localidade, ");
		sql.append(" uf.uf end_uf, ");
		sql.append(" endereco.cep end_cep ");
		sql.append("   FROM  art_contrato c  ");
		sql.append("   LEFT JOIN cad_enderecos endereco ON (endereco.codigo = c.fk_endereco) ");
		sql.append("   JOIN cad_tipos_logradouros tipo_logr ON (endereco.fk_codigo_tipos_logradouros = tipo_logr.codigo) ");
		sql.append("   JOIN cad_localidades localidades ON (endereco.fk_codigo_localidades = localidades.codigo) ");
		sql.append("   JOIN cad_ufs uf ON (localidades.fk_codigo_ufs = uf.codigo) ");
		sql.append("WHERE c.FK_ART = :numeroArt ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("numeroArt", pesquisa.getArt().getNumero());
			PageNative page = new PageNative(pesquisa.getPage(), pesquisa.getRows());
			page.paginate(query);

			Iterator<?> it = query.getResultList().iterator();

			int i = 0;
			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {
					i++;

					Object[] result = (Object[]) it.next();
					ContratoServicoDto contrato = new ContratoServicoDto();
					
					contrato.setId(result[0] == null ? "" : result[0].toString());
					contrato.setNumero(result[1] == null ? "" : result[1].toString());
					contrato.setNomeContratante(result[2] == null ? "" : result[2].toString());
					contrato.setDescricaoComplementares(result[3] == null ? "" : result[3].toString());

					BigDecimal idEndereco = (BigDecimal) result[4];
					if (idEndereco != null) {
						EnderecoDto dto = new EnderecoDto();
						dto.setId(idEndereco.setScale(0, BigDecimal.ROUND_UP).longValueExact());
						
						DomainGenericDto tipoEndereco = new DomainGenericDto();
						tipoEndereco.setDescricao(result[5] == null ? "" : result[5].toString());
						dto.setTipoEndereco(tipoEndereco);
						
						DomainGenericDto tipoDescricao = new DomainGenericDto();
						tipoDescricao.setDescricao(result[6] == null ? "" : result[6].toString());
						
						dto.setTipoLogradouro(tipoDescricao);
						
						
						dto.setLogradouro(result[7] == null ? "" : result[7].toString());
						dto.setNumero(result[8] == null ? "" : result[8].toString());
						dto.setComplemento(result[9] == null ? "" : result[9].toString());
						dto.setBairro(result[10] == null ? "" : result[10].toString());
						String localidade = result[11] == null ? "" : result[11].toString();
						String uf = result[12] == null ? "" : result[12].toString();
						dto.setCep(result[13] == null ? "" : result[13].toString());
						String enderecoDescritivo = dto.getTipoLogradouro().getDescricao() + " - " + dto.getLogradouro() + " - " + dto.getNumero() + " - " + dto.getCep()
								+ " - " + localidade + " - " + uf;
						dto.setEnderecoCompleto(enderecoDescritivo);
						contrato.setEnderecoContrato(dto);

						contrato.setAtividades(getAtividadesContrato(pesquisa.getArt().getNumero()));
						contrato.setEspecificacoes(getEspecificacoesContrato(pesquisa.getArt().getNumero()));
						contrato.setComplementos(getComplementosContrato(pesquisa.getArt().getNumero()));

					}
					contrato.setEmpresaContratada(pesquisa.getArt().getEmpresa());

					listContratos.add(contrato);

				}
			}

			return listContratos;

		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao ||  getContratosPor ", StringUtil.convertObjectToJson(pesquisa), e);
		}
		return listContratos;

	}
	
	private String getAtividadesContrato(String numeroArt) {

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT ATIVIDADE.CODIGO, ATIVIDADE.DESCRICAO   ");
		sql.append("   FROM ART_CONTRATO C, ART_CONTRATO_ATIVIDADE CONTRATO, ART_ATIVIDADE_TECNICA ATIVIDADE ");
		sql.append("   WHERE C.FK_ART = :numeroArt ");
		sql.append("   AND  CONTRATO.FK_CONTRATO = C.CODIGO AND ATIVIDADE.CODIGO = CONTRATO.FK_ATIVIDADE ");
		sql.append("   ORDER BY ATIVIDADE.CODIGO ");

		Query query = em.createNativeQuery(sql.toString());
		query.setParameter("numeroArt", numeroArt);

		Iterator<?> it = query.getResultList().iterator();
		StringBuilder resultados = new StringBuilder();
		if (query.getResultList().size() > 0) {
			while (it.hasNext()) {
				Object[] result = (Object[]) it.next();
				resultados.append(result[0].toString() + "-" + result[1].toString() + " ");

			}
		}
		return resultados.toString();
	}
	
	private String getEspecificacoesContrato(String numeroArt) {

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT ATIVIDADE.CODIGO, ATIVIDADE.DESCRICAO   ");
		sql.append("   FROM ART_CONTRATO C, ART_CONTRATO_ESPECIFICACAO ESPECIFICACAO, ART_ESPECIFICACAO_ATIVIDADE ATIVIDADE ");
		sql.append("   WHERE C.FK_ART = :numeroArt ");
		sql.append("   AND  ESPECIFICACAO.FK_CONTRATO = C.CODIGO AND ATIVIDADE.CODIGO = ESPECIFICACAO.FK_ESPECIFICACAO ");
		sql.append("   ORDER BY ATIVIDADE.CODIGO ");

		Query query = em.createNativeQuery(sql.toString());
		query.setParameter("numeroArt", numeroArt);

		Iterator<?> it = query.getResultList().iterator();
		StringBuilder resultados = new StringBuilder();
		if (query.getResultList().size() > 0) {
			while (it.hasNext()) {
				Object[] result = (Object[]) it.next();
				resultados.append(result[0].toString() + "-" + result[1].toString() + " ");

			}
		}
		return resultados.toString();
	}
	
	private String getComplementosContrato(String numeroArt) {

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT ATIVIDADE.CODIGO, ATIVIDADE.DESCRICAO   ");
		sql.append("   FROM ART_CONTRATO C, ART_CONTRATO_COMPLEMENTO COMPLEMENTO, ART_COMPLEMENTO ATIVIDADE ");
		sql.append("   WHERE C.FK_ART = :numeroArt ");
		sql.append("   AND  COMPLEMENTO.FK_CONTRATO = C.CODIGO AND ATIVIDADE.CODIGO = COMPLEMENTO.FK_COMPLEMENTO ");
		sql.append("   ORDER BY ATIVIDADE.CODIGO ");

		Query query = em.createNativeQuery(sql.toString());
		query.setParameter("numeroArt", numeroArt);

		Iterator<?> it = query.getResultList().iterator();
		StringBuilder resultados = new StringBuilder();
		if (query.getResultList().size() > 0) {
			while (it.hasNext()) {
				Object[] result = (Object[]) it.next();
				resultados.append(result[0].toString() + "-" + result[1].toString() + " ");

			}
		}
		return resultados.toString();
	}
	@SuppressWarnings("unchecked")
	public int getTotalDeRegistrosDaPesquisa(PesquisaGenericDto pesquisa) {

		List<ContratoServicoDto> listContratos = new ArrayList<ContratoServicoDto>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT c.codigo id,   ");
		sql.append(" c.numero_contrato numero, ");
		sql.append(" c.nome_contratante contratante_nome, ");
		sql.append(" c.descricao_complementares descricao, ");
		sql.append(" endereco.codigo end_codigo, ");
		sql.append(" tipo_logr.descricao end_tipo, ");
		sql.append(" tipo_logr.abreviatura end_abrev, ");
		sql.append(" endereco.logradouro end_logradouro, ");
		sql.append(" endereco.numero end_numero, ");
		sql.append(" endereco.complemento end_complemento, ");
		sql.append(" endereco.bairro end_bairro, ");
		sql.append(" localidades.descricao end_localidade, ");
		sql.append(" uf.uf end_uf, ");
		sql.append(" endereco.cep end_cep ");
		sql.append("   FROM  art_contrato c  ");
		sql.append("   LEFT JOIN cad_enderecos endereco ON (endereco.codigo = c.fk_endereco) ");
		sql.append("   JOIN cad_tipos_logradouros tipo_logr ON (endereco.fk_codigo_tipos_logradouros = tipo_logr.codigo) ");
		sql.append("   JOIN cad_localidades localidades ON (endereco.fk_codigo_localidades = localidades.codigo) ");
		sql.append("   JOIN cad_ufs uf ON (localidades.fk_codigo_ufs = uf.codigo) ");
		sql.append("WHERE c.FK_ART = :numeroArt ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("numeroArt", pesquisa.getArt().getNumero());
			listContratos = query.getResultList();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || getTotalDeRegistrosDaPesquisa", StringUtil.convertObjectToJson(pesquisa), e);
		}
		return listContratos.size();
	}
	
	public ContratoServicoDto getContratoPrimeiroSequencialPor(String numeroArt) {
		ContratoServicoDto contrato = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT c.codigo id, ");
		sql.append(" 	c.numero_contrato numero, ");
		sql.append(" 	c.nome_contratante contratante_nome, ");
		sql.append(" 	c.descricao_complementares descricao, ");
		sql.append(" 	endereco.codigo end_codigo, ");
		sql.append(" 	tipo_logr.descricao end_tipo, ");
		sql.append(" 	tipo_logr.abreviatura end_abrev, ");
		sql.append(" 	endereco.logradouro end_logradouro, ");
		sql.append(" 	endereco.numero end_numero, ");
		sql.append(" 	endereco.complemento end_complemento, ");
		sql.append(" 	endereco.bairro end_bairro, ");
		sql.append(" 	localidades.descricao end_localidade, ");
		sql.append(" 	uf.uf end_uf, ");
		sql.append(" 	endereco.cep end_cep, ");
		sql.append(" 	c.nhhjt jornada, ");
		sql.append(" 	(CASE ");
		sql.append("   		WHEN p.tipopessoa = 'PESSOAJURIDICA' OR p.tipopessoa = 'LEIGOPJ' OR p.tipopessoa = 'EMPRESA' OR ");
		sql.append("        	 p.tipopessoa = 'ENTIDADE' OR p.tipopessoa = 'ESCOLA' ");
		sql.append("   		THEN (SELECT pj.cnpj FROM cad_pessoas_juridicas pj WHERE pj.codigo = p.codigo) ");
		sql.append("   		ELSE (SELECT pf.cpf FROM cad_pessoas_fisicas pf WHERE pf.codigo = p.codigo) ");
		sql.append("  	END) cpf_cnpj_contratante, ");
		sql.append(" 	NVL(c.salario, 0) salario, ");
		sql.append(" 	NVL(tp.descricao, 'NAO INFORMADO') tipo_vinculo, ");
		sql.append("	(NVL(ar.codigo, '') ||' - '|| NVL(ar.descricao, '')) ramo ");
		sql.append("    FROM  art_contrato c ");
		sql.append("   	LEFT JOIN cad_enderecos endereco ON (endereco.codigo = c.fk_endereco) ");
		sql.append("   	JOIN cad_tipos_logradouros tipo_logr ON (endereco.fk_codigo_tipos_logradouros = tipo_logr.codigo) ");
		sql.append("   	JOIN cad_localidades localidades ON (endereco.fk_codigo_localidades = localidades.codigo) ");
		sql.append("   	JOIN cad_ufs uf ON (localidades.fk_codigo_ufs = uf.codigo) ");
		sql.append("   	LEFT JOIN cad_pessoas p ON (p.codigo = c.fk_pessoa) ");
		sql.append("   	LEFT JOIN art_tipo_vinculo tp ON (tp.id = c.fk_art_tipo_vinculo) ");
		sql.append("   	LEFT JOIN art_ramo ar ON (c.fk_ramo_art = ar.codigo) ");
		sql.append("   	WHERE c.FK_ART = :numeroArt ");
		sql.append("    AND   c.sequencial = 1 ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("numeroArt", numeroArt);

			Iterator<?> it = query.getResultList().iterator();

			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {

					Object[] result = (Object[]) it.next();
					contrato = new ContratoServicoDto();
					contrato.setId(result[0] == null ? "" : result[0].toString());
					contrato.setNumero(result[1] == null ? "" : result[1].toString());
					contrato.setNomeContratante(result[2] == null ? "" : result[2].toString());
					contrato.setDescricaoComplementares(result[3] == null ? "" : result[3].toString());

					BigDecimal idEndereco = (BigDecimal) result[4];
					if (idEndereco != null) {
						EnderecoDto enderecoContrato = new EnderecoDto();
						enderecoContrato.setId(idEndereco.setScale(0, BigDecimal.ROUND_UP).longValueExact());
						DomainGenericDto tipoEndereco = new DomainGenericDto();
						tipoEndereco.setDescricao(result[5] == null ? "" : result[5].toString());
						enderecoContrato.setTipoEndereco(tipoEndereco);
						
						DomainGenericDto tipoDescricao = new DomainGenericDto();
						tipoDescricao.setDescricao(result[6] == null ? "" : result[6].toString());
						
						enderecoContrato.setTipoLogradouro(tipoDescricao);
						enderecoContrato.setLogradouro(result[7] == null ? "" : result[7].toString());
						enderecoContrato.setNumero(result[8] == null ? "" : result[8].toString());
						enderecoContrato.setComplemento(result[9] == null ? "" : result[9].toString());
						enderecoContrato.setBairro(result[10] == null ? "" : result[10].toString());
						String localidade = result[11] == null ? "" : result[11].toString();
						String uf = result[12] == null ? "" : result[12].toString();
						enderecoContrato.setCep(result[13] == null ? "" : result[13].toString());
						String enderecoDescritivo = enderecoContrato.getTipoLogradouro().getDescricao() + " - " + enderecoContrato.getLogradouro() + " - " + enderecoContrato.getNumero() + " - " + enderecoContrato.getCep()
								+ " - " + localidade + " - " + uf;
						enderecoContrato.setEnderecoCompleto(enderecoDescritivo);
						contrato.setEnderecoContrato(enderecoContrato);

					}
					
					contrato.setAtividades(getAtividadesContrato(numeroArt));
					contrato.setEspecificacoes(getEspecificacoesContrato(numeroArt));
					contrato.setComplementos(getComplementosContrato(numeroArt));
					contrato.setJornadaDeTrabalho(result[14] == null ? "" : result[14].toString());
					contrato.setCpfOuCnpjContratante(result[15] == null ? "" : result[15].toString());
					contrato.setSalario(result[16] == null ? new BigDecimal(0) : (BigDecimal) result[16]);
					contrato.setTipoVinculoProfissional(result[17] == null ? "" : result[17].toString());

				}
			}

		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao ||  getContratoPrimeiroSequencialPor", StringUtil.convertObjectToJson(numeroArt), e);
		}
		return contrato;

	}
	
	public ContratoArt getPrimeiroContratoPor(String numeroArt) {
		ContratoArt contrato = new ContratoArt();
		StringBuilder sql = new StringBuilder();

		sql.append(" SELECT C FROM ContratoArt C      ");
		sql.append("  WHERE C.art.numero = :numeroArt ");
		sql.append("  AND C.sequencial = :primeiro ");

		try {

			TypedQuery<ContratoArt> query = em.createQuery(sql.toString(), ContratoArt.class);
			query.setParameter("numeroArt", numeroArt);
			query.setParameter("primeiro", 1L);

			contrato = query.getSingleResult();

		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || getPrimeiroContratoPor", StringUtil.convertObjectToJson(numeroArt), e);
		}
		return contrato;
	}
	
	public boolean verificaContratoSemDataInicio (String numeroArt) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(C.CODIGO) FROM ART_CONTRATO C   ");
		sql.append("  WHERE C.FK_ART = :numeroArt                 ");
		sql.append("  AND (C.PRAZO_DETERMINADO = 0                ");
		sql.append("    OR C.PRAZO_DETERMINADO IS NULL            ");
		sql.append("    OR C.DATA_INICIO IS NULL)                 ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("numeroArt", numeroArt);

			BigDecimal qt = (BigDecimal) query.getSingleResult();
		
			return qt.equals(new BigDecimal(0));
		
		} catch (NoResultException e) {
			return true;
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || verificaContratoSemDataInicio", StringUtil.convertObjectToJson(numeroArt), e);
		}
		return false;
	}
	
	
	
	public Date pegaMaiorDataFinaldeContrato (String numeroArt) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT TO_CHAR(MAX(C.DATA_FIM), 'DD/MM/YYYY')   ");
		sql.append("   FROM ART_CONTRATO C                           ");
		sql.append("  WHERE C.FK_ART = :numeroArt                    ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("numeroArt", numeroArt);

			String data = (String) query.getSingleResult();
			
			return data != null ? DateUtils.convertStringToDate(data) : null;

		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || pegaMaiorDataFinaldeContrato", StringUtil.convertObjectToJson(numeroArt), e);
		}
		return null;
		
	}
	
	public Date pegaMaiorDataInicialdeContrato (String numeroART) {
		Query query = em.createNativeQuery("Select To_Char(Max(C.Data_Inicio), 'dd/MM/yyyy') " + 
				                           "From Corporativo.Art_Contrato C " +
				                           "Where C.Fk_Art = '" + numeroART + "'");
		try{
			String data = (String) query.getSingleResult();
			
			return data != null ? DateUtils.convertStringToDate(data) : null;
		}catch(NoResultException e){
			return null;
		}
	}
	
	
	public boolean atualizaNumeroContrato(DomainGenericDto dto) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ART_CONTRATO C                      ");
		sql.append("    SET C.NUMERO_CONTRATO = :numeroContrato ");
		sql.append("  WHERE C.CODIGO = :idContrato              ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("numeroContrato", dto.getDescricao());
			query.setParameter("idContrato", dto.getNumero());
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || atualizaNumeroContrato", StringUtil.convertObjectToJson(dto), e);
			return false;
		}
		return true;
	}
	
	public boolean atualizaSalario(DomainGenericDto dto) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ART_CONTRATO C           ");
		sql.append("    SET C.SALARIO = :salario     ");
		sql.append("  WHERE C.CODIGO = :idContrato   ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("salario", dto.getValor());
			query.setParameter("idContrato", dto.getNumero());
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || atualizaSalario", StringUtil.convertObjectToJson(dto), e);
			return false;
		}
		
		return true;
	}

	public void atualizaProLabore(DomainGenericDto dto) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ART_CONTRATO C            ");
		sql.append("    SET C.PROLABORE = :proLabore  ");
		sql.append("  WHERE C.CODIGO = :idContrato    ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("proLabore", dto.isChecked());
			query.setParameter("idContrato", dto.getNumero());
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || atualizaProLabore", StringUtil.convertObjectToJson(dto), e);
		}
	}

	public boolean atualizaValorContrato(DomainGenericDto dto) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ART_CONTRATO C                    ");
		sql.append("    SET C.VALOR_CONTRATO = :valorContrato ");
		sql.append("  WHERE C.CODIGO = :idContrato            ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("valorContrato", dto.getValor());
			query.setParameter("idContrato", dto.getNumero());
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || atualizaValorContrato", StringUtil.convertObjectToJson(dto), e);
			return false;
		}
		
		return true;
	}

	public void atualizaNhhjt(DomainGenericDto dto) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ART_CONTRATO C         ");
		sql.append("    SET C.NHHJT = :nhhjt       ");
		sql.append("  WHERE C.CODIGO = :idContrato ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("nhhjt", dto.getDescricao());
			query.setParameter("idContrato", dto.getNumero());
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || atualizaNhhjt", StringUtil.convertObjectToJson(dto), e);
		}
	}
	
	public boolean atualizaDataInicio(DomainGenericDto dto) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ART_CONTRATO C              ");
		sql.append("    SET C.DATA_INICIO = :dataInicio ");
		sql.append("  WHERE C.CODIGO = :idContrato      ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("dataInicio", "".equals(dto.getDescricao()) ? "" :  DateUtils.convertStringToDate(dto.getDescricao(), DateUtils.YYYY_MM_DD_COM_TRACOS));
			query.setParameter("idContrato", dto.getNumero());
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || atualizaDataInicio", StringUtil.convertObjectToJson(dto), e);
			return false;
		}
		
		return true;
	}
	
	public boolean atualizaDataFim(DomainGenericDto dto) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ART_CONTRATO C              ");
		sql.append("    SET C.DATA_FIM = :dataFim       ");
		sql.append("  WHERE C.CODIGO = :idContrato      ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("dataFim", dto.getDescricao() == null ? "" :  DateUtils.convertStringToDate(dto.getDescricao(), DateUtils.YYYY_MM_DD_COM_TRACOS));
			query.setParameter("idContrato", dto.getNumero());
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || atualizaDataFim", StringUtil.convertObjectToJson(dto), e);
			return false;
		}
		
		return true;
	}
	
	public void atualizaPrazoDeterminado(DomainGenericDto dto) {

		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ART_CONTRATO C                          ");
		sql.append("    SET C.PRAZO_DETERMINADO = :prazoDeterminado ");
		sql.append("  WHERE C.CODIGO = :idContrato                  ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("prazoDeterminado", dto.isChecked());
			query.setParameter("idContrato", dto.getNumero());
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || atualizaPrazoDeterminado", StringUtil.convertObjectToJson(dto), e);
		}
	}

	public boolean atualizaPrazoMes(DomainGenericDto dto) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ART_CONTRATO C          ");
		sql.append("    SET C.PRAZO_MES = :prazoMes ");
		sql.append("  WHERE C.CODIGO = :idContrato  ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("prazoMes", dto.getDescricao() == null ? "" :  Long.valueOf(dto.getDescricao()).longValue());
			query.setParameter("idContrato", dto.getNumero());
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || atualizaPrazoMes", StringUtil.convertObjectToJson(dto), e);
			return false;
		}
		return true;
	}

	public boolean atualizaPrazoDia(DomainGenericDto dto) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ART_CONTRATO C           ");
		sql.append("    SET C.PRAZO_DIA = :prazoDia  ");
		sql.append("  WHERE C.CODIGO = :idContrato   ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("prazoDia", dto.getDescricao() == null ? "" : Long.valueOf(dto.getDescricao()).longValue());
			query.setParameter("idContrato", dto.getNumero());
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || atualizaPrazoDia", StringUtil.convertObjectToJson(dto), e);
			return false;
		}
		return true;
	}

	public void atualizaRamo(DomainGenericDto dto) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ART_CONTRATO C         ");
		if (dto.getId().equals(0L)) {
			sql.append("    SET C.FK_RAMO_ART = NULL  ");
		} else {
			sql.append("    SET C.FK_RAMO_ART = :ramo  ");
		}
		sql.append("  WHERE C.CODIGO = :idContrato ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			if (!dto.getId().equals(0L)) {
				query.setParameter("ramo", dto.getId());
			}
			query.setParameter("idContrato", dto.getNumero());
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || atualizaRamo", StringUtil.convertObjectToJson(dto), e);
		}
	}
	
	public void atualizaAtividade(DomainGenericDto dto, String numeroContrato) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" INSERT INTO ART_CONTRATO_ATIVIDADE (FK_CONTRATO, FK_ATIVIDADE)  ");
		sql.append("        VALUES (:idContrato, :idAtividade)                       ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idContrato", numeroContrato);
			query.setParameter("idAtividade", dto.getId());
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || atualizaAtividade", StringUtil.convertObjectToJson(dto), e);
		}
	}
	
	public void atualizaAtividadePor(Long idAtividade, String numeroContrato) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" INSERT INTO ART_CONTRATO_ATIVIDADE (FK_CONTRATO, FK_ATIVIDADE)  ");
		sql.append("        VALUES (:idContrato, :idAtividade)                       ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idContrato", numeroContrato);
			query.setParameter("idAtividade", idAtividade);
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || atualizaAtividadePor", StringUtil.convertObjectToJson(idAtividade+"-"+numeroContrato), e);
		}
	}

	// FIXME remover metodos duplicados
	public void atualizaEspecificacao(DomainGenericDto dto, String numeroContrato) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" INSERT INTO ART_CONTRATO_ESPECIFICACAO (FK_CONTRATO, FK_ESPECIFICACAO)  ");
		sql.append("        VALUES (:idContrato, :idEspecificacao)                            ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idContrato", numeroContrato);
			query.setParameter("idEspecificacao", dto.getId());
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || atualizaEspecificacao", StringUtil.convertObjectToJson(dto), e);
		}
	}
	
	public void atualizaEspecificacaoPor(Long idEspecificacao, String numeroContrato) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" INSERT INTO ART_CONTRATO_ESPECIFICACAO (FK_CONTRATO, FK_ESPECIFICACAO)  ");
		sql.append("        VALUES (:idContrato, :idEspecificacao)                            ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idContrato", numeroContrato);
			query.setParameter("idEspecificacao", idEspecificacao);
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || atualizaEspecificacaoPor", StringUtil.convertObjectToJson(idEspecificacao+"-"+numeroContrato), e);
		}
	}

	public void atualizaComplemento(DomainGenericDto dto, String numeroContrato) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" INSERT INTO ART_CONTRATO_COMPLEMENTO (FK_CONTRATO, FK_COMPLEMENTO) ");
		sql.append("        VALUES (:idContrato, :idComplemento)  ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idContrato", numeroContrato);
			query.setParameter("idComplemento", dto.getId());
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || atualizaComplemento", StringUtil.convertObjectToJson(dto), e);
		}
	}
	
	public void atualizaComplementoPor(Long idComplemento, String numeroContrato) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" INSERT INTO ART_CONTRATO_COMPLEMENTO (FK_CONTRATO, FK_COMPLEMENTO) ");
		sql.append("        VALUES (:idContrato, :idComplemento)  ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idContrato", numeroContrato);
			query.setParameter("idComplemento", idComplemento);
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || atualizaComplementoPor", StringUtil.convertObjectToJson(idComplemento+"-"+numeroContrato), e);
		}
	}
	
	public boolean atualizaNumeroPavimentos(DomainGenericDto dto) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ART_CONTRATO C                       ");
		if (dto.getDescricao() != null) {
			sql.append("    SET C.NUMERO_PAVTOS = :numeroPavimentos  ");
		} else {
			sql.append("    SET C.NUMERO_PAVTOS = NULL               ");
		}
		sql.append("  WHERE C.CODIGO = :idContrato               ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			if (dto.getDescricao() != null) {
				query.setParameter("numeroPavimentos", Long.valueOf(dto.getDescricao()).longValue());
			}
			query.setParameter("idContrato", dto.getNumero());
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || atualizaNumeroPavimentos", StringUtil.convertObjectToJson(dto), e);
			return false;
		}
		return true;
	}
	
	public void atualizaConvenioPublico(DomainGenericDto dto) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ART_CONTRATO C                            ");
		if (dto.getId().equals(0L)) {
			sql.append("    SET C.FK_CONVENIO_PUBLICO = NULL  ");
		} else {
			sql.append("    SET C.FK_CONVENIO_PUBLICO = :convenioPublico  ");
		}
		
		sql.append("  WHERE C.CODIGO = :idContrato                    ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			if (!dto.getId().equals(0L)) {
				query.setParameter("convenioPublico", dto.getId());
			}
			query.setParameter("idContrato", dto.getNumero());
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || atualizaConvenioPublico", StringUtil.convertObjectToJson(dto), e);
		}		
	}
	
	public boolean atualizaDescricaoComplementar(DomainGenericDto dto) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ART_CONTRATO C                                       ");
		sql.append("    SET C.DESCRICAO_COMPLEMENTARES = :descricaoComplementar  ");
		sql.append("  WHERE C.CODIGO = :idContrato                               ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("descricaoComplementar", dto.getDescricao().toUpperCase());
			query.setParameter("idContrato", dto.getNumero());
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || atualizaDescricaoComplementar", StringUtil.convertObjectToJson(dto), e);
			return false;
		}	
		return true;
	}

	public void atualizaAcessibilidade(DomainGenericDto dto) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ART_CONTRATO C                      ");
		sql.append("    SET C.ACESSIBILIDADE = :acessibilidade  ");
		sql.append("  WHERE C.FK_ART = :numeroArt               ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("acessibilidade", dto.isChecked());
			query.setParameter("numeroArt", dto.getNumero());
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || atualizaAcessibilidade", StringUtil.convertObjectToJson(dto), e);
		}		
	}

	public void deletaAtividade(String numeroArt, String idAtividade) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" DELETE ART_CONTRATO_ATIVIDADE A           ");
		sql.append("  WHERE A.FK_CONTRATO IN                   ");
		sql.append("   (SELECT C.CODIGO FROM ART_CONTRATO C    ");
		sql.append("     WHERE C.FK_ART = :numeroArt)          ");
		sql.append("    AND A.FK_ATIVIDADE = :idAtividade      ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("numeroArt", numeroArt);
			query.setParameter("idAtividade", idAtividade);
						
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || deletaAtividade", StringUtil.convertObjectToJson(numeroArt + " -- " + idAtividade), e);
		}	
	}

	public void deletaEspecificacao(String numeroArt, String idEspecificacao) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" DELETE ART_CONTRATO_ESPECIFICACAO E           ");
		sql.append("  WHERE E.FK_CONTRATO IN                       ");
		sql.append("   (SELECT C.CODIGO FROM ART_CONTRATO C        ");
		sql.append("     WHERE C.FK_ART = :numeroArt)              ");
		sql.append("    AND E.FK_ESPECIFICACAO = :idEspecificacao  ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("numeroArt", numeroArt);
			query.setParameter("idEspecificacao", idEspecificacao);
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || deletaEspecificacao", StringUtil.convertObjectToJson(numeroArt + " -- " + idEspecificacao), e);
		}	
	}

	public void deletaComplemento(String numeroArt, String idComplemento) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" DELETE ART_CONTRATO_COMPLEMENTO C         ");
		sql.append("  WHERE C.FK_CONTRATO IN                   ");
		sql.append("   (SELECT C.CODIGO FROM ART_CONTRATO C    ");
		sql.append("     WHERE C.FK_ART = :numeroArt)          ");
		sql.append("    AND C.FK_COMPLEMENTO = :idComplemento  ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("numeroArt", numeroArt);
			query.setParameter("idComplemento", idComplemento);
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || deletaComplemento", StringUtil.convertObjectToJson(numeroArt + " -- " + idComplemento), e);
		}	
	}

	public List<DomainGenericDto> getAtividadesDoContratoPor(String idContrato) {
		List<DomainGenericDto> lista = new ArrayList<DomainGenericDto>();

		StringBuilder sql = new StringBuilder();

		sql.append(" SELECT C.FK_CONTRATO, C.FK_ATIVIDADE, A.DESCRICAO             ");
		sql.append("   FROM ART_CONTRATO_ATIVIDADE C                               ");
		sql.append("   JOIN ART_ATIVIDADE_TECNICA A ON (C.FK_ATIVIDADE = A.CODIGO) ");
		sql.append("  WHERE C.FK_CONTRATO = :idContrato                            ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idContrato", idContrato);

			Iterator<?> it = query.getResultList().iterator();
			
			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {
					
					Object[] result = (Object[]) it.next();
					DomainGenericDto dto = new DomainGenericDto();

					dto.setNumero(result[0] == null ? "" : result[0].toString());
					
					BigDecimal idAtividade = (BigDecimal) result[1];
					dto.setId(idAtividade.setScale(0, BigDecimal.ROUND_UP).longValueExact());
					
					dto.setDescricao(result[2] == null ? "" : result[2].toString());

					lista.add(dto);
				}
			}

		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || getAtividadesDoContratoPor", StringUtil.convertObjectToJson(idContrato), e);
		}

		return lista;
	}


	public List<DomainGenericDto> getEspecificacoesDoContratoPor(String idContrato) {
		List<DomainGenericDto> lista = new ArrayList<DomainGenericDto>();

		StringBuilder sql = new StringBuilder();

		sql.append(" SELECT C.FK_CONTRATO, C.FK_ESPECIFICACAO, E.DESCRICAO                   ");
		sql.append("   FROM ART_CONTRATO_ESPECIFICACAO C                                     ");
		sql.append("   JOIN ART_ESPECIFICACAO_ATIVIDADE E ON (C.FK_ESPECIFICACAO = E.CODIGO) ");
		sql.append("  WHERE C.FK_CONTRATO = :idContrato                                      ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idContrato", idContrato);

			Iterator<?> it = query.getResultList().iterator();
			
			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {
					
					Object[] result = (Object[]) it.next();
					DomainGenericDto dto = new DomainGenericDto();

					dto.setNumero(result[0] == null ? "" : result[0].toString());
					
					BigDecimal idEspecificacao = (BigDecimal) result[1];
					dto.setId(idEspecificacao.setScale(0, BigDecimal.ROUND_UP).longValueExact());
					
					dto.setDescricao(result[2] == null ? "" : result[2].toString());

					lista.add(dto);
				}
			}

		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || getEspecificacoesDoContratoPor", StringUtil.convertObjectToJson(idContrato), e);
		}

		return lista;
	}


	public List<DomainGenericDto> getComplementosDoContratoPor(String idContrato) {
		List<DomainGenericDto> lista = new ArrayList<DomainGenericDto>();

		StringBuilder sql = new StringBuilder();

		sql.append(" SELECT C.FK_CONTRATO, C.FK_COMPLEMENTO, A.DESCRICAO       ");
		sql.append("   FROM ART_CONTRATO_COMPLEMENTO C                         ");
		sql.append("   JOIN ART_COMPLEMENTO A ON (C.FK_COMPLEMENTO = A.CODIGO) ");
		sql.append("  WHERE C.FK_CONTRATO = :idContrato                        ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idContrato", idContrato);

			Iterator<?> it = query.getResultList().iterator();
			
			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {
					
					Object[] result = (Object[]) it.next();
					DomainGenericDto dto = new DomainGenericDto();

					dto.setNumero(result[0] == null ? "" : result[0].toString());
					
					BigDecimal idComplemento = (BigDecimal) result[1];
					dto.setId(idComplemento.setScale(0, BigDecimal.ROUND_UP).longValueExact());
					
					dto.setDescricao(result[2] == null ? "" : result[2].toString());

					lista.add(dto);
				}
			}

		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || getComplementosDoContratoPor", StringUtil.convertObjectToJson(idContrato), e);
		}

		return lista;
	}


	public void atualizaTipoUnidadeAdministrativa(DomainGenericDto dto) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ART_CONTRATO C                                          ");
		sql.append("    SET C.FK_ART_TIPO_UNIDADE_ADM = :tipoUnidadeAdministrativa  ");
		sql.append("  WHERE C.CODIGO = :idContrato                                  ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("tipoUnidadeAdministrativa", dto.getId());
			query.setParameter("idContrato", dto.getNumero());
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || atualizaTipoUnidadeAdministrativa", StringUtil.convertObjectToJson(dto), e);
		}
	}

	public void atualizaTipoAcaoInstitucional(DomainGenericDto dto) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ART_CONTRATO C                                          ");
		sql.append("    SET C.FK_TIPO_ACAO_INSTITUCIONAL = :tipoAcaoInstitucional   ");
		sql.append("  WHERE C.CODIGO = :idContrato                                  ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("tipoAcaoInstitucional", dto.getId());
			query.setParameter("idContrato", dto.getNumero());
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || atualizaTipoAcaoInstitucional", StringUtil.convertObjectToJson(dto), e);
		}
	}

	public void atualizaTipoCargoFuncao(DomainGenericDto dto) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ART_CONTRATO C                                 ");
		sql.append("    SET C.FK_ART_TIPO_CARGO_FUNCAO = :tipoCargoFuncao  ");
		sql.append("  WHERE C.CODIGO = :idContrato                         ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("tipoCargoFuncao", dto.getId());
			query.setParameter("idContrato", dto.getNumero());
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || atualizaTipoCargoFuncao", StringUtil.convertObjectToJson(dto), e);
		}
	}

	public void atualizaTipoFuncao(DomainGenericDto dto) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ART_CONTRATO C                      ");
		sql.append("    SET C.FK_ART_TIPO_FUNCAO = :tipoFuncao  ");
		sql.append("  WHERE C.CODIGO = :idContrato              ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("tipoFuncao", dto.getId());
			query.setParameter("idContrato", dto.getNumero());
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || atualizaTipoFuncao", StringUtil.convertObjectToJson(dto), e);
		}
	}

	public boolean atualizaDescricaoCargoFuncao(DomainGenericDto dto) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ART_CONTRATO C                                    ");
		sql.append("    SET C.DESCRICAO_CARGO_FUNCAO = :descricaoCargoFuncao  ");
		sql.append("  WHERE C.CODIGO = :idContrato                            ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("descricaoCargoFuncao", dto.getDescricao().toUpperCase());
			query.setParameter("idContrato", dto.getNumero());
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || atualizaDescricaoCargoFuncao", StringUtil.convertObjectToJson(dto), e);
			return false;
		}
		return true;
	}
	
	public void atualizaTipoVinculo(DomainGenericDto dto) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ART_CONTRATO C                       ");
		sql.append("    SET C.FK_ART_TIPO_VINCULO = :tipoVinculo ");
		sql.append("  WHERE C.CODIGO = :idContrato               ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("tipoVinculo", dto.getId());
			query.setParameter("idContrato", dto.getNumero());
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || atualizaTipoVinculo", StringUtil.convertObjectToJson(dto), e);
		}
	}

	public boolean atualizaDataCelebracao(DomainGenericDto dto) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ART_CONTRATO C                       ");
		sql.append("    SET C.DATA_CELEBRACAO = :dataCelebracao  ");
		sql.append("  WHERE C.CODIGO = :idContrato               ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("dataCelebracao",  "".equals(dto.getDescricao()) ? "" :  DateUtils.convertStringToDate(dto.getDescricao(), DateUtils.YYYY_MM_DD_COM_TRACOS));
			query.setParameter("idContrato", dto.getNumero());
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || atualizaDataCelebracao", StringUtil.convertObjectToJson(dto), e);
			return false;
		}
		return true;
	}

	public void atualizaTipoContratante(DomainGenericDto dto) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ART_CONTRATO C                            ");
		sql.append("    SET C.FK_TIPO_CONTRATANTE = :tipoContratante  ");
		sql.append("  WHERE C.CODIGO = :idContrato                    ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("tipoContratante", dto.getId());
			query.setParameter("idContrato", dto.getNumero());
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || atualizaTipoContratante", StringUtil.convertObjectToJson(dto), e);
		}
	}

	public void atualizaFinalidade(DomainGenericDto dto) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ART_CONTRATO C                 ");
		sql.append("    SET C.FK_FINALIDADE = :finalidade  ");
		sql.append("  WHERE C.CODIGO = :idContrato         ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("finalidade", dto.getId());
			query.setParameter("idContrato", dto.getNumero());
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || atualizaFinalidade", StringUtil.convertObjectToJson(dto), e);
		}
	}

	public void atualizaCodigoObraServico(DomainGenericDto dto) {

		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ART_CONTRATO C                              ");
		sql.append("    SET C.CODIGO_OBRA_SERVICO = :codigoObraServico  ");
		sql.append("  WHERE C.CODIGO = :idContrato                      ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("codigoObraServico", dto.getDescricao());
			query.setParameter("idContrato", dto.getNumero());
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || atualizaCodigoObraServico", StringUtil.convertObjectToJson(dto), e);
		}
	}

	public void atualizaContratante(DomainGenericDto dto) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ART_CONTRATO C                                     ");
		
		if (dto.getCodigo() == 0) {
			sql.append("    SET C.FK_PESSOA = NULL,                            ");
			sql.append("        C.NOME_CONTRATANTE = NULL,                     ");
			sql.append("        C.FK_TIPO_CONTRATANTE = NULL                   ");
		} else {
			sql.append("    SET C.FK_PESSOA = :idContratante,                  ");
			sql.append("        C.NOME_CONTRATANTE = :nomeContratante          ");
		}
		
		sql.append("  WHERE C.CODIGO = :idContrato                             ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			if (dto.getCodigo() != 0) {
				query.setParameter("idContratante", dto.getCodigo());
				query.setParameter("nomeContratante", dto.getNome().toUpperCase());
			}			
			query.setParameter("idContrato", dto.getNumero());
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || atualizaContratante", StringUtil.convertObjectToJson(dto), e);
		}
	}

	public void atualizaProprietario(DomainGenericDto dto) {
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ART_CONTRATO C                                    ");
		
		if (dto.getCodigo() == 0) {
			sql.append("    SET C.FK_PROPRIETARIO = NULL                      ");
		} else {
			sql.append("    SET C.FK_PROPRIETARIO = :idContratante            ");
		}
		
		sql.append("  WHERE C.CODIGO = :idContrato                            ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			if (dto.getCodigo() != 0) {
				query.setParameter("idContratante", dto.getCodigo());
			}			
			query.setParameter("idContrato", dto.getNumero());
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || atualizaProprietario", StringUtil.convertObjectToJson(dto), e);
		}
		
	}

	public void atualizaEnderecoContrato(DomainGenericDto dto) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ART_CONTRATO C                             ");
		
		if (dto.getIdString().equals("0")) {
			sql.append("    SET C.FK_ENDERECO = NULL                   ");
		} else {
			sql.append("    SET C.FK_ENDERECO = :idEndereco            ");
		}
		
		sql.append("  WHERE C.CODIGO = :idContrato                     ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			if (!dto.getIdString().equals("0")) {
				query.setParameter("idEndereco", Long.parseLong(dto.getIdString()));
			}			
			query.setParameter("idContrato", dto.getNumero());
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || atualizaEnderecoContrato", StringUtil.convertObjectToJson(dto), e);
		}
		
	}

	public void atualizaEnderecoContratante(DomainGenericDto dto) {
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ART_CONTRATO C                             ");
		
		if (dto.getIdString().equals("0")) {
			sql.append("    SET C.FK_ENDERECO_CONTRATANTE = NULL        ");
		} else {
			sql.append("    SET C.FK_ENDERECO_CONTRATANTE = :idEndereco ");
		}
		
		sql.append("  WHERE C.CODIGO = :idContrato                     ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			if (!dto.getIdString().equals("0")) {
				query.setParameter("idEndereco", Long.parseLong(dto.getIdString()));
			}			
			query.setParameter("idContrato", dto.getNumero());
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || atualizaEnderecoContratante", StringUtil.convertObjectToJson(dto), e);
		}
	}

	public void atualizaArbitragem(DomainGenericDto dto) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ART_CONTRATO C              ");
		sql.append("    SET C.ARBITRAGEM = :arbitragem  ");
		sql.append("  WHERE C.FK_ART = :numeroArt       ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("arbitragem", dto.isChecked());
			query.setParameter("numeroArt", dto.getNumero());
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || atualizaArbitragem", StringUtil.convertObjectToJson(dto), e);
		}
	}

	public boolean atualizaFinalizado(String idContrato, boolean finalizado) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ART_CONTRATO C              ");
		sql.append("    SET C.FINALIZADO = :finalizado  ");
		sql.append("  WHERE C.CODIGO = :idContrato      ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("finalizado", finalizado);
			query.setParameter("idContrato", idContrato);
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || atualizaFinalizado", StringUtil.convertObjectToJson(idContrato + " - " + finalizado), e);
			return false;
		}
		return true;
	}
	
	public List<Long> getListaDeCodigosDasAtividadesDoContratoPor(String idContrato) {
		List<Long> lista = null;

		StringBuilder sql = new StringBuilder();

		sql.append(" SELECT C.FK_ATIVIDADE               ");
		sql.append("   FROM ART_CONTRATO_ATIVIDADE C     ");
		sql.append("  WHERE C.FK_CONTRATO = :idContrato  ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idContrato", idContrato);

			Iterator<?> it = query.getResultList().iterator();
			
			if (query.getResultList().size() > 0) {
				lista = new ArrayList<Long>();
				while (it.hasNext()) {
					BigDecimal idAtividade = (BigDecimal) it.next();
					lista.add(idAtividade.setScale(0, BigDecimal.ROUND_UP).longValueExact());
				}
			}

		}catch (NoResultException e) {
			return lista;
		}catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || getListaDeCodigosDasAtividadesDoContratoPor", StringUtil.convertObjectToJson(idContrato), e);
		}

		return lista;
	}
	
	public List<Long> getListaDeCodigosDasEspecificacoesDoContratoPor(String idContrato) {
		List<Long> lista = null;

		StringBuilder sql = new StringBuilder();

		sql.append(" SELECT C.FK_ESPECIFICACAO               ");
		sql.append("   FROM ART_CONTRATO_ESPECIFICACAO C     ");
		sql.append("  WHERE C.FK_CONTRATO = :idContrato  ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idContrato", idContrato);

			Iterator<?> it = query.getResultList().iterator();
			
			if (query.getResultList().size() > 0) {
				lista = new ArrayList<Long>();
				while (it.hasNext()) {
					
					BigDecimal idAtividade = (BigDecimal) it.next();

					lista.add(idAtividade.setScale(0, BigDecimal.ROUND_UP).longValueExact());
				}
			}

		}catch (NoResultException e) {
			return lista;
		}catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || getListaDeCodigosDasEspecificacoesDoContratoPor", StringUtil.convertObjectToJson(idContrato), e);
		}

		return lista;
	}
	
	public List<Long> getListaDeCodigosDosComplementosDoContratoPor(String idContrato) {
		List<Long> lista = null;

		StringBuilder sql = new StringBuilder();

		sql.append(" SELECT C.FK_COMPLEMENTO               ");
		sql.append("   FROM ART_CONTRATO_COMPLEMENTO C     ");
		sql.append("  WHERE C.FK_CONTRATO = :idContrato  ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idContrato", idContrato);

			Iterator<?> it = query.getResultList().iterator();
			
			if (query.getResultList().size() > 0) {
				lista = new ArrayList<Long>();
				while (it.hasNext()) {
					
					BigDecimal idAtividade = (BigDecimal) it.next();

					lista.add(idAtividade.setScale(0, BigDecimal.ROUND_UP).longValueExact());
				}
			}

		}catch (NoResultException e) {
			return lista;
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || getListaDeCodigosDosComplementosDoContratoPor", StringUtil.convertObjectToJson(idContrato), e);
		}

		return lista;
	}

	public void atualizaDataFim(ContratoArt contratoArt) {
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ART_CONTRATO C              ");
		sql.append("    SET C.DATA_FIM = :dataFim ");
		sql.append("  WHERE C.CODIGO = :idContrato      ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("dataFim", contratoArt.getDataFim());
			query.setParameter("idContrato", contratoArt.getId());
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || atualizaDataFim", StringUtil.convertObjectToJson(contratoArt), e);
		}
		
	}

	public void limpaDataFimPrazoMesEDia(DomainGenericDto dto) {
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ART_CONTRATO C         ");
		sql.append("    SET C.DATA_FIM = null      ");
		sql.append("    ,C.PRAZO_MES = null        ");
		sql.append("    ,C.PRAZO_DIA = null        ");
		sql.append("  WHERE C.CODIGO = :idContrato ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idContrato", dto.getNumero());
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || limpaDataFimPrazoMesEDia", StringUtil.convertObjectToJson(dto), e);
		}
		
	}

	public boolean todosOsContratosTemDataInicioNoMesmoMesEAno(String numeroArt) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(C.CODIGO) FROM ART_CONTRATO C            ");
		sql.append("  WHERE C.FK_ART = :numeroArt                          ");
		sql.append("    AND TO_CHAR(C.DATA_INICIO, 'MM/YYYY') <>           ");
		sql.append("       (SELECT TO_CHAR(MIN(C.DATA_INICIO), 'MM/YYYY')  ");
		sql.append("          FROM ART_CONTRATO C                          ");
		sql.append("         WHERE C.FK_ART = :numeroArt)                  ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("numeroArt", numeroArt);
			
			BigDecimal resultado = (BigDecimal) query.getSingleResult();
			
			return resultado.compareTo(new BigDecimal(0)) == 0;
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || todosOsContratosTemDataInicioNoMesmoMesEAno", StringUtil.convertObjectToJson(numeroArt), e);
		}
		return false;
	}

	public int getTotalDeContratosDaArt(String numeroArt) {
		
		int total = 0;
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(C.CODIGO) FROM ART_CONTRATO C            ");
		sql.append("  WHERE C.FK_ART = :numeroArt                          ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("numeroArt", numeroArt);
			
			total = Integer.parseInt(query.getSingleResult().toString());
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || getTotalDeContratosDaArt", StringUtil.convertObjectToJson(numeroArt), e);
		}
		return total;
	}

	/**
	 * Verifica se todos os contratos tem prazo de até 32 dias, diminuindo a data fim da data início,
	 * a quantidade 32 dias é em razão dos finais de semana
	 * @param numeroArt
	 * @return boolean
	 */
	public boolean todosOsContratosTemPrazoAteTrintaEDoisDias(String numeroArt) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(C.CODIGO)                      ");
		sql.append("   FROM ART_CONTRATO C                       ");
		sql.append("  WHERE C.FK_ART = :numeroArt                ");
		sql.append("    AND ( (C.DATA_FIM - C.DATA_INICIO) > 32  ");
		sql.append("         OR C.DATA_FIM IS NULL )             ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("numeroArt", numeroArt);
			
			BigDecimal resultado = (BigDecimal) query.getSingleResult();
			
			return resultado.compareTo(new BigDecimal("0")) == 0;
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || todosOsContratosTemPrazoAteTrintaEDoisDias", StringUtil.convertObjectToJson(numeroArt), e);
		}
		return false;
	}

	public void atualizaValorReceberCalculadoETipoTaxa(ContratoArt contrato) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ART_CONTRATO C                    ");
		sql.append("    SET C.VALOR_RECEBER = :valorReceber   ");
		sql.append("    ,C.VALOR_CALCULADO = :valorCalculado  ");
		sql.append("    ,C.FK_ART_TIPO_TAXA = :tipoTaxa       ");
		sql.append("  WHERE C.CODIGO = :idContrato            ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idContrato", contrato.getId());
			query.setParameter("valorReceber", contrato.getValorReceber());
			query.setParameter("valorCalculado", contrato.getValorCalculado());
			query.setParameter("tipoTaxa", contrato.getTipoTaxa().getId());
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || atualizaValorReceberCalculadoETipoTaxa", StringUtil.convertObjectToJson(contrato), e);
		}
	}

	public boolean todosOsValoresDeContratosSaoInferioresAMil(String numeroArt) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(C.CODIGO)                      ");
		sql.append("   FROM ART_CONTRATO C                       ");
		sql.append("  WHERE C.FK_ART = :numeroArt                ");
		sql.append("    AND C.VALOR_CONTRATO > 1000              ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("numeroArt", numeroArt);
			
			BigDecimal valor = (BigDecimal) query.getSingleResult(); 
			
			return  valor.compareTo(new BigDecimal(0)) == 0;
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || todosOsValoresDeContratosSaoInferioresAMil", StringUtil.convertObjectToJson(numeroArt), e);
		}
		return false;
	}

	public BigDecimal getSomaDoValorCalculadoDosContratos(String numeroArt) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT SUM(C.VALOR_CALCULADO)  ");
		sql.append("   FROM ART_CONTRATO C          ");
		sql.append("  WHERE C.FK_ART = :numeroArt   ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("numeroArt", numeroArt);
			
			BigDecimal valor = (BigDecimal) query.getSingleResult();
			
			return valor;
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || getSomaDoValorCalculadoDosContratos", StringUtil.convertObjectToJson(numeroArt), e);
		}
		
		return null;
	}

	public void darBaixaNosContratos(String numeroArt, TipoBaixaArtEnum tipoBaixa) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ART_CONTRATO C                    ");
		sql.append("    SET C.FK_BAIXA = :tipoBaixa,          ");
		sql.append("        C.DATA_BAIXA = :dataBaixa         ");
		sql.append("  WHERE C.FK_ART = :numeroArt             ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("tipoBaixa", tipoBaixa.getId());
			query.setParameter("dataBaixa", new Date());
			query.setParameter("numeroArt", numeroArt);
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || darBaixaNosContratos", StringUtil.convertObjectToJson(numeroArt + " - " + tipoBaixa), e);
		}
	}

	public boolean verificaSeTodosOsContratosEstaoFinalizados(String numeroArt) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(C.CODIGO)                                  ");
		sql.append("   FROM ART_CONTRATO C                                   ");
		sql.append("  WHERE C.FK_ART = :numeroArt                            ");
		sql.append("    AND (C.FINALIZADO = 0 OR C.FINALIZADO IS NULL )      ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("numeroArt", numeroArt);
			
			BigDecimal valor = (BigDecimal) query.getSingleResult(); 
			
			return valor.compareTo(new BigDecimal(0)) == 0;
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || verificaSeTodosOsContratosEstaoFinalizados", StringUtil.convertObjectToJson(numeroArt), e);
		}
		return false;
	}
	
	public boolean ehNecessarioValidarPavimentos(ContratoArt contratoArt) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(AOP.CODIGO)                                                            ");
		sql.append("   FROM ART_OBRIGATORIO_PAV AOP                                                      ");
		sql.append("  WHERE 1=1                                                                          ");
		if (contratoArt.temAtividades()) { 
			sql.append("    AND (AOP.FK_CODIGO_ATIVIDADE IN (:idsAtividades) OR AOP.FK_CODIGO_ATIVIDADE = '0')          ");
		} else {
			sql.append("    AND AOP.FK_CODIGO_ATIVIDADE = '0'          ");
		}
		if (contratoArt.temEspecificacoes()) {
			sql.append("    AND (AOP.FK_CODIGO_ESPECIFICACAO IN (:idsEspecificacoes) OR AOP.FK_CODIGO_ESPECIFICACAO = '0') ");
		} else {
			sql.append("    AND  AOP.FK_CODIGO_ESPECIFICACAO = '0' ");
		}
		if (contratoArt.temComplementos()) {
			sql.append("    AND (AOP.FK_CODIGO_COMPLEMENTO IN (:idsComplementos) OR AOP.FK_CODIGO_COMPLEMENTO = '0')     ");
		} else {
			sql.append("    AND AOP.FK_CODIGO_COMPLEMENTO = '0'     ");
		}

		try {
			Query query = em.createNativeQuery(sql.toString());
			if (contratoArt.temAtividades()) {
				query.setParameter("idsAtividades", contratoArt.getListCodigoAtividades());
			}
			if (contratoArt.temEspecificacoes()) {
				query.setParameter("idsEspecificacoes", contratoArt.getListCodigoEspecificacoes());
			}
			if (contratoArt.temComplementos()) {
				query.setParameter("idsComplementos", contratoArt.getListCodigoComplementos());
			}
			
			BigDecimal valor = (BigDecimal) query.getSingleResult(); 
			
			return valor.compareTo(new BigDecimal(0)) > 0;
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || ehNecessarioValidarPavimentos", StringUtil.convertObjectToJson(contratoArt), e);
		}
		return false;
	}

	public boolean complementoNaoEhPermitidoEnderecoDeOutroEstado(ContratoArt contratoArt) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(ACE.FK_CODIGO_COMPLEMENTO)                     ");
		sql.append("   FROM ART_COMPL_OUTROS_ESTADOS ACE                         ");
		sql.append("  WHERE ACE.FK_CODIGO_COMPLEMENTO IN (:idsComplementos)      ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idsComplementos", contratoArt.getListCodigoComplementos());
			
			BigDecimal valor = (BigDecimal) query.getSingleResult(); 
			
			return valor.compareTo(new BigDecimal(0)) == 0;
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || complementoNaoEhPermitidoEnderecoDeOutroEstado", StringUtil.convertObjectToJson(contratoArt), e);
		}
		return false;
	}

	public boolean profissionalPossuiAtribuicao90922(Long idProfissional) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(ATR.NUMERO)                                                      ");
	    sql.append("   FROM CAD_PROFXESPEC PE                                                      ");
	    sql.append("   JOIN CAD_ESPECIALIDADES E ON ( PE.FK_CODIGO_ESPECIALIDADES = E.CODIGO )     ");
	    sql.append("   JOIN CAD_ESPECXATRIB A ON ( A.FK_CODIGO_ESPECIALIDADES = E.CODIGO )         ");
	    sql.append("   JOIN CAD_ATRIBUICOES ATR ON ( ATR.CODIGO = A.FK_CODIGO_ATRIBUICOES )        ");
	    sql.append("   WHERE ATR.NUMERO = 90922                                                    "); 
		sql.append("     AND E.ATIVO = 1                                                           ");
		sql.append("     AND PE.DATACANCELAMENTO IS NULL                                           ");
		sql.append("     AND PE.FK_CODIGO_PROFISSIONAIS = :idProfissional                          ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idProfissional", idProfissional);
			
			BigDecimal valor = (BigDecimal) query.getSingleResult(); 
			
			return valor.compareTo(new BigDecimal(0)) > 0;
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || profissionalPossuiAtribuicao90922", StringUtil.convertObjectToJson(idProfissional), e);
		}
		return false;
	}
	
	public boolean atualizaCodigoESequencial(String codigoAtualContrato, String novoCodigo, Long novoSequencial) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ART_CONTRATO C                      ");
		sql.append("    SET C.CODIGO = :novoCodigo              ");
		sql.append("    , C.SEQUENCIAL = :novoSequencial        ");
		sql.append("  WHERE C.CODIGO = :idContrato              ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idContrato", codigoAtualContrato);
			query.setParameter("novoCodigo", novoCodigo);
			query.setParameter("novoSequencial", novoSequencial);
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || atualizaCodigoESequencial", StringUtil.convertObjectToJson(codigoAtualContrato +"-"+ novoCodigo +"-"+ novoSequencial), e);
			return false;
		}
		return true;
	}

	public List<String> getListaCodigosContratosByArt(String numeroArt) {
		
		List<String> lista = null;

		StringBuilder sql = new StringBuilder();

		sql.append(" SELECT C.CODIGO              ");
		sql.append("   FROM ART_CONTRATO C        ");
		sql.append("  WHERE C.FK_ART = :numeroArt ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("numeroArt", numeroArt);

			Iterator<?> it = query.getResultList().iterator();
			
			if (query.getResultList().size() > 0) {
				lista = new ArrayList<String>();
				while (it.hasNext()) {
					String idContrato = (String) it.next();
					lista.add(idContrato);
				}
			}

		}catch (NoResultException e) {
			return lista;
		}catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || getListaCodigosContratosByArt", StringUtil.convertObjectToJson(numeroArt), e);
		}

		return lista;
		
	}

	public void atualizaEnderecoProprietario(DomainGenericDto dto) {
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ART_CONTRATO C                             ");
		
		if (dto.getIdString().equals("0")) {
			sql.append("    SET C.FK_ENDERECO_PROPRIETARIO = NULL        ");
		} else {
			sql.append("    SET C.FK_ENDERECO_PROPRIETARIO = :idEndereco ");
		}
		
		sql.append("  WHERE C.CODIGO = :idContrato                     ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			if (!dto.getIdString().equals("0")) {
				query.setParameter("idEndereco", Long.parseLong(dto.getIdString()));
			}			
			query.setParameter("idContrato", dto.getNumero());
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || atualizaEnderecoProprietario", StringUtil.convertObjectToJson(dto), e);
		}
		
	}

	public List<ContratoArt> getContratosComDescricaoComplementar(String numeroArt) {
		
		List<ContratoArt>  listContrato = new ArrayList<ContratoArt>();

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT C FROM ContratoArt C                   ");
		sql.append("  WHERE C.art.numero = :numeroArt              ");
		sql.append("    AND C.descricaoComplementares IS NOT NULL  ");
		sql.append("  ORDER BY C.sequencial                        ");

		try {
			TypedQuery<ContratoArt> query = em.createQuery(sql.toString(), ContratoArt.class);
			query.setParameter("numeroArt", numeroArt);

			listContrato = query.getResultList();

		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || getContratosComDescricaoComplementar", StringUtil.convertObjectToJson(numeroArt), e);
		}

		return listContrato;
	}

	public void aplicarModeloContrato(String numeroArtModelo, String novoNumeroArt, Long enderecoContrato, Long enderecoContratante, Long enderecoProprietario) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" INSERT INTO ART_CONTRATO            ");
		sql.append("  (CODIGO, ");
		sql.append("  	ACESSIBILIDADE,	ARBITRAGEM, ART_VINCULADA_CONTRATO,	ASS_CONTRATANTE, ");
		sql.append("  	CODIGO_OBRA_SERVICO, DATA_ASS, DATA_BAIXA, DATA_CADASTRO, DATA_CELEBRACAO, DATA_FIM, DATA_INICIO, ");
		sql.append("  	DATA_ULTIMA_ALTERACAO, DESCRICAO_CARGO_FUNCAO, DESCRICAO_COMPLEMENTARES, DESCRICAO_FATO_GERADOR, ");
		sql.append("  	FINALIZADO, FK_ART, FK_ART_TIPO_CARGO_FUNCAO, FK_ART_TIPO_DOCUMENTO, FK_ART_TIPO_FUNCAO, ");
		sql.append("  	FK_ART_TIPO_TAXA, FK_ART_TIPO_UNIDADE_ADM, FK_ART_TIPO_VINCULO,	FK_BAIXA, FK_CONVENIO_PUBLICO, ");
		sql.append("  	FK_ENDERECO, FK_ENDERECO_CONTRATANTE, FK_ENDERECO_PROPRIETARIO,	FK_FINALIDADE, FK_FUNCIONARIO_ALTERACAO, ");
		sql.append("  	FK_FUNCIONARIO_CADASTRO, FK_PESSOA, FK_PROPRIETARIO, FK_RAMO_ART, FK_RECEITA, FK_TIPO_ACAO_INSTITUCIONAL, ");
		sql.append("  	FK_TIPO_COBRANCA_CONTRATO, FK_TIPO_CONTRATANTE, MIGRADO, MOTIVO_BAIXA_OUTROS, NHHJT,NOME_CONTRATANTE, ");
		sql.append("  	NUMERO_CONTRATO, NUMERO_PAVTOS, PRAZO_DETERMINADO, PRAZO_DIA, PRAZO_MES, PROLABORE, SALARIO, SEQUENCIAL, ");
		sql.append("  	TIPO_PESSOA, VALOR_CALCULADO, VALOR_CONTRATO, VALOR_INSERICO_MANUALMENTE, VALOR_PAGO, VALOR_RECEBER )");
		sql.append(" (SELECT :novoNumeroArt||'-'||SEQUENCIAL, ");
		sql.append("  	ACESSIBILIDADE,	ARBITRAGEM, ART_VINCULADA_CONTRATO,	ASS_CONTRATANTE, ");
		sql.append("  	CODIGO_OBRA_SERVICO, DATA_ASS, DATA_BAIXA, SYSDATE, DATA_CELEBRACAO, DATA_FIM, DATA_INICIO, ");
		sql.append("  	DATA_ULTIMA_ALTERACAO, DESCRICAO_CARGO_FUNCAO, DESCRICAO_COMPLEMENTARES, DESCRICAO_FATO_GERADOR, ");
		sql.append("  	0, :novoNumeroArt, FK_ART_TIPO_CARGO_FUNCAO, FK_ART_TIPO_DOCUMENTO, FK_ART_TIPO_FUNCAO, ");
		sql.append("  	FK_ART_TIPO_TAXA, FK_ART_TIPO_UNIDADE_ADM, FK_ART_TIPO_VINCULO,	FK_BAIXA, FK_CONVENIO_PUBLICO, ");
		sql.append("  	:enderecoContrato, :enderecoContratante, :enderecoProprietario,	FK_FINALIDADE, FK_FUNCIONARIO_ALTERACAO, ");
		sql.append("  	FK_FUNCIONARIO_CADASTRO, FK_PESSOA, FK_PROPRIETARIO, FK_RAMO_ART, FK_RECEITA, FK_TIPO_ACAO_INSTITUCIONAL, ");
		sql.append("  	FK_TIPO_COBRANCA_CONTRATO, FK_TIPO_CONTRATANTE, MIGRADO, MOTIVO_BAIXA_OUTROS, NHHJT,NOME_CONTRATANTE, ");
		sql.append("  	NUMERO_CONTRATO, NUMERO_PAVTOS, PRAZO_DETERMINADO, PRAZO_DIA, PRAZO_MES, PROLABORE, SALARIO, SEQUENCIAL, ");
		sql.append("  	TIPO_PESSOA, VALOR_CALCULADO, VALOR_CONTRATO, VALOR_INSERICO_MANUALMENTE, VALOR_PAGO, VALOR_RECEBER ");
		sql.append("  FROM ART_CONTRATO               ");
		sql.append("  WHERE FK_ART = :numeroArtModelo)     ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("numeroArtModelo", numeroArtModelo);
			query.setParameter("novoNumeroArt", novoNumeroArt);
			query.setParameter("enderecoContrato", enderecoContrato != null ? enderecoContrato : "");
			query.setParameter("enderecoContratante", enderecoContratante != null ? enderecoContratante : "");
			query.setParameter("enderecoProprietario", enderecoProprietario != null ? enderecoProprietario : "");
			
			query.executeUpdate();
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || aplicarModeloContrato", StringUtil.convertObjectToJson(numeroArtModelo + " - " + novoNumeroArt), e);
		}
	}
	
	public void aplicarModeloAtividades(String numeroArtModelo, String novoNumeroArt) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" INSERT INTO ART_CONTRATO_ATIVIDADE (FK_CONTRATO, FK_ATIVIDADE)      ");
		sql.append("(SELECT :novoNumeroArt||'-'||SUBSTR(A.FK_CONTRATO, INSTR(A.FK_CONTRATO, '-')+1) , A.FK_ATIVIDADE FROM ART_CONTRATO_ATIVIDADE A ");		
		sql.append(" WHERE A.FK_CONTRATO IN (SELECT CODIGO FROM ART_CONTRATO WHERE FK_ART = :numeroArtModelo)) ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("novoNumeroArt", novoNumeroArt);
			query.setParameter("numeroArtModelo", numeroArtModelo);
			
			query.executeUpdate();
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || aplicarModeloAtividades", StringUtil.convertObjectToJson(novoNumeroArt + " - " + numeroArtModelo), e);
		}
	}
	
	public void aplicarModeloEspecificacoes(String numeroArtModelo, String novoNumeroArt) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" INSERT INTO ART_CONTRATO_ESPECIFICACAO (FK_CONTRATO, FK_ESPECIFICACAO)      ");
		sql.append("(SELECT :novoNumeroArt||'-'||SUBSTR(A.FK_CONTRATO, INSTR(A.FK_CONTRATO, '-')+1) , A.FK_ESPECIFICACAO FROM ART_CONTRATO_ESPECIFICACAO A ");		
		sql.append(" WHERE A.FK_CONTRATO IN (SELECT CODIGO FROM ART_CONTRATO WHERE FK_ART = :numeroArtModelo)) ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("novoNumeroArt", novoNumeroArt);
			query.setParameter("numeroArtModelo", numeroArtModelo);
			
			query.executeUpdate();
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || aplicarModeloEspecificacoes", StringUtil.convertObjectToJson(numeroArtModelo + " - " + novoNumeroArt), e);
		}
	}
	
	public void aplicarModeloComplementos(String numeroArtModelo, String novoNumeroArt) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" INSERT INTO ART_CONTRATO_COMPLEMENTO (FK_CONTRATO, FK_COMPLEMENTO)      ");
		sql.append("(SELECT :novoNumeroArt||'-'||SUBSTR(A.FK_CONTRATO, INSTR(A.FK_CONTRATO, '-')+1) , A.FK_COMPLEMENTO FROM ART_CONTRATO_COMPLEMENTO A ");		
		sql.append(" WHERE A.FK_CONTRATO IN (SELECT CODIGO FROM ART_CONTRATO WHERE FK_ART = :numeroArtModelo)) ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("novoNumeroArt", novoNumeroArt);
			query.setParameter("numeroArtModelo", numeroArtModelo);
			
			query.executeUpdate();
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || aplicarModeloComplementos", StringUtil.convertObjectToJson(novoNumeroArt + " - " + numeroArtModelo), e);
		}
	}
	
	public void aplicarModeloQuantificacao(String numeroArtModelo, String novoNumeroArt) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" INSERT INTO ART_QUANTIFICACAO (CODIGO, FK_CONTRATO_ART, FK_UNIDADE_MEDIDA, VALOR ) ");
		sql.append("(SELECT ART_QUANTIFICACAO_SEQ.nextval ,:novoNumeroArt||'-'||SUBSTR(A.FK_CONTRATO_ART, INSTR(A.FK_CONTRATO_ART, '-')+1), A.FK_UNIDADE_MEDIDA, A.VALOR FROM ART_QUANTIFICACAO A ");		
		sql.append(" WHERE A.FK_CONTRATO_ART IN (SELECT CODIGO FROM ART_CONTRATO WHERE FK_ART = :numeroArtModelo)) ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("novoNumeroArt", novoNumeroArt);
			query.setParameter("numeroArtModelo", numeroArtModelo);
			
			query.executeUpdate();
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || aplicarModeloQuantificacao", StringUtil.convertObjectToJson(novoNumeroArt + " - " + numeroArtModelo), e);
		}
	}

	public void atualizaNumeroArtVinculadaAoContrato(DomainGenericDto dto) {
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ART_CONTRATO C                                           ");
		sql.append("    SET C.ART_VINCULADA_CONTRATO = :numeroArtVinculadaAoContrato ");
		sql.append("  WHERE C.CODIGO = :numero                                       ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("numero", dto.getNumero());
			query.setParameter("numeroArtVinculadaAoContrato", dto.getNome());
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || atualizaNumeroArtVinculadaAoContrato", StringUtil.convertObjectToJson(dto), e);
		}	
	}
	
	public boolean numeroArtVinculadaAoContratoEhValido(DomainGenericDto dto) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT A.NUMERO FROM ART_ART A                                                                            ");
		sql.append("  WHERE A.NUMERO = :numeroVinculadaAoContratoEhValido													   ");
		sql.append("  AND A.FK_NATUREZA_ART = 1                                                                                    ");
		sql.append("  AND A.VALOR_PAGO IS NOT NULL                                                                             ");
		sql.append("  AND (A.CANCELADA is null OR A.CANCELADA = 0)                                                             ");
		sql.append("  AND A.FK_EMPRESA NOT IN (SELECT ART.FK_EMPRESA FROM ART_ART ART WHERE ART.NUMERO = :numeroArt) ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("numeroVinculadaAoContratoEhValido", dto.getNome());
			query.setParameter("numeroArt", dto.getRegistro());
			if(query.getResultList().size() > 0) {
				return true;
			}
						
		} catch (NoResultException e) {
			return false;
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || numeroArtVinculadaAoContratoEhValido", StringUtil.convertObjectToJson(dto), e);
		}
		return false;
	}
	
	public boolean verificaSeEhMetroQuadrado (ContratoArt contratoArt) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" select COUNT(ACE.CODIGO)                                                                  					 ");
		sql.append(" from ART_COBRANCA_ESPECIAL ACE                                                            					 ");
		sql.append("   inner join ART_TIPO_TAXA ATT on ATT.CODIGO = ACE.FK_TIPO_TAXA_ART                       					 ");
		sql.append("   inner join ART_ATIVIDADE_TECNICA ATEC ON ATEC.CODIGO = ACE.FK_ATIVIDADE_ART             					 ");
		sql.append("   inner join ART_ESPECIFICACAO_ATIVIDADE ATECA ON ATECA.CODIGO = ACE.FK_ESPECIFICACAO_ART 					 ");
		sql.append("   inner join ART_COMPLEMENTO ATC ON ATC.CODIGO = ACE.FK_COMPLEMENTO_ART                   					 ");
		sql.append(" where ACE.FK_TIPO_TAXA_ART = 1                                                            					 ");
		sql.append(" 	and TO_CHAR(ACE.INICIO_VIGENCIA, 'YYYYMMDD') <= TO_CHAR(sysdate, 'YYYYMMDD')           					 ");
		sql.append(" 	and (TO_CHAR(ACE.FIM_VIGENCIA, 'YYYYMMDD') > TO_CHAR(sysdate, 'YYYYMMDD') OR ACE.FIM_VIGENCIA IS NULL)   ");
		sql.append(" 	and (ACE.FK_ATIVIDADE_ART IN (:idsAtividades)) 									          				 ");
		sql.append(" 	and (ACE.FK_ESPECIFICACAO_ART IN (:idEspecificacoes))            	 									 ");
		sql.append(" 	and (ACE.FK_COMPLEMENTO_ART IN (:idComplemento))            			                                 ");
		sql.append(" 	and (ACE.FK_MODALIDADE = :idModalidade) 																 ");
		
		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idsAtividades", contratoArt.getListCodigoAtividades());
			query.setParameter("idEspecificacoes", contratoArt.getListCodigoEspecificacoes());
			query.setParameter("idComplemento", contratoArt.getListCodigoComplementos());
			query.setParameter("idModalidade", contratoArt.getIdStringModalidade());

			BigDecimal qt = (BigDecimal) query.getSingleResult();
		
			return qt.compareTo(new BigDecimal(0)) > 0; 
		
		} catch (NoResultException e) {
			return true;
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || verificaSeEhMetroQuadrado", StringUtil.convertObjectToJson(contratoArt), e);
		}
		return false;
	}

	public boolean possuiRamoDaModalidadeCivil(Long idProfissional) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(DISTINCT R.CODIGO) FROM CAD_PROFXESPEC P                                          ");
		sql.append("   JOIN CAD_RAMOATIVIDADE_ESPEC E ON ( P.FK_CODIGO_ESPECIALIDADES = E.FK_CODIGO_ESPECIALIDADE ) ");
		sql.append("   JOIN CAD_ATIVIDADES A ON ( A.CODIGO = E.FK_ATIVIDADE )                                       ");
		sql.append("   JOIN CAD_RAMOS R ON ( R.CODIGO = A.FK_RAMO )                                                 ");
		sql.append("  WHERE P.FK_CODIGO_PROFISSIONAIS = :idProfissional                                             ");
		sql.append("    AND P.DATACANCELAMENTO IS NULL                                                              ");
		sql.append("    AND R.FK_MODALIDADE = 1                                                                     ");
		sql.append("    AND E.STATUS = 1                                                                            ");
		sql.append("    AND R.ART = 1                                                                               ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idProfissional", idProfissional);

			BigDecimal qt = (BigDecimal) query.getSingleResult();
		
			return qt.compareTo(new BigDecimal(0)) > 0; 
		
		} catch (NoResultException e) {
			return false;
		} catch (Throwable e) {
			httpGoApi.geraLog("ContratoArtDao || possuiRamoDaModalidadeCivil", StringUtil.convertObjectToJson(idProfissional), e);
		}

		return false;
	}

//	public boolean validaSalarioMinimoProfissional(ContratoArt contrato) {
//		
//		StringBuilder sql = new StringBuilder();
//		sql.append(" SELECT COUNT(S.ID)                                                      ");
//	    sql.append("   FROM CAD_SALARIO_MINIMO S                                                   ");
//	    sql.append("  WHERE S.NUMERO = 90922                                                    "); 
//		sql.append("     AND E.ATIVO = 1                                                           ");
//		sql.append("     AND PE.DATACANCELAMENTO IS NULL                                           ");
//		sql.append("     AND PE.FK_CODIGO_PROFISSIONAIS = :idProfissional                          ");
//		
//		try {
//			Query query = em.createNativeQuery(sql.toString());
//			query.setParameter("ano", DateUtils.getAnoCorrente());
//			query.setParameter("salario", contrato.getSalario());
//			query.setParameter("jornada", contrato.getNHHJT());
//			
//			
//			BigDecimal valor = (BigDecimal) query.getSingleResult(); 
//			
//			return valor.compareTo(new BigDecimal(0)) > 0;
//			
//		} catch (Throwable e) {
////			httpGoApi.geraLog("ContratoArtDao || profissionalPossuiAtribuicao90922", StringUtil.convertObjectToJson(idProfissional), e);
//		}
//		return false;
//	}

}
