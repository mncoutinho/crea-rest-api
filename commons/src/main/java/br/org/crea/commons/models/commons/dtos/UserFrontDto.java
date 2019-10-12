package br.org.crea.commons.models.commons.dtos;

import java.util.Map;

import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.ObjectMapper;

import br.org.crea.commons.models.corporativo.dtos.SituacaoDto;
import br.org.crea.commons.models.corporativo.enuns.TipoPessoa;


@JsonPropertyOrder({ "registro", "tipoPessoa", "nome", "razaoSocial", "departamento", "token", "base64" })
public class UserFrontDto {

	private String registro;
	
	private TipoPessoa tipoPessoa;
	
	private String cpfOuCnpj;	
	
	private String nome;
	
	private Long idPessoa;
	
	private Long idFuncionario;
	
	private Long matricula;
	
	private String razaoSocial;	
	
	private String token;
	
	private String base64;
	
	private String base64Assinatura;
	
	private String email;
	
	private GenericDto departamento;
	
	private GenericDto cargo;
	
	private boolean semRegistro;
	
	private GenericDto unidadeDeAtendimento;
	
	private Object perfil;
	
	private String ip;	
	
	private String sistemaOperacional;
	
	private String browser;
	
	private SituacaoDto situacao;
	
	private boolean trocarSenha;
	
	private Long idInstituicao;
	
	private String mensagem;
	

	public Long getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(Long idPessoa) {
		this.idPessoa = idPessoa;
	}

	public String getRegistro() {
		return registro;
	}

	public void setRegistro(String registro) {
		this.registro = registro;
	}

	public TipoPessoa getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(TipoPessoa tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}

	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(Long idFuncionario) {
		this.idFuncionario = idFuncionario;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getBase64() {
		return base64;
	}

	public void setBase64(String base64) {
		this.base64 = base64;
	}

	public String getBase64Assinatura() {
		return base64Assinatura;
	}

	public void setBase64Assinatura(String base64Assinatura) {
		this.base64Assinatura = base64Assinatura;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public GenericDto getDepartamento() {
		return departamento;
	}

	public void setDepartamento(GenericDto departamento) {
		this.departamento = departamento;
	}

	public boolean naoTemRegistroNoCrea() {
		return semRegistro;
	}

	public void setSemRegistro(boolean semRegistro) {
		this.semRegistro = semRegistro;
	}

	public GenericDto getUnidadeDeAtendimento() {
		return unidadeDeAtendimento;
	}

	public void setUnidadeDeAtendimento(GenericDto unidadeDeAtendimento) {
		this.unidadeDeAtendimento = unidadeDeAtendimento;
	}

	public Object getPerfil() {
		return perfil;
	}

	public void setPerfil(Object perfil) {
		this.perfil = perfil;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getSistemaOperacional() {
		return sistemaOperacional;
	}

	public void setSistemaOperacional(String sistemaOperacional) {
		this.sistemaOperacional = sistemaOperacional;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}


	public Long getMatricula() {
		return matricula;
	}

	public void setMatricula(Long matricula) {
		this.matricula = matricula;
	}

	public SituacaoDto getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoDto situacao) {
		this.situacao = situacao;
	}

	public GenericDto getCargo() {
		return cargo;
	}

	public void setCargo(GenericDto cargo) {
		this.cargo = cargo;
	}

	public boolean isSemRegistro() {
		return semRegistro;
	}

	public boolean isTrocarSenha() {
		return trocarSenha;
	}

	public void setTrocarSenha(boolean trocarSenha) {
		this.trocarSenha = trocarSenha;
	}

	public boolean heProfissional() {
		return this.getTipoPessoa().equals(TipoPessoa.PROFISSIONAL);
	}

	public Long getIdInstituicao() {
		return idInstituicao;
	}

	public void setIdInstituicao(Long idInstituicao) {
		this.idInstituicao = idInstituicao;
	}

	public boolean heEmpresa() {
		return this.getTipoPessoa().equals(TipoPessoa.EMPRESA);
	}

	public boolean temPerfil() {
		return this.perfil != null;
	}	
	
	public String getPerfilString() {
		if (this.temPerfil()) {
			ObjectMapper oMapper = new ObjectMapper();
	        Map<?, ?> map = oMapper.convertValue(this.getPerfil(), Map.class);
	        if (map.get("perfil") != null) {
	        	return String.valueOf(map.get("perfil"));
	        }
		}
		return "";
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
}
