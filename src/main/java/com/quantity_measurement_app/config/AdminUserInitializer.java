package com.quantity_measurement_app.config;

import com.quantity_measurement_app.model.User;
import com.quantity_measurement_app.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminUserInitializer implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(AdminUserInitializer.class);

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final String adminEmail;
	private final String adminPassword;

	public AdminUserInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder,
			@Value("${app.admin.email:}") String adminEmail,
			@Value("${app.admin.password:}") String adminPassword) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.adminEmail = adminEmail;
		this.adminPassword = adminPassword;
	}

	@Override
	public void run(String... args) {
		if (adminEmail == null || adminEmail.isBlank() || adminPassword == null || adminPassword.isBlank()) {
			log.info("Admin bootstrap skipped: set app.admin.email and app.admin.password to create an admin user.");
			return;
		}

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
