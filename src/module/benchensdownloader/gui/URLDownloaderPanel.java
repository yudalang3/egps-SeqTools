/*
 * 
 */
package module.benchensdownloader.gui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import egps2.builtin.modules.voice.fastmodvoice.DockableTabModuleFaceOfVoice;
import egps2.builtin.modules.voice.fastmodvoice.OrganizedParameterGetter;
import org.apache.commons.lang3.BooleanUtils;

import com.google.common.base.Strings;

import tsv.io.KitTable;
import tsv.io.TSVReader;
import egps2.frame.ComputationalModuleFace;
import module.benchensdownloader.URLDirectDownloader;
import egps2.builtin.modules.voice.bean.AbstractParamsAssignerAndParser4VOICE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class URLDownloaderPanel extends DockableTabModuleFaceOfVoice {
	private static final Logger log = LoggerFactory.getLogger(URLDownloaderPanel.class);

	public URLDownloaderPanel(ComputationalModuleFace cmf) {
		super(cmf);
	}

	private List<String> outputStringKept = new ArrayList<>();

	@Override
	protected void setParameter(AbstractParamsAssignerAndParser4VOICE mapProducer) {
		mapProducer.addKeyValueEntryBean("input.download.txt", "",
				"The line start with # will be ignored.\n# The contents to download, input the file path, tsv file format, Necessary.");
		mapProducer.addKeyValueEntryBean("target.column", "1",
				"The column used to download target file, first column is 1, default is 1.");
		mapProducer.addKeyValueEntryBean("output.dir.file", "", "The output dir to store the results.");
		mapProducer.addKeyValueEntryBean("has.header", "F",
				"Whether the tsv file has hader line, i.e. skip the first lien");

	}

	@Override
	protected void execute(OrganizedParameterGetter organizedParameterGetter) throws Exception {

		String inputURLPath = organizedParameterGetter.getSimplifiedString("input.download.txt");
		String outputFilePath = organizedParameterGetter.getSimplifiedString("output.dir.file");
		int targetColumn = organizedParameterGetter.getSimplifiedInt("target.column");
		boolean hasHeader = organizedParameterGetter.getSimplifiedBool("has.header");

		run(inputURLPath, targetColumn - 1, new File(outputFilePath), hasHeader);
	}

	private void run(String inputURLPath, int targetColumn, File outputDir, boolean hasHeader) throws IOException {
		if (!outputDir.exists()) {
			outputDir.mkdirs();
		}

		outputStringKept.clear();

		KitTable tsvTextFile = TSVReader.readTsvTextFile(inputURLPath, hasHeader);

		List<List<String>> contents = tsvTextFile.getContents();

		Iterator<List<String>> iterator = contents.iterator();
		while (iterator.hasNext()) {
			List<String> next = iterator.next();
			String string = next.get(0);
			if (string.length() > 0 && string.charAt(0) == '#') {
				iterator.remove();
			}

		}

		int size = contents.size();

		int curr = 0;

		URLDirectDownloader urlDirectDownloader = new URLDirectDownloader();
		urlDirectDownloader.setOutputDir(outputDir);

		for (List<String> list : contents) {
			curr++;

			String downloadURL = list.get(targetColumn);

			List<String> outputs = new LinkedList<>();
			outputs.addAll(outputStringKept);

			long timeMillis = System.currentTimeMillis();

			urlDirectDownloader.downloadContent(computationalModuleFace, downloadURL);

			outputs.add("Current is: ".concat(downloadURL));
			outputs.add("Current is: " + curr + "  /  " + size);

				long ms = (long) (Math.random() * 1000L);
				try {
					Thread.sleep(ms);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					log.warn("Thread interrupted during sleep.", e);
				}

			long thisTimeMillis = System.currentTimeMillis();

			outputs.add("Take time of  " + (thisTimeMillis - timeMillis) + " milliseconds to download the file.");
			setText4Console(outputs);
		}

	}

	@Override
	public String getShortDescription() {
		return "";
	}

	@Override
	public String getTabName() {
		return "";
	}
}
