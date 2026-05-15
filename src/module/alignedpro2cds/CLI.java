package module.alignedpro2cds;

import java.io.File;
import java.io.IOException;

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

		try {
			AlignedProt2AlignedCDS.makeTheConversion(new File(inputFilePath), new File(args[1]), new File(args[2]));
		} catch (IOException | IllegalArgumentException e) {
			log.error(e.getMessage());
			System.exit(1);
		}

		log.info("Successfully accomplished.");

	}

}
