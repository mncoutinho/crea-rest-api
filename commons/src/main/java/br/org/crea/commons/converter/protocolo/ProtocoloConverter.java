package br.org.crea.commons.converter.protocolo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.atendimento.UnidadeAtendimentoConverter;
import br.org.crea.commons.dao.cadastro.administrativo.UnidadeAtendimentoDao;
import br.org.crea.commons.dao.cadastro.funcionario.FuncionarioDao;
import br.org.crea.commons.dao.cadastro.pessoa.PessoaDao;
import br.org.crea.commons.dao.corporativo.GeradorSequenciaDao;
import br.org.crea.commons.dao.protocolo.AssuntoDao;
import br.org.crea.commons.dao.protocolo.ProtocoloDao;
import br.org.crea.commons.models.commons.Protocolo;
import br.org.crea.commons.models.commons.dtos.ProtocoloDto;
import br.org.crea.commons.models.corporativo.Assunto;
import br.org.crea.commons.models.corporativo.enuns.TipoPessoa;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;
import br.org.crea.commons.models.protocolo.dtos.TramiteDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.service.PessoaService;
import br.org.crea.commons.service.protocolo.AssuntoService;
import br.org.crea.commons.util.DateUtils;

public class ProtocoloConverter {

	@Inject
	PessoaService servicePessoa;

	@Inject
	AssuntoService serviceAssunto;
	
	@Inject
	UnidadeAtendimentoDao unidadeAtendimentoDao;
	
	@Inject
	UnidadeAtendimentoConverter unidadeAtendimentoConverter;
	
	@Inject
	ProtocoloDao protocoloDao;

	@Inject
	MovimentoConverter movimentoConverter;

	@Inject
	MovimentoProtocoloConverter movimentoProtocoloConverter;

	@Inject
	FuncionarioDao funcionarioDao;
	
	@Inject
	GeradorSequenciaDao geradorSequenciaDao;

	@Inject
	HttpClientGoApi httpGoApi;
	
	@Inject
	AssuntoDao assuntoDao;
	
	@Inject
	PessoaDao pessoaDao;

	public List<ProtocoloDto> toListDto(List<Protocolo> listModel) {

		List<ProtocoloDto> listDto = new ArrayList<ProtocoloDto>();

		for (Protocolo p : listModel) {
			listDto.add(toDto(p));
		}

		return listDto;
	}
	public List<ProtocoloDto> toListMinDto(List<Protocolo> listModel) {

		List<ProtocoloDto> listDto = new ArrayList<ProtocoloDto>();

		for (Protocolo p : listModel) {
			if(p != null) {
				listDto.add(toMinDto(p));
			}
		}

		return listDto;
	}
	public ProtocoloDto toMinDto(Protocolo model) {

		ProtocoloDto dto = new ProtocoloDto();
		
		if( model != null ) {
			
			dto.setNumeroProtocolo(model.getNumeroProtocolo());
			dto.setNumeroProcesso(model.getNumeroProcesso());
			if (model.temAssunto()) {
				dto.setAssunto(serviceAssunto.getAssuntoBy(model.getAssunto().getId()));
			}			
			dto.setDataEmissaoFormatada(model.getDataEmissao() != null ? DateUtils.format(model.getDataEmissao(), DateUtils.DD_MM_YYYY) : "-");
			
			return dto;
			
		} else {
			
			return null;
		}
	}

	public ProtocoloDto toDto(Protocolo model) {

		ProtocoloDto dto = new ProtocoloDto();
		
		if( model != null ) {
			
			dto.setNumeroProtocolo(model.getNumeroProtocolo());
			dto.setNumeroProcesso(model.getNumeroProcesso());
			dto.setNumeroProtocoloPaiAnexo(model.getIdProtocoloPaiAnexo());
			dto.setNumeroProtocoloPaiApenso(model.getIdProtocoloPaiApenso());
			dto.setTipoProtocolo(model.getTipoProtocolo() != null ? model.getTipoProtocolo() : null);
			dto.setAssunto(serviceAssunto.getAssuntoBy(model.getAssunto().getId()));
			dto.setDataEmissao(model.getDataEmissao());
			dto.setDataEmissaoFormatada(model.getDataEmissao() != null ? DateUtils.format(model.getDataEmissao(), DateUtils.DD_MM_YYYY) : "-");
			dto.setInteressado(servicePessoa.getInteressadoBy(model.getPessoa().getId()));
			dto.setObservacao(model.getObservacao());
			dto.setPrimeiroMovimento(movimentoProtocoloConverter.toDto(model.getPrimeiroMovimento()));
			dto.setUltimoMovimento(movimentoProtocoloConverter.toDto(model.getUltimoMovimento()));
			dto.setTipoProtocolo(model.getTipoProtocolo());
			dto.setEstaAnexado(model.protocoloEstaAnexado());
			dto.setEstaApensado(model.protocoloEstaApensado());
			dto.setEstaSubstituido(model.protocoloEstaSubstituido());
			dto.setDigital(model.isDigital());
			dto.setDataDigitalizacao(model.getDataDigitalizacao() != null ? model.getDataDigitalizacao() : null);
			dto.setStatusTransacaoEstaDivergente(model.protocoloEstaComStatusDivergente());
			dto.setStatusTransacao(protocoloDao.getStatusTransacaoProtocolo(model.getIdStatusTransacao()));
			dto.setIdFuncionario(model.getIdFuncionario());
			if (model.getIdUnidadeAtendimento() != null) {
					
				dto.setUnidadeAtendimento(unidadeAtendimentoConverter.toUnidadeAtendimentoDto(unidadeAtendimentoDao.getUnidadeById(model.getIdUnidadeAtendimento())).getNome());
			}
			
			if (model.getPrimeiroMovimento() != null || model.getUltimoMovimento() != null) {
				
				dto.setUltimaMovimentacao(DateUtils.format(model.getUltimoMovimento().getDataEnvio(), DateUtils.DD_MM_YYYY_HH_MM) + " - " + model.getUltimoMovimento().getDepartamentoDestino().getNome()
						+ "(" + (model.getUltimoMovimento().getSituacao() != null ?
								model.getUltimoMovimento().getSituacao().getDescricao() : "") + ")");
				
				if (model.getPrimeiroMovimento().getIdFuncionarioRemetente() != null) {
					
					Long idFuncuionarioRemetente = model.getPrimeiroMovimento().getIdFuncionarioRemetente();
					dto.setEmissao(DateUtils.format(model.getPrimeiroMovimento().getDataEnvio(), DateUtils.DD_MM_YYYY_HH_MM) + ", emitido no departamento "
							+ model.getPrimeiroMovimento().getDepartamentoOrigem().getNome() + " por " + funcionarioDao.getFuncionarioPor(idFuncuionarioRemetente).getPessoaFisica().getNome());
				} else {
					dto.setEmissao(DateUtils.format(model.getPrimeiroMovimento().getDataEnvio(), DateUtils.DD_MM_YYYY_HH_MM) + ", emitido no departamento "
							+ model.getPrimeiroMovimento().getDepartamentoOrigem().getNome());
				}
			}
			
			return dto;
			
		} else {
			
			return null;
		}
	}

	public TramiteDto toTramitacaoProtocoloDto(Protocolo model) {

		TramiteDto dto = new TramiteDto();
		
		dto.setNumeroProtocolo(model.getIdProtocolo());
		dto.setNumeroProcesso(model.getNumeroProcesso());
		dto.setEstaExcluido(model.protocoloEstaExcluido());
		dto.setEstaSubstituido(model.protocoloEstaSubstituido());
		dto.setEstaAnexado(model.protocoloEstaAnexado());
		dto.setEstaApensado(model.protocoloEstaApensado());
		dto.setEstaComStatusDivergente(model.protocoloEstaComStatusDivergente());
		dto.setUltimoMovimento(movimentoProtocoloConverter.toDto(model.getUltimoMovimento()));
		dto.setPossuiErros(model.protocoloEstaComStatusDivergente());
		dto.setTipoProtocolo(model.getTipoProtocolo() != null ? model.getTipoProtocolo() : null);
		dto.setProtocoloEhDigital(model.isDigital());
		dto.setIdPessoa(model.getPessoa().getId());
		dto.setIdSituacaoTramite(model.getIdSituacaoTramite() != null ? model.getIdSituacaoTramite() : null);
		dto.setIdObservacaoTramite(model.getIdObservacaoTramite() != null ? model.getIdObservacaoTramite() : null);
		dto.setListAnexos(toListDto(protocoloDao.getAnexosDoProtocoloPor(model.getNumeroProtocolo())));
		dto.setListApensos(toListDto(protocoloDao.getApensosDoProtocoloPor(model.getNumeroProtocolo())));
		dto.setProtocoloEstaArquivoVirtual(model.getUltimoMovimento().destinoEhArquivoVirtual());
		dto.setAssuntoAptoASerDigitalizado(model.assuntoEhBaixaQuadroTecnico() || model.assuntoEhEntregaCarteira() ? true : false);
		dto.setAssunto(serviceAssunto.getAssuntoBy(model.getAssunto().getId()));
		dto.setPessoa(servicePessoa.getInteressadoBy(model.getPessoa().getId()));
		dto.setInteressado(model.getInteressado() != null ? servicePessoa.getInteressadoBy(Long.parseLong(model.getInteressado())) : null);
		dto.setDataProtocolo(DateUtils.format(model.getDataEmissao(), DateUtils.DD_MM_YYYY));

		dto.setMensagensDoTramite(new ArrayList<String>());
		dto.getMensagensDoTramite().add(dto.isPossuiErros() ? protocoloDao.getStatusTransacaoProtocolo(model.getIdStatusTransacao()) : "");

		
		return dto;

	}

	public List<TramiteDto> toListTramitacaoProtocoloDto(List<Protocolo> listModel) {

		List<TramiteDto> listDto = new ArrayList<TramiteDto>();

		for (Protocolo p : listModel) {
			listDto.add(toTramitacaoProtocoloDto(p));
		}
		return listDto;
	}

	public Protocolo toModel(ProtocoloDto protocoloDto) {
		
		Protocolo protocolo = new Protocolo();
		
		Assunto assunto = new Assunto();
		assunto.setId(protocoloDto.getAssunto() != null ? protocoloDto.getAssunto().getId() : null);
		protocolo.setAssunto(assunto);
		protocolo.setDataEmissao(protocoloDto.getDataEmissao());
		protocolo.setExcluido(protocoloDto.estaExcluido());
		protocolo.setFinalizado(protocoloDto.isFinalizado());
		protocolo.setIdFuncionario(protocoloDto.getIdFuncionario());
		protocolo.setIdStatusTransacao(0L);
		protocolo.setNumeroProcesso(protocoloDto.getNumeroProcesso());
		protocolo.setObservacao(protocoloDto.getObservacao());
		
		if (protocoloDto.getInteressado() != null) {
			protocolo.setInteressado(protocoloDto.getInteressado().getId() != null ? String.valueOf(protocoloDto.getInteressado().getId()) : null);
		}
		
		
		Pessoa pessoa = new Pessoa();
		pessoa.setId(protocoloDto.getPessoa() != null ? protocoloDto.getPessoa().getId() : null);
		
		protocolo.setPessoa(pessoa);
		
		protocolo.setTipoPessoa(protocoloDto.getTipoPessoa() != null ? TipoPessoa.valueOf(protocoloDto.getTipoPessoa()) : null);
		
		return protocolo;
	}

	
	public ProtocoloDto toDtoCadastrar(ProtocoloDto dto, Protocolo protocolo){
		dto.setNumeroProtocolo(protocolo.getNumeroProtocolo());
		dto.setNumeroProcesso(protocolo.getNumeroProcesso());
		dto.setTipoProtocolo(protocolo.getTipoProtocolo());
		dto.setDataEmissaoFormatada(DateUtils.format(protocolo.getDataEmissao(), DateUtils.DD_MM_YYYY));
		
		return dto;
	}
	

}
