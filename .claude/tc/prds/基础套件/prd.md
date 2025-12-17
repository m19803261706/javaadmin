# PRD: 基础套件 (myadmin)

## 基本信息

- **项目**: myadmin
- **功能**: 项目开发基础套件
- **创建时间**: 2025-12-17
- **优先级**: P0 紧急
- **MVP 范围**: 完整版（包含所有规划功能+扩展接口）
- **认证方式**: JWT Token

---

## 功能概述

构建一套**通用的企业级项目开发基础套件**，作为后续所有项目的开发基础。该套件包含完整的用户认证、权限管理（精确到菜单和按钮级别）、统一的 API 返回格式，以及与 shadcn-admin 前端框架的完整对接。

### 核心价值
1. **复用性**: 一次开发，多项目复用
2. **规范性**: 统一的代码规范和接口规范
3. **兼容性**: 支持 MySQL 和达梦数据库
4. **扩展性**: 预留扩展接口，易于定制

---

## 技术栈

| 层级 | 技术 | 版本 | 说明 |
|------|------|------|------|
| **后端框架** | Spring Boot | 3.4.x / 3.5.x | 最新稳定版 |
| **ORM** | Spring Data JPA | 3.x | 支持 Specification 动态查询 |
| **数据库** | MySQL / 达梦 | 8.x / DM8 | 双数据库兼容 |
| **工具库** | Hutool | 5.8.x | Java 工具类库 |
| **HTTP客户端** | Forest | 1.8.x | 声明式 HTTP 客户端 |
| **安全框架** | Spring Security | 6.x | JWT 认证 |
| **前端框架** | shadcn-admin | Latest | React + Vite + TypeScript |

---

## 核心功能模块

### 1. 用户管理模块

#### 1.1 用户实体设计
```
User (用户表)
├── id: Long - 主键
├── username: String - 用户名（唯一）
├── password: String - 密码（加密存储）
├── nickname: String - 昵称
├── email: String - 邮箱
├── phone: String - 手机号
├── avatar: String - 头像URL
├── status: Integer - 状态（0禁用/1启用）
├── createTime: DateTime - 创建时间
├── updateTime: DateTime - 更新时间
└── roles: Set<Role> - 角色列表（多对多）
```

#### 1.2 功能清单
- [ ] 用户注册（支持用户名/邮箱/手机号）
- [ ] 用户登录（JWT Token 认证）
- [ ] 用户登出（Token 失效处理）
- [ ] 用户信息查询（分页、条件筛选）
- [ ] 用户信息修改
- [ ] 用户状态管理（启用/禁用）
- [ ] 密码重置
- [ ] 用户角色分配

---

### 2. 角色管理模块

#### 2.1 角色实体设计
```
Role (角色表)
├── id: Long - 主键
├── code: String - 角色编码（唯一）
├── name: String - 角色名称
├── description: String - 角色描述
├── status: Integer - 状态（0禁用/1启用）
├── createTime: DateTime - 创建时间
├── updateTime: DateTime - 更新时间
├── permissions: Set<Permission> - 权限列表（多对多）
└── menus: Set<Menu> - 菜单列表（多对多）
```

#### 2.2 功能清单
- [ ] 角色 CRUD
- [ ] 角色权限分配
- [ ] 角色菜单分配
- [ ] 角色状态管理

---

### 3. 菜单管理模块

#### 3.1 菜单实体设计
```
Menu (菜单表)
├── id: Long - 主键
├── parentId: Long - 父菜单ID
├── name: String - 菜单名称
├── path: String - 路由路径
├── component: String - 前端组件路径
├── icon: String - 图标
├── sort: Integer - 排序
├── type: Integer - 类型（0目录/1菜单/2按钮）
├── permission: String - 权限标识
├── visible: Boolean - 是否可见
├── status: Integer - 状态
├── createTime: DateTime - 创建时间
└── updateTime: DateTime - 更新时间
```

#### 3.2 功能清单
- [ ] 菜单树形结构 CRUD
- [ ] 菜单排序调整
- [ ] 菜单图标管理
- [ ] 按钮权限定义
- [ ] 菜单可见性控制

---

### 4. 权限管理模块

#### 4.1 权限控制粒度
1. **菜单级别**: 控制菜单是否显示
2. **按钮级别**: 控制页面内按钮是否显示/可用
3. **API级别**: 控制接口访问权限

#### 4.2 权限标识规范
```
格式: {模块}:{资源}:{操作}

示例:
- system:user:list     - 用户列表
- system:user:add      - 新增用户
- system:user:edit     - 编辑用户
- system:user:delete   - 删除用户
- system:user:export   - 导出用户
- system:role:assign   - 分配角色
```

#### 4.3 功能清单
- [ ] 权限标识管理
- [ ] 权限校验注解 (@PreAuthorize)
- [ ] 前端权限指令 (v-permission)
- [ ] 动态路由生成

---

### 5. 认证授权模块

#### 5.1 JWT Token 设计
```
Access Token:
├── 有效期: 2小时
├── 载荷: userId, username, roles, permissions
└── 刷新: 需要 Refresh Token

Refresh Token:
├── 有效期: 7天
├── 用途: 刷新 Access Token
└── 存储: Redis / 数据库
```

#### 5.2 功能清单
- [ ] JWT Token 生成与验证
- [ ] Token 刷新机制
- [ ] 登录日志记录
- [ ] 多设备登录控制（可选）
- [ ] 登录失败锁定（可选）

---

### 6. 统一返回格式

#### 6.1 Result 封装
```java
public class Result<T> {
    private Integer code;      // 状态码
    private String message;    // 消息
    private T data;            // 数据
    private Long timestamp;    // 时间戳

    // 静态方法
    public static <T> Result<T> success(T data);
    public static <T> Result<T> success(String message, T data);
    public static <T> Result<T> fail(String message);
    public static <T> Result<T> fail(Integer code, String message);
}
```

#### 6.2 状态码规范
```
200 - 成功
400 - 请求参数错误
401 - 未认证
403 - 无权限
404 - 资源不存在
500 - 服务器内部错误

业务状态码 (1000+):
1001 - 用户名已存在
1002 - 密码错误
1003 - 账号被禁用
...
```

---

## 前端页面清单

### 认证相关
- [ ] 登录页面 (/login)
- [ ] 注册页面 (/register)
- [ ] 忘记密码页面 (/forgot-password)

### 系统管理
- [ ] 用户管理页面 (/system/user)
  - 用户列表（分页、搜索、筛选）
  - 新增/编辑用户弹窗
  - 用户详情抽屉
  - 分配角色弹窗
  - 重置密码操作
- [ ] 角色管理页面 (/system/role)
  - 角色列表
  - 新增/编辑角色弹窗
  - 分配权限弹窗（树形选择）
  - 分配菜单弹窗（树形选择）
- [ ] 菜单管理页面 (/system/menu)
  - 菜单树形列表
  - 新增/编辑菜单弹窗
  - 拖拽排序
  - 按钮权限管理

### 个人中心
- [ ] 个人信息页面 (/profile)
- [ ] 修改密码页面 (/profile/password)

---

## API 接口清单

### 认证接口
```
POST   /api/auth/login          - 用户登录
POST   /api/auth/register       - 用户注册
POST   /api/auth/logout         - 用户登出
POST   /api/auth/refresh        - 刷新Token
GET    /api/auth/info           - 获取当前用户信息
```

### 用户管理接口
```
GET    /api/system/users        - 用户列表（分页）
GET    /api/system/users/{id}   - 用户详情
POST   /api/system/users        - 新增用户
PUT    /api/system/users/{id}   - 修改用户
DELETE /api/system/users/{id}   - 删除用户
PUT    /api/system/users/{id}/status  - 修改用户状态
PUT    /api/system/users/{id}/password - 重置密码
PUT    /api/system/users/{id}/roles    - 分配角色
```

### 角色管理接口
```
GET    /api/system/roles        - 角色列表
GET    /api/system/roles/{id}   - 角色详情
POST   /api/system/roles        - 新增角色
PUT    /api/system/roles/{id}   - 修改角色
DELETE /api/system/roles/{id}   - 删除角色
PUT    /api/system/roles/{id}/menus      - 分配菜单
PUT    /api/system/roles/{id}/permissions - 分配权限
```

### 菜单管理接口
```
GET    /api/system/menus        - 菜单树形列表
GET    /api/system/menus/{id}   - 菜单详情
POST   /api/system/menus        - 新增菜单
PUT    /api/system/menus/{id}   - 修改菜单
DELETE /api/system/menus/{id}   - 删除菜单
GET    /api/system/menus/routes - 获取当前用户路由
```

---

## 任务拆分（初步）

### 数据库任务
- [ ] 设计用户表 (sys_user)
- [ ] 设计角色表 (sys_role)
- [ ] 设计菜单表 (sys_menu)
- [ ] 设计用户角色关联表 (sys_user_role)
- [ ] 设计角色菜单关联表 (sys_role_menu)
- [ ] 编写数据库初始化脚本
- [ ] 达梦数据库兼容性测试

### 后端任务
- [ ] 项目初始化（Spring Boot + JPA + Security）
- [ ] 统一返回 Result 封装
- [ ] 全局异常处理
- [ ] JWT 认证实现
- [ ] 用户管理 CRUD API
- [ ] 角色管理 CRUD API
- [ ] 菜单管理 CRUD API
- [ ] 权限校验注解实现
- [ ] Hutool 工具集成
- [ ] Forest HTTP 客户端集成
- [ ] Swagger/OpenAPI 文档

### 前端任务
- [ ] shadcn-admin 项目初始化
- [ ] 登录/注册页面对接
- [ ] 用户管理页面开发
- [ ] 角色管理页面开发
- [ ] 菜单管理页面开发
- [ ] 权限指令封装
- [ ] 动态路由实现
- [ ] 个人中心页面

### 文档任务
- [ ] API 接口文档
- [ ] 部署文档
- [ ] 开发规范文档

---

## 验收标准

### 功能验收
- [ ] 用户可以正常注册、登录、登出
- [ ] JWT Token 正确生成和验证
- [ ] 管理员可以 CRUD 用户、角色、菜单
- [ ] 菜单根据用户权限动态显示
- [ ] 按钮根据权限控制显示/隐藏
- [ ] API 接口正确进行权限校验
- [ ] 统一返回格式正确

### 技术验收
- [ ] 代码符合规范，有完整中文注释
- [ ] 单元测试覆盖核心逻辑
- [ ] 支持 MySQL 和达梦数据库
- [ ] 前后端接口对接正常
- [ ] 无明显安全漏洞

### 性能验收
- [ ] 登录响应时间 < 500ms
- [ ] 列表查询响应时间 < 1s
- [ ] 支持 100+ 并发用户

---

## 需求收集记录

<details>
<summary>对话历史</summary>

### 第一轮：初始需求
**用户输入**:
> 我需要后端使用最新的springboot 以及 jpa mysql （做到兼容达梦数据库） 权限管理 精确到菜单管理显示 以及菜单的页面按钮控制 前端我准备适配 shadcn-admin 后端需要引入 hutool 以及 腾讯的快速api接口开源工具 还要封装统一的result返回做好和前端的登陆注册 还有权限管理用户管理的启用之类的页面适配 可以使用context7查看相关文档 我想用这套当作我以后的项目开发基础套件

**收集信息**:
- 后端: Spring Boot 3.x + JPA + MySQL/达梦
- 工具: Hutool + Forest (腾讯 dromara)
- 前端: shadcn-admin
- 功能: 权限管理（菜单+按钮级别）、用户管理、登录注册
- 目标: 通用项目基础套件

### 第二轮：技术文档查询
通过 Context7 查询了以下技术文档:
- Spring Boot 3.x 安全配置
- Spring Data JPA Specification 动态查询
- Hutool 工具库使用
- Forest HTTP 客户端
- shadcn-admin 组件

### 第三轮：优先级确认
- 优先级: P0 紧急
- MVP 范围: 完整版
- 认证方式: JWT Token

</details>

---

> 由 TC 系统自动创建
> 功能: 基础套件
> 技术文档: .claude/tc/context/基础套件/libraries.md
