package ru.korshun.eda.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.korshun.eda.entity.User;
import ru.korshun.eda.repository.UserRepository;
import ru.korshun.eda.response.BaseResponse;

@RestController
@RequestMapping("/api")
public class UserController {

    final UserRepository mUserRepository;

    public UserController(UserRepository mUserRepository) {
        this.mUserRepository = mUserRepository;
    }

    @GetMapping("/user/{id}")
    public BaseResponse<?> getUserName(@PathVariable Long id) {
        User user = mUserRepository.findById(id);
        if(user != null) {
            return new BaseResponse<>(HttpStatus.OK, null, user);
        }
        return new BaseResponse<>(HttpStatus.NOT_FOUND, null, "User not found");
    }

}
