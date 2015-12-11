package de.fiduciagad.sharea.server.ws;

import java.io.IOException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.fiduciagad.sharea.server.dto.Share;
import de.fiduciagad.sharea.server.dto.ShareInputDTO;
import de.fiduciagad.sharea.server.persistence.dao.ShareDaoIf;

@RestController
public class ShareEndpoint {

	private static final Log log = LogFactory.getLog(ShareEndpoint.class);

	@Autowired
	private ShareDaoIf shareDao;

	@CrossOrigin
	@RequestMapping(value = "/api/v1/shareSuggestions", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public List<Share> getShareSuggestions(@RequestBody(required=true) ShareInputDTO shareInputDTO) throws IOException {
		List<Share> listShares = shareDao.readSharesByPlaceFrom(shareInputDTO.getPlaceFrom(),shareInputDTO.getLimit());
		log.info(listShares.size() + " elements for location: " + shareInputDTO.getPlaceFrom() + " found.");
		return listShares;
	}
	
	@CrossOrigin
	@RequestMapping(value = "/api/v1/getShareByID", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public Share getShareSuggestions(@RequestBody String _id) throws IOException {
		Share share = shareDao.readById(_id);
		log.info(share.get_id()+" found.");
		return share;
	}
	
	@CrossOrigin
	@RequestMapping(value = "/api/v1/saveShare", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public String getShareSuggestions(@RequestBody Share share) throws IOException {
		
		String id = shareDao.create(share);
		log.info(share.get_id()+" found.");
		return id;
	}

}
