package br.org.crea.commons.factory.art;

import java.util.Date;

import javax.inject.Inject;

import br.org.crea.commons.models.art.dtos.ArtDto;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.models.commons.enuns.TipoAcaoEnum;
import br.org.crea.commons.models.corporativo.enuns.TipoPessoa;
import br.org.crea.commons.service.art.ArtLogService;

public class LogArtFactory {
	
	@Inject private ArtLogService service;
	
	public void logaAcaoArt(UserFrontDto usuario, ArtDto dto, TipoAcaoEnum acao) {
		
	  service
	  .usuario(usuario)
      .data(new Date())
      .numero(dto.getNumero());
			  
	  setFuncionario(usuario);
	  logaAcao(dto, acao);
	}
	
	public void auditaAcaoSubstituicaoArt(UserFrontDto usuario, String numeroArt, String numeroArtPrincipal, TipoAcaoEnum acao) {
		
		  service
		  .usuario(usuario)
	      .data(new Date())
	      .numero(numeroArtPrincipal);
				  
		  setFuncionario(usuario);
		  logaAcaoSubstituicao(numeroArt, numeroArtPrincipal, acao);
	}

	private void setFuncionario(UserFrontDto usuario) {
		if (usuario.getTipoPessoa().equals(TipoPessoa.FUNCIONARIO)) {
			service.funcionario(usuario.getIdFuncionario());
		} else {
			service.funcionario(new Long(99990));
		}
	}

	public void logaAcao(ArtDto dto, TipoAcaoEnum acao) {
		String texto = "";
		
		switch (acao) {
		case INCLUSAO_ART:
			texto = "ART nº: " + dto.getNumero() + " cadastrada";
			break;
		case BAIXA_ART:
			texto = "BAIXADA ART nº: " + dto.getNumero() + " ("+ dto.getMotivoBaixaOutros() +")";
			break;
		default:
			break;
		}
		
		service
		.textoLog(texto)
		.acao(acao.getId())
		.create();;
	}

	private void logaAcaoSubstituicao(String numeroArt, String numeroArtPrincipal, TipoAcaoEnum acao) {
		String texto = "ART Principal nº: " + numeroArtPrincipal + " substituída por " + numeroArt;
		
		service
		.textoLog(texto)
		.acao(acao.getId())
		.create();
	}
}
