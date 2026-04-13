package com.quantity_measurement_app.security;

import com.quantity_measurement_app.model.User;
import com.quantity_measurement_app.repository.UserRepository;
import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository repo;

	public CustomUserDetailsService(UserRepository repo) {
		this.repo = repo;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = repo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

		return org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
				.password(user.getPassword() == null ? "" : user.getPassword())
				.authorities(List.of(new SimpleGrantedAuthority(user.getRole())))
				.build();
	}
}
