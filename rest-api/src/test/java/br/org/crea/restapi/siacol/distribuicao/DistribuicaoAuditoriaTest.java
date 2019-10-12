package br.org.crea.restapi.siacol.distribuicao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.dao.DocumentoDao;
import br.org.crea.commons.dao.cadastro.domains.DepartamentoDao;
import br.org.crea.commons.dao.cadastro.pessoa.InteressadoDao;
import br.org.crea.commons.dao.cadastro.pessoa.PessoaDao;
import br.org.crea.commons.dao.protocolo.ProtocoloDao;
import br.org.crea.commons.dao.siacol.ProtocoloSiacolDao;
import br.org.crea.commons.factory.siacol.AuditaSiacolProtocoloFactory;
import br.org.crea.commons.models.cadastro.Auditoria;
import br.org.crea.commons.models.commons.Protocolo;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.models.siacol.ProtocoloSiacol;
import br.org.crea.commons.models.siacol.dtos.GenericSiacolDto;
import br.org.crea.commons.models.siacol.enuns.StatusProtocoloSiacol;

public class DistribuicaoAuditoriaTest {

	static DepartamentoDao departamentoDao;
	static InteressadoDao interessadoDao;
	static DocumentoDao documentoDao;
	static ProtocoloSiacolDao dao;
	static ProtocoloDao protocoloDao;
	static PessoaDao pessoaDao;
	static AuditaSiacolProtocoloFactory audita;
	static Auditoria auditoria;
	private static EntityManager em = null;
	
	@Before
	public void inicio() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dsCreaTest");
		em = factory.createEntityManager();
		departamentoDao = new DepartamentoDao();
		interessadoDao = new InteressadoDao();
		documentoDao = new DocumentoDao();
		dao = new ProtocoloSiacolDao();
		protocoloDao = new ProtocoloDao();
		audita = new AuditaSiacolProtocoloFactory();
		pessoaDao = new PessoaDao();
		auditoria = new Auditoria();
		departamentoDao.setEntityManager(em);
		interessadoDao.setEntityManager(em);
		documentoDao.setEntityManager(em);
		dao.setEntityManager(em);
		protocoloDao.setEntityManager(em);
		pessoaDao.setEntityManager(em);
	}
	
	@After
	public void fim() {
		em.close();
	}
	
	private GenericSiacolDto populaGenericSiacol() {
		GenericSiacolDto dto = new GenericSiacolDto();
		
		List<Long> idsProtocolos = new ArrayList<Long>();
		idsProtocolos.add(3083104L);
		dto.setListaId(idsProtocolos);
		
		dto.setIdDepartamento(1206L);
		dto.setIdResponsavelNovo(2000105584L);
		dto.setNomeResponsavelNovo("JOYCE TESTE");
		dto.setIdResponsavelAtual(201800000191l);
		dto.setNomeResponsavelAtual("VIVIANE ANDRADE MAIA");
		dto.setStatus(StatusProtocoloSiacol.DISTRIBUICAO_ANALISTA);
		
		return dto;
	}
	
	private UserFrontDto populaUserFrontDto() {
		UserFrontDto user = new UserFrontDto();
		user.setIp("127.0.0.1");
		user.setIdPessoa(2000105584L);
		user.setNome("JOYCE TESTE");
		user.setCpfOuCnpj("07686163792");
//		user.setPerfil(perfil);
		return user;
	}
	
	@Test
	public void auditoriaDistribuicaoTest() {
		GenericSiacolDto dto = populaGenericSiacol();
		UserFrontDto usuario = populaUserFrontDto();
		
		ProtocoloSiacol protocoloAntesDaDistribuicao = dao.getBy(dto.getListaId().get(0));
		auditoriaDaDistribuicao(dto, protocoloAntesDaDistribuicao, usuario);
	}

	private void auditoriaDaDistribuicao(GenericSiacolDto dto, ProtocoloSiacol protocolo, UserFrontDto usuario) {
		// auditoria
		if (protocolo.getNumeroProtocolo().equals(protocolo.getNumeroProcesso())) {
			
			// obtenho protocolos filhos entidade Protocolo
			List<Protocolo> protocolosFilhos = protocoloDao.getAnexosDoProtocoloPor(protocolo.getNumeroProtocolo());
		
			// tem anexo
			if (protocolosFilhos.size() > 0) {
				// audito protocolo pai
				System.out.println("Audita Distribuição Com Referência");
//				audita.auditaDistribuicao(dto, usuario, protocolo, protocolo.getNumeroProtocolo());
				
				// obtenho entidades protocoloSiacol a partir das anteriores
				for (Protocolo protocoloFilho : protocolosFilhos) {
					ProtocoloSiacol protocoloSiacolFilho = dao.getProtocoloBy(protocoloFilho.getNumeroProtocolo());
					
					if (protocoloSiacolFilho != null) {
						System.out.println("Audita Distribuição Anexo");
//						audita.auditaDistribuicaoAnexo(dto, usuario, protocoloSiacolFilho, protocolo.getNumeroProtocolo());
					}
				}
				
			} else {
				// audito protocolo sem anexo
				System.out.println("Audita Distribuição Sem Referência | No. Protocolo: " + protocolo.getNumeroProtocolo() + " No. Processo: " + protocolo.getNumeroProcesso());
//				audita.auditaDistribuicao(dto, usuario, protocolo, null);
			}
			
		} else {
			Protocolo protocoloFilho = protocoloDao.getProtocoloBy(protocolo.getNumeroProtocolo());
			ProtocoloSiacol protocoloPai = dao.getProtocoloBy(protocoloFilho.getIdProtocoloPaiAnexo());
			
			if (protocoloPai != null) {
//				audita.auditaDistribuicaoAnexo(dto, usuario, protocoloPai, protocoloPai.getNumeroProtocolo());
				
				List<Protocolo> protocolosIrmaos = protocoloDao.getAnexosDoProtocoloPor(protocolo.getNumeroProtocolo());
				
				for (Protocolo protocoloIrmao : protocolosIrmaos) {
					ProtocoloSiacol protocoloSiacolIrmao = dao.getProtocoloBy(protocoloIrmao.getNumeroProtocolo());
					
					if (protocoloSiacolIrmao != null) {
						
						if (protocoloSiacolIrmao.getNumeroProtocolo().equals(protocolo.getNumeroProcesso())) {
							System.out.println("Audita Distribuição Com Referência");
//							audita.auditaDistribuicao(dto, usuario, protocoloSiacolIrmao, protocoloPai.getNumeroProtocolo());
						} else {
							System.out.println("Audita Distribuição Anexo");
//							audita.auditaDistribuicaoAnexo(dto, usuario, protocoloSiacolIrmao, protocoloPai.getNumeroProtocolo());
						}
					}
				}
				
			} else {
				// audito protocolo sem anexo
				System.out.println("Audita Distribuição Sem Referência");
//				audita.auditaDistribuicao(dto, usuario, protocolo, null);
			}
		}
	}
	
}