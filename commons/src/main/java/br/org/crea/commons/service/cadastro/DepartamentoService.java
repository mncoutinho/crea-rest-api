package br.org.crea.commons.service.cadastro;

import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.cadastro.domains.DepartamentoConverter;
import br.org.crea.commons.dao.cadastro.domains.DepartamentoDao;
import br.org.crea.commons.dao.cadastro.profissional.ProfissionalDao;
import br.org.crea.commons.models.cadastro.dtos.DepartamentoDto;
import br.org.crea.commons.models.commons.dtos.GenericDto;

public class DepartamentoService {

	@Inject
	DepartamentoDao dao;
	
	@Inject
	ProfissionalDao profissionalDao;

	@Inject
	DepartamentoConverter converter;
	
	private static final Long ARQUIVO_TECNICO_INDUSTRIAL = 2304050101L;
	
	private static final Long ARQUIVO_TECNICO_INDUSTRIAL_HIBRIDO = 2304050102L;
	
	private static final Long ARQUIVO_TECNICO_AGRICOLA = 2304050103L;
	
	private static final Long ARQUIVO_TECNICO_AGRICOLA_HIBRIDO = 2304050104L;
	
	public List<DepartamentoDto> getAllDepartamentos(String moduloSistema) {
		return converter.toListDto(dao.getAllDepartamentos(moduloSistema));
	}

	public List<DepartamentoDto> getDepartamentoBy(Long id) {
		return converter.toListDto(dao.getDepartamentoBy(id));
	}

	public DepartamentoDto atualizaDepartamento(DepartamentoDto dto) {
		dao.update(converter.toModel(dto));
		return dto;
	}

	public List<DepartamentoDto> pesquisaPorNome(GenericDto dto) {
		return converter.toListDto(dao.pesquisaPorNome(dto));
	}

	/**
	 * Verifica se o profissional técnico possui uma modalidade.
	 * Após encontrar uma modalidade, o sistema verificará se o profissional possui mais de uma modalidade(hibrido).
	 * Estes departamentos são provisórios, para separar os protocolos destes profissionais, que futuramente serão encaminhados 
	 * para seus novos conselhos. 
	 * @param idRegistro
	 * @return
	 */
	public Long retornarDestinoProtocoloInteressadoProfissionalTecnico(Long idRegistro) {
		
		Long idDepartamento = 0L;
		
		Long idModalidade = 0L;
		idModalidade = profissionalDao.buscaCodigoModalidade(idRegistro);
		
		if(idModalidade != null) {
			if(idModalidade != 0){
				idDepartamento = ARQUIVO_TECNICO_INDUSTRIAL;
				
				if(idModalidade.equals(5L)){
					idDepartamento = ARQUIVO_TECNICO_AGRICOLA;
				}
				Long idHibrido = profissionalDao.buscaCodigoModalidadeHibrida(idRegistro);
				
				if(idHibrido != 0){
					if(idModalidade.equals(1L)){
						idDepartamento = ARQUIVO_TECNICO_INDUSTRIAL_HIBRIDO;
					}else{
						idDepartamento = ARQUIVO_TECNICO_AGRICOLA_HIBRIDO;
					}
				}
				
			}
		}
		return idDepartamento;
	}

	public Object getDepartamentosProtocoloSiacol() {
		return converter.toListDto(dao.getDepartamentosProtocoloSiacol());
	}

}
