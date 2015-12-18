package de.fiduciagad.sharea.server.data.repository;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.fiduciagad.sharea.server.data.repository.dto.CategoryConfig;

@Component
@View(name = "all", map = "function(doc) { if (doc.docType === 'CategoryConfig' ) emit( null, doc._id )}")
public class CategoryConfigRepository extends CouchDbRepositorySupport<CategoryConfig> {

	protected CategoryConfigRepository(Class<CategoryConfig> type, CouchDbConnector db) {
		super(CategoryConfig.class, db);
		initStandardDesignDocument();
	}

	@Autowired
	public CategoryConfigRepository(CouchDbConnector db) {
		this(CategoryConfig.class, db);
	}

};