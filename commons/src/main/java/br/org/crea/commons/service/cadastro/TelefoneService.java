package br.org.crea.commons.service.cadastro;

import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.cadastro.pessoa.TelefoneConverter;
import br.org.crea.commons.dao.cadastro.pessoa.TelefoneDao;
import br.org.crea.commons.models.commons.Telefone;
import br.org.crea.commons.models.commons.TipoTelefone;
import br.org.crea.commons.models.commons.dtos.TelefoneDto;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

public class TelefoneService {

	@Inject
	TelefoneConverter converter;

	@Inject
	TelefoneDao dao;
	
	@Inject
	HttpClientGoApi httpGoApi;

	public List<TelefoneDto> getTelefoneByPessoa(Long idPessoa) {
		return converter.toListDto(dao.getListTelefoneByPessoa(idPessoa));
	}

	public TelefoneDto salvar(TelefoneDto dto) {
		
		Long idTelefone = dao.create(converter.toModel(dto)).getCodigo();
		
		return converter.toDto(dao.getBy(idTelefone)); 
	}

	public TelefoneDto atualizarTelefone(TelefoneDto dto) {
	
		Telefone telefone = new Telefone();
		Pessoa pessoa = new Pessoa();
		TipoTelefone tipoTelefone = new TipoTelefone();
		
		try {
			
			pessoa.setId(dto.getCodPessoa());
			tipoTelefone.setCodigo(dto.getTipoTelefone().getId());
			
			telefone.setCodigo(dto.getCodigo());
			telefone.setPessoa(pessoa);
			telefone.setDdd(dto.getDdd());
			telefone.setNumero(dto.getNumero());
			telefone.setTipoTelefone(tipoTelefone);
			
			dao.update(telefone);
			
			
		} catch (Throwable e) {
			httpGoApi.geraLog("TelefoneService || atualizarTelefone", StringUtil.convertObjectToJson(dto), e);
		}

		return converter.toDto(dao.getBy(dto.getCodigo())); 
	}
	
	public void deletarTelefone(Long codigoTelefone) {
		dao.deleta(codigoTelefone);
	}
}
