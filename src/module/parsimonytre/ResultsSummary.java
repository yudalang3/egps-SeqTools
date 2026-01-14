package module.parsimonytre;

import evoltree.struct.EvolNode;
import evoltree.struct.util.EvolNodeUtil;
import module.analysehomogene.gui.CommonProcedureUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tsv.io.KitTable;
import tsv.io.TSVReader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * Summarize inferred node states into two reports:
 * - by position (each row contains all state transitions)
 * - by node (counts of transitions with event details)
 */
public class ResultsSummary {

	private static final Logger log = LoggerFactory.getLogger(ResultsSummary.class);

	private String pathOfLeafSates;
	private String pathOfInferredSates;
	private String pathOfPhylogeneticTree;
	private String leafIDColumnName = "leafName";
	private HashSet<String> excludeStatesColumns = new HashSet<>();

	private String outputSummaryByPosition;
	private String outputSummaryByNode;

	public void setPathOfLeafSates(String pathOfLeafSates) {
		this.pathOfLeafSates = pathOfLeafSates;
	}

	public void setPathOfInferredSates(String pathOfInferredSates) {
		this.pathOfInferredSates = pathOfInferredSates;
	}

	public void setPathOfPhylogeneticTree(String pathOfPhylogeneticTree) {
		this.pathOfPhylogeneticTree = pathOfPhylogeneticTree;
	}

	public void setLeafIDColumnName(String leafIDColumnName) {
		this.leafIDColumnName = leafIDColumnName;
	}

	public void setExcludeStatesColumns(HashSet<String> excludeStatesColumns) {
		this.excludeStatesColumns = excludeStatesColumns == null ? new HashSet<>() : excludeStatesColumns;
	}

	public void setOutputSummaryPaths(String outputSummaryByNode, String outputSummaryByPosition) {
		this.outputSummaryByPosition = outputSummaryByPosition;
		this.outputSummaryByNode = outputSummaryByNode;
	}

	public void run() throws Exception {
		EvolNode root = CommonProcedureUtil.getRootNode(pathOfPhylogeneticTree);
		List<Entry<String, Map<String, String>>> positions = new ArrayList<>(loadStates(root).entrySet());
		summarizeAll(positions, root);
	}

	private Map<String, Map<String, String>> loadStates(EvolNode root) throws IOException {
		Map<String, Map<String, String>> position2Name2State = new HashMap<>();

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

		return position2Name2State;
	}

	private void summarizeAll(List<Entry<String, Map<String, String>>> list, EvolNode root) {
		List<String> outputByPos = new ArrayList<>();
		Map<String, StringBuilder> nodeName2Events = new HashMap<>();

		EvolNodeUtil.recursiveIterateTreeIF(root, node -> nodeName2Events.put(node.getName(), new StringBuilder()));

		// No header line (compatible with previous behavior)
		for (Entry<String, Map<String, String>> entry : list) {
			String position = entry.getKey();

			StringJoiner joiner = new StringJoiner("\t");
			joiner.add(position);

			EvolNodeUtil.recursiveIterateTreeIF(root, node -> {
				Map<String, String> name2statesMap = entry.getValue();
				String name = node.getName();
				String currentState = name2statesMap.get(name);

				EvolNode parent = node.getParent();
				if (parent == null) {
					if (!Objects.equals(currentState, "0")) {
						String stateTrans = " 0 --> " + currentState;
						joiner.add(name + stateTrans);
						nodeName2Events.get(name).append("$ ").append(position).append(stateTrans);
					}
				} else {
					String parState = name2statesMap.get(parent.getName());
					if (!Objects.equals(currentState, parState)) {
						String stateTrans = " " + parState + " --> " + currentState;
						joiner.add(name + stateTrans);
						nodeName2Events.get(name).append("$ ").append(position).append(stateTrans);
					}
				}
			});

			outputByPos.add(joiner.toString());
		}

		try {
			FileUtils.writeLines(new File(outputSummaryByPosition), outputByPos);
		} catch (IOException e) {
			log.error("Write summary by position failed: {}", e.getMessage(), e);
		}

		List<String> outputByNode = new ArrayList<>();
		outputByNode.add("NodeName\tcounts\tevents");
		EvolNodeUtil.recursiveIterateTreeIF(root, node -> {
			String name = node.getName();
			String events = nodeName2Events.get(name).toString();
			int countMatches = StringUtils.countMatches(events, '$');
			outputByNode.add(name + "\t" + countMatches + "\t" + events);
		});

		try {
			FileUtils.writeLines(new File(outputSummaryByNode), outputByNode);
		} catch (IOException e) {
			log.error("Write summary by node failed: {}", e.getMessage(), e);
		}
	}
}

