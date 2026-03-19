# egps-TQTools（SeqTools）

本仓库是 eGPS2 的扩展模块集合，主要面向生物序列分析相关工具与工作流面板（SeqTools）。

- English README: `README.md`

---

## 项目定位

- 本项目是 eGPS2 桌面软件（Swing）的扩展模块集合。源码位于 `src/`，依赖 JAR 位于 `dependency-egps/`。
- 当前仓库共有 **28 个对外功能模块入口**，按实现 `IModuleLoader` 的模块统计，不包含内部子面板。
- 下表列出全部模块入口、对应源码位置和简介。

### 功能模块总览

| # | 模块名称 | 入口类 | 简介 |
|---:|---|---|---|
| 1 | `Analyse homogeneous with species tree` | `src/module/analysehomogene/IndependentModuleLoader.java` | 基于物种树信息并结合简约原则推断同源基因。 |
| 2 | `Back mutation presenter` | `src/module/backmutpres/IndependentModuleLoader.java` | 回突变结果的文本可视化。 |
| 3 | `Batch Peptides Operator` | `src/module/batchpepo/IndependentModuleLoader.java` | 多条肽序列的批量处理流水线工具。 |
| 4 | `Bed merger` | `src/module/bedmerger/IndependentModuleLoader.java` | 按记录坐标合并 BED 文件。 |
| 5 | `Batch Ensembl annotation downloader` | `src/module/benchensdownloader/IndependentModuleLoader.java` | 批量下载 Ensembl 注释文件，例如 CDS、protein 和 GFF3。 |
| 6 | `Direct sequence mapping` | `src/module/brutemapping/IndependentModuleLoader.java` | 使用 KMP 算法将短序列映射到基因组。 |
| 7 | `Correlation visualization for WNT` | `src/module/correlation4wnt/ModuleLoader.java` | Wnt 通路相关性可视化。 |
| 8 | `Datetime calculator` | `src/module/datetimecalculator/IndependentModuleLoader.java` | 基于字符串条目快速生成日期时间。 |
| 9 | `Fasta dumper` | `src/module/fastadumper/IndependentModuleLoader.java` | 快速导出 FASTA 内容，支持去除 gap、重命名和子串截取。 |
| 10 | `Fasta Tools` | `src/module/fastatools/IndependentModuleLoader.java` | 一组 FASTA 文件处理工具。 |
| 11 | `GEO Supplementary File Processor` | `src/module/geoprocessor/IndependentModuleLoader.java` | 便捷处理 GEO 数据库的补充文件。 |
| 12 | `GFF3 operator` | `src/module/gff3opr/IndependentModuleLoader.java` | 快速提取 GFF 格式文件中的信息。 |
| 13 | `Quick histogram` | `src/module/histogram/IndependentModuleLoader.java` | 根据数据序列快速绘制直方图。 |
| 14 | `Homologous gene identification` | `src/module/homoidentify/IndependentModuleLoader.java` | 结合 BLAST 和 HMMER 识别同源基因。 |
| 15 | `Line break eliminator` | `src/module/linebEliminator/IndependentModuleLoader.java` | 快速去除段落中的冗余换行。 |
| 16 | `Local blast wrapper` | `src/module/localblast/gui/IndependentModuleLoader.java` | 本地 BLAST 运行与结果处理封装界面。 |
| 17 | `MA plot` | `src/module/maplot/IndependentModuleLoader.java` | 便捷绘制 MA 图。 |
| 18 | `Multi-seqs struct view` | `src/module/multiseqview/IndependentModuleLoader.java` | 面向多序列的结构可视化工具，可展示结构域和 motif。 |
| 19 | `Extract text by regex` | `src/module/regexExtract/IndependentModuleLoader.java` | 使用正则表达式提取目标文本。 |
| 20 | `Sequence logo` | `src/module/sequencelogo/IndependentModuleLoader.java` | 便捷绘制序列 logo。 |
| 21 | `Skeleton scatter plot` | `src/module/skeletonscatter/IndependentModuleLoader.java` | 基础骨架散点图，用于展示可视化与计算能力。 |
| 22 | `String set operator` | `src/module/stringsetoperator/IndependentModuleLoader.java` | 便捷进行字符串集合运算。 |
| 23 | `Table-like text curation` | `src/module/tablecuration/IndependentModuleLoader.java` | 对类表格文本文件进行多种整理操作。 |
| 24 | `Table-like text view` | `src/module/tablelikeview/IndependentModuleLoader.java` | 导入 TSV/CSV 后快速查看类表格数据。 |
| 25 | `Theoretical target genes with motif` | `src/module/targetoftf/IndependentModuleLoader.java` | 根据基因组上的结合 motif 理论推断靶基因。 |
| 26 | `Two strings comparator` | `src/module/twostringcomp/IndependentModuleLoader.java` | 比较两条字符串，适用于检查成对比对结果。 |
| 27 | `Bio. sequence operator` | `src/operator/sequences/IndependentModuleLoader.java` | 按行处理单序列的工具集合。 |
| 28 | `Primary Struct Drawer` | `src/primary/struct/display/IndependentModuleLoader.java` | 按结构域绘制蛋白结构。 |

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
