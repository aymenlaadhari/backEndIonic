package de.fiduciagad.sharea.server.rest.dto;

import com.google.common.base.Preconditions;

import de.fiduciagad.sharea.server.data.repository.dto.Person;

public class APIPerson {

	public static APIPerson from(Person person) {
		Preconditions.checkNotNull(person);
		return new APIPerson(person);
	}

	public String id;
	public String revision;
	public String name;
	public String nickname;
	public String owningAccountId;

	private APIPerson(Person person) {
		id = person.getId();
		revision = person.getRevision();
		name = person.getName();
		nickname = person.getNickname();
		owningAccountId = person.getOwningAccountId();
	}

}
