package br.org.crea.commons.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class NumericUtils {

	public static boolean isNumericValue(String value){
		
		if (StringUtil.isValidAndNotEmpty(value)){
			try {  
		        Double.parseDouble(value);   
		        return true;  
		    } catch (NumberFormatException ex) {  
		        return false;  
		    }  
		}
		return false;  
	}
	
	public static Double numericConverter(String value){
		return Double.parseDouble(value);
	}
	
	public static String currencyFormat(BigDecimal value) {
		if (value == null) {
			return "";
		}
		NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("pt","BR"));
		return formatter.format(value.doubleValue()).split(" ")[1];
	}
	
	
}
