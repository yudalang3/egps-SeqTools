package module.localblast.gui.search.programe;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.jidesoft.swing.JideTabbedPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import egps2.frame.gui.handler.EGPSTextTransferHandler;
import egps2.UnifiedAccessPoint;
import module.localblast.gui.AreadyInstalledBlastSoftwareBean;
import module.localblast.gui.AreadyInstalledBlastSoftwareBean.BlastpConfig;
import module.localblast.gui.SearchToolsPanel;
import module.localblast.gui.util.JTextFieldHintListener;
import module.localblast.gui.util.Util;

@SuppressWarnings("serial")
public class BasicBlastpPanel extends JPanel implements SearchProgrameCommon {
	private static final Logger log = LoggerFactory.getLogger(BasicBlastpPanel.class);

	private JTextField textField_query;
	private JTextField textField_queryloc;
	private JTextField textField_out;
	private JTextField textField_evalue;
	private JTextField textField_word_size;
	private JTextField textField_gapopen;
	private JTextField textField_gapextend;
	private JTextField textField_outfmt;
	private JComboBox<String> comboBox_task;

	private SearchToolsPanel searchToolsPanel;
	private JSpinner spinnerOfThreadsToUse;
	private JTextField jTextFieldOfDB;
	private BlastpConfig config;

	/**
	 * Create the panel.
	 *
	 * @param searchToolsPanel
	 */
	public BasicBlastpPanel(SearchToolsPanel searchToolsPanel) {
		this.searchToolsPanel = searchToolsPanel;
		this.config = searchToolsPanel.getLocalBlastMain().getAreadyInstalledBlastSoftwareBean().getBlastpConfig();

		Font defaultFont = UnifiedAccessPoint.getLaunchProperty().getDefaultFont();
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblNewLabel_5 = new JLabel("out*");
		lblNewLabel_5.setFont(defaultFont);
		GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
		gbc_lblNewLabel_5.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_5.gridx = 0;
		gbc_lblNewLabel_5.gridy = 0;
		add(lblNewLabel_5, gbc_lblNewLabel_5);

		textField_out = new JTextField();
		textField_out.setToolTipText("<File_Out, file name length < 256>   Output file name   Default = `-'");
		textField_out.setFont(defaultFont);
		textField_out.setTransferHandler(new EGPSTextTransferHandler());
		GridBagConstraints gbc_textField_out = new GridBagConstraints();
		gbc_textField_out.insets = new Insets(0, 0, 5, 0);
		gbc_textField_out.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_out.gridx = 1;
		gbc_textField_out.gridy = 0;
		add(textField_out, gbc_textField_out);
		textField_out.setColumns(10);

		JLabel lblNewLabel = new JLabel("query*");
		lblNewLabel.setFont(defaultFont);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 1;
		add(lblNewLabel, gbc_lblNewLabel);

		textField_query = new JTextField();
		textField_query.setToolTipText("Input file name, Default = `-'");
		textField_query.setFont(defaultFont);
		textField_query.setTransferHandler(new EGPSTextTransferHandler());
		GridBagConstraints gbc_textField_query = new GridBagConstraints();
		gbc_textField_query.insets = new Insets(0, 0, 5, 0);
		gbc_textField_query.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_query.gridx = 1;
		gbc_textField_query.gridy = 1;
		add(textField_query, gbc_textField_query);
		textField_query.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("query_loc");
		lblNewLabel_1.setFont(defaultFont);
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 2;
		add(lblNewLabel_1, gbc_lblNewLabel_1);

		textField_queryloc = new JTextField();
		textField_queryloc.setToolTipText(
				"<html>Location on the query sequence in 1-based offsets (Format: start-stop)<br>\r\nFor example: 1-10 means for all sequences you input as query, <br>only sub-sequence of location 1 to10 is used for alignment.");
		textField_queryloc.setFont(defaultFont);
		GridBagConstraints gbc_textField_queryloc = new GridBagConstraints();
		gbc_textField_queryloc.insets = new Insets(0, 0, 5, 0);
		gbc_textField_queryloc.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_queryloc.gridx = 1;
		gbc_textField_queryloc.gridy = 2;
		add(textField_queryloc, gbc_textField_queryloc);
		textField_queryloc.setColumns(10);


		JLabel lblNewLabel_3 = new JLabel("task*");
		lblNewLabel_3.setFont(defaultFont);
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 0;
		gbc_lblNewLabel_3.gridy = 3;
		add(lblNewLabel_3, gbc_lblNewLabel_3);

		comboBox_task = new JComboBox<>();
		comboBox_task.setToolTipText(
				"<html>Four different tasks are supported: \r\n<ol>\r\n<li> “megablast”, for very similar sequences (e.g, sequencing errors), \r\n<li> “dc-megablast”, typically used for inter-species comparisons, \r\n<li> “blastn”, the traditional program used for inter-species comparisons, \r\n<li> “blastn-short”, optimized for sequences less than 30 nucleotides.");
		comboBox_task.setFont(defaultFont);
		comboBox_task.setModel(new DefaultComboBoxModel<String>(
				new String[] { "blastp", "blastp-fast", "blastp-short" }));
		GridBagConstraints gbc_comboBox_task = new GridBagConstraints();
		gbc_comboBox_task.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox_task.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_task.gridx = 1;
		gbc_comboBox_task.gridy = 3;
		add(comboBox_task, gbc_comboBox_task);

		JLabel lblNewLabel_4 = new JLabel("db*");
		lblNewLabel_4.setFont(defaultFont);
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4.gridx = 0;
		gbc_lblNewLabel_4.gridy = 4;
		add(lblNewLabel_4, gbc_lblNewLabel_4);

		AreadyInstalledBlastSoftwareBean areadyInstalledBlastSoftwareBean = searchToolsPanel.getLocalBlastMain()
				.getAreadyInstalledBlastSoftwareBean();
		jTextFieldOfDB = new JTextField(areadyInstalledBlastSoftwareBean.getProteinDBPath());
		jTextFieldOfDB.setFont(defaultFont);
		jTextFieldOfDB.setTransferHandler(new EGPSTextTransferHandler());
		jTextFieldOfDB.setToolTipText(
				"<html>BLAST database name. Users can input nt/refseq_rna/pdbnt et al. for remote blast.<br><br>"
						+ "Available BLAST databases Name Title Type <br>" + "nt Nucleotide collection DNA <br>"
						+ "nr Non-redundant Protein <br>" + "refseq_rna NCBI Transcript Reference Sequences DNA <br>"
						+ "refseq_protein NCBI Protein Reference Sequences Protein swissprot Non-redundant UniProtKB/SwissProt sequences Protein <br>"
						+ "pdbaa PDB protein database Protein <br>" + "pdbnt PDB nucleotide database DNA <br>");
		GridBagConstraints gbc_comboBox_db = new GridBagConstraints();
		gbc_comboBox_db.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox_db.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_db.gridx = 1;
		gbc_comboBox_db.gridy = 4;
		add(jTextFieldOfDB, gbc_comboBox_db);

		JLabel lblNewLabel_6 = new JLabel("evalue*");
		lblNewLabel_6.setFont(defaultFont);
		GridBagConstraints gbc_lblNewLabel_6 = new GridBagConstraints();
		gbc_lblNewLabel_6.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_6.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_6.gridx = 0;
		gbc_lblNewLabel_6.gridy = 5;
		add(lblNewLabel_6, gbc_lblNewLabel_6);

		textField_evalue = new JTextField();
		textField_evalue.setToolTipText("Expect value (E) for saving hits");
		textField_evalue.setText("10.0");
		textField_evalue.setFont(defaultFont);
		GridBagConstraints gbc_textField_evalue = new GridBagConstraints();
		gbc_textField_evalue.insets = new Insets(0, 0, 5, 0);
		gbc_textField_evalue.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_evalue.gridx = 1;
		gbc_textField_evalue.gridy = 5;
		add(textField_evalue, gbc_textField_evalue);
		textField_evalue.setColumns(10);

		JLabel lblNewLabel_7 = new JLabel("word_size");
		lblNewLabel_7.setFont(defaultFont);
		GridBagConstraints gbc_lblNewLabel_7 = new GridBagConstraints();
		gbc_lblNewLabel_7.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_7.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_7.gridx = 0;
		gbc_lblNewLabel_7.gridy = 6;
		add(lblNewLabel_7, gbc_lblNewLabel_7);

		textField_word_size = new JTextField();
		textField_word_size.setToolTipText(
				"<html>Length of initial exact match.<br>\r\nIn dc-megablast: Number of matching nucleotides in initial match. <br>\r\ndc-megablast allows non-consecutive letters to match.");
		textField_word_size.setFont(defaultFont);
		GridBagConstraints gbc_textField_word_size = new GridBagConstraints();
		gbc_textField_word_size.insets = new Insets(0, 0, 5, 0);
		gbc_textField_word_size.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_word_size.gridx = 1;
		gbc_textField_word_size.gridy = 6;
		add(textField_word_size, gbc_textField_word_size);
		textField_word_size.setColumns(10);

		JLabel lblNewLabel_8 = new JLabel("gapopen");
		lblNewLabel_8.setFont(defaultFont);
		GridBagConstraints gbc_lblNewLabel_8 = new GridBagConstraints();
		gbc_lblNewLabel_8.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_8.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_8.gridx = 0;
		gbc_lblNewLabel_8.gridy = 7;
		add(lblNewLabel_8, gbc_lblNewLabel_8);

		textField_gapopen = new JTextField();
		textField_gapopen.setFont(defaultFont);
		GridBagConstraints gbc_textField_gapopen = new GridBagConstraints();
		gbc_textField_gapopen.insets = new Insets(0, 0, 5, 0);
		gbc_textField_gapopen.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_gapopen.gridx = 1;
		gbc_textField_gapopen.gridy = 7;
		add(textField_gapopen, gbc_textField_gapopen);
		textField_gapopen.setColumns(10);

		JLabel lblNewLabel_9 = new JLabel("gapextend");
		lblNewLabel_9.setFont(defaultFont);
		GridBagConstraints gbc_lblNewLabel_9 = new GridBagConstraints();
		gbc_lblNewLabel_9.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_9.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_9.gridx = 0;
		gbc_lblNewLabel_9.gridy = 8;
		add(lblNewLabel_9, gbc_lblNewLabel_9);

		textField_gapextend = new JTextField();
		textField_gapextend.setFont(defaultFont);
		GridBagConstraints gbc_textField_gapextend = new GridBagConstraints();
		gbc_textField_gapextend.insets = new Insets(0, 0, 5, 0);
		gbc_textField_gapextend.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_gapextend.gridx = 1;
		gbc_textField_gapextend.gridy = 8;
		add(textField_gapextend, gbc_textField_gapextend);
		textField_gapextend.setColumns(10);

		JLabel lblNewLabel_10 = new JLabel("outfmt*");
		lblNewLabel_10.setFont(defaultFont);
		GridBagConstraints gbc_lblNewLabel_10 = new GridBagConstraints();
		gbc_lblNewLabel_10.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_10.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_10.gridx = 0;
		gbc_lblNewLabel_10.gridy = 9;
		add(lblNewLabel_10, gbc_lblNewLabel_10);

		textField_outfmt = new JTextField();
		textField_outfmt.setToolTipText(
				"<html>alignment view options:<br>\r\n0 = pairwise,<br>\r\n1 = query-anchored showing identities,<br>\r\n2 = query-anchored no identities,<br>\r\n3 = flat query-anchored, show identities,<br>\r\n4 = flat query-anchored, no identities,<br>\r\n5 = XML Blast output,<br>\r\n6 = tabular,<br>\r\n7 = tabular with comment lines,<br>\r\n8 = Text ASN.1,<br>\r\n9 = Binary ASN.1<br>\r\n10 = Comma-separated values<br>\r\n11 = BLAST archive format (ASN.1)<br>... See help for details");
//		textField_outfmt.setText("6");
		textField_outfmt.setFont(defaultFont);
		textField_outfmt.addFocusListener(new JTextFieldHintListener(textField_outfmt, "6"));
		GridBagConstraints gbc_textField_outfmt = new GridBagConstraints();
		gbc_textField_outfmt.insets = new Insets(0, 0, 5, 0);
		gbc_textField_outfmt.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_outfmt.gridx = 1;
		gbc_textField_outfmt.gridy = 9;
		add(textField_outfmt, gbc_textField_outfmt);
		textField_outfmt.setColumns(10);

		JLabel lblNewLabel_11 = new JLabel("num_threads*");
		lblNewLabel_11.setFont(defaultFont);
		GridBagConstraints gbc_lblNewLabel_11 = new GridBagConstraints();
		gbc_lblNewLabel_11.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_11.gridx = 0;
		gbc_lblNewLabel_11.gridy = 10;
		add(lblNewLabel_11, gbc_lblNewLabel_11);

		spinnerOfThreadsToUse = new JSpinner();
		spinnerOfThreadsToUse.setToolTipText("Number of threads (CPUs) to use in the BLAST search. Default = `1'");
		spinnerOfThreadsToUse.setModel(new SpinnerNumberModel(1, 1, 16, 1));
		spinnerOfThreadsToUse.setFont(defaultFont);
		GridBagConstraints gbc_spinnerOfThreadsToUse = new GridBagConstraints();
		gbc_spinnerOfThreadsToUse.insets = new Insets(0, 0, 5, 0);
		gbc_spinnerOfThreadsToUse.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinnerOfThreadsToUse.anchor = GridBagConstraints.EAST;
		gbc_spinnerOfThreadsToUse.gridx = 1;
		gbc_spinnerOfThreadsToUse.gridy = 10;
		add(spinnerOfThreadsToUse, gbc_spinnerOfThreadsToUse);

		JLabel lblNewLabel_12 = new JLabel("Note: the parameter note with star symbol is mandatory.");
		lblNewLabel_12.setFont(defaultFont);
		lblNewLabel_12.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblNewLabel_12 = new GridBagConstraints();
		gbc_lblNewLabel_12.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_12.gridx = 1;
		gbc_lblNewLabel_12.gridy = 11;
		add(lblNewLabel_12, gbc_lblNewLabel_12);

		// 从配置加载初始值
		loadFromConfig();

		// 添加监听器，实时更新配置
		setupListeners();
	}

	/**
	 * 从配置加载初始值
	 */
	private void loadFromConfig() {
		if (config.getQuery() != null && !config.getQuery().isEmpty()) {
			textField_query.setText(config.getQuery());
		}
		if (config.getQueryLoc() != null && !config.getQueryLoc().isEmpty()) {
			textField_queryloc.setText(config.getQueryLoc());
		}
		if (config.getTask() != null && !config.getTask().isEmpty()) {
			comboBox_task.setSelectedItem(config.getTask());
		}
		if (config.getOut() != null && !config.getOut().isEmpty()) {
			textField_out.setText(config.getOut());
		}
		if (config.getOutfmt() != null && !config.getOutfmt().isEmpty()) {
			textField_outfmt.setText(config.getOutfmt());
		}
		spinnerOfThreadsToUse.setValue(config.getNumThreads());
		if (config.getEvalue() != null && !config.getEvalue().isEmpty()) {
			textField_evalue.setText(config.getEvalue());
		}
		if (config.getWordSize() != null && !config.getWordSize().isEmpty()) {
			textField_word_size.setText(config.getWordSize());
		}
		if (config.getGapopen() != null && !config.getGapopen().isEmpty()) {
			textField_gapopen.setText(config.getGapopen());
		}
		if (config.getGapextend() != null && !config.getGapextend().isEmpty()) {
			textField_gapextend.setText(config.getGapextend());
		}
	}

	/**
	 * 设置监听器，实时更新配置
	 */
	private void setupListeners() {
		// 文本框监听
		textField_query.getDocument().addDocumentListener(createDocListener(() -> config.setQuery(textField_query.getText())));
		textField_queryloc.getDocument().addDocumentListener(createDocListener(() -> config.setQueryLoc(textField_queryloc.getText())));
		textField_out.getDocument().addDocumentListener(createDocListener(() -> config.setOut(textField_out.getText())));
		textField_outfmt.getDocument().addDocumentListener(createDocListener(() -> config.setOutfmt(textField_outfmt.getText())));
		textField_evalue.getDocument().addDocumentListener(createDocListener(() -> config.setEvalue(textField_evalue.getText())));
		textField_word_size.getDocument().addDocumentListener(createDocListener(() -> config.setWordSize(textField_word_size.getText())));
		textField_gapopen.getDocument().addDocumentListener(createDocListener(() -> config.setGapopen(textField_gapopen.getText())));
		textField_gapextend.getDocument().addDocumentListener(createDocListener(() -> config.setGapextend(textField_gapextend.getText())));

		// 下拉框监听
		comboBox_task.addItemListener(e -> {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				config.setTask(e.getItem().toString());
			}
		});

		// Spinner 监听
		spinnerOfThreadsToUse.addChangeListener((ChangeEvent e) -> config.setNumThreads((Integer) spinnerOfThreadsToUse.getValue()));
	}

	private DocumentListener createDocListener(Runnable action) {
		return new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) { action.run(); }
			@Override
			public void removeUpdate(DocumentEvent e) { action.run(); }
			@Override
			public void changedUpdate(DocumentEvent e) { action.run(); }
		};
	}

	@Override
	public void run(JTextArea normal, JTextArea error) {

		AreadyInstalledBlastSoftwareBean areadyInstalledBlastSoftwareBean = searchToolsPanel.getLocalBlastMain()
				.getAreadyInstalledBlastSoftwareBean();

		List<String> commandTokens = new ArrayList<>();

		String windowsBlastP = areadyInstalledBlastSoftwareBean.getWindowsBlastP();
		commandTokens.add(windowsBlastP);
		commandTokens.add("-query");
		commandTokens.add(textField_query.getText());

		if (textField_queryloc.getText().length() > 0) {
			commandTokens.add("-query_loc");
			commandTokens.add(textField_queryloc.getText());
		}
		commandTokens.add("-task");
		commandTokens.add(comboBox_task.getSelectedItem().toString());

		commandTokens.add("-db");
		commandTokens.add(jTextFieldOfDB.getText());

		commandTokens.add("-out");
		commandTokens.add(textField_out.getText());
		commandTokens.add("-outfmt");
		commandTokens.add(textField_outfmt.getText());
		commandTokens.add("-num_threads");
		commandTokens.add(spinnerOfThreadsToUse.getValue().toString());

		if (textField_evalue.getText().length() > 0) {
			commandTokens.add("-evalue");
			commandTokens.add(textField_evalue.getText());
		}
		if (textField_word_size.getText().length() > 0) {
			commandTokens.add("-word_size");
			commandTokens.add(textField_word_size.getText());
		}
		if (textField_gapopen.getText().length() > 0) {
			commandTokens.add("-gapopen");
			commandTokens.add(textField_gapopen.getText());
		}
		if (textField_gapextend.getText().length() > 0) {
			commandTokens.add("-gapextend");
			commandTokens.add(textField_gapextend.getText());
		}

		// 千万不要，这里不是remote
//		commandTokens.add("-remote");

		normal.setText("");
		error.setText("");
		normal.append(String.join(" ", commandTokens));

		try {

			long timeMillis = System.currentTimeMillis();
			final Process process = new ProcessBuilder(commandTokens).start();
			Util.printMessage(process.getInputStream(), false, normal, error);
			Util.printMessage(process.getErrorStream(), true, normal, error);
			// value 0 is normal
			int value = process.waitFor();
			if (value != 0) {
				JideTabbedPane bottomTabbedPanel = searchToolsPanel.getBottomTabbedPanel();
				bottomTabbedPanel.setSelectedIndex(1);
				error.requestFocus();
			} else {
				normal.append("\n\nTake time of ms: " + (System.currentTimeMillis() - timeMillis));
			}
			} catch (Exception e2) {
				log.error("Failed to run blastp.", e2);
			}

		}

	@Override
	public String getTargetDir() {
		return textField_out.getText();
	}

	public String getDBPathString() {
		String text = jTextFieldOfDB.getText();
		return text;
	}

}
