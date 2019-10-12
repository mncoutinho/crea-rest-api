package br.org.crea.commons.models.portal.enuns;

public enum AtendimentoEnum {
	
	TEMPO_ESPERA(1), CORDIALIDADE(2), CLAREZA(3), ORIENTACOES(4), SEM_CONSULTA(5);

	private final int id;

	private AtendimentoEnum(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

}
