package br.org.crea.siacol.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.dao.cadastro.pessoa.InteressadoDao;
import br.org.crea.commons.dao.siacol.PersonalidadeSiacolDao;
import br.org.crea.commons.dao.siacol.RlDocumentoProtocoloSiacolDao;
import br.org.crea.commons.factory.siacol.AuditaSiacolReuniaoFactory;
import br.org.crea.commons.models.cadastro.enuns.TipoEventoAuditoria;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.models.corporativo.pessoa.IInteressado;
import br.org.crea.commons.models.siacol.RlDeclaracaoVoto;
import br.org.crea.commons.models.siacol.RlDocumentoProtocoloSiacol;
import br.org.crea.commons.models.siacol.dtos.ItemPautaDto;
import br.org.crea.commons.models.siacol.dtos.ParticipanteReuniaoSiacolDto;
import br.org.crea.commons.models.siacol.dtos.RlDeclaracaoVotoDto;
import br.org.crea.siacol.converter.PautaReuniaoSiacolConverter;
import br.org.crea.siacol.converter.RlDeclaracaoVotoConverter;
import br.org.crea.siacol.dao.RlDeclaracaoVotoDao;

public class RlDeclaracaoVotoService {

	@Inject
	private RlDeclaracaoVotoDao rlDeclaracaoVotoDao;
	@Inject
	private RlDeclaracaoVotoConverter rlDeclaracaoVotoConverter;
	@Inject
	private RlDocumentoProtocoloSiacolDao documentoProtocoloDao;
	@Inject 
	private InteressadoDao interessadoDao;
	@Inject
	private PersonalidadeSiacolDao personalidadeDao;
	@Inject
	private PautaReuniaoSiacolConverter itemConverter;
	
	@Inject
	private AuditaSiacolReuniaoFactory auditaReuniaoFactory;

	public List<RlDeclaracaoVotoDto> getDeclaracaoByIdItem(Long idItem) {
		return rlDeclaracaoVotoConverter.toListDtoComPessoa(rlDeclaracaoVotoDao.getDeclaracaoByIdItem(idItem));
	}

	public boolean validaDeclaracaoVoto(Long idItem) {
		return rlDeclaracaoVotoDao.validaDeclaracaoVoto(idItem);
	}

	public RlDeclaracaoVotoDto atualizarDeclaracaoVoto(RlDeclaracaoVotoDto dto) {
		return rlDeclaracaoVotoConverter.toDto(rlDeclaracaoVotoDao.update(rlDeclaracaoVotoConverter.toModel(dto)));
	}

	public RlDeclaracaoVoto salvarDeclaracaoVoto(Long idItem, Long idConselheiro, UserFrontDto userDto) {
		
		RlDocumentoProtocoloSiacol item = new RlDocumentoProtocoloSiacol();
		item = documentoProtocoloDao.getBy(idItem);
		item.setTemDeclaracaoVoto(true);

		try {	
				RlDeclaracaoVoto declaracaoVoto = new RlDeclaracaoVoto();
				declaracaoVoto.setConselheiro(idConselheiro);
				declaracaoVoto.setItem(item);
				declaracaoVoto.setAnalistaUpload(userDto.getIdPessoa());

				rlDeclaracaoVotoDao.create(declaracaoVoto);
				
				documentoProtocoloDao.update(item);
				
				ItemPautaDto itemDto = itemConverter.toItemPautaDto(item);
				itemDto.setEventoAuditoria(TipoEventoAuditoria.DECLARACAO_VOTO);
				userDto.setIdPessoa(idConselheiro);
				auditaReuniaoFactory.auditaItemReuniao(itemDto, userDto);
				
				return declaracaoVoto;
	
		} catch (Throwable e) {
			return null;
		}
	}

	public Object deletarDeclaracaoVoto(Long idItem, Long idConselheiro, UserFrontDto userDto) {
		
		RlDeclaracaoVoto declaracaoVoto = new RlDeclaracaoVoto();
		
		declaracaoVoto = rlDeclaracaoVotoDao.getByItemConselheiro(idItem, idConselheiro);
		
		if (declaracaoVoto != null) {
			rlDeclaracaoVotoDao.deleta(declaracaoVoto.getId());
			
			if (rlDeclaracaoVotoDao.getDeclaracaoByIdItem(idItem) == null) {
				RlDocumentoProtocoloSiacol documentoProtocolo = documentoProtocoloDao.getBy(idItem);
				documentoProtocolo.setTemDeclaracaoVoto(false);
				documentoProtocoloDao.update(documentoProtocolo);
			}
			
		}
		
		return null;
	}

	public List<ParticipanteReuniaoSiacolDto> participantes(Long idItem) {
		
		List<ParticipanteReuniaoSiacolDto> listaParticipantes = new ArrayList<ParticipanteReuniaoSiacolDto>();
		List<RlDeclaracaoVoto> listaDeclaracao = rlDeclaracaoVotoDao.getDeclaracaoByIdItem(idItem);
		
		for (RlDeclaracaoVoto rlDeclaracaoVoto : listaDeclaracao) {
			listaParticipantes.add(populaDtoParticipante(rlDeclaracaoVoto.getConselheiro()));
		}
		
		return listaParticipantes;
	}
	
	private ParticipanteReuniaoSiacolDto populaDtoParticipante(Long idConselheiro) {
		ParticipanteReuniaoSiacolDto participante = new ParticipanteReuniaoSiacolDto();
		IInteressado interessado = interessadoDao.buscaInteressadoBy(idConselheiro);

		if (interessado != null) {
			participante.setId(interessado.getId());
			participante.setNome(interessado.getNome());
			participante.setBase64(interessado.getFotoBase64());
		}
	
		participante.setNomeGuerra(personalidadeDao.getNomeDeGuerraPorId(idConselheiro));
		return participante;
	}

}
