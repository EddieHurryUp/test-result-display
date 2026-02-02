package com.test.result.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.test.result.exception.TestCaseNotFoundException;
import com.test.result.exception.TestResultNotFoundException;
import com.test.result.model.dto.TestResultDTO;
import com.test.result.model.entity.TestCase;
import com.test.result.model.entity.TestResult;
import com.test.result.model.enums.TestStatus;
import com.test.result.repository.TestCaseRepository;
import com.test.result.repository.TestResultRepository;
import com.test.result.service.ResultService;

/**
 * 测试结果服务实现类
 * 
 * @author qijiaxi
 */
@Service
public class ResultServiceImpl implements ResultService {
    
    @Autowired
    private TestResultRepository testResultRepository;
    
    @Autowired
    private TestCaseRepository testCaseRepository;
    
    @Override
    public Page<TestResultDTO> getResults(Long projectId, TestStatus status, 
                                        LocalDateTime startTime, LocalDateTime endTime, 
                                        Pageable pageable) {
        if (projectId != null) {
            return testResultRepository.findByProjectId(projectId, pageable)
                .map(this::convertToDTO);
        } else if (status != null) {
            List<TestResult> results = testResultRepository.findByStatusOrderByExecutedAtDesc(status);
            return convertToPage(results, pageable).map(this::convertToDTO);
        } else if (startTime != null && endTime != null) {
            List<TestResult> results = testResultRepository.findByExecutedAtBetweenOrderByExecutedAtDesc(startTime, endTime);
            return convertToPage(results, pageable).map(this::convertToDTO);
        } else {
            return testResultRepository.findAll(pageable).map(this::convertToDTO);
        }
    }
    
    @Override
    public Page<TestResultDTO> getExecutionHistory(Long testCaseId, Pageable pageable) {
        return testResultRepository.findByTestCaseId(testCaseId, pageable)
            .map(this::convertToDTO);
    }
    
    @Override
    public TestResultDTO getResultById(Long id) {
        TestResult result = testResultRepository.findById(id)
            .orElseThrow(() -> new TestResultNotFoundException("测试结果不存在，ID: " + id));
        return convertToDTO(result);
    }
    
    @Override
    public TestResultDTO createResult(TestResult result) {
        // 验证测试用例是否存在
        TestCase testCase = testCaseRepository.findById(result.getTestCaseId())
            .orElseThrow(() -> new TestCaseNotFoundException("测试用例不存在，ID: " + result.getTestCaseId()));
        
        TestResult savedResult = testResultRepository.save(result);
        return convertToDTO(savedResult);
    }
    
    @Override
    public TestResultDTO convertToDTO(TestResult result) {
        return TestResultDTO.builder()
            .id(result.getId())
            .testCaseId(result.getTestCaseId())
            .status(result.getStatus())
            .executionTime(result.getExecutionTime())
            .errorMessage(result.getErrorMessage())
            .executedAt(result.getExecutedAt())
            .environment(result.getEnvironment())
            .executor(result.getExecutor())
            .build();
    }
    
    private <T> Page<T> convertToPage(List<T> list, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());
        return new org.springframework.data.domain.PageImpl<>(
            list.subList(start, end), pageable, list.size());
    }
}