package ua.foxminded.javaspring.lenskyi.university.security;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.foxminded.javaspring.lenskyi.university.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class HogwartsUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ua.foxminded.javaspring.lenskyi.university.model.User user = userRepository.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        List<String> rolesNames = new ArrayList<>();
        user.getRoles().forEach(role -> {
            rolesNames.add(role.getName());
        });
        UserDetails userDetails = User
                .withUsername(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .authorities(rolesNames.stream().toArray(String[]::new))
                .roles(rolesNames.stream().toArray(String[]::new))
                .build();
        return userDetails;
    }
}