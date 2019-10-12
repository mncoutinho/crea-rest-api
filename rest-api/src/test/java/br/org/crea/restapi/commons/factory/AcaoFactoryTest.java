package br.org.crea.restapi.commons.factory;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.dao.cadastro.AcoesDao;
import br.org.crea.commons.models.cadastro.Acoes;
import br.org.crea.commons.models.commons.TipoPessoa;
import br.org.crea.commons.models.commons.enuns.TipoAcaoEnum;
import br.org.crea.commons.models.corporativo.pessoa.Funcionario;

public class AcaoFactoryTest {

	private EntityManagerFactory emf;
	
	private EntityManager em;
	private AcoesDao acoesDao;
	
	@Before
	public void setup() {
		 emf = Persistence.createEntityManagerFactory("dsCreaTest");
		 em = emf.createEntityManager();

		 acoesDao = new AcoesDao();
		 acoesDao.setEntityManager(em);
	}
	
	@After
	public void finishTest() {
		em.close();
		emf.close();
	}
	
	@Test
	public void cadastraAcaoViaFactoryTest () {
		Acoes acoes = gerarAcaoFactory();
		System.out.println("IdDivida: "+ acoes.getId());
	}
	
	
	public Acoes gerarAcaoFactory() {
		
		Acoes acoes = new Acoes();
		
		acoes.setFuncionario(Funcionario.getIdUsuarioPortal());
		acoes.setTipoAcao(TipoAcaoEnum.BAIXA_DE_RESPONSAVEL_TECNICO.getId());
		acoes.setTipoPessoa(TipoPessoa.PROFISSIONAL.toString());
		acoes.setTipoOutraPessoa(TipoPessoa.EMPRESA.toString());
		acoes.setIdPessoa(new Long(1999106498));
		acoes.setIdOutraPessoa(new Long(1987200212));
		acoes.setDataAcao(new Date());
		
		acoesDao.createTransaction();
		Acoes acao = acoesDao.create(acoes);
		acoesDao.commitTransaction();
	  
		return acao;
	}
}
