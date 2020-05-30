package ru.otus.krivonos.library.security.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.otus.krivonos.library.security.dao.UserRepository;
import ru.otus.krivonos.library.security.model.ApplicationUser;

import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class DbUserDetailsService implements UserDetailsService {
	public static final Logger LOG = LoggerFactory.getLogger(DbUserDetailsService.class);

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		LOG.debug(passwordEncoder.encode("user"));
		LOG.debug("method=loadUserByUsername action=\"аутентификация пользователя '{}'\"", login);

		ApplicationUser applicationUser = userRepository.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException(login));

		LOG.debug("method=loadUserByUsername action=\"найден пользователь '{}'\"", applicationUser);

		return new User(
			applicationUser.getLogin(),
			applicationUser.getPassword(),
			applicationUser.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName()))
				.collect(Collectors.toSet()));
	}
}
