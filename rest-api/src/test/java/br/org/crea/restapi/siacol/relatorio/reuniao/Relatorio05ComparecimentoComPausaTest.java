package br.org.crea.restapi.siacol.relatorio.reuniao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.dao.siacol.RelatorioReuniaoSiacolDao;
import br.org.crea.commons.models.siacol.PresencaReuniaoSiacol;
import br.org.crea.commons.models.siacol.dtos.PresencaReuniaoSiacolDto;
import br.org.crea.commons.models.siacol.dtos.RelatorioReuniaoSiacolDto;
import br.org.crea.commons.util.DateUtils;

public class Relatorio05ComparecimentoComPausaTest {

	static RelatorioReuniaoSiacolDao dao;
	private static EntityManager em = null;
	
	@Before
	public void inicio() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dsCreaTest");
		em = factory.createEntityManager();
		dao = new RelatorioReuniaoSiacolDao();
		dao.setEntityManager(em);
	}
	
	@After
	public void fim() {
		em.close();
	}
	
//	@Test
	public void getParticipantesComPresencaNaReuniaoComPartesTest() {
		
		Long idReuniao = 23050L;
		List<PresencaReuniaoSiacolDto> listaPresenca = dao.getParticipantesComPresencaNaReuniaoComPartes(idReuniao);

		listaPresenca.forEach(participante -> {
			System.out.println(participante.getPessoa().getId() + " | " + participante.getPessoa().getNome());
		});
	}
	
//	@Test
	public void getQuantidadeVotadoTest() {
		Long idReuniao = 23050L;
		Long idPessoa = 1980101726L;
		System.out.println(dao.getQuantidadeVotado(idReuniao, idPessoa));
	}
	
//	@Test
	public void buscaPresencasDaReuniaoTest() {
		Long idReuniao = 23050L;
		Long idPessoa = 1980101726L;
		System.out.println(dao.buscaPresencasDaReuniaoPosDezenoveHoras(idReuniao, idPessoa));
	}
	
//	@Test
	public void getValorDiariaTest() {
		Long idPessoa = 1980101726L;
		System.out.println(dao.getValorDiaria(idPessoa));
	}
	
//	@Test
	public void getValorJetonTest() {
		Long idPessoa = 1980101726L;
		System.out.println(dao.getValorJeton(idPessoa));
	}
	
	@Test
	public void relatorioTest() {
		
		try {
			Long idReuniao = 23050L;
			RelatorioReuniaoSiacolDto relatorio = dao.relatorioDeComparecimentoComPausa(idReuniao, 3);
						
			relatorio.getRelatorio().forEach(item -> {
				System.out.println("Parte: " + item.getParte());
				if (item.getParte().equals("0")) {
					System.out.println("Nome | Qtd Votado | Diaria | Jeton | Soma");
					item.getRelatorio().forEach(subItem -> {
						System.out.println(subItem.getNome() + " | " + subItem.getQtdVotado() + " | " + subItem.getDiaria()
						+ " | " + subItem.getJeton() + " | " + subItem.getSoma());
					});
				} else {
					System.out.println("Nome | Entrada | Saida | Tempo Presente");
					item.getRelatorio().forEach(subItem -> {
						System.out.println(subItem.getNome() + " | " + subItem.getEntrada() + " | " + subItem.getSaida()
						+ " | " + subItem.getTempoPresente());
					});
				}
				
			});
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	public RelatorioReuniaoSiacolDto relatorioDeComparecimentoComPausa(Long idReuniao, int qtdPartesDaReuniao) {
		
		RelatorioReuniaoSiacolDto relatorio = new RelatorioReuniaoSiacolDto();
		relatorio.setRelatorio(new ArrayList<RelatorioReuniaoSiacolDto>());
			
		RelatorioReuniaoSiacolDto dto = new RelatorioReuniaoSiacolDto();
		dto.setRelatorio(new ArrayList<RelatorioReuniaoSiacolDto>());
				
		dto.setParte("0");
				
		List<PresencaReuniaoSiacolDto> listaPresencas = dao.getParticipantesComPresencaNaReuniaoComPartes(idReuniao);
		
		for (PresencaReuniaoSiacolDto participante : listaPresencas) {
			RelatorioReuniaoSiacolDto dtoDetalheParticipante = new RelatorioReuniaoSiacolDto();
			dtoDetalheParticipante.setNome(participante.getPessoa().getNome());
			dtoDetalheParticipante.setQtdVotado(String.valueOf(dao.getQuantidadeVotado(idReuniao, participante.getPessoa().getId())));
		
			BigDecimal qtdPartesQueAtingiuOitentaPorcento = dao.buscaPresencasDaReuniaoPosDezenoveHoras(idReuniao, participante.getPessoa().getId());
			
			if (idReuniao.equals(1307L) || idReuniao.equals(1310L) || qtdPartesQueAtingiuOitentaPorcento.equals(new BigDecimal("0"))) {
				relatorio.setDiaria(new BigDecimal("0.0"));
				relatorio.setJeton(new BigDecimal("0.0"));
			} else {
				BigDecimal diaria = dao.getValorDiaria(participante.getPessoa().getId());
				BigDecimal jeton = dao.getValorJeton(participante.getPessoa().getId());
				relatorio.setDiaria(qtdPartesQueAtingiuOitentaPorcento.multiply(diaria));
				relatorio.setJeton(qtdPartesQueAtingiuOitentaPorcento.multiply(jeton));
			}
			
			relatorio.setSoma(relatorio.getDiaria().add(relatorio.getSoma()));
					
			dto.getRelatorio().add(dtoDetalheParticipante);
		}
		relatorio.getRelatorio().add(dto);

				
		// OBTEM NOME, ENTRADA, SAIDA, TEMPO_PRESENTE DE CADA PARTE DA REUNIAO
		for (int parte = 1; parte < qtdPartesDaReuniao; parte++) {
			
			dto = new RelatorioReuniaoSiacolDto();
			dto.setRelatorio(new ArrayList<RelatorioReuniaoSiacolDto>());
					
			dto.setParte(String.valueOf(parte));
			
			List<PresencaReuniaoSiacol> listaPresenca = dao.getParticipantesComPresencaNaReuniaoComParte(idReuniao, new Long(parte));
			
			for (PresencaReuniaoSiacol participante : listaPresenca) {
				RelatorioReuniaoSiacolDto dtoDetalheParticipante = new RelatorioReuniaoSiacolDto();
				dtoDetalheParticipante.setNome(participante.getPessoa().getNome());
				dtoDetalheParticipante.setEntrada(DateUtils.format(participante.getHoraEntregaCracha(), DateUtils.HH_MM));
				dtoDetalheParticipante.setSaida(DateUtils.format(participante.getHoraDevolucaoCracha(), DateUtils.HH_MM));
				dtoDetalheParticipante.setTempoPresente(dao.getTempoPresenteNaReuniaoComParte(idReuniao, participante.getPessoa().getId(), parte));
				dto.getRelatorio().add(dtoDetalheParticipante);
			}
					
			relatorio.getRelatorio().add(dto);
		}	
			
		return relatorio;
	}

}