/*
 *
 */
package module.geoprocessor.gui;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.google.common.base.Stopwatch;

import utils.EGPSFileUtil;
import egps2.builtin.modules.voice.fastmodvoice.DockableTabModuleFaceOfVoice;
import egps2.builtin.modules.voice.bean.AbstractParamsAssignerAndParser4VOICE;
import egps2.builtin.modules.voice.fastmodvoice.OrganizedParameterGetter;
import egps2.frame.ComputationalModuleFace;

@SuppressWarnings("serial")
public class ContentsExtractorWithTagPanel extends DockableTabModuleFaceOfVoice {

	public ContentsExtractorWithTagPanel(ComputationalModuleFace cmf) {
		super(cmf);
	}

	@Override
	public String getShortDescription() {
		return "Extract table section contents with start/end tags from GEO SOFT or Series Matrix file.";
	}

	@Override
	public String getTabName() {
		return "1. Contents extractor with start/end tag";
	}

	@Override
	protected void setParameter(AbstractParamsAssignerAndParser4VOICE mapProducer) {
		mapProducer.addKeyValueEntryBean("input.txt.file", "",
				"Input data file, plain text file or compressed files are allowed. Necessary");
		mapProducer.addKeyValueEntryBean("start.tag", "",
				"For SOFT formatted family file(s), the start tag is 'platform_table_begin'; for Series Matrix File(s), the start tag is 'series_matrix_table_begin'. Necessary");
		mapProducer.addKeyValueEntryBean("end.tag", "",
				"For SOFT formatted family file(s), the start tag is 'platform_table_end'; for Series Matrix File(s), the start tag is 'series_matrix_table_end'. Necessary");

		mapProducer.addKeyValueEntryBean("output.file", "", "Output file. Necessary");
	}

	@Override
	protected void execute(OrganizedParameterGetter o) throws Exception {
		String inputFile = o.getSimplifiedString("input.txt.file");
		String outputFilePath = o.getSimplifiedString("output.file");
		String startTag = o.getSimplifiedString("start.tag");
		String endTag = o.getSimplifiedString("end.tag");
		Stopwatch stopwatch = Stopwatch.createStarted();

		List<String> outputs = new LinkedList<>();

		List<String> contents = EGPSFileUtil.getContentsFromOneFileMaybeCompressed(inputFile);

		List<String> targetContents = new LinkedList<>();

		boolean startMeet = false;
		for (String string : contents) {
			if (startMeet) {
				if (string.contains(endTag)) {
					break;
				}
				targetContents.add(string);
			}
			if (string.contains(startTag)) {
				startMeet = true;
			}
		}

		FileUtils.writeLines(new File(outputFilePath), targetContents);


		stopwatch.stop();
		long millis = stopwatch.elapsed().toMillis();
		outputs.add("Take time of  " + (millis) + " milliseconds to execute.");
		setText4Console(outputs);
	}


}
