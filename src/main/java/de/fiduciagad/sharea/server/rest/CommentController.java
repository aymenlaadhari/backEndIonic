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
import de.fiduciagad.sharea.server.data.access.PersonManager;
import de.fiduciagad.sharea.server.data.repository.dto.Comment;
import de.fiduciagad.sharea.server.data.repository.dto.Person;
import de.fiduciagad.sharea.server.rest.dto.FindComments;
import de.fiduciagad.sharea.server.rest.dto.NewComment;
import de.fiduciagad.sharea.server.security.TokenEnabledUserDetailsService;
import de.fiduciagad.sharea.server.security.User;

@RestController
public class CommentController {

	@Autowired
	private CommentManager commentManager;
	@Autowired
	private PersonManager personManager;

	@Autowired
	TokenEnabledUserDetailsService userDetailsService;

	@RequestMapping(value = "/api/v1/comment", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public Map<String, String> createComment(@RequestBody(required = true) NewComment newComment,
			Authentication authentication) {
		Person person = personManager.get(newComment.getOwningPersonId());
		Comment comment = commentManager.create(person.getId(), newComment.getText(),
				newComment.getShareId(), person.getName(), newComment.getCommentDate());
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
	
	@RequestMapping(value = "/api/v1/comment/{id}", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseBody
	public Map<String, String> deleteComment(@PathVariable String id, Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		Person person = personManager.findByAccount(user.getAccount());
		Comment comment = commentManager.get(id);
		if(person.getId().equals(comment.getOwningPersonId())){
			commentManager.delete(comment);
			return Collections.singletonMap("success", Boolean.TRUE.toString());
		}
		else{
			return Collections.singletonMap("success", Boolean.FALSE.toString());
		}
	}
	
	@RequestMapping(value = "/api/v1/comment", method = RequestMethod.PUT, produces = "application/json")
	@ResponseBody
	public Map<String, String> update(@RequestBody(required = true) Comment comment,Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		Person person = personManager.findByAccount(user.getAccount());
		if (!comment.getOwningPersonId().equals(person.getId())) {
			return Collections.singletonMap("success", Boolean.FALSE.toString());
		} else {
			commentManager.update(comment);
			return Collections.singletonMap("success", Boolean.TRUE.toString());
		}
	}

}
