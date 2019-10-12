package br.org.crea.siacol.builder.relatorio;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.protocolo.AssuntoConverter;
import br.org.crea.commons.dao.cadastro.domains.DepartamentoDao;
import br.org.crea.commons.dao.protocolo.AssuntoDao;
import br.org.crea.commons.dao.siacol.AssuntoSiacolDao;
import br.org.crea.commons.dao.siacol.relatorios.RelatorioSiacol09Dao;
import br.org.crea.commons.dao.siacol.relatorios.RelatorioSiacolCommonsDao;
import br.org.crea.commons.models.cadastro.Departamento;
import br.org.crea.commons.models.cadastro.dtos.DepartamentoDto;
import br.org.crea.commons.models.corporativo.Assunto;
import br.org.crea.commons.models.corporativo.dtos.AssuntoDto;
import br.org.crea.commons.models.siacol.AssuntoConfea;
import br.org.crea.commons.models.siacol.dtos.PesquisaRelatorioSiacolDto;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelSiacol09Dto;
import br.org.crea.commons.models.siacol.dtos.relatorios.StatusQtdDto;
import br.org.crea.commons.models.siacol.enuns.StatusProtocoloSiacol;

public class SiacolRelatorio09Builder {
	
	@Inject	RelatorioSiacol09Dao dao;
	@Inject	RelatorioSiacolCommonsDao commonsDao;
	@Inject AssuntoDao assuntoCorporativoDao;
	@Inject	AssuntoSiacolDao assuntoDao;
	@Inject	AssuntoConverter assuntoConverter;
	@Inject DepartamentoDao departamentoDao;
	
	public List<RelSiacol09Dto> relacaoDetalhadaPorTipoPendenteDeAnalise(PesquisaRelatorioSiacolDto pesquisa) {

		pesquisa.setAssuntos(assuntoConverter.toListDtoSiacol(assuntoDao.getAll()));
		pesquisa.getAssuntos().sort(Comparator.comparing(AssuntoDto::getCodigo));
		
		List<Long> listaIdsDepartamentos = new ArrayList<Long>();
		if (pesquisa.isTodosDepartamentos()) {
			for (Departamento departamento : departamentoDao.getAllDepartamentos("SIACOL")) {
				listaIdsDepartamentos.add(departamento.getId());
			}
		}else {
			for (DepartamentoDto departamento : pesquisa.getDepartamentos()) {
				listaIdsDepartamentos.add(departamento.getId());
			}
		}
		
		pesquisa.setListaIdsDepartamentos(listaIdsDepartamentos);
		
		if (pesquisa.temPerfilAnalista() && pesquisa.isSemAssunto()) {
			return populaRel09SemAssuntoSiacol(pesquisa);
		} else {
			return populaRel09ComAssuntoSiacol(pesquisa);
		}
	}
	
	private List<RelSiacol09Dto> populaRel09SemAssuntoSiacol(PesquisaRelatorioSiacolDto pesquisa) {
		
		List<RelSiacol09Dto> listaRelatorios = new ArrayList<RelSiacol09Dto>();
		List<Assunto> listaAssuntos = assuntoCorporativoDao.getAssuntoSiacolByIdPessoaRelatorioNove(pesquisa);
		Long[] listaStatus = new Long[]{1L, 2L, 4L};

		
		
		for (Assunto assunto : listaAssuntos) {
			RelSiacol09Dto relatorio = new RelSiacol09Dto();
			List<StatusQtdDto> colunasStatus = new ArrayList<StatusQtdDto>();
			
			relatorio.setAssunto(assunto.getCodigo() + " " + assunto.getDescricao());
			relatorio.setCodigoAssunto(assunto.getCodigo());
			relatorio.setDescricaoAssunto(assunto.getDescricao());
			
			AssuntoConfea assuntoConfea = commonsDao.populaAssuntoConfeaPeloAssuntoSiacol(assunto.getCodigo());
			if (assuntoConfea.getCodigo() != null) relatorio.setAssuntoConfea(assuntoConfea.getCodigo() + " - " + assuntoConfea.getNome());
			relatorio.setCodigoAssuntoConfea(assuntoConfea.getCodigo());
			relatorio.setDescricaoAssuntoConfea(assuntoConfea.getNome());
			
			for (Long idStatus : listaStatus) {
				StatusQtdDto status = new StatusQtdDto();
				status.setStatus(StatusProtocoloSiacol.getNomeBy(idStatus));
				status.setTotal(dao.qtdSemAssuntoSiacol(pesquisa, assunto.getId(), StatusProtocoloSiacol.getTipoBy(idStatus)));
				colunasStatus.add(status);
			}
			relatorio.setStatus(colunasStatus);
			listaRelatorios.add(relatorio);
		}
		
		listaRelatorios.add(linhaComTotais(listaRelatorios, listaStatus));
		
		return listaRelatorios;
	}

	
	private RelSiacol09Dto linhaComTotais(List<RelSiacol09Dto> listaRelatorios, Long[] listaStatus) {
		RelSiacol09Dto relatorio = new RelSiacol09Dto();
		List<StatusQtdDto> colunasStatus = new ArrayList<StatusQtdDto>();
		
		relatorio.setAssunto("TOTAL");
		
		for (Long idStatus : listaStatus) {
			int total = 0;
			StatusQtdDto status = new StatusQtdDto();
			status.setStatus(StatusProtocoloSiacol.getNomeBy(idStatus));
			for (RelSiacol09Dto rel : listaRelatorios) {
				for (StatusQtdDto statusDto : rel.getStatus()) {
					if (statusDto.getStatus().equals(status.getStatus())) {
						total += statusDto.getTotal();
					}
				}
			}
			status.setTotal(total);
			colunasStatus.add(status);
		}
		relatorio.setStatus(colunasStatus);
		return relatorio;
	}

	private List<RelSiacol09Dto> populaRel09PerfilAnalistaComAssunto(PesquisaRelatorioSiacolDto pesquisa) {
		List<RelSiacol09Dto> listaRelatorio = new ArrayList<RelSiacol09Dto>();
		List<AssuntoDto> assuntos = pesquisa.getAssuntos();
		Long[] listaStatus = new Long[]{1L, 2L, 4L, 5L, 6L, 7L, 11L, 37L, 47L, 48L, 52L, 53L, 54L};
		
		for (AssuntoDto assunto : assuntos) {
			RelSiacol09Dto linhaAssunto = new RelSiacol09Dto();
			
			linhaAssunto.setAssunto(assunto.getCodigo() + " - " + assunto.getNome());
			linhaAssunto.setCodigoAssunto(assunto.getCodigo());
			linhaAssunto.setDescricaoAssunto(assunto.getNome());
			
			AssuntoConfea assuntoConfea = commonsDao.populaAssuntoConfeaPeloAssuntoSiacol(assunto.getCodigo());
			if (assuntoConfea.getCodigo() != null) linhaAssunto.setAssuntoConfea(assuntoConfea.getCodigo() + " - " + assuntoConfea.getNome());
			linhaAssunto.setCodigoAssuntoConfea(assuntoConfea.getCodigo());
			linhaAssunto.setDescricaoAssuntoConfea(assuntoConfea.getNome());
			
			List<StatusQtdDto> colunasStatus = new ArrayList<StatusQtdDto>();
			
			for (Long idStatus : listaStatus) {
				StatusQtdDto status = new StatusQtdDto();
				status.setStatus(StatusProtocoloSiacol.getNomeBy(idStatus));
				status.setTotal(dao.qtdProtocolosPorAssuntoStatusEDepartamento(pesquisa, assunto.getId(), StatusProtocoloSiacol.getTipoBy(idStatus)));
				colunasStatus.add(status);
			}
		
			linhaAssunto.setStatus(colunasStatus);
			listaRelatorio.add(linhaAssunto);
	    }
		

		listaRelatorio.add(linhaComTotais(listaRelatorio, listaStatus));
		
		return listaRelatorio;
	}
	
	private List<RelSiacol09Dto> populaRel09PerfilConselheiroComAssunto(PesquisaRelatorioSiacolDto pesquisa) {
		
		List<RelSiacol09Dto> listaRelatorio = new ArrayList<RelSiacol09Dto>();
		List<AssuntoDto> assuntos = pesquisa.getAssuntos();
        // DISTRIBUICAO, PEDIDO_DE_VISTAS, ANALISE_VISTAS, ANALISE_VISTAS_VOTADO, ANALISE_VISTAS_A_PAUTAR, ANALISE_CONSELHEIRO_AD_REFERENDUM
		Long[] listaStatus = new Long[]{3L, 24L, 25L, 32L, 34L, 51L};
		
		for (AssuntoDto assunto : assuntos) {
			RelSiacol09Dto linhaAssunto = new RelSiacol09Dto();
			
			linhaAssunto.setAssunto(assunto.getCodigo() + " - " +  assunto.getNome());
			linhaAssunto.setCodigoAssunto(assunto.getCodigo());
			linhaAssunto.setDescricaoAssunto(assunto.getNome());
			
			AssuntoConfea assuntoConfea = commonsDao.populaAssuntoConfeaPeloAssuntoSiacol(assunto.getCodigo());
			if (assuntoConfea.getCodigo() != null) linhaAssunto.setAssuntoConfea(assuntoConfea.getCodigo() + " - " + assuntoConfea.getNome());
			linhaAssunto.setCodigoAssuntoConfea(assuntoConfea.getCodigo());
			linhaAssunto.setDescricaoAssuntoConfea(assuntoConfea.getNome());
			
			List<StatusQtdDto> colunasStatus = new ArrayList<StatusQtdDto>();

			for (Long idStatus : listaStatus) {
				StatusQtdDto status = new StatusQtdDto();
				status.setStatus(StatusProtocoloSiacol.getNomeBy(idStatus));
				status.setTotal(dao.qtdProtocolosPorAssuntoStatusEDepartamento(pesquisa, assunto.getId(), StatusProtocoloSiacol.getTipoBy(idStatus)));
				colunasStatus.add(status);
			}
			
			linhaAssunto.setStatus(colunasStatus);
			listaRelatorio.add(linhaAssunto);
		}
		
		listaRelatorio.add(linhaComTotais(listaRelatorio, listaStatus));
		
		return listaRelatorio;
	}
	
	private List<RelSiacol09Dto> populaRel09ComAssuntoSiacol(PesquisaRelatorioSiacolDto pesquisa) {
		
		List<RelSiacol09Dto> listaRelatorio = new ArrayList<RelSiacol09Dto>();
		
		if (pesquisa.temPerfilAnalista()) {
			listaRelatorio = populaRel09PerfilAnalistaComAssunto(pesquisa);
		}
		 
		if (pesquisa.temPerfilConselheiro()) {
			listaRelatorio = populaRel09PerfilConselheiroComAssunto(pesquisa);
		}
		 
		return listaRelatorio;
	}
}
