package br.org.crea.commons.dao.cadastro.profissional;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.org.crea.commons.converter.cadastro.pessoa.PessoaConverter;
import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.dao.cadastro.pessoa.EnderecoDao;
import br.org.crea.commons.dao.cadastro.pessoa.PessoaDao;
import br.org.crea.commons.dao.corporativo.Page;
import br.org.crea.commons.models.cadastro.EntidadeProfissional;
import br.org.crea.commons.models.cadastro.QuadroTecnico;
import br.org.crea.commons.models.cadastro.dtos.VistoDto;
import br.org.crea.commons.models.cadastro.dtos.profissional.EntidadeProfissionalDto;
import br.org.crea.commons.models.cadastro.dtos.profissional.ProfissionalHomologacaoDto;
import br.org.crea.commons.models.commons.dtos.DomainGenericDto;
import br.org.crea.commons.models.commons.dtos.GenericDto;
import br.org.crea.commons.models.commons.dtos.PesquisaGenericDto;
import br.org.crea.commons.models.corporativo.pessoa.Profissional;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.DateUtils;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class ProfissionalDao extends GenericDao<Profissional, Serializable> {

	@Inject
	HttpClientGoApi httpGoApi;

	@Inject
	PessoaDao pessoaDao;

	@Inject
	EnderecoDao enderecoDao;

	@Inject
	PessoaConverter pessoaConverter;

	public ProfissionalDao() {
		super(Profissional.class);
	}

	public Profissional getProfissionalByRnp(String numeroRnp) {

		Profissional profissional = new Profissional();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT P FROM Profissional P ");
		sql.append("	WHERE P.numeroRNP = :numeroRnp");

		try {
			TypedQuery<Profissional> query = em.createQuery(sql.toString(), Profissional.class);
			query.setParameter("numeroRnp", numeroRnp.trim());

			profissional = query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("ProfissionalDao || getProfissionalByRnp", StringUtil.convertObjectToJson(numeroRnp), e);
		}

		return profissional;
	}

	public boolean existeRnp(String numeroRnp) {

		Profissional profissional = new Profissional();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT P FROM Profissional P ");
		sql.append("	WHERE P.numeroRNP = :numeroRnp");

		try {
			TypedQuery<Profissional> query = em.createQuery(sql.toString(), Profissional.class);
			query.setParameter("numeroRnp", numeroRnp.trim());

			profissional = query.getSingleResult();
			if (profissional != null)
				return true;
		} catch (NoResultException e) {
			return false;
		} catch (Throwable e) {
			httpGoApi.geraLog("ProfissionalDao || existeRnp", StringUtil.convertObjectToJson(numeroRnp), e);
		}

		return true;
	}
	
	public boolean profissionalPossuiRnp(Long idPessoa) {

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(P.CODIGO)       ");
		sql.append("   FROM CAD_PROFISSIONAIS P   ");
		sql.append("  WHERE P.CODIGO = :idPessoa  ");
		sql.append("    AND P.RNP IS NOT NULL     ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idPessoa", idPessoa);

			BigDecimal resultado = (BigDecimal) query.getSingleResult();
	 	    
		 	return resultado.compareTo(new BigDecimal(0)) > 0;
		 	
		} catch (NoResultException e) {
			return false;
		} catch (Throwable e) {
			httpGoApi.geraLog("ProfissionalDao || profissionalPossuiRnp", StringUtil.convertObjectToJson(idPessoa), e);
		}

		return false;
	}

	public int getQuantidadeQuadroTecnico(Long id) {

		int result = 0;
		StringBuilder sql = new StringBuilder();
		sql.append("select count(*) from Cad_Quadros_Tecnicos q, Cad_Responsaveis_Tecnicos t ");
		sql.append("    where ( Q.Fk_Codigo_Profissionais = :id or q.FK_CODIGO_EMPRESAS = :id ) ");
		sql.append("    and T.Fk_Codigo_Quadros_Tecnicos = Q.Codigo ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("id", id);

			result = Integer.parseInt(query.getSingleResult().toString());
			return result;

		} catch (NoResultException e) {
			return 0;
		} catch (Throwable e) {
			httpGoApi.geraLog("ProfissionalDao || getQuantidadeQuadroTecnico", StringUtil.convertObjectToJson(id), e);
		}
		return result;
	}

	public List<Profissional> buscaListProfissionalByNome(PesquisaGenericDto dto) {
		List<Profissional> listProfissional = new ArrayList<Profissional>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT P FROM Profissional P ");
		sql.append("	WHERE P.pessoaFisica.nomePessoaFisica LIKE :nomePessoaFisica ");
		sql.append("	AND p.pessoaFisica.tipoPessoa = 'PROFISSIONAL' ");
		sql.append("	AND p.pessoaFisica.situacao.id not in (5,8,12,13,14) ");

		try {
			TypedQuery<Profissional> query = em.createQuery(sql.toString(), Profissional.class);
			query.setParameter("nomePessoaFisica", "%" + dto.getNomePessoa().toUpperCase() + "%");
			
			Page page = new Page(dto.getPage(), dto.getRows());
			page.paginate(query);
			
			listProfissional = query.getResultList();

		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("ProfissionalDao || buscaListProfissionalByNome", StringUtil.convertObjectToJson(dto), e);
		}

		return listProfissional;
	}
	
	public int  totalBuscaListProfissionalByNome(PesquisaGenericDto dto) {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT COUNT(*) FROM CAD_PROFISSIONAIS P");
		sql.append("	JOIN CAD_PESSOAS_FISICAS PF ON(P.CODIGO = PF.CODIGO) ");
		sql.append("	JOIN CAD_PESSOAS PE ON(PE.CODIGO = PF.CODIGO)");
		sql.append("	WHERE PF.NOME LIKE :nomePessoaFisica");
		sql.append("	AND PE.FK_CODIGO_SITUACAO_REGISTRO NOT IN (5,8,12,13,14) ");
		sql.append("	and PE.TIPOPESSOA = 'PROFISSIONAL' ");

		int resultado = 0;
		try {
			
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("nomePessoaFisica", "%" + dto.getNomePessoa().toUpperCase() + "%");
			
			resultado = Integer.parseInt(query.getSingleResult().toString());
			
		}catch (Throwable e) {
			httpGoApi.geraLog("ProfissionalDao || totalBuscaListProfissionalByNome", StringUtil.convertObjectToJson(dto), e);
		}
		
		return resultado;
	}
	
	
	public List<Profissional> buscaProfissionalByCPF(String numeroCPF) {
		List<Profissional> listProfissional = new ArrayList<Profissional>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT P FROM Profissional P ");
		sql.append("	WHERE P.pessoaFisica.cpf = :numeroCPF");
		sql.append("	AND P.pessoaFisica.tipoPessoa = 'PROFISSIONAL' ");
		sql.append("	AND p.pessoaFisica.situacao.id not in (5,8,12,13,14) ");

		try {
			TypedQuery<Profissional> query = em.createQuery(sql.toString(), Profissional.class);
			query.setParameter("numeroCPF", numeroCPF);

			listProfissional = query.getResultList();

		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("ProfissionalDao || buscaProfissionalByCPF", StringUtil.convertObjectToJson(numeroCPF), e);
		}

		return listProfissional;
	}

	public List<Profissional> buscaProfissionalByRegistro(String numeroRegistro) {
		List<Profissional> listProfissional = new ArrayList<Profissional>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT P FROM Profissional P ");
		sql.append("	WHERE P.registro = :numeroRegistro");
		sql.append("	AND P.pessoaFisica.tipoPessoa = 'PROFISSIONAL' ");
		sql.append("	AND p.pessoaFisica.situacao.id not in (5,8,12,13,14) ");

		try {
			TypedQuery<Profissional> query = em.createQuery(sql.toString(), Profissional.class);
			query.setParameter("numeroRegistro", numeroRegistro);

			listProfissional = query.getResultList();

		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("ProfissionalDao || buscaProfissionalByRegistro", StringUtil.convertObjectToJson(numeroRegistro), e);
		}

		return listProfissional;
	}

	public List<Profissional> buscaProfissionalByRNP(String numeroRnp) {

		List<Profissional> listProfissional = new ArrayList<Profissional>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT P FROM Profissional P ");
		sql.append("	WHERE P.numeroRNP = :numeroRnp");
		sql.append("	AND P.pessoaFisica.tipoPessoa = 'PROFISSIONAL' ");
		sql.append("	AND p.pessoaFisica.situacao.id not in (5,8,12,13,14) ");

		try {
			TypedQuery<Profissional> query = em.createQuery(sql.toString(), Profissional.class);
			query.setParameter("numeroRnp", numeroRnp.trim());

			listProfissional = query.getResultList();
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("ProfissionalDao || buscaProfissionalByRNP", StringUtil.convertObjectToJson(numeroRnp), e);
		}

		return listProfissional;
	}

	public Profissional buscaProfissionalPor(String numeroRegistro) {
		Profissional profissional = new Profissional();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT P FROM Profissional P ");
		sql.append("	WHERE P.pessoaFisica.id = :numeroRegistro");

		try {
			TypedQuery<Profissional> query = em.createQuery(sql.toString(), Profissional.class);
			query.setParameter("numeroRegistro", new Long(numeroRegistro));

			profissional = query.getSingleResult();

		} catch (NoResultException e) {
			return profissional;
		} catch (Throwable e) {
			httpGoApi.geraLog("ProfissionalDao || buscaProfissionalPor", StringUtil.convertObjectToJson(numeroRegistro), e);
		}
		return profissional;
	}

	public boolean possuiVistoOutosCreas(Long codigoProfissional) {

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT COUNT(*) FROM cad_vistos_outros_regionais V ");
		sql.append("	WHERE V.fk_cod_profissionais = :codigoProfissional ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("codigoProfissional", codigoProfissional);

			BigDecimal quantidadeVistoRegionais = (BigDecimal) query.getSingleResult();
			return quantidadeVistoRegionais.setScale(0, BigDecimal.ROUND_UP).longValueExact() > new Long(0) ? true : false;

		} catch (Throwable e) {
			httpGoApi.geraLog("ProfissionalDao || possuiVistoOutosCreas", StringUtil.convertObjectToJson(codigoProfissional), e);
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public List<VistoDto> getListVistosOutrosCreas(Long codigoProfissional) {
		List<VistoDto> listVistos = new ArrayList<VistoDto>();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT TO_CHAR(V.datavisto, 'DD/MM/YYYY') data_visto, ");
		sql.append(" 	   TO_CHAR(V.datacancelamento, 'DD/MM/YYYY') data_cancelamento, ");
		sql.append(" 	   V.cancelado esta_cancelado, ");
		sql.append("	   UF.uf regional ");
		sql.append("	   FROM  cad_vistos_outros_regionais V, ");
		sql.append("             cad_ufs UF ");
		sql.append("	   WHERE V.fk_cod_ufs = UF.codigo ");
		sql.append("	   AND   V.fk_cod_profissionais = :codigoProfissional ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("codigoProfissional", codigoProfissional);

			List<Object[]> listObject = query.getResultList();

			for (Object[] object : listObject) {

				VistoDto visto = new VistoDto();
				visto.setDataVisto((String) object[0] != null ? (String) object[0] : "");
				visto.setDataCancelamento((String) object[1] != null ? (String) object[1] : "");
				visto.setEstaCancelado((BigDecimal) object[2] == new BigDecimal(1) ? true : false);
				visto.setRegionaldoVisto((String) object[3] != null ? (String) object[3] : "");
				listVistos.add(visto);
			}

		} catch (NoResultException e) {
			return listVistos;
		} catch (Throwable e) {
			httpGoApi.geraLog("ProfissionalDao || getListVistosOutrosCreas", StringUtil.convertObjectToJson(codigoProfissional), e);
		}

		return listVistos;
	}

	private StringBuilder getSqlQuadroTecnico(PesquisaGenericDto pesquisa) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT R FROM QuadroTecnico R WHERE 1 = 1 ");

		if (pesquisa.getTipoPessoa().equals("PROFISSIONAL")) {
			sql.append("	AND R.profissional.id = :idProfissional ");
		} else {
			sql.append("	AND R.empresa.id = :idEmpresa ");

			if (pesquisa.temIdPessoa()) {
				sql.append("	AND R.profissional.id = :idProfissional ");
			} else if (pesquisa.temNomePessoa()) {
				sql.append("	AND R.profissional.pessoaFisica.nomePessoaFisica LIKE :nomePessoa ");
			}
		}
		return sql;
	}

	private TypedQuery<QuadroTecnico> getQueryQuadroTecnico(String sql, PesquisaGenericDto pesquisa) {
		TypedQuery<QuadroTecnico> query = em.createQuery(sql.toString(), QuadroTecnico.class);

		if (pesquisa.getTipoPessoa().equals("PROFISSIONAL")) {
			query.setParameter("idProfissional", pesquisa.getIdPessoa());
		} else {
			query.setParameter("idEmpresa", pesquisa.getIdEmpresa());
			if (pesquisa.temIdPessoa()) {
				query.setParameter("idProfissional", pesquisa.getRegistroProfissional());
			} else if (pesquisa.temNomePessoa()) {
				query.setParameter("nomePessoa", "%" + pesquisa.getNomePessoa().toUpperCase() + "%");
			}
		}
		return query;
	}

	public List<QuadroTecnico> getQuadroTecnicoByIdProfissional(PesquisaGenericDto pesquisa) {

		List<QuadroTecnico> quadroTecnico = new ArrayList<QuadroTecnico>();

		StringBuilder sql = this.getSqlQuadroTecnico(pesquisa);

		sql.append("    AND R.dataFim IS NULL ");

		try {

			TypedQuery<QuadroTecnico> query = this.getQueryQuadroTecnico(sql.toString(), pesquisa);

			quadroTecnico = query.getResultList().isEmpty() ? null : query.getResultList();

		} catch (Throwable e) {
			httpGoApi.geraLog("ProfissionalDao || getQuadroTecnicoByProfissional", StringUtil.convertObjectToJson(pesquisa), e);
		}

		return quadroTecnico;
	}

	public List<QuadroTecnico> getQuadroTecnicoDetalhado(PesquisaGenericDto pesquisa) {

		List<QuadroTecnico> quadroTecnico = new ArrayList<QuadroTecnico>();

		StringBuilder sql = this.getSqlQuadroTecnico(pesquisa);

		sql.append(" ORDER BY R.dataInicio DESC ");

		try {

			TypedQuery<QuadroTecnico> query = this.getQueryQuadroTecnico(sql.toString(), pesquisa);

			quadroTecnico = query.getResultList();

		} catch (Throwable e) {
			httpGoApi.geraLog("ProfissionalDao || getQuadroTecnicoDetalhado", StringUtil.convertObjectToJson(pesquisa), e);
		}

		return quadroTecnico;
	}

	public Long buscaCodigoModalidade(Long idRegistro) {

		try {

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT Nvl(Max(Fk_Codigo_Modalidades), 0) ");
			sql.append("	FROM Cad_Profxespec ");
			sql.append("	WHERE Fk_Codigo_Profissionais = :idRegistro ");
			sql.append("	AND Fk_Codigo_Escolaridades = 3 ");

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idRegistro", idRegistro);
			return ((BigDecimal) query.getSingleResult()).longValue();

		} catch (Throwable e) {
			httpGoApi.geraLog("ProfissionalDao || buscaCodigoModalidade", StringUtil.convertObjectToJson(idRegistro), e);
		}
		return null;
	}

	public Long buscaCodigoModalidadeHibrida(Long idRegistro) {

		try {

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT Nvl(Max(Fk_Codigo_Modalidades), 0) ");
			sql.append("	FROM Cad_Profxespec ");
			sql.append("	WHERE Fk_Codigo_Profissionais = :idRegistro ");
			sql.append("	AND Fk_Codigo_Escolaridades <> 3");

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idRegistro", idRegistro);
			return ((BigDecimal) query.getSingleResult()).longValue();

		} catch (Throwable e) {
			httpGoApi.geraLog("ProfissionalDao || buscaCodigoModalidadeHibrida", StringUtil.convertObjectToJson(idRegistro), e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<ProfissionalHomologacaoDto> getProfissionaisParaHomologacaoNoPeriodo(Long codigoModalidade, String dataReuniao) {
		List<ProfissionalHomologacaoDto> listResult = new ArrayList<ProfissionalHomologacaoDto>();

		Date dataInclusaoTituloHomologar = DateUtils.convertStringToDate(dataReuniao, DateUtils.YYYY_MM_DD);
		
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT X.fk_codigo_profissionais prof, ");
			sql.append("	   F.nome nome,  ");
			sql.append("	   TO_CHAR(X.datainclusao, 'DD/MM/YYYY') data_inclusao, ");
			sql.append("	   TO_CHAR(P.dataregistro, 'DD/MM/YYYY') data_registro, ");
			sql.append("	   T.descricao titulo, ");
			sql.append("	   E.descricao especialidade, ");
			sql.append("	   Z.descricao nivel, ");
			sql.append("	   R.descricao escola_titulo, ");
			sql.append("	   X.fk_Codigo_Especialidades cod_especialidade ");
			sql.append("	FROM cad_profxespec X, ");
			sql.append("	     cad_pessoas_fisicas F, ");
			sql.append("	     cad_profissionais P, ");
			sql.append("		 cad_titulos T, ");
			sql.append("		 cad_especialidades E, ");
			sql.append("		 cad_escolaridades Z, ");
			sql.append(" 		 cad_instituicoes_ensino I, ");
			sql.append("		 cad_razoes_sociais R ");
			sql.append("	WHERE trunc(X.Datainclusao) <= :dataInclusaoTituloHomologar ");
			sql.append("	AND X.fk_codigo_modalidades = :codigoModalidade ");
			sql.append("	AND X.rel_para_homologacao = 0 ");
			sql.append("	AND F.codigo = X.fk_codigo_profissionais ");
			sql.append("	AND P.codigo = X.fk_codigo_profissionais ");
			sql.append("	AND P.dataregistro IS NOT NULL ");
			sql.append("	AND P.fk_codigo_tipos_registros = 0 ");
			sql.append("	AND T.codigo = X.fk_codigo_titulos ");
			sql.append("	AND E.codigo = X.fk_codigo_especialidades ");
			sql.append("	AND Z.codigo = X.fk_codigo_escolaridades ");
			sql.append("	AND I.codigo = X.fk_codigo_instituicoes_ensino ");
			sql.append("	AND R.fk_codigo_pjs = I.fk_id_pessoas_juridicas ");
			sql.append("	AND R.ativo = 1 ");
			sql.append("	ORDER BY F.Nome ");

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("codigoModalidade", codigoModalidade);
			query.setParameter("dataInclusaoTituloHomologar", dataInclusaoTituloHomologar);

			Iterator<Object> it = query.getResultList().iterator();
			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {
					
					Object[] result = (Object[]) it.next();
					ProfissionalHomologacaoDto profissionalHomologacao = new ProfissionalHomologacaoDto();
					profissionalHomologacao.setRegistro(result[0] != null ? ((BigDecimal)result[0]).longValue() : null );
					profissionalHomologacao.setNome(result[1] != null ? (String) result[1] : "");
					profissionalHomologacao.setDataInclusaoRegistro(result[2] != null ? (String) result[2] : "");
					profissionalHomologacao.setDataRegistro(result[3] != null ? (String) result[3] : "");
					profissionalHomologacao.setTituloCadastrado(result[4] != null ? (String) result[4] : "");
					
					DomainGenericDto especialidade = new DomainGenericDto();
					especialidade.setId(result[8] != null ? ((BigDecimal)result[8]).longValue() : null );
					especialidade.setDescricao(result[5] != null ? (String) result[5] : "" );
					
					profissionalHomologacao.setEspecialidade(especialidade);
					profissionalHomologacao.setNivelTitulo(result[6] != null ? (String) result[6] : "");
					profissionalHomologacao.setEscolaObtencaoTitulo(result[7] != null ? (String) result[7] : "");
					listResult.add(profissionalHomologacao);
				}
			}

		} catch (NoResultException e) {
			return listResult;
		} catch (Throwable e) {
			httpGoApi.geraLog("ProfissionalDao || getProfissionaisParaHomologacaoNoPeriodo", StringUtil.convertObjectToJson(codigoModalidade), e);
		}
		return listResult;
	}
	
	public void atualizaTitutoProfissionalHomologacao(Long codigoProfissional, Long codigoEspecialidade, Date dataUpdate) {
		
		try {
			
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE cad_profxespec P ");
			sql.append("	SET P.rel_para_homologacao = 1, P.data_para_homologacao = :dataUpdate ");
			sql.append(" 	WHERE P.fk_codigo_profissionais = :codigoProfissional ");
			sql.append("	AND P.fk_codigo_especialidades = :codigoEspecialidade ");
			
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("codigoProfissional", codigoProfissional);
			query.setParameter("codigoEspecialidade", codigoEspecialidade);
			query.setParameter("dataUpdate", dataUpdate);
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ProfissionalDao || atualizaTitutoProfissionalHomologacao", StringUtil.convertObjectToJson(codigoProfissional), e);
		}
		
	}

	public void excluirHomologacaoProfissional(GenericDto dto) {
		
		try {
			
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE cad_profxespec P ");
			sql.append("	SET P.rel_para_homologacao = 0, P.data_para_homologacao = null ");
			sql.append(" 	WHERE TO_CHAR(P.data_para_homologacao, 'DD/MM/YYYY') = TO_CHAR(:data, 'DD/MM/YYYY') ");
			sql.append("	AND P.fk_codigo_profissionais in :listRegistro ");
			
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("data", dto.getData());
			query.setParameter("listRegistro", dto.getListIds());
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ProfissionalDao || excluirHomologacaoProfissional", StringUtil.convertObjectToJson(dto), e);
		}
		
		
		try {
			
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE cad_profxespec P ");
			sql.append("	SET P.rel_para_homologacao = 0, P.data_para_homologacao = null ");
			sql.append(" 	WHERE TO_CHAR(P.data_para_homologacao, 'DD/MM/YYYY') = TO_CHAR(:data, 'DD/MM/YYYY') ");
			sql.append("	AND P.fk_codigo_profissionais in :listRegistro ");
			
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("data", dto.getData());
			query.setParameter("listRegistro", dto.getListIds());
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ProfissionalDao || excluirHomologacaoProfissional", StringUtil.convertObjectToJson(dto), e);
		}
	}

	public void trocarDataHomologacaoProfissional(GenericDto trocarDataHomologacaoDto) {
		try {
			
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE cad_profxespec P ");
			sql.append("	SET P.data_para_homologacao = :dataNova ");
			sql.append(" 	WHERE TO_CHAR(P.data_para_homologacao, 'DD/MM/YYYY') = TO_CHAR(:dataAnterior, 'DD/MM/YYYY') ");
			sql.append("	AND P.fk_codigo_profissionais in :listRegistro ");
			
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("dataAnterior", trocarDataHomologacaoDto.getDataInicio());
			query.setParameter("dataNova", trocarDataHomologacaoDto.getDataFim());
			query.setParameter("listRegistro", trocarDataHomologacaoDto.getListIds());
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ProfissionalDao || excluirHomologacaoProfissional", StringUtil.convertObjectToJson(trocarDataHomologacaoDto), e);
		}
		
	}

	public void atualizarNumeroDocumentoHomologacao(GenericDto dto) {
		try {
			
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE cad_profxespec P ");
			sql.append("	SET P.FK_CODIGO_DOCUMENTO = :idDocumento ");
			sql.append(" 	WHERE TO_CHAR(P.DATA_PARA_HOMOLOGACAO, 'DD/MM/YYYY') = :dataFormatada ");
			sql.append("	AND P.FK_CODIGO_MODALIDADES = :idCodigo ");
			
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idDocumento", dto.getIdDocumento());
			query.setParameter("dataFormatada", dto.getDataFormatada());
			query.setParameter("idCodigo", dto.getIdCodigo());
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ProfissionalDao || atualizarNumeroDocumentoHomologacao", StringUtil.convertObjectToJson(dto), e);
		}
		
	}

	public List<ProfissionalHomologacaoDto> recuperaProfissionaisDataModalidade(GenericDto dto) {
		// TODO Auto-generated method stub
		return null;
	}

	public void atualizarDataParaHomologacao(GenericDto dto) {
		
		try {
			
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE cad_profxespec P ");
			sql.append("	SET P.DATA_PARA_HOMOLOGACAO = :novaDataHomologacao ");
			sql.append(" 	WHERE TO_CHAR(P.DATA_PARA_HOMOLOGACAO, 'DD/MM/YYYY') = :dataAnterior ");
			sql.append("	AND P.FK_CODIGO_MODALIDADES = :idCodigo ");
			
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("novaDataHomologacao", DateUtils.format(dto.getData(), DateUtils.DD_MM_YYYY));
			query.setParameter("dataAnterior", dto.getDataInicioFormatada());
			query.setParameter("idCodigo", dto.getIdModalidade());
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ProfissionalDao || atualizarNumeroDocumentoHomologacao", StringUtil.convertObjectToJson(dto), e);
		}
		
	}

	public boolean  existeOpcaoDeVotoNoAnoAtual(Long idProfissional) {
		Integer quantidadeEspecialidades = null;
		try {
			
			StringBuilder sql = new StringBuilder();
			sql.append("	SELECT COUNT(*) FROM CAD_PROFXESPEC PE ");
			sql.append("	WHERE PE.FK_CODIGO_PROFISSIONAIS = :idProfissional ");
			sql.append("	AND EXTRACT(YEAR FROM PE.DATAOPCAO) = :anoAtual ");
			
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idProfissional", idProfissional);
			query.setParameter("anoAtual", Calendar.getInstance().get(Calendar.YEAR));
			
			BigDecimal resp = (BigDecimal) query.getSingleResult();
			quantidadeEspecialidades = resp.intValueExact();
			
			if (quantidadeEspecialidades > 0) {
				return true;
			}
	
		} catch (Throwable e) {
			httpGoApi.geraLog("ProfissionalDao || buscaProfEspecialidadeComDataOpcaoAnoAtual", StringUtil.convertObjectToJson(idProfissional), e);
		}
		return false;
	}
	
	public boolean  existeTituloHabilitadoNoAnoAtual(Long idProfissional) {
		Integer quantidadeEspecialidades = null;
		try {
			
			StringBuilder sql = new StringBuilder();
			sql.append("	SELECT COUNT(*) FROM CAD_ENTIDADES_PROFISSIONAIS PE ");
			sql.append("	WHERE PE.FK_COD_PROF = :idProfissional ");
			sql.append("	AND EXTRACT(YEAR FROM PE.DATAOPCAO) = :anoAtual ");
			
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idProfissional", idProfissional);
			query.setParameter("anoAtual", Calendar.getInstance().get(Calendar.YEAR));
			
			BigDecimal resp = (BigDecimal) query.getSingleResult();
			quantidadeEspecialidades = resp.intValueExact();
			
			if (quantidadeEspecialidades > 0) {
				return true;
			}
	
		} catch (Throwable e) {
			httpGoApi.geraLog("ProfissionalDao || buscaProfEspecialidadeComDataOpcaoAnoAtual", StringUtil.convertObjectToJson(idProfissional), e);
		}
		return false;
	}

	public Integer verificaSeModalidadeEscolhidaExiste(Long idModalidade, String idProfissional) {
		Integer quantidade = 0;
		try {
			
			StringBuilder sql = new StringBuilder();
			sql.append("	SELECT COUNT(PE.FK_CODIGO_MODALIDADES) FROM CAD_PROFXESPEC PE ");
			sql.append("	WHERE PE.FK_CODIGO_PROFISSIONAIS = :idProfissional ");
			sql.append("	AND PE.FK_CODIGO_MODALIDADES = :idModalidade ");
			
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idProfissional", idProfissional);
			query.setParameter("idModalidade", idModalidade);
			
			BigDecimal resp = (BigDecimal) query.getSingleResult();
			quantidade = resp.intValueExact();
	

		} catch (Throwable e) {
			httpGoApi.geraLog("ProfissionalDao || verificaSeModalidadeEscolhidaExiste", StringUtil.convertObjectToJson(idProfissional + " - " + idModalidade), e);
		}
		return quantidade;
	}
	
	public Integer verificaSeTituloPodeSerHabilitado(Long idModalidade, String idProfissional) {
		Integer quantidade = 0;
		try {
			
			StringBuilder sql = new StringBuilder();
			sql.append("	SELECT COUNT(*) FROM CAD_ENTIDADESXMODALIDADES EM ");
			sql.append("	WHERE EM.FK_CODIGO_ENTIDADES = ( SELECT FK_COD_ENT_CLASSE FROM CAD_ENTIDADES_PROFISSIONAIS WHERE FK_COD_PROF = :idProfissional AND OPCAOVOTO = 1 )");
			sql.append("	AND EM.FK_CODIGO_MODALIDADE = :idModalidade");
			
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idProfissional", idProfissional);
			query.setParameter("idModalidade", idModalidade);
			
			BigDecimal resp = (BigDecimal) query.getSingleResult();
			quantidade = resp.intValueExact();
	

		} catch (Throwable e) {
			httpGoApi.geraLog("ProfissionalDao || verificaSeTituloPodeSerHabilitado", StringUtil.convertObjectToJson(idProfissional + " - " + idModalidade), e);
		}
		return quantidade;
	}
	public boolean habilitartituloProfissional(Long idTituloCrea,String idProfissional) {
		try {
			
			StringBuilder sql = new StringBuilder();
			sql.append("	UPDATE CAD_PROFXESPEC PE SET PE.OPCAO = 1, PE.DATAOPCAO = CURRENT_DATE");
			sql.append("	WHERE PE.FK_CODIGO_PROFISSIONAIS = :idProfissional ");
			sql.append("    and PE.FK_CODIGO_ESPECIALIDADES = :idTituloCrea");
			
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idProfissional", idProfissional);
			query.setParameter("idTituloCrea", idTituloCrea);
			query.executeUpdate();

		} catch (Throwable e) {
			httpGoApi.geraLog("ProfissionalDao || habilitartituloProfissional", StringUtil.convertObjectToJson(idTituloCrea + " " + idProfissional ), e);
			return false;
		}
		return true;
	}
	public boolean desabilitaTitulosNaoSelecionados(Long idTituloCrea,String idProfissional) {
		try {
			
			StringBuilder sql = new StringBuilder();
			sql.append("	UPDATE CAD_PROFXESPEC PE SET PE.OPCAO = 0 ");
			sql.append("	WHERE PE.FK_CODIGO_PROFISSIONAIS = :idProfissional ");
			sql.append("    and PE.FK_CODIGO_ESPECIALIDADES != :idTituloCrea");
			
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idProfissional", idProfissional);
			query.setParameter("idTituloCrea", idTituloCrea);
			query.executeUpdate();

		} catch (Throwable e) {
			httpGoApi.geraLog("ProfissionalDao || desabilitaTitulosNaoSelecionados", StringUtil.convertObjectToJson(idTituloCrea + " " + idProfissional ), e);
			return false;
		}
		return true;
	}

	public List<EntidadeProfissional> getListaEntidadesFiliadas(Long idProfissional) {
		List<EntidadeProfissional> list = new ArrayList<EntidadeProfissional>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT P FROM EntidadeProfissional P ");
		sql.append("	WHERE P.idProfissional = :idProfissional");

		try {
			TypedQuery<EntidadeProfissional> query = em.createQuery(sql.toString(), EntidadeProfissional.class);
			query.setParameter("idProfissional", idProfissional);

			list = query.getResultList();
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("ProfissionalDao || getListaEntidadesFiliadas", StringUtil.convertObjectToJson(idProfissional), e);
		}

		return list;
	}
	
	public EntidadeProfissionalDto possuiTituloProfissionalAtivo(EntidadeProfissionalDto dto) {

		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT P.FK_CODIGO_MODALIDADES, P.FK_CODIGO_ESPECIALIDADES FROM CAD_PROFXESPEC P ");
		sql.append("	WHERE P.FK_CODIGO_PROFISSIONAIS = :idProfissional");
		sql.append("	AND P.OPCAO = 1");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idProfissional", dto.getIdProfissional());		
			
			Iterator<?> it = query.getResultList().iterator();

			if (query.getResultList().size() > 0) {
				EntidadeProfissionalDto entidade = new EntidadeProfissionalDto();
				while (it.hasNext()) {
					
					Object[] result = (Object[]) it.next();

					entidade.setCodigoModalidade(result[0] == null ? "" : result[0].toString());
					entidade.setCodigoEspecialidade(result[1] == null ? "" : result[1].toString());
				}
				return entidade;
			}

		} catch (Throwable e) {
			httpGoApi.geraLog("ProfissionalDao || possuiTituloProfissionalAtivo", StringUtil.convertObjectToJson(dto), e);
		}
		return null;
	}

	public Boolean entidadeNaoPodeSerHabilitada(EntidadeProfissionalDto combinacao) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(*) FROM CAD_PROFXESPEC PE, CAD_ENTIDADESXMODALIDADES EM " );
		sql.append(" 	WHERE PE.FK_CODIGO_PROFISSIONAIS = :idProfissional " );
		sql.append("	AND PE.DATACANCELAMENTO IS NULL " );
		
		
		if(combinacao.getCodigoEspecialidade() != null){
			sql.append( " AND PE.FK_CODIGO_ESPECIALIDADES = :codigoEspecialidade" );
		}
		
		if(combinacao.getEntidadeClasse().getId() != null){
			sql.append( "  AND EM.FK_CODIGO_ENTIDADES = :codigoEntidade" );
		}
		if(combinacao.getCodigoModalidade()!= null){
			sql.append( "  AND EM.FK_CODIGO_MODALIDADE = :codigoModalidade" );
		}
		
		Query query = em.createNativeQuery(sql.toString());
		query.setParameter("idProfissional", combinacao.getIdProfissional());
		
		if(combinacao.getCodigoEspecialidade() != null){
			query.setParameter("codigoEspecialidade", combinacao.getCodigoEspecialidade());
		}
		
		if(combinacao.getEntidadeClasse().getId() != null){
			query.setParameter("codigoEntidade", combinacao.getEntidadeClasse().getId());
		}
		if(combinacao.getCodigoModalidade()!= null){
			query.setParameter("codigoModalidade", combinacao.getCodigoModalidade());
		}
		if(query.getSingleResult() != null){
			BigDecimal quantidade = (BigDecimal) query.getSingleResult();
			
			if(quantidade.equals(new BigDecimal(0)) ){
				return true;
			}
		}
		return false;
	}

	public boolean habilitarEntidadeEscolhida(EntidadeProfissionalDto dto) {
		try {
			
			StringBuilder sql = new StringBuilder();
			sql.append("	UPDATE CAD_ENTIDADES_PROFISSIONAIS E SET E.OPCAOVOTO = 1, E.DATAOPCAO = CURRENT_DATE");
			sql.append("	WHERE E.FK_COD_PROF = :idProfissional ");
			sql.append("    and E.CODIGO = :idEntidade");
			
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idProfissional", dto.getIdProfissional());
			query.setParameter("idEntidade", dto.getId());
			query.executeUpdate();

		} catch (Throwable e) {
			httpGoApi.geraLog("ProfissionalDao || habilitarEntidadeEscolhida", StringUtil.convertObjectToJson(dto), e);
			return false;
		}
		return true;
	}

	public boolean desabilitarEntidadeEscolhida(EntidadeProfissionalDto dto) {
		try {
			
			StringBuilder sql = new StringBuilder();
			sql.append("	UPDATE CAD_ENTIDADES_PROFISSIONAIS E SET E.OPCAOVOTO = 0 ");
			sql.append("	WHERE E.FK_COD_PROF = :idProfissional ");
			sql.append("    and E.CODIGO != :idEntidade");
			
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idProfissional", dto.getIdProfissional());
			query.setParameter("idEntidade", dto.getId());
			query.executeUpdate();

		} catch (Throwable e) {
			httpGoApi.geraLog("ProfissionalDao || desabilitarEntidadeEscolhida", StringUtil.convertObjectToJson(dto), e);
			return false;
		}
		return true;
		
	}

	public void participaCatalogoProfissional(DomainGenericDto dto) {
		
		try {
			StringBuilder sql = new StringBuilder();
			if (dto.isChecked()) {
				sql.append(" INSERT INTO CAD_PESSOASXCATALOGO_CREA        ");
				sql.append(" (FK_CODIGO_CATALOGO_CREA, FK_CODIGO_PESSOAS) ");
				sql.append(" VALUES (1, :idProfissional)                  ");
			} else {
				sql.append(" DELETE FROM CAD_PESSOASXCATALOGO_CREA        ");
				sql.append("  WHERE FK_CODIGO_PESSOAS = :idProfissional   ");
			}
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idProfissional", dto.getId());
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ProfissionalDao || participaCatalogoProfissional", StringUtil.convertObjectToJson(dto), e);
		}
	}

	public boolean getParticipaCatalogoProfissional(Long idProfissional) {
		
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT COUNT(*) FROM CAD_PESSOASXCATALOGO_CREA  ");
			sql.append("  WHERE FK_CODIGO_PESSOAS = :idProfissional      ");
			sql.append("    AND FK_CODIGO_CATALOGO_CREA = 1              ");
			
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idProfissional", idProfissional);
			
			BigDecimal qt = (BigDecimal) query.getSingleResult();
			
			return qt.compareTo(new BigDecimal(0)) > 0; 
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ProfissionalDao || getParticipaCatalogoProfissional", StringUtil.convertObjectToJson(idProfissional), e);
		}
		return false;
	}
}