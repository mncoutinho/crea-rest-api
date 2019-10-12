package br.org.crea.commons.helper;

import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import org.apache.commons.codec.binary.Base64;
import org.jboss.resteasy.jose.jws.JWSBuilder;
import org.jboss.resteasy.jose.jws.JWSInput;
import org.jboss.resteasy.jose.jws.crypto.RSAProvider;
import org.jboss.resteasy.jwt.JsonSerialization;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

import br.org.crea.commons.models.commons.TokenUser;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;

import com.google.gson.Gson;



public class TokenHelper<T> {	
	
	private Class<T> classe;
	
	@Inject	TokenUser newToken;
	
	public void setGenericArgument(Class<T> classe) {
		this.classe = classe;
	}

	public TokenUser generateTokenWith(UserFrontDto dto) throws Exception {
	
		KeyPair keyPair = KeyPairGenerator.getInstance("RSA").generateKeyPair();

	
		Gson gson = new Gson();
		String json = gson.toJson(dto);

		String token = new JWSBuilder()
								.contentType(MediaType.TEXT_PLAIN_TYPE)
								.content(json, MediaType.TEXT_PLAIN_TYPE).rsa256(keyPair.getPrivate());


		newToken.setPrivateKey(token);
		newToken.setChavePublica(converteChavePublicaParaString(keyPair.getPublic()));

		return newToken;
		
		
	}	


	
	public T recuperaConteudoDo(String tokenGerado) throws Exception {
		if (classe == null) {
			throw new Exception("O tipo do argumento não foi setado.");
		}
		
		JWSInput input = new JWSInput(tokenGerado, ResteasyProviderFactory.getInstance());
		byte[] content = input.getContent();
		T retorno = JsonSerialization.fromBytes(classe, content);
		return retorno;
	}
	
	public Boolean validaTokenCom(String token, String chavePublica) throws GeneralSecurityException {
		JWSInput input = new JWSInput(token, ResteasyProviderFactory.getInstance());		
		return RSAProvider.verify(input, converteStringParaChavePublica(chavePublica));
	}
	
	private String converteChavePublicaParaString(PublicKey chavePublica) throws NoSuchAlgorithmException, InvalidKeySpecException  {		
		KeyFactory fact = KeyFactory.getInstance("RSA");
	    X509EncodedKeySpec spec = fact.getKeySpec(chavePublica,X509EncodedKeySpec.class);
	    
	    return Base64.encodeBase64String(spec.getEncoded());
	}
	
	private PublicKey converteStringParaChavePublica(String chavePublica) throws GeneralSecurityException {		
	    byte[] data = Base64.decodeBase64(chavePublica);
	    X509EncodedKeySpec spec = new X509EncodedKeySpec(data);
	    KeyFactory fact = KeyFactory.getInstance("RSA");
	    return fact.generatePublic(spec);
	}

}
