package module.tablecuration;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.JTabbedPane;

import com.google.common.collect.Lists;

import egps2.UnifiedAccessPoint;
import egps2.builtin.modules.voice.fastmodvoice.DockableTabModuleFaceOfVoice;
import egps2.frame.ComputationalModuleFace;
import egps2.frame.gui.EGPSCustomTabbedPaneUI;
import module.tablecuration.gui.CountRowEntriesPanel;
import module.tablecuration.gui.ExtractTargetRowsPanel;
import module.tablecuration.gui.FilteringRowsAccording2AnotherTablePanel;
import module.tablecuration.gui.RemoveDupEntriesPanel;
import module.tablecuration.gui.TwoColumnTextComparatorPanel;
import module.tablecuration.gui.TwoTableComparatorPanel;
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
		jTabbedPane.setUI(new EGPSCustomTabbedPaneUI());

		{
			ExtractTargetRowsPanel panel3 = new ExtractTargetRowsPanel(this);
			subTabs.add(panel3);
			jTabbedPane.addTab(panel3.getTabName(), null, panel3, panel3.getShortDescription());
		}
		{
			RemoveDupEntriesPanel panel3 = new RemoveDupEntriesPanel(this);
			subTabs.add(panel3);
			jTabbedPane.addTab(panel3.getTabName(), null, panel3, panel3.getShortDescription());
		}
		{
			CountRowEntriesPanel panel3 = new CountRowEntriesPanel(this);
			subTabs.add(panel3);
			jTabbedPane.addTab(panel3.getTabName(), null, panel3, panel3.getShortDescription());
		}
		{
			TwoTableComparatorPanel panel3 = new TwoTableComparatorPanel(this);
			subTabs.add(panel3);
			jTabbedPane.addTab(panel3.getTabName(), null, panel3, panel3.getShortDescription());
		}
		{
			TwoColumnTextComparatorPanel panel3 = new TwoColumnTextComparatorPanel(this);
			subTabs.add(panel3);
			jTabbedPane.addTab(panel3.getTabName(), null, panel3, panel3.getShortDescription());
		}
		{
			FilteringRowsAccording2AnotherTablePanel panel3 = new FilteringRowsAccording2AnotherTablePanel(this);
			subTabs.add(panel3);
			jTabbedPane.addTab(panel3.getTabName(), null, panel3, panel3.getShortDescription());
		}

		add(jTabbedPane, BorderLayout.CENTER);
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
		return new String[] { "Identify the homologous genes" };
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
