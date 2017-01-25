package com.cloudsql.config;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.google.appengine.api.utils.SystemProperty;

public class EntityManagerUtils {

	protected static final String PERSISTENCE_UNIT = "my_base";
	private static EntityManagerFactory emf;

	public static void initialize() {
		Map<String, String> properties = new HashMap<String, String>();
		if (isProducao()) {
			properties.put("javax.persistence.jdbc.driver", "com.mysql.jdbc.GoogleDriver");
			properties.put("javax.persistence.jdbc.url", System.getProperty("cloudsql.url"));
		} else {
			properties.put("javax.persistence.jdbc.driver", "com.mysql.jdbc.Driver");
			properties.put("javax.persistence.jdbc.url", System.getProperty("cloudsql.url.dev"));
		}

		emf = Persistence.createEntityManagerFactory(EntityManagerUtils.PERSISTENCE_UNIT, properties);
	}

	protected static EntityManager createEntityManager() {
		if (emf != null && emf.isOpen()) {
			return emf.createEntityManager();
		}
		return null;
	}

	public static boolean isProducao() {
		return SystemProperty.environment.value() == SystemProperty.Environment.Value.Production;
	}
}
