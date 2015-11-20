package de.fiduciagad.sharea.server.persistence.generic;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface Dao<T> {

	public T readById(String id);

	public List<T> read(String query);

	

}
