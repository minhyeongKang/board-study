package com.hellomeen.boardstudy.global.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiResponseDto<T> {

    private int status;
    private String message;
    private T data;

    public ApiResponseDto(int status, String message, T data){
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public ApiResponseDto(int status, String message){
        this.status = status;
        this.message = message;
        this.data = null;
    }

    public ApiResponseDto(T data){
        this.data = data;
    }
}
