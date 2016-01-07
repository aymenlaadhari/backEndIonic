package de.fiduciagad.sharea.server.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import de.fiduciagad.sharea.server.data.repository.dto.Account;

public class User extends org.springframework.security.core.userdetails.User {

	private static final long serialVersionUID = 1L;
	private Account account;
	private String tokenText;

	public User(Account account, Collection<? extends GrantedAuthority> authorities) {
		this(account, null, authorities);
	}

	public User(Account account, String tokenText, Collection<? extends GrantedAuthority> authorities) {
		super(account.getEmail(), account.getPassword(), authorities);
		this.account = account;
		this.tokenText = tokenText;
	}

	public Account getAccount() {
		return account;
	}

	public String getTokenText() {
		return tokenText;
	}

}
