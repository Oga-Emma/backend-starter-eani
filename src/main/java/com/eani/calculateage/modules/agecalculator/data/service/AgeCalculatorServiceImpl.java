package com.eani.calculateage.modules.agecalculator.data.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class AgeCalculatorServiceImpl implements AgeCalculatorService {
    @Override
    public int calculateAge(LocalDate dob) throws Exception {
        LocalDate now = LocalDate.now();

         if(now.isBefore(dob)){
             throw new Exception("Date of birth cannot be in the future");
         }

         return now.getYear() - dob.getYear();
    }
}
