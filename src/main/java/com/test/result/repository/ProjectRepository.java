package com.test.result.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import com.test.result.model.entity.Project;

/**
 * 项目数据访问接口
 * 
 * @author qijiaxi
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    
    /**
     * 根据项目类型查询项目列表
     */
    List<Project> findByType(String type);
    
    /**
     * 根据项目状态查询项目列表
     */
    List<Project> findByStatus(String status);
    
    /**
     * 分页查询项目列表（支持筛选）
     */
    @Query("SELECT p FROM Project p WHERE " +
           "(:type IS NULL OR p.type = :type) AND " +
           "(:status IS NULL OR p.status = :status)")
    Page<Project> findProjectsByFilters(@Param("type") String type,
                                       @Param("status") String status,
                                       Pageable pageable);
    
    /**
     * 根据项目名称模糊查询
     */
    List<Project> findByNameContainingIgnoreCase(String name);
}