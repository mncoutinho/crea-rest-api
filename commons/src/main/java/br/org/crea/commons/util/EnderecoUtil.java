package br.org.crea.commons.util;

import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;

import br.org.crea.commons.models.commons.Endereco;

public class EnderecoUtil {
	
	public static Endereco removerAcentosEespacoDoEndereco(Endereco endereco) {

		endereco.setLogradouro(endereco.temLogradouro() ? StringUtil.removeAcentos(endereco.getLogradouro().toUpperCase()).trim() : null);
		endereco.setNumero(endereco.temNumero() ? StringUtil.removeAcentos(endereco.getNumero().toUpperCase()).trim() : null);
		endereco.setComplemento(endereco.temComplemento() ? StringUtil.removeAcentos(endereco.getComplemento().toUpperCase()).trim() : null);
		
		return endereco;
	}

	public static Boolean enderecosSaoIguais (Endereco enderecoPrincipal, Endereco enderecoSecundario) {
		Javers javers = JaversBuilder.javers().registerValueObject(Endereco.class).build();
		Diff diff = javers.compare(enderecoPrincipal, enderecoSecundario);
		System.out.println(diff.getChanges());
		return diff.getChanges().size() == 0;
	}

}
