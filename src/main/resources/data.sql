-- 初始化项目数据
INSERT INTO projects (name, description, type, status) VALUES
('用户管理系统测试', '测试用户注册、登录、权限管理等功能', 'INTEGRATION', 'ACTIVE'),
('支付模块测试', '测试支付流程、订单处理等核心业务', 'UNIT', 'ACTIVE'),
('性能压力测试', '测试系统在高并发下的性能表现', 'PERFORMANCE', 'COMPLETED'),
('移动端UI测试', '测试移动端用户界面和交互', 'UI', 'ACTIVE'),
('API接口测试', '测试RESTful API接口功能', 'INTEGRATION', 'ACTIVE');

-- 初始化测试用例数据
-- 用户管理系统测试用例
INSERT INTO test_cases (project_id, name, description, priority, module, tags) VALUES
(1, '用户注册成功', '测试正常流程下用户注册功能', 'HIGH', '用户管理', 'regression,smoke'),
(1, '用户登录验证', '测试用户登录身份验证', 'HIGH', '用户管理', 'regression,security'),
(1, '密码强度验证', '测试密码复杂度要求', 'MEDIUM', '用户管理', 'validation'),
(1, '邮箱格式验证', '测试邮箱格式合法性', 'MEDIUM', '用户管理', 'validation'),
(1, '用户权限检查', '测试不同用户权限访问控制', 'HIGH', '权限管理', 'security'),
(1, '用户信息更新', '测试用户资料修改功能', 'MEDIUM', '用户管理', 'regression'),
(1, '用户注销功能', '测试用户安全退出', 'LOW', '用户管理', 'session');

-- 支付模块测试用例
INSERT INTO test_cases (project_id, name, description, priority, module, tags) VALUES
(2, '支付接口调用', '测试支付网关接口集成', 'HIGH', '支付模块', 'integration'),
(2, '支付金额验证', '测试支付金额合法性', 'HIGH', '支付模块', 'validation'),
(2, '订单状态更新', '测试订单创建到完成的流程', 'HIGH', '订单管理', 'workflow'),
(2, '支付超时处理', '测试支付超时异常处理', 'MEDIUM', '支付模块', 'error-handling'),
(2, '重复支付检测', '测试重复支付防护机制', 'HIGH', '支付模块', 'security'),
(2, '退款流程测试', '测试退款申请和处理', 'MEDIUM', '订单管理', 'workflow'),
(2, '支付方式验证', '测试多种支付方式支持', 'LOW', '支付模块', 'feature');

-- 性能压力测试用例
INSERT INTO test_cases (project_id, name, description, priority, module, tags) VALUES
(3, '并发用户登录', '测试1000并发用户登录', 'HIGH', '性能测试', 'load-test'),
(3, '大数据量查询', '测试大数据量查询性能', 'MEDIUM', '性能测试', 'stress-test'),
(3, '内存泄漏检测', '测试长时间运行内存泄漏', 'MEDIUM', '性能测试', 'memory-test');

-- 移动端UI测试用例
INSERT INTO test_cases (project_id, name, description, priority, module, tags) VALUES
(4, '登录页面UI', '测试移动端登录页面布局', 'MEDIUM', 'UI测试', 'responsive'),
(4, '按钮点击响应', '测试按钮点击事件响应', 'LOW', 'UI测试', 'interaction'),
(4, '横竖屏切换', '测试横竖屏切换适配', 'MEDIUM', 'UI测试', 'responsive');

-- API接口测试用例
INSERT INTO test_cases (project_id, name, description, priority, module, tags) VALUES
(5, '用户API接口', '测试用户相关API接口', 'HIGH', 'API测试', 'integration'),
(5, '订单API接口', '测试订单相关API接口', 'HIGH', 'API测试', 'integration'),
(5, '错误码测试', '测试各种错误码返回', 'MEDIUM', 'API测试', 'error-handling');

-- 初始化测试结果数据
INSERT INTO test_results (test_case_id, status, execution_time, error_message, environment, executor) VALUES
-- 用户注册成功 - 通过
(1, 'PASSED', 250, NULL, 'test-1', 'auto-generator'),
(1, 'PASSED', 280, NULL, 'test-2', 'auto-generator'),
(1, 'FAILED', 300, '数据库连接超时', 'test-1', 'auto-generator'),
(1, 'PASSED', 220, NULL, 'test-3', 'auto-generator'),

-- 用户登录验证 - 通过
(2, 'PASSED', 150, NULL, 'test-1', 'auto-generator'),
(2, 'PASSED', 180, NULL, 'test-2', 'auto-generator'),
(2, 'PASSED', 160, NULL, 'test-3', 'auto-generator'),

-- 密码强度验证 - 部分失败
(3, 'PASSED', 120, NULL, 'test-1', 'auto-generator'),
(3, 'FAILED', 200, '密码强度不足', 'test-2', 'auto-generator'),
(3, 'PASSED', 110, NULL, 'test-3', 'auto-generator'),

-- 支付接口调用 - 通过
(8, 'PASSED', 500, NULL, 'test-1', 'auto-generator'),
(8, 'PASSED', 520, NULL, 'test-2', 'auto-generator'),
(8, 'PASSED', 480, NULL, 'test-3', 'auto-generator'),

-- 订单状态更新 - 通过
(10, 'PASSED', 300, NULL, 'test-1', 'auto-generator'),
(10, 'PASSED', 320, NULL, 'test-2', 'auto-generator'),
(10, 'ERROR', 0, '空指针异常', 'test-1', 'auto-generator'),

-- 并发用户登录 - 部分失败
(14, 'FAILED', 2000, '响应时间超时', 'test-1', 'auto-generator'),
(14, 'PASSED', 1500, NULL, 'test-2', 'auto-generator'),
(14, 'FAILED', 2500, '连接池耗尽', 'test-1', 'auto-generator'),

-- 登录页面UI - 通过
(18, 'PASSED', 80, NULL, 'test-1', 'auto-generator'),
(18, 'PASSED', 90, NULL, 'test-2', 'auto-generator'),

-- 用户API接口 - 通过
(22, 'PASSED', 200, NULL, 'test-1', 'auto-generator'),
(22, 'PASSED', 220, NULL, 'test-2', 'auto-generator'),
(22, 'PASSED', 190, NULL, 'test-3', 'auto-generator');