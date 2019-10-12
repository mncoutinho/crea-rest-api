package br.org.crea.commons.service.commons;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.cadastro.domains.EmailConverter;
import br.org.crea.commons.converter.cadastro.pessoa.EmailPessoaConverter;
import br.org.crea.commons.converter.commons.EmailEnvioConverter;
import br.org.crea.commons.dao.EmailEnvioDao;
import br.org.crea.commons.dao.cadastro.EmailDao;
import br.org.crea.commons.dao.cadastro.pessoa.PessoaDao;
import br.org.crea.commons.dao.siacol.RlEmailReuniaoSiacolDao;
import br.org.crea.commons.docflow.service.DocflowService;
import br.org.crea.commons.helper.HelperMessages;
import br.org.crea.commons.models.art.ContratoArt;
import br.org.crea.commons.models.cadastro.dtos.EmailDto;
import br.org.crea.commons.models.cadastro.dtos.RlEmailReuniaoSiacolDto;
import br.org.crea.commons.models.commons.DestinatarioEmailDto;
import br.org.crea.commons.models.commons.EmailEnvio;
import br.org.crea.commons.models.commons.dtos.ArquivoFormUploadDto;
import br.org.crea.commons.models.commons.dtos.EmailEnvioDto;
import br.org.crea.commons.models.commons.dtos.GenericDto;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.models.corporativo.dtos.PessoaDto;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;
import br.org.crea.commons.models.siacol.ReuniaoSiacol;
import br.org.crea.commons.models.siacol.RlEmailReuniaoSiacol;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.service.PessoaService;
import br.org.crea.commons.util.EmailEvent;
import br.org.crea.commons.util.StringUtil;

public class EmailEnvioService {

	@Inject	EmailEvent evento;
	@Inject	EmailConverter converter;
	@Inject	EmailDao dao;
	@Inject	RlEmailReuniaoSiacolDao rlEmailReuniaoSiacolDao;
	@Inject	PessoaDao pessoaDao;
    @Inject HelperMessages messages;
	@Inject	HttpClientGoApi httpGoApi;
	@Inject	EmailEnvioDao emailEnvioDao;
	@Inject	EmailEnvioConverter emailEnvioConverter;
	@Inject	EmailPessoaConverter emailPessoaConverter;
	@Inject	ArquivoService arquivoService;
	@Inject	DocflowService docFlowService;
	@Inject	PessoaService pessoaService;
	
	private String emailAtendimento = "atendimento@crea-rj.org.br";

	public EmailEnvioDto getBy(Long idEmail) {
		return emailEnvioConverter.toDto(emailEnvioDao.getBy(idEmail));
	}

	public EmailEnvioDto salva(EmailEnvioDto dto) {
		dto.setDataUltimoEnvio(new Date());
		return emailEnvioConverter.toDto(emailEnvioDao.create(emailEnvioConverter.toModel(dto)));
	}

	public EmailEnvioDto atualiza(EmailEnvioDto dto) {
		return emailEnvioConverter.toDto(emailEnvioDao.update(emailEnvioConverter.toModel(dto)));
	}

	public void deleta(Long idEmail) {
		emailEnvioDao.deleta(idEmail);
	}

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
			// TODO: handle exception
		}

		return dto;
	}

	public void enviarEmailSenha(Pessoa pessoa, String senha, boolean enviarResponsavel) {

		EmailEnvioDto email = new EmailEnvioDto();
		List<DestinatarioEmailDto> listaEmailsResponsaveis = new ArrayList<DestinatarioEmailDto>();
		List<DestinatarioEmailDto> listaEmailsDestinatarios = montarListaDestinatarios(pessoa);

		if (listaEmailsDestinatarios.size() > 0) {
			email.setMensagem(messages.corpoEmailEnvioSenha(senha));
			email.setAssunto("Envio de Nova Senha");
			email.setDestinatarios(listaEmailsDestinatarios);
		}

		if (enviarResponsavel && pessoa.getIdPessoaResponsavel() != null) {

			Pessoa responsavel = pessoaDao.getPessoa(pessoa.getIdPessoaResponsavel());

			listaEmailsResponsaveis = montarListaDestinatarios(responsavel);

			if (!listaEmailsResponsaveis.isEmpty()) {
				if (!listaEmailsDestinatarios.isEmpty()) {
					email.setDestinatariosCC(listaEmailsResponsaveis);
				} else {
					email.setDestinatarios(listaEmailsResponsaveis);
				}
			}
		}

		if (!listaEmailsDestinatarios.isEmpty() || !listaEmailsResponsaveis.isEmpty()) {
			email.setEmissor("atendimento@crea-rj.org.br");
			email.setDataUltimoEnvio(new Date());

			envia(email);
		}
	}

	public List<DestinatarioEmailDto> montarListaDestinatarios(Pessoa pessoa) {
		List<DestinatarioEmailDto> listaEmails = new ArrayList<DestinatarioEmailDto>();

		String emailPessoa = dao.getUltimoEmailCadastradoPor(pessoa.getId());

		if (emailPessoa != null) {

			DestinatarioEmailDto destinatario = new DestinatarioEmailDto();
			destinatario.setEmail(emailPessoa);
			destinatario.setNome(populaNomePessoa(pessoa.getId()));

			listaEmails.add(destinatario);
		}

		return listaEmails;
	}

	public String populaNomePessoa(Long idPessoa) {
		PessoaDto pessoa = pessoaService.getInteressadoBy(idPessoa);
		return pessoa.getNome();
	}

	public List<DestinatarioEmailDto> montarListaDestinatarios(String email, String nome) {
		List<DestinatarioEmailDto> listaEmails = new ArrayList<DestinatarioEmailDto>();

		DestinatarioEmailDto destinatario = new DestinatarioEmailDto();
		destinatario.setEmail(email);
		destinatario.setNome(nome);

		listaEmails.add(destinatario);

		return listaEmails;
	}

	public boolean existeEmailCadastrado(EmailDto emailDto) {
		return dao.existeEmailCadastrado(emailDto);
	}

	public EmailEnvioDto populaEmail(List<DestinatarioEmailDto> listaDestinatario, String emissor, String assunto,
			String mensagem) {

		EmailEnvioDto email = new EmailEnvioDto();

		email.setEmissor(emissor);
		email.setDataUltimoEnvio(new Date());
		email.setDestinatarios(listaDestinatario);

		email.setAssunto(assunto);
		email.setMensagem(mensagem);

		return email;
	}

	public List<EmailDto> getListEnderecoDeEmailPor(Long idPessoa) {
		return converter.toListEmailDto(dao.getListEnderecoDeEmailPor(idPessoa));
	}

	public RlEmailReuniaoSiacolDto getTemplateReuniaoBy(GenericDto dto) {
		return converter.toRlEmailReuniaoDto(rlEmailReuniaoSiacolDao.getEmailsPor(dto.getIdReuniao(), dto.getIdTipo()));
	}

	public RlEmailReuniaoSiacol salvaRlTemplateReuniao(GenericDto dto) {
		RlEmailReuniaoSiacol rlEmailReuniaoSiacol = new RlEmailReuniaoSiacol();
		ReuniaoSiacol reuniaoSiacol = new ReuniaoSiacol();
		reuniaoSiacol.setId(dto.getIdReuniao());
		EmailEnvio emailEnvio = new EmailEnvio();
		emailEnvio.setId(dto.getIdConfiguracao());

		rlEmailReuniaoSiacol.setReuniao(reuniaoSiacol);
		rlEmailReuniaoSiacol.setEmailEnvio(emailEnvio);

		return rlEmailReuniaoSiacolDao.create(rlEmailReuniaoSiacol);
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

	public boolean validaEmail(EmailDto dto) {
		return !existeEmailCadastrado(dto) && StringUtil.ehEmail(dto.getDescricao());
	}

	public void criarEmail(EmailDto dto) {
		dao.create(emailPessoaConverter.toModel(dto));
	}

	public String getUltimoEmailCadastrado(Long idPessoa) {
		return dao.getUltimoEmailCadastradoPor(idPessoa);
	}
	
	public void enviarEmailPadrao(Long idPessoa, String assunto, String mensagem) {

		EmailEnvioDto email = new EmailEnvioDto();
		Pessoa pessoa = new Pessoa();
		pessoa.setId(idPessoa);
		List<DestinatarioEmailDto> listaEmailsDestinatarios = montarListaDestinatarios(pessoa);

		if (listaEmailsDestinatarios.size() > 0) {
			email.setEmissor(emailAtendimento);
			email.setDataUltimoEnvio(new Date());
			email.setAssunto(assunto);
			email.setMensagem(mensagem);
			
			email.setDestinatarios(listaEmailsDestinatarios);
			envia(email);
		}

	}

	public void enviarEmailTaxaDeIncorporacao(Pessoa pessoa, String numeroArt) {
		
		enviarEmailPadrao(pessoa.getId(), "Taxa de Incorporação", messages.corpoEmailEnvioTaxaDeIncorporacao(populaNomePessoa(pessoa.getId()), numeroArt));

	}

	public void enviarEmailCadastroDeArtPorEmpresa(ContratoArt contrato) {

		enviarEmailPadrao(contrato.getArt().getProfissional().getId(), "CREA-RJ - Cadastro de ART", messages.corpoEmailEnvioCadastroDeArtPorEmpresa(contrato));
	}

	public void enviarEmailCadastroOuvidoriaAtendimento(Long idPessoa, Long idOuvidoria) {

		
		enviarEmailPadrao(idPessoa, "CREA-RJ - Cadastro de Atendimento Ouvidoria", messages.corpoEmailEnvioCadastroAtendimentoOuvidoria(idOuvidoria));
	}

}
