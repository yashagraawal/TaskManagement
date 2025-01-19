package com.tms.component;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.tms.model.User;
import com.tms.repository.UserRepository;

@Component
public class DatabaseSeeder {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository) {
        return args -> {
            // Delete all existing data (optional)
            userRepository.deleteAll();

            // Add default user data
            User user1 = new User();
            user1.setUserName("admin");
            user1.setPassword("$2a$12$bHLJgsrU6cQlo2O3DstcneYema9m1jmhD6EcAsHqC1pYxQDXUbxnC"); // "admin" in BCrypt Hash

            userRepository.save(user1);
            userRepository.save(user2);

            System.out.println("Database initialized with user data.");
        };
    }
}