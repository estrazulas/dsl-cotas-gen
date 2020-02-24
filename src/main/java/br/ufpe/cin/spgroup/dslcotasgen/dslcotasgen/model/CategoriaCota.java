package br.ufpe.cin.spgroup.dslcotasgen.dslcotasgen.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "categoria")
public class CategoriaCota {
	private String sigla;
	private String reserva;
	private String descricao;

	@JsonIgnore
	private Double reservaValor = 0.0;

	@JsonIgnore
	private Integer quantidadeAlocada = 0;

	@JsonIgnore
	private Integer quantidadeSomaFilhas = 0;

	@JsonIgnore
	private CategoriaCota categoriaPai;

	private List<CategoriaCota> categorias = new ArrayList<>();

	public CategoriaCota() {
	};

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

	public Double getReservaValor() {
		return reservaValor;
	}

	public void setReservaValor(Double reservaValor) {
		this.reservaValor = reservaValor;
	}

	public Integer getQuantidadeAlocada() {
		return quantidadeAlocada;
	}

	public void setQuantidadeAlocada(Integer quantidadeAlocada) {
		this.quantidadeAlocada = quantidadeAlocada;
		if(this.categoriaPai!=null)
			this.categoriaPai.somaQuantidade(quantidadeAlocada);
	}

	public CategoriaCota getCategoriaPai() {
		return categoriaPai;
	}

	public void setCategoriaPai(CategoriaCota categoriaPai) {
		this.categoriaPai = categoriaPai;
	}

	@Override
	public String toString() {
		return this.sigla.concat(" ").concat(this.getDescricao()).concat(" ").concat((this.getReserva()!=null)?this.getReserva():"-").concat(" ")
				.concat("" + this.getReservaValor()).concat(" ").concat("" + this.getQuantidadeAlocada());
	}

	public Integer getQuantidadeSomaFilhas() {
		return quantidadeSomaFilhas;
	}

	public void somaQuantidade(Integer quantidade) {
		this.quantidadeSomaFilhas += quantidade;
	}

}
