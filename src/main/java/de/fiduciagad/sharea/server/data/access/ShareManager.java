package de.fiduciagad.sharea.server.data.access;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.fiduciagad.sharea.server.data.repository.ShareRepository;
import de.fiduciagad.sharea.server.data.repository.dto.Share;
import de.fiduciagad.sharea.server.rest.dto.FindShares;

@Component
public class ShareManager {

	@Autowired
	private ShareRepository shareRepository;

	public ShareManager() {
	}

	public void create(Share share) {
		shareRepository.add(share);
	}

	public Share create(String title, String description, Set<String> categoryIds, String startLocation,
			String endLocation, Date startDate, Date endDate, String icon, String owningPersonId,
			Set<String> participantIds, int participantLimit) {
		Share share = new Share(title, description, categoryIds, icon, startLocation, endLocation, startDate, endDate,
				owningPersonId, participantIds, participantLimit);
		shareRepository.add(share);
		return share;
	}

	public List<Share> findByStartLocation(FindShares findShares) {
		return shareRepository.findByStartLocation(findShares);
	}

	public Share get(String id) {
		return shareRepository.get(id);
	}

}
