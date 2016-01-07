package de.fiduciagad.sharea.server.data.access;

import java.util.List;

import org.ektorp.DocumentNotFoundException;
import org.ektorp.support.CouchDbDocument;

import de.fiduciagad.sharea.server.data.repository.AbstractRepository;

abstract public class AbstractManager<D extends CouchDbDocument, T extends AbstractRepository<D>> {

	private T repository;

	public AbstractManager(T repository) {
		this.repository = repository;
	}

	protected void create(D document) {
		repository.add(document);
	}

	public boolean exists(String id) {
		return repository.contains(id);
	}

	public D get(String id) {
		try {
			return repository.get(id);
		} catch (DocumentNotFoundException e) {
			return null;
		}
	}

	public List<D> getAll() {
		return repository.getAll();
	}

	public T getRepository() {
		return repository;
	}

	public void update(D document) {
		repository.update(document);
	}
}
