package de.fiduciagad.sharea.server.data.access;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.fiduciagad.sharea.server.data.repository.CommentRepository;
import de.fiduciagad.sharea.server.data.repository.dto.Comment;

@Component
public class CommentManager {

	@Autowired
	private CommentRepository commentRepository;

	public CommentManager() {
	}

	public void create(Comment comment) {
		commentRepository.add(comment);
	}

	public Comment get(String id) {
		return commentRepository.get(id);
	}

}
