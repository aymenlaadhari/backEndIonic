package de.fiduciagad.sharea.server.security;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import de.fiduciagad.sharea.server.data.dao.AccountManager;
import de.fiduciagad.sharea.server.data.repository.dto.Account;
import de.fiduciagad.sharea.server.data.repository.dto.Account.Role;

@Service
public final class TokenEnabledUserDetailsService implements UserDetailsService {

	@Autowired
	private AccountManager accountHandler;

	public TokenEnabledUserDetailsService() {
	}

	private List<GrantedAuthority> getAuthorities(Account account) {
		List<Role> rolesAsList = Lists.newArrayList(account.getRoles());
		Collection<String> authorityRoles = Collections2.transform(rolesAsList, new Function<Role, String>() {

			@Override
			public String apply(Role role) {
				return "ROLE_" + role.name().toUpperCase();
			}

		});
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.createAuthorityList(authorityRoles.toArray(new String[authorityRoles.size()]));
		return grantedAuthorities;
	}

	public User loadUserByToken(String token) throws UsernameNotFoundException {
		Account account = accountHandler.getAccountByToken(token);
		if (account != null) {
			List<GrantedAuthority> grantedAuthorities = getAuthorities(account);
			return new User(account, grantedAuthorities);
		} else {
			return null;
		}
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// Account account = db.findAccountByEmail(username);
		Account account = accountHandler.getAccountByEmail(email);
		if (account != null) {
			List<GrantedAuthority> grantedAuthorities = getAuthorities(account);
			return new User(account, grantedAuthorities);
		} else {
			throw new UsernameNotFoundException("Could not find user '" + email + "'");
		}
	}

}