package com.alibou.security;

import com.alibou.security.auth.AuthenticationService;
import com.alibou.security.auth.RegisterRequest;
import com.alibou.security.entity.Category;
import com.alibou.security.entity.ItemType;
import com.alibou.security.repository.CategoryRepository;
import com.alibou.security.repository.ItemTypeRepository;
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
    private final CategoryRepository categoryRepository;
    private final ItemTypeRepository itemTypeRepository;

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


            ItemType itemType = new ItemType();
            itemType.setName("45.06");
            ItemType itemType1 = new ItemType();
            itemType1.setName("47.06");

            itemTypeRepository.save(itemType);
            itemTypeRepository.save(itemType1);

            Category category = new Category();
            category.setName("salom");
            Category category1 = new Category();
            category1.setName("salom1");

            categoryRepository.save(category1);
            categoryRepository.save(category);


//            System.out.println(userRepository.findAll());
//            System.out.println(userService.getAll());

        };
    }
}
