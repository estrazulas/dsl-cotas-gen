package br.ufpe.cin.spgroup.dslcotasgen.dslcotasgen.model.jsonutil;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import br.ufpe.cin.spgroup.dslcotasgen.dslcotasgen.model.CategoriaCota;
import br.ufpe.cin.spgroup.dslcotasgen.dslcotasgen.model.Distribuicao;

@SuppressWarnings("serial")
public class DistribuicaoSerializer extends StdSerializer<Distribuicao> {

	public DistribuicaoSerializer() {this(null);}
	
	protected DistribuicaoSerializer(Class<Distribuicao> t) {
		super(t);
	}

	@Override
	public void serialize(Distribuicao value, JsonGenerator gen, SerializerProvider provider) throws IOException {
	
		CategoriaCota categoria = value.getCategoria();
		genCategoria(gen, categoria);
	}

	private void genCategoria(JsonGenerator gen, CategoriaCota categoria) throws IOException {
		
		gen.writeStartObject();
			gen.writeStringField("sigla", categoria.getSigla());
			gen.writeStringField("reserva", categoria.getReserva());
			gen.writeStringField("descricao", categoria.getDescricao());
		
			if(categoria.temDistribuicao()) {
				gen.writeFieldName("distribuicao");
				gen.writeStartArray();
					List<CategoriaCota> categorias = categoria.getCategorias();
					for (CategoriaCota categoriaCota : categorias) {
						genCategoria(gen, categoriaCota);
					}				
				gen.writeEndArray();
			}
		gen.writeEndObject();
	}

}
