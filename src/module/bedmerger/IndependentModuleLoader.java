package module.bedmerger;

import java.util.List;

import egps2.frame.ModuleFace;
import egps2.modulei.IModuleLoader;
import egps2.modulei.ModuleClassification;
import egps2.modulei.ModuleVersion;

public class IndependentModuleLoader implements IModuleLoader{

	List<String> list;
	
	public void setList(List<String> list) {
		this.list = list;
	}

	@Override
	public String getTabName() {
		return "Bed merger";
	}

	@Override
	public String getShortDescription() {
		return "Merge bed format files according to the record locations. (inheritance from eGPS 1)";
	}

	@Override
	public ModuleVersion getVersion() {
		return new ModuleVersion(0, 0, 1);
	}

	@Override
	public ModuleFace getFace() {
		SimpleModuleMain guiMain = new SimpleModuleMain(this);
		return guiMain;
	}

	public int[] getCategory() {
		int[] ret = ModuleClassification.getOneModuleClassification(
				ModuleClassification.BYFUNCTIONALITY_SIMPLE_TOOLS_INDEX,
				ModuleClassification.BYAPPLICATION_GENOMICS_INDEX,
				ModuleClassification.BYCOMPLEXITY_LEVEL_1_INDEX,
				ModuleClassification.BYDEPENDENCY_ONLY_EMPLOY_CONTAINER
		);
		return ret;
	}
}
