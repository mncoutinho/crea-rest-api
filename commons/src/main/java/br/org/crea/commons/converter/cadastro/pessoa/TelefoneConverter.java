package br.org.crea.commons.converter.cadastro.pessoa;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.dao.cadastro.pessoa.PessoaDao;
import br.org.crea.commons.models.commons.Telefone;
import br.org.crea.commons.models.commons.TipoTelefone;
import br.org.crea.commons.models.commons.dtos.DomainGenericDto;
import br.org.crea.commons.models.commons.dtos.TelefoneDto;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;
import br.org.crea.commons.util.StringUtil;

public class TelefoneConverter {

	@Inject
	PessoaDao pessoaDao;

	public List<TelefoneDto> toListDto(List<Telefone> lista) {
		List<TelefoneDto> resultado = new ArrayList<TelefoneDto>();

		for (Telefone telefone : lista) {
			resultado.add(toDto(telefone));
		}

		return resultado;
	}

	public TelefoneDto toDto(Telefone model) {

		TelefoneDto dto = new TelefoneDto();
		dto.setCodigo(model.getCodigo());
		dto.setCodPessoa(model.getPessoa().getId());
		DomainGenericDto tipoTelefone = new DomainGenericDto();
		tipoTelefone.setId(model.getTipoTelefone().getCodigo());
		tipoTelefone.setDescricao(model.getTipoTelefone().getDescricao());
		dto.setTipoTelefone(tipoTelefone);
		dto.setDdd(model.getDdd() != null ? model.getDdd() : "xx");
		dto.setNumero(model.getNumero());

		if (model.getTipoTelefone() != null && model.getTipoTelefone().getCodigo().equals(2L)) {
			dto.setTelefoneFormatado(StringUtil.formataTelefone(model.getDdd() + model.getNumero()));
		} else if (model.getTipoTelefone() != null && model.getTipoTelefone().getCodigo().equals(3L)) {
			dto.setTelefoneFormatado(StringUtil.formataCelular(model.getDdd() + model.getNumero()));
		}

		return dto;
	}

	public List<Telefone> toListModel(List<TelefoneDto> lista) {
		List<Telefone> resultado = new ArrayList<Telefone>();

		for (TelefoneDto dto : lista) {
			resultado.add(toModel(dto));
		}

		return resultado;
	}

	public Telefone toModel(TelefoneDto dto) {

		Telefone telefone = new Telefone();
		Pessoa pessoa = new Pessoa();

		TipoTelefone tipoTelefone = new TipoTelefone();
		tipoTelefone.setCodigo(dto.getTipoTelefone().getId());
		telefone.setTipoTelefone(tipoTelefone);

		if (dto.temId()) {
			telefone.setCodigo(dto.getCodigo());
		}
		
		pessoa.setId(dto.getCodPessoa());
		telefone.setPessoa(pessoa);
		telefone.setDdd(dto.getDdd());
		telefone.setNumero(dto.getNumero());

		return telefone;
	}
}
