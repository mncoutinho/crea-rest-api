package br.org.crea.commons.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.cadastro.pessoa.EmailPessoaConverter;
import br.org.crea.commons.converter.cadastro.pessoa.EnderecoConverter;
import br.org.crea.commons.converter.cadastro.pessoa.PessoaConverter;
import br.org.crea.commons.converter.cadastro.pessoa.TelefoneConverter;
import br.org.crea.commons.dao.cadastro.EmailDao;
import br.org.crea.commons.dao.cadastro.empresa.RazaoSocialDao;
import br.org.crea.commons.dao.cadastro.leigo.LeigoPFDao;
import br.org.crea.commons.dao.cadastro.leigo.LeigoPJDao;
import br.org.crea.commons.dao.cadastro.pessoa.EnderecoDao;
import br.org.crea.commons.dao.cadastro.pessoa.InteressadoDao;
import br.org.crea.commons.dao.cadastro.pessoa.PessoaDao;
import br.org.crea.commons.dao.cadastro.pessoa.PessoaFisicaDao;
import br.org.crea.commons.dao.cadastro.pessoa.TelefoneDao;
import br.org.crea.commons.dao.corporativo.GeradorSequenciaDao;
import br.org.crea.commons.dao.protocolo.ProtocoloDao;
import br.org.crea.commons.models.cadastro.EmailPessoa;
import br.org.crea.commons.models.cadastro.dtos.pessoa.DadosContatoPessoaDto;
import br.org.crea.commons.models.commons.Endereco;
import br.org.crea.commons.models.commons.Telefone;
import br.org.crea.commons.models.commons.dtos.DomainGenericDto;
import br.org.crea.commons.models.commons.dtos.EnderecoDto;
import br.org.crea.commons.models.commons.dtos.PesquisaGenericDto;
import br.org.crea.commons.models.commons.dtos.TelefoneDto;
import br.org.crea.commons.models.commons.enuns.TipoEnderecoEnum;
import br.org.crea.commons.models.corporativo.RazaoSocial;
import br.org.crea.commons.models.corporativo.SituacaoRegistro;
import br.org.crea.commons.models.corporativo.dtos.InteressadoWsDto;
import br.org.crea.commons.models.corporativo.dtos.LeigoDto;
import br.org.crea.commons.models.corporativo.dtos.PessoaDto;
import br.org.crea.commons.models.corporativo.dtos.PessoaFisicaDto;
import br.org.crea.commons.models.corporativo.enuns.TipoPessoa;
import br.org.crea.commons.models.corporativo.pessoa.IInteressado;
import br.org.crea.commons.models.corporativo.pessoa.LeigoPF;
import br.org.crea.commons.models.corporativo.pessoa.LeigoPJ;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;
import br.org.crea.commons.models.corporativo.pessoa.PessoaFisica;
import br.org.crea.commons.models.corporativo.pessoa.PessoaJuridica;
import br.org.crea.commons.models.corporativo.pessoa.Profissional;
import br.org.crea.commons.util.StringUtil;

public class PessoaService {

	@Inject
	InteressadoDao interessadoDao;

	@Inject
	ProtocoloDao protocoloDao;

	@Inject
	PessoaConverter pessoaConverter;

	@Inject
	PessoaDao pessoaDao;
	
	@Inject
	PessoaFisicaDao pessoaFisicaDao;

	@Inject
	EmailDao emailDao;

	@Inject
	RazaoSocialDao razaoSocialDao;

	@Inject
	HttpClientGoApi httpGoApi;

	@Inject
	LeigoPFDao leigoPFDao;

	@Inject
	LeigoPJDao leigoPJDao;

	@Inject
	GeradorSequenciaDao geradorSequenciaDao;

	@Inject
	EnderecoConverter enderecoConverter;

	@Inject
	EmailPessoaConverter emailConverter;

	@Inject
	EnderecoDao enderecoDao;

	@Inject
	TelefoneDao telefoneDao;

	@Inject
	TelefoneConverter telefoneConverter;

	public PessoaDto getInteressadoBy(Long idPessoa) {

		IInteressado interessado = interessadoDao.buscaInteressadoBy(idPessoa);
		return interessado != null ? pessoaConverter.toPessoaInteressadoDto(interessado) : new PessoaDto();

	}

	public List<PessoaDto> getPessoaByNumeroCPF(String numeroCPF) {

		List<PessoaFisica> listPessoa = new ArrayList<PessoaFisica>();
		listPessoa = pessoaDao.buscaPessoaFisicaByCPF(numeroCPF);

		return (!listPessoa.isEmpty()) ? pessoaConverter.toListDtoPessoaFisica(listPessoa) : new ArrayList<PessoaDto>();

	}
	
	
	public InteressadoWsDto getInteressadoWSByNumeroCPF(String numeroCPF) {

		PessoaFisica buscaInteressadoWSPessoaFisicaByCPF = pessoaDao.buscaInteressadoWSPessoaFisicaByCPF(numeroCPF);

		return (buscaInteressadoWSPessoaFisicaByCPF != null) ? pessoaConverter.toInteressadoWSPessoaFisicaDto(buscaInteressadoWSPessoaFisicaByCPF) : null;

	}
	
	public InteressadoWsDto getInteressadoWSByNumeroCNPJ(String numeroCNPJ) {

		PessoaJuridica buscaInteressadoWSPessoaJuridicaByCNPJ = pessoaDao.buscaInteressadoWSPessoaJuridicaByCNPJ(numeroCNPJ);

		return (buscaInteressadoWSPessoaJuridicaByCNPJ != null) ? pessoaConverter.toInteressadoWSPessoaJuridicaDto(buscaInteressadoWSPessoaJuridicaByCNPJ) : null;

	}

	public List<PessoaDto> getPessoaByNumeroCNPJ(String numeroCNPJ) {

		List<PessoaJuridica> listPessoa = new ArrayList<PessoaJuridica>();
		listPessoa = pessoaDao.buscaPessoaByCNPJ(numeroCNPJ);

		return (!listPessoa.isEmpty()) ? pessoaConverter.toListDtoPessoaJuridica(listPessoa) : new ArrayList<PessoaDto>();

	}

	public List<PessoaDto> getPessoaFisicaPorNome(PesquisaGenericDto dto) {

		List<PessoaFisica> listPessoa = new ArrayList<PessoaFisica>();
		listPessoa = pessoaDao.buscaListPessoaFisicaByNome(dto);

		return !listPessoa.isEmpty() ? pessoaConverter.toListDtoPessoaFisica(listPessoa) : new ArrayList<PessoaDto>();

	}

	public List<PessoaDto> getPessoaJuridicaPorNome(PesquisaGenericDto dto) {

		List<PessoaJuridica> listPessoa = new ArrayList<PessoaJuridica>();
		listPessoa = pessoaDao.buscaListPessoaJuridicaByNome(dto);

		return !listPessoa.isEmpty() ? pessoaConverter.toListDtoPessoaJuridica(listPessoa) : new ArrayList<PessoaDto>();

	}
	
	public List<PessoaDto> getPessoaJuridicaIsentaCnpjPorNome(PesquisaGenericDto dto) {

		List<PessoaJuridica> listPessoa = new ArrayList<PessoaJuridica>();
		listPessoa = pessoaDao.buscaListPessoaJuridicaIsentaCnpjByNome(dto);

		return !listPessoa.isEmpty() ? pessoaConverter.toListDtoPessoaJuridica(listPessoa) : new ArrayList<PessoaDto>();

	}
	
	public List<PessoaDto> getPessoaFisicaIsentaCpfPorNome(PesquisaGenericDto dto) {

		List<PessoaFisica> listPessoa = new ArrayList<PessoaFisica>();
		listPessoa = pessoaDao.buscaListPessoaFisicaIsentaCpfByNome(dto);

		return !listPessoa.isEmpty() ? pessoaConverter.toListDtoPessoaFisica(listPessoa) : new ArrayList<PessoaDto>();

	}
	
	public List<PessoaDto> getPessoaPor(PesquisaGenericDto pesquisaDto) {

		List<PessoaDto> listPessoa = new ArrayList<PessoaDto>();

		switch (pesquisaDto.getTipo()) {
		case "CPF":
			listPessoa = getPessoaByNumeroCPF(pesquisaDto.getCpf());
			break;
		case "CNPJ":
			listPessoa = getPessoaByNumeroCNPJ(pesquisaDto.getCnpj());
			break;
		case "REGISTRO":
			listPessoa.add(getInteressadoBy(Long.parseLong(pesquisaDto.getRegistro())));
			break;
		case "RNP":
			listPessoa.add(getPessoaPorNumeroRNP(pesquisaDto.getNumeroRNP()));
			break;
		case "PERFIS":
			listPessoa = getPessoaPorIdsPerfil(pesquisaDto.getIdsPerfil());
			break;
		case "DESCRICAO_PERFIL":
			listPessoa = getPessoasPorDescricaoPerfil(pesquisaDto.getDescricaoPerfil());
			break;

		default:
			break;
		}

		return listPessoa;
	}

	private List<PessoaDto> getPessoasPorDescricaoPerfil(String descricaoPerfil) {

		List<Pessoa> listPessoa = new ArrayList<Pessoa>();
		List<IInteressado> listInteressado = new ArrayList<IInteressado>();
		listPessoa = pessoaDao.buscaPessoasPorDescricaoPerfil(descricaoPerfil);

		for (Pessoa p : listPessoa) {
			listInteressado.add(interessadoDao.buscaInteressadoBy(p.getId()));
		}

		return !listPessoa.isEmpty() ? pessoaConverter.toListDto(listInteressado) : new ArrayList<PessoaDto>();
	}

	private List<PessoaDto> getPessoaPorIdsPerfil(List<String> idsPerfil) {
		List<Pessoa> listPessoa = new ArrayList<Pessoa>();
		List<IInteressado> listInteressado = new ArrayList<IInteressado>();
		listPessoa = pessoaDao.buscaPessoaPorIdsPerfil(idsPerfil);

		for (Pessoa p : listPessoa) {
			listInteressado.add(interessadoDao.buscaInteressadoBy(p.getId()));
		}

		return !listPessoa.isEmpty() ? pessoaConverter.toListDto(listInteressado) : new ArrayList<PessoaDto>();
	}

	private PessoaDto getPessoaPorNumeroRNP(String numeroRNP) {

		Profissional profissional = new Profissional();
		profissional = pessoaDao.buscaProfissionalPorNumeroRNP(numeroRNP);

		return profissional != null ? pessoaConverter.toDtoProfissional(profissional) : new PessoaDto();

	}

	public LeigoDto cadastrarLeigo(LeigoDto dto) {

		try {

			if (!verificaExistenciaPessoa(dto)) {
				
				Pessoa pessoa = new Pessoa();

				dto.setId(geradorSequenciaDao.getSequencia(0L));
				
				if (dto.getTipoPessoa().equals(TipoPessoa.LEIGOPF)) {
					
					if(dto.ehIsentoCpf()) {
						dto.setCpfOuCnpj(getCpfIsento(dto.getId()));
					}

					LeigoPF leigoPF = leigoPFDao.create(pessoaConverter.toLeigoPF(dto));
					
					if(dto.getEndereco() != null){
						salvarEndereco(dto.getEndereco(), leigoPF.getPessoaFisica());
					}	

					pessoa = new Pessoa();
					pessoa.setId(leigoPF.getId());

				} else {
					
					if(dto.ehIsentoCnpj()) {
						dto.setCpfOuCnpj(getCnpjIsento(dto.getId()));
					}

					LeigoPJ leigoPJ = pessoaConverter.toLeigoPJ(dto);

					leigoPJDao.create(leigoPJ);
					
					if(dto.getEndereco() != null){

						salvarEndereco(dto.getEndereco(), leigoPJ.getPessoaJuridica());
					
					}

					cadastrarRazaoSocial(dto.getNome(), leigoPJ.getPessoaJuridica());

					pessoa.setId(leigoPJ.getId());

				}
				
				cadastrarTelefoneEEmail(dto, pessoa);

			}

		} catch (Exception e) {
			httpGoApi.geraLog("PessoaService || CadastrarLeigo ", StringUtil.convertObjectToJson(dto), e);
		}

		return dto;
	}

	private String getCnpjIsento(Long id) {
	       String registro = String.valueOf(id);
           String zeros = "";
           int numero = 14 -registro.length();
           for (int i = 0; i < numero; i++) {
               zeros += "0";
           }
           String cpf = "2" + zeros + registro;
		return cpf;
	}

	private String getCpfIsento(Long id) {
	       String registro = String.valueOf(id);
           String zeros = "";
           int numero = 14 -registro.length();
           for (int i = 0; i < numero; i++) {
               zeros += "0";
           }
           String cpf = "1" + zeros + registro;
		return cpf;
	}

	private void cadastrarTelefoneEEmail(LeigoDto dto, Pessoa pessoa) {
		if (dto.temTelefones()) {
			salvarTelefone(dto.getTelefones(), pessoa);
		}

		if (dto.getEmail() != null) {
			salvarEmail(dto.getEmail(), pessoa);
		}
	}

	private void salvarEmail(String email, Pessoa pessoa) {
		EmailPessoa novoEmail = emailConverter.toModel(email, pessoa.getId(), null);

		if (!emailDao.existeEmailCadastradoPessoa(email, pessoa.getId())) {
			emailDao.create(novoEmail);
		}

	}

	private void salvarEndereco(EnderecoDto dto, Pessoa pessoa) {
	
		if(!enderecoDao.existeEnderecoValidoEPostalPessoa(pessoa)){
			Endereco endereco = enderecoConverter.toModel(dto);
			endereco.setPessoa(pessoa);
			endereco.setPostal(true);
			endereco.setValido(true);
			endereco.setTipoEndereco(TipoEnderecoEnum.getTipoEndereco(TipoEnderecoEnum.POSTAL));
	
			endereco = enderecoDao.create(endereco);
		}	

	}

	private void salvarTelefone(List<TelefoneDto> lista, Pessoa pessoa) {

		for (TelefoneDto dto : lista) {

			Telefone telefone = telefoneConverter.toModel(dto);

			if (!telefoneDao.existeTelefoneCadastrado(telefone, pessoa)) {
				telefone.setPessoa(pessoa);

				telefoneDao.create(telefone);
			}
		}
	}

	public RazaoSocial cadastrarRazaoSocial(String nome, PessoaJuridica pessoaJuridica) {
		RazaoSocial razaoSocial = new RazaoSocial();
		razaoSocial.setId(pessoaJuridica.getId());
		razaoSocial.setAtivo(true);
		razaoSocial.setDescricao(nome.toUpperCase());
		razaoSocial.setPessoaJuridica(pessoaJuridica);
		razaoSocial.setDataCadastro(new Date());

		return razaoSocialDao.create(razaoSocial);
	}

	private boolean verificaExistenciaPessoa(LeigoDto dto) {
		if (dto.getTipoPessoa().equals(TipoPessoa.LEIGOPF)) {
			List<PessoaFisica> lista = pessoaDao.buscaPessoaFisicaByCPF(dto.getCpfOuCnpj());

			return lista.size() > 0;
		} else {
			List<PessoaJuridica> lista = pessoaDao.buscaPessoaJuridicaByCnpj(dto.getCpfOuCnpj());

			return lista.size() > 0;
		}
	}

	public List<PessoaDto> getPessoaByNumeroCNPJMatriz(String numeroCNPJ) {
		List<PessoaJuridica> listPessoa = new ArrayList<PessoaJuridica>();
		listPessoa = pessoaDao.buscaPessoaJuridicaByCnpjMatriz(numeroCNPJ);

		return (!listPessoa.isEmpty()) ? pessoaConverter.toListDtoPessoaJuridica(listPessoa) : new ArrayList<PessoaDto>();
	}

	public DadosContatoPessoaDto getDadosContatoPessoaPor(Long idPessoa) {
		DadosContatoPessoaDto contatos = new DadosContatoPessoaDto();

		try {
			contatos.setEnderecoPostal(enderecoConverter.toDto(enderecoDao.getEnderecoValidoEPostalPor(idPessoa)));
			contatos.setEmail(emailDao.getUltimoEmailCadastradoPor(idPessoa));
			contatos.setListTelefones(telefoneConverter.toListDto(telefoneDao.getListTelefoneByPessoa(idPessoa)));

		} catch (Throwable e) {
			httpGoApi.geraLog("PessoaService || getDadosContatoPessoaPor", StringUtil.convertObjectToJson(idPessoa), e);
		}
		return contatos;
	}

	public Pessoa getPessoaByTipoPessoa(String tipoPessoa, String cpfOuCnpj) {

		Pessoa pessoa = null;

		if (tipoPessoa.equals(TipoPessoa.PESSOAJURIDICA.name()) || tipoPessoa.equals(TipoPessoa.LEIGOPJ.name())) {

			List<PessoaJuridica> pessoas = pessoaDao.buscaPessoaJuridicaByCnpj(cpfOuCnpj);

			if (!pessoas.isEmpty()) {
				pessoa = pessoas.get(0);
			}

		} else if (tipoPessoa.equals(TipoPessoa.PESSOAFISICA.name()) || tipoPessoa.equals(TipoPessoa.LEIGOPF.name())) {
			List<PessoaFisica> pessoas = pessoaDao.buscaPessoaFisicaByCPF(cpfOuCnpj);

			if (!pessoas.isEmpty()) {
				pessoa = pessoas.get(0);
			}
		}

		return pessoa;
	}

	public void alterarResponsavel(PessoaDto dto) {
		Pessoa pessoa = pessoaDao.getPessoa(dto.getId());

		Pessoa responsavel = pessoaDao.getPessoa(dto.getIdPessoaResponsavel());

		pessoa.setIdPessoaResponsavel(responsavel.getId());

		pessoaDao.update(pessoa);
		
		salvarEmail(dto.getEmail(), pessoa);
	}

	public List<PessoaFisicaDto> getPessoaFisicaDetalhadaPorId(Long idPessoa) {

		List<PessoaFisica> listPessoa = new ArrayList<PessoaFisica>();
		listPessoa = pessoaDao.buscaPessoaFisicaPorId(idPessoa);

		return (!listPessoa.isEmpty()) ? pessoaConverter.toListDtoPessoaFisicaDetalhada(listPessoa) : new ArrayList<PessoaFisicaDto>();

	}

	public int totalBuscaListPessoaJuridicaByNome(PesquisaGenericDto pesquisa) {
		return pessoaDao.totalBuscaListPessoaJuridicaByNome(pesquisa);
	}
	
	public int totalBuscaListPessoaFisicaByNome(PesquisaGenericDto pesquisa) {
		return pessoaDao.totalBuscaListPessoaFisicaByNome(pesquisa);
	}

	public PessoaDto buscarPessoaJuridica(Long registro) {
		return pessoaConverter.toPessoaJuridicaDto(pessoaDao.getPessoaJuridicaPor(registro));
	}
	
	public PessoaDto buscarPessoaFisica(Long id) {
		return pessoaConverter.toPessoaFisicaDto(pessoaFisicaDao.getBy(id));
	}

	public boolean atualizaIdInstituicao(PessoaDto dto) {
		return pessoaDao.atualizaIdInstituicao(dto);
		}
	
	public PessoaDto getPessoaBy(Long idPessoa) {
		Pessoa pessoa = pessoaDao.getBy(idPessoa);
		if(pessoa != null) {
			return pessoa.ehPessoaFisica() ? buscarPessoaFisica(idPessoa) : buscarPessoaJuridica(idPessoa);
		}
		return null;
	}
	
	public List<PessoaDto> getPessoaFisicaPorNomePaginado(PesquisaGenericDto dto) {

		List<PessoaFisica> listPessoa = new ArrayList<PessoaFisica>();
		listPessoa = pessoaDao.buscaListPessoaFisicaByNomePaginado(dto);

		return !listPessoa.isEmpty() ? pessoaConverter.toListDtoPessoaFisica(listPessoa) : new ArrayList<PessoaDto>();

	}
	
	public List<PessoaDto> getPessoaJuridicaPorNomePaginado(PesquisaGenericDto dto) {

		List<PessoaJuridica> listPessoa = new ArrayList<PessoaJuridica>();
		listPessoa = pessoaDao.buscaListPessoaJuridicaByNomePaginado(dto);

		return !listPessoa.isEmpty() ? pessoaConverter.toListDtoPessoaJuridica(listPessoa) : new ArrayList<PessoaDto>();

	}
	
	public InteressadoWsDto getInteressadoWSPor(String cpfOuCnpj) {

		if(StringUtil.isBlank(cpfOuCnpj)) return null;

		if(cpfOuCnpj.trim().length() == 11)  return getInteressadoWSByNumeroCPF(cpfOuCnpj);
		
		if(cpfOuCnpj.trim().length() == 14)  return getInteressadoWSByNumeroCNPJ(cpfOuCnpj);
		
		return null;

	}

	public DomainGenericDto getSituacaoRegistro(Long idPessoa) {
		DomainGenericDto dto = new DomainGenericDto();
		
		SituacaoRegistro situacao = pessoaDao.getSituacaoRegistro(idPessoa);
		if (situacao != null) {
			dto.setId(idPessoa);
			dto.setCodigo(situacao.getId());
			dto.setDescricao(situacao.getDescricao());
		}
		
		return dto;
	}

	public LeigoDto getLeigoPF(String numeroCPF) {
		List<PessoaDto> listDto = pessoaConverter.toListDtoPessoaFisica(pessoaDao.buscaPessoaFisicaByCPF(numeroCPF));
		PessoaDto pessoaDto = listDto.get(0);
		LeigoDto leigoDto = new LeigoDto();
		leigoDto.setId(pessoaDto.getId());
		leigoDto.setEmail(emailDao.getUltimoEmailCadastradoPor(pessoaDto.getId()));
		leigoDto.setNome(pessoaDto.getNome());
		leigoDto.setCpfOuCnpj(numeroCPF);
		return leigoDto;
	}

	public LeigoDto getLeigoPJ(String numeroCNPJ) {
		List<PessoaDto> listDto = pessoaConverter.toListDtoPessoaJuridica(pessoaDao.buscaPessoaByCNPJ(numeroCNPJ));
		PessoaDto pessoaDto = listDto.get(0);
		LeigoDto leigoDto = new LeigoDto();
		leigoDto.setId(pessoaDto.getId());
		leigoDto.setEmail(emailDao.getUltimoEmailCadastradoPor(pessoaDto.getId()));
		leigoDto.setNome(pessoaDto.getNome());
		leigoDto.setCpfOuCnpj(numeroCNPJ);
		return leigoDto;
	}

}
