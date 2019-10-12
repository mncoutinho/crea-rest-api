package br.org.crea.restapi.art.dao;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.org.crea.commons.converter.art.ArtLivroOrdemConverter;
import br.org.crea.commons.dao.art.ArtLivroOrdemDao;
import br.org.crea.commons.models.art.dtos.ArtLivroDeOrdemDto;
import br.org.crea.commons.util.DateUtils;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ArtLivroOrdemDaoTest {
	
	private EntityManagerFactory emf;
	
	private EntityManager em;
	
	private ArtLivroOrdemDao dao;
	
	private ArtLivroDeOrdemDto dto;
	
	private ArtLivroOrdemConverter converter;
	
	
	@Before
	public void setup(){
		 //Cria conexão com o banco de teste
		 emf = Persistence.createEntityManagerFactory("dsCreaTest");
		 em = emf.createEntityManager();
		 
		 //o mock envolvido no teste
		 dto = populaArtLivroOrdemDto(); 
		 
		 //instancia o dao
		 dao = new ArtLivroOrdemDao(); 
		 dao.setEntityManager(em);
	}
	
	
	@Test
	public void a_deveCadastrarLivroDeOrdem(){ 
		converter = new ArtLivroOrdemConverter();
		dao.createTransaction();
		dao.commitTransaction();
		
	}
	
	private ArtLivroDeOrdemDto populaArtLivroOrdemDto() {
		ArtLivroDeOrdemDto dto = new ArtLivroDeOrdemDto();
		dto.setNumeroArt("2020180000292");
		dto.setDataInicioDaEtapa(DateUtils.format(new Date(), DateUtils.YYYY_MM_DD_COM_TRACOS));
		dto.setDataConclusao(DateUtils.format(new Date(), DateUtils.YYYY_MM_DD_COM_TRACOS));
		dto.setRelatoVisitaResponsavelTecnico("Teste relato1");
		dto.setOrientacao("Teste orientacao1");
		dto.setAcidentesDanosMateriais("Teste acidente1");
		dto.setEmpresasePrestadoresContratadosSubContratados("Teste empresas1");
		dto.setPeriodosInterrupcaoEMotivos("Teste periodos1");
		dto.setOutrosFatosEObservacoes("teste outros1");
		
		return dto;
	}

	@After
	public void finishTest(){
		em.close();
		emf.close();
	}
	
	

}
