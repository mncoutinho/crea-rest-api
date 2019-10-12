package br.org.crea.commons.util;

import java.util.Date;

import br.org.crea.commons.models.commons.Arquivo;
import br.org.crea.commons.models.commons.enuns.ModuloSistema;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;

public class StorageUtil {

	public static final String EXTENSAO_PDF = ".pdf";
	
	public static final boolean PRIVADO = true;
	
	public static final boolean PUBLICO = false;
	
	private static String raizStorage;
	
	private static ModuloSistema modulo;
	
	private static String nomeArquivoOriginal;
	
	private static String nomeArquivoStorage;
	
	private static String extensaoArquivo;
	
	private static String caminhoStorage;
	
	private static String pathStorage;
	
	private static String descricao;
	
	private static String status = "A";
	
	private static Pessoa pessoa = new Pessoa();
	
	private static boolean privado;
	
	private static String uri;
	
	private static Arquivo arquivo;
	
	public static String getRaizStorage() {
		return raizStorage;
	}
	
	public static String getRaizStoragePrivada() {
		return "/opt/arquivos/";
	}
	
	public static String getRaizStoragePublica() {
		return "/var/www/html/arquivos/";
	}

	public static String getCaminhoCompleto() {
		return raizStorage + caminhoStorage;
	}
	
	public static Arquivo populaArquivo(ModuloSistema modulo, String nomeArquivo, String extensao, boolean ehPrivado, Long idPessoa, String descricao) {
		StorageUtil.modulo = modulo;
		if (nomeArquivo != null) {
			if (nomeArquivo.length() > 250) {
				nomeArquivoOriginal = nomeArquivo.substring(0, 250);
			} else {
				nomeArquivoOriginal = nomeArquivo;
			}			
		}
		
		nomeArquivoStorage = StringUtil.randomUUID();
		extensaoArquivo = extensao;
		pathStorage = modulo + "/" + StringUtil.pathAnoMesDia();
		caminhoStorage =  pathStorage + "/" + nomeArquivoStorage + extensaoArquivo;
		setPrivado(ehPrivado);
		FileUtils.criarDiretorio(raizStorage + pathStorage);
		pessoa.setId(idPessoa);
		StorageUtil.descricao = descricao;
		setArquivo();
		
		return getArquivo();
	}
	
	public static void setPrivado(boolean valor) {
		privado = valor;
		
		if (privado) {
			raizStorage = "/opt/arquivos/";
			uri = "/commons/arquivo/" + nomeArquivoStorage;
		} else {
			raizStorage = "/var/www/html/arquivos/";
			uri = caminhoStorage;
		}	
	}

	private static Arquivo getArquivo() {
		return arquivo;
	}

	private static void setArquivo() {
		arquivo = new Arquivo();
		
		arquivo.setCaminhoOriginal(nomeArquivoStorage);
		arquivo.setCaminhoStorage(caminhoStorage);
		arquivo.setNomeOriginal(nomeArquivoOriginal);
		arquivo.setNomeStorage(nomeArquivoStorage);
		arquivo.setExtensao(extensaoArquivo);
		arquivo.setTamanho(FileUtils.obterTamanhoDoArquivo(getCaminhoCompleto()));
		arquivo.setModulo(modulo);
		arquivo.setDataInclusao(new Date());	
		arquivo.setPessoa(pessoa);
		arquivo.setStatus(status);
		arquivo.setDescricao(descricao);
		arquivo.setPrivado(privado);
		arquivo.setUri(uri);
	}

}
