package br.org.crea.commons.dao.cadastro;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.corporativo.Page;
import br.org.crea.commons.models.cadastro.Certidao;
import br.org.crea.commons.models.commons.dtos.PesquisaGenericDto;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class CertidaoDao {
	
	@Inject HttpClientGoApi httpGoApi;

	@PersistenceContext(unitName = "dscrea")
	protected EntityManager em;
	
public List<Certidao> getListCertidaoByIdPessoa(Long idPessoa) {
		
		List<Certidao> listCertidao = new ArrayList<Certidao>();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT C ");
		sql.append(" FROM Certidao C ");
		sql.append(" WHERE C.pessoa.id = :idPessoa ");

		try {
			TypedQuery<Certidao> query = em.createQuery(sql.toString(), Certidao.class);
			query.setParameter("idPessoa", idPessoa);
			
			listCertidao = query.getResultList();			
		} catch (Throwable e) {
			httpGoApi.geraLog("Certidao Dao || getCertidaoByIdPessoa ", StringUtil.convertObjectToJson(idPessoa), e);
		}
		return listCertidao;
	
	}

public List<Certidao> getListCertidaoByIdPessoaPaginada(PesquisaGenericDto pesquisa) {
	
	List<Certidao> listCertidao = new ArrayList<Certidao>();
	StringBuilder sql = new StringBuilder();
	sql.append(" SELECT C ");
	sql.append(" FROM Certidao C ");
	sql.append(" WHERE C.pessoa.id = :idPessoa ");

	try {
		TypedQuery<Certidao> query = em.createQuery(sql.toString(), Certidao.class);
		query.setParameter("idPessoa", pesquisa.getIdPessoa());
		
		Page page = new Page(pesquisa.getPage(), pesquisa.getRows());
		page.paginate(query);
		
		listCertidao = query.getResultList();			
	} catch (Throwable e) {
		httpGoApi.geraLog("Certidao Dao || getCertidaoByIdPessoa ", StringUtil.convertObjectToJson(pesquisa), e);
	}
	return listCertidao;

}


public int getTotalDeRegistrosdaPesquisa(PesquisaGenericDto pesquisa) {
	StringBuilder sql = new StringBuilder();
	sql.append(" SELECT COUNT(*) ");
	sql.append("   FROM CAD_CERTIDAO C ");
	sql.append("  WHERE C.FK_CODIGO_PESSOA = :idPessoa ");

	try {
		Query query = em.createNativeQuery(sql.toString());
		query.setParameter("idPessoa", pesquisa.getIdPessoa());

		int quantidadeCertidoes = Integer.parseInt(query.getSingleResult().toString());
		return quantidadeCertidoes;
		

	} catch (Throwable e) {
		httpGoApi.geraLog("CertidaoDao || getTotalDeRegistrosdaPesquisa", StringUtil.convertObjectToJson(pesquisa),
				e);
	}

	return 0;
}

public List<String> validaPessoaParaCertidaoRegistro(UserFrontDto dto) {
	
		List<String> list = new ArrayList<String>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("Select N.Descricao As Descricao, D.Identificador_Divida  || Decode(D.Parcela, 0, '', '(Parcela ' || D.Parcela || ')') As Identificador, S.Descricao As Situacao");
		sql.append("	From Fin_Divida D, Fin_Natureza N, Fin_Status_Divida S                                                                                   ");
		sql.append("	Where D.Fk_Codigo_Pessoa = :idPessoa                                                                                                     ");
		sql.append("	And D.Data_Vencimento < Sysdate                                                                                                          ");
		sql.append("	And N.Codigo = D.Fk_Codigo_Natureza                                                                                                      ");
		sql.append("	And (N.Certidao_Registro = 1 Or D.Fk_Codigo_Status_Divida = 3)                                                                           ");
		sql.append("	And S.Codigo = D.Fk_Codigo_Status_Divida                                                                                                 ");
		sql.append("	And S.Quitado = 0                                                                                                                        ");
		sql.append("	And S.Certidao_Registro = 1                                                                                                              ");
		sql.append("	Union                                                                                                                                    ");
		sql.append("	Select 'Anuidade' As Descricao, To_Char(Sysdate, 'YYYY') As Identificador, 'Não gerada' As Situacao From Dual                            ");
		sql.append("	Where Not Exists (Select F.Codigo From Fin_Divida F Where F.Fk_Codigo_Pessoa = :idPessoa And F.Fk_Codigo_Natureza = 6                    ");
		sql.append("	And F.Identificador_Divida = To_Char(Sysdate, 'YYYY'))                                                                                   ");
		sql.append("	Union                                                                                                                                    ");
		sql.append("	Select 'Validade de título/registro' as Descricao, To_Char(Ch.Datafinal, 'DD/MM/YYYY') As Identificador, Tc.Desctipocarteira As Situacao ");
		sql.append("	From  Cad_Carteiras Cc, Cad_Tipos_Carteiras Tc, Cad_Historicos Ch, Cad_Eventos Ce                                                        ");
		sql.append("	Where Cc.Fk_Codigo_Profissionais = :idPessoa                                                                                             ");
		sql.append("	And Cc.Cancelada <> 1                                                                                                                    ");
		sql.append("	And Tc.Codigo = Cc.Fk_Codigo_Tipos_Carteiras                                                                                             ");
		sql.append("	And Tc.Descricaoconfea != 'Definitiva'                                                                                                   ");
		sql.append("	And Ch.Fk_Codigo_Pessoas = Fk_Codigo_Profissionais                                                                                       ");
		sql.append("	And Ce.Codigo = Ch.Fk_Codigo_Eventos                                                                                                     ");
		sql.append("	And Ce.Is_Validade = 1                                                                                                                   ");
		sql.append("	And Ch.Datainicio In (Select Max(Datainicio)                                                                                             ");
		sql.append("		From Cad_Historicos H, Cad_Eventos E                                                                                                 ");
		sql.append("		where H.Fk_Codigo_Pessoas = Cc.Fk_Codigo_Profissionais                                                                               ");
		sql.append("		And E.Codigo = H.Fk_Codigo_Eventos                                                                                                   ");
		sql.append("		And E.Is_Validade = 1)                                                                                                               ");
		sql.append("	Union                                                                                                                                    ");
		sql.append("	Select Ce.Descricao As Descricao, To_Char(Ch.Datainicio, 'DD/MM/YYYY')  As Identificador, '' As Situacao                                 ");
		sql.append("	From Cad_Historicos Ch, Cad_Eventos Ce                                                                                                   ");
		sql.append("	Where Ch.Fk_Codigo_Pessoas = :idPessoa                                                                                                   ");
		sql.append("	And Ch.Datafinal Is Null                                                                                                                 ");
		sql.append("	And Ce.Codigo = Ch.Fk_Codigo_Eventos                                                                                                     ");
		sql.append("	And Ce.Codigo < 20                                                                                                                       ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idPessoa", dto.getIdPessoa());		
			
			Iterator<?> it = query.getResultList().iterator();

			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {
					
					Object[] result = (Object[]) it.next();
					StringBuilder sb = new StringBuilder();
					sb.append(result[2].toString().toUpperCase());
					sb.append(": ");
					sb.append(result[0].toString().toUpperCase());
					sb.append(" - ");
					sb.append(result[1].toString().toUpperCase());
					list.add(sb.toString());
				}
			}

		} catch (Throwable e) {
			httpGoApi.geraLog("CertidaoDao || validaPessoaParaCertidaoRegistro", StringUtil.convertObjectToJson(dto), e);
		}
	return list;
}

public List<String> validaPessoaParaCertidaoAtribuicao(UserFrontDto dto) {
	
	List<String> list = new ArrayList<String>();
	
	StringBuilder sql = new StringBuilder();
	sql.append(" Select 'Titulo sem legislação cadastrada' As Descricao, Cep.Descricao As Identificador, To_Char(Cep.Codigo) As Situacao       ");
	sql.append("	From  Cad_Profxespec Cpe, Cad_Especxatrib Cea, Cad_Atribuicoesxartigos Caa, Cad_Atribuicoes Cat, Cad_Especialidades Cep    ");
	sql.append("	Where Cpe.Fk_Codigo_Profissionais = :idPessoa                                                                              ");
	sql.append("	And Cpe.Datacancelamento Is Null                                                                                           ");
	sql.append("	And Cea.Fk_Codigo_Especialidades = Cpe.Fk_Codigo_Especialidades                                                            ");
	sql.append("	And Caa.Fk_Codigo_Atribuicoes = Cea.Fk_Codigo_Atribuicoes                                                                  ");
	sql.append("	And Cat.Codigo = Cea.Fk_Codigo_Atribuicoes                                                                                 ");
	sql.append("	And (Cat.Fk_Codigo_Dispositivos <> 3 Or Cat.Numero <> 1010)                                                                ");
	sql.append("	And Not Exists (Select Texto From Cad_Legislacao                                                                           ");
	sql.append("	Where Fk_Codigo_Dispositivos = Caa.Fk_Codigo_Dispositivos                                                                  ");
	sql.append("	And Numero_Dispositivo = Caa.Numero_Dispositivo                                                                            ");
	sql.append("	And Artigo = Caa.Numero_Artigo)                                                                                            ");
	sql.append("	And Cep.Codigo = Cpe.Fk_Codigo_Especialidades                                                                              ");
	sql.append("	Union                                                                                                                      ");
	sql.append("	Select Ce.Descricao As Descricao, To_Char(Ch.Datainicio, 'DD/MM/YYYY')  As Identificador, '' As Situacao                   ");
	sql.append("	From Cad_Historicos Ch, Cad_Eventos Ce                                                                                     ");
	sql.append("	Where Ch.Fk_Codigo_Pessoas = :idPessoa                                                                                     ");
	sql.append("	And Ch.Datafinal Is Null                                                                                                   ");
	sql.append("	And Ce.Codigo = Ch.Fk_Codigo_Eventos                                                                                       ");
	sql.append("	And Ce.Codigo < 20                                                                                                         ");

	try {
		Query query = em.createNativeQuery(sql.toString());
		query.setParameter("idPessoa", dto.getIdPessoa());		
		
		Iterator<?> it = query.getResultList().iterator();

		if (query.getResultList().size() > 0) {
			while (it.hasNext()) {
				
				Object[] result = (Object[]) it.next();
				StringBuilder sb = new StringBuilder();
				sb.append(result[2].toString().toUpperCase());
				sb.append(": ");
				sb.append(result[0].toString().toUpperCase());
				sb.append(" - ");
				sb.append(result[1].toString().toUpperCase());
				list.add(sb.toString());
			}
		}

	} catch (Throwable e) {
		httpGoApi.geraLog("CertidaoDao || validaPessoaParaCertidaoRegistro", StringUtil.convertObjectToJson(dto), e);
	}
return list;
}

public List<String> validaPessoaJuridicaParaCertidaoRegistro(UserFrontDto dto) {
	
	List<String> list = new ArrayList<String>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" Select N.Descricao As Descricao, D.Identificador_Divida  || Decode(D.Parcela, 0, '', '(Parcela ' || D.Parcela || ')') As Identificador, S.Descricao As Situacao  ");
		sql.append(" From Fin_Divida D, Fin_Natureza N, Fin_Status_Divida S																								");
		sql.append(" 	Where D.Fk_Codigo_Pessoa = :idPessoa																											");
		sql.append(" 	And D.Data_Vencimento < Sysdate																													");
		sql.append(" 	And N.Codigo = D.Fk_Codigo_Natureza																												");
		sql.append(" 	And (N.Certidao_Registro = 1 Or D.Fk_Codigo_Status_Divida = 3)																					");
		sql.append(" 	And S.Codigo = D.Fk_Codigo_Status_Divida																										");
		sql.append(" 	And S.Quitado = 0																																");
		sql.append(" 	And S.Certidao_Registro = 1																														");
		sql.append(" Union																																				");
		sql.append(" Select 'Anuidade' As Descricao, To_Char(Sysdate, 'YYYY') As Identificador, 'Não gerada' As Situacao From Dual										");
		sql.append(" 	Where Not Exists (Select F.Codigo From Fin_Divida F																								");
		sql.append(" 	Where F.Fk_Codigo_Pessoa = :idPessoa 																											");
		sql.append(" 	And F.Fk_Codigo_Natureza = 6																													");
		sql.append(" 	And F.Identificador_Divida = To_Char(Sysdate, 'YYYY'))																							");
		sql.append(" And Exists (Select E.Codigo From Cad_Empresas E, Cad_Tipos_Empresas T																				");
		sql.append(" 	Where E.Codigo = :idPessoa																														");
		sql.append(" 	And T.Codigo = E.Fk_Codigo_Tipos_Empresas																										");
		sql.append(" 	And T.Paga_Anuidade = 0)																														");
		sql.append(" Union																																				");
		sql.append(" Select 'Validade do registro da empresa' As Descricao, To_Char(H.Datafinal, 'DD/MM/YYYY') AS Identificador, '-' As Situacao						");
		sql.append(" From Cad_Empresas E, Cad_Historicos H																												");
		sql.append(" Where E.Codigo = :idPessoa 																														");
		sql.append(" And E.Fk_Codigo_Tipos_Cat_Regi = 2																													");
		sql.append(" And H.Fk_Codigo_Pessoas = E.Codigo																													");
		sql.append(" And H.Fk_Codigo_Eventos = 150																														");	
		sql.append(" And H.Datafinal In (Select Max(X.Datafinal) From Cad_Historicos X Where H.Fk_Codigo_Pessoas = E.Codigo And Fk_Codigo_Eventos = 150)				");			
		sql.append(" Union																																				");
		sql.append(" Select N.Descricao As Descricao, D.Identificador_Divida  || Decode(D.Parcela, 0, '', '(Parcela ' || D.Parcela || ')') As Identificador, F.Nome As Situacao ");
		sql.append(" From Cad_Quadros_Tecnicos Q, Cad_Pessoas_Fisicas F, Fin_Divida D, Fin_Natureza N, Fin_Status_Divida S												");
		sql.append(" Where Q.Fk_Codigo_Empresas = :idPessoa																												");
		sql.append(" And Q.Datafimqt Is Null																															");
		sql.append(" And Exists (Select R.Codigo From Cad_Responsaveis_Tecnicos R Where R.Fk_Codigo_Quadros_Tecnicos = Q.Codigo And R.Datafimrt Is Null)				");
		sql.append(" And F.Codigo = Q.Fk_Codigo_Profissionais																											");
		sql.append(" And D.Fk_Codigo_Pessoa = F.Codigo																													");
		sql.append(" And D.Data_Vencimento < Sysdate																													");
		sql.append(" And N.Codigo = D.Fk_Codigo_Natureza																												");
		sql.append(" And (N.Certidao_Registro = 1 Or D.Fk_Codigo_Status_Divida = 3)																						");
		sql.append(" And S.Codigo = D.Fk_Codigo_Status_Divida																											");
		sql.append(" And S.Quitado = 0																																	");
		sql.append(" And S.Certidao_Registro = 1																														");
		sql.append(" Union																																				");
		sql.append(" Select 'Validade do registro' As Descricao, To_Char(Ch.Datafinal,'DD/MM/YYYY') As Identificador, CF.Nome As Situacao								");
		sql.append(" From  Cad_Quadros_Tecnicos Qt, Cad_Carteiras Cc, Cad_Historicos Ch, CAD_PESSOAS_FISICAS CF															");
		sql.append(" Where Qt.Fk_Codigo_Empresas = :idPessoa 																											");
		sql.append(" And   Qt.Datafimqt Is Null																															");
		sql.append(" And Exists (Select R.Codigo From Cad_Responsaveis_Tecnicos R Where R.Fk_Codigo_Quadros_Tecnicos = Qt.Codigo And R.Datafimrt Is Null)				");
		sql.append(" And   Cc.Fk_Codigo_Profissionais = Qt.Fk_Codigo_Profissionais																						");
		sql.append(" And   Cc.Cancelada <> 1																															");
		sql.append(" And   Cc.Fk_Codigo_Tipos_Carteiras In (3, 7)																										");
		sql.append(" And   Ch.Fk_Codigo_Pessoas = Qt.Fk_Codigo_Profissionais																							");
		sql.append(" And   Ch.Fk_Codigo_Eventos In (206, 207)																											");
		sql.append(" And   Ch.Datafinal < SysDate																														");
		sql.append(" AND   CF.CODIGO = Qt.Fk_Codigo_Profissionais																										");
		sql.append(" And   Ch.Datainicio In (Select Max(Datainicio)																										");
		sql.append(" From Cad_Historicos																																");
		sql.append(" Where Fk_Codigo_Pessoas = Qt.Fk_Codigo_Profissionais																								");
		sql.append(" And Fk_Codigo_Eventos In (206, 207))																												");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idPessoa", dto.getIdPessoa());
			
			Iterator<?> it = query.getResultList().iterator();

			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {
					
					Object[] result = (Object[]) it.next();
					StringBuilder sb = new StringBuilder();
					sb.append(result[2].toString().toUpperCase());
					sb.append(" : ");
					sb.append(result[0].toString().toUpperCase());
					sb.append(" - ");
					sb.append(result[1].toString().toUpperCase());
					list.add(sb.toString());
				}
			}

		} catch (Throwable e) {
			httpGoApi.geraLog("CertidaoDao || validaPessoaParaCertidaoRegistro", StringUtil.convertObjectToJson(dto), e);
		}
		
	return list;
}

public boolean quadroTecnicoEhValido(UserFrontDto dto) {
	
	StringBuilder sql = new StringBuilder();
	
	sql.append(" Select N.Descricao As Descricao, D.Identificador_Divida  || Decode(D.Parcela, 0, '', '(Parcela ' || D.Parcela || ')') As Identificador, F.Nome As Situacao ");
	sql.append(" From Cad_Quadros_Tecnicos Q, Cad_Pessoas_Fisicas F, Fin_Divida D, Fin_Natureza N, Fin_Status_Divida S														");
	sql.append(" Where Q.Fk_Codigo_Empresas = :idPessoa																														");
	sql.append(" And Q.Datafimqt Is Null																																	");
	sql.append(" And Not Exists (Select R.Codigo From Cad_Responsaveis_Tecnicos R Where R.Fk_Codigo_Quadros_Tecnicos = Q.Codigo And R.Datafimrt Is Null)					");
	sql.append(" And F.Codigo = Q.Fk_Codigo_Profissionais																													");
	sql.append(" And D.Fk_Codigo_Pessoa = F.Codigo																															");
	sql.append(" And D.Data_Vencimento < Sysdate																															");
	sql.append(" And N.Codigo = D.Fk_Codigo_Natureza																														");
	sql.append(" And (N.Certidao_Registro = 1 Or D.Fk_Codigo_Status_Divida = 3)																								");
	sql.append(" And S.Codigo = D.Fk_Codigo_Status_Divida																													");
	sql.append(" And S.Quitado = 0																																			");
	sql.append(" And S.Certidao_Registro = 1																																");
	sql.append(" Union																																						");
	sql.append(" Select 'Validade do registro' As Descricao, To_Char(Ch.Datafinal,'DD/MM/YYYY') As Identificador, CF.Nome As Situacao										");
	sql.append(" From  Cad_Quadros_Tecnicos Qt, Cad_Carteiras Cc, Cad_Historicos Ch, CAD_PESSOAS_FISICAS CF																	");
	sql.append(" Where Qt.Fk_Codigo_Empresas = :idPessoa																													");
	sql.append(" And   Qt.Datafimqt Is Null																																	");
	sql.append(" And   Not Exists (Select R.Codigo From Cad_Responsaveis_Tecnicos R Where R.Fk_Codigo_Quadros_Tecnicos = Qt.Codigo And R.Datafimrt Is Null)					");
	sql.append(" And   Cc.Fk_Codigo_Profissionais = Qt.Fk_Codigo_Profissionais																								");
	sql.append(" And   Cc.Cancelada <> 1																																	");
	sql.append(" And   Cc.Fk_Codigo_Tipos_Carteiras In (3, 7)																												");
	sql.append(" And   Ch.Fk_Codigo_Pessoas = Qt.Fk_Codigo_Profissionais																									");
	sql.append(" And   Ch.Fk_Codigo_Eventos In (206, 207)																													");
	sql.append(" And   Ch.Datafinal < SysDate																																");
	sql.append(" AND   CF.CODIGO = Qt.Fk_Codigo_Profissionais																												");
	sql.append(" And   Ch.Datainicio In (Select Max(Datainicio)																												");
	sql.append("                         From Cad_Historicos																												");
	sql.append("                         Where Fk_Codigo_Pessoas = Qt.Fk_Codigo_Profissionais																				");
	sql.append("                         And Fk_Codigo_Eventos In (206, 207));T																								");
	try {
		Query query = em.createNativeQuery(sql.toString());
		query.setParameter("idPessoa", dto.getIdPessoa());
		

		if (query.getResultList().size() > 0) {
			return false;
		}

	} catch (Throwable e) {
		httpGoApi.geraLog("CertidaoDao || quadroTecnicoEhValido", StringUtil.convertObjectToJson(dto), e);
	}
	return true;
}
	

}
