package br.org.crea.siacol.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.cadastro.domains.EventoConverter;
import br.org.crea.commons.converter.cadastro.empresa.RequerimentoPJConverter;
import br.org.crea.commons.converter.cadastro.pessoa.PessoaConverter;
import br.org.crea.commons.converter.protocolo.ProtocoloConverter;
import br.org.crea.commons.dao.DocumentoDao;
import br.org.crea.commons.dao.cadastro.domains.DepartamentoDao;
import br.org.crea.commons.dao.cadastro.empresa.RequerimentoPJDao;
import br.org.crea.commons.dao.cadastro.pessoa.InteressadoDao;
import br.org.crea.commons.dao.protocolo.ObservacaoMovimentoDao;
import br.org.crea.commons.dao.protocolo.ProtocoloDao;
import br.org.crea.commons.dao.siacol.AssuntoSiacolDao;
import br.org.crea.commons.dao.siacol.HabilidadePessoaDao;
import br.org.crea.commons.dao.siacol.ProtocoloSiacolDao;
import br.org.crea.commons.dao.siacol.RlProtocoloResponsavelSiacolDao;
import br.org.crea.commons.dao.siacol.SiacolProtocoloExigenciaDao;
import br.org.crea.commons.factory.siacol.AuditaSiacolProtocoloFactory;
import br.org.crea.commons.models.cadastro.Departamento;
import br.org.crea.commons.models.cadastro.Documento;
import br.org.crea.commons.models.cadastro.dtos.empresa.RequerimentoPJDto;
import br.org.crea.commons.models.commons.ObservacoesMovimento;
import br.org.crea.commons.models.commons.Protocolo;
import br.org.crea.commons.models.commons.SituacaoProtocolo;
import br.org.crea.commons.models.commons.dtos.DomainGenericDto;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.models.protocolo.dtos.JuntadaProtocoloDto;
import br.org.crea.commons.models.siacol.AssuntoSiacol;
import br.org.crea.commons.models.siacol.ProtocoloSiacol;
import br.org.crea.commons.models.siacol.SiacolProtocoloExigencia;
import br.org.crea.commons.models.siacol.dtos.ConsultaProtocoloDto;
import br.org.crea.commons.models.siacol.dtos.GenericSiacolDto;
import br.org.crea.commons.models.siacol.dtos.ProtocoloSiacolDto;
import br.org.crea.commons.models.siacol.dtos.ProtocoloSiacolEmpresaDto;
import br.org.crea.commons.models.siacol.dtos.SiacolProtocoloExigenciaDto;
import br.org.crea.commons.models.siacol.dtos.VinculoProtocoloDto;
import br.org.crea.commons.models.siacol.enuns.StatusProtocoloSiacol;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.service.commons.ArquivoService;
import br.org.crea.commons.util.DateUtils;
import br.org.crea.commons.util.ExcelPoiUtil;
import br.org.crea.commons.util.StringUtil;
import br.org.crea.siacol.converter.ProtocoloResponsavelSiacolConverter;
import br.org.crea.siacol.converter.ProtocoloSiacolConverter;
import br.org.crea.siacol.converter.SiacolProtocoloExigenciaConverter;

public class ProtocoloSiacolService {

	@Inject ProtocoloSiacolConverter converter;
	@Inject PessoaConverter converterPessoa;
	@Inject ProtocoloResponsavelSiacolConverter protocoloResponsavelConverter;
	@Inject SiacolProtocoloExigenciaConverter siacolProtocoloExigenciaConverter;
	@Inject ProtocoloSiacolDao dao;
	@Inject DocumentoDao documentoDao;
	@Inject RlProtocoloResponsavelSiacolDao rlProtocoloResponsavelDao;
	@Inject InteressadoDao interessadoDao;
	@Inject DepartamentoDao departamentoDao;
	@Inject AssuntoSiacolDao assuntoSiacolDao;
	@Inject SiacolProtocoloExigenciaDao siacolProtocoloExigenciaDao;
	@Inject HabilidadePessoaDao habilidadeDao;
	@Inject ObservacaoMovimentoDao observacaoMovimentoDao;
	@Inject EventoConverter eventoConverter;
	
	@Inject HttpClientGoApi httpGoApi;
	@Inject RequerimentoPJDao requerimentoDao;
	@Inject RequerimentoPJConverter requerimentoConverter;
	@Inject ProtocoloDao protocoloDao;
	@Inject ProtocoloConverter protocoloConverter;
	@Inject ArquivoService arquivoService;
	@Inject AuditaSiacolProtocoloFactory audita;

	public List<ProtocoloSiacolDto> getAllProtocolos(ConsultaProtocoloDto consulta) {
		return converter.toListDto(dao.getAllProtocolos(consulta));
	}

	public int getQuantidadeConsultaProtocolo(ConsultaProtocoloDto consulta) {
		return dao.quantidadeConsultaProtocolos(consulta);
	}

	public boolean temHabilidadeParaRedistribuicao(GenericSiacolDto dto) {
		return dao.temHabilidadeParaRedistribuicao(dto);
	}

	public GenericSiacolDto distribuiProtocolo(GenericSiacolDto dto, UserFrontDto usuario) {

		try {

			for (Long idProtocolo : dto.getListaId()) {
				dto.setIdProtocolo(idProtocolo);
				
				if (!dto.temResponsavelNovo()) {
					distribuiParaCoordenadorDoDepartamentoOuCoac(dto);
				}
				
				assinaDespachosDeDistribuicao(idProtocolo, usuario);
				
				if (dto.getSiacol() != null) {
					distribuirParaCoordenadorCoac(dto);
				}

				ProtocoloSiacol protocolo = new ProtocoloSiacol();
				
				protocolo = dao.getBy(idProtocolo);
				
				String perfilUsuario = usuario.temPerfil() ? usuario.getPerfilString() : "";
						
	        	if ( perfilUsuario.equals("siacolaconselheiro") || perfilUsuario.equals("siacolcoordenadorcamara") ) {
	    			protocolo.setConselheiroRelator(dto.getIdResponsavelAtual());
	    			protocolo.setNomeConselheiroRelator(dto.getNomeResponsavelAtual());
	        	} else if(perfilUsuario.equals("siacolanalista") && ehCoordenadorCoac(usuario.getIdPessoa())){
			    	protocolo.setUltimoAnalista(dto.getIdResponsavelAtual());
			    }
			        
				protocolo.setDataSiacol(new Date());
				protocolo.setRecebido(false);
				protocolo.setDataRecebimento(null);
				
				dao.update(protocolo);
				
				auditoriaDaDistribuicao(dto, protocolo, usuario);

				// alteração de status
				protocolo = populaProtocolo(dto, usuario);
				dao.update(protocolo);
			}
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ProtocoloSiacolService || distribuiProtocolo", StringUtil.convertObjectToJson(dto), e);
		}

		return dto;
	}
	
	private void distribuiParaCoordenadorDoDepartamentoOuCoac(GenericSiacolDto dto) {
		Departamento departamento = new Departamento();
		departamento = departamentoDao.getBy(new Long(dto.getIdDepartamento()));
		
		if (departamento.temCoordenador()) {
			dto.setIdResponsavelNovo(departamento.getCoordenador().getId());
			dto.setNomeResponsavelNovo(interessadoDao.buscaInteressadoBy(dto.getIdResponsavelNovo()).getNome());
		} else {
			distribuirParaCoordenadorCoac(dto);
		}
	}
	
	private void distribuirParaCoordenadorCoac(GenericSiacolDto dto) {
		dto.setIdResponsavelNovo(departamentoDao.getBy(new Long(230201)).getCoordenador().getId());
		dto.setNomeResponsavelNovo(interessadoDao.buscaInteressadoBy(dto.getIdResponsavelNovo()).getNome());
	}
	
	private void assinaDespachosDeDistribuicao(Long idProtocolo, UserFrontDto usuario) {
		List<Documento> listaDocumento = new ArrayList<Documento>();
		listaDocumento = documentoDao.recuperaDocumentosByNumeroProtocolo(idProtocolo);
		
		if(!listaDocumento.isEmpty()){
			for (Documento documento : listaDocumento) {
				if (documento.getResponsavel() == usuario.getIdPessoa() || documento.getResponsavel() == usuario.getIdFuncionario()) {
					documento.setAssinado(true);
					documentoDao.update(documento);
				}
			}
		}
	}
	
	private void auditoriaDaDistribuicao(GenericSiacolDto dto, ProtocoloSiacol protocoloDistribuido, UserFrontDto usuario) {
		boolean ehProtocoloPaiDeUmAnexo = dao.temAnexoNoSiacol(protocoloDistribuido.getNumeroProtocolo());
		boolean ehProtocoloFilhoAnexadoAUmPai = dao.estaAnexadoAProtocoloNoSiacol(protocoloDistribuido.getNumeroProtocolo());
		
		if (!ehProtocoloPaiDeUmAnexo && !ehProtocoloFilhoAnexadoAUmPai) {
			audita.auditaDistribuicao(dto, usuario, protocoloDistribuido, null);
		} else {
			Long referenciaPai = ehProtocoloPaiDeUmAnexo ? protocoloDistribuido.getNumeroProtocolo() : dao.getNumeroProtocoloPaiNoSiacol(protocoloDistribuido.getNumeroProtocolo());
			
			List<ProtocoloSiacol> listaProtocolosFilhosEPai = dao.getListProtocolosAnexosNoSiacol(referenciaPai);
			listaProtocolosFilhosEPai.add(dao.getProtocoloBy(referenciaPai));
			
			for (ProtocoloSiacol protocolo : listaProtocolosFilhosEPai) {
				
				if (protocolo.getId().equals(protocoloDistribuido.getId())) {
					audita.auditaDistribuicao(dto, usuario, protocolo, referenciaPai);
				} else {
					// ATRIBUIR O MESMO RESPONSAVEL DO PROTOCOLO DISTRIBUIDO AO PROTOCOLO OCULTO
					protocolo.setIdResponsavel(dto.getIdResponsavelNovo());
					protocolo.setNomeResponsavel(dto.getNomeResponsavelNovo());
					protocolo.setUltimoAnalista(dto.getIdResponsavelNovo());
					dao.update(protocolo);
					audita.auditaDistribuicaoAnexo(dto, usuario, protocolo, referenciaPai);
				}
			}
		}
	}
	
	public DomainGenericDto buscaResponsavelAleatorioParaDistribuicao(Long idProtocolo, StatusProtocoloSiacol statusProtocoloSiacol, UserFrontDto userFrontDto) {
		ProtocoloSiacol protocoloSiacol = dao.getBy(idProtocolo);
		
		GenericSiacolDto genericDto = new GenericSiacolDto();
		genericDto.setIdDepartamento(protocoloSiacol.getDepartamento().getId());
		genericDto.setIdAssunto(protocoloSiacol.getIdAssuntoCorportativo());
		genericDto.setDistribuicaoParaConselheiro(statusProtocoloSiacol == StatusProtocoloSiacol.ANALISE ? false : true);

		DomainGenericDto responsavelDto = new DomainGenericDto();
		responsavelDto.setId(habilidadeDao.getResponsavelDistribuicao(genericDto, userFrontDto).getId());

		String nomeResponsavel = interessadoDao.buscaInteressadoBy(responsavelDto.getId()).getNome();
		responsavelDto.setNome(nomeResponsavel);

		GenericSiacolDto bloqueioResponsavelDto = new GenericSiacolDto();
		bloqueioResponsavelDto.setIdResponsavelAtual(responsavelDto.getId());
		bloqueioResponsavelDto.setIdDepartamento(protocoloSiacol.getDepartamento().getId());
		bloqueioResponsavelDto.setIdAssunto(protocoloSiacol.getIdAssuntoCorportativo());
		bloqueioResponsavelDto.setDistribuicaoParaConselheiro(false);
		habilidadeDao.bloquearResponsavelParaDistribuicao(bloqueioResponsavelDto);

		return responsavelDto;
	}
	

	public ProtocoloSiacolDto buscaProtocolo(Long idProtocolo) {
		return converter.toDto(dao.getBy(idProtocolo));
	}
	
	public ProtocoloSiacolDto getProtocoloBy(Long numeroProtocolo) {
		return converter.toDto(dao.getProtocoloBy(numeroProtocolo));
	}
	
	public List<ProtocoloSiacolDto> buscaProtocoloOficio(String status, Long idDepartamento) {
		return converter.toListDto(dao.buscaProtocoloOficio(status, idDepartamento));
	}

	public GenericSiacolDto classificar(GenericSiacolDto dto, UserFrontDto usuario) {

		ProtocoloSiacol protocolo = new ProtocoloSiacol();
		protocolo = populaProtocolo(dto, usuario);
		dao.update(protocolo);
		audita.auditaClassificar(usuario, protocolo);

		return dto;
	}

	public GenericSiacolDto justificativaDevolucao(GenericSiacolDto dto, UserFrontDto usuario) {
		ProtocoloSiacol protocolo = new ProtocoloSiacol();
		protocolo = populaProtocolo(dto, usuario);
		protocolo.setRecebido(false);
		protocolo.setDataRecebimento(null);
		dao.update(protocolo);
		audita.justificativaDevolucao(usuario, protocolo);
		return dto;
	}

	public GenericSiacolDto alteraSituacoes(GenericSiacolDto dto, UserFrontDto usuario) {

		try {

			for (Long idProtocolo : dto.getListaId()) {
				alteraSituacao(dto, idProtocolo, usuario);
			}

		} catch (Exception e) {
			httpGoApi.geraLog("ProtocoloSiacolService || alteraSituacao", StringUtil.convertObjectToJson(dto), e);
		}

		return dto;
	}
	
	public void alteraSituacao(GenericSiacolDto dto, Long idProtocolo, UserFrontDto usuario) {
		dto.setIdProtocolo(idProtocolo);
		ProtocoloSiacol protocolo = new ProtocoloSiacol();
		protocolo = populaProtocolo(dto, usuario);
		dao.update(protocolo);
	}

	private ProtocoloSiacol populaProtocolo(GenericSiacolDto dto, UserFrontDto usuario) {

		ProtocoloSiacol protocolo = new ProtocoloSiacol();

		protocolo = dao.getBy(dto.getIdProtocolo());
		protocolo.setJustificativa(null);
		protocolo.setMotivoDevolucao(null);

		if (dto.temResponsavelNovo()) {
			if (dto.getStatus().equals(StatusProtocoloSiacol.ASSINATURA_AD_REFERENDUM) || dto.getStatus().equals(StatusProtocoloSiacol.ASSINAR_OFICIO)) {
				protocolo.setUltimoAnalista(dto.getIdResponsavelAtual());
			}	
			protocolo.setIdResponsavel(dto.getIdResponsavelNovo());
			protocolo.setNomeResponsavel(dto.getNomeResponsavelNovo());
		}

		if (dto.temAssuntoSiacolProtocolo() && dto.getIdAssuntoSiacolProtocolo() != 0) {
			AssuntoSiacol assunto = new AssuntoSiacol();
			assunto.setId(dto.getIdAssuntoSiacolProtocolo());
			protocolo.setAssuntoSiacol(assunto);
		}

		if (dto.temSituacao()) {
			SituacaoProtocolo situacao = new SituacaoProtocolo();
			situacao.setId(dto.getIdSituacao());
			protocolo.setSituacao(situacao);
		}

		if (dto.temJustificativa()) {
			protocolo.setJustificativa(dto.getJustificativa());
		}

		if (dto.temMotivoDevolucao()) {
			protocolo.setMotivoDevolucao(dto.getMotivoDevolucao());
		}

		if (dto.temStatus()) {
			if (dto.temIdResponsavelAtual()) { // verifica se é uma mudança de status oriunda de uma distribuição
				
				// evita atualização de status na distribuição de intervenção feita pelo coordenador coac
				if (usuario.getIdPessoa().equals(dto.getIdResponsavelAtual())) {
					if (dto.temResponsavelNovo()) {
						
						UserFrontDto usuarioDestino = new UserFrontDto();
						usuarioDestino.setIdPessoa(dto.getIdResponsavelNovo());
						usuarioDestino.setNome(dto.getNomeResponsavelNovo());
						
						atualizaStatus(protocolo, dto.getStatus(), usuario, usuarioDestino);
					} else {
						atualizaStatus(protocolo, dto.getStatus(), usuario, usuario);
					}
				}
			} else {
				atualizaStatus(protocolo, dto.getStatus(), usuario, usuario);
			}	
		}

		if (dto.temClassificacao()) {
			protocolo.setClassificacao(dto.getClassificacao());
		}
		
		if (dto.temClassificacaoFinal()) {
			protocolo.setClassificacaoFinal(dto.getClassificacaoFinal());
			if (!protocolo.temClassificacao()) {
				protocolo.setClassificacao(dto.getClassificacaoFinal());
			}
		}
		
		if (dto.temAdReferendum()) {
			protocolo.setAdReferendum((dto.isAdReferendum()));
		}
		
		if (usuario.temPerfil() && dto.temIdResponsavelAtual() ) {
	    	String perfilUsuario = usuario.temPerfil() ? usuario.getPerfilString() : "";
	    	if ( perfilUsuario.equals("siacolconselheiro") ||
	    			perfilUsuario.equals("siacolcoordenadorcamara") ) {
				protocolo.setConselheiroRelator(dto.getIdResponsavelAtual());
				protocolo.setNomeConselheiroRelator(dto.getNomeResponsavelAtual());
			} else if(perfilUsuario.equals("siacolanalista") && !ehCoordenadorCoac(usuario.getIdPessoa())){
				protocolo.setUltimoAnalista(dto.getIdResponsavelAtual());
			}
		}
		
		if (dto.temConselheiroDevolucao()){
			protocolo.setConselheiroDevolucao(dto.getIdConselheiroDevolucao());
		} else {
			protocolo.setConselheiroDevolucao(null);
		}
		
		if (dto.temEventoProtocolo()) {
			protocolo.setEvento(eventoConverter.toModel(dto.getEventoProtocolo()));
		}
		
		if (dto.temNumeroDocumento()) {
			protocolo.setNumeroDecisao(dto.getNumeroDocumento());
		}

		return protocolo;

	}

	private boolean ehCoordenadorCoac(Long idPessoa) {
		return departamentoDao.getBy(new Long(230201)).getCoordenador().getId().equals(idPessoa);
	}

	public void vinculaProtocolosResponsavel(List<VinculoProtocoloDto> listDto) {
		rlProtocoloResponsavelDao.vinculaProtocoloResponsavel(listDto);
	}

	public void desfazVinculoProtocoloResponsavel(Long numeroProtocoloVinculado) {
		rlProtocoloResponsavelDao.desfazVinculoProtocoloResponsavel(numeroProtocoloVinculado);
	}

	public VinculoProtocoloDto getProtocoloVinculado(Long numeroProtocoloVinculado) {
		return protocoloResponsavelConverter.toDto(rlProtocoloResponsavelDao.getProtocoloVinculadoDoResponsavel(numeroProtocoloVinculado));
	}

	public List<VinculoProtocoloDto> getVinculosProtocoloPor(Long numeroProtocoloPai) {
		return protocoloResponsavelConverter.toListDto(rlProtocoloResponsavelDao.getListVinculosProtocoloResponsavelPor(numeroProtocoloPai));
	}

	public boolean protocoloJaEstaVinculadoAoResponsavel(Long numeroProtocolo) {
		return rlProtocoloResponsavelDao.protocoloJaEstaVinculado(numeroProtocolo) ? true : false;
	}

	public boolean protocoloJaEstaNoSiacol(Long numeroProtocolo) {
		return dao.protocoloJaEstaSiacol(numeroProtocolo) ? true : false;
	}

	public void ativarProtocoloPorIdDepartamento(Long idDepartamento) {
		dao.ativaProtocolos(idDepartamento);
	}

	public GenericSiacolDto receberProtocolo(GenericSiacolDto dto, UserFrontDto usuario) {
		
		try {
			
			ProtocoloSiacol protocoloSiacol = new ProtocoloSiacol();
			protocoloSiacol = dao.getBy(new Long(dto.getIdProtocolo()));
//			if (!protocoloSiacol.temStatus()) {  FIXME
//				protocoloSiacol.setStatus(StatusProtocoloSiacol.ANALISE);
//			}
			if (dto.getStatus() != null) {
				protocoloSiacol = atualizaStatus(protocoloSiacol, dto.getStatus(), usuario, usuario);
			}
			protocoloSiacol.setRecebido(true);
			protocoloSiacol.setDataRecebimento(new Date());
				
			dao.update(protocoloSiacol);
			
			audita.auditaRecebimento(dto, protocoloSiacol, usuario);
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ProtocoloSiacolService || recebeProtocolo", StringUtil.convertObjectToJson(dto), e);
		}
		
		return dto;
	}

	public void criarExigencia(SiacolProtocoloExigenciaDto exigencia, UserFrontDto usuario) {
			
		ProtocoloSiacol protocoloSiacol = dao.getBy(exigencia.getProtocolo().getId());
		
		siacolProtocoloExigenciaDao.create(siacolProtocoloExigenciaConverter.toModel(exigencia));
		audita.pausa(protocoloSiacol, exigencia, usuario);
		
		atualizaStatus(protocoloSiacol, StatusProtocoloSiacol.PROTOCOLO_PAUSADO, usuario, usuario);
	}
	
	public void excluirExigencia(SiacolProtocoloExigenciaDto exigencia, UserFrontDto usuario) {
		
		ProtocoloSiacol protocoloSiacol = dao.getBy(exigencia.getProtocolo().getId());
		
		exigencia.setDataFim(new Date());
		if (exigencia.getArquivo() != null) {
			Long IdArquivo = exigencia.getArquivo().getId();	
			exigencia.setArquivo(null);	
			siacolProtocoloExigenciaDao.update(siacolProtocoloExigenciaConverter.toModel(exigencia));
			arquivoService.delete(IdArquivo);
		}else {
			siacolProtocoloExigenciaDao.update(siacolProtocoloExigenciaConverter.toModel(exigencia));
		}
		
		audita.retiraPausa(protocoloSiacol, exigencia, usuario);
		
		atualizaStatus(protocoloSiacol, protocoloSiacol.getUltimoStatus(), StatusProtocoloSiacol.PROTOCOLO_PAUSADO, usuario, usuario);
	}

	public SiacolProtocoloExigencia buscaExigenciaByIdProtocolo(Long idProtocolo){
		return siacolProtocoloExigenciaDao.buscaExigenciaByIdProtocolo(idProtocolo);
	}
	
	/**
	 * De acordo com regras acordadas entre Juan (Calma) e equipe COAC,
	 * busca todas informações para substituição de texto dos documentos gerados da análise do protocolo de empresa
	 * quando o protocolo possui requerimento. Se não existir requerimento, só será possível obter  
	 * a empresa através do número do protocolo para a substituição dos textos de documentos.
	 * @param numeroProtocolo
	 * @return protocoloEmpresa 
	 * */
	public ProtocoloSiacolEmpresaDto buscaDadosTextoProtocoloEmpresa(Long numeroProtocolo) {
		
		ProtocoloSiacolEmpresaDto protocoloEmpresa = new ProtocoloSiacolEmpresaDto();
		RequerimentoPJDto requerimento = requerimentoConverter.toDtoTextoProtocoloEmpresa(requerimentoDao.getRequerimentoPor(numeroProtocolo)); 
				 
		if( requerimento != null ) {
			
			protocoloEmpresa.setResponsavelTecnico(requerimento.getResponsavel());
			protocoloEmpresa.setNomeEmpresa(requerimento.getRazaoSocialEmpresa());
			protocoloEmpresa.setNumeroArt(requerimento.getNumeroArt());
			return protocoloEmpresa;
			
		} else {
			
			Protocolo protocolo = protocoloDao.getProtocoloBy(numeroProtocolo);
			if( protocolo != null ) {
				protocoloEmpresa.setNomeEmpresa(interessadoDao.buscaDescricaoRazaoSocial(protocolo.getPessoa().getId()));
				return protocoloEmpresa;
			}
		}
		
		return null;
	}

	public ProtocoloSiacolDto cadastrar(ProtocoloSiacolDto protocoloSiacol, UserFrontDto userDto) {
		return converter.toDto(dao.create(converter.toModelCadastrar(protocoloSiacol, protocoloDao.getProtocoloBy(protocoloSiacol.getNumeroProtocolo()))));
	}

	public boolean apensarProtocolo(JuntadaProtocoloDto dto) {

		ProtocoloSiacol protocolo = new ProtocoloSiacol();
		protocolo = dao.getProtocoloBy(dto.getProtocoloDaJuntada().getNumeroProtocolo());
		protocolo.setAtivo(false);
		dao.update(protocolo);
		
		return true;
	}

	public boolean desapensarProtocolo(JuntadaProtocoloDto dto) {
		
		ProtocoloSiacol protocolo = new ProtocoloSiacol();
		protocolo = dao.getProtocoloBy(dto.getProtocoloDaJuntada().getNumeroProtocolo());
		protocolo.setAtivo(true);
		dao.update(protocolo);
		
		return true;
	}

	public boolean ocultarProtocolo(Long numeroProtocolo, UserFrontDto userFrontDto) {
		
		ProtocoloSiacol protocolo = new ProtocoloSiacol();
		protocolo = dao.getProtocoloBy(numeroProtocolo);
		protocolo.setUltimoAnalista(protocolo.getIdResponsavel());
// FIXME	protocolo.setIdResponsavel(new Long (0));
//		protocolo.setNomeResponsavel("SEM RESPONSAVEL");
		protocolo.setAtivo(false);
		dao.update(protocolo);
		audita.auditaOcultarProtocolo(protocolo, userFrontDto);
		
		return true;
	}

	public Object mostrarProtocolo(Long numeroProtocolo, UserFrontDto userFrontDto) {
		ProtocoloSiacol protocolo = new ProtocoloSiacol();
		protocolo.setIdResponsavel(userFrontDto.getIdPessoa());
		protocolo.setNomeResponsavel(interessadoDao.getBy(userFrontDto.getIdPessoa()).getNome());
		protocolo = dao.getProtocoloBy(numeroProtocolo);
		protocolo.setAtivo(true);
		dao.update(protocolo);
		audita.auditaMostrarProtocolo(protocolo, userFrontDto);
		
		return true;
	}

	public Object marcarComoAdReferendum(Long idProtocolo) {
		List<Long> listaIdStatus = new ArrayList<Long>();
		listaIdStatus.add(new Long(15));
		listaIdStatus.add(new Long(17));
		listaIdStatus.add(new Long(20));
		listaIdStatus.add(new Long(22));
		
		ProtocoloSiacol protocolo = new ProtocoloSiacol();
		protocolo = dao.getBy(idProtocolo);
		if (listaIdStatus.contains(protocolo.getStatus().getId())) {
//	FIXME	marcarADReferendum
		}else{
//			
		}
		
		return true;
	}

	public ProtocoloSiacol atualizaStatus(ProtocoloSiacol protocolo, StatusProtocoloSiacol status, UserFrontDto user, UserFrontDto destinatario) {
		StatusProtocoloSiacol ultimoStatus = protocolo.getStatus();
		protocolo.setUltimoStatus(ultimoStatus);
		protocolo.setStatus(status);
		
		if (!status.equals(ultimoStatus)) {
			dao.update(protocolo);
			audita.alteraStatus(user, protocolo, destinatario);
		}		
		
		return protocolo;
	}

	private ProtocoloSiacol atualizaStatus(ProtocoloSiacol protocolo, StatusProtocoloSiacol status, StatusProtocoloSiacol ultimoStatus, UserFrontDto usuario, UserFrontDto destinatario) {
		protocolo.setUltimoStatus(ultimoStatus);
		protocolo.setStatus(status);
		dao.update(protocolo);
		audita.alteraStatus(usuario, protocolo, destinatario);
		
		return protocolo;
	}

	public boolean salvarObservacaoMovimento(GenericSiacolDto dto, UserFrontDto userDto) {
		try {
			Protocolo protocolo = protocoloDao.getProtocoloBy(dto.getIdProtocolo());
			ObservacoesMovimento observacaoMovimento = new ObservacoesMovimento();
			observacaoMovimento.setIdDepartamento(protocolo.getUltimoMovimento().getDepartamentoDestino().getId());
			observacaoMovimento.setData(new Date());
			observacaoMovimento.setIdCadDocumento(new Long(dto.getId()));
			observacaoMovimento.setIdFuncionario(userDto.getIdFuncionario());
			observacaoMovimento.setMovimento(protocolo.getUltimoMovimento());

			observacaoMovimentoDao.create(observacaoMovimento);
			
			return true;
		} catch (Throwable e) {
			return false;
		}
	}

	public ProtocoloSiacol getProtocoloParaUparDocumento(Long numeroProtocolo) {
		
		Long numeroProtocoloOuNumeroAnexo = protocoloDao.getNumeroProtocoloOuProtocoloAnexado(numeroProtocolo);	
		ProtocoloSiacol protocolo = dao.getProtocoloBy(numeroProtocoloOuNumeroAnexo);
		
		return protocolo;
	}

	public byte[] geraRelatorioXls(ConsultaProtocoloDto dto) {
		
		List<ProtocoloSiacolDto> listDto = new ArrayList<ProtocoloSiacolDto>();

		listDto = converter.toListDto(dao.getAllProtocolosSemPaginacao(dto));
		

			ExcelPoiUtil excelPoiUtil = new ExcelPoiUtil();
			excelPoiUtil.setCell("Data Envio");
			excelPoiUtil.setCell("Data Recebimento");
			excelPoiUtil.setCell("Status Siacol");
			excelPoiUtil.setCell("Situação Tramitação");
			excelPoiUtil.setCell("Processo");
			excelPoiUtil.setCell("Protocolo");
			excelPoiUtil.setCell("Sigla Dep.");
			excelPoiUtil.setCell("Departamento");
			excelPoiUtil.setCell("Classificação");			
			excelPoiUtil.setCell("Código Ass. Corp.");
			excelPoiUtil.setCell("Descrição Ass. Corp.");
			excelPoiUtil.setCell("Código Ass. Siacol.");
			excelPoiUtil.setCell("Descrição Ass. Siacol.");
			excelPoiUtil.setCell("Interessado");
			excelPoiUtil.setCell("Responsável");
			excelPoiUtil.setCell("Motivo Devolução'");
			excelPoiUtil.setCell("Classificação Final");
			excelPoiUtil.setCell("Ativo");
		

			listDto.forEach(rel -> {
				excelPoiUtil.newRow();
				excelPoiUtil.setCell(DateUtils.format(rel.getDataSiacol(), DateUtils.DD_MM_YYYY));
				excelPoiUtil.setCell(DateUtils.format(rel.getDataRecebimento(), DateUtils.DD_MM_YYYY));
				excelPoiUtil.setCell(rel.getStatus());
				excelPoiUtil.setCell(rel.getSituacao().getDescricao());
				excelPoiUtil.setCell(rel.getNumeroProcesso().toString());
				excelPoiUtil.setCell(rel.getNumeroProtocolo().toString());
				excelPoiUtil.setCell(rel.getDepartamento().getSigla());
				excelPoiUtil.setCell(rel.getDepartamento().getNome());
				excelPoiUtil.setCell(rel.getClassificacao().getNome());
				if(rel.getIdAssuntoCorportativo() != null) {
					excelPoiUtil.setCell(rel.getIdAssuntoCorportativo().toString());
				} else {
					excelPoiUtil.setCell("");
				}
				if(rel.getDescricaoAssuntoCorporativo() != null) {
					excelPoiUtil.setCell(rel.getDescricaoAssuntoCorporativo());
				} else {
					excelPoiUtil.setCell("");
				}
				if(rel.getAssunto() != null) {
					excelPoiUtil.setCell(rel.getAssunto().getCodigo().toString());
				} else {
					excelPoiUtil.setCell("");
				}
				if(rel.getAssunto() != null) {
					excelPoiUtil.setCell(rel.getAssunto().getDescricao());
				} else {
					excelPoiUtil.setCell("");
				}
				excelPoiUtil.setCell(rel.getNomeInteressado());
				excelPoiUtil.setCell(rel.getNomeResponsavel());
				excelPoiUtil.setCell(rel.getJustificativa());
				
				if (rel.getClassificacaoFinal() != null) {
				excelPoiUtil.setCell(rel.getClassificacaoFinal().getNome());
				} else {
					excelPoiUtil.setCell("");
				}
				
				excelPoiUtil.setCell(rel.getAtivo() ? "SIM" : "NÃO");
			
			});

			return excelPoiUtil.buildToStream();
	
	}

	
}
