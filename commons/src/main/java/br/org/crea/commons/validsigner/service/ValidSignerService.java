package br.org.crea.commons.validsigner.service;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.codec.Base64;
import com.itextpdf.text.pdf.security.MakeSignature;
import com.itextpdf.text.pdf.security.MakeSignature.CryptoStandard;

import br.org.crea.commons.builder.documento.ValidaAssinaturaDocumentoBuilder;
import br.org.crea.commons.docflow.dto.DocflowGenericDto;
import br.org.crea.commons.docflow.model.response.ResponseCadastroDocumentoDocflow;
import br.org.crea.commons.docflow.service.DocflowService;
import br.org.crea.commons.factory.AuditaAssinaturaFactory;
import br.org.crea.commons.helper.HelperMessages;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.service.DocumentoService;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.service.commons.ArquivoService;
import br.org.crea.commons.service.redis.RedisService;
import br.org.crea.commons.util.DateUtils;
import br.org.crea.commons.util.FileUtils;
import br.org.crea.commons.util.HashUtil;
import br.org.crea.commons.util.StringUtil;
import br.org.crea.commons.validsigner.client.ExternalSignerWS;
import br.org.crea.commons.validsigner.client.ExternalSignerWS_Service;
import br.org.crea.commons.validsigner.dto.ValidSignDto;
import br.org.crea.commons.validsigner.dto.ValidSignatureDetailsDto;
import br.org.crea.commons.validsigner.enums.TipoDocumentoAssinaturaEnum;
import br.org.crea.commons.validsigner.model.BillingValid;
import br.org.crea.commons.validsigner.model.CmsPackageTypeValidEnum;
import br.org.crea.commons.validsigner.model.SignerParametersValid;
import br.org.crea.commons.validsigner.model.SignerResultValid;
import br.org.crea.commons.validsigner.util.ValidTrustFile;


public class ValidSignerService {
	
	@Inject HttpClientGoApi httpGoApi;
	
	@Inject Properties properties;
	
	@Inject DocumentoService documentoService;
	
	@Inject RedisService redisService;
	
	@Inject ArquivoService arquivoService;
	
	@Inject DocflowService docflowService;
	
	@Inject ValidTrustFile validTrustFile;
	
	@Inject ValidaAssinaturaDocumentoBuilder validaAssinaturaBuilder;
	
	@Inject HelperMessages message;
	
	@Inject AuditaAssinaturaFactory auditaFactory;
	
	private String contractId;
	
	private String userCode;
	
	private String signerUrl;
	
	private ValidSignDto dto;
	
	private ValidSignatureDetailsDto signatureDetailsDto;
	
	private ByteArrayOutputStream baosPreSigner; 
	
	private byte[] hashAssinatura;
	
	private String fieldNameDocumento;
	
	@PostConstruct
	public void before () {
		this.contractId = properties.getProperty("valid.contract.uuid");
		this.userCode = properties.getProperty("valid.user.code");
		this.signerUrl = properties.getProperty("valid.signer.url");
	}

	@PreDestroy
	public void reset () {
		properties.clear();
	}
	
	/**
	 * Valida os parâmetros de entrada necessários para efetuar a pré-assinatura.
	 * Não havendo erro de validação, efetua as demais intruções do pre-sign.
	 * 
	 * Pre Sign: Prepara o processo de assinatura ao autenticar as informações no WS da Valid Certificadora
	 * Os dados volumosos são guardados no Redis e recuperados ao fim do processo de assinatura.
	 * 
	 * A camada de serviço de documentos monta o pdf a ser assinado, recuperando o documento através do id
	 * em cad-documentos.
	 * 
	 * @param signerDto - parametros necessários para validar pre assinatura
	 * @param request
	 * @return dto - retorna com informações necessárias para finalizar assinatura
	 * */
//	public ValidSignDto preparaAssinatura(ValidSignDto signerDto, HttpServletRequest request, UserFrontDto user) {
//		
//		try {
//			
//			dto = validaAssinaturaBuilder.validarPreAssinatura(signerDto).build();
//			
//			if( dto.assinaturaValida() ) {
//				
//				validTrustFile.configuraCanalSSL();
//				dto.setUser(user);
//				dto.setDocumento(tratarBuscaDocumento(request));
//				SignerResultValid resultPreSign = autenticaAssinaturaExterna(parametrizaPreSign());
//				
//				if(resultPreSign.isSuccess()) {
//					
//					dto.setSignaturePackage(Base64.encodeBytes(resultPreSign.getSignaturePackage()));
//					dto.setSignatureAlgortimo("SHA256WithRSA");
//					dto.setAssinaturaValida(true);
//					
//					armazenaHashDocumentoRedis();
//					
//				} else {
//					dto.getMensagemValidacao().add(message.erroServicoExternoAssinatura(resultPreSign.getMensagensErro()));
//					dto.setAssinaturaValida(false);
//				}
//			}
//		} catch (Throwable e) {
//			httpGoApi.geraLog("ValidSignerService || preparaAssinatura", StringUtil.convertObjectToJson(dto), e);
//		}
//		return dto;
//	}
	
	public ValidSignDto preparaAssinatura(ValidSignDto signerDto, HttpServletRequest request, UserFrontDto user) {
		try {
			
			System.out.println("PREPARA ASSINATURA");
			// Configura canal SSL
			//validTrustFile.configuraCanalSSL();
			final URL url = new URL(signerUrl);
			validTrustFile.configurarSSL(url);

			// Transforma o certificado no formato PEM em um X509Certificate
			X509Certificate x509SignerCertificate = validTrustFile.getX509SignerCertificate(signerDto.getSignerCertificate());
			
			// Gera um UUID para o PDF
			fieldNameDocumento = UUID.randomUUID().toString();

			// Carrega o arquivo PDF
			PdfReader pdfReader = new PdfReader(signerDto.getDocumento());

			// Cria um selo (não visível) de assinatura digital
			baosPreSigner = new ByteArrayOutputStream();
			PdfStamper stamper = null;
			try {
				stamper = PdfStamper.createSignature(pdfReader, baosPreSigner, '\0');
			} catch (com.itextpdf.text.DocumentException d) {
				System.out.println(d);
			} catch (java.io.IOException io) {
				System.out.println(io);
			}
			
			stamper.getSignatureAppearance().setVisibleSignature(new Rectangle(0, 0, 0, 0), 1, fieldNameDocumento);
			// Recupera buffer que deverá ser assinado
			byte[] dataTobeSignedOut = MakeSignature.getDataTobeSigned(stamper.getSignatureAppearance(), (int) (signerDto.getDocumento().length * 2), CryptoStandard.CMS,
					x509SignerCertificate);
			
			// Calcula o hash sobre o buffer a ser assinado (usando SHA-256)
			hashAssinatura = validTrustFile.calcHash(dataTobeSignedOut);

			// Configura informações para bilhetagem
			BillingValid billingInfoReq = new BillingValid();
			// Número do contrato
			billingInfoReq.setContractUuid(contractId.trim());
			
			// Código de identificação do cliente
			billingInfoReq.setUserCode(userCode.trim());

			// Parametros para preparação do pacote que será assinado 
			SignerParametersValid signerParameters = new SignerParametersValid();
			
			// Bilhetagem
			signerParameters.setBillingInfoReq(billingInfoReq);
			
			// Tipo da política (AD-RB)
			signerParameters.setCmsPackageType(CmsPackageTypeValidEnum.P_AD_ES_AD_RB);

			// Valor do hash do dado a ser assinado
			signerParameters.setHashData(hashAssinatura);
			
			// Algoritmo de hash utilizado
			signerParameters.setMdAlg("SHA-256");
			
			// Data vazio (PDF sempre é no formato detached)
			signerParameters.setData(new byte[0]);
			
			// Certificado digital do assinante
			signerParameters.setX509SigningCertificate(x509SignerCertificate.getEncoded());

			// URL do serviço
			//URL url = new URL(PropertiesFile.getKeyValue(PropertiesFile.VSIGNER_EXT_SIGN_URL_KEY));
			ExternalSignerWS_Service externalSignerWS_Service = new ExternalSignerWS_Service(url);
			ExternalSignerWS externalSignerWS = externalSignerWS_Service.getExternalSignerWSPort();
			SignerResultValid resultPreSign = externalSignerWS.externalPreSignCMS(signerParameters);

			if(resultPreSign.isSuccess()) {
				dto = new ValidSignDto();
				dto.setSignaturePackage(Base64.encodeBytes(resultPreSign.getSignaturePackage()));
				dto.setSignatureAlgortimo("SHA256WithRSA");
				dto.setAssinaturaValida(true);
				
				armazenaHashDocumentoRedis();
				
//			} else {
///				dto.getMensagemValidacao().add(message.erroServicoExternoAssinatura(resultPreSign.getErrorList().toString()));
//				dto.setAssinaturaValida(false);
			}
			
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			
		}
		return dto;
		
	}

	/**
	 * Com base na informação do tipo de documento para assinatura informado na requisição, busca o documento em cad-documento (json)
	 * ou no file system (cad-arquivo). O documento poderá ainda vim a partir de um upload do cliente.
	 * 
	 * @param request - util para recuperar contexto e montar o pdf a partir do json salvo em cad-documento
	 * @return documento - documento em forma de array de bytes
	 * */
	public byte[] tratarBuscaDocumento(HttpServletRequest request) {
	
		try {
			
			switch (dto.getTipoDocumentoAssinatura()) {
			case DOCUMENTO_JSON:
				return documentoService.montaDocumentoPdf(request, dto.getIdDocumento());
			case ARQUIVO_FILE_SYSTEM:	
				return arquivoService.getBytesArquivo(dto.getIdDocumento());
			case ARQUIVO_UPLOAD:	
				return dto.getDocumento();
			}
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ValidSignerService || tratarBuscaDocumento", StringUtil.convertObjectToJson(dto), e);
		}
		return null;
	}
	
	/**
	 * Finaliza o processo de assinatura e gera o documento assinado
	 * @param signerDto - traz parametros necessarios para commitar a assinatura
	 * @return dto
	 * */
//	public ValidSignDto finalizaAssinatura(ValidSignDto signerDto, UserFrontDto user) {
//		
//		try {
//			
//			dto = validaAssinaturaBuilder.validarCommitAssinatura(signerDto).build();
//
//			if( dto.assinaturaValida() ) {
//				
//				dto.setUser(user);
//				getHashDocumentoRedis();
//				liberaHashDocumentoRedis();
//				
//				validTrustFile.configuraCanalSSL();
//				SignerResultValid resultCommitAssinatura = autenticaAssinaturaExterna(parametrizaCommitDaAssinatura());
//				
//				if( resultCommitAssinatura.isSuccess() ) {
//					
//					dto.setDocumento(geraDocumentoFinalAssinado(resultCommitAssinatura.getSignaturePackage()));
//					ResponseCadastroDocumentoDocflow retornoDocflow = docflowService.cadastrarDocumentoSemProcesso(populaDadosDocumentoDocflow());
//					gravaReferenciaDocflowDocumentoAssinado(retornoDocflow);
//					
//				} else {
//					dto.setAssinaturaValida(false);
//					dto.getMensagemValidacao().add(message.erroServicoExternoAssinatura(resultCommitAssinatura.getMensagensErro()));
//				}
//				
//			} else {
//				
//				dto.setAssinaturaValida(false);
//			}
//			
//		} catch (Exception e) {
//			httpGoApi.geraLog("ValidSignerService || finalizaAssinatura", StringUtil.convertObjectToJson(signerDto), e);
//		}
//		return dto;
//	}
	
	public ValidSignDto finalizaAssinatura(ValidSignDto signerDto, UserFrontDto user) {
		try {
			// Configura canal SSL
//			Crypt.configureSSLChannel();
			final URL url = new URL(signerUrl);
			validTrustFile.configurarSSL(url);
			dto = signerDto;
			
			dto.setUser(user);
			getHashDocumentoRedis();
			liberaHashDocumentoRedis();

			// Transforma o certificado no formato PEM em um X509Certificate
			X509Certificate x509SignerCertificate = validTrustFile.getX509SignerCertificate(signerDto.getSignerCertificate());

			// Configura informações para bilhetagem
			BillingValid billingInfoReq = new BillingValid();

			// Número do contrato
			billingInfoReq.setContractUuid(contractId);

			// Código de identificação do cliente
			billingInfoReq.setUserCode(userCode);

			// Parametros para finalização do pacote assinado
			SignerParametersValid signerParameters = new SignerParametersValid();

			// Bilhetagem
			signerParameters.setBillingInfoReq(billingInfoReq);

			// Tipo da política (AD-RB) - Deve ser igual ao informado na chamada
			// do serviço externalPreSignCMS
			signerParameters.setCmsPackageType(CmsPackageTypeValidEnum.P_AD_ES_AD_RB);

			// Valor do hash do dado a ser assinado - Deve ser igual ao
			// informado na chamada do serviço externalPreSignCMS
			signerParameters.setHashData(Base64.decode(signatureDetailsDto.getHash()));

			// Algoritmo de hash utilizado - Deve ser igual ao informado na
			// chamada do serviço externalPreSignCMS
			signerParameters.setMdAlg("SHA-256");

			// Data vazio (PDF sempre é no formato detached)
			signerParameters.setData(new byte[0]);

			// Certificado digital do assinante - Deve ser igual ao informado na
			// chamada do serviço externalPreSignCMS
			signerParameters.setX509SigningCertificate(x509SignerCertificate.getEncoded());

			// Assinatura digital do cliente
			signerParameters.setSignedData(Base64.decode(dto.getSignature()));

			// Pacote que foi assinado (gerado pelo serviço externalPreSignCMS)
			signerParameters.setSignedAttributes(Base64.decode(dto.getSignaturePackage()));

			// URL do serviço
			ExternalSignerWS_Service externalSignerWS_Service = new ExternalSignerWS_Service(url);
			ExternalSignerWS externalSignerWS = externalSignerWS_Service.getExternalSignerWSPort();
			SignerResultValid sr = externalSignerWS.externalPosSignCMS(signerParameters);
						
			// Verifica status de retorno
			if (!sr.isSuccess()) {
				System.out.println(sr.getErrorList().get(0).getErrorMessage());
			} else {
				// Em caso de sucesso gera arquivo final assinado e salva em
				// disco
				PdfReader pdfReader = new PdfReader(FileUtils.getByteArrayOutputStreamFromBase64(signatureDetailsDto.getBaosPdf()).toByteArray());
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				MakeSignature.signDeferredVALID(pdfReader, signatureDetailsDto.getUuid(), os, sr.getSignaturePackage());
				
				dto.setAssinaturaValida(true);
				dto.setDocumento(os.toByteArray());
			}
		} catch (Exception e) {
			e.getMessage();
			dto.setAssinaturaValida(false);
		}
		return dto;

	}

	/**
	 * Parametriza assinatura digital no início do processo, inserindo dados para gerar bilhete,
	 * certificado digital do assinante e tipo de algoritmo de encriptação utilizado.
	 * @return signerParameter
	 * */
	private SignerParametersValid parametrizaPreSign() {
		
		try {
			
			SignerParametersValid signerParameter = new SignerParametersValid();
			signerParameter.setBillingInfoReq(configuraBilhete());
			signerParameter.setCmsPackageType(CmsPackageTypeValidEnum.C_AD_ES_AD_RB);
			signerParameter.setMdAlg("SHA-256");
			signerParameter.setData(new byte[0]);
			signerParameter.setX509SigningCertificate(HashUtil.getCertificateDigital(dto.getSignerCertificate()).getEncoded());
			
			hashAssinatura = HashUtil.calcHashAssinatura(getBufferAssinatura());
			signerParameter.setHashData(hashAssinatura);
			return signerParameter;
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ValidSignerService || parametrizaPreSign", StringUtil.convertObjectToJson(dto), e);
		}
		return null;
	}
	
	/**
	 * Parametriza assinatura digital no fim do processo, inserindo dados para gerar bilhete,
	 * certificado digital do assinante, tipo de algoritmo de encriptação utilizado, signaturePackage 
	 * retornado pelo WS da Valid (externalPreSignCMS) e hash da assinatura calculado no início do processo.
	 * O hash calculado está sendo recuperado da base do Redis.
	 * @return signerParameter
	 * */
	private SignerParametersValid parametrizaCommitDaAssinatura() {
		
		try {
			
			SignerParametersValid signerParameter = new SignerParametersValid();
			signerParameter.setBillingInfoReq(configuraBilhete());
			signerParameter.setCmsPackageType(CmsPackageTypeValidEnum.C_AD_ES_AD_RB);
			signerParameter.setMdAlg("SHA-256");
			signerParameter.setData(new byte[0]);
			signerParameter.setX509SigningCertificate(HashUtil.getCertificateDigital(dto.getSignerCertificate()).getEncoded());
			signerParameter.setHashData(Base64.decode(signatureDetailsDto.getHash()));
			signerParameter.setSignedData(Base64.decode(dto.getSignature()));
			signerParameter.setSignedAttributes(Base64.decode(dto.getSignaturePackage()));
			
			return signerParameter;
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ValidSignerService || parametrizaCommitDaAssinatura", StringUtil.convertObjectToJson(dto), e);
		}
		return null;
	}
	
	/**
	 * Configura informações para bilhetagem da certificação digital
	 * @return billingValid
	 * */
	private BillingValid configuraBilhete() {
		
		BillingValid billingValid = new BillingValid();
		billingValid.setContractUuid(contractId);
		billingValid.setUserCode(userCode);
		return billingValid;
		
	}
	
//	/**
//	 * Envia parametros da assinatura para Web Service da Valid Certificadora
//	 * Este serviço deve ser requisitado em dois momentos: Na preparação do pacote de assinatura
//	 * e no momento que a assinatura for commitada.
//	 * @param dto
//	 * @return signerResult	
//	 * */
//	private SignerResultValid autenticaAssinaturaExterna(SignerParametersValid signerParameter) {
//		
//		try {
//			
//			URL url = new URL(signerUrl);
//			ExternalSignerWS_Service externalSignerWSService = new ExternalSignerWS_Service(url);
//			ExternalSignerWS externalSignerWS = externalSignerWSService.getExternalSignerWSPort();
//			SignerResultValid signerResult = externalSignerWS.externalPreSignCMS(signerParameter);
//			return signerResult; 
//			
//		} catch (Exception e) {
//			httpGoApi.geraLog("ValidSignerService || getExternalSignerWS", StringUtil.convertObjectToJson(signerParameter), e);
//		}
//		
//		return null;
//	}
	
	/**
	 * Cria um selo não visível de assinatura digital a ser inputado no documento
	 * que será assinado.
	 * @param documento - documento a ser assinado
	 * @return stamper - selo de assinatura criado
	 * */
	private PdfStamper criaSeloAssinatura(byte[] documento) {
		
		try {
			
			PdfReader pdfReader = new PdfReader(documento);
			fieldNameDocumento = UUID.randomUUID().toString();
			baosPreSigner = new ByteArrayOutputStream();
			PdfStamper stamper = PdfStamper.createSignature(pdfReader, baosPreSigner, '\0');
			stamper.getSignatureAppearance().setVisibleSignature(new Rectangle(0, 0, 0, 0), 1, fieldNameDocumento);
			
			return stamper;
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ValidSignerService || criaSeloAssinatura", StringUtil.convertObjectToJson(documento), e);

		} 
		return null;
	}
	
	/**
	 * Recupera o buffer do documento que deverá ser assinado
	 * @return
	 * */
	private byte[] getBufferAssinatura() {
		
		try {
			
			PdfStamper stamper = criaSeloAssinatura(dto.getDocumento());
			
			byte[] dataSignedOut = MakeSignature.getDataTobeSigned(stamper.getSignatureAppearance(), 
					(int) (dto.getDocumento().length * 2), CryptoStandard.CMS,HashUtil.getCertificateDigital(dto.getSignerCertificate()));
			
			return dataSignedOut;
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ValidSignerService || getBufferSeloAssinatura", StringUtil.convertObjectToJson(dto), e);
		}
		return null;
	}
	
	/**
	 * Gera documento final assinado com base no selo de assinatura que foi criado no pre-sign
	 * @param signaturePackage - retornado pelo serviço externalPreSignCMS
	 * @return outPdf - Documento final assinado
	 * */
	private byte[] geraDocumentoFinalAssinado(byte[] signaturePackage) {
		ByteArrayOutputStream outPdf = new ByteArrayOutputStream();
		
		try {
			ByteArrayOutputStream baosPdf = FileUtils.getByteArrayOutputStreamFromBase64(signatureDetailsDto.getBaosPdf());
			PdfReader pdfReader = new PdfReader(baosPdf.toByteArray());
			MakeSignature.signDeferredVALID(pdfReader, signatureDetailsDto.getUuid(), outPdf, signaturePackage);
			
		} catch (Exception e) {
			httpGoApi.geraLog("ValidSignerService || geraDocumentoFinalAssinado", StringUtil.convertObjectToJson(dto), e);
		}
		return outPdf.toByteArray();
	}
	

	/**
	 * Armazena na base do redis o documento e o hash que foi gerado no pre-sign do serviço da Valid.
	 * A chave do redis para recuperar os dados do documento que está sendo assinado, está sendo salva
	 * em cad-documento para evitar que o valor se perca entre as requisições do pre-sign e da finalização
	 * da assinatura.
	 * 
	 * Como o fluxo prevê que todo documento assinado seja salvo no Docflow, obrigatoriamente todos documentos estarão salvos em cad-documento
	 * para que a referência externa (Docflow) seja salva no fim do processo.
	 * */
	private void armazenaHashDocumentoRedis() {
		
		try {
			
			//boolean documentoParaAssinaturaExiste = documentoService.documentoParaAssinaturaExiste(dto.getIdDocumento(), dto.getTipoDocumentoAssinatura());
			
			String keyRedis = StringUtil.randomUUID();
			dto.setChaveAssinaturaRedis(keyRedis);
			
			this.redisService.setValue(keyRedis + "-uuid", fieldNameDocumento);
			this.redisService.setValue(keyRedis + "-baosPDF", FileUtils.getBase64FromByteArrayOutputStream(baosPreSigner));
			this.redisService.setValue(keyRedis + "-hash", Base64.encodeBytes(hashAssinatura));
			
//			if(documentoParaAssinaturaExiste) {
//				
//				documentoService.atualizaChaveAssinaturaRedisNoDocumento(dto.getIdDocumento(), keyRedis);
//				
//			} else {
//				
//				DocumentoGenericDto documentoDto = new DocumentoGenericDto();
//				documentoDto.setModulo(dto.getModuloSistema());
//				documentoDto.setDataCriacao(new Date());
//				documentoDto.setDataAtualizacao(new Date());
//				documentoDto.setObservacao("Documento salvo via upload para assinatura");
//				documentoDto.setChaveAssinaturaRedis(keyRedis);
//				DocumentoGenericDto documento = documentoService.salvaDocumento(documentoDto, dto.getUser());
//				dto.setIdDocumento(documento.getId());
//			}
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ValidSignerService || armazenaHashDocumentoRedis", StringUtil.convertObjectToJson(dto), e);
		}
	}
	
	/**
	 * Recupera da base do redis o documento e o hash que foi gerado no pre-sign do serviço da Valid e 
	 * @param signerDto - traz a impressão digital que é a chave para recuperar o documento da base do Redis.
	 * */
	private void getHashDocumentoRedis() {
		
		try {
			
			signatureDetailsDto = new ValidSignatureDetailsDto();
			signatureDetailsDto.setUuid(this.redisService.getValue(dto.getChaveAssinaturaRedis() + "-uuid"));
			signatureDetailsDto.setBaosPdf(this.redisService.getValue(dto.getChaveAssinaturaRedis() + "-baosPDF"));
			signatureDetailsDto.setHash(this.redisService.getValue(dto.getChaveAssinaturaRedis() + "-hash"));
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ValidSignerService || getHashDocumentoRedis", StringUtil.convertObjectToJson(dto), e);
		}
	}
	
	/**
	 * Exclui da base do redis o documento e o hash que foi gerado no pre-sign do serviço da Valid
	 * */
	private void liberaHashDocumentoRedis() {
		
		try {
		
			this.redisService.delValue(dto.getChaveAssinaturaRedis() + "-uuid");
			this.redisService.delValue(dto.getChaveAssinaturaRedis() + "-baosPDF");
			this.redisService.delValue(dto.getChaveAssinaturaRedis() + "-hash");
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ValidSignerService || liberaChavesDocumentoRedis", StringUtil.convertObjectToJson(dto), e);
		}
	}
	
	
	private DocflowGenericDto populaDadosDocumentoDocflow() {
		DocflowGenericDto genericDto = new DocflowGenericDto();
		
		genericDto.setObservacaoDoDocumento(dto.getObservacaoDocumento());
		genericDto.setAssunto(dto.getAssuntoDocumento());
		genericDto.setDataArquivo(DateUtils.format(new Date(), DateUtils.DD_MM_YYYY_HH_MM_SS_SSS));
		genericDto.setInputStreamArquivoPdf(FileUtils.converteByteArrayParaInputStream(dto.getDocumento()));
		genericDto.setUnidadeDestino(dto.getUnidadeDestino());
		genericDto.setUnidadeOrigem(dto.getUnidadeDestino());
		genericDto.setCodigoDepartamento(dto.getUnidadeDestino());
		genericDto.setInteressado(dto.getInteressadoDocumento());
		genericDto.setMatricula(dto.getUser().getMatricula().toString());
		genericDto.setNomeArquivoPdf("documento.pdf");
		genericDto.setTipoDocumento(dto.getCodigoTipoDocumento());
		return genericDto;
	}
	
	private void gravaReferenciaDocflowDocumentoAssinado(ResponseCadastroDocumentoDocflow retornoDocflow) {
	
		if( !retornoDocflow.hasError() ) {
			
			String idDocflow = retornoDocflow.getData().getDoc().getId();
			String protocoloDocflow = retornoDocflow.getData().getDoc().getProtocolo();
			if(TipoDocumentoAssinaturaEnum.DOCUMENTO_JSON.equals(dto.getTipoDocumentoAssinatura())){
				documentoService.atualizaReferenciaDoDocflowNoDocumento(dto.getIdDocumento(), idDocflow, protocoloDocflow);
			}	
			dto.setAssinaturaValida(true);
			
			String numeroDocumentoEletronico = retornoDocflow.getData().getDoc().getProtocolo();
			auditaFactory.auditaAssinaturaDigitalDocumento(dto, message.documentoAssinado(numeroDocumentoEletronico), false);
			
		} else {
			dto.getMensagemValidacao().add(retornoDocflow.getMessage().getValue());
		}
	}
}

