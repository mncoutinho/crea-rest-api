package br.org.crea.restapi.corporativo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Test;

import br.org.crea.commons.dao.cadastro.empresa.EmpresaDao;
import br.org.crea.commons.models.cadastro.dtos.empresa.VinculoEmpresaResponsavelTecnicoDto;

public class VinculoEmpresaRtTest {
	
	@Test
	public void deveBuscarVinculosEmpresaXProfissional(){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("dsCreaTest");
		EntityManager em = emf.createEntityManager();
		
		try {
			
			List<VinculoEmpresaResponsavelTecnicoDto> listVinculos = new ArrayList<VinculoEmpresaResponsavelTecnicoDto>();
			
			EmpresaDao dao = new EmpresaDao();
			dao.setEntityManager(em);
			
			listVinculos = dao.getEmpresasOndeProfissionalEhResponsavelPor(new Long(0));
			
			for (VinculoEmpresaResponsavelTecnicoDto dto : listVinculos) {
				
				System.out.println(">> " + dto.getRamoAtividade());
				System.out.println(">> " + dto.getRegistroEmpresa());
				System.out.println(">> " + dto.getNumeroArtCargoFuncao());
				System.out.println(">> " + dto.getRazaoSocialEmpresa());
				System.out.println(">> " + dto.getJornadaProfissional());
				System.out.println(">> " + dto.getSalarioProfissional());
				System.out.println(">> " + dto.getEnderecoEmpresa().getEnderecoCompleto());
			}
					
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
