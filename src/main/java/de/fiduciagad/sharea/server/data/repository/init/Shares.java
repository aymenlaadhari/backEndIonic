package de.fiduciagad.sharea.server.data.repository.init;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import de.fiduciagad.sharea.server.data.repository.ShareRepository;
import de.fiduciagad.sharea.server.data.repository.dto.Share;

@Component
public class Shares {

	private Log logger = LogFactory.getLog(Categories.class);

	@Autowired
	private ShareRepository shareRepository;

	private Map<String, String> categoryMapping;

	private List<String> personIds;

	private Date currentDate;

	public Shares() {
		// TODO Auto-generated constructor stub
	}

	private Share createShare1() {
		String internalCategoryName = "travel-together";
		String owner = getOwner();
		Set<String> participants = getParticipants(owner);

		return new Share("HBF Karlsruhe zu Standort Karlsruhe", "", getIdForCategory(internalCategoryName),
				"ion-md-car", "HBF Karlsruhe", "Standort Karlsruhe", currentDate, DateUtils.addHours(currentDate, 1),
				owner, participants, participants.size() + 2);

	}

	private Share createShare2() {
		String internalCategoryName = "travel-together";
		String owner = getOwner();
		Set<String> participants = getParticipants(owner);

		return new Share("HBF Münster zu Standort Münster", "", getIdForCategory(internalCategoryName), "ion-md-car",
				"HBF Karlsruhe", "Standort Karlsruhe", DateUtils.addHours(currentDate, 4),
				DateUtils.addHours(currentDate, 5), owner, participants, participants.size() + 2);

	}

	private String getIdForCategory(String internalName) {
		return categoryMapping.get(internalName);
	}

	private String getOwner() {
		Collections.shuffle(personIds);
		return personIds.get(0);
	}

	private Set<String> getParticipants(String notEqualTo) {
		int numberOfParticipants = new Random().nextInt(personIds.size() - 3);
		Set<String> participants = Sets.newHashSet();
		while (participants.size() < numberOfParticipants) {
			Collections.shuffle(personIds);
			String person = personIds.get(0);
			if (!person.equals(notEqualTo)) {
				participants.add(person);
			}
		}
		return participants;
	}

	public void init(List<String> personIds, Map<String, String> categoryMapping) {
		currentDate = new Date();
		this.personIds = personIds;
		this.categoryMapping = categoryMapping;
		Preconditions.checkArgument(personIds != null && personIds.size() >= 6,
				"Need at least six persons for meaningful example data.");
		Preconditions.checkArgument(categoryMapping != null && categoryMapping.size() >= 3,
				"Need at least three categories.");

		List<Share> shares = Lists.newArrayList(createShare1(), createShare2());
		for (Share share : shares) {
			List<Share> dbResult = shareRepository.findByTitle(share.getTitle());
			if (dbResult.isEmpty()) {
				logger.info("Add example share: " + share.getTitle());
				shareRepository.add(share);
			} else {
				logger.info("Did not add example share: " + share.getTitle());
			}
		}
	}

}
