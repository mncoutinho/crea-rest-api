package br.org.crea.restapi.siacol.relatorio.rel07;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.dao.siacol.relatorios.RelatorioSiacol07Dao;
import br.org.crea.commons.models.cadastro.dtos.DepartamentoDto;
import br.org.crea.commons.models.corporativo.dtos.PessoaDto;
import br.org.crea.commons.models.siacol.dtos.PesquisaRelatorioSiacolDto;
import br.org.crea.commons.models.siacol.dtos.relatorios.ProtocolosRel07Dto;
import br.org.crea.commons.models.siacol.dtos.relatorios.ProtocolosRel07RecebimentoDto;
import br.org.crea.commons.models.siacol.dtos.relatorios.StatusRel07;
import br.org.crea.commons.util.DateUtils;

public class Rel07_03PausadoTest {

	static RelatorioSiacol07Dao dao;
	private static EntityManager em = null;
	
	@Before
	public void inicio() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dsCreaTest");
		em = factory.createEntityManager();
		dao = new RelatorioSiacol07Dao();
		dao.setEntityManager(em);
	}
	
	@After
	public void fim() {
		em.close();
	}
	
	private PesquisaRelatorioSiacolDto populaPesquisa() {
		PesquisaRelatorioSiacolDto pesquisa = new PesquisaRelatorioSiacolDto();
		
		List<PessoaDto> responsaveis = new ArrayList<PessoaDto>();
		PessoaDto responsavel = new PessoaDto();
		responsavel.setId(1983103508L);
		responsavel.setPerfil("ANALISTA");
		responsaveis.add(responsavel);
		pesquisa.setResponsaveis(responsaveis);

		List<DepartamentoDto> departamentos = new ArrayList<DepartamentoDto>();
		DepartamentoDto departamento = new DepartamentoDto();
		departamento.setId(1205L);
		departamento.setNome("CAMARA CIVIL");
		departamentos.add(departamento);
		pesquisa.setDepartamentos(departamentos);
		
		pesquisa.setAno("2018");
		List<String> meses = new ArrayList<String>();
		meses.add("11");
		pesquisa.setMeses(meses);
		
		return pesquisa;
	}
	
	@Test
	public void populaPausadoTest() {
		
		try {
			List<String> listaRecebimento = new ArrayList<String>();
			listaRecebimento.add("201870001234");
			StatusRel07 status = populaPausadoAnalista(populaPesquisa(), listaRecebimento);
			
			System.out.println(status.getNome());
        	
        	status.getListaProtocolos().forEach(protocolo -> {
        		System.out.println(protocolo.getNumeroProtocolo() + " | " + protocolo.getDiferencaEmDias());
        	});  
        	
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	private StatusRel07 populaPausadoAnalista(PesquisaRelatorioSiacolDto pesquisa, List<String> listaRecebimento) {
		StatusRel07 status = new StatusRel07();
		status.setNome("PAUSADO");
		
		Date hoje = new Date();
		List<ProtocolosRel07Dto> listaProtocolos = new ArrayList<ProtocolosRel07Dto>();
		Date dataFimPausa = null;
		
		List<ProtocolosRel07RecebimentoDto> listaEntradaPausados = dao.getEntradaPausadosAnalista(pesquisa, listaRecebimento);
		
		for (ProtocolosRel07RecebimentoDto entradaPausado : listaEntradaPausados) {
			
//			ProtocolosRel07RecebimentoDto pausado = dao.getSaidaPausadosAnalista(pesquisa, entradaPausado);
//			ProtocolosRel07Dto protocolo = new ProtocolosRel07Dto();
//			protocolo.setNumeroProtocolo(entradaPausado.getNumeroProtocolo());
//			
//			if (pausado != null) {
//				dataFimPausa = pausado.getData();
//			} else {
//				dataFimPausa = hoje;
//			}
//			Date dataInicioPausa = entradaPausado.getData();
//			Long diferenca = DateUtils.getDiferencaDiasEntreDatas(dataInicioPausa, dataFimPausa);
//			protocolo.setDiferencaEmDias(diferenca.intValue());
		}
		
		status.setListaProtocolos(listaProtocolos);
		
		return status;
	}
}
