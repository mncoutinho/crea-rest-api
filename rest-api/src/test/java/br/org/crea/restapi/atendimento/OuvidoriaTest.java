package br.org.crea.restapi.atendimento;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.org.crea.atendimento.converter.OuvidoriaConverter;
import br.org.crea.commons.dao.atendimento.OuvidoriaDao;
import br.org.crea.commons.models.atendimento.Ouvidoria;
import br.org.crea.commons.models.atendimento.OuvidoriaAssunto;
import br.org.crea.commons.models.atendimento.dtos.OuvidoriaDto;

public class OuvidoriaTest {

	private OuvidoriaDao dao;
	
	private OuvidoriaConverter converter;

	private EntityManagerFactory emf;

	private EntityManager em;
	
	@Before
	public void setup() {
		emf = Persistence.createEntityManagerFactory("dsCreaTest");
		em = emf.createEntityManager();

		dao = new OuvidoriaDao();
		dao.setEntityManager(em);
	}

	@After
	public void fim() {
		em.close();
		emf.close();
	}

	@Test
	public void deveAdicionarNaOuvidoria() {
		System.out.println("OUVIDORIA TESTE : VALOR EM PROVIDÊNCIA");
		OuvidoriaDto dto = new OuvidoriaDto();
		String texto = "Teste JUnit";
		dto.setDescricao(texto);
		dto.setProvidencia(texto);
		
//		Ouvidoria model = populaOuvidoria();
//		dao.createTransaction();
//		Ouvidoria teste = dao.create(model);
//		dao.commitTransaction();
		
		Ouvidoria ouvidoria = converter.toModel(dto);
		converter.toDto(dao.getBy(dao.create(ouvidoria).getId()));
		
		System.out.println(dto.getProvidencia());
	}

	public Ouvidoria populaOuvidoria() {
		Ouvidoria model = new Ouvidoria();
		
		OuvidoriaAssunto assunto = new OuvidoriaAssunto();
		assunto.setId(1L);
		model.setAssunto(assunto);
		
		return model;
	}

}
