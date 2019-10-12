package br.org.crea.protocolo.service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import br.org.crea.commons.builder.protocolo.validaterules.ValidaEnvioProtocoloBuilder;
import br.org.crea.commons.builder.protocolo.validaterules.ValidaRecebimentoProtocoloBuilder;
import br.org.crea.commons.models.commons.dtos.ProtocoloDto;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.models.protocolo.dtos.TramitacaoProtocoloDto;
import br.org.crea.commons.models.protocolo.dtos.TramiteDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;
import br.org.crea.protocolo.builder.EnviarProtocoloBuilder;
import br.org.crea.protocolo.builder.MovimentarProtocoloArquivoVirtualBuilder;
import br.org.crea.protocolo.builder.ReceberProtocoloBuilder;

public class TramiteProtocoloService {
	
	@Inject HttpClientGoApi httpGoApi;
	
	@Inject ValidaEnvioProtocoloBuilder validateEnvio;
	
	@Inject ValidaRecebimentoProtocoloBuilder validateRecebimento;
	
	@Inject EnviarProtocoloBuilder envioProtocoloBuilder;
	
	@Inject ReceberProtocoloBuilder receberProtocoloBuilder;
	
	@Inject MovimentarProtocoloArquivoVirtualBuilder tramiteArquivoVirtualBuilder;
	
	private ExecutorService threadPool;
	
	private TramitacaoProtocoloDto tramitacaoProtocoloDto;
	
	/** Método para enviar uma lista de um ou mais protocolos para um departamento de destino
	 * 
	 * Este método recebe uma lista de protocolos a tramitar e passa para a thread gerenciar a execução do trâmite.
	 * 
	 * Esta lista é primeiramente validada, conforme as regras estabelecidas, pelo método validarEnvio 
	 * que retorna uma lista de TramiteDto relativa aos protocolos passados com suas respectivas mensagens de erro quando for o caso.
	 * 
	 * Em seguida, é gerado o movimento dos protocolos validados pelo método gerarMovimentoProtocolo que retorna a lista de TramiteDto
	 * com as devidas tramitações de envio já efetuadas.
	 * 
	 * Após a execução, a thread é finalizada para garantir que seus recursos sejam liberados.
	 * 
	 * @param dto - contém uma lista de um ou mais protocolos, funcionário que tramitou, departamento de destino 
	 *              e departamento pai em relação ao de destino (útil para validação de algumas regras).
	 * @param usuario - token
	 * @return lista de TramiteDto dos protocolos passados no dto com suas respectivas mensagens de sucesso ou de erro conforme o caso.
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public List<TramiteDto> enviar(final TramitacaoProtocoloDto dto, final UserFrontDto usuario) throws InterruptedException, ExecutionException {
		
		tramitacaoProtocoloDto = dto;	
		
		this.threadPool = Executors.newCachedThreadPool();
		Future<List<TramiteDto>> resultTramite = null;

		try {
			
			resultTramite = threadPool.submit(new Callable<List<TramiteDto>>() {

				@Override
				public List<TramiteDto> call() {
					
					return envioProtocoloBuilder
							.gerarMovimentoProtocolo(
									dto.getModulo(),
								    validateEnvio.validarEnvio(tramitacaoProtocoloDto, usuario).build(),
								    usuario)
							.buildMovimento();
				}
		   });
		
		   if(this.threadPool.isTerminated()) {
			   this.threadPool.shutdown();
		   }	
			
		} catch (Throwable e) {
			httpGoApi.geraLog("TramiteProtocoloService || enviar", StringUtil.convertObjectToJson(dto), e);
		}
		
		return resultTramite.get();
	}

	/** Método para receber uma lista de um ou mais protocolos em um departamento de destino
	 * 
	 * Este método recebe uma lista de protocolos a tramitar e passa para a thread gerenciar a execução do trâmite.
	 * 
	 * Esta lista é primeiramente validada, conforme as regras estabelecidas, pelo método validarRecebimento 
	 * que retorna uma lista de TramiteDto relativa aos protocolos passados com suas respectivas mensagens de erro quando for o caso.
	 * 
	 * Em seguida, é gerado o movimento dos protocolos validados pelo método gerarMovimentoProtocolo que retorna a lista de TramiteDto
	 * com as devidas tramitações de recebimento já efetuadas.
	 * 
	 * Após a execução, a thread é finalizada para garantir que seus recursos sejam liberados.
	 * 
	 * @param dto - contém uma lista de um ou mais protocolos, funcionário que tramitou, departamento de destino 
	 *              e departamento pai em relação ao de destino (útil para validação de algumas regras).
	 * @param usuario - token
	 * @return lista de TramiteDto dos protocolos passados no dto com suas respectivas mensagens de sucesso ou de erro conforme o caso.
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public List<TramiteDto> receber(final TramitacaoProtocoloDto dto, final UserFrontDto usuario) throws InterruptedException, ExecutionException {
		
		tramitacaoProtocoloDto = dto;
		
		this.threadPool = Executors.newCachedThreadPool();
		Future<List<TramiteDto>> resultTramite = null;
		
		try {
			resultTramite = threadPool.submit(new Callable<List<TramiteDto>>() {

				@Override
				public List<TramiteDto> call() throws Exception {
					return receberProtocoloBuilder.gerarRecebimentoProtocolo(dto.getModulo(),
							validateRecebimento.validarRecebimento(tramitacaoProtocoloDto, usuario).build(), usuario).buildMovimento();
				}
			});
			
			if(this.threadPool.isTerminated()) {
				this.threadPool.shutdown();
			}
			
		} catch (Throwable e) {
			httpGoApi.geraLog("TramiteProtocoloService || receber", StringUtil.convertObjectToJson(dto), e);
		}
		return resultTramite.get();
	}
	
	
	/**
	 * Este método recebe um protocolo principal que deve ser digital e possuir vinculos com protocolos
	 * físicos que foram cadastrados no Portal e estão no arquivo Virtual.
	 * 
	 * Será validado se o protocolo do Arquivo Virtual tem condições de ser digitalizado e cadastrado no Docflow
	 * para que possa ficar disponível a ser anexado a seu protocolo principal.
	 * 
	 * @param  usuario - token
	 * @param  protocoloPrincipal - protocolo 'pai' / principal.
	 * @param  request
	 * @return lista de protocolos movimentados do arquivo virtual.
	 * @throws InterruptedException
	 * @throws ExecutionException
	 * @throws IOException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public List<TramiteDto> movimentarProtocolosArquivoVirtual(HttpServletRequest request, UserFrontDto usuario, ProtocoloDto protocoloPrincipal) throws InterruptedException, ExecutionException, IllegalArgumentException, IllegalAccessException, IOException {
	
		try {
			return tramiteArquivoVirtualBuilder.movimentaProtocoloArquivoVirtual(protocoloPrincipal, usuario, request).build();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("TramiteProtocoloService || movimentarProtocolosArquivoVirtual", StringUtil.convertObjectToJson(protocoloPrincipal), e);
		}
		
		return null;
		
	}
}