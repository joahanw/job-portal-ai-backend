package com.johanwork.job.util;

import com.johanwork.job.constant.AppConstant;
import com.johanwork.job.exception.CustomException;
import org.springframework.http.HttpStatus;

import java.util.LinkedHashMap;
import java.util.Map;

public class Violations {

    private final Map<String, String> violations = new LinkedHashMap<>();

    public Violations check(boolean condition, String field, String message){
        if (condition){
            violations.put(field, message);
        }
        return this;
    }

    public void throwIfAny(){
        if (!violations.isEmpty()){
            throw new CustomException(HttpStatus.BAD_REQUEST,
                    AppConstant.Error.TITLE_BAD_REQUEST,
                    AppConstant.Error.MESSAGE_BAD_REQUEST,
                    violations
            );
        }
    }

}
