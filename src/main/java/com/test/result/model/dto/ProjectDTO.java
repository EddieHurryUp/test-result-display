package com.test.result.model.dto;

import lombok.*;

import java.time.LocalDateTime;

import com.test.result.model.enums.ProjectStatus;
import com.test.result.model.enums.TestType;

/**
 * 项目数据传输对象
 * 
 * @author qijiaxi
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {
    
    private Long id;
    private String name;
    private String description;
    private TestType type;
    private ProjectStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // 统计信息
    private Long testCaseCount;
    private Double passRate;
    private Long totalExecutions;
}