package com.test.result.exception;

/**
 * 测试结果不存在异常
 * 
 * @author qijiaxi
 */
public class TestResultNotFoundException extends RuntimeException {
    
    public TestResultNotFoundException(String message) {
        super(message);
    }
    
    public TestResultNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}