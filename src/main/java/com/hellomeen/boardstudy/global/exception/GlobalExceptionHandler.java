package com.hellomeen.boardstudy.global.exception;

import com.hellomeen.boardstudy.global.dto.ApiResponseDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.concurrent.RejectedExecutionException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class})
    public ApiResponseDto<?> handleIllegalArgumentException(IllegalArgumentException ex){
        return new ApiResponseDto<>(HttpStatus.BAD_REQUEST.value(),ex.getMessage());
    }
    @ExceptionHandler({NullPointerException.class})
    public ApiResponseDto<?> handleNullPointerException(NullPointerException ex){
        return new ApiResponseDto<>(HttpStatus.BAD_REQUEST.value(),ex.getMessage());
    }

    @ExceptionHandler({RejectedExecutionException.class})
    public ApiResponseDto<?> handleRejectedExecutionException(RejectedExecutionException ex){
        return new ApiResponseDto<>(HttpStatus.BAD_REQUEST.value(),ex.getMessage());
    }

    @ExceptionHandler({EntityNotFoundException.class})
    public ApiResponseDto<?> handleEntityNotFoundException(EntityNotFoundException ex){
        return new ApiResponseDto<>(HttpStatus.BAD_REQUEST.value(),ex.getMessage());
    }
}
