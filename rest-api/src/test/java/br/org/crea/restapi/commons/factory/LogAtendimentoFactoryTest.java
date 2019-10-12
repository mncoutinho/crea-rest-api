package br.org.crea.restapi.commons.factory;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.dao.atendimento.AtendimentoDao;
import br.org.crea.commons.dao.atendimento.AtendimentoLogDao;
import br.org.crea.commons.dao.financeiro.BoletoDao;
import br.org.crea.commons.dao.financeiro.FinGeradorNossoNumeroDao;
import br.org.crea.commons.models.atendimento.enuns.TipoAtendimentoEnum;
import br.org.crea.commons.models.cadastro.enuns.DepartamentoEnum;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.models.corporativo.pessoa.Funcionario;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;
import br.org.crea.commons.models.portal.Atendimento;
import br.org.crea.commons.models.portal.AtendimentoLog;

public class LogAtendimentoFactoryTest {

	private EntityManagerFactory emf;
	
	private EntityManager em;
	private AtendimentoLogDao atendimentoLogDao;
	private AtendimentoDao atendimentoDao;
	
	@Before
	public void setup() {
		 emf = Persistence.createEntityManagerFactory("dsCreaTest");
		 em = emf.createEntityManager();

		 atendimentoLogDao = new AtendimentoLogDao();
		 atendimentoDao = new AtendimentoDao();
		 atendimentoLogDao.setEntityManager(em);
		 atendimentoDao.setEntityManager(em);
	}
	
	@After
	public void finishTest() {
		em.close();
		emf.close();
	}
	
	@Test
	public void cadastraBoletoTaxaDeSegundaViaFactoryTest () {
		UserFrontDto usuario = new UserFrontDto();
		usuario.setIdPessoa(1970101593L);
		usuario.setIp("127.0.0.1");
		
		this.cadastraLogAtendimento(usuario, TipoAtendimentoEnum.SOLICITACAO_SEGUNDA_VIA_CARTEIRA);
	}
	
	
	public Atendimento cadastraAtendimento(UserFrontDto usuario) {
		
		Atendimento atendimento = new Atendimento();
		
		try {					
			atendimento.setDataInicioAtendimento(new Date());
			
			Pessoa pessoaAtendida = new Pessoa();
			pessoaAtendida.setId(usuario.getIdPessoa());
			atendimento.setPessoaAtendida(pessoaAtendida);
			atendimento.setFuncionario(Funcionario.getUsuarioPortal());
			atendimento.setNumeroAtendimento(getNumeroAtendimento(atendimento.getFuncionario().getId()));
			atendimento.setIpMaquina(usuario.getIp());
			atendimento.setIdDepartamento(DepartamentoEnum.ATENDIMENTO_PORTAL.getId());			
			
			atendimentoDao.createTransaction();
			atendimento = atendimentoDao.create(atendimento);
			atendimentoDao.commitTransaction();
			
		} catch (Throwable e) {
	//		httpGoApi.geraLog("LogAtendimentoFactory || cadastraAtendimento", StringUtil.convertObjectToJson(usuario), e);
		}
		return atendimento;
	}
	
	private Long getNumeroAtendimento(Long idFuncionario) {
		Calendar hoje = Calendar.getInstance();
		String numeroAtendimento = "" + 
				hoje.get(Calendar.YEAR) + "" + idFuncionario + 
			   (hoje.get(Calendar.MONTH)+1) + "" + 
				hoje.get(Calendar.DAY_OF_MONTH) + "" + 
				hoje.get(Calendar.HOUR_OF_DAY) + "" + 
				hoje.get(Calendar.MINUTE)+""+hoje.get(Calendar.SECOND);
		
		return Long.parseLong(numeroAtendimento);
	}
	
	public void cadastraLogAtendimento(UserFrontDto usuario, TipoAtendimentoEnum tipoAtendimento) {
		
		Atendimento atendimento = cadastraAtendimento(usuario);
		AtendimentoLog log = new AtendimentoLog();
		
		try {			
			log.setAtendimento(atendimento);
			log.setTipoAtendimento(tipoAtendimento.getObjeto());
			log.setHoraAtendimento(new Date());
		  
			atendimentoLogDao.createTransaction();
			atendimentoLogDao.create(log);
			atendimentoLogDao.commitTransaction();
		
		} catch (Throwable e) {
	//		httpGoApi.geraLog("LogAtendimentoFactory || cadastraLogAtendimentoSolicitacaoSegundaViaCarteira", StringUtil.convertObjectToJson(usuario), e);
		}
	  
	}
}
