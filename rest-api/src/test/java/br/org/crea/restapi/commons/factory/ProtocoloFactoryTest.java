package br.org.crea.restapi.commons.factory;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.dao.corporativo.GeradorSequenciaDao;
import br.org.crea.commons.dao.protocolo.MovimentoDao;
import br.org.crea.commons.dao.protocolo.ProtocoloDao;
import br.org.crea.commons.models.cadastro.Departamento;
import br.org.crea.commons.models.cadastro.enuns.DepartamentoEnum;
import br.org.crea.commons.models.commons.Movimento;
import br.org.crea.commons.models.commons.Protocolo;
import br.org.crea.commons.models.commons.SituacaoProtocolo;
import br.org.crea.commons.models.corporativo.Assunto;
import br.org.crea.commons.models.corporativo.enuns.TipoPessoa;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;
import br.org.crea.commons.models.protocolo.enuns.TipoAssuntoProtocoloEnum;
import br.org.crea.commons.models.protocolo.enuns.TipoProtocoloEnum;

public class ProtocoloFactoryTest {

	private EntityManagerFactory emf;
	
	private EntityManager em;
	private ProtocoloDao protocoloDao;
	private MovimentoDao movimentoDao;
	private GeradorSequenciaDao geradorSequenciaDao;
	
	@Before
	public void setup() {
		 emf = Persistence.createEntityManagerFactory("dsCreaTest");
		 em = emf.createEntityManager();

		 protocoloDao = new ProtocoloDao();
		 movimentoDao = new MovimentoDao();
		 geradorSequenciaDao = new GeradorSequenciaDao();
		 geradorSequenciaDao.setEntityManager(em);
		 protocoloDao.setEntityManager(em);
		 movimentoDao.setEntityManager(em);
	}
	
	@After
	public void finishTest() {
		em.close();
		emf.close();
	}
	
	@Test
	public void geraProtocoloSegundaViaFactoryTest () {
		
		this.cadastraProtocoloSegundaViaDeCarteira(1970101593L, 23020401L);
	}
	
	
	public void cadastraProtocoloSegundaViaDeCarteira(Long idPessoa, Long idDepartamento) {
		
		try {
			Protocolo protocolo = new Protocolo();
			geradorSequenciaDao.createTransaction();
			Long idProtocolo = geradorSequenciaDao.getSequenciaWithFlush(TipoProtocoloEnum.PROTOCOLO.getDigito());
			geradorSequenciaDao.commitTransaction();
			
			System.out.println("Protocolo: " + idProtocolo);
			
			protocolo.setIdProtocolo(idProtocolo);
			
			protocolo.setNumeroProtocolo(idProtocolo);
			protocolo.setNumeroProcesso(idPessoa);
			
			protocolo.setIdUnidadeAtendimento(idDepartamento); // VERIFICAR QUAL DEPT DE UNIDADE ATD E QUAL DE ORIGEM
			Pessoa pessoa = new Pessoa();
			pessoa.setId(idPessoa);
			protocolo.setPessoa(pessoa);
			protocolo.setInteressado(idPessoa != null ? String.valueOf(idPessoa) : null);
			
			Assunto assunto = new Assunto();
			assunto.setId(TipoAssuntoProtocoloEnum.SEGUNDA_VIA_CARTEIRA.getId());
		    protocolo.setAssunto(assunto);
			protocolo.setDataEmissao(new Date());
			protocolo.setObservacao(" - Local escolhido para retirada do documento: " + ""); // nome do departamento
			protocolo.setIdFuncionario(99990L);
			protocolo.setTipoPessoa(TipoPessoa.PROFISSIONAL);
			
			protocoloDao.createTransaction();
			protocoloDao.create(protocolo);
			protocoloDao.commitTransaction();
			
			
			Movimento movimento = new Movimento();
			
			Departamento departamentoOrigem = new Departamento();
			departamentoOrigem.setId(idDepartamento);
			movimento.setDepartamentoOrigem(departamentoOrigem);
			
			Departamento departamentoDestinoArquivoVirtual = new Departamento();
			departamentoDestinoArquivoVirtual.setId(DepartamentoEnum.ARQUIVO_VIRTUAL.getId());
			movimento.setDepartamentoDestino(departamentoDestinoArquivoVirtual);
			movimento.setIdFuncionarioRemetente(99990L);
			movimento.setIdFuncionarioReceptor(99990L);
			movimento.setDataRecebimento(new Date());
			SituacaoProtocolo situacao = new SituacaoProtocolo();
			situacao.setId(0L); //FIXME criar enum
			movimento.setSituacao(situacao);
			
			movimento.setProtocolo(protocolo);
			
			movimentoDao.createTransaction();
			movimento = movimentoDao.create(movimento);
			movimentoDao.commitTransaction();
			
			
			protocolo.setPrimeiroMovimento(movimento);
			protocolo.setUltimoMovimento(movimento);
			
			protocoloDao.createTransaction();
			protocoloDao.update(protocolo);
			protocoloDao.commitTransaction();
			
			
		} catch (Throwable e) {
//			httpGoApi.geraLog("ProtocoloFactory || geraProtocoloSegundaViaDeCarteira", StringUtil.convertObjectToJson(""), e);
		}
	  
	}
}
