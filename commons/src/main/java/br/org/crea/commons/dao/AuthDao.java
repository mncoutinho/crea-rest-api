package br.org.crea.commons.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.cadastro.pessoa.InteressadoDao;
import br.org.crea.commons.models.atendimento.AgendamentoMobile;
import br.org.crea.commons.models.commons.dtos.AuthenticationDto;
import br.org.crea.commons.models.commons.dtos.AuthenticationSemLoginDto;
import br.org.crea.commons.models.corporativo.pessoa.Funcionario;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;
import br.org.crea.commons.models.corporativo.pessoa.PessoaJuridica;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.HashUtil;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class AuthDao extends GenericDao<Pessoa, Serializable> {

	public AuthDao() {
		super(Pessoa.class);
	}
	
	@Inject
	InteressadoDao interessadoDao;
	
	@Inject
	HttpClientGoApi httpGoApi;
	
	public Pessoa autentica(Long idPessoa, String senha) {

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT P FROM Pessoa P  ");
		sql.append("  WHERE P.id = :id       ");
		sql.append("    AND P.senha = :senha ");
		
		try {
			TypedQuery<Pessoa> query = em.createQuery(sql.toString(), Pessoa.class);
			query.setParameter("id", idPessoa);
			query.setParameter("senha", HashUtil.criptografa(senha));
			
			return query.getSingleResult();
			
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("AuthDao || autentica", StringUtil.convertObjectToJson(idPessoa + " -- " + senha), e);
		}
		
		return null;
	}
	
	
	public Funcionario autenticaPorLogin(AuthenticationDto auth) {

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT F FROM Funcionario F ");
		sql.append("    WHERE F.pessoaFisica.senha = :senha ");
		sql.append("    AND F.matricula = :matricula ");
		sql.append("    AND F.cadastroAtivo = 1 ");
		
		try {
			TypedQuery<Funcionario> query = em.createQuery(sql.toString(), Funcionario.class);
			query.setParameter("matricula", Long.parseLong(auth.getLogin().trim()));
			query.setParameter("senha", HashUtil.criptografa(auth.getSenha()));

			return query.getSingleResult();
			
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("AuthDao || autenticaPorLogin", StringUtil.convertObjectToJson(auth), e);
		}
		return null;
		
	}
	
	public AgendamentoMobile getNomeEmailUsuarioSemRegistro(String cpfOuCnpj){
		
		List<AgendamentoMobile> listAgendamento = new ArrayList<AgendamentoMobile>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT F FROM AgendamentoMobile F ");
		sql.append("    WHERE F.cpfOuCnpj = :cpfOuCnpj ");
		
		TypedQuery<AgendamentoMobile> query = em.createQuery(sql.toString(), AgendamentoMobile.class);
		query.setParameter("cpfOuCnpj", cpfOuCnpj);
		
		listAgendamento = query.getResultList();
		
		if(!listAgendamento.isEmpty()){
			return listAgendamento.get(0);
		}else{
			return null;
		}
		
	}
	
	
	public PessoaJuridica autenticaCnpj(AuthenticationSemLoginDto auth) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT P FROM PessoaJuridica P ");
		sql.append("    WHERE P.cnpj = :cnpj ");
		sql.append("    AND P.senha = :senha ");
		
		try {
			
			TypedQuery<PessoaJuridica> query = em.createQuery(sql.toString(), PessoaJuridica.class);
			query.setParameter("cnpj", auth.getCpfOuCnpj());
			query.setParameter("senha", HashUtil.criptografa(auth.getSenha().trim()));
			
			return  query.getSingleResult();
			
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("AuthDao || autenticaCnpj", StringUtil.convertObjectToJson(auth), e);
		}
		
		return null;

	}
	
	
	public boolean profissionalPossuiEventoTransferidoCAUComDataFinalVazia (String codigoPessoa) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" select count(CODIGO)   							    ");
		sql.append(" from CAD_HISTORICOS where                			    ");
		sql.append(" FK_CODIGO_PESSOAS = :codigoPessoa                      ");
		sql.append(" and FK_CODIGO_EVENTOS = '7'                            ");
		sql.append(" and (DATAFINAL IS NULL or trunc(DATAFINAL) >= sysdate) ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("codigoPessoa", codigoPessoa);

			BigDecimal qt = (BigDecimal) query.getSingleResult();
		
			return qt.compareTo(new BigDecimal(0)) > 0;
		
		} catch (NoResultException e) {
			return true;
		} catch (Throwable e) {
			httpGoApi.geraLog("AuthDao || profissionalPossuiEventoTransferidoCAUComDataFinalVazia", StringUtil.convertObjectToJson(codigoPessoa), e);
		}
		return false;
	}
	
	public boolean profissionalPossuiEventoTransferidoParaCFTComDataFinalVazia (String codigoPessoa) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" select count(CODIGO)   							    ");
		sql.append(" from CAD_HISTORICOS where                			    ");
		sql.append(" FK_CODIGO_PESSOAS = :codigoPessoa                      ");
		sql.append(" and FK_CODIGO_EVENTOS = '4'                            ");
		sql.append(" and (DATAFINAL IS NULL or trunc(DATAFINAL) >= sysdate) ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("codigoPessoa", codigoPessoa);

			BigDecimal qt = (BigDecimal) query.getSingleResult();
		
			return qt.compareTo(new BigDecimal(0)) > 0;
		
		} catch (NoResultException e) {
			return true;
		} catch (Throwable e) {
			httpGoApi.geraLog("AuthDao || profissionalPossuiEventoTransferidoParaCFTComDataFinalVazia", StringUtil.convertObjectToJson(codigoPessoa), e);
		}
		return false;
	}

	
	public boolean profissionalPossuiEventoFalecidoComDataFinalVazia (String codigoPessoa) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" select count(CODIGO)   							    ");
		sql.append(" from CAD_HISTORICOS where                			    ");
		sql.append(" FK_CODIGO_PESSOAS = :codigoPessoa                      ");
		sql.append(" and FK_CODIGO_EVENTOS = '11'                           ");
		sql.append(" and (DATAFINAL IS NULL or trunc(DATAFINAL) >= sysdate) ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("codigoPessoa", codigoPessoa);

			BigDecimal qt = (BigDecimal) query.getSingleResult();
		
			return qt.compareTo(new BigDecimal(0)) > 0;
		
		} catch (NoResultException e) {
			return true;
		} catch (Throwable e) {
			httpGoApi.geraLog("AuthDao || profissionalPossuiEventoFalecidoComDataFinalVazia", StringUtil.convertObjectToJson(codigoPessoa), e);
		}
		return false;
	}
	
	public boolean profissionalPossuiEventoExcluidoOuSuspenso (String codigoPessoa) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" select count(CODIGO)   													         ");
		sql.append(" from CAD_HISTORICOS where                										     ");
		sql.append(" FK_CODIGO_PESSOAS = :codigoPessoa                  								 ");
		sql.append(" and FK_CODIGO_EVENTOS < '20' and (DATAFINAL IS NULL or trunc(DATAFINAL) >= sysdate) ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("codigoPessoa", codigoPessoa);

			BigDecimal qt = (BigDecimal) query.getSingleResult();
		
			return qt.compareTo(new BigDecimal(0)) > 0;
		
		} catch (NoResultException e) {
			return true;
		} catch (Throwable e) {
			httpGoApi.geraLog("AuthDao || profissionalPossuiEventoExcluidoOuSuspenso", StringUtil.convertObjectToJson(codigoPessoa), e);
		}
		return false;
	}
	
	
	/**
	 * Atenção se o usuário logado for funcionário ou fiscal não utilizar esse método.
	 * @param codigoPessoa
	 * @return
	 */
	public boolean profissionalPossuiSituacaoRegistroNovoInscritoOuDataRegistroVazia (String codigoPessoa) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" select count(CPE.CODIGO)  				                                ");
		sql.append(" from CAD_PESSOAS CPE                	                                ");
		sql.append(" inner join CAD_PROFISSIONAIS CPRO on CPE.CODIGO = CPRO.CODIGO          ");
		sql.append(" where CPE.CODIGO = :codigoPessoa                                       ");
		sql.append(" and (CPE.FK_CODIGO_SITUACAO_REGISTRO = 5 or CPRO.DATAREGISTRO is null) ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("codigoPessoa", codigoPessoa);

			BigDecimal qt = (BigDecimal) query.getSingleResult();
		
			return qt.compareTo(new BigDecimal(0)) > 0;
		
		} catch (NoResultException e) {
			return true;
		} catch (Throwable e) {
			httpGoApi.geraLog("AuthDao || profissionalPossuiSituacaoRegistroNovoInscritoOuDataRegistroVazia", StringUtil.convertObjectToJson(codigoPessoa), e);
		}
		return false;
	}
	
	public boolean empresaPossuiEventoTransferidoCAU (String codigoPessoa) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" select count(CODIGO)   			                       ");
		sql.append(" from CAD_HISTORICOS where                                 ");
		sql.append(" FK_CODIGO_PESSOAS = :codigoPessoa                         ");
		sql.append(" and FK_CODIGO_EVENTOS = '7'                               ");
		sql.append(" and (DATAFINAL IS NULL or trunc(DATAFINAL) >= sysdate)    ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("codigoPessoa", codigoPessoa);

			BigDecimal qt = (BigDecimal) query.getSingleResult();
		
			return qt.compareTo(new BigDecimal(0)) > 0;
		
		} catch (NoResultException e) {
			return true;
		} catch (Throwable e) {
			httpGoApi.geraLog("AuthDao || empresaPossuiEventoTransferidoCAU", StringUtil.convertObjectToJson(codigoPessoa), e);
		}
		return false;
	}
	
	public boolean empresaPossuiEventoExcluidoOuSuspensoOuInterropidoOuSemValidade (String codigoPessoa) {
		StringBuilder sql = new StringBuilder();
		sql.append(" select count(CODIGO)   													         ");
		sql.append(" from CAD_HISTORICOS where                										     ");
		sql.append(" FK_CODIGO_PESSOAS = :codigoPessoa                  								 ");
		sql.append(" and FK_CODIGO_EVENTOS < '20' and (DATAFINAL IS NULL or trunc(DATAFINAL) >= sysdate) ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("codigoPessoa", codigoPessoa);

			BigDecimal qt = (BigDecimal) query.getSingleResult();
		
			return qt.compareTo(new BigDecimal(0)) > 0;
		
		} catch (NoResultException e) {
			return true;
		} catch (Throwable e) {
			httpGoApi.geraLog("AuthDao || empresaPossuiSituacaoRegistroExcluidoOuSuspensoOuInterropidoOuSemValidade", StringUtil.convertObjectToJson(codigoPessoa), e);
		}
		return false;
	}
	
	public boolean empresaPossuiDataRegistroVaziaETipoCategoriaRegistroDiferenteDeVisto (String codigoPessoa) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" select count(CPE.CODIGO) from CAD_PESSOAS CPE                      ");
		sql.append(" inner join CAD_EMPRESAS CAE on CPE.CODIGO = CAE.CODIGO             ");
		sql.append(" where CPE.CODIGO = :codigoPessoa and                               ");
		sql.append(" CAE.DATAEXPREGISTRO is null and CAE.FK_CODIGO_TIPOS_CAT_REGI <> 3  ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("codigoPessoa", codigoPessoa);

			BigDecimal qt = (BigDecimal) query.getSingleResult();
		
			return qt.compareTo(new BigDecimal(0)) > 0;
		
		} catch (NoResultException e) {
			return true;
		} catch (Throwable e) {
			httpGoApi.geraLog("AuthDao || empresaPossuiDataRegistroVaziaETipoCategoriaRegistroDiferenteDeVisto", StringUtil.convertObjectToJson(codigoPessoa), e);
		}
		return false;
	}
	
	
}
