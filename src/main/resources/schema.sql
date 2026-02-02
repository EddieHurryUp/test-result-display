-- 创建项目表
CREATE TABLE IF NOT EXISTS projects (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    type VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 创建测试用例表
CREATE TABLE IF NOT EXISTS test_cases (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    project_id BIGINT NOT NULL,
    name VARCHAR(200) NOT NULL,
    description TEXT,
    priority VARCHAR(50) NOT NULL,
    module VARCHAR(100),
    tags VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE
);

-- 创建测试结果表
CREATE TABLE IF NOT EXISTS test_results (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    test_case_id BIGINT NOT NULL,
    status VARCHAR(50) NOT NULL,
    execution_time INT,
    error_message TEXT,
    executed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    environment VARCHAR(100),
    executor VARCHAR(100),
    FOREIGN KEY (test_case_id) REFERENCES test_cases(id) ON DELETE CASCADE
);

-- 创建索引
CREATE INDEX IF NOT EXISTS idx_testcase_project ON test_cases(project_id);
CREATE INDEX IF NOT EXISTS idx_testcase_priority ON test_cases(priority);
CREATE INDEX IF NOT EXISTS idx_testcase_module ON test_cases(module);
CREATE INDEX IF NOT EXISTS idx_result_testcase ON test_results(test_case_id);
CREATE INDEX IF NOT EXISTS idx_result_status ON test_results(status);
CREATE INDEX IF NOT EXISTS idx_result_executed ON test_results(executed_at);