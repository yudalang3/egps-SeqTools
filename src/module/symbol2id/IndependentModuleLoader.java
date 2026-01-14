package module.symbol2id;

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
		return "Gene symbol to entrezID";
	}

	@Override
	public String getShortDescription() {
		return "Quick translated the gene symbol to entrez IDs.";
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
		mapProducer.addKeyValueEntryBean("input.geneID.path", "",
				"The file path has the gene information, file format is tsv. Necessary");
		mapProducer.addKeyValueEntryBean("input.need2translate", "",
				"The file path has the gene symbols to translation, file format is tsv. Necessary");
		mapProducer.addKeyValueEntryBean("output.file.path", "",
				"The file path to output, file format is tsv. Necessary");
		mapProducer.addKeyValueEntryBean("index.of.column4translate", "1",
				"This is the column index need to translate (First is 1)");

	}

	@Override
	protected void execute(OrganizedParameterGetter o) throws Exception {
		String refPath = o.getSimplifiedString("input.geneID.path");
		String geneSymbolQueryPath = o.getSimplifiedString("input.need2translate");
		String outputPath = o.getSimplifiedString("output.file.path");
		int translateIndex = o.getSimplifiedInt("index.of.column4translate") - 1;


		TranslateCalculator runner = new TranslateCalculator();
		runner.setInputPath(refPath);
		runner.setOutputPath(outputPath);
		runner.setNeedToTranslateFilePath(geneSymbolQueryPath);

		runner.setIndexOfGeneSymbol(translateIndex);
		runner.symbol2entrezID();
		
		List<String> strings4output = runner.strings4output;

		this.setText4Console(strings4output);
	}
}
