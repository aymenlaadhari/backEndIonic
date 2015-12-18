package de.fiduciagad.sharea.server.rest;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.fiduciagad.sharea.server.data.access.ShareManager;
import de.fiduciagad.sharea.server.data.repository.dto.Share;

@RestController
public class ShareController {

	@Autowired
	private ShareManager shareManager;

	@CrossOrigin
	@RequestMapping(value = "/api/v1/share", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public Map<String, Object> createShare(@RequestBody(required = true) Share share) {
		
		shareManager.create(share);

		return Collections.singletonMap("id", share.getId());
	}

	@CrossOrigin
	@RequestMapping(value = "/api/v1/share", method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public List<Share> findShares(@RequestParam(required = true) String location,
			@RequestParam(required = true) int limit) {
		return shareManager.findByStartLocation(location, limit);
	}

	@CrossOrigin
	@RequestMapping(value = "/api/v1/share/{id}", method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public Share getShare(@RequestParam(required = true) String id) {
		return shareManager.get(id);
	}

}
