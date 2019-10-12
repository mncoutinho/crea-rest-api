package br.org.crea.restapi.siacol.relatorio.rel06;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.models.siacol.dtos.relatorios.RelSiacol06Dto;

public class PopulaPercentualTest {
	
	RelSiacol06Dto linha;
	
	@Before
	public void setup() {
		linha = new RelSiacol06Dto();
		linha.setEntrada(4.0);
		linha.setPassivo(2.0);
		linha.setSaida(20.0);
	}
	
		
	@Test
	public void percentual () {
		populaPercentual(linha);
		System.out.println(linha.getPercentual());
		assertEquals("333,4 %", linha.getPercentual());
	}

	
	
	
	
	private void populaPercentual(RelSiacol06Dto linha) {
		DecimalFormat df = new DecimalFormat("#.#");
		df.setRoundingMode(RoundingMode.CEILING);
		double percentual;
		double entrada = linha.getEntrada() + linha.getPassivo();
		if (entrada == 0) { // evitar divisão por zero
			percentual = 0;
		} else {
			percentual = linha.getSaida() / entrada * 100.0;
		}
		linha.setPercentual(String.valueOf(df.format(percentual)) + " %");
	}
	
}
