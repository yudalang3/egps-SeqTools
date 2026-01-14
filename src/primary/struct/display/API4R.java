package primary.struct.display;

import primary.struct.display.data.GeneData;
import primary.struct.display.drawer.DraggableRectangleDrawer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.JFrame;

public class API4R {
	private static final Logger log = LoggerFactory.getLogger(API4R.class);

	/**
	 * 
	 * @param jsonString
	 * @param w
	 * @param h
	 */
	public void draw_multiple_genes(String jsonString, int w, int h, int geneHeight) {

		try {
			// 解析JSON字符串为GeneData对象
			GeneData geneData = new GeneData(jsonString);
			DraggableRectangleDrawer panel = new DraggableRectangleDrawer(geneData,geneHeight);
			JFrame frame = new JFrame("Draggable Rectangle Drawer");
			frame.add(panel);
			frame.setSize(w, h);
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);

		} catch (Exception e) {
			log.error("Failed to draw genes: {}", e.getMessage(), e);
		}

	}

	public static void main(String[] args) {

	}
}
