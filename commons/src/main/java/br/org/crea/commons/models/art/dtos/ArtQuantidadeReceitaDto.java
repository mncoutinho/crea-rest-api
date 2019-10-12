package br.org.crea.commons.models.art.dtos;

import java.math.BigDecimal;

public class ArtQuantidadeReceitaDto {

		private String numero;
		private BigDecimal quantidade;
		
		
		public String getNumero() {
			return numero;
		}
		public void setNumero(String numero) {
			this.numero = numero;
		}
		public BigDecimal getQuantidade() {
			return quantidade;
		}
		public void setQuantidade(BigDecimal quantidade) {
			this.quantidade = quantidade;
		}

}
