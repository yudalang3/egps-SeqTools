package module.regexExtract;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.EGPSUtil;

public class CLI {
	private static final Logger log = LoggerFactory.getLogger(CLI.class);
	
	public static void main(String[] args) throws IOException {
		if (args.length < 1) {
			String cliUtilityName = EGPSUtil.getCLIUtilityName(CLI.class);
			log.error("Usage: {} configFile", cliUtilityName);
			log.error("Warnings: do not use the whitespace, or the parameters will be split.");
			return;
		}
		
		IndependentModuleLoader independentModuleLoader = new IndependentModuleLoader();
		GuiMain guiMain = (GuiMain) independentModuleLoader.getFace();
		VOICM4RegexExtract voicm = new VOICM4RegexExtract(guiMain);
		
		String path = args[0];
		log.info("The input path is: {}", path);
		String fileToString = FileUtils.readFileToString(new File(path), StandardCharsets.UTF_8);
		voicm.execute(fileToString);
		log.info("Successfully execute.");
	}
	

}
