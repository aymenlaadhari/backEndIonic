package de.fiduciagad.sharea.server.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import de.fiduciagad.sharea.server.data.repository.dto.Account;

public class User extends org.springframework.security.core.userdetails.User {

	private static final long serialVersionUID = 1L;
	private Account account;

	public User(Account account, Collection<? extends GrantedAuthority> authorities) {
		super(account.getEmail(), account.getPassword(), authorities);
		this.account = account;
	}

	public Account getAccount() {
		return account;
	}

}
