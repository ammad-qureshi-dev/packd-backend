package com.packd.dtos.response;
import java.time.Instant;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private T data;
    private boolean isSuccess;

    @Builder.Default
    private Instant completedAt = Instant.now();

    @Builder.Default
    private UUID requestId = UUID.randomUUID();

    private String url;
}
