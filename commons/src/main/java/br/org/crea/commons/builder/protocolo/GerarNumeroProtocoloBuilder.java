package br.org.crea.commons.builder.protocolo;

import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.dao.cadastro.domains.DepartamentoDao;
import br.org.crea.commons.dao.corporativo.GeradorSequenciaDao;
import br.org.crea.commons.dao.protocolo.AssuntoDao;
import br.org.crea.commons.dao.protocolo.ProtocoloDao;
import br.org.crea.commons.models.commons.Protocolo;
import br.org.crea.commons.models.commons.dtos.ProtocoloDto;
import br.org.crea.commons.models.corporativo.Assunto;
import br.org.crea.commons.models.protocolo.enuns.TipoProtocoloEnum;

public class GerarNumeroProtocoloBuilder {
	
	@Inject GeradorSequenciaDao geradorSequenciaDao;
	@Inject ProtocoloDao protocoloDao;
	@Inject AssuntoDao assuntoDao;
	@Inject DepartamentoDao departamentoDao;
	
	Protocolo protocolo;
	
	/**
	 * Capturar numero de processo, numero de protocolo e tipo de protocolo.
	 * @param protocoloDto 
	 * @since 06/2018
	 */
	public GerarNumeroProtocoloBuilder geraNumeroProtocoloPorTipoDeAssunto(Protocolo protocolo, ProtocoloDto protocoloDto){
		
		this.protocolo = protocolo;
		 if (protocoloDto.getIdDepartamentoOrigem() != null && 
		 departamentoDao.getBy(protocoloDto.getIdDepartamentoOrigem()).getModulo().equals("SIACOL")) {
			 gerarNumeroTipoProtocolo();
		} else {
			Assunto assunto = assuntoDao.getAssuntoBy(protocolo.getAssunto().getId());
			
			switch (assunto.getTipoAssunto().name()) {
			case "PROTOCOLO":
				gerarNumeroTipoProtocolo();
				break;
				
			case "JUNTA":
				gerarNumeroTipoJunta();
				break;	
				
			case "AUTUA":
				gerarNumeroTipoAutua();
				break;
				
			default:
				break;
			}
		}
		
		
		return this;
	}

	/**
	 * A natureza de assunto do Protocolo do tipo 7 "PROTOCOLO", não gera numero de processo, sendo o valor da coluna da mesma zero(0).
	 * @since 06/2018
	 */
	private void gerarNumeroTipoProtocolo() {
		Long numero = geradorSequenciaDao.getSequencia(TipoProtocoloEnum.PROTOCOLO.getDigito());
		protocolo.setIdProtocolo(numero);
		protocolo.setTipoProtocolo(TipoProtocoloEnum.PROTOCOLO);
		protocolo.setNumeroProtocolo(numero);
		protocolo.setNumeroProcesso(0L);
	}
	
	/**
	 * Verifica a existencia de outros protocolos da pessoa do protocolo sendo ela do tipo Profissional ou Empresa.
	 * @since 06/2018
	 */
	private void gerarNumeroTipoJunta() {
		if(protocolo.getNumeroProcesso().equals(0L)){
			List<Protocolo> protocolosAntecedentes = protocoloDao.getProtocoloAntecedente(protocolo.getPessoa().getId(), protocolo.getAssunto().getId(), protocolo.getTipoPessoa());
			
			if(protocolosAntecedentes.isEmpty()){
				regraPessoaAssuntoSemProtocolosAntecedentes();
			}else{
			    regraPessoaAssuntoComProtocolosAntecedentes(protocolosAntecedentes.stream().findFirst().get());
			}
		}
		Long numero = geradorSequenciaDao.getSequencia(TipoProtocoloEnum.PROTOCOLO.getDigito());
		
		protocolo.setTipoProtocolo(TipoProtocoloEnum.PROTOCOLO);
		protocolo.setIdProtocolo(numero);
		protocolo.setNumeroProtocolo(numero);
	}

	private void regraPessoaAssuntoComProtocolosAntecedentes(Protocolo protocoloAntecedente) {
		protocolo.setNumeroProcesso(protocoloAntecedente.getNumeroProcesso());
	}
	
	
	/**
	 * Caso não tenha protocolos antecedentes, logo será o primeiro protocolo do processo.
	 * O nome do processo será autuado com base no registro da pessoa dona do protocolo.
	 * @since 06/2018
	 */
	private void regraPessoaAssuntoSemProtocolosAntecedentes() {
		Assunto assunto = assuntoDao.getAssuntoBy(protocolo.getAssunto().getId());
		
		if(assunto.getBloco().getTipoProcesso().getDigito().equals(new Long(1))
				|| assunto.getBloco().getTipoProcesso().getDigito().equals(new Long(2))){
			protocolo.setNumeroProcesso(protocolo.getPessoa().getId());
		}		
	}

	private void gerarNumeroTipoAutua() {
		if(protocolo.getNumeroProcesso().equals(0L)){
			regraAutuaSemNumeroProcesso();
		}else{
			regraAutuaComNumeroProcesso();
		}
	}
	
	/**
	 * Se houver número de processo informado, o número do protocolo será do tipo 7 - PROTOCOLO.
	 * @since 06/2018
	 */
	private void regraAutuaComNumeroProcesso() {
		Long numero = geradorSequenciaDao.getSequencia(TipoProtocoloEnum.PROTOCOLO.getDigito());
		protocolo.setIdProtocolo(numero);
		protocolo.setTipoProtocolo(TipoProtocoloEnum.PROTOCOLO);
		protocolo.setNumeroProtocolo(numero);
	}

	/**
	 * Para os assuntos de profissional e empresa, a rotina verifica a existência de processo antecedente com base no registro do interessado.
	 * Se existir, o número do protocolo gerado será do tipo 7 - PROTOCOLO e o processo será o mesmo número de registro do interessado.
	 * Caso não exista, o número do protocolo será igual ao número de registro do interessado. Logo o protocolo gerado será também o processo do profissional ou empresa.
	 * 
	 * Para os demais interessados de tipos diferentes, será gerado o número de acordo com o tipo de assunto escolhido e o processo será o número do protocolo.
	 * @since 06/2018
	 */
	private void regraAutuaSemNumeroProcesso() {
		Assunto assunto = assuntoDao.getAssuntoBy(protocolo.getAssunto().getId());
		
		TipoProtocoloEnum tipoProcessoAssunto = assunto.getBloco().getTipoProcesso();
		
		if(tipoProcessoAssunto.equals(TipoProtocoloEnum.PROFISSIONAL) 
				|| tipoProcessoAssunto.equals(TipoProtocoloEnum.EMPRESA)){
			boolean processoExiste = protocoloDao.existeProcesso(protocolo.getPessoa().getId());
			
			if(processoExiste){
				Long numero = geradorSequenciaDao.getSequencia(TipoProtocoloEnum.PROTOCOLO.getDigito());
				
				protocolo.setIdProtocolo(numero);
				protocolo.setNumeroProtocolo(numero);
				protocolo.setNumeroProcesso(protocolo.getPessoa().getId());
			}else{
				protocolo.setIdProtocolo(protocolo.getPessoa().getId());
				protocolo.setNumeroProtocolo(protocolo.getPessoa().getId());
				protocolo.setNumeroProcesso(protocolo.getPessoa().getId());
			}
			
			protocolo.setTipoProtocolo(assunto.getBloco().getTipoProcesso());
		}else{
			Long numero = geradorSequenciaDao.getSequencia(tipoProcessoAssunto.getDigito());
			protocolo.setIdProtocolo(numero);
			protocolo.setNumeroProtocolo(numero);
			protocolo.setNumeroProcesso(numero);
			protocolo.setTipoProtocolo(assunto.getBloco().getTipoProcesso());
		}		
	}

	public Protocolo build(){
		return protocolo;
	}
	
}
