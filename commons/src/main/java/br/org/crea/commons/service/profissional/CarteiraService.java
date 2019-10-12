package br.org.crea.commons.service.profissional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.dao.cadastro.EmailDao;
import br.org.crea.commons.dao.cadastro.pessoa.EnderecoDao;
import br.org.crea.commons.dao.cadastro.pessoa.PessoaDao;
import br.org.crea.commons.dao.cadastro.pessoa.PessoaFisicaDao;
import br.org.crea.commons.dao.cadastro.profissional.CarteiraDao;
import br.org.crea.commons.dao.cadastro.profissional.CarteiraFilaDao;
import br.org.crea.commons.dao.cadastro.profissional.ProfissionalDao;
import br.org.crea.commons.factory.atendimento.LogAtendimentoFactory;
import br.org.crea.commons.factory.commons.EmailFactory;
import br.org.crea.commons.factory.financeiro.BoletoFactory;
import br.org.crea.commons.factory.protocolo.ProtocoloFactory;
import br.org.crea.commons.models.atendimento.enuns.TipoAtendimentoEnum;
import br.org.crea.commons.models.cadastro.CarteiraFila;
import br.org.crea.commons.models.commons.dtos.DomainGenericDto;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.models.corporativo.pessoa.Funcionario;


public class CarteiraService {
		
	@Inject CarteiraDao dao;
	@Inject EmailDao emailDao;
	@Inject EnderecoDao enderecoDao;
	@Inject PessoaDao pessoaDao;
	@Inject PessoaFisicaDao pessoaFisicaDao;
	@Inject ProfissionalDao profissionalDao;
	@Inject ProtocoloFactory protocoloFactory;
	@Inject BoletoFactory boletoFactory;
	@Inject LogAtendimentoFactory logAtendimentoFactory;
	@Inject EmailFactory emailFactory;
	@Inject CarteiraFilaDao carteiraFilaDao;
	

	public List<String> validaSolicitacaoCreaonline(Long idPessoa) {
		List<String> mensagens = new ArrayList<>();
		
		if (!pessoaDao.possuiSituacaoRegistroAtiva(idPessoa)) mensagens.add("Profissional não está ativo.");

		if (!pessoaFisicaDao.possuiFoto(idPessoa)) mensagens.add("Profissional não possui foto.");
		
		if (!pessoaFisicaDao.possuiAssinatura(idPessoa)) mensagens.add("Profissional não possui assinatura.");
		
		if (!dao.ultimaCarteiraAtivaEhDefinitiva(idPessoa)) mensagens.add("Profissional não habilitado para carteira definitiva.");
		
		if (dao.existePedidoNaFilaDeImpressao(idPessoa)) mensagens.add("Existe pedido na fila de Impressão");
		
		if (!profissionalDao.profissionalPossuiRnp(idPessoa)) mensagens.add("Profissional deverá fazer recadastramento. RNP não encontrado.");
		
		if (!emailDao.pessoaPossuiEmailCadastrado(idPessoa)) mensagens.add("Profissional não possui e-mail cadastrado.");
		
		if (!enderecoDao.possuiEnderecoValidoEPostalPor(idPessoa)) mensagens.add("Profissional não possui endereço postal válido.");
		
		return mensagens;
	}

	/** Metodo responsavel pela solicitacao de carteira do Creaonline
	 * 
	 * @param dto - codigo é idDepartamento e descricao é nomeDepartamento
	 * @param usuario
	 * @return
	 */
	public List<String> solicitaCarteiraCreaonline(DomainGenericDto dto, UserFrontDto usuario) {
		
		// Gerar Protocolo
		protocoloFactory.cadastraProtocoloSegundaViaDeCarteira(usuario.getIdPessoa(), dto.getCodigo(), dto.getDescricao());
		
		// Gerar Log Atendimento
		logAtendimentoFactory.cadastraLogAtendimento(usuario, TipoAtendimentoEnum.SOLICITACAO_SEGUNDA_VIA_CARTEIRA);
					
				
		// se carteira setada n tem validade
		if (dao.ultimaCarteiraEhSemValidade(dto.getId())) {
			
			// Gerar Boleto e Divida
			boletoFactory.cadastraBoletoTaxaDeSegundaViaDeCarteira(usuario.getIdPessoa());
			
			
			// gera PDF
			
			
		}
		
		// se tem validade, gera protocolo e envia para fila de impressao
	    else {
			// inserir na fila de carteira
			inserirSolicitacaoCarteiraNaFila(usuario.getIdPessoa(), dto.getCodigo());				
		}
		
		// Gerar PDF do Protocolo
		
		// Gerar PDF do Boleto, se com validade
		
		// Enviar Email para análise interna da solicitação, se for sem validade, para análise
		emailFactory.enviarEmailSolicitacaoSegundaViaDeCarteira(usuario.getIdPessoa());
	
		
		return null;
	}


	private void inserirSolicitacaoCarteiraNaFila(Long idProfissional, Long idUnidade) {
		CarteiraFila carteira = new CarteiraFila();
		
		carteira.setDataCadastro(new Date());
		carteira.setIdProfissional(idProfissional);
		carteira.setIdFuncionario(Funcionario.getIdUsuarioPortal());
		carteira.setIdUnidadeAtendimento(idUnidade);
		
		carteiraFilaDao.create(carteira);
	}

}
