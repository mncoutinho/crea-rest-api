package br.org.crea.siacol.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.cadastro.domains.DepartamentoConverter;
import br.org.crea.commons.dao.cadastro.domains.DepartamentoDao;
import br.org.crea.commons.dao.cadastro.pessoa.InteressadoDao;
import br.org.crea.commons.dao.cadastro.profissional.ProfissionalDao;
import br.org.crea.commons.dao.cadastro.profissional.ProfissionalEspecialidadeDao;
import br.org.crea.commons.dao.siacol.PersonalidadeSiacolDao;
import br.org.crea.commons.dao.siacol.ReuniaoSiacolDao;
import br.org.crea.commons.models.cadastro.dtos.DepartamentoDto;
import br.org.crea.commons.models.commons.dtos.PesquisaGenericDto;
import br.org.crea.commons.models.corporativo.pessoa.IInteressado;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;
import br.org.crea.commons.models.corporativo.pessoa.Profissional;
import br.org.crea.commons.models.siacol.PersonalidadeSiacol;
import br.org.crea.commons.models.siacol.PresencaReuniaoSiacol;
import br.org.crea.commons.models.siacol.ReuniaoSiacol;
import br.org.crea.commons.models.siacol.dtos.AuthPainelSiacolDto;
import br.org.crea.commons.models.siacol.dtos.ParticipanteReuniaoSiacolDto;
import br.org.crea.commons.models.siacol.enuns.VotoReuniaoEnum;
import br.org.crea.siacol.dao.PresencaReuniaoSiacolDao;

public class PersonalidadeSiacolService {
	
	@Inject PersonalidadeSiacolDao dao;
	
	@Inject PresencaReuniaoSiacolDao presencaReuniaoDao;
	
	@Inject InteressadoDao interessadoDao;
	
	@Inject ReuniaoSiacolDao reuniaoDao;
	
	@Inject DepartamentoDao departamentoDao;
	
	@Inject DepartamentoConverter departamentoConverter;
	
	@Inject PresencaReuniaoSiacolDao presencaDao;
	
	@Inject ProfissionalDao profissionalDao;
	
	@Inject ProfissionalEspecialidadeDao profissionalEspecialidadeDao;
	
	public ParticipanteReuniaoSiacolDto autentica(AuthPainelSiacolDto authDto) {

		ParticipanteReuniaoSiacolDto participante = new ParticipanteReuniaoSiacolDto();

		PersonalidadeSiacol personalidade = dao.autentica(authDto.getSenha());
		if (personalidade != null) {
			IInteressado conselheiro = interessadoDao.buscaProfissionalBy(personalidade.getId());

			if (conselheiro != null) {
				participante.setId(conselheiro.getId());
				participante.setNome(conselheiro.getNome());
				participante.setBase64(conselheiro.getFotoBase64());
				participante.setVoto(VotoReuniaoEnum.V);
			}
		} else {
			return null;
		}
		return participante;
	}
	
	public boolean estaAbertaParaVotacao(Long idReuniao) {
		return reuniaoDao.estaAbertaParaVotacao(idReuniao);
	}
	
	public boolean estaPresente(Long idConselheiro, Long idReuniao) {
		return presencaReuniaoDao.estaPresente(idConselheiro, idReuniao);
	}
	
	public ParticipanteReuniaoSiacolDto getConselheiroPor(PesquisaGenericDto dto) {
		return populaParticipanteReuniaoSiacolDto(dao.getConselheiroPor(dto), dto.getIdDocumento(), new Long(11));
	}

	public List<ParticipanteReuniaoSiacolDto> getConselheirosPorIdDepartamento(Long idDepartamento, Long idReuniao) {
		List<Long> listaConselheirosEfetivosESuplentesDoDepartamento = new ArrayList<Long>();
		Long statusLicenca = new Long(0);
		boolean ehComissao = false;
		List<Long> listaIdsEfetivosAtivosDoDepartamento = dao.getConselheirosEfetivosPorIdDepartamento(idDepartamento, statusLicenca, ehComissao);
		List<Long> listaIdsSuplentesDosEfetivosAtivosDoDepartamento = dao.getSuplentesPor(listaIdsEfetivosAtivosDoDepartamento);
		List<Long> listaIdsSuplentesDosLicenciadosDoDepartamento = dao.getSuplentesDosLicenciadosPor(idDepartamento);
		
		listaConselheirosEfetivosESuplentesDoDepartamento.addAll(listaIdsEfetivosAtivosDoDepartamento);
		listaConselheirosEfetivosESuplentesDoDepartamento.addAll(listaIdsSuplentesDosEfetivosAtivosDoDepartamento);
		listaConselheirosEfetivosESuplentesDoDepartamento.addAll(listaIdsSuplentesDosLicenciadosDoDepartamento);
		return populaListaParticipanteReuniaoSiacolDto(listaConselheirosEfetivosESuplentesDoDepartamento, idReuniao, idDepartamento);
	}

	
	public List<ParticipanteReuniaoSiacolDto> getConselheirosPorIdComissoes(Long idComissao, Long idReuniao) {
		Long statusLicenca = new Long(0);
		boolean ehComissao = true;
		List<Long> listaIdsEfetivosAtivosDoDepartamento = dao.getConselheirosEfetivosPorIdDepartamento(idComissao, statusLicenca, ehComissao);
		
		return populaListaParticipanteReuniaoSiacolDto(listaIdsEfetivosAtivosDoDepartamento, idReuniao, idComissao);
	}
	
	public List<ParticipanteReuniaoSiacolDto> getConselheirosComPresencaNaReuniao(Long idReuniao) {
		List<Long> listaIdsComPresencaNaReuniao = presencaDao.getConselheirosEfetivosESuplentesComPresencaNaReuniao(idReuniao);
		
		List<ParticipanteReuniaoSiacolDto> listaComPresenca = populaListaParticipanteComPresencaNaReuniaoSiacolDto(listaIdsComPresencaNaReuniao, idReuniao, null);
		listaComPresenca.sort(Comparator.comparing(ParticipanteReuniaoSiacolDto::getNome));
		return listaComPresenca;
	}
	
	public List<ParticipanteReuniaoSiacolDto> getConselheirosAusentesPorIdDepartamento(Long idDepartamento, Long idReuniao) {
		List<Long> listaIdsAusentes = new ArrayList<Long>();
		Long statusLicenca = new Long(0);
		boolean ehComissao = false;
		List<Long> listaIdsEfetivosAtivosDoDepartamento = dao.getConselheirosEfetivosPorIdDepartamento(idDepartamento, statusLicenca, ehComissao);
		List<Long> listaIdsEfetivosESuplentesDoDepartamentoPresentes = presencaDao.getConselheirosEfetivosESuplentesPresentesNaReuniao(idReuniao);
		List<Long> listaIdsEfetivosComSuplentePresente = dao.getConselheirosEfetivosDoSuplentes(listaIdsEfetivosESuplentesDoDepartamentoPresentes);
		List<Long> listaIdsSuplentesDosLicenciadosDoDepartamento = dao.getSuplentesDosLicenciadosPor(idDepartamento);
		
		listaIdsAusentes.addAll(listaIdsEfetivosAtivosDoDepartamento);
		listaIdsAusentes.addAll(listaIdsSuplentesDosLicenciadosDoDepartamento);
		listaIdsAusentes.removeAll(listaIdsEfetivosESuplentesDoDepartamentoPresentes);
		listaIdsAusentes.removeAll(listaIdsEfetivosComSuplentePresente);
		
		List<ParticipanteReuniaoSiacolDto> listaParticipantesAusentes = populaListaParticipanteReuniaoSiacolDto(listaIdsAusentes, null, null);
		listaParticipantesAusentes.sort(Comparator.comparing(ParticipanteReuniaoSiacolDto::getNome));
		return listaParticipantesAusentes;
	}

	public List<ParticipanteReuniaoSiacolDto> getConselheirosLicenciadosPorIdDepartamento(Long idDepartamento) {
		Long statusLicenca = new Long(1);
		boolean ehComissao = false;
		List<Long> listaIdsEfetivosLicenciadosDoDepartamento = dao.getConselheirosEfetivosPorIdDepartamento(idDepartamento, statusLicenca, ehComissao);

		List<ParticipanteReuniaoSiacolDto> listaLicenciados = populaListaParticipanteReuniaoSiacolDto(listaIdsEfetivosLicenciadosDoDepartamento, null, idDepartamento);
		listaLicenciados.sort(Comparator.comparing(ParticipanteReuniaoSiacolDto::getNome));
		return listaLicenciados;
	}

	public List<ParticipanteReuniaoSiacolDto> populaListaParticipanteReuniaoSiacolDto(List<Long> listaIdConselheiro, Long idReuniao, Long idDepartamento) {

		List<ParticipanteReuniaoSiacolDto> listaParticipanteDto = new ArrayList<ParticipanteReuniaoSiacolDto>();

		for (Long idConselheiro : listaIdConselheiro) {
			listaParticipanteDto.add(populaParticipanteReuniaoSiacolDto(idConselheiro, idReuniao, idDepartamento));
		}

		return listaParticipanteDto;
	}
	
	public List<ParticipanteReuniaoSiacolDto> populaListaParticipanteComPresencaNaReuniaoSiacolDto(List<Long> listaIdConselheiro, Long idReuniao, Long idDepartamento) {

		List<ParticipanteReuniaoSiacolDto> listaParticipanteDto = new ArrayList<ParticipanteReuniaoSiacolDto>();

		for (Long idConselheiro : listaIdConselheiro) {
			listaParticipanteDto.add(populaDtoParticipanteComPresencaNaReuniao(idConselheiro, idReuniao));
		}

		return listaParticipanteDto;
	}
	
	public List<ParticipanteReuniaoSiacolDto> getConselheirosComPresentesSemMesaDiretora(Long idReuniao) {
		List<Long> listaIdsComPresencaNaReuniao = presencaDao.getConselheirosEfetivosESuplentesComPresencaNaReuniao(idReuniao);
		
		List<ParticipanteReuniaoSiacolDto> listaComPresenca = populaListaParticipanteComPresencaNaReuniaoSiacolDto(listaIdsComPresencaNaReuniao, idReuniao, null);
		listaComPresenca.sort(Comparator.comparing(ParticipanteReuniaoSiacolDto::getNome));
		return listaComPresenca;
	}

	private ParticipanteReuniaoSiacolDto populaParticipanteReuniaoSiacolDto(Long idConselheiro, Long idReuniao, Long idDepartamento) {
		ParticipanteReuniaoSiacolDto participante = new ParticipanteReuniaoSiacolDto();
		IInteressado interessado = interessadoDao.buscaInteressadoBy(idConselheiro);

		if (interessado != null) {
			participante.setId(interessado.getId());
			participante.setNome(interessado.getNome());
			participante.setBase64(interessado.getFotoBase64());
		}
		Long idSuplente = dao.buscaIdSuplente(idConselheiro);
		participante.setEhSuplente(dao.ehSuplente(idConselheiro, new Long(99)));
		participante.setIdSuplente(idSuplente);
		
		if (idSuplente != null) {
			IInteressado interessadoSuplente = interessadoDao.buscaInteressadoBy(idSuplente);
			participante.setNomeSuplente(interessadoSuplente.getNome());
		}

		if (idReuniao != null) {
			participante.setStatusPresenca(presencaReuniaoDao.getStatusPresencaParticipante(idReuniao, idConselheiro));
			if (idSuplente != null) {
				participante.setStatusPresencaEfetivoOuSuplente(presencaReuniaoDao.getStatusPresencaParticipante(idReuniao, idSuplente));
			} else {
				Long idConselheiroEfetivo = dao.getConselheiroEfetivoDoSuplente(idConselheiro);
				participante.setStatusPresencaEfetivoOuSuplente(presencaReuniaoDao.getStatusPresencaParticipante(idReuniao,idConselheiroEfetivo));

			}
			
			
			// !ehComissao this.reuniao.departamento.id >= 1301 && this.reuniao.departamento.id <= 1314) || this.reuniao.departamento.id === 2301
			if (participante.getEhSuplente() && !((idDepartamento.longValue() >= new Long(1301) && idDepartamento.longValue() <= new Long(1314)) || idDepartamento.longValue() == new Long(2301))) { // FIXME verificar caso de suplente frequentar reuniao de comissao no lugar de efetivo
				participante.setDescricaoCargo("SUPLENTE");
				participante.setEhSuplenteDoLicenciado(dao.ehSuplente(idConselheiro, new Long(1)));
			} else {
				participante.setEhSuplenteDoLicenciado(false);
				if (idDepartamento.longValue() == new Long(11)) {
					participante.setDescricaoCargo("MEMBRO");
				} else {
					participante.setDescricaoCargo(dao.getDescricaoCargoPorIdDepartamento(idConselheiro, idDepartamento));
				}
			}
			
			participante.setCracha(dao.getCrachaPorId(idConselheiro));
			participante.setNomeGuerra(dao.getNomeDeGuerraPorId(idConselheiro));
			
			
			Profissional profissional = profissionalDao.buscaProfissionalPor(String.valueOf(idConselheiro));
			
			if (profissional.getId() != null) {
				participante.setTitulos(profissionalEspecialidadeDao.getTituloProfissional(profissional));
			}
			
		}
		return participante;
	}
	
	private ParticipanteReuniaoSiacolDto populaDtoParticipanteComPresencaNaReuniao(Long idConselheiro, Long idReuniao) {
		ParticipanteReuniaoSiacolDto participante = new ParticipanteReuniaoSiacolDto();
		IInteressado interessado = interessadoDao.buscaInteressadoBy(idConselheiro);

		if (interessado != null) {
			participante.setId(interessado.getId());
			participante.setNome(interessado.getNome());
			participante.setBase64(interessado.getFotoBase64());
		}
		Long idSuplente = dao.buscaIdSuplente(idConselheiro);
		participante.setEhSuplente(dao.ehSuplente(idConselheiro, new Long(99)));
		participante.setIdSuplente(idSuplente);
		participante.setVoto(VotoReuniaoEnum.X);
		participante.setNomeGuerra(dao.getNomeDeGuerraPorId(idConselheiro));
		participante.setIdReuniao(idReuniao);
		
		return participante;
	}
		
	public List<ParticipanteReuniaoSiacolDto> getDiretoria(Long idReuniao) {
		List<ParticipanteReuniaoSiacolDto> listaParticipantesDiretoria = dao.getDiretoria();
		
		listaParticipantesDiretoria.forEach(participante -> {
			IInteressado interessado = interessadoDao.buscaProfissionalBy(participante.getId());
			participante.setNome(interessado.getNome());
			PresencaReuniaoSiacol presenca = presencaReuniaoDao.getParticipanteComPresencaNaReuniao(participante.getId(), idReuniao);
			if (presenca != null) {
				participante.setPapel(presenca.getPapel());
			} else {
				participante.setPapel("");
			}
			
		});		
	
		return listaParticipantesDiretoria;
	}
	
	public ParticipanteReuniaoSiacolDto getFuncionarioMesaDiretora(Long idReuniao) {
		ParticipanteReuniaoSiacolDto participante = presencaReuniaoDao.getFuncionarioMesaDiretora(idReuniao);
		if (participante != null) {
			IInteressado interessado = interessadoDao.buscaProfissionalBy(participante.getId());
			participante.setNome(interessado.getNome());
		}		
		return participante;
	}
	
	public boolean redefineSenha(ParticipanteReuniaoSiacolDto participante) {
		if (dao.senhaEstaEmUso(participante.getSenha())) {
			return false;			
		} else {
			dao.redefineSenha(participante);
			return true;
		}		
	}

	public void atualizaNomeGuerra(ParticipanteReuniaoSiacolDto participante) {
		dao.atualizaNomeGuerra(participante);		
	}
	
	public void cadastraMesaDiretora(Long idReuniao, List<ParticipanteReuniaoSiacolDto> listaParticipantesMesaDiretora) {
		listaParticipantesMesaDiretora.forEach(participante -> {
			atualizaPresencaMesaDiretora(idReuniao, participante);	
		});
	}

	private PresencaReuniaoSiacol atualizaPresencaMesaDiretora(Long idReuniao, ParticipanteReuniaoSiacolDto participante) {
		
		PresencaReuniaoSiacol presenca = presencaReuniaoDao.getRegistroPresencaMesaDiretora(idReuniao, participante.getPapel());
		
		Pessoa pessoa = new Pessoa();
		pessoa.setId(participante.getId());
		
		if (presenca != null) {
			presenca.setPessoa(pessoa);
			
			return presencaReuniaoDao.update(presenca);
			
		} else {
			presenca = new PresencaReuniaoSiacol();
			presenca.setPessoa(pessoa);
			presenca.setHoraEntregaCracha(new Date());
			
			ReuniaoSiacol reuniao = new ReuniaoSiacol();
			reuniao.setId(idReuniao);
			presenca.setReuniao(reuniao);
			
			presenca.setPapel(participante.getPapel());
			presenca.setTipo("MESA DIRETORA");
			
			if (participante.getPapel().equals("PRESIDENTE DE MESA")) {
				presenca.setVotoMinerva(true);
			}
			
			return presencaReuniaoDao.create(presenca);
		}
	}
	
	public List<DepartamentoDto> getDepartamentoByIdConselheiro(Long idConselheiro) {
		List<Long> listaIdDepartamentos = dao.getDepartamentoByIdConselheiro(idConselheiro);
		List<DepartamentoDto> listaDepartamentos = new ArrayList<DepartamentoDto>();
		
		listaIdDepartamentos.forEach(idDepartamento -> {
			DepartamentoDto departamento = departamentoConverter.toDto(departamentoDao.getBy(idDepartamento));
			listaDepartamentos.add(departamento);
		});
		if (!listaIdDepartamentos.isEmpty()) {
			listaDepartamentos.add(departamentoConverter.toDto(departamentoDao.getBy(new Long(11))));
		}
		
		return listaDepartamentos;
	}

	public ParticipanteReuniaoSiacolDto getMinerva(Long idReuniao) {
		
		Long idMinervaparticipante = presencaDao.getParticipanteComVotoDeMinerva(idReuniao);
		
		return idMinervaparticipante != null ? populaDtoParticipanteComPresencaNaReuniao(idMinervaparticipante, idReuniao) : null;
	}



}
