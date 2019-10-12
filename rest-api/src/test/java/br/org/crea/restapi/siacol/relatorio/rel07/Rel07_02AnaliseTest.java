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
import br.org.crea.commons.util.ListUtils;

public class Rel07_02AnaliseTest {

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
		responsavel.setId(1985103413L);
		responsavel.setPerfil("ANALISTA");
		responsaveis.add(responsavel);
		pesquisa.setResponsaveis(responsaveis);

		List<DepartamentoDto> departamentos = new ArrayList<DepartamentoDto>();
		DepartamentoDto departamento = new DepartamentoDto();
		departamento.setId(1206L);
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
	public void populaAnaliseTest() {
		
		try {
			Long idAssuntoSiacol = 52L;
			List<String> listaProtocolosRecebimento = new ArrayList<String>();
			listaProtocolosRecebimento.add("2018200355");
			StatusRel07 status = populaAnaliseAnalista(populaPesquisa(), idAssuntoSiacol, listaProtocolosRecebimento);
			
        	System.out.println(status.getNome());
        	
        	status.getListaProtocolos().forEach(protocolo -> {
        		System.out.println(protocolo.getNumeroProtocolo() + " | " + protocolo.getDiferencaEmDias());
        	});        	

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	private StatusRel07 populaAnaliseAnalista(PesquisaRelatorioSiacolDto pesquisa, Long idAssuntoSiacol, List<String> listaProtocolosRecebimento) {
		StatusRel07 status = new StatusRel07();
		status.setNome("ANÁLISE");
		
		Date hoje = new Date();
		Date dataFimAnalise = null;
		List<ProtocolosRel07Dto> listaProtocolos = new ArrayList<ProtocolosRel07Dto>();
		
		listaProtocolosRecebimento = (List<String>) ListUtils.removerDuplicidade(listaProtocolosRecebimento);
		
		List<ProtocolosRel07RecebimentoDto> listaEntradaAnalise = dao.getEntradaAnaliseAnalista(pesquisa, listaProtocolosRecebimento);
		
		for (ProtocolosRel07RecebimentoDto recebimentoAnalise : listaEntradaAnalise) { 
			ProtocolosRel07Dto protocolo = new ProtocolosRel07Dto();
			protocolo.setNumeroProtocolo(recebimentoAnalise.getNumeroProtocolo());
			
			ProtocolosRel07RecebimentoDto saidaAnalise = dao.getSaidaAnaliseAnalista(pesquisa, idAssuntoSiacol, recebimentoAnalise);
			
			if (saidaAnalise != null) {
				dataFimAnalise = saidaAnalise.getData();
				
			} else {
				dataFimAnalise = hoje;
			}
			Date dataInicioAnalise = recebimentoAnalise.getData();
			Long diferenca = DateUtils.getDiferencaDiasEntreDatas(dataInicioAnalise, dataFimAnalise);
			protocolo.setDiferencaEmDias(diferenca.intValue());
			listaProtocolos.add(protocolo);
		}
		
		// FIXME somar o tempo de análise geral para cada protocolo, sem duplicidade
		
		status.setListaProtocolos(listaProtocolos);
		
		return status;
	}
}
