package br.org.crea.commons.interfaceutil;

import java.util.function.BiFunction;

import javax.inject.Inject;

import br.org.crea.commons.dao.cadastro.domains.DepartamentoDao;
import br.org.crea.commons.dao.cadastro.pessoa.PessoaDao;
import br.org.crea.commons.dao.protocolo.AssuntoDao;
import br.org.crea.commons.dao.protocolo.BlocosAssuntosDao;
import br.org.crea.commons.dao.protocolo.ProtocoloDao;

/**
 * @author Monique Santos
 * @since 05/2018
 * Classe útil para validar a existência de um objeto dado um código identificador.
 * A classe implementa a FunctionalInterface BiFunction e recebe por padrão
 * o identificador e o objeto a ser verificado (Ex (1111, Protocolo.class) ), retornando true ou false
 * na execução do método apply. 
 * 
 * A rotina que fizer uso do método apply deve injetar a classe EntityCreaExistence
 * Ex. Verificando a existência de um protocolo: 
 * '@Inject EntityCreaExistence exist'
 *         exist(1111, Protocolo.class)
 * */

public class EntityCreaExistence implements BiFunction<Long, String, Boolean> {

	@Inject DepartamentoDao departamentoDao;
	
	@Inject ProtocoloDao protocoloDao;
	
	@Inject AssuntoDao assuntoDao;
	
	@Inject BlocosAssuntosDao blocoAssuntoDao;
	
	@Inject PessoaDao pessoaDao;
	
	private Boolean exist;
	
	@Override
	public Boolean apply(Long identificador, String nameClass) {
		
		switch (nameClass) {
		case "Departamento":
			existDepartamento(identificador);
			break;
		case "Protocolo":
			existProtocolo(identificador);
		case "Assunto":
			existAssuntoProtocolo(identificador);	
			break;
		case "Pessoa":
			existPessoa(identificador);	
			break;
		default:
			break;
		}
		
		return exist;
	}

	private void existProtocolo(Long identificador) {
		exist = false;
		exist = protocoloDao.getProtocoloBy(identificador) != null;
		
	}

	private void existDepartamento(Long identificador) {
		exist = false;
		exist = departamentoDao.buscaDepartamentoPor(identificador) != null;
	}
	
	private void existAssuntoProtocolo(Long identificador) {
		exist = false;
		exist = assuntoDao.getBy(identificador) != null;
	}
	
	private void existPessoa(Long identificador) {
		exist = false;
		exist = pessoaDao.getBy(identificador) != null;
	}

}
