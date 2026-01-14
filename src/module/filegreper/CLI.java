package module.filegreper;

import java.util.List;

import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.string.EGPSStringUtil;
import utils.EGPSUtil;

public class CLI {
	private static final Logger log = LoggerFactory.getLogger(CLI.class);

	public static void main(String[] args) throws Exception {
		if (args.length < 4) {
			log.error("Usage:");
			String cliUtilityName = EGPSUtil.getCLIUtilityName(CLI.class);
			log.error(cliUtilityName
					+ " queryString filePath ifExportHeaderLine neighborLineNumber");
			log.error("Example: {} query input/file/path T 0", cliUtilityName);
			return;
		}
		String queryString = args[0];
		String inputFilePath = args[1];
		boolean exportHeaderLine = BooleanUtils.toBoolean(args[2]);
		int neiberLineNumber = Integer.parseInt(args[3]);

		DoGrepAction doGrepAction = new DoGrepAction();
		doGrepAction.inputFilePath = inputFilePath;
		doGrepAction.searchKeys = EGPSStringUtil.split(queryString, '|');
		doGrepAction.exportHeader = exportHeaderLine;
		doGrepAction.followingLineNumber = neiberLineNumber;

		doGrepAction.doIt();

		List<String> outputList = doGrepAction.getOutputList();
		for (String string : outputList) {
			log.info(string);
		}

	}

}
