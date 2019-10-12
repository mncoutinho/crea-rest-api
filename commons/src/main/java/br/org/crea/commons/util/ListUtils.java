package br.org.crea.commons.util;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;


public class ListUtils {
	
	public static boolean verificaSeHaElementoComum (List<?> lista1, List<?> lista2) {
		return CollectionUtils.containsAny(lista1, lista2);
	}

	public static boolean verificaSeTodosOsElementosSaoComuns (List<?> lista1, List<?> lista2) {
		return CollectionUtils.containsAll(lista1, lista2);
	}

	public static List<?> removerDuplicidade(List<?> lista) {
		return lista.stream()
			     .distinct()
			     .collect(Collectors.toList());
	}

	public static List<?> copy(List<?> list) {
		return list.stream().collect(Collectors.toList());
	}
	
}
