package de.fiduciagad.sharea.server.data.repository;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbDocument;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.DesignDocument;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

@Component
abstract public class AbstractRepository<T extends CouchDbDocument> extends CouchDbRepositorySupport<T> {

	protected AbstractRepository(Class<T> type, CouchDbConnector db) {
		super(type, db, true);
		initStandardDesignDocument();
		db.ensureFullCommit();
	}

	// Hide constructor
	private AbstractRepository(Class<T> type, CouchDbConnector db, boolean createIfNotExists) {
		super(type, db, createIfNotExists);
	}

	// Hide constructor
	private AbstractRepository(Class<T> type, CouchDbConnector db, String designDocName) {
		super(type, db, designDocName);
	}

	@Override
	public void add(T entity) {
		Preconditions.checkArgument(Strings.isNullOrEmpty(entity.getId()), "New entries may not define an ID.");
		Preconditions.checkArgument(Strings.isNullOrEmpty(entity.getRevision()),
				"New entries may not define an revision.");
		super.add(entity);
	}

	@Override
	public void initStandardDesignDocument() {
		super.initStandardDesignDocument();
		DesignDocument designDoc;
		if (db.contains(stdDesignDocumentId)) {
			designDoc = getDesignDocumentFactory().getFromDatabase(db, stdDesignDocumentId);
		} else {
			designDoc = getDesignDocumentFactory().newDesignDocumentInstance();
			designDoc.setId(stdDesignDocumentId);
		}
		DesignDocument.View allView = new DesignDocument.View();
		allView.setMap("function(doc) { if (doc.docType === '" + type.getSimpleName() + "' ) emit( null, doc._id )}");
		designDoc.addView("all", allView);
		db.update(designDoc);
	}

}
