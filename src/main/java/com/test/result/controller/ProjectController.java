package com.test.result.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.test.result.model.dto.ErrorResponse;
import com.test.result.model.dto.ProjectDTO;
import com.test.result.model.request.ProjectRequest;
import com.test.result.service.ProjectService;

/**
 * 项目管理控制器
 * 
 * @author qijiaxi
 */
@RestController
@RequestMapping("/api/v1/projects")
@Tag(name = "项目管理", description = "测试项目管理相关接口")
public class ProjectController {
    
    @Autowired
    private ProjectService projectService;
    
    @GetMapping
    @Operation(summary = "获取项目列表", description = "支持分页、筛选和排序")
    public ResponseEntity<Page<ProjectDTO>> getProjects(
            @RequestParam(defaultValue = "0") @Parameter(description = "页码，从0开始") int page,
            @RequestParam(defaultValue = "10") @Parameter(description = "每页大小") int size,
            @RequestParam(required = false) @Parameter(description = "项目类型") String type,
            @RequestParam(required = false) @Parameter(description = "项目状态") String status,
            @RequestParam(defaultValue = "createdAt") @Parameter(description = "排序字段") String sortBy,
            @RequestParam(defaultValue = "desc") @Parameter(description = "排序方向") String order) {
        
        Sort sort = Sort.by(Sort.Direction.fromString(order), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<ProjectDTO> projects = projectService.getProjects(type, status, pageable);
        return ResponseEntity.ok(projects);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "获取项目详情", description = "根据ID获取项目详细信息")
    public ResponseEntity<ProjectDTO> getProjectById(@PathVariable Long id) {
        ProjectDTO project = projectService.getProjectById(id);
        return ResponseEntity.ok(project);
    }
    
    @PostMapping
    @Operation(summary = "创建项目", description = "创建新的测试项目")
    public ResponseEntity<ProjectDTO> createProject(@Valid @RequestBody ProjectRequest request) {
        ProjectDTO project = projectService.createProject(request);
        return ResponseEntity.ok(project);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "更新项目", description = "根据ID更新项目信息")
    public ResponseEntity<ProjectDTO> updateProject(@PathVariable Long id, @Valid @RequestBody ProjectRequest request) {
        ProjectDTO project = projectService.updateProject(id, request);
        return ResponseEntity.ok(project);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "删除项目", description = "根据ID删除项目")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
}