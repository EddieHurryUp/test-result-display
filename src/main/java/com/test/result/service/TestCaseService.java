package com.test.result.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.test.result.model.dto.TestCaseDTO;
import com.test.result.model.entity.TestCase;
import com.test.result.model.request.TestCaseRequest;

/**
 * 测试用例服务接口
 * 
 * @author qijiaxi
 */
public interface TestCaseService {
    
    /**
     * 获取测试用例列表
     */
    Page<TestCaseDTO> getTestCases(Long projectId, Pageable pageable);
    
    /**
     * 获取测试用例详情
     */
    TestCaseDTO getTestCaseById(Long id);
    
    /**
     * 创建测试用例
     */
    TestCaseDTO createTestCase(TestCaseRequest request);
    
    /**
     * 更新测试用例
     */
    TestCaseDTO updateTestCase(Long id, TestCaseRequest request);
    
    /**
     * 删除测试用例
     */
    void deleteTestCase(Long id);
    
    /**
     * 执行测试用例
     */
    TestCaseDTO executeTestCase(Long id, String environment, String executor);
    
    /**
     * 将实体转换为DTO
     */
    TestCaseDTO convertToDTO(TestCase testCase);
}