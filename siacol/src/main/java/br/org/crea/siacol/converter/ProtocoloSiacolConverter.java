package br.org.crea.siacol.converter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.cadastro.domains.DepartamentoConverter;
import br.org.crea.commons.converter.cadastro.domains.EventoConverter;
import br.org.crea.commons.dao.DocumentoDao;
import br.org.crea.commons.dao.cadastro.pessoa.InteressadoDao;
import br.org.crea.commons.dao.siacol.ProtocoloSiacolDao;
import br.org.crea.commons.dao.siacol.SiacolProtocoloExigenciaDao;
import br.org.crea.commons.models.commons.Protocolo;
import br.org.crea.commons.models.commons.dtos.DomainGenericDto;
import br.org.crea.commons.models.commons.dtos.StatusDto;
import br.org.crea.commons.models.corporativo.dtos.AssuntoDto;
import br.org.crea.commons.models.corporativo.dtos.SituacaoDto;
import br.org.crea.commons.models.siacol.AssuntoSiacol;
import br.org.crea.commons.models.siacol.ProtocoloSiacol;
import br.org.crea.commons.models.siacol.dtos.ProtocoloSiacolDto;
import br.org.crea.commons.models.siacol.enuns.ClassificacaoProtocoloPautaEnum;
import br.org.crea.commons.models.siacol.enuns.StatusProtocoloSiacol;
import br.org.crea.commons.util.DateUtils;

public class ProtocoloSiacolConverter {

	@Inject ProtocoloSiacolDao dao;
	
	@Inject DocumentoDao documentoDao;
	
	@Inject SiacolProtocoloExigenciaDao siacolProtocoloExigenciaDao;
	
	@Inject DocumentoSiacolConverter documentoConverter;
	
	@Inject DepartamentoConverter departamentoConverter;
	
	@Inject InteressadoDao interessadoDao;
	
	@Inject EventoConverter eventoConverter;
	
	public List<ProtocoloSiacolDto> toListDto(List<ProtocoloSiacol> ListaProtocoloSiacol) {

		List<ProtocoloSiacolDto> listDto = new ArrayList<ProtocoloSiacolDto>();

		for (ProtocoloSiacol p : ListaProtocoloSiacol) {
			if ( p != null ) {
				listDto.add(toDto(p));
			}
		}
		return listDto;
	}

	public ProtocoloSiacolDto toDto(ProtocoloSiacol model) {
		ProtocoloSiacolDto dto = null;

		if(model != null) {
			
			dto = new ProtocoloSiacolDto();
			
			dto.setId(model.getId());
			dto.setNumeroProcesso(model.getNumeroProcesso());
			dto.setNumeroProtocolo(model.getNumeroProtocolo());
			dto.setIdAssuntoCorportativo(model.getIdAssuntoCorportativo());
			dto.setDescricaoAssuntoCorporativo(model.getDescricaoAssuntoCorporativo());
			
			
			if (model.temAssunto()) {
				dto.setAssunto(populaAssunto(model));
			}
			if (model.temDepartamento()) {
				dto.setDepartamento(departamentoConverter.toDto(model.getDepartamento()));
			}
			if (model.temSituacao()) {
				dto.setSituacao(populaSituacao(model));
			}
			if (model.temStatus()) {
				dto.setStatus(model.getStatus().getDescricao());
				dto.setStatusDto(populaStatus(model));
			}
			if (model.temUltimoStatus()) {
				dto.setUltimoStatus(populaUltimoStatus(model));			
			}
			if (model.temConselheiroRelator()) {
				dto.setIdConselheiroRelator(model.getConselheiroRelator());
				dto.setConselheiroRelator(model.getNomeConselheiroRelator());
			}
			if (model.temConselheiroDevolucao()) {
				dto.setIdConselheiroDevolucao(model.getConselheiroDevolucao());
			}
			if (dao.temRelatorioVoto(model.getNumeroProtocolo())) {
				dto.setListRelatorioVoto(documentoConverter.toListRelatorioVotoDto(documentoDao.getDocumentosPorTipoENumeroProtocolo(new Long(1101), model.getNumeroProtocolo())));
			}
			if (dao.temSugestaoRelatorioVoto(model.getNumeroProtocolo())) {
				dto.setListSugestaoRelatorioVoto(documentoConverter.toListSugestaoRelatorioVotoDto(documentoDao.getDocumentosPorTipoENumeroProtocolo(new Long(1103), model.getNumeroProtocolo())));
			}
			if (dao.temDecisao(model.getNumeroProtocolo())) {
				dto.setListDecisao(documentoConverter.toListDecisaoDto(documentoDao.getDocumentosPorTipoENumeroProtocolo(new Long(1102), model.getNumeroProtocolo())));
			}
			if (dao.temVistas(model.getNumeroProtocolo())) {
				dto.setListVistas(documentoConverter.toListSugestaoRelatorioVotoDto(documentoDao.getDocumentosPorTipoENumeroProtocolo(new Long(1108), model.getNumeroProtocolo())));
			}
			if (dao.temDecisaoPrimeiraInstancia(model.getNumeroProtocolo())) {
				dto.setDecisaoPrimeiraInstancia(documentoConverter.toDecisaoDto(documentoDao.getDocumentosPorTipoENumeroProtocolo(new Long(1115), model.getNumeroProtocolo()).get(0)));
			}
			dto.setDataCadastro(model.getDataCadastro());
			dto.setDataCadastroFormatada(DateUtils.format(model.getDataCadastro(), DateUtils.DD_MM_YYYY));
			dto.setObservacao(model.getObservacao());
			dto.setIdInteressado(model.getIdInteressado());
			dto.setNomeInteressado(model.getNomeInteressado());
			dto.setIdResponsavel(model.getIdResponsavel());
			dto.setNomeResponsavel(model.getNomeResponsavel());
			dto.setAtivo(model.getAtivo());
			dto.setRecebido(model.getRecebido());
			dto.setDataRecebimento(model.getDataRecebimento());
			dto.setDataSiacol(model.getDataSiacol());
			dto.setUltimoAnalista(model.getUltimoAnalista());
			dto.setClassificacao(populaClassificacao(model));
			dto.setClassificacaoFinal(populaClassificacaoFinal(model));
			dto.setJustificativa(model.getJustificativa());
			dto.setMotivoDevolucao(model.getMotivoDevolucao());
			dto.setExigencia(siacolProtocoloExigenciaDao.buscaExigenciaByIdProtocolo(dto.getId()));
			if (model.temProvisorio()) {
				dto.setProvisorio(model.getProvisorio());
			}
			if (model.temUrgenciaVotado()) {
				dto.setUrgenciaVotado(model.getUrgenciaVotado());
			}
			if (model.temAdReferendum()) {
				dto.setAdReferendum(model.getAdReferendum());
			}
			if (model.temHomologacaoPF()) {
				dto.setHomologacaoPF(model.getHomologacaoPF());
			}			
			
			dto.setNumeroProtocoloExigencia(new Long(2018200172));
			
			if (model.temProtocoloPrimeiraInstancia()) {
				dto.setProtocoloSiacolPrimeiraInstancia(toDto(model.getProtocoloPrimeiraInstancia()));
			}
			
			if (model.temEvento()) {
				dto.setEvento(eventoConverter.toDto(model.getEvento()));
			}
			dto.setNumeroDecisao(model.getNumeroDecisao());

		}

		return dto;
	}
	
	private SituacaoDto populaSituacao(ProtocoloSiacol model) {
		SituacaoDto dto = new SituacaoDto();
		dto.setId(model.getSituacao().getId());
		dto.setDescricao(model.getSituacao().getDescricao());
		return dto;
	}
	
	private StatusDto populaStatus(ProtocoloSiacol model) {
		StatusDto dto = new StatusDto();
		dto.setId(model.getStatus().getId());
		dto.setTipo(model.getStatus().getTipo());
		dto.setDescricao(model.getStatus().getDescricao());
		return dto;
	}
	
	private StatusDto populaUltimoStatus(ProtocoloSiacol model) {
		StatusDto dto = new StatusDto();
		dto.setId(model.getUltimoStatus().getId());
		dto.setTipo(model.getUltimoStatus().getTipo());
		dto.setDescricao(model.getUltimoStatus().getDescricao());
		return dto;
	}

	private AssuntoDto populaAssunto(ProtocoloSiacol model) {
		AssuntoDto dto = new AssuntoDto();
		dto.setId(model.getAssuntoSiacol().getId());
		dto.setNome(model.getAssuntoSiacol().getNome());
		dto.setCodigo(model.getAssuntoSiacol().getCodigo());
		dto.setDescricao(model.getAssuntoSiacol().getNome());
		dto.setLegislacao(model.getAssuntoSiacol().getLegislacao());
		return dto;
	}
	
	private DomainGenericDto populaClassificacao(ProtocoloSiacol model) {
		DomainGenericDto dto = new DomainGenericDto();
		if (model.getClassificacao() != null) {
			dto.setId(model.getClassificacao().getId());
			dto.setNome(model.getClassificacao().getDescricao());
		} else {
			dto.setId(new Long(0));
			dto.setNome("Não classficado");
		}
		return dto;
	}
	
	private DomainGenericDto populaClassificacaoFinal(ProtocoloSiacol model) {
		DomainGenericDto dto = new DomainGenericDto();
		if (model.getClassificacaoFinal() != null) {
			dto.setId(model.getClassificacaoFinal().getId());
			dto.setNome(model.getClassificacaoFinal().getDescricao());
		} else {
			dto.setId(new Long(0));
			dto.setNome("Não classficado");
		}
		return dto;
	}

	public ProtocoloSiacol toModelCadastrar(ProtocoloSiacolDto dto, Protocolo protocoloDoSistema) {
		ProtocoloSiacol protocoloSiacol = new ProtocoloSiacol();
		
		String nomeInteressado = interessadoDao.buscaInteressadoBy(protocoloDoSistema.getPessoa()).getNomeRazaoSocial();

		protocoloSiacol.setNumeroProtocolo(protocoloDoSistema.getNumeroProtocolo());
		protocoloSiacol.setNumeroProcesso(protocoloDoSistema.getNumeroProcesso());
		protocoloSiacol.setDataCadastro(protocoloDoSistema.getDataEmissao());
		protocoloSiacol.setDataRecebimento(null);
		protocoloSiacol.setIdInteressado(protocoloDoSistema.getPessoa().getId());
		protocoloSiacol.setNomeInteressado(nomeInteressado);
		protocoloSiacol.setIdAssuntoCorportativo(protocoloDoSistema.getAssunto().getId());
		protocoloSiacol.setDescricaoAssuntoCorporativo(protocoloDoSistema.getAssunto().getDescricao());
		protocoloSiacol.setDescricaoTipoAssuntoCorporativo(protocoloDoSistema.getAssunto().getTipoAssunto().name());
		protocoloSiacol.setSituacao(protocoloDoSistema.getUltimoMovimento().getSituacao());
		protocoloSiacol.setDepartamento(protocoloDoSistema.getUltimoMovimento().getDepartamentoDestino());
		protocoloSiacol.setObservacao(protocoloDoSistema.getObservacao());
		protocoloSiacol.setDataSiacol(new Date());
		protocoloSiacol.setAtivo(true);
		protocoloSiacol.setProvisorio(false);
		protocoloSiacol.setHomologacaoPF(false);
		protocoloSiacol.setUrgenciaVotado(false);
		
		
		if (dto.getAssunto() != null) {
			AssuntoSiacol assuntoSiacol = new AssuntoSiacol();
			assuntoSiacol.setId(dto.getAssunto().getId());
			protocoloSiacol.setAssuntoSiacol(assuntoSiacol);;
		}
		if (!protocoloSiacol.temRecebido()) {
			protocoloSiacol.setRecebido(false);
			protocoloSiacol.setDataRecebimento(null);
		}
		if (dto.getStatus() != null){
			protocoloSiacol.setStatus(StatusProtocoloSiacol.getStatusNomeBy(dto.getStatus()));
		}else {
			protocoloSiacol.setStatus(StatusProtocoloSiacol.AGUARDANDO_RECEBIMENTO);
		}
		
		if (dto.getClassificacao() != null && dto.getClassificacaoFinal() == null ) {
			protocoloSiacol.setClassificacaoFinal(converterClassificacaoEntity(dto.getClassificacaoFinalDescritivo()));
		}else if ((dto.getClassificacaoFinal() != null || dto.getClassificacaoFinalDescritivo() != null) && dto.getClassificacao() == null) {
			protocoloSiacol.setClassificacao(converterClassificacaoEntity(dto.getClassificacaoFinalDescritivo()));
			protocoloSiacol.setClassificacaoFinal(converterClassificacaoEntity(dto.getClassificacaoFinalDescritivo()));
		}
		
		if (protocoloSiacol.temHomologacaoPF()) {
			protocoloSiacol.setHomologacaoPF(dto.isHomologacaoPF());
		}
		
		protocoloSiacol.setConselheiroDevolucao(null);
		protocoloSiacol.setUltimoAnalista(null);
		protocoloSiacol.setNumeroDecisao(dto.getNumeroDecisao());
		
		return protocoloSiacol;
	}

	private ClassificacaoProtocoloPautaEnum converterClassificacaoEntity(String descricao) {
		
		for(ClassificacaoProtocoloPautaEnum s : ClassificacaoProtocoloPautaEnum.values()){
			if(s.name().equals(descricao)){
				return s;
			}
		}
		
		return null;
	}
	
}
