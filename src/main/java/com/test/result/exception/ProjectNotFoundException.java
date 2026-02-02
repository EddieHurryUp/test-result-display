package com.test.result.exception;

/**
 * 项目不存在异常
 * 
 * @author qijiaxi
 */
public class ProjectNotFoundException extends RuntimeException {
    
    public ProjectNotFoundException(String message) {
        super(message);
    }
    
    public ProjectNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}