package br.org.crea.commons.models.commons.dtos;

import br.org.crea.commons.models.commons.enuns.TipoRlArquivo;

public class RlArquivoDto {
		
		private Long id;

		private TipoRlArquivo tipoRlArquivo;
		
		private Long idTipoRlArquivo;

		private ArquivoDto arquivo;
		
		private Long idArquivo;
		
		private String posicao;
		
		private String descricao;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public TipoRlArquivo getTipoRlArquivo() {
			return tipoRlArquivo;
		}

		public void setTipoRlArquivo(TipoRlArquivo tipoRlArquivo) {
			this.tipoRlArquivo = tipoRlArquivo;
		}

		public Long getIdTipoRlArquivo() {
			return idTipoRlArquivo;
		}

		public void setIdTipoRlArquivo(Long idTipoRlArquivo) {
			this.idTipoRlArquivo = idTipoRlArquivo;
		}

		public ArquivoDto getArquivo() {
			return arquivo;
		}

		public void setArquivo(ArquivoDto arquivo) {
			this.arquivo = arquivo;
		}

		public boolean temTipoRlArquivo() {
			return this.tipoRlArquivo != null;
		}

		public Long getIdArquivo() {
			return idArquivo;
		}

		public void setIdArquivo(Long idArquivo) {
			this.idArquivo = idArquivo;
		}

		public boolean temArquivo() {
			return this.arquivo != null;
		}

		public String getPosicao() {
			return posicao;
		}

		public void setPosicao(String posicao) {
			this.posicao = posicao;
		}

		public String getDescricao() {
			return descricao;
		}

		public void setDescricao(String descricao) {
			this.descricao = descricao;
		}	

	}

