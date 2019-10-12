package br.org.crea.commons.models.fiscalizacao.dtos;

import java.util.Date;
import java.util.List;

import br.org.crea.commons.models.cadastro.dtos.empresa.EmpresaDto;
import br.org.crea.commons.models.cadastro.dtos.empresa.ResponsavelTecnicoDto;
import br.org.crea.commons.models.commons.dtos.DomainGenericDto;
import br.org.crea.commons.models.commons.dtos.EnderecoDto;
import br.org.crea.commons.models.commons.dtos.ProtocoloDto;
import br.org.crea.commons.models.financeiro.dtos.DividaDto;

public class AutoInfracaoDto {

	private String numeroRf;
	
	private String numeroDoAuto;
	
	private String codigoFatoGerador;
	
	private String descricaoFatoGerador;
	
	private Date dataLavraturaAuto;
	
	private String dataLavraturaAutoFormatada;
	
	private Date dataConstatacaoFiscal;
	
	private String dataConstatacaoFiscalFormatada;
	
	private EnderecoDto enderecoLocalFiscalizado;
	
	private DividaDto dividaInfracao;
	
	private EnderecoDto enderecoPessoaAutuada;
	
	private String nomePessoaAutuada;
	
	private Long registroPessoaAutuada;
	
	private String fonteInformacao;
	
	private String qualificacaoFonteInformacao;
	
	private String nomeFiscal;
	
	private Long valorMulta;
	
	private List<ProtocoloDto> protocolosVinculadosProcessoAutoInfracao;
	
	private String ramoFiscalizado;
	
	private String descritivoFatoGeradorRf;
	
	private String matriculaFiscal;
	
	private ResponsavelTecnicoDto profissionalAutuado;
	
	private EmpresaDto empresaAutuada;
	
	private DomainGenericDto tipoAuto;
	
	private MultaInfracaoDto valoresAplicaveis; 
	
	private String letraFundamento;
	
	private List<AutoReincidenciaDto> autosMesmaCapitulacao;
	
	private String tipoAtividadeFiscalizada;
	
	private String atividadeFiscalizada;
	
	private String descricaoCapitulacao;
	
	private String enquadramentoCapitulacao;
	
	public String getNumeroRf() {
		return numeroRf;
	}

	public void setNumeroRf(String numeroRf) {
		this.numeroRf = numeroRf;
	}

	public String getNumeroDoAuto() {
		return numeroDoAuto;
	}

	public void setNumeroDoAuto(String numeroDoAuto) {
		this.numeroDoAuto = numeroDoAuto;
	}

	public String getCodigoFatoGerador() {
		return codigoFatoGerador;
	}

	public void setCodigoFatoGerador(String codigoFatoGerador) {
		this.codigoFatoGerador = codigoFatoGerador;
	}

	public String getDescricaoFatoGerador() {
		return descricaoFatoGerador;
	}

	public void setDescricaoFatoGerador(String descricaoFatoGerador) {
		this.descricaoFatoGerador = descricaoFatoGerador;
	}

	public Date getDataLavraturaAuto() {
		return dataLavraturaAuto;
	}

	public void setDataLavraturaAuto(Date dataLavraturaAuto) {
		this.dataLavraturaAuto = dataLavraturaAuto;
	}

	public String getDataLavraturaAutoFormatada() {
		return dataLavraturaAutoFormatada;
	}

	public void setDataLavraturaAutoFormatada(String dataLavraturaAutoFormatada) {
		this.dataLavraturaAutoFormatada = dataLavraturaAutoFormatada;
	}

	public Date getDataConstatacaoFiscal() {
		return dataConstatacaoFiscal;
	}

	public void setDataConstatacaoFiscal(Date dataConstatacaoFiscal) {
		this.dataConstatacaoFiscal = dataConstatacaoFiscal;
	}

	public String getDataConstatacaoFiscalFormatada() {
		return dataConstatacaoFiscalFormatada;
	}

	public void setDataConstatacaoFiscalFormatada(
			String dataConstatacaoFiscalFormatada) {
		this.dataConstatacaoFiscalFormatada = dataConstatacaoFiscalFormatada;
	}

	public EnderecoDto getEnderecoLocalFiscalizado() {
		return enderecoLocalFiscalizado;
	}

	public void setEnderecoLocalFiscalizado(EnderecoDto enderecoLocalFiscalizado) {
		this.enderecoLocalFiscalizado = enderecoLocalFiscalizado;
	}

	public DividaDto getDividaInfracao() {
		return dividaInfracao;
	}

	public void setDividaInfracao(DividaDto dividaInfracao) {
		this.dividaInfracao = dividaInfracao;
	}

	public EnderecoDto getEnderecoPessoaAutuada() {
		return enderecoPessoaAutuada;
	}

	public void setEnderecoPessoaAutuada(EnderecoDto enderecoPessoaAutuada) {
		this.enderecoPessoaAutuada = enderecoPessoaAutuada;
	}

	public String getNomePessoaAutuada() {
		return nomePessoaAutuada;
	}

	public void setNomePessoaAutuada(String nomePessoaAutuada) {
		this.nomePessoaAutuada = nomePessoaAutuada;
	}

	public Long getRegistroPessoaAutuada() {
		return registroPessoaAutuada;
	}

	public void setRegistroPessoaAutuada(Long registroPessoaAutuada) {
		this.registroPessoaAutuada = registroPessoaAutuada;
	}

	public String getFonteInformacao() {
		return fonteInformacao;
	}

	public void setFonteInformacao(String fonteInformacao) {
		this.fonteInformacao = fonteInformacao;
	}

	public String getQualificacaoFonteInformacao() {
		return qualificacaoFonteInformacao;
	}

	public void setQualificacaoFonteInformacao(String qualificacaoFonteInformacao) {
		this.qualificacaoFonteInformacao = qualificacaoFonteInformacao;
	}

	public String getNomeFiscal() {
		return nomeFiscal;
	}

	public void setNomeFiscal(String nomeFiscal) {
		this.nomeFiscal = nomeFiscal;
	}

	public Long getValorMulta() {
		return valorMulta;
	}

	public void setValorMulta(Long valorMulta) {
		this.valorMulta = valorMulta;
	}

	public List<ProtocoloDto> getProtocolosVinculadosProcessoAutoInfracao() {
		return protocolosVinculadosProcessoAutoInfracao;
	}

	public void setProtocolosVinculadosProcessoAutoInfracao(List<ProtocoloDto> protocolosVinculadosProcessoAutoInfracao) {
		this.protocolosVinculadosProcessoAutoInfracao = protocolosVinculadosProcessoAutoInfracao;
	}

	public String getRamoFiscalizado() {
		return ramoFiscalizado;
	}

	public void setRamoFiscalizado(String ramoFiscalizado) {
		this.ramoFiscalizado = ramoFiscalizado;
	}

	public String getDescritivoFatoGeradorRf() {
		return descritivoFatoGeradorRf;
	}

	public void setDescritivoFatoGeradorRf(String descritivoFatoGeradorRf) {
		this.descritivoFatoGeradorRf = descritivoFatoGeradorRf;
	}

	public String getMatriculaFiscal() {
		return matriculaFiscal;
	}

	public void setMatriculaFiscal(String matriculaFiscal) {
		this.matriculaFiscal = matriculaFiscal;
	}

	public ResponsavelTecnicoDto getProfissionalAutuado() {
		return profissionalAutuado;
	}

	public void setProfissionalAutuado(ResponsavelTecnicoDto profissionalAutuado) {
		this.profissionalAutuado = profissionalAutuado;
	}

	public EmpresaDto getEmpresaAutuada() {
		return empresaAutuada;
	}

	public void setEmpresaAutuada(EmpresaDto empresaAutuada) {
		this.empresaAutuada = empresaAutuada;
	}

	public DomainGenericDto getTipoAuto() {
		return tipoAuto;
	}

	public void setTipoAuto(DomainGenericDto tipoAuto) {
		this.tipoAuto = tipoAuto;
	}

	public MultaInfracaoDto getValoresAplicaveis() {
		return valoresAplicaveis;
	}

	public void setValoresAplicaveis(MultaInfracaoDto valoresAplicaveis) {
		this.valoresAplicaveis = valoresAplicaveis;
	}

	public String getLetraFundamento() {
		return letraFundamento;
	}

	public void setLetraFundamento(String letraFundamento) {
		this.letraFundamento = letraFundamento;
	}

	public List<AutoReincidenciaDto> getAutosMesmaCapitulacao() {
		return autosMesmaCapitulacao;
	}

	public void setAutosMesmaCapitulacao(List<AutoReincidenciaDto> autosMesmaCapitulacao) {
		this.autosMesmaCapitulacao = autosMesmaCapitulacao;
	}

	public String getTipoAtividadeFiscalizada() {
		return tipoAtividadeFiscalizada;
	}

	public void setTipoAtividadeFiscalizada(String tipoAtividadeFiscalizada) {
		this.tipoAtividadeFiscalizada = tipoAtividadeFiscalizada;
	}

	public String getAtividadeFiscalizada() {
		return atividadeFiscalizada;
	}

	public void setAtividadeFiscalizada(String atividadeFiscalizada) {
		this.atividadeFiscalizada = atividadeFiscalizada;
	}

	public String getDescricaoCapitulacao() {
		return descricaoCapitulacao;
	}

	public void setDescricaoCapitulacao(String descricaoCapitulacao) {
		this.descricaoCapitulacao = descricaoCapitulacao;
	}

	public String getEnquadramentoCapitulacao() {
		return enquadramentoCapitulacao;
	}

	public void setEnquadramentoCapitulacao(String enquadramentoCapitulacao) {
		this.enquadramentoCapitulacao = enquadramentoCapitulacao;
	}
	
}
