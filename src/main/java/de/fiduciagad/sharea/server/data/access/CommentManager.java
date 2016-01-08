package de.fiduciagad.sharea.server.data.access;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.fiduciagad.sharea.server.data.repository.CommentRepository;
import de.fiduciagad.sharea.server.data.repository.dto.Comment;

@Component
public class CommentManager extends AbstractManager<Comment, CommentRepository> {

	@Autowired
	public CommentManager(CommentRepository commentRepository) {
		super(commentRepository);
	}

	public Comment create(String authorId, String text, String shareId, String name, Date commentDate) {
		Comment comment = new Comment(authorId, text, shareId, name, commentDate);
		create(comment);
		return comment;
	}

	public List<Comment> findByShareId(String shareId) {
		return getRepository().findByShares(shareId);
	}
	

}
