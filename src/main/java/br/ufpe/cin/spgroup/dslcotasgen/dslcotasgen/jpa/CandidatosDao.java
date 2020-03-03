package br.ufpe.cin.spgroup.dslcotasgen.dslcotasgen.jpa;

import java.util.Iterator;
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

	public List<Candidato> findCandidatosByCategoriaInscricao(String categoriaInscricao, long codigoCurso, int limit) {
		TypedQuery<Candidato> query = entityManager.createQuery(
				"SELECT c FROM Candidato c WHERE c.codigoCurso =  :codigoCurso AND c.categoriaInscricao = :categoriaInscricao  ORDER BY c.classificacao",
				Candidato.class).setMaxResults(limit);
		query.setParameter("codigoCurso", codigoCurso);
		query.setParameter("categoriaInscricao", categoriaInscricao);
		return query.getResultList();
	}

	@Transactional
	public int aprovaCandidatosByCategoriaInscricao(String categoriaAmpla, String categoriaInscricao, long codigoCurso,
			int qtdAprovar) {
		
		boolean aprovarAmpla = (categoriaAmpla.equals(categoriaInscricao));
		// seleciona candidatos pela ordem a aprovar
		String sqlSelecaoCandidatos = "SELECT c FROM Candidato c WHERE c.classificacao > 0 AND c.situacaoDeInscricao = 'CLA' AND c.codigoCurso =  :codigoCurso ";

		// se for ampla deve selecionar todos os candidatos pela classificação
		if (!aprovarAmpla) {
			sqlSelecaoCandidatos =	sqlSelecaoCandidatos.concat("  AND c.categoriaInscricao = :categoriaInscricao ");
		}

		sqlSelecaoCandidatos.concat("ORDER BY c.classificacao ");

		TypedQuery<Candidato> query = entityManager.createQuery(sqlSelecaoCandidatos, Candidato.class)
				.setMaxResults(qtdAprovar);

		query.setParameter("codigoCurso", codigoCurso);
		
		if (!aprovarAmpla) {
			query.setParameter("categoriaInscricao", categoriaInscricao);
		}

		List<Candidato> resultList = query.getResultList();

		int aprovados = 0;
		// marca aprovação na cota por candidato selecionado
		for (Candidato candidato : resultList) {
			String updateStr = "UPDATE Candidato c SET c.situacaoDeInscricao ='APV', c.situacaoDeClassificacao=:situacaoDeClassificacao WHERE c.situacaoDeInscricao = 'CLA' AND c.codigoInscricao = :codigoInscricao ";

			Query updateSql = entityManager.createQuery(updateStr);

			if (!!aprovarAmpla) {
				updateSql.setParameter("situacaoDeClassificacao", categoriaInscricao);
			} else {
				updateSql.setParameter("situacaoDeClassificacao", categoriaAmpla);
			}

			updateSql.setParameter("codigoInscricao", candidato.getCodigoInscricao());

			int executeUpdate = updateSql.executeUpdate();
			aprovados += executeUpdate;
		}

		entityManager.flush();
		return aprovados;
	}

	@Transactional
	public void saveAll(List<Candidato> candidatos) {
		for (Candidato candidato : candidatos) {
			entityManager.persist(candidato);
		}
		entityManager.flush();
	}

	public List<Candidato> findCandidatoByCodigoCurso(long codigoCurso) {
		entityManager.clear();
		TypedQuery<Candidato> query = entityManager.createQuery(
				"SELECT c FROM Candidato c WHERE c.codigoCurso =  :codigoCurso  ORDER BY c.classificacao",
				Candidato.class);
		query.setParameter("codigoCurso", codigoCurso);
		return query.getResultList();
	}

	@Transactional
	public void limpaCandidatos(long codigoCurso) {
		Query query = entityManager.createQuery("DELETE FROM Candidato c WHERE c.codigoCurso = codigoCurso ");
		query.executeUpdate();
	}

}
