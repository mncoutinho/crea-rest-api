package br.org.crea.commons.service.cadastro;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import br.org.crea.commons.converter.cadastro.ParticipantePremioTCTConverter;
import br.org.crea.commons.converter.cadastro.PremioTCTConverter;
import br.org.crea.commons.converter.cadastro.pessoa.EnderecoConverter;
import br.org.crea.commons.dao.cadastro.EmailDao;
import br.org.crea.commons.dao.cadastro.ParticipantePremioTCTDao;
import br.org.crea.commons.dao.cadastro.PremioTCTDao;
import br.org.crea.commons.dao.cadastro.pessoa.EnderecoDao;
import br.org.crea.commons.dao.cadastro.pessoa.TelefoneDao;
import br.org.crea.commons.dao.cadastro.profissional.ProfissionalDao;
import br.org.crea.commons.dao.cadastro.profissional.ProfissionalEspecialidadeDao;
import br.org.crea.commons.models.cadastro.ParticipantePremioTCT;
import br.org.crea.commons.models.cadastro.PremioTCT;
import br.org.crea.commons.models.cadastro.dtos.ParticipantePremioTCTDto;
import br.org.crea.commons.models.cadastro.dtos.premio.PremioTCTDto;
import br.org.crea.commons.models.cadastro.dtos.premio.RelPremioQuantitativoDto;
import br.org.crea.commons.models.commons.Endereco;
import br.org.crea.commons.models.commons.Telefone;
import br.org.crea.commons.models.commons.dtos.ArquivoDto;
import br.org.crea.commons.models.commons.dtos.EnderecoDto;
import br.org.crea.commons.models.corporativo.pessoa.Profissional;
import br.org.crea.commons.util.ExcelPoiUtil;

public class PremioTCTService {

	@Inject
	private PremioTCTDao dao;

	@Inject
	private ParticipantePremioTCTDao participanteDao;

	@Inject
	private ParticipantePremioTCTConverter participanteConverter;

	@Inject
	private PremioTCTConverter converter;

	@Inject
	private EnderecoConverter enderecoConverter;

	@Inject
	private EnderecoDao enderecoDao;

	@Inject
	private ProfissionalEspecialidadeDao profissionalEspecialidadeDao;

	@Inject
	private ProfissionalDao profissionalDao;

	@Inject
	private EmailDao emailDao;

	@Inject
	private TelefoneDao telefoneDao;

	public PremioTCTDto premio(PremioTCTDto dto) {
		PremioTCT premio = dao.getPremio(dto);

		if (premio == null) {
			premio = converter.toModel(dto);
			dao.create(premio);
			premio = dao.getPremioById(premio.getId());
			return converter.toDtoComParticipantes(premio);
		} else {
			return converter.toDtoComParticipantes(premio);
		}
	}

	public boolean possuiDezIndicacoesFinalizadas(PremioTCTDto dto) {
		return dao.possuiDezIndicacoesFinalizadas(dto);
	}

	public PremioTCTDto salvaPremioNivelMestradoOuDoutorado(PremioTCTDto dto) {
		PremioTCT premio = dao.getPremioNaoFinalizado(dto);
		if (premio == null) {
			premio = converter.toModel(dto);
			dao.create(premio);
			premio = dao.getPremioById(premio.getId());
			return converter.toDtoComParticipantes(premio);
		} else {
			return converter.toDtoComParticipantes(premio);
		}
	}

	public PremioTCTDto atualizaIndicacao(PremioTCTDto dto) {
		dao.atualizaIndicacao(dto);
		return dto;
	}

	public PremioTCTDto atualizaAceite(PremioTCTDto dto) {
		dao.atualizaAceiteEStatus(dto);
		return dto;
	}

	public void deleta(Long id) {
		PremioTCT premioTCT = dao.getPremioById(id);
		if (premioTCT != null) {
			if (premioTCT.temArquivo()) {
				dao.deletaArquivo(id);
			}
			participanteDao.deleteByIdPremio(id);
		}
		dao.deleta(id);
	}

	public List<ParticipantePremioTCTDto> getParticipantes(Long idPremio) {
		return participanteConverter.toListDto(participanteDao.getListParticipantes(idPremio));
	}

	public ParticipantePremioTCTDto participante(ParticipantePremioTCTDto dto) {
		ParticipantePremioTCT participante = participanteConverter.toModel(dto);
		participanteDao.create(participante);
		dto = participanteConverter.toDto(participante);

		return dto;
	}

	public ParticipantePremioTCTDto deletaParticipante(Long id) {
		ParticipantePremioTCTDto dto = participanteConverter.toDto(participanteDao.deleta(id));
		return dto;
	}

	public ParticipantePremioTCTDto atualizaParticipante(ParticipantePremioTCTDto dto) {
		dao.atualizaParticipante(dto);
		return dto;
	}

	public PremioTCTDto atualizaArquivoTermo(PremioTCTDto dto) {
		dao.atualizaArquivoTermo(dto);
		return dto;
	}

	public PremioTCTDto atualizaArquivoResumo(PremioTCTDto dto) {
		dao.atualizaArquivoResumo(dto);
		return dto;
	}

	public PremioTCTDto atualizaArquivo(PremioTCTDto dto) {
		dao.atualizaArquivo(dto);
		return dto;
	}

	public void deletarArquivo(Long id) {
		dao.deletaArquivo(id);
	}
	
	public void deletarArquivoResumo(Long id) {
		dao.deletaArquivoResumo(id);
	}
	
	public void deletarArquivoTermo(Long id) {
		dao.deletaArquivoTermo(id);
	}

	public PremioTCTDto getPremioByID(Long id) {
		return converter.toDtoComParticipantes(dao.getPremioById(id));
	}

	public List<PremioTCTDto> pesquisa(PremioTCTDto dto) {
		return converter.toListDtoSemParticipantes(dao.pesquisa(dto));
	}

	public boolean verificaLimiteDeQuatroAutores(List<ParticipantePremioTCTDto> lista, ParticipantePremioTCTDto dto) {
		return lista.stream().map(ParticipantePremioTCTDto::getPapel).filter(dto.getPapel()::equals).count() == 4;
	}

	public boolean verificaAcumulacaoPapelAutor(List<ParticipantePremioTCTDto> lista, ParticipantePremioTCTDto dto) {
		for (ParticipantePremioTCTDto participante : lista) {
			if (participante.getPessoa().getId().equals(dto.getPessoa().getId())) {
				return true;
			}
		}
		return false;
	}

	public boolean verificaLimiteDeTresAvaliadores(List<ParticipantePremioTCTDto> lista, ParticipantePremioTCTDto dto) {
		return lista.stream().map(ParticipantePremioTCTDto::getPapel).filter(dto.getPapel()::equals).count() == 3;
	}

	public boolean verificaAcumulacaoPapelAvaliador(List<ParticipantePremioTCTDto> lista,
			ParticipantePremioTCTDto dto) {
		for (ParticipantePremioTCTDto participante : lista) {
			if (participante.getPessoa().getId().equals(dto.getPessoa().getId())
					&& (participante.getPapel().equals("2") || participante.getPapel().equals("1"))) {
				return true;
			}
		}
		return false;
	}

	public boolean verificaLimiteDeUmCoorientador(List<ParticipantePremioTCTDto> lista, ParticipantePremioTCTDto dto) {
		return lista.stream().map(ParticipantePremioTCTDto::getPapel).filter(dto.getPapel()::equals).count() == 1;
	}

	public boolean verificaAcumulacaoPapelCoorientador(List<ParticipantePremioTCTDto> lista,
			ParticipantePremioTCTDto dto) {
		for (ParticipantePremioTCTDto participante : lista) {
			if (participante.getPessoa().getId().equals(dto.getPessoa().getId()) && (participante.getPapel().equals("3")
					|| participante.getPapel().equals("4") || (participante.getPapel().equals("1")))) {
				return true;
			}
		}
		return false;
	}

	public boolean verificaLimiteDeUmOrientador(List<ParticipantePremioTCTDto> lista, ParticipantePremioTCTDto dto) {
		return lista.stream().map(ParticipantePremioTCTDto::getPapel).filter(dto.getPapel()::equals).count() == 1;
	}

	public boolean verificaAcumulacaoPapelOrientador(List<ParticipantePremioTCTDto> lista,
			ParticipantePremioTCTDto dto) {
		for (ParticipantePremioTCTDto participante : lista) {
			if (participante.getPessoa().getId().equals(dto.getPessoa().getId())
					&& ((participante.getPapel().equals("1")) || (participante.getPapel().equals("3")))) {
				return true;
			}
		}
		return false;
	}

	public boolean verificaLimiteTresComissao(List<ParticipantePremioTCTDto> lista, ParticipantePremioTCTDto dto) {
		return lista.stream().map(ParticipantePremioTCTDto::getPapel).filter(dto.getPapel()::equals).count() == 3;
	}

	public boolean verificaAcumulacaoPapelComissao(List<ParticipantePremioTCTDto> lista, ParticipantePremioTCTDto dto) {
		for (ParticipantePremioTCTDto participante : lista) {
			if (participante.getPessoa().getId().equals(dto.getPessoa().getId())
					&& (participante.getPapel().equals("1") || participante.getPapel().equals("5"))) {
				return true;
			}
		}
		return false;
	}

	public byte[] getRelatorio(PremioTCTDto dto) {

		switch (dto.getTipoRelatorio()) {

		case "MALA_DIRETA":
			return getRelatorioMalaDireta(dto);

		case "CRACHA":
			return getRelatorioCracha(dto);

		case "CERTIFICADO":
			return getRelatorioCertificado(dto);

		case "QUANTITATIVO":
			return getRelatorioQuantitativo(dto);

		case "CONFIRMACAO_PRESENCA":
			return getRelatorioConfirmacaoPresenca(dto);

		case "PLANILHAO":
			return getRelatorioPlanilhao(dto);
		}
		return null;
	}

	private byte[] getRelatorioMalaDireta(PremioTCTDto dto) {
		List<ParticipantePremioTCTDto> listaParticipantes = participanteConverter.toListDto(dao.relatorioCracha(dto));

		for (ParticipantePremioTCTDto participante : listaParticipantes) {
			participante.getPessoa().setEnderecoPostal(
					enderecoConverter.toDto(enderecoDao.getEnderecoValidoEPostalPor(participante.getPessoa().getId())));
		}

		return gerarPlanilhaMalaDireta(listaParticipantes);
	}

	private byte[] getRelatorioCracha(PremioTCTDto dto) {
		List<ParticipantePremioTCT> listaParticipantes = dao.relatorioCracha(dto);
		return gerarPlanilhaCracha(listaParticipantes);
	}

	private byte[] getRelatorioCertificado(PremioTCTDto dto) {
		List<ParticipantePremioTCT> listaParticipantes = dao.relatorioCertificado(dto);
		return gerarPlanilhaCertificado(listaParticipantes);
	}

	private byte[] gerarPlanilhaMalaDireta(List<ParticipantePremioTCTDto> listaParticipantes) {
		try {
			ExcelPoiUtil excelPoiUtil = new ExcelPoiUtil();
			excelPoiUtil.setCell("GRUPO");
			excelPoiUtil.setCell("NOME");
			excelPoiUtil.setCell("TITULO");
			excelPoiUtil.setCell("EMAIL");
			excelPoiUtil.setCell("ENDEREÇO");
			excelPoiUtil.setCell("BAIRRO");
			excelPoiUtil.setCell("CIDADE");
			excelPoiUtil.setCell("ESTADO");
			excelPoiUtil.setCell("CEP");
			listaParticipantes.forEach(participante -> {
				excelPoiUtil.newRow();
				excelPoiUtil.setCell(converter.toDescricaoPapel(participante.getPapel()));
				excelPoiUtil.setCell(participante.getPessoa().getNome());
				excelPoiUtil.setCell(populaTituloProfissional(participante.getPessoa().getId()));
				excelPoiUtil.setCell(participante.getEmail());
				// nao utilizar endereco completo, concatenar valores
				if (participante.getPessoa().temEnderecoPostal()) {
					excelPoiUtil.setCell(populaEndereco(participante.getPessoa().getEnderecoPostal()));
					excelPoiUtil.setCell(participante.getPessoa().getEnderecoPostal().getBairro());
					excelPoiUtil.setCell(participante.getPessoa().getEnderecoPostal().temLocalidade()
							? participante.getPessoa().getEnderecoPostal().getLocalidade().getDescricao()
							: "");
					excelPoiUtil.setCell(participante.getPessoa().getEnderecoPostal().temUf()
							? participante.getPessoa().getEnderecoPostal().getUf().getSigla()
							: "");
					excelPoiUtil.setCell(participante.getPessoa().getEnderecoPostal().getCep());
				} else {
					excelPoiUtil.setCell("");
					excelPoiUtil.setCell("");
					excelPoiUtil.setCell("");
					excelPoiUtil.setCell("");
					excelPoiUtil.setCell("");
				}

			});

			return excelPoiUtil.buildToStream();
		} catch (Exception e) {
//			httpgo
			System.err.println(e.getMessage());
		}
		return null;
	}

	private String populaEndereco(EnderecoDto dto) {
		return dto.getTipoLogradouro().getDescricao() + " " + dto.getLogradouro() + " " + dto.getNumero() + " "
				+ (dto.getComplemento() != null ? dto.getComplemento() : "");
	}

	private byte[] getRelatorioQuantitativo(PremioTCTDto dto) { // continuar com as listagens

		try {
			ExcelPoiUtil excelPoiUtil = new ExcelPoiUtil();

			List<String> instituicoes = dao.getInstituicoes(dto);

			excelPoiUtil.setCell("TOTAL DE TRABALHOS INSCRITOS");
			excelPoiUtil.setCell(String.valueOf(dao.getTotalDeTrabalhosInscritos(dto)));

			excelPoiUtil.newRow();
			excelPoiUtil.setCell("TOTAL DE INSTITUIÇÕES");
			excelPoiUtil.setCell(String.valueOf(instituicoes.size()));

			excelPoiUtil.newRow();
			excelPoiUtil.setCell("NÍVEL MÉDIO");
			excelPoiUtil.setCell(String.valueOf(dao.getTotalDeTrabalhosPorNivel(dto.getAno(), "TÉCNICO")));

			excelPoiUtil.newRow();
			excelPoiUtil.setCell("NÍVEL SUPERIOR");
			excelPoiUtil.setCell(String.valueOf(dao.getTotalDeTrabalhosPorNivel(dto.getAno(), "GRADUAÇÃO")));

			excelPoiUtil.newRow();
			excelPoiUtil.setCell("NÍVEL MESTRADO");
			excelPoiUtil.setCell(String.valueOf(dao.getTotalDeTrabalhosPorNivel(dto.getAno(), "MESTRADO")));

			excelPoiUtil.newRow();
			excelPoiUtil.setCell("NÍVEL DOUTORADO");
			excelPoiUtil.setCell(String.valueOf(dao.getTotalDeTrabalhosPorNivel(dto.getAno(), "DOUTORADO")));

			excelPoiUtil.newRow();
			excelPoiUtil.setCell("TOTAL DE CURSOS ABRANGIDOS");
			excelPoiUtil.setCell(String.valueOf(dao.getTotalDeCursosAbrangidos(dto)));

			excelPoiUtil.newRow();
			excelPoiUtil.setCell("AUTORES");
			excelPoiUtil.setCell(String.valueOf(dao.getTotalDeParticipantesPorPapel(dto.getAno(), 1L)));

			excelPoiUtil.newRow();
			excelPoiUtil.setCell("AVALIADORES");
			excelPoiUtil.setCell(String.valueOf(dao.getTotalDeParticipantesPorPapel(dto.getAno(), 2L)));

			excelPoiUtil.newRow();
			excelPoiUtil.setCell("ORIENTADORES");
			excelPoiUtil.setCell(String.valueOf(dao.getTotalDeParticipantesPorPapel(dto.getAno(), 4L)));

			excelPoiUtil.newRow();
			excelPoiUtil.setCell("COORIENTADORES");
			excelPoiUtil.setCell(String.valueOf(dao.getTotalDeParticipantesPorPapel(dto.getAno(), 3L)));

			excelPoiUtil.newRow();
			excelPoiUtil.newRow();
			excelPoiUtil.setCell("INSTITUIÇÕES DE ENSINO");
			excelPoiUtil.setCell("QTD AUTORES");

			List<RelPremioQuantitativoDto> qtdAutoresPorInstituicao = dao
					.getRelatorioQuantitativoAutoresPorInstituicao(dto);

			for (RelPremioQuantitativoDto quantitativo : qtdAutoresPorInstituicao) {
				excelPoiUtil.newRow();
				excelPoiUtil.setCell(quantitativo.getInstituicao());
				excelPoiUtil.setCell(quantitativo.getQuantidade());
			}

			excelPoiUtil.newRow();
			excelPoiUtil.newRow();
			excelPoiUtil.setCell("GRUPO");
			excelPoiUtil.setCell("QTD AUTORES");

			List<RelPremioQuantitativoDto> qtdAutoresPorTipo = dao.getRelatorioQuantitativoAutoresPorNivel(dto);

			for (RelPremioQuantitativoDto quantitativo : qtdAutoresPorTipo) {
				excelPoiUtil.newRow();
				excelPoiUtil.setCell(quantitativo.getInstituicao());
				excelPoiUtil.setCell(quantitativo.getQuantidade());
			}

			excelPoiUtil.newRow();
			excelPoiUtil.newRow();
			excelPoiUtil.setCell("INSTITUIÇÕES DE ENSINO");
			excelPoiUtil.setCell("CURSOS");
			excelPoiUtil.setCell("NÍVEL");

			List<PremioTCT> instituicoesCursoNivel = dao.getRelatorioQuantitativoInstituicaoCursoENivel(dto);

			for (PremioTCT instituicao : instituicoesCursoNivel) {
				excelPoiUtil.newRow();
				excelPoiUtil.setCell(instituicao.getNomeInstituicaoEnsino());
				excelPoiUtil.setCell(instituicao.getIdCursoCouchDb() != null ? instituicao.getNomeCurso()
						: instituicao.getProtocoloCurso() + " - " + instituicao.getDescricaoCurso());
				excelPoiUtil.setCell(converter.toDescricaoNivel(instituicao.getNivel()));
			}

			return excelPoiUtil.buildToStream();
		} catch (Exception e) {
//			httpgo
			System.err.println(e.getMessage());
		}
		return null;
	}

	private byte[] getRelatorioConfirmacaoPresenca(PremioTCTDto dto) {
		List<ParticipantePremioTCT> listaParticipantes = dao.relatorioConfirmacaoPresenca(dto);
		return gerarPlanilhaConfirmacaoPresenca(listaParticipantes);
	}

	private byte[] getRelatorioPlanilhao(PremioTCTDto dto) {
		List<PremioTCTDto> listaPremios = converter.toListDtoComParticipantes(dao.getPremiosFinalizados(dto));
		return gerarPlanilhao(listaPremios);
	}

	public byte[] gerarPlanilhaCracha(List<ParticipantePremioTCT> listaParticipantes) {
		try {
			ExcelPoiUtil excelPoiUtil = new ExcelPoiUtil();
			excelPoiUtil.setCell("GRUPO");
			excelPoiUtil.setCell("NOME");
			excelPoiUtil.setCell("TÍTULO");
			excelPoiUtil.setCell("INSTITUIÇÃO");
			listaParticipantes.forEach(participante -> {
				excelPoiUtil.newRow();
				excelPoiUtil.setCell(converter.toDescricaoPapel(participante.getPapel()));
				excelPoiUtil.setCell(participante.getPessoa().getNome());
				excelPoiUtil.setCell(populaTituloProfissional(participante.getPessoa().getId()));
				excelPoiUtil.setCell(participante.getPremio().getNomeInstituicaoEnsino());
			});

			return excelPoiUtil.buildToStream();
		} catch (Exception e) {
//			httpgo
			System.err.println(e.getMessage());
		}
		return null;
	}

	private byte[] gerarPlanilhaCertificado(List<ParticipantePremioTCT> listaParticipantes) {
		try {
			ExcelPoiUtil excelPoiUtil = new ExcelPoiUtil();
			excelPoiUtil.setCell("GRUPO");
			excelPoiUtil.setCell("NOME");
			excelPoiUtil.setCell("TITULO");
			excelPoiUtil.setCell("CURSO");
			listaParticipantes.forEach(participante -> {
				excelPoiUtil.newRow();
				excelPoiUtil.setCell(converter.toDescricaoPapel(participante.getPapel()));
				excelPoiUtil.setCell(participante.getPessoa().getNome());
				excelPoiUtil.setCell(participante.getPremio().getTitulo());
				excelPoiUtil.setCell(participante.getPremio().getNomeCurso());
			});

			return excelPoiUtil.buildToStream();
		} catch (Exception e) {
//			httpgo
			System.err.println(e.getMessage());
		}
		return null;
	}

	private byte[] gerarPlanilhaConfirmacaoPresenca(List<ParticipantePremioTCT> listaParticipantes) {
		try {
			ExcelPoiUtil excelPoiUtil = new ExcelPoiUtil();
			excelPoiUtil.setCell("GRUPO");
			excelPoiUtil.setCell("NOME");
			excelPoiUtil.setCell("INSTITUIÇÃO");
			listaParticipantes.forEach(participante -> {
				excelPoiUtil.newRow();
				excelPoiUtil.setCell(converter.toDescricaoPapel(participante.getPapel()));
				excelPoiUtil.setCell(participante.getPessoa().getNome());
				excelPoiUtil.setCell(participante.getPremio().getNomeInstituicaoEnsino());
			});

			return excelPoiUtil.buildToStream();
		} catch (Exception e) {
//			httpgo
			System.err.println(e.getMessage());
		}
		return null;
	}

	private byte[] gerarPlanilhao(List<PremioTCTDto> listaPremios) {
		try {
			ExcelPoiUtil excelPoiUtil = new ExcelPoiUtil();
			excelPoiUtil.setCell("ID");
			excelPoiUtil.setCell("ANO");
			excelPoiUtil.setCell("DATA");
			excelPoiUtil.setCell("INSTITUIÇÃO");
			excelPoiUtil.setCell("CAMPUS");
			excelPoiUtil.setCell("CURSO");
			excelPoiUtil.setCell("NÍVEL");
			excelPoiUtil.setCell("TÍTULO DO TRABALHO");
			excelPoiUtil.setCell("ACEITE");
			excelPoiUtil.setCell("ARQUIVO");
			excelPoiUtil.setCell("RESUMO");
			excelPoiUtil.setCell("TERMO");
			excelPoiUtil.setCell("FINALIZADO");

			excelPoiUtil.setCell("RESPONSÁVEL CPF");
			excelPoiUtil.setCell("RESPONSÁVEL NOME");
			excelPoiUtil.setCell("RESPONSÁVEL E-MAIL");
			excelPoiUtil.setCell("RESPONSÁVEL TELEFONES");
			excelPoiUtil.setCell("RESPONSÁVEL ENDEREÇO");
			excelPoiUtil.setCell("RESPONSÁVEL MUNICIPIO");
			excelPoiUtil.setCell("RESPONSÁVEL UF");
			excelPoiUtil.setCell("RESPONSÁVEL CEP");

			excelPoiUtil.setCell("AUTOR 1 CPF");
			excelPoiUtil.setCell("AUTOR 1 NOME");
			excelPoiUtil.setCell("AUTOR 1 TÍTULOS");
			excelPoiUtil.setCell("AUTOR 1 E-MAIL");
			excelPoiUtil.setCell("AUTOR 1 TELEFONES");
			excelPoiUtil.setCell("AUTOR 1 ENDEREÇO");
			excelPoiUtil.setCell("AUTOR 1 MUNICIPIO");
			excelPoiUtil.setCell("AUTOR 1 UF");
			excelPoiUtil.setCell("AUTOR 1 CEP");

			excelPoiUtil.setCell("AUTOR 2 CPF");
			excelPoiUtil.setCell("AUTOR 2 NOME");
			excelPoiUtil.setCell("AUTOR 2 TÍTULOS");
			excelPoiUtil.setCell("AUTOR 2 E-MAIL");
			excelPoiUtil.setCell("AUTOR 2 TELEFONES");
			excelPoiUtil.setCell("AUTOR 2 ENDEREÇO");
			excelPoiUtil.setCell("AUTOR 2 MUNICIPIO");
			excelPoiUtil.setCell("AUTOR 2 UF");
			excelPoiUtil.setCell("AUTOR 2 CEP");

			excelPoiUtil.setCell("AUTOR 3 CPF");
			excelPoiUtil.setCell("AUTOR 3 NOME");
			excelPoiUtil.setCell("AUTOR 3 TÍTULOS");
			excelPoiUtil.setCell("AUTOR 3 E-MAIL");
			excelPoiUtil.setCell("AUTOR 3 TELEFONES");
			excelPoiUtil.setCell("AUTOR 3 ENDEREÇO");
			excelPoiUtil.setCell("AUTOR 3 MUNICIPIO");
			excelPoiUtil.setCell("AUTOR 3 UF");
			excelPoiUtil.setCell("AUTOR 3 CEP");

			excelPoiUtil.setCell("AUTOR 4 CPF");
			excelPoiUtil.setCell("AUTOR 4 NOME");
			excelPoiUtil.setCell("AUTOR 4 TÍTULOS");
			excelPoiUtil.setCell("AUTOR 4 E-MAIL");
			excelPoiUtil.setCell("AUTOR 4 TELEFONES");
			excelPoiUtil.setCell("AUTOR 4 ENDEREÇO");
			excelPoiUtil.setCell("AUTOR 4 MUNICIPIO");
			excelPoiUtil.setCell("AUTOR 4 UF");
			excelPoiUtil.setCell("AUTOR 4 CEP");

			excelPoiUtil.setCell("AVALIADOR CPF");
			excelPoiUtil.setCell("AVALIADOR NOME");
			excelPoiUtil.setCell("AVALIADOR TÍTULOS");
			excelPoiUtil.setCell("AVALIADOR E-MAIL");
			excelPoiUtil.setCell("AVALIADOR TELEFONES");
			excelPoiUtil.setCell("AVALIADOR ENDEREÇO");
			excelPoiUtil.setCell("AVALIADOR MUNICIPIO");
			excelPoiUtil.setCell("AVALIADOR UF");
			excelPoiUtil.setCell("AVALIADOR CEP");

			excelPoiUtil.setCell("ORIENTADOR CPF");
			excelPoiUtil.setCell("ORIENTADOR NOME");
			excelPoiUtil.setCell("ORIENTADOR TÍTULOS");
			excelPoiUtil.setCell("ORIENTADOR E-MAIL");
			excelPoiUtil.setCell("ORIENTADOR TELEFONES");
			excelPoiUtil.setCell("ORIENTADOR ENDEREÇO");
			excelPoiUtil.setCell("ORIENTADOR MUNICIPIO");
			excelPoiUtil.setCell("ORIENTADOR UF");
			excelPoiUtil.setCell("ORIENTADOR CEP");

			excelPoiUtil.setCell("COORIENTADOR CPF");
			excelPoiUtil.setCell("COORIENTADOR NOME");
			excelPoiUtil.setCell("COORIENTADOR TÍTULOS");
			excelPoiUtil.setCell("COORIENTADOR E-MAIL");
			excelPoiUtil.setCell("COORIENTADOR TELEFONES");
			excelPoiUtil.setCell("COORIENTADOR ENDEREÇO");
			excelPoiUtil.setCell("COORIENTADOR MUNICIPIO");
			excelPoiUtil.setCell("COORIENTADOR UF");
			excelPoiUtil.setCell("COORIENTADOR CEP");

			excelPoiUtil.setCell("COMISSÃO 1 CPF");
			excelPoiUtil.setCell("COMISSÃO 1 NOME");
			excelPoiUtil.setCell("COMISSÃO 1 TÍTULOS");
			excelPoiUtil.setCell("COMISSÃO 1 E-MAIL");
			excelPoiUtil.setCell("COMISSÃO 1 TELEFONES");
			excelPoiUtil.setCell("COMISSÃO 1 ENDEREÇO");
			excelPoiUtil.setCell("COMISSÃO 1 MUNICIPIO");
			excelPoiUtil.setCell("COMISSÃO 1 UF");
			excelPoiUtil.setCell("COMISSÃO 1 CEP");

			excelPoiUtil.setCell("COMISSÃO 2 CPF");
			excelPoiUtil.setCell("COMISSÃO 2 NOME");
			excelPoiUtil.setCell("COMISSÃO 2 TÍTULOS");
			excelPoiUtil.setCell("COMISSÃO 2 E-MAIL");
			excelPoiUtil.setCell("COMISSÃO 2 TELEFONES");
			excelPoiUtil.setCell("COMISSÃO 2 ENDEREÇO");
			excelPoiUtil.setCell("COMISSÃO 2 MUNICIPIO");
			excelPoiUtil.setCell("COMISSÃO 2 UF");
			excelPoiUtil.setCell("COMISSÃO 2 CEP");

			excelPoiUtil.setCell("COMISSÃO 3 CPF");
			excelPoiUtil.setCell("COMISSÃO 3 NOME");
			excelPoiUtil.setCell("COMISSÃO 3 TÍTULOS");
			excelPoiUtil.setCell("COMISSÃO 2 E-MAIL");
			excelPoiUtil.setCell("COMISSÃO 2 TELEFONES");
			excelPoiUtil.setCell("COMISSÃO 3 ENDEREÇO");
			excelPoiUtil.setCell("COMISSÃO 3 MUNICIPIO");
			excelPoiUtil.setCell("COMISSÃO 3 UF");
			excelPoiUtil.setCell("COMISSÃO 3 CEP");

			listaPremios.forEach(premio -> {
				excelPoiUtil.newRow();
				excelPoiUtil.setCell(String.valueOf(premio.getId()));
				excelPoiUtil.setCell(String.valueOf(premio.getAno()));
				excelPoiUtil.setCell(premio.getDataEnvioFormatada());
				excelPoiUtil.setCell(premio.getInstituicao().getNome());
				excelPoiUtil.setCell(premio.getCampus().getNome());
				excelPoiUtil.setCell(premio.getCurso().getNome());
				excelPoiUtil.setCell(converter.toDescricaoNivel(premio.getNivel()));
				excelPoiUtil.setCell(premio.getTitulo());
				excelPoiUtil.setCell(premio.getAceite() ? "SIM" : "NÃO");
				excelPoiUtil.setCell(populaLinkParaArquivo(premio.getArquivo()));
				excelPoiUtil.setCell(populaLinkParaArquivo(premio.getArquivoResumo()));
				excelPoiUtil.setCell(populaLinkParaArquivo(premio.getArquivoTermo()));
				excelPoiUtil.setCell(premio.getStatus().equals(1L) ? "SIM" : "NÃO");

				excelPoiUtil.setCell(premio.getCpfPessoa());
				excelPoiUtil.setCell(premio.getNomePessoa());
				excelPoiUtil.setCell(emailDao.getUltimoEmailCadastradoPor(premio.getIdPessoa()));
				excelPoiUtil.setCell(populaTelefones(telefoneDao.getListTelefoneByPessoa(premio.getIdPessoa())));

				excelPoiUtil.setCell(populaEndereco(enderecoDao.getEnderecoValidoEPostalPor(premio.getIdPessoa())));
				excelPoiUtil.setCell(populaMunicipio(enderecoDao.getEnderecoValidoEPostalPor(premio.getIdPessoa())));
				excelPoiUtil.setCell(populaUF(enderecoDao.getEnderecoValidoEPostalPor(premio.getIdPessoa())));
				excelPoiUtil.setCell(populaCep(enderecoDao.getEnderecoValidoEPostalPor(premio.getIdPessoa())));

				List<ParticipantePremioTCTDto> listaAutores = premio.getListaParticipantes().stream()
						.filter(pessoa -> pessoa.getPapel().equals("1")).collect(Collectors.toList());

				listaAutores.forEach(participante -> {
					excelPoiUtil.setCell(participante.getPessoa().getCpfOuCnpj());
					excelPoiUtil.setCell(participante.getPessoa().getNome());

					excelPoiUtil.setCell(populaTituloProfissional(participante.getPessoa().getId()));
					excelPoiUtil.setCell(participante.getEmail());
					excelPoiUtil.setCell(participante.getTelefone() + " / " + participante.getCelular());
					if (participante.getIdEndereco() != null) {
						EnderecoDto dto = enderecoConverter
								.toDto(enderecoDao.getEnderecoById(Long.valueOf(participante.getIdEndereco())));
						excelPoiUtil.setCell(dto.transformaEnderecoSemCepMunicipioEUf());
						excelPoiUtil.setCell(dto.getLocalidade().getDescricao());
						excelPoiUtil.setCell(dto.getUf().getSigla());
						excelPoiUtil.setCell(dto.getCep());

					} else {
						excelPoiUtil.setCell("");
						excelPoiUtil.setCell("");
						excelPoiUtil.setCell("");
						excelPoiUtil.setCell("");
					}
				});

				for (int i = 1; i <= (4 - listaAutores.size()); i++) {
					excelPoiUtil.setCell("");
					excelPoiUtil.setCell("");
					excelPoiUtil.setCell("");
					excelPoiUtil.setCell("");
					excelPoiUtil.setCell("");
					excelPoiUtil.setCell("");
					excelPoiUtil.setCell("");
					excelPoiUtil.setCell("");
					excelPoiUtil.setCell("");
				} // completa as células dos autores não indicados

				List<ParticipantePremioTCTDto> listaAvaliadores = premio.getListaParticipantes().stream()
						.filter(pessoa -> pessoa.getPapel().equals("2")).collect(Collectors.toList());
				listaAvaliadores.forEach(participante -> {
					excelPoiUtil.setCell(participante.getPessoa().getCpfOuCnpj());
					excelPoiUtil.setCell(participante.getPessoa().getNome());

					excelPoiUtil.setCell(populaTituloProfissional(participante.getPessoa().getId()));
					excelPoiUtil.setCell(participante.getEmail());
					excelPoiUtil.setCell(participante.getTelefone() + " / " + participante.getCelular());
					if (participante.getIdEndereco() != null) {
						EnderecoDto dto = enderecoConverter
								.toDto(enderecoDao.getEnderecoById(Long.valueOf(participante.getIdEndereco())));
						excelPoiUtil.setCell(dto.transformaEnderecoSemCepMunicipioEUf());
						excelPoiUtil.setCell(dto.getLocalidade().getDescricao());
						excelPoiUtil.setCell(dto.getUf().getSigla());
						excelPoiUtil.setCell(dto.getCep());

					} else {
						excelPoiUtil.setCell("");
						excelPoiUtil.setCell("");
						excelPoiUtil.setCell("");
						excelPoiUtil.setCell("");
					}
				});

				List<ParticipantePremioTCTDto> listaOrientadores = premio.getListaParticipantes().stream()
						.filter(pessoa -> pessoa.getPapel().equals("4")).collect(Collectors.toList());
				listaOrientadores.forEach(participante -> {
					excelPoiUtil.setCell(participante.getPessoa().getCpfOuCnpj());
					excelPoiUtil.setCell(participante.getPessoa().getNome());
					excelPoiUtil.setCell(populaTituloProfissional(participante.getPessoa().getId()));
					excelPoiUtil.setCell(participante.getEmail());
					excelPoiUtil.setCell(participante.getTelefone() + " / " + participante.getCelular());
					if (participante.getIdEndereco() != null) {
						EnderecoDto dto = enderecoConverter
								.toDto(enderecoDao.getEnderecoById(Long.valueOf(participante.getIdEndereco())));
						excelPoiUtil.setCell(dto.transformaEnderecoSemCepMunicipioEUf());
						excelPoiUtil.setCell(dto.getLocalidade().getDescricao());
						excelPoiUtil.setCell(dto.getUf().getSigla());
						excelPoiUtil.setCell(dto.getCep());

					} else {
						excelPoiUtil.setCell("");
						excelPoiUtil.setCell("");
						excelPoiUtil.setCell("");
						excelPoiUtil.setCell("");
					}
				});

				List<ParticipantePremioTCTDto> listaCoorientadores = premio.getListaParticipantes().stream()
						.filter(pessoa -> pessoa.getPapel().equals("3")).collect(Collectors.toList());
				listaCoorientadores.forEach(participante -> {
					excelPoiUtil.setCell(participante.getPessoa().getCpfOuCnpj());
					excelPoiUtil.setCell(participante.getPessoa().getNome());
					excelPoiUtil.setCell(populaTituloProfissional(participante.getPessoa().getId()));
					excelPoiUtil.setCell(participante.getEmail());
					excelPoiUtil.setCell(participante.getTelefone() + " / " + participante.getCelular());
					if (participante.getIdEndereco() != null) {
						EnderecoDto dto = enderecoConverter
								.toDto(enderecoDao.getEnderecoById(Long.valueOf(participante.getIdEndereco())));
						excelPoiUtil.setCell(dto.transformaEnderecoSemCepMunicipioEUf());
						excelPoiUtil.setCell(dto.getLocalidade().getDescricao());
						excelPoiUtil.setCell(dto.getUf().getSigla());
						excelPoiUtil.setCell(dto.getCep());

					} else {
						excelPoiUtil.setCell("");
						excelPoiUtil.setCell("");
						excelPoiUtil.setCell("");
						excelPoiUtil.setCell("");
					}
				});

				if (listaCoorientadores.size() == 0) {
					excelPoiUtil.setCell("");
					excelPoiUtil.setCell("");
					excelPoiUtil.setCell("");
					excelPoiUtil.setCell("");
					excelPoiUtil.setCell("");
					excelPoiUtil.setCell("");
					excelPoiUtil.setCell("");
					excelPoiUtil.setCell("");
					excelPoiUtil.setCell("");
				} // preenche células, caso não tenha sido indicado coorientador

				List<ParticipantePremioTCTDto> listaComissao = premio.getListaParticipantes().stream()
						.filter(pessoa -> pessoa.getPapel().equals("5")).collect(Collectors.toList());
				listaComissao.forEach(participante -> {
					excelPoiUtil.setCell(participante.getPessoa().getCpfOuCnpj());
					excelPoiUtil.setCell(participante.getPessoa().getNome());
					excelPoiUtil.setCell(populaTituloProfissional(participante.getPessoa().getId()));
					excelPoiUtil.setCell(participante.getEmail());
					excelPoiUtil.setCell(participante.getTelefone() + " / " + participante.getCelular());
					if (participante.getIdEndereco() != null) {
						EnderecoDto dto = enderecoConverter
								.toDto(enderecoDao.getEnderecoById(Long.valueOf(participante.getIdEndereco())));
						excelPoiUtil.setCell(dto.transformaEnderecoSemCepMunicipioEUf());
						excelPoiUtil.setCell(dto.getLocalidade().getDescricao());
						excelPoiUtil.setCell(dto.getUf().getSigla());
						excelPoiUtil.setCell(dto.getCep());

					} else {
						excelPoiUtil.setCell("");
						excelPoiUtil.setCell("");
						excelPoiUtil.setCell("");
						excelPoiUtil.setCell("");
					}
				});

			});

			return excelPoiUtil.buildToStream();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

	private String populaLinkParaArquivo(ArquivoDto arquivo) {
		if (arquivo.isPrivado()) {
			return "http://portalservicos.crea-rj.org.br/rest-api/crea" + arquivo.getUri();
		} else {
			return "http://portalservicos.crea-rj.org.br/arquivos" + arquivo.getUri();
		}
	}

	private String populaEndereco(Endereco endereco) {
		if (endereco != null) {
			EnderecoDto dto = enderecoConverter.toDto(endereco);
			String rtn = dto.transformaEnderecoSemCepMunicipioEUf();
			return rtn;
		} else {
			return "";
		}
	}

	private String populaMunicipio(Endereco endereco) {
		if (endereco != null) {
			EnderecoDto dto = enderecoConverter.toDto(endereco);
			String rtn = dto.getLocalidade().getDescricao();
			return rtn;
		} else {
			return "";
		}
	}

	private String populaUF(Endereco endereco) {
		if (endereco != null) {
			EnderecoDto dto = enderecoConverter.toDto(endereco);
			String rtn = dto.getUf().getSigla();
			return rtn;
		} else {
			return "";
		}
	}

	private String populaCep(Endereco endereco) {
		if (endereco != null) {
			EnderecoDto dto = enderecoConverter.toDto(endereco);
			String rtn = dto.getCep();
			return rtn;
		} else {
			return "";
		}
	}

	private String populaTelefones(List<Telefone> listaTelefones) {
		String telefones = "";
		for (Telefone telefone : listaTelefones) {
			telefones = "(" + telefone.getDdd() + ") " + telefone.getNumero();
			telefones = telefones + " / ";
		}

		return telefones;
	}

	private String populaTituloProfissional(Long idPessoa) {
		Profissional profissional = profissionalDao.buscaProfissionalPor(String.valueOf(idPessoa));

		if (profissional.getId() != null) {
			return profissionalEspecialidadeDao.getTituloProfissional(profissional);
		} else {
			return "NÃO É PROFISSIONAL";
		}
	}

}
