package br.org.crea.commons.interfaceutil;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class FormatMensagensConsumer implements Consumer <List<String>> {

	/**
	 * @author Monique Santos
	 * Consumer útil para remover mensagens vazias sobre qualquer lista de validação 
	 * de regras. Para uso deve-se injetar o consumer.
	 * 
	 * */
	
	@Override
	public void accept(List<String> mensagensValidacao) {
		
		Iterator<String> mensagem = mensagensValidacao.iterator();
		while (mensagem.hasNext()) {
			if (mensagem.next().isEmpty()) {
				mensagem.remove();
			}
		}
	}
	
	
}

