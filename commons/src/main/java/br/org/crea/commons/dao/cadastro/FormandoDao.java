package br.org.crea.commons.dao.cadastro;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.dao.corporativo.Page;
import br.org.crea.commons.models.cadastro.dtos.pessoa.FormandoDto;
import br.org.crea.commons.models.commons.dtos.DomainGenericDto;
import br.org.crea.commons.models.corporativo.pessoa.Formando;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.DateUtils;
import br.org.crea.commons.util.StringUtil;
@Stateless
public class FormandoDao extends GenericDao<Formando, Serializable> {
	
	@Inject HttpClientGoApi httpGoApi;
	
	public FormandoDao() {
		super(Formando.class);
	}
	
	public BigDecimal getByCPFAndCurso(String cpf, Long idCurso) {
		
		try{			
			Query query = em.createNativeQuery("select c.CODIGO from Cad_Pessoas_Fisicas f, Cad_Formandos c " + 
					"				where F.Cpf = :cpf" + 
					"				and C.Fk_Id_Pessoas_Fisicas = f.codigo" + 
					"				and C.Fk_Codigo_Cursos = :idCurso ");
			query.setParameter("cpf", cpf);
			query.setParameter("idCurso", idCurso);
			return (BigDecimal) query.getSingleResult();
		}catch (Throwable e) {
			httpGoApi.geraLog("FormandoDao || getByCPFAndCurso", StringUtil.convertObjectToJson(cpf), e);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Formando> getALlByFormandoDtoForGrid(FormandoDto formandoDTO){
		List<Formando> listFormandos = new ArrayList<Formando>();
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT f FROM Formando f ");
			sql.append("WHERE f.instituicaoEnsino.id = :idInstituicao and f.curso.id = :idCurso and f.curso.campi.id = :idCampus ");
			sql.append("and trunc(f.dataFormatura) = trunc(:dataFormatura)");
					
			Query query = em.createQuery(sql.toString());
			query.setParameter("idInstituicao", formandoDTO.getInstituicao().getId());
			query.setParameter("idCurso", formandoDTO.getCurso().getId());
			query.setParameter("idCampus", formandoDTO.getCampus().getId());
			query.setParameter("dataFormatura", formandoDTO.getDataFormatura());
			
			listFormandos = query.getResultList();
			
		}catch (Throwable e) {
			//httpGoApi.geraLog("FormandoDao || getAllByInstituicaoForGrid", StringUtil.convertObjectToJson(cadastroFormandoDto), e);
			/*Comentado por que no front-end utilizo o watch no vue que faz a requisiçao por componente */
		}
		return listFormandos;
	}
	
	public void atualizaNumeroDeProtocolo(FormandoDto dto) {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE  CAD_FORMANDOS F ");
		sql.append("     SET F.NUMEROPROCESSO = :protocolo ");
		sql.append("	 WHERE F.CODIGO = :id ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("protocolo", dto.getProtocolo());
			query.setParameter("id", dto.getId());
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("FormandoDao || atualizaNumeroDeProtocolo", StringUtil.convertObjectToJson(dto), e);
		}
	}
	
	public FormandoDto consultar(FormandoDto formandoDto) {
		StringBuilder sql = new StringBuilder();
		sql.append(" select ");
		sql.append("   CPF.CPF, ");
		sql.append("   CPF.NOME, ");
		sql.append("   CF.NUMEROPROCESSO, ");
		sql.append("   CF.DATAFORMATURA ");
		sql.append(" from CAD_FORMANDOS CF ");
		sql.append(" inner join CAD_PESSOAS_FISICAS CPF on CPF.CODIGO = CF.FK_ID_PESSOAS_FISICAS ");
		sql.append(" inner join CAD_INSTITUICOES_ENSINO CIE on CF.FK_CODIGO_INSTITUICOES_ENSINO = CIE.CODIGO ");
		sql.append(" inner join CAD_RAZOES_SOCIAIS CRS on CIE.CODIGO = CRS.FK_CODIGO_PJS AND CRS.ATIVO = 1 ");
		sql.append(" inner join CAD_CURSOS CC on CC.CODIGO = CF.FK_CODIGO_CURSOS  ");
		sql.append(" inner join CAD_CAMPI CCI on CCI.CODIGO = CC.FK_CODIGO_CAMPI ");
		sql.append(" where  ");
		sql.append("   CIE.CODIGO = :idInstituicao  ");
		
		if(formandoDto.temCursoSelecionado()) sql.append(" and CC.CODIGO = :idCurso  ");
		if(formandoDto.temCampiSelecionado()) sql.append(" and CCI.CODIGO = :idCampi  ");
		if(formandoDto.temDataFormatura()) sql.append("    and trunc(CF.DATAFORMATURA) = :dataFormatura  ");
		if(formandoDto.temProtocolo()) sql.append("        and CF.NUMEROPROCESSO = :protocolo  ");
		
		sql.append(" order by CF.DATAFORMATURA desc ");
		
		Query query = em.createNativeQuery(sql.toString());
		query.setParameter("idInstituicao", formandoDto.getInstituicao().getId());
		
		if(formandoDto.temCursoSelecionado()) query.setParameter("idCurso", formandoDto.getCurso().getId());
		if(formandoDto.temCampiSelecionado()) query.setParameter("idCampi", formandoDto.getCampus().getId());
		if(formandoDto.temDataFormatura()) query.setParameter("dataFormatura", formandoDto.getDataFormatura());
		if(formandoDto.temProtocolo()) query.setParameter("protocolo", formandoDto.getProtocolo());
		
		Page page = new Page(formandoDto.getPage(), formandoDto.getRows());
		page.paginate(query);
		
		Iterator<?> it = query.getResultList().iterator();
		
		List<FormandoDto> formandoDtos = new ArrayList<FormandoDto>();
		
		if (query.getResultList().size() > 0) {	
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", new Locale("pt", "BR"));
			
			while (it.hasNext()) {
				Object[] result = (Object[]) it.next();
				FormandoDto formandoDtoRetornado = new FormandoDto();
				formandoDtoRetornado.setCpf(result[0] == null ? "" : result[0].toString());
				formandoDtoRetornado.setNome(result[1] == null ? "" : result[1].toString());
				formandoDtoRetornado.setProtocolo(result[2] == null ? "" : result[2].toString());
				try {
					formandoDtoRetornado.setDataFormatura(result[3] == null ? null : format.parse(result[3].toString()));
					formandoDtoRetornado.setDataFormaturaFormatada(result[3] == null ? null : DateUtils.format(formandoDtoRetornado.getDataFormatura(), DateUtils.DD_MM_YYYY));
				} catch (ParseException e) {
					formandoDtoRetornado.setDataFormatura(null);
				}
				formandoDtos.add(formandoDtoRetornado);
			}
		}
		
		formandoDto.setFormandos(formandoDtos);
		return formandoDto;
	}
	
	public int getTotalConsulta(FormandoDto formandoDto) {
		StringBuilder sql = new StringBuilder();
		sql.append(" select ");
		sql.append("   CPF.CPF ");
		sql.append(" from CAD_FORMANDOS CF ");
		sql.append(" inner join CAD_PESSOAS_FISICAS CPF on CPF.CODIGO = CF.FK_ID_PESSOAS_FISICAS ");
		sql.append(" inner join CAD_INSTITUICOES_ENSINO CIE on CF.FK_CODIGO_INSTITUICOES_ENSINO = CIE.CODIGO ");
		sql.append(" inner join CAD_RAZOES_SOCIAIS CRS on CIE.CODIGO = CRS.FK_CODIGO_PJS AND CRS.ATIVO = 1 ");
		sql.append(" inner join CAD_CURSOS CC on CC.CODIGO = CF.FK_CODIGO_CURSOS  ");
		sql.append(" inner join CAD_CAMPI CCI on CCI.CODIGO = CC.FK_CODIGO_CAMPI ");
		sql.append(" where  ");
		sql.append("   CIE.CODIGO = :idInstituicao  ");
		
		if(formandoDto.temCursoSelecionado()) sql.append(" and CC.CODIGO = :idCurso  ");
		if(formandoDto.temCampiSelecionado()) sql.append(" and CCI.CODIGO = :idCampi  ");
		if(formandoDto.temDataFormatura()) sql.append("    and trunc(CF.DATAFORMATURA) = :dataFormatura  ");
		if(formandoDto.temProtocolo()) sql.append("        and CF.NUMEROPROCESSO = :protocolo  ");
		
		sql.append(" order by CF.DATAFORMATURA desc ");
		
		Query query = em.createNativeQuery(sql.toString());
		query.setParameter("idInstituicao", formandoDto.getInstituicao().getId());
		
		if(formandoDto.temCursoSelecionado()) query.setParameter("idCurso", formandoDto.getCurso().getId());
		if(formandoDto.temCampiSelecionado()) query.setParameter("idCampi", formandoDto.getCampus().getId());
		if(formandoDto.temDataFormatura()) query.setParameter("dataFormatura", formandoDto.getDataFormatura());
		if(formandoDto.temProtocolo()) query.setParameter("protocolo", formandoDto.getProtocolo());
		
		return query.getResultList().size();
		
	}
	
	
	public FormandoDto consultarParaPlanilha(FormandoDto formandoDto) {
		StringBuilder sql = new StringBuilder();
		sql.append(" select ");
		sql.append("   CPF.CPF, ");
		sql.append("   CPF.NOME, ");
		sql.append("   CF.NUMEROPROCESSO, ");
		sql.append("   CF.DATAFORMATURA, ");
		sql.append("   CC.CODIGO AS CODIGOCURSO, ");
		sql.append("   CC.NOME AS NOMECURSO, ");
		sql.append("   CCI.CODIGO, ");
		sql.append("   CCI.NOME AS NOMECAPI");
		sql.append(" from CAD_FORMANDOS CF ");
		sql.append(" inner join CAD_PESSOAS_FISICAS CPF on CPF.CODIGO = CF.FK_ID_PESSOAS_FISICAS ");
		sql.append(" inner join CAD_INSTITUICOES_ENSINO CIE on CF.FK_CODIGO_INSTITUICOES_ENSINO = CIE.CODIGO ");
		sql.append(" inner join CAD_RAZOES_SOCIAIS CRS on CIE.CODIGO = CRS.FK_CODIGO_PJS AND CRS.ATIVO = 1 ");
		sql.append(" inner join CAD_CURSOS CC on CC.CODIGO = CF.FK_CODIGO_CURSOS  ");
		sql.append(" inner join CAD_CAMPI CCI on CCI.CODIGO = CC.FK_CODIGO_CAMPI ");
		sql.append(" where  ");
		sql.append("   CIE.CODIGO = :idInstituicao  ");
		
		if(formandoDto.temCursoSelecionado()) sql.append(" and CC.CODIGO = :idCurso  ");
		if(formandoDto.temCampiSelecionado()) sql.append(" and CCI.CODIGO = :idCampi  ");
		if(formandoDto.temDataFormatura()) sql.append("    and trunc(CF.DATAFORMATURA) = :dataFormatura  ");
		if(formandoDto.temProtocolo()) sql.append("        and CF.NUMEROPROCESSO = :protocolo  ");
		
		sql.append(" order by CF.DATAFORMATURA desc ");
		
		Query query = em.createNativeQuery(sql.toString());
		query.setParameter("idInstituicao", formandoDto.getInstituicao().getId());
		
		if(formandoDto.temCursoSelecionado()) query.setParameter("idCurso", formandoDto.getCurso().getId());
		if(formandoDto.temCampiSelecionado()) query.setParameter("idCampi", formandoDto.getCampus().getId());
		if(formandoDto.temDataFormatura()) query.setParameter("dataFormatura", formandoDto.getDataFormatura());
		if(formandoDto.temProtocolo()) query.setParameter("protocolo", formandoDto.getProtocolo());
		
		Iterator<?> it = query.getResultList().iterator();
		
		List<FormandoDto> formandoDtos = new ArrayList<FormandoDto>();
		
		if (query.getResultList().size() > 0) {	
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", new Locale("pt", "BR"));
			
			while (it.hasNext()) {
				
				Object[] result = (Object[]) it.next();
				
				FormandoDto formandoDtoRetornado = new FormandoDto();
				formandoDtoRetornado.setCpf(result[0] == null ? "" : result[0].toString());
				formandoDtoRetornado.setNome(result[1] == null ? "" : result[1].toString());
				formandoDtoRetornado.setProtocolo(result[2] == null ? "" : result[2].toString());
				
				try {
					formandoDtoRetornado.setDataFormatura(result[3] == null ? null : format.parse(result[3].toString()));
					formandoDtoRetornado.setDataFormaturaFormatada(result[3] == null ? null : DateUtils.format(formandoDtoRetornado.getDataFormatura(), DateUtils.DD_MM_YYYY));
				} catch (ParseException e) {
					formandoDtoRetornado.setDataFormatura(null);
				}
				
				DomainGenericDto curso = new DomainGenericDto();
				curso.setCodigo(Long.valueOf(result[4].toString()));
				curso.setNome(result[5].toString());
				
				formandoDtoRetornado.setCurso(curso);
				
				DomainGenericDto campi = new DomainGenericDto();
				campi.setCodigo(Long.valueOf(result[6].toString()));
				campi.setNome(result[7].toString());
				
				formandoDtoRetornado.setCampus(campi);
				
				formandoDtos.add(formandoDtoRetornado);
			}
		}
		
		formandoDto.setFormandos(formandoDtos);
		return formandoDto;
	}
	
	
}
