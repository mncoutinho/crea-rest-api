package br.org.crea.restapi.commons;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.dao.cadastro.EmailDao;
import br.org.crea.commons.models.commons.DestinatarioEmailDto;

public class EmailUtilTest {
	
	private EntityManagerFactory emf;
	private EntityManager em;
	private EmailDao emailDao;
	
	@Before
	public void setup() {
		 emf = Persistence.createEntityManagerFactory("dsCreaTest");
		 em = emf.createEntityManager();
		 emailDao = new EmailDao();
		 emailDao.setEntityManager(em);
	}
	
	@After
	public void finishTest() {
		em.close();
		emf.close();
	}
	
	@Test
	public void deveTrazerModaUnica() {
		
		List<DestinatarioEmailDto> listaEmails = montarListaDestinatarios(1977102871L, 2018200333L, 2018200330L);
		
		listaEmails.forEach(email -> {
			System.out.println(email.getNome() + " - " + email.getEmail());
		});
	}
	
	public List<DestinatarioEmailDto> montarListaDestinatarios(Long ... idsPessoa) {
		
		List<DestinatarioEmailDto> listaEmails = new ArrayList<DestinatarioEmailDto>();
		
		for (Long idPessoa : idsPessoa) {
			String emailPessoa = emailDao.getUltimoEmailCadastradoPor(idPessoa);

			if (emailPessoa != null) {

				DestinatarioEmailDto destinatario = new DestinatarioEmailDto();
				destinatario.setEmail(emailPessoa);
				destinatario.setNome("Teste");

				listaEmails.add(destinatario);
			}
		}		

		return listaEmails;
	}
}
