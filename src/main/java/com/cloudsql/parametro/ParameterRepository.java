package com.cloudsql.parametro;

import javax.persistence.TypedQuery;

import com.cloudsql.config.BaseRepository;

public class ParameterRepository extends BaseRepository {

	public Parameter get(String chave) {
		final TypedQuery<Parameter> query = em.createQuery("FROM Params p WHERE p.key = :key", Parameter.class);
		query.setParameter("key", chave);

		final Parameter parametro = query.getSingleResult();
		System.out.println("Param:" + parametro.getValue());
		return parametro;
	}

	public void create() {
		final Parameter parametro = new Parameter();
		parametro.setKey("PARAM_KEY");
		parametro.setValue("123KEY");
		em.persist(parametro);
	}

}
