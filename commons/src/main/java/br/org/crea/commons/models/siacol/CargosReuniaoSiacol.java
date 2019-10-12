package br.org.crea.commons.models.siacol;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="PER_CARGOS")
public class CargosReuniaoSiacol {


		@Id
		@Column(name="CODIGO")
		private Long id;
		
		@Column(name="DESCRICAO")
		private String descricao;
		
		@Column(name="VISIVEL_CONSELHEIROS")
		private Boolean visivelConselheiros;
		
		@Column(name="VISIVEL_INSPETORES")
		private Boolean visivelInspetores;
		
		@Column(name="VISIVEL_CONTATOS")
		private Boolean visivelContatos;
		
		@Column(name="VISIVEL_DIRECOES")
		private Boolean visivelDirecoes;
		
		@Column(name="REMOVIDO")
		private Boolean removido;
		
		@Column(name="PERMITE_CONSECUTIVIDADE")
		private Boolean permiteConsecutividade;
		
		@Column(name="FK_CODIGO_CARGO")
		private Long idCargo;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getDescricao() {
			return descricao;
		}

		public void setDescricao(String descricao) {
			this.descricao = descricao;
		}

		public Boolean getVisivelConselheiros() {
			return visivelConselheiros;
		}

		public void setVisivelConselheiros(Boolean visivelConselheiros) {
			this.visivelConselheiros = visivelConselheiros;
		}

		public Boolean getVisivelInspetores() {
			return visivelInspetores;
		}

		public void setVisivelInspetores(Boolean visivelInspetores) {
			this.visivelInspetores = visivelInspetores;
		}

		public Boolean getVisivelContatos() {
			return visivelContatos;
		}

		public void setVisivelContatos(Boolean visivelContatos) {
			this.visivelContatos = visivelContatos;
		}

		public Boolean getVisivelDirecoes() {
			return visivelDirecoes;
		}

		public void setVisivelDirecoes(Boolean visivelDirecoes) {
			this.visivelDirecoes = visivelDirecoes;
		}

		public Boolean getRemovido() {
			return removido;
		}

		public void setRemovido(Boolean removido) {
			this.removido = removido;
		}

		public Boolean getPermiteConsecutividade() {
			return permiteConsecutividade;
		}

		public void setPermiteConsecutividade(Boolean permiteConsecutividade) {
			this.permiteConsecutividade = permiteConsecutividade;
		}

		public Long getIdCargo() {
			return idCargo;
		}

		public void setIdCargo(Long idCargo) {
			this.idCargo = idCargo;
		}

}
