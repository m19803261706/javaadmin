-- =====================================================
-- MyAdmin 系统核心表结构
-- 版本: V1
-- 说明: 创建用户、角色、菜单及关联表
-- 兼容: MySQL 8.x / 达梦 DM8
-- =====================================================

-- -----------------------------------------------------
-- 1. 用户表 (sys_user)
-- 存储系统用户基本信息
-- -----------------------------------------------------
CREATE TABLE sys_user (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    username VARCHAR(50) NOT NULL COMMENT '用户名（登录账号）',
    password VARCHAR(100) NOT NULL COMMENT '密码（BCrypt加密）',
    nickname VARCHAR(50) DEFAULT NULL COMMENT '用户昵称',
    email VARCHAR(100) DEFAULT NULL COMMENT '电子邮箱',
    phone VARCHAR(20) DEFAULT NULL COMMENT '手机号码',
    avatar VARCHAR(255) DEFAULT NULL COMMENT '头像URL',
    status INT NOT NULL DEFAULT 1 COMMENT '状态（0禁用/1启用）',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_sys_user_username (username),
    KEY idx_sys_user_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- -----------------------------------------------------
-- 2. 角色表 (sys_role)
-- 存储系统角色信息
-- -----------------------------------------------------
CREATE TABLE sys_role (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '角色ID',
    code VARCHAR(50) NOT NULL COMMENT '角色编码（唯一标识）',
    name VARCHAR(50) NOT NULL COMMENT '角色名称',
    description VARCHAR(200) DEFAULT NULL COMMENT '角色描述',
    status INT NOT NULL DEFAULT 1 COMMENT '状态（0禁用/1启用）',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_sys_role_code (code),
    KEY idx_sys_role_code (code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色表';

-- -----------------------------------------------------
-- 3. 菜单表 (sys_menu)
-- 存储系统菜单和权限按钮信息
-- -----------------------------------------------------
CREATE TABLE sys_menu (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
    parent_id BIGINT NOT NULL DEFAULT 0 COMMENT '父菜单ID（0表示顶级）',
    name VARCHAR(50) NOT NULL COMMENT '菜单名称',
    path VARCHAR(200) DEFAULT NULL COMMENT '路由路径',
    component VARCHAR(200) DEFAULT NULL COMMENT '前端组件路径',
    icon VARCHAR(50) DEFAULT NULL COMMENT '菜单图标',
    sort INT NOT NULL DEFAULT 0 COMMENT '排序号（越小越靠前）',
    type INT NOT NULL DEFAULT 1 COMMENT '类型（0目录/1菜单/2按钮）',
    permission VARCHAR(100) DEFAULT NULL COMMENT '权限标识',
    visible INT NOT NULL DEFAULT 1 COMMENT '是否可见（0隐藏/1显示）',
    status INT NOT NULL DEFAULT 1 COMMENT '状态（0禁用/1启用）',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    KEY idx_sys_menu_parent_id (parent_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='菜单表';

-- -----------------------------------------------------
-- 4. 用户角色关联表 (sys_user_role)
-- 用户与角色的多对多关联
-- -----------------------------------------------------
CREATE TABLE sys_user_role (
    user_id BIGINT NOT NULL COMMENT '用户ID',
    role_id BIGINT NOT NULL COMMENT '角色ID',
    PRIMARY KEY (user_id, role_id),
    KEY idx_sys_user_role_user_id (user_id),
    KEY idx_sys_user_role_role_id (role_id),
    CONSTRAINT fk_sys_user_role_user FOREIGN KEY (user_id) REFERENCES sys_user (id) ON DELETE CASCADE,
    CONSTRAINT fk_sys_user_role_role FOREIGN KEY (role_id) REFERENCES sys_role (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户角色关联表';

-- -----------------------------------------------------
-- 5. 角色菜单关联表 (sys_role_menu)
-- 角色与菜单的多对多关联
-- -----------------------------------------------------
CREATE TABLE sys_role_menu (
    role_id BIGINT NOT NULL COMMENT '角色ID',
    menu_id BIGINT NOT NULL COMMENT '菜单ID',
    PRIMARY KEY (role_id, menu_id),
    KEY idx_sys_role_menu_role_id (role_id),
    KEY idx_sys_role_menu_menu_id (menu_id),
    CONSTRAINT fk_sys_role_menu_role FOREIGN KEY (role_id) REFERENCES sys_role (id) ON DELETE CASCADE,
    CONSTRAINT fk_sys_role_menu_menu FOREIGN KEY (menu_id) REFERENCES sys_menu (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色菜单关联表';
