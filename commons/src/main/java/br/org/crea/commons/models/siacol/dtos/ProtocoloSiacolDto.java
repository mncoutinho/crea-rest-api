package br.org.crea.commons.models.siacol.dtos;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonPropertyOrder;

import br.org.crea.commons.models.cadastro.dtos.DepartamentoDto;
import br.org.crea.commons.models.cadastro.enuns.TipoEventoAuditoria;
import br.org.crea.commons.models.commons.dtos.DomainGenericDto;
import br.org.crea.commons.models.commons.dtos.StatusDto;
import br.org.crea.commons.models.corporativo.dtos.AssuntoDto;
import br.org.crea.commons.models.corporativo.dtos.SituacaoDto;
import br.org.crea.commons.models.siacol.SiacolProtocoloExigencia;
import br.org.crea.commons.models.siacol.dtos.documento.DecisaoDto;
import br.org.crea.commons.models.siacol.dtos.documento.RelatorioVotoDto;
import br.org.crea.commons.models.siacol.dtos.documento.SugestaoRelatorioVotoDto;
import br.org.crea.commons.models.siacol.enuns.VotoReuniaoEnum;

@JsonPropertyOrder({ "id", "numeroProtocolo", "numeroProcesso", 
    "idAssuntoCorportativo", "descricaoAssuntoCorporativo", "dataCadastro",  "dataCadastroFormatada",
    "idInteressado", "nomeInteressado", "idResponsavel", "nomeResponsavel", "observacao", "status", "ativo", "recebido",
        "dataRecebimento", "dataSiacol", "ultimoAnalista", "conselheiroRelator", "quantidadeVotosSim", "quantidadeVotosNao",
        "quantidadeVotosAbstencao", "quantidadeVotosDestaque", "totalVotos", "quorumReuniao", "geraDecisao", "semQuorum",
        "heDestacado", "assunto", "situacao", "departamento", "listRelatorioVoto", "listDecisao", "classificacao"   })
public class ProtocoloSiacolDto {
		
	private Long id;	
	
	private Long numeroProtocolo;
	
	private Long numeroProcesso;
	
	private Long idAssuntoCorportativo;

	private String descricaoAssuntoCorporativo;

    private Date dataCadastro;
	
	private String dataCadastroFormatada;
	
	private Long idInteressado;
	
	private String nomeInteressado;

    private Long idResponsavel;
    
	private String nomeResponsavel;
	
	private Long idConselheiroDevolucao;
	
	private String observacao;
	
//	private StatusDto status;
	
	private String status;
	
	private StatusDto statusDto;
	
	private StatusDto ultimoStatus;
	
	private Boolean ativo;

	private Boolean recebido;
	
	private Date dataRecebimento;
	
	private Date dataSiacol;
	
	private Long ultimoAnalista;
	
	private Long idConselheiroRelator;
	
	private String conselheiroRelator;
	
	private String justificativa;
	
	private String motivoDevolucao;
	
	private int quantidadeVotosSim;

	private int quantidadeVotosNao;

	private int quantidadeVotosAbstencao;
	
	private int quantidadeVotosDestaque;
	
	private int totalVotos;
	
	private int quorumReuniao;
	
	private boolean geraDecisao;
	
	private boolean semQuorum;
	
	private boolean heDestacado;
	
	private VotoReuniaoEnum votoCoordenadorOuAdjunto;
	
	private AssuntoDto assunto;
	
	private SituacaoDto situacao;
	
	private DepartamentoDto departamento;
	
	private List<RelatorioVotoDto> listRelatorioVoto;
	
	private List<SugestaoRelatorioVotoDto> listSugestaoRelatorioVoto;
	
	private List<DecisaoDto> listDecisao;
	
	private List<SugestaoRelatorioVotoDto> listVistas;
	
	private List<DecisaoDto> listDecisaoPai;
	
	private DomainGenericDto classificacao;
	
	private DomainGenericDto classificacaoFinal;
	
	private SiacolProtocoloExigencia exigencia;
	
	private List<Long> listNumeroProtocolosFilho;
	
	private boolean provisorio;
	
	private boolean urgenciaVotado;
	
	private boolean adReferendum;
	
	private boolean homologacaoPF;
	
	private TipoEventoAuditoria tipoEventoAuditoria;

	private Long numeroProtocoloExigencia;
	
	private Long numeroProtocoloUrgencia;
	
	private ProtocoloSiacolDto protocoloSiacolPrimeiraInstancia;
	
	private DecisaoDto decisaoPrimeiraInstancia;
	
	private EventoDto evento;
	
	private String numeroDecisao;
	
	private String classificacaoFinalDescritivo;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNumeroProtocolo() {
		return numeroProtocolo;
	}

	public void setNumeroProtocolo(Long numeroProtocolo) {
		this.numeroProtocolo = numeroProtocolo;
	}

	public Long getNumeroProcesso() {
		return numeroProcesso;
	}

	public void setNumeroProcesso(Long numeroProcesso) {
		this.numeroProcesso = numeroProcesso;
	}

	public AssuntoDto getAssunto() {
		return assunto;
	}

	public void setAssunto(AssuntoDto assunto) {
		this.assunto = assunto;
	}

	public SituacaoDto getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoDto situacao) {
		this.situacao = situacao;
	}

	public DepartamentoDto getDepartamento() {
		return departamento;
	}

	public void setDepartamento(DepartamentoDto departamento) {
		this.departamento = departamento;
	}

	public Long getIdAssuntoCorportativo() {
		return idAssuntoCorportativo;
	}

	public void setIdAssuntoCorportativo(Long idAssuntoCorportativo) {
		this.idAssuntoCorportativo = idAssuntoCorportativo;
	}

	public String getDescricaoAssuntoCorporativo() {
		return descricaoAssuntoCorporativo;
	}

	public void setDescricaoAssuntoCorporativo(String descricaoAssuntoCorporativo) {
		this.descricaoAssuntoCorporativo = descricaoAssuntoCorporativo;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}	
	
	public String getDataCadastroFormatada() {
		return dataCadastroFormatada;
	}

	public void setDataCadastroFormatada(String dataCadastroFormatada) {
		this.dataCadastroFormatada = dataCadastroFormatada;
	}

	public Long getIdInteressado() {
		return idInteressado;
	}

	public void setIdInteressado(Long idInteressado) {
		this.idInteressado = idInteressado;
	}

	public String getNomeInteressado() {
		return nomeInteressado;
	}

	public void setNomeInteressado(String nomeInteressado) {
		this.nomeInteressado = nomeInteressado;
	}

	public Long getIdResponsavel() {
		return idResponsavel;
	}

	public void setIdResponsavel(Long idResponsavel) {
		this.idResponsavel = idResponsavel;
	}

	public String getNomeResponsavel() {
		return nomeResponsavel;
	}

	public void setNomeResponsavel(String nomeResponsavel) {
		this.nomeResponsavel = nomeResponsavel;
	}

	public String getJustificativa() {
		return justificativa;
	}

	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

	public String getMotivoDevolucao() {
		return motivoDevolucao;
	}

	public void setMotivoDevolucao(String motivoDevolucao) {
		this.motivoDevolucao = motivoDevolucao;
	}

	public Long getIdConselheiroDevolucao() {
		return idConselheiroDevolucao;
	}

	public void setIdConselheiroDevolucao(Long idConselheiroDevolucao) {
		this.idConselheiroDevolucao = idConselheiroDevolucao;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public StatusDto getStatusDto() {
		return statusDto;
	}

	public void setStatusDto(StatusDto statusDto) {
		this.statusDto = statusDto;
	}

	public StatusDto getUltimoStatus() {
		return ultimoStatus;
	}

	public void setUltimoStatus(StatusDto ultimoStatus) {
		this.ultimoStatus = ultimoStatus;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Boolean getRecebido() {
		return recebido;
	}

	public void setRecebido(Boolean recebido) {
		this.recebido = recebido;
	}

	public Date getDataRecebimento() {
		return dataRecebimento;
	}

	public void setDataRecebimento(Date dataRecebimento) {
		this.dataRecebimento = dataRecebimento;
	}

	public Date getDataSiacol() {
		return dataSiacol;
	}

	public void setDataSiacol(Date dataSiacol) {
		this.dataSiacol = dataSiacol;
	}

	public Long getUltimoAnalista() {
		return ultimoAnalista;
	}

	public void setUltimoAnalista(Long ultimoAnalista) {
		this.ultimoAnalista = ultimoAnalista;
	}

	public Long getIdConselheiroRelator() {
		return idConselheiroRelator;
	}

	public void setIdConselheiroRelator(Long idConselheiroRelator) {
		this.idConselheiroRelator = idConselheiroRelator;
	}

	public String getConselheiroRelator() {
		return conselheiroRelator;
	}

	public void setConselheiroRelator(String conselheiroRelator) {
		this.conselheiroRelator = conselheiroRelator;
	}

	public List<DecisaoDto> getListDecisao() {
		return listDecisao;
	}

	public void setListDecisao(List<DecisaoDto> listDecisao) {
		this.listDecisao = listDecisao;
	}

	public List<DecisaoDto> getListDecisaoPai() {
		return listDecisaoPai;
	}

	public void setListDecisaoPai(List<DecisaoDto> listDecisaoPai) {
		this.listDecisaoPai = listDecisaoPai;
	}

	public List<RelatorioVotoDto> getListRelatorioVoto() {
		return listRelatorioVoto;
	}

	public void setListRelatorioVoto(List<RelatorioVotoDto> listRelatorioVoto) {
		this.listRelatorioVoto = listRelatorioVoto;
	}

	public List<SugestaoRelatorioVotoDto> getListSugestaoRelatorioVoto() {
		return listSugestaoRelatorioVoto;
	}

	public void setListSugestaoRelatorioVoto(
			List<SugestaoRelatorioVotoDto> listSugestaoRelatorioVoto) {
		this.listSugestaoRelatorioVoto = listSugestaoRelatorioVoto;
	}

	public List<SugestaoRelatorioVotoDto> getListVistas() {
		return listVistas;
	}

	public void setListVistas(List<SugestaoRelatorioVotoDto> listVistas) {
		this.listVistas = listVistas;
	}

	public int getQuantidadeVotosSim() {
		return quantidadeVotosSim;
	}

	public void setQuantidadeVotosSim(int quantidadeVotosSim) {
		this.quantidadeVotosSim = quantidadeVotosSim;
	}

	public int getQuantidadeVotosNao() {
		return quantidadeVotosNao;
	}

	public void setQuantidadeVotosNao(int quantidadeVotosNao) {
		this.quantidadeVotosNao = quantidadeVotosNao;
	}

	public int getQuantidadeVotosAbstencao() {
		return quantidadeVotosAbstencao;
	}

	public void setQuantidadeVotosAbstencao(int quantidadeVotosAbstencao) {
		this.quantidadeVotosAbstencao = quantidadeVotosAbstencao;
	}

	public int getQuantidadeVotosDestaque() {
		return quantidadeVotosDestaque;
	}

	public void setQuantidadeVotosDestaque(int quantidadeVotosDestaque) {
		this.quantidadeVotosDestaque = quantidadeVotosDestaque;
	}


	public DomainGenericDto getClassificacao() {
		return classificacao;
	}

	public void setClassificacao(DomainGenericDto classificacao) {
		this.classificacao = classificacao;
	}

	public DomainGenericDto getClassificacaoFinal() {
		return classificacaoFinal;
	}

	public void setClassificacaoFinal(DomainGenericDto classificacaoFinal) {
		this.classificacaoFinal = classificacaoFinal;
	}

	public List<Long> getListNumeroProtocolosFilho() {
		return listNumeroProtocolosFilho;
	}

	public void setListNumeroProtocolosFilho(List<Long> listNumeroProtocolosFilho) {
		this.listNumeroProtocolosFilho = listNumeroProtocolosFilho;
	}

	public boolean isGeraDecisao() {
		return geraDecisao;
	}

	public void setGeraDecisao(boolean geraDecisao) {
		this.geraDecisao = geraDecisao;
	}

	public boolean isSemQuorum() {
		return semQuorum;
	}

	public void setSemQuorum(boolean semQuorum) {
		this.semQuorum = semQuorum;
	}

	public boolean isHeDestacado() {
		return heDestacado;
	}

	public void setHeDestacado(boolean heDestacado) {
		this.heDestacado = heDestacado;
	}

	public int getQuorumReuniao() {
		return quorumReuniao;
	}

	public void setQuorumReuniao(int quorumReuniao) {
		this.quorumReuniao = quorumReuniao;
	}
	
	public boolean deuEmpate() {
		return this.quantidadeVotosSim == this.quantidadeVotosNao;
	}

	public Long getNumeroProtocoloExigencia() {
		return numeroProtocoloExigencia;
	}

	public void setNumeroProtocoloExigencia(Long numeroProtocoloExigencia) {
		this.numeroProtocoloExigencia = numeroProtocoloExigencia;
	}
	
	public boolean temNumeroProtocoloExigencia() {
		return this.numeroProtocoloExigencia != null ? true : false;
	}

	public Long getNumeroProtocoloUrgencia() {
		return numeroProtocoloUrgencia;
	}

	public void setNumeroProtocoloUrgencia(Long numeroProtocoloUrgencia) {
		this.numeroProtocoloUrgencia = numeroProtocoloUrgencia;
	}

	public void calculaGeraDecisao() {
		

		if(existeVotoEmDestaque()){
			setDestacado();
		}
		
		if( totalDeVotosHeMenorQueQuorumDaReuniao() ){
			setReuniaoSemQuorum();
		}else {
			
			if(deuEmpate()){
				
				
				if(votoDoCoordenadorOuAjuntoForSim()){
					if(heFavoravel()){
						geraDecisao();
					}
				}else {
					if(heDesfavoravel()){
						geraDecisao();
					}else {
						naoGeraDecisao();
					}
				}
				
			}else {
				
				if(maioriaDosVotosHeSim()){
					if(heFavoravel()){
						geraDecisao();
					}
				}else {
					if(heDesfavoravel()){
						geraDecisao();
					}else {
						naoGeraDecisao();
					}
				}
				
			}
		}
		
	}
	
	private boolean votoDoCoordenadorOuAjuntoForSim() {
		if(this.votoCoordenadorOuAdjunto != null) {
			return this.votoCoordenadorOuAdjunto.equals(VotoReuniaoEnum.S);
		} else {
			return false;
		}
	}

	public void setDestacado() {
		this.heDestacado = true;
	}
	
	public boolean existeVotoEmDestaque(){
		return this.quantidadeVotosDestaque > 0;
	}

	public boolean maioriaDosVotosHeSim(){
		return this.quantidadeVotosSim > this.quantidadeVotosNao;
	}
	
	public boolean totalDeVotosHeMenorQueQuorumDaReuniao(){
		this.totalVotos = this.quantidadeVotosSim + this.quantidadeVotosNao + this.quantidadeVotosAbstencao;
		return this.totalVotos < this.quorumReuniao;
	}
	
	public void setReuniaoSemQuorum() {
		this.semQuorum = true;
	}
	
	public void geraDecisao(){
		this.geraDecisao = true;
	}
	
	public void naoGeraDecisao(){
		this.geraDecisao = false;
	}
	
	public boolean heFavoravel() {
		return this.classificacao.getId() == 2;
	}
	
	public boolean heDesfavoravel() {
		return this.classificacao.getId() == 1;
	}
	
	public boolean heNaoClassificado() {
		return this.classificacao.getId() == 0;
	}

	public int getTotalVotos() {
		return totalVotos;
	}

	public void setTotalVotos(int totalVotos) {
		this.totalVotos = totalVotos;
	}
	
	public boolean foiFavoravelAoInteressado() {
		return this.geraDecisao ? true : false;
	}
	
	public boolean foiDesfavoravelAoInteressado() {
		return !this.geraDecisao && !this.semQuorum;
	}
	
	public boolean obteveQuorumMinimo() {
		return !this.semQuorum;
	}


	public boolean temCoordenadorNoDepartamentoDoProtocolo () {
		return this.departamento.getCoordenador() != null;
	}
	
	public boolean temAdjuntoNoDepartamentoDoProtocolo () {
		return this.departamento.getAdjunto() != null;
	}

	public VotoReuniaoEnum getVotoCoordenadorOuAdjunto() {
		return votoCoordenadorOuAdjunto;
	}

	public void setVotoCoordenadorOuAdjunto(VotoReuniaoEnum votoCoordenadorOuAdjunto) {
		this.votoCoordenadorOuAdjunto = votoCoordenadorOuAdjunto;
	}

	public boolean temCoordanadorOuAdjunto() {
		return this.obtemIdCoordenadorOuAdjunto() != null;
	}

	public Long obtemIdCoordenadorOuAdjunto() {
		if (this.temCoordenadorNoDepartamentoDoProtocolo()) {
			return this.getDepartamento().getCoordenador().getId();
		} else if (this.temAdjuntoNoDepartamentoDoProtocolo()) {
			return this.getDepartamento().getAdjunto().getId();
		} else {
			return null;
		}
	}

	public boolean votoDestaque() {
		return this.heDestacado;
	}

	public SiacolProtocoloExigencia getExigencia() {
		return exigencia;
	}

	public void setExigencia(SiacolProtocoloExigencia exigencia) {
		this.exigencia = exigencia;
	}

	public boolean isProvisorio() {
		return provisorio;
	}

	public void setProvisorio(boolean provisorio) {
		this.provisorio = provisorio;
	}

	public boolean isUrgenciaVotado() {
		return urgenciaVotado;
	}

	public void setUrgenciaVotado(boolean urgenciaVotado) {
		this.urgenciaVotado = urgenciaVotado;
	}

	public boolean isAdReferendum() {
		return adReferendum;
	}

	public void setAdReferendum(boolean adReferendum) {
		this.adReferendum = adReferendum;
	}

	public boolean teveMudancadeMerito() {
		return (!deuEmpate() && !maioriaDosVotosHeSim()) || (deuEmpate() && getVotoCoordenadorOuAdjunto() == VotoReuniaoEnum.N) ;
	}

	public TipoEventoAuditoria getTipoEventoAuditoria() {
		return tipoEventoAuditoria;
	}

	public void setTipoEventoAuditoria(TipoEventoAuditoria tipoEventoAuditoria) {
		this.tipoEventoAuditoria = tipoEventoAuditoria;
	}

	public boolean isHomologacaoPF() {
		return homologacaoPF;
	}

	public void setHomologacaoPF(boolean homologacaoPF) {
		this.homologacaoPF = homologacaoPF;
	}

	public ProtocoloSiacolDto getProtocoloSiacolPrimeiraInstancia() {
		return protocoloSiacolPrimeiraInstancia;
	}

	public void setProtocoloSiacolPrimeiraInstancia(ProtocoloSiacolDto protocoloSiacolPrimeiraInstancia) {
		this.protocoloSiacolPrimeiraInstancia = protocoloSiacolPrimeiraInstancia;
	}

	public DecisaoDto getDecisaoPrimeiraInstancia() {
		return decisaoPrimeiraInstancia;
	}

	public void setDecisaoPrimeiraInstancia(DecisaoDto decisaoPrimeiraInstancia) {
		this.decisaoPrimeiraInstancia = decisaoPrimeiraInstancia;
	}

	public EventoDto getEvento() {
		return evento;
	}

	public void setEvento(EventoDto evento) {
		this.evento = evento;
	}

	public String getNumeroDecisao() {
		return numeroDecisao;
	}

	public void setNumeroDecisao(String numeroDecisao) {
		this.numeroDecisao = numeroDecisao;
	}

	public String getClassificacaoFinalDescritivo() {
		return classificacaoFinalDescritivo;
	}

	public void setClassificacaoFinalDescritivo(String classificacaoFinalDescritivo) {
		this.classificacaoFinalDescritivo = classificacaoFinalDescritivo;
	}

	public String getTipoProcesso() {
		if (numeroProcesso > 99) {
			String stringNumeroProcesso = numeroProcesso.toString();
			return stringNumeroProcesso.substring(3, 3);
		} else {
			return "7";
		}
		
	}
	
}
