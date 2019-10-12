package br.org.crea.restapi.siacol;

import org.junit.Test;

public class ContagemVotosSiacolTest {
	
	@Test
	public void inicio() {
		
		Exemplo ex = new Exemplo();
		ex.setIdade(2);
		ex.setNome("Ricardo");
		System.out.println(ex.toString());
		
	}
	

}


class Exemplo {
	private String nome;
	private int idade;
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getIdade() {
		return idade;
	}
	public void setIdade(int idade) {
		this.idade = idade;
	}
	@Override
	public String toString() {
		return "Nome:" + nome + ", Idade:" + idade + "]";
	}
	
	
}
