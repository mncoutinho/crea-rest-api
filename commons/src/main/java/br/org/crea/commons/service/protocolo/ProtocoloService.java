package br.org.crea.commons.service.protocolo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.builder.protocolo.AnexarProtocoloBuilder;
import br.org.crea.commons.builder.protocolo.ApensarProtocoloBuilder;
import br.org.crea.commons.builder.protocolo.DesvincularJuntadaProtocoloBuilder;
import br.org.crea.commons.builder.protocolo.GerarNumeroProtocoloBuilder;
import br.org.crea.commons.builder.protocolo.SubstituirProtocoloBuilder;
import br.org.crea.commons.builder.protocolo.validaterules.ValidaAnexoProtocoloBuilder;
import br.org.crea.commons.builder.protocolo.validaterules.ValidaApensoProtocoloBuilder;
import br.org.crea.commons.builder.protocolo.validaterules.ValidaCadastroProtocoloBuilder;
import br.org.crea.commons.builder.protocolo.validaterules.ValidaDesvinculoJuntadaProtocoloBuilder;
import br.org.crea.commons.builder.protocolo.validaterules.ValidateSubstituicaoProtocoloBuilder;
import br.org.crea.commons.converter.cadastro.funcionario.FuncionarioConverter;
import br.org.crea.commons.converter.protocolo.MovimentoConverter;
import br.org.crea.commons.converter.protocolo.MovimentoProtocoloConverter;
import br.org.crea.commons.converter.protocolo.ObservacaoConverter;
import br.org.crea.commons.converter.protocolo.ProtocoloConverter;
import br.org.crea.commons.dao.cadastro.FormandoDao;
import br.org.crea.commons.dao.cadastro.domains.DepartamentoDao;
import br.org.crea.commons.dao.cadastro.pessoa.PessoaDao;
import br.org.crea.commons.dao.corporativo.GeradorSequenciaDao;
import br.org.crea.commons.dao.protocolo.AssuntoDao;
import br.org.crea.commons.dao.protocolo.CoInteressadoDao;
import br.org.crea.commons.dao.protocolo.MovimentoDao;
import br.org.crea.commons.dao.protocolo.ProtocoloDao;
import br.org.crea.commons.dao.protocolo.SituacaoProtocoloDao;
import br.org.crea.commons.docflow.builder.CadastrarDocumentoProtocoloDocflowBuilder;
import br.org.crea.commons.factory.AuditaProtocoloFactory;
import br.org.crea.commons.models.cadastro.Departamento;
import br.org.crea.commons.models.cadastro.dtos.pessoa.FormandoDto;
import br.org.crea.commons.models.commons.Cointeressado;
import br.org.crea.commons.models.commons.Movimento;
import br.org.crea.commons.models.commons.ObservacoesMovimento;
import br.org.crea.commons.models.commons.Protocolo;
import br.org.crea.commons.models.commons.TipoPessoa;
import br.org.crea.commons.models.commons.dtos.ObservacaoDto;
import br.org.crea.commons.models.commons.dtos.PesquisaGenericDto;
import br.org.crea.commons.models.commons.dtos.ProtocoloDto;
import br.org.crea.commons.models.commons.dtos.SituacaoProtocoloDto;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.models.commons.enuns.TipoCointeressado;
import br.org.crea.commons.models.corporativo.dtos.MovimentoDto;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;
import br.org.crea.commons.models.protocolo.dtos.JuntadaProtocoloDto;
import br.org.crea.commons.models.protocolo.dtos.MovimentoProtocoloDto;
import br.org.crea.commons.models.protocolo.dtos.SubstituicaoProtocoloDto;
import br.org.crea.commons.models.protocolo.enuns.TipoProtocoloEnum;

public class ProtocoloService {

	@Inject ProtocoloConverter converter;

	@Inject MovimentoProtocoloConverter movimentoProtocoloConverter;

	@Inject ObservacaoConverter observacaoConverter;
	
	@Inject FuncionarioConverter funcionarioConverter;

	@Inject ProtocoloDao protocoloDao;

	@Inject ValidaAnexoProtocoloBuilder validateAnexoBuilder;
	
	@Inject ValidaDesvinculoJuntadaProtocoloBuilder validateDesanexoBuilder;
	
	@Inject ValidaApensoProtocoloBuilder validateApensoBuilder;
	
	@Inject ValidateSubstituicaoProtocoloBuilder validateSubstituicaoBuilder;
	
	@Inject AnexarProtocoloBuilder anexoBuilder;

	@Inject ValidaCadastroProtocoloBuilder validateCadastroProtocoloBuilder;

	@Inject DesvincularJuntadaProtocoloBuilder desvinculoJuntadaBuilder;
	
	@Inject SubstituirProtocoloBuilder substituicaoBuilder;
	
	@Inject GerarNumeroProtocoloBuilder gerarNumeroProtocoloBuilder;
	
	@Inject MovimentoDao movimentoDao;
	
	@Inject MovimentoConverter movimentoConverter;
	
	@Inject DepartamentoDao departamentoDao;
	
	@Inject SituacaoProtocoloDao situacaoProtocoloDao;

	@Inject ApensarProtocoloBuilder apensoBuilder;
	
	@Inject PessoaDao pessoaDao;
	
	@Inject AssuntoDao assuntoDao;
	
	@Inject GeradorSequenciaDao geradorSequenciaDao;
	
	@Inject CoInteressadoDao coInteressadoDao;
	
	@Inject FormandoDao formandoDao;
	
	@Inject AuditaProtocoloFactory audita;
	
	@Inject CadastrarDocumentoProtocoloDocflowBuilder builderCadastroDocumentoDocFlow;
	
	public ProtocoloDto getProtocoloById(Long numeroProtocolo) {

		Protocolo protocolo = new Protocolo();
		protocolo = protocoloDao.getProtocoloBy(numeroProtocolo);

		return protocolo != null ? converter.toDto(protocolo) : null;

	}

	public List<ProtocoloDto> getProtocoloByProcesso(Long numeroProtocolo) {
		return converter.toListDto(protocoloDao.getProtocoloByProcesso(numeroProtocolo));
	}

	public List<MovimentoDto> getMovimentos(Long numeroProtocolo) {
		return movimentoConverter.toListDto(protocoloDao.getMovimentosBy(numeroProtocolo));
	}

	public List<ObservacaoDto> getObservacoesMovimentos(Long idMovimento) {
		List<ObservacoesMovimento> listObservacao = new ArrayList<ObservacoesMovimento>();
		listObservacao = protocoloDao.getObservacoesMovimento(idMovimento);
		return observacaoConverter.toListDto(listObservacao);
	}

	public byte[] downloadAnexoObservacao(Long idObservacao) throws IOException {

		byte[] bytes = protocoloDao.getAnexoObservacaoBy(idObservacao).getAnexo();
		if(bytes != null) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream(bytes.length);
			baos.write(bytes, 0, bytes.length);
		}
		return bytes;
	}

	public List<ProtocoloDto> getProtocolosByPessoa(Long idPessoa) {

		List<Protocolo> listProtocolos = new ArrayList<Protocolo>();
		listProtocolos = protocoloDao.getProtocolosByPessoa(idPessoa);

		return !listProtocolos.isEmpty() ? converter.toListDto(listProtocolos) : null;

	}
	
	public List<ProtocoloDto> getProtocolosPaginadosByPessoa(PesquisaGenericDto pesquisa) {
		List<Protocolo> listProtocolos = new ArrayList<Protocolo>();
		listProtocolos = protocoloDao.getProtocolosPaginadosByPessoa(pesquisa);

		return !listProtocolos.isEmpty() ? converter.toListDto(listProtocolos) : null;
	}
	
	public int getTotalDeRegistrosDaPesquisa(PesquisaGenericDto pesquisa) {
		return protocoloDao.getTotalDeRegistrosDaPesquisaProtocolosByPessoa(pesquisa);
	}
	
	public String getStatusTransacaoProtocoloPor(Long idStatusTransacao) {
		return protocoloDao.getStatusTransacaoProtocolo(idStatusTransacao);
	}

	public boolean vefificaDigitalicao(String protocolo) {
		return protocoloDao.verificaDigitalizacao(protocolo);
	}
	
	public ProtocoloDto buscaProtocoloEVinculos(Long numeroProtocolo) {
		
		ProtocoloDto protocolo = converter.toDto(protocoloDao.getProtocoloBy(numeroProtocolo));
		
		protocolo.setListAnexos(converter.toListDto(protocoloDao.getAnexosDoProtocoloPor(numeroProtocolo)));
		protocolo.setListApensos(converter.toListDto(protocoloDao.getApensosDoProtocoloPor(numeroProtocolo)));
		protocolo.setListProtocolosVinculadosProcesso(converter.toListDto(protocoloDao.getProtocoloByProcesso(numeroProtocolo)));
		
		return protocolo;
	}
	
	public List<Protocolo> buscaProtocolosFisicosDoPortalVinculadoAProcessoDigital(Long numeroProtocolo) {
		
		ProtocoloDto protocolo = converter.toDto(protocoloDao.getProtocoloBy(numeroProtocolo));
		return !protocolo.isDigital() ? new ArrayList<Protocolo>() : protocoloDao.getListProtocolosVirtuaisProcessoDigital(protocolo); 
	}
	
	public ProtocoloDto cadastrarProtocolo(ProtocoloDto protocoloDto, UserFrontDto user) {

		Protocolo protocolo = new Protocolo();
		
		validateCadastroProtocoloBuilder.validarCadastro(protocoloDto).build();
		
		if( !protocoloDto.possuiErros() ){
			prepararCadastroProtocolo(protocoloDto, user);	
						
			protocolo = gerarNumeroProtocoloBuilder.geraNumeroProtocoloPorTipoDeAssunto(converter.toModel(protocoloDto), protocoloDto).build();
			
			protocolo.setDataEmissao(new Date());

			Movimento movimento = movimentoProtocoloConverter.toModel(prepararCadastroPrimeiroMovimento(protocoloDto, user));
			
			movimentoDao.cadastrarPrimeiroMovimento(movimento, protocolo);
			
			
		//	builderCadastroDocumentoDocFlow.cadastrarMetadadosDocumento(dadosParaCriaCapa(protocolo, protocoloDto, movimento, user));
					 
		}
		
		return converter.toDtoCadastrar(protocoloDto, protocolo);
	}
	

	private ProtocoloDto prepararCadastroProtocolo(ProtocoloDto protocoloDto, UserFrontDto user){
		protocoloDto.setIdFuncionario(user.getIdFuncionario());
		
		Pessoa pessoa = pessoaDao.getBy(protocoloDto.getPessoa().getId());
		
		protocoloDto.setTipoPessoa(pessoa.getTipoPessoa().name());
		
		return protocoloDto;		
	}
	
	private MovimentoProtocoloDto prepararCadastroPrimeiroMovimento(ProtocoloDto protocoloDto, UserFrontDto user){
		
		Departamento departamento = departamentoDao.getBy(protocoloDto.getIdDepartamentoOrigem());

		SituacaoProtocoloDto situacaoDto = new SituacaoProtocoloDto();
		situacaoDto.setId(0L);
		
		MovimentoProtocoloDto movimentoDto = new MovimentoProtocoloDto();
		movimentoDto.setDataEnvio(new Date());
		movimentoDto.setDataRecebimento(new Date());
		movimentoDto.setIdDepartamentoOrigem(departamento.getId());
		movimentoDto.setIdDepartamentoDestino(departamento.getId());
		movimentoDto.setIdFuncionarioReceptor(user.getIdFuncionario());
		movimentoDto.setIdFuncionarioRemetente(user.getIdFuncionario());
		movimentoDto.setIdDepartamentoPaiDestino(departamento.getDepartamentoPai().getId());
		movimentoDto.setModuloDepartamentoDestino(departamento.getModuloDepartamento());
		movimentoDto.setSituacao(situacaoDto);
		
		return movimentoDto;
	}
	
	public JuntadaProtocoloDto anexarProtocolo(JuntadaProtocoloDto dto, UserFrontDto usuario) {
		JuntadaProtocoloDto response = anexoBuilder.juntarProtocoloAnexo(validateAnexoBuilder.validarAnexacao(dto, usuario).build(), usuario).buildAnexo();
		
		if (!response.possuiErrosNaJuntada()) audita.auditaJuntada(response, usuario);
		
		return response;
	}
	
	public JuntadaProtocoloDto apensarProtocolo(JuntadaProtocoloDto dto, UserFrontDto usuario) {
		JuntadaProtocoloDto response = apensoBuilder.juntarProtocoloApenso(validateApensoBuilder.validarApensacao(dto, usuario).build(), usuario).buildApenso();
		
		if (!response.possuiErrosNaJuntada()) audita.auditaJuntada(response, usuario);
		
		return response;
	}
	
	public JuntadaProtocoloDto desvincularProtocoloJuntada(JuntadaProtocoloDto dto, UserFrontDto usuario) {
		JuntadaProtocoloDto response = desvinculoJuntadaBuilder.desvincularProtocolo(validateDesanexoBuilder.validarDesvinculo(dto, usuario).build(), usuario).buildDesvinculo();
		
		if (!response.possuiErrosNaJuntada()) audita.auditaJuntada(response, usuario);
		
		return response;
	}
	
	public SubstituicaoProtocoloDto substituirProtocolo(SubstituicaoProtocoloDto dto, UserFrontDto usuario) {
		return substituicaoBuilder.substituirProtocolo(validateSubstituicaoBuilder.validarSubstituicao(dto, usuario).build(), usuario).build();
	}

	public boolean verificaAnexo(ProtocoloDto protocoloDto) {
		List<Protocolo> listProtocoloAnexo = new ArrayList<Protocolo>(); 
		listProtocoloAnexo = protocoloDao.getAnexosDoProtocoloPor(protocoloDto.getNumeroProcesso());
		for (Protocolo p : listProtocoloAnexo) {
			if (p.getNumeroProtocolo() == protocoloDto.getNumeroProtocolo()) {
				return true;
			}
		}
		return false;
	}
	
	public FormandoDto cadastrarProtocoloParaCadastroDeFormandosExterno(FormandoDto dto) throws Exception {
		
		Protocolo protocolo = new Protocolo();
		protocolo.setIdFuncionario(99990l);
		protocolo.setDataEmissao(Calendar.getInstance().getTime());
		
		Pessoa pessoa = pessoaDao.getBy(dto.getInstituicao().getId());
		
		protocolo.setInteressado(pessoa.getId() != null ? String.valueOf(pessoa.getId()) : null);
	    protocolo.setPessoa(pessoa);
	    protocolo.setTipoPessoa(pessoa.getTipoPessoa());
	    protocolo.setNumeroProcesso(dto.getInstituicao().getId());
	    protocolo.setAssunto(assuntoDao.getBy(17184l));
	    
	    Long numero = geradorSequenciaDao.getSequenciaWithFlush(TipoProtocoloEnum.PROTOCOLO.getDigito());
	    //Necessário para evitar o OptimisticLockException devido a concorrência
	    int avoidInfinteLoop = 0;
	    while(numero == 0) {
	    	numero = geradorSequenciaDao.getSequenciaWithFlush(TipoProtocoloEnum.PROTOCOLO.getDigito());
	    	avoidInfinteLoop+=1;
	    	if(avoidInfinteLoop > 1000) throw new Exception("Exceção de OptimisticLockException");
	    }
	    
	    protocolo.setNumeroProtocolo(numero);
	    protocolo.setIdProtocolo(numero);
	    protocolo.setTipoProtocolo(TipoProtocoloEnum.PROTOCOLO);
	    protocolo.setAi(0l);
	    protocolo.setNotificacao(0l);

	    Movimento movimento = new Movimento();
	    movimento.setDepartamentoOrigem(departamentoDao.getBy(23020203l));
	    movimento.setDepartamentoDestino(departamentoDao.getBy(23040501l));
	    movimento.setIdFuncionarioRemetente(99990l);
	    movimento.setIdFuncionarioReceptor(null);
	    movimento.setSituacao(situacaoProtocoloDao.getBy(0l));
	    movimento.setDataEnvio(Calendar.getInstance().getTime());
	    movimento.setTempoPermanencia(0l);
	    
	    protocolo = movimentoDao.cadastrarPrimeiroMovimento(movimento, protocolo);

	    for(FormandoDto formandoDto : dto.getFormandosProcessadoComSucesso()) {
	    	Cointeressado cointeressado = new Cointeressado();
	    	Pessoa pessoaCointeressado = pessoaDao.getBy(formandoDto.getIdPessoa());
	    	cointeressado.setPessoa(pessoaCointeressado);
	    	cointeressado.setTipoCointeressado(TipoCointeressado.COINTERESSADO);
	    	cointeressado.setRequerente(true);
	    	cointeressado.setTipoPessoa(TipoPessoa.FORMANDO);
	    	cointeressado.setProtocolo(protocolo);
	    	coInteressadoDao.create(cointeressado);
	    	formandoDto.setProtocolo(String.valueOf(protocolo.getNumeroProtocolo()));
	    	formandoDao.atualizaNumeroDeProtocolo(formandoDto);
	    }
	    
	    dto.setProtocolo(String.valueOf(protocolo.getNumeroProtocolo()));
	    dto.setProtocoloGerado(true);
		
	    return dto;
	}

	public Protocolo atualizarParaDigital(String numeroProtocolo) {
		Protocolo protocolo = protocoloDao.getProtocoloBy(new Long (numeroProtocolo));
		protocolo.setDigital(true);
		return protocoloDao.update(protocolo);
	}
	
	public List<ProtocoloDto> getListaProtocoloPorNumero(PesquisaGenericDto dto) {
		
		List<ProtocoloDto> listaProtocoloDto = null; 
		
		if(dto.isChecked()) {
			List<Cointeressado> listaCoInteressado = coInteressadoDao.getListaFiltroCoInteressadoProtocolosPaginado(dto);
			List<Protocolo> listaProtocolo = new ArrayList<Protocolo>();
			if(listaCoInteressado != null) {
				for(Cointeressado cointeressado : listaCoInteressado) {
					listaProtocolo.add(cointeressado.getProtocolo());
				}
			}
			listaProtocoloDto = converter.toListMinDto(listaProtocolo);
		} else {
			listaProtocoloDto = converter.toListMinDto(protocoloDao.getListaFiltroProtocolosPaginado(dto));
		}

		return listaProtocoloDto;
	}

	public int getTotalDeProtocolos(PesquisaGenericDto pesquisa) {
		return pesquisa.isChecked() ? coInteressadoDao.getTotalDeProtocolosCoInteressados(pesquisa) : protocoloDao.getTotalDeProtocolos(pesquisa);
	}

	
}