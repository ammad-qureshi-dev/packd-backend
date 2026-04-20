package com.packd.dtos.response;

import com.packd.dtos.enums.ExceptionCode;
import com.packd.dtos.enums.ExceptionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Data
@AllArgsConstructor
@Builder
public class ApiErrorResponse {
    private String message;
    private ExceptionCode errorCode;
    private ExceptionType severityType;

    @Builder.Default
    private HttpStatus status = HttpStatus.BAD_REQUEST;

    @Builder.Default
    private Instant completedAt = Instant.now();

}
