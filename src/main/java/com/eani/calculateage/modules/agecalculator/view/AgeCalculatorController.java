package com.eani.calculateage.modules.agecalculator.view;

import com.eani.calculateage.modules.agecalculator.data.service.AgeCalculatorService;
import com.eani.calculateage.modules.agecalculator.model.ResponseModel;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController()
public class AgeCalculatorController {
    private final AgeCalculatorService ageCalculatorService;
    private final Bucket bucket;
    AgeCalculatorController(AgeCalculatorService ageCalculatorService,
                            @Value("${appdata.rateLimit}") int rate,
                            @Value("${appdata.rateLimitDurationInSeconds}") int rateLimitDurationInSeconds){
        this.ageCalculatorService = ageCalculatorService;

        //setup rate limiting
        Bandwidth limit = Bandwidth.classic(rate, Refill.greedy(rate, Duration.ofSeconds(rateLimitDurationInSeconds)));
        this.bucket = Bucket4j.builder()
                .addLimit(limit)
                .build();
    }

    @GetMapping(value = "/howold", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity calculateAge(@RequestParam("dob") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dob) throws Exception {

        if (!bucket.tryConsume(1)) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(
                    new ResponseModel<>(false, "Too many request, you can only make 3 api calls per second", null)
            );
        }

        int age = ageCalculatorService.calculateAge(dob);

        Map<String, Integer> map = new HashMap<>();
        map.put("age", age);

        return ResponseEntity.ok(new ResponseModel<>(true, "Age calculated", map));
    }
}
