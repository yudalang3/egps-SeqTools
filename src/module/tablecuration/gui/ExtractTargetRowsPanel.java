/*
 *
 */
package module.tablecuration.gui;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;

import com.google.common.base.Stopwatch;
import com.google.common.base.Strings;

import utils.string.EGPSStringUtil;
import tsv.io.TSVReader;
import tsv.io.TSVWriter;
import egps2.frame.ComputationalModuleFace;
import egps2.builtin.modules.voice.bean.AbstractParamsAssignerAndParser4VOICE;
import egps2.builtin.modules.voice.fastmodvoice.DockableTabModuleFaceOfVoice;
import egps2.builtin.modules.voice.fastmodvoice.OrganizedParameterGetter;

@SuppressWarnings("serial")
public class ExtractTargetRowsPanel extends DockableTabModuleFaceOfVoice {

	public ExtractTargetRowsPanel(ComputationalModuleFace cmf) {
		super(cmf);
	}

	@Override
	public String getShortDescription() {
		return "Extract target rows from a table by matching key column with an entry list (inline or from file).";
	}

	@Override
	public String getTabName() {
		return "Extract target records";
	}

	@Override
	protected void setParameter(AbstractParamsAssignerAndParser4VOICE mapProducer) {
		mapProducer.addKeyValueEntryBean("@", "Table-like curation (Extract target records)", "");

		mapProducer.addKeyValueEntryBean("input.table.file", "",
				"Input data file, usually the tsv file. Necessary");
		mapProducer.addKeyValueEntryBean("target.colum", "", "input one column: col1. Necessary");
		mapProducer.addKeyValueEntryBean("entries", "",
				"input target entries, user can directly input or paste entries. Or input one file path. Necessary\n# entries=file/to/path\n# entries = \n# a\n# b\n# c");

		mapProducer.addKeyValueEntryBean("output.file", "", "Output file. Necessary");
	}

	@Override
	protected void execute(OrganizedParameterGetter o) throws Exception {
		String inputTableFile = o.getSimplifiedString("input.table.file");
		String targetColumnName = o.getSimplifiedString("target.colum");
		String outputFile = o.getSimplifiedString("output.file");

		List<String> entries;
		var complicatedValues = o.getComplicatedValues("entries");
		if (complicatedValues.isEmpty() || complicatedValues.get().isEmpty()) {
			throw new IllegalArgumentException("Please input the parameter of $entries");
		}
		List<String> raw = complicatedValues.get();
		if (raw.size() > 1) {
			entries = raw.subList(1, raw.size());
		} else {
			String candidate = o.getSimplifiedString("entries");
			Path path = Path.of(candidate);
			if (Files.exists(path)) {
				entries = FileUtils.readLines(path.toFile(), StandardCharsets.UTF_8);
			} else {
				entries = Arrays.asList(EGPSStringUtil.split(candidate, ';'));
			}
		}

		Stopwatch stopwatch = Stopwatch.createStarted();

		List<String> outputs = new LinkedList<>();

		Map<String, List<String>> asKey2ListMap = TSVReader.readAsKey2ListMap(inputTableFile);
		int totalSize = getRowSize(asKey2ListMap);

		TableOrganizer tableOrganizer = new TableOrganizer();
		Map<String, List<String>> ret = tableOrganizer.extractTargetRecords(asKey2ListMap, targetColumnName,
				entries);

		int size = getRowSize(ret);
		outputs.add("Total records size in table is : " + totalSize);
		String format = EGPSStringUtil.format("Records need to extract is {}, extracted record size is {}",
				entries.size(), size);

		outputs.add(format);
		if (entries.size() > size) {
			List<String> extracted = ret.get(targetColumnName);
			HashSet<String> hashSet = new HashSet<>(entries);
			hashSet.removeAll(extracted);
			outputs.add("Following entries not found:");
			for (String string2 : hashSet) {
				outputs.add(string2);
			}
		}



		TSVWriter.write(ret, outputFile);

		stopwatch.stop();
		long millis = stopwatch.elapsed().toMillis();
		outputs.add("Take time of  " + (millis) + " milliseconds to execute.");
		setText4Console(outputs);

	}

	private int getRowSize(Map<String, List<String>> ret) {
		Entry<String, List<String>> firstEntry = ret.entrySet().iterator().next();
		int size = firstEntry.getValue().size();
		return size;
	}

}
