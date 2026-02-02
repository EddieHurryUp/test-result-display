package com.test.result.model.dto;

import lombok.*;

/**
 * 执行结果响应对象
 * 
 * @author qijiaxi
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecutionResult {
    
    private boolean success;
    private String message;
    private Integer executedCount;
    private Long executionTime;
}