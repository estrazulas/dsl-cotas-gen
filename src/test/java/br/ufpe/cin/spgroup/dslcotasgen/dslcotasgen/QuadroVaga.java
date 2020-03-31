package br.ufpe.cin.spgroup.dslcotasgen.dslcotasgen;

public class QuadroVaga {

	private String sigla;
	private Long quantidade;
	public QuadroVaga(String sigla, Long quantidade) {
		super();
		this.sigla = sigla;
		this.quantidade = quantidade;
	}
	public Long getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Long quantidade) {
		this.quantidade = quantidade;
	}
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	
}
