package br.org.crea.siacol.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.dao.siacol.ReuniaoSiacolDao;
import br.org.crea.commons.dao.siacol.RlDocumentoProtocoloSiacolDao;
import br.org.crea.commons.models.siacol.ReuniaoSiacol;
import br.org.crea.commons.models.siacol.VotoReuniaoSiacol;
import br.org.crea.commons.models.siacol.dtos.PesquisaVotoReuniaoDto;
import br.org.crea.commons.models.siacol.dtos.ProtocoloSiacolDto;
import br.org.crea.commons.models.siacol.dtos.VotoReuniaoSiacolDto;
import br.org.crea.commons.models.siacol.enuns.VotoReuniaoEnum;
import br.org.crea.siacol.converter.ProtocoloSiacolConverter;
import br.org.crea.siacol.converter.VotoReuniaoSiacolConverter;
import br.org.crea.siacol.dao.VotoReuniaoSiacolDao;

public class VotoReuniaoSiacolService {

	@Inject
	private VotoReuniaoSiacolConverter converter;

	@Inject
	private VotoReuniaoSiacolDao dao;

	@Inject
	private RlDocumentoProtocoloSiacolDao rlDocumentoProtocoloDao;

	@Inject
	private ProtocoloSiacolConverter protocoloConverter;

	@Inject
	private ReuniaoSiacolDao reuniaoDao;

	public VotoReuniaoSiacolDto salvar(VotoReuniaoSiacolDto dto) {
		VotoReuniaoSiacol reuniao = new VotoReuniaoSiacol();

		VotoReuniaoSiacol voto = new VotoReuniaoSiacol();
		voto = dao.getVotoByIdPessoaByIdProtocolo(dto.getPessoa().getId(), dto.getProtocolo().getId());

		if (voto != null) {
			dto.setId(voto.getId());
			reuniao = dao.update(converter.toModel(dto));
		} else {
			reuniao = dao.create(converter.toModel(dto));
		}
		return converter.toDto(dao.getBy(reuniao.getId()));
	}

	public VotoReuniaoSiacolDto getByIdVoto(Long id) {
		return converter.toDto(dao.getBy(id));
	}

	public List<VotoReuniaoSiacolDto> getVotosByIdProtocolo(Long id) {
		return converter.toListDto(dao.getByIdProtocolo(id));
	}



	public List<ProtocoloSiacolDto> contagem(PesquisaVotoReuniaoDto pesquisa) {

		List<ProtocoloSiacolDto> resultContagem = new ArrayList<ProtocoloSiacolDto>();
		resultContagem = protocoloConverter.toListDto(rlDocumentoProtocoloDao.getListProtocolos(pesquisa.getIdPauta()));
		ReuniaoSiacol reuniao = reuniaoDao.getBy(pesquisa.getIdReuniao());
		int quorum = Integer.parseInt(reuniao.getQuorum().toString());

		for (ProtocoloSiacolDto p : resultContagem) {

			p.setQuantidadeVotosSim(dao.getQuantidadesVotosPor(p.getId(), pesquisa.getIdReuniao(), VotoReuniaoEnum.S));
			p.setQuantidadeVotosNao(dao.getQuantidadesVotosPor(p.getId(), pesquisa.getIdReuniao(), VotoReuniaoEnum.N));
			p.setQuantidadeVotosAbstencao(dao.getQuantidadesVotosPor(p.getId(), pesquisa.getIdReuniao(), VotoReuniaoEnum.A));
			p.setQuantidadeVotosDestaque(dao.getQuantidadesVotosPor(p.getId(), pesquisa.getIdReuniao(), VotoReuniaoEnum.D));
			p.setQuorumReuniao(quorum);
			
			if (p.deuEmpate()) {
				if(p.temCoordanadorOuAdjunto()) {
					VotoReuniaoSiacol voto = null;
					voto = dao.getVotoCoordenadorOuAdjunto(p.getId(), pesquisa.getIdReuniao(), p.getIdCoordenadorOuAdjunto() );
					if(voto != null) {
						p.setVotoCoordenadorOuAdjunto(voto.getVoto());
					}
				}
			}
			
			p.calculaGeraDecisao();

		}

		return resultContagem;
	}


	public List<VotoReuniaoSiacolDto> pesquisa(PesquisaVotoReuniaoDto pesquisa) {
		return converter.toListDto(dao.pesquisa(pesquisa));
	}

	public boolean protocoloFoiDestacado(PesquisaVotoReuniaoDto pesquisa) {

		for (VotoReuniaoSiacolDto voto : pesquisa(pesquisa)) {

			if (voto.isDestaque()) {
				return true;
			}
		}

		return false;
	}

}
