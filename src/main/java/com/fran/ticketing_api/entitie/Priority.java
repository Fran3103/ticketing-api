package com.fran.ticketing_api.entitie;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Locale;

public enum Priority {
    LOW,
    MEDIUM,
    HIGH,
    URGENT;


    @JsonCreator
    public static Priority from(String value){
        if(value== null) return  null;

        return Priority.valueOf(value.trim().toUpperCase(Locale.ROOT).replace('-','_'));

    }

    @JsonValue
    public String toJson(){
        return name();
    }
}
