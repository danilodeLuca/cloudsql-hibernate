package com.cloudsql.config;

import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class DatabaseContextListener implements ServletContextListener {

	private Logger logger = Logger.getLogger(DatabaseContextListener.class.getCanonicalName());

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			logger.info("########### Listener ################");
			EntityManagerUtils.initialize();
		} catch (RuntimeException e) {

		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
	}

}
