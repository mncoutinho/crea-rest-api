package br.org.crea.commons.util;

public class ExceptionUtil {
	
	public static String getStackTrace(Throwable throwable) {
	    StringBuilder result = new StringBuilder();	    
	    while (throwable != null) {
	    	
	    	for (StackTraceElement st : throwable.getStackTrace()) {
	    		result.append("File   >> " + st.getFileName()).append("\n")
				      .append("Método >> " + st.getMethodName()).append("\n")
				      .append("Classe >> " + st.getClassName());		
			}	    		    	
	        throwable = throwable.getCause();	        
	        if (throwable != null) {
	        	result.append("\n");
	        }
	    }
	    return result.toString();
	}
	
	public static String getExceptionMessageChain(Throwable throwable) {
	    StringBuilder result = new StringBuilder();	    
	    while (throwable != null) {	    	
	    	result.append(throwable.getMessage());	    	
	        throwable = throwable.getCause();	        
	        if (throwable != null) {
	        	result.append("\n");
	        }
	    }
	    return result.toString();
	}

}
