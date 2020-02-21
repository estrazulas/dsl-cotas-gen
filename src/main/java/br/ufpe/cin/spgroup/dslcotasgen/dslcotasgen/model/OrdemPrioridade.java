package br.ufpe.cin.spgroup.dslcotasgen.dslcotasgen.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

public class OrdemPrioridade {

	private Map<String,String> categorias = new HashMap<String,String>();
	
	public OrdemPrioridade() {
	}
	
	public OrdemPrioridade(Map<String, String> categorias) {
		this.categorias = categorias;
	}


    @JsonAnySetter
    public void setCategorias(String key, String value){
    	categorias.put(key, value);
    }

	@JsonAnyGetter
	public Map<String, String> getCategorias() {
		return categorias;
	}
}
