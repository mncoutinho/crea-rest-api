package br.org.crea.restapi.resources.corporativo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import br.org.crea.commons.annotations.Publico;
import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.dao.cadastro.pessoa.InteressadoDao;
import br.org.crea.commons.models.cadastro.dtos.EmailDto;
import br.org.crea.commons.models.commons.dtos.PesquisaGenericDto;
import br.org.crea.commons.models.corporativo.dtos.InteressadoWsDto;
import br.org.crea.commons.models.corporativo.dtos.LeigoDto;
import br.org.crea.commons.models.corporativo.dtos.PessoaDto;
import br.org.crea.commons.models.corporativo.dtos.PessoaFisicaDto;
import br.org.crea.commons.service.CommonsService;
import br.org.crea.commons.service.EmailService;
import br.org.crea.commons.service.PessoaService;
import br.org.crea.commons.util.EmailUtil;
import br.org.crea.commons.util.ResponseRestApi;

@Resource
@Path("/pessoa")
public class PessoaResource {

	@Inject
	ResponseRestApi response;

	@Inject
	PessoaService service;

	@Inject
	InteressadoDao pessoaDao;

	@Inject
	CommonsService commonsService;
	
	@Inject
	EmailService emailService;
	
	@Inject
	EmailUtil emailUtil;

	@GET
	@Path("interessado/{idPessoa}")
	@Publico
	public Response getInteressadoBy(@PathParam("idPessoa") Long idPessoa) {

		if (pessoaDao.existePessoa(idPessoa)) {
			return response.success().data(service.getInteressadoBy(idPessoa)).build();
		} else {
			return response.error().message("interessado.notExist").build();
		}
	}

	@POST
	@Path("consulta")
	@Publico
	public Response consultaPessoa(PesquisaGenericDto pesquisaDto) {

		List<PessoaDto> listPessoaDto = new ArrayList<PessoaDto>();

		listPessoaDto = service.getPessoaPor(pesquisaDto);

		if (!listPessoaDto.isEmpty() && listPessoaDto.get(0).getId() != null) {
			return response.success().data(listPessoaDto).build();
		} else {
			return response.error().message("interessado.notExist").build();
		}

	}

	@POST
	@Path("fisica/consulta-por-nome")
	@Publico
	public Response getPessoaFisicaPorNome(PesquisaGenericDto dto) {

		List<PessoaDto> listDto = new ArrayList<PessoaDto>();
		listDto = service.getPessoaFisicaPorNome(dto);
		
		if (!listDto.isEmpty()) {
			if (dto.ehPrimeiraConsulta()) {
				return response.success().totalCount(service.totalBuscaListPessoaFisicaByNome(dto)).data(listDto).build();
			} else {
				return response.success().data(listDto).build();
			} 
		} else {
			return response.error().message("interessado.notExist").build();
		}
	}

	@POST
	@Path("juridica/consulta-por-nome")
	@Publico
	public Response getPessoaJuridicaPorNome(PesquisaGenericDto dto) {

		List<PessoaDto> listDto = new ArrayList<PessoaDto>();
		listDto = service.getPessoaJuridicaPorNome(dto);
		
		if (!listDto.isEmpty()) {
			if (dto.ehPrimeiraConsulta()) {
				return response.success().totalCount(service.totalBuscaListPessoaJuridicaByNome(dto)).data(listDto).build();
			} else {
				return response.success().data(listDto).build();
			} 
		} else {
			return response.error().message("interessado.notExist").build();
		}
	}
	


	@GET
	@Path("fisica/{numeroCPF}")
	@Publico
	public Response getPessoaByCPF(@PathParam("numeroCPF") String numeroCPF) {

		if (!commonsService.cpfHeValido(numeroCPF)) {
			return response.error().message("CPF inválido").build();
		}

		List<PessoaDto> listDto = new ArrayList<PessoaDto>();

		listDto = service.getPessoaByNumeroCPF(numeroCPF);

		if (!listDto.isEmpty()) {
			return response.success().data(listDto).build();
		} else {
			return response.information().build();
		}

	}

	@GET
	@Path("juridica/{numeroCNPJ}")
	@Publico
	public Response getPessoaByCNPJ(@PathParam("numeroCNPJ") String numeroCNPJ) {

		if (!commonsService.cnpjHeValido(numeroCNPJ)) {
			return response.error().message("validator.cnpj.invalido").build();
		}

		List<PessoaDto> listDto = new ArrayList<PessoaDto>();

		listDto = service.getPessoaByNumeroCNPJ(numeroCNPJ);

		if (!listDto.isEmpty()) {
			return response.success().data(listDto).build();
		} else {
			return response.information().build();
		}

	}

	@GET
	@Path("juridica-matriz/{numeroCNPJ}")
	@Publico
	public Response getPessoaByCNPJMatriz(@PathParam("numeroCNPJ") String numeroCNPJ) {
		
		if (!commonsService.cnpjHeValido(numeroCNPJ)) {
		  return response.error().message("validator.cnpj.invalido").build();
	    }
	
		List<PessoaDto> listDto = new ArrayList<PessoaDto>();

		listDto = service.getPessoaByNumeroCNPJMatriz(numeroCNPJ);

		if (!listDto.isEmpty()) {
			return response.success().data(listDto).build();
		} else {
			return response.information().build();
		}

	}
	
	@POST
	@Path("leigo")
	@Publico
	public Response cadastrarLeigo(LeigoDto dto) {
		
		EmailDto emailDto = null;
		
		if(commonsService.validaFormatoCpfOuCnpj(dto.getCpfOuCnpj())){
			
			if(dto.getEmail() != null){
				emailDto = new EmailDto();
				emailDto.setIdPessoa(dto.getId());
				emailDto.setDescricao(dto.getEmail());
				
				if(!emailUtil.validaEmail(emailDto)){
					return response.error().message("E-mail inválido").build();
				}
			}
			
			return response.success().data(service.cadastrarLeigo(dto)).build();
	
		}else {
			return response.error().message("CPF inválido").build();
		}
		
	}
	
	@POST
	@Path("leigo-minimo")
	@Publico
	public Response cadastraMinimoLeigo(LeigoDto dto) {
		
		if (commonsService.validaFormatoCpfOuCnpj(dto.getCpfOuCnpj())) {
			if (service.cadastrarLeigo(dto) != null) {
				return response.success().message("Cadastrado com sucesso").build();
			} else {
				return response.error().message("Não foi possível cadastrar!").build();
			}
		} else {
			return response.error().message("cpfOuCnpj.invalido").build();
		}		
	}
	
	@GET
	@Path("leigopf/{numeroCPF}")
	@Publico
	public Response consultaLeigoPFPorCPF(@PathParam("numeroCPF") String numeroCPF) {
		LeigoDto dto = new LeigoDto();
		dto = service.getLeigoPF(numeroCPF);
		return response.success().data(dto).build();
	}
	
	@GET
	@Path("leigopj/{numeroCNPJ}")
	@Publico
	public Response consultaLeigoPFPorCNPJ(@PathParam("numeroCNPJ") String numeroCNPJ) {
		LeigoDto dto = new LeigoDto();
		dto = service.getLeigoPJ(numeroCNPJ);
		return response.success().data(dto).build();
	}
	
	@GET
	@Path("buscar-contatos/{idPessoa}")
	public Response buscarDadosContato(@PathParam("idPessoa") Long idPessoa) {
		return response.success().data(service.getDadosContatoPessoaPor(idPessoa)).build();
	}
	
	@POST
	@Path("alterar-responsavel")@Publico
	public Response alterarResponsavel(PessoaDto dto) {
		service.alterarResponsavel(dto);
		return response.success().message("condominio.alterar.responsavel").build();
	}	
 
	@GET
	@Path("fisica/detalhar/{idPessoa}")
	@Publico
	public Response buscaDetalhadaPessoaFisicaPorId(@PathParam("idPessoa") Long idPessoa) {

		List<PessoaFisicaDto> listDto = new ArrayList<PessoaFisicaDto>();

		listDto = service.getPessoaFisicaDetalhadaPorId(idPessoa);

		if (!listDto.isEmpty()) {
			return response.success().data(listDto).build();
		} else {
			return response.information().build();
		}

	}
	
	@GET
	@Path("juridica-registro/{registro}")
	public Response buscarPessoaJuridica(@PathParam("registro") Long registro) {
		return response.success().data(service.buscarPessoaJuridica(registro)).build();
	}
	
	@PUT
	@Path("permissao-instituicao")
	@Publico
	public Response atualizaIdInstituicao(PessoaDto dto){
		return response.success().data(service.atualizaIdInstituicao(dto)).build();
	}
	
	@POST
	@Path("fisica/consulta-por-nome-paginado")
	@Publico
	public Response getPessoaFisicaPorNomePaginado(PesquisaGenericDto dto) {

		List<PessoaDto> listDto = new ArrayList<PessoaDto>();
		listDto = service.getPessoaFisicaPorNomePaginado(dto);
		
		if (!listDto.isEmpty()) {
			return response.success().data(listDto).build(); 
		} else {
			return response.notFound().build();
		}
	}
	
	@POST
	@Path("juridica/consulta-por-nome-paginado")
	@Publico
	public Response getPessoaJuridicaPorNomePaginado(PesquisaGenericDto dto) {

		List<PessoaDto> listDto = new ArrayList<PessoaDto>();
		listDto = service.getPessoaJuridicaPorNomePaginado(dto);
		
		if (!listDto.isEmpty()) {
			return response.success().data(listDto).build();
		} else {
			return response.notFound().build();
		}
	}
	
	@POST
	@Path("juridica/isenta-cnpj/consulta-por-nome")
	@Publico
	public Response getPessoaJuridicaIsentaCnpjPorNome(PesquisaGenericDto dto) {

		List<PessoaDto> listDto = new ArrayList<PessoaDto>();
		listDto = service.getPessoaJuridicaIsentaCnpjPorNome(dto);
		
		if (!listDto.isEmpty()) {
				return response.success().data(listDto).build();
		} else {
			return response.error().message("interessado.notExist").build();
		}
	}
	
	@POST
	@Path("juridica/isenta-cpf/consulta-por-nome")
	@Publico
	public Response getPessoaFisicaIsentaCnpjPorNome(PesquisaGenericDto dto) {

		List<PessoaDto> listDto = new ArrayList<PessoaDto>();
		listDto = service.getPessoaFisicaIsentaCpfPorNome(dto);
		
		if (!listDto.isEmpty()) {
				return response.success().data(listDto).build();
		} else {
			return response.error().message("interessado.notExist").build();
		}
	}
	
	@GET
	@Path("interessado-ws/{cpfOuCnpj}")
	@Publico
	public Response getInteressadoBy(@PathParam("cpfOuCnpj") String cpfOuCnpj) {
		InteressadoWsDto interessadoWSPor = service.getInteressadoWSPor(cpfOuCnpj);
		if(interessadoWSPor == null) return response.error().message("interessado.notExist").build();
		return response.success().data(interessadoWSPor).build();
	}
	
	@GET
	@Path("situacao-registro/{idPessoa}")
	public Response getSituacaoRegistro(@PathParam("idPessoa") Long idPessoa) {
		return response.success().data(service.getSituacaoRegistro(idPessoa)).build();
	}
	
}
