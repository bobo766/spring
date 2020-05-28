package ru.korshun.eda.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.korshun.eda.entity.User;
import ru.korshun.eda.repository.UserRepository;
import ru.korshun.eda.response.BaseResponse;
import ru.korshun.eda.service.CustomUserDetailsService;

import java.util.Collection;

@RestController
@RequestMapping("/api")
public class UserController {

    final UserRepository mUserRepository;

    final CustomUserDetailsService mCustomUserDetailsService;

    public UserController(UserRepository mUserRepository, CustomUserDetailsService customUserDetailsService) {
        this.mUserRepository = mUserRepository;
        this.mCustomUserDetailsService = customUserDetailsService;
    }

    @GetMapping("/user/{id}")
//    @PreAuthorize("hasRole('Admin')")
    public BaseResponse<?> getUserName(@PathVariable int id) {
        User user = mUserRepository.findById(id);

        if(user != null) {

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//            System.out.println(auth.getAuthorities());

//            UserDetails userDetails = mCustomUserDetailsService.loadUserById(id);
//
//            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
//
            for(GrantedAuthority role : auth.getAuthorities()) {
                System.out.println(role.getAuthority());
            }

            return new BaseResponse<>(HttpStatus.OK, null, user);
        }

        return new BaseResponse<>(HttpStatus.NOT_FOUND, null, "User not found");
    }

}
