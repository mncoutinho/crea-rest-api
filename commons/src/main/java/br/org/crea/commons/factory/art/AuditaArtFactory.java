package br.org.crea.commons.factory.art;

import java.util.Date;

import javax.inject.Inject;

import br.org.crea.commons.models.cadastro.enuns.TipoEventoAuditoria;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.models.commons.enuns.ModuloSistema;
import br.org.crea.commons.service.cadastro.AuditoriaService;

public class AuditaArtFactory {

	@Inject private AuditoriaService service;
	
	public void finalizaArt(String numeroArt, UserFrontDto usuario) {
		
		  service
		  .usuario(usuario)
	      .modulo(ModuloSistema.ART)
	      .dataCriacao(new Date())
	      .numero(numeroArt)
	      .textoAuditoria("ART NÚMERO: 	" + numeroArt + " cadastrado pelo usuário: " + usuario.getNome())
		  .tipoEvento(TipoEventoAuditoria.ART_CADASTRO)
		  .create();
		
	}
	
	public void artGeradaAPartirDoModelo(String numeroArt, String numeroArtModelo, UserFrontDto usuario) {
		
		  service
		  .usuario(usuario)
	      .modulo(ModuloSistema.ART)
	      .dataCriacao(new Date())
	      .numero(numeroArt)
	      .textoAuditoria("ART NÚMERO: 	" + numeroArt + " foi gerada a partir da ART modelo nº: "+numeroArtModelo+" cadastrada pelo usuário: " + usuario.getNome())
		  .tipoEvento(TipoEventoAuditoria.ART_CADASTRO_GERADO_MODELO)
		  .create();
		
	}
	
	public void iniciaArt(String numeroArt, UserFrontDto usuario) {
		
		  service
		  .usuario(usuario)
	      .modulo(ModuloSistema.ART)
	      .dataCriacao(new Date())
	      .numero(numeroArt)
	      .textoAuditoria("ART NÚMERO: 	" + numeroArt + " iniciada pelo usuário: " + usuario.getNome())
		  .tipoEvento(TipoEventoAuditoria.ART_INICIADA)
		  .create();
		
	}

	
}
