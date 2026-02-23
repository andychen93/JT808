package com.example.jt808.config;

import com.example.jt808.domain.Permission;
import com.example.jt808.domain.Role;
import com.example.jt808.domain.User;
import com.example.jt808.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EntityManager entityManager;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder, EntityManager entityManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (userRepository.findByUsername("admin").isPresent()) {
            return;
        }

        Permission p1 = new Permission();
        p1.setCode("vehicle:view");
        p1.setName("车辆查看");
        entityManager.persist(p1);

        Permission p2 = new Permission();
        p2.setCode("protocol:parse");
        p2.setName("协议解析");
        entityManager.persist(p2);

        Role adminRole = new Role();
        adminRole.setCode("ADMIN");
        adminRole.setName("系统管理员");
        adminRole.setPermissions(Set.of(p1, p2));
        entityManager.persist(adminRole);

        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("Admin@123"));
        admin.setEnabled(true);
        admin.setRoles(Set.of(adminRole));
        userRepository.save(admin);
    }
}
