package com.test.result.model.enums;

/**
 * 测试类型枚举
 * 
 * @author qijiaxi
 */
public enum TestType {
    UNIT("unit", "单元测试"),
    INTEGRATION("integration", "集成测试"),
    PERFORMANCE("performance", "性能测试"),
    UI("ui", "UI测试");
    
    private final String code;
    private final String description;
    
    TestType(String code, String description) {
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