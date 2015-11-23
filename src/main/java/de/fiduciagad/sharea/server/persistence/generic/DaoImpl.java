package de.fiduciagad.sharea.server.persistence.generic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.cloudant.client.api.model.Document;
import com.cloudant.client.api.model.Response;
import com.cloudant.client.api.views.AllDocsResponse;
import com.google.gson.Gson;

public class DaoImpl<T> implements Dao<T> {

	@Autowired
	private DatabaseProvider databaseProvider;

	private Class<T> type;

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

	// TODO: Nur für Testzwecke
	@Override
	public List<Document> getAllDocs() {
		try {
			AllDocsResponse allDocs = databaseProvider.getDatabase().getAllDocsRequestBuilder().includeDocs(true)
					.build().getResponse();
			List<Document> listeDocs = allDocs.getDocs();
			return listeDocs;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String create(T t) {
		Response r = databaseProvider.getDatabase().save(t);
		return r.getId();
	}

	@Override
	public List<String> create(List<T> t) {
		List<String> listIds = new ArrayList<String>();
		for (T iterateableT : t) {
			listIds.add(databaseProvider.getDatabase().save(iterateableT).getId());
		}
		return listIds;
	}

	@Override
	public List<T> read(String query) {

		return databaseProvider.getDatabase().findByIndex(query, type);

	}

}
