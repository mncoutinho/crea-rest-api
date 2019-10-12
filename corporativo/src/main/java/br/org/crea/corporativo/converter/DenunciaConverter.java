package br.org.crea.corporativo.converter;

import javax.inject.Inject;

import br.org.crea.commons.converter.cadastro.pessoa.EnderecoConverter;
import br.org.crea.commons.models.corporativo.Denuncia;
import br.org.crea.commons.models.corporativo.dtos.DenunciaDto;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

public class DenunciaConverter {
	
	@Inject HttpClientGoApi httpGoApi;
	
	@Inject EnderecoConverter enderecoConverter;
	
	public Denuncia toModel(DenunciaDto dto){
		
		Denuncia model = new Denuncia();
		Pessoa pessoa = new Pessoa();
		
		try {

			
			pessoa.setId(new Long(dto.getPessoa().getId()));
			
			model.setNumero(dto.getNumero());			
			model.setMotivo(dto.getMotivo());
			model.setDenunciado(dto.getDenunciado());
			model.setEndereco(enderecoConverter.toModel(dto.getEndereco()));
			model.setDataDenuncia(dto.getDataDenuncia());
			model.setDataCadastro(dto.getDataCadastro());
			model.setTipoDenuncia(dto.getTipoDenuncia());
			model.setProcedenciaDenuncia(dto.getProcedenciaDenuncia());
/*			model.setProtocolo(dto.getProtocolo());
			model.setDepartamento(dto.getDepartamento());
			model.setFuncionario(dto.getFuncionario());*/
			model.setPessoa(pessoa);
			model.setTipoPessoa(dto.getTipoPessoa());
/*			model.setDdd(dto.getDdd());
			model.setTelefone(dto.getTelefone());
			model.setEmail(dto.getEmail());*/
			model.setFatoGerador(dto.getFatoGerador());
/*			model.setDescritivoFatoGerador(dto.getDescritivoFatoGerador());
			model.setDataUltimaAlteracao(dto.getDataUltimaAlteracao());
			model.setFuncionarioAlteracao(dto.getFuncionarioAlteracao());
			model.setFuncionarioCadastro(dto.getFuncionarioCadastro());
*/
			
		} catch (Throwable e) {
			httpGoApi.geraLog("DenunciaConverter || toModel", StringUtil.convertObjectToJson(model), e);
		}
		
		return model;
	}
}
