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

    private final AlarmsRepository mAlarmsRepository;
    private final JwtTokenProvider tokenProvider;

    public AlarmsController(AlarmsRepository mAjaxRepository, JwtTokenProvider tokenProvider) {
        this.mAlarmsRepository = mAjaxRepository;
        this.tokenProvider = tokenProvider;
    }

    @GetMapping("/getAllAlarms")
    private BaseResponse<?> getAllAlarms() {

        List<Alarms> alarms;

        try {
            alarms = mAlarmsRepository.getAllAlarms();
        } catch (Exception e) {
            return new BaseResponse<>(HttpStatus.BAD_REQUEST, "Wrong data", null);
        }

        return new BaseResponse<>(HttpStatus.OK, null, alarms);
    }




    @GetMapping("/getGbr")
    public BaseResponse<List<AlarmsRepository.Gbr>> getGbr() {
        List<AlarmsRepository.Gbr> gbrList = mAlarmsRepository.findAllGbr();

        if(gbrList != null) {
            return new BaseResponse<>(HttpStatus.OK, null, gbrList);
        }

        return new BaseResponse<>(HttpStatus.NOT_FOUND,"getGbr() error", null);

    }



    @GetMapping("/finishAlarm/{idUser}/{idGbr}")
    public BaseResponse<Void> finishAlarm(@PathVariable int idUser, @PathVariable int idGbr) {

        try {
            mAlarmsRepository.finishAlarm(idUser, idGbr);
            return new BaseResponse<>(HttpStatus.OK, null, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse<>(HttpStatus.BAD_REQUEST, "Wrong data", null);
        }

    }


//    @GetMapping("/getGbrAlarm/{id}")
//    private BaseResponse<?> getGbrAlarm(@PathVariable int id) {
//
//        if(tokenProvider.getJwtUserId() != id) {
//            return new BaseResponse<>(HttpStatus.BAD_REQUEST, "Wrong data", null);
//        }
//
//        List<AlarmsRepository.ForGbrOnly> alarms;
//
//        try {
//            alarms = mAlarmsRepository.getGbrAlarms(id);
//        } catch (Exception e) {
//            return new BaseResponse<>(HttpStatus.BAD_REQUEST, "Wrong data", null);
//        }
//
//        return new BaseResponse<>(HttpStatus.OK, null, alarms);
//    }

}
