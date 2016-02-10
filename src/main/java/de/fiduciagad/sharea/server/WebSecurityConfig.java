package de.fiduciagad.sharea.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import de.fiduciagad.sharea.server.rest.PersonController;
import de.fiduciagad.sharea.server.rest.TokenController;
import de.fiduciagad.sharea.server.security.TokenAuthenticationFilter;
import de.fiduciagad.sharea.server.security.TokenEnabledUserDetailsService;

@EnableWebSecurity
@Configuration
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private TokenAuthenticationFilter tokenAuthenticationFilter;

	@Autowired
	private TokenEnabledUserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()//
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()//
				.authorizeRequests()//
				// .antMatchers("/api/v1/data").permitAll()//
				.antMatchers(PersonController.API_PERSON_RANDOM).permitAll()//
				.antMatchers(PersonController.API_PERSON).permitAll()//
				.antMatchers(TokenController.API_TOKEN_RANDOM).permitAll()//
				.antMatchers("/api/v1/account").permitAll()//
				.antMatchers("/api/v1/token").permitAll()//
				// TODO1: Only allow read access to shares
				.antMatchers("/api/v1/share/*").permitAll()//
				.antMatchers("/logout").permitAll()//
				// allow all cors-preflight options requests
				.antMatchers(HttpMethod.OPTIONS, "/api/**").permitAll()
				// springfox documentation :-)))
				.antMatchers("/api-docs/**").permitAll()//
				.antMatchers("/configuration/security").permitAll()//
				.antMatchers("/swagger-resources/**").permitAll()//
				.antMatchers("/configuration/ui/**").permitAll()//
				.antMatchers("/v2/api-docs/**").permitAll()//
				.antMatchers("/swagger-ui.html").permitAll()//
				.antMatchers("/webjars/springfox-swagger-ui/**").permitAll()//
				.antMatchers("/images/**").permitAll()//
				.anyRequest().authenticated().and()//
				.addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/api/**")
						.allowedOrigins("http://localhost:8100", "https://sharedota.eu-gb.mybluemix.net", // Produktivsystem
								// "http://sharedotadotdev.eu-gb.mybluemix.net",
								// //Unsicher
								"https://sharedotadotdev.eu-gb.mybluemix.net")
						.allowedHeaders("Content-Type", "Accept", "X-Requested-With", "remember-me", "x-auth-token",
								"cache-control", "user-agent")
						.exposedHeaders("Content-Type", "Accept", "X-Requested-With", "remember-me", "x-auth-token",
								"cache-control", "user-agent")
						.allowedMethods("POST", "GET", "OPTIONS").allowCredentials(true).maxAge(3600);
			}
		};
	}

	@Bean
	protected PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public UserDetailsService userDetailsServiceBean() throws Exception {
		return userDetailsService;
	}
}