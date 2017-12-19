/*
 * File: NameSurferEntry.java
 * --------------------------
 * This class represents a single entry in the database.  Each
 * NameSurferEntry contains a name and a list giving the popularity
 * of that name for each decade stretching back to 1900.
 */

public class NameSurferEntry implements NameSurferConstants {

	public static String name = "";
	
	/* Constructor: NameSurferEntry(line) */
	/**
	 * Creates a new NameSurferEntry from a data line as it appears
	 * in the data file.  Each line begins with the name, which is
	 * followed by integers giving the rank of that name for each
	 * decade.
	 */
	public NameSurferEntry(String line) {
		// You fill this in //
		entry = line;
	}

	/* Method: getName() */
	/**
	 * Returns the name associated with this entry.
	 */
	public String getName() {
		// You need to turn this stub into a real implementation //
		if(entry != null){
			int nameStart = 0;
			int nameEnd = entry.indexOf(" ");
/*			String temp = entry.substring(nameStart, nameEnd).toLowerCase();
			String firstLetter = temp.substring(nameStart, nameStart).toUpperCase();
			name = firstLetter+temp.substring(1);
			return name;*/
			return entry.substring(nameStart, nameEnd);
		} else {
			return "quack";// name entered in text field doesn't exist &/ names-text.txt is corrupt
		}
	}

	/* Method: getRank(decade) */
	/**
	 * Returns the rank associated with an entry for a particular
	 * decade.  The decade value is an integer indicating how many
	 * decades have passed since the first year in the database,
	 * which is given by the constant START_DECADE.  If a name does
	 * not appear in a decade, the rank value is 0.
	 */
	public int getRank(int decade) {
		// You need to turn this stub into a real implementation //
		if(entry != ""){
			frequency = new int[NameSurferConstants.NDECADES];
			currentDecadeStart = entry.indexOf(" ")+1;
			
			// A 83 140 228 286 426 612 486 577 836 0 0 0
			for(int i = 0; i < NameSurferConstants.NDECADES; i++){
				if(i == NameSurferConstants.NDECADES-1){
					String num = entry.substring(currentDecadeStart);
					frequency[i] = Integer.parseInt(num);
				} else {
					int currentDecadeEnd = entry.indexOf(" ", currentDecadeStart);
					String num = entry.substring(currentDecadeStart, currentDecadeEnd);
					frequency[i] = Integer.parseInt(num);
					currentDecadeStart = currentDecadeEnd+1;
				}
			}
			
			if(decade >= 0 && decade < NameSurferConstants.NDECADES){
				return frequency[decade];
			} else {
				return -2;// the text file read in is corrupt
			}
		}	else {
			return -1;
		}
	}

	/* Method: toString() */
	/**
	 * Returns a string that makes it easy to see the value of a
	 * NameSurferEntry.
	 */
	public String toString() {
		// You need to turn this stub into a real implementation //
		return "Name: " + entry + ", Frequencies: " + frequency.toString();
	}
	private String entry;// Mark 194 215 226 199 57 9 6 18 33 49 80 295
	private int[] frequency;
	private int currentDecadeStart;
}