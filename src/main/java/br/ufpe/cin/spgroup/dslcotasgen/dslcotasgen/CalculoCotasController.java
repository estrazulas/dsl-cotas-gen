package br.ufpe.cin.spgroup.dslcotasgen.dslcotasgen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.ufpe.cin.spgroup.dslcotasgen.dslcotasgen.jpa.CandidatosDao;
import br.ufpe.cin.spgroup.dslcotasgen.dslcotasgen.model.Arredondamento;
import br.ufpe.cin.spgroup.dslcotasgen.dslcotasgen.model.Candidato;
import br.ufpe.cin.spgroup.dslcotasgen.dslcotasgen.model.CategoriaCota;
import br.ufpe.cin.spgroup.dslcotasgen.dslcotasgen.model.LeiDeCota;
import br.ufpe.cin.spgroup.dslcotasgen.dslcotasgen.model.util.CategoriaCotaUtil;
import br.ufpe.cin.spgroup.dslcotasgen.dslcotasgen.model.util.LeiLoader;

@RestController
public class CalculoCotasController {

	@Autowired
	private LeiLoader leiUtil;

	@Autowired
	private CandidatosDao candidatosDao;

	Logger logger = LoggerFactory.getLogger(CalculoCotasController.class);

	@GetMapping("/dsl-cotas/quadro-vagas/{versao}/{quantidade}")
	public Map<String, Integer> getQuadroVagas(@PathVariable String versao, @PathVariable Integer quantidade) {
		return calculaQuadroVagas(versao, quantidade);
	}

	@PostMapping("/dsl-cotas/aprova-candidatos/{versao}/{quantidade}")
	public List<Candidato> aprovaCandidatos(@PathVariable String versao, @PathVariable Integer quantidade,
			@RequestBody List<Candidato> candidatos) {
		
		candidatosDao.saveAll(candidatos);

		Map<String, Integer> calculaQuadroVagas = calculaQuadroVagas(versao, quantidade);

		List<String> quadroVagasLista = new ArrayList<String>(calculaQuadroVagas.keySet());

		long codigoCurso = candidatos.get(0).getCodigoCurso();

		for (String categoria : quadroVagasLista) {
			Integer quantidadeAprovar = calculaQuadroVagas.get(categoria);
			int quantidadeAprovados = candidatosDao.aprovaCandidatosByCategoriaInscricao(categoria, codigoCurso,
					quantidadeAprovar);
			logger.info(
					"Aprovados " + quantidadeAprovados + " na categoria " + categoria + " do curso  " + codigoCurso);
		}

		return candidatosDao.findCandidatoByCodigoCurso(codigoCurso);

	}

	@GetMapping("/dsl-cotas/get-candidatos/")
	public List<Candidato> getCandidatos() {
		return candidatosDao.findCandidatoByCodigoCurso(123456);
	}

	@GetMapping("/dsl-cotas/ordem-prioridade/{versao}/")
	public Map<String, String> retornaOrdemPrioridade(@PathVariable String versao) {
		try {
			LeiDeCota lei = leiUtil.getLeiCota(versao);
			return lei.getOrdemprioridade().getCategorias();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Map<String, Integer> calculaQuadroVagas(String versao, Integer quantidade) {
		HashMap<String, Integer> hashMap = new HashMap<String, Integer>();

		try {
			LeiDeCota lei = leiUtil.getLeiCota(versao);

			Arredondamento formaArredondamento = lei.getDadosGerais().getArredondamento();

			CategoriaCota categoriaPai = lei.getDistribuicao().getCategoria();

			CategoriaCotaUtil.insereConfiguracoesReserva(categoriaPai, lei.getConfiguracoes());

			defineAlocacaoVagas(quantidade, categoriaPai, formaArredondamento, hashMap);

			return hashMap;

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return hashMap;
	}

	private void defineAlocacaoVagas(Integer quantidade, CategoriaCota categoria, Arredondamento formaArredondamento,
			HashMap<String, Integer> quadroVagas) {
		if (categoria.getCategoriaPai() == null) {
			categoria.setQuantidadeAlocada(quantidade);
		} else {
			categoria.setQuantidadeAlocada(formaArredondamento.defineAlocacaoPeloPercentual(categoria));
		}
		List<CategoriaCota> categorias = categoria.getCategorias();

		if (categorias.isEmpty()) {
			quadroVagas.put(categoria.getSigla(), categoria.getQuantidadeAlocada());
		}
		for (CategoriaCota categoriaCota : categorias) {
			defineAlocacaoVagas(quantidade, categoriaCota, formaArredondamento, quadroVagas);
		}

	}

	private ArrayList<Candidato> baseCandidatosTeste() {
		Candidato teste = new Candidato(1, 1000123451, 123456, "CLA", "CLAG");
		Candidato teste2 = new Candidato(2, 1000123452, 123456, "CLA", "EP_RI_PPI");
		Candidato teste3 = new Candidato(3, 1000123453, 123456, "CLA", "CLAG");
		Candidato teste4 = new Candidato(4, 1000123454, 123456, "CLA", "EP_RI_PPI");
		Candidato teste5 = new Candidato(5, 1000123455, 123456, "CLA", "CLAG");
		Candidato teste6 = new Candidato(6, 1000123456, 123456, "CLA", "EP_RS_PPI");
		Candidato teste7 = new Candidato(7, 1000123457, 123456, "CLA", "EP_RI_PPI");
		Candidato teste8 = new Candidato(8, 1000123458, 123456, "CLA", "CLAG");
		Candidato teste9 = new Candidato(9, 1000123459, 123456, "CLA", "EP_RI_PPI");

		ArrayList<Candidato> arrayList = new ArrayList<Candidato>();
		arrayList.add(teste);
		arrayList.add(teste2);
		arrayList.add(teste3);
		arrayList.add(teste4);
		arrayList.add(teste5);
		arrayList.add(teste6);
		arrayList.add(teste7);
		arrayList.add(teste8);
		arrayList.add(teste9);
		return arrayList;
	}

}
