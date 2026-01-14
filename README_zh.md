# egps-TQTools（SeqTools）

本仓库是 eGPS2 的一个扩展模块集合（插件/组件），侧重于生物序列相关的工具与工作流面板（SeqTools）。

- English README: `README.md`

---

## 项目定位

- 本项目是 eGPS2 桌面软件（Swing）的一个模块集合，代码在 `src/`，依赖 JAR 在 `dependency-egps/`。
- 本项目不使用 Maven/Gradle；通常用 IntelliJ IDEA（JDK 25）或手动 `javac` 编译。

## 环境要求

- JDK：**25**
- 本机 JDK 25 路径（按现有约定使用全路径）：
  - `/home/dell/software/java25/jdk-25+36/bin/javac`
  - `/home/dell/software/java25/jdk-25+36/bin/java`

## 编译（手动）

```bash
/home/dell/software/java25/jdk-25+36/bin/javac \
  -d ./out/production/egps-TQTools \
  -cp "dependency-egps/*" \
  $(find src -name "*.java")
```

如果你需要同时检查编译告警（推荐在清理告警时使用）：

```bash
/home/dell/software/java25/jdk-25+36/bin/javac -Xlint:all -Xmaxwarns 10000 \
  -d ./out/production/egps-TQTools \
  -cp "dependency-egps/*" \
  $(find src -name "*.java")
```

更多告警清理策略见：`remove_compile_warnings.md`。

## 打包为 JAR（可选）

编译完成后，可以从 class 输出目录直接打包：

```bash
jar -cf egps-TQTools.jar -C ./out/production/egps-TQTools .
```

## 部署到 eGPS2（概念说明）

通常做法是把编译产物（或打出来的 `egps-TQTools.jar`）放入 eGPS2 的 `dependency-egps/`，由 eGPS2 的模块/插件系统在启动时或运行时加载。

注意：本仓库仅包含模块代码，不包含 eGPS2 的 `egps2.Launcher` 主入口类；运行需要在 eGPS2 主工程环境下进行。

## 运行参数（JDK 模块访问）

`eGPS.args` 文件包含若干 `--add-exports/--add-opens` 参数，用于在较新 JDK 上兼容部分 UI 依赖（例如 JIDE）。

- 在启动 eGPS2 主程序时，通常以 `@eGPS.args` 的形式传入，例如：`/home/dell/software/java25/jdk-25+36/bin/java ... @eGPS.args ...`
- 本仓库提供的 `eGPS.args` 仅作为参考，具体以你的 eGPS2 主工程/发布包要求为准

## 目录结构（简要）

- `src/`：模块源代码（大量以 `module.*` 包组织的面板与工具）
- `dependency-egps/`：运行/编译依赖 JAR（手动依赖管理）
- `out/`：编译输出（默认约定为 `out/production/egps-TQTools`）
- `remove_compile_warnings.md`：基于 `-Xlint:all` 的告警清理说明与策略

