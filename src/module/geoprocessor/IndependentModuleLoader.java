package module.geoprocessor;

import egps2.frame.ModuleFace;
import egps2.modulei.IModuleLoader;
import egps2.modulei.ModuleClassification;
import egps2.modulei.ModuleVersion;

public class IndependentModuleLoader implements IModuleLoader{


	@Override
	public String getTabName() {
		return "GEO Supplementary File Processor";
	}

	@Override
	public String getShortDescription() {
		return "Conveniently process the supplemental files from the GEO database";
	}

	@Override
	public ModuleVersion getVersion() {
		return new ModuleVersion(0, 0, 1);
	}

	@Override
	public ModuleFace getFace() {
		return new GuiMain(this);
	}

	@Override
	public int[] getCategory() {
		int[] ret = ModuleClassification.getOneModuleClassification(
				ModuleClassification.BYFUNCTIONALITY_OPERATIONAL_WORKBENCH_INDEX,
				ModuleClassification.BYAPPLICATION_GENOMICS_INDEX, ModuleClassification.BYCOMPLEXITY_LEVEL_3_INDEX,
				ModuleClassification.BYDEPENDENCY_CROSS_MODULE_REFERENCED
		);
		return ret;
	}
}
