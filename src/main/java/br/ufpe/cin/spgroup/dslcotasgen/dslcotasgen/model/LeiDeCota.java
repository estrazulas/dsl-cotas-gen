package br.ufpe.cin.spgroup.dslcotasgen.dslcotasgen.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "lei")
public class LeiDeCota {

	public LeiDeCota() {
	}

	@JsonProperty(value = "geral")
	private DadosGerais dadosGerais;

	@JsonProperty(value = "configuracoes")
	private Configuracoes configuracoes;

	@JsonProperty(value = "distribuicao")
	private Distribuicao distribuicao;

	@JsonProperty(value = "ordemprioridade")
	private OrdemPrioridade ordemprioridade;

	public LeiDeCota(DadosGerais dadosGerais, Distribuicao distribuicao) {
		super();
		this.dadosGerais = dadosGerais;
		this.distribuicao = distribuicao;
	}

	public LeiDeCota(DadosGerais dadosGerais, OrdemPrioridade ordemprioridade) {
		this.dadosGerais = dadosGerais;
		this.ordemprioridade = ordemprioridade;
	}

	public LeiDeCota(DadosGerais dadosGerais, Configuracoes configuracoes) {
		this.dadosGerais = dadosGerais;
		this.configuracoes = configuracoes;
	}

	public LeiDeCota(DadosGerais dadosGerais) {
		this.dadosGerais = dadosGerais;
	}

	public Configuracoes getConfiguracoes() {
		return configuracoes;
	}

	public void setConfiguracoes(Configuracoes configuracoes) {
		this.configuracoes = configuracoes;
	}

	public Distribuicao getDistribuicao() {
		return distribuicao;
	}

	public void setDistribuicao(Distribuicao distribuicao) {
		this.distribuicao = distribuicao;
	}

	public OrdemPrioridade getOrdemprioridade() {
		return ordemprioridade;
	}

	public void setOrdemprioridade(OrdemPrioridade ordemprioridade) {
		this.ordemprioridade = ordemprioridade;
	}

	public DadosGerais getDadosGerais() {
		return dadosGerais;
	}

	public void setDadosGerais(DadosGerais dadosGerais) {
		this.dadosGerais = dadosGerais;
	}

}
