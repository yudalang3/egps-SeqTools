package module.parsimonytre;

import com.google.common.collect.Lists;
import egps2.UnifiedAccessPoint;
import egps2.panels.dialog.SwingDialog;
import evoltree.struct.EvolNode;
import evoltree.struct.util.EvolNodeUtil;
import module.analysehomogene.gui.CommonProcedureUtil;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tsv.io.KitTable;
import tsv.io.TSVReader;
import utils.string.EGPSStringUtil;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.StringJoiner;

/**
 * Watch/visualize the inferred node states by comparing leaf states + inferred internal states.
 *
 * <p>Note: The legacy visualization based on evolview graphics is removed from this collection.
 * This implementation provides a lightweight transition summary and optional TSV output.
 */
public class WatchNodeStatesWithChange {

	private static final Logger log = LoggerFactory.getLogger(WatchNodeStatesWithChange.class);

	private String pathOfLeafSates;
	private String pathOfInferredSates;
	private String pathOfPhylogeneticTree;

	/**
	 * 0 for all; otherwise one-based index of position to watch.
	 */
	protected int indexOfPositionOneBased = 0;

	protected boolean interactiveWatch = false;
	private String leafIDColumnName = "leafName";
	private HashSet<String> excludeStatesColumns = new HashSet<>();
	private Optional<String> outputOneFigurePathOption = Optional.empty();
	private String ancestralStatesPath;

	public void setInteractiveWatch(boolean interactiveWatch) {
		this.interactiveWatch = interactiveWatch;
	}

	public void setPathOfLeafSates(String pathOfLeafSates) {
		this.pathOfLeafSates = pathOfLeafSates;
	}

	public void setPathOfInferredSates(String pathOfInferredSates) {
		this.pathOfInferredSates = pathOfInferredSates;
	}

	public void setPathOfPhylogeneticTree(String pathOfPhylogeneticTree) {
		this.pathOfPhylogeneticTree = pathOfPhylogeneticTree;
	}

	public void setOutputTheSummaryEvoutionEvents(String outputTheSummaryEvoutionEvents) {
		// Legacy field kept for compatibility; this implementation writes to outputOneFigurePathOption when provided.
	}

	public void setIndexOfPositionOneBased(int indexOfPositionOneBased) {
		this.indexOfPositionOneBased = indexOfPositionOneBased;
	}

	public void setLeafIDColumnName(String leafIDColumnName) {
		this.leafIDColumnName = leafIDColumnName;
	}

	public void setExcludeStatesColumns(HashSet<String> excludeStatesColumns) {
		this.excludeStatesColumns = excludeStatesColumns == null ? new HashSet<>() : excludeStatesColumns;
	}

	public void setOutputOneFigurePath(String outputOneFigurePath) {
		this.outputOneFigurePathOption = Optional.ofNullable(outputOneFigurePath).filter(s -> !s.isBlank());
	}

	public void setAncestralStatesPath(String ancestralStatesPath) {
		this.ancestralStatesPath = ancestralStatesPath;
	}

	public void run() throws Exception {
		EvolNode root = CommonProcedureUtil.getRootNode(pathOfPhylogeneticTree);

		Map<String, Map<String, String>> position2Name2State = loadStates(root);
		ArrayList<Entry<String, Map<String, String>>> positions = new ArrayList<>(position2Name2State.entrySet());

		if (positions.isEmpty()) {
			throw new IllegalArgumentException("No position records found after loading leaf/inferred states.");
		}

		if (indexOfPositionOneBased > 0) {
			int index = indexOfPositionOneBased - 1;
			if (index < 0 || index >= positions.size()) {
				throw new IndexOutOfBoundsException("position.to.watch out of range: " + indexOfPositionOneBased);
			}

			Entry<String, Map<String, String>> entry = positions.get(index);
			String position = entry.getKey();
			List<String> report = buildTransitionReport(position, root, entry.getValue());

			if (outputOneFigurePathOption.isPresent()) {
				FileUtils.writeLines(new File(outputOneFigurePathOption.get()), StandardCharsets.UTF_8.name(), report);
			}

			if (interactiveWatch && UnifiedAccessPoint.isGULaunched()) {
				SwingDialog.showInfoMSGDialog("Position " + position + " transitions", String.join("\n", report));
			} else {
				log.info("Position {} transitions:\n{}", position, String.join("\n", report));
			}

			return;
		}

		if (outputOneFigurePathOption.isPresent()) {
			String outPath = outputOneFigurePathOption.get();
			List<String> summary = buildAllPositionsSummary(positions, root);
			FileUtils.writeLines(new File(outPath), StandardCharsets.UTF_8.name(), summary);
			log.info("Wrote transitions summary to: {}", outPath);
			if (interactiveWatch && UnifiedAccessPoint.isGULaunched()) {
				SwingDialog.showInfoMSGDialog("Output written", "Transitions summary written to:\n" + outPath);
			}
		} else if (interactiveWatch && UnifiedAccessPoint.isGULaunched()) {
			SwingDialog.showWarningMSGDialog("No output path",
					"Please set $output.figure.data.path to write a transitions summary, or set $position.to.watch to view one position.");
		}
	}

	private Map<String, Map<String, String>> loadStates(EvolNode root) throws IOException {
		Map<String, Map<String, String>> position2Name2State = new LinkedHashMap<>();

		// leaf states
		Map<String, List<String>> positionAsKey = TSVReader.readAsKey2ListMap(pathOfLeafSates);
		for (String excludeStatesColumn : excludeStatesColumns) {
			positionAsKey.remove(excludeStatesColumn);
		}
		List<String> leafNames = positionAsKey.remove(leafIDColumnName);
		if (leafNames == null) {
			throw new IllegalArgumentException("Leaf states file missing leaf identifier column: " + leafIDColumnName);
		}

		for (Entry<String, List<String>> entry : positionAsKey.entrySet()) {
			String position = entry.getKey();
			List<String> values = entry.getValue();
			Map<String, String> map = new HashMap<>();
			for (int i = 0; i < values.size(); i++) {
				map.put(leafNames.get(i), values.get(i));
			}
			position2Name2State.put(position, map);
		}

		// inferred node states (optional)
		if (pathOfInferredSates != null && !pathOfInferredSates.isBlank()) {
			KitTable inferred = TSVReader.readTsvTextFile(pathOfInferredSates);
			List<String> headerNames = inferred.getHeaderNames();
			if (headerNames.isEmpty() || !Objects.equals("position", headerNames.getFirst())) {
				throw new IllegalArgumentException("Invalid inferred states file: first header must be 'position'.");
			}

			int numOfCols = headerNames.size();
			int numOfPositions = numOfCols - 2;
			for (List<String> row : inferred.getContents()) {
				String positionStr = row.getFirst();
				Map<String, String> nodeStates = new HashMap<>();
				for (int i = 1; i < numOfPositions; i++) {
					nodeStates.put(headerNames.get(i), row.get(i));
				}
				position2Name2State.merge(positionStr, nodeStates, (oldMap, newMap) -> {
					oldMap.putAll(newMap);
					return oldMap;
				});
			}
		}

		// sanity check: all leaves in tree should exist in each position
		List<EvolNode> leaves = EvolNodeUtil.getLeaves(root);
		for (Entry<String, Map<String, String>> entry : position2Name2State.entrySet()) {
			Map<String, String> states = entry.getValue();
			for (EvolNode leaf : leaves) {
				if (!states.containsKey(leaf.getName())) {
					throw new IllegalArgumentException("Leaf '" + leaf.getName() + "' not found for position: " + entry.getKey());
				}
			}
		}

		return position2Name2State;
	}

	private static List<String> buildTransitionReport(String position, EvolNode root, Map<String, String> name2state) {
		List<String> lines = Lists.newArrayList();
		lines.add("position\t" + position);
		lines.add("nodeName\tfrom\tto");

		EvolNodeUtil.recursiveIterateTreeIF(root, node -> {
			String current = name2state.get(node.getName());
			EvolNode parent = node.getParent();
			String parentState = parent == null ? "0" : name2state.get(parent.getName());
			if (!Objects.equals(parentState, current)) {
				lines.add(node.getName() + "\t" + parentState + "\t" + current);
			}
		});

		return lines;
	}

	private static List<String> buildAllPositionsSummary(List<Entry<String, Map<String, String>>> positions, EvolNode root) {
		List<String> output = new ArrayList<>();
		output.add("position\ttransitions");

		for (Entry<String, Map<String, String>> entry : positions) {
			String position = entry.getKey();
			Map<String, String> name2state = entry.getValue();

			StringJoiner joiner = new StringJoiner(" ; ");
			EvolNodeUtil.recursiveIterateTreeIF(root, node -> {
				String current = name2state.get(node.getName());
				EvolNode parent = node.getParent();
				String parentState = parent == null ? "0" : name2state.get(parent.getName());
				if (!Objects.equals(parentState, current)) {
					joiner.add(node.getName() + ":" + parentState + "->" + current);
				}
			});

			output.add(position + "\t" + joiner);
		}

		return output;
	}
}
