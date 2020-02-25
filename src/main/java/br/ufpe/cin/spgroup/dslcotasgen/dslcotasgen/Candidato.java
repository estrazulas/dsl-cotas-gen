package br.ufpe.cin.spgroup.dslcotasgen.dslcotasgen;

public class Candidato {

	private int classificacao;
	
	private long codigoInscricao;
	
	private String situacaoDeInscricao;
	
	private String categoriaInscricao;
	
	private String situacaoDeClassificacao;

	public Candidato() {
		
	}
	
	public Candidato(int classificacao, long codigoInscricao, String situacaoDeInscricao, String categoriaInscricao) {
		this.classificacao = classificacao;
		this.codigoInscricao = codigoInscricao;
		this.situacaoDeInscricao = situacaoDeInscricao;
		this.categoriaInscricao = categoriaInscricao;
	}

	public int getClassificacao() {
		return classificacao;
	}

	public void setClassificacao(int classificacao) {
		this.classificacao = classificacao;
	}

	public long getCodigoInscricao() {
		return codigoInscricao;
	}

	public void setCodigoInscricao(long codigoInscricao) {
		this.codigoInscricao = codigoInscricao;
	}

	public String getSituacaoDeInscricao() {
		return situacaoDeInscricao;
	}

	public void setSituacaoDeInscricao(String situacaoDeInscricao) {
		this.situacaoDeInscricao = situacaoDeInscricao;
	}

	public String getCategoriaInscricao() {
		return categoriaInscricao;
	}

	public void setCategoriaInscricao(String categoriaInscricao) {
		this.categoriaInscricao = categoriaInscricao;
	}

	public String getSituacaoDeClassificacao() {
		return situacaoDeClassificacao;
	}

	public void setSituacaoDeClassificacao(String situacaoDeClassificacao) {
		this.situacaoDeClassificacao = situacaoDeClassificacao;
	}
	
	
}
