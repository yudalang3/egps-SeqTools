package module.homoidentify.totsv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.EGPSUtil;

/**
 * <pre>
 * 请你帮我用JAVA的库 commons-cli，编写一个命令行的启动类。
 * 我有三个参数，第一个是 -i --input意义是 input hmmer search results file path；
 * 第二个是 -o --output 意义是 output tsv file path；
 * 第三个是 -n --num 意义是 first n number of columns to extract
 * 帮我写一个这样的命令行参数解析类。
 * 
 * </pre>
 */
public class CLI {
	private static final Logger log = LoggerFactory.getLogger(CLI.class);

	public static void main(String[] args) {
		// 创建 Options 对象
		Options options = new Options();

		// 添加 input 参数
		Option input = new Option("i", "input", true, "input hmmer search results file path");
		input.setRequired(true);
		options.addOption(input);

		// 添加 output 参数
		Option output = new Option("o", "output", true, "output tsv file path");
		output.setRequired(true);
		options.addOption(output);

		// 添加 num 参数
		Option num = new Option("n", "num", true, "first n number of columns to extract");
		num.setRequired(true);
		options.addOption(num);

		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		CommandLine cmd;

		try {
			// 解析命令行参数
			cmd = parser.parse(options, args);

			// 获取参数值
			String inputFilePath = cmd.getOptionValue("input");
			String outputFilePath = cmd.getOptionValue("output");
				String numValue = cmd.getOptionValue("num");

				// 打印参数值
				log.info("Input file: {}", inputFilePath);
				log.info("Output file: {}", outputFilePath);
				log.info("Number of columns: {}", numValue);

			// 在这里继续添加您的逻辑

			int int1 = Integer.parseInt(numValue);
			process(inputFilePath, outputFilePath, int1);

			} catch (ParseException e) {
				// 解析失败，打印帮助信息
				log.error(e.getMessage());
				formatter.printHelp(EGPSUtil.getCLIUtilityName(CLI.class), options);

				System.exit(1);
			} catch (IOException e) {
				log.error("I/O error.", e);
			}
	}

	private static void process(String inputFilePath, String outputFilePath, int numValue) throws IOException {

		int sz = 10 * 1024 * 1024;
		BufferedWriter bufferedOutputStream = new BufferedWriter(new FileWriter(outputFilePath), sz);
		
		try (BufferedReader br = new BufferedReader(new FileReader(inputFilePath), sz)) {
			String readLine = null;
			while ((readLine = br.readLine()) != null) {
				if (readLine.charAt(0) == '#') {
					continue;
				}
				// 使用 StringUtils.split 来拆分字符串，记得看它的API
				String[] parts = StringUtils.split(readLine, " ");

				bufferedOutputStream.write(parts[0]);
				for (int i = 1; i < numValue; i++) {
					bufferedOutputStream.write("\t");
					bufferedOutputStream.write(parts[i]);
				}
				bufferedOutputStream.write("\n");
			}

		}

		bufferedOutputStream.close();

	}

}
