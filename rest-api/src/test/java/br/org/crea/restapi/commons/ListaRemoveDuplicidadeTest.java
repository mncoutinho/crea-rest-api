package br.org.crea.restapi.commons;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import br.org.crea.commons.util.ListUtils;

public class ListaRemoveDuplicidadeTest {

	@Test
	public void deveRemoverDuplicidadeListaDeInteiros() {
		List<?> listaInteiros = ListUtils.removerDuplicidade(Arrays.asList(1, 2, 3, 4, 3, 4, 1, 1, 4));
		System.out.println(listaInteiros.toString());
		assertTrue(ListUtils.verificaSeTodosOsElementosSaoComuns(listaInteiros, Arrays.asList(1, 2, 3, 4)));
	}
	

	@Test
	public void deveRemoverDuplicidadeListaDeLong() {
		List<?> listaLongs = ListUtils.removerDuplicidade(Arrays.asList(1L, 2L, 3L, 4L, 3L, 4L, 1L, 1L, 4L));
		System.out.println(listaLongs.toString());
		assertTrue(ListUtils.verificaSeTodosOsElementosSaoComuns(listaLongs, Arrays.asList(1L, 2L, 3L, 4L)));
	}
	

	@Test
	public void deveRemoverDuplicidadeListaDeString() {
		List<?> listaStrings = ListUtils.removerDuplicidade(Arrays.asList("1", "2", "3", "4", "3", "4", "1", "1", "4"));
		System.out.println(listaStrings.toString());
		assertTrue(ListUtils.verificaSeTodosOsElementosSaoComuns(listaStrings, Arrays.asList("1", "2", "3", "4")));
	}
}
