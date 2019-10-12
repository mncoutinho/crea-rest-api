package br.org.crea.commons.util;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.UUID;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import org.apache.commons.codec.binary.Base64;
import org.jboss.resteasy.jose.Base64Url;
import org.jboss.resteasy.jose.jws.JWSBuilder;
import org.jboss.resteasy.jwt.JsonSerialization;

import br.org.crea.commons.models.commons.TokenUser;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.service.HttpClientGoApi;

import com.google.gson.Gson;

public class TokenUtil {
	
	@Inject
	static HttpClientGoApi httpGoApi;
	
	
	public static TokenUser generateTokenWith(UserFrontDto dto)  {
		
		
		TokenUser newToken = new TokenUser();
		try {
			KeyPair keyPair = KeyPairGenerator.getInstance("RSA").generateKeyPair();
			
			Gson gson = new Gson();
			String json = gson.toJson(dto);

			String token = new JWSBuilder()
									.contentType(MediaType.TEXT_PLAIN_TYPE)
									.content(json, MediaType.TEXT_PLAIN_TYPE).rsa256(keyPair.getPrivate());


			newToken.setId(UUID.randomUUID().toString().replace("-", "").substring(12));
			newToken.setPrivateKey(token);
			newToken.setChavePublica(converteChavePublicaParaString(keyPair.getPublic()));

		} catch (Throwable e) {
			httpGoApi.geraLog("TokenUtil || generateTokenWith", StringUtil.convertObjectToJson(dto), e);
		}
		return newToken;
	
		
	}	
	
	public static UserFrontDto recuperaDto(TokenUser token) {
		
		UserFrontDto dto = new UserFrontDto();
		
		try {
			String[] parts = token.getPrivateKey().split("\\.");
			String encodedContent = parts[1];
			byte[] content = Base64Url.decode(encodedContent);
			return JsonSerialization.fromBytes(UserFrontDto.class, content);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return dto;
	}
	
	private static String converteChavePublicaParaString(PublicKey chavePublica) throws NoSuchAlgorithmException, InvalidKeySpecException  {		
		
		KeyFactory fact = KeyFactory.getInstance("RSA");
	    X509EncodedKeySpec spec = fact.getKeySpec(chavePublica,X509EncodedKeySpec.class);
	    
	    return Base64.encodeBase64String(spec.getEncoded());
	}

}
