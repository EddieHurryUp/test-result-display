package com.test.result.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.github.javafaker.Faker;
import com.test.result.model.dto.ExecutionResult;
import com.test.result.model.dto.MockDataResponse;
import com.test.result.model.entity.Project;
import com.test.result.model.entity.TestCase;
import com.test.result.model.entity.TestResult;
import com.test.result.model.enums.Priority;
import com.test.result.model.enums.ProjectStatus;
import com.test.result.model.enums.TestStatus;
import com.test.result.model.enums.TestType;
import com.test.result.model.request.MockDataRequest;
import com.test.result.model.request.RandomExecutionRequest;
import com.test.result.repository.ProjectRepository;
import com.test.result.repository.TestCaseRepository;
import com.test.result.repository.TestResultRepository;
import com.test.result.service.MockDataService;

/**
 * 模拟数据服务实现类
 * 
 * @author qijiaxi
 */
@Service
public class MockDataServiceImpl implements MockDataService {
    
    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private TestCaseRepository testCaseRepository;
    
    @Autowired
    private TestResultRepository testResultRepository;
    
    private final Faker faker = new Faker();
    private final Random random = new Random();
    
    @Override
    public MockDataResponse generateMockData(MockDataRequest request) {
        long startTime = System.currentTimeMillis();
        
        try {
            generateTestData(request.getProjectCount(), request.getTestCaseCount());
            
            long endTime = System.currentTimeMillis();
            return MockDataResponse.builder()
                .success(true)
                .message("模拟数据生成完成")
                .projectCount(request.getProjectCount())
                .testCaseCount(request.getTestCaseCount())
                .executionTime(endTime - startTime)
                .build();
                
        } catch (Exception e) {
            long endTime = System.currentTimeMillis();
            return MockDataResponse.builder()
                .success(false)
                .message("数据生成失败: " + e.getMessage())
                .executionTime(endTime - startTime)
                .build();
        }
    }
    
    @Override
    public ExecutionResult executeRandomTests(RandomExecutionRequest request) {
        long startTime = System.currentTimeMillis();
        
        try {
            List<TestResult> results = new ArrayList<>();
            
            // 获取所有测试用例
            List<TestCase> testCases = testCaseRepository.findAll();
            if (testCases.isEmpty()) {
                return ExecutionResult.builder()
                    .success(false)
                    .message("没有找到测试用例")
                    .executionTime(System.currentTimeMillis() - startTime)
                    .build();
            }
            
            // 随机执行指定数量的测试
            for (int i = 0; i < request.getCount(); i++) {
                TestCase randomTestCase = testCases.get(random.nextInt(testCases.size()));
                TestResult result = generateRandomTestResult(randomTestCase.getId());
                results.add(result);
            }
            
            // 保存结果
            testResultRepository.saveAll(results);
            
            long endTime = System.currentTimeMillis();
            return ExecutionResult.builder()
                .success(true)
                .message("随机测试执行完成")
                .executedCount(results.size())
                .executionTime(endTime - startTime)
                .build();
                
        } catch (Exception e) {
            long endTime = System.currentTimeMillis();
            return ExecutionResult.builder()
                .success(false)
                .message("执行失败: " + e.getMessage())
                .executionTime(endTime - startTime)
                .build();
        }
    }
    
    @Override
    public void generateTestData(int projectCount, int testCaseCount) {
        // 生成项目
        List<Project> projects = generateProjects(projectCount);
        projectRepository.saveAll(projects);
        
        // 生成测试用例
        List<TestCase> testCases = new ArrayList<>();
        for (Project project : projects) {
            int casesPerProject = testCaseCount / projects.size();
            testCases.addAll(generateTestCases(project.getId(), casesPerProject));
        }
        testCaseRepository.saveAll(testCases);
        
        // 生成测试结果
        List<TestResult> results = new ArrayList<>();
        for (TestCase testCase : testCases) {
            int resultCount = random.nextInt(5) + 3; // 每个用例3-7次执行记录
            results.addAll(generateTestResults(testCase.getId(), resultCount));
        }
        testResultRepository.saveAll(results);
    }
    
    private List<Project> generateProjects(int count) {
        List<Project> projects = new ArrayList<>();
        String[] projectNames = {
            "用户管理系统测试", "支付模块测试", "性能压力测试", 
            "移动端UI测试", "API接口测试", "数据库测试",
            "安全测试", "兼容性测试", "回归测试", "自动化测试"
        };
        
        for (int i = 0; i < count; i++) {
            Project project = Project.builder()
                .name(projectNames[i % projectNames.length] + " " + (i + 1))
                .description(faker.lorem().sentence(10, 15))
                .type(TestType.values()[random.nextInt(TestType.values().length)])
                .status(ProjectStatus.ACTIVE)
                .createdAt(faker.date().past(30, java.util.concurrent.TimeUnit.DAYS).toInstant()
                    .atZone(java.time.ZoneId.systemDefault()).toLocalDateTime())
                .build();
            projects.add(project);
        }
        return projects;
    }
    
    private List<TestCase> generateTestCases(Long projectId, int count) {
        List<TestCase> testCases = new ArrayList<>();
        String[] modules = {"用户管理", "订单管理", "支付模块", "权限管理", "数据同步", "报表管理"};
        String[] testNames = {
            "测试用户注册功能", "测试登录验证", "测试密码修改", "测试权限检查",
            "测试订单创建", "测试订单查询", "测试订单状态更新", "测试支付流程",
            "测试退款流程", "测试并发处理", "测试数据一致性", "测试异常处理"
        };
        
        for (int i = 0; i < count; i++) {
            TestCase testCase = TestCase.builder()
                .projectId(projectId)
                .name(testNames[i % testNames.length] + " " + (i + 1))
                .description(faker.lorem().sentence(5, 10))
                .priority(Priority.values()[random.nextInt(Priority.values().length)])
                .module(modules[random.nextInt(modules.length)])
                .tags("tag" + random.nextInt(10) + ",tag" + random.nextInt(10))
                .createdAt(faker.date().past(30, java.util.concurrent.TimeUnit.DAYS).toInstant()
                    .atZone(java.time.ZoneId.systemDefault()).toLocalDateTime())
                .build();
            testCases.add(testCase);
        }
        return testCases;
    }
    
    private List<TestResult> generateTestResults(Long testCaseId, int count) {
        List<TestResult> results = new ArrayList<>();
        
        for (int i = 0; i < count; i++) {
            TestResult result = generateRandomTestResult(testCaseId);
            results.add(result);
        }
        return results;
    }
    
    private TestResult generateRandomTestResult(Long testCaseId) {
        // 基于优先级的概率算法
        Priority priority = Priority.values()[random.nextInt(Priority.values().length)];
        TestStatus status = selectStatusByPriority(priority);
        
        int executionTime = random.nextInt(2000) + 100; // 100-2100ms
        String errorMessage = null;
        
        if (status == TestStatus.FAILED) {
            executionTime = random.nextInt(5000) + 1000; // 失败时执行时间更长
            errorMessage = generateErrorMessage();
        } else if (status == TestStatus.SKIPPED) {
            executionTime = 0;
            errorMessage = "测试被跳过: " + faker.lorem().sentence(2, 3);
        } else if (status == TestStatus.ERROR) {
            errorMessage = "系统错误: " + generateErrorMessage();
        }
        
        LocalDateTime executedAt = faker.date().past(30, java.util.concurrent.TimeUnit.DAYS).toInstant()
            .atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
        
        return TestResult.builder()
            .testCaseId(testCaseId)
            .status(status)
            .executionTime(executionTime)
            .errorMessage(errorMessage)
            .executedAt(executedAt)
            .environment("test-" + random.nextInt(5) + 1)
            .executor("auto-generator")
            .build();
    }
    
    private TestStatus selectStatusByPriority(Priority priority) {
        // 高优先级通过率更高
        double randomValue = random.nextDouble();
        
        if (priority == Priority.HIGH) {
            if (randomValue < 0.85) return TestStatus.PASSED;
            if (randomValue < 0.95) return TestStatus.FAILED;
            if (randomValue < 0.98) return TestStatus.SKIPPED;
            return TestStatus.ERROR;
        } else if (priority == Priority.MEDIUM) {
            if (randomValue < 0.80) return TestStatus.PASSED;
            if (randomValue < 0.92) return TestStatus.FAILED;
            if (randomValue < 0.96) return TestStatus.SKIPPED;
            return TestStatus.ERROR;
        } else {
            if (randomValue < 0.75) return TestStatus.PASSED;
            if (randomValue < 0.90) return TestStatus.FAILED;
            if (randomValue < 0.94) return TestStatus.SKIPPED;
            return TestStatus.ERROR;
        }
    }
    
    private String generateErrorMessage() {
        String[] messages = {
            "网络连接超时", "数据库查询失败", "参数验证错误", "权限不足",
            "服务不可用", "配置文件错误", "内存不足", "文件不存在",
            "并发冲突", "数据格式错误", "第三方服务异常", "超时异常"
        };
        return messages[random.nextInt(messages.length)];
    }
}