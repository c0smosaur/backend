package com.core.linkup.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.core.linkup.common.response.BaseResponseStatus.SUCCESS;

@Getter
@AllArgsConstructor
public class BaseResponse<T> {

    private boolean isSuccess;
    private int statusCode;
    private String message;
    private BaseResponseStatus status;
    // null이면 json에 포함 x 옵션
    // @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;


    public static <T> BaseResponse<T> response(T data) {
        return new BaseResponse<>(
                SUCCESS.isSuccess(),
                SUCCESS.getStatusCode(),
                SUCCESS.getMessage(),
                SUCCESS,
                data
        );
    }

    public static <T> BaseResponse<T> response(BaseResponseStatus status) {
        return new BaseResponse<>(
                status.isSuccess(),
                status.getStatusCode(),
                status.getMessage(),
                status,
                null
        );
    }
}