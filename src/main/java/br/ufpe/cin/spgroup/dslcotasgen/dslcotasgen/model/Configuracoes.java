package br.ufpe.cin.spgroup.dslcotasgen.dslcotasgen.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

public class Configuracoes {

	private Map<String,String> configuracoes = new HashMap<String,String>();
	
	public Configuracoes() {
	}
	
    @JsonAnySetter
    public void setConfiguracoes(String key, String value){
    	configuracoes.put(key, value);
    }

	@JsonAnyGetter
	public Map<String, String> getConfiguracoes() {
		return configuracoes;
	}
}
