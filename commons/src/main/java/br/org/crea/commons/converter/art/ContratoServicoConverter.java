package br.org.crea.commons.converter.art;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.cadastro.pessoa.EnderecoConverter;
import br.org.crea.commons.converter.cadastro.pessoa.TelefoneConverter;
import br.org.crea.commons.dao.art.ArtDomainDao;
import br.org.crea.commons.dao.cadastro.empresa.EmpresaDao;
import br.org.crea.commons.dao.cadastro.empresa.RazaoSocialDao;
import br.org.crea.commons.dao.cadastro.pessoa.EnderecoDao;
import br.org.crea.commons.dao.cadastro.pessoa.PessoaDao;
import br.org.crea.commons.dao.cadastro.pessoa.TelefoneDao;
import br.org.crea.commons.dao.cadastro.profissional.ProfissionalDao;
import br.org.crea.commons.models.art.Art;
import br.org.crea.commons.models.art.ArtReceita;
import br.org.crea.commons.models.art.BaixaArt;
import br.org.crea.commons.models.art.ContratoArt;
import br.org.crea.commons.models.art.RamoArt;
import br.org.crea.commons.models.art.dtos.ContratoServicoDto;
import br.org.crea.commons.models.commons.Endereco;
import br.org.crea.commons.models.commons.dtos.GenericDto;
import br.org.crea.commons.models.corporativo.enuns.TipoPessoa;
import br.org.crea.commons.models.corporativo.pessoa.Empresa;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;
import br.org.crea.commons.models.corporativo.pessoa.PessoaFisica;
import br.org.crea.commons.models.corporativo.pessoa.PessoaJuridica;
import br.org.crea.commons.models.corporativo.pessoa.Profissional;
import br.org.crea.commons.models.fiscalizacao.ContratoAtividade;
import br.org.crea.commons.models.fiscalizacao.ContratoInativado;
import br.org.crea.commons.models.fiscalizacao.ContratoServico;
import br.org.crea.commons.models.fiscalizacao.enuns.TipoAtividade;
import br.org.crea.commons.models.fiscalizacao.enuns.TipoContratacao;
import br.org.crea.commons.util.DateUtils;
import br.org.crea.commons.util.StringUtil;

public class ContratoServicoConverter {

	@Inject
	EnderecoConverter enderecoConverter;

	@Inject
	TelefoneConverter telefoneConverter;

	@Inject
	EnderecoDao enderecoDao;

	@Inject
	TelefoneDao telefoneDao;

	@Inject
	RazaoSocialDao razaoSocialDao;

	@Inject
	PessoaDao pessoaDao;
	
	@Inject
	ArtDomainDao dao;
	
	@Inject
	ProfissionalDao profissionalDao;
	
	@Inject
	EmpresaDao empresaDao;

	public List<ContratoServicoDto> toListDto(List<ContratoServico> lista) {
		List<ContratoServicoDto> resultado = new ArrayList<ContratoServicoDto>();

		for (ContratoServico model : lista) {
			resultado.add(toDto(model));
		}

		return resultado;
	}

	public ContratoServicoDto toDto(ContratoServico model) {
		ContratoServicoDto dto = new ContratoServicoDto();

		dto.setId(String.valueOf(model.getCodigo()));
		dto.setNumeroArt(model.getArt());

		dto.setAtivo(model.isAtivo());
		dto.setCodigo(model.getCodigo());

		dto.setIdPessoaContratada(model.getContratado().getId());

		Pessoa contratado = pessoaDao.getBy(model.getContratado().getId());

		if (contratado instanceof PessoaFisica) {
			dto.setNomeContratado(contratado.getNome());
			PessoaFisica pessoa = (PessoaFisica) contratado;
			dto.setCpfOuCnpjContratado(StringUtil.getCnpjCpfFormatado(pessoa.getCpf()));
		} else {
			String nome = razaoSocialDao.buscaDescricaoRazaoSocial(model.getContratado().getId());
			dto.setNomeContratado(nome);
			PessoaJuridica pessoa = (PessoaJuridica) contratado;
			dto.setCpfOuCnpjContratado(StringUtil.getCnpjCpfFormatado(pessoa.getCnpj()));
		}

		dto.setIdPessoaContratante(model.getContratante().getId());

		Pessoa contratante = pessoaDao.getBy(model.getContratante().getId());

		if (contratante.getTipoPessoa().equals(TipoPessoa.PESSOAFISICA) || contratante.getTipoPessoa().equals(TipoPessoa.LEIGOPF)) {
			dto.setNomeContratante(model.getContratante().getNome());
			PessoaFisica pessoa = (PessoaFisica) model.getContratante();
			dto.setCpfOuCnpjContratante(StringUtil.getCnpjCpfFormatado(pessoa.getCpf()));
		} if (contratante.getTipoPessoa().equals(TipoPessoa.PESSOAJURIDICA) || contratante.getTipoPessoa().equals(TipoPessoa.LEIGOPJ)) {
			String nome = razaoSocialDao.buscaDescricaoRazaoSocial(model.getContratante().getId());
			dto.setNomeContratante(nome);
			PessoaJuridica pessoa = (PessoaJuridica) contratante;
			dto.setCpfOuCnpjContratante(StringUtil.getCnpjCpfFormatado(pessoa.getCnpj()));
		}

		dto.setEnderecoContrato(enderecoConverter.toDto(enderecoDao.getEnderecoPessoaById(model.getContratado().getId())));
		dto.setTelefonesContratante(telefoneConverter.toListDto(telefoneDao.getListTelefoneByPessoa(model.getContratante().getId())));
		dto.setTelefonesContratado(telefoneConverter.toListDto(telefoneDao.getListTelefoneByPessoa(model.getContratado().getId())));

		if(model.getAtividade() != null){
			ContratoAtividade atividade = dao.getAtividade(model.getAtividade().getCodigo());
			GenericDto atividadeDto = new GenericDto();
			atividadeDto.setCodigo(String.valueOf(atividade.getCodigo()));
			atividadeDto.setDescricao(atividade.getDescricao().toUpperCase());
	
			dto.setAtividade(atividadeDto);
		}
		
		dto.setDataInicio(model.getDataInicio());
		dto.setDataFim(model.getDataFinal());
		dto.setDataCadastro(model.getDataAtualizacao());
		dto.setNotaFiscal(model.getNotaFiscal());
		dto.setNumeroDocumentoCondominio(model.getNumeroDocumentoCondominio());
		dto.setDataInicioFormatada(DateUtils.format(model.getDataInicio(), new SimpleDateFormat("dd/MM/yyyy")));
		dto.setDataFimFormatada(DateUtils.format(model.getDataFinal(), new SimpleDateFormat("dd/MM/yyyy")));
		dto.setTipoAtividade(model.getTipoAtividade() != null ? model.getTipoAtividade().toString() : null);
		dto.setTipoContratacao(model.getTipoAtividade() != null ? model.getTipoContratacao().toString() : null);
		dto.setDataContratoServicoFormatada(DateUtils.format(model.getDataContratoServico(), new SimpleDateFormat("dd/MM/yyyy")));
		dto.setAtivoSimNao(model.isAtivo() ? "SIM" : "NÃO");
		dto.setPossuiContratoFormal(String.valueOf(model.isPossuiContratoFormal()));
		
		if (contratado.getTipoPessoa().equals(TipoPessoa.PROFISSIONAL) || contratado.getTipoPessoa().equals(TipoPessoa.LEIGOPF)) {
			Profissional profissional = profissionalDao.getBy(model.getContratado().getId());
			
			if(profissional != null){
				dto.setRegistroContratado(profissional.getRegistro());
			}	
		}else if (contratado.getTipoPessoa().equals(TipoPessoa.EMPRESA)  || contratado.getTipoPessoa().equals(TipoPessoa.LEIGOPJ)) {
			Empresa empresa = empresaDao.getBy(model.getContratado().getId());
			
			if(empresa != null){
				dto.setRegistroContratado(empresa.getRegistro());
			}	
		}
		
		dto.setContrato(model.getContrato());
		dto.setProcesso(model.getProcesso());
		dto.setPedidoCompra(model.getPedidoCompra());
		dto.setNotaFiscal(model.getNotaFiscal());
		dto.setOrdemServico(model.getOrdemServico());
		dto.setCartaConvite(model.getCartaConvite());
		if(model.getRamo() != null){
			GenericDto ramo = new GenericDto();
			ramo.setId(String.valueOf(model.getRamo().getId()));
			ramo.setDescricao(model.getRamo().getDescricao());
			dto.setRamo(ramo);
		}
		dto.setValorContrato(model.getValorEmReais());
		dto.setAtividadeDesenvolvida(model.getAtividadeDesenvolvida() != null ? model.getAtividadeDesenvolvida().toUpperCase() : null);
		dto.setObjetoContrato(model.getObjetoContrato() != null ? model.getObjetoContrato().toUpperCase() : null);
		
		return dto;

	}

	public ContratoServico toModel(ContratoServicoDto dto) {
		ContratoServico model = new ContratoServico();
		model.setArt(dto.getNumeroArt() != null ? dto.getNumeroArt().toUpperCase().trim() : "");

		Pessoa contratado = new Pessoa();
		contratado.setId(dto.getIdPessoaContratada());
		model.setContratado(contratado);

		Pessoa contratante = new Pessoa();
		contratante.setId(dto.getIdPessoaContratante());
		model.setContratante(contratante);
		

		model.setDataAtualizacao(new Date());
		model.setAtivo(true);
		model.setDataInicio(dto.getDataInicio());
		model.setDataFinal(dto.getDataFim());
		ContratoAtividade atividade = new ContratoAtividade();
		atividade.setCodigo(Long.parseLong(dto.getAtividade().getCodigo()));
		model.setAtividade(atividade);
		model.setPossuiContratoFormal(Boolean.parseBoolean(dto.possuiContratoFormal()));
		model.setNotaFiscal(!model.isPossuiContratoFormal() && dto.getNotaFiscal() == null ? "NÃO INFORMADO" : dto.getNotaFiscal().toUpperCase().trim());
		model.setTipoAtividade(TipoAtividade.valueOf(dto.getTipoAtividade()));
		model.setTipoContratacao(dto.getTipoContratacao() != null ? TipoContratacao.valueOf(dto.getTipoContratacao()) : TipoContratacao.INDEFINIDA);
		model.setNumeroDocumentoCondominio(dto.getNumeroDocumentoCondominio());
		model.setDataContratoServico(dto.getDataContratoServico());
		model.setContrato(dto.getContrato());
		model.setProcesso(dto.getProcesso());
		model.setPedidoCompra(dto.getPedidoCompra());
		model.setNotaFiscal(dto.getNotaFiscal());
		model.setOrdemServico(dto.getOrdemServico());
		model.setCartaConvite(dto.getCartaConvite());
		model.setValorEmReais(dto.getValorContrato());
		model.setAtividadeDesenvolvida(dto.getAtividadeDesenvolvida() != null ? dto.getAtividadeDesenvolvida().toUpperCase() : null);
		model.setObjetoContrato(dto.getObjetoContrato());
		
		if(dto.getRamo() != null){
			RamoArt ramo = new RamoArt();
			ramo.setId(Long.parseLong(dto.getRamo().getId()));
			model.setRamo(ramo);
		}	
				
		return model;
	}
	
	public ContratoInativado toModelContratoInativado(ContratoServicoDto dto){
		ContratoInativado contratoInativado = new ContratoInativado();
		
		ContratoAtividade atividade = dao.getAtividade(dto.getAtividade() != null ? Long.parseLong(dto.getAtividade().getCodigo()) : null);
				
		contratoInativado.setAtividade(atividade);
		
		Pessoa pessoa = pessoaDao.getBy(dto.getIdPessoaContratante());
				
		contratoInativado.setPessoa(pessoa);
		contratoInativado.setMotivo(dto.getMotivoBaixaOutros().toUpperCase());
		contratoInativado.setData(new Date());
		
		return contratoInativado;
	}
	
	public ContratoServicoDto toServicoDto(ContratoArt model) {

		ContratoServicoDto dto = new ContratoServicoDto();		
		dto.setId(model.getId());
		
		Pessoa pessoa = model.getPessoa();
		if(pessoa instanceof PessoaFisica){
			PessoaFisica pf = (PessoaFisica)pessoa;
			dto.setCpfOuCnpj(pf.getCpfFormatado());
		}else if(pessoa instanceof PessoaJuridica){
			PessoaJuridica pj = (PessoaJuridica) pessoa;
			dto.setCpfOuCnpj(pj.getCnpjFormatado());
		}
		dto.setNomeContratante(model.getNomeContratante());
		dto.setNumero(model.getNumeroContrato());
		dto.setEnderecoContratante(enderecoConverter.toDto(model.getEnderecoContratante()));
		dto.setEnderecoContrato(enderecoConverter.toDto(model.getEndereco()));
		dto.setIdReceita(model.getReceita() != null ? model.getReceita().getId() : null);
		
		Profissional profissional = model.getArt().getProfissional();
		
		dto.setNomeProfissional(profissional.getNome() != null ? profissional.getNome() : profissional.getNomeRazaoSocial());
		dto.setNumeroRegistroCreaProfissional(profissional.getNumeroRNP() != null ? profissional.getNumeroRNP() : profissional.getRegistro());
		
		Endereco endereco = enderecoDao.getEnderecoPessoaById(profissional.getPessoaFisica().getId());
		
		dto.setEnderecoProfissional(enderecoConverter.transformaEnderecoCompleto(endereco));
		
		dto.setDataCadastro(new Date(model.getDataCadastro().getTime()));
		
		return dto;
	}

	public List<ContratoServicoDto> toListServicoDto(List<ContratoArt> lista){
		List<ContratoServicoDto> resultado = new ArrayList<ContratoServicoDto>();
		for(ContratoArt contrato : lista){
			resultado.add(toServicoDto(contrato));
		}
		return resultado;
	}
	
	public ContratoArt toServicoModel(ContratoServicoDto dto){
	
		Art art = new Art();
		ContratoArt contrato = new ContratoArt();
				
		art.setNumero(dto.getNumeroArt());
		
		BaixaArt baixa = new BaixaArt();
		baixa.setId(8L);
		
		Pessoa pessoa = new Pessoa();
		pessoa.setId(dto.getIdPessoaContratante());
		
		RamoArt ramo = new RamoArt();
		ramo.setId(Long.parseLong(dto.getRamo().getId()));
		
		ArtReceita receita = new ArtReceita();
		receita.setId(dto.getIdReceita());
				
		contrato.setArt(art);
		
		contrato.setSequencial(dto.getSequencial() + 1);
		contrato.setId(dto.getNumeroArt() + "-" + contrato.getSequencial());
		contrato.setDataCadastro(new Date());
		contrato.setDataInicio(dto.getDataInicio());
		contrato.setDataFim(dto.getDataFim());
		contrato.setAssContratante(true);
		contrato.setBaixaArt(baixa);
		contrato.setPessoa(pessoa);
		contrato.setRamoart(ramo);
		contrato.setReceita(receita);
		contrato.setFuncionarioCadastro(99990L);
		
		return contrato;
	}
}
