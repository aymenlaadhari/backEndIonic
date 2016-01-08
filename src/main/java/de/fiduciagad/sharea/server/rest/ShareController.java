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

import de.fiduciagad.sharea.server.data.access.PersonManager;
import de.fiduciagad.sharea.server.data.access.ShareManager;
import de.fiduciagad.sharea.server.data.repository.dto.Person;
import de.fiduciagad.sharea.server.data.repository.dto.Share;
import de.fiduciagad.sharea.server.rest.dto.FindShares;
import de.fiduciagad.sharea.server.security.TokenEnabledUserDetailsService;
import de.fiduciagad.sharea.server.security.User;

@RestController
public class ShareController {

	@Autowired
	private ShareManager shareManager;
	@Autowired
	private PersonManager personManager;

	@Autowired
	TokenEnabledUserDetailsService userDetailsService;

	@RequestMapping(value = "/api/v1/share", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public Map<String, String> createShare(@RequestBody Share share, Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		String owningPersonId = user.getAccount().getId();
		share = shareManager.create(share.getTitle(), share.getDescription(), share.getCategoryId(), share.getIcon(),
				share.getStartLocation(), share.getEndLocation(), share.getStartDate(), share.getEndDate(),
				owningPersonId, share.getParticipantLimit());
		return Collections.singletonMap("id", share.getId());
	}

	@RequestMapping(value = "/api/v1/findShares", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public List<Share> findShares(@RequestBody FindShares findShares) {
		return shareManager.findByStartLocation(findShares.getStartLocation(), findShares.getLimit());
	}

	@RequestMapping(value = "/api/v1/share/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Share getShare(@PathVariable String id) {
		return shareManager.get(id);
	}

	@RequestMapping(value = "/api/v1/share/{nickname}", method = RequestMethod.PUT, produces = "application/json")
	@ResponseBody
	public Map<String, String> update(@PathVariable String id, Authentication authentication) {
		// TODO: The owner should be able do update the Share, all others should
		// be able to participate.
		// Right now this just adds the user to the participant list.
		User user = (User) authentication.getPrincipal();
		Share share = shareManager.get(id);
		Person person = personManager.findByAccount(user.getAccount());
		if (!share.getOwningPersonNickname().equals(person.getNickname())) {
			share.getParticipantNicknames().add(person.getNickname());
			shareManager.update(share);
			return Collections.singletonMap("success", Boolean.TRUE.toString());
		} else {
			return Collections.singletonMap("success", Boolean.TRUE.toString());
		}
	}

	@RequestMapping(value = "/api/v1/share", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public Map<String, String> updateShare(@RequestBody Share share) {
		shareManager.update(share);
		return Collections.singletonMap("id", share.getId());
	}

}
