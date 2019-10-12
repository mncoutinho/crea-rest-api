package br.org.crea.restapi.siacol.relatorio.reuniao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class Relatorio01CoordenadoresEAdjuntosPorDepartamentoTest {

private static EntityManager em = null;
	
	@BeforeClass
	public static void inicio() {
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dsCreaTest");
		em = factory.createEntityManager();
		Assert.assertTrue("entity manager iniciado", em != null);
	}
	
	@AfterClass
	public static void fim() {
		em.close();
		Assert.assertFalse("entity manager finalizado", em.isOpen() );
	}
	
	
	@Test
	public void relatorioDeCoordenadoresEAdjuntosPorDepartamento() {
		
		List<RelatorioReuniaoSiacolDto> listaRelatorio = new ArrayList<RelatorioReuniaoSiacolDto>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT D.SIGLA AS DEPARTAMENTO,                                            ");
		sql.append("        S.DESCRICAO,                                                        ");
		sql.append("        P.CRACHA,                                                           ");
		sql.append("        F.NOME,                                                             ");
		sql.append("        CASE WHEN EXISTS(                                                   ");
		sql.append("        SELECT 1 FROM siacol_reuniao_presenca e                             ");
		sql.append("        WHERE e.fk_reuniao = 10055                                          ");
		sql.append("        and e.fk_pessoa = C.FK_CODIGO_PERSONALIDADE)                        ");
		sql.append("        THEN 'PRESENTE'                                                     ");
		sql.append("        ELSE  'AUSENTE'                                                     ");
		sql.append("        END AS \"statusPresenca\"                                           ");
		sql.append(" FROM PER_CARGO_CONSELHEIRO C                                               ");
		sql.append(" JOIN PER_CARGOS S ON (C.FK_CARGO_RAIZ = S.CODIGO)                          ");
		sql.append(" JOIN PER_PERSONALIDADES P ON (C.FK_CODIGO_PERSONALIDADE = P.CODIGO)        ");
		sql.append(" JOIN CAD_PESSOAS_FISICAS F ON (C.FK_CODIGO_PERSONALIDADE = F.CODIGO)       ");
		sql.append(" JOIN PRT_DEPARTAMENTOS D ON (C.FK_DEPARTAMENTO = D.ID)                     ");
		sql.append("  WHERE C.FK_CARGO_RAIZ IN (2559, 2560)                                     ");
		sql.append("  AND TO_CHAR(C.DATAFINALCARGO, 'YYYYMMDD') >= TO_CHAR(SYSDATE, 'YYYYMMDD') ");
		sql.append("  AND C.REMOVIDO = 0                                                        ");
		sql.append("  AND C.DATADESLIGAMENTOCARGO IS NULL                                       ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());

			Iterator<?> it = query.getResultList().iterator();
			
			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {
					
					RelatorioReuniaoSiacolDto dto = new RelatorioReuniaoSiacolDto();
					
					Object[] result = (Object[]) it.next();
					
					dto.setDepartamento(result[0] == null ? "" : result[0].toString());
					dto.setCracha(result[1] == null ? "" : result[1].toString());
					dto.setNome(result[2] == null ? "" : result[2].toString());
					dto.setPresenca(result[3] == null ? "" : result[3].toString());
					
					listaRelatorio.add(dto);
				}
			}
			
		} catch (NoResultException e) {
			System.out.println("Sem resultados");
		} catch (Throwable e) {
			System.err.println("Erro:" + e.getMessage());
		}
		listaRelatorio.forEach(item -> {
			System.out.println(item.toString());
		});
		
	}
}

class RelatorioReuniaoSiacolDto {
	
	private String departamento;
	
	private String cracha;
	
	private String nome;
	
	private String presenca;

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getCracha() {
		return cracha;
	}

	public void setCracha(String cracha) {
		this.cracha = cracha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getPresenca() {
		return presenca;
	}

	public void setPresenca(String presenca) {
		this.presenca = presenca;
	}

	@Override
	public String toString() {
		return "Departamento: " + departamento + ", Cracha: " + cracha + ", Nome: " + nome + ", Presença: " + presenca;
	}
}
