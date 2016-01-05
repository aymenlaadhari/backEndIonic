package de.fiduciagad.sharea.server.data.access;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.fiduciagad.sharea.server.data.repository.ShareRepository;
import de.fiduciagad.sharea.server.data.repository.dto.Share;

@Component
public class ShareManager extends AbstractManager<Share, ShareRepository> {

	@Autowired
	public ShareManager(ShareRepository shareRepository) {
		super(shareRepository);
	}

	public Share create(String title, String description, String categoryId, String startLocation, String endLocation,
			Date startDate, Date endDate, String icon, String owningPersonId, Set<String> participantIds,
			int participantLimit) {
		Share share = new Share(title, description, categoryId, icon, startLocation, endLocation, startDate, endDate,
				owningPersonId, participantIds, participantLimit);
		// TODO Add some validations: startDate in future, endDate > startDate,
		// things not empty...
		create(share);
		return share;
	}

	public List<Share> findByStartLocation(String startLocation, int limit) {
		return getRepository().findByStartLocation(startLocation, limit);
	}

}
