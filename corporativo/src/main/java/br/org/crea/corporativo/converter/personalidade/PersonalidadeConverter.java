package br.org.crea.corporativo.converter.personalidade;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.dao.cadastro.leigo.LeigoPFDao;
import br.org.crea.commons.dao.cadastro.profissional.ProfissionalDao;
import br.org.crea.commons.models.corporativo.personalidade.dto.PersonalidadeDto;
import br.org.crea.commons.models.corporativo.personalidade.entity.Personalidade;
import br.org.crea.commons.util.DateUtils;
import br.org.crea.corporativo.dao.personalidade.AjudaCustoDao;



public class PersonalidadeConverter {
	
	@Inject AjudaCustoConverter ajudaCustoConverter;
	@Inject LeigoPFDao leigoDao;
	@Inject ProfissionalDao profissionalDao;
	@Inject AjudaCustoDao ajudaCustoDao;
	
	
	public PersonalidadeDto toDto(Personalidade model){
		
		PersonalidadeDto dto = new PersonalidadeDto();
			
			
			if(model != null){
			
				if(model.getId() != null){
					dto.setId(model.getId());
				}
				
				dto.setApelido(model.getApelido());
				dto.setCracha(model.getCracha());				
				dto.setDataAlteracao(model.getDataAlteracao() != null ? DateUtils.format(model.getDataAlteracao(), DateUtils.DD_MM_YYYY) : "-");
				dto.setDataCadastro(model.getDataCadastro() != null ? DateUtils.format(model.getDataCadastro(), DateUtils.DD_MM_YYYY) : "-");
				dto.setDataNascimentoConjuge(model.getDataNascimentoConjuge());
				dto.setFilhos(model.getFilhos());
				dto.setKmDomicilioCrea(model.getKmDomicilioCrea());
				dto.setMatriculaAlteracao(model.getMatriculaAlteracao());
				dto.setNomeBanco(model.getNomeBanco());
				dto.setNomeConjuge(model.getNomeConjuge());
				dto.setNomeGuerra(model.getNomeGuerra());
				dto.setNumeroAgencia(model.getNumeroAgencia());
				dto.setNumeroConta(model.getNumeroConta());
				dto.setRemovido(model.getRemovido());
				dto.setSenhaPersonalidade(model.getSenhaPersonalidade());
				dto.setTratamento(model.getTratamento());
				dto.setTratamentoTexto(model.getTratamentoTexto());
				dto.setVocativo(model.getVocativo());
				dto.setCodigoBarra(model.getCodigoBarra() );
				
				if ( model.getAjudaCusto() != null ) {
				     dto.setAjudaCusto( model.getAjudaCusto() );
				     dto.setIdAjudaCusto( model.getAjudaCusto().getId() );
			
				}
				
				if ( model.getLeigo() != null ) {
					 dto.setLeigo( model.getLeigo() );
					 dto.setIdLeigo( model.getLeigo().getId() );
					 dto.setNome(model.getLeigo().getPessoaFisica().getNome() );
				}
				
				if ( model.getProfissional() != null ) {
					 dto.setProfissional(model.getProfissional() );
					 dto.setIdProfissional( model.getProfissional().getId() ); 
					 dto.setNome(model.getProfissional().getPessoaFisica().getNome() );
				}

			}
			
			return dto;
		}
		
		
		public List<PersonalidadeDto> toListDto(List<Personalidade> listModel) {
			
			List<PersonalidadeDto> listDto = new ArrayList<PersonalidadeDto>();
			
			for(Personalidade a : listModel){
				listDto.add(toDto(a));
			}
			
			return listDto;
		}
		
		public Personalidade toModel(PersonalidadeDto dto){
			
			Personalidade model = new Personalidade();

			if(dto.getId() != null){
				model.setId(dto.getId());
			}
	
			model.setApelido(dto.getApelido());
			model.setCracha(dto.getCracha());
			model.setDataAlteracao(DateUtils.generateDate(dto.getDataAlteracao()));
			model.setDataCadastro(DateUtils.generateDate(dto.getDataCadastro()));
			model.setDataNascimentoConjuge(dto.getDataNascimentoConjuge());
			model.setFilhos(dto.getFilhos());
			model.setKmDomicilioCrea(dto.getKmDomicilioCrea());
			model.setMatriculaAlteracao(dto.getMatriculaAlteracao());
			model.setNomeBanco(dto.getNomeBanco());
			model.setNomeConjuge(dto.getNomeConjuge());
			model.setNomeGuerra(dto.getNomeGuerra());
			model.setNumeroAgencia(dto.getNumeroAgencia());
			model.setNumeroConta(dto.getNumeroConta());
			model.setRemovido(dto.getRemovido());
			model.setSenhaPersonalidade(dto.getSenhaPersonalidade());
			model.setTratamento(dto.getTratamento());
			model.setTratamentoTexto(dto.getTratamentoTexto());
			model.setVocativo(dto.getVocativo());
			model.setCodigoBarra(dto.getCodigoBarra() );
			
			if ( dto.getAjudaCusto() != null ) {
				 model.setAjudaCusto(dto.getAjudaCusto());
			}else if ( dto.getIdAjudaCusto() != null ) {
				 model.setAjudaCusto( ajudaCustoDao.getBy( dto.getIdAjudaCusto() ));
			}
			
			if ( dto.getIdLeigo() != null ) {
			     model.setLeigo(leigoDao.getBy(dto.getIdLeigo()));
			}
			
			if ( dto.getIdProfissional() != null ) {
				model.setProfissional(profissionalDao.getBy(dto.getIdProfissional()));
			}else {}
			
			model.setRemovido(dto.getRemovido() != null ? dto.getRemovido() : false);
			
            model.setNome(dto.getNome());
			
			return model;
			
		}
		
public List<PersonalidadeDto> toList(List<String> list) {
			
			List<PersonalidadeDto> listDto = new ArrayList<PersonalidadeDto>();
			PersonalidadeDto dto = new PersonalidadeDto();
			
			for(String a : list){
				dto.setNome(a);
				listDto.add(dto);
			}
			
			return listDto;
		}

}
