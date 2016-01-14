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

	public Shares() {
		// TODO Auto-generated constructor stub
	}

	private Share createShare(String internalCategoryName, String title, String description, String icon,
			String startLocation, String endLocation, Date startDate) {

		String owner = getOwner();
		Set<String> participants = getParticipants(owner);
		return new Share(title, description, getIdForCategory(internalCategoryName), icon, startLocation, endLocation,
				startDate, DateUtils.addHours(startDate, 1), owner, participants, participants.size()
						+ new Random().nextInt(10));
	}

	private void createShares(List<String> personIds, Map<String, String> categoryMapping, Date startDate) {
		
		logger.info("CreateShares has been entered");
		
		this.personIds = personIds;
		this.categoryMapping = categoryMapping;
		Preconditions.checkArgument(personIds != null && personIds.size() >= 6,
				"Need at least six persons for meaningful example data.");
		Preconditions.checkArgument(categoryMapping != null && categoryMapping.size() >= 3,
				"Need at least three categories.");

		logger.info("Preconditions are met");
		
		List<Share> shares = Lists.newArrayList(
				//
				createShare("travel-together", "HBF Karlsruhe zu Standort Karlsruhe", "", "ion-md-car",
						"HBF Karlsruhe", "Standort Karlsruhe", startDate), //
				createShare("travel-together", "HBF Münster zu Standort Münster", "", "ion-md-car", "HBF Münster",
						"Standort Münster", startDate), //
				createShare("eat-together", "Betriebsrestaurant München", "", "ion-md-restaurant", "München", "",
						startDate), //
				createShare("eat-together", "Kaffee trinken", "", "ion-md-cafe", "Berlin", "", startDate), //
				createShare("share-office", "Büro Frankfurt", "", "ion-ios-monitor", "Frankfurt", "", startDate) //
				);
		
		logger.info("List of shares created");
		
		for (Share share : shares) {
			logger.info("Add example share: " + share.getTitle());
			shareRepository.add(share);
		}
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
		Date currentDate = new Date();
		List<Date> dates = Lists.newArrayList(//
				DateUtils.addHours(currentDate, 2), //
				DateUtils.addHours(currentDate, 7), //
				DateUtils.addDays(currentDate, 1), //
				DateUtils.addHours(DateUtils.addDays(currentDate, 3), 1), //
				DateUtils.addHours(DateUtils.addDays(currentDate, 1), 2), //
				DateUtils.addHours(DateUtils.addDays(currentDate, 2), 2), //
				DateUtils.addHours(DateUtils.addDays(currentDate, 2), 5), //
				DateUtils.addHours(DateUtils.addDays(currentDate, 3), 5), //
				DateUtils.addHours(DateUtils.addDays(currentDate, 1), 7), //
				DateUtils.addMonths(currentDate, 2), //
				DateUtils.addMonths(currentDate, 4), //
				DateUtils.addMonths(currentDate, 6)//
				);

		for (Date date : dates) {
			logger.info("createShares: " + date);
			try{
				createShares(personIds, categoryMapping, date);
			
			} catch (Throwable t){
				t.printStackTrace();
			}
		}
	}

}
