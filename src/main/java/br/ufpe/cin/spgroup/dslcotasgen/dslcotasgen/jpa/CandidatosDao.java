package br.ufpe.cin.spgroup.dslcotasgen.dslcotasgen.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import br.ufpe.cin.spgroup.dslcotasgen.dslcotasgen.model.Candidato;

@Repository
public class CandidatosDao {

	@PersistenceContext
    private EntityManager entityManager;
	
    public List<Candidato> findCandidatosByCategoriaInscricao(String categoriaInscricao,long codigoCurso, int limit) {
        TypedQuery<Candidato> query = entityManager.createQuery("SELECT c FROM Candidato c WHERE c.codigoCurso =  :codigoCurso AND c.categoriaInscricao = :categoriaInscricao  ORDER BY c.classificacao",
        		Candidato.class).setMaxResults(limit);
        query.setParameter("codigoCurso", codigoCurso);
        query.setParameter("categoriaInscricao", categoriaInscricao);
		return query.getResultList();
    }
    
    public int aprovaCandidatosByCategoriaInscricao(String categoriaInscricao,long codigoCurso, int limit) {
        Query query = entityManager.createQuery("UPDATE Candidato c SET c.situacaoDeInscricao ='APV', c.situacaoDeClassificacao=:categoriaInscricao WHERE c.situacaoDeInscricao = 'CLA' AND c.codigoCurso = :codigoCurso AND c.categoriaInscricao = :categoriaInscricao").setMaxResults(limit);
        query.setParameter("codigoCurso", codigoCurso);
        query.setParameter("categoriaInscricao", categoriaInscricao);
		return query.executeUpdate();
    }

}
