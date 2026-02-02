package com.test.result.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.test.result.model.dto.ErrorResponse;
import com.test.result.model.dto.TestCaseDTO;
import com.test.result.model.request.ExecuteRequest;
import com.test.result.model.request.TestCaseRequest;
import com.test.result.service.TestCaseService;

/**
 * 测试用例管理控制器
 * 
 * @author qijiaxi
 */
@RestController
@RequestMapping("/api/v1/testcases")
@Tag(name = "测试用例管理", description = "测试用例管理相关接口")
public class TestCaseController {
    
    @Autowired
    private TestCaseService testCaseService;
    
    @GetMapping
    @Operation(summary = "获取测试用例列表", description = "支持分页查询")
    public ResponseEntity<Page<TestCaseDTO>> getTestCases(
            @RequestParam @Parameter(description = "项目ID") Long projectId,
            @RequestParam(defaultValue = "0") @Parameter(description = "页码") int page,
            @RequestParam(defaultValue = "20") @Parameter(description = "每页大小") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<TestCaseDTO> testCases = testCaseService.getTestCases(projectId, pageable);
        return ResponseEntity.ok(testCases);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "获取测试用例详情", description = "根据ID获取测试用例详细信息")
    public ResponseEntity<TestCaseDTO> getTestCaseById(@PathVariable Long id) {
        TestCaseDTO testCase = testCaseService.getTestCaseById(id);
        return ResponseEntity.ok(testCase);
    }
    
    @PostMapping
    @Operation(summary = "创建测试用例", description = "创建新的测试用例")
    public ResponseEntity<TestCaseDTO> createTestCase(@Valid @RequestBody TestCaseRequest request) {
        TestCaseDTO testCase = testCaseService.createTestCase(request);
        return ResponseEntity.ok(testCase);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "更新测试用例", description = "根据ID更新测试用例信息")
    public ResponseEntity<TestCaseDTO> updateTestCase(@PathVariable Long id, @Valid @RequestBody TestCaseRequest request) {
        TestCaseDTO testCase = testCaseService.updateTestCase(id, request);
        return ResponseEntity.ok(testCase);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "删除测试用例", description = "根据ID删除测试用例")
    public ResponseEntity<Void> deleteTestCase(@PathVariable Long id) {
        testCaseService.deleteTestCase(id);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/{id}/execute")
    @Operation(summary = "执行测试用例", description = "执行指定的测试用例并生成结果")
    public ResponseEntity<TestCaseDTO> executeTestCase(@PathVariable Long id,
                                                      @Valid @RequestBody ExecuteRequest request) {
        TestCaseDTO testCase = testCaseService.executeTestCase(id, request.getEnvironment(), request.getExecutor());
        return ResponseEntity.ok(testCase);
    }
}