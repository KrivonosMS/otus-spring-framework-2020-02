package ru.otus.krivonos.library.security.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class DbUserDetailsServiceTest {
	@Autowired
	@Qualifier("dbUserDetailsService")
	private UserDetailsService userDetailsService;

	@Test
	void shouldReturnUserDetailsWithTwoRole() {
		UserDetails userDetails = userDetailsService.loadUserByUsername("admin");

		assertThat(userDetails)
			.matches(user -> "admin".equals(user.getUsername()))
			.matches(user -> "$2a$10$12Kf286jLClqDETm9pVz6OfKfKF6sp03L8L1hdtc92tp0BfUYHEYS".equals(user.getPassword()));
		assertThat(userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
			.hasSize(2)
			.doesNotHaveDuplicates()
			.contains("UserRole", "AdminRole");
	}
}