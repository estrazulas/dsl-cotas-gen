package br.ufpe.cin.spgroup.dslcotasgen.dslcotasgen.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "categoria")
public class CategoriaCota {
	private String sigla;
	private String reserva;
	private String descricao;
	
	private List<CategoriaCota> categorias = new ArrayList<>();
	
	public CategoriaCota() {};
	public CategoriaCota(String sigla, String reserva, String descricao) {
		super();
		this.sigla = sigla;
		this.reserva = reserva;
		this.descricao = descricao;
	}

	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	public String getReserva() {
		return reserva;
	}
	public void setReserva(String reserva) {
		this.reserva = reserva;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<CategoriaCota> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<CategoriaCota> categorias) {
		this.categorias = categorias;
	}

	public void addCategoriaDistribuicao(CategoriaCota cat) {
		this.categorias.add(cat);
	}

	public boolean temDistribuicao() {
		return !this.getCategorias().isEmpty();
	}

	
}
