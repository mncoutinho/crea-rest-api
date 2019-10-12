package br.org.crea.siacol.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.cadastro.pessoa.PessoaConverter;
import br.org.crea.commons.dao.DocumentoDao;
import br.org.crea.commons.dao.siacol.ProtocoloSiacolDao;
import br.org.crea.commons.models.cadastro.Departamento;
import br.org.crea.commons.models.cadastro.Documento;
import br.org.crea.commons.models.commons.SituacaoProtocolo;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.models.siacol.AssuntoSiacol;
import br.org.crea.commons.models.siacol.ProtocoloSiacol;
import br.org.crea.commons.models.siacol.dtos.ConsultaProtocoloDto;
import br.org.crea.commons.models.siacol.dtos.GenericSiacolDto;
import br.org.crea.commons.models.siacol.dtos.ProtocoloSiacolDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;
import br.org.crea.siacol.converter.ProtocoloSiacolConverter;

public class ProtocoloSiacolService {

	@Inject
	ProtocoloSiacolConverter converter;
	@Inject
	PessoaConverter converterPessoa;
	@Inject
	ProtocoloSiacolDao dao;
	@Inject
	DocumentoDao documentoDao;

	@Inject
	HttpClientGoApi httpGoApi;
	

	public List<ProtocoloSiacolDto> getAllProtocolos(ConsultaProtocoloDto consulta) {
		return converter.toListDto(dao.getAllProtocolos(consulta));
	}

	public int getQuantidadeConsultaProtocolo(ConsultaProtocoloDto consulta) {
		return dao.quantidadeConsultaProtocolos(consulta);
	}


	public boolean temHabilidadeParaRedistribuicao(GenericSiacolDto dto) {
		return dao.temHabilidadeParaRedistribuicao(dto) ? true : false;
	}

	public GenericSiacolDto distribuiProtocolo(GenericSiacolDto dto, UserFrontDto usuario) {

		for (Long idProtocolo : dto.getListaId()) {

			List<Documento> listaDocumento = new ArrayList<Documento>();
			listaDocumento = documentoDao.recuperaDocumentosByNumeroProtocolo(idProtocolo);
			for (Documento documento : listaDocumento) {
				if (documento.getResponsavel() == usuario.getIdPessoa() || documento.getResponsavel() == usuario.getIdFuncionario()) {
					documento.setAssinado(true);
					documentoDao.update(documento);
				}
			}

			ProtocoloSiacol protocolo = new ProtocoloSiacol();
			dto.setIdProtocolo(idProtocolo);
			protocolo = populaProtocolo(dto);

			protocolo.setUltimoAnalista(usuario.getIdPessoa());
			protocolo.setDataSiacol(new Date());
			protocolo.setRecebido(false);
			protocolo.setDataRecebimento(null);
			dao.update(protocolo);
		}
		return dto;
	}

	public GenericSiacolDto classficar(GenericSiacolDto dto, UserFrontDto usuario) {

		dao.update(populaProtocolo(dto));
		return dto;

	}

	public GenericSiacolDto justificativaDevolucao(GenericSiacolDto dto) {
		ProtocoloSiacol protocolo = new ProtocoloSiacol();
		protocolo = populaProtocolo(dto);
		protocolo.setRecebido(false);
		protocolo.setDataRecebimento(null);
		dao.update(protocolo);
		return dto;
	}

	public GenericSiacolDto alteraSituacao(GenericSiacolDto dto) {

		try {
			for (Long idProtocolo : dto.getListaId()) {
				dto.setIdProtocolo(idProtocolo);
				dao.update(populaProtocolo(dto));
			}

		} catch (Exception e) {
			httpGoApi.geraLog("ProtocoloSiacolService || alteraSituacao", StringUtil.convertObjectToJson(dto), e);
		}

		return dto;
	}

	private ProtocoloSiacol populaProtocolo(GenericSiacolDto dto) {

		ProtocoloSiacol protocolo = new ProtocoloSiacol();
		protocolo = dao.getBy(dto.getIdProtocolo());

		if (dto.temResponsavelNovo()) {
			protocolo.setIdResponsavel(dto.getIdResponsavelNovo());
			protocolo.setNomeResponsavel(dto.getNomeResponsavelNovo());
		}

		if (dto.temAssuntoSiacolProtocolo()) {
			AssuntoSiacol assunto = new AssuntoSiacol();
			assunto.setId(dto.getIdAssuntoSiacolProtocolo());
			protocolo.setAssuntoSiacol(assunto);
		}

		if (dto.temDepartamento()) {
			Departamento departamento = new Departamento();
			departamento.setId(dto.getIdDepartamento());
			protocolo.setDepartamento(departamento);
		}

		if (dto.temSituacao()) {
			SituacaoProtocolo situacao = new SituacaoProtocolo();
			situacao.setId(dto.getIdSituacao());
			protocolo.setSituacao(situacao);
		}

		if (dto.temJustificativa()) {
			protocolo.setJustificativa(dto.getJustificativa());
		}

		if (dto.temMotivoDevolucao()) {
			protocolo.setMotivoDevolucao(dto.getMotivoDevolucao());
		}

		if (dto.temStatus()) {
			protocolo.setStatus(dto.getStatus());
		}

		if (dto.temClassificacao()) {
			protocolo.setClassificacao(dto.getClassificacao());
		}

		return protocolo;

	}

	public GenericSiacolDto receberProtocolo(GenericSiacolDto dto) {
		dao.recebeProtocolo(dto.getIdProtocolo());
		return dto;
	}

	public void ativarProtocoloPorIdDepartamento(Long idDepartamento) {
		dao.ativaProtocolos(idDepartamento);
	}

}
