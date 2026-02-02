package com.test.result.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.test.result.exception.ProjectNotFoundException;
import com.test.result.model.dto.ProjectDTO;
import com.test.result.model.entity.Project;
import com.test.result.model.request.ProjectRequest;
import com.test.result.repository.ProjectRepository;
import com.test.result.service.ProjectService;

/**
 * 项目服务实现类
 * 
 * @author qijiaxi
 */
@Service
public class ProjectServiceImpl implements ProjectService {
    
    @Autowired
    private ProjectRepository projectRepository;
    
    @Override
    public Page<ProjectDTO> getProjects(String type, String status, Pageable pageable) {
        Page<Project> projects = projectRepository.findProjectsByFilters(type, status, pageable);
        return projects.map(this::convertToDTO);
    }
    
    @Override
    public ProjectDTO getProjectById(Long id) {
        Project project = projectRepository.findById(id)
            .orElseThrow(() -> new ProjectNotFoundException("项目不存在，ID: " + id));
        return convertToDTO(project);
    }
    
    @Override
    public ProjectDTO createProject(ProjectRequest request) {
        Project project = Project.builder()
            .name(request.getName())
            .description(request.getDescription())
            .type(request.getType())
            .status(request.getStatus())
            .build();
        
        Project savedProject = projectRepository.save(project);
        return convertToDTO(savedProject);
    }
    
    @Override
    public ProjectDTO updateProject(Long id, ProjectRequest request) {
        Project project = projectRepository.findById(id)
            .orElseThrow(() -> new ProjectNotFoundException("项目不存在，ID: " + id));
        
        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setType(request.getType());
        project.setStatus(request.getStatus());
        
        Project updatedProject = projectRepository.save(project);
        return convertToDTO(updatedProject);
    }
    
    @Override
    public void deleteProject(Long id) {
        Project project = projectRepository.findById(id)
            .orElseThrow(() -> new ProjectNotFoundException("项目不存在，ID: " + id));
        
        projectRepository.delete(project);
    }
    
    @Override
    public ProjectDTO convertToDTO(Project project) {
        return ProjectDTO.builder()
            .id(project.getId())
            .name(project.getName())
            .description(project.getDescription())
            .type(project.getType())
            .status(project.getStatus())
            .createdAt(project.getCreatedAt())
            .updatedAt(project.getUpdatedAt())
            .build();
    }
}