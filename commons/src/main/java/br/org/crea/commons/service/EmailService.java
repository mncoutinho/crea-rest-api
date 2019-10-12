package br.org.crea.commons.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.cadastro.domains.EmailConverter;
import br.org.crea.commons.converter.cadastro.pessoa.EmailPessoaConverter;
import br.org.crea.commons.converter.cadastro.pessoa.PessoaConverter;
import br.org.crea.commons.converter.commons.EmailEnvioConverter;
import br.org.crea.commons.dao.EmailEnvioDao;
import br.org.crea.commons.dao.cadastro.EmailDao;
import br.org.crea.commons.dao.cadastro.pessoa.PessoaDao;
import br.org.crea.commons.docflow.service.DocflowService;
import br.org.crea.commons.helper.HelperMessages;
import br.org.crea.commons.models.cadastro.dtos.EmailDto;
import br.org.crea.commons.models.commons.dtos.ArquivoFormUploadDto;
import br.org.crea.commons.models.commons.dtos.EmailEnvioDto;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.service.commons.ArquivoService;
import br.org.crea.commons.util.EmailEvent;
import br.org.crea.commons.util.StringUtil;

public class EmailService {

	@Inject	EmailEvent evento;
	@Inject	EmailConverter converter;
	@Inject	EmailDao dao;
	@Inject	PessoaDao pessoaDao;
	@Inject PessoaConverter pessoaConverter;
    @Inject HelperMessages messages;
	@Inject	HttpClientGoApi httpGoApi;
	@Inject	EmailEnvioDao emailEnvioDao;
	@Inject	EmailEnvioConverter emailEnvioConverter;
	@Inject	EmailPessoaConverter emailPessoaConverter;
	@Inject	ArquivoService arquivoService;
	@Inject	DocflowService docFlowService;
	@Inject	PessoaService pessoaService;

	public boolean envia(EmailEnvioDto email) {

		try {
			List<ArquivoFormUploadDto> anexos = new ArrayList<ArquivoFormUploadDto>();
			if (email.temIdsArquivos()) {
				email.getIdsCadArquivo().forEach(id -> {
					anexos.add(populaAnexo(arquivoService.download(id)));
				});
			}

			if (email.temIdsDocFlow()) {
				email.getIdsDocFlow().forEach(id -> {
					anexos.add(populaAnexo(docFlowService.recuperaArquivo(id, email.getIdPessoa())));
				});
			}

			if (!anexos.isEmpty()) {
				email.anexos = anexos;
			}

		} catch (Throwable e) {
			httpGoApi.geraLog("EmailService || envia", StringUtil.convertObjectToJson(email), e);
		} finally {
			try {
				evento.consumeEvent(email);
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
				return false;
			}
		}

		return true;
	}

	private ArquivoFormUploadDto populaAnexo(File file) {
		ArquivoFormUploadDto dto = new ArquivoFormUploadDto();
		dto.setDescricao(file.getName());
		dto.setFileName(file.getName());
		dto.setPath(file.getAbsolutePath());

		try {
			InputStream input = new FileInputStream(new File(file.getPath()));
			dto.setFile(input);

		} catch (Exception e) {
			httpGoApi.geraLog("EmailService || populaAnexo", StringUtil.convertObjectToJson(file.getName()), e);
		}

		return dto;
	}

	public boolean existeEmailCadastrado(EmailDto emailDto) {
		return dao.existeEmailCadastrado(emailDto);
	}

	public List<EmailDto> getListEnderecoDeEmailPor(Long idPessoa) {
		return converter.toListEmailDto(dao.getListEnderecoDeEmailPor(idPessoa));
	}

	public EmailDto atualizarEmail(EmailDto dto, UserFrontDto userFrontDto) {
		String email = dao.getUltimoEmailCadastradoPor(dto.getIdPessoa());
		if (email != null) {
			return dao.atualizaEmailPessoa(dto, userFrontDto);
		} else {
			criarEmail(dto);
			return dto;
		}
	}

	public void criarEmail(EmailDto dto) {
		dao.create(emailPessoaConverter.toModel(dto));
	}

	public String getUltimoEmailCadastrado(Long idPessoa) {
		return dao.getUltimoEmailCadastradoPor(idPessoa);
	}


}
