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

import br.org.crea.commons.converter.cadastro.pessoa.EnderecoConverter;
import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.commons.Endereco;
import br.org.crea.commons.models.commons.Localidade;
import br.org.crea.commons.models.commons.Logradouro;
import br.org.crea.commons.models.commons.TipoEndereco;
import br.org.crea.commons.models.commons.TipoLogradouro;
import br.org.crea.commons.models.commons.UF;
import br.org.crea.commons.models.commons.dtos.EnderecoDto;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class EnderecoDao extends GenericDao<Endereco, Serializable> {

	@Inject
	HttpClientGoApi httpGoApi;

	@Inject
	EnderecoConverter converter;

	public EnderecoDao() {
		super(Endereco.class);
	}

	public List<TipoEndereco> getTipoEndereco() {

		List<TipoEndereco> listTipoEndereco = new ArrayList<TipoEndereco>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT T FROM TipoEndereco T ");
		sql.append("	ORDER BY T.descricao ");

		try {
			TypedQuery<TipoEndereco> query = em.createQuery(sql.toString(), TipoEndereco.class);

			listTipoEndereco = query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("EnderecoDao || Get Tipo Endereco", StringUtil.convertObjectToJson("SEM PARAMETRO"), e);
		}

		return listTipoEndereco;
	}

	public List<TipoLogradouro> getTipoLogradouro() {

		List<TipoLogradouro> listTipoLogradouro = new ArrayList<TipoLogradouro>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT T FROM TipoLogradouro T ");
		sql.append("	ORDER BY T.descricao ");

		try {
			TypedQuery<TipoLogradouro> query = em.createQuery(sql.toString(), TipoLogradouro.class);

			listTipoLogradouro = query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("EnderecoDao || Get Tipo Logradouro", StringUtil.convertObjectToJson("SEM PARAMETRO"), e);
		}

		return listTipoLogradouro;
	}

	public TipoLogradouro getTipoLogradouroByDescricao(String descricao) {
		TipoLogradouro listTipoLogradouro = new TipoLogradouro();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT T FROM TipoLogradouro T ");
		sql.append("	WHERE T.descricao = :descricao ");

		try {
			TypedQuery<TipoLogradouro> query = em.createQuery(sql.toString(), TipoLogradouro.class);
			query.setParameter("descricao", descricao.toUpperCase());

			listTipoLogradouro = query.getSingleResult();
		} catch (Throwable e) {
			httpGoApi.geraLog("EnderecoDao || Get Tipo Logradouro By Descricao",
					StringUtil.convertObjectToJson(descricao), e);
		}

		return listTipoLogradouro;
	}

	public List<Localidade> getMunicipioBySigla(String uf) {

		List<Localidade> listLocalidades = new ArrayList<Localidade>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT L FROM Localidade L ");
		sql.append("	WHERE L.uf.sigla = :uf ");
		sql.append("	ORDER BY L.descricao ");

		try {
			TypedQuery<Localidade> query = em.createQuery(sql.toString(), Localidade.class);
			query.setParameter("uf", uf.toUpperCase());

			listLocalidades = query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("EnderecoDao || Get Municipio By Id Uf", StringUtil.convertObjectToJson(uf), e);
		}

		return listLocalidades;
	}

	public List<Localidade> getMunicipioByIdUf(Long idUf) {

		List<Localidade> listLocalidades = new ArrayList<Localidade>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT L FROM Localidade L ");
		sql.append("	WHERE L.uf.id = :idUf ");
		sql.append("	ORDER BY L.descricao ");

		try {
			TypedQuery<Localidade> query = em.createQuery(sql.toString(), Localidade.class);
			query.setParameter("idUf", idUf);

			listLocalidades = query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("EnderecoDao || getMunicipioByIdUf", StringUtil.convertObjectToJson(idUf), e);
		}

		return listLocalidades;
	}

	public Logradouro getLogradouroByCep(String cep) {

		Logradouro logradouro = null;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT L FROM Logradouro L ");
		sql.append("	WHERE L.cep = :cep ");

		try {
			TypedQuery<Logradouro> query = em.createQuery(sql.toString(), Logradouro.class);
			query.setParameter("cep", cep);

			logradouro = query.getSingleResult();

		} catch (Exception e) {
			httpGoApi.geraLog("EnderecoDao || Get Logradouro By Cep", StringUtil.convertObjectToJson(cep), e);
		}

		return logradouro;
	}

	public Localidade getLocalidadeByCep(String cep) {

		Localidade localidade = null;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT L FROM Localidade L ");
		sql.append("	WHERE L.cep = :cep ");

		try {
			TypedQuery<Localidade> query = em.createQuery(sql.toString(), Localidade.class);
			query.setParameter("cep", cep);

			localidade = query.getSingleResult();

		} catch (Exception e) {
			httpGoApi.geraLog("EnderecoDao || Get Endereco By Cep", StringUtil.convertObjectToJson(cep), e);
		}

		return localidade;
	}

	public Endereco getEnderecoPessoaById(Long id) {

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT E FROM Endereco E  ");
		sql.append("  WHERE E.pessoa.id = :id  ");
		sql.append("    and E.postal = 1       ");
		sql.append("    and E.valido = 1       ");
		sql.append("    and E.excluido = 0     ");

		try {
			TypedQuery<Endereco> query = em.createQuery(sql.toString(), Endereco.class);
			query.setParameter("id", id);

			return query.getSingleResult();

		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			httpGoApi.geraLog("EnderecoDao || Get Endereco By Pessoa", StringUtil.convertObjectToJson(id), e);
		}

		return null;
	}

	public String getNomePessoaAtualizacaoEnderecoPorId(Long id) {

		Object nome = null;

		StringBuilder sql = new StringBuilder();

		sql.append(" SELECT pf.nome ");
		sql.append("   FROM cad_enderecos e, ");
		sql.append("        cad_pessoas_fisicas pf ");
		sql.append("  WHERE e.codigo = :id ");
		sql.append("    AND e.fk_pessoa_atualizacao = pf.codigo ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("id", id);

			nome = query.getSingleResult();

		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			httpGoApi.geraLog("EnderecoDao || Get Pessoa Atualizacao Por Id", StringUtil.convertObjectToJson(id), e);
		}

		return nome != null ? (String) nome : null;
	}

	public Endereco getEnderecoResidencialPessoaPor(Long idPessoa) {
		Endereco endereco = new Endereco();

		StringBuilder sql = new StringBuilder();

		sql.append("SELECT E FROM Endereco E ");
		sql.append("	WHERE E.id = (SELECT MAX(X.id) FROM Endereco X ");
		sql.append("				  		 WHERE X.pessoa.id = :idPessoa ");
		sql.append("						 AND   X.postal = true ");
		sql.append("						 AND   X.valido = true ");
		sql.append("						 AND   X.excluido = false ");
		sql.append("						 AND   X.tipoEndereco.id = 1 ");
		sql.append("				 )");

		try {
			TypedQuery<Endereco> query = em.createQuery(sql.toString(), Endereco.class);
			query.setParameter("idPessoa", idPessoa);

			endereco = query.getSingleResult();

		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			httpGoApi.geraLog("EnderecoDao || getEnderecoResidencialPessoaPor",
					StringUtil.convertObjectToJson(idPessoa), e);
		}
		return endereco;
	}

	public Endereco getEnderecoValidoPessoaById(Long id) {
		Endereco endereco = new Endereco();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT E FROM Endereco E ");
		sql.append("   WHERE E.pessoa.id = :id ");
		sql.append("   and E.valido = 1 ");

		try {
			TypedQuery<Endereco> query = em.createQuery(sql.toString(), Endereco.class);
			query.setParameter("id", id);

			endereco = query.getSingleResult();

		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			httpGoApi.geraLog("EnderecoDao || Get Endereco By Pessoa", StringUtil.convertObjectToJson(id), e);
		}

		return endereco;
	}

	@SuppressWarnings("unchecked")
	public List<EnderecoDto> getLogradourosByFilter(EnderecoDto dto) {
		List<EnderecoDto> lista = new ArrayList<EnderecoDto>();

		long resto = 0;

		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT log.descricao, log.cep, loc.descricao localidade, uf.UF, tip.descricao tipo, bai.descricao bairro, log.NUM_INICIAL, log.NUM_FINAL, log.par, log.impar, uf.codigo idUf, tip.codigo idTipo, loc.codigo codLogradouro ");
		sql.append("      FROM CAD_LOGRADOUROS log ");
		sql.append("      INNER JOIN CAD_LOCALIDADES loc on loc.CODIGO = log.FK_CODIGO_LOCALIDADES ");
		sql.append("      INNER JOIN CAD_BAIRROS bai on bai.CODIGO = log.FK_CODIGO_BAIRROS ");
		sql.append("      INNER JOIN CAD_TIPOS_LOGRADOUROS tip on tip.CODIGO = log.FK_CODIGO_TIPOS_LOGRADOURO ");
		sql.append("      INNER JOIN CAD_UFS uf on uf.CODIGO = log.FK_CODIGO_UFS ");
		sql.append("      WHERE log.DESCRICAO like :logradouro ");

		if (dto.getNumero() != null) {
			sql.append("AND ((log.par = 0 and log.impar = 0) ");

			Long numero = Long.parseLong(dto.getNumero());
			resto = numero % 2;

			if (resto == 0) {
				sql.append("OR log.par = 1 ");
			} else {
				sql.append("OR log.impar = 1 ");
			}

			sql.append(") ");

			sql.append("AND to_number(log.NUM_INICIAL) <= :numero ");
			sql.append("AND to_number(log.NUM_FINAL) >= :numero ");
		}

		sql.append("AND loc.DESCRICAO like :localidade ");
		sql.append("AND uf.codigo = :uf ");

		if (dto.temTipoLogradouro()) {
			sql.append("and log.fk_codigo_tipos_logradouro = :tipo ");
		}

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("logradouro",
					"%" + StringUtil.removeAcentos(dto.getLogradouro()).toUpperCase().replace(" ", "%") + "%");
			query.setParameter("localidade",
					"%" + StringUtil.removeAcentos(dto.getLocalidade().getDescricao()).toUpperCase().replace(" ", "%")
							+ "%");
			query.setParameter("uf", dto.getUf().getId());
			if (dto.getNumero() != null) {
				query.setParameter("numero", Long.parseLong(dto.getNumero()));
			}

			if (dto.temTipoLogradouro()) {
				query.setParameter("tipo", dto.getTipoLogradouro().getId());
			}

			List<Object[]> resultado = query.getResultList();

			lista = converter.toListLogradourosECep(resultado);
		} catch (NoResultException e) {
			return lista;
		} catch (Exception e) {
			httpGoApi.geraLog("EnderecoDao || Get Logradouros by filter", StringUtil.convertObjectToJson(dto), e);
		}

		return lista;
	}

	public List<UF> getUf() {
		List<UF> listUf = new ArrayList<UF>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT U FROM UF U ");
		sql.append("    where brasil = 1");
		sql.append("	ORDER BY U.sigla ");

		try {
			TypedQuery<UF> query = em.createQuery(sql.toString(), UF.class);

			listUf = query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("EnderecoDao || Get Uf", StringUtil.convertObjectToJson("SEM PARAMETRO"), e);
		}

		return listUf;
	}

	public List<UF> getUfPaises() {
		
		List<UF> listUf = new ArrayList<UF>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT U FROM UF U ");
		sql.append("  WHERE brasil <> 1 ");
		sql.append("  ORDER BY U.sigla  ");

		try {
			TypedQuery<UF> query = em.createQuery(sql.toString(), UF.class);

			listUf = query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("EnderecoDao || Get Uf Paises", StringUtil.convertObjectToJson("SEM PARAMETRO"), e);
		}

		return listUf;
	}

	public Localidade getLocalidadeBy(Long id) {
		Localidade localidade = null;

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT L FROM Localidade L ");
		sql.append("     WHERE id = :id");

		try {
			TypedQuery<Localidade> query = em.createQuery(sql.toString(), Localidade.class);
			query.setParameter("id", id);

			localidade = query.getSingleResult();
		} catch (Throwable e) {
			httpGoApi.geraLog("EnderecoDao || Get Localidade By", StringUtil.convertObjectToJson(id), e);
		}

		return localidade;
	}

	public Localidade getLocalidadePorDescricao(String descricao) {
		Localidade localidade = null;

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT L FROM Localidade L ");
		sql.append("     WHERE descricao = :descricao");

		try {
			TypedQuery<Localidade> query = em.createQuery(sql.toString(), Localidade.class);
			query.setParameter("descricao", StringUtil.removeAcentos(descricao.toUpperCase()));

			localidade = query.getSingleResult();
		} catch (Throwable e) {
			httpGoApi.geraLog("EnderecoDao || Get Localidade por descricao", StringUtil.convertObjectToJson(descricao),
					e);
		}

		return localidade;
	}

	public Localidade getLocalidadePorDescricaoEdescricaoUF(String descricaoLocalidade, String descricaoUF) {
		Localidade localidade = null;

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT L FROM Localidade L WHERE 1 = 1 ");
		sql.append("     AND L.descricao LIKE :descricaoLocalidade ");
		sql.append("     AND L.uf.sigla LIKE :descricaoUF ");

		try {
			TypedQuery<Localidade> query = em.createQuery(sql.toString(), Localidade.class);
			query.setParameter("descricaoLocalidade",
					"%" + StringUtil.removeAcentos(descricaoLocalidade).toUpperCase().replace(" ", "%") + "%");
			query.setParameter("descricaoUF",
					"%" + StringUtil.removeAcentos(descricaoUF).toUpperCase().replace(" ", "%") + "%");
			
			query.setMaxResults(1);
			
			localidade = query.getSingleResult();
		} catch (Throwable e) {
			httpGoApi.geraLog("EnderecoDao || getLocalidadePorDescricaoEdescricaoUF",
					StringUtil.convertObjectToJson(descricaoLocalidade + " -- " + descricaoUF), e);
		}

		return localidade;
	}

	public UF getUfPorSigla(String sigla) {
		UF uf = null;

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT U FROM UF U ");
		sql.append("     WHERE U.sigla = :sigla");

		try {
			TypedQuery<UF> query = em.createQuery(sql.toString(), UF.class);
			query.setParameter("sigla", sigla.toUpperCase());

			uf = query.getSingleResult();
		} catch (Throwable e) {
			httpGoApi.geraLog("EnderecoDao || Get UF por sigla", StringUtil.convertObjectToJson(sigla), e);
		}

		return uf;
	}

	public List<Endereco> getListEnderecosValidosPor(Long idPessoa) {
		List<Endereco> list = new ArrayList<Endereco>();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT E FROM Endereco E ");
		sql.append("   WHERE E.pessoa.id = :idPessoa ");
		sql.append("   AND   E.valido = true ");
		sql.append("   AND   E.excluido = 0 ");

		try {
			TypedQuery<Endereco> query = em.createQuery(sql.toString(), Endereco.class);
			query.setParameter("idPessoa", idPessoa);

			list = query.getResultList();

		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			httpGoApi.geraLog("EnderecoDao || getListEnderecosValidosPor", StringUtil.convertObjectToJson(idPessoa), e);
		}

		return list;
	}

	public boolean existeEnderecoValidoEPostalPessoa(Pessoa pessoa) {
		Endereco endereco = getEnderecoValidoEPostalPor(pessoa.getId());
		return endereco == null;
	}

	public Endereco getEnderecoById(Long idEndereco) {

		Endereco endereco = null;

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT E FROM Endereco E ");
		sql.append("   WHERE E.id = :idEndereco ");

		try {
			TypedQuery<Endereco> query = em.createQuery(sql.toString(), Endereco.class);
			query.setParameter("idEndereco", idEndereco);

			endereco = query.getSingleResult();

		} catch (Exception e) {
			httpGoApi.geraLog("EnderecoDao || Get Endereco By Id", StringUtil.convertObjectToJson(idEndereco), e);
		}

		return endereco;

	}

	public boolean excluiEnderecoById(Long idEndereco) {

		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE  CAD_ENDERECOS E ");
		sql.append("     SET E.EXCLUIDO = 1 ");
		sql.append("	 WHERE E.CODIGO = :idEndereco ");

		try {
			
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idEndereco", idEndereco);
			
			query.executeUpdate();
		} catch (Throwable e) {
			httpGoApi.geraLog("EnderecoDao || excluiEnderecoById", StringUtil.convertObjectToJson(idEndereco), e);
		}
		return true;
	}
	
	public Endereco getEnderecoValidoEPostalPor(Long idPessoa) {
		Endereco endereco = new Endereco();

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT E FROM Endereco E       ");
		sql.append("  WHERE E.pessoa.id = :idPessoa ");
		sql.append("    AND E.valido = 1            ");
		sql.append("    AND E.postal = 1            ");
		sql.append("    AND E.excluido = 0          ");

		try {
			TypedQuery<Endereco> query = em.createQuery(sql.toString(), Endereco.class);
			query.setParameter("idPessoa", idPessoa);

			query.setMaxResults(1);
			endereco = query.getSingleResult();

		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			httpGoApi.geraLog("EnderecoDao || getEnderecoValidoEPostalPor", StringUtil.convertObjectToJson(idPessoa),
					e);
		}

		return endereco;
	}
	
	public boolean possuiEnderecoValidoEPostalPor(Long idPessoa) {

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(E.CODIGO) FROM CAD_ENDERECOS E   ");
		sql.append("  WHERE E.FK_CODIGO_PESSOAS = :idPessoa        ");
		sql.append("    AND E.ENDERECOVALIDO = 1                   ");
		sql.append("    AND E.POSTAL = 1                           ");
		sql.append("    AND E.EXCLUIDO = 0                         ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idPessoa", idPessoa);

			BigDecimal resultado = (BigDecimal) query.getSingleResult();
	 	    
		 	return resultado.compareTo(new BigDecimal(0)) > 0;

		} catch (NoResultException e) {
			return false;
		} catch (Exception e) {
			httpGoApi.geraLog("EnderecoDao || possuiEnderecoValidoEPostalPor", StringUtil.convertObjectToJson(idPessoa),
					e);
		}

		return false;
	}
	
	public boolean updatePostal(Long idEndereco, boolean ehPostal) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE  CAD_ENDERECOS E ");
		sql.append("     SET E.POSTAL = :postal ");
		sql.append("	 WHERE E.CODIGO = :idEndereco ");
		
		try {
			
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("postal", ehPostal);
			query.setParameter("idEndereco", idEndereco);
			
			query.executeUpdate();
		} catch (Throwable e) {
			httpGoApi.geraLog("EnderecoDao || atualizarEndereco", StringUtil.convertObjectToJson(idEndereco), e);
		}
		return true;
	}

	public EnderecoDto atualizarEndereco(EnderecoDto dto) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE CAD_ENDERECOS E              ");
		sql.append("    SET E.NUMERO = :numero ,         ");
		sql.append("        E.POSTAL = :postal ,         ");
		sql.append("        E.COMPLEMENTO = :complemento ");
		sql.append("  WHERE E.CODIGO = :idEndereco       ");
		
		try {
			
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("numero", dto.getNumero());
			query.setParameter("postal", dto.ehPostal());
			query.setParameter("complemento", dto.getComplemento());
			query.setParameter("idEndereco", dto.getId());
			
			query.executeUpdate();
		} catch (Throwable e) {
			httpGoApi.geraLog("EnderecoDao || atualizarEndereco", StringUtil.convertObjectToJson(dto), e);
		}
		return dto;
	}
}
