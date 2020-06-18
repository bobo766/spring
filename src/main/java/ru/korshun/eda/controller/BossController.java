package ru.korshun.eda.controller;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.korshun.eda.entity.Role;
import ru.korshun.eda.entity.User;
import ru.korshun.eda.repository.BossRepository;
import ru.korshun.eda.requests.SignUpRequest;
import ru.korshun.eda.response.BaseResponse;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/boss")
public class BossController {

    private static final Logger logger = LoggerFactory.getLogger(BossController.class);

    final BossRepository bossRepository;
    final PasswordEncoder passwordEncoder;

    @Value("${app.generatePassLength}")
    private int passLength;

    public BossController(BossRepository bossRepository, PasswordEncoder passwordEncoder) {
        this.bossRepository = bossRepository;
        this.passwordEncoder = passwordEncoder;
    }



    @GetMapping("/getAllUsers")
    public BaseResponse<List<BossRepository.Users>> getAllUsers() {
        List<BossRepository.Users> usersList = bossRepository.findAllUsers();

        if(usersList != null) {
            return new BaseResponse<>(HttpStatus.OK, null, usersList);
        }

        return new BaseResponse<>(HttpStatus.NOT_FOUND,"getRoles() error", null);

    }



    @GetMapping("/getRoles")
    public BaseResponse<List<BossRepository.Roles>> getRoles() {
        List<BossRepository.Roles> roleList = bossRepository.findRoles();

        if(roleList != null) {
            return new BaseResponse<>(HttpStatus.OK, null, roleList);
        }

        return new BaseResponse<>(HttpStatus.NOT_FOUND,"getRoles() error", null);

    }




    @PostMapping("/signup")
    public BaseResponse<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {

        logger.info("Try to register {}", signUpRequest.getPhone());

        if(bossRepository.existsByPhone(signUpRequest.getPhone())) {
            logger.error("Phone {} already in use", signUpRequest.getPhone());

            return new BaseResponse<>(HttpStatus.CONFLICT,"Phone already in use!",null);
        }

        String password = RandomStringUtils.randomNumeric(passLength);
        logger.info("pass: {}", password);

        // Creating user's account
        User user = new User(signUpRequest.getName(), passwordEncoder.encode(password), signUpRequest.getPhone(),
                new Role(signUpRequest.getRole()));

//        user.setPassword(passwordEncoder.encode(password));

        bossRepository.addUser(user.getPassword(), signUpRequest.getName(), signUpRequest.getPhone(),
                signUpRequest.getRole());
        logger.info("{} register successfully", signUpRequest.getPhone());

        return new BaseResponse<>(HttpStatus.OK, null, null);

    }

}
