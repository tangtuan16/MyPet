package org.example.mypet.Utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse<T> {
    private int status;
    private String message;
    private T data;
    private Object meta;
    private Object error;
    private LocalDateTime timestamp;

    public static <T> ResponseEntity<ApiResponse<T>> success(T data) {
        return success(data, 200, "Success", null);
    }

    public static <T> ResponseEntity<ApiResponse<T>> success(T data, String message) {
        return success(data, 200, message, null);
    }

    public static <T> ResponseEntity<ApiResponse<T>> success(T data, int status, String message) {
        return success(data, status, message, null);
    }

    public static <T> ResponseEntity<ApiResponse<T>> success(T data, int status, String message, Object meta) {
        ApiResponse<T> apiResponse = ApiResponse.<T>builder()
                .status(status)
                .message(message)
                .data(data)
                .meta(meta)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(status).body(apiResponse);
    }

    public static <T> ResponseEntity<ApiResponse<T>> error(String message, int status, Object errorDetails) {
        ApiResponse<T> apiResponse = ApiResponse.<T>builder()
                .status(status)
                .message(message)
                .error(errorDetails)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(status).body(apiResponse);
    }
}
