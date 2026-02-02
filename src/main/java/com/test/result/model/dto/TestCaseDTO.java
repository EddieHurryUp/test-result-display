package com.test.result.model.dto;

import lombok.*;

import java.time.LocalDateTime;

import com.test.result.model.enums.Priority;

/**
 * 测试用例数据传输对象
 * 
 * @author qijiaxi
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestCaseDTO {
    
    private Long id;
    private Long projectId;
    private String name;
    private String description;
    private Priority priority;
    private String module;
    private String tags;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // 最后执行信息
    private TestResultDTO lastExecution;
    
    // 统计信息
    private Long totalExecutions;
    private Long passedCount;
    private Long failedCount;
    private Double passRate;
    private Long averageExecutionTime;
}