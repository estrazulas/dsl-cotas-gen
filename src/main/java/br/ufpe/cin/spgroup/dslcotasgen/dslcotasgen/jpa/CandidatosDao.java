package br.ufpe.cin.spgroup.dslcotasgen.dslcotasgen.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.ufpe.cin.spgroup.dslcotasgen.dslcotasgen.model.Candidato;

@Repository
public class CandidatosDao {

	@PersistenceContext
    private EntityManager entityManager;
	
    public List<Candidato> findCandidatosByCategoriaInscricao(String categoriaInscricao, int limit) {
        return entityManager.createQuery("SELECT c FROM Candidato c ORDER BY c.classificacao",
        		Candidato.class).setMaxResults(limit).getResultList();
    }

}
