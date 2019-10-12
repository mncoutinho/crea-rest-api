package br.org.crea.restapi.siacol;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Test;

import br.org.crea.commons.models.cadastro.Departamento;
import br.org.crea.commons.models.siacol.AssuntoSiacol;
import br.org.crea.commons.models.siacol.ProtocoloSiacol;

public class ProtocoloSiacolEnversTest {
	
	
	
	@Test
	public void testaEnversProtocoloSiacol() {

		try {
			
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("dsCreaTest");
			EntityManager em = emf.createEntityManager();
			

			ProtocoloSiacol protocolo = populaProtocolo();

			em.getTransaction().begin();
			em.persist(protocolo);
			em.flush();
			em.getTransaction().commit();
			em.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private ProtocoloSiacol populaProtocolo() {
		ProtocoloSiacol m = new ProtocoloSiacol();
		//1992003664
		
		m.setNumeroProcesso(new Long(2222));
		m.setNumeroProtocolo(new Long(2222));
		AssuntoSiacol assunto = new AssuntoSiacol();
		assunto.setId(new Long(2));
		m.setAssuntoSiacol(assunto);
		m.setIdAssuntoCorportativo(new Long(203));
		m.setDescricaoAssuntoCorporativo("teste assunto");
		Departamento departamento = new Departamento();
		departamento.setId(new Long(10003));
		m.setDepartamento(departamento);
//		SituacaoProtocolo situacao = new SituacaoProtocolo();
//		situacao.setId(new Long(26));
//		m.setSituacao(situacao);
		m.setIdInteressado(new Long(1992003664));
		m.setIdResponsavel(new Long(1992003664));
		
		
		return m;
	}

}
