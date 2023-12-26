package com.alibou.security;

import com.alibou.security.auth.AuthenticationService;
import com.alibou.security.auth.RegisterRequest;
import com.alibou.security.repository.UserRepository;
import com.alibou.security.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import static com.alibou.security.user.Role.ADMIN;
import static com.alibou.security.user.Role.MANAGER;

@SpringBootApplication
@AllArgsConstructor
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class SecurityApplication {
    private final UserRepository userRepository;
    private final UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(
            AuthenticationService service
    ) {
        return args -> {


            var admin = RegisterRequest.builder()
//					.firstname("Admin")
//					.lastname("Admin")
                    .username("admin@mail.com")
                    .password("password")
                    .role(ADMIN)
                    .build();
            System.out.println("Admin token: " + service.register(admin).getAccessToken());

            var manager = RegisterRequest.builder()
//					.firstname("Admin")
//					.lastname("Admin")
                    .username("manager@mail.com")
                    .password("password")
                    .role(MANAGER)
                    .build();
            System.out.println("Manager token: " + service.register(manager).getAccessToken());
//            System.out.println(userRepository.findAll());
//            System.out.println(userService.getAll());

        };
    }
}
