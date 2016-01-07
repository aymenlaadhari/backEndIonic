package de.fiduciagad.sharea.server.data.repository;

import java.util.List;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import de.fiduciagad.sharea.server.data.repository.dto.Comment;

@Component
public class CommentRepository extends AbstractRepository<Comment> {

	private static final String FIND_COMMENTS = "findComments";

	@Autowired
	public CommentRepository(CouchDbConnector db) {
		super(Comment.class, db);
	}

	@View(name = FIND_COMMENTS, map = "function(doc) { if (doc.docType === 'Comment' ) emit( doc.shareId, doc._id )}")
	public List<Comment> findByShares(String shareId) {
		Preconditions.checkArgument(!Strings.isNullOrEmpty(shareId), "ShareId cannot be empty.");
		return queryView(FIND_COMMENTS, shareId);
	}

};