package de.fiduciagad.sharea.server;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudFactory;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import de.fiduciagad.sharea.server.security.TokenAuthenticationFilter;
import de.fiduciagad.sharea.server.security.TokenEnabledUserDetailsService;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class App {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(App.class, args);
	}

}

@Configuration
class CloudConfiguration extends AbstractCloudConfig {

	@Value("${CLOUDANT_CONFIG_KEY}")
	private String dbServiceName;

	@Value("${CLOUDANT_DB_NAME}")
	private String dbName;;

	private Cloud cloud;

	public CloudConfiguration() {
		CloudFactory cloudFactory = new CloudFactory();
		cloud = cloudFactory.getCloud();
	}

	@Bean
	public CouchDbConnector couchDbConnector() {
		return couchDbInstance().createConnector(dbName, true);
	}

	@Bean
	public CouchDbInstance couchDbInstance() {
		return cloud.getServiceConnector(dbServiceName, CouchDbInstance.class,
				null /* default config */);
	}

}

@EnableWebSecurity
@Configuration
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private TokenAuthenticationFilter tokenAuthenticationFilter;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()//
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()//
				.authorizeRequests()//
				// .antMatchers("/api/v1/data").permitAll()//
				.antMatchers("/api/v1/account").permitAll()//
				.antMatchers("/api/v1/token").permitAll()//
				// TODO1: Only allow read access to shares
				.antMatchers("/api/v1/share/*").permitAll()//
				.antMatchers("/logout").permitAll()//
				.anyRequest().authenticated().and()//
				.addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

	}

	@Bean
	protected PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public TokenEnabledUserDetailsService tokenEnabledUserDetailsService() throws Exception {
		return new TokenEnabledUserDetailsService();
	}

	@Override
	public UserDetailsService userDetailsServiceBean() throws Exception {
		return new TokenEnabledUserDetailsService();
	}

}
