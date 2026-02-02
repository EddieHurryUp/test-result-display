package com.test.result.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

import com.test.result.model.dto.TestResultDTO;
import com.test.result.model.entity.TestResult;
import com.test.result.model.enums.TestStatus;

/**
 * 测试结果服务接口
 * 
 * @author qijiaxi
 */
public interface ResultService {
    
    /**
     * 获取测试结果列表
     */
    Page<TestResultDTO> getResults(Long projectId, TestStatus status, 
                                  LocalDateTime startTime, LocalDateTime endTime, 
                                  Pageable pageable);
    
    /**
     * 获取测试用例的执行历史
     */
    Page<TestResultDTO> getExecutionHistory(Long testCaseId, Pageable pageable);
    
    /**
     * 获取测试结果详情
     */
    TestResultDTO getResultById(Long id);
    
    /**
     * 创建测试结果
     */
    TestResultDTO createResult(TestResult result);
    
    /**
     * 将实体转换为DTO
     */
    TestResultDTO convertToDTO(TestResult result);
}