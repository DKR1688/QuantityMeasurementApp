package com.quantity_measurement_app.config;

import com.quantity_measurement_app.model.User;
import com.quantity_measurement_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminUserInitializer implements CommandLineRunner {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final String adminEmail;
	private final String adminPassword;

	public AdminUserInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder,
			@Value("${app.admin.email:admin@gmail.com}") String adminEmail,
			@Value("${app.admin.password:123456}") String adminPassword) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.adminEmail = adminEmail;
		this.adminPassword = adminPassword;
	}

	@Override
	public void run(String... args) {
		User adminUser = userRepository.findByEmail(adminEmail).orElseGet(User::new);

		adminUser.setEmail(adminEmail);
		adminUser.setRole("ROLE_ADMIN");
		adminUser.setProvider("LOCAL");

		String password = adminUser.getPassword();
		if (password == null || password.isBlank() || !password.startsWith("$2")) {
			adminUser.setPassword(passwordEncoder.encode(adminPassword));
		}

		userRepository.save(adminUser);
	}
}
