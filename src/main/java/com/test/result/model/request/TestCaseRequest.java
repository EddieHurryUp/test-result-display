package com.test.result.model.request;

import jakarta.validation.constraints.*;
import lombok.*;

import com.test.result.model.enums.Priority;

/**
 * 测试用例创建请求对象
 * 
 * @author qijiaxi
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestCaseRequest {
    
    @NotNull(message = "项目ID不能为空")
    private Long projectId;
    
    @NotBlank(message = "用例名称不能为空")
    @Size(max = 200, message = "用例名称不能超过200个字符")
    private String name;
    
    @Size(max = 1000, message = "用例描述不能超过1000个字符")
    private String description;
    
    @NotNull(message = "优先级不能为空")
    private Priority priority;
    
    private String module;
    private String tags;
}