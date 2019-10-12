package br.org.crea.siacol.service;

import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.dao.siacol.relatorios.RelatorioSiacol01Dao;
import br.org.crea.commons.dao.siacol.relatorios.RelatorioSiacol02Dao;
import br.org.crea.commons.dao.siacol.relatorios.RelatorioSiacol03Dao;
import br.org.crea.commons.dao.siacol.relatorios.RelatorioSiacol04Dao;
import br.org.crea.commons.dao.siacol.relatorios.RelatorioSiacol05Dao;
import br.org.crea.commons.dao.siacol.relatorios.RelatorioSiacol06Dao;
import br.org.crea.commons.dao.siacol.relatorios.RelatorioSiacol07Dao;
import br.org.crea.commons.dao.siacol.relatorios.RelatorioSiacol08Dao;
import br.org.crea.commons.dao.siacol.relatorios.RelatorioSiacol09Dao;
import br.org.crea.commons.dao.siacol.relatorios.RelatorioSiacol10Dao;
import br.org.crea.commons.dao.siacol.relatorios.RelatorioSiacol11Dao;
import br.org.crea.commons.dao.siacol.relatorios.RelatorioSiacol12Dao;
import br.org.crea.commons.dao.siacol.relatorios.RelatorioSiacol13Dao;
import br.org.crea.commons.dao.siacol.relatorios.RelatorioSiacol14Dao;
import br.org.crea.commons.models.siacol.dtos.PesquisaRelatorioSiacolDto;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelDetalhadoSiacol04Dto;
import br.org.crea.commons.util.ResponseRestApi;
import br.org.crea.siacol.builder.relatorio.SiacolRelatorio06Builder;
import br.org.crea.siacol.builder.relatorio.SiacolRelatorio07Builder;
import br.org.crea.siacol.builder.relatorio.SiacolRelatorio09Builder;
import br.org.crea.siacol.builder.relatorio.SiacolRelatorio10Builder;
import br.org.crea.siacol.builder.relxls.SiacolRelXlsBuilder;

public class RelatorioSiacolService {

	@Inject	ResponseRestApi responseApi;
	@Inject	RelatorioSiacol01Dao relatorio01Dao;
	@Inject	RelatorioSiacol02Dao relatorio02Dao;
	@Inject	RelatorioSiacol03Dao relatorio03Dao;
	@Inject	RelatorioSiacol04Dao relatorio04Dao;
	@Inject	RelatorioSiacol05Dao relatorio05Dao;
	@Inject	RelatorioSiacol06Dao relatorio06Dao;
	@Inject	RelatorioSiacol07Dao relatorio07Dao;
	@Inject	RelatorioSiacol08Dao relatorio08Dao;
	@Inject	RelatorioSiacol09Dao relatorio09Dao;
	@Inject	RelatorioSiacol10Dao relatorio10Dao;
	@Inject	RelatorioSiacol11Dao relatorio11Dao;
	@Inject	RelatorioSiacol12Dao relatorio12Dao;
	@Inject	RelatorioSiacol13Dao relatorio13Dao;
	@Inject	RelatorioSiacol14Dao relatorio14Dao;
	@Inject	SiacolRelatorio06Builder relatorio06Builder;
	@Inject	SiacolRelatorio07Builder relatorio07Builder;
	@Inject	SiacolRelatorio09Builder relatorio09Builder;
	@Inject	SiacolRelatorio10Builder relatorio10Builder;
	@Inject	SiacolRelXlsBuilder builderXls;

	public ResponseRestApi geraRelatorio(PesquisaRelatorioSiacolDto pesquisa) {

		switch (pesquisa.getTipo()) {

		case REL_01:
			responseApi.success().data(relatorio01Dao.quantidadeDeProtocolosPorDepartamentosEPorAno(pesquisa)).build();
			break;
		case REL_02:
			responseApi.success().data(relatorio02Dao.quantidadeDeProtocolosJulgadosPorDepartamentoAssuntoEClassificacao(pesquisa)).build();
			break;
		case REL_03:
			responseApi.success().data(relatorio03Dao.quantidadeDeProtocolosJulgadosPorEventoDepartamentosEPorMesAno(pesquisa)).build();
			break;
		case REL_04:
			responseApi.success().data(relatorio04Dao.quantidadeDeProtocolosJulgadosENaoJulgadosPorAssuntoEDepartamentosEPorMesAno(pesquisa)).build();
			break;
		case REL_05:
			responseApi.success().data(relatorio05Dao.detalhamentoDoSaldoAcumuladoPorDepartamentoMesEAssunto(pesquisa)).build();
			break;
		case REL_06:
			responseApi.success().data(relatorio06Builder.quantidadeDeProtocolosPorAnalistaOuConselheiroPorMes(pesquisa)).build();
			break;
		case REL_07: // FIXME colocar relatorio 7 no service e somente as queries no dao, fazer o mesmo para os demais
			responseApi.success().data(relatorio07Builder.detalhamentoDoHistoricoDeProducaoDaQuantidadeDeProtocolosPorAnalistaOuConselheiroEPorAssunto(pesquisa)).build();
			break;
		case REL_08:
			responseApi.success().data("").build();
			break;
		case REL_09:
			responseApi.success().data(relatorio09Builder.relacaoDetalhadaPorTipoPendenteDeAnalise(pesquisa)).build();
			break;
		case REL_10:
			responseApi.success().data(relatorio10Builder.detalhamentoDoHistoricoDeProducaoDaQuantidadeDeProtocolosPorAnalistaOuConselheiroEPorAssunto(pesquisa)).build();
			break;
		case REL_11:
			responseApi.success().data(relatorio11Dao.vidaDoProtocolo(pesquisa)).build();
			break;
		case REL_12:
			responseApi.success().data(relatorio12Dao.somatorioDoTotalDeDecisoesPorDepartamento(pesquisa)).build();
			break;
		case REL_13:
			responseApi.success().data(relatorio13Dao.relatorioDeProtocolosAssuntosSiacolECorporativoPorDepartamento(pesquisa)).build();
			break;
		case REL_14:
			responseApi.success().data(relatorio14Dao.quantidadeMensalDeProtocolosJulgadosNasReunioesPresenciaisEVirtuaisPorDepartamento(pesquisa)).build();
			break;
		default:
			break;
		}

		return responseApi;
	}

	public byte[] geraRelatorioXls(PesquisaRelatorioSiacolDto pesquisa) {

		byte[] rel = null;

		switch (pesquisa.getTipo()) {

		case REL_02:
			rel = builderXls.rel02(relatorio02Dao.quantidadeDeProtocolosJulgadosPorDepartamentoAssuntoEClassificacao(pesquisa));
			break;
		case REL_03:
			rel = builderXls.rel03(relatorio03Dao.quantidadeDeProtocolosJulgadosPorEventoDepartamentosEPorMesAno(pesquisa));
			break;
		case REL_04:
			rel = builderXls.rel04(relatorio04Dao.quantidadeDeProtocolosJulgadosENaoJulgadosPorAssuntoEDepartamentosEPorMesAno(pesquisa));
			break;
		case REL_05:
			rel = builderXls.rel05(relatorio05Dao.detalhamentoDoSaldoAcumuladoPorDepartamentoMesEAssunto(pesquisa));
			break;
		case REL_06:
			rel = builderXls.rel06(relatorio06Builder.quantidadeDeProtocolosPorAnalistaOuConselheiroPorMes(pesquisa));
			break;
		case REL_07:
			//FIXME FALTA
			responseApi.success().data(relatorio07Builder.detalhamentoDoHistoricoDeProducaoDaQuantidadeDeProtocolosPorAnalistaOuConselheiroEPorAssunto(pesquisa)).build();
			break;
		case REL_08:
			//FIXME FALTA
			break;
		case REL_09:
			//FIXME FALTA
			rel = builderXls.rel09(relatorio09Builder.relacaoDetalhadaPorTipoPendenteDeAnalise(pesquisa));
			break;
		case REL_10:
			//FIXME FALTA
			break;
		case REL_11:
			rel = builderXls.rel11(relatorio11Dao.vidaDoProtocolo(pesquisa));
			break;
		case REL_12:
			rel = builderXls.rel12(relatorio12Dao.somatorioDoTotalDeDecisoesPorDepartamento(pesquisa));
			break;
		case REL_13:
			rel = builderXls.rel13(relatorio13Dao.relatorioDeProtocolosAssuntosSiacolECorporativoPorDepartamento(pesquisa));
			break;
		case REL_14:
			rel = builderXls.rel14(relatorio14Dao.quantidadeMensalDeProtocolosJulgadosNasReunioesPresenciaisEVirtuaisPorDepartamento(pesquisa));
			break;
		default:
			break;
		}

		return rel;
	}
	
	public byte[] geraRelatorioXlsDetalheRel04(List<RelDetalhadoSiacol04Dto> dto) {
		return builderXls.rel04Detalhe(dto);
	}
	

}
