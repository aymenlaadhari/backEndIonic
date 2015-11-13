package de.fiduciagad.sharea.server.persistence.generic;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.cloudant.client.api.model.Response;
import com.google.gson.Gson;

public class DaoImpl<T> implements Dao<T> {

	@Autowired
	private DatabaseProvider databaseProvider;

	private T t;

	public void set(T t) {
		this.t = t;
	}

	public T get() {
		return t;
	}

	private static final String ENCODING = "UTF-8";
	private Gson gson = new Gson();

	@Override
	public T readById(String id, Class<T> requiredType) {
		String jsonString = null;
		try {
			jsonString = IOUtils.toString(databaseProvider.getDB().find(id), ENCODING);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		T entity = gson.fromJson(jsonString, requiredType);

		return entity;
	}
	
	public String push(T t) {
		Response resonse = databaseProvider.getDB().post(t);
		return resonse.getId();
	}

}
