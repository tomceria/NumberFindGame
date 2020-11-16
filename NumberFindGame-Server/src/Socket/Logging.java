package Socket;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class Logging {

	static String filePath = new File("").getAbsolutePath() + "\\log.txt";
	static File file = new File(filePath);

	public static void readFile() {
		BufferedReader reader = null;
		String result = "";
		String currentLine;

		try {
			reader = new BufferedReader(new FileReader(file));

			while ((currentLine = reader.readLine()) != null) {
				System.out.println(currentLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}

	public static void writeFile(String s) {
		FileWriter fw = null;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"); //16-11-2020 00:39:47
		LocalDateTime now = LocalDateTime.now();
		System.out.println(dtf.format(now));
		try {
			fw = new FileWriter(file, true);

			fw.write(dtf.format(now) + "  " + s);
			fw.write("\n");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
