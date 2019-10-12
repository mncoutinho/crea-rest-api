package br.org.crea.commons.factory.protocolo;

import java.util.Date;

import javax.inject.Inject;

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
import br.org.crea.commons.models.corporativo.pessoa.Funcionario;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;
import br.org.crea.commons.models.protocolo.enuns.SituacaoProtocoloEnum;
import br.org.crea.commons.models.protocolo.enuns.TipoAssuntoProtocoloEnum;
import br.org.crea.commons.models.protocolo.enuns.TipoProtocoloEnum;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

public class ProtocoloFactory {

	@Inject private ProtocoloDao protocoloDao;
	
	@Inject private MovimentoDao movimentoDao;
	
	@Inject private GeradorSequenciaDao geradorSequenciaDao;
	
	@Inject private HttpClientGoApi httpGoApi;
	
	public Long cadastraProtocoloSegundaViaDeCarteira(Long idPessoa, Long idDepartamento, String nomeDepartamento) {
			
		Long idProtocolo = null;
				
		try {
			Protocolo protocolo = new Protocolo();
			idProtocolo = geradorSequenciaDao.getSequenciaWithFlush(TipoProtocoloEnum.PROTOCOLO.getDigito());
			
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
			protocolo.setObservacao(" - Local escolhido para retirada do documento: " + nomeDepartamento);
			protocolo.setIdFuncionario(99990L);
			protocolo.setTipoPessoa(TipoPessoa.PROFISSIONAL);
			
			protocoloDao.create(protocolo);
			
			
			Movimento movimento = new Movimento();
			
			Departamento departamentoOrigem = new Departamento();
			departamentoOrigem.setId(idDepartamento);
			movimento.setDepartamentoOrigem(departamentoOrigem);
			
			movimento.setDepartamentoDestino(DepartamentoEnum.ARQUIVO_VIRTUAL.getObjeto());
			movimento.setIdFuncionarioRemetente(Funcionario.getIdUsuarioPortal());
			movimento.setIdFuncionarioReceptor(Funcionario.getIdUsuarioPortal());
			movimento.setDataRecebimento(new Date());
			SituacaoProtocolo situacao = new SituacaoProtocolo();
			situacao.setId(SituacaoProtocoloEnum.EM_TRAMITACAO.getId());
			movimento.setSituacao(situacao);
			
			movimento.setProtocolo(protocolo);
			
			movimento = movimentoDao.create(movimento);
			
			
			protocolo.setPrimeiroMovimento(movimento);
			protocolo.setUltimoMovimento(movimento);
			
			protocoloDao.update(protocolo);
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ProtocoloFactory || cadastraProtocoloSegundaViaDeCarteira", StringUtil.convertObjectToJson(""), e);
		}
		
		return idProtocolo;
	}
	public Long cadastraProtocoloBaixaQTRT(Long idEmpresa, Long idDepartamento, String nomeDepartamento) {
		
		Long idProtocolo = null;
				
		try {
			Protocolo protocolo = new Protocolo();
			
			idProtocolo = geradorSequenciaDao.getSequenciaWithFlush(TipoProtocoloEnum.PROTOCOLO.getDigito());
			
			
			protocolo.setIdFuncionario(Funcionario.getIdUsuarioPortal());
			protocolo.setNumeroProcesso(idEmpresa);
			
			Pessoa pessoa = new Pessoa();
			pessoa.setId(idEmpresa);
			protocolo.setPessoa(pessoa);
			protocolo.setInteressado(idEmpresa != null ? String.valueOf(idEmpresa) : null);
			protocolo.setTipoPessoa(TipoPessoa.EMPRESA);
			
			Assunto assunto = new Assunto();
			assunto.setId(TipoAssuntoProtocoloEnum.BAIXA_DE_RESPONSAVEL_TECNICO.getId());
			protocolo.setAssunto(assunto);
			
			//SetCointeressado
			//SetTipoCoInteressado
			//SetMotivoCointeressado
			//SetId Departamento destino
			
			protocolo.setIdProtocolo(idProtocolo);			
			protocolo.setNumeroProtocolo(idProtocolo);
			
			protocolo.setIdUnidadeAtendimento(idDepartamento); // VERIFICAR QUAL DEPT DE UNIDADE ATD E QUAL DE ORIGEM

			protocolo.setInteressado(idEmpresa != null ? String.valueOf(idEmpresa) : null);
			
			protocolo.setDataEmissao(new Date());
			protocolo.setObservacao(" - Local escolhido para retirada do documento: " + nomeDepartamento);
			
			protocoloDao.create(protocolo);
			
			
			Movimento movimento = new Movimento();
			
			Departamento departamentoOrigem = new Departamento();
			departamentoOrigem.setId(idDepartamento);
			movimento.setDepartamentoOrigem(departamentoOrigem);
			
			movimento.setDepartamentoDestino(DepartamentoEnum.ARQUIVO_VIRTUAL.getObjeto());
			movimento.setIdFuncionarioRemetente(Funcionario.getIdUsuarioPortal());
			movimento.setIdFuncionarioReceptor(Funcionario.getIdUsuarioPortal());
			movimento.setDataRecebimento(new Date());
			SituacaoProtocolo situacao = new SituacaoProtocolo();
			situacao.setId(SituacaoProtocoloEnum.EM_TRAMITACAO.getId());
			movimento.setSituacao(situacao);
			
			movimento.setProtocolo(protocolo);
			
			movimento = movimentoDao.create(movimento);
			
			
			protocolo.setPrimeiroMovimento(movimento);
			protocolo.setUltimoMovimento(movimento);
			
			protocoloDao.update(protocolo);
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ProtocoloFactory || cadastraProtocoloSegundaViaDeCarteira", StringUtil.convertObjectToJson(""), e);
		}
		
		return idProtocolo;
	}

}
