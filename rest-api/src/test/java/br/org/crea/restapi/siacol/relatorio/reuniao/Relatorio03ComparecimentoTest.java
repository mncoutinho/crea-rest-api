package br.org.crea.restapi.siacol.relatorio.reuniao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.dao.siacol.RelatorioReuniaoSiacolDao;
import br.org.crea.commons.models.siacol.PresencaReuniaoSiacol;
import br.org.crea.commons.models.siacol.dtos.RelatorioReuniaoSiacolDto;
import br.org.crea.commons.util.DateUtils;
import br.org.crea.siacol.dao.VotoReuniaoSiacolDao;

public class Relatorio03ComparecimentoTest {

	static RelatorioReuniaoSiacolDao dao;
	static VotoReuniaoSiacolDao votoDao;
	private static EntityManager em = null;
	
	@Before
	public void inicio() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dsCreaTest");
		em = factory.createEntityManager();
		dao = new RelatorioReuniaoSiacolDao();
		votoDao = new VotoReuniaoSiacolDao();
		dao.setEntityManager(em);
		votoDao.setEntityManager(em);
	}
	
	@After
	public void fim() {
		em.close();
	}
	
//	@Test
	public void getTest() {
		RelatorioReuniaoSiacolDto relatorio = new RelatorioReuniaoSiacolDto();
		
		relatorio.setDiaria(new BigDecimal("0"));
		relatorio.setJeton(new BigDecimal("0"));
		BigDecimal c = BigDecimal.ZERO;
		
		BigDecimal resultado = c.add(relatorio.getDiaria()).add(relatorio.getJeton());
		System.out.println(resultado);
	}
	
//	@Test
	public void getParticipantesComPresencaNaReuniaoTest() {
		Long idReuniao = 21050L;
		List<PresencaReuniaoSiacol> listaPresenca = dao.getParticipantesComPresencaNaReuniao(idReuniao);
		
		listaPresenca.forEach(presenca -> {
			System.out.println(presenca.getPessoa().getNome());
		});
	}
	
//	@Test
	public void getTempoPresenteTest() {
		Long idReuniao = 21050L;
		Long idPessoa = 1976101120L;
		System.out.println("Tempo Presente: " + dao.getTempoPresenteNaReuniao(idReuniao, idPessoa));
	}
	
//	@Test
	public void getQuantidadeVotadoTest() {
		Long idReuniao = 21050L;
		Long idPessoa = 1980101726L;
		System.out.println("QTD VOTADO: " + dao.getQuantidadeVotado(idReuniao, idPessoa));
	}
	
//	@Test
	public void getValorDiariaTest() {
		Long idPessoa = 1980101726L;
		System.out.println("DIÁRIA: " + dao.getValorDiaria(idPessoa));
	}
	
//	@Test
	public void getValorJetonTest() {
		Long idPessoa = 1980101726L;
		System.out.println("JETON: " + dao.getValorJeton(idPessoa));
	}
	
	@Test
	public void relatorioTest() {
		
		try {
			Long idReuniao = 18156L;
			List<RelatorioReuniaoSiacolDto> listaRelatorio = relatorioDeComparecimento(idReuniao);
			
			System.out.println("Nome | Entrada | Saída | Tempo Presente | Qtd Votado | Diária | Jeton | Soma");
			listaRelatorio.forEach(item -> {
				System.out.println(item.getNome() + " | " + item.getEntrada() + " | " + item.getSaida()
				+ " | " + item.getTempoPresente() + " | " + item.getQtdVotado()
				+ " | " + item.getDiaria() + " | " + item.getJeton() + " | " + item.getSoma());
			});
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}	
	
	public List<RelatorioReuniaoSiacolDto> relatorioDeComparecimento(Long idReuniao) {
		
		List<RelatorioReuniaoSiacolDto> listaRelatorio = new ArrayList<RelatorioReuniaoSiacolDto>();
	        
		List<PresencaReuniaoSiacol> listaPresenca = dao.getParticipantesComPresencaNaReuniao(idReuniao);
		
		boolean teveAoMenosUmItemVotado = votoDao.reuniaoTeveAoMenosUmItemVotado(idReuniao);
		boolean reuniaoEncerraAposDezenoveHoras = true;
		
		for (PresencaReuniaoSiacol participante : listaPresenca) {
			
			RelatorioReuniaoSiacolDto relatorio = new RelatorioReuniaoSiacolDto();
			
			relatorio.setNome(participante.getPessoa().getNome());
			relatorio.setEntrada(DateUtils.format(participante.getHoraEntregaCracha(), DateUtils.HH_MM));
			relatorio.setSaida(DateUtils.format(participante.getHoraDevolucaoCracha(), DateUtils.HH_MM));

			relatorio.setTempoPresente(dao.getTempoPresenteNaReuniao(idReuniao, participante.getPessoa().getId()));
			
			relatorio.setQtdVotado(String.valueOf(dao.getQuantidadeVotado(idReuniao, participante.getPessoa().getId())));
			
			if ( (teveAoMenosUmItemVotado && reuniaoEncerraAposDezenoveHoras) && (idReuniao.equals(1307L) || idReuniao.equals(1310L) || participante.naoAtingiu80())) {
				relatorio.setDiaria(new BigDecimal("0.0"));
				relatorio.setJeton(new BigDecimal("0.0"));
			} else {
				relatorio.setDiaria(dao.getValorDiaria(participante.getPessoa().getId()));
				relatorio.setJeton(dao.getValorJeton(participante.getPessoa().getId()));
			}
			
			relatorio.setSoma(relatorio.getDiaria().add(relatorio.getSoma()));
			
			listaRelatorio.add(relatorio);
		}
		
		return listaRelatorio;
	}

	private String obtemDiaria(Long idReuniao) {
		
		// DIÁRIA   
//		sql.append(" (SELECT CASE                                                                                                        "); 
//		sql.append("  WHEN (SELECT PR.ATINGIU_80 FROM SIACOL_REUNIAO_PRESENCA PR WHERE PR.FK_REUNIAO = :idReuniao AND PR.FK_PESSOA = P.FK_PESSOA) = 0 THEN '0,00'     ");
//		sql.append("  WHEN (SELECT PR.ATINGIU_80 FROM SIACOL_REUNIAO_PRESENCA PR WHERE PR.FK_REUNIAO = :idReuniao AND PR.FK_PESSOA = P.FK_PESSOA) IS NULL THEN '0,00' ");
//		sql.append("  WHEN (SELECT R.FK_DEPARTAMENTO FROM SIACOL_REUNIAO R WHERE R.ID = :idReuniao) = 1310 THEN '0,00'                   "); 
//		sql.append("  WHEN (SELECT R.FK_DEPARTAMENTO FROM SIACOL_REUNIAO R WHERE R.ID = :idReuniao) = 1307 THEN '0,00'                   "); 
//		sql.append("  WHEN (SELECT L.REGIAO_METROPOLITANA FROM CAD_LOCALIDADES L                                                         "); 
//		sql.append("   WHERE CODIGO IN                                                                                                   "); 
//		sql.append(" 	(SELECT E.FK_CODIGO_LOCALIDADES                                                                                  "); 
//		sql.append(" 		FROM CAD_ENDERECOS E                                                                                         "); 
//		sql.append(" 		WHERE E.FK_CODIGO_PESSOAS = P.FK_PESSOA                                                                      "); 
//		sql.append(" 	      AND E.POSTAL = 1 AND E.ENDERECOVALIDO = 1)) = 1  THEN '0,00'                                               "); 
//		sql.append("  WHEN P.KM_DOMICILIO_CREA > 400 THEN '321,49'                                                                       "); 
//		sql.append("  ELSE '160,65' END AS DIARIA_JETON                                                                                  "); 
//		sql.append("  FROM PER_PERSONALIDADES P                                                                                          "); 
//		sql.append("  WHERE P.CODIGO = P.FK_PESSOA),                                                                                     ");
				
		return null;
	}

	private String obtemJeton(Long idReuniao) {

		// JETON  IMPORTANTE.... SÓ HAVERÁ JETON PARA QUEM COMPLETAR OS 80%, FILTRAR
//		sql.append(" (SELECT CASE                                                                                                        "); 
//		sql.append("  WHEN (SELECT PR.ATINGIU_80 FROM SIACOL_REUNIAO_PRESENCA PR WHERE PR.FK_REUNIAO = :idReuniao AND PR.FK_PESSOA = P.FK_PESSOA) = 0 THEN '0,00'     ");
//		sql.append("  WHEN (SELECT PR.ATINGIU_80 FROM SIACOL_REUNIAO_PRESENCA PR WHERE PR.FK_REUNIAO = :idReuniao AND PR.FK_PESSOA = P.FK_PESSOA) IS NULL THEN '0,00' ");
//		sql.append("  WHEN (SELECT R.FK_DEPARTAMENTO FROM SIACOL_REUNIAO R WHERE R.ID = :idReuniao) = 1310 THEN '0,00'                   "); 
//		sql.append("  WHEN (SELECT R.FK_DEPARTAMENTO FROM SIACOL_REUNIAO R WHERE R.ID = :idReuniao) = 1307 THEN '0,00'                   "); 
//		sql.append("  WHEN (SELECT L.REGIAO_METROPOLITANA FROM CAD_LOCALIDADES L                                                         "); 
//		sql.append("   WHERE CODIGO IN                                                                                                   "); 
//		sql.append(" 	(SELECT E.FK_CODIGO_LOCALIDADES                                                                                  "); 
//		sql.append(" 		FROM CAD_ENDERECOS E                                                                                         "); 
//		sql.append(" 		WHERE E.FK_CODIGO_PESSOAS = P.FK_PESSOA                                                                      "); 
//		sql.append(" 	      AND E.POSTAL = 1 AND E.ENDERECOVALIDO = 1)) = 1  THEN '145,00'                                             "); 
//		sql.append("  WHEN P.KM_DOMICILIO_CREA > 400 THEN '145,00'                                                                       "); 
//		sql.append("  ELSE '145,00' END AS DIARIA_JETON                                                                                  "); 
//		sql.append("  FROM PER_PERSONALIDADES P                                                                                          "); 
//		sql.append("  WHERE P.CODIGO = P.FK_PESSOA),                                                                                     ");
				
				
		return null;
	}
}