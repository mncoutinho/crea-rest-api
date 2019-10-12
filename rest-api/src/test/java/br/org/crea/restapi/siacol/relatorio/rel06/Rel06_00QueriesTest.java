package br.org.crea.restapi.siacol.relatorio.rel06;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.dao.siacol.relatorios.RelatorioSiacol06Dao;
import br.org.crea.commons.models.corporativo.dtos.PessoaDto;
import br.org.crea.commons.models.siacol.dtos.PesquisaRelatorioSiacolDto;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelDetalhadoSiacol06Dto;

public class Rel06_00QueriesTest {

	static RelatorioSiacol06Dao dao;
	private static EntityManager em = null;
	List<Long> idsAssuntoSiacol = new ArrayList<Long>();
	String mes = "01";
	
	@Before
	public void inicio() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dsCreaTest");
		em = factory.createEntityManager();
		dao = new RelatorioSiacol06Dao();
		dao.setEntityManager(em);
		
		idsAssuntoSiacol.add(50L);
	}
	
	@After
	public void fim() {
		em.close();
	}
	
	private PesquisaRelatorioSiacolDto populaPesquisa() {
		PesquisaRelatorioSiacolDto pesquisa = new PesquisaRelatorioSiacolDto();
		
		List<PessoaDto> responsaveis = new ArrayList<PessoaDto>();
		PessoaDto responsavel = new PessoaDto();
		responsavel.setId(2000105584L);
		responsavel.setPerfil("siacolanalista");
		responsaveis.add(responsavel);
		pesquisa.setResponsaveis(responsaveis);
		pesquisa.setAno("2019");
		
		return pesquisa;
	}
	
//	@Test
	public void percentual () {
		double percentual;
		double entrada = 1 + 1;
		if (entrada == 0) { // evitar divisão por zero
			percentual = 0;
		} else {
			percentual = 1 / entrada * 100.0;
		}
		System.out.println(String.valueOf(percentual));
	}
	
	@Test
	public void getPassivoTest() {
		List<RelDetalhadoSiacol06Dto> lista = dao.getPassivoAnalistaAnosAnteriores(populaPesquisa(), idsAssuntoSiacol);
		System.out.println("Id | Protocolo  | Ref. | Data | Descrição Assunto | Departamento | Peso");
		lista.forEach(item -> {
			System.out.println(item.getIdAuditoria() + " | " + item.getNumeroProtocolo() + " | " + item.getNumeroProtocoloReferencia() + " | " + item.getData() + " | " + item.getNomeAssunto() + " | " + item.getNomeDepartamento() + " | " + item.getPesoDto().getPesoValido());
		});
	}
	
	
//	@Test
	public void entradaAnalistaTest() {
		List<RelDetalhadoSiacol06Dto> lista = dao.getEntradaAnalistaDistribuicaoOuTramitacao(populaPesquisa(), mes, idsAssuntoSiacol);
		System.out.println("Id   | Protocolo  | Ref. | Data | Descrição Assunto | Departamento | Peso");
		lista.forEach(item -> {
			System.out.println(item.getIdAuditoria() + " | " + item.getNumeroProtocolo() + " | " + item.getNumeroProtocoloReferencia() + " | " + item.getData() + " | " + item.getNomeAssunto() + " | " + item.getNomeDepartamento() + " | " + item.getPesoDto().getPesoValido());
		});
	}
	
//	@Test
	public void pausadoAnalistaTest() {
		RelDetalhadoSiacol06Dto entrada = new RelDetalhadoSiacol06Dto();
		entrada.setIdAuditoria("20");
		entrada.setNumeroProtocolo("20");
		RelDetalhadoSiacol06Dto item = dao.getPausadoAnalista(populaPesquisa(), mes, entrada, idsAssuntoSiacol);
		System.out.println("Id   | Protocolo  | Ref. | Data | Descrição Assunto | Departamento | Peso");
		System.out.println(item.getIdAuditoria() + " | " + item.getNumeroProtocolo() + " | " + item.getNumeroProtocoloReferencia() + " | " + item.getData() + " | " + item.getNomeAssunto() + " | " + item.getNomeDepartamento() + " | " + item.getPesoDto().getPesoValido());
	}
	
//	@Test
	public void retornoAnalistaTest() {
		List<RelDetalhadoSiacol06Dto> lista = dao.getRetornoAnalista(populaPesquisa(), mes, idsAssuntoSiacol);
		System.out.println("Id   | Protocolo  | Ref. | Data | Descrição Assunto | Departamento | Peso");
		lista.forEach(item -> {
			System.out.println(item.getIdAuditoria() + " | " + item.getNumeroProtocolo() + " | " + item.getNumeroProtocoloReferencia() + " | " + item.getData() + " | " + item.getNomeAssunto() + " | " + item.getNomeDepartamento() + " | " + item.getPesoDto().getPesoValido());
		});
	}
	
//	@Test
	public void saidaConselheiroTest() {
		boolean ehPassivo = true;
		String tipoSaida = "IMPEDIMENTO";
		RelDetalhadoSiacol06Dto entrada = new RelDetalhadoSiacol06Dto();
//		RelDetalhadoSiacol06Dto item = dao.getSaidaConselheiroImpedimentoOuAssinaturaRelatorioVoto(populaPesquisa(), mes, entrada, !ehPassivo, tipoSaida, idsAssuntoSiacol);
		System.out.println("Id   | Protocolo  | Ref. | Data | Descrição Assunto | Departamento | Peso");
//		System.out.println(item.getIdAuditoria() + " | " + item.getNumeroProtocolo() + " | " + item.getNumeroProtocoloReferencia() + " | " + item.getData() + " | " + item.getNomeAssunto() + " | " + item.getNomeDepartamento() + " | " + item.getPesoDto().getPesoValido());
	}
	
//	@Test
	public void passivoConselheiroTest() {
		List<RelDetalhadoSiacol06Dto> lista = dao.getPassivoConselheiro(populaPesquisa(), mes);
		System.out.println("Id   | Protocolo  | Ref. | Data | Descrição Assunto | Departamento | Peso");
		lista.forEach(item -> {
			System.out.println(item.getIdAuditoria() + " | " + item.getNumeroProtocolo() + " | " + item.getNumeroProtocoloReferencia() + " | " + item.getData() + " | " + item.getNomeAssunto() + " | " + item.getNomeDepartamento() + " | " + item.getPesoDto().getPesoValido());
		});
	}
	
//	@Test
	public void passivoEntradaConselheiroTest() {
		List<RelDetalhadoSiacol06Dto> lista = dao.getPassivoEntradaConselheiro(populaPesquisa(), idsAssuntoSiacol);
		System.out.println("Id   | Protocolo  | Ref. | Data | Descrição Assunto | Departamento | Peso");
		lista.forEach(item -> {
			System.out.println(item.getIdAuditoria() + " | " + item.getNumeroProtocolo() + " | " + item.getNumeroProtocoloReferencia() + " | " + item.getData() + " | " + item.getNomeAssunto() + " | " + item.getNomeDepartamento() + " | " + item.getPesoDto().getPesoValido());
		});
	}
	
}
