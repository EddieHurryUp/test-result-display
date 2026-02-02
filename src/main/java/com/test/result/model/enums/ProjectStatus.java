package com.test.result.model.enums;

/**
 * 项目状态枚举
 * 
 * @author qijiaxi
 */
public enum ProjectStatus {
    ACTIVE("active", "进行中"),
    COMPLETED("completed", "已完成"),
    PAUSED("paused", "已暂停");
    
    private final String code;
    private final String description;
    
    ProjectStatus(String code, String description) {
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