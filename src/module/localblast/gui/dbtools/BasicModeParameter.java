package module.localblast.gui.dbtools;

import java.util.ArrayList;
import java.util.List;

public class BasicModeParameter {

	final String[] parameterNames = {
			"in",
			"dbtype",
			"input_type",
			"title",
			"parse_seqids",
			"hash_index",
			"out",
	};
	
	String inParameterString;
	String input_typeParameterString;
	String titleParameterString;
	String outParameterString;
	String dbtypeParameterString;
	boolean ifParse_seqids;
	boolean ifHash_index;
	
	
	
	public void setInParameterString(String inParameterString) {
		this.inParameterString = inParameterString;
	}



	public void setInput_typeParameterString(String input_typeParameterString) {
		this.input_typeParameterString = input_typeParameterString;
	}



	public void setTitleParameterString(String titleParameterString) {
		this.titleParameterString = titleParameterString;
	}



	public void setOutParameterString(String outParameterString) {
		this.outParameterString = outParameterString;
	}
	
	public void setDbtypeParameterString(String dbtypeParameterString) {
		this.dbtypeParameterString = dbtypeParameterString;
	}



	public void setIfParse_seqids(boolean ifParse_seqids) {
		this.ifParse_seqids = ifParse_seqids;
	}


	public void setIfHash_index(boolean ifHash_index) {
		this.ifHash_index = ifHash_index;
	}



	public String[] getParameterNames() {
		return parameterNames;
	}
	
	public List<String> getCommandTokens(String exePath) {
		List<String> tokens = new ArrayList<>();
		tokens.add(exePath);
		tokens.add("-in");
		tokens.add(inParameterString);
		tokens.add("-dbtype");
		tokens.add(dbtypeParameterString);
		tokens.add("-input_type");
		tokens.add(input_typeParameterString);

		if (titleParameterString.length() > 0) {
			tokens.add("-title");
			tokens.add(titleParameterString);
		}

		if (ifParse_seqids) {
			tokens.add("-parse_seqids");
		}

		if (ifHash_index) {
			tokens.add("-hash_index");
		}

		tokens.add("-out");
		tokens.add(outParameterString);

		return tokens;
	}
	
	public String getOutParameterString() {
		return outParameterString;
	}
}
