package com.core.linkup.common.exception;

import com.core.linkup.common.response.BaseResponse;
import com.core.linkup.common.response.BaseResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler<T> {

    @ExceptionHandler(value = BaseException.class)
    public ResponseEntity<BaseResponse<T>> exception(BaseException exception) {
        log.warn("Exception occurred: HttpStatusCode: {}, message: {}",
                exception.getStatus().getStatusCode(),
                exception.getStatus().getMessage());

        return ResponseEntity
                .status(exception.getStatus().getStatusCode())
                .body(BaseResponse.response(exception.getStatus()));
    }

    // 커스텀으로 처리되지 않은 예외 처리
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<BaseResponse<T>> exception(Exception exception) {
        log.error("InternalServerError occurred: error message: {}",
                exception.getMessage());
        exception.printStackTrace();

        return ResponseEntity
                .internalServerError()
                .body(BaseResponse.response(BaseResponseStatus.SERVER_ERROR));
    }
}
