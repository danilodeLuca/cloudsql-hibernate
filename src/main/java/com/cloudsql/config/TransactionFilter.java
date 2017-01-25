package com.cloudsql.config;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.Response;

public class TransactionFilter implements Filter {

	private Logger logger = Logger.getLogger(TransactionFilter.class.getCanonicalName());

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		logger.info("[START] Transaction filter");

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		try {
			if (HttpMethod.GET.equals(req.getMethod())) {
				TransactionalContext.createEm();
			} else {
				TransactionalContext.beginTransaction();
			}

			chain.doFilter(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			if (TransactionalContext.isOpen()) {
				TransactionalContext.rollbackTransaction();
				TransactionalContext.close();
			}
		} finally {
			if (TransactionalContext.isOpen()) {
				try {
					if (shouldRollback(response))
						TransactionalContext.rollbackTransaction();
					else
						TransactionalContext.commitTransaction();
				} catch (Exception e) {
					TransactionalContext.rollbackTransaction();
				}

				TransactionalContext.close();
			}
		}
	}

	private boolean shouldRollback(ServletResponse resp) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Method method2 = resp.getClass().getMethod("getResponse");
		Object parentResponse = method2.invoke(resp, new Object[] {});

		Method method3 = parentResponse.getClass().getMethod("getStatus");
		Integer status = (Integer) method3.invoke(parentResponse, new Object[] {});

		if (status != null)
			return Response.Status.Family.familyOf(status).equals(Response.Status.Family.CLIENT_ERROR)
					|| Response.Status.Family.familyOf(status).equals(Response.Status.Family.SERVER_ERROR);

		return false;
	}

	@Override
	public void destroy() {
	}

}
