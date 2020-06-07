package ru.korshun.eda.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.korshun.eda.entity.User;
import ru.korshun.eda.repository.InsertRepository;
import ru.korshun.eda.repository.UserRepository;
import ru.korshun.eda.requests.InsertLocationRequest;
import ru.korshun.eda.response.BaseResponse;
import ru.korshun.eda.service.CustomUserDetailsService;
import ru.korshun.eda.tokenData.JwtTokenProvider;
import ru.korshun.eda.utils.Functions;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserRepository mUserRepository;
    private final InsertRepository mInsertRepository;
    private final JwtTokenProvider tokenProvider;


    final CustomUserDetailsService mCustomUserDetailsService;

    public UserController(UserRepository mUserRepository, InsertRepository insertRepository,
                          CustomUserDetailsService customUserDetailsService, JwtTokenProvider tokenProvider) {
        this.mUserRepository = mUserRepository;
        this.mInsertRepository = insertRepository;
        this.mCustomUserDetailsService = customUserDetailsService;
        this.tokenProvider = tokenProvider;
    }





    @GetMapping("/user/{id}")
//    @PreAuthorize("hasRole('Admin')")
    public BaseResponse<?> getUserName(@PathVariable int id) {
        User user = mUserRepository.findById(id);

        if(user != null) {

//            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//            System.out.println(auth.getAuthorities());

//            UserDetails userDetails = mCustomUserDetailsService.loadUserById(id);
//
//            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
//
//            for(GrantedAuthority role : auth.getAuthorities()) {
//                System.out.println(role.getAuthority());
//            }

            return new BaseResponse<>(HttpStatus.OK, null, user);
        }

        return new BaseResponse<>(HttpStatus.NOT_FOUND,"User not found", null);

    }



    @PutMapping("/put/location")
    public BaseResponse<?> insertLocation(@RequestBody InsertLocationRequest insertLocationRequest) {

//        System.out.println("Token ID: " + tokenProvider.getJwtUserId() + ", insert ID: " + insertLocationRequest.getId());

        if(tokenProvider.getJwtUserId() != insertLocationRequest.getId()) {
            return new BaseResponse<>(HttpStatus.BAD_REQUEST, "Wrong data", null);
        }

        int isGps = insertLocationRequest.getIsGpsEnable() ? 1 : 0;
        int isNW = insertLocationRequest.getIsNetworkEnable() ? 1 : 0;

        if(mInsertRepository.insertLocation(
                insertLocationRequest.getId(),
                insertLocationRequest.getLat(),
                insertLocationRequest.getLon(),
                isGps,
                isNW)) {

            Functions
                .getLogger(UserController.class)
                .info("Query /put/location from {}", insertLocationRequest.getId());

            return new BaseResponse<>(HttpStatus.OK, null, null);

        }

        return new BaseResponse<>(HttpStatus.INTERNAL_SERVER_ERROR,"SQL query error", null);
    }

}
