package com.test.result.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.test.result.exception.ProjectNotFoundException;
import com.test.result.exception.TestCaseNotFoundException;
import com.test.result.model.dto.TestCaseDTO;
import com.test.result.model.entity.Project;
import com.test.result.model.entity.TestCase;
import com.test.result.model.request.TestCaseRequest;
import com.test.result.model.request.ExecuteRequest;
import com.test.result.repository.ProjectRepository;
import com.test.result.repository.TestCaseRepository;
import com.test.result.repository.TestResultRepository;
import com.test.result.service.TestCaseService;

/**
 * 测试用例服务实现类
 * 
 * @author qijiaxi
 */
@Service
public class TestCaseServiceImpl implements TestCaseService {
    
    @Autowired
    private TestCaseRepository testCaseRepository;
    
    @Autowired
    private TestResultRepository testResultRepository;
    
    @Autowired
    private ProjectRepository projectRepository;
    
    @Override
    public Page<TestCaseDTO> getTestCases(Long projectId, Pageable pageable) {
        Page<TestCase> testCases = testCaseRepository.findByProjectId(projectId, pageable);
        return testCases.map(this::convertToDTO);
    }
    
    @Override
    public TestCaseDTO getTestCaseById(Long id) {
        TestCase testCase = testCaseRepository.findById(id)
            .orElseThrow(() -> new TestCaseNotFoundException("测试用例不存在，ID: " + id));
        return convertToDTO(testCase);
    }
    
    @Override
    public TestCaseDTO createTestCase(TestCaseRequest request) {
        // 验证项目是否存在
        Project project = projectRepository.findById(request.getProjectId())
            .orElseThrow(() -> new ProjectNotFoundException("项目不存在，ID: " + request.getProjectId()));
        
        TestCase testCase = TestCase.builder()
            .projectId(request.getProjectId())
            .name(request.getName())
            .description(request.getDescription())
            .priority(request.getPriority())
            .module(request.getModule())
            .tags(request.getTags())
            .build();
        
        TestCase savedTestCase = testCaseRepository.save(testCase);
        return convertToDTO(savedTestCase);
    }
    
    @Override
    public TestCaseDTO updateTestCase(Long id, TestCaseRequest request) {
        TestCase testCase = testCaseRepository.findById(id)
            .orElseThrow(() -> new TestCaseNotFoundException("测试用例不存在，ID: " + id));
        
        testCase.setName(request.getName());
        testCase.setDescription(request.getDescription());
        testCase.setPriority(request.getPriority());
        testCase.setModule(request.getModule());
        testCase.setTags(request.getTags());
        
        TestCase updatedTestCase = testCaseRepository.save(testCase);
        return convertToDTO(updatedTestCase);
    }
    
    @Override
    public void deleteTestCase(Long id) {
        TestCase testCase = testCaseRepository.findById(id)
            .orElseThrow(() -> new TestCaseNotFoundException("测试用例不存在，ID: " + id));
        
        testCaseRepository.delete(testCase);
    }
    
    @Override
    public TestCaseDTO executeTestCase(Long id, String environment, String executor) {
        TestCase testCase = testCaseRepository.findById(id)
            .orElseThrow(() -> new TestCaseNotFoundException("测试用例不存在，ID: " + id));
        
        // 随机生成测试结果
        java.util.Random random = new java.util.Random();
        int result = random.nextInt(100);
        
        String status = "passed";
        String errorMessage = null;
        int executionTime = random.nextInt(1000) + 100;
        
        if (result < 15) {
            status = "failed";
            errorMessage = "测试执行失败: " + generateRandomErrorMessage();
        } else if (result < 20) {
            status = "skipped";
            errorMessage = "测试被跳过";
            executionTime = 0;
        } else if (result < 22) {
            status = "error";
            errorMessage = "测试执行异常: " + generateRandomErrorMessage();
        }
        
        // 保存测试结果
        com.test.result.model.entity.TestResult testResult = 
            com.test.result.model.entity.TestResult.builder()
                .testCaseId(id)
                .status(com.test.result.model.enums.TestStatus.valueOf(status.toUpperCase()))
                .executionTime(executionTime)
                .errorMessage(errorMessage)
                .environment(environment)
                .executor(executor)
                .build();
        
        testResultRepository.save(testResult);
        
        return convertToDTO(testCase);
    }
    
    @Override
    public TestCaseDTO convertToDTO(TestCase testCase) {
        // 获取统计信息
        Long totalExecutions = getTotalExecutionCount(testCase.getId());
        Long passedCount = getExecutionCount(testCase.getId(), com.test.result.model.enums.TestStatus.PASSED);
        Long failedCount = getExecutionCount(testCase.getId(), com.test.result.model.enums.TestStatus.FAILED);
        Double passRate = totalExecutions > 0 ? (double) passedCount / totalExecutions * 100 : 0.0;
        Long avgExecutionTime = getAverageExecutionTime(testCase.getId());
        
        // 获取最后执行结果
        Optional<com.test.result.model.entity.TestResult> latestResult = 
            testResultRepository.findLatestResultByTestCaseId(testCase.getId());
        
        com.test.result.model.dto.TestResultDTO lastExecution = latestResult.isPresent() ? 
            convertToDTO(latestResult.get()) : null;
        
        return TestCaseDTO.builder()
            .id(testCase.getId())
            .projectId(testCase.getProjectId())
            .name(testCase.getName())
            .description(testCase.getDescription())
            .priority(testCase.getPriority())
            .module(testCase.getModule())
            .tags(testCase.getTags())
            .createdAt(testCase.getCreatedAt())
            .updatedAt(testCase.getUpdatedAt())
            .lastExecution(lastExecution)
            .totalExecutions(totalExecutions)
            .passedCount(passedCount)
            .failedCount(failedCount)
            .passRate(passRate)
            .averageExecutionTime(avgExecutionTime)
            .build();
    }
    
    private Long getExecutionCount(Long testCaseId, com.test.result.model.enums.TestStatus status) {
        return testResultRepository.countByTestCaseIdAndStatus(testCaseId, status);
    }
    
    private Long getTotalExecutionCount(Long testCaseId) {
        return testResultRepository.countByTestCaseId(testCaseId);
    }
    
    private Long getAverageExecutionTime(Long testCaseId) {
        List<com.test.result.model.entity.TestResult> results = 
            testResultRepository.findByTestCaseIdOrderByExecutedAtDesc(testCaseId);
        if (results.isEmpty()) {
            return 0L;
        }
        
        long totalTime = results.stream()
            .mapToLong(r -> r.getExecutionTime() != null ? r.getExecutionTime() : 0)
            .sum();
        return totalTime / results.size();
    }
    
    private String generateRandomErrorMessage() {
        String[] messages = {
            "网络连接超时",
            "数据库查询失败",
            "参数验证错误",
            "权限不足",
            "服务不可用",
            "配置文件错误",
            "内存不足",
            "文件不存在"
        };
        return messages[(int) (Math.random() * messages.length)];
    }
    
    private com.test.result.model.dto.TestResultDTO convertToDTO(com.test.result.model.entity.TestResult result) {
        return com.test.result.model.dto.TestResultDTO.builder()
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
}