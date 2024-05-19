package com.core.linkup.common.exception;

import com.core.linkup.common.response.BaseResponseStatus;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {
    private final BaseResponseStatus status;
    private String message;

    public BaseException(BaseResponseStatus status) {
        this.status = status;
        this.message = "";
    }

    public BaseException(BaseResponseStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    @Override
    public String getMessage() {
        if(message.isEmpty()){
            return status.getMessage();
        }
        return String.format("message: %s, \ndescription: %s", status.getMessage(), message);
    }
}
