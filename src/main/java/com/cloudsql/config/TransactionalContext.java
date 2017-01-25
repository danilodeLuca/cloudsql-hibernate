package com.cloudsql.config;

import java.util.Stack;

import javax.persistence.EntityManager;

public class TransactionalContext {

	private static class InternalContext {

		private Stack<EntityManager> ems = new Stack<>();

		EntityManager getEntityManager() {
			return ems.peek();
		}

		public void beginTransaction() {
			EntityManager em = EntityManagerUtils.createEntityManager();
			em.getTransaction().begin();
			ems.push(em);
		}

		void createEm() {
			EntityManager em = EntityManagerUtils.createEntityManager();
			ems.push(em);
		}

		public void commitTransaction() {
			EntityManager em = getEntityManager();
			if (em.getTransaction().isActive()) {
				em.getTransaction().commit();
			}
			closeEntityManager();
		}

		void rollbackTransaction() {
			EntityManager em = getEntityManager();
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}

			closeEntityManager();
		}

		private void closeEntityManager() {
			EntityManager em = ems.pop();
			if (em == null || !em.isOpen()) {
			} else {
				try {
					em.close();
				} catch (RuntimeException e) {
				}
			}
		}

		private void close() {
			if (!ems.isEmpty()) {
				do {
					closeEntityManager();
				} while (!ems.isEmpty());
			}
		}
	}

	private static final ThreadLocal<InternalContext> transContext = new ThreadLocal<>();

	public static EntityManager getEntityManager() {
		if (transContext.get() == null) {
			throw new IllegalStateException("No entity manager found");
		}
		return transContext.get().getEntityManager();
	}

	public static void open() {
		transContext.set(new InternalContext());
	}

	public static void close() {
		transContext.get().close();
		transContext.remove();
	}

	public static void createEm() {
		if (!isOpen()) {
			open();
		}
		transContext.get().createEm();
	}

	public static void closeEm() {
		transContext.get().closeEntityManager();
	}

	public static void beginTransaction() {
		if (!isOpen()) {
			open();
		}
		transContext.get().beginTransaction();
	}

	public static void commitTransaction() {
		transContext.get().commitTransaction();
	}

	public static void rollbackTransaction() {
		transContext.get().rollbackTransaction();
	}

	public static boolean isOpen() {
		return transContext.get() != null;
	}
}
