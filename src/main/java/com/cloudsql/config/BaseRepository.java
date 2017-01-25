package com.cloudsql.config;

import javax.persistence.EntityManager;

public class BaseRepository {

	protected EntityManager em;

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	protected void begin() {
		em.getTransaction().begin();
	}

	protected void commit() {
		em.getTransaction().commit();
	}

	protected void close() {
		em.close();
	}

}
