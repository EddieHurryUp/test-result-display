package com.test.result.model.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * 随机执行测试请求对象
 * 
 * @author qijiaxi
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RandomExecutionRequest {
    
    @NotNull(message = "执行数量不能为空")
    @Min(value = 1, message = "执行数量至少为1")
    private Integer count;
}