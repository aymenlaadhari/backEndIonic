package de.fiduciagad.sharea.server.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Component
public class TokenAuthenticationFilter extends GenericFilterBean {

	@Autowired
	private TokenAuthenticationService tokenAuthenticationService;

	public TokenAuthenticationFilter() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		SecurityContextHolder.getContext()
				.setAuthentication(tokenAuthenticationService.getAuthentication((HttpServletRequest) req));
		chain.doFilter(req, res); // always continue
	}

}