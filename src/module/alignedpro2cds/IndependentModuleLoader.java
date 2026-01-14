package module.alignedpro2cds;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import com.google.common.base.Strings;

import utils.EGPSFileUtil;
import egps2.builtin.modules.voice.bean.AbstractParamsAssignerAndParser4VOICE;
import egps2.builtin.modules.voice.fastmodvoice.OrganizedParameterGetter;
import egps2.builtin.modules.voice.fastmodvoice.TabModuleFaceOfVoice;
import egps2.modulei.ModuleClassification;
import egps2.modulei.ModuleVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class IndependentModuleLoader extends TabModuleFaceOfVoice {

	private static final Logger log = LoggerFactory.getLogger(IndependentModuleLoader.class);

	@Override
	public String getTabName() {
		return "Aligned Protein To CDS";
	}

	@Override
	public String getShortDescription() {
		return "A convenient  graphic tool to quick translate protein alignment to CDS alignment.";
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
				ModuleClassification.BYCOMPLEXITY_LEVEL_2_INDEX,
				ModuleClassification.BYDEPENDENCY_CROSS_MODULE_REFERENCED
		);
		return ret;
	}

	@Override
	protected void setParameter(AbstractParamsAssignerAndParser4VOICE mapProducer) {

		mapProducer.addKeyValueEntryBean("input.aligned.protein.path", "",
				"The target coding sequence file for translation, Necessary.");
		mapProducer.addKeyValueEntryBean("input.cds.path", "",
				"The target coding sequence file for translation, Necessary.");
		mapProducer.addKeyValueEntryBean("output.aligned.cds.path", "",
				"Set the output path of the aligned CDS file, if null, the file path is *aligned.cds.fas");

	}

	@Override
	protected void execute(OrganizedParameterGetter o) throws Exception {
		String inputAlignedProt = null;
		String inputCDSFile = null;
		String outputAlignedCDSFile = null;

		String string = o.getSimplifiedString("input.aligned.protein.path");
		if (Strings.isNullOrEmpty(string)) {
			throw new IllegalArgumentException("Plese input the $input.aligned.protein.path");
		} else {
			inputAlignedProt = string;
		}

		string = o.getSimplifiedString("input.cds.path");
		if (Strings.isNullOrEmpty(string)) {
			throw new IllegalArgumentException("Plese input the $input.cds.path");
		} else {
			inputCDSFile = string;
		}

		string = o.getSimplifiedStringWithDefault("output.aligned.cds.path");
		if (Strings.isNullOrEmpty(string)) {
			outputAlignedCDSFile = EGPSFileUtil.appendAdditionalStr2path(inputCDSFile, "aligned.cds");
		} else {
			outputAlignedCDSFile = string;
		}


		try {
			AlignedProt2AlignedCDS.makeTheConversion(new File(inputAlignedProt), new File(inputCDSFile),
					new File(outputAlignedCDSFile));
			List<String> strings4output = Arrays
					.asList("Successfully converted to file: ".concat(outputAlignedCDSFile));
			this.setText4Console(strings4output);

		} catch (Exception e) {
			log.error("AlignedProt2AlignedCDS conversion failed: {}", e.getMessage(), e);
			List<String> strings4output = Arrays.asList("Error: ".concat(e.getMessage()));
			this.setText4Console(strings4output);
		}


	}
}
