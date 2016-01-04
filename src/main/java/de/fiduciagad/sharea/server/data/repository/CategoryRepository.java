package de.fiduciagad.sharea.server.data.repository;

import org.ektorp.CouchDbConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.fiduciagad.sharea.server.data.repository.dto.Category;

@Component
public class CategoryRepository extends AbstractRepository<Category> {

	@Autowired
	public CategoryRepository(CouchDbConnector db) {
		super(Category.class, db);
	}

};