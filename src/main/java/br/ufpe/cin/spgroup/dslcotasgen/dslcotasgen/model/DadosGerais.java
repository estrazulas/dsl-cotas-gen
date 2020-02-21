package br.ufpe.cin.spgroup.dslcotasgen.dslcotasgen.model;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "geral")
public class DadosGerais {

	private String codigo;
	private String descricao;
	private Arredondamento arredondamento;
	
	public DadosGerais() {}
	public DadosGerais(String codigo, String descricao, Arredondamento arredondamento) {
		super();
		this.codigo = codigo;
		this.descricao = descricao;
		this.arredondamento = arredondamento;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Arredondamento getArredondamento() {
		return arredondamento;
	}

	public void setArredondamento(Arredondamento arredondamento) {
		this.arredondamento = arredondamento;
	}
	
	
	
	
}
