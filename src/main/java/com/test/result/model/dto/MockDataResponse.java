package com.test.result.model.dto;

import lombok.*;

/**
 * 模拟数据生成响应对象
 * 
 * @author qijiaxi
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MockDataResponse {
    
    private boolean success;
    private String message;
    private Integer projectCount;
    private Integer testCaseCount;
    private Long executionTime;
}