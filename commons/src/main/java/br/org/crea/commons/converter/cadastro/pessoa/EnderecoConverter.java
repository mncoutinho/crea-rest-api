package br.org.crea.commons.converter.cadastro.pessoa;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.dao.cadastro.pessoa.EnderecoDao;
import br.org.crea.commons.models.commons.Endereco;
import br.org.crea.commons.models.commons.Localidade;
import br.org.crea.commons.models.commons.Logradouro;
import br.org.crea.commons.models.commons.TipoEndereco;
import br.org.crea.commons.models.commons.TipoLogradouro;
import br.org.crea.commons.models.commons.UF;
import br.org.crea.commons.models.commons.dtos.DomainGenericDto;
import br.org.crea.commons.models.commons.dtos.EnderecoDto;
import br.org.crea.commons.models.commons.dtos.LocalidadeDto;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;
import br.org.crea.commons.util.DateUtils;
import br.org.crea.commons.util.StringUtil;

public class EnderecoConverter {
	
	@Inject
	EnderecoDao enderecoDao;

	public EnderecoDto toDto(Endereco model) {
		EnderecoDto dto = new EnderecoDto();
		try {
			if(model == null){
				return null;
			}
				
			
			
			dto.setId(model.getId());
			dto.setIdString(String.valueOf(model.getId()));
			
			if(model.temTipoLogradouro()){
				dto.setTipoLogradouro(toTipoLogradouroDto(model.getTipoLogradouro()));
			}
			
			dto.setUf(toUfDto(model.getUf()));
			dto.setLocalidade(toLocalidadeDto(model.getLocalidade()));
			dto.setTipoEndereco(toTipoEnderecoDto(model.getTipoEndereco()));
			dto.setPostal(model.isPostal() ? "SIM" : "NAO");
			dto.setLogradouro(model.getLogradouro());
			dto.setNumero(model.getNumero());
			dto.setBairro(model.getBairro());
			dto.setComplemento(model.getComplemento());
			dto.setCep(model.getCep());
			dto.setLatitude(model.getLatitude());
			dto.setLongitude(model.getLongitude());
			dto.setAproximado(model.isAproximado());
			dto.setDataInclusao(DateUtils.format(model.getDataEndereco(), DateUtils.DD_MM_YYYY));
			dto.setEnderecoCompleto(transformaEnderecoCompleto(model));
			dto.setEnderecoCompletoAnalise(tranformaeEnderecoCompletoAnalise(model));
		} catch (Throwable e) {
			System.err.println(e.getMessage());
		}
		
		
		return dto;
	}

	public EnderecoDto toDto(Logradouro logradouro) {
		
		EnderecoDto dto = new EnderecoDto();
		
		dto.setCep(logradouro.getCep());
		dto.setTipoLogradouro(toTipoLogradouroDto(logradouro.getTipoLogradouro()));
		dto.setLogradouro(logradouro.getDescricao());
		dto.setBairro(logradouro.temBairro() ? logradouro.getBairro().getDescricao() : null);
		dto.setLocalidade(toLocalidadeDto(logradouro.getLocalidade()));
		dto.setUf(toUfDto(logradouro.getUf()));
				
		return dto;
	}
	
	public String transformaEnderecoCompleto(Endereco endereco){

		StringBuilder enderecoConcat = new StringBuilder();
		
		if(endereco.getTipoLogradouro() != null){
			enderecoConcat.append(endereco.getTipoLogradouro().getDescricao() + " ");
		}
		
		enderecoConcat.append(endereco.getLogradouro());
		
		if(endereco.getNumero() != null && endereco.getNumero().trim().length() > 0){
			enderecoConcat.append(", " + endereco.getNumero());
		}
		
		if(endereco.getBairro() != null){
			enderecoConcat.append(" - " + endereco.getBairro());
		}
		
		enderecoConcat.append(" - " + endereco.getLocalidade().getDescricao());
		enderecoConcat.append(" - " + endereco.getUf().getSigla());
		
		if(endereco.getCep() != null){
			enderecoConcat.append(" - " + endereco.getCepFormatado());
		}
		
		return enderecoConcat.toString();
	}
	
	public String transformaEnderecoSemCep(Endereco endereco){

		StringBuilder enderecoConcat = new StringBuilder();
		
		if(endereco.getTipoLogradouro() != null){
			enderecoConcat.append(endereco.getTipoLogradouro().getDescricao() + " ");
		}
		
		enderecoConcat.append(endereco.getLogradouro());
		
		if(endereco.getNumero() != null && endereco.getNumero().trim().length() > 0){
			enderecoConcat.append(", " + endereco.getNumero());
		}
		
		if(endereco.getBairro() != null){
			enderecoConcat.append(" - " + endereco.getBairro());
		}
		
		enderecoConcat.append(" - " + endereco.getLocalidade().getDescricao());
		enderecoConcat.append(" - " + endereco.getUf().getSigla());
		
		
		
		return enderecoConcat.toString();
	}
	
	
	private String tranformaeEnderecoCompletoAnalise(Endereco endereco) {
		
		StringBuilder enderecoConcat = new StringBuilder();
		
		if(endereco.getTipoLogradouro() != null){
			enderecoConcat.append(endereco.getTipoLogradouro().getDescricao() + " ");
		}
		
		enderecoConcat.append(endereco.getLogradouro());
		
		if(endereco.getNumero() != null && endereco.getNumero().trim().length() > 0){
			enderecoConcat.append(", " + endereco.getNumero());
		}
		
		if(endereco.getBairro() != null){
			enderecoConcat.append(" - " + endereco.getBairro());
		}
		
		enderecoConcat.append(" - " + endereco.getLocalidade().getDescricao());
		enderecoConcat.append(" - " + endereco.getUf().getSigla());
		
		if(endereco.getCep() != null){
			enderecoConcat.append(" - " + endereco.getCepFormatado());
		}
		
		return enderecoConcat.toString();
	}
	
	public EnderecoDto toDto(Localidade localidade) {
			
		EnderecoDto dto = new EnderecoDto();
		
		dto.setCep(localidade.getCep());
		dto.setLocalidade(toLocalidadeDto(localidade));
		dto.setUf(toUfDto(localidade.getUf()));
		
		return dto;
	}
	
	public DomainGenericDto toTipoEnderecoDto(TipoEndereco model) {
		
		DomainGenericDto dto = new DomainGenericDto();
		
		dto.setId(model.getId());
		dto.setDescricao(model.getDescricao() == null ? "" : model.getDescricao());
		
		return dto;
	}
	
	public DomainGenericDto toTipoLogradouroDto(TipoLogradouro model) {
	
		DomainGenericDto dto = new DomainGenericDto();
		
		dto.setId(model.getId());
		dto.setDescricao(model.getDescricao());
		
		return dto;
	}
	
	public DomainGenericDto toMunicipioDto(Localidade model) {
		
		DomainGenericDto dto = new DomainGenericDto();
		
		dto.setId(model.getId());
		dto.setDescricao(model.getDescricao());
		
		return dto;
	}
	
	public List<EnderecoDto> toListDto(List<Endereco> listModel) {
		List<EnderecoDto> listDto = new ArrayList<EnderecoDto>();
		
		for(Endereco e : listModel){
			listDto.add(toDto(e));
		}
		
		return listDto;
	}
	
	
	public List<DomainGenericDto> toListTipoEnderecoDto(List<TipoEndereco> listModel) {
		List<DomainGenericDto> listDto = new ArrayList<DomainGenericDto>();
		
		for(TipoEndereco te : listModel){
			listDto.add(toTipoEnderecoDto(te));
		}
		
		return listDto;
	}
	
	public List<DomainGenericDto> toListTipoLogradouroDto(List<TipoLogradouro> listModel) {
		List<DomainGenericDto> listDto = new ArrayList<DomainGenericDto>();
		
		for(TipoLogradouro tl : listModel){
			listDto.add(toTipoLogradouroDto(tl));
		}
		
		return listDto;
	}
	
	public List<DomainGenericDto> toListMunicipioDto(List<Localidade> listModel) {
		List<DomainGenericDto> listDto = new ArrayList<DomainGenericDto>();
		
		for(Localidade tl : listModel){
			listDto.add(toMunicipioDto(tl));
		}
		
		return listDto;
	}

	public Endereco toModel(EnderecoDto dto) {
		
		Endereco endereco = new Endereco();
		
		Long idEndereco = null;
		if(dto.getIdString() != null) idEndereco = Long.parseLong(dto.getIdString());
		endereco.setId(idEndereco);
		endereco.setDataEndereco(new Date());
		
		if (dto.temTipoLogradouro()) {
			endereco.setTipoLogradouro(toTipoLogradouroModel(dto.getTipoLogradouro()));
		}
		
		if (dto.temLocalidade()) {
			Localidade localidade = new Localidade();
			localidade.setId(dto.getLocalidade().getId());
			localidade.setDescricao(dto.getLocalidade().getDescricao());
			endereco.setLocalidade(localidade);
		}
		
		if (dto.temUf()) {
			UF uf = new UF();
			uf.setId(dto.getUf().getId());
			uf.setSigla(dto.getUf().getSigla());
			endereco.setUf(uf);
		}
		
		if (dto.getCodPessoa() != null) {
			Pessoa pessoa = new Pessoa();
			pessoa.setId(dto.getCodPessoa());
			endereco.setPessoa(pessoa);
		}
		
		if (dto.temTipoEndereco()) {
			TipoEndereco tipo = new TipoEndereco();
			tipo.setId(dto.getTipoEndereco().getId());
			endereco.setTipoEndereco(tipo);
		}
		
		endereco.setCep(dto.getCep() != null ? dto.getCep().replace("-", "") : null);
		endereco.setLogradouro(dto.temLogradouro() ? dto.getLogradouro().toUpperCase() : null);
		endereco.setNumero(dto.temNumero() ? dto.getNumero().toUpperCase() : null);
		endereco.setComplemento(dto.temComplemento() ? dto.getComplemento().toUpperCase() : null);
		endereco.setBairro(dto.temBairro() ? dto.getBairro().toUpperCase() : null);
		endereco.setLatitude(StringUtil.formataCoordenadaGeografica(dto.getLatitude()));
		endereco.setLongitude(StringUtil.formataCoordenadaGeografica(dto.getLongitude()));
		endereco.setAproximado(dto.isAproximado());
		endereco.setPostal(dto.ehPostal());
		
		endereco.setValido(true);
		
		return endereco;
	}

	private TipoLogradouro toTipoLogradouroModel(DomainGenericDto dto) {
		TipoLogradouro tipoLogradouro = new TipoLogradouro();
		tipoLogradouro.setId(dto.getId());
		tipoLogradouro.setDescricao(dto.getDescricao());
		return tipoLogradouro;
	}

	public List<EnderecoDto> toListLogradourosECep(List<Object[]> listaLogradouros){
		List<EnderecoDto> lista = new ArrayList<EnderecoDto>();
		
		for(int i = 0; i< listaLogradouros.size(); i++){
			Object[] obj = listaLogradouros.get(i);
			lista.add(converteLogradouroEmEnderecoDto(obj));
		}
		
		return lista;
	}

	private EnderecoDto converteLogradouroEmEnderecoDto(Object[] obj) {
		StringBuilder enderecoConcatenado = new StringBuilder();
		enderecoConcatenado.append((String)obj[4] + " ");
		enderecoConcatenado.append((String)obj[0]);
		
		String numInicio = (String)obj[6];
		String numFinal = (String)obj[7];
		if(!numInicio.equals("0") && !numFinal.equals("9999999999")){
			enderecoConcatenado.append(" - ");
			enderecoConcatenado.append(numInicio);
			enderecoConcatenado.append(" até ");
			enderecoConcatenado.append(numFinal);
		}else if(numInicio.equals("0") && !numFinal.equals("9999999999")){
			enderecoConcatenado.append(" - ");
			enderecoConcatenado.append(" até ");
			enderecoConcatenado.append(numFinal);
		}else if(!numInicio.equals("0") && numFinal.equals("9999999999")){
			enderecoConcatenado.append(" - ");
			enderecoConcatenado.append(numInicio);
			enderecoConcatenado.append(" até ");
			enderecoConcatenado.append(" ao fim ");
		}
		
		BigDecimal par = (BigDecimal)obj[8];
		BigDecimal impar = (BigDecimal)obj[9];
		
		if(par.equals(new BigDecimal(1))){
			enderecoConcatenado.append(" - ");
			enderecoConcatenado.append("lado par");
		}else if(impar.equals(new BigDecimal(1))){
			enderecoConcatenado.append(" - ");
			enderecoConcatenado.append("lado impar");
		}
		
		LocalidadeDto localidade = new LocalidadeDto();		
		localidade.setId(Long.parseLong(String.valueOf(obj[12])));
		localidade.setDescricao((String)obj[2]); 
		localidade.setNome((String)obj[2] + "/" + (String)obj[3]); 
		
		DomainGenericDto tipoLogradouro = new DomainGenericDto();
		tipoLogradouro.setId(Long.parseLong(String.valueOf(obj[11])));
		tipoLogradouro.setDescricao((String)obj[4]);
		
		DomainGenericDto uf = new DomainGenericDto();
		uf.setId(Long.parseLong(String.valueOf(obj[10])));
		uf.setSigla((String)obj[3]);
		
		EnderecoDto dto = new EnderecoDto();
		dto.setEnderecoCompleto(enderecoConcatenado.toString());
		dto.setLogradouro((String)obj[0]);
		dto.setBairro((String)obj[5]);
		dto.setLocalidade(localidade);
		dto.setCep((String)obj[1]);
		dto.setTipoLogradouro(tipoLogradouro);
		dto.setUf(uf);
		
		return dto;
	}

	public List<DomainGenericDto> toListUfDto(List<UF> lista) {
		List<DomainGenericDto> listaDto = new ArrayList<DomainGenericDto>();
		for(UF uf : lista){
			listaDto.add(toUfDto(uf));
		}
		return listaDto;
	}

	public DomainGenericDto toUfDto(UF uf) {
		DomainGenericDto dto = new DomainGenericDto();
		dto.setId(uf.getId());
		dto.setSigla(uf.getSigla());
		dto.setDescricao(uf.getDescricao());
		return dto;
	}

	public LocalidadeDto toLocalidadeDto(Localidade localidade) {
		LocalidadeDto dto = new LocalidadeDto();
		dto.setId(localidade.getId());
		dto.setDescricao(localidade.getDescricao());
		
		dto.setCep(localidade.getCep());
		DomainGenericDto uf = new DomainGenericDto();
		uf.setId(localidade.getUf() == null ? null : localidade.getUf().getId());
		uf.setSigla(localidade.getUf() == null ? null : localidade.getUf().getSigla());
		dto.setUf(uf);
		
		return dto;
	}	
}
