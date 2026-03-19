# egps-TQTools (SeqTools)

This repository is an extension module collection for **eGPS2**, focused on biological sequence analysis tools and workflow panels (SeqTools).

- 中文说明请看：`README_zh.md`

---

## Project Positioning

- This project is a collection of eGPS2 desktop modules (Swing). The source code lives under `src/`, and the dependency JARs live under `dependency-egps/`.
- The repository currently exposes **28 public module entry points**, counted by modules that implement `IModuleLoader` and excluding internal subpanels.
- The table below lists every module entry point, its source location, and a short description.

### Module Overview

| # | Module Name | Entry Class | Description |
|---:|---|---|---|
| 1 | `Analyse homogeneous with species tree` | `src/module/analysehomogene/IndependentModuleLoader.java` | Infers homologous genes from species-tree information using the parsimony principle. |
| 2 | `Back mutation presenter` | `src/module/backmutpres/IndependentModuleLoader.java` | Text visualization of back mutations. |
| 3 | `Batch Peptides Operator` | `src/module/batchpepo/IndependentModuleLoader.java` | A pipeline for batch-processing multiple peptide sequences. |
| 4 | `Bed merger` | `src/module/bedmerger/IndependentModuleLoader.java` | Merges BED files according to record coordinates. |
| 5 | `Batch Ensembl annotation downloader` | `src/module/benchensdownloader/IndependentModuleLoader.java` | Bulk-downloads Ensembl annotation files such as CDS, protein, and GFF3. |
| 6 | `Direct sequence mapping` | `src/module/brutemapping/IndependentModuleLoader.java` | Maps short sequences to the genome using the Knuth-Morris-Pratt algorithm. |
| 7 | `Correlation visualization for WNT` | `src/module/correlation4wnt/ModuleLoader.java` | Correlation visualization for the Wnt pathway. |
| 8 | `Datetime calculator` | `src/module/datetimecalculator/IndependentModuleLoader.java` | Quickly generates date/time values from string entries. |
| 9 | `Fasta dumper` | `src/module/fastadumper/IndependentModuleLoader.java` | Quickly exports FASTA content, with support for gap removal, renaming, and substring extraction. |
| 10 | `Fasta Tools` | `src/module/fastatools/IndependentModuleLoader.java` | A collection of FASTA file processing tools. |
| 11 | `GEO Supplementary File Processor` | `src/module/geoprocessor/IndependentModuleLoader.java` | Convenient processing of GEO supplementary files. |
| 12 | `GFF3 operator` | `src/module/gff3opr/IndependentModuleLoader.java` | Quickly extracts information from GFF-formatted files. |
| 13 | `Quick histogram` | `src/module/histogram/IndependentModuleLoader.java` | Quickly draws histograms from data series. |
| 14 | `Homologous gene identification` | `src/module/homoidentify/IndependentModuleLoader.java` | Identifies homologous genes with BLAST and HMMER. |
| 15 | `Line break eliminator` | `src/module/linebEliminator/IndependentModuleLoader.java` | Quickly removes redundant line breaks from a paragraph. |
| 16 | `Local blast wrapper` | `src/module/localblast/gui/IndependentModuleLoader.java` | A wrapper for running local BLAST and processing the results. |
| 17 | `MA plot` | `src/module/maplot/IndependentModuleLoader.java` | A convenient tool for drawing MA plots. |
| 18 | `Multi-seqs struct view` | `src/module/multiseqview/IndependentModuleLoader.java` | A structure-visualization tool for multiple sequences, including domains and motifs. |
| 19 | `Extract text by regex` | `src/module/regexExtract/IndependentModuleLoader.java` | Extracts target text using regular expressions. |
| 20 | `Sequence logo` | `src/module/sequencelogo/IndependentModuleLoader.java` | A convenient tool for drawing sequence logos. |
| 21 | `Skeleton scatter plot` | `src/module/skeletonscatter/IndependentModuleLoader.java` | A basic skeleton scatter plot for demonstrating visualization and computation capabilities. |
| 22 | `String set operator` | `src/module/stringsetoperator/IndependentModuleLoader.java` | Convenient operations on string sets. |
| 23 | `Table-like text curation` | `src/module/tablecuration/IndependentModuleLoader.java` | Multiple tools for curating table-like text files. |
| 24 | `Table-like text view` | `src/module/tablelikeview/IndependentModuleLoader.java` | Quickly view table-like data imported from TSV/CSV files. |
| 25 | `Theoretical target genes with motif` | `src/module/targetoftf/IndependentModuleLoader.java` | Infers theoretical target genes from binding motifs on the genome. |
| 26 | `Two strings comparator` | `src/module/twostringcomp/IndependentModuleLoader.java` | Compares two strings, which is useful for checking pairwise alignment results. |
| 27 | `Bio. sequence operator` | `src/operator/sequences/IndependentModuleLoader.java` | A collection of line-by-line operations for single sequences. |
| 28 | `Primary Struct Drawer` | `src/primary/struct/display/IndependentModuleLoader.java` | Draws protein structures with domain annotations. |

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
