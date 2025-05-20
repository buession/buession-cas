 Buession Cas Changelog
===========================


## [3.0.1](https://github.com/buession/buession-cas/releases/tag/v3.0.1) (2025-05-20)

### 🔨依赖升级

- [依赖库版本升级和安全漏洞修复](https://github.com/buession/buession-parent/releases/tag/v3.0.1)


---


## [3.0.0](https://github.com/buession/buession-cas/releases/tag/v3.0.0) (2024-11-07)

### 🔨依赖升级

- [依赖库版本升级和安全漏洞修复](https://github.com/buession/buession-parent/releases/tag/v3.0.0)
- [cas](https://www.apereo.org/cas) 版本升级至 6.6.15.2


### 🔔 变化

- RegisteredService id 类型由 long 变更为 int
- 删除 buession-cas-support-couchbase、buession-cas-support-couchdb、buession-cas-support-jdbc、buession-cas-support-mongodb、buession-cas-support-redis、buession-cas-support-cassandra、buession-cas-support-hazelcast


---


## [2.3.3](https://github.com/buession/buession-cas/releases/tag/v2.3.3) (2024-05-06)

### 🔨依赖升级

- [依赖库版本升级和安全漏洞修复](https://github.com/buession/buession-parent/releases/tag/v2.3.3)


### 🔔 变化

- **buession-cas-logging：** 基本日志控制台模式，集成 com.buession.logging:buession-logging-support-console


### ⏪ 优化

- **buession-cas-logging：** 优化和规范 LogHandler bean 初始化
- **buession-cas-logging：** 优化日志数据参数


---


## [2.3.2](https://github.com/buession/buession-cas/releases/tag/v2.3.2) (2023-12-27)

### 🔨依赖升级

- [依赖库版本升级和安全漏洞修复](https://github.com/buession/buession-parent/releases/tag/v2.3.2)


---


## [2.3.1](https://github.com/buession/buession-cas/releases/tag/v2.3.1) (2023-11-19)

### 🔨依赖升级

- [依赖库版本升级和安全漏洞修复](https://github.com/buession/buession-parent/releases/tag/v2.3.1)


### 🐞 Bug 修复

- **buession-cas-webflow：** 修复 AccountPasswordMustChangeException 异常时，工作流 casMustChangePassView 不存在的 BUG


---


## [2.3.0](https://github.com/buession/buession-cas/releases/tag/v2.3.0) (2023-08-17)

### 🔨依赖升级

- [依赖库版本升级和安全漏洞修复](https://github.com/buession/buession-parent/releases/tag/v2.3.0)


### ⭐ 新特性

- **buession-cas-support：** 登录日志增加记录 User-Agent、设备客户端操作系统及其版本、浏览器及其版本
- **buession-cas-support：** 登录日志增加 JDBC、MongoDB、CouchDb、Redis、Rest、Slf4j、Couchbase、DynamoDb 支持
- **buession-cas-support：** 登录日志增加存储历史登录日志


---


## [2.2.1](https://github.com/buession/buession-cas/releases/tag/v2.2.1) (2023-03-31)

### 🔨依赖升级

- [依赖库版本升级和安全漏洞修复](https://github.com/buession/buession-parent/releases/tag/v2.2.1)


---


## [2.2.0](https://github.com/buession/buession-cas/releases/tag/v2.2.0) (2023-03-10)

### 🔨依赖升级

- [依赖库版本升级和安全漏洞修复](https://github.com/buession/buession-parent/releases/tag/v2.2.0)


### ⭐ 新特性

- **buession-cas-client：** 新增 RegisteredService Client SDK


---


## [2.1.2](https://github.com/buession/buession-cas/releases/tag/v2.1.2) (2022-11-15)

### 🔨依赖升级

- [依赖库版本升级和安全漏洞修复](https://github.com/buession/buession-parent/releases/tag/v2.1.2)


---


## [2.1.1](https://github.com/buession/buession-cas/releases/tag/v2.1.1) (2022-08-25)

### 🔨依赖升级

- [依赖库版本升级和安全漏洞修复](https://github.com/buession/buession-parent/releases/tag/v2.1.1)


---


## [2.1.0](https://github.com/buession/buession-cas/releases/tag/v2.1.0) (2022-08-10)

### 🔨依赖升级

- [依赖库版本升级和安全漏洞修复](https://github.com/buession/buession-parent/releases/tag/v2.1.0)
- [javaparser-core](https://mvnrepository.com/artifact/com.github.javaparser/javaparser-core) 版本升级至 3.24.4


### ⭐ 新特性

- **buession-cas-support：** 增加记录用户登录日志


---


## [2.0.2](https://github.com/buession/buession-cas/releases/tag/v2.0.2) (2022-07-28)

### 🔨依赖升级

- [依赖库版本升级和安全漏洞修复](https://github.com/buession/buession-parent/releases/tag/v2.0.2)


---


## [2.0.1](https://github.com/buession/buession-cas/releases/tag/v2.0.1) (2022-07-18)

### 🔨依赖升级

- [依赖库版本升级和安全漏洞修复](https://github.com/buession/buession-parent/releases/tag/v2.0.1)


---


## [2.0.0](https://github.com/buession/buession-cas/releases/tag/v2.0.0) (2022-07-08)

### 🔨依赖升级

- [依赖库版本升级和安全漏洞修复](https://github.com/buession/buession-parent/releases/tag/v2.0.0)
- [javaparser-core](https://mvnrepository.com/artifact/com.github.javaparser/javaparser-core) 版本升级至 3.24.0


### ⭐ 新特性

- **buession-cas-audit：** 新增集成 CAS Audit 模块
- **buession-cas-captcha：** 新增支持极验、阿里云、腾讯云行为验证
- **buession-cas-service** 新增集成 CAS Service Registry 模块
- **buession-cas-throttle：** 新增集成 CAS Throttle 模块
- **buession-cas-ticket：** 集成 CAS Ticket Registry 模块
- **buession-cas-webflow：** 新增支持极验、阿里云、腾讯云行为验证