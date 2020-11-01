package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Display {

	public void tableDisplay(List<String[]> tableList) {
		/*
		 * leftJustifiedRows - If true, it will add "-" as a flag to format string to
		 * make it left justified. Otherwise right justified.
		 */
		boolean leftJustifiedRows = true;

		/*
		 * Maximum allowed width. Line will be wrapped beyond this width.
		 */
		int maxWidth = 40;

		/*
		 * Table to print in console in 2-dimensional array. Each sub-array is a row.
		 */
			
		
		/*
		 * Create new table array with wrapped rows
		 */
		List<String[]> finalTableList = new ArrayList<>();
		for (String[] row : tableList) {
			// If any cell data is more than max width, then it will need extra row.
			boolean needExtraRow = false;
			// Count of extra split row.
			int splitRow = 0;
			do {
				needExtraRow = false;
				String[] newRow = new String[row.length];
				for (int i = 0; i < row.length; i++) {
					// If data is less than max width, use that as it is.
					if (row[i].length() < maxWidth) {
						newRow[i] = splitRow == 0 ? row[i] : "";
					} else if ((row[i].length() > (splitRow * maxWidth))) {
						// If data is more than max width, then crop data at maxwidth.
						// Remaining cropped data will be part of next row.
						int end = row[i].length() > ((splitRow * maxWidth) + maxWidth)
								? (splitRow * maxWidth) + maxWidth
								: row[i].length();
						newRow[i] = row[i].substring((splitRow * maxWidth), end);
						needExtraRow = true;
					} else {
						newRow[i] = "";
					}
				}
				finalTableList.add(newRow);
				if (needExtraRow) {
					splitRow++;
				}
			} while (needExtraRow);
		}
		String[][] finalTable = new String[finalTableList.size()][finalTableList.get(0).length];
		for (int i = 0; i < finalTable.length; i++) {
			finalTable[i] = finalTableList.get(i);
		}

		/*
		 * Calculate appropriate Length of each column by looking at width of data in
		 * each column.
		 * 
		 * Map columnLengths is <column_number, column_length>
		 */
		Map<Integer, Integer> columnLengths = new HashMap<>();
		Arrays.stream(finalTable).forEach(a -> Stream.iterate(0, (i -> i < a.length), (i -> ++i)).forEach(i -> {
			if (columnLengths.get(i) == null) {
				columnLengths.put(i, 0);
			}
			if (columnLengths.get(i) < a[i].length()) {
				columnLengths.put(i, a[i].length());
			}
		}));
		//System.out.println("columnLengths = " + columnLengths);

		/*
		 * Prepare format String
		 */
		final StringBuilder formatString = new StringBuilder("");
		String flag = leftJustifiedRows ? "-" : "";
		columnLengths.entrySet().stream().forEach(e -> formatString.append("| %" + flag + e.getValue() + "s "));
		formatString.append("|\n");
		//System.out.println("formatString = " + formatString.toString());

		/*
		 * Prepare line for top, bottom & below header row.
		 */
		String line = columnLengths.entrySet().stream().reduce("", (ln, b) -> {
			String templn = "+-";
			templn = templn + Stream.iterate(0, (i -> i < b.getValue()), (i -> ++i)).reduce("", (ln1, b1) -> ln1 + "-",
					(a1, b1) -> a1 + b1);
			templn = templn + "-";
			return ln + templn;
		}, (a, b) -> a + b);
		line = line + "+\n";
		//System.out.println("Line = " + line);

		/*
		 * Print table
		 */
		System.out.print(line);
		Arrays.stream(finalTable).limit(1).forEach(a -> System.out.printf(formatString.toString(), a));
		System.out.print(line);

		Stream.iterate(1, (i -> i < finalTable.length), (i -> ++i))
				.forEach(a -> System.out.printf(formatString.toString(), finalTable[a]));
		System.out.print(line);
	}
}
