package com.test.result.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.test.result.model.dto.ExecutionResult;
import com.test.result.model.dto.MockDataResponse;
import com.test.result.model.request.MockDataRequest;
import com.test.result.model.request.RandomExecutionRequest;
import com.test.result.service.MockDataService;

/**
 * 模拟数据管理控制器
 * 
 * @author qijiaxi
 */
@RestController
@RequestMapping("/api/v1/mock")
@Tag(name = "数据模拟", description = "模拟数据生成和管理相关接口")
public class MockDataController {
    
    @Autowired
    private MockDataService mockDataService;
    
    @PostMapping("/generate-data")
    @Operation(summary = "生成模拟数据", description = "批量生成测试项目、用例和结果数据")
    public ResponseEntity<MockDataResponse> generateMockData(@Valid @RequestBody MockDataRequest request) {
        MockDataResponse response = mockDataService.generateMockData(request);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/execute-random")
    @Operation(summary = "随机执行测试", description = "随机选择测试用例执行并生成结果")
    public ResponseEntity<ExecutionResult> executeRandomTests(@Valid @RequestBody RandomExecutionRequest request) {
        ExecutionResult result = mockDataService.executeRandomTests(request);
        return ResponseEntity.ok(result);
    }
}