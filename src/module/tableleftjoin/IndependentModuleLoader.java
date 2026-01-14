package module.tableleftjoin;

import java.util.List;

import egps2.builtin.modules.voice.bean.AbstractParamsAssignerAndParser4VOICE;
import egps2.builtin.modules.voice.fastmodvoice.OrganizedParameterGetter;
import egps2.builtin.modules.voice.fastmodvoice.TabModuleFaceOfVoice;
import egps2.modulei.ModuleClassification;
import egps2.modulei.ModuleVersion;

@SuppressWarnings("serial")
public class IndependentModuleLoader extends TabModuleFaceOfVoice {

	@Override
	public String getTabName() {
		return "Two table LeftJoin";
	}

	@Override
	public String getShortDescription() {
		return "Quick query column values according to the reference table, large file is supported.";
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
		mapProducer.addKeyValueEntryBean("query.table.path", "",
				"The file path has the query table, file format is tsv. Necessary");

		mapProducer.addKeyValueEntryBean("reference.table.path", "",
				"The file path has the reference table, file format is tsv. Necessary");

		mapProducer.addKeyValueEntryBean("output.table.path", "",
				"The file path for output, file format is tsv. Necessary");

		mapProducer.addKeyValueEntryBean("query.column.index", "1",
				"This is the column index need to query (First is 1)");

		mapProducer.addKeyValueEntryBean("ref.column.index", "1",
				"This is the column index need for reference, (First is 1)");

		mapProducer.addKeyValueEntryBean("query.file.hasHeader", "F",
				"If the query file has header line.");
		mapProducer.addKeyValueEntryBean("ref.file.hasHeader", "F",
				"If the reference file has header line.");

	}

	@Override
	protected void execute(OrganizedParameterGetter o) throws Exception {
		String queryFilePath = o.getSimplifiedString("query.table.path");
		String refPath = o.getSimplifiedString("reference.table.path");
		String outputPath = o.getSimplifiedString("output.table.path");
		int queryColumnIndex = o.getSimplifiedInt("query.column.index") - 1;
		int refColumnIndex = o.getSimplifiedInt("ref.column.index") - 1;
		boolean queryHasHeader = o.getSimplifiedBool("query.file.hasHeader");
		boolean refHasHeader = o.getSimplifiedBool("ref.file.hasHeader");

		LeftJoinTable runner = new LeftJoinTable();
		runner.setInputPath(queryFilePath);
		runner.setRefernecedTablePath(refPath);
		runner.setOutputPath(outputPath);
		runner.setQueryColumnIndex(queryColumnIndex);
		runner.setReferenceColumnIndex(refColumnIndex);
		runner.setQueryFileHasHeader(queryHasHeader);
		runner.setRefFileHasHeader(refHasHeader);
		
		runner.doIt();

		List<String> strings4output = runner.strings4output;

		this.setText4Console(strings4output);
	}
}
