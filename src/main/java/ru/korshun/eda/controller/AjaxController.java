package ru.korshun.eda.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.korshun.eda.entity.Ajax;
import ru.korshun.eda.repository.AjaxRepository;
import ru.korshun.eda.response.BaseResponse;
import ru.korshun.eda.tokenData.JwtAuthenticationEntryPoint;
import ru.korshun.eda.utils.Functions;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/ajax")
public class AjaxController {

    private final AjaxRepository mAjaxRepository;

    public AjaxController(AjaxRepository mAjaxRepository) {
        this.mAjaxRepository = mAjaxRepository;
    }

    @GetMapping("/alarms")
    private BaseResponse<?> getAlarms() {
//        Functions
//                .getLogger(AjaxController.class)
//                .info("Query /ajax/alarms");
        List<Ajax> alarms = mAjaxRepository.findAll();

        return new BaseResponse<>(HttpStatus.OK, null, alarms);
    }
}
