package br.org.crea.commons.service.art;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.art.ContratoServicoConverter;
import br.org.crea.commons.converter.cadastro.pessoa.EnderecoConverter;
import br.org.crea.commons.converter.cadastro.pessoa.TelefoneConverter;
import br.org.crea.commons.dao.cadastro.pessoa.EnderecoDao;
import br.org.crea.commons.dao.cadastro.pessoa.TelefoneDao;
import br.org.crea.commons.dao.fiscalizacao.ContratoInativadoDao;
import br.org.crea.commons.dao.fiscalizacao.ContratoServicoDao;
import br.org.crea.commons.models.art.dtos.ContratoServicoDto;
import br.org.crea.commons.models.art.dtos.PesquisaContratoDto;
import br.org.crea.commons.models.commons.Endereco;
import br.org.crea.commons.models.commons.Telefone;
import br.org.crea.commons.models.fiscalizacao.ContratoInativado;
import br.org.crea.commons.models.fiscalizacao.ContratoServico;

public class ContratoService {

	@Inject ContratoServicoDao dao;
	
	@Inject TelefoneDao telefoneDao;
	
	@Inject EnderecoDao enderecoDao;
	
	@Inject ContratoServicoConverter converter;
	
	@Inject EnderecoConverter enderecoConverter;
	
	@Inject TelefoneConverter telefoneConverter;
	
	@Inject ContratoInativadoDao contratoInativadoDao;	

	public List<ContratoServicoDto> getServicosPorContratante(PesquisaContratoDto dto) {
		return converter.toListDto(dao.getServicosPorContratante(dto));
	}

	public ContratoServicoDto salvar(ContratoServicoDto dto) {

		ContratoServico contratoServico = converter.toModel(dto);

		inativarContratosAnteriores(dto);
		contratoServico = dao.salvar(contratoServico);

		if (dto.getEnderecoContrato().getId() == null) {
			salvarEnderecoContratado(dto, contratoServico);
		}

		List<Telefone> telefones = telefoneConverter.toListModel(dto.getTelefonesContratado());

		for (Telefone telefone : telefones) {
			if (!telefoneDao.existeTelefoneCadastrado(telefone, contratoServico.getContratado())) {
				if (telefone.getCodigo() == null) {
					telefone.setPessoa(contratoServico.getContratado());
					telefoneDao.create(telefone);
				}
			}

		}

		return converter.toDto(contratoServico);
	}

	private void salvarEnderecoContratado(ContratoServicoDto dto, ContratoServico contratoServico) {
		Endereco endereco = enderecoConverter.toModel(dto.getEnderecoContrato());

		endereco.setPostal(true);
		endereco.setValido(true);
		endereco.setPessoa(contratoServico.getContratado());

		enderecoDao.create(endereco);
	}

	public ContratoServicoDto substituirContratoAtividade(ContratoServicoDto dto) {

		return salvar(dto);
	}

	public boolean existeAtividadeParaContratanteInformado(ContratoServicoDto dto) {
		
		ContratoServico servico = new ContratoServico();
		servico = dao.getServicoAtivoPorContratanteContratadoEAtividade(dto);
		
		if(servico != null) {
			if ( servico.getDataFinal().before(dto.getDataInicio()) || servico.getDataFinal().equals(dto.getDataInicio())) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}
	
	public void inativarContratosAnteriores(ContratoServicoDto dto) {
		
		List<ContratoServico> lista = new ArrayList<ContratoServico>();
		lista = dao.getListServicosPorContratanteContratadoEAtividade(dto);

		for (ContratoServico contrato : lista) {
			contrato.setAtivo(false);
			dao.update(contrato);
		}
	}
	
	public void interromperContratoAtividade(ContratoServicoDto dto) {
		ContratoInativado contratoInativado = converter.toModelContratoInativado(dto);
		contratoInativadoDao.create(contratoInativado);
		
		inativarContratosAnteriores(dto);		
	}	
}
