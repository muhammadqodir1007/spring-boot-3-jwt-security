package com.alibou.security;

import com.alibou.security.auth.AuthenticationService;
import com.alibou.security.auth.RegisterRequest;
import com.alibou.security.entity.Category;
import com.alibou.security.entity.ItemType;
import com.alibou.security.entity.MaterialCategory;
import com.alibou.security.entity.MaterialType;
import com.alibou.security.repository.*;
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
    private final MaterialTypeRepository materialTypeRepository;
    private final MaterialCategoryRepository materialCategoryRepository;

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

            MaterialType maaterialType = new MaterialType();
            maaterialType.setName("45.06");
            MaterialType materialType1 = new MaterialType();
            materialType1.setName("47.06");

            materialTypeRepository.save(maaterialType);
            materialTypeRepository.save(materialType1);

            MaterialCategory materialCategory = new MaterialCategory();
            materialCategory.setName("salom");
            MaterialCategory materialCategory1 = new MaterialCategory();
            materialCategory1.setName("salom1");

            materialCategoryRepository.save(materialCategory1);
            materialCategoryRepository.save(materialCategory);


//            System.out.println(userRepository.findAll());
//            System.out.println(userService.getAll());

        };
    }
}
