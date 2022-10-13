package com.example.proj;

import com.example.proj.entity.Category;
import com.example.proj.entity.Role;
import com.example.proj.entity.User;
import com.example.proj.repository.CategoryRepository;
import com.example.proj.repository.RoleRepository;
import com.example.proj.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@SpringBootApplication
@EnableWebMvc
public class ProjApplication implements CommandLineRunner {
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	UserService userService;

	CategoryRepository categoryRepository;
	public static void main(String[] args) {

		SpringApplication.run(ProjApplication.class, args);

	}
	@Bean
	public static BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Autowired
	private final PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) throws Exception {

		Role usrRole = new Role();
		usrRole.setName("ROLE_USER");
		Role adminRole = new Role();
		adminRole.setName("ROLE_ADMIN");
		roleRepository.save(usrRole);
		roleRepository.save(adminRole);

		Category category1 = new Category();
		category1.setName("Laptop");

		Category category2 = new Category();
		category2.setName("Smartphone");

		Category category3 = new Category();
		category3.setName("Television");

		categoryRepository.save(category1);
		categoryRepository.save(category2);
		categoryRepository.save(category3);

		User admin = new User();
		admin.setFirstname("Amir");
		admin.setLastname("Ergaliev");
		List<Role>roles = new ArrayList<>();
		Role role = roleRepository.findRoleByName("ROLE_ADMIN");
		roles.add(role);
		admin.setRoles(roles);
		admin.setEmail("AmirKing@mail.ru");
		admin.setPassword(passwordEncoder.encode("1234"));
		userService.saveUser(admin);

	}
}
