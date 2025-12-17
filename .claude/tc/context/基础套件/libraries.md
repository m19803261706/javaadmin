# 基础套件 - 技术文档参考

> 由 TC 系统自动生成，基于 Context7 查询结果

## 1. Spring Boot 3.x

### 版本信息
- 推荐版本: Spring Boot 3.4.x / 3.5.x
- Context7 ID: `/spring-projects/spring-boot`

### 安全配置示例

```yaml
# OAuth2 资源服务器配置
spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          jwk-set-uri: "https://example.com/oauth2/default/v1/keys"
```

### 测试安全示例

```java
@SpringBootTest
@AutoConfigureMockMvc
class MySecurityTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void testAdminEndpoint() throws Exception {
        mockMvc.perform(get("/admin"))
               .andExpect(status().isOk());
    }
}
```

---

## 2. Spring Data JPA

### 版本信息
- Context7 ID: `/spring-projects/spring-data-jpa`
- Benchmark Score: 92.8

### Repository 定义示例

```java
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductRepository extends JpaRepository<Product, Long>,
                                            JpaSpecificationExecutor<Product> {
}
```

### Specification 动态查询

```java
public class ProductSpecifications {
    public static Specification<Product> hasName(String name) {
        return (root, query, cb) ->
            name == null ? null : cb.equal(root.get("name"), name);
    }

    public static Specification<Product> isActive() {
        return (root, query, cb) -> cb.isTrue(root.get("active"));
    }
}
```

---

## 3. Hutool 工具库

### 版本信息
- Context7 ID: `/dromara/hutool`
- 推荐版本: 5.8.x

### 常用工具示例

```java
// 集合工具
HashMap<String, String> map = CollectionUtil.newHashMap();
String str = CollectionUtil.join(col, "#");

// 正则工具
String result = ReUtil.get("\\w{2}", content, 0);

// 数据库工具
Db db = Db.use();
List<Entity> users = db.findAll("users");

// 国密算法
String sm3Hash = SmUtil.sm3("数据内容");
```

---

## 4. Forest HTTP 客户端

### 版本信息
- Context7 ID: `/dromara/forest`
- 推荐版本: 1.8.x

### Spring Boot 配置

```yaml
forest:
  connect-timeout: 10000
  read-timeout: 3600000
  variables:
    baseUrl: https://api.example.com
    apiKey: YOUR_API_KEY
```

### 声明式接口定义

```java
@ForestScan(basePackages = "com.yoursite.client")
public interface AmapClient {
    @Get("http://ditu.amap.com/service/regeo?longitude={0}&latitude={1}")
    Map getLocation(String longitude, String latitude);
}

// 发送 JSON
@Post("/register")
String registerUser(@JSONBody MyUser user);

// 发送 XML
@Post("/message")
String sendXmlMessage(@XMLBody MyMessage message);
```

---

## 5. shadcn-admin 前端框架

### 版本信息
- Context7 ID: `/satnaing/shadcn-admin`
- 技术栈: React + Vite + TypeScript + Tailwind CSS

### 核心组件

#### 主题管理
```typescript
import { useTheme } from '@/context/theme-provider'

function ThemeToggle() {
  const { theme, setTheme } = useTheme()
  return <Button onClick={() => setTheme('dark')}>Dark Mode</Button>
}
```

#### 数据表格
```typescript
import { DataTableToolbar } from '@/components/data-table/toolbar'
import { DataTablePagination } from '@/components/data-table/pagination'

<DataTableToolbar
  table={table}
  searchPlaceholder="搜索用户..."
  filters={[
    { columnId: 'status', title: '状态', options: [...] }
  ]}
/>
```

#### 登录表单
```typescript
import { UserAuthForm } from '@/features/auth/sign-in/components/user-auth-form'

<UserAuthForm redirectTo="/dashboard" />
```

#### 命令菜单 (Cmd+K)
```typescript
import { CommandMenu } from '@/components/command-menu'
import { useSearch } from '@/context/search-provider'
```

---

## 6. 达梦数据库兼容性注意事项

### JPA 方言配置
```yaml
spring:
  jpa:
    database-platform: org.hibernate.dialect.DmDialect
    # 或使用自定义方言
```

### SQL 兼容性要点
- 避免使用 MySQL 特有函数
- 使用标准 SQL 语法
- 注意关键字差异（如 `USER` 是达梦保留字）
- 分页使用 JPA 标准方式，不要硬编码 LIMIT
