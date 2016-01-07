package de.fiduciagad.sharea.server.data.repository;

import java.util.List;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.fiduciagad.sharea.server.data.repository.dto.Category;

@Component
public class CategoryRepository extends AbstractRepository<Category> {

	private static final String BY_INTERNAL_NAME = "by_internalName";

	@Autowired
	public CategoryRepository(CouchDbConnector db) {
		super(Category.class, db);
	}

	public boolean existsByInternalName(String internalName) {
		return findByInternalName(internalName).size() > 0;
	}

	@View(name = BY_INTERNAL_NAME, map = "function(doc) { if(doc.docType === 'Category' && doc.internalName) {emit(doc.internalName, doc._id)} }")
	public List<Category> findByInternalName(String internalName) {
		return queryView(BY_INTERNAL_NAME, internalName);
	}

};