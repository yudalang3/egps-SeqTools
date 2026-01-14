package module.batchexcom;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TsvPrettyPrinter {
	private static final Logger log = LoggerFactory.getLogger(TsvPrettyPrinter.class);

	    public static void main(String[] args) {
	        if (args.length < 1) {
				log.error("Please provides the tsv file path.");
	            return;
	        }
        
        String filePath = args[0];
        List<String[]> rows = new ArrayList<>();
        int[] columnWidths = null;

        // 读取并解析文件
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split("\t");
                rows.add(fields);
                
                // 初始化或更新列宽
                if (columnWidths == null) {
                    columnWidths = new int[fields.length];
                }
                for (int i = 0; i < fields.length; i++) {
                    columnWidths[i] = Math.max(columnWidths[i], fields[i].length());
                }
            }
	        } catch (IOException e) {
	            log.error("Failed to read TSV file: {}", filePath, e);
	            return;
	        }
			if (columnWidths == null) {
				log.warn("Empty file: {}", filePath);
				return;
			}

	        // 打印表格
	        for (String[] row : rows) {
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < row.length; i++) {
					sb.append(padRight(row[i], columnWidths[i] + 1));
				}
				log.info(sb.toString());
	        }
	    }

		private static String padRight(String s, int width) {
			return String.format("%-" + width + "s", s);
		}
	}
