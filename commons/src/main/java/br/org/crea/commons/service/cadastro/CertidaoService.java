package br.org.crea.commons.service.cadastro;

import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.cadastro.pessoa.CertidaoConverter;
import br.org.crea.commons.dao.cadastro.CertidaoDao;
import br.org.crea.commons.dao.cadastro.empresa.ObjetoSocialDao;
import br.org.crea.commons.models.cadastro.dtos.CertidaoDto;
import br.org.crea.commons.models.commons.dtos.PesquisaGenericDto;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;

public class CertidaoService {

	@Inject
	CertidaoDao dao;

	@Inject
	CertidaoConverter converter;

	@Inject
	ObjetoSocialDao objetoSocialDao;

	public List<CertidaoDto> getCertidaoByIdPessoa(Long idPessoa) {
		return converter.toListDto(dao.getListCertidaoByIdPessoa(idPessoa));
	}
	
	public List<CertidaoDto> getListCertidaoByIdPessoaPaginada(PesquisaGenericDto pesquisa) {
		return converter.toListDto(dao.getListCertidaoByIdPessoaPaginada(pesquisa));
	}

	public int getTotalDeRegistrosDaPesquisa(PesquisaGenericDto pesquisa) {
		return dao.getTotalDeRegistrosdaPesquisa(pesquisa);
	}

	public List<String> validaPessoaParaCertidaoRegistro(UserFrontDto dto) {

		return dao.validaPessoaParaCertidaoRegistro(dto);
	}

	public List<String> validaPessoaParaCertidaoAtribuicao(UserFrontDto dto) {

		return dao.validaPessoaParaCertidaoAtribuicao(dto);
	}

	public List<String> validaPessoaJuridicaParaCertidaoRegistro(UserFrontDto dto) {
		return dao.validaPessoaJuridicaParaCertidaoRegistro(dto);
	}

	public boolean validaQuadroTecnico(UserFrontDto dto) {
		return dao.quadroTecnicoEhValido(dto);
	}

	public boolean existeObjetoSocial(UserFrontDto dto) {
		if (objetoSocialDao.getObjetosSociaisByEmpresa(dto.getIdPessoa()).size() > 0) {
			return true;
		}
		return false;
	}

}
