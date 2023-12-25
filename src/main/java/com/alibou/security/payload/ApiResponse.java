package com.alibou.security.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<E> {


    private E data;

    private boolean success;

    private String message;

    private List<ErrorData> errors;

    public ApiResponse(E data) {
        this.data = data;
        this.success = true;
    }

    private ApiResponse(String message) {
        this.success = true;
        this.message = message;
    }

    private ApiResponse(E data, String message) {
        this.success = true;
        this.message = message;
        this.data = data;
    }

    private ApiResponse(E data, boolean success) {
        this.data = data;
        this.success = success;
    }

    private ApiResponse(String errorMsg, Integer code) {
        this.success = false;
        this.errors = List.of(new ErrorData(errorMsg, code));
    }

    private ApiResponse(List<ErrorData> errors) {
        this.success = false;
        this.errors = errors;
    }

    public static <T> ApiResponse<T> successResponse(String message) {
        return new ApiResponse<T>(message);
    }

    public static <T> ApiResponse<T> successResponse(T data, String message) {
        return new ApiResponse<T>(data, message);
    }

    public static <T> ApiResponse<T> successResponse(T data) {
        return new ApiResponse<T>(data);
    }

    public static ApiResponse<ErrorData> errorResponse(String errorMsg, Integer code) {
        return new ApiResponse<ErrorData>(errorMsg, code);
    }

    public static ApiResponse<ErrorData> errorResponse(List<ErrorData> errors) {
        return new ApiResponse<ErrorData>(errors);
    }

    public static <E> ApiResponse<E> errorResponseWithData(E e) {
        return new ApiResponse<E>(e, false);
    }

}
