package com.test.result.model.enums;

/**
 * 优先级枚举
 * 
 * @author qijiaxi
 */
public enum Priority {
    HIGH("high", "高"),
    MEDIUM("medium", "中"),
    LOW("low", "低");
    
    private final String code;
    private final String description;
    
    Priority(String code, String description) {
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