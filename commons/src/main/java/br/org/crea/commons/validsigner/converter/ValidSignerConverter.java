
package br.org.crea.commons.validsigner.converter;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;

import br.org.crea.commons.dao.DocumentoDao;
import br.org.crea.commons.models.cadastro.Documento;
import br.org.crea.commons.models.cadastro.dtos.DocumentoDto;
import br.org.crea.commons.service.ReportService;
import br.org.crea.commons.util.FileUtils;
import br.org.crea.commons.validsigner.dto.FormUploadAssinaturaDto;
import br.org.crea.commons.validsigner.dto.ValidSignDto;
public class ValidSignerConverter {
	
	@Inject ReportService reportService;
	@Inject DocumentoDao documentoDao;
	
	public ValidSignDto toDto(FormUploadAssinaturaDto formFileDto, HttpServletRequest request) {
		ValidSignDto dto = new ValidSignDto();
		
		dto.setThumbprint(formFileDto.getThumbprint());
		dto.setSignature(formFileDto.getSignature());
		dto.setSignatureAlgortimo(formFileDto.getSignatureAlgortimo());
		dto.setSignaturePackage(formFileDto.getSignaturePackage());
		dto.setSignerCertificate(formFileDto.getSignerCertificate());
		dto.setTipoDocumento(formFileDto.getTipoDocumento());
		dto.setAssinaturaValida(formFileDto.isValid());
		if (formFileDto.getFile() != null ) {
			dto.setDocumento(formFileDto.getFile() != null ? FileUtils.converteInputStreamParaByteArray(formFileDto.getFile()) : null);
		} else if (formFileDto.getIdDocumento() != null) {
			try {
				Documento documento = documentoDao.getBy(formFileDto.getIdDocumento());
				Gson gson = new Gson();
				DocumentoDto rascunho = gson.fromJson(documento.getDocumento(), DocumentoDto.class);
				
				dto.setDocumento(FileUtils.converteInputStreamParaByteArray(reportService.getDocumentoInputStream(request, rascunho)));
			} catch (Throwable e) {
				dto.setDocumento(null);
			}
		}
		dto.setAssuntoDocumento(formFileDto.getAssuntoDocumento());
		dto.setInteressadoDocumento(formFileDto.getInteressadoDocumento());
		dto.setModuloSistema(formFileDto.getModuloSistema());
		dto.setUnidadeDestino(formFileDto.getUnidadeDestino());
		dto.setChaveAssinaturaRedis(formFileDto.getChaveAssinaturaRedis());
		
		System.out.println("DTO CONVERTIDO");
		return dto;
	}
}