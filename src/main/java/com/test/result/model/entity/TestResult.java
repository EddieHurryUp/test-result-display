package com.test.result.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import com.test.result.model.enums.TestStatus;

/**
 * 测试结果实体类
 * 
 * @author qijiaxi
 */
@Entity
@Table(name = "test_results")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestResult {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "test_case_id", nullable = false)
    private Long testCaseId;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TestStatus status;
    
    @Column(name = "execution_time")
    private Integer executionTime; // 执行时间(毫秒)
    
    @Column(name = "error_message", columnDefinition = "TEXT")
    private String errorMessage;
    
    @Column(name = "executed_at", nullable = false)
    private LocalDateTime executedAt;
    
    @Column(length = 100)
    private String environment; // 执行环境
    
    @Column(length = 100)
    private String executor; // 执行者
    
    @PrePersist
    public void prePersist() {
        if (executedAt == null) {
            executedAt = LocalDateTime.now();
        }
    }
}