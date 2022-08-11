package com.eani.calculateage.modules.agecalculator.model;

import lombok.Data;

@Data
public class ResponseModel<T> {
    boolean success;
    String message;
    T data;

    public ResponseModel(boolean success, String message, T data){
        this.success = success;
        this.message = message;
        this.data = data;
    }
}
