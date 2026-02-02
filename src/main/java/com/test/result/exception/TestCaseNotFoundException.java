package com.test.result.exception;

/**
 * 测试用例不存在异常
 * 
 * @author qijiaxi
 */
public class TestCaseNotFoundException extends RuntimeException {
    
    public TestCaseNotFoundException(String message) {
        super(message);
    }
    
    public TestCaseNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}