package com.eani.calculateage.modules.agecalculator.data.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface AgeCalculatorService {
    int calculateAge(LocalDate dob) throws Exception;
}
