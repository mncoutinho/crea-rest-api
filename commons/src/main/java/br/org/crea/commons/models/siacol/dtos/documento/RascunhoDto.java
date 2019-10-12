package br.org.crea.commons.models.siacol.dtos.documento;

import java.util.List;

public class RascunhoDto {

	private boolean efeitoLegal;
	
	private List<Long> listRegistroProfissional;

	public boolean isEfeitoLegal() {
		return efeitoLegal;
	}

	public void setEfeitoLegal(boolean efeitoLegal) {
		this.efeitoLegal = efeitoLegal;
	}

	public List<Long> getListRegistroProfissional() {
		return listRegistroProfissional;
	}

	public void setListRegistroProfissional(List<Long> listRegistroProfissional) {
		this.listRegistroProfissional = listRegistroProfissional;
	}	
	
}
