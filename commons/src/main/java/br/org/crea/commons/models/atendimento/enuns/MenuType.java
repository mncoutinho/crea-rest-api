package br.org.crea.commons.models.atendimento.enuns;

public enum MenuType {
	
	RECEPCAO(new Long(8322), "atendimento", "Recepcão",  "app.agendamento.recepcao"),
	DASHBOARD(new Long(8323), "atendimento", "Dashboard",  "app.agendamento.dashboard"),
	GUICHE(new Long(8324), "atendimento", "Guichê",  "app.agendamento.guiche"),
	EVENTO(new Long(8325), "evento", "Evento",  "app.cadastro.evento"),
	ATENDIMENTO_INDICADORES(new Long(8326), "atendimento", "Indicadores",  "app.agendamento.indicadores");

	private final Long id;
	
	private String menu;
	
	private final String titulo;
	
	private final String url;

	private MenuType(Long id, String menu,  String titulo, String url) {
		this.id = id;
		this.menu = menu;
		this.titulo = titulo;
		this.url = url;
	}
	
	
	public static MenuType getMenuById(Long id){
		
		for(MenuType m : MenuType.values()){
			if(m.id.equals(id)){
				return m;
			}
		}
		return null;
	}


	public Long getId() {
		return id;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getUrl() {
		return url;
	}
	
	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}
	



}
