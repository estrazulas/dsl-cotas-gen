package br.ufpe.cin.spgroup.dslcotasgen.dslcotasgen.model.util;

import java.util.ArrayList;
import java.util.List;

import br.ufpe.cin.spgroup.dslcotasgen.dslcotasgen.model.CategoriaCota;
import br.ufpe.cin.spgroup.dslcotasgen.dslcotasgen.model.Configuracoes;

public class CategoriaCotaUtil {

	public static void insereConfiguracoesReserva(CategoriaCota categoria, Configuracoes configs) {
		transformaValoresConfiguracoes(categoria, configs);
	}

	private static void transformaValoresConfiguracoes(CategoriaCota categoria, Configuracoes configs) {
		if(categoria.getReserva() != null && !categoria.getReserva().isEmpty()) {
			List<String> list = new ArrayList<String>(configs.getConfiguracoes().keySet());
			for (String configChave : list) {
				categoria.setReserva(categoria.getReserva().replaceAll(configChave, configs.getConfiguracoes().get(configChave)));
				try {
			       Double num = Double.parseDouble(categoria.getReserva());
			       categoria.setReservaValor(num);
				} catch (NumberFormatException e) { 
		        }			
			}
		}
		List<CategoriaCota> categorias = categoria.getCategorias();
		if(!categorias.isEmpty()) {
			for (CategoriaCota categoriaFilha : categorias) {
				categoriaFilha.setCategoriaPai(categoria);
				transformaValoresConfiguracoes(categoriaFilha, configs);
			}
		}
	}

}
