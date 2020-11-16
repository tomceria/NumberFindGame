package Socket;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class Logger {

	static String filePath = "log.txt";
	static File file = new File(filePath);

	public static void writeFile(String s) {
		FileWriter fw = null;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"); // 16-11-2020 00:39:47
		LocalDateTime now = LocalDateTime.now();
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
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
