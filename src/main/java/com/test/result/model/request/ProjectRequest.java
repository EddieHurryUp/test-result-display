package com.test.result.model.request;

import jakarta.validation.constraints.*;
import lombok.*;

import com.test.result.model.enums.ProjectStatus;
import com.test.result.model.enums.TestType;

/**
 * 项目创建请求对象
 * 
 * @author qijiaxi
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectRequest {
    
    @NotBlank(message = "项目名称不能为空")
    @Size(max = 100, message = "项目名称不能超过100个字符")
    private String name;
    
    @Size(max = 500, message = "项目描述不能超过500个字符")
    private String description;
    
    @NotNull(message = "项目类型不能为空")
    private TestType type;
    
    @NotNull(message = "项目状态不能为空")
    private ProjectStatus status;
    
    // 自定义验证方法
    public boolean isValid() {
        return ProjectStatus.ACTIVE.equals(status) || 
               ProjectStatus.COMPLETED.equals(status) || 
               ProjectStatus.PAUSED.equals(status);
    }
}