package module.benchensdownloader;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import module.benchensdownloader.util.UrlUtils;

public class ListDirectories {
	private static final Logger log = LoggerFactory.getLogger(ListDirectories.class);

    public static void main(String[] args) {

		List<String> outputList = new LinkedList<>();
		String speciesName = null;

        try {
            // 要访问的URL
//            String dirURL = "https://ftp.ensembl.org/pub/release-112/fasta/";
			String dirURL = "file:/C:\\Users\\yudal\\Documents\\BaiduSyncdisk\\博士后工作开展\\工作汇报\\基于富集分析的通路激活\\WntCompoentsCollections\\Readme.txt";
            URL url = UrlUtils.toURL(dirURL);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

            // 正则表达式匹配文件夹链接
            Pattern dirPattern = Pattern.compile("<td><a href=\"(.*?)/\">.*?</a></td>");
            String line;
            
            // 读取每一行，寻找文件夹链接
            while ((line = reader.readLine()) != null) {
                Matcher matcher = dirPattern.matcher(line);
                if (matcher.find()) {
                    String dirName = matcher.group(1); // 获取文件夹名称
					speciesName = dirName.replaceAll("_", " ");
					String newUrl = dirURL + dirName + "/cdna/";
					// 要得到 all.fa.gz的 文件
					// <a href="Acanthochromis_polyacanthus.ASM210954v1.cdna.all.fa.gz">
					String pathOfFastaGZ = null;

						try {

							pathOfFastaGZ = extractPathOfFastaGZ(newUrl);
						} catch (Exception e) {
							log.error("Failed to extract fasta gz path from: {}", newUrl, e);
						}
						if (pathOfFastaGZ != null) {
							log.info("{}", newUrl + pathOfFastaGZ);
							StringJoiner sJoiner = new StringJoiner("\t");
							sJoiner.add(newUrl + pathOfFastaGZ).add(speciesName).add(dirName);
							outputList.add(sJoiner.toString());
						}
                }

			}

            reader.close();

				FileUtils.writeLines(new File(
						"C:\\Users\\yudal\\Documents\\BaiduSyncdisk\\博士后工作开展\\文献管理\\具体文献\\Wnt通路\\素材\\DB\\SpeciesColl\\Ensembl_taxon\\20240514\\species_cdna_download.txt"),
						outputList);
	        } catch (IOException e) {
	            log.error("Failed to list directories.", e);
	        }

			for (String outputList2 : outputList) {
				log.info(outputList2);
			}
	    }

	private static String extractPathOfFastaGZ(String urlPath) throws IOException {
		URL url = UrlUtils.toURL(urlPath);
		BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

		// 正则表达式匹配文件夹链接
		Pattern dirPattern = Pattern.compile("<a href=\"(.*?)cdna\\.all\\.fa\\.gz\">");
		String line;

		String ret = null;

		// 读取每一行，寻找文件夹链接
		while ((line = reader.readLine()) != null) {
			Matcher matcher = dirPattern.matcher(line);
			if (matcher.find()) {
				String dirName = matcher.group(1); // 获取文件夹名称
				// 要得到 all.fa.gz的 文件
				// <a href="Acanthochromis_polyacanthus.ASM210954v1.cdna.all.fa.gz">
				ret = dirName.concat("cdna.all.fa.gz");
				break;
			}

		}

		reader.close();

		return ret;
	}
}
