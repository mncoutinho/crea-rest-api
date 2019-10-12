package br.org.crea.restapi.siacol.relatorio;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.converter.cadastro.domains.DepartamentoConverter;
import br.org.crea.commons.converter.protocolo.AssuntoConverter;
import br.org.crea.commons.dao.cadastro.domains.DepartamentoDao;
import br.org.crea.commons.dao.siacol.AssuntoSiacolDao;
import br.org.crea.commons.dao.siacol.relatorios.RelatorioSiacol05Dao;
import br.org.crea.commons.models.cadastro.dtos.DepartamentoDto;
import br.org.crea.commons.models.corporativo.dtos.AssuntoDto;
import br.org.crea.commons.models.siacol.dtos.PesquisaRelatorioSiacolDto;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelDepartamentosSiacolDto;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelSiacol05Dto;
import br.org.crea.commons.models.siacol.enuns.StatusProtocoloSiacol;
import br.org.crea.siacol.builder.relxls.SiacolRelXlsBuilder;

public class Relatorio05DetalhamentoDoSaldoAcumuladoPorDepartamentoMesEAssuntoTest {

	static RelatorioSiacol05Dao dao;
	static DepartamentoDao departamentoDao;
	static DepartamentoConverter departamentoConverter;
	static AssuntoConverter assuntoConverter;
	static AssuntoSiacolDao assuntoDao;
	private static EntityManager em = null;
	private SiacolRelXlsBuilder builder;
	
	
	@Before
	public void inicio() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dsCreaTest");
		em = factory.createEntityManager();
		builder = new SiacolRelXlsBuilder();
		dao = new RelatorioSiacol05Dao();
		departamentoDao = new DepartamentoDao();
		departamentoConverter = new DepartamentoConverter();
		assuntoConverter = new AssuntoConverter();
		assuntoDao = new AssuntoSiacolDao();
		dao.setEntityManager(em);
		departamentoDao.setEntityManager(em);
		assuntoDao.setEntityManager(em);
	}
	
	@After
	public void fim() {
		em.close();
	}
	
	private PesquisaRelatorioSiacolDto populaPesquisa() {
		PesquisaRelatorioSiacolDto pesquisa = new PesquisaRelatorioSiacolDto();
		
		List<DepartamentoDto> departamentos = new ArrayList<DepartamentoDto>();
		DepartamentoDto departamento = new DepartamentoDto();
		departamento.setId(1208L);
		departamento.setNome("CAMARA QUIMICA");
		departamentos.add(departamento);
		pesquisa.setDepartamentos(departamentos);
		
		List<AssuntoDto> assuntos = new ArrayList<AssuntoDto>();
		AssuntoDto assunto = new AssuntoDto();
		assunto.setId(47L);
		assunto.setNome("X");
		assuntos.add(assunto);
		pesquisa.setAssuntos(assuntos);
		
		pesquisa.setTodosAssuntos(true);
		pesquisa.setTodosDepartamentos(false);
		
		return pesquisa;
	}
	
//	@Test
	public void qtdPorStatusDepartamentoEAssuntoTest() {
		Long idDepartamento = 1208L;
		String status = "AGUARDANDO_RECEBIMENTO";
		List<Long> idsAssuntoSiacol = new ArrayList<Long>();
		Long idAssuntoSiacol = 51L;
		idsAssuntoSiacol.add(idAssuntoSiacol);
		System.out.println("Qtd: " + dao.qtdPorStatusDepartamentoEAssuntos(idDepartamento, status, idsAssuntoSiacol));
	}
	
	@Test
	public void relatorioTest() {
		
		try {
			List<RelSiacol05Dto> listaRelatorio = detalhamentoDoSaldoAcumuladoPorDepartamentoMesEAssunto(populaPesquisa());
			
			listaRelatorio.forEach(linhaStatus -> {
				System.out.println("Status: " + linhaStatus.getStatus());
				linhaStatus.getDepartamentos().forEach(departamento -> {
					System.out.println(departamento.getNome() + " | " + departamento.getQtd());
				});
				System.out.println("Total: " + linhaStatus.getTotal());
			});
			
			File file = new File("/opt/temp/rel05.xls");
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
			bos.write(builder.rel05(listaRelatorio));
			bos.close();

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public List<RelSiacol05Dto> detalhamentoDoSaldoAcumuladoPorDepartamentoMesEAssunto(PesquisaRelatorioSiacolDto pesquisa) {

		List<DepartamentoDto> departamentos = pesquisa.getDepartamentos();
		List<AssuntoDto> assuntos = pesquisa.getAssuntos();
		
		if (pesquisa.isTodosDepartamentos()) {
			departamentos = departamentoConverter.toListDto(departamentoDao.getAllDepartamentos("SIACOL")); 
		}
		
		if (pesquisa.isTodosAssuntos()) {
			assuntos = assuntoConverter.toListDtoSiacol(assuntoDao.getAllAssuntos());
		}
		
		List<Long> idsAssuntoSiacol = populaListaIdsAssuntos(assuntos);
		StatusProtocoloSiacol[] listaStatus = StatusProtocoloSiacol.class.getEnumConstants();
		
		List<RelSiacol05Dto> listaRelatorio = new ArrayList<RelSiacol05Dto>();
		

		for (StatusProtocoloSiacol status : listaStatus) {
		
			RelSiacol05Dto relatorio = new RelSiacol05Dto();
			relatorio.setStatus(status.getDescricao());
			List<RelDepartamentosSiacolDto> relDepartamentos = new ArrayList<RelDepartamentosSiacolDto>();
			
			for (DepartamentoDto departamento : departamentos) {
				RelDepartamentosSiacolDto relDepartamento = new RelDepartamentosSiacolDto();
				relDepartamento.setNome(departamento.getNome());
				relDepartamento.setQtd(dao.qtdPorStatusDepartamentoEAssuntos(departamento.getId(), status.getTipo(), idsAssuntoSiacol));
				
				relDepartamentos.add(relDepartamento);
			}
			
			relatorio.setDepartamentos(relDepartamentos);
			
			int total = 0;
			
			for (RelDepartamentosSiacolDto dept : relDepartamentos) {
				total += dept.getQtd();
			}
			
			relatorio.setTotal(total);
			
			listaRelatorio.add(relatorio);
		}
		
		return listaRelatorio;
	}
	
	public List<Long> populaListaIdsAssuntos(List<AssuntoDto> assuntos) {
		List<Long> idsAssuntos = new ArrayList<Long>();
		
		for (AssuntoDto assunto : assuntos) {
			idsAssuntos.add(assunto.getId());
		}
		return idsAssuntos;
	}

}
