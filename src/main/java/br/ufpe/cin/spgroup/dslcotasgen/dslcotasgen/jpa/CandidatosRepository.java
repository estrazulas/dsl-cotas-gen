package br.ufpe.cin.spgroup.dslcotasgen.dslcotasgen.jpa;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ufpe.cin.spgroup.dslcotasgen.dslcotasgen.model.Candidato;
 

@Repository
public interface CandidatosRepository extends JpaRepository<Candidato, Long> {

	@Query("SELECT c FROM Candidatos c WHERE c.categoriaInscricao = :categoriaInscricao ORDER BY classificacao")
	List<Candidato> findCandidatoByCategoriaInscricao(String categoriaInscricao);
	
	@Query("SELECT c FROM Candidatos c WHERE c.codigoCurso = :codigoCurso ORDER BY classificacao")
	List<Candidato> findCandidatoByCodigoCurso(long codigoCurso);
}
