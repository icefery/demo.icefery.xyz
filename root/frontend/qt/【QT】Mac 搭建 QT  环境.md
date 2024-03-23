## 一、安装相关

### 1.1 在线安装

- https://www.qt.io/download-qt-installer

### 1.2 升级维护

![](__image__/d49ab7ba7ece4667a7bf01bc2607f64c.png)

![](__image__/2fc26aba34fd4b54ac2aef6144a8b02d.png)

### 1.3 文档

![](__image__/f90344c5c3374c1c85fc8528aa055425.png)

## 二、命令行编译运行

### 2.1 环境变量

将 Qt 库的 `bin` 目录加入 `PATH` 环境变量：

```bash
export PATH=$PATH:/opt/qt/6.2.2/macos/bin
```

### 2.2 示例

1. 手动创建项目目录

   ```bash
   mkdir -p qt-demo
   ```

2. `main.cpp`

   ![](__image__/f6a1394ed8fc435db24d93e4145b128a.png)

3. QMake 生成 `.pro` 文件

   ```bash
   qmake -project
   ```

   ![](__image__/e870a83897e241e4958e54943bf1b452.png)

4. 手动添加构建选项

   ```bash
   QT += widgets
   ```

   ![](__image__/6f3184fbac7d4d1a80057e7e6e43c88c.png)

5. 生成 `Makefile`

   ```bash
   qmake
   ```

   ![](__image__/4b6060f833924736b3f7de5786d2c993.png)

6. 编译链接

   ```bash
   make
   ```

   ![](__image__/6cf33130a8a449d1bce8dc1d25bf46b8.png)

7. 运行

   ![](__image__/c622065f716b4d27945d7c10c4a791ef.png)
