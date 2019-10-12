package br.org.crea.siacol.builder.relxls;

import java.util.List;

import br.org.crea.commons.models.siacol.dtos.relatorios.RelDetalhadoSiacol04Dto;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelSiacol02Dto;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelSiacol03Dto;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelSiacol04Dto;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelSiacol05Dto;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelSiacol06Dto;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelSiacol07Dto;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelSiacol09Dto;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelSiacol11Dto;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelSiacol12Dto;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelSiacol13Dto;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelSiacol14Dto;
import br.org.crea.commons.util.ExcelPoiUtil;
import br.org.crea.commons.util.ExcelPoiUtil.Style;

public class SiacolRelXlsBuilder {

	public byte[] rel02(List<RelSiacol02Dto> listRel) {

		ExcelPoiUtil excelPoiUtil = new ExcelPoiUtil();
		excelPoiUtil.setCell("Cod. Ass. Confea");
		excelPoiUtil.setCell("Desc. Ass. Confea");
		excelPoiUtil.setCell("Cod. Assunto");
		excelPoiUtil.setCell("Assunto");
		excelPoiUtil.setCell("Qtd Concedido Registro Provisório");
		excelPoiUtil.setCell("Qtd Aprovado Registro Provisório");
		excelPoiUtil.setCell("Qtd Concedido Ad Referendum");
		excelPoiUtil.setCell("Qtd Aprovado Ad Referendum");
		excelPoiUtil.setCell("Qtd Total Ad Referendum");
		excelPoiUtil.setCell("Qtd Reunião Virtual");
		excelPoiUtil.setCell("Qtd Reunião Presencial");
		excelPoiUtil.setCell("Qtd Total Reuniões");

		listRel.forEach(rel -> {
			excelPoiUtil.newRow();
			if (rel.getCodigoAssuntoConfea() != null) {
				excelPoiUtil.setCell(rel.getCodigoAssuntoConfea().toString());
			} else {
				excelPoiUtil.setCell("");
			}

			excelPoiUtil.setCell(rel.getDescricaoAssuntoConfea());
			if (rel.getCodigoAssunto() != null) {
				excelPoiUtil.setCell(rel.getCodigoAssunto().toString());
			} else {
				excelPoiUtil.setCell("");
			}

			excelPoiUtil.setCell(rel.getAssunto());
			excelPoiUtil.setCell(rel.getQtdConcedidoRegistroProvisorio());
			excelPoiUtil.setCell(rel.getQtdAprovadoRegistroProvisorio());
			excelPoiUtil.setCell(rel.getQtdConcedidoAdReferendum());
			excelPoiUtil.setCell(rel.getQtdAprovadoAdReferendum());
			excelPoiUtil.setCell(rel.getQtdTotalADeRP());
			excelPoiUtil.setCell(rel.getQtdReuniaoVirtual());
			excelPoiUtil.setCell(rel.getQtdReuniaoPresencial());
			excelPoiUtil.setCell(rel.getQtdTotalReunioes());
		});

		excelPoiUtil.newRow();
		excelPoiUtil.setMergedCell("Total não Classificados: " + listRel.get(0).getQtdNaoClassificado(), listRel.size(), Style.BOLD);

		return excelPoiUtil.buildToStream();
	}

	public byte[] rel03(List<RelSiacol03Dto> listRel) {

		ExcelPoiUtil excelPoiUtil = new ExcelPoiUtil();
		excelPoiUtil.setCell("Evento");

		listRel.get(0).getDepartamentos().forEach(dep -> {
			excelPoiUtil.setCell(dep.getNome());
		});

		excelPoiUtil.setCell("Total");

		listRel.forEach(rel -> {
			excelPoiUtil.newRow();
			excelPoiUtil.setCell(rel.getEvento());
			rel.getDepartamentos().forEach(dep -> {
				excelPoiUtil.setCell(dep.getQtd());
			});
			excelPoiUtil.setCell(rel.getTotal());
		});

		return excelPoiUtil.buildToStream();
	}

	public byte[] rel04(List<RelSiacol04Dto> listRel) {

		ExcelPoiUtil excelPoiUtil = new ExcelPoiUtil();
		excelPoiUtil.setCell("Cod. Ass. Confea");
		excelPoiUtil.setCell("Desc. Ass. Confea");
		excelPoiUtil.setCell("Cod. Assunto");
		excelPoiUtil.setCell("Assunto");

		listRel.get(0).getDepartamentos().forEach(dep -> {
			excelPoiUtil.setCell(dep.getNome() +  " - Julgados");
			excelPoiUtil.setCell(dep.getNome() +  " - Não Julgados");
		});

		//excelPoiUtil.setCell("Total");

		listRel.forEach(rel -> {
			excelPoiUtil.newRow();
			if (rel.getCodigoAssuntoConfea() != null) {
				excelPoiUtil.setCell(rel.getCodigoAssuntoConfea().toString());
			} else {
				excelPoiUtil.setCell("");
			}

			excelPoiUtil.setCell(rel.getDescricaoAssuntoConfea());
			if(rel.getCodigoAssunto() != null) {
				excelPoiUtil.setCell(rel.getCodigoAssunto().toString());
			} else {
				excelPoiUtil.setCell("");
			}
			
			excelPoiUtil.setCell(rel.getAssunto());
			rel.getDepartamentos().forEach(dep -> {
				excelPoiUtil.setCell(dep.getQtdJulgados());
				excelPoiUtil.setCell(dep.getQtdNaoJulgados());
			});
		});

		excelPoiUtil.newRow();
		excelPoiUtil.setMergedCell("Total não Classificados: " + listRel.get(0).getQtdNaoClassificado(), listRel.size(), Style.BOLD);

		return excelPoiUtil.buildToStream();
	}

	public byte[] rel05(List<RelSiacol05Dto> listRel) {

		ExcelPoiUtil excelPoiUtil = new ExcelPoiUtil();
		excelPoiUtil.setCell("Status");

		listRel.get(0).getDepartamentos().forEach(dep -> {
			excelPoiUtil.setCell(dep.getNome());
		});

		listRel.forEach(rel -> {
			excelPoiUtil.newRow();
			excelPoiUtil.setCell(rel.getStatus());
			rel.getDepartamentos().forEach(dep -> {
				excelPoiUtil.setCell(dep.getQtd());
			});
		});
		return excelPoiUtil.buildToStream();
	}

	public byte[] rel06(List<RelSiacol06Dto> listRel) {
		ExcelPoiUtil excelPoiUtil = new ExcelPoiUtil();
		excelPoiUtil.setCell("Mês");
		excelPoiUtil.setCell("Entrada");
		excelPoiUtil.setCell("Saída");
		excelPoiUtil.setCell("Passivo");
		excelPoiUtil.setCell("Percentual");
		excelPoiUtil.setCell("Pausado");
		excelPoiUtil.setCell("Retorno");

		listRel.forEach(rel -> {
			excelPoiUtil.newRow();
			excelPoiUtil.setCell(rel.getMes());
			excelPoiUtil.setCell(String.valueOf(rel.getEntrada()));
			excelPoiUtil.setCell(String.valueOf(rel.getSaida()));
			excelPoiUtil.setCell(String.valueOf(rel.getPassivo()));
			excelPoiUtil.setCell(String.valueOf(rel.getPercentual()));
			excelPoiUtil.setCell(String.valueOf(rel.getPausado()));
			excelPoiUtil.setCell(String.valueOf(rel.getRetorno()));
		});

		return excelPoiUtil.buildToStream();
	}

	public byte[] rel07(List<RelSiacol07Dto> listRel) {
		// TODO Auto-generated method stub
		return null;
	}

	public byte[] rel11(List<RelSiacol11Dto> listRel) {
		ExcelPoiUtil excelPoiUtil = new ExcelPoiUtil();
		excelPoiUtil.setCell("Data");
		excelPoiUtil.setCell("Texto Auditoria");

		listRel.forEach(rel -> {
			excelPoiUtil.newRow();
			excelPoiUtil.setCell(rel.getDataFormatada());
			excelPoiUtil.setCell(rel.getTextoAuditoria());
		});

		return excelPoiUtil.buildToStream();
	}

	public byte[] rel12(List<RelSiacol12Dto> listRel) {
		ExcelPoiUtil excelPoiUtil = new ExcelPoiUtil();
		excelPoiUtil.setCell("Departamento");
		excelPoiUtil.setCell("Decisões Favoráveis");
		excelPoiUtil.setCell("Decisões Desfavoráveis");
		excelPoiUtil.setCell("Decisões Homologação");
		excelPoiUtil.setCell("Decisões Assunto");

		for (RelSiacol12Dto rel : listRel) {
			excelPoiUtil.newRow();
			excelPoiUtil.setCell(rel.getDepartamento());
			excelPoiUtil.setCell(rel.getQtdDecisoesFavoraveis());
			excelPoiUtil.setCell(rel.getQtdDecisoesDesfavoraveis());
			excelPoiUtil.setCell(rel.getQtdDecisoesHomologacao());
			excelPoiUtil.setCell(rel.getQtdDecisoesAssunto());
		}
		excelPoiUtil.newRow();
		excelPoiUtil.setMergedCell("Total não Classificados: " + listRel.get(0).getQtdNaoClassificados(), listRel.size(), Style.BOLD);

		return excelPoiUtil.buildToStream();
	}

	public byte[] rel13(List<RelSiacol13Dto> listRel) {
		ExcelPoiUtil excelPoiUtil = new ExcelPoiUtil();
		excelPoiUtil.setCell("Protocolo");
		excelPoiUtil.setCell("Cod. Ass. Corporativo");
		excelPoiUtil.setCell("Desc. Ass. Corporativo");
		excelPoiUtil.setCell("Cod. Ass. Siacol");
		excelPoiUtil.setCell("Desc. Ass. Siacol");
		excelPoiUtil.setCell("Departamento Criação");
		excelPoiUtil.setCell("Matrícula Criador");
		excelPoiUtil.setCell("Nome Criador");

		listRel.forEach(rel -> {
			excelPoiUtil.newRow();
			excelPoiUtil.setCell(rel.getNumeroProtocolo());
			excelPoiUtil.setCell(rel.getCodigoAssuntoCorporativo());
			excelPoiUtil.setCell(rel.getDescricaoAssuntoCorporativo());
			excelPoiUtil.setCell(rel.getCodigoAssuntoSiacol());
			excelPoiUtil.setCell(rel.getDescricaoAssuntoSiacol());
			excelPoiUtil.setCell(rel.getDepartamentoCriacao());
			excelPoiUtil.setCell(rel.getMatriculaCriador());
			excelPoiUtil.setCell(rel.getNomeCriador());
		});

		return excelPoiUtil.buildToStream();
	}

	public byte[] rel14(List<RelSiacol14Dto> listRel) {
		ExcelPoiUtil excelPoiUtil = new ExcelPoiUtil();
		excelPoiUtil.setCell("Mês");

		listRel.get(0).getDepartamentos().forEach(dep -> {
			excelPoiUtil.setCell(dep.getNome() + "-Presencial");
			excelPoiUtil.setCell(dep.getNome() + "-Virtual");
		});

		listRel.forEach(rel -> {
			excelPoiUtil.newRow();
			excelPoiUtil.setCell(rel.getMes());
			rel.getDepartamentos().forEach(dep -> {
				excelPoiUtil.setCell(dep.getQtdReuniaoPresencial());
				excelPoiUtil.setCell(dep.getQtdReuniaoVirtual());
			});
		});

		return excelPoiUtil.buildToStream();
	}

	public byte[] rel09(List<RelSiacol09Dto> list) {
		ExcelPoiUtil excelPoiUtil = new ExcelPoiUtil();

		excelPoiUtil.setCell("Cod. Ass. Confea");
		excelPoiUtil.setCell("Desc. Ass. Confea");
		excelPoiUtil.setCell("Cod. Assunto");
		excelPoiUtil.setCell("Assunto");

		list.get(0).getStatus().forEach(dep -> {
			excelPoiUtil.setCell(dep.getStatus());
		});

		list.forEach(rel -> {
			excelPoiUtil.newRow();
			if (rel.getCodigoAssuntoConfea() != null) {
				excelPoiUtil.setCell(rel.getCodigoAssuntoConfea().toString());
			} else {
				excelPoiUtil.setCell("");
			}
			excelPoiUtil.setCell(rel.getDescricaoAssuntoConfea());
			if(rel.getCodigoAssunto() != null) {
				excelPoiUtil.setCell(rel.getCodigoAssunto().toString());
			} else {
				excelPoiUtil.setCell("");
			}
			excelPoiUtil.setCell(rel.getAssunto());

			rel.getStatus().forEach(dep -> {
				excelPoiUtil.setCell(dep.getTotal());
			});
		});

		return excelPoiUtil.buildToStream();
	}

	public byte[] rel04Detalhe(List<RelDetalhadoSiacol04Dto> dto) {
		ExcelPoiUtil excelPoiUtil = new ExcelPoiUtil();
		excelPoiUtil.setCell("Departamento");
		excelPoiUtil.setCell("Protocolo");
		excelPoiUtil.setCell("Cod. Ass. Corporativo");
		excelPoiUtil.setCell("Desc. Ass. Corporativo");

		dto.forEach(rel -> {
			excelPoiUtil.newRow();
			excelPoiUtil.setCell(rel.getDepartamento());
			excelPoiUtil.setCell(rel.getProtocolo());
			excelPoiUtil.setCell(rel.getCodigoAssuntoCorporativo());
			excelPoiUtil.setCell(rel.getAssuntoCorporativo());
		});

		return excelPoiUtil.buildToStream();
	}
}
