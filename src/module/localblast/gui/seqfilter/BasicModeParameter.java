package module.localblast.gui.seqfilter;

import java.util.ArrayList;
import java.util.List;

public class BasicModeParameter {
	
	String dbParameterString;
	String dbtypeParameterString;
	String entryParameterString;
	String rangeParameterString;
	String strandParameterString;
	String entry_batchParameterString;
	String outParameterString;
	String outfmtParameterString;
	
	
	public void setDbParameterString(String dbParameterString) {
		this.dbParameterString = dbParameterString;
	}

	public void setDbtypeParameterString(String dbtypeParameterString) {
		this.dbtypeParameterString = dbtypeParameterString;
	}

	public void setEntryParameterString(String entryParameterString) {
		this.entryParameterString = entryParameterString;
	}

	public void setRangeParameterString(String rangeParameterString) {
		this.rangeParameterString = rangeParameterString;
	}

	public void setStrandParameterString(String strandParameterString) {
		this.strandParameterString = strandParameterString;
	}

	public void setEntry_batchParameterString(String entry_batchParameterString) {
		this.entry_batchParameterString = entry_batchParameterString;
	}

	public void setOutParameterString(String outParameterString) {
		this.outParameterString = outParameterString;
	}

	public void setOutfmtParameterString(String outfmtParameterString) {
		this.outfmtParameterString = outfmtParameterString;
	}

	public List<String> getCommandTokens(String exePath) {
		List<String> tokens = new ArrayList<>();
		tokens.add(exePath);
		tokens.add("-db");
		tokens.add(dbParameterString);
		tokens.add("-dbtype");
		tokens.add(dbtypeParameterString);
		if (entryParameterString.length() > 0) {
			tokens.add("-entry");
			tokens.add(entryParameterString);
		}
		if (rangeParameterString.length() > 0) {
			tokens.add("-range");
			tokens.add(rangeParameterString);
		}
		if (strandParameterString.length() > 0) {
			tokens.add("-strand");
			tokens.add(strandParameterString);
		}
		if (entry_batchParameterString.length() > 0) {
			tokens.add("-entry_batch");
			tokens.add(entry_batchParameterString);
		}
		tokens.add("-out");
		tokens.add(outParameterString);
		tokens.add("-outfmt");
		tokens.add(outfmtParameterString);

		return tokens;
	}
	
	public String getOutParameterString() {
		return outParameterString;
	}
}
