package de.fiduciagad.sharea.server.data.repository;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.fiduciagad.sharea.server.data.repository.dto.Comment;

@Component
@View(name = "all", map = "function(doc) { if (doc.docType === 'Comment') emit( null, doc._id )}")
public class CommentRepository extends CouchDbRepositorySupport<Comment> {

	protected CommentRepository(Class<Comment> type, CouchDbConnector db) {
		super(Comment.class, db);
		initStandardDesignDocument();
	}

	@Autowired
	public CommentRepository(CouchDbConnector db) {
		this(Comment.class, db);
	}

};