package com.fran.ticketing_api.entitie;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Locale;

public enum Status {

    OPEN,
    CLOSED,
    IN_PROGRESS,
    RESOLVED,
    REOPENED;

    @JsonCreator
    public static Status from(String value){
        if(value== null) return  null;

        return Status.valueOf(value.trim().toUpperCase(Locale.ROOT).replace('-','_'));

    }

    @JsonValue
    public String toJson(){
        return name();
    }
}
