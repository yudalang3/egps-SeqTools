# `ProcessBuilder(List<String>)`：命令拆分（含空格/引号）风险说明与拟解决方案（单独处理）

> 本文件用于单独讨论 “2.5 进程执行参数拆分风险”。不直接改动任何业务逻辑，仅给出问题与可落地的解决思路，待你确认后再在代码里逐步实施。

## 1. 问题背景

从 `Runtime.exec(String)` 迁移到 `ProcessBuilder(List<String>)` 可以消除 `exec(String)` 的弃用告警，并让参数传递更清晰。

但现实中很多地方保存的是“整条命令字符串”（例如 GUI 拼接出来的 `runProgrameCommand`），迁移时常见做法是：

- `String` 按空白切分为 `List<String>`（例如用 `StringTokenizer` 或 `split("\\s+")`）

这个“按空白切分”的做法在**含空格路径、引号、转义**时会出错，造成“隐藏的运行时错误”。

## 2. 典型出错场景

### 2.1 路径包含空格

例如（Windows 很常见）：

```
C:\Program Files\NCBI\blast-2.15.0+\bin\blastn.exe -h
```

按空白切分会变成：

- `C:\Program`
- `Files\NCBI\blast-2.15.0+\bin\blastn.exe`
- `-h`

结果：进程启动失败（找不到可执行文件）。

### 2.2 参数中包含空格且用引号包裹

```
blastn -query "C:\My Data\query.fa" -db "C:\DB Files\nt"
```

按空白切分会把 `"C:\My` 和 `Data\query.fa"` 分开，导致参数错乱。

### 2.3 不同平台的 quoting 规则不同

- Windows 的 `cmd.exe` quoting 与 Unix shell 不同
- 直接把命令交给 shell（`/bin/sh -c ...` 或 `cmd.exe /c ...`）会改变引号/转义规则，存在行为差异与安全风险

## 3. 为什么这是“隐藏错误”

- 在开发机上如果路径没有空格，会“看起来能跑”
- 用户环境一旦出现空格路径/复杂参数，就会突然报错
- 这类问题很像“偶发 bug”，但根因是命令拆分策略不可靠

## 4. 推荐的总体策略（从根上解决）

### 4.1 最推荐：从源头就维护 `List<String>`

不要把命令拼成一个大字符串再解析；而是：

- 把可执行文件路径作为一个 token
- 每个参数作为一个 token
- 最终把 `List<String>` 交给 `ProcessBuilder`

优点：
- 不需要“解析字符串命令”，避免 quoting 复杂性
- 行为稳定、跨平台更一致
- 可在 GUI 层对每个参数做校验（路径存在性、是否可执行等）

实现方式（思路）：
- 把 `runProgrameCommand` 这类 `String` 改为 `List<String> commandTokens`
- GUI 的每个输入框对应一个 token（或一组 token）

### 4.2 过渡方案：提供“引号感知”的简易 tokenizer（有限但可控）

如果短期内必须继续保存 `String` 命令，那么可以实现一个 tokenizer：

- 支持双引号包裹的 token（保留空格）
- 支持反斜杠转义（可选）
- 明确限制：不支持复杂 shell 语法（管道 `|`、重定向 `>`、`&&` 等）

并在手册/错误提示中明确：
- 不要依赖 shell 语法
- 路径含空格必须使用引号包裹

### 4.3 不建议：无脑改为 shell 执行

例如：

- `new ProcessBuilder("/bin/sh", "-c", cmd)`
- `new ProcessBuilder("cmd.exe", "/c", cmd)`

问题：
- 行为变化大（引号/转义解析由 shell 接管）
- 可能引入命令注入风险（如果 `cmd` 拼接了用户输入）
- 跨平台差异更大

除非你明确需要 shell 特性（管道/重定向等），否则不建议作为默认方案。

## 5. 拟落地的“实施路线”（建议）

1) **先确定哪些模块的命令是“可拆分”的**（无引号、无空格路径）以及哪些不是
2) 对明显存在空格路径风险的模块，优先改为“源头维护 `List<String>`”
3) 对短期无法重构的模块，使用“引号感知 tokenizer”作为过渡
4) 在 GUI 里增加提示：如果路径含空格请使用引号；并在错误日志里输出 token 列表以便排查

## 6. 需要你确认的选择

为了避免引入行为变化，我建议你在下面两种方案中选一个作为默认路线：

- 方案 A（更稳）：逐步把核心模块改为“源头维护 `List<String>`”，不做字符串解析
- 方案 B（更快）：实现 tokenizer，继续保留 `String` 命令输入（但要接受其限制）

