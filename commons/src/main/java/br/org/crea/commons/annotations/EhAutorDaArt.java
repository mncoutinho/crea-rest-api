package br.org.crea.commons.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** /** Anotação criada para que um recurso não público seja acessado somente pelo autor da ART que ele deseja alterar
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EhAutorDaArt {

	boolean value() default false;
	
}
