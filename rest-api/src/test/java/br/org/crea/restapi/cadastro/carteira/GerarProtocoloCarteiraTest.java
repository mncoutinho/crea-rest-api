package br.org.crea.restapi.cadastro.carteira;

import java.util.Calendar;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.dao.protocolo.MovimentoDao;
import br.org.crea.commons.dao.protocolo.ProtocoloDao;
import br.org.crea.commons.models.commons.Movimento;
import br.org.crea.commons.models.commons.Protocolo;

public class GerarProtocoloCarteiraTest {


	private EntityManagerFactory emf;
	
	private EntityManager em;
	private ProtocoloDao protocoloDao;
	private MovimentoDao movimentoDao;
	
	@Before
	public void setup() {
		 emf = Persistence.createEntityManagerFactory("dsCreaTest");
		 em = emf.createEntityManager();

		 protocoloDao = new ProtocoloDao();
		 movimentoDao = new MovimentoDao();
		 protocoloDao.setEntityManager(em);
		 movimentoDao.setEntityManager(em);
	}
	
	@After
	public void finishTest() {
		em.close();
		emf.close();
	}
	
	@Test
	public void deveGerarProtocoloTest(){ 
		this.gerarProtocolo();
		
		
	}
	
	private void gerarProtocolo () {

		Protocolo protocolo = new Protocolo();
		protocolo.setIdFuncionario(99990l);
		protocolo.setDataEmissao(Calendar.getInstance().getTime());
		
		protocoloDao.create(protocolo);

	    Movimento movimento = new Movimento();
	    movimento.setIdFuncionarioRemetente(99990l);
	    movimento.setIdFuncionarioReceptor(null);
	    movimento.setDataEnvio(Calendar.getInstance().getTime());
	    movimento.setTempoPermanencia(0l);
	    
	    movimentoDao.create(movimento);
	}
}
