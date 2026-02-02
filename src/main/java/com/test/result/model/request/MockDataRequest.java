package com.test.result.model.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * 模拟数据生成请求对象
 * 
 * @author qijiaxi
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MockDataRequest {
    
    @NotNull(message = "项目数量不能为空")
    @Min(value = 1, message = "项目数量至少为1")
    private Integer projectCount;
    
    @NotNull(message = "测试用例数量不能为空")
    @Min(value = 1, message = "测试用例数量至少为1")
    private Integer testCaseCount;
}