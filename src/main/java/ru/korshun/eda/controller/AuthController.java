package ru.korshun.eda.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.korshun.eda.entity.Role;
import ru.korshun.eda.entity.User;
import ru.korshun.eda.repository.UserRepository;
import ru.korshun.eda.requests.SignInRequest;
import ru.korshun.eda.requests.SignUpRequest;
import ru.korshun.eda.response.BaseResponse;
import ru.korshun.eda.response.data.SignInData;
import ru.korshun.eda.tokenData.JwtTokenProvider;
import ru.korshun.eda.utils.Functions;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    final AuthenticationManager authenticationManager;
    final UserRepository userRepository;
    final PasswordEncoder passwordEncoder;
    final JwtTokenProvider tokenProvider;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
                          PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/signin")
    public BaseResponse<?> authenticateUser(@RequestBody SignInRequest signInRequest) {

//        System.out.println("authenticateUser");

        User user = userRepository.findByLogin(signInRequest.getLogin());

        if(user == null) {
            return new BaseResponse<>(HttpStatus.NOT_FOUND, "User not found", null);
        }

        Functions
                .getLogger(AuthController.class)
                .info("Query /auth/signin with login {}, and pass {}",
                        signInRequest.getLogin(), signInRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInRequest.getLogin(),
                        signInRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Functions
                .getLogger(AuthController.class)
                .info("User {} login successfully", signInRequest.getLogin());

        return new BaseResponse<>(HttpStatus.OK, null,
                new SignInData(user.getId(), tokenProvider.generateToken(authentication)));
    }


    @PostMapping("/signup")
    public BaseResponse<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {

        if(userRepository.existsBylogin(signUpRequest.getEmail())) {
            return new BaseResponse<>(HttpStatus.CONFLICT,"Email Address already in use!",null);
        }

        // Creating user's account
        User user = new User(signUpRequest.getName(), signUpRequest.getEmail(), signUpRequest.getPassword(),
                new Role(signUpRequest.getRole()));

        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        userRepository.addUser(signUpRequest.getEmail(), user.getPassword(),
                signUpRequest.getName(), signUpRequest.getRole());
//        userRepository.save(user);

        return new BaseResponse<>(HttpStatus.OK, null, null);

    }


}