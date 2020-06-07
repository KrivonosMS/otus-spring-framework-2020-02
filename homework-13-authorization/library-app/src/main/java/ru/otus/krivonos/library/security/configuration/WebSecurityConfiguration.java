package ru.otus.krivonos.library.security.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
			.and()
			.authorizeRequests().antMatchers("/library/book/all").hasAnyRole("ADMIN", "USER")
			.and()
			.authorizeRequests().antMatchers("/library/book/add").hasRole("ADMIN")
			.and()
			.authorizeRequests().antMatchers("/library/book/*/delete").hasRole("ADMIN")
			.and()
			.authorizeRequests().antMatchers("/library/book/*/edit").hasRole("ADMIN")
			.and()
			.authorizeRequests().antMatchers("/library/book/*/add/comment").hasAnyRole("ADMIN", "USER")
			.and()
			.authorizeRequests().antMatchers("/library/book/delete/comment/*").hasRole("ADMIN")
			.and()
			.authorizeRequests().antMatchers("/library/genre/all").hasAnyRole("ADMIN", "USER")
			.and()
			.authorizeRequests().antMatchers("/library/genre/add").hasRole("ADMIN")
			.and()
			.authorizeRequests().antMatchers("/library/genre/*/edit").hasRole("ADMIN")
			.and()
			.logout()
			.logoutUrl("/library/logout").permitAll().clearAuthentication(true).deleteCookies("JSESSIONID")
			.logoutSuccessHandler((httpServletRequest, httpServletResponse, authentication) -> httpServletResponse.setStatus(200))
			.and()
			.formLogin()
			.loginProcessingUrl("/j_auth")
			.usernameParameter("j_username")
			.passwordParameter("j_password")
			.failureHandler(new SimpleUrlAuthenticationFailureHandler())
			.successHandler((httpServletRequest, httpServletResponse, authentication) -> httpServletResponse.setStatus(200))
			.and()
			.exceptionHandling()
			.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
			.and()
			.rememberMe().key("MySecret").tokenValiditySeconds(10*24*60*60);
	}

	@Bean
	public static BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}