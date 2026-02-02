package com.test.result.model.enums;

/**
 * 测试状态枚举
 * 
 * @author qijiaxi
 */
public enum TestStatus {
    PASSED("passed", "通过"),
    FAILED("failed", "失败"),
    SKIPPED("skipped", "跳过"),
    ERROR("error", "错误");
    
    private final String code;
    private final String description;
    
    TestStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }
    
    public String getCode() {
        return code;
    }
    
    public String getDescription() {
        return description;
    }
}