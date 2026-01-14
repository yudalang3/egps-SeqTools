package module.correlation4wnt;

import module.correlation4wnt.io.ParaModel;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.string.EGPSStringUtil;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;

/**
 * Command line renderer for the correlation visualization module.
 *
 * <p>This CLI generates a PNG image by rendering {@link PaintingPanel} offscreen.
 */
public class CLI {

	private static final Logger log = LoggerFactory.getLogger(CLI.class);

	public static void main(String[] args) throws Exception {
		Options options = new Options();
		options.addOption(Option.builder("i").longOpt("input").hasArg(true).required(true)
				.desc("Input TSV path (same format as VOICE importer expects).").build());
		options.addOption(Option.builder("o").longOpt("output").hasArg(true).required(true)
				.desc("Output PNG file path.").build());
		options.addOption(Option.builder().longOpt("width").hasArg(true).required(false)
				.desc("Image width, default 1200.").build());
		options.addOption(Option.builder().longOpt("height").hasArg(true).required(false)
				.desc("Image height, default 800.").build());

		options.addOption(Option.builder().longOpt("cat1").hasArg(true).required(false)
				.desc("cat1.col.names, default: name;category").build());
		options.addOption(Option.builder().longOpt("cat2").hasArg(true).required(false)
				.desc("cat2.col.names, default: name;cat2;value").build());
		options.addOption(Option.builder().longOpt("exp").hasArg(true).required(false)
				.desc("expMatrix.col.names, default: name").build());

		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();

		try {
			CommandLine cmd = parser.parse(options, args);

			String inputPath = cmd.getOptionValue("input");
			String outputPath = cmd.getOptionValue("output");
			int width = Integer.parseInt(cmd.getOptionValue("width", "1200"));
			int height = Integer.parseInt(cmd.getOptionValue("height", "800"));

			String cat1 = cmd.getOptionValue("cat1", "name;category");
			String cat2 = cmd.getOptionValue("cat2", "name;cat2;value");
			String exp = cmd.getOptionValue("exp", "name");

			ParaModel paraModel = new ParaModel();
			paraModel.setExpMatrixCol(Arrays.asList(exp));
			paraModel.setCat1Col(Arrays.asList(EGPSStringUtil.split(cat1, ';')));
			paraModel.setCat2Col(Arrays.asList(EGPSStringUtil.split(cat2, ';')));

			DataModel dataModel = new DataModel(inputPath, paraModel);

			PaintingPanel panel = new PaintingPanel();
			panel.setDataModel(dataModel);
			panel.setSize(width, height);

			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = image.createGraphics();
			panel.paint(g2d);
			g2d.dispose();

			File outputFile = new File(outputPath);
			if (outputFile.getParentFile() != null) {
				outputFile.getParentFile().mkdirs();
			}
			ImageIO.write(image, "png", outputFile);
			log.info("Saved image to: {}", outputFile.getAbsolutePath());

		} catch (Exception e) {
			log.error("CLI failed: {}", e.getMessage(), e);
			formatter.printHelp("CorrelationVis CLI", options);
		}
	}

}
