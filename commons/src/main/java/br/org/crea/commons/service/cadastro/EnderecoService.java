package br.org.crea.commons.service.cadastro;

import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.cadastro.pessoa.EnderecoConverter;
import br.org.crea.commons.dao.cadastro.pessoa.EnderecoDao;
import br.org.crea.commons.models.commons.Endereco;
import br.org.crea.commons.models.commons.Localidade;
import br.org.crea.commons.models.commons.Logradouro;
import br.org.crea.commons.models.commons.dtos.DomainGenericDto;
import br.org.crea.commons.models.commons.dtos.EnderecoDto;
import br.org.crea.commons.models.commons.dtos.LocalidadeDto;
import br.org.crea.commons.service.HttpClientGoApi;

public class EnderecoService {
	
	@Inject
	HttpClientGoApi httpGoApi;
	
	@Inject EnderecoConverter converter;
	
	@Inject EnderecoDao dao;

	public EnderecoDto getBy(Long id) {
		return converter.toDto(dao.getBy(id));
	}

	public List<DomainGenericDto> getTipoEndereco() {
		return converter.toListTipoEnderecoDto(dao.getTipoEndereco());
	}

	public List<DomainGenericDto> getTipoLogradouro() {
		return converter.toListTipoLogradouroDto(dao.getTipoLogradouro());
	}
	
	public DomainGenericDto getTipoLogradouroByDescricao(String descricao) {
		return converter.toTipoLogradouroDto(dao.getTipoLogradouroByDescricao(descricao));
	}

	public List<DomainGenericDto> getMunicipioBySigla(String uf) {
		return converter.toListMunicipioDto(dao.getMunicipioBySigla(uf));
	}

	public List<DomainGenericDto> getMunicipioByIdUf(Long idUf) {
		return converter.toListMunicipioDto(dao.getMunicipioByIdUf(idUf));
	}
	
	public EnderecoDto getEnderecoByCep(String cep) {
		Logradouro logradouro = dao.getLogradouroByCep(cep);
		
		if (logradouro != null) {
			return converter.toDto(logradouro);
			
		} else {
			Localidade localidade = dao.getLocalidadeByCep(cep);
			
			if(localidade != null){
				return converter.toDto(localidade);	
			} else {
				return null;
			}
		}
	}

	public EnderecoDto getEnderecoPessoaById(Long id) {
		return converter.toDto(dao.getEnderecoPessoaById(id));
	}
	
	public EnderecoDto getEnderecoValidoPessoaById(Long id) {
		return converter.toDto(dao.getEnderecoPessoaById(id));
	}

	public List<EnderecoDto> listarCepPorLogradouro(EnderecoDto enderecoDto) {
		return dao.getLogradourosByFilter(enderecoDto);
	}

	public List<DomainGenericDto> getUf() {
		return converter.toListUfDto(dao.getUf());
	}

	public List<DomainGenericDto> getUfPaises() {
		return converter.toListUfDto(dao.getUfPaises());
	}
	
	public LocalidadeDto getLocalidadePorDescricao(String descricao) {
		return converter.toLocalidadeDto(dao.getLocalidadePorDescricao(descricao));
	}

	public DomainGenericDto getUfPorSigla(String sigla) {
		return converter.toUfDto(dao.getUfPorSigla(sigla));
	}

	public LocalidadeDto getLocalidadePorDescricaoEdescricaoUF(String descricaoLocalidade, String descricaoUF) {
		return converter.toLocalidadeDto(dao.getLocalidadePorDescricaoEdescricaoUF(descricaoLocalidade, descricaoUF));
	}

//	public EnderecoDto atualizarEndereco(EnderecoDto dto) {
//		return dao.atualizarEndereco(dto);
//	}
	
	public EnderecoDto adicionaEndereco(EnderecoDto dto) {
		Endereco endereco = dao.create(converter.toModel(dto));		
		return converter.toDto(endereco); 
	}

	public EnderecoDto getEnderecoById(Long idEndereco) {
		return converter.toDto(dao.getEnderecoById(idEndereco));
	}

	public boolean excluiEnderecoById(Long idEndereco) {
		return dao.excluiEnderecoById(idEndereco);
	}

	public EnderecoDto atualizarEnderecoArt(EnderecoDto dto) {
		dao.update(converter.toModel(dto));
		return converter.toDto(dao.getBy(dto.getIdString() != null ? Long.parseLong(dto.getIdString()) : dto.getId()));
	}
	
	public boolean atualizarEndereco(EnderecoDto dto) {
		dto.setId(Long.parseLong(dto.getIdString()));
		boolean naoTemEnderecoPostal = false;
		
		Endereco endereco = dao.getEnderecoValidoEPostalPor(dto.getCodPessoa());
		if (dto.ehPostal()) {
			
			if (endereco != null) {
				dao.updatePostal(endereco.getId(),false);
			}
			dao.atualizarEndereco(dto);
			
		} else {
			
			if (!endereco.getId().equals(dto.getId())) {
				dao.atualizarEndereco(dto);
			} else {
				naoTemEnderecoPostal = true;
			}
		}
		return naoTemEnderecoPostal;
	}

	public List<EnderecoDto> getListEnderecoPorIdPessoa(Long idPessoa) {
		return converter.toListDto(dao.getListEnderecosValidosPor(idPessoa));
	}

	public boolean adicionaEnderecoPessoa(EnderecoDto dto) {
		boolean naoTemEnderecoPostal = false;
		
		Endereco endereco = dao.getEnderecoValidoEPostalPor(dto.getCodPessoa());

		if (dto.ehPostal()) {
			
			if (endereco != null) {
				dao.updatePostal(endereco.getId(),false);
			}
			dao.create(converter.toModel(dto));
			
		} else {
			
			dao.create(converter.toModel(dto));
			naoTemEnderecoPostal = true;
		}
		return naoTemEnderecoPostal;
	}


}
