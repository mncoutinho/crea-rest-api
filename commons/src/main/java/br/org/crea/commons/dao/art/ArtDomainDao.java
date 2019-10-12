package br.org.crea.commons.dao.art;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.org.crea.commons.models.art.ArtClasseProduto;
import br.org.crea.commons.models.art.ArtClasseToxicologica;
import br.org.crea.commons.models.art.ArtCultura;
import br.org.crea.commons.models.art.ArtDiagnostico;
import br.org.crea.commons.models.art.ArtGrupoQuimico;
import br.org.crea.commons.models.art.ArtIngredienteAtivo;
import br.org.crea.commons.models.art.ArtProduto;
import br.org.crea.commons.models.art.BaixaArt;
import br.org.crea.commons.models.art.ComplementoArt;
import br.org.crea.commons.models.art.ContratoArtFinalidade;
import br.org.crea.commons.models.art.ContratoArtTipoAcaoInstitucional;
import br.org.crea.commons.models.art.ContratoArtTipoCargoFuncao;
import br.org.crea.commons.models.art.ContratoArtTipoContratante;
import br.org.crea.commons.models.art.ContratoArtTipoFuncao;
import br.org.crea.commons.models.art.ContratoArtTipoUnidadeAdministrativa;
import br.org.crea.commons.models.art.ContratoArtTipoVinculo;
import br.org.crea.commons.models.art.ConvenioPublico;
import br.org.crea.commons.models.art.FatoGeradorArt;
import br.org.crea.commons.models.art.ParticipacaoTecnicaArt;
import br.org.crea.commons.models.art.RamoArt;
import br.org.crea.commons.models.art.TipoArt;
import br.org.crea.commons.models.cadastro.EntidadeClasse;
import br.org.crea.commons.models.cadastro.UnidadeMedida;
import br.org.crea.commons.models.cadastro.enuns.ModalidadeEnum;
import br.org.crea.commons.models.commons.dtos.DomainGenericDto;
import br.org.crea.commons.models.fiscalizacao.ContratoAtividade;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class ArtDomainDao {

	@Inject
	HttpClientGoApi httpGoApi;

	@PersistenceContext(unitName = "dscrea")
	protected EntityManager em;

	public List<ArtCultura> getAllArtCulturas() {

		List<ArtCultura> artCulturas = new ArrayList<ArtCultura>();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT A FROM ArtCultura A ");
		sql.append("ORDER BY A.descricao ");

		try {

			TypedQuery<ArtCultura> queryArtCultura = em.createQuery(sql.toString(), ArtCultura.class);

			artCulturas = queryArtCultura.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDomainDao || Get All ArtCultura", StringUtil.convertObjectToJson("SEM PARAMETRO"), e);
		}

		return artCulturas;
	}

	public List<ArtDiagnostico> getAllArtDiagnostico() {

		List<ArtDiagnostico> artDiagnostico = new ArrayList<ArtDiagnostico>();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT A FROM ArtDiagnostico A ");
		sql.append("ORDER BY A.descricao ");

		try {
			TypedQuery<ArtDiagnostico> queryArtDiagnostico = em.createQuery(sql.toString(), ArtDiagnostico.class);
			artDiagnostico = queryArtDiagnostico.getResultList();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDomainDao || Get All ArtDiagnostico", StringUtil.convertObjectToJson("SEM PARAMETRO"), e);
		}

		return artDiagnostico;
	}

	public List<ArtProduto> getAllArtProduto() {

		List<ArtProduto> artProdutos = new ArrayList<ArtProduto>();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT A FROM ArtProduto A ");
		sql.append("	ORDER BY A.descricao ");

		try {
			TypedQuery<ArtProduto> queryArtProduto = em.createQuery(sql.toString(), ArtProduto.class);
			artProdutos = queryArtProduto.getResultList();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDomainDao || Get All ArtProduto", StringUtil.convertObjectToJson("SEM PARAMETRO"), e);
		}

		return artProdutos;
	}

	public ArtCultura getCultura(String sigla) {
		return em.find(ArtCultura.class, sigla);
	}

	public ArtDiagnostico getDiagnostico(Long codigo) {
		return em.find(ArtDiagnostico.class, codigo);
	}

	public ArtClasseProduto getClasseProduto(Long codigo) {
		return em.find(ArtClasseProduto.class, codigo);
	}

	public ArtClasseToxicologica getClasseToxicacao(Long codigo) {
		return em.find(ArtClasseToxicologica.class, codigo);
	}

	public ArtGrupoQuimico getGrupoQuimico(Long codigo) {
		return em.find(ArtGrupoQuimico.class, codigo);
	}

	public ArtIngredienteAtivo getIngredienteAtivo(String codigo) {
		return em.find(ArtIngredienteAtivo.class, codigo);
	}

	public List<UnidadeMedida> getAllUnidadesMedidaArtReceita() {
		List<UnidadeMedida> lista = new ArrayList<UnidadeMedida>();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT U FROM UnidadeMedida U ");
		sql.append("    WHERE U.receita = 1 ");
		sql.append("	ORDER BY U.abreviatura ");

		try {
			TypedQuery<UnidadeMedida> queryUnidadeMedida = em.createQuery(sql.toString(), UnidadeMedida.class);
			lista = queryUnidadeMedida.getResultList();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDomainDao || Get All UnidadeMedida", StringUtil.convertObjectToJson("SEM PARAMETRO"), e);
		}

		return lista;
	}

	public UnidadeMedida getUnidadeMedida(Long codigo) {
		return em.find(UnidadeMedida.class, codigo);
	}

	public List<RamoArt> getAllRamosModalidade(ModalidadeEnum modalidade) {
		List<RamoArt> lista = new ArrayList<RamoArt>();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT R FROM RamoArt R ");
		sql.append("    WHERE R.modalidade.id = :modalidade ");
		sql.append("	ORDER BY R.descricao ");

		try {
			TypedQuery<RamoArt> queryRamo = em.createQuery(sql.toString(), RamoArt.class);
			queryRamo.setParameter("modalidade", modalidade.getId());
			lista = queryRamo.getResultList();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDomainDao || Get All RamoArtAgronomia", StringUtil.convertObjectToJson("SEM PARAMETRO"), e);
		}

		return lista;
	}
	
	public List<RamoArt> getAllRamosFiscalizacao() {
		List<RamoArt> lista = new ArrayList<RamoArt>();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT R FROM RamoArt R ");
		sql.append("    WHERE R.fiscalizacao = 1 ");
		sql.append("	ORDER BY R.descricao ");

		try {
			TypedQuery<RamoArt> queryRamo = em.createQuery(sql.toString(), RamoArt.class);
			
			lista = queryRamo.getResultList();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDomainDao || Get All getAllRamosFiscalizacao", StringUtil.convertObjectToJson("SEM PARAMETRO"), e);
		}

		return lista;
	}

	public List<DomainGenericDto> getAllRamosByIdProfissional(Long idProfissional) {
		List<DomainGenericDto> lista = new ArrayList<DomainGenericDto>();

		StringBuilder sql = new StringBuilder();

		sql.append(" SELECT DISTINCT R.CODIGO, R.DESCRICAO FROM CAD_PROFXESPEC P ");
		sql.append("        JOIN CAD_RAMOATIVIDADE_ESPEC E ON ( P.FK_CODIGO_ESPECIALIDADES = E.FK_CODIGO_ESPECIALIDADE ) ");
		sql.append("        JOIN CAD_ATIVIDADES A ON ( A.CODIGO = E.FK_ATIVIDADE ) ");
		sql.append("        JOIN CAD_RAMOS R ON ( R.CODIGO = A.FK_RAMO ) ");
		sql.append("                WHERE P.FK_CODIGO_PROFISSIONAIS = :idProfissional ");
		sql.append("                AND P.DATACANCELAMENTO IS NULL ");
		sql.append("                AND E.STATUS = 1 ");
		sql.append("                AND R.ART = 1 ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idProfissional", idProfissional);

			Iterator<?> it = query.getResultList().iterator();
			
			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {
					
					Object[] result = (Object[]) it.next();
					DomainGenericDto ramo = new DomainGenericDto();

					BigDecimal idRamo = (BigDecimal) result[0];
					ramo.setId(idRamo.setScale(0, BigDecimal.ROUND_UP).longValueExact());
					ramo.setNome(result[1] == null ? "" : result[1].toString());

					lista.add(ramo);
				}
			}

		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDomainDao || Get All Ramos By Id Profissional", StringUtil.convertObjectToJson(idProfissional), e);
		}

		return lista;
	}

	public List<ComplementoArt> getAllComplementosArt() {
		List<ComplementoArt> lista = new ArrayList<ComplementoArt>();

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT C FROM ComplementoArt C  ");
		sql.append(" ORDER BY C.descricao");

		try {
			TypedQuery<ComplementoArt> query = em.createQuery(sql.toString(), ComplementoArt.class);
			lista = query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDomainDao || Get All ComplementosArt", StringUtil.convertObjectToJson("SEM PARAMETRO"), e);
		}

		return lista;
	}
	
	public List<ContratoAtividade> getAllAtividades() {
		List<ContratoAtividade> lista = new ArrayList<ContratoAtividade>();

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT C FROM ContratoAtividade C ");
		sql.append(" WHERE ativo = 1 ");
		sql.append(" ORDER BY C.descricao");

		try {
			TypedQuery<ContratoAtividade> query = em.createQuery(sql.toString(), ContratoAtividade.class);
			lista = query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDomainDao || Get All ContratoAtividade", StringUtil.convertObjectToJson("SEM PARAMETRO"), e);
		}

		return lista;
	}
	
	public List<DomainGenericDto> getAllAtividadesTecnicasByIdProfissional(Long idProfissional) {
		List<DomainGenericDto> lista = new ArrayList<DomainGenericDto>();

		StringBuilder sql = new StringBuilder();

		sql.append(" SELECT DISTINCT T.CODIGO, T.DESCRICAO FROM CAD_PROFXESPEC P                                         ");
		sql.append("        JOIN CAD_RAMOATIVIDADE_ESPEC E ON ( P.FK_CODIGO_ESPECIALIDADES = E.FK_CODIGO_ESPECIALIDADE ) ");
		sql.append("        JOIN CAD_ATIVIDADES A ON ( A.CODIGO = E.FK_ATIVIDADE )                                       ");
		sql.append("        JOIN CAD_RAMOS R ON ( R.CODIGO = A.FK_RAMO )                                                 ");
		sql.append("        JOIN ART_RAMOS_ATIVIDADE I ON ( R.CODIGO = I.FK_CODIGO_RAMO )                                ");
		sql.append("        JOIN ART_ATIVIDADE_TECNICA T ON ( T.CODIGO = I.FK_CODIGO_ATIVIDADE )                         ");
		sql.append("                WHERE P.FK_CODIGO_PROFISSIONAIS = :idProfissional                                    ");
		sql.append("                  AND P.DATACANCELAMENTO IS NULL                                                     ");
		sql.append("                  AND E.STATUS = 1                                                                   ");
		sql.append("                  AND R.ART = 1                                                                      ");
		sql.append("        ORDER BY T.DESCRICAO                                                                         ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idProfissional", idProfissional);

			Iterator<?> it = query.getResultList().iterator();

			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {

					Object[] result = (Object[]) it.next();
					DomainGenericDto atividadeTecnica = new DomainGenericDto();

					BigDecimal idAtividadeTecnica = (BigDecimal) result[0];
					atividadeTecnica.setId(idAtividadeTecnica.setScale(0, BigDecimal.ROUND_UP).longValueExact());
					atividadeTecnica.setNome(result[1] == null ? "" : result[1].toString());

					lista.add(atividadeTecnica);
				}
			}

		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDomainDao || Get All Atividades Tecnicas", StringUtil.convertObjectToJson(idProfissional), e);
		}

		return lista;
	}
	

	public List<DomainGenericDto> getAllEspecificacaoAtividadeByIdProfissional(Long idProfissional) {
		List<DomainGenericDto> lista = new ArrayList<DomainGenericDto>();

		StringBuilder sql = new StringBuilder();

		sql.append(" SELECT DISTINCT O.CODIGO, O.DESCRICAO FROM CAD_PROFXESPEC P                                         ");
		sql.append("        JOIN CAD_RAMOATIVIDADE_ESPEC E ON ( P.FK_CODIGO_ESPECIALIDADES = E.FK_CODIGO_ESPECIALIDADE ) ");
		sql.append("        JOIN CAD_ATIVIDADES A ON ( A.CODIGO = E.FK_ATIVIDADE )                                       ");
		sql.append("        JOIN CAD_RAMOS R ON ( R.CODIGO = A.FK_RAMO )                                                 ");
		sql.append("        JOIN ART_RAMOS_ESPECIFICACAO S ON ( R.CODIGO = S.FK_CODIGO_RAMO )                            ");
		sql.append("        JOIN ART_ESPECIFICACAO_ATIVIDADE O ON ( O.CODIGO = S.FK_CODIGO_ESPECIFICACAO )               ");
		sql.append("                WHERE P.FK_CODIGO_PROFISSIONAIS = :idProfissional                                    ");
		sql.append("                  AND P.DATACANCELAMENTO IS NULL                                                     ");
		sql.append("                  AND E.STATUS = 1                                                                   ");
		sql.append("                  AND S.STATUS = 1                                                                   ");
		sql.append("                  AND R.ART = 1                                                                      ");
		sql.append("        ORDER BY O.DESCRICAO                                                                         ");
		

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idProfissional", idProfissional);

			Iterator<?> it = query.getResultList().iterator();

			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {

					Object[] result = (Object[]) it.next();
					DomainGenericDto especificacaoAtividade = new DomainGenericDto();

					BigDecimal idEspecificacaoAtividade = (BigDecimal) result[0];
					especificacaoAtividade.setId(idEspecificacaoAtividade.setScale(0, BigDecimal.ROUND_UP).longValueExact());
					especificacaoAtividade.setNome(result[1] == null ? "" : result[1].toString());

					lista.add(especificacaoAtividade);
				}
			}

		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDomainDao || Get All Especificacao Atividade", StringUtil.convertObjectToJson(idProfissional), e);
		}

		return lista;
	}

	public List<DomainGenericDto> getAllComplementosArtByIdProfissional(Long idProfissional) {
		List<DomainGenericDto> lista = new ArrayList<DomainGenericDto>();

		StringBuilder sql = new StringBuilder();

		sql.append(" SELECT DISTINCT T.CODIGO, T.DESCRICAO FROM CAD_PROFXESPEC P                                         ");
		sql.append("        JOIN CAD_RAMOATIVIDADE_ESPEC E ON ( P.FK_CODIGO_ESPECIALIDADES = E.FK_CODIGO_ESPECIALIDADE ) ");
		sql.append("        JOIN CAD_ATIVIDADES A ON ( A.CODIGO = E.FK_ATIVIDADE )                                       ");
		sql.append("        JOIN CAD_RAMOS R ON ( R.CODIGO = A.FK_RAMO )                                                 ");
		sql.append("        JOIN ART_RAMOS_COMPLEMENTO I ON ( R.CODIGO = I.FK_CODIGO_RAMO )                              ");
		sql.append("        JOIN ART_COMPLEMENTO T ON ( T.CODIGO = I.FK_CODIGO_COMPLEMENTO )                             ");
		sql.append("                WHERE P.FK_CODIGO_PROFISSIONAIS = :idProfissional                                    ");
		sql.append("                  AND P.DATACANCELAMENTO IS NULL                                                     ");
		sql.append("                  AND E.STATUS = 1                                                                   ");
		sql.append("                  AND R.ART = 1                                                                      ");
		sql.append("        ORDER BY T.DESCRICAO                                                                         ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idProfissional", idProfissional);

			Iterator<?> it = query.getResultList().iterator();

			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {

					Object[] result = (Object[]) it.next();
					DomainGenericDto complemento = new DomainGenericDto();

					BigDecimal idComplementos = (BigDecimal) result[0];
					complemento.setId(idComplementos.setScale(0, BigDecimal.ROUND_UP).longValueExact());
					complemento.setNome(result[1] == null ? "" : result[1].toString());

					lista.add(complemento);

				}

			}

		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDomainDao || Get All Complementos Art By Id Profissional", StringUtil.convertObjectToJson(idProfissional), e);
		}

		return lista;
	}

	public List<DomainGenericDto> getAllNatureza(Long idProfissional) {

		List<DomainGenericDto> lista = new ArrayList<DomainGenericDto>();

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT * FROM ART_NATUREZA N ");
		sql.append("      WHERE N.ISONLINE = '1' ");
		sql.append("      AND (N.CODIGO != '3' ");
		sql.append("      OR EXISTS (SELECT * FROM CAD_PROFXESPEC X  ");
		sql.append("      	WHERE X.FK_CODIGO_PROFISSIONAIS = :idProfissional  ");
		sql.append("     	AND X.FK_CODIGO_MODALIDADES = '5'  ");
		sql.append("     	AND X.DATACANCELAMENTO IS NULL ))  ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idProfissional", idProfissional);
			
			Iterator<?> it = query.getResultList().iterator();

			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {

					Object[] result = (Object[]) it.next();
					DomainGenericDto natureza = new DomainGenericDto();

					BigDecimal idNatureza = (BigDecimal) result[0];
					natureza.setId(idNatureza.setScale(0, BigDecimal.ROUND_UP).longValueExact());
					natureza.setNome(result[1] == null ? "" : result[1].toString());

					lista.add(natureza);
				}
			}
			
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDomainDao || Get All Natureza", StringUtil.convertObjectToJson(idProfissional), e);
		}

		return lista;
	}

	public List<FatoGeradorArt> getAllFatoGerador() {
		List<FatoGeradorArt> lista = new ArrayList<FatoGeradorArt>();

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT F FROM FatoGeradorArt F ");
		sql.append("  ORDER BY F.id                 ");

		try {
			TypedQuery<FatoGeradorArt> query = em.createQuery(sql.toString(), FatoGeradorArt.class);
			lista = query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDomainDao || Get All Fato Gerador", StringUtil.convertObjectToJson("SEM PARAMETRO"), e);
		}

		return lista;
	}

	public List<TipoArt> getAllTipo() {
		List<TipoArt> lista = new ArrayList<TipoArt>();

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT T FROM TipoArt T ");
		sql.append("  WHERE T.isOnline = 1   ");
		sql.append(" ORDER BY T.id           ");

		try {
			TypedQuery<TipoArt> query = em.createQuery(sql.toString(), TipoArt.class);
			lista = query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDomainDao || Get All Tipo Art", StringUtil.convertObjectToJson("SEM PARAMETRO"), e);
		}

		return lista;
	}
	
	public List<ParticipacaoTecnicaArt> getAllParticipacaoTecnica() {
		
		List<ParticipacaoTecnicaArt> lista = new ArrayList<ParticipacaoTecnicaArt>();

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT T FROM ParticipacaoTecnicaArt T ");
		sql.append("  WHERE T.isOnline = 1                  ");
		sql.append("    AND T.ativo = 1                     ");
		sql.append(" ORDER BY T.id                          ");

		try {
			TypedQuery<ParticipacaoTecnicaArt> query = em.createQuery(sql.toString(), ParticipacaoTecnicaArt.class);
			lista = query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDomainDao || getAllParticipacaoTecnica", StringUtil.convertObjectToJson("SEM PARAMETRO"), e);
		}

		return lista;
	}

	public List<ConvenioPublico> getAllConvenioPublico() {
		List<ConvenioPublico> lista = new ArrayList<ConvenioPublico>();

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT C FROM ConvenioPublico C ");
		sql.append(" ORDER BY C.descricao ");

		try {
			TypedQuery<ConvenioPublico> query = em.createQuery(sql.toString(), ConvenioPublico.class);
			lista = query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDomainDao || Get All Convenio Publico", StringUtil.convertObjectToJson("SEM PARAMETRO"), e);
		}

		return lista;
	}

	public List<UnidadeMedida> getAllUnidadeMedida() {
		List<UnidadeMedida> lista = new ArrayList<UnidadeMedida>();

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT U FROM UnidadeMedida U ");
		sql.append(" ORDER BY U.descricao ");

		try {
			TypedQuery<UnidadeMedida> query = em.createQuery(sql.toString(), UnidadeMedida.class);
			lista = query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDomainDao || Get All Unidade Medida", StringUtil.convertObjectToJson("SEM PARAMETRO"), e);
		}

		return lista;
	}

	public List<EntidadeClasse> getEntidadesClasse() {
	
		List<EntidadeClasse> lista = new ArrayList<EntidadeClasse>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT E FROM EntidadeClasse E ");
		sql.append("             WHERE E.tipoEntidade IN (0, 1) ");
		
		try{
			TypedQuery<EntidadeClasse> query = em.createQuery(sql.toString(), EntidadeClasse.class);
			
			lista = query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDomainDao || Get Entidades Classe", StringUtil.convertObjectToJson("SEM PARAMETRO"), e);
		}
		
		return lista;
	}
	
	public ContratoAtividade getAtividade(Long codigo) {
		ContratoAtividade atividade = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT A FROM ContratoAtividade A ");
		sql.append("              WHERE A.codigo = :codigo");
		
		try{
			TypedQuery<ContratoAtividade> query = em.createQuery(sql.toString(), ContratoAtividade.class);
			query.setParameter("codigo", codigo);
			
			atividade = query.getSingleResult();			
		} catch (Throwable e) {
			httpGoApi.geraLog("FiscalizacaoDomainDao || Get Atividade ", StringUtil.convertObjectToJson(codigo), e);
		}
		
		return atividade;
	}
	
	
	public List<ContratoArtTipoUnidadeAdministrativa> getTiposUnidadesAdministrativaAtivas() {
		
		List<ContratoArtTipoUnidadeAdministrativa> lista = new ArrayList<ContratoArtTipoUnidadeAdministrativa>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT C FROM ContratoArtTipoUnidadeAdministrativa C ");
		sql.append("             WHERE C.ativo = true ");
		
		try{
			TypedQuery<ContratoArtTipoUnidadeAdministrativa> query = em.createQuery(sql.toString(), ContratoArtTipoUnidadeAdministrativa.class);
			
			lista = query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDomainDao || getTiposUnidadesAdministrativa", StringUtil.convertObjectToJson("SEM PARAMETRO"), e);
		}
		
		return lista;
	}

	public List<ContratoArtTipoAcaoInstitucional> getTiposAcoesInstitucionaisAtivas() {
		List<ContratoArtTipoAcaoInstitucional> lista = new ArrayList<ContratoArtTipoAcaoInstitucional>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("   SELECT C FROM ContratoArtTipoAcaoInstitucional C ");
		sql.append("    WHERE C.ativo = true                            ");
		sql.append(" ORDER BY C.descricao                               ");
		
		try{
			TypedQuery<ContratoArtTipoAcaoInstitucional> query = em.createQuery(sql.toString(), ContratoArtTipoAcaoInstitucional.class);
			
			lista = query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDomainDao || getTiposAcoesInstitucionais", StringUtil.convertObjectToJson("SEM PARAMETRO"), e);
		}
		
		return lista;
	}

	public List<ContratoArtTipoCargoFuncao> getTiposCargosFuncoesAtivas() {
		
		List<ContratoArtTipoCargoFuncao> lista = new ArrayList<ContratoArtTipoCargoFuncao>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("   SELECT C FROM ContratoArtTipoCargoFuncao C ");
		sql.append("    WHERE C.ativo = true                      ");
		sql.append(" ORDER BY C.descricao                         ");
		
		try{
			TypedQuery<ContratoArtTipoCargoFuncao> query = em.createQuery(sql.toString(), ContratoArtTipoCargoFuncao.class);
			
			lista = query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDomainDao || getTiposCargosFuncoes", StringUtil.convertObjectToJson("SEM PARAMETRO"), e);
		}
		
		return lista;
	}

	public List<ContratoArtTipoFuncao> getTiposFuncoesAtivas() {
		
		List<ContratoArtTipoFuncao> lista = new ArrayList<ContratoArtTipoFuncao>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("   SELECT C FROM ContratoArtTipoFuncao C ");
		sql.append("    WHERE C.ativo = true                 ");
		sql.append(" ORDER BY C.descricao                    ");
		
		try{
			TypedQuery<ContratoArtTipoFuncao> query = em.createQuery(sql.toString(), ContratoArtTipoFuncao.class);
			
			lista = query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDomainDao || getTiposFuncoesAtivas", StringUtil.convertObjectToJson("SEM PARAMETRO"), e);
		}
		
		return lista;
	}

	public List<ContratoArtTipoVinculo> getTiposVinculosAtivos() {
		
		List<ContratoArtTipoVinculo> lista = new ArrayList<ContratoArtTipoVinculo>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT C FROM ContratoArtTipoVinculo C ");
		sql.append("             WHERE C.ativo = true ");
		
		try{
			TypedQuery<ContratoArtTipoVinculo> query = em.createQuery(sql.toString(), ContratoArtTipoVinculo.class);
			
			lista = query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDomainDao || getTiposVinculosAtivos", StringUtil.convertObjectToJson("SEM PARAMETRO"), e);
		}
		
		return lista;
	}

	public List<ContratoArtTipoContratante> getTiposContratantesAtivos() {
		
		List<ContratoArtTipoContratante> lista = new ArrayList<ContratoArtTipoContratante>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT C FROM ContratoArtTipoContratante C ");
		sql.append("             WHERE C.ativo = true ");
		
		try{
			TypedQuery<ContratoArtTipoContratante> query = em.createQuery(sql.toString(), ContratoArtTipoContratante.class);
			
			lista = query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDomainDao || getTiposContratantesAtivos", StringUtil.convertObjectToJson("SEM PARAMETRO"), e);
		}
		
		return lista;
		
	}

	public List<ContratoArtFinalidade> getFinalidadesAtivas() {
		
		List<ContratoArtFinalidade> lista = new ArrayList<ContratoArtFinalidade>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("   SELECT C FROM ContratoArtFinalidade C ");
		sql.append("    WHERE C.ativo = true                 ");
		sql.append(" ORDER BY C.descricao                    ");
		
		try{
			TypedQuery<ContratoArtFinalidade> query = em.createQuery(sql.toString(), ContratoArtFinalidade.class);
			
			lista = query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDomainDao || getFinalidadesAtivas", StringUtil.convertObjectToJson("SEM PARAMETRO"), e);
		}
		
		return lista;
	}

	public List<BaixaArt> getTiposBaixa() {
		List<BaixaArt> lista = new ArrayList<BaixaArt>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT B FROM BaixaArt B ");
		sql.append("             WHERE B.onLine = true ");
		
		try{
			TypedQuery<BaixaArt> query = em.createQuery(sql.toString(), BaixaArt.class);
			
			lista = query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtDomainDao || getTiposBaixa", StringUtil.convertObjectToJson("SEM PARAMETRO"), e);
		}
		
		return lista;
	}



}
