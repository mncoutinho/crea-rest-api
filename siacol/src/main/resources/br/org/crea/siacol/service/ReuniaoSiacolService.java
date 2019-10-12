package br.org.crea.siacol.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.cadastro.domains.DocumentoConverter;
import br.org.crea.commons.dao.siacol.ProtocoloSiacolDao;
import br.org.crea.commons.dao.siacol.ReuniaoSiacolDao;
import br.org.crea.commons.dao.siacol.RlDocumentoProtocoloSiacolDao;
import br.org.crea.commons.models.commons.dtos.DocumentoGenericDto;
import br.org.crea.commons.models.commons.dtos.PesquisaGenericDto;
import br.org.crea.commons.models.siacol.ProtocoloSiacol;
import br.org.crea.commons.models.siacol.ReuniaoSiacol;
import br.org.crea.commons.models.siacol.dtos.PesquisaVotoReuniaoDto;
import br.org.crea.commons.models.siacol.dtos.ProtocoloSiacolDto;
import br.org.crea.commons.models.siacol.dtos.ReuniaoSiacolDto;
import br.org.crea.commons.models.siacol.dtos.TesteSiacolGenericDto;
import br.org.crea.commons.models.siacol.enuns.StatusProtocoloSiacol;
import br.org.crea.commons.models.siacol.enuns.StatusReuniaoSiacol;
import br.org.crea.commons.util.DateUtils;
import br.org.crea.siacol.converter.ProtocoloSiacolConverter;
import br.org.crea.siacol.converter.ReuniaoSiacolConverter;

public class ReuniaoSiacolService {
	
	@Inject
	private ReuniaoSiacolConverter converter;
	
	@Inject
	private ProtocoloSiacolConverter protocoloConverter;
	
	@Inject
	private ReuniaoSiacolDao dao;
	
	@Inject
	private DocumentoConverter documentoConverter;
	
	@Inject
	private RlDocumentoProtocoloSiacolDao rlDocumentoProtocoloDao;
	
	@Inject
	private ProtocoloSiacolDao protocoloDao;

	@Inject
	private VotoReuniaoSiacolService votoService;

	public ReuniaoSiacolDto salvar(ReuniaoSiacolDto dto) {
		
		ReuniaoSiacol reuniao = converter.toModel(dto);
		return converter.toDto(dao.create(reuniao));
		
	}
	
	//FIXME pq um delete pauta neste método que atualiza o objeto reunião?

	public ReuniaoSiacolDto atualizar(ReuniaoSiacolDto dto) {
		ReuniaoSiacol reuniao = converter.toModel(dto);
		dao.deletePauta(dto);
		return converter.toDto(dao.update(reuniao));
	}

	public List<ReuniaoSiacolDto> getReunioesSiacol(PesquisaGenericDto dto) {
		return converter.toListDto(dao.getReunioesSiacol(dto));
	}
	
	public List<ProtocoloSiacolDto> getProtocolosAPautarEmReuniao(ReuniaoSiacolDto dto) {
		return protocoloConverter.toListDto(dao.getProtocolosAPautar(dto));
	}
	
	public List<ProtocoloSiacolDto> getProtocolosSemClassificacaoPautaPor(Long idDepartamento) {
		return protocoloConverter.toListDto(dao.getProtocolosSemClassificacaoPautaPor(idDepartamento));
	}
	
	public ProtocoloSiacolDto buscaProtocoloAReclassificarPor(Long numeroProtocolo, Long idDepartamento) {
		
		return protocoloConverter.
				toDto(dao.buscaProtocoloAReclassificarPor(numeroProtocolo, idDepartamento));
	}

	public DocumentoGenericDto getPautaPorIdReuniao(Long idReuniao) {
	
		ReuniaoSiacol reuniao = new ReuniaoSiacol();
		reuniao = dao.getBy(idReuniao);

		return reuniao.temPauta() ? documentoConverter.toDto(reuniao.getPauta()) : null;
		
	}
	
	public List<ProtocoloSiacolDto> getProtocolosPautados(Long idPauta) {
		return protocoloConverter.toListDto(rlDocumentoProtocoloDao.getListProtocolos(idPauta));
	}

	public List<ProtocoloSiacolDto> getProtocolosAAssinar(Long idDepartamento) {
		return protocoloConverter.toListDto(dao.getProtocolosAAssinar(idDepartamento));
	}

	public Object getReuniaoPor(Long idPauta) {
		return converter.toDto(dao.getReuniaoPor(idPauta));
	}
	
	public void finalizaReuniaoVirtual(Date dataVerificacaoFechamento) {
		
		for (ReuniaoSiacol reuniaoSiacol : dao.getReunioesAptasEncerrar(dataVerificacaoFechamento)) {
			
			reuniaoSiacol.setStatus(StatusReuniaoSiacol.FECHADA);
			reuniaoSiacol.setHoraFim(dataVerificacaoFechamento);
			dao.update(reuniaoSiacol);
			atualizaStatusProtocolosDeReuniaoFinalizada(reuniaoSiacol);
		}
		
	}
	
	public void atualizaStatusProtocolosDeReuniaoFinalizada(ReuniaoSiacol reuniaoSiacol) {
		ProtocoloSiacol protocoloVotado = null;
		
		PesquisaVotoReuniaoDto pesquisaResultado = new PesquisaVotoReuniaoDto();
		pesquisaResultado.setIdPauta(reuniaoSiacol.getPauta().getId());
		pesquisaResultado.setIdReuniao(reuniaoSiacol.getId());
		
		for (ProtocoloSiacolDto dto : votoService.contagem(pesquisaResultado)) {
			
			if(dto.foiFavoravelAoInteressado()) {
				
				protocoloVotado = new ProtocoloSiacol();
				protocoloVotado = protocoloDao.getBy(dto.getId());
				protocoloVotado.setStatus(StatusProtocoloSiacol.PARA_ASSINAR);
				protocoloDao.update(protocoloVotado);
				
			}
			
			if(dto.foiDesfavoravelAoInteressado()) {
				
				protocoloVotado = new ProtocoloSiacol();
				protocoloVotado = protocoloDao.getBy(dto.getId());
				protocoloVotado.setStatus(StatusProtocoloSiacol.PROXIMA_REUNIAO_PRESENCIAL);
				protocoloDao.update(protocoloVotado);
				
			}
			
			if(!dto.obteveQuorumMinimo()) {
				
				protocoloVotado = new ProtocoloSiacol();
				protocoloVotado = protocoloDao.getBy(dto.getId());
				protocoloVotado.setStatus(StatusProtocoloSiacol.A_PAUTAR);
				protocoloDao.update(protocoloVotado);
				
			}
		}
		
	}
	
	//FIXME: Excluir após apresentação em 16/02
	public String manipulaReuniao(TesteSiacolGenericDto dto) throws ParseException {
		
		if(dto.getNome().equals("finalizaReuniao")) {
			
			List<ReuniaoSiacol> listReunioes = new ArrayList<ReuniaoSiacol>();
			listReunioes = dao.getReunioesAptasEncerrar(DateUtils.DD_MM_YYYY.parse(dto.getDataManipularReuniao()));
			
			for (ReuniaoSiacol reuniao : listReunioes) {
				finalizaReuniaoVirtual(reuniao.getDataReuniao());
			}
			
		}else {
			dao.manipulaReuniao(dto);
		}
		return "Ação realizada sobre a reuniao para apresentacao: " + dto.getNome(); 
		
	}

	public List<ProtocoloSiacolDto> getProtocolosDeferido() {
		return protocoloConverter.toListDto(dao.getProtocolosDeferido());
	}

}
