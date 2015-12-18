package de.fiduciagad.sharea.server.data.repository.dto;

import java.util.EnumSet;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.CouchDbDocument;
import org.ektorp.support.TypeDiscriminator;

import com.google.common.collect.Sets;

public class Account extends CouchDbDocument {

	public enum Role {
		USER, ADMIN
	}

	private static final long serialVersionUID = 1L;

	private Set<AccessToken> accessTokens;

	private String email;

	private Set<Person> persons;

	private String password;

	private EnumSet<Role> roles;

	@JsonProperty("docType")
	@TypeDiscriminator(value = "doc.docType === 'Account'")
	private final String docType = "Account";

	// Used for deserialization of the CouchDBDocument
	@SuppressWarnings("unused")
	private Account() {
	}

	public Account(String email, String password) {
		this.email = email;
		this.password = password;
		accessTokens = Sets.newConcurrentHashSet();
		roles = EnumSet.of(Role.USER);
		persons = Sets.newHashSet();
	}

	public Set<AccessToken> getAccessTokens() {
		return accessTokens;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public Set<Person> getPersons() {
		return persons;
	}

	public EnumSet<Role> getRoles() {
		return roles;
	}

	public void setAccessTokens(Set<AccessToken> accessTokens) {
		this.accessTokens = accessTokens;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPersons(Set<Person> persons) {
		this.persons = persons;
	}

	public void setRoles(EnumSet<Role> roles) {
		this.roles = roles;
	}

}