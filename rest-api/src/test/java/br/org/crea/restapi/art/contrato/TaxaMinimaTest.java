package br.org.crea.restapi.art.contrato;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.dao.art.ArtDao;
import br.org.crea.commons.dao.art.ContratoArtDao;
import br.org.crea.commons.models.art.ContratoArt;
import br.org.crea.commons.models.commons.Endereco;
import br.org.crea.commons.util.EnderecoUtil;
import br.org.crea.commons.util.ListUtils;

public class TaxaMinimaTest {

	ContratoArtDao dao;
	ArtDao artDao;
	private static EntityManager em = null;
	
	@Before
	public void inicio() {
		dao = new ContratoArtDao();
		artDao = new ArtDao();
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dsCreaTest");
		em = factory.createEntityManager();
		dao.setEntityManager(em);
		artDao.setEntityManager(em);
	}
	
	@After
	public void fim() {
		em.close();
	}
	
	public void deveTerTaxaMinimaQuandoEhDesempenhoDeCargoFuncaoEContratoTemPrazoIndeterminado() {
		dao.createTransaction();
		ContratoArt contratoArt = dao.getContratoPor("2020180000476-1");
		assertTrue(this.defineSeVaiUtilizarTaxaMinima(contratoArt));
		dao.commitTransaction();
	}
	
	public void naoDeveTerTaxaMinimaQuandoNaoPossuiArtPrincipal() {
		dao.createTransaction();
		ContratoArt contratoArt = dao.getContratoPor("2020180000475-1");
		assertFalse(this.defineSeVaiUtilizarTaxaMinima(contratoArt));
		dao.commitTransaction();
	}
	
	public void deveTerTaxaMinimaQuandoArtForMultiplaEArtPrincipalEhObraServicoComMesmoContratanteNumeroContratoEEnderecoDoContratante() {
		dao.createTransaction();
		ContratoArt contratoArt = dao.getContratoPor("2020180000498-1");
		assertTrue(this.defineSeVaiUtilizarTaxaMinima(contratoArt));
		dao.commitTransaction();
	}
	
	public void deveTerTaxaMinimaQuandoArtForMultiplaEArtPrincipalEhDesempenhoDeCargoFuncaoComMesmoContratante() {
		dao.createTransaction();
		ContratoArt contratoArt = dao.getContratoPor("2020180000498-1");
		assertTrue(this.defineSeVaiUtilizarTaxaMinima(contratoArt));
		dao.commitTransaction();
	}
	
	public void deveTerTaxaMinimaQuandoEhMesmoContratanteEmpresaEEnderecoDoContrato() {
		dao.createTransaction();
		ContratoArt contratoArt = dao.getContratoPor("2020180000498-1");
		assertTrue(this.defineSeVaiUtilizarTaxaMinima(contratoArt));
		dao.commitTransaction();
	}
	
	public void deveTerTaxaMinimaQuandoEhMesmoContratanteEEnderecoDoContratoESemEmpresaEmAmbasEPeloMenosUmaAtividadeIgual() {
		dao.createTransaction();
		ContratoArt contratoArt = dao.getContratoPor("2020180000498-1");
		assertTrue(this.defineSeVaiUtilizarTaxaMinima(contratoArt));
		dao.commitTransaction();
	}
	
	@Test
	public void deveTerTaxaMinimaQuandoEhObraServicoEArtPrincipalEhDesempenhoDeCargoFuncaoEMesmoContratanteProfissionalEEmpresa() {
		dao.createTransaction();
		ContratoArt contratoArt = dao.getContratoPor("2020180000498-1");
		assertTrue(this.defineSeVaiUtilizarTaxaMinima(contratoArt));
		dao.commitTransaction();
	}
	
	public Boolean defineSeVaiUtilizarTaxaMinima(ContratoArt contrato) {
		
		//Se for receituario Agronomico sempre a taxaMinima é true e retrun
		
		if(contrato.artEhDesempenhoDeCargoEFuncao() && contrato.temPrazoIndeterminado()) {
			artDao.atualizaTaxaMinima(contrato.getArt().getNumero(), true);
			return true;
		}
		
		if(contrato.artNaoPossuiArtPrincipal()) { // Linha 1616 - legado
			artDao.atualizaTaxaMinima(contrato.getArt().getNumero(), false);
			return false;
		}
		
		if(contrato.artPossuiArtPrincipal()) {
			ContratoArt contratoArtPrincipal = dao.getPrimeiroContratoPor(contrato.getArt().getNumeroARTPrincipal());
			
			if (contrato.artEhMultipla() && contratoArtPrincipal.artEhObraServico()) {
				if(contratanteEhOMesmo(contrato,contratoArtPrincipal) && 
				   numeroDeContratoEhOMesmo(contrato,contratoArtPrincipal) &&
				   enderecoDoContratanteEhOmesmo(contrato,contratoArtPrincipal)
				) {
					artDao.atualizaTaxaMinima(contrato.getArt().getNumero(), true);
					return true;
				}
			}
			
			if (contrato.artEhMultipla() && contratoArtPrincipal.artEhDesempenhoDeCargoEFuncao()) {
				if(contratanteEhOMesmo(contrato,contratoArtPrincipal)) {
					artDao.atualizaTaxaMinima(contrato.getArt().getNumero(), true);
					return true;
				}
			}
			
			if(enderecoDoContratoEhOmesmo(contrato,contratoArtPrincipal) &&
			   contratanteEhOMesmo(contrato,contratoArtPrincipal)  &&
			   empresaEhAMesma(contrato,contratoArtPrincipal)
			  ) {
				artDao.atualizaTaxaMinima(contrato.getArt().getNumero(), true);
				return true;
			}
			
			if(enderecoDoContratoEhOmesmo(contrato,contratoArtPrincipal) &&
			   contratanteEhOMesmo(contrato,contratoArtPrincipal)  &&
			   peloMenosUmaAtividadeEhIgual(contrato,contratoArtPrincipal) &&
			   naoHaEmpresaEmAmbasArts(contrato,contratoArtPrincipal)
			  ) {
				artDao.atualizaTaxaMinima(contrato.getArt().getNumero(), true);
				return true;
			}
			
			if(contrato.artEhObraServico() && contratoArtPrincipal.artEhDesempenhoDeCargoEFuncao()) {
				if(profissionalEhOMesmo(contrato,contratoArtPrincipal) &&
				   empresaEhAMesma(contrato,contratoArtPrincipal) &&
				   contratanteEhOMesmo(contrato,contratoArtPrincipal)
				) {
					artDao.atualizaTaxaMinima(contrato.getArt().getNumero(), true);
					return true;
				}
			}
			
		}
		
		artDao.atualizaTaxaMinima(contrato.getArt().getNumero(), false);
		return false;
		
	}

	
	private Boolean numeroDeContratoEhOMesmo(ContratoArt contrato, ContratoArt contratoArtPrincipal) {
		if (contrato.temNumeroContrato() && contratoArtPrincipal.temNumeroContrato()) {
			return contrato.getNumeroContrato().equals(contratoArtPrincipal.getNumeroContrato());
		}
		return false;
	}

	private Boolean naoHaEmpresaEmAmbasArts(ContratoArt contrato, ContratoArt contratoArtPrincipal) {
		return !contrato.getArt().temEmpresa() && !contratoArtPrincipal.getArt().temEmpresa();
	}

	private Boolean peloMenosUmaAtividadeEhIgual(ContratoArt contrato, ContratoArt contratoArtPrincipal) {
		return ListUtils.verificaSeHaElementoComum(dao.getListaDeCodigosDasAtividadesDoContratoPor(contrato.getId()), dao.getListaDeCodigosDasAtividadesDoContratoPor(contratoArtPrincipal.getId()));
	}

	private Boolean profissionalEhOMesmo(ContratoArt contrato, ContratoArt contratoArtPrincipal) {
		if(contrato.getArt().temProfissional() && contratoArtPrincipal.getArt().temProfissional()) {
			return contrato.getArt().getProfissional().getPessoaFisica().getId().equals(contrato.getArt().getProfissional().getPessoaFisica().getId());
		}
		return false;
	}

	private Boolean empresaEhAMesma(ContratoArt contrato, ContratoArt contratoArtPrincipal) {
		if(contrato.getArt().temEmpresa() && contratoArtPrincipal.getArt().temEmpresa()) {
			return contrato.getArt().getEmpresa().getPessoaJuridica().getId().equals(contratoArtPrincipal.getArt().getEmpresa().getPessoaJuridica().getId());
		}
		return false;
	}

	private Boolean enderecoDoContratanteEhOmesmo(ContratoArt contratoArt, ContratoArt contratoArtPrincipal) {
		if(contratoArt.temEnderecoContratante() && contratoArtPrincipal.temEnderecoContratante()) {
			Endereco enderecoArt = contratoArt.getEnderecoContratante().getClone();
			Endereco enderecoArtPrincipal = contratoArtPrincipal.getEnderecoContratante().getClone();
			
			return EnderecoUtil.enderecosSaoIguais(EnderecoUtil.removerAcentosEespacoDoEndereco(enderecoArt), EnderecoUtil.removerAcentosEespacoDoEndereco(enderecoArtPrincipal));
		} else if (!contratoArt.temEnderecoContratante() && !contratoArtPrincipal.temEnderecoContratante()) {
			return true;
		}
		return false;
	}
	
	private Boolean enderecoDoContratoEhOmesmo(ContratoArt contratoArt, ContratoArt contratoArtPrincipal) {
		if(contratoArt.temEndereco() && contratoArtPrincipal.temEndereco()) {
			Endereco enderecoArt = contratoArt.getEndereco().getClone();
			Endereco enderecoArtPrincipal = contratoArtPrincipal.getEndereco().getClone();
			
			return EnderecoUtil.enderecosSaoIguais(EnderecoUtil.removerAcentosEespacoDoEndereco(enderecoArt), EnderecoUtil.removerAcentosEespacoDoEndereco(enderecoArtPrincipal));
		} else if (!contratoArt.temEndereco() && !contratoArtPrincipal.temEndereco()) {
			return true;
		}
		return false;
	}
	
	private Boolean contratanteEhOMesmo(ContratoArt contrato,ContratoArt contratoArtPrincipal) {
		if(contrato.temPessoa() && contratoArtPrincipal.temPessoa()) {
			return contrato.getPessoa().getId().equals(contratoArtPrincipal.getPessoa().getId());
		}
		return false;
	}
	

	
	
	
	
}
