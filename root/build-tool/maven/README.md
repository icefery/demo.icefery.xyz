# Maven

## 镜像源

#### 修改 `settings.xml` 方式

```xml
<?xml version="1.0" encoding="UTF-8"?>
<settings>
    <mirrors>
        <mirror>
            <id>aliyun</id>
            <url>https://maven.aliyun.com/repository/public</url>
            <mirrorOf>*</mirrorOf>
        </mirror>
    </mirrors>
</settings>
```

#### 修改 `pom.xml` 方式

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project>
    <repositories>
        <repository>
            <id>aliyun</id>
            <url>https://maven.aliyun.com/repository/public</url>
        </repository>
    </repositories>

    <pluginRepositories>
        <pluginRepository>
            <id>aliyun</id>
            <url>https://maven.aliyun.com/repository/public</url>
        </pluginRepository>
    </pluginRepositories>
</project>
```
