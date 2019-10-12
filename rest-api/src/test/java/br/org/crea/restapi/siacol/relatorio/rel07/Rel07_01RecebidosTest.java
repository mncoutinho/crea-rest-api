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

public class Rel07_01RecebidosTest {

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
		responsavel.setId(2000105584L);
		responsavel.setPerfil("siacolanalista");
		responsaveis.add(responsavel);
		pesquisa.setResponsaveis(responsaveis);

		List<DepartamentoDto> departamentos = new ArrayList<DepartamentoDto>();
		DepartamentoDto departamento = new DepartamentoDto();
		departamento.setId(1208L);
		departamento.setNome("CAMARA QUIMICA");
		departamentos.add(departamento);
		pesquisa.setDepartamentos(departamentos);
		
		pesquisa.setAno("2018");
		List<String> meses = new ArrayList<String>();
		meses.add("12");
		pesquisa.setMeses(meses);
		
		return pesquisa;
	}
	
//	@Test
	public void rel07DistribuicaoPorOutroUsuarioTest() {
		Long idAssuntoSiacol = 52L;
		List<ProtocolosRel07RecebimentoDto> listaDistribuicaoOutroUsuario = dao.getDistribuicaoPorOutroUsuario(populaPesquisa(), idAssuntoSiacol);
			
		System.out.println("Qtd Distribuicao Outro Usuário: " + listaDistribuicaoOutroUsuario.size());
		listaDistribuicaoOutroUsuario.forEach(recebimento -> {
			System.out.println(recebimento.getIdAuditoria() + " | " + recebimento.getNumeroProtocolo() + " | " + recebimento.getData());
		});
	}
	
//	@Test
	public void rel07RecebimentoSiacolTest() {
		Long idAssuntoSiacol = 52L;
		List<ProtocolosRel07RecebimentoDto> listaRecebimentoSiacol = dao.getRecebimentoSiacol(populaPesquisa(), idAssuntoSiacol);
			
		System.out.println("Qtd Recebimento Siacol: " + listaRecebimentoSiacol.size());
		listaRecebimentoSiacol.forEach(recebimento -> {
			System.out.println(recebimento.getIdAuditoria() + " | " + recebimento.getNumeroProtocolo() + " | " + recebimento.getData());
		});
	}
	
	@Test
	public void rel07RecebidoAnalistaTest() {
		
		try {
			PesquisaRelatorioSiacolDto pesquisa = populaPesquisa();
			
			Long idAssuntoSiacol = 51L;
			StatusRel07 status = populaRecebidosAnalista(pesquisa, idAssuntoSiacol);
			
			System.out.println(status.getNome());
			status.getListaProtocolos().forEach(protocolo -> {
				System.out.println(protocolo.getNumeroProtocolo() + " | " + protocolo.getDiferencaEmDias());
			});

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	private StatusRel07 populaRecebidosAnalista(PesquisaRelatorioSiacolDto pesquisa, Long idAssuntoSiacol) {
		StatusRel07 status = new StatusRel07();
		status.setNome("RECEBER");
		
		List<ProtocolosRel07Dto> listaProtocolos = new ArrayList<ProtocolosRel07Dto>();
		Date dataRecebimento = new Date();
		Date dataInicioRecebimento = new Date();
		
		List<ProtocolosRel07RecebimentoDto> listaRecebimentos = new ArrayList<ProtocolosRel07RecebimentoDto>();
			
		// nao recebido, sofreu intervencao (usuario remetente <> usuario que executou ação na auditoria e evento distribuicao)
		List<ProtocolosRel07RecebimentoDto> listaDistribuicaoOutroUsuario = dao.getDistribuicaoPorOutroUsuario(pesquisa, idAssuntoSiacol);
		listaRecebimentos.addAll(listaDistribuicaoOutroUsuario);
		
		List<ProtocolosRel07RecebimentoDto> listaRecebimentoSiacol = dao.getRecebimentoSiacol(pesquisa, idAssuntoSiacol);
		listaRecebimentos.addAll(listaRecebimentoSiacol);
	
		for (ProtocolosRel07RecebimentoDto recebimento : listaRecebimentos) {
			ProtocolosRel07Dto protocolo = new ProtocolosRel07Dto();
			protocolo.setNumeroProtocolo(recebimento.getNumeroProtocolo());
			dataRecebimento = recebimento.getData();
			
			
			ProtocolosRel07RecebimentoDto distribuicao = dao.getDistribuicaoAnalista(pesquisa, recebimento.getNumeroProtocolo(), recebimento.getIdAuditoria());
			
			if (distribuicao == null) {
				ProtocolosRel07RecebimentoDto tramiteEnvio = dao.getTramiteEnvioAnalista(pesquisa, recebimento.getNumeroProtocolo(), recebimento.getIdAuditoria());
				dataInicioRecebimento = tramiteEnvio.getData();
			} else {
				dataInicioRecebimento = distribuicao.getData();
			}
			
			Long diferenca = DateUtils.getDiferencaDiasEntreDatas(dataInicioRecebimento, dataRecebimento);
			protocolo.setDiferencaEmDias(diferenca.intValue());
			listaProtocolos.add(protocolo);
		}
		
		List<ProtocolosRel07RecebimentoDto> listaRecebimentoRel07 = listaRecebimentos;
		
		status.setListaProtocolos(listaProtocolos);
		
		return status;
	}
}
