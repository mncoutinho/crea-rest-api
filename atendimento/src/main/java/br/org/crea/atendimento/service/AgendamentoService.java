package br.org.crea.atendimento.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.atendimento.converter.AgendamentoConverter;
import br.org.crea.atendimento.dao.AgendamentoDao;
import br.org.crea.atendimento.dao.AssuntoMobileDao;
import br.org.crea.commons.converter.atendimento.UnidadeAtendimentoConverter;
import br.org.crea.commons.dao.cadastro.funcionario.FuncionarioDao;
import br.org.crea.commons.models.atendimento.AgendamentoMobile;
import br.org.crea.commons.models.atendimento.StatusAgendamentoMobile;
import br.org.crea.commons.models.atendimento.dtos.AgendamentoDto;
import br.org.crea.commons.models.atendimento.dtos.CriaHorariosDto;
import br.org.crea.commons.models.atendimento.dtos.HorariosDisponiveisDto;
import br.org.crea.commons.models.cadastro.enuns.TipoEventoAuditoria;
import br.org.crea.commons.models.commons.DestinatarioEmailDto;
import br.org.crea.commons.models.commons.dtos.EmailEnvioDto;
import br.org.crea.commons.models.commons.dtos.GenericDto;
import br.org.crea.commons.models.commons.dtos.PesquisaGenericDto;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.models.corporativo.Assunto;
import br.org.crea.commons.models.corporativo.dtos.AssuntoDto;
import br.org.crea.commons.service.CadastroService;
import br.org.crea.commons.service.EmailService;
import br.org.crea.commons.service.cadastro.AuditoriaService;
import br.org.crea.commons.util.DateUtils;

public class AgendamentoService {

	@Inject	AgendamentoDao dao;

	@Inject	AssuntoMobileDao assuntoDao;

	@Inject	FuncionarioDao funcionarioDao;

	@Inject	AgendamentoConverter converter;

	@Inject	CadastroService cadastroService;

	@Inject	UnidadeAtendimentoConverter unidadeAtendimentoConverter;
	
	@Inject	AgendamentoConverter agendamentoConverter;
	
	@Inject EmailService emailService;

	@Inject AuditoriaService auditoriaService;

	public List<AgendamentoDto> agendarWeb(List<AgendamentoDto> listDto, UserFrontDto pessoa) {

		for (AgendamentoDto dto : listDto) {

			criaHorario(dto, pessoa);

		}

		return listDto;

	}

	public AgendamentoDto cancelar(AgendamentoDto dto) {

		dao.cancelaAgenadmento(dto.getId());

		AgendamentoMobile novoAgendamento = new AgendamentoMobile();

		novoAgendamento = dao.getBy(dto.getId());
		novoAgendamento.setIdPessoa(null);
		novoAgendamento.getStatus().setId(new Long(0));
		novoAgendamento.getAssunto().setId(new Long(0));
		novoAgendamento.setId(null);
		novoAgendamento.setTelefone(null);
		novoAgendamento.setSenha(null);
		novoAgendamento.setNome(null);
		novoAgendamento.setEmail(null);
		novoAgendamento.setCpfOuCnpj(null);
		novoAgendamento.setIdDepartamento(dto.getIdUnidadeAtendimento());
		

		dao.create(novoAgendamento);

		return dto;
	}

	public List<AgendamentoDto> excluirAgendamentos(List<AgendamentoDto> listDto) {

		for (AgendamentoDto dto : listDto) {

			dao.deleta(dto.getId());
			
			if(dto.temEmailAgendado()){
				emailService.envia(populaEmail(dto));
			}
			
		}
		return listDto;
	}

	private EmailEnvioDto populaEmail(AgendamentoDto dto) {

		EmailEnvioDto email = new EmailEnvioDto();
		List<DestinatarioEmailDto> listDestinatario = new ArrayList<DestinatarioEmailDto>();
		DestinatarioEmailDto destinatario = new DestinatarioEmailDto();
		
		StringBuilder sb = new StringBuilder();
		
		String dataFormatada = DateUtils.format(dto.getDataAgendamento(), DateUtils.DD_MM_YYYY_HH_MM);
		
		sb.append("<h3>Prezado(a)  " + dto.getNome()+"</h3>");
		sb.append("<p style='color:#000000'>Informamos que seu agendamento na data: "+ dataFormatada +" foi cancelado por motivos operacionais.</p>");
		sb.append("<p style='color:#000000'>Solicitamos que realize novo agendamento em nosso portal, através do link <a href='http://portalservicos.crea-rj.org.br/#/app/agendamento/acesso'>Clique aqui para agendar</a> </p>");
		sb.append("<p style='color:#000000'>Caso possua alguma dúvida, comunique-se com o(a) Coordenador(a) da Regional através dos seguintes contatos:</p>");
		sb.append("<p style='color:#000000'>" + dto.getFuncionario().getNome() + " - " + dto.getFuncionario().getTelefones() + " ou email " + dto.getFuncionario().getEmail() + "</p>");		
		sb.append("<p style='color:#000000'>Atenciosamente,</p>");
		sb.append("<p style='color:#000000'>" + dto.getFuncionario().getNome() +"</p>");
		
		email.setMensagem(sb.toString());
		email.setAssunto("Cancelamento Agendamento Crea");
		destinatario.setNome(dto.getNome());
		destinatario.setEmail(dto.getEmail());
		listDestinatario.add(destinatario);
		email.setDestinatarios(listDestinatario);
		email.setEmissor("atendimento@crea-rj.org.br");
		email.setDataUltimoEnvio(new Date());
		return email;
	}

	public List<AgendamentoDto> getAgendados(String cpfOuCnpj) {
		return converter.toListDto(dao.getAgendadosPor(cpfOuCnpj));
	}

	public boolean mesDisponivel(CriaHorariosDto dto) {
		return dao.mesDisponivel(dto);
	}
	
	public boolean validaDataAgendamento(PesquisaGenericDto dto) {
		return dao.validaDataAgendamento(dto);
	}

	public List<AssuntoDto> getAssuntosMobile(UserFrontDto pessoa) {
		return converter.toListAssuntoDto(assuntoDao.getAssuntos(pessoa));
	}

	public List<HorariosDisponiveisDto> disponiveis(PesquisaGenericDto dto) {
		return converter.toListDisponiveisDto(dao.getDisponiveis(dto.getMesAno(), dto.getUnidadeAtendimento()));
	}
	
	public List<HorariosDisponiveisDto> horariosDisponiveis(PesquisaGenericDto dto) {
		return converter.toListDisponiveisDto(dao.getHorariosDisponiveis(dto.getData(), dto.getUnidadeAtendimento()));
	}

	public List<AgendamentoDto> getAgendamentosParaEdicao(PesquisaGenericDto dto) {
		return converter.toListDto(dao.getAgendamentosParaEdicao(dto));
	}

	public List<AgendamentoDto> getAgendamentosFuturos(PesquisaGenericDto dto) {

		if (dto.getDataAtual() == null) {
			return new ArrayList<AgendamentoDto>();
		}
		return converter.toListDto(dao.getAgendamentosFuturos(dto));
	}

	public void duplica(AgendamentoDto dto, UserFrontDto pessoa) {

		criaHorario(dto, pessoa);

	}

	public List<AgendamentoMobile> verificaAgendamentoDsiponivel(AgendamentoDto dto) {
		return dao.existeAgendamentoDisponivel(dto);
	}

	public AgendamentoDto agendaHorarioNovo(AgendamentoDto dto, UserFrontDto pessoa) {
		
		AgendamentoMobile novoAgendamento = new AgendamentoMobile();
		novoAgendamento = dao.getBy(dto.getId());
		
		if (pessoa.naoTemRegistroNoCrea()) {

			AgendamentoMobile agendamento = dao.getNomeEmailNaBaseAgendamentoSeExistir(pessoa.getCpfOuCnpj());

			if (agendamento != null) {
				novoAgendamento.setEmail(agendamento.getEmail());
				novoAgendamento.setNome(agendamento.getNome());
				novoAgendamento.setCpfOuCnpj(agendamento.getCpfOuCnpj());
			}else{
				novoAgendamento.setEmail(pessoa.getEmail());
				novoAgendamento.setNome(pessoa.getNome());
				novoAgendamento.setCpfOuCnpj(pessoa.getCpfOuCnpj());
			}

		}else{
			novoAgendamento.setEmail(pessoa.getEmail());
			novoAgendamento.setNome(pessoa.getNome());
			novoAgendamento.setCpfOuCnpj(pessoa.getCpfOuCnpj());
		}
		
		novoAgendamento.setTelefone(dto.getTelefone());
		novoAgendamento.getStatus().setId(new Long(1));
		novoAgendamento.setAssunto(null);
		Assunto assunto = new Assunto();
		assunto.setId(dto.getIdAssunto());
		novoAgendamento.setAssunto(assunto);
		novoAgendamento.geraSenha();
		AgendamentoDto dtoNovo = agendamentoConverter.toDto(dao.update(novoAgendamento));
		
		return dtoNovo;
	}


	public boolean podeAgendarCliente(AgendamentoDto dto) {
		return dao.podeAgendarHorarioNoDia(dto);
	}
	
	public boolean podeAgendar(AgendamentoDto dto, String cpfOuCnpj) {
		return dao.podeAgendarHorario(dto, cpfOuCnpj);
	}


	public AgendamentoDto criaHorarioExtra(AgendamentoDto dto, UserFrontDto pessoa) {
	
		AgendamentoMobile novoAgendamento = new AgendamentoMobile();
		
		novoAgendamento.setTelefone(dto.getTelefone());
		novoAgendamento.setCpfOuCnpj(dto.getCpfOuCnpj());
		novoAgendamento.setEmail(dto.getEmail());
		novoAgendamento.setDataAgendamento(dto.getDataAgendamento());
		novoAgendamento.setTelefone(dto.getTelefone());
		novoAgendamento.setTurno(dto.getTurno());
		novoAgendamento.setNome(dto.getNome().toUpperCase());
		novoAgendamento.setIdDepartamento(dto.getIdUnidadeAtendimento());
		Assunto assunto = new Assunto();
		assunto.setId(dto.getIdAssunto());
		novoAgendamento.setAssunto(assunto);
		novoAgendamento.geraSenha();
		StatusAgendamentoMobile status = new StatusAgendamentoMobile();
		status.setId(new Long(1));;
		novoAgendamento.setStatus(status);
		novoAgendamento.setFuncionario(funcionarioDao.getFuncionarioByPessoa(pessoa.getIdPessoa()));
		novoAgendamento.setExtra(true);
				
		AgendamentoDto dtoNovo = agendamentoConverter.toDto(dao.update(novoAgendamento));
		
		
		return dtoNovo;
	}

	private void criaHorario(AgendamentoDto dto, UserFrontDto pessoa) {
		
		AgendamentoMobile agendamento = new AgendamentoMobile();
		Assunto assunto = new Assunto();
		assunto.setId(new Long(0));

		StatusAgendamentoMobile statusAgendamento = new StatusAgendamentoMobile();
		statusAgendamento.setId(new Long(0));

		agendamento.setIdDepartamento(dto.getIdUnidadeAtendimento());
		agendamento.setTurno(dto.getTurno());
		agendamento.setDataAgendamento(dto.getDataAgendamento());
		agendamento.setAssunto(assunto);
		agendamento.setStatus(statusAgendamento);
		agendamento.setFuncionario(funcionarioDao.getFuncionarioByPessoa(pessoa.getIdPessoa()));

		dao.create(agendamento);
		
	}

	public AgendamentoDto recuperaHorarioDisponivel(AgendamentoDto dto) {
		return agendamentoConverter.toDto(dao.recuperaHorarioDisponivel(dto));
	}

	public PesquisaGenericDto apagarAgendadosEdicao(PesquisaGenericDto dto) {
		dao.apagarAgendadosEdicao(dto);
		
		return dto;
	}

	
	public List<AssuntoDto> getAssuntosMobileParaHorarioExtra(String tipoPessoa) {
		return assuntoDao.getTodosAssuntos(tipoPessoa);
	}

	public boolean validaPagamentoRegistro(Long idAssunto, UserFrontDto userDto) {
		return assuntoDao.pagamentoRegistroEstaPago(idAssunto, userDto.getIdPessoa());
	}

	public void agendamentoEmitirTaxa(GenericDto dto, UserFrontDto userFrontDto) {
		
		auditoriaService
			.usuario(userFrontDto)
			.textoAuditoria(dto.getDescricao())
			.tipoEvento(TipoEventoAuditoria.AGENDAMENTO_EMISSAO_TAXA)
			.create();
	}
	

}
