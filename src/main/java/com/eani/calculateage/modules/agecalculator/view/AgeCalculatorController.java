package com.eani.calculateage.modules.agecalculator.view;

import com.eani.calculateage.modules.agecalculator.data.service.AgeCalculatorService;
import com.eani.calculateage.modules.agecalculator.model.ResponseModel;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController()
public class AgeCalculatorController {
    private AgeCalculatorService ageCalculatorService;

    AgeCalculatorController(AgeCalculatorService ageCalculatorService){
        this.ageCalculatorService = ageCalculatorService;
    }

    @GetMapping(value = "/howold", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity calculateAge(@RequestParam("dob") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dob) throws Exception {
        int age = ageCalculatorService.calculateAge(dob);

        Map<String, Integer> map = new HashMap<>();
        map.put("age", age);

        return ResponseEntity.ok(new ResponseModel<>(true, "", map));
    }
}
