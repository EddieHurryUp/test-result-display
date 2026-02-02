package com.test.result.service;

import com.test.result.model.dto.MockDataResponse;
import com.test.result.model.dto.ExecutionResult;
import com.test.result.model.request.MockDataRequest;
import com.test.result.model.request.RandomExecutionRequest;

/**
 * 模拟数据服务接口
 * 
 * @author qijiaxi
 */
public interface MockDataService {
    
    /**
     * 生成模拟数据
     */
    MockDataResponse generateMockData(MockDataRequest request);
    
    /**
     * 随机执行测试
     */
    ExecutionResult executeRandomTests(RandomExecutionRequest request);
    
    /**
     * 生成测试数据（内部使用）
     */
    void generateTestData(int projectCount, int testCaseCount);
}