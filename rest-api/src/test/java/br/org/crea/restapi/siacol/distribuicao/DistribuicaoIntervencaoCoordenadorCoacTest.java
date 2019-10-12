package br.org.crea.restapi.siacol.distribuicao;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;

import br.org.crea.commons.dao.DocumentoDao;
import br.org.crea.commons.dao.cadastro.domains.DepartamentoDao;
import br.org.crea.commons.dao.cadastro.pessoa.InteressadoDao;
import br.org.crea.commons.dao.protocolo.ProtocoloDao;
import br.org.crea.commons.dao.siacol.ProtocoloSiacolDao;
import br.org.crea.commons.factory.siacol.AuditaSiacolProtocoloFactory;
import br.org.crea.commons.models.cadastro.Departamento;
import br.org.crea.commons.models.cadastro.Documento;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.models.siacol.ProtocoloSiacol;
import br.org.crea.commons.models.siacol.dtos.GenericSiacolDto;
import br.org.crea.commons.models.siacol.dtos.ProtocoloSiacolDto;
import br.org.crea.commons.models.siacol.enuns.StatusProtocoloSiacol;
import br.org.crea.commons.util.DateUtils;

public class DistribuicaoIntervencaoCoordenadorCoacTest {

	static DepartamentoDao departamentoDao;
	static InteressadoDao interessadoDao;
	static DocumentoDao documentoDao;
	static ProtocoloSiacolDao dao;
	static ProtocoloDao protocoloDao;
	static AuditaSiacolProtocoloFactory audita;
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
		departamentoDao.setEntityManager(em);
		interessadoDao.setEntityManager(em);
		documentoDao.setEntityManager(em);
		dao.setEntityManager(em);
		protocoloDao.setEntityManager(em);
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
	
//	@Test
	public void distribuicaoIntervencaoCoordenadorCoacTest() {
		GenericSiacolDto dto = populaGenericSiacol();
		UserFrontDto usuario = populaUserFrontDto();
		ProtocoloSiacol protocoloAntesDaDistribuicao = dao.getBy(dto.getListaId().get(0));
		
		ProtocoloSiacolDto protocoloDto = new ProtocoloSiacolDto();
		protocoloDto.setId(protocoloAntesDaDistribuicao.getId());
		protocoloDto.setUltimoAnalista(protocoloAntesDaDistribuicao.getUltimoAnalista());
		protocoloDto.setDataSiacol(protocoloAntesDaDistribuicao.getDataSiacol());
		protocoloDto.setRecebido(protocoloAntesDaDistribuicao.getRecebido());
		protocoloDto.setDataRecebimento(protocoloAntesDaDistribuicao.getDataRecebimento());
		
		
		distribuiProtocolo(dto, usuario);
		
		ProtocoloSiacol protocoloDepoisDaDistribuicao = dao.getBy(dto.getListaId().get(0));
		
		assertTrue(protocoloFoiDistribuido(protocoloDepoisDaDistribuicao, usuario));
		
		System.out.println("Antes da Distribuição");
		System.out.println("Id      | Ultimo Analista | Data Siacol         | Recebido | Data Recebimento");
		System.out.println(protocoloDto.getId() + " | " + protocoloDto.getUltimoAnalista() + "      | " + DateUtils.format(protocoloDto.getDataSiacol(), DateUtils.DD_MM_YYYY_HH_MM_SS) + " | " + protocoloDto.getRecebido() + "    | " + protocoloDto.getDataRecebimento());
		
		System.out.println("Depois da Distribuição");
		System.out.println("Id      | Ultimo Analista | Data Siacol         | Recebido | Data Recebimento");
		System.out.println(protocoloDepoisDaDistribuicao.getId() + " | " + protocoloDepoisDaDistribuicao.getUltimoAnalista() + "      | " + DateUtils.format(protocoloDepoisDaDistribuicao.getDataSiacol(), DateUtils.DD_MM_YYYY_HH_MM_SS) + " | " + protocoloDepoisDaDistribuicao.getRecebido() + "    | " + protocoloDepoisDaDistribuicao.getDataRecebimento());
		
		dao.createTransaction();
		protocoloAntesDaDistribuicao.setUltimoAnalista(protocoloDto.getUltimoAnalista());
		protocoloAntesDaDistribuicao.setDataSiacol(protocoloDto.getDataSiacol());
		protocoloAntesDaDistribuicao.setRecebido(protocoloDto.getRecebido());
		protocoloAntesDaDistribuicao.setDataRecebimento(protocoloDto.getDataRecebimento());
		
		dao.update(protocoloAntesDaDistribuicao);
		dao.commitTransaction();
	}
	
	private boolean protocoloFoiDistribuido(ProtocoloSiacol protocolo, UserFrontDto usuario) {
		return !protocolo.getRecebido() && protocolo.getDataRecebimento() == null && protocolo.getUltimoAnalista().equals(usuario.getIdPessoa());		
	}

	public GenericSiacolDto distribuiProtocolo(GenericSiacolDto dto, UserFrontDto usuario) {
		
		try {

			for (Long idProtocolo : dto.getListaId()) {
				
				if (!dto.temResponsavelNovo()) {
					distribuiParaCoordenadorDoDepartamentoOuCoac(dto);
				}
				
				assinaOsPropriosDocumentosAoDistribuir(idProtocolo, usuario);
				
				if (dto.getSiacol() != null) {
					distribuirParaCoordenadorCoac(dto);
				}

				ProtocoloSiacol protocolo = dao.getBy(idProtocolo);
				
				protocolo.setUltimoAnalista(usuario.getIdPessoa());
				protocolo.setDataSiacol(new Date());
				protocolo.setRecebido(false);
				protocolo.setDataRecebimento(null);
				
				dao.createTransaction();
				dao.update(protocolo);
				dao.commitTransaction();
				
				dto.setIdProtocolo(idProtocolo);
				
//				auditoriaDaDistribuicao(dto, protocolo, usuario);

				// alteração de status
//				protocolo = populaProtocolo(dto, usuario);
//				dao.update(protocolo);
			}
			
		} catch (Throwable e) {
			System.err.println(e.getMessage());
		}

		return dto;

	}

	private void distribuirParaCoordenadorCoac(GenericSiacolDto dto) {
		dto.setIdResponsavelNovo(departamentoDao.getBy(new Long(230201)).getCoordenador().getId());
		dto.setNomeResponsavelNovo(interessadoDao.buscaInteressadoBy(dto.getIdResponsavelNovo()).getNome());
	}

	private void assinaOsPropriosDocumentosAoDistribuir(Long idProtocolo, UserFrontDto usuario) {
		List<Documento> listaDocumento = new ArrayList<Documento>();
		listaDocumento = documentoDao.recuperaDocumentosByNumeroProtocolo(idProtocolo);
		
		if(!listaDocumento.isEmpty()){
			for (Documento documento : listaDocumento) {
				if (documento.getResponsavel() == usuario.getIdPessoa() || documento.getResponsavel() == usuario.getIdFuncionario()) {
					documento.setAssinado(true);
					documentoDao.update(documento);
				}
			}
		}
	}

	private void distribuiParaCoordenadorDoDepartamentoOuCoac(GenericSiacolDto dto) {
		Departamento departamento = new Departamento();
		departamento = departamentoDao.getBy(new Long(dto.getIdDepartamento()));
		
		if (departamento.temCoordenador()) {
			dto.setIdResponsavelNovo(departamento.getCoordenador().getId());
			dto.setNomeResponsavelNovo(interessadoDao.buscaInteressadoBy(dto.getIdResponsavelNovo()).getNome());
		} else {
			distribuirParaCoordenadorCoac(dto);
		}
	}
}
