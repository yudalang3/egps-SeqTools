package module.filegreper;

import java.util.List;

import utils.string.EGPSStringUtil;
import egps2.builtin.modules.voice.bean.AbstractParamsAssignerAndParser4VOICE;
import egps2.builtin.modules.voice.fastmodvoice.OrganizedParameterGetter;
import egps2.builtin.modules.voice.fastmodvoice.TabModuleFaceOfVoice;
import egps2.modulei.ModuleClassification;
import egps2.modulei.ModuleVersion;

@SuppressWarnings("serial")
public class IndependentModuleLoader extends TabModuleFaceOfVoice {

	@Override
	public String getTabName() {
		return "File greper";
	}

	@Override
	public String getShortDescription() {
		return "A convenient graphic tool to find target lines as the Linux command grep.";
	}

	@Override
	public ModuleVersion getVersion() {
		return new ModuleVersion(0, 0, 1);
	}

	@Override
	public int[] getCategory() {
		int[] ret = ModuleClassification.getOneModuleClassification(
				ModuleClassification.BYFUNCTIONALITY_SIMPLE_TOOLS_INDEX,
				ModuleClassification.BYAPPLICATION_COMMON_MODULE_INDEX,
				ModuleClassification.BYCOMPLEXITY_LEVEL_1_INDEX,
				ModuleClassification.BYDEPENDENCY_ONLY_EMPLOY_CONTAINER
		);
		return ret;
	}

	@Override
	protected void setParameter(AbstractParamsAssignerAndParser4VOICE mapProducer) {
		mapProducer.addKeyValueEntryBean("input.file.path", "",
				"The target file path for search, a text file, Necessary.");
		mapProducer.addKeyValueEntryBean("query.string", "my",
				"The search key string, multiple string is supported (with | char), i.e. ENSP00000488240.1|ENSP00000487941.1. Necessary");
		mapProducer.addKeyValueEntryBean("following.line.number", "0",
				"The following lines after the searched line to output. Necessary");
		mapProducer.addKeyValueEntryBean("export.header", "F", "Whether export header line.");
		mapProducer.addKeyValueEntryBean("results.limitation.count", Integer.toString(Integer.MAX_VALUE),
				"The maximun number of results to output. For e.g.: 1 only output one searched line content.");

	}

	@Override
	protected void execute(OrganizedParameterGetter o) throws Exception {

		String inputFilePath = o.getSimplifiedString("input.file.path");
		String queryString = o.getSimplifiedStringWithDefault("query.string");
		boolean exportHeaderLine = o.getSimplifiedBool("export.header");
		int neiberLineNumber = o.getSimplifiedInt("following.line.number");
		int searchedResultsLimitation = o.getSimplifiedInt("results.limitation.count");
		if (searchedResultsLimitation < 1) {
			throw new IllegalArgumentException("Parameter $results.limitation.count should greater 0.");
		}

		DoGrepAction doGrepAction = new DoGrepAction();
		doGrepAction.inputFilePath = inputFilePath;
		doGrepAction.searchKeys = EGPSStringUtil.split(queryString, '|');
		doGrepAction.exportHeader = exportHeaderLine;
		doGrepAction.followingLineNumber = neiberLineNumber;
		doGrepAction.searchedResultsLimitation = searchedResultsLimitation;

		long timeMillis = System.currentTimeMillis();

		doGrepAction.doIt();
		List<String> strings4output = doGrepAction.getOutputList();

		long thisTimeMillis = System.currentTimeMillis();
		strings4output.add("Take time of  " + (thisTimeMillis - timeMillis) + " milliseconds to grep.");

		this.setText4Console(strings4output);

	}
}
