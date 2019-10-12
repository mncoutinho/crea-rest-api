//package br.org.crea.restapi.corporativo;
//
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Persistence;
//
//import org.junit.Test;
//
//import com.google.gson.Gson;
//
//import br.org.crea.commons.dao.cadastro.profissional.ProfissionalDao;
//import br.org.crea.commons.dao.cadastro.profissional.ProfissionalEspecialidadeDao;
//import br.org.crea.commons.models.commons.dtos.ProtocoloDto;
//import br.org.crea.commons.models.corporativo.pessoa.Profissional;
//
//public class ProfissionalTest {
//	
//
//	//@Test
//	public void getProfissional(){
//		EntityManagerFactory emf = Persistence.createEntityManagerFactory("dsCreaTest");
//		EntityManager em = emf.createEntityManager();
//		
//		try {
//			
//			Profissional profissional = new Profissional();
//			
//			ProfissionalDao dao = new ProfissionalDao();
//			dao.setEntityManager(em);
//			
//			profissional = dao.buscaProfissionalPor("1988105244");
//			
//			System.out.println(profissional.getPessoaFisica().getNome());
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//	}
//	
//	@Test
//	public void getTitulosProfissional(){
//		EntityManagerFactory emf = Persistence.createEntityManagerFactory("dsCreaTest");
//		EntityManager em = emf.createEntityManager();
//		
//		try {
//			
//			Profissional profissional = new Profissional();
//			
//			ProfissionalDao dao = new ProfissionalDao();
//			dao.setEntityManager(em);
//			
//			ProfissionalEspecialidadeDao especialidadeDao = new ProfissionalEspecialidadeDao();
//			especialidadeDao.setEntityManager(em);
//			
//			profissional = dao.buscaProfissionalPor("1988105244");
//			String titulo = especialidadeDao.getTituloProfissional(profissional);
//			
//			System.out.println(">> TITULO: " + titulo);
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//	}
//
//}
