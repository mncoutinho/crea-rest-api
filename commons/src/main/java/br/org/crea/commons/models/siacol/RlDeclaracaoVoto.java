package br.org.crea.commons.models.siacol;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="SIACOL_RL_DECLARACAO_VOTO")
@SequenceGenerator(name="sqSiacolRlDeclVoto",sequenceName="SQ_SIACOL_VOTO_REUNIAO", initialValue = 1, allocationSize = 1)
public class RlDeclaracaoVoto {
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sqSiacolRlDeclVoto")
	private Long id;
	
	@OneToOne
	@JoinColumn(name="FK_ITEM_REUNIAO")
	private RlDocumentoProtocoloSiacol item;
	
	@Column(name="FK_CONSELHEIRO")
	private Long conselheiro;
	
	@Column(name="FK_ANALISTA_UPLOAD")
	private Long analistaUpload;
	
	@Column(name="FK_ARQUIVO_EXTERNO")
	private Long idArquivoDocflow;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RlDocumentoProtocoloSiacol getItem() {
		return item;
	}

	public void setItem(RlDocumentoProtocoloSiacol item) {
		this.item = item;
	}

	public Long getConselheiro() {
		return conselheiro;
	}

	public void setConselheiro(Long conselheiro) {
		this.conselheiro = conselheiro;
	}

	public Long getAnalistaUpload() {
		return analistaUpload;
	}

	public void setAnalistaUpload(Long analistaUpload) {
		this.analistaUpload = analistaUpload;
	}

	public Long getIdArquivoDocflow() {
		return idArquivoDocflow;
	}

	public void setIdArquivoDocflow(Long idArquivoDocflow) {
		this.idArquivoDocflow = idArquivoDocflow;
	}
	
}
