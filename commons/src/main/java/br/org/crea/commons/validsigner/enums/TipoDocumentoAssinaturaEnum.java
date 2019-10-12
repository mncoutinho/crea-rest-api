package br.org.crea.commons.validsigner.enums;

public enum TipoDocumentoAssinaturaEnum {
	
	DOCUMENTO_JSON(new Long(0), "Documento Armazenado em Cad-Documento"),
	ARQUIVO_FILE_SYSTEM(new Long(1), "Documento Armazenado no File System"),
	ARQUIVO_UPLOAD(new Long(2), "Documento Upload");
	
	public final Long id;
	public final String descricao;
	
	private TipoDocumentoAssinaturaEnum(Long id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}
}
