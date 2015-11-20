package de.fiduciagad.sharea.server.persistence.generic;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.cloudant.client.api.model.Response;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class DaoImpl<T> implements Dao<T> {

	@Autowired
	private DatabaseProvider databaseProvider;

	private T t;

	private Class<T> type;

	public void set(T t) {
		this.t = t;
	}

	public T get() {
		return t;
	}

	public DaoImpl(Class<T> type) {
		super();
		this.type = type;
	}

	private static final String ENCODING = "UTF-8";
	private Gson gson = new Gson();

	@Override
	public T readById(String id) {
		String jsonString = null;
		try {
			jsonString = IOUtils.toString(databaseProvider.getDatabase().find(id), ENCODING);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		T entity = gson.fromJson(jsonString, type);

		return entity;
	}

	@Override
	public List<T> read(String query) {
		String jsonString = null;
		try {
			jsonString = IOUtils.toString(databaseProvider.getDatabase().search(jsonString).queryForStream(query),
					ENCODING);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		Type listType = new TypeToken<ArrayList<T>>() {
		}.getType();
		List<T> listEntites = new Gson().fromJson(jsonString, listType);

		return listEntites;

	}

	public String push(T t) {
		Response resonse = databaseProvider.getDatabase().post(t);
		return resonse.getId();
	}

}
