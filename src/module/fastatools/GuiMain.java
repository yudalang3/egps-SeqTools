package module.fastatools;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.JTabbedPane;

import com.google.common.collect.Lists;

import egps2.UnifiedAccessPoint;
import egps2.builtin.modules.voice.fastmodvoice.DockableTabModuleFaceOfVoice;
import egps2.frame.ComputationalModuleFace;
import egps2.frame.gui.EGPSCustomTabbedPaneUI;
import module.fastatools.gui.ExtractSequencePartialMatchPanel;
import module.fastatools.gui.FastaConcatenatePanel;
import module.fastatools.gui.GenomeFastaSummarizer;
import module.fastatools.gui.RenamerPartialMatchPanel;
import egps2.modulei.IInformation;
import egps2.modulei.IModuleLoader;

@SuppressWarnings("serial")
public class GuiMain extends ComputationalModuleFace {

	private final List<DockableTabModuleFaceOfVoice> subTabs = Lists.newArrayList();
	private final JTabbedPane jTabbedPane = new JTabbedPane(JTabbedPane.LEFT);

	protected GuiMain(IModuleLoader moduleLoader) {
		super(moduleLoader);
		setLayout(new BorderLayout());

		Font defaultTitleFont = UnifiedAccessPoint.getLaunchProperty().getDefaultTitleFont();

		jTabbedPane.setFont(defaultTitleFont);
		initPanels(jTabbedPane);

		jTabbedPane.setUI(new EGPSCustomTabbedPaneUI());

		add(jTabbedPane, BorderLayout.CENTER);
	}

	private void initPanels(JTabbedPane jTabbedPane) {
		{
			GenomeFastaSummarizer panel = new GenomeFastaSummarizer(this);
			subTabs.add(panel);
			jTabbedPane.addTab(panel.getTabName(), null, panel, panel.getShortDescription());
		}
		{
			FastaConcatenatePanel panel = new FastaConcatenatePanel(this);
			subTabs.add(panel);
			jTabbedPane.addTab(panel.getTabName(), null, panel, panel.getShortDescription());
		}
		{
			ExtractSequencePartialMatchPanel panel = new ExtractSequencePartialMatchPanel(this);
			subTabs.add(panel);
			jTabbedPane.addTab(panel.getTabName(), null, panel, panel.getShortDescription());
		}
		{
			RenamerPartialMatchPanel panel = new RenamerPartialMatchPanel(this);
			subTabs.add(panel);
			jTabbedPane.addTab(panel.getTabName(), null, panel, panel.getShortDescription());
		}
	}

	@Override
	public boolean canImport() {
		return false;
	}

	@Override
	public void importData() {

	}

	@Override
	public boolean canExport() {
		return false;
	}

	@Override
	public void exportData() {

	}

	@Override
	public String[] getFeatureNames() {
		return new String[] { "Download the annotation file from the Ensembl ftp." };
	}

	@Override
	protected void initializeGraphics() {
		for (DockableTabModuleFaceOfVoice subTab : subTabs) {
			subTab.initializeGraphics();
		}
	}

	@Override
	public IInformation getModuleInfo() {
		IInformation iInformation = new IInformation() {

			@Override
			public String getWhatDataInvoked() {
				return "The data is loading from the import dialog.";
			}

			@Override
			public String getSummaryOfResults() {
				return "The functionality is powered by the eGPS software.";
			}
		};
		return iInformation;
	}

}
