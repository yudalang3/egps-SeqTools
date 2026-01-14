package module.alignedpro2cds;

import java.io.File;

import utils.EGPSUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CLI {
	private static final Logger log = LoggerFactory.getLogger(CLI.class);

	public static void main(String[] args) throws Exception {
		if (args.length < 3) {
			String cliUtilityName = EGPSUtil.getCLIUtilityName(CLI.class);
			log.error("Usage:");
			log.error("{} filePath geneticCodeName", cliUtilityName);
			log.error("Example: {} aligned.protein.fas cds.fas aligned.cds.fas ", cliUtilityName);
			return;
		}
		String inputFilePath = args[0];

		AlignedProt2AlignedCDS.makeTheConversion(new File(inputFilePath), new File(args[1]), new File(args[2]));

		log.info("Successfully accomplished.");

	}

}
