package de.fiduciagad.sharea.server.rest;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.fiduciagad.sharea.server.data.access.CommentManager;
import de.fiduciagad.sharea.server.data.repository.dto.Comment;
import de.fiduciagad.sharea.server.rest.dto.FindComments;
import de.fiduciagad.sharea.server.rest.dto.NewComment;
import de.fiduciagad.sharea.server.security.TokenEnabledUserDetailsService;
import de.fiduciagad.sharea.server.security.User;

@RestController
public class CommentController {

	@Autowired
	private CommentManager commentManager;

	@Autowired
	TokenEnabledUserDetailsService userDetailsService;

	@RequestMapping(value = "/api/v1/comment", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public Map<String, String> createComment(@RequestBody(required = true) NewComment newComment,
			Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		Comment comment = commentManager.create(user.getAccount().getId(), newComment.getText(),
				newComment.getShareId());
		return Collections.singletonMap("id", comment.getId());
	}

	@RequestMapping(value = "/api/v1/findComments", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public List<Comment> findComments(@RequestBody(required = true) FindComments findComments) {
		return commentManager.findByShareId(findComments.getShareId());
	}

	@RequestMapping(value = "/api/v1/comment/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Comment getComment(@PathVariable String id) {
		return commentManager.get(id);
	}

}
