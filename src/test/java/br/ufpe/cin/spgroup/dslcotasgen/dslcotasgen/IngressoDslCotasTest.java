package br.ufpe.cin.spgroup.dslcotasgen.dslcotasgen;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

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
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.ufpe.cin.spgroup.dslcotasgen.dslcotasgen.model.Candidato;
import reactor.core.publisher.Flux;

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

		for (Iterator<Curso> iterator = listaCursosTeste.iterator(); iterator.hasNext();) 
		{
			
			Curso curso = (Curso) iterator.next();
			
			List<Candidato> candidatosAClassificar =null;
			
			String versao="";
			
			if (!curso.isRegraNova()) {
				versao = "Ifsc12711001";
			} else {
				versao = "Ifsc13409002";
			}
			
			candidatosAClassificar = retornaListaCandidatosPrimeiraChamada(curso.getIdCurso(),curso.isRegraNova());
			
			List<Candidato> aprovaCandidatosApi = aprovaCandidatosApi(candidatosAClassificar,versao,curso);
			
			System.out.println("Comparando "+curso.getNomeCurso()+"["+curso.getIdCurso()+"] de "+curso.getProcessoSeletivo()+" vagas["+curso.getQuantidadeVagas()+"]");
			for (Candidato candidato : aprovaCandidatosApi) {
				System.out.println(candidato.toString());
			}
			//comparar resultado por classificação e situação de classificação
		}

		assertEquals(listaCursosTeste.isEmpty(), false);
	}

	private List<Candidato> retornaListaCandidatosPrimeiraChamada(Long idCurso, boolean regraNova) {
		String montaSiglasInscricao = (regraNova)?caseRegraNova():caseRegraAntiga();
		String consultaCandidatos = ""
				+ "SELECT \n" + 
				"    'CLA' AS situacaoDeInscricao,\n" + 
				"    null as situacaoDeClassificacao,\n" + 
				"    idCandidato AS codigoInscricao,\n" + 
				"    curso as codigoCurso,\n" + 
				"    classificacao,\n" + 
				montaSiglasInscricao+
				"	 \n" + 
				"FROM\n" + 
				"    candidatos can\n" + 
				"WHERE\n" + 
				"    curso = "+idCurso+"  and classificacao > 0\n" + 
				"ORDER BY can.classificacao";
		
		return this.jdbcTemplate.query(consultaCandidatos, new CandidatosMapper());
	}
	
	private String caseRegraAntiga() {
		return "	CASE\n" + 
		"			WHEN cotaRendaInferior = \"S\" AND cotaPPI=\"S\" THEN \"AAEPRIPPI\"\n" + 
		"			WHEN cotaRendaInferior = \"S\" AND cotaPPI=\"N\"  THEN \"AAEPRINPPI\"\n" + 
		"            WHEN cotaRendaInferior = \"N\" AND cotaPPI=\"S\"  THEN \"AAEPRSPPI\"\n" + 
		"			WHEN cotaEscolaPublica = \"S\" AND cotaRendaInferior = \"N\" AND cotaPPI=\"N\"  THEN \"AAEPRSNPPI\"\n" + 
		"            WHEN cotaEscolaPublica = \"N\" THEN \"CLAG\"\n" + 
		"			ELSE \"??\"\n" + 
		"		END as categoriaInscricao\n";
	}
	
	private String caseRegraNova() {
		return "	CASE\n" + 
		"			WHEN cotaRendaInferior = \"S\" AND cotaPPI=\"S\"  THEN \"AAEPRIPPI\"\n" + 
		"			WHEN cotaRendaInferior = \"S\" AND cotaPPI=\"N\"  THEN \"AAEPRINPPI\"\n" + 
		"           WHEN cotaRendaInferior = \"N\" AND cotaPPI=\"S\"  THEN \"AAEPRSPPI\"\n" + 
		"			WHEN cotaEscolaPublica = \"S\" AND cotaRendaInferior = \"N\" AND cotaPPI=\"N\"  THEN \"AAEPRSNPPI\"\n" + 
		"           WHEN cotaEscolaPublica = \"N\" THEN \"CLAG\"\n" + 
		"			ELSE \"??\"\n" + 
		"		END as categoriaInscricao\n";
	}

	public List<Curso> getListaCursosTeste() {
		String sqlCursos = "SELECT ps.nome as psNome, ps.regraNovaCotas as regraCotas, cur.idcurso, cur.nome as curNome, cur.vagas, ps.regraNovaCotas From \n"
				+ "ps_processo_seletivo ps\n" + "inner join cursos cur on( cur.idprocesso = ps.idprocesso )\n"
				+ "where cur.idcurso = 6570 and cota_escola_publica >0  and\n"
				+ "ps.idprocesso  in (388,389,390,424,458,463,586,605,606,661,730,739,745,9771,9772,9827) \n"
				+ "order by ps.idprocesso, cur.idcurso\n";
		return this.jdbcTemplate.query(sqlCursos, new CursosMapper());
	}

	
	private List<Candidato> aprovaCandidatosApi(List<Candidato> inscritos, String versao, Curso curso) throws JsonMappingException, JsonProcessingException {
		Flux<Candidato> flux  = client3.post().uri("aprova-candidatos/"+versao+"/"+curso.getQuantidadeVagas()).body(Flux.fromIterable(inscritos),
				Candidato.class).retrieve().bodyToFlux(Candidato.class);	
		return flux.collectList().block();
	}
	
}
