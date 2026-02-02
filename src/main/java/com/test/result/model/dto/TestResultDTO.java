package com.test.result.model.dto;

import lombok.*;

import java.time.LocalDateTime;

import com.test.result.model.enums.TestStatus;

/**
 * 测试结果数据传输对象
 * 
 * @author qijiaxi
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestResultDTO {
    
    private Long id;
    private Long testCaseId;
    private TestStatus status;
    private Integer executionTime;
    private String errorMessage;
    private LocalDateTime executedAt;
    private String environment;
    private String executor;
}