package br.ufpe.cin.spgroup.dslcotasgen.dslcotasgen.model;

public class Distribuicao {

	private CategoriaCota categoria;

	
	public Distribuicao(CategoriaCota categoria) {
		this.categoria = categoria;
	}

	public CategoriaCota getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaCota categoria) {
		this.categoria = categoria;
	}
	
	
}
