package br.org.crea.commons.dao.cadastro.pessoa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.cadastro.InstituicaoEnsino;
import br.org.crea.commons.models.commons.dtos.PesquisaGenericDto;
import br.org.crea.commons.models.corporativo.RazaoSocial;
import br.org.crea.commons.models.corporativo.pessoa.Empresa;
import br.org.crea.commons.models.corporativo.pessoa.Formando;
import br.org.crea.commons.models.corporativo.pessoa.Funcionario;
import br.org.crea.commons.models.corporativo.pessoa.IInteressado;
import br.org.crea.commons.models.corporativo.pessoa.LeigoPF;
import br.org.crea.commons.models.corporativo.pessoa.LeigoPJ;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;
import br.org.crea.commons.models.corporativo.pessoa.Profissional;
import br.org.crea.commons.models.financeiro.FinDivida;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class InteressadoDao extends GenericDao<Pessoa, Serializable> {

	@Inject HttpClientGoApi httpGoApi;
	
	public InteressadoDao() {
		super(Pessoa.class);
	}

	public IInteressado buscaProfissionalBy(Long idPessoa) {

		IInteressado interessado = null;

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM CAD_PROFISSIONAIS P ");
		sql.append("	WHERE P.FK_ID_PESSOAS_FISICAS = :idPessoa ");

		try {

			Query query = em.createNativeQuery(sql.toString(), Profissional.class);
			query.setParameter("idPessoa", idPessoa);

			interessado = (IInteressado) query.getSingleResult();

		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("InteressadoDao || Busca Profissional By", StringUtil.convertObjectToJson(idPessoa), e);
		}

		return interessado;

	}

	public IInteressado buscaEmpresaBy(Long idPessoa) {

		IInteressado interessado = null;

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM CAD_EMPRESAS P ");
		sql.append("	WHERE P.FK_ID_PESSOAS_JURIDICAS = :idPessoa ");

		try {

			Query query = em.createNativeQuery(sql.toString(), Empresa.class);
			query.setParameter("idPessoa", idPessoa);

			interessado = (IInteressado) query.getSingleResult();

			if (interessado != null) {
				interessado.setNomeRazaoSocial(buscaDescricaoRazaoSocial(idPessoa));
			}

		} catch (Throwable e) {
			httpGoApi.geraLog("InteressadoDao || Busca Empresa By", StringUtil.convertObjectToJson(idPessoa), e);
		}

		return interessado;

	}

	public IInteressado buscaLeigoPFBy(Long idPessoa) {

		IInteressado interessado = null;

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM CAD_LEIGOS_PF P ");
		sql.append("	WHERE P.FK_ID_PESSOAS_FISICAS = :idPessoa ");

		try {

			Query query = em.createNativeQuery(sql.toString(), LeigoPF.class);
			query.setParameter("idPessoa", idPessoa);

			interessado = (IInteressado) query.getSingleResult();

		}catch(NoResultException e){
			return null;
		}catch (Throwable e) {
			httpGoApi.geraLog("InteressadoDao || Busca LeigoPF By", StringUtil.convertObjectToJson(idPessoa), e);
		}

		return interessado;

	}

	public IInteressado buscaLeigoPJBy(Long idPessoa) {

		IInteressado interessado = null;

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM CAD_LEIGOS_PJ P ");
		sql.append("	WHERE P.FK_ID_PESSOAS_JURIDICAS = :idPessoa ");

		try {

			Query query = em.createNativeQuery(sql.toString(), LeigoPJ.class);
			query.setParameter("idPessoa", idPessoa);

			interessado = (IInteressado) query.getSingleResult();

			if (interessado != null) {
				interessado.setNomeRazaoSocial(buscaDescricaoRazaoSocial(idPessoa));
			}

		} catch (Throwable e) {
			httpGoApi.geraLog("InteressadoDao || Busca LeigoPJ By", StringUtil.convertObjectToJson(idPessoa), e);
		}

		return interessado;

	}

	public IInteressado buscaFuncionarioBy(Long idPessoa) {

		IInteressado interessado = null;

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM CAD_FUNCIONARIOS P ");
		sql.append("	WHERE P.FK_ID_PESSOAS_FISICAS = :idPessoa ");
		sql.append("	AND P.CADASTROATIVO = 1 ");

		try {

			Query query = em.createNativeQuery(sql.toString(), Funcionario.class);
			query.setParameter("idPessoa", idPessoa);

			interessado = (IInteressado) query.getSingleResult();

		} catch (Throwable e) {
			httpGoApi.geraLog("InteressadoDao || Busca Funcionario By", StringUtil.convertObjectToJson(idPessoa), e);
		}

		return interessado;

	}
	

	public IInteressado buscaFormandoBy(Long idPessoa) {

		IInteressado interessado = null;

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM CAD_FORMANDOS P ");
		sql.append("	WHERE P.FK_ID_PESSOAS_FISICAS = :idPessoa ");

		try {

			Query query = em.createNativeQuery(sql.toString(), Formando.class);
			query.setParameter("idPessoa", idPessoa);

			if(!query.getResultList().isEmpty()){
				interessado = (IInteressado) query.getResultList().get(0);
			}else {
				interessado = buscaLeigoPFBy(idPessoa);
			}
			
		} catch (Throwable e) {
			httpGoApi.geraLog("InteressadoDao || Busca Formando By", StringUtil.convertObjectToJson(idPessoa), e);
		}

		return interessado;

	}

	public IInteressado buscaEntidadeClasseBy(Long idPessoa) {

		IInteressado interessado = null;

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM CAD_ENTIDADES_CLASSE P ");
		sql.append("	WHERE P.FK_ID_PESSOAS_JURIDICAS = :idPessoa ");

		try {

			Query query = em.createNativeQuery(sql.toString(), LeigoPJ.class);
			query.setParameter("idPessoa", idPessoa);

			interessado = (IInteressado) query.getSingleResult();

		} catch (Throwable e) {
			httpGoApi.geraLog("InteressadoDao || Busca Entidade Classe By", StringUtil.convertObjectToJson(idPessoa), e);
		}

		return interessado;

	}

	public IInteressado buscaInstituicaoEnsinoBy(Long idPessoa) {

		IInteressado interessado = null;

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM CAD_INSTITUICOES_ENSINO P ");
		sql.append("	WHERE P.FK_ID_PESSOAS_JURIDICAS = :idPessoa ");

		try {

			Query query = em.createNativeQuery(sql.toString(), InstituicaoEnsino.class);
			query.setParameter("idPessoa", idPessoa);

			interessado = (IInteressado) query.getSingleResult();

			if (interessado != null) {
				interessado.setNomeRazaoSocial(buscaDescricaoRazaoSocial(idPessoa));
			}

		} catch (Throwable e) {
			httpGoApi.geraLog("InteressadoDao || Busca Instituicao Ensino By", StringUtil.convertObjectToJson(idPessoa), e);
		}

		return interessado;

	}
	
	public String getNomeContratanteBy(Long idPessoa){
		
		IInteressado interessado = buscaInteressadoBy(idPessoa);
		return interessado.getNome() != null ? interessado.getNome() : interessado.getNomeRazaoSocial();

	}

	public IInteressado buscaInteressadoBy(Long idPessoa) {

		IInteressado interessado = null;

		Pessoa pessoa = new Pessoa();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT P FROM Pessoa P ");
		sql.append("	WHERE P.id = :idPessoa ");

		try {

			TypedQuery<Pessoa> query = em.createQuery(sql.toString(), Pessoa.class);
			query.setParameter("idPessoa", idPessoa);

			pessoa = query.getSingleResult();
			
			if(pessoa != null){
				
				switch (pessoa.getTipoPessoa()) {
				case PROFISSIONAL:
					interessado = buscaProfissionalBy(idPessoa);
					break;
				case EMPRESA:
					interessado = buscaEmpresaBy(idPessoa);
					break;
				case LEIGOPF:
					interessado = buscaLeigoPFBy(idPessoa);
					break;
				case LEIGOPJ:
					interessado = buscaLeigoPJBy(idPessoa);
					break;
				case FUNCIONARIO:
					interessado = buscaFuncionarioBy(idPessoa);
					break;
				case FORMANDO:
					interessado = buscaFormandoBy(idPessoa);
					break;
				case ENTIDADE:
					interessado = buscaEntidadeClasseBy(idPessoa);
					break;
				case ESCOLA:
					interessado = buscaInstituicaoEnsinoBy(idPessoa);
					break;
				default:
					break;
				}
				
			}else{
				return null;
			}

		}catch(NoResultException e){
			return null;
		}catch (Throwable e) {
			httpGoApi.geraLog("InteressadoDao || Busca Interessado By idPessoa", StringUtil.convertObjectToJson(idPessoa), e);
		}

		return interessado;
	}
	
	public IInteressado buscaInteressadoBy(Pessoa pessoa) {
		
		IInteressado interessado = null;
		
		try {
			
			switch (pessoa.getTipoPessoa()) {
			case PROFISSIONAL:
				interessado = buscaProfissionalBy(pessoa.getId());
				break;
			case EMPRESA:
				interessado = buscaEmpresaBy(pessoa.getId());
				break;
			case LEIGOPF:
				interessado = buscaLeigoPFBy(pessoa.getId());
				break;
			case LEIGOPJ:
				interessado = buscaLeigoPJBy(pessoa.getId());
				break;
			case FUNCIONARIO:
				interessado = buscaFuncionarioBy(pessoa.getId());
				break;
			case FORMANDO:
				interessado = buscaFormandoBy(pessoa.getId());
				break;
			case ENTIDADE:
				interessado = buscaEntidadeClasseBy(pessoa.getId());
				break;
			case ESCOLA:
				interessado = buscaInstituicaoEnsinoBy(pessoa.getId());
				break;
			default:
				break;
			}
			
		} catch (Throwable e) {
			httpGoApi.geraLog("InteressadoDao || Busca Interessado By Pessoa", StringUtil.convertObjectToJson(pessoa), e);
		}
		
		return interessado;
	}

	public String buscaDescricaoRazaoSocial(Long idPessoa) {

		String razaoSocial = "";

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT RS.Descricao FROM cad_razoes_sociais RS ");
		sql.append("	WHERE RS.fk_codigo_pjs = :idPessoa ");
		sql.append("	AND RS.ativo = 1 ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idPessoa", idPessoa);

			razaoSocial = (String) query.getSingleResult();

		}catch (NoResultException e) {
			return "";
		} catch (Throwable e) {
			httpGoApi.geraLog("InteressadoDao || buscaDescricaoRazaoSocial", StringUtil.convertObjectToJson(idPessoa), e);
		}

		return razaoSocial;

	}
	
	@SuppressWarnings("unused")
	public boolean existePessoa(Long idPessoa) {

		Pessoa pessoa = new Pessoa();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT P FROM Pessoa P ");
		sql.append("	WHERE P.id = :idPessoa ");

		try {

			TypedQuery<Pessoa> query = em.createQuery(sql.toString(), Pessoa.class);
			query.setParameter("idPessoa", idPessoa);

			pessoa = query.getSingleResult();
		
		}catch (NoResultException e) {
			return false;
		} catch (Throwable e) {
			httpGoApi.geraLog("InteressadoDao || Existe Pessoa", StringUtil.convertObjectToJson(idPessoa), e);
		}

		return true;

	}

	public List<RazaoSocial> buscaListaInteressadoPorNomePJ(PesquisaGenericDto dto) {

		
		List<RazaoSocial> listRazaoSocial = new ArrayList<RazaoSocial>();
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT R FROM RazaoSocial R ");
		sql.append("	 WHERE R.descricao LIKE :razaoSocial ");
		sql.append("	 AND R.ativo = 1 ");

		try {
			TypedQuery<RazaoSocial> query = em.createQuery(sql.toString(), RazaoSocial.class);
			query.setParameter("razaoSocial", "%" + dto.getRazaoSocial() + "%");
			
			listRazaoSocial = query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("InteressadoDao || Busca Lista Interessado Por Nome PJ", StringUtil.convertObjectToJson(dto), e);
		}
		
		return listRazaoSocial;
	}
	
	/** Método retorna false, caso profissional ou empresa não possua anuidade paga nos últimos 24 meses
	 * 
	 * @param idPessoa
	 * @return boolean
	 */
	public boolean verificaSeEmpresaOuProfissionalEstaAtivo(Long idPessoa) {
		
		List<FinDivida> listaDividas = new ArrayList<FinDivida>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT F FROM FinDivida F                                                         ");
		sql.append("  WHERE F.pessoa.id = :idPessoa                                                    ");
		sql.append("    AND F.identificadorDivida >= TO_CHAR( ADD_MONTHS( TRUNC(SYSDATE), -24),'YYYY') ");
		sql.append("	AND F.status.quitado = 1                                                       ");
		sql.append("	AND F.natureza.id = 6                                                          ");
		sql.append("  ORDER BY F.dataVencimento                                                        ");
		

		try {
			TypedQuery<FinDivida> query = em.createQuery(sql.toString(), FinDivida.class);
			query.setParameter("idPessoa", idPessoa);
			
			listaDividas = query.getResultList();
			return listaDividas.size() > 0;
			
		} catch (NoResultException e) {
			return false;
		}catch (Throwable e) {
			httpGoApi.geraLog("InteressadoDao || getAtivoProfissionalEmpresa", StringUtil.convertObjectToJson(idPessoa), e);
		}
		
		return false;
	}
	
	public boolean verificaSeEmpresaOuProfissionalEstaRegular(Long idPessoa) {
		
		List<FinDivida> listaDividas = new ArrayList<FinDivida>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT F FROM FinDivida F                                                         ");
		sql.append("  WHERE F.pessoa.id = :idPessoa                                                    ");
		sql.append("    AND F.dataVencimento < sysdate 												   ");
		sql.append("	AND F.status.quitado = 0                                                       ");
		sql.append("	AND F.natureza.id in (2, 6)                                                    ");
		sql.append("  ORDER BY F.dataVencimento                                                        ");
		

		try {
			TypedQuery<FinDivida> query = em.createQuery(sql.toString(), FinDivida.class);
			query.setParameter("idPessoa", idPessoa);
			
			listaDividas = query.getResultList();
			if (listaDividas.size() > 0) {
				return false;
			}
			
		} catch (NoResultException e) {
			return true;
		}catch (Throwable e) {
			httpGoApi.geraLog("InteressadoDao || verificaSeEmpresaOuProfissionalEstaRegular", StringUtil.convertObjectToJson(idPessoa), e);
		}
		
		return true;
	}
	

	


}
