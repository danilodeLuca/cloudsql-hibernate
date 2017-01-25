package com.cloudsql.example;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/exemplos")
public class ExampleRS {

	@GET
	@Path("/create")
	public void test() {
		new ExampleService().create();
	}

	@GET
	@Path("/testParam")
	public Response testParam() {
		new ExampleService().param();
		return Response.ok().build();
	}

}
