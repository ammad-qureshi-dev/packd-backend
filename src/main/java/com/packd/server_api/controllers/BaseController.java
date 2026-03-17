package com.packd.server_api.controllers;

import com.packd.server_api.controllers.dto.response.ApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BaseController {

    public <T> ResponseEntity<ApiResponse<T>> getResponse(HttpStatus status, T data, boolean isSuccess) {
        ApiResponse<T> responseBody = ApiResponse.<T>builder().data(data).isSuccess(isSuccess).build();

        return new ResponseEntity<>(responseBody, status);
    }

    public <T> ResponseEntity<ApiResponse<T>> getResponse(HttpStatus status, T data) {
        ApiResponse<T> responseBody = ApiResponse.<T>builder().data(data).isSuccess(true).build();

        return new ResponseEntity<>(responseBody, status);
    }

    public <T> ResponseEntity<ApiResponse<T>> getResponse(HttpStatus status, T data, boolean isSuccess,
                                                          HttpHeaders headers) {
        ApiResponse<T> responseBody = ApiResponse.<T>builder().data(data).isSuccess(isSuccess).build();

        return new ResponseEntity<>(responseBody, headers, status);
    }
}
