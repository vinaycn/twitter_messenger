package org.twitter.messenger.configuration;

import static org.assertj.core.api.Assertions.shouldHaveThrown;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private final String username = "user";
	private final String password = "user";
	
	@Autowired
	private MyBasicAuthenticationEntryPoint myBasicAuthenticationEntryPoint;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors()
		.and().authorizeRequests().antMatchers("/h2-console/*").permitAll()
		.anyRequest()
		.authenticated()
		.and()
		.httpBasic()
		.authenticationEntryPoint(myBasicAuthenticationEntryPoint);

		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.headers().frameOptions().disable();
	}

	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser(username)
		.password(password).roles("USER");
	}
	
	
}