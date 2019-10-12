package br.org.crea.commons.dao.cadastro.empresa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.cadastro.dtos.empresa.RamoEmpresaDto;
import br.org.crea.commons.models.corporativo.pessoa.Empresa;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.DateUtils;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class EmpresaEspecialidadeDao extends GenericDao<Empresa, Serializable> {

	@Inject HttpClientGoApi httpGoApi;
	
	public EmpresaEspecialidadeDao() {
		super(Empresa.class);
	}

	public String getTituloEmpresa(Long idEmpresa) {

		StringBuilder ramos = new StringBuilder();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DECODE(CR.DESCRICAO , CA.DESCRICAO , CR.DESCRICAO , CR.DESCRICAO || ' ' || NVL(CA.DESCRICAO, '' )) DESCRICAO ");
		sql.append("FROM CAD_RAMOS_ATIVIDADE CRA , CAD_RAMOS CR , CAD_ATIVIDADES CA  ");
		sql.append("WHERE CRA.FK_CODIGO_EMPRESAS = :idEmpresa ");
		sql.append("  AND CRA.DATACANCELAMENTO IS NULL ");
		sql.append("  AND CR.CODIGO = CRA.FK_CODIGO_RAMOS ");
		sql.append("  AND CA.FK_RAMO = CRA.FK_CODIGO_RAMOS ");
		sql.append("  AND CA.CODIGO = CRA.FK_CODIGO_ATIVIDADES AND EXISTS  ");
		sql.append("    (SELECT CRT.FK_COD_RAMOS_ATIVIDADE FROM CAD_RESPONSAVEIS_TECNICOS CRT ,  ");
		sql.append("      (SELECT * FROM CAD_QUADROS_TECNICOS WHERE FK_CODIGO_EMPRESAS = :idEmpresa AND DATAFIMQT IS NULL )X ");
		sql.append("    WHERE X.CODIGO = CRT.FK_CODIGO_QUADROS_TECNICOS  AND CRT.FK_COD_RAMOS_ATIVIDADE = CRA.CODIGO ");
		sql.append("    GROUP BY CRT.FK_COD_RAMOS_ATIVIDADE) ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idEmpresa", idEmpresa);
			
			for(Object rl : query.getResultList()){
				ramos.append(rl.toString().substring(0, rl.toString().length() - 1) + ", ");
			}

		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("Empresa Especialidade Dao || Get Titulos",StringUtil.convertObjectToJson(idEmpresa), e);
		}

		if (ramos.length() > 0 ) {
			return ramos.substring (0, ramos.length() - 2);	
		}else{
			return ramos.toString();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<RamoEmpresaDto> getRamosSemResponsavelTecnicoPor(Long idEmpresa) {
		List<RamoEmpresaDto> listRamos = new ArrayList<RamoEmpresaDto>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DECODE(CR.descricao, CA.descricao, CR.descricao, CR.descricao || ' / ' || NVL(CA.descricao, '')) AS descricao, ");
		sql.append("			  CRA.dataaprovacao, CRA.datacancelamento ");
		sql.append("	FROM  	  cad_ramos_atividade CRA, ");
		sql.append("		  	  cad_ramos 		  CR,  ");
		sql.append("		  	  cad_atividades      CA   ");
		sql.append("	WHERE 	  CRA.fk_codigo_empresas = :idEmpresa   ");
//		sql.append("    AND   	  CRA.datacancelamento is null 		    ");
		sql.append("    AND   	  CR.codigo  = CRA.fk_codigo_ramos 	    ");
		sql.append("    AND   	  CA.fk_ramo = CRA.fk_codigo_ramos      ");
		sql.append("    AND   	  CA.codigo  = CRA.fk_codigo_atividades ");
		sql.append("	AND   	  CRA.codigo NOT IN 				    ");
		sql.append("         	  (SELECT  RT.fk_cod_ramos_atividade    ");
		sql.append("			 	FROM  cad_quadros_tecnicos 		QT, ");
		sql.append("					  cad_responsaveis_tecnicos RT 	");
		sql.append("				WHERE QT.fk_codigo_empresas         = CRA.fk_codigo_empresas ");
		sql.append("				AND   RT.fk_codigo_quadros_tecnicos = QT.codigo     		 ");
		sql.append("				AND   QT.datafimqt 					is null         		 ");
		sql.append("				AND   RT.datafimrt 					is null 	    		 ");
		sql.append("			  ) 															 ");
		sql.append("	ORDER BY CRA.fk_codigo_atividades 										 ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idEmpresa", idEmpresa);
			
			List<Object[]> listObject = query.getResultList();
			listRamos = toListRamosEmpresaDto(listObject);

		} catch (Throwable e) {
			httpGoApi.geraLog("Empresa Especialidade Dao || getRamosSemResponsavelTecnicoPor",StringUtil.convertObjectToJson(idEmpresa), e);
		}
		return listRamos;
	}
	
	@SuppressWarnings("unchecked")
	public List<RamoEmpresaDto> getRamosComResponsavelTecnicoPor(Long idEmpresa) {
		List<RamoEmpresaDto> listRamos = new ArrayList<RamoEmpresaDto>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DECODE(CR.descricao, CA.descricao, CR.descricao, CR.descricao || ' / ' || NVL(CA.descricao, '')) AS descricao, ");
		sql.append("			  CRA.dataaprovacao, CRA.datacancelamento ");
		sql.append("	FROM  	  cad_ramos_atividade CRA, ");
		sql.append("		  	  cad_ramos 		  CR,  ");
		sql.append("		  	  cad_atividades      CA   ");
		sql.append("	WHERE 	  CRA.fk_codigo_empresas = :idEmpresa   ");
//		sql.append("    AND   	  CRA.datacancelamento is null 		    ");
		sql.append("    AND   	  CR.codigo  = CRA.fk_codigo_ramos 	    ");
		sql.append("    AND   	  CA.fk_ramo = CRA.fk_codigo_ramos      ");
		sql.append("    AND   	  CA.codigo  = CRA.fk_codigo_atividades ");
		sql.append("	AND   	  CRA.codigo IN 				        ");
		sql.append("         	  (SELECT  RT.fk_cod_ramos_atividade    ");
		sql.append("			 	FROM  cad_quadros_tecnicos 		QT, ");
		sql.append("					  cad_responsaveis_tecnicos RT 	");
		sql.append("				WHERE QT.fk_codigo_empresas         = CRA.fk_codigo_empresas ");
		sql.append("				AND   RT.fk_codigo_quadros_tecnicos = QT.codigo     		 ");
		sql.append("				AND   QT.datafimqt 					is null         		 ");
		sql.append("				AND   RT.datafimrt 					is null 	    		 ");
		sql.append("			  ) 															 ");
		sql.append("	ORDER BY CRA.fk_codigo_atividades 										 ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idEmpresa", idEmpresa);
			
			List<Object[]> listObject = query.getResultList();
			listRamos = toListRamosEmpresaDto(listObject);

		} catch (Throwable e) {
			httpGoApi.geraLog("Empresa Especialidade Dao || getRamosComResponsavelTecnicoPor",StringUtil.convertObjectToJson(idEmpresa), e);
		}
		return listRamos;
	}
	
	
	public List<RamoEmpresaDto> toListRamosEmpresaDto(List<Object[]> listObject) {
		List<RamoEmpresaDto> list = new ArrayList<RamoEmpresaDto>();
		
		for (Object[] object : listObject) {

			RamoEmpresaDto ramo = new RamoEmpresaDto();
			ramo.setRamoAtividade(formataDescricaoRamo((String) object[0]));
			ramo.setDataAprovacao((Date)object[1] != null ? DateUtils.format((Date) object[1], DateUtils.DD_MM_YYYY) : "-");
			list.add(ramo);
		}	
		return list;
	}
	
	public String formataDescricaoRamo(String descricaoRamoAtividade) {
		
		String descricao = descricaoRamoAtividade.trim();
		return String.valueOf(descricao.charAt(descricao.length()-1)).equals("/") ? descricaoRamoAtividade.substring (0, descricaoRamoAtividade.length() - 2) : descricaoRamoAtividade;
		
	}
	
}
