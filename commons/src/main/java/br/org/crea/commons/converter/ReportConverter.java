package br.org.crea.commons.converter;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.codec.binary.Base64;

import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

public class ReportConverter {
	
	@Inject HttpClientGoApi httpGoApi;
	
	public List<Map<String, Object>> toMapJrBeanCollection(Object dto) throws IllegalArgumentException, IllegalAccessException {
		
		Map<String, Object> params = new HashMap<String, Object>();
		List<Map<String, Object>> listParams = new ArrayList<>();
		InputStream inputAssinatura  = null;
		
		try {
			Field[] reportProperties = dto.getClass().getDeclaredFields();
			
			for (Field propertie : reportProperties) {
				
				propertie.setAccessible(true);
				if(propertie.getName().equals("assinatura")) {
					if(propertie.get(dto) != null){
						inputAssinatura = getImageAssinatura(propertie.get(dto));
					} 
				}
				
				if(inputAssinatura != null && propertie.getName().equals("inputAssinatura")) {
					params.put(propertie.getName(), inputAssinatura);
				} else {
					params.put(propertie.getName(), propertie.get(dto));
				}
			}
			listParams.add(params); 
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ReportConverter || toMapJrBeanCollection", StringUtil.convertObjectToJson(dto), e);
		}

		return listParams;
	}
	
	public InputStream getImageAssinatura(Object object) throws IllegalArgumentException, IllegalAccessException {
		
		String assinaturaBase64 = (String)object; 
		
		InputStream inputAssinatura = new ByteArrayInputStream(Base64.decodeBase64(assinaturaBase64.getBytes()));
		return inputAssinatura;
	}
}
