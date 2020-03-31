package br.ufpe.cin.spgroup.dslcotasgen.dslcotasgen;

public class QuadroVaga {

	private String sigla;
	private Integer quantidade;
	public QuadroVaga(String sigla, Integer quantidade) {
		super();
		this.sigla = sigla;
		this.quantidade = quantidade;
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	
}
