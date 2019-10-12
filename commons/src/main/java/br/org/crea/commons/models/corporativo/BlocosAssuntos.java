package br.org.crea.commons.models.corporativo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.org.crea.commons.models.protocolo.enuns.TipoProtocoloEnum;

@Entity
@Table(name = "PRT_BLOCOSASSUNTOS")
public class BlocosAssuntos {

	@Id
	@Column(name="ID")
	private Long id;

	@Column(name="DESCRICAO")
	private String descricao;

	@Column(name="FK_ID_TIPOSPROCESSOS")
	private int tipoProcesso;
	
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

	public TipoProtocoloEnum getTipoProcesso() {
		
		switch (tipoProcesso) {
		case 0:
			return TipoProtocoloEnum.LEIGO;
		case 1:
			return TipoProtocoloEnum.PROFISSIONAL;
		case 2:
			return TipoProtocoloEnum.EMPRESA;
		case 3:
			return TipoProtocoloEnum.AUTOINFRACAO;
		case 4:
			return TipoProtocoloEnum.ADMINISTRATIVO_FINANCEIRO;
		case 5:
			return TipoProtocoloEnum.OUTROS_TIPOS;
		case 6:
			return TipoProtocoloEnum.NOTIFICACAO_OFICIO;
		case 7:
			return TipoProtocoloEnum.PROTOCOLO;
		case 8:
			return TipoProtocoloEnum.ENTIDADE_CLASSE_ENSINO;
		case 9:
			return TipoProtocoloEnum.AUTOINFRACAO_EXTERNO;
		default:
			return null;
		}
	}
	
	

}
