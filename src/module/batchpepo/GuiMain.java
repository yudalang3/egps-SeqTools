package module.batchpepo;

import com.google.common.collect.Lists;
import egps2.UnifiedAccessPoint;
import egps2.builtin.modules.voice.fastmodvoice.DockableTabModuleFaceOfVoice;
import egps2.frame.ComputationalModuleFace;
import egps2.frame.gui.EGPSCustomTabbedPaneUI;
import egps2.modulei.IInformation;
import egps2.modulei.IModuleLoader;
import module.batchpepo.gui.ExtractUniquePeptideRunner;
import module.batchpepo.gui.GetOneGeneWithLongestTranscriptPanel;

import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import java.awt.Font;
import java.util.List;

@SuppressWarnings("serial")
public class GuiMain extends ComputationalModuleFace {

	private final List<DockableTabModuleFaceOfVoice> subTabs = Lists.newArrayList();
	private final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);

	protected GuiMain(IModuleLoader moduleLoader) {
		super(moduleLoader);
		setLayout(new BorderLayout());

		Font defaultTitleFont = UnifiedAccessPoint.getLaunchProperty().getDefaultTitleFont();
		tabbedPane.setFont(defaultTitleFont);
		tabbedPane.setUI(new EGPSCustomTabbedPaneUI());

		initPanels(tabbedPane);
		add(tabbedPane, BorderLayout.CENTER);
	}

	private void initPanels(JTabbedPane tabbedPane) {
		{
			ExtractUniquePeptideRunner panel = new ExtractUniquePeptideRunner(this);
			subTabs.add(panel);
			tabbedPane.addTab(panel.getTabName(), null, panel, panel.getShortDescription());
		}
		{
			GetOneGeneWithLongestTranscriptPanel panel = new GetOneGeneWithLongestTranscriptPanel(this);
			subTabs.add(panel);
			tabbedPane.addTab(panel.getTabName(), null, panel, panel.getShortDescription());
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
		return new String[] { "Operate the protein sequences." };
	}

	@Override
	protected void initializeGraphics() {
		for (DockableTabModuleFaceOfVoice subTab : subTabs) {
			subTab.initializeGraphics();
		}
	}

	@Override
	public IInformation getModuleInfo() {
		return new IInformation() {

			@Override
			public String getWhatDataInvoked() {
				return "The data is loading from the import dialog.";
			}

			@Override
			public String getSummaryOfResults() {
				return "The functionality is powered by the eGPS software.";
			}
		};
	}
}
