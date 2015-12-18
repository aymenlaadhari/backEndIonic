package de.fiduciagad.sharea.server.data.repository;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.fiduciagad.sharea.server.data.repository.dto.Category;

@Component
@View(name = "all", map = "function(doc) { if (doc.docType === 'Category' ) emit( null, doc._id )}")
public class CategoryRepository extends CouchDbRepositorySupport<Category> {

	protected CategoryRepository(Class<Category> type, CouchDbConnector db) {
		super(Category.class, db);
		initStandardDesignDocument();
	}

	@Autowired
	public CategoryRepository(CouchDbConnector db) {
		this(Category.class, db);
	}

};