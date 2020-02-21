package br.ufpe.cin.spgroup.dslcotasgen.dslcotasgen;


import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

import br.ufpe.cin.spgroup.dslcotasgen.dslcotasgen.model.Arredondamento;
import br.ufpe.cin.spgroup.dslcotasgen.dslcotasgen.model.CategoriaCota;
import br.ufpe.cin.spgroup.dslcotasgen.dslcotasgen.model.Configuracoes;
import br.ufpe.cin.spgroup.dslcotasgen.dslcotasgen.model.DadosGerais;
import br.ufpe.cin.spgroup.dslcotasgen.dslcotasgen.model.Distribuicao;
import br.ufpe.cin.spgroup.dslcotasgen.dslcotasgen.model.LeiDeCota;
import br.ufpe.cin.spgroup.dslcotasgen.dslcotasgen.model.OrdemPrioridade;
import br.ufpe.cin.spgroup.dslcotasgen.dslcotasgen.model.jsonutil.DistribuicaoSerializer;
class JsonMappingTest {


    
   
	@Test
	void testSerializa() throws JsonProcessingException {
	    ObjectMapper objectMapper = new ObjectMapper();
	    objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
		LeiDeCota lei = new LeiDeCota(new DadosGerais("IFSC_13409_002","003 Lei 13.409 portaria nr 18/2012/MEC (atual)",Arredondamento.CEIL));
		String jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(lei);
		System.out.println(jsonString);
	}
	
	@Test
	void testDeserializa() throws JsonMappingException, JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);
		String json="{\n" + 
				"  \"lei\" : {\n" + 
				"    \"geral\" : {\n" + 
				"      \"codigo\" : \"IFSC_13409_002\",\n" + 
				"      \"descricao\" : \"003 Lei 13.409 portaria nr 18/2012/MEC (atual)\",\n" + 
				"      \"arredondamento\" : \"ceil\"\n" + 
				"    }\n" + 
				"  }\n" + 
				"}";
		
		LeiDeCota lei = objectMapper.readValue(json, LeiDeCota.class);
		
		assertNotNull(lei);
		
	}
	@Test
	void testSerializaConfiguracoes() throws JsonProcessingException {
	    ObjectMapper objectMapper = new ObjectMapper();
	    
	    objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
	    
	    Configuracoes configs = new Configuracoes();
	    configs.getConfiguracoes().put("PercentualEP", "50.0");
	    configs.getConfiguracoes().put("PercentualPPI", "15.7");
	    configs.getConfiguracoes().put("PercentualPCD", "7.69");
	    
		LeiDeCota lei = new LeiDeCota(new DadosGerais("IFSC_13409_002","003 Lei 13.409 portaria nr 18/2012/MEC (atual)",Arredondamento.CEIL),configs);
		
		
		String jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(lei);
		System.out.println(jsonString);
	}

	@Test
	void testDeserializaConfiguracoes() throws JsonProcessingException {
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);
		String json="{\n" + 
				"  \"lei\" : {\n" + 
				"    \"geral\" : {\n" + 
				"      \"codigo\" : \"IFSC_13409_002\",\n" + 
				"      \"descricao\" : \"003 Lei 13.409 portaria nr 18/2012/MEC (atual)\",\n" + 
				"      \"arredondamento\" : \"ceil\"\n" + 
				"    },\n" + 
				"    \"configuracoes\" : {\n" + 
				"      \"PercentualPPI\" : \"15.7\",\n" + 
				"      \"PercentualEP\" : \"50.0\",\n" + 
				"      \"PercentualPCD\" : \"7.69\"\n" + 
				"    }\n" + 
				"  }\n" + 
				"}";
		
		LeiDeCota lei = objectMapper.readValue(json, LeiDeCota.class);
		
		assertNotNull(lei);
	}
	
	
	@Test
	void testSerializaOrdemPrioridade() throws JsonProcessingException {
	    ObjectMapper objectMapper = new ObjectMapper();
	    
	    objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
	    OrdemPrioridade ordem = new OrdemPrioridade();
	    ordem.getCategorias().put("0", "EP_RI_PPI_PCD");
	    ordem.getCategorias().put("1", "EP_RI_NPPI_PCD");
	    ordem.getCategorias().put("2", "EP_RS_PPI_PCD");
	    ordem.getCategorias().put("3", "EP_RS_NPPI_PCD");
	    ordem.getCategorias().put("4", "EP_RI_PPI_NPCD");
	    ordem.getCategorias().put("5", "EP_RI_NPPI_NPCD");
	    ordem.getCategorias().put("6", "EP_RS_PPI_NPCD");
	    ordem.getCategorias().put("7", "EP_RS_NPPI_NPCD");
	    ordem.getCategorias().put("8", "CLAG");
	    
		LeiDeCota lei = new LeiDeCota(new DadosGerais("IFSC_13409_002","003 Lei 13.409 portaria nr 18/2012/MEC (atual)",Arredondamento.CEIL),ordem);
		
		
		String jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(lei);
		System.out.println(jsonString);
	}
	
	@Test
	void testaSerializarCategoria() throws JsonProcessingException {
	    ObjectMapper objectMapper = new ObjectMapper();
	    
	    objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
	    SimpleModule module = new SimpleModule();
	    module.addSerializer(Distribuicao.class, new DistribuicaoSerializer());
	    objectMapper.registerModule(module);
	    
		CategoriaCota pai = new CategoriaCota("TOTALVAGAS", "", "Total de vagas");
		CategoriaCota clag = new CategoriaCota("CLAG", "CLAG", "Ampla concorrência");
		CategoriaCota ep = new CategoriaCota("EP", "EP", "Escola pública");
		pai.addCategoriaDistribuicao(clag);
		pai.addCategoriaDistribuicao(ep);
		Distribuicao dis = new Distribuicao(pai);
		
		LeiDeCota lei = new LeiDeCota(new DadosGerais("IFSC_13409_002","003 Lei 13.409 portaria nr 18/2012/MEC (atual)",Arredondamento.CEIL),dis);
		String jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(lei);
		System.out.println(jsonString);
		
		
	}
}
