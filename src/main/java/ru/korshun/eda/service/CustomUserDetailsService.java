package ru.korshun.eda.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.korshun.eda.UserPrincipal;
import ru.korshun.eda.entity.User;
import ru.korshun.eda.repository.UserRepository;

@Service
public class CustomUserDetailsService
        implements UserDetailsService {

    final UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login)
            throws UsernameNotFoundException {

        User user = userRepository.findByLogin(login);

        if(user == null) {
            logger.error("User not found with email {}", login);
            throw new UsernameNotFoundException("User not found with login: " + login);
        }

//        System.out.println((user.getRoles()).getAuthority());

        return UserPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadUserById(int id) {
        User user = userRepository.findById(id);

        if(user == null) {
            logger.error("User not found with id {}", id);
            throw new UsernameNotFoundException("User not found with id: " + id);
        }

        return UserPrincipal.create(user);
    }
}
