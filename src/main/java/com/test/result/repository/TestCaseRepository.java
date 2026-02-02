package com.test.result.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.test.result.model.entity.TestCase;

/**
 * 测试用例数据访问接口
 * 
 * @author qijiaxi
 */
@Repository
public interface TestCaseRepository extends JpaRepository<TestCase, Long> {
    
    /**
     * 根据项目ID查询测试用例列表
     */
    List<TestCase> findByProjectId(Long projectId);
    
    /**
     * 根据项目ID分页查询测试用例
     */
    Page<TestCase> findByProjectId(Long projectId, Pageable pageable);
    
    /**
     * 根据优先级查询测试用例
     */
    List<TestCase> findByPriority(String priority);
    
    /**
     * 根据模块查询测试用例
     */
    List<TestCase> findByModule(String module);
    
    /**
     * 根据项目ID和优先级查询测试用例
     */
    List<TestCase> findByProjectIdAndPriority(Long projectId, String priority);
    
    /**
     * 根据用例名称模糊查询
     */
    List<TestCase> findByNameContainingIgnoreCase(String name);
    
    /**
     * 查询项目下最近执行的测试用例
     */
    @Query("SELECT tc FROM TestCase tc WHERE tc.projectId = :projectId " +
           "AND tc.id IN (SELECT tr.testCaseId FROM TestResult tr " +
           "WHERE tr.testCaseId = tc.id AND tr.executedAt >= :sinceDate)")
    List<TestCase> findRecentExecutedTestCases(@Param("projectId") Long projectId,
                                              @Param("sinceDate") LocalDateTime sinceDate);
}