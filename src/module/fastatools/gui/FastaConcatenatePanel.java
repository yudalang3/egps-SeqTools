/*
 *
 */
package module.fastatools.gui;

import java.util.Arrays;
import java.util.List;

import com.google.common.base.Strings;

import utils.string.EGPSStringUtil;
import egps2.builtin.modules.voice.fastmodvoice.DockableTabModuleFaceOfVoice;
import egps2.builtin.modules.voice.bean.AbstractParamsAssignerAndParser4VOICE;
import egps2.builtin.modules.voice.fastmodvoice.OrganizedParameterGetter;
import egps2.frame.ComputationalModuleFace;

@SuppressWarnings("serial")
public class FastaConcatenatePanel extends DockableTabModuleFaceOfVoice {

	public FastaConcatenatePanel(ComputationalModuleFace cmf) {
		super(cmf);
	}

	@Override
	public String getShortDescription() {
		return "Concatenate fasta files from a directory into one output fasta.";
	}

	@Override
	public String getTabName() {
		return "1. Fasta Concatenate";
	}

	@Override
	protected void setParameter(AbstractParamsAssignerAndParser4VOICE mapProducer) {
		mapProducer.addKeyValueEntryBean("fasta.dir.path", "",
				"Input fasta directory path, the fasta files in this directory will be processed. Necessary");

		mapProducer.addKeyValueEntryBean("output.file.path", "", "Output fasta file path. Necessary");
		mapProducer.addKeyValueEntryBean("exclude.file.suffix", "",
				"File to exclude, multiple entries are supported, for example a;b;c. Default is none.");

	}

	@Override
	protected void execute(OrganizedParameterGetter o) throws Exception {
		String inputDir = o.getSimplifiedString("fasta.dir.path");
		String outputFastaPath = o.getSimplifiedString("output.file.path");
		String excludeString = o.getSimplifiedStringWithDefault("exclude.file.suffix");

		List<String> excludeStrings;
		if (Strings.isNullOrEmpty(excludeString)) {
			excludeStrings = List.of();
		} else {
			String[] split = EGPSStringUtil.split(excludeString, ';');
			excludeStrings = Arrays.asList(split);
		}

		run(inputDir, outputFastaPath, excludeStrings);
	}

	private void run(String inputDir, String outputFastaPath, List<String> excludeStrings) throws Exception {

		FastaConcatenator fastaConcatenator = new FastaConcatenator();
		List<String> inputFiles = Arrays.asList(inputDir);
		fastaConcatenator.setInputFiles(inputFiles);
		fastaConcatenator.setExcludeStrings(excludeStrings);

		fastaConcatenator.setOutputPath(outputFastaPath);
		fastaConcatenator.setModuleFace(computationalModuleFace);

		long timeMillis = System.currentTimeMillis();

		fastaConcatenator.doIt();

		List<String> outputs = fastaConcatenator.getOutputs();
		long thisTimeMillis = System.currentTimeMillis();
		outputs.add("Take time of  " + (thisTimeMillis - timeMillis) + " milliseconds to execute.");

		setText4Console(outputs);

	}


}
