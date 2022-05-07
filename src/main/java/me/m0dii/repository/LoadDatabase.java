package me.m0dii.repository;

import me.m0dii.models.ERole;
import me.m0dii.models.Role;
import me.m0dii.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Configuration
public class LoadDatabase
{
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
    
    @Autowired
    PasswordEncoder encoder;
    
    @Bean
    CommandLineRunner preloadRoles(RoleRepository roleRepo, UserRepository userRepo)
    {
        return args -> {
            preloadRole(roleRepo, ERole.ROLE_USER);
            preloadRole(roleRepo, ERole.ROLE_MOD);
            preloadRole(roleRepo, ERole.ROLE_ADMIN);
            
            Set<Role> roles = new HashSet<>();
    
            roleRepo.findByName(ERole.ROLE_ADMIN).ifPresent(roles::add);
            
            Optional<Role> userRole = roleRepo.findByName(ERole.ROLE_USER);
            
            userRole.ifPresent(roles::add);
            
            log.info("Preloading user:" + userRepo.save(
                    new User("admin", "admin@admin.com", encoder.encode("admin123"), roles)));
    
            userRole.ifPresent(role -> {
                log.info("Preloading user:" + userRepo.save(
                        new User("user", "user@user.com", encoder.encode("user123"), role)));
            });
        };
    }
    
    void preloadRole(RoleRepository repo, ERole role)
    {
        if (repo.findByName(role).isPresent())
        {
            return;
        }

        log.info("Preloading role: " + repo.save(new Role(role)));
    }
}
