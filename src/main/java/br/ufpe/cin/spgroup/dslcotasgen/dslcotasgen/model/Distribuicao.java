package br.ufpe.cin.spgroup.dslcotasgen.dslcotasgen.model;

import java.util.List;

public class Distribuicao {

	private CategoriaCota categoria;

	public Distribuicao()
	{
		
	}
	public Distribuicao(CategoriaCota categoria) {
		this.categoria = categoria;
	}

	public CategoriaCota getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaCota categoria) {
		this.categoria = categoria;
	}
	public String getCategoriaAmpla() {
		if(!categoria.getCategorias().isEmpty()) {
			List<CategoriaCota> categorias = categoria.getCategorias();
			CategoriaCota categoriaCota = categorias.get((categorias.size()-1));
			return categoriaCota.getSigla();
		}
		return "";
	}
	
	
}
