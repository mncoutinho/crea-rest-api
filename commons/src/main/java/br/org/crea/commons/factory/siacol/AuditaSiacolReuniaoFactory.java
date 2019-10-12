package br.org.crea.commons.factory.siacol;

import java.util.Date;

import javax.inject.Inject;

import br.org.crea.commons.dao.cadastro.pessoa.InteressadoDao;
import br.org.crea.commons.models.cadastro.enuns.TipoEventoAuditoria;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.models.commons.enuns.ModuloSistema;
import br.org.crea.commons.models.corporativo.pessoa.IInteressado;
import br.org.crea.commons.models.siacol.dtos.ItemPautaDto;
import br.org.crea.commons.models.siacol.dtos.ReuniaoSiacolDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.service.cadastro.AuditoriaService;
import br.org.crea.commons.util.StringUtil;

public class AuditaSiacolReuniaoFactory {

	@Inject private AuditoriaService service;
	@Inject private InteressadoDao interessadoDao;
	@Inject private HttpClientGoApi httpGoApi;
	
	public void auditaAcaoReuniao(ReuniaoSiacolDto dto, UserFrontDto usuario) {
		
		try {
			 service
			  .usuario(usuario)
		      .modulo(ModuloSistema.SIACOL)
		      .dtoNovo(StringUtil.convertObjectToJson(dto))
		      .dataCriacao(new Date())
		      .numero(String.valueOf(dto.getId()));
			  
			  auditaAcao(dto);
			  
		} catch (Throwable e) {
			httpGoApi.geraLog("AuditaSiacolReuniaoFactory || auditaAcaoReuniao", StringUtil.convertObjectToJson(dto + " - " + usuario), e);
		}
		
	}
		
	public void auditaAcao(ReuniaoSiacolDto reuniao) {
		
		try {
			TipoEventoAuditoria evento = null; 
			String mensagem = null;
			
			switch (reuniao.getAcao()) {
			case "INICIAR":
				evento = TipoEventoAuditoria.SIACOL_REUNIAO_INICIAR;
				mensagem = "Reunião " + reuniao.getId() + " iniciada com sucesso";
				break;
			case "PAUSAR":
				evento = TipoEventoAuditoria.SIACOL_REUNIAO_PAUSAR;
				mensagem = "Reunião " + reuniao.getId() + " pausada com sucesso";
				break;
			case "CANCELAR":
				evento = TipoEventoAuditoria.SIACOL_REUNIAO_CANCELAR;
				mensagem = "Reunião " + reuniao.getId() + " cancelada com sucesso";
				break;
			case "ENCERRAR":
				evento = TipoEventoAuditoria.SIACOL_REUNIAO_ENCERRAR;
				mensagem = "Reunião " + reuniao.getId() + " encerrada com sucesso";
				break;
			default:
				break;
			}
			
			service
			.tipoEvento(evento)
			.textoAuditoria(mensagem)
			.create();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("AuditaSiacolReuniaoFactory || auditaAcao", StringUtil.convertObjectToJson(reuniao), e);
		}
		
	}
	
	public void auditaItemReuniao(ItemPautaDto dto, UserFrontDto usuario) {
		
		try {
			
			service
			  .usuario(usuario)
		      .modulo(ModuloSistema.SIACOL)
		      .dtoNovo(StringUtil.convertObjectToJson(dto))
		      .dataCriacao(new Date())
		      .numero(dto.getDescricaoItem());
			  
				String mensagem = null;
				IInteressado interessado = null;
				
				switch (dto.getEventoAuditoria()) {
					case INCLUSAO_PROTOCOLO_EXTRAPAUTA:
						mensagem = "O protocolo " + dto.getDescricaoItem() + " foi incluído na extrapauta da reunião";
						break;
					case INCLUSAO_PROTOCOLO_EMERGENCIAL:
						mensagem = "O protocolo " + dto.getDescricaoItem() + " foi incluído emergencialmente pelo operador " + usuario.getNome() + " (" + usuario.getMatricula() + " - " + usuario.getPerfilString() + ")";
						break;
					case PEDIDO_DESTAQUE:
						interessado = interessadoDao.buscaProfissionalBy(dto.getIdPessoaDestaque()); 
						mensagem = "O protocolo " + dto.getDescricaoItem() + " teve pedido de destaque pelo conselheiro " + interessado.getNome();
						break;
					case VISTA_CONCEDIDA_PROTOCOLO:
						interessado = interessadoDao.buscaProfissionalBy(dto.getIdPessoaVista());
						mensagem = "O protocolo " + dto.getDescricaoItem() + " teve pedido de vista concedido para o conselheiro " + interessado.getNome();
						break;
					case VOTO_OFFLINE:
						mensagem = "O Protocolo nº " + dto.getDescricaoItem() + " teve seus votos inputados de forma offline pelo operador " + usuario.getNome() + " (" + usuario.getMatricula() + " - " + usuario.getPerfilString() + ")";
						break;
					case VOTO_ACLAMACAO:
						mensagem = "O operador " + usuario.getNome() + " (" + usuario.getMatricula() + " - " + usuario.getPerfilString() + ") registrou os votos do protocolo nº " + dto.getDescricaoItem() + " por aclamação";
						break;
					case DECLARACAO_VOTO:
						interessado = interessadoDao.buscaProfissionalBy(usuario.getIdPessoa());
						mensagem = "O Protocolo nº " + dto.getDescricaoItem() + " teve declaração de voto pelo conselheiro " + interessado.getNome();
						break;
					default:
						break;
				}
				
				service
				.tipoEvento(dto.getEventoAuditoria())
				.textoAuditoria(mensagem)
				.create();
				
		} catch (Throwable e) {
			httpGoApi.geraLog("AuditaSiacolReuniaoFactory || auditaItemReuniao", StringUtil.convertObjectToJson(dto + " - " + usuario), e);
		}
			
	}

	
}
