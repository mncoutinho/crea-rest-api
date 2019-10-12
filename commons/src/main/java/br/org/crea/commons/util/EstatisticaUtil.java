package br.org.crea.commons.util;

import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EstatisticaUtil {

	public static double soma(List<Integer> numeros) {
	    return numeros.stream().mapToInt(Integer::intValue).sum();
	}
	
	public static int soma(Stream<Integer> numeros) {
	    return numeros.mapToInt(Integer::intValue).sum();
	}
	
	public static int mediaInt(List<Integer> numeros) {
	     Double soma = numeros.stream().mapToInt(Integer::intValue).average().orElse(0);
	     return soma.intValue();
	}	
	
	public static double desvioPadrao(List<Integer> numeros) {
	    double media = numeros.stream().mapToInt(Integer::intValue).average().orElse(0);
	    double mediaDesvios = numeros.stream().mapToDouble(i -> Math.pow(i - media, 2)).average().orElse(0);
	    return Math.sqrt(mediaDesvios);
	}

	public static int valorMinimo(List<Integer> lista) {
		return lista.stream()
			      .mapToInt(v -> v)
			      .min().orElse(0);
	}
	
	public static int valorMaximo(List<Integer> lista) {
		return lista.stream()
			      .mapToInt(v -> v)
			      .max().orElse(0);
	}

	public static int modaUnica(List<Integer> lista) {
		Optional<Entry<Integer, Long>> i = lista.stream()
			      .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
			      .entrySet()
			      .stream()
			      .max(Comparator.comparing(Entry::getValue));

		return i.get().getKey();
	}

	public static double somaDouble(List<Double> numeros) {
		 return numeros.stream().mapToDouble(Double::doubleValue).sum();
	}
}
