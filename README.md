# egps-TQTools (SeqTools)

This repository is an extension module collection for **eGPS2** (a Swing-based desktop app), focused on biological sequence tools and workflows (SeqTools).

- 中文说明请看：`README_zh.md`

---

## Overview

This repo contains module source code under `src/` and JAR dependencies in `dependency-egps/`. It does not use Maven/Gradle; builds are typically done via IntelliJ IDEA (JDK 25) or manual `javac`.

## Requirements

- JDK: **25**
- JDK 25 on this machine:
  - `/home/dell/software/java25/jdk-25+36/bin/javac`
  - `/home/dell/software/java25/jdk-25+36/bin/java`

## Build (manual)

```bash
/home/dell/software/java25/jdk-25+36/bin/javac \
  -d ./out/production/egps-TQTools \
  -cp "dependency-egps/*" \
  $(find src -name "*.java")
```

To reproduce/check compile warnings (recommended when cleaning warnings):

```bash
/home/dell/software/java25/jdk-25+36/bin/javac -Xlint:all -Xmaxwarns 10000 \
  -d ./out/production/egps-TQTools \
  -cp "dependency-egps/*" \
  $(find src -name "*.java")
```

See `remove_compile_warnings.md` for the warning-cleanup notes.

## Package as JAR (optional)

```bash
jar -cf egps-TQTools.jar -C ./out/production/egps-TQTools .
```

## Deploy into eGPS2 (concept)

Typically, you copy the compiled output (or `egps-TQTools.jar`) into eGPS2's `dependency-egps/`, then let eGPS2 load it via its module/plugin system.

Note: this repo contains module code only; it does not include the eGPS2 main entry (`egps2.Launcher`). You need the eGPS2 main project to run the application.

## Runtime args (JDK module access)

`eGPS.args` includes a set of `--add-exports/--add-opens` flags to keep some UI dependencies (e.g., JIDE) working on newer JDKs.

- When launching the eGPS2 main application, you typically pass it as `@eGPS.args`, e.g. `/home/dell/software/java25/jdk-25+36/bin/java ... @eGPS.args ...`
- The `eGPS.args` in this repo is a reference; follow your eGPS2 main project/distribution requirements if they differ
