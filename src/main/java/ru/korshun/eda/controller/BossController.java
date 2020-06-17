package ru.korshun.eda.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.korshun.eda.entity.Role;
import ru.korshun.eda.entity.User;
import ru.korshun.eda.repository.AlarmsRepository;
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

    public BossController(BossRepository bossRepository, PasswordEncoder passwordEncoder) {
        this.bossRepository = bossRepository;
        this.passwordEncoder = passwordEncoder;
    }



    @GetMapping("/getRoles")
    public BaseResponse<List<BossRepository.Roles>> getRoles() {
        List<BossRepository.Roles> roleList = bossRepository.findRoles();

        if(roleList != null) {
            return new BaseResponse<>(HttpStatus.OK, null, roleList);
        }

        return new BaseResponse<>(HttpStatus.NOT_FOUND,"getRoles() error", null);

    }




//    @PostMapping("/signup")
//    public BaseResponse<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
//
//        logger.info("Try to register {}", signUpRequest.getPhone());
//
//        if(bossRepository.existsByPhone(signUpRequest.getPhone())) {
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
//        bossRepository.addUser(user.getPassword(), signUpRequest.getName(), signUpRequest.getPhone(),
//                signUpRequest.getRole());
//        logger.info("{} register successfully", signUpRequest.getPhone());
//
//        return new BaseResponse<>(HttpStatus.OK, null, null);
//
//    }

}
