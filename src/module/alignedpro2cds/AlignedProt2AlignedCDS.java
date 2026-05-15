package module.alignedpro2cds;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import fasta.io.FastaReader;
import org.apache.commons.io.FileUtils;

public class AlignedProt2AlignedCDS {
	private static final String FASTA_ID_DELIMITER = "\\s+";
	
	public static void makeTheConversion(File alignedProtFile,File unAlignedCDSFile,File outputFile) throws IOException {
		LinkedHashMap<String, String> protSeqs = FastaReader.readFastaDNASequence(alignedProtFile);
		LinkedHashMap<String, String> cdsSeqs = FastaReader.readFastaDNASequence(unAlignedCDSFile);
		
		if (protSeqs.size() != cdsSeqs.size()) {
			throw new IOException("FASTA record count mismatch: aligned protein has " + protSeqs.size()
					+ " records, CDS has " + cdsSeqs.size() + " records.");
		}

		Map<String, Entry<String, String>> cdsById = indexByFirstId(cdsSeqs, "CDS");
		validateProteinIds(protSeqs, cdsById);
		
		List<String> output = new ArrayList<>();
		StringBuilder sBuilder = new StringBuilder();
		
		for (Entry<String, String> protEntry : protSeqs.entrySet()) {
			String protHeader = protEntry.getKey();
			String protId = getFirstId(protHeader);
			String protSeq = protEntry.getValue();
			Entry<String, String> cdsEntry = cdsById.get(protId);
			String cdsSeq = cdsEntry.getValue();
			
			validateCdsLength(protId, protSeq, cdsSeq);
			
			int indexOfAA = 0;
			sBuilder.setLength(0);
			for (char c : protSeq.toCharArray()) {
				if (c == '-') {
					sBuilder.append("---");
				}else {
					int fromIndex = indexOfAA * 3;
					sBuilder.append(cdsSeq, fromIndex, fromIndex + 3);
					indexOfAA ++;
				}
			}
			
			output.add(">".concat(cdsEntry.getKey()));
			output.add(sBuilder.toString());
		}
		
		FileUtils.writeLines(outputFile, output);
	}

	private static Map<String, Entry<String, String>> indexByFirstId(LinkedHashMap<String, String> seqs, String fastaName)
			throws IOException {
		Map<String, Entry<String, String>> byId = new LinkedHashMap<>();
		for (Entry<String, String> entry : seqs.entrySet()) {
			String id = getFirstId(entry.getKey());
			if (byId.put(id, entry) != null) {
				throw new IOException("Duplicate " + fastaName + " FASTA ID: " + id);
			}
		}
		return byId;
	}

	private static void validateProteinIds(LinkedHashMap<String, String> protSeqs,
			Map<String, Entry<String, String>> cdsById) throws IOException {
		Set<String> proteinIds = new HashSet<>();
		for (String protHeader : protSeqs.keySet()) {
			String protId = getFirstId(protHeader);
			if (!proteinIds.add(protId)) {
				throw new IOException("Duplicate protein FASTA ID: " + protId);
			}
			if (!cdsById.containsKey(protId)) {
				throw new IOException("Missing CDS sequence for protein FASTA ID: " + protId);
			}
		}

		for (String cdsId : cdsById.keySet()) {
			if (!proteinIds.contains(cdsId)) {
				throw new IOException("CDS FASTA ID has no matching protein sequence: " + cdsId);
			}
		}
	}

	private static void validateCdsLength(String id, String protSeq, String cdsSeq) throws IOException {
		int cdsLength = cdsSeq.length();
		if (cdsLength % 3 != 0) {
			throw new IOException("CDS length is not a multiple of 3 for ID " + id + ": " + cdsLength + " nt.");
		}

		int protResidues = countNonGapResidues(protSeq);
		int requiredCdsLength = protResidues * 3;
		if (cdsLength != requiredCdsLength && cdsLength != requiredCdsLength + 3) {
			throw new IOException("Protein/CDS length mismatch for ID " + id + ": protein has " + protResidues
					+ " non-gap residues, CDS has " + cdsLength + " nt.");
		}
	}

	private static int countNonGapResidues(String protSeq) {
		int count = 0;
		for (int i = 0; i < protSeq.length(); i++) {
			if (protSeq.charAt(i) != '-') {
				count++;
			}
		}
		return count;
	}

	private static String getFirstId(String fastaHeader) throws IOException {
		String trimmedHeader = fastaHeader.trim();
		if (trimmedHeader.isEmpty()) {
			throw new IOException("FASTA header is empty.");
		}
		return trimmedHeader.split(FASTA_ID_DELIMITER, 2)[0];
	}

}
