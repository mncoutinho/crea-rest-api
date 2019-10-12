package br.org.crea.restapi.portal;

import static org.junit.Assert.assertEquals;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.org.crea.commons.dao.atendimento.AtendimentoDao;
import br.org.crea.commons.models.portal.dto.AtendimentoDto;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AtendimentoTest {
	
	private EntityManagerFactory emf;
	
	private EntityManager em;
	
	private AtendimentoDto dto;
	
	private AtendimentoDao dao;
	
	@Before
	public void setup(){
		 //Cria conexão com o banco de teste
		 emf = Persistence.createEntityManagerFactory("dsCreaTest");
		 em = emf.createEntityManager();
		 
		//o mock envolvido no teste
		 dto = populaAtendimentoDto(); 
		 
		//instancia o dao
		 dao = new AtendimentoDao(); 
		 dao.setEntityManager(em);
	}
	
	
	@Test
	public void a_deveValidarUmAtendimentoDisponivelParaPesquisaDeSatisfacao(){
		preparaBanco(dto); 
		assertEquals(true, dao.atendimentoEstaDisponivelParaPesquisa(dto.getNumeroChamado()));
		
	}
	
	@Test
	public void b_deveInserirUmaNovaPesquisaDeSatisfacao(){
		
		dao.createTransaction();
		
		dao.atualizaPesquisa(dto);
		
		dao.commitTransaction();
		
	}
	
	@Test
	public void c_deveNegarUmaNovaPesquisaPorJaTerFeitoAntes(){
		
		assertEquals(false, dao.atendimentoEstaDisponivelParaPesquisa(dto.getNumeroChamado()));
		
	}
	
	@After
	public void finishTest(){
		em.close();
		emf.close();
	}
	

	private AtendimentoDto populaAtendimentoDto() {
		AtendimentoDto dto = new AtendimentoDto();
		dto.setNumeroChamado(3217848L);
		dto.setClareza(2);
		dto.setCordialidade(5);
		dto.setTempoEspera(5);
		dto.setOrientacao(8);
		dto.setSugestao("testelaçlskdfaçlkdflçasdkf");
		return dto;
	}
	
	private void preparaBanco(AtendimentoDto dto) {

		em.getTransaction().begin();
		
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE  Atendimento A "
				+ "	 SET A.tempoEspera = 0, "
				+ "      A.cordialidade = 0, "
				+ "		 A.clareza = 0, "
				+ "		 A.orientacoes = 0, "
				+ "		 A.sugestao = '' "
				+ "	 WHERE A.codigo = :codigo");

		Query query = em.createQuery(sql.toString());
		query.setParameter("codigo", dto.getNumeroChamado());
		query.executeUpdate();
		
		em.getTransaction().commit();
	}
	
	

}
