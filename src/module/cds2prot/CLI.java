package module.cds2prot;

import java.util.List;

import utils.EGPSUtil;
import geneticcodes.IGeneticCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CLI {
	private static final Logger log = LoggerFactory.getLogger(CLI.class);

	public static void main(String[] args) throws Exception {
		if (args.length < 2) {
			log.error("Usage:");
			String cliUtilityName = EGPSUtil.getCLIUtilityName(CLI.class);
			log.error("{} filePath geneticCodeName", cliUtilityName);
			log.error(
					"Example: " + cliUtilityName + " input/file/path " + IGeneticCode.GeneticCodeTableNames[0]);
			return;
		}
		String inputFilePath = args[0];

		DoCds2ProtTranslateAction doGrepAction = new DoCds2ProtTranslateAction();
		doGrepAction.inputFilePath = inputFilePath;
		doGrepAction.geneticodeName = args[1];

		doGrepAction.doIt();

		List<String> outputList = doGrepAction.getOutputList();
		for (String string : outputList) {
			log.info(string);
		}

	}

}
