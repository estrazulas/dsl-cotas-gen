package br.ufpe.cin.spgroup.dslcotasgen.dslcotasgen.model.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import br.ufpe.cin.spgroup.dslcotasgen.dslcotasgen.model.LeiDeCota;

@Component
public class LeiLoader {
	
	Logger logger = LoggerFactory.getLogger(LeiLoader.class);

	@Autowired
	ResourceLoader resourceLoader;
	
	public LeiDeCota getLeiCota(String versao) throws JsonMappingException, JsonProcessingException {
		LeiDeCota lei = null;
				
		ObjectMapper objectMapper = new ObjectMapper();
	    
	    SimpleModule module = new SimpleModule();
	    objectMapper.registerModule(module);
	    
		objectMapper.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);
	
		String json = this.loadJsonLei(versao);
		
		if(!json.isEmpty()) {
			lei = objectMapper.readValue(json, LeiDeCota.class);
			
			String jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(lei);
			logger.info(jsonString);
		}
		return lei;
	}
	
	private String loadJsonLei(String versao) {
		String str= "";
		
		Resource res = loadArquivoLei(versao);
	
		try {
			InputStream inputStream = res.getInputStream();
			InputStreamReader isReader = new InputStreamReader(inputStream);
			BufferedReader br = new BufferedReader(isReader);
			StringBuffer sb = new StringBuffer();
			
			while ((str = br.readLine()) != null) {
				sb.append(str);
			}
			
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return str;
	
	}
	
	private Resource loadArquivoLei(String versao) {
		return resourceLoader.getResource("classpath:templates/" + versao + ".json");
	}
}

