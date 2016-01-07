package de.fiduciagad.sharea.server;

import javax.servlet.Filter;

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
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import springfox.documentation.swagger1.annotations.EnableSwagger;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import de.fiduciagad.sharea.server.security.CorsFilter;
import de.fiduciagad.sharea.server.security.TokenAuthenticationFilter;
import de.fiduciagad.sharea.server.security.TokenEnabledUserDetailsService;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableSwagger
@EnableSwagger2
public class App {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(App.class, args);
	}

}

@Configuration
class CloudConfiguration extends AbstractCloudConfig {

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
		// If more than one Cloudant DB is bound to the app this will throw an
		// error. This is intentional to prevent more than one Cloudant
		// database.
		return cloud.getSingletonServiceConnector(CouchDbInstance.class, null);
	}
}

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
				.antMatchers("/api/v1/account").permitAll()//
				.antMatchers("/api/v1/token").permitAll()//
				// TODO1: Only allow read access to shares
				.antMatchers("/api/v1/share/*").permitAll()//
				.antMatchers("/logout").permitAll()//
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
	protected PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public UserDetailsService userDetailsServiceBean() throws Exception {
		return userDetailsService;
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/*").allowedOrigins("http://localhost:8100",
						"http://sharedotadotdev.eu-gb.mybluemix.net",
						"https://sharedotadotdev.eu-gb.mybluemix.net");
			}
		};
	}

	@Bean
	public Filter corsFilter() {
		return new CorsFilter();
	}

}
