package com.test.result.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import com.test.result.model.dto.ErrorResponse;
import com.test.result.model.dto.TestResultDTO;
import com.test.result.model.entity.TestResult;
import com.test.result.model.enums.TestStatus;
import com.test.result.service.ResultService;

/**
 * 测试结果管理控制器
 * 
 * @author qijiaxi
 */
@RestController
@RequestMapping("/api/v1/results")
@Tag(name = "测试结果管理", description = "测试结果管理相关接口")
public class ResultController {
    
    @Autowired
    private ResultService resultService;
    
    @GetMapping
    @Operation(summary = "获取测试结果列表", description = "支持多种筛选条件")
    public ResponseEntity<Page<TestResultDTO>> getResults(
            @RequestParam(required = false) @Parameter(description = "项目ID") Long projectId,
            @RequestParam(required = false) @Parameter(description = "测试状态") TestStatus status,
            @RequestParam(required = false) @Parameter(description = "开始时间") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam(required = false) @Parameter(description = "结束时间") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
            @RequestParam(defaultValue = "0") @Parameter(description = "页码") int page,
            @RequestParam(defaultValue = "20") @Parameter(description = "每页大小") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<TestResultDTO> results = resultService.getResults(projectId, status, startTime, endTime, pageable);
        return ResponseEntity.ok(results);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "获取测试结果详情", description = "根据ID获取测试结果详细信息")
    public ResponseEntity<TestResultDTO> getResultById(@PathVariable Long id) {
        TestResultDTO result = resultService.getResultById(id);
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/testcase/{testCaseId}")
    @Operation(summary = "获取测试用例执行历史", description = "获取指定测试用例的所有执行历史")
    public ResponseEntity<Page<TestResultDTO>> getExecutionHistory(@PathVariable Long testCaseId,
                                                                  @RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<TestResultDTO> results = resultService.getExecutionHistory(testCaseId, pageable);
        return ResponseEntity.ok(results);
    }
    
    @PostMapping
    @Operation(summary = "创建测试结果", description = "手动创建测试结果记录")
    public ResponseEntity<TestResultDTO> createResult(@Valid @RequestBody TestResult result) {
        TestResultDTO createdResult = resultService.createResult(result);
        return ResponseEntity.ok(createdResult);
    }
}