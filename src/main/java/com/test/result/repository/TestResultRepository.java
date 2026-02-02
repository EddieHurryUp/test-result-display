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

import com.test.result.model.entity.TestResult;
import com.test.result.model.enums.TestStatus;

/**
 * 测试结果数据访问接口
 * 
 * @author qijiaxi
 */
@Repository
public interface TestResultRepository extends JpaRepository<TestResult, Long> {
    
    /**
     * 根据测试用例ID查询测试结果列表
     */
    List<TestResult> findByTestCaseIdOrderByExecutedAtDesc(Long testCaseId);
    
    /**
     * 根据测试用例ID分页查询测试结果
     */
    Page<TestResult> findByTestCaseId(Long testCaseId, Pageable pageable);
    
    /**
     * 根据测试用例ID和状态查询结果数量
     */
    long countByTestCaseIdAndStatus(Long testCaseId, TestStatus status);
    
    /**
     * 根据测试用例ID查询结果数量
     */
    long countByTestCaseId(Long testCaseId);
    
    /**
     * 根据测试状态查询测试结果
     */
    List<TestResult> findByStatusOrderByExecutedAtDesc(TestStatus status);
    
    /**
     * 根据执行时间范围查询测试结果
     */
    List<TestResult> findByExecutedAtBetweenOrderByExecutedAtDesc(LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 根据测试状态和执行时间范围查询测试结果
     */
    @Query("SELECT tr FROM TestResult tr WHERE tr.status = :status " +
           "AND tr.executedAt BETWEEN :startTime AND :endTime " +
           "ORDER BY tr.executedAt DESC")
    List<TestResult> findByStatusAndTimeRange(@Param("status") TestStatus status,
                                             @Param("startTime") LocalDateTime startTime,
                                             @Param("endTime") LocalDateTime endTime);
    
    /**
     * 查询项目下的所有测试结果
     */
    @Query("SELECT tr FROM TestResult tr WHERE tr.testCaseId IN " +
           "(SELECT tc.id FROM TestCase tc WHERE tc.projectId = :projectId)")
    Page<TestResult> findByProjectId(@Param("projectId") Long projectId, Pageable pageable);
    
    /**
     * 统计项目下各状态的测试结果数量
     */
    @Query("SELECT tr.status, COUNT(tr) FROM TestResult tr " +
           "WHERE tr.testCaseId IN (SELECT tc.id FROM TestCase tc WHERE tc.projectId = :projectId) " +
           "GROUP BY tr.status")
    List<Object[]> countByStatusAndProject(@Param("projectId") Long projectId);
    
    /**
     * 获取项目下测试结果的平均执行时间
     */
    @Query("SELECT AVG(tr.executionTime) FROM TestResult tr " +
           "WHERE tr.testCaseId IN (SELECT tc.id FROM TestCase tc WHERE tc.projectId = :projectId)")
    Double getAverageExecutionTimeByProject(@Param("projectId") Long projectId);
    
    /**
     * 统计指定状态的测试结果数量
     */
    long countByStatus(TestStatus status);
    
    /**
     * 统计项目下指定状态的测试结果数量
     */
    @Query("SELECT COUNT(tr) FROM TestResult tr " +
           "WHERE tr.testCaseId IN (SELECT tc.id FROM TestCase tc WHERE tc.projectId = :projectId) " +
           "AND tr.status = :status")
    long countByStatusAndProject(@Param("status") TestStatus status, @Param("projectId") Long projectId);
    
    /**
     * 查询最近的测试执行结果
     */
    @Query("SELECT tr FROM TestResult tr WHERE tr.testCaseId = :testCaseId " +
           "ORDER BY tr.executedAt DESC LIMIT 1")
    Optional<TestResult> findLatestResultByTestCaseId(@Param("testCaseId") Long testCaseId);
}