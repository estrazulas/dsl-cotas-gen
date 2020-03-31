package br.ufpe.cin.spgroup.dslcotasgen.dslcotasgen;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CursosMapper implements RowMapper<Curso>{

	@Override
	public Curso mapRow(ResultSet rs, int rowNum) throws SQLException {

		Curso novoCurso = new Curso();
		
		novoCurso.setIdCurso(rs.getLong("idcurso"));
		novoCurso.setProcessoSeletivo(rs.getString("psNome"));
		novoCurso.setRegraNova(rs.getString("regraNovaCotas").equals("S"));
		novoCurso.setQuantidadeVagas(rs.getInt("vagas"));
		novoCurso.setNomeCurso(rs.getString("curNome"));
		return novoCurso;
	}

}
