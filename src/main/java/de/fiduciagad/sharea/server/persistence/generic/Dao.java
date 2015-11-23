package de.fiduciagad.sharea.server.persistence.generic;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cloudant.client.api.model.Document;

@Service
public interface Dao<T> {

	public T readById(String id);

	public List<T> read(String query);

	// TODO: Nur für Testzwecke
	public List<Document> getAllDocs();

	public String create(T t);

	public List<String> create(List<T> t);

}
