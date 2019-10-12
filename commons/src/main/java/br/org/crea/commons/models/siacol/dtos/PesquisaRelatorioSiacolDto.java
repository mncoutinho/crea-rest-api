package br.org.crea.commons.models.siacol.dtos;

import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import br.org.crea.commons.models.cadastro.dtos.DepartamentoDto;
import br.org.crea.commons.models.commons.dtos.GenericDto;
import br.org.crea.commons.models.commons.dtos.SituacaoProtocoloDto;
import br.org.crea.commons.models.corporativo.dtos.AssuntoDto;
import br.org.crea.commons.models.corporativo.dtos.PessoaDto;
import br.org.crea.commons.models.siacol.enuns.TipoRelatorioSiacolEnum;

public class PesquisaRelatorioSiacolDto {
	
	private TipoRelatorioSiacolEnum tipo;
	
	private String ano;
	
	private List<String> meses;
	
	private DepartamentoDto departamento;
		
	private List<PessoaDto> responsaveis;
    
    private List<DepartamentoDto> departamentos;
    
    private boolean todosDepartamentos;
        
    private boolean todosAssuntos;
    
    private boolean todasClassificacoes;
    
    private boolean comDocumento;
    
    private boolean semAssunto;
    
    private boolean mesclado;

	private List<AssuntoDto> assuntos;
    
    private List<SituacaoProtocoloDto> situacoes;
    
    private List<GenericDto> status;
    
    private List<GenericDto> classificacao;

    private List<GenericDto> statusProtocolo;
    
    private String statusJulgado;
    
    private List<AssuntoDto> assuntosProtocolo;
   
	private Date inicioData;
	
	private Date fimData;
	
	private Long idPessoa;
	
	private String numeroProtocolo;
	
	private List<Long> listaIdsDepartamentos;

	public TipoRelatorioSiacolEnum getTipo() {
		return tipo;
	}

	public void setTipo(TipoRelatorioSiacolEnum tipo) {
		this.tipo = tipo;
	}

	public List<PessoaDto> getResponsaveis() {
		return responsaveis;
	}

	public void setResponsaveis(List<PessoaDto> responsaveis) {
		this.responsaveis = responsaveis;
	}

	public List<DepartamentoDto> getDepartamentos() {
		return departamentos;
	}

	public void setDepartamentos(List<DepartamentoDto> departamentos) {
		this.departamentos = departamentos;
	}

	public List<AssuntoDto> getAssuntos() {
		return assuntos;
	}

	public void setAssuntos(List<AssuntoDto> assuntos) {
		this.assuntos = assuntos;
	}

	public List<SituacaoProtocoloDto> getSituacoes() {
		return situacoes;
	}

	public void setSituacoes(List<SituacaoProtocoloDto> situacoes) {
		this.situacoes = situacoes;
	}

	public List<GenericDto> getStatus() {
		return status;
	}

	public void setStatus(List<GenericDto> status) {
		this.status = status;
	}

	public List<GenericDto> getClassificacao() {
		return classificacao;
	}

	public void setClassificacao(List<GenericDto> classificacao) {
		this.classificacao = classificacao;
	}

	public Date getInicioData() {
		return inicioData;
	}

	public void setInicioData(Date inicioData) {
		this.inicioData = inicioData;
	}

	public Date getFimData() {
		return fimData;
	}

	public void setFimData(Date fimData) {
		this.fimData = fimData;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public List<GenericDto> getStatusProtocolo() {
		return statusProtocolo;
	}

	public void setStatusProtocolo(List<GenericDto> statusProtocolo) {
		this.statusProtocolo = statusProtocolo;
	}

	public List<AssuntoDto> getAssuntosProtocolo() {
		return assuntosProtocolo;
	}

	public void setAssuntosProtocolo(List<AssuntoDto> assuntosProtocolo) {
		this.assuntosProtocolo = assuntosProtocolo;
	}

	public Long getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(Long idPessoa) {
		this.idPessoa = idPessoa;
	}

	public boolean isTodosDepartamentos() {
		return todosDepartamentos;
	}

	public void setTodosDepartamentos(boolean todosDepartamentos) {
		this.todosDepartamentos = todosDepartamentos;
	}
    
    public boolean isTodasClassificacoes() {
		return todasClassificacoes;
	}

	public void setTodasClassificacoes(boolean todasClassificacoes) {
		this.todasClassificacoes = todasClassificacoes;
	}
	
	public boolean isTodosAssuntos() {
		return todosAssuntos;
	}

	public void setTodosAssuntos(boolean todosAssuntos) {
		this.todosAssuntos = todosAssuntos;
	}

	public DepartamentoDto getDepartamento() {
		return departamento;
	}

	public void setDepartamento(DepartamentoDto departamento) {
		this.departamento = departamento;
	}
	
	public List<Long> getIdsDepartamentos() {
		return this.departamentos.stream().map(DepartamentoDto::getId).collect(Collectors.toList());
	}

	public boolean temMeses() {
		if (this.meses != null) {
			return !this.meses.isEmpty();
		}
		return false;
	}

	public String getNumeroProtocolo() {
		return numeroProtocolo;
	}

	public void setNumeroProtocolo(String numeroProtocolo) {
		this.numeroProtocolo = numeroProtocolo;
	}

	public boolean temPerfilConselheiro() {
		String perfil = responsaveis.get(0).getPerfil();
		return perfil.equals("siacolconselheiro") || perfil.equals("siacolcoordenadorcamara");
	}
	
	public boolean temPerfilAnalista() {
		String perfil = responsaveis.get(0).getPerfil();
		return perfil.equals("siacolestagiarioAI") || perfil.equals("siacolanalista") || perfil.equals("siacolanalistaadministrador") || perfil.equals("siacolanalistaautoinfracao") || perfil.equals("siacolanalistaregional") || perfil.equals("siacolanalistacomissao");
	}
	
	public boolean filtroEhJulgados() {
		return this.statusJulgado.equals("JULGADOS");
	}
	
	public boolean filtroEhNaoJulgados() {
		return this.statusJulgado.equals("NAO_JULGADOS");
	}
	
	public boolean filtroEhMesclado() {
		return this.statusJulgado.equals("MESCLADO");
	}

	public List<String> getMeses() {
		return meses;
	}

	public void setMeses(List<String> meses) {
		this.meses = meses;
	}

	public boolean isComDocumento() {
		return comDocumento;
	}

	public void setComDocumento(boolean comDocumento) {
		this.comDocumento = comDocumento;
	}

	public boolean isSemAssunto() {
		return semAssunto;
	}

	public void setSemAssunto(boolean semAssunto) {
		this.semAssunto = semAssunto;
	}

	public String getStatusJulgado() {
		return statusJulgado;
	}

	public void setStatusJulgado(String statusJulgado) {
		this.statusJulgado = statusJulgado;
	}

	public boolean ehSemDocumento() {
		return !this.comDocumento;
	}

	public boolean isMesclado() {
		return mesclado;
	}

	public void setMesclado(boolean mesclado) {
		this.mesclado = mesclado;
	}

	public List<Long> getListaIdsDepartamentos() {
		return listaIdsDepartamentos;
	}

	public void setListaIdsDepartamentos(List<Long> listaIdsDepartamentos) {
		this.listaIdsDepartamentos = listaIdsDepartamentos;
	}
	
}
