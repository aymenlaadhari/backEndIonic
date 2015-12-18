package de.fiduciagad.sharea.server.rest;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
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
	public Map<String, Object> createShare(@RequestParam(required = true) String title,
			@RequestParam(required = true) String description, @RequestParam(required = true) Set<String> categoryIds,
			@RequestParam(required = true) String startLocation, @RequestParam(required = true) String endLocation,
			@RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date startDate,
			@RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date endDate,
			@RequestParam(required = true) String icon, @RequestParam(required = true) String owningPersonId,
			@RequestParam(required = true) Set<String> participantIds,
			@RequestParam(required = true) int participantLimit) {

		shareManager.create(title, description, categoryIds, startLocation, endLocation, startDate, endDate, icon,
				owningPersonId, participantIds, participantLimit);
		// TODO ehm... if it doesn't throw an exception everything is cool?
		// Hmm... maybe better error handling.
		return Collections.singletonMap("id", true);
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
