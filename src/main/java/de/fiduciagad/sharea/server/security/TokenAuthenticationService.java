package de.fiduciagad.sharea.server.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class TokenAuthenticationService {

	private static final String AUTH_HEADER_NAME = "X-AUTH-TOKEN";

	@Autowired
	private TokenEnabledUserDetailsService userDetailsService;

	public TokenAuthenticationService() {
	}

	public Authentication getAuthentication(HttpServletRequest request) {
		final String token = request.getHeader(AUTH_HEADER_NAME);
		if (token != null) {
			final User user = userDetailsService.loadUserByToken(token);
			if (user != null) {
				return new PreAuthenticatedAuthenticationToken(user, token, user.getAuthorities());
			}
		}
		return null;
	}
}