package br.org.crea.commons.report;

public enum TypeExportEnum {
	
	PDF("PDF"),
	XLS("XLS"),
	HTML("HTML");

	private final String descricao;

	private TypeExportEnum(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static TypeExportEnum obterPor(String descricao) {

        for (TypeExportEnum type : TypeExportEnum.values()) {
            if (type.getDescricao().equals(descricao)) {
                return type;
            }
        }

        throw new IllegalArgumentException("Não foi encontrado o tipo de exportação com a o código: [" + descricao + "] .");
    }

}
