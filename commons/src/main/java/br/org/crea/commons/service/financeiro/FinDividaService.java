package br.org.crea.commons.service.financeiro;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.financeiro.DividaConverter;
import br.org.crea.commons.dao.cadastro.TipoSolicitacaoRPJDao;
import br.org.crea.commons.dao.cadastro.pessoa.PessoaDao;
import br.org.crea.commons.dao.financeiro.BancoDao;
import br.org.crea.commons.dao.financeiro.BoletoDao;
import br.org.crea.commons.dao.financeiro.FinDevolucaoDao;
import br.org.crea.commons.dao.financeiro.FinDividaDao;
import br.org.crea.commons.dao.financeiro.FinNaturezaDao;
import br.org.crea.commons.dao.financeiro.RlDividaBoletoDao;
import br.org.crea.commons.models.commons.dtos.DomainGenericDto;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.models.corporativo.dtos.PessoaDto;
import br.org.crea.commons.models.financeiro.Boleto;
import br.org.crea.commons.models.financeiro.FinDevolucao;
import br.org.crea.commons.models.financeiro.FinDivida;
import br.org.crea.commons.models.financeiro.RlDividaBoleto;
import br.org.crea.commons.models.financeiro.dtos.DividaDto;
import br.org.crea.commons.models.financeiro.dtos.ResultDividaDto;
import br.org.crea.commons.models.financeiro.dtos.ValidaDevolucaoTransferenciaCreditoDto;
import br.org.crea.commons.service.CommonsService;

public class FinDividaService {
	
	@Inject FinDividaDao dao;
	
	@Inject DividaConverter converter;
	
	@Inject TipoSolicitacaoRPJDao daoTipoSolicitacaoRPJ;	
	
	@Inject BancoDao bancoDao;
	
	@Inject FinNaturezaDao finNaturezaDao;
	
	@Inject BoletoDao boletoDao;
	
	@Inject RlDividaBoletoDao rlDividaBoletoDao;
	
	@Inject FinDevolucaoDao finDevolucaoDao;
	
	@Inject CommonsService commonsService;
	
	@Inject PessoaDao pessoaDao;

	public List<DividaDto> getDividaPor(Long idPessoa) {
		return converter.toListDto(dao.getAnuidadeDivida(idPessoa));
	}

	public List<DividaDto> getAnuidadesDividaPorPessoa(Long idPessoa) {
		return converter.toListDto(dao.getAnuidadesDividas(idPessoa));
	}

	public ResultDividaDto getDividaPor(PessoaDto pessoaDto) {
	
		List<FinDivida> listDivida = new ArrayList<FinDivida>();
		listDivida = dao.getAnuidadeDivida(pessoaDto.getId());
		
		ResultDividaDto result = new ResultDividaDto();
		result.setLiberaParcelamentoAnuidadeCorrente(dao.liberaParcelamentoAnuidadeCorrente(pessoaDto.getId()));
		result.setPessoa(pessoaDto);
		
		if(!listDivida.isEmpty()){
			result.setDividas(converter.toListDto(listDivida));
		}
		
		return result;
	}

	public List<DividaDto> getParcelasAvencer(Long idPessoa, String anuidade) {
		return converter.toListDto(dao.getParcelasAvencer(idPessoa, anuidade));
	}

	public List<DividaDto> getMultasAvencer(Long idPessoa, String anuidade) {
		return converter.toListDto(dao.getMultasAvencer(idPessoa, anuidade));
	}

	public List<DividaDto> getDebitosAnuidadePorPessoa(Long idPessoa) {
		return converter.toListDto(dao.getDebitosAnuidadePorPessoa(idPessoa));
	}

	public List<DividaDto> getDebitosAnuidadeQuadroTecnicoPorPessoa(Long idPessoa) {
		return converter.toListDto(dao.getDebitosAnuidadeQuadroTecnicoPorPessoa(idPessoa));
	}
	
	public List<DividaDto> getDebitosArtPorPessoa(Long idPessoa) {
		return converter.toListDto(dao.getDebitosArtPorPessoa(idPessoa));
	}
	
	public List<DividaDto> getDebitosArtETaxaDeIncorporacaoPorPessoa(Long idPessoa) {
		return converter.toListDto(dao.getDebitosArtETaxaIncorporacaoPorPessoa(idPessoa));
	}

	public List<DividaDto> getDebitosTaxasPorPessoa(Long idPessoa) {
		return converter.toListDto(dao.getDebitosTaxasPorPessoa(idPessoa));
	}

	public boolean verificaTaxaPaga(UserFrontDto user, Long idAssunto) {
		return dao.verificaTaxaPaga(user.getIdPessoa(), idAssunto);		
	}

	public List<DividaDto> getDebitosArtPorNumero(String numeroArt) {
		return converter.toListDto(dao.getDebitosArtPorNumero(numeroArt));
	}

	public List<DomainGenericDto> getAllBancos() {
		return converter.toListBancoDto(bancoDao.getAll());
	}

	public List<DomainGenericDto> getNaturezaParaDevolucaoTransferenciaDeCredito() {
		return converter.toListNaturezaDto(finNaturezaDao.getNaturezaParaDevolucaoTransferenciaDeCredito());
	}

	public List<String> validaDevolucaoTransferenciaDeCredito(ValidaDevolucaoTransferenciaCreditoDto dto) {
		List<String> mensagens = new ArrayList<>();
		
		if (pessoaDao.verificaRegistroEnquadradoArtigo64(1L, dto.getTipoPessoa())) mensagens.add("Profissional enquadrável no Artigo 64 da Lei 5.194/1966.");
		
		Boleto boleto = boletoDao.getBoletoByNossoNumero(dto.getNossoNumero());
		
		if(boleto == null) { 
			mensagens.add("Não foi localizado o boleto de pagamento informado.");
		} else {
			if (boleto.temDataPagamento()) {
				if(!boleto.getIdPessoa().equals(dto.getIdPessoa())) {
					mensagens.add("O boleto pago não pertence ao requerente.");
				}
					
				
				List<FinDevolucao> listDevolucaoDividas = new ArrayList<FinDevolucao>();
				List<RlDividaBoleto> listRl = rlDividaBoletoDao.getByIdBoleto(boleto.getId());
				if (listRl.isEmpty()) {
					mensagens.add("Não foram localizadas dividas para o boleto. Favor entrar em contato com a Central de Atendimento (21) 2179-2007.");
				} else {
					for (RlDividaBoleto rl : listRl) {
						List<FinDevolucao> devolucao = finDevolucaoDao.getDevolucaoByIdDivida(rl.getDivida().getId());
						if (!devolucao.isEmpty()) {
							for (FinDevolucao devo : devolucao) {
								listDevolucaoDividas.add(devo);
							}
						}
					}
				}
					
				List<FinDevolucao> listaDevolucoes = new ArrayList<FinDevolucao>();
				listaDevolucoes.addAll(listaDevolucoes);
				listaDevolucoes.addAll(finDevolucaoDao.getDevolucaoByIdBoleto(boleto.getId()));
				
				if(!listaDevolucoes.isEmpty()) mensagens.add("Já foi realizado pedido de transferencia/devolução da(s) divida(s) através do protocolo: " + listaDevolucoes.get(0).getIdProtocolo());
			}
		}
		
		if (!commonsService.validaFormatoCpfOuCnpj(dto.getCpfOuCnpj())) mensagens.add("CPF/CNPJ do favorecido é inválido.");
		
		return mensagens;
	}

}
