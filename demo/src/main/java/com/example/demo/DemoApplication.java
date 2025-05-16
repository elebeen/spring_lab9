package com.example.demo;

// import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.context.annotation.Bean;
// import org.springframework.security.crypto.password.PasswordEncoder;

// import com.example.demo.model.User;
// import com.example.demo.repository.UserRepository;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	// @Bean
    // CommandLineRunner init(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    //     return args -> {
    //         User user = new User();
    //         user.setUsername("testuser");
    //         user.setPassword(passwordEncoder.encode("password123"));
    //         user.setRole("USER");
    //         userRepository.save(user);
    //     };
    // }
}
