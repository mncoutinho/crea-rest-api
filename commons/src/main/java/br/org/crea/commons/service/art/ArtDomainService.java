package br.org.crea.commons.service.art;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.art.ArtDomainConverter;
import br.org.crea.commons.dao.art.ArtDomainDao;
import br.org.crea.commons.dao.cadastro.pessoa.InteressadoDao;
import br.org.crea.commons.models.art.dtos.NaturezaDto;
import br.org.crea.commons.models.cadastro.EntidadeClasse;
import br.org.crea.commons.models.cadastro.enuns.ModalidadeEnum;
import br.org.crea.commons.models.commons.dtos.DomainGenericDto;
import br.org.crea.commons.models.commons.dtos.GenericDto;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;

public class ArtDomainService {

	@Inject
	ArtDomainConverter converter;
	
	@Inject
	InteressadoDao interessadoDao;

	@Inject
	ArtDomainDao domainDao;

	public List<GenericDto> getAllArtCulturas() {
		return converter.toListArtCulturaDto(domainDao.getAllArtCulturas());
	}

	public List<GenericDto> getAllArtDiagnosticos() {
		return converter.toListArtDiagnosticoDto(domainDao.getAllArtDiagnostico());
	}

	public List<GenericDto> getAllUnidadesMedidaArtReceita() {
		return converter.toListUnidadeMedidaReceitaDto(domainDao.getAllUnidadesMedidaArtReceita());
	}

	public List<GenericDto> getAllRamosAgronomia() {
		return converter.toListRamosDto(domainDao.getAllRamosModalidade(ModalidadeEnum.AGRONOMIA));
	}

	public List<DomainGenericDto> getRamosByIdProfissional(Long idProfissional) {
		return domainDao.getAllRamosByIdProfissional(idProfissional);
	}

	public List<DomainGenericDto> getAllComplementosArt() {
		return converter.toListComplementoArtDto(domainDao.getAllComplementosArt());
	}

	public List<DomainGenericDto> getAllComplementosArtByIdProfissional(Long idProfissional, NaturezaDto natureza) {
		List<DomainGenericDto> listaDeComplementos = domainDao.getAllComplementosArtByIdProfissional(idProfissional);
		
		if (natureza.heDesempenhoCargoFuncao()) {
			
			Iterator<DomainGenericDto> iterador = listaDeComplementos.iterator();
			while (iterador.hasNext()) {
				DomainGenericDto complemento = iterador.next();	
				
				if (!natureza.heCompativelComComplementoParaDesempenhoCargoFuncao(complemento.getId())) {
					iterador.remove();		
				}		
			}

		} else {
			if (natureza.heReceituarioAgronomico()) {
				Iterator<DomainGenericDto> iterador = listaDeComplementos.iterator();
				while (iterador.hasNext()) {
					DomainGenericDto complemento = iterador.next();	
					
					if (!natureza.heCompativelComComplementosParaReceituarioAgronomico(complemento.getId())) {
						iterador.remove();		
					}		
				}
				
			} else {
				Iterator<DomainGenericDto> iterador = listaDeComplementos.iterator();
				while (iterador.hasNext()) {
					DomainGenericDto complemento = iterador.next();	
					
					if (natureza.heCompativelComComplementoParaDesempenhoCargoFuncao(complemento.getId())) {
						iterador.remove();		
					}		
				}
			}
		}

		return listaDeComplementos;
	}

	public Object getAllAtividades() {
		return converter.toListAtividadesDto(domainDao.getAllAtividades());
	}

	public List<DomainGenericDto> getAllAtividadesTecnicasByIdProfissional(Long idProfissional, NaturezaDto natureza) {
		List<DomainGenericDto> listaDeAtividadesTecnica = domainDao.getAllAtividadesTecnicasByIdProfissional(idProfissional);
		
		if (natureza.heDesempenhoCargoFuncao()) {
			
			Iterator<DomainGenericDto> iterador = listaDeAtividadesTecnica.iterator();
			while (iterador.hasNext()) {
				DomainGenericDto atividade = iterador.next();	
				
				if (!natureza.heCompativelComAtividadeTecnicaParaDesempenhoCargoFuncao(atividade.getId())) {
					iterador.remove();		
				}		
			}

		} else {
			if (natureza.heReceituarioAgronomico()) {
				Iterator<DomainGenericDto> iterador = listaDeAtividadesTecnica.iterator();
				while (iterador.hasNext()) {
					DomainGenericDto atividade = iterador.next();	
					
					if (!natureza.heCompativelComAtividadeTecnicaParaReceituarioAgronomico(atividade.getId())) {
						iterador.remove();		
					}		
				}
				
			} else {
				Iterator<DomainGenericDto> iterador = listaDeAtividadesTecnica.iterator();
				while (iterador.hasNext()) {
					DomainGenericDto atividade = iterador.next();	
					
					if (natureza.heCompativelComAtividadeTecnicaParaDesempenhoCargoFuncao(atividade.getId())) {
						iterador.remove();		
					}		
				}
			}
		}

		return listaDeAtividadesTecnica;
	}

	public List<DomainGenericDto> getAllEspecificacaoAtividadeByIdProfissional(Long idProfissional, NaturezaDto natureza) {
		List<DomainGenericDto> listaDeEspecificacaoAtividade = domainDao.getAllEspecificacaoAtividadeByIdProfissional(idProfissional);
		
		if (natureza.heDesempenhoCargoFuncao()) {
			
			return null;

		} else {
			if (natureza.heReceituarioAgronomico()) {
				Iterator<DomainGenericDto> iterador = listaDeEspecificacaoAtividade.iterator();
				while (iterador.hasNext()) {
					DomainGenericDto especificacao = iterador.next();	
					
					if (!natureza.heCompativelComEspecificacaoParaReceituarioAgronomico(especificacao.getId())) {
						iterador.remove();		
					}		
				}
				
			} 
		}

		return listaDeEspecificacaoAtividade;
		
	}

	public List<DomainGenericDto> getAllNatureza(UserFrontDto usuario) {
		List<DomainGenericDto> listaDeNatureza = domainDao.getAllNatureza(usuario.getIdPessoa());	
		return listaDeNatureza;
	}

	public List<DomainGenericDto> getAllFatoGerador() {
		return converter.toListFatoGeradorDto(domainDao.getAllFatoGerador());
	}

	public List<DomainGenericDto> getAllTipo() {
		return converter.toListTipoDto(domainDao.getAllTipo());
	}
	
	public List<DomainGenericDto> getAllParticipacaoTecnica() {
		return converter.toListParticipacaoTecnicaDto(domainDao.getAllParticipacaoTecnica());
	}

	public List<DomainGenericDto> getAllConvenioPublico() {
		return converter.toListConvenioPublicoDto(domainDao.getAllConvenioPublico());
	}

	public List<DomainGenericDto> getAllUnidadeMedida() {
		return converter.toListUnidadeMedidaDto(domainDao.getAllUnidadeMedida());
	}

	public List<DomainGenericDto> getEntidadesClasse() {
		
		List<DomainGenericDto> listaEntidadeClasseDto = new ArrayList<DomainGenericDto>();
		
		for(EntidadeClasse e : domainDao.getEntidadesClasse()) {
			
			DomainGenericDto dto = new DomainGenericDto();
			
			dto.setId(e.getId());
			dto.setSigla(e.getPessoaJuridica().getAbreviatura());
			dto.setNome(interessadoDao.buscaDescricaoRazaoSocial(e.getPessoaJuridica().getId()));
			
			listaEntidadeClasseDto.add(dto);
			
		}
		
		return listaEntidadeClasseDto;
	}
	
	public List<DomainGenericDto> getTiposUnidadesAdministrativasAtivas() {
		return converter.toListTiposUnidadesAdministrativaDto(domainDao.getTiposUnidadesAdministrativaAtivas());
	}

	public List<DomainGenericDto> getTiposAcoesInstitucionaisAtivas() {
		return converter.toListTiposAcoesInstitucionaisDto(domainDao.getTiposAcoesInstitucionaisAtivas());
	}

	public List<DomainGenericDto> getTiposCargosFuncoesAtivas() {
		return converter.toListTiposCargosFuncoesDto(domainDao.getTiposCargosFuncoesAtivas());
	}

	public List<DomainGenericDto> getTiposFuncoesAtivas() {
		return converter.toListTiposFuncoesDto(domainDao.getTiposFuncoesAtivas());
	}

	public List<DomainGenericDto> getTiposVinculosAtivos() {
		return converter.toListTiposVinculosDto(domainDao.getTiposVinculosAtivos());
	}

	public List<DomainGenericDto> getTiposContratantesAtivos() {
		return converter.toListTiposContratantesDto(domainDao.getTiposContratantesAtivos());
	}

	public List<DomainGenericDto> getFinalidadesAtivas() {
		return converter.toListFinalidadesDto(domainDao.getFinalidadesAtivas());
	}

	public List<DomainGenericDto> getTiposBaixa() {
		return converter.toListTiposBaixaDto(domainDao.getTiposBaixa());
	}


	

	
}
