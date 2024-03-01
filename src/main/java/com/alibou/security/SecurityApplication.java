package com.alibou.security;

import com.alibou.security.auth.AuthenticationService;
import com.alibou.security.auth.RegisterRequest;
import com.alibou.security.entity.Category;
import com.alibou.security.entity.ItemType;
import com.alibou.security.entity.MaterialCategory;
import com.alibou.security.entity.MaterialType;
import com.alibou.security.repository.*;
import com.alibou.security.user.User;
import com.alibou.security.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

import static com.alibou.security.user.Role.ADMIN;

@SpringBootApplication
@AllArgsConstructor
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class SecurityApplication {
    private final UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(
            AuthenticationService service
    ) {
        return args -> {
            Optional<User> user = userRepository.findByUsername("admin@mail.com");
            if (user.isEmpty()) {
                var admin = RegisterRequest.builder()
                        .username("admin@mail.com")
                        .password("password")
                        .role(ADMIN)
                        .build();
                System.out.println("Admin token: " + service.register(admin).getAccessToken());
            } else {
                User user1 = user.get();
                System.out.println("Admin token: " + user1.getTokens().get(0).token);


            }


        };
    }
}
