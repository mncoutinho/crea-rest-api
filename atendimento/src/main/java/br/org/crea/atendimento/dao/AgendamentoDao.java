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
import br.org.crea.commons.models.atendimento.dtos.CriaHorariosDto;
import br.org.crea.commons.models.commons.dtos.PesquisaGenericDto;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class AgendamentoDao extends GenericDao<AgendamentoMobile, Serializable> {

	@Inject
	HttpClientGoApi httpGoApi;

	public AgendamentoDao() {
		super(AgendamentoMobile.class);
	}

	public List<AgendamentoMobile> existeAgendamentoDisponivel(AgendamentoDto dto) {

		List<AgendamentoMobile> listaAgendamento = new ArrayList<AgendamentoMobile>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT F FROM AgendamentoMobile F ");
		sql.append(" 	  WHERE F.status.id = 0 ");
		sql.append(" 	  AND TO_CHAR(F.dataAgendamento, 'DD/MM/YYYY HH24:MI') = TO_CHAR(:dataAgendamento, 'DD/MM/YYYY HH24:MI') ");
		sql.append(" 	  AND F.idDepartamento = :idUnidadeAtendimento ");

		try {

			TypedQuery<AgendamentoMobile> query = em.createQuery(sql.toString(), AgendamentoMobile.class);
			query.setParameter("dataAgendamento", dto.getDataAgendamento());
			query.setParameter("idUnidadeAtendimento", dto.getIdUnidadeAtendimento());

			listaAgendamento = query.getResultList();

		} catch (Throwable e) {
			httpGoApi.geraLog("AgendamentoDao || Verifica agendamento Disponivel", StringUtil.convertObjectToJson(dto), e);
		}

		return listaAgendamento;

	}

	public boolean podeAgendarHorario(AgendamentoDto dto, String cpfOuCnpj) {

		List<AgendamentoMobile> listaAgendamento = new ArrayList<AgendamentoMobile>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT F FROM AgendamentoMobile F ");
		sql.append(" 	  WHERE TO_CHAR(F.dataAgendamento, 'DD/MM/YYYY') = TO_CHAR(:dataAgendamento, 'DD/MM/YYYY') ");
		sql.append("      AND F.cpfOuCnpj = :cpfCnpj ");
		sql.append(" 	  AND F.idDepartamento = :idUnidadeAtendimento ");
		sql.append(" 	  AND F.status.id = 1 ");

		try {

			TypedQuery<AgendamentoMobile> query = em.createQuery(sql.toString(), AgendamentoMobile.class);
			query.setParameter("dataAgendamento", dto.getDataAgendamento());
			query.setParameter("idUnidadeAtendimento", dto.getIdUnidadeAtendimento());
			query.setParameter("cpfCnpj", cpfOuCnpj);

			listaAgendamento = query.getResultList();

			return !listaAgendamento.isEmpty() ? false : true;

		} catch (NoResultException e) {
			return true;
		} catch (Throwable e) {
			httpGoApi.geraLog("AgendamentoDao || Verifica agendamento Disponivel", StringUtil.convertObjectToJson(dto), e);
		}

		return false;

	}

	public boolean podeAgendarHorarioNoDia(AgendamentoDto dto) {

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT A FROM AgendamentoMobile A ");
		sql.append(" 	  WHERE A.id = :id ");
		sql.append(" 	  AND  A.status.id = 0 ");

		try {

			TypedQuery<AgendamentoMobile> query = em.createQuery(sql.toString(), AgendamentoMobile.class);
			query.setParameter("id", dto.getId());

			AgendamentoMobile agendamento = new AgendamentoMobile();
			agendamento = query.getSingleResult();
			
			return agendamento != null ? true : false;

		
		} catch (Throwable e) {
			httpGoApi.geraLog("AgendamentoDao || podeAgendarHorarioNoDia", StringUtil.convertObjectToJson(dto), e);
		}
		
		return true;


	}

	
	
	
	public boolean mesDisponivel(CriaHorariosDto dto) {

		List<AgendamentoMobile> listAgendamentoMobile = new ArrayList<AgendamentoMobile>();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT A FROM AgendamentoMobile A ");
		sql.append("      WHERE  TO_CHAR(A.dataAgendamento, 'mm/yyyy') = :mesAno   ");
		sql.append(" 	  AND A.idDepartamento = :idUnidadeAtendimento");

		try {
			TypedQuery<AgendamentoMobile> query = em.createQuery(sql.toString(), AgendamentoMobile.class);
			query.setParameter("mesAno", dto.getMesAno());
			query.setParameter("idUnidadeAtendimento", dto.getIdUnidadeAtendimento());

			listAgendamentoMobile = query.getResultList();

			return listAgendamentoMobile.isEmpty() ? true : false;

		} catch (Throwable e) {
			httpGoApi.geraLog("AgendamentoDao || Valida se existe agendamento", StringUtil.convertObjectToJson(dto), e);
		}

		return false;
	}

	public boolean validaDataAgendamento(PesquisaGenericDto dto) {

		List<AgendamentoMobile> listAgendamentoMobile = new ArrayList<AgendamentoMobile>();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT A FROM AgendamentoMobile A ");
		sql.append("      WHERE  TO_CHAR(A.dataAgendamento,'dd/mm/yyyy') = TO_CHAR(:novaData,'dd/mm/yyyy')   ");
		sql.append(" 	  AND A.idDepartamento = :idUnidadeAtendimento");

		try {
			TypedQuery<AgendamentoMobile> query = em.createQuery(sql.toString(), AgendamentoMobile.class);
			query.setParameter("novaData", dto.getData());
			query.setParameter("idUnidadeAtendimento", dto.getUnidadeAtendimento());

			listAgendamentoMobile = query.getResultList();

			return listAgendamentoMobile.isEmpty() ? true : false;

		} catch (Throwable e) {
			httpGoApi.geraLog("AgendamentoDao || Valida se existe agendamento", StringUtil.convertObjectToJson(dto), e);
		}

		return false;
	}

	public void cancelaAgenadmento(Long idAgendamento) {

		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE MOB_AGENDAMENTO A ");
		sql.append("      SET A.FK_STATUS_AGENDAMENTO = 5, A.DATA_ATUAL = :dataAtual ");
		sql.append(" 	  WHERE A.ID = :idAgendamento ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idAgendamento", idAgendamento);
			query.setParameter("dataAtual", new Date());

			query.executeUpdate();

		} catch (Throwable e) {
			httpGoApi.geraLog("AgendamentoDao || Cancela Agendamento", StringUtil.convertObjectToJson(idAgendamento), e);
		}

	}

	public List<AgendamentoMobile> getAgendadosPor(String cpfOuCnpj) {

		List<AgendamentoMobile> listAgendamento = new ArrayList<AgendamentoMobile>();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT A FROM AgendamentoMobile A ");
		sql.append("   WHERE A.cpfOuCnpj = :cpfOuCnpj  ");
		sql.append(" 	  AND  A.status.id = 1 ");
		sql.append("      ORDER BY A.dataAgendamento ");

		try {
			TypedQuery<AgendamentoMobile> query = em.createQuery(sql.toString(), AgendamentoMobile.class);

			query.setParameter("cpfOuCnpj", cpfOuCnpj);
			listAgendamento = query.getResultList();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("AgendamentoDao || Agendados por Pessoa", StringUtil.convertObjectToJson(cpfOuCnpj), e);
		}

		return listAgendamento;

	}

	public AgendamentoMobile getNomeEmailNaBaseAgendamentoSeExistir(String cpfOuCnpj) {

		List<AgendamentoMobile> listAgendamento = new ArrayList<AgendamentoMobile>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT F FROM AgendamentoMobile F ");
		sql.append("    WHERE F.cpfOuCnpj = :cpfOuCnpj ");

		try {
			TypedQuery<AgendamentoMobile> query = em.createQuery(sql.toString(), AgendamentoMobile.class);
			query.setParameter("cpfOuCnpj", cpfOuCnpj);
			listAgendamento = query.getResultList();

			return !listAgendamento.isEmpty() ? listAgendamento.get(0) : null;

		} catch (Throwable e) {
			httpGoApi.geraLog("AgendamentoDao || Get nome email usuario sem registro", StringUtil.convertObjectToJson(cpfOuCnpj), e);
		}

		return null;

	};

	@SuppressWarnings("unchecked")
	public List<Date> getDisponiveis(String mesAno, Long unidadeAtendimento) {

		List<Date> listDate = new ArrayList<Date>();
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT DATA_AGENDAMENTO from MOB_AGENDAMENTO ");
		sql.append("       where FK_STATUS_AGENDAMENTO = 0 ");
		sql.append("       AND  TO_CHAR(DATA_AGENDAMENTO, 'MM/YYYY') = :mesAno   ");
		sql.append("       AND  TO_CHAR(DATA_AGENDAMENTO, 'MM/DD/YYYY HH24:mi') > TO_CHAR(:hoje, 'MM/DD/YYYY HH24:mi')   ");
		sql.append("       AND  FK_ID_UNIDADE_ATENDIMENTO = :unidadeAtendimento   ");
		sql.append("       GROUP BY DATA_AGENDAMENTO ORDER BY DATA_AGENDAMENTO ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("mesAno", mesAno);
			query.setParameter("unidadeAtendimento", unidadeAtendimento);
			query.setParameter("hoje", new Date());

			return query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("AgendamentoDao || Get horários disponiveis", StringUtil.convertObjectToJson(mesAno + " >> " + unidadeAtendimento), e);
		}

		return listDate;

	}

	public List<AgendamentoMobile> getAgendamentosParaEdicao(PesquisaGenericDto pesquisa) {

		List<AgendamentoMobile> listAgendamento = new ArrayList<AgendamentoMobile>();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT A FROM AgendamentoMobile A ");
		sql.append(" WHERE  TO_CHAR(A.dataAgendamento, 'yyyymmdd')  >=  TO_CHAR(:dataInicio, 'yyyymmdd') ");
		sql.append("   AND  TO_CHAR(A.dataAgendamento, 'yyyymmdd')  <=  TO_CHAR(:dataFim, 'yyyymmdd') ");
		sql.append(" 	 AND  A.idDepartamento = :idUnidade ");
		sql.append(" 	 AND  A.status.id IN (0,1) ");
		sql.append(" ORDER BY A.dataAgendamento");

		try {
			TypedQuery<AgendamentoMobile> query = em.createQuery(sql.toString(), AgendamentoMobile.class);
			Page page = new Page(pesquisa.getPage(), pesquisa.getRows());
			page.paginate(query);
			query.setParameter("idUnidade", pesquisa.getUnidadeAtendimento());
			query.setParameter("dataInicio", pesquisa.getDataInicio());
			query.setParameter("dataFim", pesquisa.getDataFim());

			return query.getResultList();
		
		} catch (Throwable e) {
			httpGoApi.geraLog("AgendamentoDao || Get agendamentos para edicao", StringUtil.convertObjectToJson(pesquisa), e);
		}

		return listAgendamento;

	}

	public List<AgendamentoMobile> getAgendamentosFuturos(PesquisaGenericDto pesquisa) {

		List<AgendamentoMobile> listAgendamento = new ArrayList<AgendamentoMobile>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT A FROM AgendamentoMobile A ");
		sql.append("     WHERE TO_CHAR(A.dataAgendamento, 'yyyy/mm/dd') = TO_CHAR(:data, 'yyyy/mm/dd') ");
		sql.append(" 	 AND  A.idDepartamento = :idUnidade ");
		sql.append(" 	 AND  A.status.id = 1 ");
		sql.append("ORDER BY A.dataAgendamento");

		try {
			TypedQuery<AgendamentoMobile> query = em.createQuery(sql.toString(), AgendamentoMobile.class);
			query.setParameter("idUnidade", pesquisa.getUnidadeAtendimento());
			query.setParameter("data", pesquisa.getDataAtual());

			return query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("AgendamentoDao || Get agendamentos futuros", StringUtil.convertObjectToJson(pesquisa), e);
		}

		return listAgendamento;

	}

	public void agendaCliente(AgendamentoDto dto, UserFrontDto pessoa) {
		StringBuilder sql = new StringBuilder();

		sql.append("UPDATE MOB_AGENDAMENTO MA  ");
		sql.append("       SET MA.FK_STATUS_AGENDAMENTO = 1, ");
		sql.append("       MA.DATA_ATUAL = :dataAtual,  ");
		sql.append("       MA.FK_ID_ASSUNTO_PROTOCOLO = :idAssunto,  ");
		sql.append("       MA.TIPO_CADASTRO = :meioAgendamento,  ");
		sql.append("       MA.FK_ID_UNIDADE_ATENDIMENTO =  :idUnidadeAtendimento,  ");
		sql.append("       MA.TELEFONE =  :telefone,  ");
		sql.append("       MA.SENHA =  :senha,  ");
		sql.append("       MA.CPFCNPJ = :cpfOuCnpj,  ");
		sql.append("       MA.EMAIL = :email , ");

		if (!pessoa.naoTemRegistroNoCrea()) {
			sql.append("       MA.NOME = :nome, ");
			sql.append("       MA.FK_PESSOA = :idPessoa  ");
		} else {
			sql.append("       MA.NOME = :nome ");
		}

		sql.append("    WHERE MA.ID = :idAgendamento ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idAgendamento", dto.getId());
			query.setParameter("dataAtual", new Date());
			query.setParameter("idAssunto", dto.getIdAssunto());
			query.setParameter("meioAgendamento", dto.getMeioAgendamento());
			query.setParameter("idUnidadeAtendimento", dto.getIdUnidadeAtendimento());
			query.setParameter("telefone", dto.getTelefone());
			query.setParameter("senha", dto.getSenha());
			query.setParameter("cpfOuCnpj", pessoa.getCpfOuCnpj());
			query.setParameter("nome", pessoa.getNome());
			query.setParameter("email", pessoa.getEmail());

			if (!pessoa.naoTemRegistroNoCrea()) {
				query.setParameter("idPessoa", pessoa.getIdPessoa());
			}

			query.executeUpdate();

		} catch (Throwable e) {
			httpGoApi.geraLog("AgendamentoDao || agendaCliente", StringUtil.convertObjectToJson(dto) + StringUtil.convertObjectToJson(pessoa), e);
		}

	}

	@SuppressWarnings("unchecked")
	public List<Date> getHorariosDisponiveis(Date dataDoDia, Long unidadeAtendimento) {

		List<Date> horarios = new ArrayList<Date>();
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT DATA_AGENDAMENTO from MOB_AGENDAMENTO ");
		sql.append("       where FK_STATUS_AGENDAMENTO = 0 ");
		sql.append("       AND  TO_CHAR(DATA_AGENDAMENTO, 'MM/DD/YYYY') = TO_CHAR(:dataDoDia, 'MM/DD/YYYY')   ");
		sql.append("       AND  DATA_AGENDAMENTO > sysdate	");
		sql.append("       AND  FK_ID_UNIDADE_ATENDIMENTO = :unidadeAtendimento   ");
		sql.append("       GROUP BY DATA_AGENDAMENTO ORDER BY DATA_AGENDAMENTO ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("dataDoDia", dataDoDia);
			query.setParameter("unidadeAtendimento", unidadeAtendimento);

			return query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("AgendamentoDao || getHorariosDisponiveis", StringUtil.convertObjectToJson(dataDoDia + " >> " + unidadeAtendimento), e);
		}

		return horarios;

	}

	public AgendamentoMobile recuperaHorarioDisponivel(AgendamentoDto dto) {
		
		AgendamentoMobile agendamento = new AgendamentoMobile();
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT A FROM AgendamentoMobile A ");
		sql.append("     WHERE TO_CHAR(A.dataAgendamento, 'yyyy/mm/dd') = TO_CHAR(:data, 'yyyy/mm/dd') ");
		sql.append(" 	 AND  A.idDepartamento = :idUnidade ");
		sql.append(" 	 AND  A.status.id = 0 ");
		sql.append("ORDER BY A.dataAgendamento");

		try {
			TypedQuery<AgendamentoMobile> query = em.createQuery(sql.toString(), AgendamentoMobile.class);
			query.setParameter("idUnidade", dto.getIdUnidadeAtendimento());
			query.setParameter("data", dto.getDataAgendamento());

			agendamento = query.getResultList().get(0);
			
		} catch (Throwable e) {
			httpGoApi.geraLog("AgendamentoDao || recuperaHorarioDisponivel", StringUtil.convertObjectToJson(dto), e);
		}

		return agendamento;
	}
	
	
	public PesquisaGenericDto apagarAgendadosEdicao(PesquisaGenericDto dto) {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" DELETE FROM MOB_AGENDAMENTO A ");
		sql.append("	 WHERE TO_CHAR(A.DATA_AGENDAMENTO, 'yyyymmdd')  >=  TO_CHAR(:dataInicio, 'yyyymmdd')   ");
		sql.append("	 AND TO_CHAR(A.DATA_AGENDAMENTO, 'yyyymmdd')  <=  TO_CHAR(:dataFim, 'yyyymmdd')  ");
		sql.append(" 	 AND  A.FK_ID_UNIDADE_ATENDIMENTO = :idUnidadeAtendimento ");
		sql.append(" 	 AND  A.FK_STATUS_AGENDAMENTO = 0 ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("dataInicio", dto.getDataInicio());
			query.setParameter("dataFim", dto.getDataFim());
			query.setParameter("idUnidadeAtendimento", dto.getUnidadeAtendimento());
			
			query.executeUpdate();

		} catch (Throwable e) {
			httpGoApi.geraLog("AgendamentoDao || apagarAgendadosEdicao", StringUtil.convertObjectToJson(dto),e);
		}
		return dto;
		
	}
}