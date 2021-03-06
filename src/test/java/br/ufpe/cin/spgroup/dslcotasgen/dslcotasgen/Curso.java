package br.ufpe.cin.spgroup.dslcotasgen.dslcotasgen;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Curso {

	private String processoSeletivo;
	private String nomeCurso;
	private Long idCurso;
	private int quantidadeVagas;
	private boolean regraNova;
	private List<QuadroVaga> quadroVagas;
	
	public String getProcessoSeletivo() {
		return processoSeletivo;
	}
	public void setProcessoSeletivo(String processoSeletivo) {
		this.processoSeletivo = processoSeletivo;
	}
	public String getNomeCurso() {
		return nomeCurso;
	}
	public void setNomeCurso(String nomeCurso) {
		this.nomeCurso = nomeCurso;
	}
	public Long getIdCurso() {
		return idCurso;
	}
	public void setIdCurso(Long idCurso) {
		this.idCurso = idCurso;
	}
	public int getQuantidadeVagas() {
		return quantidadeVagas;
	}
	public void setQuantidadeVagas(int quantidadeVagas) {
		this.quantidadeVagas = quantidadeVagas;
	}
	public boolean isRegraNova() {
		return regraNova;
	}
	public void setRegraNova(boolean regraNova) {
		this.regraNova = regraNova;
	}
	@Override
	public String toString() {
		return "Curso [" + processoSeletivo + ", curso=" + nomeCurso + ", id=" + idCurso
				+ ", vagas=" + quantidadeVagas + "]";
	}
	public void setQuadroVagas(List<QuadroVaga> list) {
		this.quadroVagas = list;
		
	}
	public Map<String,Integer> getQuadroVagas() {
		HashMap<String, Integer> mapQuadroVagas = new HashMap<>();
		if(quadroVagas == null)
		{
			return mapQuadroVagas;
		}
		for (QuadroVaga quadroVaga : quadroVagas) {
			mapQuadroVagas.put(quadroVaga.getSigla(), quadroVaga.getQuantidade());
		}
		return mapQuadroVagas;
	}
	
}
