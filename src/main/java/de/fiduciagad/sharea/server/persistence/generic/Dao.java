package de.fiduciagad.sharea.server.persistence.generic;

import org.springframework.stereotype.Service;

@Service
public interface Dao<T> {

	public T readById(String id);

	

}
