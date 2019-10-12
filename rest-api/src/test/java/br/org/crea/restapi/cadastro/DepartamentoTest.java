package br.org.crea.restapi.cadastro;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Test;

import br.org.crea.commons.dao.cadastro.CadastroDao;
import br.org.crea.commons.dao.cadastro.domains.DepartamentoDao;
import br.org.crea.commons.models.cadastro.Departamento;

public class DepartamentoTest {
	
	private CadastroDao dao;
	
	
	//@Test
	public void devePegarDepartamentosIrmaos() {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("dsCreaTest");
		EntityManager em = emf.createEntityManager();

		try {

			List<Departamento> unidadesIrmasFuncionario = new ArrayList<Departamento>();
		
			dao = new CadastroDao();
	

			unidadesIrmasFuncionario = dao.getUnidadesAtendimentoRegionalPor(new Long(898));
			
			for (Departamento departamento : unidadesIrmasFuncionario) {
				System.out.println(departamento.getNome());
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void devePegarUnidadesTramitacaoDoFuncionario() {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("dsCreaTest");
		EntityManager em = emf.createEntityManager();

		try {

			List<Departamento> departamentos = new ArrayList<Departamento>();
		
			DepartamentoDao departamentoDao = new DepartamentoDao();
			departamentoDao.setEntityManager(em);
	

			departamentos = departamentoDao.getListUnidadesTramitacaoPor(new Long(39));
			System.out.println(">>>> Qtd departamentos: " + departamentos.size());
			
			for (Departamento departamento : departamentos) {
				System.out.println(departamento.getNome());
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}


	

}
