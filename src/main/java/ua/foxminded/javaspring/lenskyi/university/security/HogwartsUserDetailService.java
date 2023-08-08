package ua.foxminded.javaspring.lenskyi.university.security;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.foxminded.javaspring.lenskyi.university.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class HogwartsUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ua.foxminded.javaspring.lenskyi.university.model.User user = userRepository.findUserByFirstName(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
//        List<String> rolesNames = new ArrayList<>();
//        user.getRoles().forEach(role -> {
//            rolesNames.add(role.getName());
//        });
        UserDetails userDetails = User
                .withUsername(user.getFirstName().toLowerCase())
                .password(user.getFirstName().toLowerCase())
                //.authorities(rolesNames.stream().toArray(String[]::new))
                .authorities("admin")
                .build();
        return userDetails;
    }
}