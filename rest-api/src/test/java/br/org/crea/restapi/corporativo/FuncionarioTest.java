package br.org.crea.restapi.corporativo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Test;

import br.org.crea.commons.dao.cadastro.funcionario.FuncionarioDao;
import br.org.crea.commons.models.corporativo.pessoa.Funcionario;


public class FuncionarioTest {
	
	

	
	@Test
	public void getFuncionario(){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("dsCreaTest");
		EntityManager em = emf.createEntityManager();
		
		try {
			
			Funcionario funcionario = new Funcionario();
			
			FuncionarioDao dao = new FuncionarioDao();
			dao.setEntityManager(em);
			
			funcionario = dao.getFuncionarioBy(new Long(174));
			
			System.out.println(funcionario.getPessoaFisica().getNome());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	

}
