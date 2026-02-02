package com.test.result.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * 测试执行请求对象
 * 
 * @author qijiaxi
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecuteRequest {
    
    private String environment;
    private String executor;
}