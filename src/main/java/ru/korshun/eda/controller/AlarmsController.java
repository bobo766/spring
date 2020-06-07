package ru.korshun.eda.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.korshun.eda.entity.Alarms;
import ru.korshun.eda.repository.AlarmsRepository;
import ru.korshun.eda.response.BaseResponse;
import ru.korshun.eda.tokenData.JwtTokenProvider;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/alarms")
public class AlarmsController {

    private final AlarmsRepository mAjaxRepository;
    private final JwtTokenProvider tokenProvider;

    public AlarmsController(AlarmsRepository mAjaxRepository, JwtTokenProvider tokenProvider) {
        this.mAjaxRepository = mAjaxRepository;
        this.tokenProvider = tokenProvider;
    }

    @GetMapping("/getAllAlarms")
    private BaseResponse<?> getAlarms() {
//        Functions
//                .getLogger(AjaxController.class)
//                .info("Query /ajax/alarms");
        List<Alarms> alarms;

        try {
            alarms = mAjaxRepository.findAll();
        } catch (Exception e) {
            return new BaseResponse<>(HttpStatus.BAD_REQUEST, "Wrong data", null);
        }

        return new BaseResponse<>(HttpStatus.OK, null, alarms);
    }

    @GetMapping("/getGbrAlarm/{id}")
    private BaseResponse<?> getGbrAlarm(@PathVariable int id) {
//        Functions
//                .getLogger(AjaxController.class)
//                .info("Query /ajax/alarms");

        if(tokenProvider.getJwtUserId() != id) {
            return new BaseResponse<>(HttpStatus.BAD_REQUEST, "Wrong data", null);
        }

        List<AlarmsRepository.ForGbrOnly> alarms;

        try {
            alarms = mAjaxRepository.getGbrAlarms(id);
        } catch (Exception e) {
            return new BaseResponse<>(HttpStatus.BAD_REQUEST, "Wrong data", null);
        }

        return new BaseResponse<>(HttpStatus.OK, null, alarms);
    }

}
