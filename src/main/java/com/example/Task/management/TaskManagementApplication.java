package com.example.Task.management;

//import com.example.Todo.management.config.DefaultAdminAndUsersConfig;
import com.example.Task.management.config.DefaultAdminAndUsersConfig;
import com.example.Task.management.model.Role;
import com.example.Task.management.repository.RoleRepository;
import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;


@SpringBootApplication
@Import(DefaultAdminAndUsersConfig.class)
public class TaskManagementApplication implements CommandLineRunner {
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(TaskManagementApplication.class, args);
	}
	@Autowired
	private RoleRepository roleRepository;
	@Override
	public void run(String... args) throws Exception {
		if (roleRepository.count() == 0) {
			Role adminRole = new Role();
			adminRole.setName("ROLE_ADMIN");
			roleRepository.save(adminRole);

			Role userRole = new Role();
			userRole.setName("ROLE_USER");
			roleRepository.save(userRole);
		}
	}
}
