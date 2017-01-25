package com.cloudsql.example;

import com.cloudsql.config.Service;
import com.cloudsql.parametro.ParameterRepository;

public class ExampleService extends Service {

	public void create() {
		repo(ParameterRepository.class).create();
	}

	public void param() {
		repo(ParameterRepository.class).get("BLA");
	}

}
