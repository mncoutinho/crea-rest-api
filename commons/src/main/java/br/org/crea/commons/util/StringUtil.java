package br.org.crea.commons.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;

import com.google.gson.Gson;

import br.com.caelum.stella.ValidationMessage;
import br.com.caelum.stella.format.CNPJFormatter;
import br.com.caelum.stella.format.CPFFormatter;
import br.com.caelum.stella.format.Formatter;
import br.com.caelum.stella.validation.NITValidator;



public class StringUtil {

	public static final String VAZIO = "---";
	
	public static final Pattern EMAIL_REGEX = 
		    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);


	public static <T> boolean existe(List<T> lista) {
		if (lista != null) {
			return lista.size() > 0 ? true : false;
		}
		return false;
	}

	public static String getAnoBy(String id) {
		return id.substring(id.length() - 4, id.length());
	}

	public static Long getCodigoBy(String id) {
		return new Long(id.substring(0, id.length() - 4));
	}
	
	public static boolean validaCompetencia(String cmpt){
		
		String regex = "^((19|20)\\d\\d)(0?[1-9]|1[012])$";
		
		if(Pattern.matches(regex, cmpt)){
			return true;
		}else{
			return false;
		}
		
	}
	
	
	public static String convertObjectToJson(Object o){
		Gson gson = new Gson();
		return gson.toJson(o);
	}

	public static String removeAcentos(String texto) {
		if (texto == null) {
			return "";
		}
		texto = Normalizer.normalize(texto, Normalizer.Form.NFD);
		texto = texto.replaceAll("[^\\p{ASCII}]", "");
		return texto;
	}

	public static String removePontuacao(String texto) {
		if (texto == null) {
			return "";
		}
		texto = Normalizer.normalize(texto, Normalizer.Form.NFD);
		texto = texto.replaceAll("\\.", "").replaceAll("\\_", "").replaceAll("\\/", "").replaceAll("\\-", "");
		return texto;
	}

	public static String criaNomeDocumento(String origem, String ano, String numero){
		String nome = origem + " " + numero + "/" + ano;
		return nome;
	}
	
	public static String removeCaracteres(String string) {
	    string = Normalizer.normalize(string, Normalizer.Form.NFD);
        string = string.replaceAll("[^\\p{ASCII}]", "");
        return string;
	}
	
	public static String convertePrimeiraLetraDaPalavraParaMaiuscula(String texto) {
		if (texto == null) {
			return "";
		}
		return texto.substring(0, 1).toUpperCase() + texto.substring(1, texto.length()).toLowerCase();
	}



	public static String preencheComZeros(String serial, int numeroDeZeros) {

		if (serial == null || numeroDeZeros <= 0) {
			return "";
		}

		while (serial.length() < numeroDeZeros) {
			serial = "0" + serial;
		}

		return serial;
	}

	public static String randomUUID() {
		return UUID.randomUUID().toString().replace("-", "").substring(12);
	}
	
	public static String gerarSenha(){
		Random ran = new Random();
		String[] letras ={"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
		String senha = "";
		for (int i = 0; i < 8; i++){
		   int a = ran.nextInt(letras.length);
		   senha += letras[a];
		}
		 		 
		return senha; 
	}


	public static Boolean isValidAndNotEmpty(String value) {
		return (value != null) && (!value.trim().isEmpty());
	}


	public static String convertePortaria(String numeroPortaria) {
		if ((numeroPortaria != null) && (!numeroPortaria.contains("/"))){
			String prefix = (numeroPortaria.substring(3,5).equals("01") ? "GM" : "SAS" );
			return prefix + " " + numeroPortaria.substring(5) + "/20" + numeroPortaria.substring(1,3);  
		}
		return numeroPortaria;
	}
	
	public static String getCnpjCpfFormatado(String cpfOuCnpj){
		if(cpfOuCnpj.length() == 14){
			try {
				Formatter formatter = new CNPJFormatter();
				return formatter.format(cpfOuCnpj);
			} catch (Exception e) {
				return "";
			}
			
		}else{
			try {
				Formatter formatter = new CPFFormatter();
				return formatter.format(cpfOuCnpj);
			} catch (Exception e) {
				return "";
			}
			
		}
	}
	
	public static String formataTelefone(String numero){
		JFormattedTextField telefoneFormatado = null; 
		try {
			MaskFormatter phoneFormatter = new MaskFormatter("(##) ####-####");
			telefoneFormatado = new JFormattedTextField(phoneFormatter);
			telefoneFormatado.setText(numero);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return telefoneFormatado.getText();
	}
	
	public static String formataCpf(String numero){
		JFormattedTextField telefoneFormatado = null; 
		try {
			MaskFormatter phoneFormatter = new MaskFormatter("###.###.###-##");
			telefoneFormatado = new JFormattedTextField(phoneFormatter);
			telefoneFormatado.setText(numero);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return telefoneFormatado.getText();
	}
	
	
	public static String formataCelular(String numero){
		JFormattedTextField telefoneFormatado = null; 
		try {
			MaskFormatter phoneFormatter = new MaskFormatter("(##) #####-####");
			telefoneFormatado = new JFormattedTextField(phoneFormatter);
			telefoneFormatado.setText(numero);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return telefoneFormatado.getText();
	}

	public static boolean ehEmail(String email) {
		Matcher matcher = EMAIL_REGEX .matcher(email);
        return matcher.find();
	}	
	
	public static String convertBigDecimalParaReal(BigDecimal valor){
		DecimalFormat decFormat = new DecimalFormat("¤ #,###,##0.00");
		return decFormat.format(valor);
	}
	
	public static String pathAnoMesDia() {
		LocalDate hoje = LocalDate.now();
		DateTimeFormatter formatador = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		return hoje.format(formatador);
	}
	
    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }
    
    public static String formataCoordenadaGeografica(String coordenadaGeografica) {
    	if(!isBlank(coordenadaGeografica)) {
    		if(coordenadaGeografica.length() > 10) return coordenadaGeografica.substring(0, 10);
    	}
    	return coordenadaGeografica;
    }
    
    public static String limitaTamanhoString(String texto, int tamanho) {
    	if(!isBlank(texto)) {
    		if(texto.length() > tamanho) return texto.substring(0, tamanho);
    	}
    	return texto;
    }

	public static boolean ehPisPasepNit(String nit) {
		NITValidator validator = new NITValidator();
		
		List<ValidationMessage> msgsErro = validator.invalidMessagesFor(nit);
		return msgsErro.isEmpty();
	}
}
