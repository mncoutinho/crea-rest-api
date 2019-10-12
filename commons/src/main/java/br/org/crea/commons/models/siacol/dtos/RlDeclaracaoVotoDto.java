package br.org.crea.commons.models.siacol.dtos;

import br.org.crea.commons.models.corporativo.dtos.PessoaDto;


public class RlDeclaracaoVotoDto {
	
	private Long id;
	
	private ItemPautaDto item;
	
	private Long idConselheiro;
	
	private PessoaDto conselheiro;
	
	private Long idAnalistaUpload;
	
	private PessoaDto analistaUpload;
	
	private Long idArquivoDocflow;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ItemPautaDto getItem() {
		return item;
	}

	public void setItem(ItemPautaDto item) {
		this.item = item;
	}

	public Long getIdConselheiro() {
		return idConselheiro;
	}

	public void setIdConselheiro(Long idConselheiro) {
		this.idConselheiro = idConselheiro;
	}

	public PessoaDto getConselheiro() {
		return conselheiro;
	}

	public void setConselheiro(PessoaDto conselheiro) {
		this.conselheiro = conselheiro;
	}

	public Long getIdAnalistaUpload() {
		return idAnalistaUpload;
	}

	public void setIdAnalistaUpload(Long idAnalistaUpload) {
		this.idAnalistaUpload = idAnalistaUpload;
	}

	public PessoaDto getAnalistaUpload() {
		return analistaUpload;
	}

	public void setAnalistaUpload(PessoaDto analistaUpload) {
		this.analistaUpload = analistaUpload;
	}

	public Long getIdArquivoDocflow() {
		return idArquivoDocflow;
	}

	public void setIdArquivoDocflow(Long idArquivoDocflow) {
		this.idArquivoDocflow = idArquivoDocflow;
	}
	
	

}
