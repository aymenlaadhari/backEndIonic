package de.fiduciagad.sharea.server.data.repository;

import org.ektorp.CouchDbConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.fiduciagad.sharea.server.data.repository.dto.CategoryConfig;

@Component
public class CategoryConfigRepository extends AbstractRepository<CategoryConfig> {

	@Autowired
	public CategoryConfigRepository(CouchDbConnector db) {
		super(CategoryConfig.class, db);
	}

};