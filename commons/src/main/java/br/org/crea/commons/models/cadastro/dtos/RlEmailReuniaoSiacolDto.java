package br.org.crea.commons.models.cadastro.dtos;

import br.org.crea.commons.models.commons.dtos.EmailEnvioDto;
import br.org.crea.commons.models.siacol.dtos.ReuniaoSiacolDto;

public class RlEmailReuniaoSiacolDto {

	private Long id;

	private ReuniaoSiacolDto reuniao;

	private EmailEnvioDto emailEnvio;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ReuniaoSiacolDto getReuniao() {
		return reuniao;
	}

	public void setReuniao(ReuniaoSiacolDto reuniao) {
		this.reuniao = reuniao;
	}

	public EmailEnvioDto getEmailEnvio() {
		return emailEnvio;
	}

	public void setEmailEnvio(EmailEnvioDto emailEnvio) {
		this.emailEnvio = emailEnvio;
	}


	
}
