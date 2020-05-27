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
import ru.korshun.eda.repository.UserRepository;
import ru.korshun.eda.requests.LoginRequest;
import ru.korshun.eda.response.BaseResponse;
import ru.korshun.eda.response.data.LoginData;
import ru.korshun.eda.tokenData.JwtAuthenticationEntryPoint;
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
    public BaseResponse<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Functions
                .getLogger(JwtAuthenticationEntryPoint.class)
                .info("Query /auth/signin with login {}, and pass {}",
                        loginRequest.getLogin(), loginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getLogin(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Functions
                .getLogger(JwtAuthenticationEntryPoint.class)
                .info("User {} login successfully", loginRequest.getLogin());

        return new BaseResponse<>(HttpStatus.OK, null,
                new LoginData(tokenProvider.generateToken(authentication)));
    }

}