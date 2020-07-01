package ru.otus.krivonos.library.security.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthorizationController {

	@PostMapping("/roles")
	public List<String> roles() {
		return SecurityContextHolder
			.getContext()
			.getAuthentication()
			.getAuthorities()
			.stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.toList());
	}
}
