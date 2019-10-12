package br.org.crea.restapi.commons;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.dao.cadastro.pessoa.InteressadoDao;
import br.org.crea.commons.models.commons.dtos.PesquisaGenericDto;
import br.org.crea.commons.models.corporativo.RazaoSocial;
import br.org.crea.commons.models.corporativo.pessoa.IInteressado;

public class ProtocoloPorInteressadoTest {
	
	private EntityManagerFactory emf;
	
	private EntityManager em; 
	
	private InteressadoDao dao;
	
	private String nome;
	
	private String razaoSocial;
	
	private String cpf;
	
	private String cnpj;
	
	private String tipoPessoa;
	
	
	
	@Before
	public void setup(){
		emf = Persistence.createEntityManagerFactory("dsCreaTest");
		em = emf.createEntityManager();
		
		dao = new InteressadoDao();
		dao.setEntityManager(em);
		
//		nome = "Ricardo Leite";
//		cpf = "12191235794";
		
//		tipoPessoa ="PESSOAFISICA";
//		tipoPessoa ="PESSOAJURIDICA";
		
//		razaoSocial = "SETA-REALENGO";
//		cnpj = "32330003000180";
	}
	
	
	
	@Test
	public void deveTestarBuscaPorNome(){
		
		List<IInteressado> listInteressado = new ArrayList<IInteressado>();
		List<RazaoSocial> listR = new ArrayList<RazaoSocial>();
		PesquisaGenericDto dto = new PesquisaGenericDto();
		
		dto.setCpf(cpf);
		dto.setNomePessoa(nome);
		
		dto.setCnpj(cnpj);
		dto.setRazaoSocial(razaoSocial);
		
		dto.setTipoPessoa(tipoPessoa);
		
		try {
			
//			listInteressado = dao.buscaListaInteressadoPorCpf(dto);
//			listInteressado = dao.buscaListaInteressadoPorNomePF(dto);
//			listInteressado = dao.buscaInteressadoPorCnpj(dto);			
			listR = dao.buscaListaInteressadoPorNomePJ(dto);
			
			System.out.println(">>>>>>>>>");
			
		} catch (Exception e) {
			
			System.err.println("Erro >>>>>>>>>> " + e.getMessage());
			
		}
	}
	
	
	@After
	public void finish(){
		
		
	}
	

}
