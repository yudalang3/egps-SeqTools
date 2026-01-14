package module.skeletonscatter;

import egps2.frame.ModuleFace;
import egps2.modulei.IModuleLoader;
import egps2.modulei.ModuleClassification;
import egps2.modulei.ModuleVersion;

public class IndependentModuleLoader implements IModuleLoader{

	@Override
	public String getTabName() {
		return "Skeleton scatter plot";
	}

	@Override
	public String getShortDescription() {
		return "A primary skeleton scatter plot to show the ability of visualization computation.";
	}

	@Override
	public ModuleVersion getVersion() {
		return new ModuleVersion(0, 0, 1);
	}

	@Override
	public ModuleFace getFace() {
		return new MainFace(this);
	}
	
	
	@Override
	public int[] getCategory() {
		int[] ret = ModuleClassification.getOneModuleClassification(
				
				ModuleClassification.BYFUNCTIONALITY_PRIMITIVE_VISUALIZATION_INDEX,
				ModuleClassification.BYAPPLICATION_VISUALIZATION_INDEX,
				ModuleClassification.BYCOMPLEXITY_LEVEL_1_INDEX,
				ModuleClassification.BYDEPENDENCY_voice_INVOKED
		);
		return ret;
	}

}
