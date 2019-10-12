package br.org.crea.commons.dao.cadastro.empresa;

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
import javax.swing.text.MaskFormatter;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.dao.corporativo.Page;
import br.org.crea.commons.models.cadastro.QuadroTecnico;
import br.org.crea.commons.models.cadastro.dtos.empresa.VinculoEmpresaResponsavelTecnicoDto;
import br.org.crea.commons.models.commons.dtos.DomainGenericDto;
import br.org.crea.commons.models.commons.dtos.EnderecoDto;
import br.org.crea.commons.models.commons.dtos.PesquisaGenericDto;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.models.corporativo.pessoa.Empresa;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.DateUtils;
import br.org.crea.commons.util.StringUtil;


@Stateless
public class EmpresaDao extends GenericDao<Empresa, Serializable> {
	
	@Inject HttpClientGoApi httpGoApi;
	
	public EmpresaDao() {
		super(Empresa.class);
	}
	
	public List<Empresa> buscaListEmpresaByNome(PesquisaGenericDto dto) {

		List<Empresa> listEmpresa = new ArrayList<Empresa>();
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT E FROM Empresa E, RazaoSocial R  ");
		sql.append("	WHERE E.id = R.pessoaJuridica.id ");
		sql.append("	AND R.descricao LIKE :razaoSocial ");
		sql.append("	AND R.ativo = 1 ");
		sql.append("	AND E.pessoaJuridica.tipoPessoa = 'EMPRESA' ");
		sql.append("	AND E.pessoaJuridica.situacao.id not in (4,8,9,10) ");
		
		try {
			TypedQuery<Empresa> query = em.createQuery(sql.toString(), Empresa.class);
			query.setParameter("razaoSocial", "%" + dto.getRazaoSocial().toUpperCase() + "%");
			
			Page page = new Page(dto.getPage(), dto.getRows());
			page.paginate(query);

			listEmpresa = query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("EmpresaDao || buscaListEmpresaByNome ", StringUtil.convertObjectToJson(dto), e);
		}
		
		return listEmpresa;


	}
	public int  totalBuscaListEmpresaByNome(PesquisaGenericDto dto) {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT COUNT(R.DESCRICAO) ");
		sql.append("	FROM  CAD_RAZOES_SOCIAIS R, ");
		sql.append("	CAD_PESSOAS_JURIDICAS PJ,");
		sql.append("	CAD_PESSOAS P,");
		sql.append("	CAD_SITUACAO_REGISTRO SR ");
		sql.append("	WHERE PJ.CODIGO = P.CODIGO ");
		sql.append("	AND   PJ.CODIGO = R.FK_CODIGO_PJS ");
		sql.append("	AND   P.FK_CODIGO_SITUACAO_REGISTRO = SR.CODIGO ");
		sql.append("	AND   R.DESCRICAO LIKE :razaoSocial ");
		sql.append("	AND   R.ATIVO = 1 ");
		sql.append("	AND   P.TIPOPESSOA = 'EMPRESA' ");
		sql.append("	AND   SR.CODIGO NOT IN (4,8,9,10) ");

		int resultado = 0;
		try {
			
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("razaoSocial", "%" + dto.getRazaoSocial().toUpperCase() + "%");
			
			resultado = Integer.parseInt(query.getSingleResult().toString());
			
		}catch (Throwable e) {
			httpGoApi.geraLog("EmpresaDao || totalBuscaListEmpresaByNome", StringUtil.convertObjectToJson(dto), e);
		}
		
		return resultado;
	}

	public List<Empresa> buscaEmpresaByCNPJ(String numeroCNPJ) {

		List<Empresa> listEmpresa = new ArrayList<Empresa>();
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT E FROM Empresa E  ");
		sql.append("	WHERE E.pessoaJuridica.cnpj = :cnpj ");
		sql.append("	AND E.pessoaJuridica.tipoPessoa = 'EMPRESA' ");
		sql.append("	AND E.pessoaJuridica.situacao.id not in (4,8,9,10) ");
		
		try {
			TypedQuery<Empresa> query = em.createQuery(sql.toString(), Empresa.class);
			query.setParameter("cnpj", numeroCNPJ);

			listEmpresa = query.getResultList();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("EmpresaDao || buscaEmpresaByCNPJ ", StringUtil.convertObjectToJson(numeroCNPJ), e);
		}

		return listEmpresa;

	}
	
	public List<Empresa> buscaEmpresaByRegistro(Long idPessoa) {

		List<Empresa> listEmpresa = new ArrayList<Empresa>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT E FROM Empresa E ");
		sql.append("	WHERE E.id = :idPessoa ");
		sql.append("	AND E.pessoaJuridica.tipoPessoa = 'EMPRESA' ");
		sql.append("	AND E.pessoaJuridica.situacao.id not in (4,8,9,10) ");

		try {
			TypedQuery<Empresa> query = em.createQuery(sql.toString(), Empresa.class);
			query.setParameter("idPessoa", idPessoa);
		
			listEmpresa = query.getResultList();
		} catch (NoResultException e){
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("EmpresaDao || buscaEmpresaByRegistro", StringUtil.convertObjectToJson(idPessoa), e);
		}
		
		return listEmpresa;

	}

	public List<Empresa> buscaEmpresaDetalhadaByRegistro(Long idPessoa) {

		List<Empresa> listEmpresa = new ArrayList<Empresa>();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT e FROM Empresa e                        ");
		sql.append("  WHERE e.id = :idPessoa                        ");
		sql.append("    AND e.pessoaJuridica.tipoPessoa = 'EMPRESA' ");

		try {
			TypedQuery<Empresa> query = em.createQuery(sql.toString(), Empresa.class);
			query.setParameter("idPessoa", idPessoa);
		
			listEmpresa = query.getResultList();
			
		} catch (NoResultException e){
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("EmpresaDao || buscaEmpresaDetalhadaByRegistro", StringUtil.convertObjectToJson(idPessoa), e);
		}
		
		return listEmpresa;

	}
	
	public Empresa getEmpresaPor(Long codigoEmpresa) {

		Empresa empresa = new Empresa();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT E FROM Empresa E ");
		sql.append("	WHERE E.id = :codigoEmpresa ");
		sql.append("	AND E.pessoaJuridica.tipoPessoa = 'EMPRESA' ");

		try {
			TypedQuery<Empresa> query = em.createQuery(sql.toString(), Empresa.class);
			query.setParameter("codigoEmpresa", codigoEmpresa);
		
			empresa = query.getSingleResult();
		} catch (NoResultException e){
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("EmpresaDao || getEmpresaPor", StringUtil.convertObjectToJson(codigoEmpresa), e);
		}
		
		return empresa;

	}
	

	@SuppressWarnings({ "unchecked"})
	public List<VinculoEmpresaResponsavelTecnicoDto> getEmpresasOndeProfissionalEhResponsavelPor(Long idProfissional) {
		List<VinculoEmpresaResponsavelTecnicoDto> listVinculosRtComEmpresas = new ArrayList<VinculoEmpresaResponsavelTecnicoDto>();
		
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT cqt.DATAINICIOQT, ");
		sql.append("    crs.FK_CODIGO_PJS registro_empresa, ");
		sql.append("    NVL(crs.descricao, 'NAO CADASTRADO') razao_social, ");
		sql.append("    NVL(TO_CHAR(cqt.jornadatrabalho), 'NAO INFORMADO' ) jornada, ");
		sql.append("    NVL(cqt.remuneracao, 0 ) salario, ");
		sql.append("    cvt.descricao, ");
		sql.append("    cend.codigo end_codigo, ");
		sql.append("    tl.descricao end_tipo, ");
		sql.append("    tl.abreviatura end_abrev, ");
		sql.append("    cend.logradouro end_logradouro, ");
		sql.append("    cend.numero end_numero, ");
		sql.append("    cend.complemento end_complemento, ");
		sql.append("    cend.bairro end_bairro, ");
		sql.append("    loc.descricao end_localidade, ");
		sql.append("    uf.uf end_uf, ");
		sql.append("    cend.cep end_cep, ");
		sql.append("    cqt.rt, ");
		sql.append("    cqt.codigo as qt_codigo ");
		sql.append("FROM CAD_QUADROS_TECNICOS cqt ");
		sql.append("LEFT JOIN CAD_RAZOES_SOCIAIS crs ON ( cqt.FK_CODIGO_EMPRESAS = ");
		sql.append("crs.FK_CODIGO_PJS AND ATIVO = 1 ) ");
		sql.append("LEFT JOIN CAD_TIPOS_VINCULOS cvt ON ( cvt.CODIGO = cqt.FK_CODIGO_TIPOS_VINCULOS ) ");
		sql.append("LEFT JOIN cad_enderecos cend ON (cend.FK_CODIGO_PESSOAS = cqt.FK_CODIGO_EMPRESAS AND cend.POSTAL = 1) ");
		sql.append("LEFT JOIN cad_tipos_logradouros tl ON (cend.fk_codigo_tipos_logradouros = tl.codigo) ");
		sql.append("LEFT JOIN cad_localidades loc ON (cend.fk_codigo_localidades = loc.codigo) ");
		sql.append("LEFT JOIN cad_ufs uf ON (loc.fk_codigo_ufs = uf.codigo) ");
		sql.append("WHERE cqt.FK_CODIGO_PROFISSIONAIS = :idProfissional AND cqt.DATAFIMQT IS NULL ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idProfissional", idProfissional);
//			1992104828
			
			Iterator<Object> it = query.getResultList().iterator();
			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {
					
					Object[] result = (Object[]) it.next();
					VinculoEmpresaResponsavelTecnicoDto vinculo = new VinculoEmpresaResponsavelTecnicoDto();
					
					vinculo.setEhResponsavelTecnico(false);
					vinculo.setResponsavelTecnico("NÃO");
					Date dataInicioQt = result[0] == null ? null :  (Date) result[0];
					vinculo.setDataInicioQt(result[0] == null ? "" : DateUtils.format(dataInicioQt, DateUtils.DD_MM_YYYY));				
					vinculo.setRegistroEmpresa(result[1] == null ? new Long(0) : Long.parseLong(result[1].toString()));
					vinculo.setRazaoSocialEmpresa(result[2] == null ? "" : result[2].toString());

					vinculo.setJornadaProfissional(result[3] == null ? "" : result[3].toString());
					vinculo.setSalarioProfissional(result[4] == null ? "" : result[4].toString());
					vinculo.setTipoVinculoProfissional(result[5] == null ? "" : result[5].toString());
					
					buscarResponsabilidadeTecnica(vinculo, result[17] == null ? "" : result[17].toString());

					BigDecimal idEndereco = (BigDecimal) result[6];
					
					if (idEndereco != null) {
						EnderecoDto enderecoEmpresa = new EnderecoDto();
						enderecoEmpresa.setId(idEndereco.setScale(0, BigDecimal.ROUND_UP).longValueExact());
						
						DomainGenericDto tipoEndereco = new DomainGenericDto();
						tipoEndereco.setDescricao(result[7] == null ? "" : result[7].toString());
						enderecoEmpresa.setTipoEndereco(tipoEndereco);
						
						DomainGenericDto tipoDescricao = new DomainGenericDto();
						tipoDescricao.setDescricao(result[8] == null ? "" : result[8].toString());
						
						enderecoEmpresa.setTipoLogradouro(tipoDescricao);
						enderecoEmpresa.setLogradouro(result[9] == null ? "" : result[9].toString());
						enderecoEmpresa.setNumero(result[10] == null ? "" : result[10].toString());
						enderecoEmpresa.setComplemento(result[11] == null ? "" : result[11].toString());
						enderecoEmpresa.setBairro(result[12] == null ? "" : result[12].toString());
						String localidade = result[13] == null ? "" : result[13].toString();
						String uf = result[14] == null ? "" : result[14].toString();
						enderecoEmpresa.setCep(result[15] == null ? "" : result[15].toString());
						String enderecoDescritivo = enderecoEmpresa.getTipoLogradouro().getDescricao() + " - " + enderecoEmpresa.getLogradouro() + " - " + enderecoEmpresa.getNumero() + " - " + enderecoEmpresa.getCep()
								+ " - " + localidade + " - " + uf;
						enderecoEmpresa.setEnderecoCompleto(enderecoDescritivo);
						
						vinculo.setEnderecoEmpresa(enderecoEmpresa);

					}
					
					if (vinculo.getRegistroEmpresa() != null) {
						setCnpj(vinculo);
						setArtCargoFuncao(vinculo,idProfissional, 69L, 190L);
					}
					
					listVinculosRtComEmpresas.add(vinculo);
				}
				
			}

		} catch (Throwable e) {
			httpGoApi.geraLog("EmpresaDao || getEmpresasOndeProfissionalEhResponsavelPor",StringUtil.convertObjectToJson(idProfissional), e);
		}
		return listVinculosRtComEmpresas;
	}
	

//	METODO PARA PEGAR ART CARGO E FUNÇÃO
	@SuppressWarnings({ "unchecked"})
	private void setArtCargoFuncao(VinculoEmpresaResponsavelTecnicoDto vinculo, Long idProfissional, Long idAtividade, Long idComplemento) {
		
		StringBuilder sql = new StringBuilder();

		sql.append(" SELECT ART.NUMERO ");
		sql.append(" FROM   ART_ART ART, ");
		sql.append("        ART_CONTRATO AC ");
		sql.append(" WHERE  ART.NUMERO          = AC.FK_ART ");
		sql.append(" AND    ART.FK_PROFISSIONAL = :idProfissional ");
		sql.append(" AND    ART.FK_EMPRESA = :registroEmpresa ");
		sql.append(" AND    ART.FK_NATUREZA_ART = 2 ");
		sql.append(" AND    ART.CANCELADA       = 0 ");
		sql.append(" AND    ART.BAIXADA         = 0 ");
		sql.append(" AND    EXISTS (  ");
		sql.append("       SELECT 1 ");
		sql.append("        FROM   ART_CONTRATO_ATIVIDADE ACA ");
		sql.append("        WHERE  ACA.FK_CONTRATO = AC.CODIGO ");
		sql.append("       AND    FK_ATIVIDADE  = :idAtividade) ");
		sql.append(" AND    EXISTS (  ");
		sql.append("       SELECT 1 ");
		sql.append("       FROM   ART_CONTRATO_COMPLEMENTO ACC ");
		sql.append("        WHERE  ACC.FK_CONTRATO = AC.CODIGO ");
		sql.append("        AND    FK_COMPLEMENTO  = :idComplemento) ");
		
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("registroEmpresa", vinculo.getRegistroEmpresa());
			query.setParameter("registroProfissional", idProfissional);
			query.setParameter("idAtividade", idAtividade); // -- 68 ou 69
			query.setParameter("idComplemento", idComplemento); // -- 189 ou 190

			Iterator<Object> it = query.getResultList().iterator();
			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {
					
					Object[] result = (Object[]) it.next();

					vinculo.setArtCargoFuncao(result[0] == null ? null :  result[0].toString());
				}
			}
			
		} catch (Throwable e) {
			httpGoApi.geraLog("EmpresaDao || setArtCargoFuncao ", StringUtil.convertObjectToJson(vinculo), e);
		}	
		
	}

	private void setCnpj(VinculoEmpresaResponsavelTecnicoDto vinculo) {
		
		Empresa empresa = new Empresa();
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT E FROM Empresa E  ");
		sql.append("	WHERE E.registro = :registro ");
		
		try {
			TypedQuery<Empresa> query = em.createQuery(sql.toString(), Empresa.class);
			query.setParameter("registro", vinculo.getRegistroEmpresa().toString());

			empresa = query.getSingleResult();
//			vinculo.setCnpjEmpresa(empresa.getCpfCnpj());
			if (empresa.getCpfCnpj() != null) {

				vinculo.setCnpjEmpresa(mascaraCnpj(empresa.getCpfCnpj()));
			}
		} catch (Throwable e) {
			httpGoApi.geraLog("EmpresaDao || buscaEmpresaByCNPJ ", StringUtil.convertObjectToJson(vinculo), e);
		}		
	}
	
	private String mascaraCnpj(String cnpj) {
		try {
			MaskFormatter mask = new MaskFormatter("###.###.###/####-##");
	        mask.setValueContainsLiteralCharacters(false);
//	        System.out.println("CNPJ : " + mask.valueToString(cnpj));
	        return mask.valueToString(cnpj);
			} catch (Throwable ex) {}
		return cnpj;
	}



	private void buscarResponsabilidadeTecnica(VinculoEmpresaResponsavelTecnicoDto vinculo, String codigoQuadroTecnico) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT crt.DATAINICIORT, ");
		sql.append("    DECODE(cr.descricao, ca.descricao, cr.descricao, cr.descricao || ' / ' || NVL(ca.descricao, '')) descricao_ramo, ");
		sql.append("    cqt.RT, ");
		sql.append("    cqt.codigo as id_quadro_tecnico, ");
		sql.append("    cqt.FK_CODIGO_EMPRESAS as registro_empresa ");
		sql.append("    FROM CAD_RESPONSAVEIS_TECNICOS crt ");
		sql.append("        LEFT JOIN CAD_QUADROS_TECNICOS cqt ON ( cqt.codigo = crt.FK_CODIGO_QUADROS_TECNICOS ) ");
		sql.append("        LEFT JOIN CAD_RAMOS_ATIVIDADE cra ON ( crt.FK_COD_RAMOS_ATIVIDADE = cra.CODIGO AND cra.DATACANCELAMENTO IS NULL) ");
		sql.append("        LEFT JOIN CAD_RAMOS cr ON ( cra.FK_CODIGO_RAMOS = cr.CODIGO ) ");
		sql.append("        LEFT JOIN CAD_ATIVIDADES ca ON ( cra.FK_CODIGO_ATIVIDADES = ca.CODIGO ) ");
		sql.append("    WHERE crt.FK_CODIGO_QUADROS_TECNICOS = :codigoQuadroTecnico ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("codigoQuadroTecnico", codigoQuadroTecnico);
			
			Iterator<?> it = query.getResultList().iterator();
			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {
					
					Object[] result = (Object[]) it.next();
					
					Date dataInicioRt = result[0] == null ? null :  (Date) result[0];
					vinculo.setDataInicioRt(result[0] == null ? "" : DateUtils.format(dataInicioRt, DateUtils.DD_MM_YYYY));
					vinculo.setRamoAtividade(result[1] == null ? "" : result[1].toString());
					
					boolean ehResponsavelTecnico = result[2] == null ? false : result[2].toString().equals("1") ? true : false;
					
					vinculo.setEhResponsavelTecnico(ehResponsavelTecnico);
					vinculo.setResponsavelTecnico(ehResponsavelTecnico ? "SIM" : "NÃO");
					
					vinculo.setNumeroArtCargoFuncao(result[3] == null ? "" : result[3].toString());
					
					
				}
				
			}
		} catch (Throwable e) {
			httpGoApi.geraLog("EmpresaDao || buscarResponsabilidadeTecnica",StringUtil.convertObjectToJson(codigoQuadroTecnico), e);
		}
		
	}

	public String formataDescricaoRamo(String descricaoRamoAtividade) {
		
		String descricao = descricaoRamoAtividade.trim();
		return String.valueOf(descricao.charAt(descricao.length()-1)).equals("/") ? descricaoRamoAtividade.substring (0, descricaoRamoAtividade.length() - 2) : descricaoRamoAtividade;
	}
	
	public List<QuadroTecnico> getQuadroTecnicoPorEmpresa(Long idEmpresa) {

		List<QuadroTecnico> quadroTecnico = new ArrayList<QuadroTecnico>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT q FROM QuadroTecnico q ");
		sql.append(" WHERE q.empresa.id = :idEmpresa ");
		sql.append(" ORDER BY q.dataInicio DESC ");

		try {
			TypedQuery<QuadroTecnico> query = em.createQuery(sql.toString(), QuadroTecnico.class);
			
			query.setParameter("idEmpresa", idEmpresa);
			
			quadroTecnico = query.getResultList();
			
		}catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("EmpresaDao || getQuadroTecnicoPorEmpresa", StringUtil.convertObjectToJson(idEmpresa), e);
		}
		
		return quadroTecnico;
	}
	
	public List<QuadroTecnico> getQuadroTecnicoPorEmpresaPaginado(PesquisaGenericDto pesquisa) {

		List<QuadroTecnico> quadroTecnico = new ArrayList<QuadroTecnico>();
		
		StringBuilder sql = new StringBuilder();
		pesquisa.setChecked(pesquisa.isChecked() == null ? false : pesquisa.isChecked());
		pesquisa.setSegundoChecked(pesquisa.isSegundoChecked() == null ? false : pesquisa.isSegundoChecked());
		if (pesquisa.isChecked()) {
			sql.append("SELECT distinct r.quadro FROM ResponsavelTecnico r    ");
			sql.append(" WHERE 1 = 1                                          ");
			if (pesquisa.getIdEmpresa() != null) {
				sql.append("   AND r.quadro.empresa.id = :idEmpresa           ");
			} else {
				sql.append("   AND r.quadro.profissional.id = :idProfissional ");
			}
			sql.append("  AND r.dataFim IS NULL                               ");
			if(pesquisa.isSegundoChecked()) {
				sql.append("  AND r.quadro.dataFim IS NOT NULL                ");
			} else {
				sql.append("  AND r.quadro.dataFim IS NULL                    ");
			}
			sql.append(" ORDER BY r.quadro.dataInicio DESC                    ");
		} else {
			sql.append("SELECT distinct q FROM QuadroTecnico q                ");
			sql.append(" WHERE 1 = 1                                          ");
			if (pesquisa.getIdEmpresa() != null) {
				sql.append("   AND q.empresa.id = :idEmpresa                  ");
			} else {
				sql.append("   AND q.profissional.id = :idProfissional        ");
			}
			if(pesquisa.isSegundoChecked()) {
				sql.append("  AND q.dataFim IS NOT NULL                       ");
			} else {
				sql.append("  AND q.dataFim IS NULL                           ");
			}
			sql.append(" ORDER BY q.dataInicio DESC                           ");
		}
		

		try {
			TypedQuery<QuadroTecnico> query = em.createQuery(sql.toString(), QuadroTecnico.class);
			Page page = new Page(pesquisa.getPage(), pesquisa.getRows());
			page.paginate(query);
			
			if (pesquisa.getIdEmpresa() != null) {
				query.setParameter("idEmpresa", pesquisa.getIdEmpresa());
			} else {
				query.setParameter("idProfissional", pesquisa.getIdPessoa());
			}
			
			quadroTecnico = query.getResultList();
			
			if (!quadroTecnico.isEmpty()) {
				for (QuadroTecnico qt : quadroTecnico) {
					qt.setEhVisaoProfissional(pesquisa.getIdPessoa() != null);
				}
			}
			
		}catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("EmpresaDao || getQuadroTecnicoPorEmpresaPaginado", StringUtil.convertObjectToJson(pesquisa), e);
		}
		
		return quadroTecnico;
	}

	public boolean vefificaProvisorio(Long registroEmpresa) {

		StringBuilder sql = new StringBuilder();
		boolean ehProvisorio = false;

		sql.append("SELECT CR.CODIGO, CR.DESCRICAO ");   
		sql.append("	FROM CAD_EMPRESAS E,CAD_TIPOS_CAT_REGI CR ");  
		sql.append("		WHERE E.FK_CODIGO_TIPOS_CAT_REGI = CR.CODIGO ");  
		sql.append("			AND E.FK_ID_PESSOAS_JURIDICAS = :registroEmpresa ");  

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("registroEmpresa", registroEmpresa);

			Object[] result = (Object[]) query.getSingleResult();
			if (result != null) {
//				Long codigoProvisorio = new Long(((BigDecimal) result[0]).setScale(0, BigDecimal.ROUND_UP).longValueExact());
				Long codigoProvisorio = Long.valueOf(result[0].toString()).longValue();
				ehProvisorio = codigoProvisorio == 2 ? true : false;
			}

		} catch (Throwable e) {
			httpGoApi.geraLog("EmpresaDao || vefificaProvisorio", StringUtil.convertObjectToJson(registroEmpresa), e);
		}

		return ehProvisorio;

	}
	
	public int getTotalDeRegistrosQuadroTecnico(PesquisaGenericDto pesquisa) {
		
		StringBuilder sql = new StringBuilder();
		pesquisa.setChecked(pesquisa.isChecked() == null ? false : pesquisa.isChecked());
		pesquisa.setSegundoChecked(pesquisa.isSegundoChecked() == null ? false : pesquisa.isSegundoChecked());
		if (pesquisa.isChecked()) {
			sql.append(" SELECT  COUNT(distinct R.FK_CODIGO_QUADROS_TECNICOS)                          ");
			sql.append("   FROM CAD_RESPONSAVEIS_TECNICOS R                                            ");
			sql.append("   JOIN CAD_QUADROS_TECNICOS Q ON (R.FK_CODIGO_QUADROS_TECNICOS = Q.CODIGO)    ");
			sql.append("  WHERE 1=1                                                                    ");
			if (pesquisa.getIdEmpresa() != null) {
				sql.append("    AND Q.FK_CODIGO_EMPRESAS = :idEmpresa                                  ");
			} else {
				sql.append("    AND Q.FK_CODIGO_PROFISSIONAIS = :idProfissional                        ");
			}
			if(pesquisa.isSegundoChecked()) {
				sql.append("  AND Q.DATAFIMQT IS NOT NULL                ");
			} else {
				sql.append("  AND Q.DATAFIMQT IS NULL                    ");
			}
			sql.append("  AND R.DATAFIMRT IS NULL                                                      ");
		} else {
			sql.append(" SELECT COUNT(C.CODIGO)                                 ");
			sql.append("   FROM CAD_QUADROS_TECNICOS C                          ");
			sql.append("  WHERE 1=1                                             ");
			if (pesquisa.getIdEmpresa() != null) {
				sql.append("    AND C.FK_CODIGO_EMPRESAS = :idEmpresa           ");
			} else {
				sql.append("    AND C.FK_CODIGO_PROFISSIONAIS = :idProfissional ");
			}
			if(pesquisa.isSegundoChecked()) {
				sql.append("  AND C.DATAFIMQT IS NOT NULL                ");
			} else {
				sql.append("  AND C.DATAFIMQT IS NULL                    ");
			}
		}
		
		try {
			
			Query query = em.createNativeQuery(sql.toString());
			if (pesquisa.getIdEmpresa() != null) {
				query.setParameter("idEmpresa", pesquisa.getIdEmpresa());
			} else {
				query.setParameter("idProfissional", pesquisa.getIdPessoa());
			}

			BigDecimal resultado = (BigDecimal) query.getSingleResult();
			return resultado.intValue();

		} catch (Throwable e) {
			httpGoApi.geraLog("EmpresaDao || getTotalDeRegistrosQuadroTecnico", StringUtil.convertObjectToJson(pesquisa), e);
		}

		return 0;
	}
	
	public QuadroTecnico getQuadroTecnicoPorId(Long id) {

		QuadroTecnico quadroTecnico = new QuadroTecnico();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT q FROM QuadroTecnico q ");
		sql.append(" WHERE q.id = :id ");

		try {
			TypedQuery<QuadroTecnico> query = em.createQuery(sql.toString(), QuadroTecnico.class);
			query.setParameter("id", id);
			
			quadroTecnico = query.getSingleResult();
			
		}catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("EmpresaDao || getQuadroTecnicoPorId", StringUtil.convertObjectToJson(id), e);
		}
		
		return quadroTecnico;
	}
	
	public List<DomainGenericDto> getEmpresasCurriculo(UserFrontDto dto) {
		
		List<DomainGenericDto> list = new ArrayList<DomainGenericDto>();
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("Select R.Descricao, E.Codigo                                                           ");
		sql.append("From Cad_Quadros_Tecnicos Q, Cad_Empresas E, Cad_Razoes_Sociais R                      ");
		sql.append("	Where Q.Fk_Codigo_Profissionais = :idPessoa                                        ");
		sql.append("    And Q.Datainicioqt In (Select Z.Datainicioqt                                       ");
		sql.append("    	From Cad_Quadros_Tecnicos Z                                                    ");
		sql.append("    	Where Z.Fk_Codigo_Profissionais = Q.Fk_Codigo_Profissionais                    ");
		sql.append("        And Z.Fk_Codigo_Empresas = Q.Fk_Codigo_Empresas)                               ");
		sql.append("        And E.Codigo = Q.Fk_Codigo_Empresas                                            ");
		sql.append("        And R.Fk_Codigo_Pjs = E.Codigo                                                 ");
		sql.append("        And R.Ativo = 1                                                                ");
		sql.append("    	Order By R.Descricao                                                           ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idPessoa", dto.getIdPessoa());
			
			Iterator<?> it = query.getResultList().iterator();
			
			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {
					
					Object[] result = (Object[]) it.next();
					DomainGenericDto empresa = new DomainGenericDto();
					empresa.setNome(result[0].toString());
					empresa.setIdString(result[1].toString());
					list.add(empresa);
				}
			}
		} catch (Throwable e) {
			httpGoApi.geraLog("EmpresaDao || getEmpresasCurriculo",StringUtil.convertObjectToJson(dto), e);
		}
		return list;
	}
	
}