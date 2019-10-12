package br.org.crea.commons.factory.commons;

import java.util.Date;
import javax.inject.Inject;
import br.org.crea.commons.dao.cadastro.AcoesDao;
import br.org.crea.commons.models.cadastro.Acoes;
import br.org.crea.commons.models.commons.TipoPessoa;
import br.org.crea.commons.models.corporativo.pessoa.Funcionario;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

public class AcaoFactory {
	
	@Inject
	private AcoesDao acoesDao;
	
	@Inject
	private HttpClientGoApi httpGoApi;
	
	public Acoes cadastraAcao(Long idTipoAcao, Long idProfissional, Long idEmpresa) {
		
		Acoes acoes = new Acoes();
		
		try {
			acoes.setFuncionario(Funcionario.getIdUsuarioPortal());
			acoes.setTipoAcao(idTipoAcao);
			acoes.setTipoPessoa(TipoPessoa.PROFISSIONAL.toString());
			acoes.setTipoOutraPessoa(TipoPessoa.EMPRESA.toString());
			acoes.setIdPessoa(idProfissional);
			acoes.setIdOutraPessoa(idEmpresa);
			acoes.setDataAcao(new Date());
			acoesDao.create(acoes);
			
			
		} catch (Throwable e) {
			httpGoApi.geraLog("LogAtendimentoFactory || cadastraAtendimento", StringUtil.convertObjectToJson(idTipoAcao + idProfissional + idEmpresa), e);
		}
		return acoes;
	}
}
