package com.test.result.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.test.result.model.dto.ProjectDTO;
import com.test.result.model.entity.Project;
import com.test.result.model.request.ProjectRequest;

/**
 * 项目服务接口
 * 
 * @author qijiaxi
 */
public interface ProjectService {
    
    /**
     * 获取项目列表
     */
    Page<ProjectDTO> getProjects(String type, String status, Pageable pageable);
    
    /**
     * 获取项目详情
     */
    ProjectDTO getProjectById(Long id);
    
    /**
     * 创建项目
     */
    ProjectDTO createProject(ProjectRequest request);
    
    /**
     * 更新项目
     */
    ProjectDTO updateProject(Long id, ProjectRequest request);
    
    /**
     * 删除项目
     */
    void deleteProject(Long id);
    
    /**
     * 将实体转换为DTO
     */
    ProjectDTO convertToDTO(Project project);
}