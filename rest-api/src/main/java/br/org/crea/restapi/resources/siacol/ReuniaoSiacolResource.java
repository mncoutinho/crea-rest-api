package br.org.crea.restapi.resources.siacol;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.org.crea.commons.annotations.Publico;
import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.models.commons.dtos.GenericDto;
import br.org.crea.commons.models.commons.dtos.PesquisaGenericDto;
import br.org.crea.commons.models.siacol.dtos.RelatorioReuniaoSiacolDto;
import br.org.crea.commons.models.siacol.dtos.ReuniaoSiacolDto;
import br.org.crea.commons.models.siacol.dtos.TesteSiacolGenericDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.ResponseRestApi;
import br.org.crea.siacol.service.ReuniaoSiacolService;


@Resource
@Path("siacol/reuniao")
public class ReuniaoSiacolResource {

	@Inject 
	ResponseRestApi response;

	@Inject 
	ReuniaoSiacolService service;
	@Inject
	HttpClientGoApi httpClientGoApi;
	
	@POST
	@Path("pesquisa") @Publico
	public Response getAll(PesquisaGenericDto dto) { 
//		return response.success().data(service.getReunioesSiacol(dto)).build();
//		RelSiacol01Dto aaa = null;
		
		List<ReuniaoSiacolDto> listDto = new ArrayList<ReuniaoSiacolDto>();

		listDto = service.getReunioesSiacol(dto);

		if (!listDto.isEmpty()) {
			if (dto.ehPrimeiraConsulta()) {
				return response.success().totalCount(service.getQuantidadeConsultaReunioes(dto)).data(listDto).build();
			} else {
				return response.success().data(listDto).build();
			}

		} else {
			return response.information().message("Não foi encontrada reunião").build();
		}
	}
	
	@GET
	@Path("abertas") @Publico
	public Response getReunioesAbertas() { 
		return response.success().data( service.getReunioesAbertas()).build();
	}
	
	@GET
	@Path("status-protocolo-siacol") @Publico
	public Response getStatusProtocoloSiacol() {
		return response.success().data(service.getStatusProtocoloSiacol()).build();
	}
	
	@PUT
	@Path("status-painel-conselheiro/{idReuniao}/{status}") @Publico
	public Response setStatusPainelConselheiro(@PathParam("idReuniao") Long idReuniao, @PathParam("status") String status) {
		service.setStatusPainelConselheiro(idReuniao, status);
		return response.success().build();
	}
	
	@POST
	@Path("salvar") @Publico
	public Response enviar(ReuniaoSiacolDto dto){	
		ReuniaoSiacolDto reuniaoSiacolDto = service.salvar(dto);
		if (reuniaoSiacolDto != null) {
			return response.success().data(reuniaoSiacolDto).build();
		}else {
			return response.information().message("Já Existe Reunião nesse intervalo de data e horário").build();
		}
		
	}
	
	@PUT @Publico
	public Response atualizar(ReuniaoSiacolDto dto){	
		ReuniaoSiacolDto reuniaoSiacolDto = service.atualizar(dto);
		if (reuniaoSiacolDto != null) {
			return response.success().data(reuniaoSiacolDto).build();
		}else {
			return response.information().message("Já Existe Reunião nesse intervalo de data e horário").build();
		}
	}
	
	@GET
	@Path("pode-salvar-pauta/{idReuniao}") @Publico
	public Response podeSalvarPauta(@PathParam("idReuniao") Long idReuniao){	
		return response.success().data(service.podeSalvarPauta(idReuniao)).build();
	}
	
	@POST
	@Path("retirar-arquivo-atualizar-profissional") @Publico
	public Response retirarArquivoAlterarProfissional(GenericDto dto){	
		return response.success().data(service.retirarArquivoAlterarProfissional(dto)).build();
	}
	
	@POST
	@Path("protocolos-a-pautar") @Publico
	public Response getProtocolosAPautar(ReuniaoSiacolDto dto){	
		return response.success().data(service.getProtocolosAPautarEmReuniao(dto)).build();
	}
	
	@POST
	@Path("busca-reuniao-anterior")@Publico
	public Response getReuniaoAnteriorPor(ReuniaoSiacolDto reuniaoAtual) {
		return response.success().data(service.getReuniaoAnteriorPor(reuniaoAtual)).build();
	}
	
	@GET
	@Path("busca-reuniao/{idPauta}")@Publico
	public Response getReuniaoPor(@PathParam("idPauta") Long idPauta) {
		return response.success().data(service.getReuniaoPor(idPauta)).build();
	}
	
	@GET
	@Path("busca-pauta/{idReuniao}")@Publico
	public Response getPautaPor(@PathParam("idReuniao") Long idReuniao) {
		return response.success().data(service.getPautaPorIdReuniao(idReuniao)).build();
	}
	
	@GET
	@Path("busca-protocolos-a-classificar/{idDepartamento}")@Publico
	public Response getProtocolosSemClassificacaoPautaPor(@PathParam("idDepartamento") Long idDepartamento) {
		return response.success().data(service.getProtocolosSemClassificacaoPautaPor(idDepartamento)).build();
	}
	
	@GET
	@Path("busca-protocolo-a-reclassificar/{numeroProtocolo}/{idDepartamento}")@Publico
	public Response buscaProtocoloAReclassificarPor(@PathParam("numeroProtocolo") Long numeroProtocolo, @PathParam("idDepartamento") Long idDepartamento) {
		
		return service.buscaProtocoloAReclassificarPor(numeroProtocolo, idDepartamento) == null ?
				response.information().message("Protocolo não localizado para reclassificação").build() :
					response.success().data(service.buscaProtocoloAReclassificarPor(numeroProtocolo, idDepartamento)).build();	
	}
	
	@GET
	@Path("busca-protocolos-pautados/{idDocumento}")@Publico
	public Response getProtocolosPautados(@PathParam("idDocumento") Long idDocumento) {
		return response.success().data(service.getProtocolosPautados(idDocumento)).build();
	}
	
	@GET
	@Path("busca-protocolos-a-assinar/{idDepartamento}")@Publico
	public Response getProtocolosAAssinar(@PathParam("idDepartamento") Long idDepartamento) {
		return response.success().data(service.getProtocolosAAssinar(idDepartamento)).build();
	}
	
	@GET
	@Path("busca-protocolos-a-assinar-adreferendum/{idDepartamento}")@Publico
	public Response getProtocolosAAssinarAdReferendum(@PathParam("idDepartamento") Long idDepartamento) {
		return response.success().data(service.getProtocolosAAssinarAdReferendum(idDepartamento)).build();
	}
	
	@GET
	@Path("busca-protocolos-homologacao/{idDepartamento}")@Publico
	public Response getProtocolosHomologacao(@PathParam("idDepartamento") Long idDepartamento) {
		return response.success().data(service.getProtocolosHomologacao(idDepartamento)).build();
	}
	
	@GET
	@Path("busca-protocolos-deferido/{idDepartamento}")@Publico
	public Response getProtocolosDeferido(@PathParam("idDepartamento") Long idDepartamento) {
		return response.success().data(service.getProtocolosDeferido(idDepartamento)).build();
	}
	
	@GET
	@Path("busca-protocolos-provisorio/{idDepartamento}")@Publico
	public Response getProtocolosProvisorio(@PathParam("idDepartamento") Long idDepartamento) {
		return response.success().data(service.getProtocolosProvisorio(idDepartamento)).build();
	}
	
	
	//FIXME: Excluir após fim do projeto
	@POST
	@Path("manipula-reuniao")@Publico
	public Response manipulaReuniao(TesteSiacolGenericDto dto) throws ParseException {
		return response.success().data(service.manipulaReuniao(dto)).build();
	}
	
	@PUT
	@Path("finaliza-pauta/{idPauta}")@Publico	
	public Response finalizaPauta(@PathParam("idPauta") Long idPauta) {	
		return response.success().data(service.finalizaPauta(idPauta)).build();
	}	
	

	@GET //FIXME substituir idfuncionario pelo token
	@Path("verifica-pauta-edicao/{idPauta}/{idFuncionario}")@Publico	
	public Response verificaPautaEmEdicao(@PathParam("idPauta") Long idPauta, @PathParam("idFuncionario") Long idFuncionario) {	
		
		if (service.verificaPautaEmEdicao(idPauta, idFuncionario)) {
			return response.error().message("Pauta já está em Edição").build();
		}else {
			return response.success().build();
		}
	}	
	
	/** Método para abrir, pausar, cancelar ou encerrar uma reunião
	 * @param ReuniaoSiacolDto 
	 */
	@PUT
	@Path("presencial/{acao}") @Publico
	public Response atualizaReuniao(ReuniaoSiacolDto dto, @PathParam("acao") String acao, @HeaderParam("Authorization") String token){
		dto.setAcao(acao);
		
		if (dto.heIniciarSessao()) {
//			if (!DateUtils.eNoDiaAtual(dto.getDataReuniao())) {
//				return response.error().message("Reunião só poderá ser aberta na data agendada.").build();
//			}
			if (service.naoAtingiuQuorumMinimo(dto)) {
				return response.error().message("Reunião não atingiu o quórum mínimo").build();
			}
		} 

		if (service.atualizaReuniaoPresencial(dto, httpClientGoApi.getUserDto(token))) {
			return response.success().data(dto).build();
		} else {
			return response.error().build();
		}
		
	}
	
	@POST
	@Path("popula-indicadores-conselheiros/{idDepartamento}/{idReuniao}") @Publico
	public Response populaIndicadoresPlenaria(@PathParam("idDepartamento") Long idDepartamento, @PathParam("idReuniao") Long idReuniao){
		service.populaIndicadoresPlenaria(idDepartamento, idReuniao);
		return response.success().build();
	}
	
	@GET
	@Path("busca-emails-conselheiros/{idDepartamento}") @Publico
	public Response buscaEmailsConselheiros(@PathParam("idDepartamento") Long idDepartamento){
		return response.success().data(service.buscaEmailsConselheiros(idDepartamento)).build();
	}
	
	@GET
	@Path("busca-mesa-diretora/{idReuniao}") @Publico
	public Response getMesaDiretora(@PathParam("idReuniao") Long idReuniao){
		return response.success().data(service.getMesaDiretora(idReuniao)).build();
	}
	
	@GET
	@Path("busca-presidente-da-mesa-diretora/{idReuniao}") @Publico
	public Response getPresidenteDaMesaDiretora(@PathParam("idReuniao") Long idReuniao){
		return response.success().data(service.getPresidenteDaMesaDiretora(idReuniao)).build();
	}
	
	@GET
	@Path("relatorios/{idReuniao}") @Publico
	public Response getRelatorioReuniaoPor(@PathParam("idReuniao") Long idReuniao){
		
		List<RelatorioReuniaoSiacolDto> relatorio = service.getRelatoriosReuniaoPor(idReuniao);
		
		if (relatorio != null) {
			return response.success().data(relatorio).build();
		} else {
			return response.information().message("Nenhum relatório para exibir").build();
		}
		
	}
	

	
	@GET
	@Path("relatorio-coordenadores/{idReuniao}/{tipo}") @Publico
	public Response gerarRelatorioCoordenadoresPor(@PathParam("idReuniao") Long idReuniao, @PathParam("tipo") String tipo){
		
		if (tipo.equals("PDF")) {
			return Response.ok(service.gerarRelatorioCoordenadoresPdfPor(idReuniao))
					.header("Content-Disposition", "attachment; filename=" + "relatorio-de-agendamentos" + ".pdf" )
					.header("Content-Type", "application/pdf").type(MediaType.APPLICATION_OCTET_STREAM).build();
		} else {
			return response.success().data(service.gerarRelatorioCoordenadoresNaTelaPor(idReuniao)).build();
		}
	}
	
	@GET
	@Path("reunioes-para-criacao-sumula/{idDepartamento}")@Publico	
	public Response reunioesParaCriacaoDeSumula(@PathParam("idDepartamento") Long idDepartamento) {	
		return response.success().data(service.reunioesParaCriacaoDeSumula(idDepartamento)).build();
	}
	
	@GET
	@Path("reunioes-para-criacao-ata/{idDepartamento}/{anoBusca}")@Publico	
	public Response reunioesParaCriacaoDeAta(@PathParam("idDepartamento") Long idDepartamento, @PathParam("anoBusca") Long anoBusca) {	
		return response.success().data(service.reunioesParaCriacaoDeAta(idDepartamento, anoBusca)).build();
	}
	
	@POST
	@Path("enviar-lembrate")@Publico	
	public Response enviarLembrate(ReuniaoSiacolDto reuniao) {	
		return response.success().data(service.enviarLembrate(reuniao)).build();
	}
		
}
