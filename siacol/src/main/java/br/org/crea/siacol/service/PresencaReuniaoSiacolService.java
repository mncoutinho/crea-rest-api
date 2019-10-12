package br.org.crea.siacol.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.PathParam;

import br.org.crea.commons.dao.cadastro.pessoa.InteressadoDao;
import br.org.crea.commons.dao.siacol.PersonalidadeSiacolDao;
import br.org.crea.commons.factory.siacol.AuditaSiacolPresencaFactory;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.models.corporativo.pessoa.IInteressado;
import br.org.crea.commons.models.siacol.PersonalidadeSiacol;
import br.org.crea.commons.models.siacol.PresencaReuniaoSiacol;
import br.org.crea.commons.models.siacol.ReuniaoSiacol;
import br.org.crea.commons.models.siacol.dtos.ParticipanteReuniaoSiacolDto;
import br.org.crea.commons.models.siacol.dtos.PresencaReuniaoSiacolDto;
import br.org.crea.commons.models.siacol.dtos.ReuniaoSiacolDto;
import br.org.crea.commons.models.siacol.enuns.TipoRelatorioSiacolEnum;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;
import br.org.crea.siacol.converter.PresencaReuniaoSiacolConverter;
import br.org.crea.siacol.dao.PresencaReuniaoSiacolDao;
import br.org.crea.siacol.relatorio.GeraRelatorioSiacol;

public class PresencaReuniaoSiacolService {
	
	@Inject
	HttpClientGoApi httpGoApi;
	
	@Inject 
	private PresencaReuniaoSiacolDao dao;
		
	@Inject 
	private InteressadoDao interessadoDao;
	
	@Inject 
	private PersonalidadeSiacolDao personalidadeDao;
	
	@Inject 
	private PresencaReuniaoSiacolConverter converter;
	
	@Inject 
	private GeraRelatorioSiacol geraRelatorio;
	
	@Inject 
	private AuditaSiacolPresencaFactory auditaPresencaFactory;
	
	public void registraPresenca(ParticipanteReuniaoSiacolDto dto) {
		PresencaReuniaoSiacol participantePresente = dao.getParticipanteComPresencaNaReuniao(dto.getId(), dto.getIdReuniao());

		if (participantePresente == null) {
			registraEntrada(dto.getIdReuniao(), dto.getId(), dto.getDescricaoCargo());
		} else {
			registraSaida(participantePresente, dto.getIdReuniao());
		}
	}

	public void entregaCrachaAutomatica(Long idReuniao, Long idPersonalidade) {
		registraEntrada(idReuniao, idPersonalidade, "MEMBRO");
	}

	public void devolucaoCrachaAutomatica(Long idReuniao, Long idPersonalidade) {
		PresencaReuniaoSiacol participantePresente = dao.getParticipanteComPresencaNaReuniao(idPersonalidade, idReuniao);
		registraSaida(participantePresente, idReuniao);
	}
	
	public void removeRegistroPresenca(Long idReuniao, @PathParam("idParticipante") Long idParticipante) {
		PresencaReuniaoSiacol participantePresente = dao.getParticipanteComPresencaNaReuniao(idParticipante, idReuniao);
		
//		if (dao.atingiuInicioDeQuorum(idReuniao)) {
//			if (rlReuniaoRelatorioDao.existeRelatorio(idReuniao, TipoRelatorioSiacolEnum.RELATORIO_INICIO_DE_QUORUM)) {
//				// XX
//				geraRelatorio.gerarRelatorio(idReuniao, TipoRelatorioSiacolEnum.RELATORIO_INICIO_DE_QUORUM, geraConteudo.tabelaPresentes(relatorioDao.relatorioPresentesNoMomento(idReuniao)));
//			}
//		}	
		
		dao.deleta(participantePresente.getId());
		
		auditaPresencaFactory.auditaAcaoPresenca(idReuniao, participantePresente.getPessoa().getId(), "DELETA", populaMockUsuarioParaAuditoria());
	}
	
	private void registraEntrada(Long idReuniao, Long idParticipante, String cargo) {
		PresencaReuniaoSiacol presenca = populaPresencaReuniaoSiacol(idReuniao, idParticipante);
		presenca.setHoraEntregaCracha(new Date());
		presenca.setPapel(cargo);
		
		dao.create(presenca);
		
		auditaPresencaFactory.auditaAcaoPresenca(idReuniao, idParticipante, "ENTRADA", populaMockUsuarioParaAuditoria());
		
		if (dao.atingiuInicioDeQuorum(idReuniao)) {
			ReuniaoSiacolDto reuniaoDto = new ReuniaoSiacolDto();
			reuniaoDto.setId(idReuniao);
			geraRelatorio.gera(reuniaoDto, TipoRelatorioSiacolEnum.RELATORIO_INICIO_DE_QUORUM);
		}		
	}
	
	private void registraSaida(PresencaReuniaoSiacol presenca, Long idReuniao) {
		presenca.setHoraDevolucaoCracha(new Date());
		presenca.setVotoMinerva(false);
		dao.update(presenca);
		
		auditaPresencaFactory.auditaAcaoPresenca(idReuniao, presenca.getPessoa().getId(), "SAIDA", populaMockUsuarioParaAuditoria());
				
		if (dao.atingiuFimDeQuorum(idReuniao)) {
			ReuniaoSiacolDto reuniaoDto = new ReuniaoSiacolDto();
			reuniaoDto.setId(idReuniao);
			geraRelatorio.gera(reuniaoDto, TipoRelatorioSiacolEnum.RELATORIO_FIM_DE_QUORUM);
		}		
	}
	
	private PresencaReuniaoSiacol populaPresencaReuniaoSiacol(Long idReuniao, Long idParticipante) {
		PresencaReuniaoSiacol presenca = new PresencaReuniaoSiacol();
		
		ReuniaoSiacol reuniao = new ReuniaoSiacol();
		reuniao.setId(idReuniao);
		presenca.setReuniao(reuniao);

		PersonalidadeSiacol personalidade = new PersonalidadeSiacol();
		personalidade.setId(idParticipante);
		presenca.setPessoa(personalidade);
		
		return presenca;
	}

	public List<ParticipanteReuniaoSiacolDto> presentes(Long idReuniao) {
		
		List<PresencaReuniaoSiacol> listPresentes = new ArrayList<PresencaReuniaoSiacol>();
		List<ParticipanteReuniaoSiacolDto> listDto = new ArrayList<ParticipanteReuniaoSiacolDto>();
		
		listPresentes = dao.getPresentes(idReuniao);
		
		listPresentes.forEach(presente -> {
			listDto.add(toParticipanteDto(presente));
		});
		
		listDto.sort(Comparator.comparing(ParticipanteReuniaoSiacolDto::getNomeGuerra));
		
		return listDto;
	}

	private ParticipanteReuniaoSiacolDto toParticipanteDto(PresencaReuniaoSiacol presenca) {
		ParticipanteReuniaoSiacolDto participante = new ParticipanteReuniaoSiacolDto();
		IInteressado interessado = interessadoDao.buscaInteressadoBy(presenca.getPessoa().getId());

		if (interessado != null) {
			participante.setId(interessado.getId());
			participante.setNome(interessado.getNome());
			participante.setBase64(interessado.getFotoBase64());
			participante.setVotoMinerva(presenca.isVotoMinerva());
		}
		participante.setNomeGuerra(personalidadeDao.getNomeDeGuerraPorId(presenca.getPessoa().getId()));
		
		return participante;
	}

	public void atualizaMesaDiretoraCamra(ParticipanteReuniaoSiacolDto dto) {
		dao.zeraVotoMinerva(dto.getIdReuniao());
		dao.atualizaMesaDiretoraCamara(dto.getIdReuniao(), dto.getId());
	}

	public boolean atualizaPresenca(PresencaReuniaoSiacolDto dto) {
		
		try {

			if (dto.ehEntrada()) {
				PresencaReuniaoSiacol presenca = dao.getParticipanteComPresencaNaReuniao(dto.getId(), dto.getReuniao().getId());

				if (presenca == null) {
					registraEntrada(dto.getReuniao().getId(), dto.getId(), dto.getPapel());
				}
				
			} else if (dto.ehSaida()) {
				
				PresencaReuniaoSiacol presenca = dao.getParticipanteComPresencaNaReuniao(dto.getId(), dto.getReuniao().getId());

				if (presenca != null) {
					registraSaida(presenca, dto.getReuniao().getId());
				}
				 
			} else if (dto.ehDeletaPresenca()) {
				PresencaReuniaoSiacol participantePresente = dao.getParticipanteComPresencaNaReuniao(dto.getPessoa().getId(), dto.getReuniao().getId());
				
//				if (dao.atingiuInicioDeQuorum(idReuniao)) {
//					if (rlReuniaoRelatorioDao.existeRelatorio(idReuniao, TipoRelatorioSiacolEnum.RELATORIO_INICIO_DE_QUORUM)) {
//						// XX
//						geraRelatorio.gerarRelatorio(idReuniao, TipoRelatorioSiacolEnum.RELATORIO_INICIO_DE_QUORUM, geraConteudo.tabelaPresentes(relatorioDao.relatorioPresentesNoMomento(idReuniao)));
//					}
//				}	
				
				dao.deleta(participantePresente.getId());
				
				auditaPresencaFactory.auditaAcaoPresenca(dto.getReuniao().getId(), participantePresente.getPessoa().getId(), "DELETA", populaMockUsuarioParaAuditoria());
			}
			
		} catch (Exception e) {
			httpGoApi.geraLog("PresencaReuniaoSiacolService || atualizaPresenca", StringUtil.convertObjectToJson(dto), e);
		}

		return true;
	}

	// FIXME substituir quando token for usado
	private UserFrontDto populaMockUsuarioParaAuditoria() {
		UserFrontDto usuario = new UserFrontDto(); 
		usuario.setIp("127.0.0.1");
		usuario.setIdPessoa(new Long(99990));
		usuario.setNome("Teste Sem Token");
		usuario.setCpfOuCnpj("15279111015");
		return usuario;
	}

}


