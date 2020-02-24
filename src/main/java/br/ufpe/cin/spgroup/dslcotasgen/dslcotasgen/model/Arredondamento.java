package br.ufpe.cin.spgroup.dslcotasgen.dslcotasgen.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Arredondamento {

	@JsonProperty("ceil")
	CEIL,
	
	@JsonProperty("round")
	ROUND;

	public Integer defineAlocacaoPeloPercentual(CategoriaCota categoria) {
		
		if(categoria.getReservaValor()==null || categoria.getReservaValor() == 0) {
			if(categoria.getReserva().contains("RESTANTE_VAGAS")) {
				return categoria.getCategoriaPai().getQuantidadeAlocada()-categoria.getCategoriaPai().getQuantidadeSomaFilhas();
			}else {
				return 0;
			}
		}
		
		if(this == CEIL) {
			double quantidade = Math.ceil((categoria.getCategoriaPai().getQuantidadeAlocada()*categoria.getReservaValor())/100);
			return (int) quantidade;
		}else {
			double quantidade = Math.round((categoria.getCategoriaPai().getQuantidadeAlocada()*categoria.getReservaValor())/100);
			return (int) quantidade;
		}
	}
	
}
