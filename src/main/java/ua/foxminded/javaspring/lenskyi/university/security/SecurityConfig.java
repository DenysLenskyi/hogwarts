package ua.foxminded.javaspring.lenskyi.university.security;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import ua.foxminded.javaspring.lenskyi.university.repository.UserRepository;

@Configuration
@EnableWebSecurity
@Transactional
public class SecurityConfig {

    @Autowired
    private UserRepository userRepository;

    @Bean
    public UserDetailsManager userDetailsService() {
        UserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
        userRepository.findAll().forEach(u -> {
            UserDetails userDetails = User.withUsername(u.getFirstName() + u.getLastName())
                    .password(passwordEncoder().encode(u.getFirstName() + u.getLastName()))
                    .roles(u.getRoles().stream().findFirst().get().getName())
                    .build();
            userDetailsManager.createUser(userDetails);
        });
        UserDetails user = User.withUsername("user")
                .password(passwordEncoder().encode("user"))
                .roles("user")
                .build();
        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("admin")
                .build();
        userDetailsManager.createUser(user);
        userDetailsManager.createUser(admin);
        return userDetailsManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}