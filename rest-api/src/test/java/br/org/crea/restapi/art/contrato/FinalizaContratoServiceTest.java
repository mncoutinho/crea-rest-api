package br.org.crea.restapi.art.contrato;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.dao.art.ContratoArtDao;
import br.org.crea.commons.models.art.ContratoArt;
import br.org.crea.commons.models.commons.Endereco;
import br.org.crea.commons.util.ListUtils;
import br.org.crea.commons.util.StringUtil;

public class FinalizaContratoServiceTest {
	
	ContratoArtDao daoContratoArt;
	private static EntityManager em = null;
	
	@Before
	public void inicio() {
		daoContratoArt = new ContratoArtDao();
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dsCreaTest");
		em = factory.createEntityManager();
		daoContratoArt.setEntityManager(em);
	}
	
	@After
	public void fim() {
		em.close();
	}
	
	@Test
	public void deveRetornarFalseNoEnderecoContratante() {
		ContratoArt contratoArt = daoContratoArt.getContratoPor("2020180000475-1");
		ContratoArt contratoArtPrincipal = daoContratoArt.getContratoPor("2020180000477-1");
		Assert.assertFalse(this.verificaSeEnderecoDoContratanteEhOmesmo(contratoArt, contratoArtPrincipal));
	}
	
	@Test
	public void deveRetornarTrueQuandoUmItemDaListaPertenceAOutraLista() {
		List<Long> lista1 = Arrays.asList(1L, 2L, 3L);
		List<Long> lista2 = Arrays.asList(6L, 1L);
		Assert.assertTrue(ListUtils.verificaSeHaElementoComum(lista1, lista2));
	}
	
	@Test
	public void deveRetornarFalseQuandoNenhumItemDaListaPertenceAOutraLista() {
		List<Long> lista1 = Arrays.asList(1L, 2L, 3L);
		List<Long> lista2 = Arrays.asList(4L, 7L, 5L);
		Assert.assertFalse(ListUtils.verificaSeHaElementoComum(lista1, lista2));
	}
	
	
	
	private Boolean verificaSeEnderecoDoContratanteEhOmesmo(ContratoArt contratoArt, ContratoArt contratoArtPrincipal) {
		if(contratoArt.temEnderecoContratante() && contratoArtPrincipal.temEnderecoContratante()) {
			Endereco enderecoArt = contratoArt.getEnderecoContratante().getClone();
			Endereco enderecoArtPrincipal = contratoArtPrincipal.getEnderecoContratante().getClone();
			
			return enderecosSaoIguais(removerAcentosEespacoDoEndereco(enderecoArt), removerAcentosEespacoDoEndereco(enderecoArtPrincipal));
		} else if (!contratoArt.temEnderecoContratante() && !contratoArtPrincipal.temEnderecoContratante()) {
			return true;
		}
		return false;
	}
	
	private Endereco removerAcentosEespacoDoEndereco(Endereco endereco) {

		endereco.setLogradouro(endereco.temLogradouro() ? StringUtil.removeAcentos(endereco.getLogradouro().toUpperCase()).trim() : null);
		endereco.setNumero(endereco.temNumero() ? StringUtil.removeAcentos(endereco.getNumero().toUpperCase()).trim() : null);
		endereco.setComplemento(endereco.temComplemento() ? StringUtil.removeAcentos(endereco.getComplemento().toUpperCase()).trim() : null);
		
		return endereco;
	}

	private Boolean enderecosSaoIguais (Endereco enderecoPrincipal, Endereco enderecoSecundario) {
		Javers javers = JaversBuilder.javers().registerValueObject(Endereco.class).build();
		Diff diff = javers.compare(enderecoPrincipal, enderecoSecundario);
		System.out.println(diff.getChanges());
		return diff.getChanges().size() == 0;
	}

}
