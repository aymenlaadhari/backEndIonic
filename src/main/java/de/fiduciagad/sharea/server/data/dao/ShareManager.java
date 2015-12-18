package de.fiduciagad.sharea.server.data.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.fiduciagad.sharea.server.data.repository.ShareRepository;
import de.fiduciagad.sharea.server.data.repository.dto.Category;
import de.fiduciagad.sharea.server.data.repository.dto.Share;

@Component
public class ShareManager {

	@Autowired
	private ShareRepository shareRepository;

	public ShareManager() {
	}

	public void create(Share share) {
		shareRepository.add(share);
	}

	public void create(String title, String description, Category category, String startLocation, String endLocation,
			Date startDate, Date endDate, String icon, String owningPersonId, Set<String> participantIds,
			int participantLimit) {
		Share share = new Share(title, description, category, icon, startLocation, endLocation, startDate, endDate,
				owningPersonId, participantIds, participantLimit);
		shareRepository.add(share);
	}

	public List<Share> findByStartLocation(String startLocation, int limit) {
		return shareRepository.findByStartLocation(startLocation, limit);
	}

	public Share get(String id) {
		return shareRepository.get(id);
	}

}
