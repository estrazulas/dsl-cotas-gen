package br.ufpe.cin.spgroup.dslcotasgen.dslcotasgen.model;

import java.util.HashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "candidatos", uniqueConstraints = @UniqueConstraint(columnNames = {"codigoCurso", "classificacao"}))
public class Candidato {

	private int classificacao;
	
	@Id
	@Column(unique = true)
	private long codigoInscricao;
	
	private long codigoCurso;
	
	private String situacaoDeInscricao;
		
	private String categoriaInscricao;
	
	private String situacaoDeClassificacao;
	
	private String situacaoDeClassificacaoIngresso;

	public Candidato() {
		
	}
	
	public Candidato(int classificacao, long codigoInscricao, long codigoCurso, String situacaoDeInscricao, String categoriaInscricao) {
		this.classificacao = classificacao;
		this.codigoInscricao = codigoInscricao;
		this.situacaoDeInscricao = situacaoDeInscricao;
		this.categoriaInscricao = categoriaInscricao;
		this.codigoCurso =  codigoCurso;
	}

	public long getCodigoCurso() {
		return codigoCurso;
	}
	
	public void setCodigoCurso(long codigoCurso) {
		this.codigoCurso = codigoCurso;
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

	@Override
	public String toString() {
		return "Candidato[" + codigoInscricao + "] classificacao=" + classificacao +  ", codigoCurso="
				+ codigoCurso + ", situacaoDeInscricao=" + situacaoDeInscricao + ", categoriaInscricao="
				+ categoriaInscricao + ", situacaoDeClassificacao=" + situacaoDeClassificacao + "";
	}
	
	public void setSituacaoDeClassificacaoIngresso(String situacaoDeClassificacaoIngresso) {
		this.situacaoDeClassificacaoIngresso = situacaoDeClassificacaoIngresso;
	}
	public String getSituacaoDeClassificacaoIngresso(HashMap<Long, String> categoriaRealIngresso) {
		this.situacaoDeClassificacaoIngresso = categoriaRealIngresso.get(this.getCodigoInscricao());
		return situacaoDeClassificacaoIngresso;
	}

	public String confereCota() {
		
		
		if(this.situacaoDeClassificacao!= null && this.situacaoDeClassificacaoIngresso != null && !this.situacaoDeClassificacao.isEmpty() 
				&& ! this.getSituacaoDeClassificacaoIngresso().isEmpty()){
			if(this.situacaoDeClassificacao.equals(this.situacaoDeClassificacaoIngresso)) {
				return "OK";
			}else {
				return "Divergência:"+situacaoDeClassificacao+" esperado "+situacaoDeClassificacaoIngresso;
			}
		}else {
			return "Divergência:"+situacaoDeClassificacao+" esperado "+situacaoDeClassificacaoIngresso;
		}
			
	}

	private String getSituacaoDeClassificacaoIngresso() {
		return this.situacaoDeClassificacaoIngresso;
	}
	
}
