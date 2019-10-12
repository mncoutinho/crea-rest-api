package br.org.crea.commons.helper;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.art.ArtConverter;
import br.org.crea.commons.converter.cadastro.pessoa.EnderecoConverter;
import br.org.crea.commons.docflow.dto.DocflowGenericDto;
import br.org.crea.commons.models.art.ContratoArt;
import br.org.crea.commons.models.art.dtos.ArtDto;
import br.org.crea.commons.models.cadastro.enuns.TipoEventoAuditoria;
import br.org.crea.commons.models.commons.dtos.DocumentoGenericDto;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.models.financeiro.FinDivida;
import br.org.crea.commons.models.protocolo.dtos.JuntadaProtocoloDto;
import br.org.crea.commons.models.protocolo.dtos.SubstituicaoProtocoloDto;
import br.org.crea.commons.models.protocolo.dtos.TramiteDto;
import br.org.crea.commons.models.protocolo.enuns.TipoJuntadaProtocoloEnum;
import br.org.crea.commons.models.protocolo.enuns.TipoProtocoloEnum;
import br.org.crea.commons.models.siacol.ProtocoloSiacol;
import br.org.crea.commons.models.siacol.RlDocumentoProtocoloSiacol;
import br.org.crea.commons.models.siacol.dtos.GenericSiacolDto;
import br.org.crea.commons.util.DateUtils;

/**
 * @author Monique Santos Classe útil para isolar todo bloco de mensagem ou de
 *         texto em geral, cuja a aplicação necessite fazer uso.
 */

public class HelperMessages {

	@Inject
	private ArtConverter artConverter;

	@Inject
	private EnderecoConverter enderecoConverter;

	public String historicoClassificarProtolocoSiacol(Long numeroProtocolo) {

		StringBuilder sb = new StringBuilder();
		sb.append("O Protocolo Número: ");
		sb.append(numeroProtocolo);
		sb.append(" foi classificado para o Assunto: ");
//		sb.append(historico.getAssuntoSiacol().getNome());
		return sb.toString();
	}

	public String protocoloExcluido(Long numeroProtocolo) {
		StringBuilder sb = new StringBuilder();
		sb.append("O Protocolo Número: ");
		sb.append(numeroProtocolo);
		sb.append(" encontra-se excluído e não pode ser movimentado.");
		return sb.toString();
	}

	public String protocoloSubstituido(Long numeroProtocolo) {
		StringBuilder sb = new StringBuilder();
		sb.append("O Protocolo Número: ");
		sb.append(numeroProtocolo);
		sb.append(" encontra-se substituído e não pode ser movimentado.");
		return sb.toString();

	}

	public String protocoloAnexado(Long numeroProtocolo) {
		StringBuilder sb = new StringBuilder();
		sb.append("O Protocolo Número: ");
		sb.append(numeroProtocolo);
		sb.append(" encontra-se anexado e não pode ser movimentado.");
		return sb.toString();

	}

	public String protocoloApensado(Long numeroProtocolo) {
		StringBuilder sb = new StringBuilder();
		sb.append("O Protocolo Número: ");
		sb.append(numeroProtocolo);
		sb.append(" encontra-se apensado e não pode ser movimentado.");
		return sb.toString();

	}

	public String protocoloInventariado(Long numeroProtocolo) {
		StringBuilder sb = new StringBuilder();
		sb.append("O Protocolo Número: ");
		sb.append(numeroProtocolo);
		sb.append(" está sendo inventariado e não pode ser movimentado.");
		return sb.toString();

	}

	public String protocoloOrigemEDestinoIguais(Long numeroProtocolo) {
		StringBuilder sb = new StringBuilder();
		sb.append("O Protocolo Número: ");
		sb.append(numeroProtocolo);
		sb.append(" não pode ser movimentado para onde ele já está.");
		return sb.toString();

	}

	public String protocoloTramitePendenteRecebimento(Long numeroProtocolo) {
		StringBuilder sb = new StringBuilder();
		sb.append("O Protocolo Número: ");
		sb.append(numeroProtocolo);
		sb.append(" possui um trâmite pendente de recebimento e não poderá ser movimentado.");
		return sb.toString();
	}

	public String protocoloTramiteJaRecebido(Long numeroProtocolo) {
		StringBuilder sb = new StringBuilder();
		sb.append("O Protocolo Número: ");
		sb.append(numeroProtocolo);
		sb.append(" já foi recebido pelo departamento.");
		return sb.toString();
	}

	public String protocoloDestinoIndeterminado(Long numeroProtocolo) {
		StringBuilder sb = new StringBuilder();
		sb.append("O Protocolo Número: ");
		sb.append(numeroProtocolo);
		sb.append(
				" não pode ser movimentado. Por favor, entre em contato com a CDOC informando que o destino do protocolo corresponde ao departamento de código 999.");
		return sb.toString();
	}

	public String permissaoFuncionarioUnidadeProtocolo(Long numeroProtocolo) {
		StringBuilder sb = new StringBuilder();
		sb.append("O Protocolo Número: ");
		sb.append(numeroProtocolo);
		sb.append(" não pode ser tramitado com as suas permissões de movimentação.");
		return sb.toString();
	}

	public String arquivoProtocoloRegistroProvisorio(Long numeroProtocolo) {
		StringBuilder sb = new StringBuilder();
		sb.append("O Protocolo Número: ");
		sb.append(numeroProtocolo);
		sb.append(
				" pertence a empresa de registro provisório e não poderá ser arquivado enquanto não houver o lançamento do registro definitivo.");
		return sb.toString();
	}

	public String arquivoProtocoloAutoInfracao(Long numeroProtocolo) {
		StringBuilder sb = new StringBuilder();
		sb.append("O Protocolo Número: ");
		sb.append(numeroProtocolo);
		sb.append(
				" corresponde a um Auto de Infração que não está quitado ou está em recurso. Processo não pode ser movimentado para o arquivo.");
		return sb.toString();
	}

	public String localizacaoEprocesso(Long numeroProtocolo) {
		StringBuilder sb = new StringBuilder();
		sb.append("A localização do protocolo ");
		sb.append(numeroProtocolo);
		sb.append("  no Docflow difere da localização no Corporativo - Fale com o Administrador.");
		return sb.toString();
	}

	public String funcionarioTramiteEprocesso(Long numeroProtocolo) {
		StringBuilder sb = new StringBuilder();
		sb.append("Você não está alocado na mesma unidade do protocolo ");
		sb.append(numeroProtocolo);
		sb.append("  - Fale com o Administrador.");
		return sb.toString();
	}

	public String unidadePadraoFuncionarioEprocesso() {
		StringBuilder sb = new StringBuilder();
		sb.append(
				"A unidade padrão do funcionário no Docflow difere do Corporativo. Entre em contato com o CDOC para providenciar o devido acerto.");
		return sb.toString();
	}

	public String erroTramiteEnvioDocflow(Long numeroProtocolo, String mensagemDocflow) {
		StringBuilder sb = new StringBuilder();
		sb.append("Mensagem do Docflow para o protocolo ");
		sb.append(numeroProtocolo + ": ");
		sb.append(mensagemDocflow);

		return sb.toString();
	}

	public String confirmacaoEnvioProtocolo(Long numeroProtocolo, String departamentoDestino) {
		StringBuilder sb = new StringBuilder();
		sb.append("Protocolo ");
		sb.append(numeroProtocolo);
		sb.append(" tramitado com sucesso para o departamento ");
		sb.append(departamentoDestino);

		return sb.toString();
	}

	public String tramitePendenteRecebimentoDocflow(Long numeroProtocolo) {
		StringBuilder sb = new StringBuilder();
		sb.append("O protocolo ");
		sb.append(numeroProtocolo);
		sb.append(" possui um trâmite pendente de recebimento no Docflow. ");

		return sb.toString();
	}

	public String tramiteRecebidoDocflow(Long numeroProtocolo) {
		StringBuilder sb = new StringBuilder();
		sb.append("O protocolo ");
		sb.append(numeroProtocolo);
		sb.append(" já está recebido no Docflow. ");

		return sb.toString();
	}

	public String eprocessoNaoLiberadoParaTramiteEnvio() {
		StringBuilder sb = new StringBuilder();
		sb.append("Tramite de envio de e-Processos indisponível temporariamente. ");

		return sb.toString();
	}

	public String eprocessoNaoLiberadoParaTramiteRecebimento() {
		StringBuilder sb = new StringBuilder();
		sb.append("Tramite de recebimento de e-Processos indisponível temporariamente. ");

		return sb.toString();
	}

	public String confirmacaoRecebimentoProtocolo(Long numeroProtocolo, String departamento) {
		StringBuilder sb = new StringBuilder();
		sb.append("Protocolo n°: ");
		sb.append(numeroProtocolo);
		sb.append(" recebido com sucesso no departamento ");
		sb.append(departamento);
		sb.append(".");

		return sb.toString();
	}

	public String confirmarInclusaoDividaAtiva() {
		StringBuilder sb = new StringBuilder();
		sb.append("Auto de Infração incluido em Dívida Ativa.");
		return sb.toString();
	}

	public String confirmarNaoInclusaoDividaAtiva() {
		StringBuilder sb = new StringBuilder();
		sb.append("Auto de Infração não incluido na DIATI por não constar divida em aberto no FINANCEIRO.");
		return sb.toString();
	}

	public String protocoloGerenciadoPeloSiacol(Long numeroProtocolo) {
		StringBuilder sb = new StringBuilder();
		sb.append("O protocolo ");
		sb.append(numeroProtocolo);
		sb.append(" está sob gerência do Siacol e só poderá ser tramitado ou recebido pelo seu responsável·");

		return sb.toString();

	}

	public String corpoEmailEnvioSenha(String senha) {
		StringBuilder corpo = new StringBuilder();
		corpo.append("<h3>Envio de senha</h3>");
		corpo.append("<div>");
		corpo.append("<p>Prezado usuário,</p><br><br>");
		corpo.append("<p>Segue abaixo a senha para acessar Portal do Crea-RJ:</p><br>");
		corpo.append("<p><b>" + senha + "</b></p>");
		corpo.append("Em caso de dúvidas, entre em contato com a Central de Atendimento (21) 2179-2007.");

		return corpo.toString();

	}

	public String corpoEmailEnvioTaxaDeIncorporacao(String nome, String numeroArt) {
		StringBuilder corpo = new StringBuilder();
		corpo.append("<p>Prezado Profissional, " + nome + " <p>");
		corpo.append("<p>Constatamos o cadastramento da ART de atividade concluída " + numeroArt
				+ ". Informamos que para a inclusão da ");
		corpo.append(
				"mesma em seu acervo técnico, conforme legislação específica, deverão ser quitadas as taxas da \"ART\" e \"de ");
		corpo.append(
				"incorporação de acervo\", agendar o atendimento e comparecer a uma unidade de atendimento munido da seguinte documentação:</p>");
		corpo.append("<br>");
		corpo.append(
				"<p><b> - Para Inclusão de obra ou serviço concluído (específica ou múltipla) (Normativo disciplinador Resolução 1050/2013 Confea)</b></p>");
		corpo.append("<br>");
		corpo.append("<ul>");
		corpo.append("<li>Requerimento solicitando a inclusão assinado pelo profissional;</li>");
		corpo.append("<li>ART original da obra/serviço assinada pelo contratado e contratante.</li>");
		corpo.append(
				"<li>Documento hábil que comprove a efetiva participação do profissional. Exemplo: atestado da obra/serviço onde conste o nome do profissional. Caso não possua o atestado da obra/serviço, o profissional poderá apresentar um dos seguintes documentos: trabalhos técnicos, correspondências, diários de obras, livros de ordem, ou documento equivalente, emitido pelo contratante principal dos serviços.</li>");
		corpo.append(
				"<li>Cópia do contrato que originou à obra ou serviço. Se o contrato foi verbal, apresentar cópia da Nota Fiscal ou Recibo de Pagamento de Autônomo (RPA) relativo a contratação.</li>");
		corpo.append(
				"<li>Vínculo com a empresa contratada: cópia da carteira de trabalho (CTPS) ou contrato particular de prestação de serviços, que comprove o vínculo do profissional com a empresa contratada, na época de realização dos serviços.</li>");
		corpo.append("</ul>");
		corpo.append("<br>");
		corpo.append(
				"<p>Caso seja apresentado Atestado/Declaração/Certidão emitido em por pessoa física ou jurídica de direito público ou ");
		corpo.append(
				"privado contratante com o objetivo de fazer prova da real participação do profissional, deverá ser discriminado  as");
		corpo.append(
				"atividades técnicas, valores quantidades e prazos e demais dados mínimos exigidos na Resolução 1.025/2009 e Decisão Normativa 085/11.</p>");
		corpo.append("<br>");
		corpo.append(
				"<p>O atestado deverá ser emitido em papel timbrado, no caso de contratante pessoa jurídica de direito público ou privado.</p>");
		corpo.append("<br>");
		corpo.append(
				"<p>Em caso de documento diverso do atestado, com o objetivo de comprovar a real participação do profissional, poderá ");
		corpo.append(
				"ser deferida a incorporação da atividade, entretanto, o mesmo não será objeto de averbação, sendo apenas emitida a certidão de acervo técnico.</p>");
		corpo.append("<br>");
		corpo.append(
				"<p><b> - Para Inclusão de cargo ou função de vínculo extinto (Normativo disciplinador Resolução 1.101/2018 Confea)</b></p>");
		corpo.append("<br>");
		corpo.append("<ul>");
		corpo.append("<li>Formulário da ART devidamente preenchido e assinado;</li>");
		corpo.append(
				"<li>Caso não possua o atestado do vínculo empregatício, documento comprobatório da vinculação do profissional ao quadro técnico da pessoa jurídica, tal como contrato de trabalho anotado na Carteira de Trabalho e Previdência Social – CTPS, contrato de prestação de serviço, livro ou ficha de registro de empregado, contrato social, ata de assembleia ou ato administrativo de nomeação ou designação do qual constem a indicação do cargo ou função técnica, a data de início e de término, bem como a descrição das atividades desenvolvidas pelo profissional;</li>");
		corpo.append(
				"<li>Comprovante de extinção ou alteração de órgão, entidade pública ou empresa, se for o caso;</li>");
		corpo.append("</ul>");
		corpo.append("<br>");
		corpo.append(
				"<p>Caso seja apresentado Atestado/Declaração/Certidão  emitido  por pessoa física ou jurídica de direito público ou ");
		corpo.append(
				"privado contratante, em papel timbrado, com o objetivo de fazer prova da real participação do profissional, deverá ");
		corpo.append(
				"ser discriminado  o(s)  cargo(s) ou função(ões), períodos e demais dados mínimos exigidos na Resolução 1.025/2009 e Decisão Normativa 085/11. </p>");
		corpo.append("<br>");
		corpo.append(
				"<p>Em caso de documento diverso do atestado, com o objetivo de comprovar a real participação do profissional, poderá ");
		corpo.append(
				"ser deferida a incorporação da atividade, entretanto, o mesmo não será objeto de averbação, sendo apenas emitida a certidão de acervo técnico.</p>");
		corpo.append("<br>");
		corpo.append(
				"<p><b> - Para Inclusão de obra ou serviço concluído (específica ou múltipla) OU Inclusão de cargo ou função de vínculo extinto COM emissão de CAT:</b></p>");
		corpo.append("<br>");
		corpo.append(
				"<p>Seguir os passos para solicitação de Certidão de Acervo Técnico no portal eletrônico: Entrar com o login do ");
		corpo.append(
				"profissional; Ir em “outros serviços”; –Emitir taxa de Certidão de Acervo Técnico com averbação de atestado ");
		corpo.append(
				"(selecionar as ARTs referentes ao serviço do atestado). Após esse procedimento será gerado um arquivo .pdf que deverá ser impresso (RC com número de protocolo e taxa de certidão); </p>");
		corpo.append("<br>");
		corpo.append("<ul>");
		corpo.append("<li>Formulário de Requerimento de Certidão (RC) preenchido e assinado pelo profissional;</li>");
		corpo.append(
				"<li>Cópia da carteira de identidade do profissional solicitante para confirmação da assinatura.</li>");
		corpo.append("<li>Comprovante de pagamento da \"Taxa de Certidão com Averbação\" devida;</li>");
		corpo.append("<li>Formulário das ARTs indicadas para a CAT devidamente preenchidos e assinados;</li>");
		corpo.append(
				"<li>Atestado de capacidade técnica em via original (e envio de versão escaneada para atestados@crea-rj.org.br; OU 2 cópias autenticadas) emitido pelo contratante dos serviços, com período, valores contratuais e demais dados mínimos exigidos na Resolução 1.025/2009 e Decisão Normativa 085/11. Caso o mesmo seja emitido por pessoa jurídica, deverá estar em papel timbrado.</li>");
		corpo.append(
				"<li>Declaração se houve subempreitadas/subcontratações (em caso afirmativo, descrever o nome dos subempreiteiros e os serviços por eles executados, se possível indicar o número das ARTs). <a href=\"http://portalservicos.crea-rj.org.br/#/app/formularios\">Clique aqui e confira em “modelos de declarações” o documento referente à declaração para subcontratação.</a></li>");
		corpo.append("</ul>");
		corpo.append("<br>");
		corpo.append("<br>");
		corpo.append("<p>Atenciosamente,</p>");
		corpo.append("<p>ROSIANE DA SILVA MOULIN CURTI</p>");
		corpo.append("<p>Coordenadora de Acervo Técnico</p>");

		return corpo.toString();

	}

	public String alteraSituacaoProtoco(Long idProtocolo, String descricao) {
		StringBuilder sb = new StringBuilder();
		sb.append("O protocolo n°: ");
		sb.append(idProtocolo);
		sb.append(" alterou sua situação para: ");
		sb.append(descricao);
		return sb.toString();
	}

	public String distribuicaoProtocolo(Long idProtocolo, String nomeResponsavelNovo) {
		StringBuilder sb = new StringBuilder();
		sb.append("O protocolo n°: ");
		sb.append(idProtocolo);
		sb.append(" foi distribuido para o responsável: ");
		sb.append(nomeResponsavelNovo);
		return sb.toString();
	}

	public String classificacaoProtocolo(Long idProtocolo, String descricao) {

		StringBuilder sb = new StringBuilder();
		sb.append("O protocolo n°: ");
		sb.append(idProtocolo);
		sb.append(" foi classificado para ");
		sb.append(descricao);
		return sb.toString();

	}

	public String alterarAssuntoSiacolProtocolo(Long idProtocolo, String descricao) {

		StringBuilder sb = new StringBuilder();
		sb.append("O protocolo n°: ");
		sb.append(idProtocolo);
		sb.append(" foi classificado para o assunto ");
		sb.append(descricao);
		return sb.toString();

	}

	public String alterarDocumento(String nome, Long idDocumento) {
		StringBuilder sb = new StringBuilder();
		sb.append("O usuário : ");
		sb.append(nome);
		sb.append(" alterou o documento n°: ");
		sb.append(idDocumento);
		return sb.toString();
	}

	public String excluirDocumento(String nome, Long idDocumento) {
		StringBuilder sb = new StringBuilder();
		sb.append("O usuário : ");
		sb.append(nome);
		sb.append(" excluiu o documento n°: ");
		sb.append(idDocumento);
		return sb.toString();
	}

	public String cadastrarDocumento(String nome, Long idDocumento) {
		StringBuilder sb = new StringBuilder();
		sb.append("O usuário : ");
		sb.append(nome);
		sb.append(" cadastrou o documento n°: ");
		sb.append(idDocumento);
		return sb.toString();
	}

	public String protocoloPrincipalNaoEncontrado() {
		StringBuilder sb = new StringBuilder();
		sb.append("Não foi possível localizar o protocolo principal informado. ");
		return sb.toString();
	}

	public String protocoloAnexoNaoEncontrado() {
		StringBuilder sb = new StringBuilder();
		sb.append("Não foi possível localizar o protocolo anexo informado. ");
		return sb.toString();
	}

	public String protocoloApensoNaoEncontrado() {
		StringBuilder sb = new StringBuilder();
		sb.append("Não foi possível localizar o protocolo apenso informado. ");
		return sb.toString();
	}

	public String protocoloApensoNaoEncontrado(Long numeroProtocolo) {
		StringBuilder sb = new StringBuilder();
		sb.append("O protocolo a ser apensado número ");
		sb.append(numeroProtocolo);
		sb.append(" não foi encontrado. ");
		return sb.toString();
	}

	public String protocoloJuntadaIgualAoPrincipal() {
		StringBuilder sb = new StringBuilder();
		sb.append("O protocolo informado não pode ser juntado a ele mesmo. ");
		return sb.toString();
	}

	public String protocoloJaAnexado(Long numeroProtocolo, Long numeroProtocoloPaiAnexo,
			TipoJuntadaProtocoloEnum acao) {
		StringBuilder sb = new StringBuilder();
		sb.append("O protocolo número ");
		sb.append(numeroProtocolo);
		sb.append(" da ");
		sb.append(acao.toString());
		sb.append(" já encontra-se anexado ao protocolo ");
		sb.append(numeroProtocoloPaiAnexo);
		sb.append(".");
		return sb.toString();

	}

	public String protocoloPrincipalJaAnexado(Long numeroProtocolo, Long numeroProtocoloPaiAnexo) {
		StringBuilder sb = new StringBuilder();
		sb.append("O protocolo principal número ");
		sb.append(numeroProtocolo);
		sb.append(" já encontra-se anexado ao protocolo ");
		sb.append(numeroProtocoloPaiAnexo);
		sb.append(".");
		return sb.toString();

	}

	public String protocoloJaApensado(Long numeroProtocolo, Long numeroProtocoloPaiAnexo,
			TipoJuntadaProtocoloEnum acao) {
		StringBuilder sb = new StringBuilder();
		sb.append("O protocolo número ");
		sb.append(numeroProtocolo);
		sb.append(" da ");
		sb.append(acao.toString());
		sb.append(" já encontra-se apensado ao protocolo ");
		sb.append(numeroProtocoloPaiAnexo);
		sb.append(".");
		return sb.toString();

	}

	public String protocoloPrincipalJaApensado(Long numeroProtocolo, Long numeroProtocoloPaiApenso) {
		StringBuilder sb = new StringBuilder();
		sb.append("O protocolo principal número ");
		sb.append(numeroProtocolo);
		sb.append(" já encontra-se apensado ao protocolo ");
		sb.append(numeroProtocoloPaiApenso);
		sb.append(".");
		return sb.toString();
	}

	public String permissaoFuncionarioDepartamento(Long numeroProtocolo) {
		StringBuilder sb = new StringBuilder();
		sb.append("O funcionário não possui ");
		sb.append("permissão na unidade do protocolo ");
		sb.append(numeroProtocolo);
		sb.append(".");
		return sb.toString();
	}

	public String protocoloNaoRecebido(Long numeroProtocolo) {
		StringBuilder sb = new StringBuilder();
		sb.append("O protocolo ");
		sb.append(numeroProtocolo);
		sb.append(" não está recebido. ");

		return sb.toString();
	}

	public String protocoloDepartamentoDiferente(Long protocoloPrincipal, Long protocoloSecundario) {
		StringBuilder sb = new StringBuilder();
		sb.append("O protocolo ");
		sb.append(protocoloPrincipal);
		sb.append(" não é do mesmo departamento do protocolo ");
		sb.append(protocoloSecundario);
		sb.append(".");

		return sb.toString();
	}

	public String protocoloPrincipalDigitalizado(TipoJuntadaProtocoloEnum acao) {
		StringBuilder sb = new StringBuilder();
		sb.append("O protocolo principal da ");
		sb.append(acao.toString());
		sb.append(" já foi digitalizado e o anexo não consta como digitalizado. ");

		return sb.toString();

	}

	public String protocoloDaJuntadaDigitalizado(TipoJuntadaProtocoloEnum acao) {
		StringBuilder sb = new StringBuilder();
		sb.append("O protocolo da ");
		sb.append(acao.toString());
		sb.append(" já foi digitalizado e o principal não consta como digitalizado. ");

		return sb.toString();

	}

	public String protocoloAnexacaoIndevida() {
		StringBuilder sb = new StringBuilder();
		sb.append("Este tipo de protocolo (tipo 7 e processo 0) não pode ser anexado, somente apensado. ");

		return sb.toString();

	}

	public String processoInicialAnexacao() {
		StringBuilder sb = new StringBuilder();
		sb.append("O protocolo principal deve ser o processo inicial. ");

		return sb.toString();
	}

	public String protocolosProcessosDiferentes() {
		StringBuilder sb = new StringBuilder();
		sb.append("Os protocolos são de processos diferentes. ");

		return sb.toString();
	}

	public String tipoJuntadaNaoInformada() {
		StringBuilder sb = new StringBuilder();
		sb.append("O tipo de ação não foi informada. Contate o Administrador do sistema. ");

		return sb.toString();
	}

	public String moduloSistemaNaoInformado() {
		StringBuilder sb = new StringBuilder();
		sb.append(
				"O módulo do sistema que está consumindo o serviço não foi informado. Contate o Administrador do sistema. ");

		return sb.toString();
	}

	public String anexoDesvinculadoDoPrincipal(Long numeroProcessoAnexo) {
		StringBuilder sb = new StringBuilder();
		sb.append("O protocolo só poderá ser anexado ao processo ao qual está vinculado. ");
		sb.append("(");
		sb.append(numeroProcessoAnexo);
		sb.append(")");

		return sb.toString();
	}

	public String protocolosAnexacaoTipoSete() {
		StringBuilder sb = new StringBuilder();
		sb.append("Protocolo do tipo 7 somente pode receber anexo deste mesmo tipo.");

		return sb.toString();
	}

	public String permissaoProtocoloParaReceberAnexo() {
		StringBuilder sb = new StringBuilder();
		sb.append("O tipo do protocolo principal não permite receber anexo.");

		return sb.toString();
	}

	public String interessadosAnexacao() {
		StringBuilder sb = new StringBuilder();
		sb.append("Os interessados dos protocolos envolvidos não podem ser diferentes.");

		return sb.toString();
	}

	public String transacaoDeAnexo(TipoProtocoloEnum tipoProtocoloPrincipal, TipoProtocoloEnum tipoProtocoloAnexo) {
		StringBuilder sb = new StringBuilder();
		sb.append("O protocolo do tipo ");
		sb.append(tipoProtocoloAnexo.toString());
		sb.append(" não pode ser anexado a protocolo do tipo ");
		sb.append(tipoProtocoloPrincipal.toString());
		return sb.toString();
	}

	public String alocacaoFuncionarioDiferenteProtocoloPrincipal(JuntadaProtocoloDto dto) {
		StringBuilder sb = new StringBuilder();
		sb.append("ATENÇÃO ! Você precisa estar alocado na mesma unidade do protocolo principal da  ");
		sb.append(dto.getTipoJuntadaProtocolo().toString());
		sb.append(". Contate o Administrador. ");
		sb.append("(");
		sb.append(dto.getProtocoloPrincipal().getNumeroProtocolo());
		sb.append(")");
		return sb.toString();
	}

	public String alocacaoFuncionarioDiferenteProtocoloDaJuntada(JuntadaProtocoloDto dto) {
		StringBuilder sb = new StringBuilder();
		sb.append("ATENÇÃO ! Você precisa estar alocado na mesma unidade do protocolo  da  ");
		sb.append(dto.getTipoJuntadaProtocolo().toString());
		sb.append(". Contate o Administrador. ");
		sb.append("(");
		sb.append(dto.getProtocoloDaJuntada().getNumeroProtocolo());
		sb.append(")");
		return sb.toString();

	}

	public String eprocessoBloqueadoParaAnexar() {
		StringBuilder sb = new StringBuilder();
		sb.append("A Anexação de e-processos está temporariamente bloqueada. Por favor contate a CTEC.");

		return sb.toString();

	}

	public String anexoForaOrdemCronologica() {
		StringBuilder sb = new StringBuilder();
		sb.append(
				"ATENÇÃO ! Ação não pode ser executada, existe protocolo com data anterior a este que não foi anexado ao protocolo principal.");

		return sb.toString();
	}

	public String erroCadastroProtocolo(Long numeroProtocolo, String mensagemDocflow) {
		StringBuilder sb = new StringBuilder();
		sb.append("Mensagem do Docflow para o protocolo ");
		sb.append(numeroProtocolo + ": ");
		sb.append(mensagemDocflow);

		return sb.toString();
	}

	public String movimentoProtocoloArquivoVirtual(Long numeroProtocolo) {

		StringBuilder sb = new StringBuilder();
		sb.append("O protocolo ");
		sb.append(numeroProtocolo);
		sb.append(" foi movido do arquivo virtual e está apto para ser juntado ao processo ao qual está vinculado.");

		return sb.toString();
	}

	public String permissaoMoverProtocoloArquivoVirtual(Long numeroProtocolo) {
		StringBuilder sb = new StringBuilder();

		sb.append("O protocolo ");
		sb.append(numeroProtocolo);
		sb.append(" não pode ser movimentado automaticamente através deste recurso.");
		sb.append(" Para movimentá-lo será necessário acessar o serviço de trâmite de protocolos.");

		return sb.toString();
	}

	public String auditaMovimentoArquivoVirtual(Long numeroProtocolo) {
		StringBuilder sb = new StringBuilder();

		sb.append("O protocolo ");
		sb.append(numeroProtocolo);
		sb.append(" foi movimentado do arquivo virtual, afim de torná-lo um e-processo para que o mesmo possa");
		sb.append(" ser juntado ao seu processo que já está digitalizado.");

		return sb.toString();

	}

	public String cadastroProtocoloArquivoVirtualDocflow(Long numeroProtocolo) {
		StringBuilder sb = new StringBuilder();

		sb.append("O protocolo ");
		sb.append(numeroProtocolo);
		sb.append(" era físico e estava localizado no Arquivo Virtual. O mesmo foi cadastrado no Docflow");
		sb.append(" para que possa ser juntado ao seu processo que já está digitalizado.");

		return sb.toString();
	}

	public String vinculoProtocoloArquivoVirtual(Long protocoloArquivoVirtual, Long protocoloPrincipal) {
		StringBuilder sb = new StringBuilder();

		sb.append("O protocolo ");
		sb.append(protocoloArquivoVirtual);
		sb.append(" não está vinculado ao protocolo ");
		sb.append(protocoloPrincipal);

		return sb.toString();

	}

	public String corpoEmailErrosTramiteEnvio(String nomeFuncionario, String mensagem, int totalErro) {

		StringBuilder sb = new StringBuilder();

		sb.append("<div>");
		sb.append("<p>Prezado (a) ");
		sb.append(nomeFuncionario + ",");
		sb.append("</p>");
		sb.append("<p>Durante sua última ação de envio de protocolo(s), o sistema detectou anormalidades em ");
		sb.append(
				"alguns protocolos. Veja a listagem abaixo para melhor entendimento. Após análise, na impossibilidade ");
		sb.append("de continuar o trâmite, contate o CDOC para suporte.</p>");
		sb.append("</div>");

		sb.append("<div>");
		sb.append("<p>Total de protocolos: <b>" + totalErro + "</b>");
		sb.append("<ul>");

		sb.append(mensagem);

		sb.append("</ul>");
		sb.append("</div>");

		return sb.toString();
	}

	public String corpoEmailSucessoTramiteEnvio(String nomeDepartamento, String mensagem, int totalSucesso) {

		StringBuilder sb = new StringBuilder();

		sb.append("<div>");
		sb.append("<p>Prezado (a) Responsável pela Unidade de Recebimento ");
		sb.append(nomeDepartamento + ",");
		sb.append("</p>");
		sb.append(
				"<p>Você está recebendo este email com a relação dos protocolos enviados para a fila desta Unidade.</p> ");
		sb.append(
				"<p>Por gentileza visualize a listagem abaixo para detalhar a relação dos protocolos recebidos.</p> ");

		sb.append("<div>");
		sb.append("<p>Total de protocolos: <b>" + totalSucesso + "</b>");
		sb.append("<ul>");

		sb.append(mensagem);

		sb.append("</ul>");
		sb.append("</div>");

		return sb.toString();
	}

	public String corpoEmailErrosTramiteRecebimento(String nomeFuncionario, String mensagem, int totalErro) {

		StringBuilder sb = new StringBuilder();

		sb.append("<div>");
		sb.append("<p>Prezado (a) ");
		sb.append(nomeFuncionario + ",");
		sb.append("</p>");
		sb.append("<p>Durante sua última ação de recebimento de protocolo(s), o sistema detectou anormalidades em ");
		sb.append(
				"alguns protocolos. Veja a listagem abaixo para melhor entendimento. Após análise, na impossibilidade ");
		sb.append("de continuar o recebimento, contate o CDOC para suporte.</p>");
		sb.append("</div>");

		sb.append("<div>");
		sb.append("<p>Total de protocolos: <b>" + totalErro + "</b>");
		sb.append("<ul>");

		sb.append(mensagem);

		sb.append("</ul>");
		sb.append("</div>");

		return sb.toString();
	}

	public String textoProtocoloVirtualBaixaQuadroTecnico(TramiteDto dto) {

		StringBuilder texto = new StringBuilder();
		texto.append("O protocolo número ");
		texto.append(dto.getNumeroProtocolo().toString());
		texto.append(" foi cadastrado pelo profissional ");
		texto.append(dto.getInteressado().getNome());
		texto.append(" - ");
		texto.append("registro ");
		texto.append(dto.getInteressado().getRegistro());
		texto.append(" na área restrita do portal de serviços deste Crea-RJ através de login e senha em ");
		texto.append(dto.getDataProtocolo());
		texto.append(",");
		texto.append(" com pedido de baixa de quadro técnico da empresa ");
		texto.append(dto.getPessoa().getNome());
		texto.append(",");
		texto.append(
				" cuja baixa foi concedida nesta mesma data tendo a empresa sido notificada através de email cadastrado neste Conselho.");
		texto.append("\n");
		texto.append("\n");
		texto.append(
				"OBSERVAÇÃO: O protocolo estava localizado no arquivo virtual e foi movimentado para o departamento destino");
		texto.append(" do processo ao qual está vinculado tendo sido nesta data transformado em e-processo. ");
		texto.append("\n");
		texto.append("Usuário responsável pela movimentação: ");
		texto.append(dto.getFuncionarioTramite().getNome());
		texto.append(" - ");
		texto.append("Matricula ");
		texto.append(dto.getFuncionarioTramite().getMatricula());
		texto.append(".");

		return texto.toString();
	}

	public String textoProtocoloVirtualEntregaCarteira(TramiteDto dto) {

		StringBuilder texto = new StringBuilder();

		texto.append("O protocolo número ");
		texto.append(dto.getNumeroProtocolo().toString());
		texto.append(" foi cadastrado pelo profissional ");
		texto.append(dto.getInteressado().getNome());
		texto.append(" - ");
		texto.append("registro ");
		texto.append(dto.getInteressado().getRegistro());
		texto.append(" na área restrita do portal de serviços deste Crea-RJ através de login e senha em ");
		texto.append(dto.getDataProtocolo());
		texto.append(",");
		texto.append(" de Segunda Via de Carteira de Identidade Profissional.");
		texto.append("\n");
		texto.append("\n");
		texto.append(
				"OBSERVAÇÃO: O protocolo estava localizado no arquivo virtual e foi movimentado para o departamento destino ");
		texto.append(" do processo ao qual está vinculado tendo sido nesta data transformado em e-processo. ");
		texto.append("\n");
		texto.append("Usuário responsável pela movimentação: ");
		texto.append(dto.getFuncionarioTramite().getNome());
		texto.append(" - ");
		texto.append("Matricula ");
		texto.append(dto.getFuncionarioTramite().getMatricula());
		texto.append(".");

		return texto.toString();
	}

	public String erroJuntadaAnexoDocflow(JuntadaProtocoloDto dto, String mensagem) {
		StringBuilder sb = new StringBuilder();

		sb.append("Mensagem do Docflow para os protocolos em anexação ");
		sb.append("principal(");
		sb.append(dto.getProtocoloPrincipal().getNumeroProtocolo() + ")");
		sb.append(" anexo(");
		sb.append(dto.getProtocoloDaJuntada().getNumeroProtocolo() + ") : ");
		sb.append(mensagem);

		return sb.toString();
	}

	public String erroJuntadaDesanexoDocflow(JuntadaProtocoloDto dto, String mensagem) {
		StringBuilder sb = new StringBuilder();

		sb.append("Mensagem do Docflow para os protocolos da desvinculação ");
		sb.append("principal(");
		sb.append(dto.getProtocoloPrincipal().getNumeroProtocolo() + ")");
		sb.append(" anexo(");
		sb.append(dto.getProtocoloDaJuntada().getNumeroProtocolo() + ") : ");
		sb.append(mensagem);

		return sb.toString();
	}

	public String erroJuntadaApensoDocflow(JuntadaProtocoloDto dto, String mensagem) {
		StringBuilder sb = new StringBuilder();

		sb.append("Mensagem do Docflow para os protocolos em apensação ");
		sb.append("principal(");
		sb.append(dto.getProtocoloPrincipal().getNumeroProtocolo() + ")");
		sb.append(" anexo(");
		sb.append(dto.getProtocoloDaJuntada().getNumeroProtocolo() + ") : ");
		sb.append(mensagem);

		return sb.toString();
	}

	public String confirmacaoAnexoProtocolo(Long principal, Long anexo) {
		StringBuilder sb = new StringBuilder();

		sb.append("O protocolo ");
		sb.append(anexo);
		sb.append(" foi anexado com sucesso ao protocolo ");
		sb.append(principal);
		sb.append("!");

		return sb.toString();
	}

	public String confirmacaoApensoProtocolo(Long principal, Long anexo) {
		StringBuilder sb = new StringBuilder();

		sb.append("O protocolo ");
		sb.append(anexo);
		sb.append(" foi apensado com sucesso ao protocolo ");
		sb.append(principal);
		sb.append("!");

		return sb.toString();
	}

	public String permissaoFuncionarioDesvincular(String nome) {
		StringBuilder sb = new StringBuilder();

		sb.append("O funcionário ");
		if (nome != null) {
			sb.append(nome.toUpperCase() + " ");
		}
		sb.append("não possui permissão para desvincular o protocolo. ");

		return sb.toString();
	}

	public String eprocessoBloqueadoParaDesapensar() {

		StringBuilder sb = new StringBuilder();
		sb.append("A Desapensação de e-processos está temporariamente bloqueada. Por favor contate a CTEC.");

		return sb.toString();
	}

	public String desanexarProtocoloVinculadoProtocoloOrigemFisico(Long protocoloDesanexar) {
		StringBuilder sb = new StringBuilder();
		sb.append("O protocolo ");
		sb.append(protocoloDesanexar);
		sb.append(
				" não poderá ser desvinculado, pois está vinculado a processo que tinha origem física e já foi digitalizado.");

		return sb.toString();
	}

	public String protocoloPrincipalStatusTransacao(Long numeroProtocolo, String statusTranscao) {
		StringBuilder sb = new StringBuilder();
		sb.append("O protocolo principal ");
		sb.append(numeroProtocolo);
		sb.append(" está com status divergente e  precisa ser normalizado. Contate o administrador: ");
		sb.append(statusTranscao);

		return sb.toString();
	}

	public String protocoloJuntadaStatusTransacao(Long numeroProtocolo, String statusTransacao) {
		StringBuilder sb = new StringBuilder();
		sb.append("O protocolo ");
		sb.append(numeroProtocolo);
		sb.append(" está com status divergente e precisa ser normalizado. Contate o administrador: ");
		sb.append(statusTransacao);

		return sb.toString();
	}

	public String confirmacaoDesanexoProtocolo(Long principal, Long anexo) {
		StringBuilder sb = new StringBuilder();

		sb.append("O protocolo ");
		sb.append(anexo);
		sb.append(" foi desvinculado com sucesso do protocolo ");
		sb.append(principal);
		sb.append("!");

		return sb.toString();
	}

	public String confirmacaoDesapensoProtocolo(Long principal, Long vinculado) {
		StringBuilder sb = new StringBuilder();

		sb.append("O protocolo ");
		sb.append(vinculado);
		sb.append(" foi desapensado com sucesso do protocolo ");
		sb.append(principal);
		sb.append("!");

		return sb.toString();
	}

	public String desvincularProtocoloJuntada(Long protocoloPrincipal, Long protocoloADesvincular) {
		StringBuilder sb = new StringBuilder();

		sb.append("Não é possível efetuar a desvinculação. O protocolo ");
		sb.append(protocoloADesvincular);
		sb.append(" não está juntado ao protocolo principal ");
		sb.append(protocoloPrincipal);
		sb.append(".");
		return sb.toString();
	}

	public String eprocessoBloqueado() {
		StringBuilder sb = new StringBuilder();

		sb.append(
				"Atenção! No momento as funcionalidades relacionados ao Docflow estão inoperantes. Por favor, contate o Administrador. ");
		return sb.toString();
	}

	public String eprocessoBloqueadoParaDesanexar() {
		StringBuilder sb = new StringBuilder();
		sb.append("A Desanexação de e-processos está temporariamente bloqueada. Por favor contate a CTEC.");

		return sb.toString();

	}

	public String eprocessoBloqueadoParaApensar() {
		StringBuilder sb = new StringBuilder();
		sb.append("A Apensação de e-processos está temporariamente bloqueada. Por favor contate a CTEC.");

		return sb.toString();
	}

	public String assuntoProtocoloNotExist(Long codigo) {
		StringBuilder sb = new StringBuilder();
		sb.append("Atenção! Não existe assunto com o código informado: ");
		sb.append(codigo);

		return sb.toString();
	}

	public String pessoaProtocoloNotExist(Long codigo) {
		StringBuilder sb = new StringBuilder();
		sb.append("Atenção! Não existe pessoa com o código informado: ");
		sb.append(codigo);

		return sb.toString();
	}

	public String interessadoProtocoloNotExist(Long codigo) {
		StringBuilder sb = new StringBuilder();
		sb.append("Atenção! Não existe interessado com o código informado: ");
		sb.append(codigo);

		return sb.toString();
	}

	public String assuntoProtocoloNaoInformado() {
		StringBuilder sb = new StringBuilder();
		sb.append("Atenção: Assunto do protocolo não informado. ");

		return sb.toString();
	}

	public String pessoaProtocoloNaoInformada() {
		StringBuilder sb = new StringBuilder();
		sb.append("Atenção: Pessoa do protocolo não informada. ");

		return sb.toString();
	}

	public String departamentoNotExist(Long idDepartamentoOrigem) {
		StringBuilder sb = new StringBuilder();
		sb.append("Atenção! Não existe departamento com o código informado: ");
		sb.append(idDepartamentoOrigem);

		return sb.toString();
	}

	public String profissionalQuantidadeDividaNaoPaga(List<FinDivida> listaDividasNaoPagas) {
		StringBuilder sb = new StringBuilder();
		sb.append("Profissional possui ");
		sb.append(listaDividasNaoPagas.size());
		sb.append(" divida(s) a ser(em) quitada(s).");

		return sb.toString();
	}

	public String empresaQuantidadeDividaNaoPaga(List<FinDivida> listaDividasNaoPagas) {
		StringBuilder sb = new StringBuilder();
		sb.append("Empresa possui ");
		sb.append(listaDividasNaoPagas.size());
		sb.append(" divida(s) a ser(em) quitada(s).");

		return sb.toString();
	}

	public String processoInexistenteOuNaoEhdoInteressado() {
		StringBuilder sb = new StringBuilder();
		sb.append("O processo não existe ou não pertence ao interessado.");

		return sb.toString();
	}

	public String interessadoProtocoloNaoInformada() {
		StringBuilder sb = new StringBuilder();
		sb.append("Atenção: Interessado do protocolo não informado.");

		return sb.toString();
	}

	public String departamentoProtocoloNaoInformada() {
		StringBuilder sb = new StringBuilder();
		sb.append("Atenção: Departamento do protocolo não informado.");

		return sb.toString();
	}

	public String assuntoNaoTemTipoProcesso(String descricaoAssunto) {
		StringBuilder sb = new StringBuilder();
		sb.append("Atenção: O assunto ");
		sb.append(descricaoAssunto);
		sb.append(" não possui um tipo de processo.");

		return sb.toString();
	}

	public String certificadoNaoInformado() {
		StringBuilder sb = new StringBuilder();
		sb.append("Atenção: Certificado não informado. ");
		sb.append("É necessário selecionar um  ");
		sb.append("certificado válido para efetuar a assinatura.");
		return sb.toString();
	}

	public String protocoloSubstituidoNaoEncontrado(Long numeroSubstituido) {

		StringBuilder sb = new StringBuilder();
		sb.append("Não foi possível localizar o protocolo a ser substituído informado. ");
		return sb.toString();
	}

	public String thumbprintCertificadoNaoInformada() {
		StringBuilder sb = new StringBuilder();
		sb.append("Atenção: A impressão do certificado digital (Thumbprint) ");
		sb.append("não foi informada. ");
		sb.append("É necessário enviar a informação para efetuar a assinatura.");
		return sb.toString();
	}

	public String protocoloSubstitutoNaoEncontrado(Long numeroSubstituto) {
		StringBuilder sb = new StringBuilder();
		sb.append("Não foi possível localizar o protocolo substituto informado. ");
		return sb.toString();
	}

	public String tipoDocumentoAssinaturaNaoInformado() {
		StringBuilder sb = new StringBuilder();
		sb.append("Atenção: É necessário informar qual o tipo de documento para assinatura está sendo submetido. ");
		sb.append(
				"Contate ao Administrador do Sistema para informar uma das descrições: DOCUMENTO_JSON, FILE_SYSTEM, DOCUMENTO_UPLOAD.");
		return sb.toString();
	}

	public String eprocessoBloqueadoParaSubstituir() {

		StringBuilder sb = new StringBuilder();
		sb.append("A Substituição de e-processos está temporariamente bloqueada. Por favor contate a CTEC.");
		return sb.toString();
	}

	public String redisServiceIndisponivel() {
		StringBuilder sb = new StringBuilder();
		sb.append("Atenção: Houve um erro de conexão com o serviço 'Redis' ");
		sb.append("Contate o Administrador do Sistema.");
		return sb.toString();
	}

	public String documentoParaAssinaturaNaoInformado() {
		StringBuilder sb = new StringBuilder();
		sb.append("Atenção: O documento para assinatura não foi informado. ");
		sb.append("É necessário submeter um documento cadastrado para assinatura ou fazer o upload ");
		sb.append("de um pdf válido.");
		return sb.toString();
	}

	public String protocoloSubstituidoStatusTransacao(Long numeroProtocolo, String status) {
		StringBuilder sb = new StringBuilder();
		sb.append("O protocolo substituído ");
		sb.append(numeroProtocolo);
		sb.append(" está com status divergente e  precisa ser normalizado. Contate o administrador: ");
		sb.append(status);
		return sb.toString();
	}

	public String protocoloSubstitutoStatusTransacao(Long numeroProtocolo, String status) {
		StringBuilder sb = new StringBuilder();
		sb.append("O protocolo substituto ");
		sb.append(numeroProtocolo);
		sb.append(" está com status divergente e  precisa ser normalizado. Contate o administrador: ");
		sb.append(status);
		return sb.toString();
	}

	public String assinaturaCertificadoNaoInformada() {
		StringBuilder sb = new StringBuilder();
		sb.append("Atenção: O parâmetro 'signature' necessário para finalizar a assinatura ");
		sb.append("não foi informado, contate o Administrador do Sistema.");
		return sb.toString();
	}

	public String signaturePackageNaoInformado() {
		StringBuilder sb = new StringBuilder();
		sb.append("Atenção: O parâmetro 'signaturePackage' necessário para finalizar a assinatura ");
		sb.append("não foi informado, contate o Administrador do Sistema.");
		return sb.toString();
	}

	public String erroServicoExternoAssinatura(String erroExternalSign) {
		StringBuilder sb = new StringBuilder();
		sb.append("Mensagem do serviço externo para o documento em assinatura: ");
		sb.append(erroExternalSign);
		return sb.toString();
	}

	public String protocoloSubstituidoDigitalizado() {
		StringBuilder sb = new StringBuilder();
		sb.append("O protocolo substituido ");
		sb.append(" já foi digitalizado e o substituto não consta como digitalizado. ");
		return sb.toString();
	}

	public String protocoloSubstitutoDigitalizado() {
		StringBuilder sb = new StringBuilder();
		sb.append("O protocolo substituto ");
		sb.append(" já foi digitalizado e o substituido não consta como digitalizado. ");
		return sb.toString();
	}

	public String erroSubstituicaoProtocoloDocflow(SubstituicaoProtocoloDto dto, String mensagem) {
		StringBuilder sb = new StringBuilder();

		sb.append("Mensagem do Docflow para os protocolos em substituição ");
		sb.append("substituido(");
		sb.append(dto.getProtocoloSubstituido().getNumeroProtocolo() + ")");
		sb.append(" substituto(");
		sb.append(dto.getProtocoloSubstituto().getNumeroProtocolo() + ") : ");
		sb.append(mensagem);
		return sb.toString();
	}

	public String chaveRedisAssinaturaNaoLocalizada() {
		StringBuilder sb = new StringBuilder();
		sb.append(
				"Atenção: Para finalizar a assinatura é necessário informar o campo chave para recuperar o documento que está sendo assinado. ");
		sb.append("Contate o Administrador e informe que a chave do Banco Redis não foi localizada para o documento.");
		return sb.toString();
	}

	public String permissaoSubstituirProtocolo(String nomeFuncionario, Long numeroProtocolo) {
		StringBuilder sb = new StringBuilder();

		sb.append("O funcionário  ");
		sb.append(nomeFuncionario);
		sb.append(" não possui permissão para substituir o protocolo ");
		sb.append(numeroProtocolo);
		sb.append(" .Entre em contato com o Administrador do Sistema de Protocolos. ");
		return sb.toString();
	}

	public String documentoAssinaturaNaoInformado() {
		StringBuilder sb = new StringBuilder();
		sb.append(
				"Atenção: Para finalizar a assinatura é necessário informar identificador do documento que está sendo assinado. ");
		sb.append("Contate o Administrador e informe que o id do documento não foi localizado.");
		return sb.toString();
	}

	public String confirmacaoSubstituicaoProtocolo(SubstituicaoProtocoloDto dto) {

		StringBuilder sb = new StringBuilder();
		sb.append("O protocolo ");
		sb.append(dto.getProtocoloSubstituido().getNumeroProtocolo());
		sb.append(" foi substituído com sucesso pelo protocolo ");
		sb.append(dto.getProtocoloSubstituto().getNumeroProtocolo());
		sb.append("!");
		return sb.toString();
	}

	public String protocoloSubstitutoIgualAoSubstituido() {
		StringBuilder sb = new StringBuilder();
		sb.append("Os protocolos são iguais. Não é possível realizar a substituição. ");
		return sb.toString();
	}

	public String documentoAssinado(String numeroDocumentoEletronico) {
		StringBuilder sb = new StringBuilder();
		sb.append("Documento assinado com sucesso. ");
		sb.append("Número de cadastro do documento no Docflow:  ");
		sb.append(numeroDocumentoEletronico);
		return sb.toString();
	}

	public String distribuicao(GenericSiacolDto dto, UserFrontDto usuario, ProtocoloSiacol protocolo,
			String perfilRemetente, String perfilDestinatario) {
		StringBuilder sb = new StringBuilder();
		sb.append("O protocolo ");
		sb.append(protocolo.getNumeroProtocolo());
		sb.append(" foi distribuído");

		if (usuario.getIdPessoa().equals(dto.getIdResponsavelAtual())) {
			sb.append(" de " + usuario.getNome() + " (" + usuario.getPerfilString() + ")");
		} else {
			sb.append(" por " + usuario.getNome() + "(" + usuario.getPerfilString() + ")");
			sb.append(" de " + dto.getNomeResponsavelAtual() + " (" + perfilRemetente + ")");
		}

		sb.append(" para " + dto.getNomeResponsavelNovo() + " (" + perfilDestinatario + ")");

		if (protocolo.temJustificativa()) {
			sb.append(" com a seguinte justificativa: " + protocolo.getJustificativa());
		}

		return sb.toString();
	}

	public String alterarStatusProtocoloSiacol(ProtocoloSiacol protocolo) {
		StringBuilder sb = new StringBuilder();
		sb.append("O status do protocolo ");
		sb.append(protocolo.getNumeroProtocolo());
		sb.append(" foi alterado de ");
		sb.append(protocolo.getUltimoStatus().getDescricao());
		sb.append(" para ");
		sb.append(protocolo.getStatus().getDescricao());

		return sb.toString();
	}

	public String recebimentoSiacol(ProtocoloSiacol protocolo, UserFrontDto usuario) {
		StringBuilder sb = new StringBuilder();
		sb.append("O protocolo ");
		sb.append(protocolo.getNumeroProtocolo());
		sb.append(" foi recebido no Siacol por ");
		sb.append(usuario.getNome() + " (" + usuario.getPerfilString() + ")");
		sb.append(" no departamento " + protocolo.getDepartamento().getNome());

		return sb.toString();
	}

	public String pausaSiacol(ProtocoloSiacol protocolo, UserFrontDto usuario) {
		StringBuilder sb = new StringBuilder();
		sb.append("O protocolo ");
		sb.append(protocolo.getNumeroProtocolo());
		sb.append(" foi pausado no Siacol por ");
		sb.append(usuario.getNome() + " (" + usuario.getPerfilString() + ")");
		sb.append(" no departamento " + protocolo.getDepartamento().getNome());

		return sb.toString();
	}

	public String retiraPausaSiacol(ProtocoloSiacol protocolo, UserFrontDto usuario) {
		StringBuilder sb = new StringBuilder();
		sb.append("O protocolo ");
		sb.append(protocolo.getNumeroProtocolo());
		sb.append(" foi retirado de pausa no Siacol por ");
		sb.append(usuario.getNome() + " (" + usuario.getPerfilString() + ")");
		sb.append(" no departamento " + protocolo.getDepartamento().getNome());

		return sb.toString();
	}

	public String anexarProtocolo(JuntadaProtocoloDto dto, UserFrontDto usuario, String acaoEmissor) {
		StringBuilder sb = new StringBuilder();
		sb.append("O protocolo ");
		sb.append(dto.getProtocoloDaJuntada().getNumeroProtocolo());
		sb.append(acaoEmissor);
		sb.append(dto.getProtocoloPrincipal().getNumeroProtocolo());
		sb.append(" por " + usuario.getNome());

		return sb.toString();
	}

	public String receberAnexoProtocolo(JuntadaProtocoloDto dto, UserFrontDto usuario, String acaoDestinatario) {
		StringBuilder sb = new StringBuilder();
		sb.append("O protocolo ");
		sb.append(dto.getProtocoloPrincipal().getNumeroProtocolo());
		sb.append(acaoDestinatario);
		sb.append(dto.getProtocoloDaJuntada().getNumeroProtocolo());
		sb.append(" por " + usuario.getNome());

		return sb.toString();
	}

	public String ocultarProtocolo(ProtocoloSiacol protocolo, UserFrontDto usuario) {
		StringBuilder sb = new StringBuilder();
		sb.append("O protocolo ");
		sb.append(protocolo.getNumeroProtocolo());
		sb.append(" foi ocultado por anexação ou pensação");
		sb.append(" - O autor da ação foi: " + usuario.getNome());

		return sb.toString();
	}

	public String mostrarProtocolo(ProtocoloSiacol protocolo, UserFrontDto usuario) {
		StringBuilder sb = new StringBuilder();
		sb.append("O protocolo ");
		sb.append(protocolo.getNumeroProtocolo());
		sb.append(" foi ativado pelo usuário para analise");
		sb.append(" - O autor da ação foi: " + usuario.getNome());

		return sb.toString();
	}

	public String cadastrarDocumentoDocflow(DocflowGenericDto dto, UserFrontDto usuario, String departamento) {
		StringBuilder sb = new StringBuilder();
		sb.append("O documento ");
		sb.append(dto.getIdDocumento());
		sb.append(" do tipo ");
		sb.append(dto.getTipoDocumento());
		sb.append(" foi cadastrado no Docflow por ");
		sb.append(usuario.getNome() + " (" + usuario.getPerfilString() + ")");
		sb.append(" no departamento " + departamento);

		return sb.toString();
	}

	public String criacaoDocumento(DocumentoGenericDto dto, UserFrontDto usuario, String departamento) {
		StringBuilder sb = new StringBuilder();
		if (dto.isAssinado()) {
			sb.append("O documento ");
		} else {
			sb.append("A minuta do documento ");
		}
		sb.append(dto.getId());
		sb.append(" do tipo ");
		sb.append(dto.getTipo().getDescricao());
		sb.append(dto.isAssinado() ? " foi criado por " : " foi criada por ");
		sb.append(usuario.getNome() + " (" + usuario.getPerfilString() + ")");
		sb.append(" no departamento " + departamento);

		return sb.toString();
	}

	public String atualizaDocumento(DocumentoGenericDto dto, UserFrontDto usuario, String departamento) {
		StringBuilder sb = new StringBuilder();
		if (dto.isAssinado()) {
			sb.append("O documento ");
		} else {
			sb.append("A minuta do documento ");
		}
		sb.append(dto.getId());
		sb.append(" do tipo ");
		sb.append(dto.getTipo().getDescricao());
		sb.append(dto.isAssinado() ? " foi atualizada por " : " foi atualizada por ");
		sb.append(usuario.getNome() + " (" + usuario.getPerfilString() + ")");
		sb.append(" no departamento " + departamento);

		return sb.toString();
	}

	public String exclusaoDocumento(DocumentoGenericDto dto, UserFrontDto usuario, String departamento) {
		StringBuilder sb = new StringBuilder();
		sb.append("O documento ");
		sb.append(dto.getId());
		sb.append(" do tipo ");
		sb.append(dto.getTipo().getDescricao());
		sb.append(" foi excluído por ");
		sb.append(usuario.getNome() + " (" + usuario.getPerfilString() + ")");
		sb.append(" no departamento " + departamento);

		return sb.toString();
	}

	public String assinaDocumento(DocumentoGenericDto dto, UserFrontDto usuario, String departamento) {
		StringBuilder sb = new StringBuilder();
		sb.append("O documento ");
		sb.append(dto.getId());
		sb.append(" do tipo ");
		sb.append(dto.getTipo().getDescricao());
		sb.append(" foi assinado por ");
		sb.append(usuario.getNome() + " (" + usuario.getPerfilString() + ")");
		sb.append(" no departamento " + departamento);

		return sb.toString();
	}

	public String auditaMovimentoAnexo(TramiteDto tramite, ProtocoloSiacol dto, TipoEventoAuditoria evento) {
		StringBuilder sb = new StringBuilder();
		sb.append("Protocolo ");
		sb.append(dto.getNumeroProtocolo());
		sb.append(" foi tramitado com sucesso para o departamento ");
		sb.append(tramite.getIdDepartamentoDestino());

		return sb.toString();
	}

	public String auditaMovimentoApenso(TramiteDto tramite, ProtocoloSiacol dto, TipoEventoAuditoria evento) {
		StringBuilder sb = new StringBuilder();
		sb.append("Protocolo ");
		sb.append(dto.getNumeroProtocolo());
		sb.append(" foi tramitado com sucesso para o departamento ");
		sb.append(tramite.getIdDepartamentoDestino());

		return sb.toString();
	}

	public String auditaEnvioOuRecebimentoDocflow(TramiteDto dto, TipoEventoAuditoria evento) {
		StringBuilder sb = new StringBuilder();
		sb.append("Protocolo ");
		sb.append(dto.getNumeroProtocolo());
		sb.append(evento.equals(TipoEventoAuditoria.RECEBER_PROTOCOLO_DOCFLOW) ? " recebido " : " tramitado ");
		sb.append(" no Docflow");

		return sb.toString();
	}

	public String corpoEmailEnvioCadastroDeArtPorEmpresa(ContratoArt contrato) {
		ArtDto artDto = artConverter.toDto(contrato.getArt());

		StringBuilder sb = new StringBuilder();
		sb.append("<p>Sr(a) " + contrato.getArt().getProfissional().getNome() + ",</p>");
		sb.append("<br/>");
		sb.append("<p>Informamos que a empresa: " + artDto.getEmpresa().getNome() + "</p>");
		sb.append("<p>Nº: " + artDto.getNumero() + " no seu cadastro de ART's.</p>");
		sb.append("<p>Contratante: " + contrato.getNomeContratante() + "</p>");
		sb.append("<br/>");
		sb.append("<p>Endereço: " + enderecoConverter.transformaEnderecoCompleto(contrato.getEndereco()) + "</p>");
		sb.append("<br/>");
		sb.append(
				"<p>Caso possua alguma dúvida, entrar em contato com a Central de Relacionamento do Crea-RJ e fale com um dos ");
		sb.append("nossos Atendentes: (21) 2179-2007, de segunda a sexta-feira, no horário de 09:00 as 17:30.</p>");
		sb.append("<br/>");
		sb.append(
				"<p>Esta é uma mensagem automática, respostas a esse email não serão monitoradas ou consideradas.</p>");

		return sb.toString();
	}

	public String protocoloJulgado(ProtocoloSiacol protocolo, RlDocumentoProtocoloSiacol item) {

		StringBuilder sb = new StringBuilder();
		sb.append("O protocolo " + protocolo.getNumeroProtocolo());
		sb.append(" foi votado e teve resultado " + item.getResultado() + ", sendo ");
		sb.append(item.getTotalVotosSim() != null ? item.getTotalVotosSim() : "0");
		sb.append(" a favor, ");
		sb.append(item.getTotalVotosNao() != null ? item.getTotalVotosNao() : "0");
		sb.append(" contra, ");
		sb.append(item.getTotalVotosAbstencao() != null ? item.getTotalVotosAbstencao() : "0");
		sb.append(" abstenção.");

		return sb.toString();
	}

	public String corpoEmailEnvioCadastroAtendimentoOuvidoria(Long idOuvidoria) {

		StringBuilder sb = new StringBuilder();
		sb.append("<p> Prezado(a) Senhor(a),</p>");
		sb.append("<br/>");
		sb.append("Comunicamos que a sua manifestação foi recebida e registrada no sistema desta Ouvidoria sob o nº "
				+ idOuvidoria + " na data " + DateUtils.format(new Date(), DateUtils.DD_MM_YYYY) + ".");
		sb.append("<br/>");
		sb.append(
				"<p>Informamos que, após análise prévia, caso sua manifestação seja pertinente à Central de Relacionamento do Crea-RJ, ");
		sb.append("primeira instância de atendimento, lhe daremos novo retorno com as devidas orientações.</p>");
		sb.append("<p>Caso contrário, encaminharemos à unidade competente para providências.</p>");
		sb.append("Atenciosamente,");
		sb.append("<br/>");
		sb.append("Ouvidoria Crea-RJ");
		sb.append("<br/>");
		sb.append("<br/>");
		sb.append(
				"<p>Esta é uma mensagem automática, respostas a esse email não serão monitoradas ou consideradas.<p>");

		return sb.toString();

	}
	
	public String corpoEmailEnvioSolicitacaoSegundaViaDeCarteira() {

		StringBuilder sb = new StringBuilder();
		sb.append("<p> Solicição através do sistema web de atendimento - Portal CREA-RJ</p>");
		sb.append("<br/>");
		sb.append("<p>Caso possua alguma dúvida, entrar em contato com a Central de Relacionamento do Crea-RJ e  ");
		sb.append("fale com um dos nossos Atendentes: (21) 2179-2007, de segunda a sexta-feira, no horário de 09:00 as 17:30.</p>");

		return sb.toString();

	}

}
