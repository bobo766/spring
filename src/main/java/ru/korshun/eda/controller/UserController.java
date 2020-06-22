package ru.korshun.eda.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.korshun.eda.repository.InsertRepository;
import ru.korshun.eda.requests.InsertLocationRequest;
import ru.korshun.eda.response.BaseResponse;
import ru.korshun.eda.service.CustomUserDetailsService;
import ru.korshun.eda.tokenData.JwtTokenProvider;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

//    private final UserRepository mUserRepository;
    private final InsertRepository mInsertRepository;
    private final JwtTokenProvider tokenProvider;


    final CustomUserDetailsService mCustomUserDetailsService;

    public UserController(InsertRepository insertRepository, CustomUserDetailsService customUserDetailsService,
                          JwtTokenProvider tokenProvider) {
//        this.mUserRepository = mUserRepository;
        this.mInsertRepository = insertRepository;
        this.mCustomUserDetailsService = customUserDetailsService;
        this.tokenProvider = tokenProvider;
    }



//    @GetMapping("/user/{id}")
////    @PreAuthorize("hasRole('Admin')")
//    public BaseResponse<?> getUserName(@PathVariable int id) {
//        User user = mUserRepository.findById(id);
//
//        if(user != null) {
//
////            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
////            System.out.println(auth.getAuthorities());
//
////            UserDetails userDetails = mCustomUserDetailsService.loadUserById(id);
////
////            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
////
////            for(GrantedAuthority role : auth.getAuthorities()) {
////                System.out.println(role.getAuthority());
////            }
//
//            return new BaseResponse<>(HttpStatus.OK, null, user);
//        }
//
//        return new BaseResponse<>(HttpStatus.NOT_FOUND,"User not found", null);
//
//    }



    @PutMapping("/put/location")
    public BaseResponse<?> insertLocation(@RequestBody InsertLocationRequest insertLocationRequest) {

//        System.out.println("Token ID: " + tokenProvider.getJwtUserId() + ", insert ID: " + insertLocationRequest.getId());

        if(tokenProvider.getJwtUserId() != insertLocationRequest.getId()) {
//            Functions
//                    .getLogger(UserController.class)
//                    .info("Query /put/location from {} failed: Wrong data", insertLocationRequest.getId());
            logger.error("Query /put/location from {} failed: Wrong data", insertLocationRequest.getId());
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

//            Functions
//                .getLogger(UserController.class)
//                .info("Query /put/location from {} success", insertLocationRequest.getId());
            logger.info("Query /put/location from {} success", insertLocationRequest.getId());
            return new BaseResponse<>(HttpStatus.OK, null, null);

        }

        return new BaseResponse<>(HttpStatus.BAD_REQUEST,"SQL query error", null);
    }

}
