package de.fiduciagad.sharea.server.rest;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.fiduciagad.sharea.server.data.access.CommentManager;
import de.fiduciagad.sharea.server.data.repository.dto.Comment;
import de.fiduciagad.sharea.server.rest.dto.FindComments;

@RestController
public class CommentController {

	@Autowired
	private CommentManager commentManager;

	@CrossOrigin
	@RequestMapping(value = "/api/v1/comment", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public Map<String, Object> createShare(@RequestBody(required = true) Comment comment) {

		commentManager.create(comment);

		return Collections.singletonMap("id", comment.getId());
	}
	
	@CrossOrigin
	@RequestMapping(value = "/api/v1/findComments", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public List<Comment> findComments(@RequestBody(required = true) FindComments findComments) {
		return commentManager.findByShares(findComments.getShareId());
	}

	@CrossOrigin
	@RequestMapping(value = "/api/v1/comment/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Comment getShare(@PathVariable String id) {
		return commentManager.get(id);
	}

}
