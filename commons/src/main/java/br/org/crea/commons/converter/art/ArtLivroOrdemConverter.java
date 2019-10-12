package br.org.crea.commons.converter.art;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.commons.ArquivoConverter;
import br.org.crea.commons.models.art.Art;
import br.org.crea.commons.models.art.ArtLivroOrdem;
import br.org.crea.commons.models.art.dtos.ArtLivroDeOrdemDto;
import br.org.crea.commons.models.commons.Arquivo;
import br.org.crea.commons.models.corporativo.dtos.PessoaDto;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;
import br.org.crea.commons.service.PessoaService;
import br.org.crea.commons.util.DateUtils;

public class ArtLivroOrdemConverter {
	
	@Inject
	PessoaService pessoaService;
	
	@Inject
	ArquivoConverter arquivoConverter;
	
	public ArtLivroDeOrdemDto toDto(ArtLivroOrdem model) {

		ArtLivroDeOrdemDto dto = new ArtLivroDeOrdemDto();
		dto.setCodigo(String.valueOf((model.getId())));
		dto.setNumeroArt(model.getArt().getNumero());
		dto.setDataInicioDaEtapa(model.getDataInicioDaEtapa() != null ? DateUtils.format(model.getDataInicioDaEtapa(), DateUtils.DD_MM_YYYY) : "-");
		dto.setDataInicioDaEtapaFormatada(model.getDataInicioDaEtapa() != null ? DateUtils.format(model.getDataInicioDaEtapa(), DateUtils.DD_MM_YYYY) : "-");
		dto.setDataInicioDaEtapaFormatadaInput(model.getDataInicioDaEtapa() != null ? DateUtils.format(model.getDataInicioDaEtapa(), DateUtils.YYYY_MM_DD_COM_TRACOS) : "-");
		dto.setDataConclusao(model.getDataConclusao() != null ? DateUtils.format(model.getDataConclusao(), DateUtils.DD_MM_YYYY) : "-");
		dto.setDataConclusaoFormatada(model.getDataConclusao() != null ? DateUtils.format(model.getDataConclusao(), DateUtils.DD_MM_YYYY) : "-");
		dto.setDataConclusaoFormatadaInput(model.getDataConclusao() != null ? DateUtils.format(model.getDataConclusao(), DateUtils.YYYY_MM_DD_COM_TRACOS) : "-");
		dto.setRelatoVisitaResponsavelTecnico(model.getRelatoVisitaResponsavelTecnico());
		dto.setOrientacao(model.getOrientacao());
		dto.setAcidentesDanosMateriais(model.getAcidentesDanosMateriais());
		dto.setEmpresasePrestadoresContratadosSubContratados(model.getEmpresasePrestadoresContratadosSubContratados());
		dto.setPeriodosInterrupcaoEMotivos(model.getPeriodosInterrupcaoEMotivos());
		dto.setOutrosFatosEObservacoes(model.getOutrosFatosEObservacoes());
		
		if(model.temArquivo()) {
			dto.setArquivo(arquivoConverter.toDto(model.getArquivo()));
		}
		
		if (model.temPessoa()) {
			PessoaDto contratado = pessoaService.getPessoaBy(model.getPessoa().getId());
			dto.setIdPessoa(String.valueOf(model.getPessoa().getId()));
			dto.setCpfCnpjContratado(contratado.getCpfOuCnpj());
			dto.setNomeContratado(contratado.getNome());
		}
		
		return dto;
	}
	
	
	public List<ArtLivroDeOrdemDto> toListDto(List<ArtLivroOrdem> listModel) {

		List<ArtLivroDeOrdemDto> listDto = new ArrayList<ArtLivroDeOrdemDto>();
		
		for(ArtLivroOrdem a : listModel){
			listDto.add(toDto(a));
		}
		
		return listDto;
	}
	
	public ArtLivroOrdem toModel(ArtLivroDeOrdemDto dto) throws Exception {
		try {
			Art art = new Art();
			art.setNumero(dto.getNumeroArt());
			
			ArtLivroOrdem artLivroOrdem = new ArtLivroOrdem();
			
			if(dto.temCodigo()) {
				artLivroOrdem.setId(Long.valueOf(dto.getCodigo()));
			}
			
			artLivroOrdem.setArt(art);
			artLivroOrdem.setDataInicioDaEtapa(dto.getDataInicioDaEtapa() != null ? DateUtils.convertStringToDate(dto.getDataInicioDaEtapa(), DateUtils.YYYY_MM_DD_COM_TRACOS) : null);
			artLivroOrdem.setDataConclusao(dto.getDataConclusao() != null ? DateUtils.convertStringToDate(dto.getDataConclusao(), DateUtils.YYYY_MM_DD_COM_TRACOS) : null);
			artLivroOrdem.setRelatoVisitaResponsavelTecnico(dto.getRelatoVisitaResponsavelTecnico());
			artLivroOrdem.setOrientacao(dto.getOrientacao());
			artLivroOrdem.setAcidentesDanosMateriais(dto.getAcidentesDanosMateriais());
			artLivroOrdem.setEmpresasePrestadoresContratadosSubContratados(dto.getEmpresasePrestadoresContratadosSubContratados());
			artLivroOrdem.setPeriodosInterrupcaoEMotivos(dto.getPeriodosInterrupcaoEMotivos());
			artLivroOrdem.setOutrosFatosEObservacoes(dto.getOutrosFatosEObservacoes());
			
			if(dto.temIdArquivo()) {
				Arquivo arquivo = new Arquivo();
				arquivo.setId(dto.getArquivo().getId());
				artLivroOrdem.setArquivo(arquivo);
			}
			
			if(dto.temPessoa()) {
				Pessoa pessoa = new Pessoa();
				pessoa.setId(Long.valueOf(dto.getIdPessoa()));
				artLivroOrdem.setPessoa(pessoa);
			}
			
			return artLivroOrdem;
			
		} catch (Exception e) {
			throw new Exception();
		}
		
	}

}
