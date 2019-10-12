package br.org.crea.commons.factory.atendimento;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import br.org.crea.commons.dao.atendimento.AtendimentoDao;
import br.org.crea.commons.dao.atendimento.AtendimentoLogDao;
import br.org.crea.commons.models.atendimento.enuns.TipoAtendimentoEnum;
import br.org.crea.commons.models.cadastro.enuns.DepartamentoEnum;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.models.corporativo.pessoa.Funcionario;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;
import br.org.crea.commons.models.portal.Atendimento;
import br.org.crea.commons.models.portal.AtendimentoLog;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

public class LogAtendimentoFactory {

	@Inject private AtendimentoDao atendimentoDao;
	
	@Inject private AtendimentoLogDao atendimentoLogDao;
	
	@Inject private HttpClientGoApi httpGoApi;
	
	public void cadastraLogAtendimento(UserFrontDto usuario, TipoAtendimentoEnum tipoAtendimento) {
		
		Atendimento atendimento = cadastraAtendimento(usuario);
		AtendimentoLog log = new AtendimentoLog();
		
		try {			
			log.setAtendimento(atendimento);
			log.setTipoAtendimento(tipoAtendimento.getObjeto());
			log.setHoraAtendimento(new Date());
		  
			atendimentoLogDao.create(log);
		
		} catch (Throwable e) {
			httpGoApi.geraLog("LogAtendimentoFactory || cadastraLogAtendimentoSolicitacaoSegundaViaCarteira", StringUtil.convertObjectToJson(usuario), e);
		}
	  
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
			
			atendimento = atendimentoDao.create(atendimento);
		
		} catch (Throwable e) {
			httpGoApi.geraLog("LogAtendimentoFactory || cadastraAtendimento", StringUtil.convertObjectToJson(usuario), e);
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

}
