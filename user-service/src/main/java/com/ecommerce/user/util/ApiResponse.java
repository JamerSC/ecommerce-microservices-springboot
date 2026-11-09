package com.ecommerce.user.util;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.time.ZoneId;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    private int status;

    // Change this to OffsetDateTime to be consistent with Manila +08:00
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private OffsetDateTime timestamp = OffsetDateTime.now(ZoneId.of("Asia/Manila"));

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errorCode; // optional error code for debugging/tracking
}