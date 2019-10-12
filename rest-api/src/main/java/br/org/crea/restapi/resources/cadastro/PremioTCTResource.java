package br.org.crea.restapi.resources.cadastro;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.org.crea.commons.annotations.Publico;
import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.models.cadastro.dtos.EmailDto;
import br.org.crea.commons.models.cadastro.dtos.ParticipantePremioTCTDto;
import br.org.crea.commons.models.cadastro.dtos.premio.PremioTCTDto;
import br.org.crea.commons.models.commons.dtos.AuthenticationDto;
import br.org.crea.commons.models.commons.dtos.GenericDto;
import br.org.crea.commons.models.commons.dtos.TelefoneDto;
import br.org.crea.commons.models.corporativo.dtos.PessoaDto;
import br.org.crea.commons.service.EmailService;
import br.org.crea.commons.service.cadastro.PremioTCTService;
import br.org.crea.commons.service.cadastro.TelefoneService;
import br.org.crea.commons.service.funcionario.PerfilService;
import br.org.crea.commons.util.ResponseRestApi;
import br.org.crea.commons.util.StringUtil;
import br.org.crea.corporativo.service.AuthService;

@Resource
@Path("/cadastro/instituicao-ensino/premio-tct")
public class PremioTCTResource {

	@Inject
	ResponseRestApi response;

	@Inject
	EmailService emailService;

	@Inject
	TelefoneService telefoneService;

	@Inject
	PremioTCTService service;

	@Inject
	PerfilService perfilService;

	@Inject
	AuthService authService;

	@POST
	public Response premio(PremioTCTDto dto) {
		PremioTCTDto premioDto = service.premio(dto);
		if (premioDto.foiFinalizado()) {
			return response.information().message(
					"Desculpe! Já foi indicado um trabalho para este curso e nível nesse ano. De acordo com o regulamento só pode ser indicado um trabalho por curso e nível (Técnico e Graduação).")
					.data(premioDto).build();
		}

		if (!premioDto.getCampus().getIdString().equals(dto.getCampus().getIdString())) {
			return response.information().message(
					"Desculpe! Já foi iniciada uma indicação para esta instituição, curso e nível, porém de campus diferente.")
					.data(premioDto).build();
		}

		return response.success().data(premioDto).build();
	}

	@POST
	@Path("mestrado-doutorado")
	public Response salvaPremioNivelMestradoOuDoutorado(PremioTCTDto dto) {

		if (service.possuiDezIndicacoesFinalizadas(dto)) {
			return response.information().message(
					"Desculpe! Já foram indicados dez trabalhos para este curso e nível neste ano. De acordo com o regulamento o limite é de dez indicações de trabalho por instituição, curso e nível (Mestrado e Doutorado).")
					.build();
		}

		PremioTCTDto premioDto = service.salvaPremioNivelMestradoOuDoutorado(dto);
		if (premioDto.foiFinalizado()) {
			return response.information().message(
					"Desculpe! Já foi indicado um trabalho para este curso e nível neste ano. De acordo com o regulamento só pode ser indicado um trabalho por instituição, curso e nível (Técnico e Graduação).")
					.data(premioDto).build();
		}

		return response.success().data(premioDto).build();
	}

	@POST
	@Path("pesquisa")
	public Response pesquisa(PremioTCTDto dto) {
		return response.success().data(service.pesquisa(dto)).build();
	}

	@PUT
	public Response atualizaIndicacao(PremioTCTDto dto) {
		return response.success().data(service.atualizaIndicacao(dto)).build();
	}

	@PUT
	@Path("aceite")
	public Response atualizaAceite(PremioTCTDto dto) {
		return response.success().data(service.atualizaAceite(dto))
				.message("Aceite realizado com sucesso. Inscrição finalizada. Agradecemos sua participação.").build();
	}

	@DELETE
	@Path("{id}")
	public Response deleta(@PathParam("id") Long id) {
		service.deleta(id);
		return response.success().message("Indicação cancelada com sucesso!").build();
	}

	@GET
	@Path("participantes/{idPremio}")
	@Publico
	public Response getParticipantes(@PathParam("idPremio") Long id) {
		return response.success().data(service.getParticipantes(id)).build();
	}

	@POST
	@Path("participante")
	public Response participante(ParticipantePremioTCTDto dto) {

		List<ParticipantePremioTCTDto> lista = service.getParticipantes(dto.getIdPremio());
		if (dto.ehAutor()) {
			if (service.verificaLimiteDeQuatroAutores(lista, dto)) {
				return response.information().message("O limite é de até quatro autores por indicação.").build();
			}
			if (service.verificaAcumulacaoPapelAutor(lista, dto)) {
				return response.information().message("Participante não pode assumir mais este papel de autor.")
						.build();
			}
		} else if (dto.ehAvaliador()) {
			if (service.verificaLimiteDeTresAvaliadores(lista, dto)) {
				return response.information().message("O limite é de três avaliadores por indicação.").build();
			}
			if (service.verificaAcumulacaoPapelAvaliador(lista, dto)) {
				return response.information().message("Participante não pode assumir mais este papel.").build();
			}
		} else if (dto.ehCoorientador()) {
			if (service.verificaLimiteDeUmCoorientador(lista, dto)) {
				return response.information().message("O limite é de um coorientador por indicação.").build();
			}
			if (service.verificaAcumulacaoPapelCoorientador(lista, dto)) {
				return response.information().message("Participante não pode assumir mais este papel.").build();
			}
		} else if (dto.ehOrientador()) {
			if (service.verificaLimiteDeUmOrientador(lista, dto)) {
				return response.information().message("O limite é de um orientador por indicação.").build();
			}
			if (service.verificaAcumulacaoPapelOrientador(lista, dto)) {
				return response.information().message("Participante não pode assumir mais este papel.").build();
			}
		} else if (dto.ehComissao()) {
			if (service.verificaLimiteTresComissao(lista, dto)) {
				return response.information().message("O limite é de três participantes na comissão de seleção.")
						.build();
			}
			if (service.verificaAcumulacaoPapelComissao(lista, dto)) {
				return response.information().message("Participante não pode assumir mais este papel.").build();
			}
		}
		return response.success().data(service.participante(dto)).message("Participante adicionado com sucesso!")
				.build();
	}

	@DELETE
	@Path("participante/{id}")
	public Response participante(@PathParam("id") Long id) {
		return response.success().data(service.deletaParticipante(id)).message("Participante excluído com sucesso")
				.build();
	}

	@PUT
	@Path("participante/atualiza")
	public Response atualizaParticipante(ParticipantePremioTCTDto dto) {
		return response.success().data(service.atualizaParticipante(dto))
				.message("Dados do Participante atualizados com Sucesso!").build();
	}

	@PUT
	@Path("arquivoResumo")
	public Response atualizaArquivoResumo(PremioTCTDto dto) {
		return response.success().data(service.atualizaArquivoResumo(dto))
				.message("Upload feito com sucesso e arquivo atualizado.").build();
	}

	@PUT
	@Path("arquivoTermo")
	public Response atualizaArquivoTermo(PremioTCTDto dto) {
		return response.success().data(service.atualizaArquivoTermo(dto))
				.message("Upload feito com sucesso e arquivo atualizado.").build();
	}

	@PUT
	@Path("arquivo")
	public Response atualizaArquivo(PremioTCTDto dto) {
		return response.success().data(service.atualizaArquivo(dto))
				.message("Upload feito com sucesso e arquivo atualizado.").build();
	}

	@DELETE
	@Path("arquivo/{id}")
	@Publico
	public Response atualizaArquivo(@PathParam("id") Long id) {
		service.deletarArquivo(id);
		return response.success().message("Arquivo excluído! Favor selecione outro arquivo.").build();
	}

	@DELETE
	@Path("arquivoTermo/{id}")
	@Publico
	public Response atualizaArquivoTermo(@PathParam("id") Long id) {
		service.deletarArquivoTermo(id);
		return response.success().message("Arquivo excluído! Favor selecione outro arquivo.").build();
	}
	
	@DELETE
	@Path("arquivoResumo/{id}")
	@Publico
	public Response atualizaArquivoResumo(@PathParam("id") Long id) {
		service.deletarArquivoResumo(id);
		return response.success().message("Arquivo excluído! Favor selecione outro arquivo.").build();
	}
	
	@GET
	@Path("premio/{id}")
	public Response getPremioById(@PathParam("id") Long id) {
		return response.success().data(service.getPremioByID(id)).build();
	}

	@PUT
	@Path("gera-acesso/{cpf}")
	@Publico
	public Response geraAcesso(@PathParam("cpf") String cpf, PessoaDto pessoa) {

		if (pessoa.temTelefone()) {
			TelefoneDto telefone = pessoa.getTelefones().get(0);
			telefoneService.salvar(telefone);
		}

		if (pessoa.getEmail() != null) {
			EmailDto emailDto = new EmailDto();
			emailDto.setDescricao(pessoa.getEmail());
			emailDto.setIdPessoa(pessoa.getId());

			if (emailService.existeEmailCadastrado(emailDto)) {
				return response.information().message("Este e-mail já está em uso.").build();
			} else {
				if (StringUtil.ehEmail(emailDto.getDescricao())) {
					emailService.criarEmail(emailDto);
				} else {
					return response.information().message("Este e-mail não possui um formato válido").build();
				}
			}
		}

		GenericDto dto = new GenericDto();
		dto.setId(pessoa.getId().toString());
		dto.setIdPerfil("76a57a761bda896075597391735cd6a1");
		dto.setDescricaoPerfil("operadorinstituicaopremio");

		AuthenticationDto authDto = new AuthenticationDto();
		authDto.setTipo("LEIGOPF");
		authDto.setLogin(cpf);

		perfilService.salvaPerfil(dto); // genericDto, id, idPerfil e descricaoPerfil
		authService.gerarSenha(authDto); // authDto, tipo e login
		return response.success().build();
	}

	@POST
	@Path("relatorio")
	public Response getRelatorio(PremioTCTDto dto) {

		return Response.ok(service.getRelatorio(dto))
				.header("Content-Disposition", "attachment; filename=" + "planilha-" + dto.getTipoRelatorio() + ".xls")
				.header("Content-Type", "application/vnd.ms-excel").type(MediaType.APPLICATION_OCTET_STREAM).build();

	}
}
