package br.org.crea.commons.models.corporativo.pessoa;

import br.org.crea.commons.models.cadastro.Departamento;
import br.org.crea.commons.models.corporativo.SituacaoRegistro;
import br.org.crea.commons.models.corporativo.enuns.TipoPessoa;

public interface IInteressado {

	public Long getId();

	public String getRegistro();

	public TipoPessoa getTipoPessoa();

	public String getCpfCnpj();
	
	public Long getMatricula();

	public String getNomeRazaoSocial();

	public void setNomeRazaoSocial(String nomeRazaoSocial);
	
	public void setDepartamento(Departamento departamento);
	
	public String getNome();

	public String getFotoBase64();
	
//	public String getAssinaturaBase64();
	
	public SituacaoRegistro getSituacao();
	
	public Departamento getDepartamento();
	
	public String getPerfil();
	
	public Long getIdPessoa();

}
