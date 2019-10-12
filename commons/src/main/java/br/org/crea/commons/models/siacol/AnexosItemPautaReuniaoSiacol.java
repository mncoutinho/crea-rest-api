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

import br.org.crea.commons.models.commons.Arquivo;


@Entity
@Table(name="SIACOL_ANEXO_ITEM_PAUTA")
@SequenceGenerator(name="sqSiacolAnexoItemPauta",sequenceName="SQ_SIACOL_ANEXO_ITEM_PAUTA",allocationSize = 1)
public class AnexosItemPautaReuniaoSiacol {
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sqSiacolAnexoItemPauta")
	private Long id;
	
	@OneToOne
	@JoinColumn(name="FK_ITEM")
	private RlDocumentoProtocoloSiacol item;
	
	@OneToOne
	@JoinColumn(name="FK_ARQUIVO")
	private Arquivo arquivo;

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

	public Arquivo getArquivo() {
		return arquivo;
	}

	public void setArquivo(Arquivo arquivo) {
		this.arquivo = arquivo;
	}


	
	

	


}


