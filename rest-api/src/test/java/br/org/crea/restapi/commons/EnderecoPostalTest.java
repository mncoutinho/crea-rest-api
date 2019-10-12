package br.org.crea.restapi.commons;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.dao.cadastro.pessoa.EnderecoDao;
import br.org.crea.commons.models.commons.Endereco;
import br.org.crea.commons.models.commons.dtos.EnderecoDto;

public class EnderecoPostalTest {
	
	private EnderecoDao dao;
	
	private EntityManagerFactory emf;
	
	private EntityManager em;
	
	@Before
	public void inicio() {
		
		emf = Persistence.createEntityManagerFactory("dsCreaTest");
		em = emf.createEntityManager();
		
		dao = new EnderecoDao();
		dao.setEntityManager(em);
	}
	
	@After
	public void fim() {
		em.close();
		emf.close();
	}
	
	
	@Test
	public void deveTrazerEnderecoPostal() {
		EnderecoDto dto = populaEnderecoDto();
		dto.setId(Long.parseLong(dto.getIdString()));
		
		if(dto.getPostal().equals("SIM")) {
			Endereco endereco = dao.getEnderecoValidoEPostalPor(dto.getCodPessoa());
			if (endereco != null) {
				
				dao.createTransaction();
				dao.updatePostal(endereco.getId(),false);
				dao.commitTransaction();
				
				dao.createTransaction();
				dao.atualizarEndereco(dto);
				dao.commitTransaction();
				System.out.println("altearado");
			}
		} else {
			Endereco endereco = dao.getEnderecoValidoEPostalPor(dto.getCodPessoa());
			if (!endereco.getId().equals(dto.getId())) {
				dao.createTransaction();
				dao.atualizarEndereco(dto);
				dao.commitTransaction();
			} else {
				System.out.println("Não pode apagar este endereço");
			}
		}
	}
	public EnderecoDto populaEnderecoDto() {
		EnderecoDto dto = new EnderecoDto();
		
		dto.setCodPessoa(1999106498L);
		dto.setId(11L);
		dto.setIdString("13689671430927597");
		dto.setNumero("11111");
		dto.setPostal("SIM");
		dto.setComplemento("999");
		
		return dto;
	}
	
}
