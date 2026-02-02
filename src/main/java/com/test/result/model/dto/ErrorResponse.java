package com.test.result.model.dto;

import lombok.*;

import java.time.LocalDateTime;

/**
 * 错误响应对象
 * 
 * @author qijiaxi
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    
    private boolean success = false;
    private String message;
    private String code;
    private LocalDateTime timestamp;
    private Object data;
}