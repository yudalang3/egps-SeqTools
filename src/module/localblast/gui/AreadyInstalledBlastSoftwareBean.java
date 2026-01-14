package module.localblast.gui;

public class AreadyInstalledBlastSoftwareBean {

	private String binDirPath = "";
	private String dbFilePath = "";
	private String proteinDBPath = "";

	// Database tools 配置
	private DbToolsConfig dbToolsConfig = new DbToolsConfig();

	// Blastn 配置
	private BlastnConfig blastnConfig = new BlastnConfig();

	// Blastp 配置
	private BlastpConfig blastpConfig = new BlastpConfig();

	public String getBinDirPath() {
		return binDirPath;
	}

	public void setBinDirPath(String binDirPath) {
		this.binDirPath = binDirPath;
	}

	public String getWindowsBlastN() {
		return binDirPath.concat("/blastn.exe");
	}

	public String getWindowsBlastP() {
		return binDirPath.concat("/blastp.exe");
	}
	public String getWindowsMakeBlastDB() {
		return binDirPath.concat("/makeblastdb.exe");
	}

	public String getBlastdbcmd() {
		return binDirPath.concat("/blastdbcmd.exe");
	}

	public String getDbFilePath() {
		return dbFilePath;
	}

	public void setDbFilePath(String dbFilePath) {
		this.dbFilePath = dbFilePath;
	}

	public String getProteinDBPath() {
		return proteinDBPath;
	}

	public void setProteinDBPath(String proteinDBPath) {
		this.proteinDBPath = proteinDBPath;
	}

	public DbToolsConfig getDbToolsConfig() {
		return dbToolsConfig;
	}

	public void setDbToolsConfig(DbToolsConfig dbToolsConfig) {
		this.dbToolsConfig = dbToolsConfig;
	}

	public BlastnConfig getBlastnConfig() {
		return blastnConfig;
	}

	public void setBlastnConfig(BlastnConfig blastnConfig) {
		this.blastnConfig = blastnConfig;
	}

	public BlastpConfig getBlastpConfig() {
		return blastpConfig;
	}

	public void setBlastpConfig(BlastpConfig blastpConfig) {
		this.blastpConfig = blastpConfig;
	}

	/**
	 * Database tools 配置
	 */
	public static class DbToolsConfig {
		private String inPath = "";
		private String inputType = "fasta";
		private String dbType = "nucl";
		private String title = "";
		private boolean parseSeqids = false;
		private boolean hashIndex = false;
		private String outPath = "";

		public String getInPath() { return inPath; }
		public void setInPath(String inPath) { this.inPath = inPath; }

		public String getInputType() { return inputType; }
		public void setInputType(String inputType) { this.inputType = inputType; }

		public String getDbType() { return dbType; }
		public void setDbType(String dbType) { this.dbType = dbType; }

		public String getTitle() { return title; }
		public void setTitle(String title) { this.title = title; }

		public boolean isParseSeqids() { return parseSeqids; }
		public void setParseSeqids(boolean parseSeqids) { this.parseSeqids = parseSeqids; }

		public boolean isHashIndex() { return hashIndex; }
		public void setHashIndex(boolean hashIndex) { this.hashIndex = hashIndex; }

		public String getOutPath() { return outPath; }
		public void setOutPath(String outPath) { this.outPath = outPath; }
	}

	/**
	 * Blastn 配置
	 */
	public static class BlastnConfig {
		private String query = "";
		private String queryLoc = "";
		private String strand = "both";
		private String task = "megablast";
		private String out = "";
		private String outfmt = "6";
		private int numThreads = 1;
		private String evalue = "10.0";
		private String wordSize = "";
		private String gapopen = "";
		private String gapextend = "";

		public String getQuery() { return query; }
		public void setQuery(String query) { this.query = query; }

		public String getQueryLoc() { return queryLoc; }
		public void setQueryLoc(String queryLoc) { this.queryLoc = queryLoc; }

		public String getStrand() { return strand; }
		public void setStrand(String strand) { this.strand = strand; }

		public String getTask() { return task; }
		public void setTask(String task) { this.task = task; }

		public String getOut() { return out; }
		public void setOut(String out) { this.out = out; }

		public String getOutfmt() { return outfmt; }
		public void setOutfmt(String outfmt) { this.outfmt = outfmt; }

		public int getNumThreads() { return numThreads; }
		public void setNumThreads(int numThreads) { this.numThreads = numThreads; }

		public String getEvalue() { return evalue; }
		public void setEvalue(String evalue) { this.evalue = evalue; }

		public String getWordSize() { return wordSize; }
		public void setWordSize(String wordSize) { this.wordSize = wordSize; }

		public String getGapopen() { return gapopen; }
		public void setGapopen(String gapopen) { this.gapopen = gapopen; }

		public String getGapextend() { return gapextend; }
		public void setGapextend(String gapextend) { this.gapextend = gapextend; }
	}

	/**
	 * Blastp 配置
	 */
	public static class BlastpConfig {
		private String query = "";
		private String queryLoc = "";
		private String task = "blastp";
		private String out = "";
		private String outfmt = "6";
		private int numThreads = 1;
		private String evalue = "10.0";
		private String wordSize = "";
		private String gapopen = "";
		private String gapextend = "";

		public String getQuery() { return query; }
		public void setQuery(String query) { this.query = query; }

		public String getQueryLoc() { return queryLoc; }
		public void setQueryLoc(String queryLoc) { this.queryLoc = queryLoc; }

		public String getTask() { return task; }
		public void setTask(String task) { this.task = task; }

		public String getOut() { return out; }
		public void setOut(String out) { this.out = out; }

		public String getOutfmt() { return outfmt; }
		public void setOutfmt(String outfmt) { this.outfmt = outfmt; }

		public int getNumThreads() { return numThreads; }
		public void setNumThreads(int numThreads) { this.numThreads = numThreads; }

		public String getEvalue() { return evalue; }
		public void setEvalue(String evalue) { this.evalue = evalue; }

		public String getWordSize() { return wordSize; }
		public void setWordSize(String wordSize) { this.wordSize = wordSize; }

		public String getGapopen() { return gapopen; }
		public void setGapopen(String gapopen) { this.gapopen = gapopen; }

		public String getGapextend() { return gapextend; }
		public void setGapextend(String gapextend) { this.gapextend = gapextend; }
	}
}
