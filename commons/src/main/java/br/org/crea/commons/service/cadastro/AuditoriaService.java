package br.org.crea.commons.service.cadastro;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.commons.AuditoriaConverter;
import br.org.crea.commons.dao.cadastro.AuditoriaDao;
import br.org.crea.commons.dao.siacol.ProtocoloSiacolDao;
import br.org.crea.commons.models.cadastro.Auditoria;
import br.org.crea.commons.models.cadastro.enuns.TipoEventoAuditoria;
import br.org.crea.commons.models.commons.dtos.AuditoriaDto;
import br.org.crea.commons.models.commons.dtos.PesquisaAuditoriaDto;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.models.commons.enuns.ModuloSistema;

public class AuditoriaService {
	
	@Inject AuditoriaDao dao;
	
	@Inject AuditoriaConverter converter;
	
	@Inject ProtocoloSiacolDao protocoloSiacolDao;
	
	private Auditoria auditoria;
	
	public AuditoriaService usuario(UserFrontDto usuario) {
		auditoria = new Auditoria();
		auditoria.setIp(usuario.getIp());
		auditoria.setIdUsuario(usuario.getIdPessoa());
		auditoria.setNomeUsuario(usuario.getNome());
		auditoria.setRegistroUsuario(usuario.getCpfOuCnpj());
	    auditoria.setPerfilUsuario(usuario.getPerfilString());
        
		return this;
	}

	public AuditoriaService modulo(ModuloSistema modulo) {
		if (modulo == null) {
			auditoria.setModulo(ModuloSistema.getIdBy(ModuloSistema.CORPORATIVO));
		}else {
			auditoria.setModulo(ModuloSistema.getIdBy(modulo));
		}
		return this;
	}

	public AuditoriaService numero(String numero) {
		auditoria.setNumero(numero);
		return this;
	}
	

	public AuditoriaService numeroReferencia(String numeroReferencia) {
		auditoria.setNumeroReferencia(numeroReferencia);
		return this;
	}

	public AuditoriaService departamentoDestino(Long numero) {
		auditoria.setIdDepartamentoDestino(numero);
		return this;
	}
	
	public AuditoriaService departamentoOrigem(Long numero) {
		auditoria.setIdDepartamentoOrigem(numero);
		return this;
	}
	
	public AuditoriaService textoAuditoria(String texto) {
		auditoria.setTextoAuditoria(texto);
		return this;
	} 
	

	public AuditoriaService responsavelDestino(Long idResponsavel, String nomeResponsavel, String perfilDestinatario) {
		auditoria.setIdDestinatario(idResponsavel);
		auditoria.setNomeDestinatario(nomeResponsavel);
		auditoria.setPerfilDestinatario(perfilDestinatario);
		return this;
	} 
	
	public AuditoriaService responsavelRemetente(Long idResponsavel, String nomeResponsavel, String perfilRemetente) {
		auditoria.setIdRemetente(idResponsavel);
		auditoria.setNomeRemetente(nomeResponsavel);
		auditoria.setPerfilRemetente(perfilRemetente);
		return this;
	} 

	public AuditoriaService tipoEvento(TipoEventoAuditoria tipoEvento) {
		auditoria.setEvento(TipoEventoAuditoria.getIdBy(tipoEvento));
		return this;
	}
	
	public AuditoriaService isError(boolean isError) {
		auditoria.setError(isError);
		return this;
	}
	
	public AuditoriaService acao(String acao) {
		auditoria.setAcao(acao);
		return this;
	}
	
	public AuditoriaService dtoAntigo(String dto) {
		auditoria.setDtoAntigo(dto);
		return this;
	}

	public AuditoriaService dtoNovo(String dto) {
		auditoria.setDtoNovo(dto);
		return this;
	}
	
	public AuditoriaService dtoClass(String dto) {
		auditoria.setDtoClass(dto);
		return this;
	}

	public AuditoriaService dataCriacao(Date data) {
		auditoria.setDataCriacao(data);
		return this;
	}
	
	public AuditoriaService statusAntigo(String status) {
		auditoria.setStatusAntigo(status);
		return this;
	}
	
	public AuditoriaService statusNovo(String status) {
		auditoria.setStatusNovo(status);
		return this;
	}

	
	public void create (){
		dao.create(auditoria);
	}

	public List<AuditoriaDto> pesquisa(PesquisaAuditoriaDto dto) {
		return converter.toListDto(dao.pesquisa(dto));
	}
	
	public List<Long> conselheirosImpedidos(PesquisaAuditoriaDto pesquisaAuditoriaDto) {
		
		List<Auditoria> listAuditoria = new ArrayList<Auditoria>();
		List<Long> listIdsImpedidos = new ArrayList<Long>();
		pesquisaAuditoriaDto.setIdDepartamentoDestino(
				protocoloSiacolDao.getProtocoloBy(new Long(pesquisaAuditoriaDto.getNumero())).getDepartamento().getId());
		listAuditoria = dao.pesquisa(pesquisaAuditoriaDto);
		for (Auditoria auditoria : listAuditoria) {
			listIdsImpedidos.add(auditoria.getIdUsuario());
		}
		
		return listIdsImpedidos;
		
	}

	public AuditoriaDto confirmacaoLeitura(PesquisaAuditoriaDto dto) {
		auditoria = new Auditoria();
		return null;
	}

}
