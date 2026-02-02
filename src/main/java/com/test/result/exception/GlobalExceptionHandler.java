package com.test.result.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.test.result.model.dto.ErrorResponse;

/**
 * 全局异常处理器
 * 
 * @author qijiaxi
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    /**
     * 处理业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
        ErrorResponse error = ErrorResponse.builder()
            .success(false)
            .message(e.getMessage())
            .code(e.getCode())
            .timestamp(LocalDateTime.now())
            .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
    
    /**
     * 处理项目不存在异常
     */
    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProjectNotFoundException(ProjectNotFoundException e) {
        ErrorResponse error = ErrorResponse.builder()
            .success(false)
            .message(e.getMessage())
            .code("PROJECT_NOT_FOUND")
            .timestamp(LocalDateTime.now())
            .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    
    /**
     * 处理测试用例不存在异常
     */
    @ExceptionHandler(TestCaseNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTestCaseNotFoundException(TestCaseNotFoundException e) {
        ErrorResponse error = ErrorResponse.builder()
            .success(false)
            .message(e.getMessage())
            .code("TESTCASE_NOT_FOUND")
            .timestamp(LocalDateTime.now())
            .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    
    /**
     * 处理测试结果不存在异常
     */
    @ExceptionHandler(TestResultNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTestResultNotFoundException(TestResultNotFoundException e) {
        ErrorResponse error = ErrorResponse.builder()
            .success(false)
            .message(e.getMessage())
            .code("TESTRESULT_NOT_FOUND")
            .timestamp(LocalDateTime.now())
            .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    
    /**
     * 处理参数验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        ErrorResponse error = ErrorResponse.builder()
            .success(false)
            .message("参数验证失败")
            .code("VALIDATION_ERROR")
            .timestamp(LocalDateTime.now())
            .data(errors)
            .build();
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
    
    /**
     * 处理其他未捕获的异常
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception e) {
        ErrorResponse error = ErrorResponse.builder()
            .success(false)
            .message("系统内部错误: " + e.getMessage())
            .code("SYSTEM_ERROR")
            .timestamp(LocalDateTime.now())
            .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}