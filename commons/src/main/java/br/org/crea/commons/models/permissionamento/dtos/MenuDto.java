package br.org.crea.commons.models.permissionamento.dtos;

import java.util.List;

public class MenuDto {
	
	private String text;
	
	private String icon;
	
	private String sref;
	
	private List<SubMenuDto> submenu;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getSref() {
		return sref;
	}

	public void setSref(String sref) {
		this.sref = sref;
	}

	public List<SubMenuDto> getSubmenu() {
		return submenu;
	}

	public void setSubmenu(List<SubMenuDto> submenu) {
		this.submenu = submenu;
	}
	
	

}
