# 安装及使用


### Maven 中央仓库搜索
* [https://mvnrepository.com/search?q=com.buession.cas](https://mvnrepository.com/search?q=com.buession.cas)
* [https://search.maven.org/search?q=g:com.buession.cas](https://search.maven.org/search?q=g:com.buession.cas)

### 手动编译
```shell
git clone https://github.com/buession/buession-cas
cd buession-cas/buession-cas-parent && mvn clean install
```

### Maven
```xml
<dependency>
    <groupId>com.buession.cas</groupId>
    <artifactId>buession-cas-xxx</artifactId>
    <version>x.x.x</version>
</dependency>
```

### Gradle
```gradle
compile group: 'com.buession.cas', name: 'buession-cas-xxx', version: 'x.x.x'
```

其中，artifactId 中的 xxx 表示对应的子模块；version 中的 x.x.x 代表版本号，根据需要使用特定版本，建议使用 maven 仓库中已构建好的最新版本[![Maven Central](https://img.shields.io/maven-central/v/com.buession.cas/buession-cas-core.svg)](https://search.maven.org/search?q=g:com.buession.cas)的包。