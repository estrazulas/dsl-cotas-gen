package br.ufpe.cin.spgroup.dslcotasgen.dslcotasgen;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import br.ufpe.cin.spgroup.dslcotasgen.dslcotasgen.model.Candidato;


	public class CandidatosMapper implements RowMapper<Candidato>{

		@Override
		public Candidato mapRow(ResultSet rs, int rowNum) throws SQLException {
			Candidato novoCurso = new Candidato();
			novoCurso.setCodigoInscricao(rs.getLong("codigoInscricao"));
			novoCurso.setCodigoCurso(rs.getLong("codigoCurso"));
			novoCurso.setClassificacao(rs.getInt("classificacao"));
			novoCurso.setSituacaoDeInscricao(rs.getString("situacaoDeInscricao"));
			novoCurso.setSituacaoDeClassificacao(rs.getString("situacaoDeClassificacao"));
			novoCurso.setCategoriaInscricao(rs.getString("categoriaInscricao"));
			return novoCurso;
		}

	}
