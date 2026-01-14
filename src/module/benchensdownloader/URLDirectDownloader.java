package module.benchensdownloader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tsv.io.KitTable;
import tsv.io.TSVReader;
import egps2.UnifiedAccessPoint;
import egps2.frame.ComputationalModuleFace;
import egps2.frame.MyFrame;
import module.benchensdownloader.util.UrlUtils;

/**
 * 使用写一个新的模块Tab Name： URL List Downloader<br>
 * Description：Automatic download the files in URL list with progress indicator.
 * 创新点：为用户创建一个方便下载的小工具，拥有指示条，可以估计还需要多少时间。
 * 还需要改装的地方：需要openStream的时候获取整个文件的大小，然后更具已经给出的指示条计算当前进度。问题在于有些服务器它不会返回文件有多大。所以还得增加一个判定。
 */
public class URLDirectDownloader {
	private static final Logger log = LoggerFactory.getLogger(URLDirectDownloader.class);

	private File outputDir = new File(
			"C:\\Users\\yudal\\Documents\\BaiduSyncdisk\\博士后工作开展\\文献管理\\具体文献\\Wnt通路\\素材\\DB\\protein\\Ensembl");

	private File inputURLsFile = new File(
			"C:\\Users\\yudal\\Documents\\BaiduSyncdisk\\博士后工作开展\\文献管理\\具体文献\\Wnt通路\\素材\\DB\\SpeciesColl\\Ensembl_taxon\\20240514\\species_protein_download.tsv");

	private int startIndexOneBased = 8;

	private boolean hasHeaderLine = false;

	private MyFrame instanceFrame;

	public URLDirectDownloader() {
		boolean guIlaucnched = UnifiedAccessPoint.isGULaunched();
		if (guIlaucnched) {
			instanceFrame = UnifiedAccessPoint.getInstanceFrame();
		}
	}

	public void setOutputDir(File outputDir) {
		this.outputDir = outputDir;
	}

	public void setInputURLsFile(File inputURLsFile) {
		this.inputURLsFile = inputURLsFile;
	}

	public void setStartIndexOneBased(int startIndexOneBased) {
		this.startIndexOneBased = startIndexOneBased;
	}

	public void setHasHeaderLine(boolean hasHeaderLine) {
		this.hasHeaderLine = hasHeaderLine;
	}

	public static void main(String[] args) throws IOException {
		new URLDirectDownloader().run();
	}

	private void run() throws IOException {

		if (!outputDir.exists()) {
			outputDir.mkdirs();
		}

		KitTable tsvTextFile = TSVReader.readTsvTextFile(inputURLsFile.getAbsolutePath(), hasHeaderLine);

		List<List<String>> contents = tsvTextFile.getContents();
		int size = contents.size();

		int curr = 0;
		// 第13个下完了
		for (List<String> list : contents) {
			curr++;

			if (curr < startIndexOneBased) {
				continue;
			}
				String downloadURL = list.get(0);
				downloadContent(downloadURL);
				log.info("Current is {}, total is {}", curr, size);
			}

		}

	public String downloadContent(String urlStr) throws IOException {
		return downloadContent(null, urlStr);
	}

	public String downloadContent(ComputationalModuleFace computationalModuleFace, String urlString)
			throws IOException {

		///////////////////////////////////////////////////////////

		URL url = UrlUtils.toURL(urlString);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		try {
			// 获取文件大小（部分服务器可能返回 -1）
			long totalFileSize = connection.getContentLengthLong();
			long downloadedSize = 0;

			// 创建输出流以保存文件
			String fileName = urlString.substring(urlString.lastIndexOf('/') + 1);

			// 创建输入流以读取文件
			try (InputStream is = connection.getInputStream();
					FileOutputStream fos = new FileOutputStream(new File(outputDir, fileName))) {

				byte[] buffer = new byte[409600000];
				int bytesRead;

				StringBuilder stringBuilder = new StringBuilder();
				while ((bytesRead = is.read(buffer)) != -1) {
					fos.write(buffer, 0, bytesRead);
					downloadedSize += bytesRead;

					if (computationalModuleFace != null && instanceFrame != null) {
						stringBuilder.setLength(0);
						if (totalFileSize > 0) {
							stringBuilder.append(downloadedSize).append(" / ").append(totalFileSize).append(" = ");
							double percentage = (double) downloadedSize / (double) totalFileSize * 100.0;
							stringBuilder.append((int) percentage).append(" %");
						} else {
							stringBuilder.append(downloadedSize).append(" / unknown");
						}

						String statusText = stringBuilder.toString();
						SwingUtilities.invokeLater(
								() -> instanceFrame.onlyRefreshButtomStatesBar(computationalModuleFace, statusText, 100));
					}
				}
			}

			return fileName;
		} finally {
			connection.disconnect();
		}

	}

}
