package br.org.crea.commons.dao.cadastro.pessoa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.dao.corporativo.Page;
import br.org.crea.commons.models.commons.dtos.PesquisaGenericDto;
import br.org.crea.commons.models.corporativo.SituacaoRegistro;
import br.org.crea.commons.models.corporativo.dtos.PessoaDto;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;
import br.org.crea.commons.models.corporativo.pessoa.PessoaFisica;
import br.org.crea.commons.models.corporativo.pessoa.PessoaJuridica;
import br.org.crea.commons.models.corporativo.pessoa.Profissional;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class PessoaDao extends GenericDao<Pessoa, Serializable> {
	
	@Inject HttpClientGoApi httpGoApi;

	public PessoaDao() {
		super(Pessoa.class);
	}

	public Pessoa getPessoa(Long idPessoa) {

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT P FROM Pessoa P ");
		sql.append("	WHERE P.id = :idPessoa ");

		try {
			TypedQuery<Pessoa> query = em.createQuery(sql.toString(), Pessoa.class);
			query.setParameter("idPessoa", idPessoa);
		
			return query.getSingleResult();
		} catch (Throwable e) {
			httpGoApi.geraLog("PessoaDao || Busca Pessoa by IdPessoa", StringUtil.convertObjectToJson(idPessoa), e);
		}
		
		return null;

	}
	
	public int  totalBuscaListPessoaFisicaByNome(PesquisaGenericDto dto) {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT COUNT(*) FROM CAD_PESSOAS_FISICAS R");
		sql.append(" WHERE R.NOME LIKE :nome ");


		int resultado = 0;
		try {
			
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("nome", "%" + dto.getNomePessoa().toUpperCase() + "%");
			
			resultado = Integer.parseInt(query.getSingleResult().toString());
			
		}catch (Throwable e) {
			httpGoApi.geraLog("PessoaDao || totalBuscaListPessoaFisicaByNome", StringUtil.convertObjectToJson(dto), e);
		}
		
		return resultado;
	}

	public List<PessoaFisica> buscaListPessoaFisicaByNome(PesquisaGenericDto dto) {

		List<PessoaFisica> listPessoaFisica = new ArrayList<PessoaFisica>();
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT P FROM PessoaFisica P  ");
		sql.append("	WHERE P.nomePessoaFisica LIKE :nome ");
		sql.append(" ORDER BY P.nomePessoaFisica ");
		
		try {
			TypedQuery<PessoaFisica> query = em.createQuery(sql.toString(), PessoaFisica.class);
			query.setParameter("nome", "%" + dto.getNomePessoa().toUpperCase() + "%");
			
			Page page = new Page(dto.getPage(), dto.getRows());
			page.paginate(query);

			listPessoaFisica = query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("PessoaDao || Busca Pessoa Física por Nome", StringUtil.convertObjectToJson(dto), e);
		}

	
		return listPessoaFisica;

	}
	
	public String buscaNomePessoaFisica(Long idPessoa) {

		StringBuilder sql = new StringBuilder();

		sql.append("SELECT P FROM PessoaFisica P  ");
		sql.append("	WHERE P.id = :idPessoa ");
		
		try {
			
			TypedQuery<PessoaFisica> query = em.createQuery(sql.toString(), PessoaFisica.class);
			query.setParameter("idPessoa", idPessoa);

			return  query.getSingleResult().getNome();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("PessoaDao || buscaNomePessoaFisica", StringUtil.convertObjectToJson(idPessoa), e);
		}
		
		return null;

	}

	public List<PessoaJuridica> buscaListPessoaJuridicaByNome(PesquisaGenericDto dto) {

		List<PessoaJuridica> listPessoaJuridica = new ArrayList<PessoaJuridica>();
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT P.pessoaJuridica FROM RazaoSocial P  ");
		sql.append("	WHERE P.descricao LIKE :descricao ");
		sql.append(" ORDER BY P.descricao ");

		try {
			TypedQuery<PessoaJuridica> query = em.createQuery(sql.toString(), PessoaJuridica.class);
			query.setParameter("descricao", "%" + dto.getRazaoSocial().toUpperCase() + "%");
			
			Page page = new Page(dto.getPage(), dto.getRows());
			page.paginate(query);

			listPessoaJuridica = query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("PessoaDao || buscaListPessoaJuridicaByNome ", StringUtil.convertObjectToJson(dto), e);
		}
		
		return listPessoaJuridica;

	}
	
	
	public int  totalBuscaListPessoaJuridicaByNome(PesquisaGenericDto dto) {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT COUNT(*) FROM CAD_RAZOES_SOCIAIS R");
		sql.append(" WHERE R.DESCRICAO LIKE :descricao ");


		int resultado = 0;
		try {
			
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("descricao", "%" + dto.getRazaoSocial().toUpperCase() + "%");
			
			resultado = Integer.parseInt(query.getSingleResult().toString());
			
		}catch (Throwable e) {
			httpGoApi.geraLog("PessoaDao || totalBuscaListPessoaJuridicaByNome", StringUtil.convertObjectToJson(dto), e);
		}
		
		return resultado;
	}

	public List<PessoaJuridica> buscaPessoaJuridicaByCnpj(String cnpj) {

		StringBuilder sql = new StringBuilder();

		sql.append("SELECT P FROM PessoaJuridica P  ");
		sql.append("	WHERE P.cnpj = :cnpj ");
		
		try {
			TypedQuery<PessoaJuridica> query = em.createQuery(sql.toString(), PessoaJuridica.class);
			query.setParameter("cnpj", cnpj);
			
			return query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("PessoaDao || Busca Pessoa Jurídica por CPF ", cnpj, e);
		}

		return null;
	}

	public List<PessoaFisica> buscaPessoaFisicaByCPF(String numeroCPF) {

		List<PessoaFisica> listPessoaFisica = new ArrayList<PessoaFisica>();
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT P FROM PessoaFisica P  ");
		sql.append("	WHERE P.cpf = :cpf ");

		try {
			TypedQuery<PessoaFisica> query = em.createQuery(sql.toString(), PessoaFisica.class);
			query.setParameter("cpf", numeroCPF);

			listPessoaFisica = query.getResultList();
			ordenaListPessoaFisicaPeloTipo(listPessoaFisica);
			
		} catch (Throwable e) {
			httpGoApi.geraLog("PessoaDao || Busca Pessoa Física por CPF ", StringUtil.convertObjectToJson(numeroCPF), e);
		}

		return listPessoaFisica;
	}
	
	public List<PessoaJuridica> buscaPessoaJuridicaByCnpjMatriz(String cnpj) {

		StringBuilder sql = new StringBuilder();

		sql.append("SELECT P FROM PessoaJuridica P  ");
		sql.append("	WHERE SUBSTRING(P.cnpj,0,8) = :cnpj ");
		
		try {
			TypedQuery<PessoaJuridica> query = em.createQuery(sql.toString(), PessoaJuridica.class);
			query.setParameter("cnpj", cnpj.substring(0, 8));
			
			return query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("PessoaDao || Busca Pessoa Jurídica por CPF ", cnpj, e);
		}

		return null;
	}

	public List<PessoaJuridica> buscaPessoaByCNPJ(String numeroCNPJ) {

		List<PessoaJuridica> listPessoaJuridica = new ArrayList<PessoaJuridica>();
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT P FROM PessoaJuridica P  ");
		sql.append("	WHERE P.cnpj = :cnpj ");
		
		try {
			TypedQuery<PessoaJuridica> query = em.createQuery(sql.toString(), PessoaJuridica.class);
			query.setParameter("cnpj", numeroCNPJ);

			listPessoaJuridica = query.getResultList();
			ordenaListPessoaJuridicaPeloTipo(listPessoaJuridica);
			
		} catch (Throwable e) {
			httpGoApi.geraLog("PessoaDao || Busca Pessoa Jurídicapor CNPJ ", StringUtil.convertObjectToJson(numeroCNPJ), e);
		}

		return listPessoaJuridica;

	}

	public Profissional buscaProfissionalPorNumeroRNP(String numeroRNP) {
		
		
		Profissional profissional = new Profissional();
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT P FROM Profissional P  ");
		sql.append("	WHERE P.numeroRNP = :numeroRNP ");

		try {

			TypedQuery<Profissional> query = em.createQuery(sql.toString(), Profissional.class);
			query.setParameter("numeroRNP", numeroRNP);

			profissional = query.getSingleResult();

		} catch (NoResultException e) {
			profissional = null;
		} catch (Throwable e) {
		  httpGoApi.geraLog("PessoaDao || Busca Profissional por RNP ", StringUtil.convertObjectToJson(numeroRNP), e);
	    }
		
		return profissional;

	}

	public List<Pessoa> buscaPessoaPorIdsPerfil(List<String> idsPerfil) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT P FROM Pessoa P ");
		sql.append("	WHERE P.perfil IN (:idsPerfil) ");

		try {
			
			TypedQuery<Pessoa> query = em.createQuery(sql.toString(), Pessoa.class);
			query.setParameter("idsPerfil", idsPerfil);
		
			return query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("PessoaDao || buscaPessoaPorIdsPerfil", StringUtil.convertObjectToJson(idsPerfil), e);
		}
		
		return null;
	}
	
	public PessoaJuridica getPessoaJuridicaPor(Long idPessoa) {
		PessoaJuridica pessoaJuridica = new PessoaJuridica();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT P FROM PessoaJuridica P ");
		sql.append("	WHERE P.id = :idPessoa ");
		
		try {
			
			TypedQuery<PessoaJuridica> query = em.createQuery(sql.toString(), PessoaJuridica.class);
			query.setParameter("idPessoa", idPessoa);
		
			pessoaJuridica = query.getSingleResult();
			
		} catch (NoResultException e) {
			return pessoaJuridica;
		} catch (Throwable e) {
			httpGoApi.geraLog("PessoaDao || getPessoaJuridicaPor", StringUtil.convertObjectToJson(idPessoa), e);
		}
		return pessoaJuridica;
	}
	
	public PessoaFisica getPessoaFisicaPor(Long idPessoa) {
		PessoaFisica pessoaFisica = new PessoaFisica();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT P FROM PessoaFisica P ");
		sql.append("	WHERE P.id = :idPessoa ");
		
		try {
			
			TypedQuery<PessoaFisica> query = em.createQuery(sql.toString(), PessoaFisica.class);
			query.setParameter("idPessoa", idPessoa);
			pessoaFisica  = query.getSingleResult();
			
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("PessoaDao || getPessoaFisicaPor", StringUtil.convertObjectToJson(idPessoa), e);
		}
		return pessoaFisica;
	}
	
	public void ordenaListPessoaFisicaPeloTipo(List<PessoaFisica> listPessoaFisica) {
		listPessoaFisica.sort((PF1, PF2) -> PF1.getTipoPessoa().getOrdem().compareTo(PF2.getTipoPessoa().getOrdem()));
	}
	
	public void ordenaListPessoaJuridicaPeloTipo(List<PessoaJuridica> listPessoaJuridica) {
		listPessoaJuridica.sort((PJ1, PJ2) -> PJ1.getTipoPessoa().getOrdem().compareTo(PJ2.getTipoPessoa().getOrdem()));
	}

	public List<Pessoa> buscaPessoasPorDescricaoPerfil(String descricaoPerfil) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT P FROM Pessoa P ");
		sql.append("	WHERE P.descricaoPerfil = :descricaoPerfil ");

		try {
			
			TypedQuery<Pessoa> query = em.createQuery(sql.toString(), Pessoa.class);
			query.setParameter("descricaoPerfil", descricaoPerfil);
		
			return query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("PessoaDao || buscaPessoasPorDescricaoPerfil", StringUtil.convertObjectToJson(descricaoPerfil), e);
		}
		
		return null;
	}
	
	public String buscaPerfilPor(Long idPessoa) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT P.DS_PERFIL FROM CAD_PESSOAS P ");
		sql.append("	WHERE P.CODIGO = :idPessoa ");

		try {
			
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idPessoa", idPessoa);
		
			Object resultado = query.getSingleResult();
			
			return resultado != null ? resultado.toString() : null;
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("PessoaDao || buscaPerfilPor", StringUtil.convertObjectToJson(idPessoa), e);
		}
		
		return null;
	}
	
	public void atualizaDescricaoPerfil(String idPerfil, String descricaoPerfil) {

		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE  CAD_PESSOAS P ");
		sql.append("     SET P.DS_PERFIL = :descricaoPerfil ");
		sql.append("	 WHERE P.PERFIL = :idPerfil ");
		
		// FIXME deveria ser passado o idPessoa, não está sendo executado, gerando apenas erros, verificar necessidade deste código
//		StringBuilder sql2 = new StringBuilder();
//		sql2.append("DELETE FROM SIACOL_HABILIDADE_PESSOA SHP ");
//		sql2.append("	 WHERE SHP.FK_PESSOA = :idPerfil "); 
		

		try {
			
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idPerfil", idPerfil);
			query.setParameter("descricaoPerfil", descricaoPerfil);
			query.executeUpdate();
			
//			Query query2 = em.createNativeQuery(sql2.toString());
//			query2.setParameter("idPerfil", idPerfil);
//			query2.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("PessoaDao || atualizaDescricaoPerfil", StringUtil.convertObjectToJson(idPerfil + " ---- " + descricaoPerfil), e);
		}

	}

	public List<PessoaFisica> buscaPessoaFisicaPorId(Long id) {

		List<PessoaFisica> listPessoaFisica = new ArrayList<PessoaFisica>();
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT pf FROM PessoaFisica pf ");
		sql.append("WHERE  pf.id = :id ");

		try {
			TypedQuery<PessoaFisica> query = em.createQuery(sql.toString(), PessoaFisica.class);
			query.setParameter("id", id);

			listPessoaFisica = query.getResultList();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("PessoaDao || Busca Pessoa Física por ID ", StringUtil.convertObjectToJson(id), e);
		}

		return listPessoaFisica;
	}

	public boolean atualizaIdInstituicao(PessoaDto dto) {
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE CAD_PESSOAS P ");
		sql.append(" 	SET P.ID_INSTITUICAO = :id ");
		sql.append(" 	WHERE P.CODIGO = :idPessoa ");
		
		try{
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("id", dto.getIdInstituicao());
			query.setParameter("idPessoa", dto.getId());
			query.executeUpdate();
						
			
		} catch(Throwable e) {
			httpGoApi.geraLog("PessoaDao || atualizaIdInstituicao", StringUtil.convertObjectToJson(dto), e);
		}
		return true;
	}
	
	public List<PessoaFisica> buscaListPessoaFisicaByNomePaginado(PesquisaGenericDto dto) {

		List<PessoaFisica> listPessoaFisica = new ArrayList<PessoaFisica>();
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT P FROM PessoaFisica P  ");
		sql.append("	WHERE P.nomePessoaFisica LIKE :nome ");
		sql.append("	AND length(P.cpf) = 11 ");
		sql.append(" ORDER BY P.nomePessoaFisica ");
		
		try {
			TypedQuery<PessoaFisica> query = em.createQuery(sql.toString(), PessoaFisica.class);
			query.setParameter("nome", dto.getNomePessoa().toUpperCase() + "%");
			
			Page page = new Page(dto.getPage(), dto.getRows());
			page.paginate(query);

			listPessoaFisica = query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("PessoaDao || buscaListPessoaFisicaByNomePaginado", StringUtil.convertObjectToJson(dto), e);
		}

	
		return listPessoaFisica;

	}
	
	public List<PessoaJuridica> buscaListPessoaJuridicaByNomePaginado(PesquisaGenericDto dto) {

		List<PessoaJuridica> listPessoaJuridica = new ArrayList<PessoaJuridica>();
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT P.pessoaJuridica FROM RazaoSocial P  ");
		sql.append("	WHERE P.descricao LIKE :descricao  ");
		sql.append("	  AND length(P.pessoaJuridica.cnpj) = 14  ");
		sql.append(" ORDER BY P.descricao ");

		try {
			TypedQuery<PessoaJuridica> query = em.createQuery(sql.toString(), PessoaJuridica.class);
			query.setParameter("descricao", dto.getRazaoSocial().toUpperCase() + "%");
			
			Page page = new Page(dto.getPage(), dto.getRows());
			page.paginate(query);

			listPessoaJuridica = query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("PessoaDao || buscaListPessoaJuridicaByNomePaginado ", StringUtil.convertObjectToJson(dto), e);
		}
		
		return listPessoaJuridica;

	}
	
	public List<PessoaJuridica> buscaListPessoaJuridicaIsentaCnpjByNome(PesquisaGenericDto dto) {

		List<PessoaJuridica> listPessoaJuridica = new ArrayList<PessoaJuridica>();
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT P.pessoaJuridica FROM RazaoSocial P  ");
		sql.append("	WHERE P.descricao LIKE :descricao ");
		sql.append("	AND LENGTH(P.pessoaJuridica.cnpj) > 14 ");
		sql.append(" ORDER BY P.descricao ");

		try {
			TypedQuery<PessoaJuridica> query = em.createQuery(sql.toString(), PessoaJuridica.class);
			query.setParameter("descricao", dto.getRazaoSocial().toUpperCase() + "%");
			
			Page page = new Page(dto.getPage(), dto.getRows());
			page.paginate(query);

			listPessoaJuridica = query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("PessoaDao || buscaListPessoaJuridicaIsentaCnpjByNome", StringUtil.convertObjectToJson(dto), e);
		}
		
		return listPessoaJuridica;

	}
	
	public List<PessoaFisica> buscaListPessoaFisicaIsentaCpfByNome(PesquisaGenericDto dto) {

		List<PessoaFisica> listPessoaFisica = new ArrayList<PessoaFisica>();
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT P FROM PessoaFisica P  ");
		sql.append("	WHERE P.nomePessoaFisica LIKE :nomePessoaFisica ");
		sql.append("	AND LENGTH(P.cpf) > 14 ");
		sql.append(" ORDER BY P.nomePessoaFisica ");

		try {
			TypedQuery<PessoaFisica> query = em.createQuery(sql.toString(), PessoaFisica.class);
			query.setParameter("nomePessoaFisica", dto.getNomePessoa().toUpperCase() + "%");
			
			Page page = new Page(dto.getPage(), dto.getRows());
			page.paginate(query);

			listPessoaFisica = query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("PessoaDao || buscaListPessoaFisicaIsentaCpfByNome", StringUtil.convertObjectToJson(dto), e);
		}
		
		return listPessoaFisica;

	}
	
	public PessoaFisica buscaInteressadoWSPessoaFisicaByCPF(String numeroCPF) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT P FROM PessoaFisica P  ");
		sql.append("	WHERE P.cpf = :cpf ");
		try {
			TypedQuery<PessoaFisica> query = em.createQuery(sql.toString(), PessoaFisica.class);
			query.setParameter("cpf", numeroCPF);
			query.setMaxResults(1);
			return query.getSingleResult();
		} catch (Throwable e) {
			httpGoApi.geraLog("PessoaDao || Busca Interessado WS Pessoa Física por CPF ", StringUtil.convertObjectToJson(numeroCPF), e);
			return null;
		}
	}
	
	public PessoaJuridica buscaInteressadoWSPessoaJuridicaByCNPJ(String numeroCNPJ) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT P FROM PessoaJuridica P  ");
		sql.append("	WHERE P.cnpj = :cnpj ");
		try {
			Query query = em.createQuery(sql.toString(), PessoaJuridica.class);
			query.setParameter("cnpj", numeroCNPJ);
			query.setMaxResults(1);
			return (PessoaJuridica) query.getSingleResult();
		} catch (Throwable e) {
			httpGoApi.geraLog("PessoaDao || Busca Interessado WS Pessoa Jurídica por CNPJ ", StringUtil.convertObjectToJson(numeroCNPJ), e);
			return null;
		}
	}

	public SituacaoRegistro getSituacaoRegistro(Long idPessoa) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT P FROM Pessoa P  ");
		sql.append("  WHERE P.id = :idPessoa ");
		
		try {
			Query query = em.createQuery(sql.toString(), Pessoa.class);
			query.setParameter("idPessoa", idPessoa);

			Pessoa pessoa = (Pessoa) query.getSingleResult();
			return pessoa != null ? pessoa.getSituacao() : null;
			
		} catch (Throwable e) {
			httpGoApi.geraLog("PessoaDao || getSituacaoRegistro", StringUtil.convertObjectToJson(idPessoa), e);
		}
		
		return null;
	}
	
	public boolean possuiSituacaoRegistroAtiva(Long idPessoa) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(P.CODIGO) FROM CAD_PESSOAS P                                     ");
		sql.append("   JOIN CAD_SITUACAO_REGISTRO S ON (P.FK_CODIGO_SITUACAO_REGISTRO = S.CODIGO)  ");
		sql.append("  WHERE P.CODIGO = :idPessoa                                                   ");
		sql.append("    AND S.REGISTRO_ATIVO = 1                                                   ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idPessoa", idPessoa);

			BigDecimal valor = (BigDecimal) query.getSingleResult(); 
			
			return valor.compareTo(new BigDecimal(0)) > 0;
			
		} catch (Throwable e) {
			httpGoApi.geraLog("PessoaDao || possuiSituacaoRegistroAtiva", StringUtil.convertObjectToJson(idPessoa), e);
		}
		
		return false;
	}

	public boolean verificaRegistroEnquadradoArtigo64(Long registro, String tipoRegistro) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" Select count(*) From Cad_Pessoas P, Cad_Situacao_Registro S ");
		if (tipoRegistro.equals("EMPRESA")) {
			sql.append(", Cad_Empresas E, Cad_Tipos_Empresas T ");
		}
		sql.append(" Where P.Codigo = :registro ");
		sql.append(" And S.Codigo = P.Fk_Codigo_Situacao_Registro ");
		sql.append(" And S.Registro_Ativo = 1 ");
		sql.append(" And Not Exists (Select Par.Codigo From Fin_Parcelamento Par, Fin_Parcelamento_Divida Pdv, Fin_Divida Div ");
		sql.append(" Where Par.Fk_Codigo_Pessoa = P.Codigo ");
		sql.append(" And par.fk_codigo_natureza = 6 ");
		sql.append(" And Par.Fk_Codigo_Status_Divida Not In (4, 8) ");
		sql.append(" And Pdv.Fk_Codigo_Parcelamento = Par.Codigo ");
		sql.append(" And Div.Codigo = Pdv.Fk_Codigo_Divida ");
		sql.append(" And Div.Fk_Codigo_Status_Divida < 4 ");
        sql.append(" And Div.Data_Vencimento >= (Sysdate - 30)) ");
        sql.append(" And Not Exists (Select Div.Codigo From Fin_Divida Div ");
        sql.append(" Where Div.Fk_Codigo_Pessoa = P.Codigo ");
        sql.append(" And Div.Fk_Codigo_Natureza = 6 ");
        sql.append(" And Div.Fk_Codigo_Status_Divida In (3, 4, 10, 22, 24) ");
        sql.append(" And Div.Identificador_Divida >= To_Char((To_Number(To_Char(Sysdate, 'YYYY')) - 2))) ");
        sql.append(" And not Exists (Select H.Codigo From Cad_Historicos H Where H.Fk_Codigo_Pessoas = P.Codigo ");
        sql.append(" And H.Fk_Codigo_Eventos In (1, 5, 8) And H.Datafinal Is Null) ");
        sql.append(" And Not Exists (Select Ht.Codigo From Cad_Historicos Ht " );
        sql.append(" Where Ht.Fk_Codigo_Pessoas = P.Codigo ");
        sql.append(" And Ht.Fk_Codigo_Eventos In (150, 171, 315, 316, 317, 318) ");
        sql.append(" And Ht.Datafinal Is Null ");
        sql.append(" And To_Char(Ht.Datainicio, 'YYYY') > To_Char((To_Number(To_Char(Sysdate, 'YYYY')) - 3))) ");
        sql.append(" And Not Exists (Select Prt.Id From Prt_Protocolos Prt ");
        sql.append(" Where Prt.Fk_Id_Pessoas = P.Codigo ");
        sql.append(" And Prt.Fk_Id_Assuntos In (1015, 5015, 1232) ");
        sql.append(" And Not Exists (Select His.Codigo From Cad_Historicos His ");
        sql.append(" Where His.Fk_Codigo_Pessoas = P.Codigo ");
        sql.append(" And His.Fk_Codigo_Eventos < 20 ");
        sql.append(" And His.Datainicio >= (Prt.Dataemissao - 5)) ");
        sql.append(" And Not Exists (Select Hst.Codigo From Cad_Historicos Hst ");
        sql.append(" Where Hst.Fk_Codigo_Pessoas = P.Codigo ");
        sql.append(" And Hst.Fk_Codigo_Eventos In (315, 316, 317, 318) ");
        sql.append(" And Hst.Datainicio >= Prt.Dataemissao)) ");
	    if (tipoRegistro.equals("EMPRESA")) {
	    	sql.append(" And E.Codigo = P.Codigo ");
			sql.append(" And E.FK_CODIGO_TIPOS_CAT_REGI NOT IN (3, 4) ");
			sql.append(" And To_Char(E.Dataexpregistro, 'YYYY') < To_Char((To_Number(To_Char(Sysdate, 'YYYY')) - 2)) ");
			sql.append(" And (E.Fk_Codigo_Tipos_Empresas <> 22 Or (E.Fk_Codigo_Tipos_Empresas = 22 AND (TO_CHAR(Sysdate, 'YYYY') > '2014'))) ");
            sql.append(" And T.Codigo = E.Fk_Codigo_Tipos_Empresas ");
            sql.append(" And T.Paga_Anuidade = 0");
	    } else {
	    	sql.append(" And Exists (Select Cp.Codigo From Cad_Profissionais Cp Where Cp.Codigo = P.Codigo ");
	    	sql.append(" And ((To_Char(Cp.Dataregistro, 'YYYY') < To_Char((To_Number(To_Char(Sysdate, 'YYYY')) - 2)) And Cp.Fk_Codigo_Tipos_Registros = 0) ");
            sql.append(" Or (To_Char(Cp.Datavisto, 'YYYY') < To_Char((To_Number(To_Char(Sysdate, 'YYYY')) - 2)) AND CP.FK_CODIGO_TIPOS_REGISTROS = 1))) ");
            sql.append(" And Exists (Select Pxe.Fk_Codigo_Profissionais From Cad_Profxespec Pxe ");
            sql.append(" Where Pxe.Fk_Codigo_Profissionais = P.Codigo ");
            sql.append(" And Pxe.Fk_Codigo_Tipos_Titulos In (1, 2, 4, 5, 6, 8, 9, 10, 11, 12) ");
            sql.append(" And Pxe.Datacancelamento Is Null) ");
	    }
		Query query = em.createNativeQuery(sql.toString());
		query.setParameter("registro", registro);
		
		BigDecimal resp = new BigDecimal(0); 
		resp = (BigDecimal) query.getSingleResult();
		
		return resp.compareTo(BigDecimal.ZERO) > 0;

	}
}
