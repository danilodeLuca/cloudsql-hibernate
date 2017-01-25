package com.cloudsql.config;

public class Service {

	public static <T extends BaseRepository> T repo(Class<T> clazz) {
		return Repository.invoke(clazz);
	}

}
