package br.org.crea.art.service;

import javax.inject.Inject;

import br.org.crea.commons.dao.art.ArtPessoaAcaoElevadoresDao;
import br.org.crea.commons.models.art.ArtPessoaAcaoElevadores;

public class ArtAcaoOrdinariaService {
		
	@Inject
	private ArtPessoaAcaoElevadoresDao artPessoaAcaoElevadoresDao;
	
	public boolean verificaSeTemAcaoJudicialParaIsencaoDePagamentoDeArt(Long idPessoa) {
		ArtPessoaAcaoElevadores acao = artPessoaAcaoElevadoresDao.getByIdPessoa(idPessoa);
		
		if (acao != null) {
			if (acao.estaVigente()) {
				return true;				
			}
		}
		return false;
	}

}
