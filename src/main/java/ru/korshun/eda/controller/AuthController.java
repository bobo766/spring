package ru.korshun.eda.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.korshun.eda.entity.Role;
import ru.korshun.eda.entity.User;
import ru.korshun.eda.repository.UserRepository;
import ru.korshun.eda.requests.SignInRequest;
import ru.korshun.eda.requests.SignUpRequest;
import ru.korshun.eda.response.BaseResponse;
import ru.korshun.eda.response.data.SignInDataResponse;
import ru.korshun.eda.tokenData.JwtTokenProvider;
import ru.korshun.eda.utils.Functions;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@CrossOrigin("*")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    final AuthenticationManager authenticationManager;
    final UserRepository userRepository;
//    final PasswordEncoder passwordEncoder;
    final JwtTokenProvider tokenProvider;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
                          JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/signin")
    public BaseResponse<SignInDataResponse> authenticateUser(@RequestBody SignInRequest signInRequest) {

//        System.out.println("authenticateUser");

//        Functions
//                .getLogger(AuthController.class)
//                .info("Query /auth/signin with login {}, and pass {}",
//                        signInRequest.getPhone(), signInRequest.getPassword());
        logger.error("Query /auth/signin with login {}, and pass {}",
                        signInRequest.getPhone(), signInRequest.getPassword());

        User user = userRepository.findByPhone(signInRequest.getPhone());

        if(user == null) {
//            Functions
//                    .getLogger(AuthController.class)
//                    .info("User {} not found", signInRequest.getPhone());

            logger.error("User {} not found", signInRequest.getPhone());

            return new BaseResponse<>(HttpStatus.NOT_FOUND, "User not found", null);
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInRequest.getPhone(),
                        signInRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

//        Functions
//                .getLogger(AuthController.class)
//                .info("User {} ({}) login successfully", signInRequest.getPhone(), user.getRole().getAuthority());

        logger.info("User {} ({}) login successfully", signInRequest.getPhone(), user.getRole().getAuthority());

        return new BaseResponse<>(HttpStatus.OK, null,
                new SignInDataResponse(user.getId(), tokenProvider.generateToken(authentication),
                        user.getRole().getAuthority()));
    }


//    @PostMapping("/signup")
//    public BaseResponse<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
//
////        Functions
////                .getLogger(AuthController.class)
////                .info("Try to register {}", signUpRequest.getPhone());
//        logger.info("Try to register {}", signUpRequest.getPhone());
//
//        if(userRepository.existsByPhone(signUpRequest.getPhone())) {
////            Functions
////                    .getLogger(AuthController.class)
////                    .info("Phone {} already in use", signUpRequest.getPhone());
//            logger.error("Phone {} already in use", signUpRequest.getPhone());
//
//            return new BaseResponse<>(HttpStatus.CONFLICT,"Phone already in use!",null);
//        }
//
//        // Creating user's account
//        User user = new User(signUpRequest.getName(), signUpRequest.getPassword(), signUpRequest.getPhone(),
//                new Role(signUpRequest.getRole()));
//
//        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
//
//        userRepository.addUser(user.getPassword(), signUpRequest.getName(), signUpRequest.getPhone(),
//                signUpRequest.getRole());
////        userRepository.save(user);
//
////        Functions
////                .getLogger(AuthController.class)
////                .info("{} register successfully", signUpRequest.getPhone());
//        logger.info("{} register successfully", signUpRequest.getPhone());
//
//
//        return new BaseResponse<>(HttpStatus.OK, null, null);
//
//    }


}