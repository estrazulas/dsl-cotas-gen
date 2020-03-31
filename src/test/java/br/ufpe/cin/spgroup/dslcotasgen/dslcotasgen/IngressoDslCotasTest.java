package br.ufpe.cin.spgroup.dslcotasgen.dslcotasgen;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Mono;

@ActiveProfiles("logging-test")
class IngressoDslCotasTest {

	private JdbcTemplate jdbcTemplate;

	private WebClient client3 = WebClient
			  .builder()
			    .baseUrl("http://localhost:8087/dsl-cotas/")
			    .defaultCookie("cookieKey", "cookieValue")
			    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE) 
			    .defaultUriVariables(Collections.singletonMap("url", "http://localhost:8087/dsl-cotas/get-candidatos/"))
			  .build();
	
	Logger logger = LoggerFactory.getLogger(IngressoDslCotasTest.class);
	
	 ObjectMapper mapper = new ObjectMapper();

	public IngressoDslCotasTest() {
		jdbcTemplate = new JdbcTemplate(this.mysqlDataSource());
	}

	public DataSource mysqlDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3307/coing_ingresso");
		dataSource.setUsername("root");
		dataSource.setPassword("gtigti");
		return dataSource;
	}

	@Test
	void testaQuadroVagas() throws JsonMappingException, JsonProcessingException {
		List<Curso> listaCursosTeste = getListaCursosTeste();		
		HashMap<Long, List<QuadroVaga>>  quadroVagasIngresso = getQuadroVagasIngresso();

		for (Iterator<Curso> iterator = listaCursosTeste.iterator(); iterator.hasNext();) {
			Curso curso = (Curso) iterator.next();
			curso.setQuadroVagas(quadroVagasIngresso.get(curso.getIdCurso()));

			Map<String, Integer> retornaQuadroVagasApi = null;
			if (!curso.isRegraNova()) {
				retornaQuadroVagasApi	= retornaQuadroVagasApi(curso.getQuantidadeVagas(),"Ifsc12711001");
			} else {
				retornaQuadroVagasApi 	= retornaQuadroVagasApi(curso.getQuantidadeVagas(),"Ifsc13409002");
			}
			comparaResultado(retornaQuadroVagasApi,curso);
		}

		assertEquals(listaCursosTeste.isEmpty(), false);
	}

	private void comparaResultado(Map<String, Integer> retornaQuadroVagasApi, Curso curso) {
		
		//comparar se a quantidade bate para cada cota, mostrar dados ps e curso
		
	}

	public List<Curso> getListaCursosTeste() {
		String sqlCursos = "SELECT ps.nome as psNome, ps.regraNovaCotas as regraCotas, cur.idcurso, cur.nome as curNome, cur.vagas, ps.regraNovaCotas From \n"
				+ "ps_processo_seletivo ps\n" + "inner join cursos cur on( cur.idprocesso = ps.idprocesso )\n"
				+ "where cota_escola_publica >0  and\n"
				+ "ps.idprocesso  in (388,389,390,424,458,463,586,605,606,661,730,739,745,9771,9772,9827) \n"
				+ "order by ps.idprocesso, cur.idcurso\n";
		return this.jdbcTemplate.query(sqlCursos, new CursosMapper());
	}

	public HashMap<Long, List<QuadroVaga>> getQuadroVagasIngresso() {
		List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(
				"SELECT cur.idCurso, can.situacaoDeClassificacao as siglaCota, count(can.idcandidato) as quantidadeApv From \n"
						+ "ps_processo_seletivo ps\n" + "inner join cursos cur on( cur.idprocesso = ps.idprocesso )\n"
						+ "inner join candidatos can on (can.idProcesso = ps.idProcesso and can.curso = cur.idCurso)\n"
						+ "where cota_escola_publica >0  and\n"
						+ "ps.idprocesso  in (388,389,390,424,458,463,586,605,606,661,730,739,745,9771,9772,9827) and situacao = 'APV' and chamada =1\n"
						+ "group by cur.idCurso, can.situacaoDeClassificacao");

		HashMap<Long, List<QuadroVaga>> cursoQuadro = new HashMap<>();

		for (Map<String, Object> linha : queryForList) {
			Long idCurso = (Long) linha.get("idCurso");
			List<QuadroVaga> list = cursoQuadro.get(idCurso);
			if (list == null) {
				list = new ArrayList<>();
				list.add(new QuadroVaga((String) linha.get("siglaCota"), (Long) linha.get("quantidadeApv")));
				cursoQuadro.put(idCurso, list);
			} else {
				list.add(new QuadroVaga((String) linha.get("siglaCota"), (Long) linha.get("quantidadeApv")));
			}
		}
		return cursoQuadro;

	}
	
	private Map<String, Integer> retornaQuadroVagasApi(Integer quantidade, String lei) throws JsonMappingException, JsonProcessingException {
		
		Mono<String> flux = client3.get().uri("quadro-vagas/"+lei+"/"+quantidade).retrieve().bodyToMono(String.class);
		
		 Map<String, Integer> carMap = mapper.readValue(flux.block(), new TypeReference<Map<String, Integer>>() {
	      });

		return carMap;
	}
}
