package module.ambigbse;

/**
 * Utility for generating DNA complements and reverse complements.
 *
 * <p>This class supports common IUPAC nucleotide codes:
 * A, C, G, T, R, Y, S, W, K, M, B, D, H, V, N.
 */
public class DNAComplement {

	public String getReverseComplement(String sequence) {
		if (sequence == null) {
			return null;
		}
		int length = sequence.length();
		StringBuilder sb = new StringBuilder(length);
		for (int i = length - 1; i >= 0; i--) {
			sb.append(getComplementBase(sequence.charAt(i)));
		}
		return sb.toString();
	}

	public char getComplementBase(char base) {
		char upper = Character.toUpperCase(base);
		char complement = switch (upper) {
			case 'A' -> 'T';
			case 'T' -> 'A';
			case 'C' -> 'G';
			case 'G' -> 'C';
			case 'R' -> 'Y'; // A/G <-> C/T
			case 'Y' -> 'R';
			case 'S' -> 'S'; // C/G
			case 'W' -> 'W'; // A/T
			case 'K' -> 'M'; // G/T <-> A/C
			case 'M' -> 'K';
			case 'B' -> 'V'; // C/G/T <-> A/C/G
			case 'V' -> 'B';
			case 'D' -> 'H'; // A/G/T <-> A/C/T
			case 'H' -> 'D';
			case 'N' -> 'N';
			default -> 'N';
		};
		return Character.isLowerCase(base) ? Character.toLowerCase(complement) : complement;
	}
}

