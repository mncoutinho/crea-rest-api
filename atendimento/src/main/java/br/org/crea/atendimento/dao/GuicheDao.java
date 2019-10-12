package br.org.crea.atendimento.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.dao.corporativo.Page;
import br.org.crea.commons.models.atendimento.AgendamentoMobile;
import br.org.crea.commons.models.atendimento.dtos.AgendamentoDto;
import br.org.crea.commons.models.atendimento.dtos.PesquisaAtendimentoDto;
import br.org.crea.commons.models.commons.dtos.PesquisaGenericDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class GuicheDao extends GenericDao<AgendamentoMobile, Serializable> {

	@Inject
	HttpClientGoApi httpGoApi;

	public GuicheDao() {
		super(AgendamentoMobile.class);
	}

	public List<AgendamentoMobile> getFilaDoDia(PesquisaGenericDto pesquisa) {

		List<AgendamentoMobile> listaAgendamento = new ArrayList<AgendamentoMobile>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT A FROM AgendamentoMobile A ");
		sql.append("     WHERE TO_CHAR(A.dataAgendamento, 'mm/dd/yyyy') = TO_CHAR(:hoje, 'mm/dd/yyyy') ");
		sql.append(" 	 AND  A.idDepartamento = :idUnidade ");
		if (pesquisa.getStatus().equals(new Long(99))) {
			sql.append(" 	 AND  A.status.id NOT IN (2, 4, 5, 6, 7, 9) ");
		} else {
			sql.append(" 	 AND  A.status.id = :status ");
		}
		sql.append("ORDER BY A.dataAgendamento, A.dataUpdate");

		try {

			TypedQuery<AgendamentoMobile> query = em.createQuery(sql.toString(), AgendamentoMobile.class);
			query.setParameter("idUnidade", pesquisa.getUnidadeAtendimento());
			query.setParameter("hoje", new Date());
			if (!pesquisa.getStatus().equals(new Long(99))) {
				query.setParameter("status", pesquisa.getStatus());
			}

			Page page = new Page(pesquisa.getPage(), pesquisa.getRows());
			page.paginate(query);

			listaAgendamento = query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("GuicheDao || Get Fila do dia", StringUtil.convertObjectToJson(pesquisa), e);
		}
		return listaAgendamento;
	}

	public List<AgendamentoMobile> getFilaEmAndamento(PesquisaGenericDto dto) {

		List<AgendamentoMobile> listAgendamento = new ArrayList<AgendamentoMobile>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT A FROM AgendamentoMobile A ");
		sql.append("     WHERE TO_CHAR(A.dataAgendamento, 'mm/dd/yyyy') = TO_CHAR(:hoje, 'mm/dd/yyyy') ");
		sql.append(" 	 AND  A.funcionario.id = :idFuncionario ");
		sql.append(" 	 AND  A.idDepartamento = :idUnidade ");
		sql.append(" 	 AND  A.status.id IN (7, 9) ");
		sql.append("ORDER BY A.dataAgendamento");

		try {
			TypedQuery<AgendamentoMobile> query = em.createQuery(sql.toString(), AgendamentoMobile.class);
			query.setParameter("idFuncionario", dto.getIdFuncionario());
			query.setParameter("idUnidade", dto.getUnidadeAtendimento());
			query.setParameter("hoje", new Date());

			listAgendamento = query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("GuicheDao || Get Fila em Andamento", StringUtil.convertObjectToJson(dto), e);
		}

		return listAgendamento;

	}

	public List<AgendamentoMobile> getListaAtendidosDoDia(PesquisaGenericDto pesquisa, Long idFuncionario) {

		List<AgendamentoMobile> listAgendamento = new ArrayList<AgendamentoMobile>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT A FROM AgendamentoMobile A ");
		sql.append("     WHERE TO_CHAR(A.dataAgendamento, 'mm/dd/yyyy') = TO_CHAR(:hoje, 'mm/dd/yyyy') ");
		sql.append(" 	 AND  A.idDepartamento = :idUnidade ");
		sql.append(" 	 AND  A.status.id = 2 ");
		sql.append(" 	 AND  A.funcionario.id = :idFuncionario ");
		sql.append("ORDER BY A.dataAgendamento");

		try {
			TypedQuery<AgendamentoMobile> query = em.createQuery(sql.toString(), AgendamentoMobile.class);
			query.setParameter("idUnidade", pesquisa.getUnidadeAtendimento());
			query.setParameter("idFuncionario", idFuncionario);
			query.setParameter("hoje", new Date());

			listAgendamento = query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("GuicheDao || Get Lista Atendimento do dia", StringUtil.convertObjectToJson(pesquisa), e);
		}

		return listAgendamento;
	}

	public void confirmarPresenca(AgendamentoDto dto, Long idFuncionario) {

		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE  MOB_AGENDAMENTO M ");
		sql.append("     SET M.FK_STATUS_AGENDAMENTO = 8, ");
		sql.append("         M.HORARIO_CHEGADA = SYSDATE,    ");
		sql.append("         M.FK_ID_FUNCIONARIO = :idFuncionario  ");
		sql.append("	 WHERE M.ID = :idAtendimento ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idAtendimento", dto.getId());
			query.setParameter("idFuncionario", idFuncionario);

			query.executeUpdate();
		} catch (Throwable e) {
			httpGoApi.geraLog("GuicheDao || Confirmar Presenca", StringUtil.convertObjectToJson(dto + "---" + idFuncionario), e);
		}

	}

	public void chamarCliente(AgendamentoDto dto, Long idFuncionario) {

		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE  MOB_AGENDAMENTO M ");
		sql.append("     SET M.FK_STATUS_AGENDAMENTO = 9, ");
		sql.append("         M.DATA_ATUAL = SYSDATE,    ");
		sql.append("         M.FK_ID_FUNCIONARIO = :idFuncionario  ");
		sql.append("	 WHERE M.ID = :idAtendimento ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idAtendimento", dto.getId());
			query.setParameter("idFuncionario", idFuncionario);

			query.executeUpdate();
		} catch (Throwable e) {
			httpGoApi.geraLog("GuicheDao || Chamar Cliente", StringUtil.convertObjectToJson(dto + "----" + idFuncionario), e);
		}

	}

	public void iniciarAtendimento(AgendamentoDto dto) {

		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE  MOB_AGENDAMENTO M ");
		sql.append("     SET M.FK_STATUS_AGENDAMENTO = 7, ");
		sql.append("         M.HORARIO_INICIO = SYSDATE,    ");
		sql.append("         M.FK_ID_FUNCIONARIO = :idFuncionario,  ");
		sql.append("         M.GUICHE = :idGuiche  ");
		sql.append("	 WHERE M.ID = :idAtendimento ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idAtendimento", dto.getId());
			query.setParameter("idGuiche", dto.getGuiche());
			query.setParameter("idFuncionario", dto.getIdFuncionario());

			query.executeUpdate();
		} catch (Throwable e) {
			httpGoApi.geraLog("GuicheDao || Iniciar Atendimento", StringUtil.convertObjectToJson(dto), e);
		}

	}

	public void liberarChamada(AgendamentoDto dto) {

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT A FROM AgendamentoMobile A ");
		sql.append(" 	 WHERE  A.id = :id ");

		TypedQuery<AgendamentoMobile> query = em.createQuery(sql.toString(), AgendamentoMobile.class);
		query.setParameter("id", dto.getId());

		AgendamentoMobile agendamento = new AgendamentoMobile();
		agendamento = query.getSingleResult();

		StringBuilder sql2 = new StringBuilder();
		sql2.append("UPDATE  MOB_AGENDAMENTO M ");

		if (agendamento.getHorarioChegada() == null) {
			sql2.append("   SET M.FK_STATUS_AGENDAMENTO = 1, ");
		} else {
			sql2.append("   SET M.FK_STATUS_AGENDAMENTO = 8, ");
		}

		sql2.append("           M.DATA_ATUAL = SYSDATE,    ");
		sql2.append("           M.FK_ID_FUNCIONARIO = :idFuncionario  ");
		sql2.append("	    WHERE M.ID = :idAtendimento ");

		try {
			Query query2 = em.createNativeQuery(sql2.toString());
			query2.setParameter("idFuncionario", dto.getIdFuncionario());
			query2.setParameter("idAtendimento", dto.getId());

			query2.executeUpdate();
		} catch (Throwable e) {
			httpGoApi.geraLog("GuicheDao || Liberar Chamada", StringUtil.convertObjectToJson(dto), e);
		}

	}

	public void finalizarAtendimento(AgendamentoDto dto, Long idFuncionario) {

		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE  MOB_AGENDAMENTO M ");
		sql.append("     SET M.FK_STATUS_AGENDAMENTO = 2, ");
		sql.append("         M.DATA_ATUAL = SYSDATE,    ");
		sql.append("         M.FK_ID_FUNCIONARIO = :idFuncionario,  ");
		sql.append("         M.GUICHE = :idGuiche  ");
		sql.append("	 WHERE M.ID = :idAtendimento ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idAtendimento", dto.getId());
			query.setParameter("idGuiche", dto.getGuiche());
			query.setParameter("idFuncionario", idFuncionario);

			query.executeUpdate();
		} catch (Throwable e) {
			httpGoApi.geraLog("GuicheDao || Finalizar Atendimento", StringUtil.convertObjectToJson(dto), e);
		}

	}

	public void cancela(AgendamentoDto dto, Long idFuncionario) {

		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE  MOB_AGENDAMENTO M ");
		sql.append("     SET M.FK_STATUS_AGENDAMENTO = 6, ");
		sql.append("         M.GUICHE = :guiche,    ");
		sql.append("         M.DATA_ATUAL = SYSDATE,    ");
		sql.append("         M.FK_ID_FUNCIONARIO = :idFuncionario  ");
		sql.append("	 WHERE M.ID = :idAtendimento ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idAtendimento", dto.getId());
			query.setParameter("guiche", dto.getGuiche());
			query.setParameter("idFuncionario", idFuncionario);

			query.executeUpdate();
		} catch (Throwable e) {
			httpGoApi.geraLog("GuicheDao || Cancela", StringUtil.convertObjectToJson(dto), e);
		}

	}

	public void marcarAusencia(AgendamentoDto dto) {

		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE  MOB_AGENDAMENTO M ");
		sql.append("     SET M.FK_STATUS_AGENDAMENTO = 3, ");
		sql.append("         M.DATA_ATUAL = SYSDATE,    ");
		sql.append("         M.FK_ID_FUNCIONARIO = :idFuncionario  ");
		sql.append("	 WHERE M.ID = :idAtendimento ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idAtendimento", dto.getId());
			query.setParameter("idFuncionario", dto.getIdFuncionario());

			query.executeUpdate();
		} catch (Throwable e) {
			httpGoApi.geraLog("GuicheDao || Marcar Ausencia", StringUtil.convertObjectToJson(dto), e);
		}

	}

	public boolean clienteFoiCapturado(AgendamentoDto dto) {

		boolean resposta = false;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT A FROM AgendamentoMobile A ");
		sql.append(" 	 WHERE  A.id = :id ");

		try {

			TypedQuery<AgendamentoMobile> query = em.createQuery(sql.toString(), AgendamentoMobile.class);
			query.setParameter("id", dto.getId());

			AgendamentoMobile agendamento = new AgendamentoMobile();
			agendamento = query.getSingleResult();

			if (agendamento.getStatus().getId() == new Long(7)) {
				resposta = true;
			} else {
				resposta = false;
			}

		} catch (Throwable e) {
			httpGoApi.geraLog("GuicheDao || Cliente Foi Capturado", StringUtil.convertObjectToJson(dto), e);
		}
		return resposta;
	}

	public boolean clienteFoiChamado(AgendamentoDto dto) {

		boolean resposta = false;

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT A FROM AgendamentoMobile A ");
		sql.append(" 	 WHERE  A.id = :id ");
		sql.append(" 	 AND  A.funcionario.id <> :idFuncionario ");

		try {

			TypedQuery<AgendamentoMobile> query = em.createQuery(sql.toString(), AgendamentoMobile.class);
			query.setParameter("id", dto.getId());
			query.setParameter("idFuncionario", dto.getFuncionario().getId());

			AgendamentoMobile agendamento = new AgendamentoMobile();
			agendamento = query.getSingleResult();

			if (agendamento.foiChamado()) {
				resposta = true;
			} else {
				resposta = false;
			}
		} catch (NoResultException e) {
			return false;
		} catch (Throwable e) {
			httpGoApi.geraLog("GuicheDao || Cliente Foi Chamado", StringUtil.convertObjectToJson(dto), e);
		}
		return resposta;
	}

	public List<AgendamentoMobile> filtroPesquisa(PesquisaGenericDto pesquisa) {

		List<AgendamentoMobile> listaAgendamento = new ArrayList<AgendamentoMobile>();
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT A FROM AgendamentoMobile A WHERE 1=1 ");
		queryFiltroIndicadores(pesquisa, sql);

		try {
			TypedQuery<AgendamentoMobile> query = em.createQuery(sql.toString(), AgendamentoMobile.class);

			parametrosFiltroIndicadores(pesquisa, query);

			Page page = new Page(pesquisa.getPage(), pesquisa.getRows());
			page.paginate(query);

			listaAgendamento = query.getResultList();

		} catch (Throwable e) {
			httpGoApi.geraLog("GuicheDao || Pesquisa Filtro", StringUtil.convertObjectToJson(pesquisa), e);
		}

		return listaAgendamento;
	}

	public List<AgendamentoMobile> filtroPesquisaSemPaginacao(PesquisaGenericDto pesquisa) {

		List<AgendamentoMobile> listaAgendamento = new ArrayList<AgendamentoMobile>();
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT A FROM AgendamentoMobile A WHERE 1=1 ");
		queryFiltroIndicadores(pesquisa, sql);

		try {
			TypedQuery<AgendamentoMobile> query = em.createQuery(sql.toString(), AgendamentoMobile.class);

			parametrosFiltroIndicadores(pesquisa, query);

			listaAgendamento = query.getResultList();

		} catch (Throwable e) {
			httpGoApi.geraLog("GuicheDao || Pesquisa Filtro", StringUtil.convertObjectToJson(pesquisa), e);
		}

		return listaAgendamento;
	}

	public int pesquisaTotalFiltroIndicadores(PesquisaGenericDto pesquisa) {

		List<AgendamentoMobile> listaAgendamento = new ArrayList<AgendamentoMobile>();
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT A FROM AgendamentoMobile A WHERE 1=1 ");
		queryFiltroIndicadores(pesquisa, sql);

		try {
			TypedQuery<AgendamentoMobile> query = em.createQuery(sql.toString(), AgendamentoMobile.class);

			parametrosFiltroIndicadores(pesquisa, query);

			listaAgendamento = query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("GuicheDao || pesquisaTotalFiltroIndicadores", StringUtil.convertObjectToJson(pesquisa), e);
		}

		return listaAgendamento.size();
	}

	private void parametrosFiltroIndicadores(PesquisaGenericDto pesquisa, TypedQuery<AgendamentoMobile> query) {
		if (!pesquisa.getStatus().equals(new Long(99))) {
			query.setParameter("status", pesquisa.getStatus());
		}
		query.setParameter("idUnidade", pesquisa.getUnidadeAtendimento());
		query.setParameter("dataInicio", pesquisa.getDataInicio());
		query.setParameter("dataFim", pesquisa.getDataFim());
	}

	private void queryFiltroIndicadores(PesquisaGenericDto pesquisa, StringBuilder sql) {
		if (!pesquisa.getStatus().equals(new Long(99))) {
			sql.append(" AND  A.status.id = :status ");
		}
		sql.append(" AND  A.idDepartamento = :idUnidade ");
		sql.append(" AND  TO_CHAR(A.dataAgendamento, 'yyyymmdd')  >=  TO_CHAR(:dataInicio, 'yyyymmdd') ");
		sql.append(" AND  TO_CHAR(A.dataAgendamento, 'yyyymmdd')  <=  TO_CHAR(:dataFim, 'yyyymmdd') ");
		sql.append("ORDER BY A.dataAgendamento, A.dataUpdate");
	}

	public List<AgendamentoMobile> filtroPesquisaRecepcao(PesquisaAtendimentoDto pesquisa) {
		List<AgendamentoMobile> listaAgendamento = new ArrayList<AgendamentoMobile>();
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT A FROM AgendamentoMobile A WHERE  1= 1");
		queryFiltroRecepcao(pesquisa, sql);

		try {
			TypedQuery<AgendamentoMobile> query = em.createQuery(sql.toString(), AgendamentoMobile.class);

			parametrosFiltroRecepcao(pesquisa, query);
			Page page = new Page(pesquisa.getPage(), pesquisa.getRows());
			page.paginate(query);
			
			listaAgendamento = query.getResultList();

		} catch (Throwable e) {
			httpGoApi.geraLog("GuicheDao || Filtro Pesquisa Recepcao", StringUtil.convertObjectToJson(pesquisa), e);
		}

		return listaAgendamento;
	}

	private void parametrosFiltroRecepcao(PesquisaAtendimentoDto pesquisa, TypedQuery<AgendamentoMobile> query) {
		
		if (!pesquisa.getStatus().equals(new Long(99))) {
			query.setParameter("status", pesquisa.getStatus());
		}
		if (pesquisa.temSenha()){
			query.setParameter("senha", pesquisa.getSenha());
		}
		if (pesquisa.temNome()){
			query.setParameter("nomePessoa", "%" + pesquisa.getNomePessoa().toUpperCase() + "%");
		}
		if (pesquisa.temCpfOuCnpj()){
			query.setParameter("cpfOuCnpj","%" + pesquisa.getCpfOuCnpj() + "%" );
		}
		query.setParameter("idDepartamento", pesquisa.getIdDepartamento());

	}

	private void queryFiltroRecepcao(PesquisaAtendimentoDto pesquisa, StringBuilder sql) {
		if (!pesquisa.getStatus().equals(new Long(99))) {
			sql.append(" AND  A.status.id = :status ");
		}
		if (pesquisa.temSenha()){
			sql.append(" AND  A.senha = :senha ");
		}
		if (pesquisa.temNome()){
			sql.append(" AND upper(A.nome) LIKE :nomePessoa");
		}
		if (pesquisa.temCpfOuCnpj()){
			sql.append(" AND A.cpfOuCnpj LIKE :cpfOuCnpj");
		}
		sql.append(" AND  A.idDepartamento = :idDepartamento ");
		sql.append(" AND  TO_CHAR(A.dataAgendamento, 'mm/dd/yyyy') = TO_CHAR( SYSDATE, 'mm/dd/yyyy' )");
	}

	public boolean temAtendimentoAindaEmAberto(Long idFuncionario) {
		
		List<AgendamentoMobile> listaAgendamento = new ArrayList<AgendamentoMobile>();
		
		StringBuilder sql = new StringBuilder();

		sql.append(" SELECT A FROM AgendamentoMobile A WHERE 1=1 ");
		sql.append(" AND TO_CHAR(A.dataAgendamento, 'mm/dd/yyyy') = TO_CHAR( SYSDATE, 'mm/dd/yyyy' ) ");
		sql.append(" AND A.funcionario.id = :idFuncionario ");	
		sql.append(" AND A.status.id IN (7, 9) ");
		
		try {
			TypedQuery<AgendamentoMobile> query = em.createQuery(sql.toString(), AgendamentoMobile.class);

			query.setParameter("idFuncionario", idFuncionario );
			listaAgendamento = query.getResultList();

		} catch (Throwable e) {
			httpGoApi.geraLog("GuicheDao || Tem Atendimento Ainda Em Aberto", StringUtil.convertObjectToJson(idFuncionario), e);
		}
		
				
		return listaAgendamento.isEmpty() ? false : true;
	}
}