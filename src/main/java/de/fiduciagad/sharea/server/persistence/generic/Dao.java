package de.fiduciagad.sharea.server.persistence.generic;

public interface Dao<T> {
	
	public T readById(String id);

}
