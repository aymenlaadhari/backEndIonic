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

	private Log logger = LogFactory.getLog(Shares.class);

	@Autowired
	private ShareRepository shareRepository;

	private Map<String, String> categoryMapping;

	private List<String> personIds;

	private Date currentDate;

	public Shares() {
		// TODO Auto-generated constructor stub
	}

	private Share createShare(String internalCategoryName, String title, String description, String icon,
			String startLocation, String endLocation) {

		String owner = getOwner();

		Date startDate = null;
		Date endDate = null;
		if (new Random().nextBoolean()) {
			startDate = DateUtils.addDays(currentDate, new Random().nextInt(3));
			endDate = DateUtils.addHours(startDate, new Random().nextInt(5));
		} else {
			startDate = DateUtils.addHours(currentDate, new Random().nextInt(5));
			endDate = DateUtils.addHours(startDate, new Random().nextInt(2));
		}

		Set<String> participants = getParticipants(owner);
		return new Share(title, description, getIdForCategory(internalCategoryName), icon, startLocation, endLocation,
				startDate, endDate, owner, participants, participants.size() + new Random().nextInt(10));
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

		List<Share> shares = Lists.newArrayList(//
				createShare("travel-together", "HBF Karlsruhe zu Standort Karlsruhe", "", "ion-md-car", "HBF Karlsruhe",
						"Standort Karlsruhe"), //
				createShare("travel-together", "HBF Münster zu Standort Münster", "", "ion-md-car", "HBF Münster",
						"Standort Münster"), //
				createShare("eat-together", "Betriebsrestaurant München", "", "ion-md-restaurant", "München", ""), //
				createShare("eat-together", "Kaffee trinken", "", "ion-md-cafe", "Berlin", ""), //
				createShare("share-office", "Büro Frankfurt", "", "ion-ios-monitor", "Frankfurt", "") //
		);
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
