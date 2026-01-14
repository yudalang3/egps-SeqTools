package module.scountmerger;

import egps2.builtin.modules.voice.bean.AbstractParamsAssignerAndParser4VOICE;
import egps2.builtin.modules.voice.fastmodvoice.OrganizedParameterGetter;
import egps2.builtin.modules.voice.fastmodvoice.TabModuleFaceOfVoice;
import egps2.modulei.ModuleClassification;
import egps2.modulei.ModuleVersion;

@SuppressWarnings("serial")
public class IndependentModuleLoader extends TabModuleFaceOfVoice {

	@Override
	public String getTabName() {
		return "Sample counts merger";
	}

	@Override
	public String getShortDescription() {
		return "A convenient graphic tool to merge expression mapped counts of genes calculated from the featureCounts program.";
	}

	@Override
	public ModuleVersion getVersion() {
		return new ModuleVersion(0, 0, 1);
	}

	@Override
	public int[] getCategory() {
		int[] ret = ModuleClassification.getOneModuleClassification(
				ModuleClassification.BYFUNCTIONALITY_SIMPLE_TOOLS_INDEX,
				ModuleClassification.BYAPPLICATION_GENOMICS_INDEX,
				ModuleClassification.BYCOMPLEXITY_LEVEL_1_INDEX,
				ModuleClassification.BYDEPENDENCY_ONLY_EMPLOY_CONTAINER
		);
		return ret;
	}

	@Override
	protected void setParameter(AbstractParamsAssignerAndParser4VOICE mapProducer) {
		mapProducer.addKeyValueEntryBean("counts.file.suffix", ".counts.tsv",
				"The suffix of the counts file, this parameter is mandatory. Please take seriously.\n# DON NOT forget the . char");
		mapProducer.addKeyValueEntryBean("input.dir.path", "",
				"Input the directory path that contians the input files.");
		mapProducer.addKeyValueEntryBean("out.file.path", "",
				"Input the output file path, this parameter is mandatory.");
		mapProducer.addKeyValueEntryBean("if.export.tpm.matrix", "T", "Whether export the tpm matrix file.");
	}

	@Override
	protected void execute(OrganizedParameterGetter o) throws Exception {
		String inputFilePath = o.getSimplifiedString("input.dir.path");
		String outputFilePath = o.getSimplifiedString("out.file.path");
		String fileSuffix = o.getSimplifiedString("counts.file.suffix");
		boolean shouldExportTPM = o.getSimplifiedBool("if.export.tpm.matrix");

		MergeCountFiles mergeCountFiles = new MergeCountFiles();
		mergeCountFiles.setInputPath(inputFilePath);
		mergeCountFiles.setOutputPath(outputFilePath);
		mergeCountFiles.fileSuffix = fileSuffix;
		mergeCountFiles.shouldExportTPM = shouldExportTPM;

		mergeCountFiles.doIt();
		

		this.setText4Console(mergeCountFiles.output4console);
	}
}
