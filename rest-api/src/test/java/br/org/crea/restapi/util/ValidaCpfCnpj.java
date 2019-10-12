//package br.org.crea.restapi.util;
//
//import org.junit.Test;
//
//import br.com.caelum.stella.validation.CNPJValidator;
//import br.com.caelum.stella.validation.CPFValidator;
//import br.com.caelum.stella.validation.InvalidStateException;
//
//public class ValidaCpfCnpj {
//	
//	
//	@Test
//	public void validaCpf(){
//		String cpf = "0515263483";
//		
//		CPFValidator validator = new CPFValidator();
//		
//		try {
//		    // lógica de negócio ...
//		    validator.assertValid(cpf);
//		    // continuação da lógica de negócio ...
//		} catch (InvalidStateException e) { // exception lançada quando o documento é inválido
//		    System.out.println(e.getInvalidMessages());
//		}
//		
//	}
//	
//	@Test
//	public void validaCNPJ(){
//		
//		String cnpj = "14663126000150";
//		
//		CNPJValidator validator = new CNPJValidator();
//		
//		try {
//			// lógica de negócio ...
//			validator.assertValid(cnpj);
//			// continuação da lógica de negócio ...
//		} catch (InvalidStateException e) { // exception lançada quando o documento é inválido
//			System.out.println(e.getInvalidMessages());
//		}
//		
//	}
//
//}
