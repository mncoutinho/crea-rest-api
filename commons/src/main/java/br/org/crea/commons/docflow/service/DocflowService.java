package br.org.crea.commons.docflow.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import br.org.crea.commons.dao.cadastro.domains.DepartamentoDao;
import br.org.crea.commons.docflow.builder.CadastrarDocumentoProtocoloDocflowBuilder;
import br.org.crea.commons.docflow.client.ClientExclusaoConteudoDocflow;
import br.org.crea.commons.docflow.client.ClientInformacoesProtocoloDocflow;
import br.org.crea.commons.docflow.client.ClientInformacoesUsuarioDocflow;
import br.org.crea.commons.docflow.client.ClientJuntadaProtocoloDocflow;
import br.org.crea.commons.docflow.client.ClientRecuperaArquivo;
import br.org.crea.commons.docflow.client.ClientSubstituicaoProtocoloDocflow;
import br.org.crea.commons.docflow.client.ClientTramiteProtocoloDocflow;
import br.org.crea.commons.docflow.converter.DepartamentoDocFlowConverter;
import br.org.crea.commons.docflow.converter.DocflowGenericConverter;
import br.org.crea.commons.docflow.converter.DocumentoDocflowConverter;
import br.org.crea.commons.docflow.converter.ProtocoloDocflowConverter;
import br.org.crea.commons.docflow.converter.UsuarioDocflowConverter;
import br.org.crea.commons.docflow.dto.DepartamentoDocflowDto;
import br.org.crea.commons.docflow.dto.DocflowGenericDto;
import br.org.crea.commons.docflow.dto.DocumentoDocflowDto;
import br.org.crea.commons.docflow.dto.FileUploadDocflowDto;
import br.org.crea.commons.docflow.dto.ProtocoloDocflowDto;
import br.org.crea.commons.docflow.dto.UsuarioDocflowDto;
import br.org.crea.commons.docflow.model.departamento.DepartamentoDocflow;
import br.org.crea.commons.docflow.model.processo.DocumentoProcessoDocflow;
import br.org.crea.commons.docflow.model.response.ResponseAuthDocflow;
import br.org.crea.commons.docflow.model.response.ResponseCadastroDocumentoDocflow;
import br.org.crea.commons.docflow.model.response.ResponseConsultaProcessoDocflow;
import br.org.crea.commons.docflow.model.response.ResponseConsultaUsuarioDocflow;
import br.org.crea.commons.docflow.model.response.ResponseExclusaoConteudoDocflow;
import br.org.crea.commons.docflow.model.response.ResponseJuntadaProtocoloDocflow;
import br.org.crea.commons.docflow.model.response.ResponseSubstituicaoProtocoloDocflow;
import br.org.crea.commons.docflow.model.response.ResponseTramiteProtocoloDocflow;
import br.org.crea.commons.docflow.util.AuthUtilDocflow;
import br.org.crea.commons.factory.commons.AuditaDocflowFactory;
import br.org.crea.commons.models.cadastro.Departamento;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.service.ReportService;
import br.org.crea.commons.validsigner.dto.FormUploadAssinaturaDto;

public class DocflowService {
	
	@Inject CadastrarDocumentoProtocoloDocflowBuilder builderCadastro;
	
	@Inject AuthUtilDocflow authUtil;
	
	@Inject ReportService reportService;
	
	@Inject ClientTramiteProtocoloDocflow clientTramitacao;
	
	@Inject ClientExclusaoConteudoDocflow clientExclusaoConteudo;
	
	@Inject ClientInformacoesProtocoloDocflow clientConsultaProtocolo;
	
	@Inject ClientInformacoesUsuarioDocflow clientConsultaUsuario;
	
	@Inject ClientJuntadaProtocoloDocflow clientJuntada;
	
	@Inject ClientSubstituicaoProtocoloDocflow clientSubstituicao;
	
	@Inject ClientRecuperaArquivo clientRecuperaArquivo;
	
	@Inject ProtocoloDocflowConverter protocoloConverter;
	
	@Inject DocumentoDocflowConverter documentoConverter;
	
	@Inject DepartamentoDao departamentoDao;
	
	@Inject UsuarioDocflowConverter usuarioConverter;
	
	@Inject DepartamentoDocFlowConverter departamentoConverter;
	
	@Inject DocflowGenericConverter genericDocflowConverter;
	
	@Inject AuditaDocflowFactory audita;
	
//	@Inject ProtocoloSiacolDao protocoloSiacolDao;
	
	/**
	 * Gera um documento utilizando a camada de report e na sequência insere o documento criado
	 * em processo já existente no Docflow.
	 * 
	 * Após geração do documento, consome api do Docflow e cria metadados do documento, faz upload do binário e 
	 * associa o documento ao processo.
	 *  
	 * @author Monique Santos
	 * @param request - necessário para que a camada de report 'enxergue' o contexto e localize o template usado na geração do documento
	 * @param dto - deve conter parâmetros obrigatórios necessários a criação do documento no Docflow.
	 * */
	public ResponseCadastroDocumentoDocflow gerarInserirDocumentoEmProcessoEletronico(HttpServletRequest request, DocflowGenericDto dto, UserFrontDto usuario) throws IllegalArgumentException, IllegalAccessException, IOException{
	
		dto.setInputStreamArquivoPdf(reportService.getDocumentoInputStream(request, dto.getDocumento()));
		ResponseCadastroDocumentoDocflow response =  builderCadastro.cadastrarMetadadosDocumento(dto).uploadBinarioDocumentoPdf(dto).associarDocumentoAProcessoExistente(dto).buildCadastroDocumento();
		
		audita.cadastrarDocumento(dto, usuario);
		
		return response;
	}
	
	/**
	 * Gera um documento utilizando a camada de report e na sequência insere o documento criado
	 * em processo NÂO existente no Docflow.
	 * 
	 * Após geração do documento, consome api do Docflow e cria metadados do documento, faz upload do binário e 
	 * cria um processo para receber o documento.
	 *  
	 * @author Fernando Nicolau (Calma Informática)
	 * @param request - necessário para que a camada de report 'enxergue' o contexto e localize o template usado na geração do documento
	 * @param dto - deve conter parâmetros obrigatórios necessários a criação do documento no Docflow.
	 * */
	public ResponseCadastroDocumentoDocflow gerarInserirDocumentoEmProcessoEletronicoNaoDigital(HttpServletRequest request, DocflowGenericDto dto, UserFrontDto usuario) throws IllegalArgumentException, IllegalAccessException, IOException{
	
		dto.setInputStreamArquivoPdf(reportService.getDocumentoInputStream(request, dto.getDocumento()));
		ResponseCadastroDocumentoDocflow response =  builderCadastro.cadastrarMetadadosDocumento(dto).uploadBinarioDocumentoPdf(dto).criarProtocoloAssociarDocumento(dto).buildCadastroDocumento();
		
		audita.cadastrarDocumento(dto, usuario);
		
		return response;
	}
	
	/**
	 * Associa um documento 'externo' (documento que chega como form data, existente no file system do usuário)
	 * ao processo já existente no Docflow.
	 * 
	 * Consome api do Docflow e cria metadados do documento, faz upload do binário e 
	 * associa o documento ao processo.
	 * 
	 * @author Monique Santos
	 * @param fileUploadDto - deve trazer o file e as demais informações necessárias para consumir a api Docflow
	 * 
	 * */
	public ResponseCadastroDocumentoDocflow uparDocumentoEmProcessoEletronico(FileUploadDocflowDto fileUploadDto) {
		
		DocflowGenericDto dto = genericDocflowConverter.toDocFlowGenericDto(fileUploadDto);
		return builderCadastro.cadastrarMetadadosDocumento(dto).uploadBinarioDocumentoPdf(dto).associarDocumentoAProcessoExistente(dto).buildCadastroDocumento();
	}
	
	/**
	 * Cadastra um protocolo no Docflow, com base em um documento. Obrigatoriamente para cadastro de um protocolo, deve haver a existência de
	 * um documento.
	 * Consome api do Docflow e cria metadados do documento, faz upload do binário e autua o processo com base no documento.
	 * @author Monique Santos
	 * @param dto - deve conter parâmetros obrigatórios necessários ao cadastro do protocolo no Docflow.
	 * */
	public ResponseCadastroDocumentoDocflow cadastrarProtocoloEletronico(DocflowGenericDto dto) {
		return builderCadastro.cadastrarMetadadosDocumento(dto).uploadBinarioDocumentoPdf(dto).autuarProcessoComBaseEmDocumentoExistente(dto).buildCadastroDocumento();
	}

	/**
	 * Exclui um documento no Docflow com base no identificador do mesmo.
	 * @author Monique Santos
	 * @param dto - deve conter parâmetros obrigatórios necessários a exclusão do documento no Docflow.
	 * */
	public ResponseExclusaoConteudoDocflow excluirDocumento(DocflowGenericDto dto) {
		
		dto.setToken(authUtil.auth().getAutenticationData().getAuthToken());
		return clientExclusaoConteudo.excluirDocumento(dto);
	}
	
	/**
	 * Exclui um documento no Docflow com base no identificador do mesmo.
	 * @author Monique Santos
	 * @param userFrontDto 
	 * @param dto - deve conter parâmetros obrigatórios necessários a exclusão do documento no Docflow.
	 * */
	public File recuperaArquivo(Long idDocumento, Long idPessoa) {
		
//		dto.setToken(authUtil.auth().getAutenticationData().getAuthToken());
		return clientRecuperaArquivo.recuperaArquivo(idDocumento, authUtil.auth().getAutenticationData().getAuthToken(), idPessoa);
	}
	
	/**
	 * Cadastro um documento 'avulso'no Docflow, ou seja, sem estar associado com nenhum protocolo do Corporativo.
	 * Consome api do Docflow cria metadados do documento e faz upload do binário.
	 * @author Monique Santos
	 * @param dto - deve conter parâmetros obrigatórios necessários ao cadastro do documento no Docflow. 
	 * Aguarda um file form-data
	 * @return ResponseCadastroDocumentoDocflow
	 * */
	public ResponseCadastroDocumentoDocflow cadastrarDocumentoSemProcesso(FileUploadDocflowDto fileUploadDto) {
		
		DocflowGenericDto dto = genericDocflowConverter.toDocFlowGenericDto(fileUploadDto);
		return builderCadastro.cadastrarMetadadosDocumento(dto).uploadBinarioDocumentoPdf(dto).buildCadastroDocumento();
	}

	/**
	 * Cadastro um documento 'avulso'no Docflow, ou seja, sem estar associado com nenhum protocolo do Corporativo.
	 * Consome api do Docflow cria metadados do documento e faz upload do binário.
	 * @author Monique Santos
	 * @param dto - deve conter parâmetros obrigatórios necessários ao cadastro do documento no Docflow. 
	 * Aguarda um file form-data
	 * @return ResponseCadastroDocumentoDocflow
	 * */
	public ResponseCadastroDocumentoDocflow cadastrarDocumentoSemProcesso(DocflowGenericDto dto) {
		return builderCadastro.cadastrarMetadadosDocumento(dto).uploadBinarioDocumentoPdf(dto).buildCadastroDocumento();
	}
	
	/**
	 * Retorna token de autorização para consumo dos recursos da api Docflow.
	 * A geração deste token é do tipo 'sistema', ou seja, sem informar quem é o usuário que solicita a autorização.
	 * @author Monique Santos
	 * @return ResponseAuthDocflow
	 * */
	public ResponseAuthDocflow getToken() {
		return authUtil.auth();
	}
	
	/**
	 * Realiza um trâmite de envio de protocolo no Docflow
	 * @author Monique Santos
	 * @param dto - deve conter parâmetros obrigatórios necessários ao envio de protocolo no Docflow.
	 * @return ResponseTramiteProtocoloDocflow
	 * */
	public ResponseTramiteProtocoloDocflow enviarProtocolo(DocflowGenericDto dto) {
		
		dto.setToken(authUtil.auth().getAutenticationData().getAuthToken());
		return clientTramitacao.enviarProtocolo(dto);
	}
	
	/**
	 * Realiza um trâmite de recebimento de protocolo no Docflow
	 * @author Monique Santos
	 * @param dto - deve conter parâmetros obrigatórios necessários ao recebimento de protocolo no Docflow.
	 * @return ResponseTramiteProtocoloDocflow
	 * */
	public ResponseTramiteProtocoloDocflow receberProtocolo(DocflowGenericDto dto) {
		dto.setToken(authUtil.auth().getAutenticationData().getAuthToken());
		return clientTramitacao.receberProtocolo(dto);
	}

	/**
	 * Anexa um protocolo a outro no Docflow
	 * @author Monique Santos
	 * @param dto - deve conter parâmetros obrigatórios necessários a anexação de protocolo no Docflow.
	 * @return ResponseJuntadaProtocoloDocflow
	 * */
	public ResponseJuntadaProtocoloDocflow anexarProtocolo(DocflowGenericDto dto) {
		dto.setToken(authUtil.auth().getAutenticationData().getAuthToken());
		return clientJuntada.anexarProtocolo(dto);
	}
	
	/**
	 * Desanexa um protocolo do seu processo principal antes anexado no Docflow
	 * @author Monique Santos
	 * @param dto - deve conter parâmetros obrigatórios necessários a desanexação de protocolo no Docflow.
	 * @return ResponseJuntadaProtocoloDocflow
	 * */
	public ResponseJuntadaProtocoloDocflow desanexarProtocolo(DocflowGenericDto dto) {
		dto.setToken(authUtil.auth().getAutenticationData().getAuthToken());
		return clientJuntada.desanexarProtocolo(dto);
	}
	
	/**
	 * Apensa um protocolo a outro no Docflow
	 * @author Monique Santos
	 * @param dto - deve conter parâmetros obrigatórios necessários a apensacao de protocolo no Docflow.
	 * @return ResponseJuntadaProtocoloDocflow
	 * */
	public ResponseJuntadaProtocoloDocflow apensarProtocolo(DocflowGenericDto dto) {
		dto.setToken(authUtil.auth().getAutenticationData().getAuthToken());
		return clientJuntada.apensarProtocolo(dto);
	}
	
	/**
	 * Desapensa um protocolo do seu processo principal antes apensado no Docflow
	 * @author Monique Santos
	 * @param dto - deve conter parâmetros obrigatórios necessários a desapensação de protocolo no Docflow.
	 * @return ResponseJuntadaProtocoloDocflow
	 * */
	public ResponseJuntadaProtocoloDocflow desapensarProtocolo(DocflowGenericDto dto) {
		dto.setToken(authUtil.auth().getAutenticationData().getAuthToken());
		return clientJuntada.desapensarProtocolo(dto);
	}
	
	/**
	 * Substitui um protocolo por outro. O substituto passar a ter o estado cancelado
	 * e o novo protocolo passa a ter apenas os novos documentos do protocolo substituto.
	 * @author Monique Santos
	 * @param dto - deve conter parâmetros obrigatórios necessários a substituição de protocolo no Docflow.
	 * @return ResponseSubstituicaoProtocoloDocflow
	 * */
	public ResponseSubstituicaoProtocoloDocflow substituirProtocolo(DocflowGenericDto dto) {
		dto.setToken(authUtil.auth().getAutenticationData().getAuthToken());
		return clientSubstituicao.substituirProtocolo(dto);
	}
	
	/**
	 * Retorna informações do usuário cadastrado no Docflow
	 * Útil para verificar unidade padrão, unidades permissionadas, nível de sigilosidade etc.
	 * @author Monique Santos
	 * @param dto - deve conter parâmetros obrigatórios necessários a consulta de usuário no Docflow.
	 * @return usuarioDto
	 * */
	public UsuarioDocflowDto consultarUsuario(DocflowGenericDto dto) {

		dto.setToken(authUtil.auth().getAutenticationData().getAuthToken());
		ResponseConsultaUsuarioDocflow responseConsulta = clientConsultaUsuario.consultarUsuario(dto);
		
		UsuarioDocflowDto usuarioDto = new UsuarioDocflowDto();
		
		if(responseConsulta.hasError()) {
			
			usuarioDto.setMessageError(responseConsulta.getMessage().getValue());
			
		} else {
			
			usuarioDto = usuarioConverter.toDto(responseConsulta.getInfo().getUsuario().getDadosUsuario());
			usuarioDto.setUnidades(populaDepartamentosUsuario(responseConsulta.getInfo().getUsuario().getUnidades()));
		}
		
		return usuarioDto;
	}
	
	public UsuarioDocflowDto consultarUnidadesSiacolUsuario(DocflowGenericDto dto) {

		dto.setToken(authUtil.auth().getAutenticationData().getAuthToken());
		ResponseConsultaUsuarioDocflow responseConsulta = clientConsultaUsuario.consultarUsuario(dto);
		
		UsuarioDocflowDto usuarioDto = new UsuarioDocflowDto();
		
		if(responseConsulta.hasError()) {
			
			usuarioDto.setMessageError(responseConsulta.getMessage().getValue());
			
		} else {
			
			usuarioDto = usuarioConverter.toDto(responseConsulta.getInfo().getUsuario().getDadosUsuario());
			usuarioDto.setUnidades(populaDepartamentosSiacolUsuario(responseConsulta.getInfo().getUsuario().getUnidades()));
		}
		
		return usuarioDto;
	}
	
	public UsuarioDocflowDto consultarIdsUnidadesUsuario(DocflowGenericDto dto) {

		List<Long> idsUnidadesUsuario = new ArrayList<Long>();
		dto.setToken(authUtil.auth().getAutenticationData().getAuthToken());
		ResponseConsultaUsuarioDocflow responseConsulta = clientConsultaUsuario.consultarUsuario(dto);
		
		UsuarioDocflowDto usuarioDto = new UsuarioDocflowDto();
		
		if(responseConsulta.hasError()) {
			
			usuarioDto.setMessageError(responseConsulta.getMessage().getValue());
			
		} else {
			
			for (DepartamentoDocflowDto departamento : populaDepartamentosUsuario(responseConsulta.getInfo().getUsuario().getUnidades())) {
				if (departamento.getCodigoUnidade() != null) {
					idsUnidadesUsuario.add(new Long(departamento.getCodigoUnidade()));
				}
			}
		}
		
		usuarioDto.setIdsUnidadesUsuario(idsUnidadesUsuario);
		return usuarioDto;
	}
	
	/**
	 * Retorna informações do protocolo cadastrado no Docflow
	 * Útil para verificar localização, detentor, bem como demais informações relacionadas ao estado do protoocolo no Docflow
	 * @author Monique Santos
	 * @param dto - deve conter parâmetros obrigatórios necessários a consulta de protocolo no Docflow.
	 * @return protocoloDto
	 * */
	public ProtocoloDocflowDto consultarProtocolo(DocflowGenericDto dto) {
		
		dto.setToken(authUtil.auth().getAutenticationData().getAuthToken());
		ResponseConsultaProcessoDocflow responseConsulta = clientConsultaProtocolo.consultarProtocolo(dto);

		ProtocoloDocflowDto protocoloDto = new ProtocoloDocflowDto();
		if(responseConsulta.hasError()){
			
			protocoloDto.setMessageError(responseConsulta.getMessage().getValue());
			
		} else {
			
			protocoloDto = protocoloConverter.toDto(responseConsulta.getData().getProc().getDadosProcesso());
			protocoloDto.setDocumentosDoProcesso(populaDocumentosProcesso(responseConsulta.getData().getProc().getDocs()));
		}
		
		return protocoloDto;
	}
	
	/**
	 * Verifica se determinado usuário está na mesma unidade onde localiza-se o protocolo.
	 * @author Monique Santos
	 * @param dto - deve conter parâmetros obrigatórios necessários a consulta de protocolo e do usuário no Docflow.
	 * @return true / false
	 * */
	public boolean usuarioEstaNaMesmaUnidadeProtocoloTramitado(DocflowGenericDto dto) {
		boolean estaMesmaUnidade = false;
		
		UsuarioDocflowDto usuarioDocflow = consultarUsuario(dto);
		ProtocoloDocflowDto protocoloDocflow = consultarProtocolo(dto); 
		
		for (DepartamentoDocflowDto unidade : usuarioDocflow.getUnidades()) {
			
			if(unidade != null && unidade.getCodigoUnidade() != null && unidade.getCodigoUnidade().equals(protocoloDocflow.getCodigoUnidadeAtual())) {
				estaMesmaUnidade = true;
				break;
			}
		}
		return estaMesmaUnidade;
	}
	
	/**
	 * Verifica se o protocolo do Docflow está na mesma unidade do protocolo no Corporativo
	 * @author Monique Santos
	 * @param dto - deve conter parâmetros obrigatórios necessários a consulta de protocolo no Docflow.
	 * @param idDestinoProtocolo - id da unidade onde está o protocolo no Corporativo.
	 * @return true / false
	 * */
	public boolean movimentoProtocoloPossuiDivergencia(DocflowGenericDto dto, Long idDestinoProtocolo) {
		
		ProtocoloDocflowDto protocoloDocflow = consultarProtocolo(dto);
		
		if(protocoloDocflow.getMessageError() == null ) {
			return !idDestinoProtocolo.equals(Long.parseLong(protocoloDocflow.getCodigoUnidadeAtual())) ? true : false;
		} else {
			return true;
		}
	}

	/**
	 * Verifica se o protocolo está recebido no Docflow
	 * @author Monique Santos
	 * @param dto - deve conter parâmetros obrigatórios necessários a consulta de protocolo no Docflow.
	 * @return true / false
	 * */
	public boolean protocoloEstaRecebidoDocflow(DocflowGenericDto dto) {
		
		ProtocoloDocflowDto protocoloDocflow = consultarProtocolo(dto);
		return protocoloDocflow.getDataRecebido() != null ? true : false; 
	}
	
	/**
	 * Retorna a lista de informações dos documentos que está dentro de um processo
	 * @author Monique Santos
	 * @param listMetadadosDocumentos 
	 * @return list de documentos do processo Docflow
	 * */
	public List<DocumentoDocflowDto> populaDocumentosProcesso(List<DocumentoProcessoDocflow> listMetadadosDocumentos) {
		return !listMetadadosDocumentos.isEmpty() ? documentoConverter.toListDto(listMetadadosDocumentos) : null; 
	}
	
	/**
	 * Retorna a lista de informações dos departamentos de um usuário
	 * @author Monique Santos
	 * @param listMetadadosDepartamentos 
	 * @return list de departamentos usuário Docflow
	 * */
	public List<DepartamentoDocflowDto> populaDepartamentosUsuario(List<DepartamentoDocflow> listMetadadosDepartamentos) {
		return !listMetadadosDepartamentos.isEmpty() ? departamentoConverter.toListDto(listMetadadosDepartamentos) : null; 
	}
	
	public List<DepartamentoDocflowDto> populaDepartamentosSiacolUsuario(List<DepartamentoDocflow> listMetadadosDepartamentos) {
		
		List<DepartamentoDocflowDto> listDepartamentoSiacol = new ArrayList<DepartamentoDocflowDto>();
		
		for (DepartamentoDocflow departamentoDocflow : listMetadadosDepartamentos) {
			DepartamentoDocflowDto departamentoDocflowDto = departamentoConverter.toDto(departamentoDocflow.getDadosUnidade());
			Departamento departamento = departamentoDao.getBy(new Long(departamentoDocflowDto.getCodigoUnidade()));
			
			if ( (departamentoDocflowDto.getCodigoUnidade() != null) && 
					departamento.getModulo() != null && departamento.getModulo().equals("SIACOL")) {
				listDepartamentoSiacol.add(departamentoDocflowDto);
			}
		}		
		return listDepartamentoSiacol.isEmpty() ? null : listDepartamentoSiacol;
		
	}

	public ResponseCadastroDocumentoDocflow uparDocumentoEmProcessoEletronicoAssinado(FormUploadAssinaturaDto fileUploadDto, byte[] bs) {

		try {
			DocflowGenericDto dto = genericDocflowConverter.toDocFlowGenericDtoAssinado(fileUploadDto, bs);			
			return builderCadastro.cadastrarMetadadosDocumento(dto).uploadBinarioDocumentoPdf(dto).associarDocumentoAProcessoExistente(dto).buildCadastroDocumento();
		} catch (Throwable e) {
			// TODO: handle exception
			System.out.println(e);
		}
		return null;
	}
	
	
}
